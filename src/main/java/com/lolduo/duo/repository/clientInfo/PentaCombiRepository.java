package com.lolduo.duo.repository.clientInfo;

import com.lolduo.duo.object.entity.clientInfo.PentaCombiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PentaCombiRepository extends JpaRepository<PentaCombiEntity,Long>, ICombiRepository {
    @Query(value = "select * from penta_combi where all_count >= 10 and json_contains(champion_id,?1) and json_contains(position,?2) and json_contains(json_extract(position, '$.*'), ?3) and not json_has_exclude_position(position, ?1, ?4) order by win_count / all_count DESC limit 30",nativeQuery = true)
    List<PentaCombiEntity> findAllByChampionIdAndPositionWinRateDesc(String championId, String position, String positionList, String excludePositionList);

    @Query(value = "select * from penta_combi where all_count >= 10 and json_contains(champion_id,?1) and json_contains(position,?2) and json_contains(json_extract(position, '$.*'), ?3) and not json_has_exclude_position(position, ?1, ?4) order by win_count / all_count ASC limit 30",nativeQuery = true)
    List<PentaCombiEntity> findAllByChampionIdAndPositionWinRateAsc(String championId, String position, String positionList, String excludePositionList);

    @Query(value = "select * from penta_combi where all_count >= 10 and json_contains(champion_id,?1) and json_contains(position,?2) and json_contains(json_extract(position, '$.*'), ?3) and not json_has_exclude_position(position, ?1, ?4) order by all_count DESC limit 30",nativeQuery = true)
    List<PentaCombiEntity> findAllByChampionIdAndPositionGameCountDesc(String championId, String position, String positionList, String excludePositionList);

    @Query(value = "select * from penta_combi where all_count >= 10 and json_contains(champion_id,?1) and json_contains(position,?2) and json_contains(json_extract(position, '$.*'), ?3) and not json_has_exclude_position(position, ?1, ?4) order by all_count ASC limit 30",nativeQuery = true)
    List<PentaCombiEntity> findAllByChampionIdAndPositionGameCountAsc(String championId, String position, String positionList, String excludePositionList);

    @Query(value = "select * from penta_combi where json_contains(champion_id,?1) and json_contains(position,?2) limit 1",nativeQuery = true)
    Optional<PentaCombiEntity> findByChampionIdAndPosition(String championId, String position);
}