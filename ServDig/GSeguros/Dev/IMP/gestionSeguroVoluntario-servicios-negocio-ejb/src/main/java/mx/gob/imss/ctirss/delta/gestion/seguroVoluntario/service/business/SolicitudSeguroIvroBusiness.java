package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SeguroIvroServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SolicitudSeguroIvroRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroSolicitudUtil;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Servicio para obtenr una solicitud ivro a partir de su folio, obteneiendo el
 * detalle del tramite
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "solicitudSeguroIvroBusiness", mappedName = "solicitudSeguroIvroBusiness")
public class SolicitudSeguroIvroBusiness implements SolicitudSeguroIvroRemote {

    /**
     * Servicio de solicitudes
     */
    @EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;
    
    
    @EJB
    private SeguroIvroServiceLocal seguroEntity;
    
    /**
     * Obtiene la solicitud de un tramite de seguro ivro
     * @param folio numero de folio de la solicitud
     * @return Lo solicitud asociada a su folio
     * @throws SolicitudNoEncontradaException Error si el numero de folio no es encontrado en la bd
     */
    @Override
    public Solicitud consultarSolicitudSeguroPorFolio(String folio)
            throws SolicitudNoEncontradaException {
        mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitudPortal = 
                new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
        solicitudPortal.setNoFolioSolicitud(folio);
        solicitudPortal = solicitudBusiness.consultarFolio(solicitudPortal);
        Solicitud solicitudImssDigitalModelo = IvroSolicitudUtil
                .transformarSolicitudDeltaASolicitud(solicitudPortal);

        return solicitudImssDigitalModelo;
    }

	@Override
	public boolean existeRechazo(Long idPersona) {
		return seguroEntity.existeRechazo(idPersona, ModalidadEnum.TREINTAYCUATRO.getId());
	}

    @Override
    public Solicitud consultarSolicitudPorSeguro(SeguroIvro seguro) throws SolicitudNoEncontradaException {
        mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitudDelta = null;
        solicitudDelta = solicitudBusiness.consultarPorIdTramite(seguro.getTramite().getTramiteId());
        return IvroSolicitudUtil.transformarSolicitudDeltaASolicitud(solicitudDelta);
    }

}
