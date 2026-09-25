/**
 * Prueba de los servicios que proporcionan los datos de catalogos para SIROC.
 */
package mx.gob.imss.csdiss.sdroc.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author daniel.hernandez
 * 
 */
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CatalogosServiceTest extends TestCase {
	/**
	 * PATH_URI
	 */
	private static String PATH_BASE_URI = "http://localhost:7001/sdroc-rest/";
	
	public static void main(String[] args) {
		//calcularSemaforo();
		ultimoReporteBimestralReportado();
	}
	
	private static void ultimoReporteBimestralReportado(){
		RestTemplate restTemplate = new RestTemplate();
		InformacionIncidenciaDTO incidencia = new InformacionIncidenciaDTO();
		incidencia = restTemplate.getForObject(PATH_BASE_URI.concat("consultarUltimoReporteBimestralPresentado/{cveInformacionObra}"),InformacionIncidenciaDTO.class, "2891", 6);
		String ultimoDiaPaPresentar = incidencia.getCalendarioReporteDTO().getFecFinPerDeclarado().getDate()+"/"+(incidencia.getCalendarioReporteDTO().getFecFinPerDeclarado().getMonth()+1)+"/"+incidencia.getNumAnio();
		
	}
	
	private static void borrarBimestres(){
		try {
			RestTemplate restTemplate = new RestTemplate();
			Date fecha = new Date();
			Boolean a = restTemplate.getForObject("http://localhost:7001/sdroc-rest/eliminaReporteBimByCveInfoObra/{a}/{b}", Boolean.class, new Long("2886"), "31122014");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	

}
