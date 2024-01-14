package com.search.marvel.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class SearchMarvelServiceImplTest {

    /**
     * Test para obtener todos los Caracters del Api Marvel al no propocionar ningun id
     * */
    @Test
    void getAllCharacters() {

        SearchMarvelService searchMarvelService= new SearchMarvelServiceImpl();
        ResponseEntity<Object> respuesta = searchMarvelService.getCharacters(0);
        assertNotNull(respuesta);
        assertEquals(200,respuesta.getStatusCodeValue());
    }

    /**
     * Test para obtener un Caracters del Api Marvel al propocionar un id valido
     * */
    @Test
    void getCharacterById() {

        Integer idCharacter = 1009146;
        /**
             "id":1009146,
            "name":"Abomination (Emil Blonsky)",
            * */
        SearchMarvelService searchMarvelService= new SearchMarvelServiceImpl();
        ResponseEntity<Object> respuesta = searchMarvelService.getCharacters(idCharacter);
        assertNotNull(respuesta);
        assertEquals(200,respuesta.getStatusCodeValue());
    }

    /**
     * Test para obtener un statusCode 404 al no encontrar ningun Character
     * con el id proporcionado
     * */
    @Test
    void getCharacterByIdNotFound() {

        Integer idCharacter = 100;
        /**
         "id":100,
         {"code":404,"status":"We couldn't find that character"}
         * */
        SearchMarvelService searchMarvelService= new SearchMarvelServiceImpl();
        ResponseEntity<Object> respuesta = searchMarvelService.getCharacters(idCharacter);
        assertEquals(404,respuesta.getStatusCodeValue());
    }
}