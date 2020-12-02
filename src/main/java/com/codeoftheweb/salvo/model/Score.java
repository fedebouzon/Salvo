package com.codeoftheweb.salvo.model;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gameId")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="playerId")
    private Player player;

    private double score;

    private Date finishDate;

    //region constructores

    public Score() {
    }

    public Score(Game game, Player player, Date finishDate, double score ) {
        this.game = game;
        this.player = player;
        this.finishDate = finishDate;
        this.score = score;
    }

    //endregion

    //region settergetter


    public Long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    //endregion

}
