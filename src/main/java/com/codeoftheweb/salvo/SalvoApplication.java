package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.Instant;
import java.util.*;


@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository, SalvoRepository salvoRepository){
		return args -> {

			//region save a couple of players
			Player player1 = new Player("DavidCosio","david@gmail.com");
			Player player2 = new Player("Cohete","rocket1@gmail.com");
			Player player3 = new Player("Cohete2","rocket2@gmail.com");
			Player player4 = new Player("Cohete3","rocket3@gmail.com");
			Player player5 = new Player("Cohete4","rocket4@gmail.com");
			Player player6 = new Player("Cohete5","rocket5@gmail.com");
			playerRepository.saveAll(List.of(player1,player2,player3,player4,player5,player6));
			
			//endregion

			//region save a couple of games
			Game game1 = new Game();
			game1.setDate(Date.from(Instant.now()));
			Game game2 = new Game();
			game2.setDate(Date.from(game1.getCreationDate().toInstant().plusSeconds(3600)));
			Game game3 = new Game();
			game3.setDate(Date.from(game2.getCreationDate().toInstant().plusSeconds(3600)));

			gameRepository.save(game1);
			gameRepository.save(game2);
			gameRepository.save(game3);
			//endregion

			//region save a couple of ships

			/*
			Number Type Length
			1 Carrier 5
			1 Battleship 4
			1 Submarine 3
			1 Destroyer 3
			1 Patrol Boat 2
			*/

			Ship ship1 = new Ship("Carrier", Arrays.asList("A1", "A2", "A3","A4","A5"));
			//probando si hay diferencia con Arrays as List
			Ship ship2 = new Ship("Battleship", List.of("B1","B2","B3","B4"));
			Ship ship3 = new Ship("Destructor", List.of("D1", "D2", "D3"));
			Ship ship4 = new Ship("Submarine", List.of("E1","E2","E3"));
			Ship ship5 = new Ship("Patrol_Boat", List.of("F1","F2"));
			//Ship ship6 = new Ship("patrol",Arrays.as)

			shipRepository.save(ship1);
			shipRepository.save(ship2);
			shipRepository.save(ship3);
			shipRepository.save(ship4);
			shipRepository.save(ship5);

			//endregion

			//region save a couple of salvoes
			Salvo salvo1 = new Salvo("1", Arrays.asList("A1", "A2", "A3","A4","A5"));
			Salvo salvo2 = new Salvo("1", Arrays.asList("B1", "B2", "B3","B4","B5"));
			salvoRepository.save(salvo1);
			salvoRepository.save(salvo2);
			//endregion

			//region save a couple of gameplayers
			GamePlayer gamePlayer1 = new GamePlayer(player1, game1);
			GamePlayer gamePlayer2 = new GamePlayer(player2, game1);
			GamePlayer gamePlayer3 = new GamePlayer(player3, game2);
			GamePlayer gamePlayer4 = new GamePlayer(player4, game2);
			GamePlayer gamePlayer5 = new GamePlayer(player5, game3);
			GamePlayer gamePlayer6 = new GamePlayer(player6, game3);
			gamePlayer1.addShip(ship1);
			gamePlayer1.addShip(ship2);
			gamePlayer1.addShip(ship3);
			gamePlayer2.addShip(ship4);
			gamePlayer2.addShip(ship5);
			gamePlayer1.addSalvo(salvo1);
			gamePlayer2.addSalvo(salvo2);
			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);
			gamePlayerRepository.save(gamePlayer5);
			gamePlayerRepository.save(gamePlayer6);
			//endregion


		};
	}

}
