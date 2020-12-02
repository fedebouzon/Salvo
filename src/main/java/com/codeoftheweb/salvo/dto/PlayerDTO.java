package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlayerDTO {
    private Player player;

    //region constructor
    public PlayerDTO() {
    }

    public PlayerDTO(Player player) {
        this.player = player;
    }
//endregion

    public Map<String, Object> makePlayerDTO(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("name", player.getName());
        dto.put("email", player.getEmail());

        return dto;
    }
}
