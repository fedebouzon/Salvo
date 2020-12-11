package com.codeoftheweb.salvo.util;

import com.codeoftheweb.salvo.model.GamePlayer;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Util {
    public static Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, value);
        return map;
    }

    public static boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
    public static GamePlayer getOpponent(GamePlayer gamePlayer){
        GamePlayer opponent= new GamePlayer();
        for( GamePlayer g :gamePlayer.getGame().getGamePlayers()){
            if(g.getId()!=gamePlayer.getId()){
                opponent=g;
            }
        }
        return opponent;
    }
}
