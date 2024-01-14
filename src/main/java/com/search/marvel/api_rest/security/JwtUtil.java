package com.search.marvel.api_rest.security;

/**
 * @author MBL
 */

public interface JwtUtil {

	/**Método para validar las credenciales de un usuario.
	 *
	 * @param username El nombre de usuario que se va a validar. Este es el identificador único del usuario en el sistema.
	 * @param password La contraseña que se va a validar. Esta debe ser la contraseña en texto plano del usuario.
	 * @return String Si las credenciales son válidas, este método devuelve un token JWT que el usuario puede usar para autenticarse en solicitudes futuras.
	 */
	String validateCredentials(String username, String password);
	
}
