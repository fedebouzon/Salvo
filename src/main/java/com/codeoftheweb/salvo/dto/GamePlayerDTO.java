package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.GamePlayer;


import java.util.LinkedHashMap;
import java.util.Map;

public class GamePlayerDTO {
    private GamePlayer gamePlayer;

    //region constructor
    public GamePlayerDTO() {
    }

    public GamePlayerDTO(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
//endregion

    public Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer){
        PlayerDTO dtoPlayer = new PlayerDTO();
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("gamePlayerid", gamePlayer.getId());
        dto.put("joinDate", gamePlayer.getJoinDate());
        //codigo editado para listar datos de player
        dto.put("player", dtoPlayer.makePlayerDTO(gamePlayer.getPlayer()));

        return dto;
    }

    //region gettersetter

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    //endregion
}
