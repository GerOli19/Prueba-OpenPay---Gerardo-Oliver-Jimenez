package com.search.marvel.api_rest.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MBL
 */

@Service
public class JwtUtilImpl implements JwtUtil{
	
	@Value(value = "${secret.Key.jwt}")
	private String secretKey;

	@Override
	public String validateCredentials(String username, String password) {
		
		boolean usuarioEncontrado = ("root".equals(username) && "admin".equals(password));
		JSONObject response = new JSONObject();
		
		if(usuarioEncontrado) {
			
			String token = getJWTToken(username);
			
			response.put("status", HttpStatus.OK.value());
			response.put("token", token);
			
		}else {
			
			response.put("status", HttpStatus.FORBIDDEN.value());
			
		}
		
		return response.toString();
		
	}
	
	/**Método para validar la existencia de un token JWT en la solicitud.
	 *
	 * /@param request  La solicitud HTTP. Puedes usar esto para obtener información sobre la solicitud,
	 *                 como los encabezados, que pueden incluir el token JWT.
	 * /@param response La respuesta HTTP. Puedes usar esto para modificar la respuesta que se enviará al cliente.
	 * @return boolean Si existe un token JWT en la solicitud, devuelve true, de lo contrario devuelve false.
	 */
	private String getJWTToken(String user) {
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("marvelJWT")
				.setSubject(user)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 60000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
		
	}
	
}
