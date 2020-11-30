package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Ship;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameDTO {
    private Game game;

    //region constructor
    public GameDTO() {
    }

    public GameDTO(Game game) {
        this.game = game;
    }
//endregion

    public Map<String, Object> makeGameDTO(Game game){
        GamePlayerDTO dtoGamePlayer = new GamePlayerDTO();
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());
        List<GamePlayer> gamePlayers = game.getGamePlayers().stream().collect(Collectors.toList());
        dto.put("gamePlayers", gamePlayers.stream().map(gamePlayer -> dtoGamePlayer.makeGamePlayerDTO(gamePlayer)));
        return dto;
    }

    //region gettersetter

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    //endregion
}
