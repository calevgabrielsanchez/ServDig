package mx.gob.imss.ctirss.delta.derechohabientes.web.utils;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

import org.apache.commons.lang.StringUtils;

public class TramiteUtil {
	
	/**
	 * Metodo para obtener el tramite de registro a partir de una solicitud
	 * @param solicitud
	 * @return
	 */
	public static TramiteRegistroDerechohabiente getTramiteRegistroFromSolicitud(Solicitud solicitud) {
		TramiteRegistroDerechohabiente registro = null;
		
		if(solicitud != null && solicitud.getTramites() != null) {
			for(Tramite tramite: solicitud.getTramites()) {
				if(tramite instanceof TramiteRegistroDerechohabiente) {
					registro = (TramiteRegistroDerechohabiente) tramite;
					break;
				}
			}
		}
		
		return registro;
	}
	
	/**
	 * Metodo para saber si es asegurado
	 * @param afectado
	 * @return
	 */
	public static Boolean isPadre(GrupoFamiliar afectado) {
		if(afectado != null) {
			Long idParentesco = afectado.getParentesco().getIdParentesco();
			
			if(idParentesco.equals(ParentescoEnum.PADRES.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Metodo para saber si es asegurado
	 * @param afectado
	 * @return
	 */
	public static Boolean isAsegurado(GrupoFamiliar afectado) {
		if(afectado != null) {
			Long idParentesco = afectado.getParentesco().getIdParentesco();
			
			if(idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Metodo para saber si es conyuge
	 * @param afectado
	 * @return
	 */
	public static Boolean isConyuge(GrupoFamiliar afectado) {
		if(afectado != null) {
			Long idParentesco = afectado.getParentesco().getIdParentesco();
			
			if(idParentesco.equals(ParentescoEnum.CONYUGE.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Boolean permiteCambioMedico(GrupoFamiliar afectado) {
		
		return isConyuge(afectado) || isAsegurado(afectado);
	}

	public static TramiteCorreccionDerechohabiente convetirGrupoCorreccion(GrupoFamiliar integrante) {
		TramiteCorreccionDerechohabiente correccion = new TramiteCorreccionDerechohabiente();
		
		correccion.setIdPersona(integrante.getDerechohabiente().getIdPersona());
		correccion.setNombre(integrante.getDerechohabiente().getNombre());
		correccion.setPrimerApellido(integrante.getDerechohabiente().getPrimerApellido());
		correccion.setSegundoApellido(integrante.getDerechohabiente().getSegundoApellido());
		correccion.setCurpCap(integrante.getDerechohabiente().getCurp());
		if(integrante.getDerechohabiente().getSexo().getIdSexo().equals(SexoEnum.MUJER.getId()) && integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PADRES.getId())){
			correccion.setSexo(new Sexo());
			correccion.getSexo().setIdSexo(Integer.parseInt(""+ParentescoEnum.MADRE.getId()));
		} else {
			correccion.setSexo(integrante.getDerechohabiente().getSexo());
		}
		correccion.setFechaNacimiento(integrante.getDerechohabiente().getFechaNacimiento());
		correccion.setLugarNacimiento(integrante.getDerechohabiente().getLugarNacimiento());
		correccion.setParentesco(integrante.getParentesco());
		correccion.setCalidad(integrante.getCalidad().toString());
		correccion.setNss(integrante.getAsignacionNSS().getNssStr());
		correccion.setDomicilio(integrante.getDomicilio());
		correccion.setMedicoEnTurno(integrante.getMedicoEnTurno());
		correccion.setEstadoCivil(integrante.getDerechohabiente().getEstadoCivil());
		correccion.setCorreoElectronico(integrante.getDerechohabiente().getCorreoElectronico());
		correccion.setFacebook(integrante.getDerechohabiente().getFacebook());
		correccion.setTwitter(integrante.getDerechohabiente().getTwitter());
		correccion.setTelefonoFijo(integrante.getDerechohabiente().getTelefonoFijo());
		correccion.setTelefonoMovil(integrante.getDerechohabiente().getTelefonoMovil());
		correccion.setMesRegistroNac(integrante.getDerechohabiente().getMesRegistroNac());
		correccion.setAnioRegistroNac(integrante.getDerechohabiente().getAnioRegistroNac());
		
		if(integrante.getMedicoEnTurno() != null && integrante.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
			correccion.setIdUmfOrigen(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
		}
		
		if( integrante.getEstadoDerechohabiente() != null )
			correccion.setIdEstadoDerechohabiente(integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
		
		correccion.setParentescoActual(integrante.getParentesco());
		correccion.setIndicadorRN(integrante.getIndRecienNacido());
		
		
		return correccion;
	}
	
	/**
	 * MEtodo para obtener al integrante de un grupo familiar con la mayor calidad
	 * donde la mayor es la calidad 1
	 * @param integrantes
	 * @return GrupoFamiliar - Integrante con la mayor calidad
	 */
	public static  GrupoFamiliar getMayorCalidad(List<GrupoFamiliar> integrantes) {
		GrupoFamiliar mayor = null;
		if(!integrantes.isEmpty()) {
			mayor = integrantes.get(0);
			for(GrupoFamiliar integrante: integrantes) {
				if(integrante.getCalidad().intValue() < mayor.getCalidad().intValue()) {
					mayor = integrante;
				}
			}
		}
		return mayor;
	}
	
	public static TramiteCorreccionDerechohabiente obtenerCorreccion(Solicitud solicitud){
		
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteCorreccionDerechohabiente) {
				return (TramiteCorreccionDerechohabiente) tramite;
			}
		}
		return null;
	}
	
	public static TramiteCircunscripcionForanea obtenerCircunscripcion(Solicitud solicitud){

		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteCircunscripcionForanea) {
				return (TramiteCircunscripcionForanea) tramite;
			}
		}
		return null;
	}
	
	/**
	 * Lista los tipos de documentos que no pediran para concluir el trámite
	 * 
	 * @param correccion
	 * @return
	 */
	public static String quitarTiposDocumentos(Fisica fisica ){
		
		String idTipoTramites = "";
		if(fisica != null) {
			//se valida que se tenga la fecha de nacimiento
			if(fisica.getFechaNacimiento() != null || (fisica.getAnioRegistroNac() != null || fisica.getMesRegistroNac() != null)) {
				idTipoTramites = TramiteUtil.quitarTipoDocumentosMenoresEdadad(fisica.getFechaNacimiento());
			}
			
			// ------------------------------------------------------------------
			// Si se consulto la CURP y ya tenia acta, no se le piden
			// ------------------------------------------------------------------
			if(!StringUtils.isEmpty(fisica.getCurp())){
				idTipoTramites+=","+String.valueOf(TipoDocumentoProbatorioEnum.ACTAS.getId());
			}
		}
		
		return idTipoTramites;
	}
	/**
	 * Si el beneficiario es menor de edad se elimina la identificaci&oacute;n de los
	 * documentos probatorios
	 * 
	 * @param fechaNacimiento Date Fecha de nacimiento del beneficiario
	 * @return
	 */
	public static String quitarTipoDocumentosMenoresEdadad(Date fechaNacimiento){
		
		String tiposDocumentos = "";
		
		if( fechaNacimiento != null && DateUtils.getEdad(fechaNacimiento) < 18 ){
			return String.valueOf(TipoDocumentoProbatorioEnum.IDENTIFICACION.getId());
		}
		
		return tiposDocumentos;
	}
	
	/**
	 * Metodo para obtener un tramite de correccion por tipo
	 * @param solicitud
	 * @param idTipoTramite
	 * @return
	 */
	public static TramiteCorreccionDerechohabiente getTramiteCorreccionPorTipo(Solicitud solicitud, Long idTipoTramite) {
		TramiteCorreccionDerechohabiente correccion = null;
		
		for(Tramite tramite: solicitud.getTramites()) {
			if(idTipoTramite != null) {
				if(tramite.getTipoTramite().getIdTipoTramite().equals(idTipoTramite.intValue())) {
					correccion = (TramiteCorreccionDerechohabiente) tramite;
					break;
				}
			} else {
				if(tramite instanceof TramiteCorreccionDerechohabiente) {
					correccion = (TramiteCorreccionDerechohabiente) tramite;
					break;
				}
			}
		}
		return correccion;
	}
}
