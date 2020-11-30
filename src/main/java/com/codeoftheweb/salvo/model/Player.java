package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;
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
    //nombre de la variable a buscar
    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    //nombre del set de la base de datos
    private Set<GamePlayer> gamePlayers;
    //endregion

    //region Constructores
    public Player() {
    }

    public Player(String name, String email) {
        this.name = name;
        this.email = email;
    }
    //endregion

    //region getterSetters
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

