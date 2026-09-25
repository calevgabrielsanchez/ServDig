/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.test.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.web.client.RestTemplate;

/**
 * @author daniel.hernandez
 * 
 */
public class TestService {

	public static void main(String[] args) {
		consulta();
	}

	private static void consulta() {

		consultaUtimaIncidencia();

	}

	private static void consultaUtimaIncidencia() {
		RestTemplate template = new RestTemplate();
		Long cveInformacionObra = new Long("686");
	}
}
