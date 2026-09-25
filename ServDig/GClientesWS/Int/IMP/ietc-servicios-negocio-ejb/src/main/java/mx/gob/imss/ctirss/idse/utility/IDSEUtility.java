package mx.gob.imss.ctirss.idse.utility;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.idse.model.Persona;
import mx.gob.imss.ctirss.idse.model.RegistroPatronal;
import mx.gob.imss.ctirss.idse.model.RequerimientoIDSEBean;
import mx.gob.imss.ctirss.idse.persistencia.Patrones;
import mx.gob.imss.ctirss.idse.persistencia.PatronesIdse;

import org.springframework.util.CollectionUtils;

@Stateless(mappedName="idseUtility", name="idseUtility")
public class IDSEUtility implements IDSEUtilityLocal{

	private static final int POSICIONES_MAXIMA_DOMICILIO_SINDO = 40;
	private static final int POSICIONES_MAXIMA_NOMBRE_PATRON_SINDO = 50;
	private static final int POSICIONES_MAXIMA_CORREO_SINDO = 50;
	private static final int POSICIONES_MAXIMA_NOMBRE_RL_SINDO = 60;
	private static final String PREFIJO_RP = "P";
	
	@Override
	public Patrones convertirModelToEntityPatrones(RegistroPatronal registroPatronal) {
		Patrones patron = new Patrones();
		int tamanioCadena = registroPatronal.getNrp().length();
		String rp = registroPatronal.getNrp().substring(0, tamanioCadena-1);
		String dv = registroPatronal.getNrp().substring(tamanioCadena-1);
		
		patron.setRegPatron(rp);
		patron.setRegPatronDig(String.valueOf(dv));
		patron.setActividad(registroPatronal.getActividad());
		patron.setClaseRt(new BigDecimal(registroPatronal.getClase()));
		patron.setCveDel(new BigDecimal(registroPatronal.getCveDelegacion()));
		patron.setCveSub(new BigDecimal(registroPatronal.getCveSubdelegacion()));
		patron.setFraccion(new BigDecimal(registroPatronal.getFraccion()));
		patron.setLocalidad(registroPatronal.getLocalidad());
		patron.setMunicipio(registroPatronal.getCveMunicipio());
		patron.setRegPatronFijo(PREFIJO_RP);
		patron.setRfc(registroPatronal.getPatron().getRfc());
		patron.setSector(new BigDecimal(registroPatronal.getCveSector()));
		patron.setTipoPatron(BigDecimal.ZERO);
		//Se recibe domicilio con 200 posiciones maximo para IETC, se trunca a 40 para SINDO.
		if(registroPatronal.getDomicilioCentroTrabajo()!=null){
			patron.setDomicilio(obtenerCadena(registroPatronal.getDomicilioCentroTrabajo(), POSICIONES_MAXIMA_DOMICILIO_SINDO));
		}
		//Se recibe nombreRazonSocial con 100 posiciones maximo para IETC, se trunca a 50 para SINDO.
		if(registroPatronal.getRazonSocial()!=null){
			patron.setNombrePatron(obtenerCadena(registroPatronal.getRazonSocial(), POSICIONES_MAXIMA_NOMBRE_PATRON_SINDO));
		}
		return patron;
	}

	@Override
	public PatronesIdse convertirModelToEntityPatronesIdse(
			RegistroPatronal registroPatronal) {
		PatronesIdse patron = new PatronesIdse(); 
		String rp = registroPatronal.getNrp().substring(0, (registroPatronal.getNrp().length())-1);
		patron.setRegPatron(rp);
		patron.setNofolio(BigDecimal.ZERO);
		patron.setNpie(registroPatronal.getNrp());
		patron.setRepresentanteLegal(" ");		
		//Se recibe correo con 100 posiciones maximo para IETC, se trunca a 50 para SINDO.
		if(registroPatronal.getCorreo()!=null){
			patron.setEmail(obtenerCadena(registroPatronal.getCorreo(), POSICIONES_MAXIMA_CORREO_SINDO));
		}		
		if(registroPatronal.getRepresentantes() != null){
			List<Persona> representantesLegales = Arrays.asList(registroPatronal.getRepresentantes());	
			if(!CollectionUtils.isEmpty(representantesLegales)){
				Persona representanteLegal = representantesLegales.get(0);
				//Se recibe nombre con 100 posiciones maximo para IETC, se trunca a 60 para SINDO.
				if(representanteLegal.getNombreRazonSocial()!=null){
					patron.setRepresentanteLegal(obtenerCadena(representanteLegal
						.getNombreRazonSocial(), POSICIONES_MAXIMA_NOMBRE_RL_SINDO));
				}
			}
		}
		return patron;
	}
	
	private String obtenerCadena(String cadena, int maximoCadena){
		if(cadena.length()>maximoCadena){
			cadena = cadena.substring(0, maximoCadena);
		}
		return  cadena;
	}
	
	
	@Override
	public Patrones convertirModelToEntityPatrones(RequerimientoIDSEBean reqIdse) {
		Patrones patron = new Patrones();
		patron.setActividad(reqIdse.getActividad());
		patron.setClaseRt(new BigDecimal(reqIdse.getClaseRt()));
		patron.setCveDel(new BigDecimal(reqIdse.getCveDel()));
		patron.setCveSub(new BigDecimal(reqIdse.getCveSub()));
		patron.setDomicilio(reqIdse.getDomicilio());
		patron.setFraccion(new BigDecimal(reqIdse.getFraccion()));
		patron.setLocalidad(reqIdse.getLocalidad());
		patron.setMunicipio(reqIdse.getMunicipio());
		patron.setNombrePatron(reqIdse.getRazonSocial());
		patron.setRegPatronFijo("P");
		patron.setRegPatron(reqIdse.getRegPatron());
		patron.setRegPatronDig(String.valueOf(reqIdse.getRegPatronDig()));
		patron.setRfc(reqIdse.getRfc());
		patron.setSector(new BigDecimal(reqIdse.getSector()));
		patron.setTipoPatron(BigDecimal.ZERO);
		
		return patron;
	}

	@Override
	public PatronesIdse convertirModelToEntityPatronesIdse(
			RequerimientoIDSEBean reqIdse) {
		PatronesIdse patron = new PatronesIdse();
		patron.setEmail(reqIdse.getEmail());
		patron.setRegPatron(reqIdse.getRegPatron());
		patron.setNofolio(BigDecimal.ZERO);
		patron.setNpie(reqIdse.getRegPatron()+reqIdse.getRegPatronDig());
		patron.setRepresentanteLegal(reqIdse.getRepLegal());
		
		return patron;
	}
}
