package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DicPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacionPK;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalific;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalificPK;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;

@Stateless(name="calificacionesPersonaUtilityService", mappedName="calificacionesPersonaUtilityService")
public class CalificacionesPersonaUtilityService extends AbstractServiceUtility
		implements CalificacionesPersonaUtilityServiceLocal {

	@Override
	public PersonaCalificacion transformarAModelo(
			DitHistPersonaCalificacion entity) throws TransformacionException {	
				
		if (entity == null) {
			throw new TransformacionException();
		}
		
		PersonaCalificacion model = new PersonaCalificacion();
		Calificacion calificacion = new Calificacion();
		
		calificacion.setIdCalificacion(entity.getId().getCveIdCalificacion());
		calificacion.setDescripcion(CalificacionEnum.valueIntOf(entity.getId().getCveIdCalificacion().intValue()).getDescripcion());
		
		model.setCalificacion(calificacion);
		
		Date fechaCalificacion = null;
		
		// Se comparan la fecha de actualización contra la de alta
		if (entity.getFecRegistroActualizado() != null) {
			if (entity.getFecRegistroActualizado().compareTo(entity.getFecRegistroAlta()) > 0){
				fechaCalificacion = entity.getFecRegistroActualizado();
			} else {
				fechaCalificacion = entity.getFecRegistroAlta();
			}
		} else {
			fechaCalificacion = entity.getFecRegistroAlta();
		}
		
		model.setFechaCalificacion(fechaCalificacion);
		
		return model;
	}
	
	@Override
	public PersonaCalificacion transformarAModelo(
			DitHistPersonaMoralCalific entity) throws TransformacionException {
		
		if (entity == null) {
			throw new TransformacionException();
		}
		
		PersonaCalificacion model = new PersonaCalificacion();
		Calificacion calificacion = new Calificacion();
		
		calificacion.setIdCalificacion(entity.getId().getCveIdCalificacion());
		calificacion.setDescripcion(CalificacionEnum.valueIntOf(
				Long.valueOf(entity.getId().getCveIdCalificacion()).intValue())
				.getDescripcion());
		
		model.setCalificacion(calificacion);
		
		Date fechaCalificacion = null;
		
		// Se comparan la fecha de actualización contra la de alta
		if (entity.getFecRegistroActualizado() != null) {
			if (entity.getFecRegistroActualizado().compareTo(entity.getFecRegistroAlta()) > 0){
				fechaCalificacion = entity.getFecRegistroActualizado();
			} else {
				fechaCalificacion = entity.getFecRegistroAlta();
			}
		} else {
			fechaCalificacion = entity.getFecRegistroAlta();
		}
		
		model.setFechaCalificacion(fechaCalificacion);
				
		return model;
	}

	@Override
	public DitHistPersonaCalificacion transformarAEntidad(
			PersonaCalificacion personaCalificacion, Fisica fisica) {
		
		DitHistPersonaCalificacion ditHistPersonaCalificacion = null;
		DitPersona ditPersona = null;
				
		if(personaCalificacion != null){
			
            DicPersonaCalificacion dicPersonaCalificacion = new DicPersonaCalificacion();
            dicPersonaCalificacion.setCveIdCalificacion(personaCalificacion.getCalificacion().getIdCalificacion());
            
            ditHistPersonaCalificacion = new DitHistPersonaCalificacion();
            ditHistPersonaCalificacion.setDicPersonaCalificacion(dicPersonaCalificacion);
            
			ditPersona = new DitPersona();
			ditPersona.setCveIdPersona(fisica.getIdPersona());
			
            ditHistPersonaCalificacion.setDitPersona(ditPersona);
            
            ditPersona.getDitHistPersonaCalificacions().add(ditHistPersonaCalificacion);
            
            DitHistPersonaCalificacionPK idCalificacion = new DitHistPersonaCalificacionPK();
    		idCalificacion.setCveIdCalificacion(ditHistPersonaCalificacion
    				.getDicPersonaCalificacion().getCveIdCalificacion());
    		idCalificacion.setCveIdPersona(ditHistPersonaCalificacion
    				.getDitPersona().getCveIdPersona());

    		ditHistPersonaCalificacion.setId(idCalificacion);
			
		}
		
		return ditHistPersonaCalificacion;
	}
	
	@Override
	public DitHistPersonaMoralCalific transformarAEntidad(
			PersonaCalificacion personaCalificacion, Moral moral) {
		
		DitHistPersonaMoralCalific ditHistPersonaMoralCalific = null;
		DitPersonaMoral ditPersonaMoral = null;
				
		if(personaCalificacion != null){
			
            DicPersonaCalificacion dicPersonaCalificacion = new DicPersonaCalificacion();
            dicPersonaCalificacion.setCveIdCalificacion(personaCalificacion.getCalificacion().getIdCalificacion());
            
            ditHistPersonaMoralCalific = new DitHistPersonaMoralCalific();
            ditHistPersonaMoralCalific.setDicPersonaCalificacion(dicPersonaCalificacion);
            
			ditPersonaMoral = new DitPersonaMoral();
			ditPersonaMoral.setCveIdPersonaMoral(moral.getCveMoral());
			
            ditHistPersonaMoralCalific.setDitPersonaMoral(ditPersonaMoral);
            
            ditPersonaMoral.getDitHistPersonaMoralCalifics().add(ditHistPersonaMoralCalific);
            
            DitHistPersonaMoralCalificPK idCalificacion = new DitHistPersonaMoralCalificPK();
    		idCalificacion.setCveIdCalificacion(ditHistPersonaMoralCalific
    				.getDicPersonaCalificacion().getCveIdCalificacion());
    		idCalificacion.setCveIdPersonaMoral(ditHistPersonaMoralCalific
    				.getDitPersonaMoral().getCveIdPersonaMoral());

    		ditHistPersonaMoralCalific.setId(idCalificacion);
			
		}
		
		return ditHistPersonaMoralCalific;
		
	}

	@Override
	public List<PersonaCalificacion> filtrarCalificacionesVigentesPersonaFisica(
			List<DitHistPersonaCalificacion> listCalificaciones)
			throws TransformacionException {
		List<PersonaCalificacion> listCalificacionesVigentes = new ArrayList<PersonaCalificacion>();

		if (listCalificaciones != null && !listCalificaciones.isEmpty()) {
			for (DitHistPersonaCalificacion ditHistPersonaCalificacion : listCalificaciones) {
				if (ditHistPersonaCalificacion != null
						&& validarCalificacionVigente(ditHistPersonaCalificacion)) {
					listCalificacionesVigentes.add(transformarAModelo(ditHistPersonaCalificacion));
				}
			}
		}

		return listCalificacionesVigentes;
	}
	
	@Override
	public List<PersonaCalificacion> filtrarCalificacionesVigentesPersonaMoral(
			List<DitHistPersonaMoralCalific> listCalificaciones)
			throws TransformacionException {
		
		List<PersonaCalificacion> listCalificacionesVigentes = new ArrayList<PersonaCalificacion>();

		if (listCalificaciones != null && !listCalificaciones.isEmpty()) {
			for (DitHistPersonaMoralCalific ditHistPersonaMoralCalific : listCalificaciones) {
				if (ditHistPersonaMoralCalific != null
						&& validarCalificacionVigente(ditHistPersonaMoralCalific)) {
					listCalificacionesVigentes.add(transformarAModelo(ditHistPersonaMoralCalific));
				}
			}
		}

		return listCalificacionesVigentes;
	}

	@Override
	public String getCalificacionesVigentesAsString(List<PersonaCalificacion> listCalificaciones) {
		final StringBuilder califStrB = new StringBuilder();

		if (listCalificaciones != null && !listCalificaciones.isEmpty()) {
			for (PersonaCalificacion personaCalificacion : listCalificaciones) {
				if (personaCalificacion.getCalificacion() != null) {
					califStrB.append(personaCalificacion.getCalificacion().getDescripcion());
					califStrB.append(", ");
				}
			}
		}

		return quitarComaFinal(califStrB.toString());
	}

	private boolean validarCalificacionVigente(DitHistPersonaCalificacion ditHistPersonaCalificacion) {
		DicPersonaCalificacion dicPersonaCalificacion = ditHistPersonaCalificacion.getDicPersonaCalificacion();
		boolean isCalificacionVigente;

		if (dicPersonaCalificacion == null) {
			isCalificacionVigente = false;
		} else if (Utilerias.isBlank(dicPersonaCalificacion.getCveIdCalificacion())) {
			isCalificacionVigente = false;
		} else {
			Integer idCalificacion = dicPersonaCalificacion.getCveIdCalificacion().intValue();

			if (idCalificacion.equals(CalificacionEnum.VALIDADO_IMSS.getCodigo())
					|| idCalificacion.equals(CalificacionEnum.VALIDADO_RENAPO.getCodigo())
					|| idCalificacion.equals(CalificacionEnum.VALIDADO_SAT.getCodigo())) {
				if (ditHistPersonaCalificacion.getFecRegistroBaja() != null) {
					isCalificacionVigente = false;
				} else {
					isCalificacionVigente = true;
				}
			} else {
				isCalificacionVigente = false;
			}
		}

		return isCalificacionVigente;
	}
	
	private boolean validarCalificacionVigente(DitHistPersonaMoralCalific ditHistPersonaMoralCalific) {
		DicPersonaCalificacion dicPersonaCalificacion = ditHistPersonaMoralCalific.getDicPersonaCalificacion();
		boolean isCalificacionVigente;

		if (dicPersonaCalificacion == null) {
			isCalificacionVigente = false;
		} else if (Utilerias.isBlank(dicPersonaCalificacion.getCveIdCalificacion())) {
			isCalificacionVigente = false;
		} else {
			Integer idCalificacion = dicPersonaCalificacion.getCveIdCalificacion().intValue();

			if (idCalificacion.equals(CalificacionEnum.VALIDADO_IMSS.getCodigo())
					|| idCalificacion.equals(CalificacionEnum.VALIDADO_SAT.getCodigo())) {
				if (ditHistPersonaMoralCalific.getFecRegistroBaja() != null) {
					isCalificacionVigente = false;
				} else {
					isCalificacionVigente = true;
				}
			} else {
				isCalificacionVigente = false;
			}
		}

		return isCalificacionVigente;
	}
	
	private String quitarComaFinal(String cadena) {
		String cadenaSinComaFinal = "";

		if (StringUtils.isNotBlank(cadena)) {
			try {
				int fin = cadena.lastIndexOf(",");
				cadenaSinComaFinal = cadena.substring(0, fin);
			} catch (Exception e) {
				cadenaSinComaFinal = "Sin calificaci\u00f3n a causa de una excepci\u00f3n: " + e.getMessage();
			}
		} else {
			cadenaSinComaFinal = "Sin calificaci\u00f3n";
		}

		return cadenaSinComaFinal;
	}
}
