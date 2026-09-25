package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Certificado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Remote
public interface AfiliacionGlobalServiceRemote {
	
	/**
	 * Finaliza la solicitud proporcionada. Este metodo considera solicitudes con uno o dos trámites:
	 * ALTA DE SRT
	 * ACTUALIZACION DE DATOS GENERALES(DENOMINACION RAZON SOCIAL)
	 * 
	 * 
	 * @param idSolcitud identificador de la solicitud en la base delta
	 * @param numeroRegistroPatronal numero de registro patronal generado por el servicio web a 11 digitos
	 * @throws GestionPatronalBusinessException
	 */
	void concluirAltaPatronal(Long idSolicitud, String numeroRegistroPatronal, String nrpAsociado) throws GestionPatronalBusinessException;
	
	
	/**
	 * Finaliza la solicitud proporcionada. Este metodo considera unicamente solicitudes que contengan los siguientes tramites:
	 * ACTUALIZACION_DENOMINACION_RAZON_SOCIAL
	 * ESCRITURA_CONSTITUTIVA
	 * REGISTRO_SINDICATO
	 * ACTUALIZACION DE DATOS DE CONTACTO
	 * ACTUALIZACION DE SOCIOS
	 * ACTUALIZACION DE REPRESENTANTES LEGALES
	 * 
	 * 
	 * @param idSolcitud
	 * @throws GestionPatronalBusinessException
	 */
	void concluirSolicitudDatosPatronales(Long idSolicitud) throws GestionPatronalBusinessException;
	
	
	/**
	 *Obtiene la información de un patrón requerida por secretaría de economia.
	 * @param numeroRegistroPatronal
	 */
	void recuperarInformacionPatronalPorNumeroDeRegistro(String numeroRegistroPatronal);
	
	/**
	 * Se concluye el trámite de alta afectando los datos del registro patronal y asociando el trámite al nuevo RP.
	 * Además se agrega el beneficio RISS por defecto al nuevo rp en caso de que tenga este beneficio vigente.
	 * Como paso final se actualiza la información del certificado digital del firmante (RepresentanteLegal o Patrón)
	 * @param tramite
	 * @param numeroRegistroPatronal
	 * @param firma
	 * @return Tramite con el nuevo NRP
	 * @throws GestionPatronalBusinessException
	 */
	Tramite concluirTramiteAltaPatronal(Tramite tramite, String numeroRegistroPatronal, 
			Certificado certificado, OrigenSolicitud origenSolicitud) throws GestionPatronalBusinessException;
	
}

