/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;

import org.apache.commons.collections.MapUtils;

/**
 * 130912
 * @author Samuel Rodríguez Grajeda
 *
 */
@Stateless
public class CompararPersonaMoralEntidadExternaUtility extends AbstractServiceUtility implements CompararPersonaMoralEntidadExternaUtilityLocal{

	@EJB
	private CompararPersonasServiceUtilityLocal compararPersonasServiceUtility;
	
	@Override
	public Moral compararPersonaMoralConSAT(Moral candidato, Moral entidad, Moral entrada) throws ErrorComparacionDatosSATException {
		
		// 1. Comparamos los elementos del nombre, primer apellido y segundo apellido.
		Boolean registroCoincide = this.comparaNombreDePersonaMoral(entidad, entrada);
		if(registroCoincide){
			candidato.setRfc(entidad.getRfc());
			candidato.setRazonSocial(entidad.getRazonSocial());
			candidato.setFechaCreacion(entidad.getFechaCreacion());
			candidato.setTipoSociedad(entidad.getTipoSociedad());
			
			//TODO verificar si se debe dejar el Acta tecleada por el usuario
			candidato.setActaConstitutiva(entrada.getActaConstitutiva());
			//2. Agregamos la calificacion de SAT.
			Calificacion calificacion = new Calificacion();
			calificacion.setIdCalificacion(new Long(CalificacionPersona.VALIDADO_SAT));
			calificacion.setDescripcion(CalificacionPersona.CALIFICACION_2_VALIDADO_SAT);

			PersonaCalificacion calificacionSat = new PersonaCalificacion();
			calificacionSat.setCalificacion(calificacion);
			calificacionSat.setPersona(candidato);

			candidato.getPersonaCalificaciones().add(calificacionSat);
			
		}else{
			throw new ErrorComparacionDatosSATException();
		}
		
		return candidato;
		
	}

	
	/**
	 * Compara la razon social entre los datos de entrada y los del SAT
	 * @param entidad Datos del SAT
	 * @param entrada Datos de la persona moral
	 * @return TRUE en caso de que coincidan o FALSE si no coinciden.
	 */
	private Boolean comparaNombreDePersonaMoral(Moral entidad, Moral entrada) {
		Boolean modificarRegistro = Boolean.FALSE;

		// 1. Comparamos los nombres de la persona.
		int c1 = entidad.getRazonSocial().compareToIgnoreCase(entrada.getRazonSocial());

		// Se suman los valores de las comparaciones, si la suma es 0 es que todos los registros coinciden.
		int cf = c1;

		if (cf != 0) {
			modificarRegistro = Boolean.FALSE;
		} else {
			modificarRegistro = Boolean.TRUE;
		}

		return modificarRegistro;
	}
	
	/**
	 * Contiene la lógica del caso de uso DST - 10 Comparar Persona
	 * 
	 * @param moral - entidad base
	 * @param entidad - entidad externa
	 * @param mensajes - mapara para regresar los mensajes que resulten de la comparación
	 * 
	 */
	public ICADatosRespuesta compararDosPersonasMorales(
			Moral moral, Moral entidad, Map<String, String> mensajes) {
		
		int cambiosSat = 0;
		
		ICADatosRespuesta icaDatosRespuesta = new ICADatosRespuesta();
		
		Map<String, CambioComparacionEnum> diferencias = new TreeMap<String, CambioComparacionEnum>();
		
		Map<String, Object> respuesta = this.compararPersonasServiceUtility.compararDatosBasicosSatPersonaFisica(moral, entidad, diferencias);
		cambiosSat += (Integer) respuesta.get("COUNT_DIFF");
		
		//se cambia la validacion para comparar el nomber o razon social completo y mandar un mensaje que recive el controler
		/*TODO se comenta la validacion de nombre o razon social para que no mande el mensaje a la capa de presentacion y permita el ICA a peticion del usaurio
		 * JMLL 02/01/2018 solo se evaluara el RFC este antes no se evaluaba y permitia hacer ICA
		if (diferencias.get("tipoSociedadNullSat") != null || 
				diferencias.get("nombreRazonSocial").getId().longValue() != CambioComparacionEnum.NINGUNO.getId().longValue()) {
			    mensajes.put("MSG_DIF_NOM_RAZON_SOCIAL_SOCIEDAD", "Existen diferencias entre en el nombre/razón social o tipo de sociedad entre IMSS y SAT");
		}
		*/
		
		if (diferencias.get("rfc").getId().longValue() !=  CambioComparacionEnum.NINGUNO.getId().longValue()){
			  mensajes.put("MSG_DIF_RFC", "Existen diferencias entre el RFC entre IMSS y SAT");
		}
		
		
		
		cambiosSat += this.compararPersonasServiceUtility.compararDomicilioFiscal(moral, entidad, diferencias);
		
		cambiosSat += this.compararPersonasServiceUtility.compararMediosFiscales(moral, entidad, diferencias);
				
		/*
		 * Se compara la situación SAT - De ambas entidades se obtiene la
		 * posición 0 de la lista, ya que: el SAT sólo devuelve una situación; y
		 * en el IMSS se debe de tener sólo una situación SAT activa.
		 */
		SituacionSAT situacionSatImss = moral.getSituacionesSAT() == null ? null : moral.getSituacionesSAT().get(0);
		SituacionSAT situacionSatEntidad = entidad.getSituacionesSAT() == null ? null : entidad.getSituacionesSAT().get(0);
		cambiosSat += this.compararPersonasServiceUtility.compararSituacionesSAT(situacionSatImss, situacionSatEntidad, diferencias);
				
		if (cambiosSat > 0) {
			mensajes.put("MSG02-SAT", "Existen diferencias en datos de SAT");
		}
		
		icaDatosRespuesta.setCambios(diferencias);
		icaDatosRespuesta.setTraza(mensajes);
		
		this.log.debug("Las diferencias encontradas son: ");
		MapUtils.verbosePrint(System.out, "DIFERENCIAS", diferencias);
		this.log.debug("Las mensajes generados por la comparacion de personas morales son: " + mensajes);
		
		return icaDatosRespuesta;
	}
}
