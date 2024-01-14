package com.search.marvel.vo;

import java.io.Serializable;

public class ApiMarvelResponseVo  implements Serializable {
    private String mensaje;
    private Integer statusCode;
    private String characterData;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getCharacterData() {
        return characterData;
    }

    public void setCharacterData(String characterData) {
        this.characterData = characterData;
    }

}
