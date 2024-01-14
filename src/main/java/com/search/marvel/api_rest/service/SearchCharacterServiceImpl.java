package com.search.marvel.api_rest.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.marvel.api_rest.utils.SearchCharactersConstants;
import com.search.marvel.api_rest.repository.BitacoraAccesoMarvelEntity;
import com.search.marvel.api_rest.repository.BitacoraMarvelRepository;
import com.search.marvel.api_rest.vo.CharacterVO;
import com.search.marvel.service.SearchMarvelService;
import com.search.marvel.service.SearchMarvelServiceImpl;
import com.search.marvel.vo.ApiMarvelResponseVo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class SearchCharacterServiceImpl implements SearchCharacterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchCharacterServiceImpl.class);

    @Autowired
    private SearchMarvelService searchMarvelService;

    @Autowired
    private BitacoraMarvelRepository bitacoraRepository;

    /**
     * Metodo para obtener los personajes del API
     *
     * @param characterId
     */
    @Override
    public String getCharacters(Integer characterId) {

        saveBitacora(characterId);

        ResponseEntity<Object> responseMarvel = getCharactersApi(characterId);

        return processResponseApi(responseMarvel);
    }

    /**Método que se utiliza para procesar la respuesta obtenida de la API de Marvel.
     *
     * @param responseMarvel Un objeto ResponseEntity que contiene la respuesta de la API de Marvel.
     *                       Este objeto puede contener los datos de los personajes o información de error, dependiendo de la respuesta de la API.
     * @return Una cadena de texto que representa el resultado del procesamiento de la respuesta de la API.
     *         Esta cadena puede contener los datos de los personajes en un formato específico, o un mensaje de error si la respuesta de la API indicó un error.
     * @author MBL
     */
    private String processResponseApi(ResponseEntity<Object> responseMarvel){

        JSONObject response = null;

        if(responseMarvel != null) {

            boolean repuestaApiCorrecta = (responseMarvel.getStatusCode() == HttpStatus.OK);

            if(repuestaApiCorrecta && responseMarvel.getBody() != null) {

                ApiMarvelResponseVo responseOk = (ApiMarvelResponseVo) responseMarvel.getBody();

                if(responseOk != null && responseOk.getCharacterData() != null) {

                    String jsonResponseMarvel = responseOk.getCharacterData();
                    JSONObject dataMarvel = new JSONObject(jsonResponseMarvel);
                    boolean conResultados = (dataMarvel.has(SearchCharactersConstants.KEY_DATA) &&
                            dataMarvel.get(SearchCharactersConstants.KEY_DATA) != JSONObject.NULL &&
                            dataMarvel.getJSONObject(SearchCharactersConstants.KEY_DATA).has(SearchCharactersConstants.KEY_RESULTS) &&
                            dataMarvel.getJSONObject(SearchCharactersConstants.KEY_DATA).get(SearchCharactersConstants.KEY_RESULTS) != JSONObject.NULL);

                    if(conResultados && dataMarvel.getJSONObject(SearchCharactersConstants.KEY_DATA)
                            .get(SearchCharactersConstants.KEY_RESULTS) instanceof JSONArray arrayResultados) {

                        response = setDataCharacters(arrayResultados);

                    }

                }

            }

        }

        if(response == null){

            response = new JSONObject();
            response.put(SearchCharactersConstants.KEY_STATUS, HttpStatus.NOT_FOUND.value());

        }

        return response.toString();

    }

    /**Método que se utiliza para establecer los datos de los personajes en un JSONObject a partir de un JSONArray de resultados.
     *
     * @param arrayResultados Un JSONArray que contiene los resultados obtenidos de la API.
     *                        Este array contiene objetos JSON que representan a los personajes.
     * @return Un JSONObject que contiene los datos de los personajes.
     *         Cada clave en este JSONObject es un atributo de los personajes,
     *         y el valor asociado es el valor de ese atributo para los personajes en arrayResultados.
     * @author MBL
     */
    private JSONObject setDataCharacters(JSONArray arrayResultados) {

        JSONObject response = null;

        if(arrayResultados != null && !arrayResultados.toString().isEmpty()) {

            JSONArray arrayCharacters = new JSONArray();
            JSONObject character;
            CharacterVO characterVO;

            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            for (int indice = 0; indice < arrayResultados.length(); indice++) {

                character = (JSONObject) arrayResultados.get(indice);

                try {

                    characterVO = objectMapper.readValue(character.toString(), CharacterVO.class);
                    arrayCharacters.put(new JSONObject(objectMapper.writeValueAsString(characterVO)));

                } catch (JSONException e) {

                    LOGGER.error("Error al establecer los datos de los personajes - JSONException", e);

                } catch (Exception e) {

                    LOGGER.error("Error al establecer los datos de los personajes - Exception", e);

                }

            }

            if(!arrayCharacters.toString().isEmpty()) {

                response = new JSONObject();

                response.put(SearchCharactersConstants.KEY_STATUS, HttpStatus.OK.value());
                response.put(SearchCharactersConstants.KEY_ARRAY_CHARACTERS, arrayCharacters);

            }

        }

        return response;

    }

    /**Método se utiliza para obtener los personajes de la API externa.
     * @param characterId El ID del personaje que se desea obtener. Este es un entero que representa el identificador único del personaje.
     * @return Un objeto ResponseEntity que contiene el resultado de la llamada a la API.
     *         Si la llamada fue exitosa, este objeto contendrá los datos del personaje.
     *         Si la llamada falló, este objeto contendrá información sobre el error.
     */
    private ResponseEntity<Object> getCharactersApi(Integer characterId) {
        searchMarvelService = new SearchMarvelServiceImpl();

        return searchMarvelService.getCharacters(characterId);

    }

    /**Este método se utiliza para guardar un registro en la bitácora de acceso a los personajes de Marvel.
     * @param characterId El ID del personaje de Marvel que se accedió. Este ID se guardará en la bitácora
     * para llevar un registro de los personajes a los que se ha accedido.
     */
    private void saveBitacora(Integer characterId) {

        try {

            BitacoraAccesoMarvelEntity bitacoraEntity = new BitacoraAccesoMarvelEntity();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            bitacoraEntity.setHoraConsulta(sdf.format(calendar.getTime()));

            if(characterId != null) {

                bitacoraEntity.setPath(SearchCharactersConstants.DESCRIPTION_PATH_TWO);

            }else {

                bitacoraEntity.setPath(SearchCharactersConstants.DESCRIPTION_PATH_ONE);

            }

            bitacoraRepository.save(bitacoraEntity);

        } catch (Exception e) {

            LOGGER.error("No se logró guardar el registro en la bitácora", e);

        }

    }


    @Override
    public List<BitacoraAccesoMarvelEntity> getDataBitacora() {

        return (List<BitacoraAccesoMarvelEntity>) bitacoraRepository.findAll();

    }
}
