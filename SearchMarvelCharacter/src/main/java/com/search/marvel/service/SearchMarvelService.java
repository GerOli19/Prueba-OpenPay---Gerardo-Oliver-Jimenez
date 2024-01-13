package com.search.marvel.service;

import org.springframework.http.ResponseEntity;

/**
* Interfaz de metodo para consumo de API Marvel
* @autor Gerardo Oliver Jimenez
* */

public interface SearchMarvelService {

    /**
    * Metodo para obtener los personajes del API
    * */
    ResponseEntity<Object> getCharacters(Integer characterId);

}
