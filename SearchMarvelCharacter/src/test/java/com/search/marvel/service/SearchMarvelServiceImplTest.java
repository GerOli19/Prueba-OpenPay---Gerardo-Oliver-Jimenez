package com.search.marvel.service;

import com.search.marvel.utils.ApiMarvelException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class SearchMarvelServiceImplTest {

    @Test
    void getCharacters() throws ApiMarvelException {

        SearchMarvelService searchMarvelService= new SearchMarvelServiceImpl();
        ResponseEntity<Object> respuesta = searchMarvelService.getCharacters(0);
        assertNotNull(respuesta);
    }
}