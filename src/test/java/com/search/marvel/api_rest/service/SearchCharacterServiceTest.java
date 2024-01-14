package com.search.marvel.api_rest.service;

import com.search.marvel.api_rest.ClienteMarvelApplication;
import com.search.marvel.api_rest.repository.BitacoraAccesoMarvelEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchCharacterServiceTest {

    @Test
    void getAllCharacters() {

        int characterId = 0;
        // Crear la instancia del servicio con el cliente simulado
        SearchCharacterService marvelService = new SearchCharacterServiceImpl();

        // Llamar al método del servicio que utiliza el cliente simulado
        String characterName = marvelService.getCharacters(characterId);

        // Verificar que el resultado es el esperado
        assertNotNull(characterName);

    }

    @Test
    void getAllCharacterById() {

        int characterId = 1011334;
        /**
         * Id: 1011334
         * Charcter: 3-D Man
         * */
        // Crear la instancia del servicio con el cliente simulado
        SearchCharacterService marvelService = new SearchCharacterServiceImpl();

        // Llamar al método del servicio que utiliza el cliente simulado
        String characterName = marvelService.getCharacters(characterId);

        // Verificar que el resultado es el esperado
        assertNotNull(characterName);

    }

    @Test
    void getDataBitacora() {

        // Crear la instancia del servicio con el cliente simulado
        SearchCharacterService marvelService = new SearchCharacterServiceImpl();

        // Llamar al método del servicio que utiliza el cliente simulado
        List<BitacoraAccesoMarvelEntity> datosBitacora = marvelService.getDataBitacora();

        // Verificar que el resultado es el esperado
        assertNotNull(datosBitacora);

    }
}