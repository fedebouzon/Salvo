package com.codeoftheweb.salvo.util;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.model.Ship;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Util {
    public static Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }

    public static boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    public static GamePlayer getOpponent(GamePlayer gamePlayer) {
        GamePlayer opponent = new GamePlayer();
        for (GamePlayer gp : gamePlayer.getGame().getGamePlayers()) {
            if (gp.getId() != gamePlayer.getId()) {
                opponent = gp;
            }
        }
        return opponent;
    }
    /*public static Map<String, Integer> shipTypes = Stream.of(
            new Object[][]{
                    {"carrier", 5},
                    {"battleship", 4},
                    {"submarine", 3},
                    {"destroyer", 3},
                    {"patrolBoat", 2}
            }).collect(toMap(data -> (String)data[0], data -> (Integer)data[1]));

     */
    public static String gameState(GamePlayer gamePlayer){
       if(gamePlayer.getShips().isEmpty()){
            return "PLACESHIPS";
        }
        if(gamePlayer.getGame().getGamePlayers().size() == 1){
            return "WAITINGFOROPP";
        }

        return "PLAY";
    }

    public static List<String> getLocationByType(String type, GamePlayer gamePlayer) {
        return gamePlayer.getShips().size() == 0 ? new ArrayList<>() : gamePlayer.getShips().stream().filter(ship -> ship.getType().equals(type)).findFirst().get().getLocations();
    }
}