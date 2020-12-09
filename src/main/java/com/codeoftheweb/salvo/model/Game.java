package com.codeoftheweb.salvo.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

@Entity
public class Game {
    //region attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    //nombre de la variable a buscar
    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    @OrderBy
    //nombre del set de base de datos
    private Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    @OrderBy
    //nombre del set de base de datos
    private Set<Score> scores;

    private Date created;
    //endregion

    //region constructores
    public Game() {
        scores = new HashSet<>();
        gamePlayers = new HashSet<>();
        created = new Date();
    }

    public Game(Date created) {
        scores = new HashSet<>();
        this.created = created;
        gamePlayers = new HashSet<>();
    }


//endregion

    //region settergetter


    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setDate(Date date) {
        this.created = date;
    }

    public Date getCreationDate() {
        return created;
    }

    public Long getId() {
        return id;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

//endregion

}
