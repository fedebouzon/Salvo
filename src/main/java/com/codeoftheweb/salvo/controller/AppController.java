package com.codeoftheweb.salvo.controller;
import com.codeoftheweb.salvo.dto.*;
import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.*;
import com.codeoftheweb.salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    GamePlayerRepository gamePlayerRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    SalvoRepository salvoRepository;
    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    GameRepository gameRepository;


    @RequestMapping(path = "/game_view/{ID}", method = RequestMethod.GET )
    public ResponseEntity<Map<String, Object>> getGamePlayerView(@PathVariable long ID, Authentication authentication) {
        Long playerLogged = playerRepository.findByEmail(authentication.getName()).getId();
        Long playerCheck = gamePlayerRepository.getOne(ID).getPlayer().getId();
        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("error", "Not Logged in"), HttpStatus.UNAUTHORIZED);
        }
        if (playerLogged != playerCheck){
            return new ResponseEntity<>(Util.makeMap("error", "This is not your game"), HttpStatus.FORBIDDEN);
        }

        Game_ViewDTO dtoGame_View = new Game_ViewDTO();
        return new ResponseEntity<>(dtoGame_View.makeGame_ViewDTO(gamePlayerRepository.getOne(ID)), HttpStatus.ACCEPTED);
    }

    @RequestMapping("/leaderBoard")
    public List<Map<String, Object>> getLeaderBoard() {
        PlayerScoreDTO dtoPlayerScore = new PlayerScoreDTO();
        return playerRepository.findAll()
                .stream().map(player -> dtoPlayerScore.makePlayerScoreDTO(player))
                .collect(Collectors.toList());
    }


    @RequestMapping("/ships")
    public List<Map<String, Object>> getShipAll() {
        ShipDTO dtoShip = new ShipDTO();
        return shipRepository.findAll()
                .stream().map(ship -> dtoShip.makeShipDTO(ship))
                .collect(Collectors.toList());
    }


    @RequestMapping("/salvos")
    public List<Map<String, Object>> getSalvoAll() {
        SalvoDTO dtoSalvo = new SalvoDTO();
        return salvoRepository.findAll()
                .stream().map(salvo -> dtoSalvo.makeSalvoDTO(salvo))
                .collect(Collectors.toList());
    }

    @RequestMapping("/scores")
    public List<Map<String, Object>> getScoreAll() {
        ScoreDTO dtoScore = new ScoreDTO();
        return scoreRepository.findAll()
                .stream().map(score -> dtoScore.makeScoreDTO(score))
                .collect(Collectors.toList());
    }

    @RequestMapping("/gameplayers")
    public List<Map<String,Object>> getGamePlayersAll() {
        GamePlayerDTO dtoGamePlayer = new GamePlayerDTO();
        return gamePlayerRepository.findAll()
                .stream().map(gp -> dtoGamePlayer.makeGamePlayerDTO(gp))
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/game/{game_id}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> getJoinGame(@PathVariable long game_id,Authentication authentication) {
        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("error", "Not Logged in"), HttpStatus.UNAUTHORIZED);
        }
        Player player = playerRepository.findByEmail(authentication.getName());

        Game gameToJoin = gameRepository.getOne(game_id);
        if (gameToJoin == null) {
            return new ResponseEntity<>(Util.makeMap("error", "No such game"), HttpStatus.FORBIDDEN);
        }

        long gamePlayersCount = gameToJoin.getGamePlayers().size();

        Set<Long> gamePlayersCheck = gameToJoin.getGamePlayers().stream()
                .map(gamePlayer -> gamePlayer.getPlayer().getId()).collect(Collectors.toSet());

        if (gamePlayersCheck.contains(player.getId())){
            return new ResponseEntity<>(Util.makeMap("error","You are already in the game"), HttpStatus.FORBIDDEN);
        }
        if (gamePlayersCount == 1){
            GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player, gameToJoin));
            return new ResponseEntity<>(Util.makeMap("gpid",gamePlayer.getId()), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(Util.makeMap("error","Game is full"), HttpStatus.FORBIDDEN);
        }


    }
}