package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Salvo;
import java.util.LinkedHashMap;
import java.util.Map;

public class SalvoDTO {

    private Salvo salvo;

    //region constructor

    public SalvoDTO() {
    }

    public SalvoDTO(Salvo salvo) {
        this.salvo = salvo;
    }

    //endregion

    public Map<String, Object> makeSalvoDTO(Salvo salvo){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("player", salvo.getGamePlayer().getPlayer().getId());
        dto.put("turn", salvo.getTurn());
        dto.put("locations", salvo.getLocations());
        return dto;
    }

    //region gettersetter

    public Salvo getSalvo() {
        return salvo;
    }

    public void setSalvo(Salvo salvo) {
        this.salvo = salvo;
    }

    //endregion
}
