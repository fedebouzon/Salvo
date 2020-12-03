package com.codeoftheweb.salvo.controller;
import com.codeoftheweb.salvo.dto.*;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.*;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
    GameRepository gameRepository;
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
    PasswordEncoder passwordEncoder;

    @RequestMapping("/game_view/{ID}")
    public Map<String, Object> getGamePlayerView(@PathVariable long ID) {
        Game_ViewDTO dtoGame_View = new Game_ViewDTO();
        return dtoGame_View.makeGame_ViewDTO(gamePlayerRepository.getOne(ID));
    }

    @RequestMapping("/leaderBoard")
    public List<Map<String, Object>> getLeaderBoard() {
        PlayerScoreDTO dtoPlayerScore = new PlayerScoreDTO();
        return playerRepository.findAll()
                .stream().map(player -> dtoPlayerScore.makePlayerScoreDTO(player))
                .collect(Collectors.toList());
    }

    @RequestMapping("/players")
    public List<Map<String, Object>> getPlayerAll() {
        PlayerDTO dtoPlayer = new PlayerDTO();
        return playerRepository.findAll()
                .stream().map(player -> dtoPlayer.makePlayerDTO(player))
                .collect(Collectors.toList());
    }
    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam String email, @RequestParam String password) {

        if (email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/ships")
    public List<Map<String, Object>> getShipAll() {
        ShipDTO dtoShip = new ShipDTO();
        return shipRepository.findAll()
                .stream().map(ship -> dtoShip.makeShipDTO(ship))
                .collect(Collectors.toList());
    }

    @RequestMapping("/games")
    public Map<String, Object> getGameAll(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();
        if(isGuest(authentication)){
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
    private boolean isGuest(Authentication authentication){
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
}