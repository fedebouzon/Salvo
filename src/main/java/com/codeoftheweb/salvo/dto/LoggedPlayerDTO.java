package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class LoggedPlayerDTO {
    private Player player;

    //region constructor
    public LoggedPlayerDTO() {
    }

    public LoggedPlayerDTO(Player player) {
        this.player = player;
    }
//endregion

    public Map<String, Object> makeLoggedPlayerDTO(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("name", player.getName());
        dto.put("email", player.getEmail());

        return dto;
    }
}
