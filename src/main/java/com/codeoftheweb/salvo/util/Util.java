package com.codeoftheweb.salvo.util;

import com.codeoftheweb.salvo.dto.HitsDTO;
import com.codeoftheweb.salvo.model.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;


import java.util.*;


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

    public static String gameState(GamePlayer gamePlayer) {



        if (gamePlayer.getShips().isEmpty()) {
            return "PLACESHIPS";
        }
        if (gamePlayer.getGame().getGamePlayers().size() == 1 || Util.getOpponent(gamePlayer).getShips().size() == 0) {
            return "WAITINGFOROPP";
        }
        HitsDTO oppHit = new HitsDTO();
        HitsDTO myHit = new HitsDTO();

        if(Util.getOpponent(gamePlayer).getShips().size() == 5) {
            myHit.makeHitsDTO(gamePlayer);
            oppHit.makeHitsDTO(Util.getOpponent(gamePlayer));
            if (myHit.isAllSunk() && oppHit.isAllSunk()) {
                if (gamePlayer.getSalvoes().size() == Util.getOpponent(gamePlayer).getSalvoes().size()) {
                    return "TIE";
                }
            } else if (myHit.isAllSunk()) {
                if (gamePlayer.getSalvoes().size() == Util.getOpponent(gamePlayer).getSalvoes().size()) {
                    return "LOST";
                }
            } else if (oppHit.isAllSunk()) {
                if (gamePlayer.getSalvoes().size() == Util.getOpponent(gamePlayer).getSalvoes().size()) {
                    return "WON";
                }
            }
            if (gamePlayer.getSalvoes().size() > Util.getOpponent(gamePlayer).getSalvoes().size()) {
                return "WAIT";
            }
        }
        return "PLAY";
    }

    public static List<String> getLocationByType(String type, GamePlayer gamePlayer) {
        List<String> list = new ArrayList<>();
        if (!gamePlayer.getShips().isEmpty()){
        return gamePlayer.getShips().stream()
                .filter(ship -> ship.getType().equals(type)).findFirst().get().getLocations();
        }
        return list;
    }
}
