package mx.gob.imss.ctirss.delta.derechohabientes.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAcuerdoDh;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteReactivacionDerechohab;

public class TramiteUtil {

	public static final String KEY_TRAMITE = "tramite";
	public static final String KEY_AFECTADOS = "afectados";
	public static String KEY_INTEGRANTES_EN_UMF = "keyIntegrantesEnUmfDestino";
	public static String KEY_EXISTE_CAMBIO_MEDICO = "keyExisteCambioMedicoEnUmfDestino";
	public static String KEY_FECHA_CAMBIO = "keyFechaCambioMedicoTurno";
	public static String KEY_SOLICITUD = "solicitud";
	
	public static TramiteBajaDerechohabiente getTramiteBajaFromSolicitud(Solicitud solicitud) {
		TramiteBajaDerechohabiente tramiteBaja = null;
		
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteBajaDerechohabiente) {
				tramiteBaja = (TramiteBajaDerechohabiente) tramite;
				break;
			}
		}
		
		return tramiteBaja;
	}
	
	public static TramiteAcuerdoDh getTramiteAcuerdoFromSolicitud(Solicitud solicitud) {
		TramiteAcuerdoDh tramiteAcuerdo = null;
		
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteAcuerdoDh) {
				tramiteAcuerdo = (TramiteAcuerdoDh) tramite;
				break;
			}
		}
		
		return tramiteAcuerdo;
	}
	
	public static TramiteReactivacionDerechohab getTramiteReactivacionFromSolicitud(Solicitud solicitud) {
		TramiteReactivacionDerechohab tramiteR = null;
		
		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteReactivacionDerechohab) {
				tramiteR = (TramiteReactivacionDerechohab) tramite;
				break;
			}
		}
		
		return tramiteR;
	}
	
	public static Solicitud getSolicitudFromMap(Map<String, Object> result) {
		return (Solicitud) result.get(KEY_SOLICITUD);
	}
	
	public static TramiteCorreccionDerechohabiente getTramiteCorreccionFromMap(Map<String, Object> result) {
		return (TramiteCorreccionDerechohabiente) result.get(KEY_TRAMITE);
	}
	
	@SuppressWarnings("unchecked")
	public static List<GrupoFamiliar> getAfectadosFromMap(Map<String, Object> result) {
		return (List<GrupoFamiliar>) result.get(KEY_AFECTADOS);
	}
	/**
	 * Metodo para obtener tramite de tipo correccion de la solicitud
	 * @param solicitud
	 * @return
	 */
	public static TramiteCorreccionDerechohabiente getTramiteCorreccionFromSolicitud(Solicitud solicitud){
		TramiteCorreccionDerechohabiente correccion = null;
		
		for(Tramite tramite: solicitud.getTramites()) {
			correccion = (TramiteCorreccionDerechohabiente) tramite;
		}
		return correccion;
	}
	
	public static TramiteCircunscripcionForanea getCircunscrcipcionFromSolicitud(Solicitud solicitud) {
		TramiteCircunscripcionForanea circunscrcipcion = null;
		
		if(solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
			for(Tramite tramite: solicitud.getTramites()) {
				if(tramite instanceof TramiteCircunscripcionForanea) {
					circunscrcipcion = (TramiteCircunscripcionForanea) tramite;
					break;
				}
			}
		}
			
		return circunscrcipcion;
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
			if(tramite.getTipoTramite().getIdTipoTramite().equals(idTipoTramite.intValue())) {
				correccion = (TramiteCorreccionDerechohabiente) tramite;
			}
		}
		return correccion;
	}
	
	public static TramiteCorreccionDerechohabiente copiarCorreccion(
			TramiteCorreccionDerechohabiente entrada) {
		TramiteCorreccionDerechohabiente salida = new TramiteCorreccionDerechohabiente();

		salida.setCalidad(entrada.getCalidad());
		salida.setCandidatosCambioClinica(entrada.getCandidatosCambioClinica());
		salida.setCorreoElectronico(entrada.getCorreoElectronico());
		salida.setCurpCap(entrada.getCurpCap());
		salida.setDetalleTramiteXml(entrada.getDetalleTramiteXml());
		salida.setDomicilio(entrada.getDomicilio());
		salida.setEnUmfDestino(entrada.getEnUmfDestino());
		salida.setEstadoCivil(entrada.getEstadoCivil());
		salida.setFacebook(entrada.getFacebook());
		salida.setFechaNacimiento(entrada.getFechaNacimiento());
		salida.setFechaNacimientoStr(entrada.getFechaNacimientoStr());
		salida.setIdPersona(entrada.getIdPersona());
		salida.setIdUmfOrigen(entrada.getIdUmfOrigen());
		salida.setLugarNacimiento(entrada.getLugarNacimiento());
		salida.setMedicoEnTurno(entrada.getMedicoEnTurno());
		salida.setNombre(entrada.getNombre());
		salida.setNss(entrada.getNss());
		salida.setParentesco(entrada.getParentesco());
		salida.setPersona(entrada.getPersona());
		salida.setPersonas(entrada.getPersonas());
		salida.setPrimerApellido(entrada.getPrimerApellido());
		salida.setSegundoApellido(entrada.getSegundoApellido());
		salida.setSexo(entrada.getSexo());
		salida.setTelefonoFijo(entrada.getTelefonoFijo());
		salida.setTelefonoMovil(entrada.getTelefonoMovil());
		salida.setTipoTramite(entrada.getTipoTramite());
		salida.setTwitter(entrada.getTwitter());
		salida.setPersona(entrada.getPersona());
		salida.setFechaCambioMedico(entrada.getFechaCambioMedico());
		
		return salida;
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
	
	public static Boolean isAseguradoPensionado(Long idParentesco) {
		
		if(idParentesco != null) {
			return idParentesco.equals(ParentescoEnum.PENSIONADO.getId()) || idParentesco.equals(ParentescoEnum.ASEGURADO.getId());
		}
		
		return false;
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
	public static Boolean isConcubina_rio(GrupoFamiliar afectado) {
		if(afectado != null) {
			Long idParentesco = afectado.getParentesco().getIdParentesco();
			
			if(idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId())) {
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
	public static Boolean isHijo(GrupoFamiliar afectado) {
		if(afectado != null) {
			Long idParentesco = afectado.getParentesco().getIdParentesco();
			
			if(idParentesco.equals(ParentescoEnum.HIJOS.getId())) {
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
	public static Boolean isPersonaEnUnionCivil(GrupoFamiliar afectado) {
		if(afectado != null) {
			Long idParentesco = afectado.getParentesco().getIdParentesco();
			
			if(idParentesco.equals(ParentescoEnum.PERSONA_EN_UNION_CIVIL.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static List<Long> getTiposBaja() {
		List<Long> tiposBaja = new ArrayList<Long>();
		tiposBaja.add(TipoBajaDerechohabienteEnum.DEFUNCION.getId());
		tiposBaja.add(TipoBajaDerechohabienteEnum.DIVORCIO.getId());
		tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId());
		tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId());
		tiposBaja.add(TipoBajaDerechohabienteEnum.ADMINISTRATIVA.getId());
		tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_DE_UNION_CIVIL.getId());
		
		return tiposBaja;
	}
	/**
	 * MEtodo para obtener al integrante de un grupo familiar con la mayor
	 * calidad donde la mayor es la calidad 1
	 * 
	 * @param integrantes
	 * @return GrupoFamiliar - Integrante con la mayor calidad
	 */
	public static GrupoFamiliar getIntegranteConMayorCalidad(List<GrupoFamiliar> integrantes) {
		GrupoFamiliar mayor = null;
		if (!integrantes.isEmpty()) {
			mayor = integrantes.get(0);
			for (GrupoFamiliar integrante : integrantes) {
				if (integrante.getCalidad().intValue() < mayor.getCalidad().intValue()) {
					mayor = integrante;
				}
			}
		}
		return mayor;
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
	
	public static Boolean isConyuge(Long idParentesco) {
		
		if(idParentesco != null) {
			return idParentesco.equals(ParentescoEnum.CONYUGE.getId());
		}
		
		return false;
	}
	
	public static Long getIdUmfFromCorreccion(TramiteCorreccionDerechohabiente correccion) {
		Long idUmf = null;
		
		if(correccion!= null) {
			idUmf = TramiteUtil.getIdUmfFromMedicoEnTurno(correccion.getMedicoEnTurno());
		}
		
		return idUmf;
	}
	
	/**
	 * 
	 * @param medico
	 * @return
	 */
	public static Long getIdUmfFromMedicoEnTurno(MedicoEnTurno medico) {
		Long idUmf = null;
		
		if(medico != null) {
			UnidadMedicaFamiliar umf = medico.getUnidadMedicaFamiliar();
			if(umf != null && umf.getIdUMF() != null) {
				idUmf = umf.getIdUMF();
			}
		}
		
		return idUmf;
	}
	
	
	/**
	 * Se calculan los dias que han pasado desde la fecha de cambio de medico a la fecha de hoy
	 * @param fecha
	 * @return
	 */
	public static Long diasEntreFechayHoy(Date fecha) {

		if (fecha == null)
			return null;

		long tiempo1 = new Date().getTime();

		long tiempo2 = fecha.getTime();
		Long diasTranscrurridos = (tiempo1 - tiempo2) / (24 * 60 * 60 * 1000);
		return diasTranscrurridos;
	}
	
	/**
	 * Metodo para que a partir de una lista d eintegrantes nos regrese la primera fecha de cambio de
	 * medico y turno que no se encuentre nula
	 * @param entrada
	 * @return
	 */
	public static Date buscarFechaCambioMedicoEnLista(List<GrupoFamiliar> entrada) {

		for (GrupoFamiliar integrante : entrada) {
			if (integrante.getFechaCambioTurnoMedico() != null)
				return integrante.getFechaCambioTurnoMedico();
		}
		return null;
	}
}
