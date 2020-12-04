package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.dto.GameDTO;
import com.codeoftheweb.salvo.dto.LoggedPlayerDTO;
import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.GameRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GamesController {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    GamePlayerRepository gamePlayerRepository;
    @Autowired
    PlayerRepository playerRepository;

    @RequestMapping(path = "/games", method = RequestMethod.GET)
    public Map<String, Object> getGameAll(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();
        if(Util.isGuest(authentication)){
            dto.put("player", "Guest");
        }else {
            Player playerLogged =  playerRepository.findByEmail(authentication.getName());
            LoggedPlayerDTO loggedPlayerDTO = new LoggedPlayerDTO(playerLogged);
            dto.put("player", loggedPlayerDTO.makeLoggedPlayerDTO(playerLogged));
        }
        GameDTO dtoGame = new GameDTO();
        dto.put("games", gameRepository.findAll()
                .stream().map(game -> dtoGame.makeGameDTO(game))
                .collect(Collectors.toList()));
        return dto;
    }

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> createNewGame(Authentication authentication) {
        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("error","Not Logged in"), HttpStatus.UNAUTHORIZED);
        } else {
            Game game = new Game(Date.from(Instant.now()));
            gameRepository.save(game);
            String playerEmail = authentication.getName();
            GamePlayer gamePlayer = new GamePlayer(playerRepository.findByEmail(playerEmail), game);
            gamePlayerRepository.save(gamePlayer);
            return new ResponseEntity<>(Util.makeMap("gpid",gamePlayer.getId()), HttpStatus.CREATED);
        }
    }




}