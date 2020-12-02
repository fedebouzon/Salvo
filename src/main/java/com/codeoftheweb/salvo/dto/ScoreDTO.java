package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Score;
import java.util.LinkedHashMap;
import java.util.Map;

public class ScoreDTO {
    private Score score;

    //region constructor
    public ScoreDTO() {
    }

    public ScoreDTO(Score score) {
        this.score = score;
    }
//endregion

    public Map<String, Object> makeScoreDTO(Score score){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", score.getId());
        dto.put("player", score.getPlayer().getId());
        dto.put("score", score.getScore());
        dto.put("finishdate", score.getFinishDate());
        return dto;
    }

    //region gettersetter

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }


    //endregion

}