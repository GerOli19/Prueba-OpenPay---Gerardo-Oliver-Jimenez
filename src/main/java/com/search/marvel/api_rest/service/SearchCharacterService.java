package com.search.marvel.api_rest.service;

import com.search.marvel.api_rest.repository.BitacoraAccesoMarvelEntity;

import java.util.List;

/**
 * Interfaz de metodo para consumo de API Marvel
 * @autor Gerardo Oliver Jimenez
 * */
public interface SearchCharacterService {

    /**
     * Metodo para obtener los personajes del API
     */
    String getCharacters(Integer characterId);

    /**
     * Metodo para obtener los registros de las consultas realizadas.
     * */
    List<BitacoraAccesoMarvelEntity> getDataBitacora();
}
