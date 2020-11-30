package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Salvo {


    //region Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String turn;

    @ElementCollection
    @Column(name="locations")
    private List<String> locations;


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="gPID")
    private GamePlayer gamePlayer;
    //endregion


    //region constructores

    public Salvo() {
        locations = new ArrayList<>();
    }

    public Salvo(String turn, List<String> locations) {
        this.turn = turn;
        this.locations = locations;
    }

    //endregion

    public void addLocation(String newLocation){
        this.locations.add(newLocation);
    }

    //region gettersetter

    public Long getId() {
        return id;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }


    //endregion
}
