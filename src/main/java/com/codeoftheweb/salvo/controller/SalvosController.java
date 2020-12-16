package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.repository.SalvoRepository;
import com.codeoftheweb.salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api")
public class SalvosController {
    @Autowired
    GamePlayerRepository gamePlayerRepository;
    @Autowired
    SalvoRepository salvoRepository;
    @Autowired
    PlayerRepository playerRepository;

    @RequestMapping(value="/games/players/{gpID}/salvoes", method= RequestMethod.POST)
    public ResponseEntity<Map> addSalvo(@PathVariable long gpID, @RequestBody Salvo salvo, Authentication authentication) {
        GamePlayer gamePlayer = gamePlayerRepository.findById(gpID).orElse(null);
        GamePlayer opponent = Util.getOpponent(gamePlayer);


        if (Util.isGuest(authentication)) {
            return new ResponseEntity<>(Util.makeMap("error","Not Logged in"), HttpStatus.UNAUTHORIZED);
        }
        if (gamePlayer == null) {
            return new ResponseEntity<>(Util.makeMap("error","Player doesnt exist"), HttpStatus.UNAUTHORIZED);
        }
        String playerEmail = authentication.getName();
        if (gamePlayer.getPlayer().getId() != playerRepository.findByEmail(playerEmail).getId()) {
            return new ResponseEntity<>(Util.makeMap("error","This is not your game"), HttpStatus.UNAUTHORIZED);
        }
        if (gamePlayer.getSalvoes().size() - opponent.getSalvoes().size() >= 1){
            return new ResponseEntity<>(Util.makeMap("error","Your opponent hasnt played yet"), HttpStatus.FORBIDDEN);
        }
        long turn = gamePlayer.getSalvoes().size() + 1;
        salvo.setGamePlayer(gamePlayer);
        salvo.setTurn(turn);
        salvoRepository.save(salvo);

        return new ResponseEntity<>(Util.makeMap("OK", "Salvo Created"), HttpStatus.CREATED);

    }
}
