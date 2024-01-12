package com.search.marvel.service;

import com.search.marvel.utils.ApiMarvelException;
import com.search.marvel.utils.MarvelConstants;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

@Service
public class SearchMarvelServiceImpl implements SearchMarvelService{

    @Autowired
    private WebClient webClient;

    @Value("${public.key.api}")
    private String publicKeyApi;

    @Value("${private.key.api}")
    private String privateKeyApi;

    /**
     * Implementacion de metodo para consumo de API Marvel
     * @autor Gerardo Oliver Jimenez
     * */
    @Override
    public ResponseEntity<Object> getCharacters(Integer characterId) throws ApiMarvelException {

        String urlPath = getUrlpath(characterId);
        String jsonResponse = null;
        try {

            jsonResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(urlPath)
                            .queryParam(MarvelConstants.QUERY_PARAM_API_KEY, publicKeyApi)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (jsonResponse == null || jsonResponse.isEmpty()){
                throw new ApiMarvelException("No se pudo encontrar la informacion solicitada en API Marvel.");
            }

        }catch (Exception exc){
            throw new ApiMarvelException("Ocurrio un error al consultar el API Marvel: " + exc.getMessage());
        }


        return validResponseApi(jsonResponse);
    }

    /**
    * Metodo para validar si se consulta algun personaje en especifico por Id
    * o si se recupera la lista completa de registros en caso de no proporcionar
    * ningun Id de Caracter*/
    private String getUrlpath(Integer characterId){
        StringBuilder urlPath = new StringBuilder();

        urlPath.append(MarvelConstants.PATH_MARVER_CHARACTERS);

        if(characterId != null && characterId > 0){

            urlPath.append("/");
            urlPath.append(characterId.toString());

        }
        return urlPath.toString();
    }

    /**
    * Metodo en el cual se agregan los Headers al ResponseEntity
    * para asignar el tipo de objeto de respuesta asi como el encoding que este tendra
    * al leer la informacion que se obtiene al consultar el API de Marvel*/
    private ResponseEntity<Object> validResponseApi(String responseApi){

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.toString());

        JSONObject jsonResponseApiObject = new JSONObject(responseApi);

        return new ResponseEntity<>(jsonResponseApiObject.toString(), httpHeaders, HttpStatus.OK);
    }
}
