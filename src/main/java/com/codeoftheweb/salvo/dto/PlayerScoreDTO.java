package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlayerScoreDTO {
    private Player player;

    public PlayerScoreDTO() {
    }

    public PlayerScoreDTO(Player player) {
        this.player = player;
    }

    public Map<String, Object> makePlayerScoreDTO(Player player){
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> score = new LinkedHashMap<>();
        dto.put("email", player.getEmail());
        dto.put("score", score);
            score.put("total", player.getTotalScore());
            score.put("won", player.getWonScore());
            score.put("lost", player.getLostScore());
            score.put("tied", player.getTiedScore());
        return dto;
    }

}
