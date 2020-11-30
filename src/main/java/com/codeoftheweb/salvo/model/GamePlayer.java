package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GamePlayer {

    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    //nombre de la columna en base de datos
    @JoinColumn(name="player")
    //nombre de la variable a buscar
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    //nombre de la columna en base de datos
    @JoinColumn(name="game")
    //nombre de la variable a buscar
    private Game game;

    @OneToMany(fetch = FetchType.EAGER)
    //nombre de la columna en base de datos
    @JoinColumn(name="gamePlayerID")
    //nombre de la variable a buscar
    @OrderBy
    private Set<Ship> ships;

    @OneToMany(fetch = FetchType.EAGER)
    //nombre de la columna en base de datos
    @JoinColumn(name="gPID")
    //nombre de la variable a buscar
    @OrderBy
    private Set<Salvo> salvoes;

    private Date joinDate;

    //endregion

    //region Constructores
    public GamePlayer() {
        ships = new HashSet<>();
        salvoes = new HashSet<>();
    }

    public GamePlayer(Player player, Game game) {
        ships = new HashSet<>();
        salvoes = new HashSet<>();
        this.joinDate =  new Date();
        this.player = player;
        this.game = game;
    }
    //endregion

    //region GetterSetters
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public Set<Ship> getShip() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    public void addShip(Ship newShip) {
        this.ships.add(newShip);
        newShip.setGamePlayer(this);
    }

    public Set<Salvo> getSalvoes(){
        return salvoes;
    }

    public void setSalvos(Set<Salvo> salvos){
        this.salvoes = salvos;
    }

    public void addSalvo (Salvo newSalvo) {
        this.salvoes.add(newSalvo);
        newSalvo.setGamePlayer(this);
    }
//endregion

}