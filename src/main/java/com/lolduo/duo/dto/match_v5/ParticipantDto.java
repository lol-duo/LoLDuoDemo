package com.lolduo.duo.dto.match_v5;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParticipantDto {
    private Long assists;
    private Long baronKills;
    private Long bountyLevel;
    private Long champExperience;
    private Long champLevel;
    private Long championId;
    private String championName;
    private Long championTransfrom;
    private Long consumablesPurchased;
    private Long damageDealtToBuildings;
    private Long damageDealtToObjectives;
    private Long damageDealtToTurrets;
    private Long damageSelfMitigated;
    private Long deaths;
    private Long detectorWardsPlaced;
    private Long doubleKills;
    private Long dragonKills;
    private Boolean firstBloodAssist;
    private Boolean firstBloodKill;
    private Boolean firstTowerAssist;
    private Boolean firstTowerKill;
    private Boolean gameEndedInEarlySurrender;
    private Boolean gameEndedInSurrender;
    private Long goldEarned;
    private Long goldSpent;
    private String individualPosition;
    private Long inhibitorKills;
    private Long inhibitorTakedowns;
    private Long inhibitorsLost;
    private Long item0;
    private Long item1;
    private Long item2;
    private Long item3;
    private Long item4;
    private Long item5;
    private Long item6;
    private Long itemsPurchased;
    private Long killingSprees;
    private Long kills;
    private String lane;
    private Long largestCriticalStrike;
    private Long largestKillingSpree;
    private Long largestMultiKill;
    private Long longestTimeSpentLiving;
    private Long magicDamageDealt;
    private Long magicDamageDealtToChampions;
    private Long magicDamageTaken;
    private Long neutralMinionsKilled;
    private Long nexusKills;
    private Long nexusTakedowns;
    private Long nexusLost;
    private Long objectivesStolen;
    private Long objectivesStolenAssists;
    private Long participantId;
    private Long pentaKills;
    private PerksDto perks;
    private Long physicalDamageDealt;
    private Long physicalDamageDealtToChampions;
    private Long physicalDamageTaken;
    private Long profileIcon;
    private String puuid;
    private Long quadraKills;
    private String riotIdName;
    private String riotIdTagline;
    private String role;
    private Long sightWardsBoughtInGame;
    private Long spell1Casts;
    private Long spell2Casts;
    private Long spell3Casts;
    private Long spell4Casts;
    private Long summoner1Casts;
    private Long summoner1Id;
    private Long summoner2Casts;
    private Long summoner2Id;
    private String summonerId;
    private Long summonerLevel;
    private String summonerName;
    private Boolean teamEarlySurrendered;
    private Long teamId;
    private String teamPosition;
    private Long timeCCingOthers;
    private Long timePlayed;
    private Long totalDamageDealt;
    private Long totalDamageDealtToChampions;
    private Long totalDamageShieldedOnTeammates;
    private Long totalDamageTaken;
    private Long totalHeal;
    private Long totalHealsOnTeammates;
    private Long totalMinionsKilled;
    private Long totalTimeCCDealt;
    private Long totalTimeSpentDead;
    private Long totalUnitsHealed;
    private Long tripleKills;
    private Long trueDamageDealt;
    private Long trueDamageDealtToChampions;
    private Long trueDamageTaken;
    private Long turretKills;
    private Long turretTakedowns;
    private Long turretsLost;
    private Long unrealKills;
    private Long visionScore;
    private Long visionWardsBoughtInGame;
    private Long wardsKilled;
    private Long wardsPlaced;
    private Boolean win;

    public ParticipantDto(Long assists, Long baronKills, Long bountyLevel, Long champExperience, Long champLevel, Long championId, String championName, Long championTransfrom, Long consumablesPurchased, Long damageDealtToBuildings, Long damageDealtToObjectives, Long damageDealtToTurrets, Long damageSelfMitigated, Long deaths, Long detectorWardsPlaced, Long doubleKills, Long dragonKills, Boolean firstBloodAssist, Boolean firstBloodKill, Boolean firstTowerAssist, Boolean firstTowerKill, Boolean gameEndedInEarlySurrender, Boolean gameEndedInSurrender, Long goldEarned, Long goldSpent, String individualPosition, Long inhibitorKills, Long inhibitorTakedowns, Long inhibitorsLost, Long item0, Long item1, Long item2, Long item3, Long item4, Long item5, Long item6, Long itemsPurchased, Long killingSprees, Long kills, String lane, Long largestCriticalStrike, Long largestKillingSpree, Long largestMultiKill, Long longestTimeSpentLiving, Long magicDamageDealt, Long magicDamageDealtToChampions, Long magicDamageTaken, Long neutralMinionsKilled, Long nexusKills, Long nexusTakedowns, Long nexusLost, Long objectivesStolen, Long objectivesStolenAssists, Long participantId, Long pentaKills, PerksDto perks, Long physicalDamageDealt, Long physicalDamageDealtToChampions, Long physicalDamageTaken, Long profileIcon, String puuid, Long quadraKills, String riotIdName, String riotIdTagline, String role, Long sightWardsBoughtInGame, Long spell1Casts, Long spell2Casts, Long spell3Casts, Long spell4Casts, Long summoner1Casts, Long summoner1Id, Long summoner2Casts, Long summoner2Id, String summonerId, Long summonerLevel, String summonerName, Boolean teamEarlySurrendered, Long teamId, String teamPosition, Long timeCCingOthers, Long timePlayed, Long totalDamageDealt, Long totalDamageDealtToChampions, Long totalDamageShieldedOnTeammates, Long totalDamageTaken, Long totalHeal, Long totalHealsOnTeammates, Long totalMinionsKilled, Long totalTimeCCDealt, Long totalTimeSpentDead, Long totalUnitsHealed, Long tripleKills, Long trueDamageDealt, Long trueDamageDealtToChampions, Long trueDamageTaken, Long turretKills, Long turretTakedowns, Long turretsLost, Long unrealKills, Long visionScore, Long visionWardsBoughtInGame, Long wardsKilled, Long wardsPlaced, Boolean win) {
        this.assists = assists;
        this.baronKills = baronKills;
        this.bountyLevel = bountyLevel;
        this.champExperience = champExperience;
        this.champLevel = champLevel;
        this.championId = championId;
        this.championName = championName;
        this.championTransfrom = championTransfrom;
        this.consumablesPurchased = consumablesPurchased;
        this.damageDealtToBuildings = damageDealtToBuildings;
        this.damageDealtToObjectives = damageDealtToObjectives;
        this.damageDealtToTurrets = damageDealtToTurrets;
        this.damageSelfMitigated = damageSelfMitigated;
        this.deaths = deaths;
        this.detectorWardsPlaced = detectorWardsPlaced;
        this.doubleKills = doubleKills;
        this.dragonKills = dragonKills;
        this.firstBloodAssist = firstBloodAssist;
        this.firstBloodKill = firstBloodKill;
        this.firstTowerAssist = firstTowerAssist;
        this.firstTowerKill = firstTowerKill;
        this.gameEndedInEarlySurrender = gameEndedInEarlySurrender;
        this.gameEndedInSurrender = gameEndedInSurrender;
        this.goldEarned = goldEarned;
        this.goldSpent = goldSpent;
        this.individualPosition = individualPosition;
        this.inhibitorKills = inhibitorKills;
        this.inhibitorTakedowns = inhibitorTakedowns;
        this.inhibitorsLost = inhibitorsLost;
        this.item0 = item0;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
        this.item6 = item6;
        this.itemsPurchased = itemsPurchased;
        this.killingSprees = killingSprees;
        this.kills = kills;
        this.lane = lane;
        this.largestCriticalStrike = largestCriticalStrike;
        this.largestKillingSpree = largestKillingSpree;
        this.largestMultiKill = largestMultiKill;
        this.longestTimeSpentLiving = longestTimeSpentLiving;
        this.magicDamageDealt = magicDamageDealt;
        this.magicDamageDealtToChampions = magicDamageDealtToChampions;
        this.magicDamageTaken = magicDamageTaken;
        this.neutralMinionsKilled = neutralMinionsKilled;
        this.nexusKills = nexusKills;
        this.nexusTakedowns = nexusTakedowns;
        this.nexusLost = nexusLost;
        this.objectivesStolen = objectivesStolen;
        this.objectivesStolenAssists = objectivesStolenAssists;
        this.participantId = participantId;
        this.pentaKills = pentaKills;
        this.perks = perks;
        this.physicalDamageDealt = physicalDamageDealt;
        this.physicalDamageDealtToChampions = physicalDamageDealtToChampions;
        this.physicalDamageTaken = physicalDamageTaken;
        this.profileIcon = profileIcon;
        this.puuid = puuid;
        this.quadraKills = quadraKills;
        this.riotIdName = riotIdName;
        this.riotIdTagline = riotIdTagline;
        this.role = role;
        this.sightWardsBoughtInGame = sightWardsBoughtInGame;
        this.spell1Casts = spell1Casts;
        this.spell2Casts = spell2Casts;
        this.spell3Casts = spell3Casts;
        this.spell4Casts = spell4Casts;
        this.summoner1Casts = summoner1Casts;
        this.summoner1Id = summoner1Id;
        this.summoner2Casts = summoner2Casts;
        this.summoner2Id = summoner2Id;
        this.summonerId = summonerId;
        this.summonerLevel = summonerLevel;
        this.summonerName = summonerName;
        this.teamEarlySurrendered = teamEarlySurrendered;
        this.teamId = teamId;
        this.teamPosition = teamPosition;
        this.timeCCingOthers = timeCCingOthers;
        this.timePlayed = timePlayed;
        this.totalDamageDealt = totalDamageDealt;
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
        this.totalDamageShieldedOnTeammates = totalDamageShieldedOnTeammates;
        this.totalDamageTaken = totalDamageTaken;
        this.totalHeal = totalHeal;
        this.totalHealsOnTeammates = totalHealsOnTeammates;
        this.totalMinionsKilled = totalMinionsKilled;
        this.totalTimeCCDealt = totalTimeCCDealt;
        this.totalTimeSpentDead = totalTimeSpentDead;
        this.totalUnitsHealed = totalUnitsHealed;
        this.tripleKills = tripleKills;
        this.trueDamageDealt = trueDamageDealt;
        this.trueDamageDealtToChampions = trueDamageDealtToChampions;
        this.trueDamageTaken = trueDamageTaken;
        this.turretKills = turretKills;
        this.turretTakedowns = turretTakedowns;
        this.turretsLost = turretsLost;
        this.unrealKills = unrealKills;
        this.visionScore = visionScore;
        this.visionWardsBoughtInGame = visionWardsBoughtInGame;
        this.wardsKilled = wardsKilled;
        this.wardsPlaced = wardsPlaced;
        this.win = win;
    }
}
