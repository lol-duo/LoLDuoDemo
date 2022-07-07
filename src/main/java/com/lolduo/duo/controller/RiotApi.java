package com.lolduo.duo.controller;


import com.lolduo.duo.service.RiotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiotApi {
    @Autowired
    private RiotService riotService;

    @GetMapping("/challenger")
    public void getChallengerlist(@RequestParam(value = "k")String key, @RequestParam(value = "s")String startTime, @RequestParam(value = "e") String endTime){
        riotService.getChallengerList(key, startTime, endTime);
    }

    @GetMapping("/getMatch")
    public void getMatchId(@RequestParam(value = "k")String key, @RequestParam(value = "s")String startTime, @RequestParam(value = "e") String endTime) {
        riotService.getMatchId(key, startTime, endTime);
    }

    @GetMapping("/getSolo")
    public void makeDB(){
        riotService.getSolo();
    }
}
