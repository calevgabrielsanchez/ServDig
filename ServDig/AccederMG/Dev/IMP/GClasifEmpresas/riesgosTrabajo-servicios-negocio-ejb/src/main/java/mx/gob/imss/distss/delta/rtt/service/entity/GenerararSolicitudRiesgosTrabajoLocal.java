package mx.gob.imss.distss.delta.rtt.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;

@Local
public interface GenerararSolicitudRiesgosTrabajoLocal {
    
    String ERROR_SOLICITUD="Lo sentimos ha ocurrido un error inesperado, intente m&aacute;s tarde.";

    /**
     * Crea una Solicitud de riesgos de trabajo
     *
     * @param patron
     * @param listaRiesgosT
     * @return
     * @throws RiesgosTrabajoException
     */
    Solicitud crearSolicitudRiesgosTrabajoPatronales(PatronRiesgosTrabajo patron,  List<RiesgoTrabajo> listaRiesgosT, OrigenSolicitudEnum origenSolicitud, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException;

    /**
     * Genera firma digital
     *
     * @param riesgosTrabajoList
     * @param solicitud
     * @param patron
     * @return
     * @throws RiesgosTrabajoException
     */
    FirmaElectronica obtenerDatosSellado(List<RiesgoTrabajo> riesgosTrabajoList, Solicitud solicitud,
             PatronRiesgosTrabajo patron, String rfc) throws RiesgosTrabajoException;

    /**
     * 
     * @param patron
     * @return
     * @throws RiesgosTrabajoException
     */
	Solicitud crearSolicitudTerminosCondiciones(PatronRiesgosTrabajo patron, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException;

	/**
	 *
	 * @param rfc
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	Solicitud crearSolicitudTerminosCondicionesRfc(PatronRiesgosTrabajo patron, String rfc, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException;

	/**
	 * 
	 * @param cadenaOriginal
	 * @param solicitud
	 * @param patron
	 * @return
	 * @throws RiesgosTrabajoException
	 */
	FirmaElectronica obtenerFirmaElectronica(String cadenaOriginal, Solicitud solicitud, PatronRiesgosTrabajo patron)
			throws RiesgosTrabajoException;

	Solicitud crearSolicitudRTRFC(PatronRiesgosTrabajo patron,  List<RiesgoTrabajo> listaRiesgosT, OrigenSolicitudEnum origenSolicitud, String rfc, TipoDescargaArchivo tipoArchivo) throws RiesgosTrabajoException;

}
