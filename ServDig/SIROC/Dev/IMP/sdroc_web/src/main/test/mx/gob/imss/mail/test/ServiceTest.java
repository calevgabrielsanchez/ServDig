/**
 * 
 */
package mx.gob.imss.mail.test;

import mx.gob.imss.csdiss.sdroc.model.ClasificacionObraDTO;
import mx.gob.imss.csdiss.sdroc.model.DelegacionDTO;
import mx.gob.imss.csdiss.sdroc.model.EstatusObraDTO;
import mx.gob.imss.csdiss.sdroc.model.InformacionObraDTO;
import mx.gob.imss.csdiss.sdroc.model.InformacionPatronDTO;
import mx.gob.imss.csdiss.sdroc.model.ObjetoContratoDTO;
import mx.gob.imss.csdiss.sdroc.model.SubDelegacionDTO;
import mx.gob.imss.csdiss.sdroc.model.TipoObraDTO;
import mx.gob.imss.csdiss.sdroc.model.TipoPatronDTO;
import mx.gob.imss.csdiss.sdroc.model.TipoPersonaDTO;
import mx.gob.imss.csdiss.sdroc.model.UbicacionObraDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author daniel.hernandez
 * 
 */
public class ServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();

		InformacionObraDTO obra = new InformacionObraDTO();

		DelegacionDTO delegacion = new DelegacionDTO();
		delegacion.setCveDelegacion((short) 1);
		delegacion.setNomDelegacion("");

		SubDelegacionDTO subdelegacion = new SubDelegacionDTO();
		subdelegacion.setCveSubdelegacion((short) 1);
		subdelegacion.setDelegacionDTO(delegacion);

		ClasificacionObraDTO claficiacion = new ClasificacionObraDTO();
		claficiacion.setCveClasificacionObra((short) 1);

		TipoObraDTO tipoObra = new TipoObraDTO();
		tipoObra.setClasificacionObraDTO(claficiacion);
		tipoObra.setCveTipoObra((short) 20);

		TipoPersonaDTO tipoPersona = new TipoPersonaDTO();
		tipoPersona.setCveTipoPersona((short) 1);

		TipoPatronDTO tipoPatron = new TipoPatronDTO();
		tipoPatron.setCveTipoPatron((short) 1);

		InformacionPatronDTO informacionPatron = new InformacionPatronDTO();
		informacionPatron.setNomPatron("nompatron");
		informacionPatron.setRefApellidoPaterno("ramirez");
		informacionPatron.setRefApellidoMaterno("hernandez");
		informacionPatron.setRefRazonSocial("razonsocialalgo");
		informacionPatron.setCveRfc("HEGB820907l48");
		informacionPatron.setCveRegPatronal("IUWEIWUIEUWIE");
		informacionPatron.setTipoPatronDTO(tipoPatron);
		informacionPatron.setTipoPersonaDTO(tipoPersona);

		EstatusObraDTO estatus = new EstatusObraDTO();
		estatus.setCveEstatusObra(new Long(1));

		UbicacionObraDTO ubicacion = new UbicacionObraDTO();
		ubicacion.setCalle("calle algo");
		ubicacion.setNumExterior("234");
		ubicacion.setCodigoPostal("57140");

		ObjetoContratoDTO objetoContrato = new ObjetoContratoDTO();
		objetoContrato.setCveObjetoContrato((short) 1);

		obra.setSubDelegacionDTO(subdelegacion);
		obra.setCveRegistroObra(new Long("123456789012345"));
		obra.setTipoObraDTO(tipoObra);
		obra.setInformacionPatronDTO(informacionPatron);
		obra.setEstatusObraDTO(estatus);
		obra.setUbicacionObraDTO(ubicacion);
		obra.setObjetoContratoDTO(objetoContrato);

		for (int i = 0; i < 10000; i++) {
			ResponseEntity<Long> response = restTemplate
					.postForEntity(
							"http://lstkeg93745:8080/Spring4/data/guardarInformacionObra",
							obra, Long.class);
		}

	}

}
