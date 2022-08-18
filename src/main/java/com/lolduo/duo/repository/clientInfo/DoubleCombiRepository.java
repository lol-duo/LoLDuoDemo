package com.lolduo.duo.repository.clientInfo;

import com.lolduo.duo.object.entity.clientInfo.DoubleCombiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoubleCombiRepository extends JpaRepository<DoubleCombiEntity,Long>, ICombiRepository {

    @Query(value = "select * from double_combi where all_count >= 10 and json_contains(champion_id,?1) and json_contains(position,?2) and json_contains(json_extract(position, '$.*'), ?3) and not json_has_exclude_position(position, ?1, ?4) order by win_count / all_count DESC limit 30",nativeQuery = true)
    List<DoubleCombiEntity> findAllByChampionIdAndPositionWinRateDesc(String championId, String position, String positionList, String excludePositionList);

    @Query(value = "select * from double_combi where all_count >= 10 and json_contains(champion_id,?1) and json_contains(position,?2) and json_contains(json_extract(position, '$.*'), ?3) and not json_has_exclude_position(position, ?1, ?4) order by win_count / all_count ASC limit 30",nativeQuery = true)
    List<DoubleCombiEntity> findAllByChampionIdAndPositionWinRateAsc(String championId, String position, String positionList, String excludePositionList);

    @Query(value = "select * from double_combi where all_count >= 10 and json_contains(champion_id,?1) and json_contains(position,?2) and json_contains(json_extract(position, '$.*'), ?3) and not json_has_exclude_position(position, ?1, ?4) order by all_count DESC limit 30",nativeQuery = true)
    List<DoubleCombiEntity> findAllByChampionIdAndPositionGameCountDesc(String championId, String position, String positionList, String excludePositionList);

    @Query(value = "select * from double_combi where all_count >= 10 and json_contains(champion_id,?1) and json_contains(position,?2) and json_contains(json_extract(position, '$.*'), ?3) and not json_has_exclude_position(position, ?1, ?4) order by all_count ASC limit 30",nativeQuery = true)
    List<DoubleCombiEntity> findAllByChampionIdAndPositionGameCountAsc(String championId, String position, String positionList, String excludePositionList);

    @Query(value = "select * from double_combi where json_contains(champion_id,?1) and json_contains(position,?2) limit 1",nativeQuery = true)
    Optional<DoubleCombiEntity> findByChampionIdAndPosition(String championId, String position);
}