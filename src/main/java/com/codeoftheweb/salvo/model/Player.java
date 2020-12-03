package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Player {

    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private String email;
    private String password;
    //nombre de la variable a buscar
    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    @OrderBy
    //nombre del set de la base de datos
    private Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    @OrderBy
    //nombre del set de base de datos
    private Set<Score> scores;
    //endregion

    //region Constructores
    public Player() {
        scores = new HashSet<>();
    }

    public Player(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        scores = new HashSet<>();
    }

    public Player(String email, String password) {
        this.email = email;
        this.password = password;
        scores = new HashSet<>();
    }
    //endregion

    //region Scores
    public double getWonScore(){
        return this.getScores().stream().filter(score -> score.getScore() == 1.0D).count();
    }
    public double getLostScore(){
        return this.getScores().stream().filter(score -> score.getScore() == 0.0D).count();
    }
    public double getTiedScore(){
        return this.getScores().stream().filter(score -> score.getScore() == 0.5D).count();
    }
    public double getTotalScore(){
        return getWonScore() * 1.0D + getTiedScore() * 0.5D;
    }

    public Score getGameScore(Game game){
        return scores.stream().filter(score -> score.getGame().getId() == game.getId()).findFirst().orElse(null);
    }

    //endregion

    //region getterSetters

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void addScore(Score newScore) {
        this.scores.add(newScore);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }
    //endregion
}

