package com.search.marvel.utils;

public class ApiMarvelException extends Exception{
    public ApiMarvelException(String mensaje){
        super(mensaje);
    }

    public ApiMarvelException(String mensaje, Throwable causa){
        super(mensaje, causa);
    }
}
