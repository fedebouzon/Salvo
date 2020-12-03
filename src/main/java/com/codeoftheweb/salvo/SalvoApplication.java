package com.codeoftheweb.salvo;
import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SalvoApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository,
									  GameRepository gameRepository,
									  GamePlayerRepository gamePlayerRepository,
									  ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository){
		return args -> {

			//region save a couple of players
			Player player1 = new Player("DavidCosio","david@gmail.com", passwordEncoder().encode("qweasd"));
			Player player2 = new Player("Cohete","rocket1@gmail.com",passwordEncoder().encode("qweasd"));
			Player player3 = new Player("Cohete2","rocket2@gmail.com", passwordEncoder().encode("qweasd"));
			Player player4 = new Player("Cohete3","rocket3@gmail.com", passwordEncoder().encode("qweasd"));
			Player player5 = new Player("Cohete4","rocket4@gmail.com", passwordEncoder().encode("qweasd"));
			Player player6 = new Player("Cohete5","rocket5@gmail.com", passwordEncoder().encode("qweasd"));
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

			//region save a couple of scores
			Score score1 = new Score(game1,player1,Date.from(Instant.now()),1.0D);
			Score score2 = new Score(game1,player2,Date.from(Instant.now()),0.0D);
			Score score3 = new Score(game2,player1,Date.from(Instant.now()),1.0D);
			Score score4 = new Score(game2,player2,Date.from(Instant.now()),0.0D);
			scoreRepository.save(score1);
			scoreRepository.save(score2);
			scoreRepository.save(score3);
			scoreRepository.save(score4);
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
			Salvo salvo3 = new Salvo("2", Arrays.asList("D1", "D2", "D3","D4","D5"));
			salvoRepository.save(salvo1);
			salvoRepository.save(salvo2);
			salvoRepository.save(salvo3);
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
			gamePlayer1.addSalvo(salvo3);
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

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputEmail-> {
			Player player = playerRepository.findByEmail(inputEmail);
			if (player != null) {
				return new User(player.getEmail(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputEmail);
			}
		});
	}
}
@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/web/**").permitAll()
				.antMatchers("/api/**").permitAll()
				//.antMatchers("/api/game_view/*").hasAuthority("USER")
				.antMatchers("/h2-console/**").permitAll()
				//.antMatchers("/api/games").permitAll()
				;

		http.formLogin()
				.usernameParameter("name")
				.passwordParameter("pwd")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}