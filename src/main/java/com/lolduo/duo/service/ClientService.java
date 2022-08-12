package com.lolduo.duo.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lolduo.duo.object.dto.client.ChampionInfoDTO;
import com.lolduo.duo.object.entity.clientInfo.sub.Item;
import com.lolduo.duo.object.entity.clientInfo.sub.Perk;
import com.lolduo.duo.object.entity.clientInfo.sub.Spell;
import com.lolduo.duo.object.entity.clientInfo.sub.Sub;
import com.lolduo.duo.object.response.ChampionInfoList;
import com.lolduo.duo.object.response.sub.ChampionInfo;
import com.lolduo.duo.object.entity.ChampionEntity;
import com.lolduo.duo.object.entity.clientInfo.ICombinationInfoEntity;
import com.lolduo.duo.object.entity.clientInfo.entity.SoloInfoEntity;
import com.lolduo.duo.repository.ChampionRepository;
import com.lolduo.duo.repository.clientInfo.*;
import com.lolduo.duo.repository.clientInfo.repository.DuoInfoRepository;
import com.lolduo.duo.repository.clientInfo.repository.QuintetInfoRepository;
import com.lolduo.duo.repository.clientInfo.repository.SoloInfoRepository;
import com.lolduo.duo.repository.clientInfo.repository.TrioInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final SoloInfoRepository soloInfoRepository;
    private final DuoInfoRepository duoInfoRepository;
    private final TrioInfoRepository trioInfoRepository;
    private final QuintetInfoRepository quintetInfoRepository;
    private final ChampionRepository championRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public ResponseEntity<?> getChampionList(){
        List<ChampionEntity> championEntityList = new ArrayList<>(championRepository.findAll());
        for(ChampionEntity champion : championEntityList){
            champion.setImgUrl("https://lol-duo-bucket.s3.ap-northeast-2.amazonaws.com/champion/"+champion.getImgUrl());
        }
        Collections.sort(championEntityList);
        return new ResponseEntity<>(championEntityList, HttpStatus.OK);
    }

    private ChampionInfo championInfo2ClientChampionInfo(ChampionInfoDTO championInfoDTO){  // 챔피언 이름, 이미지 URL, 포지션 가져옴
        log.info(championRepository.findAll().size()+" 사이즈가 0 인 경우, ritoService에서 setChampion 실행 아직 안된 상태");
        ChampionEntity champion = championRepository.findById(championInfoDTO.getChampionId()).orElse(new ChampionEntity(0L,"A","A.png"));
        String baseUrl = "https://lol-duo-bucket.s3.ap-northeast-2.amazonaws.com/champion/";
        String positionbaseUrl = "https://lol-duo-bucket.s3.ap-northeast-2.amazonaws.com/line/";
        return new ChampionInfo(champion.getName() ,baseUrl+champion.getImgUrl(),championInfoDTO.getPosition(),positionbaseUrl + championInfoDTO.getPosition()+".png");
    }

    public ResponseEntity<?> getChampionInfoList(ArrayList<ChampionInfoDTO> championInfoDTOList){
        // 주석은 모듈화를 위해 임시로 추가한 것. 이후 삭제해야 함.
        List<ChampionInfoList> result = new ArrayList<>();
        ICombinationInfoRepository infoRepository;
        int championCount = championInfoDTOList.size();

        // 챔피언 수에 해당하는 리포지토리를 가져오는 부분
        infoRepository = getInfoRepository(championCount);
        if(infoRepository == null && championCount != 1)
            return new ResponseEntity<>("요청한 챔피언 개수가 올바르지 않습니다. (1, 2, 3, 5만 가능)",HttpStatus.BAD_REQUEST);

        // 챔피언 수가 1일 때
        if (championCount == 1) {
            ChampionInfoDTO championInfoDTO = championInfoDTOList.get(0);
            List<SoloInfoEntity> infoEntityList;

            log.info("getChampionInfoList() - 매치 데이터 검색.\n검색 championId = {}\n검색 position = {}", championInfoDTO.getChampionId(), championInfoDTO.getPosition());
            // 리포지토리에서 조합 정보를 검색하는 부분
            if (championInfoDTO.getChampionId() == 0) { // ?일 때
                if (!championInfoDTO.getPosition().equals("ALL")) // ALL이 아닐 때
                    infoEntityList = soloInfoRepository.findAllByPositionDesc(championInfoDTO.getPosition()).orElse(null);
                else
                    infoEntityList = soloInfoRepository.findAllDesc().orElse(null);
            }
            else { // ?이 아닐 때
                if (!championInfoDTO.getPosition().equals("ALL")) // ALL이 아닐 때
                    infoEntityList = soloInfoRepository.findAllByChampionIdAndPositionDesc(championInfoDTO.getChampionId(), championInfoDTO.getPosition()).orElse(null);
                else
                    infoEntityList = soloInfoRepository.findAllByChampionIdDesc(championInfoDTO.getChampionId()).orElse(null);
            }

            // 조합 정보가 존재한다면 ClientChampionInfo의 List를 만들고, 승률을 계산해 반환한다.
            if (infoEntityList != null && !infoEntityList.isEmpty()) {
                log.info("getChampionInfoList() - 검색 결과.");
                infoEntityList.forEach( infoEntity -> {
                    log.info("championId = {}, position = {}, AllCount = {}, WinCount = {}",
                            infoEntity.getChampionId(), infoEntity.getPosition(), infoEntity.getAllCount(), infoEntity.getWinCount());

                    List<ChampionInfo> clientChampionInfoList = new ArrayList<>(1);
                    clientChampionInfoList.add(championInfo2ClientChampionInfo(new ChampionInfoDTO(infoEntity.getChampionId(), infoEntity.getPosition())));
                    result.add(new ChampionInfoList(clientChampionInfoList, String.format("%.2f%%", 100 * ((double) infoEntity.getWinCount() / infoEntity.getAllCount())),
                            String.valueOf(infoEntity.getAllCount()).replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",") + " 게임"));
                });
            }
            // 조합 정보가 존재하지 않는다면 입력 값을 그대로 ClientChampionInfo로 변환한 List만 반환한다.
            else {
                log.info("getChampionInfoList() - 검색 결과.\n해당하는 데이터 행이 존재하지 않습니다.");
                List<ChampionInfo> clientChampionInfoList = new ArrayList<ChampionInfo>(1);
                clientChampionInfoList.add(championInfo2ClientChampionInfo(championInfoDTO));
                result.add(new ChampionInfoList(clientChampionInfoList, "데이터가 존재하지 않습니다.","0 게임"));
            }
        }
        //챔피언 수가 2 이상일 때
        else {
            Map<Long, String> champPositionMap = new HashMap<Long, String>();
            List<String> excludePositionList = new ArrayList<>(5);

            Map<Long, Long> selectedChampionOrderMap = new HashMap<>();
            Map<String, Long> selectedPositionOrderMap = new HashMap<>();
            Queue<ChampionInfo> allQueue = new LinkedList<ChampionInfo>();

            // 입력된 챔피언 각각에 대해 ?인지, 그리고 ALL포지션인지 확인하여 포지션, 챔피언 목록과 관련된 객체를 채워 넣는다.
            setChampAndPositionInfo(championInfoDTOList, champPositionMap, excludePositionList, selectedChampionOrderMap, selectedPositionOrderMap);

            try {
                // DB에서 조합 정보를 검색한다.
                List<? extends ICombinationInfoEntity> infoEntityList = infoRepository
                        .findAllByChampionIdAndPositionDesc(objectMapper.writeValueAsString(selectedChampionOrderMap.keySet()), objectMapper.writeValueAsString(champPositionMap), objectMapper.writeValueAsString(selectedPositionOrderMap.keySet()), objectMapper.writeValueAsString(excludePositionList)).orElse(null);
                log.info("getChampionInfoList() - 매치 데이터 검색.\n지정된 championId = {}\n지정된 position = {}\n실제 검색 position = {}\n선택한 챔피언들에게 금지된 position = {}",
                        objectMapper.writeValueAsString(selectedChampionOrderMap.keySet()), objectMapper.writeValueAsString(selectedPositionOrderMap.keySet()), objectMapper.writeValueAsString(champPositionMap), objectMapper.writeValueAsString(excludePositionList));

                // 나온 검색 결과를 반환할 형태로 변환한 후 결과 리스트에 넣어준다.
                putCombinationInfoToResult(result, infoEntityList, championInfoDTOList, selectedPositionOrderMap, selectedChampionOrderMap, allQueue);
            } catch (JsonProcessingException e) {
                log.error("objectMapper writeValue error");
                return new ResponseEntity<>("404 BAD_REQUEST", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    private ICombinationInfoRepository getInfoRepository(int championCount) {
        if (championCount == 1) {
            log.info("getInfoRepository() - championCount : {}, 1명", championCount);
            return null;
        }
        else if (championCount == 2) {
            log.info("getInfoRepository() - championCount : {}, 2명", championCount);
            return duoInfoRepository;
        }
        else if (championCount == 3) {
            log.info("getInfoRepository() - championCount : {}, 3명", championCount);
            return trioInfoRepository;
        }
        else if (championCount == 5) {
            log.info("getInfoRepository() - championCount : {}, 5명", championCount);
            return quintetInfoRepository;
        }
        else {
            log.info("getInfoRepository() - 요청 문제 발생");
            return null;
        }
    }

    private void setChampAndPositionInfo(List<ChampionInfoDTO> championInfoDTOList, Map<Long, String> champPositionMap, List<String> excludePositionList, Map<Long, Long> selectedChampionOrderMap, Map<String, Long> selectedPositionOrderMap) {
        Long inputOrder = 0L;
        // 입력된 챔피언 각각에 대해 ?인지, 그리고 ALL포지션인지 확인하여 포지션, 챔피언 목록과 관련된객체를 채워 넣는다.
        for (ChampionInfoDTO championInfoDTO : championInfoDTOList) {
            if (championInfoDTO.getChampionId() == 0) {
                if (!championInfoDTO.getPosition().equals("ALL")) {
                    selectedPositionOrderMap.put(championInfoDTO.getPosition(), inputOrder);
                    excludePositionList.add(championInfoDTO.getPosition());
                }
            }
            else {
                selectedChampionOrderMap.put(championInfoDTO.getChampionId(), inputOrder);
                if (!championInfoDTO.getPosition().equals("ALL")) {
                    champPositionMap.put(championInfoDTO.getChampionId(), championInfoDTO.getPosition());
                    selectedPositionOrderMap.put(championInfoDTO.getPosition(), inputOrder);
                }
            }
            inputOrder++;
        }
        log.info("setChampAndPositionInfo - 챔피언/포지션 정보 저장 결과\nchampPositionMap: {}\nexcludePositionList: {}\nselectedChampionOrderMap: {}\nselectedPositionOrderMap: {}",
                champPositionMap, excludePositionList, selectedChampionOrderMap, selectedPositionOrderMap);
    }

    private void putCombinationInfoToResult(List<ChampionInfoList> result, List<? extends ICombinationInfoEntity> infoEntityList, List<ChampionInfoDTO> championInfoDTOList, Map<String, Long> selectedPositionOrderMap, Map<Long, Long> selectedChampionOrderMap, Queue<ChampionInfo> allQueue) {
        if (infoEntityList != null && !infoEntityList.isEmpty()) {
            log.info("putCombinationInfoToResult() - 조합 정보.");
            infoEntityList.forEach(infoEntity -> {
                log.info("putCombinationInfoToResult() - 검색 결과 : championId = {}, position = {}, AllCount = {}, WinCount = {}", infoEntity.getChampionId().toString(), infoEntity.getPosition().toString(), infoEntity.getAllCount(), infoEntity.getWinCount());
                result.add(new ChampionInfoList(
                                createClientChampionInfoDTOList(infoEntity.getChampionId().size(), infoEntity, selectedPositionOrderMap, selectedChampionOrderMap, allQueue),
                                String.format("%.2f%%", 100 * ((double) infoEntity.getWinCount() / infoEntity.getAllCount())),
                        String.valueOf(infoEntity.getAllCount()).replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",") + " 게임"

                        )
                );
            });
        }
        else {
            log.info("putCombinationInfoToResult() - 조합 정보.\n해당하는 데이터 행이 존재하지 않습니다.");
            List<ChampionInfo> clientChampionInfoList = new ArrayList<ChampionInfo>();
            championInfoDTOList.forEach(championInfoDTO ->
                    clientChampionInfoList.add(championInfo2ClientChampionInfo(championInfoDTO))
            );
            result.add(new ChampionInfoList(clientChampionInfoList, "데이터가 존재하지 않습니다.","0 게임"));
        }
    }

    private List<ChampionInfo> createClientChampionInfoDTOList(int championCount, ICombinationInfoEntity infoEntity, Map<String, Long> selectedPositionOrderMap, Map<Long, Long> selectedChampionOrderMap, Queue<ChampionInfo> allQueue) {
        ChampionInfo[] championInfoArray = new ChampionInfo[championCount];

        for (Map.Entry<Long, String> positionEntry : infoEntity.getPosition().entrySet()) {
            Long order = -1L;
            if (selectedPositionOrderMap.containsKey(positionEntry.getValue()))
                order = selectedPositionOrderMap.get(positionEntry.getValue());
            else if (selectedChampionOrderMap.containsKey(positionEntry.getKey()))
                order = selectedChampionOrderMap.get(positionEntry.getKey());

            if (order != -1L)
                championInfoArray[order.intValue()] = championInfo2ClientChampionInfo(new ChampionInfoDTO(positionEntry.getKey(), positionEntry.getValue()));
            else
                allQueue.offer(championInfo2ClientChampionInfo(new ChampionInfoDTO(positionEntry.getKey(), positionEntry.getValue())));
        }

        log.info("createClientChampionInfoDTOList() - 반환 리스트 원소 값");
        for (int i = 0; i < championInfoArray.length; i++) {
            if (championInfoArray[i] == null)
                championInfoArray[i] = allQueue.poll();

            log.info("createClientChampionInfoDTOList() - {}번째 원소 : {}={}", i + 1, championInfoArray[i].getChampionName(), championInfoArray[i].getPosition());
        }
        return Arrays.asList(championInfoArray);
    }
    public List<Item> getItemDetail(ArrayList<ChampionInfoDTO> championInfoDTOList){
        ICombinationInfoRepository repository = getInfoRepository(championInfoDTOList.size());
        List<Item> itemList = new ArrayList<>();
        if(championInfoDTOList.size()==1){ //1명일 때
            ChampionInfoDTO championInfoDTO = championInfoDTOList.get(0);
            SoloInfoEntity soloInfoEntity = soloInfoRepository.findByChampionIdAndPosition(championInfoDTO.getChampionId(), championInfoDTO.getPosition()).orElse(null);
            if(soloInfoEntity==null){
                log.info("1명일때 soloInfoEntity null 오류!");
                return null;
            }
            findTopK(soloInfoEntity.getItemList(),2).forEach(item ->{
                itemList.add((Item)item);
            });
        }
        else { //2명이상일 때
            Map<Long, String> champPositionMap = new HashMap<Long, String>();
            TreeSet<Long> championIdSet = new TreeSet<>();
            setChampAndPositionInfo(championInfoDTOList,champPositionMap,championIdSet);
            ICombinationInfoEntity infoEntity =null;
            try{
                infoEntity = repository.findByChampionIdAndPosition(objectMapper.writeValueAsString(championIdSet),objectMapper.writeValueAsString(champPositionMap)).orElse(null);
            }   catch (JsonProcessingException e) {
                log.info("objectMapper Json Parsing 오류!");
                return null;
            }
            if(infoEntity ==null){
                log.info(" 2명이상일때 infoEntity null 오류!");
                return null;
            }
            findTopK(infoEntity.getItemList(),2).forEach(item ->{
                itemList.add((Item)item);
            });
        }
        return itemList;
    }
    public List<Perk> getPerkDetail(ArrayList<ChampionInfoDTO> championInfoDTOList){
        ICombinationInfoRepository repository = getInfoRepository(championInfoDTOList.size());
        List<Perk> perkList = new ArrayList<>();
        if(championInfoDTOList.size()==1){ //1명 일 때
            ChampionInfoDTO championInfoDTO = championInfoDTOList.get(0);
            SoloInfoEntity soloInfoEntity = soloInfoRepository.findByChampionIdAndPosition(championInfoDTO.getChampionId(),championInfoDTO.getPosition()).orElse(null);
            if(soloInfoEntity ==null){
                log.info(" soloInfoEntity null 오류!");
                return null;
            }
            findTopK(soloInfoEntity.getPerkList(),2).forEach(perk ->{
                perkList.add( (Perk)perk);
            });
        }
        else { //2명이상일 때
            Map<Long, String> champPositionMap = new HashMap<Long, String>();
            TreeSet<Long> championIdSet = new TreeSet<>();
            setChampAndPositionInfo(championInfoDTOList,champPositionMap,championIdSet);
            ICombinationInfoEntity infoEntity =null;
            try {
                infoEntity = repository.findByChampionIdAndPosition(objectMapper.writeValueAsString(championIdSet),objectMapper.writeValueAsString(champPositionMap)).orElse(null);
            } catch (JsonProcessingException e) {
                log.info("objectMapper Json Parsing 오류!");
                return null;
            }
            if(infoEntity ==null){
                log.info(" infoEntity null 오류!");
                return null;
            }
            findTopK(infoEntity.getPerkList(),2).forEach(perk ->{
                perkList.add( (Perk) perk);
            });
        }
        return perkList;
    }
    public List<? extends Sub> findTopK(List<? extends  Sub> input , int k){
        PriorityQueue<Sub> maxHeap = new PriorityQueue<>();
        for (Sub element : input) {
            maxHeap.add(element);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        List<Sub> topList = new ArrayList<>(maxHeap);
        Collections.reverse(topList);
        return topList;
    }
    public void setChampAndPositionInfo(List<ChampionInfoDTO> championInfoDTOList, Map<Long, String> champPositionMap,Set<Long> championIdSet){
        for (ChampionInfoDTO championInfoDTO : championInfoDTOList) {
            champPositionMap.put(championInfoDTO.getChampionId(), championInfoDTO.getPosition());
            championIdSet.add(championInfoDTO.getChampionId());
        }
    }
}
