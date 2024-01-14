package com.search.marvel.api_rest.controller;

import com.search.marvel.api_rest.repository.BitacoraAccesoMarvelEntity;
import com.search.marvel.api_rest.security.JwtUtil;
import com.search.marvel.api_rest.service.SearchCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "consumer/api/marvel")
public class SearchCharactersController {

    @Autowired
    private SearchCharacterService consumerCharactersService;

    @Autowired
    private JwtUtil jwtUtil;

    /**Método que obtiene los Characters al consumir API de Marvel.
     * @param characterId Integer: ID del Character a recuperar, solo si se recibe este parámetro se realizará la búsqueda filtrada,
     * de lo contrario recuperará el catálogo completo.
     * @return responseEntity ResponseEntity<Object>: Objeto que contiene la respuesta exitosa con formato.
     */
    @GetMapping(value = "getCharacters", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getCharacters(@RequestParam(value = "characterId", required = false) Integer characterId){

        return consumerCharactersService.getCharacters(characterId);

    }

    /**Este método se utiliza para validar las credenciales de un usuario y generar un token JWT si las credenciales son válidas.
     * @param username El nombre de usuario que se va a validar.
     * @param pwd La contraseña que se va a validar.
     * @return Un token JWT si las credenciales son válidas, o un mensaje de error si no lo son.
     */
    @PostMapping(value = "access", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String access(@RequestParam("user") String username, @RequestParam("password") String pwd) {

        return jwtUtil.validateCredentials(username, pwd);

    }

    /**Este método se utiliza para obtener los datos de la bitácora de acceso a la API de Marvel.
     *
     * @return Una lista de entidades BitacoraAccesoMarvelEntity que representan los registros de la bitácora de acceso.
     */
    @GetMapping(value = "getDataBitacora", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<BitacoraAccesoMarvelEntity> getDataBitacora(){

        return consumerCharactersService.getDataBitacora();

    }

}
