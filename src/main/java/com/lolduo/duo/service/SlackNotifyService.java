package com.lolduo.duo.service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackNotifyService {

    String notifyChannelName = "notification";
    String notifyChannelId;
    @Value("${slack.token}")
    String token;
    @Value("${spring.profiles.active}")
    String springProfile;

    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshedEvent(ContextRefreshedEvent event) {
        if (springProfile.equals("local")) {
            if (notifyChannelId == null)
                initChannelId();

            sendMessage(LocalDateTime.now() + " - 서버를 기동합니다.");
        }
    }

    @EventListener(ContextClosedEvent.class)
    public void onContextClosedEvent(ContextClosedEvent event) {
        if (springProfile.equals("server")) {
            if (notifyChannelId == null)
                initChannelId();

            sendMessage(LocalDateTime.now() + " - 서버가 종료되었습니다.");
        }
    }

    public void sendMessage(String text) {
        if (notifyChannelId == null) {
            log.error("[SlackAPI] error: channelId is null");
            return;
        }

        var client = Slack.getInstance().methods();
        try {
            var result = client.chatPostMessage( requestConfig ->
                     requestConfig
                            .token(token)
                            .channel(notifyChannelId)
                            .text(text)
            );
            log.info("[SlackAPI] result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("[SlackAPI] error: {}", e.getMessage(), e);
        }
    }

    private void initChannelId() {
        log.info("[SlackAPI] channelName: {}", notifyChannelName);
        var client = Slack.getInstance().methods();

        try {
            var result = client.conversationsList( requestConfig ->
                    requestConfig.token(token)
            );
            for (Conversation channel : result.getChannels()) {
                if (channel.getName().equals(notifyChannelName)) {
                    notifyChannelId = channel.getId();
                    log.info("[SlackAPI] Channel ID: {}", notifyChannelId);
                    break;
                }
            }
        } catch (IOException | SlackApiException | NullPointerException e) {
            log.error("[SlackAPI] error: {}", e.getMessage(), e);
        }
    }
}
