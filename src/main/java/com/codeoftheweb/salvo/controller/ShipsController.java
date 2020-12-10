package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Ship;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.repository.ShipRepository;
import com.codeoftheweb.salvo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class ShipsController {
    @Autowired
    GamePlayerRepository gamePlayerRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;

    @RequestMapping(value="/games/players/{gpID}/ships", method= RequestMethod.POST)
    public ResponseEntity<Map> addShip(@PathVariable long gpID, @RequestBody List<Ship> shipList, Authentication authentication) {
        GamePlayer gamePlayer = gamePlayerRepository.findById(gpID).orElse(null);
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
        if (gamePlayer.getShips().size() + shipList.size() > 5){
            int shipSize = shipList.size();
            return new ResponseEntity<>(Util.makeMap("error",shipSize + " ships have been placed"), HttpStatus.FORBIDDEN);
        }

        List<Ship> newShipList = shipList.stream().map(ship -> {ship.setGamePlayer(gamePlayer);return ship;}).collect(Collectors.toList());
        shipRepository.saveAll(newShipList);

        return new ResponseEntity<>(Util.makeMap("OK", "Ships Created"), HttpStatus.CREATED);

    }
}
