/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;
import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.CartaNoAdeudoServiceRemote;
import mx.gob.imss.ctirss.delta.model.enums.RespuestaOpinion32DEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;

/**
 * @author daniel.hernandez
 * 
 */
@Configuration
@PropertySource("classpath:messages.properties")
public class ConsultaObraServiceImpl implements ConsultaObraService {

	/**
	 * logger EscritorioController
	 */
	private static final Logger logger = Logger.getLogger(ConsultaObraServiceImpl.class);

	/**
	 * Path base para localizacion de servicios.
	 */
	// private String PATH_BASE_URI =
	// "http://serviciosdigitales-stage.imss.gob.mx/sdroc-rest/";
	private String PROTOCOL = "http://";
	private String CONTEXT = "/sdroc-rest/";

	@Autowired
	private Environment env;

	@Autowired
	private CartaNoAdeudoServiceRemote cartaNoAdeudoService;

	@Autowired
	RestTemplate restTemplate;

	private String recoveryIP() {
		return env.getProperty("endpoint.rest");
	}

	/**
	 * consultaSubcontratosPorNumRegistroObra
	 */
	public Object consultaSubcontratosPorNumRegistroObra(String cveRegistroObraPrincipal) {

		List<InformacionObraDTO> listaObrasRegistradas = new ArrayList<InformacionObraDTO>();
		String respuesta32 = "";
		
		
		InformacionObraDTO[] listaObras = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT)
						.concat("consultarObrasPorCveRegistroObraPrincipal/{cveRegistroObraPrincipal}"),
				InformacionObraDTO[].class, cveRegistroObraPrincipal);
		
		for (int i = 0; i < listaObras.length; i++) {
			InformacionObraDTO obra = listaObras[i];

			respuesta32 = consultaOpinionCumplimiento32D(obra.getInformacionPatronDTO().getCveRfc());

			if (!respuesta32.equals("")) {
				obra.setNumEvaluacionD32(Integer.valueOf(respuesta32));
			} else
				obra.setNumEvaluacionD32(new Integer(3));
			listaObras[i] = obra;
		}

		listaObrasRegistradas = Arrays.asList(listaObras);

		return listaObrasRegistradas;
	}

	
	/**
	 * consultaObrasRegistradasAnualesPorRFC
	 */
	public int consultaObrasRegistradasAnualesPorRFC(String rfc, String anio) {
		
		Integer numObras = restTemplate.getForObject(PROTOCOL.concat(recoveryIP()).concat(CONTEXT)
				.concat("consultarNumeroTotalObrasPorCveRfcyAnio/{rfc}/{anio}"), Integer.class, rfc, anio);

		return numObras;
	}

	/**
	 * @param RFC, Anio
	 * consultarInformacionObrasPorCveRfcyAnio
	 * return List<InformacionObraDTO>
	 */
	public List<InformacionObraDTO> consultarInformacionObrasPorCveRfcyAnio(String rfc, String anio) {

		List<InformacionObraDTO> listaObras = new ArrayList<InformacionObraDTO>();

		InformacionObraDTO[] registrosObra = restTemplate.getForObject(
				PROTOCOL.concat(recoveryIP()).concat(CONTEXT)
						.concat("consultarReporteInformacionObrasPorCveRfcyAnio/{rfc}/{anio}"),
				InformacionObraDTO[].class, rfc, anio);

		listaObras = Arrays.asList(registrosObra);
		return listaObras;
	}
	
	/**
	 * Resuelve el tipo de persona a la que corresponde. 
	 * @param rfc
	 * @return Long {1 Moral, 2 Fisica}
	 */
	private Long resolverTipoPersonaPorRFC(String rfc){
		return (rfc.length() == 12 ? 2L : 1L);
	}
	
	/**
	 * Realiza la consulta que calcula la opinion de cumplimiento.
	 * 
	 * @param rfc
	 * @return String
	 */
	private String consultaOpinionCumplimiento32D(String rfc) {
		String opinionCumplimiento = "";
		try {
			Persona persona = new Persona();
			persona.setRfc(rfc);
			TipoPersona tipoPersona = new TipoPersona();
			
			tipoPersona.setIdTipoPersona(resolverTipoPersonaPorRFC(rfc));
			
			persona.setTipoPersona(tipoPersona);
			
			RespuestaOpinion32DEnum respuesta32 = null;
			respuesta32 = cartaNoAdeudoService.getOpinionRFC(persona, "", 2L);

			if (respuesta32.getId() == 1) {
				opinionCumplimiento = "1";
			} else if (respuesta32.getId() == 2 || respuesta32.getId() == 3 || respuesta32.getId() == 4
					|| respuesta32.getId() == 6) {
				opinionCumplimiento = "3";
			} else {
				opinionCumplimiento = "2";
			}

		} catch (EstadoAdeudoException e) {
			logger.error(e.getMessage(), e);
		}

		return opinionCumplimiento;
	}

}
