package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.GamePlayer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Game_ViewDTO {
    private GamePlayer gamePlayer;

    //region constructor
    public Game_ViewDTO() {
    }

    public Game_ViewDTO(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
//endregion

    public Map<String, Object> makeGame_ViewDTO(GamePlayer gamePlayer){
        GameDTO dtoGame = new GameDTO();
        Map<String, Object> dto = dtoGame.makeGameDTO(gamePlayer.getGame());
        ShipDTO dtoShip = new ShipDTO();
        SalvoDTO dtoSalvo = new SalvoDTO();
        dto.put("ships", gamePlayer.getShips().stream().map(ship -> dtoShip.makeShipDTO(ship)).collect(Collectors.toList()));
        dto.put("salvoes", gamePlayer.getGame().getGamePlayers().stream()
                .flatMap(gamePlayer1 -> gamePlayer1.getSalvoes()
                        .stream()
                        .map(salvo -> dtoSalvo.makeSalvoDTO(salvo)))
                .collect(Collectors.toList()));
        //hardcoding hits
        Map<String, Object> hits = new LinkedHashMap<>();

        hits.put("self", new ArrayList<>());
        hits.put("opponent", new ArrayList<>());
        dto.put("hits", hits);
        dto.put("gameState", "PLAY");
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
