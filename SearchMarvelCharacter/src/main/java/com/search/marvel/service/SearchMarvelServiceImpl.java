package com.search.marvel.service;

import com.search.marvel.vo.ApiMarvelResponseVo;
import com.search.marvel.config.SearchMarvelConfig;
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
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

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
    public ResponseEntity<Object> getCharacters(Integer characterId) {

        String jsonResponse = null;
        try {

            webClient = new SearchMarvelConfig().webClient();

            URI uri = getUriConfig(characterId);

            jsonResponse = webClient.get()
                    .uri(uri)
                    .header("Content-Type", String.valueOf(MediaType.APPLICATION_JSON))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        }catch (Exception exc){
            return validResponseApi(String.valueOf(((WebClientResponseException.NotFound) exc).getRawStatusCode()));
        }

        return validResponseApi(jsonResponse);
    }

    /**
     * Metodo para configurar la URL que se utilizara para consumir el API utilizando el
     * parametro del charcterid, apikey, hash y timestamp.
     * */
    private URI getUriConfig(Integer characterId) throws NoSuchAlgorithmException {

        String urlPath = getUrlpath(characterId);
        Instant timeStamp = Instant.now();
        String hash = getHash(timeStamp);

        return UriComponentsBuilder.fromUriString(MarvelConstants.PATH_DEFAULT + urlPath)
                .queryParam(MarvelConstants.QUERY_PARAM_TIME_STAMP, timeStamp)
                .queryParam(MarvelConstants.QUERY_PARAM_API_KEY, MarvelConstants.PUBLIC_KEY)
                .queryParam(MarvelConstants.QUERY_PARAM_HASH, hash)
                .build()
                .toUri();
    }

    /**
    * Metodo para validar si se consulta algun personaje en especifico por Id
    * o si se recupera la lista completa de registros en caso de no proporcionar
    * ningun Id de Caracter*/
    private String getUrlpath(Integer characterId){
        StringBuilder urlPath = new StringBuilder();

        urlPath.append(MarvelConstants.PATH_MARVEL_CHARACTERS);

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

        ApiMarvelResponseVo apiMarvelResponseVo = new ApiMarvelResponseVo();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.toString());

        if(responseApi.equals("404")){

            apiMarvelResponseVo.setStatusCode(Integer.valueOf(responseApi));
            apiMarvelResponseVo.setMensaje(MarvelConstants.MENSAJE_RESPONSE_NO_ENCONTRADO);
            return new ResponseEntity<>(apiMarvelResponseVo, httpHeaders, HttpStatus.NOT_FOUND);
        }

        if(responseApi.equals("500")){

            apiMarvelResponseVo.setStatusCode(Integer.valueOf(responseApi));
            apiMarvelResponseVo.setMensaje(MarvelConstants.MENSAJE_RESPONSE_ERROR);
            return new ResponseEntity<>(apiMarvelResponseVo, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        JSONObject jsonResponseApiObject = new JSONObject(responseApi);

        apiMarvelResponseVo.setStatusCode(Integer.valueOf(jsonResponseApiObject.get("code").toString()));
        apiMarvelResponseVo.setCharacterData(jsonResponseApiObject.toString());
        apiMarvelResponseVo.setMensaje(MarvelConstants.MENSAJE_RESPONSE_OK);

        return new ResponseEntity<>(apiMarvelResponseVo, httpHeaders, HttpStatus.OK);
    }

    /**
     * Método que crea un hash para el consumo de la API.
     * se utiliza timestamp para la peticion al api
     * tambien se utiliza el public y private key que nos proporciona al registrarnos en la pagina.
     * y se genera un hash que permita validar y consultar de forma correcta.
     */
    private String getHash(Instant timeStamp) throws NoSuchAlgorithmException {

        StringBuilder parametros = new StringBuilder();

        parametros.append(timeStamp);
        parametros.append(MarvelConstants.PRIVATE_KEY);
        parametros.append(MarvelConstants.PUBLIC_KEY);

        MessageDigest md = MessageDigest.getInstance(MarvelConstants.ALGORITHM_MD5);
        byte[] hash = md.digest(parametros.toString().getBytes());

        return DatatypeConverter.printHexBinary(hash).toLowerCase();

    }
}
