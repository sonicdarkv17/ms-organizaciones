package com.smart.wsorg.utilerias;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase de utilerias
 * @author Ivan Garcia
 *
 */
@Slf4j
public final class Utilerias {

	/**
	 * Metodo encargado de encriptar en algoritmo SHA 256
	 * @param campo String  a encriptar
	 * @return String encriptado
	 */
	public static String encripta(String campo) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			log.error("Error al encriptra ", e);
		}
		byte[] hash = digest.digest(campo.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(hash);
	}

}
