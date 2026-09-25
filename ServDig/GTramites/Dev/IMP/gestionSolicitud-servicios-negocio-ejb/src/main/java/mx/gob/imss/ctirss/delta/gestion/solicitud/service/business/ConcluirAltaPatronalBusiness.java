package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.SolicitudEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ConcluirAltaPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name="concluirAltaPatronalBusiness",  mappedName="concluirAltaPatronalBusiness")
public class ConcluirAltaPatronalBusiness implements ConcluirAltaPatronalBusinessRemote {

    private static final Logger log = LoggerFactory.getLogger(ConcluirAltaPatronalBusiness.class);

    @EJB
    private SolicitudEntityLocal solicitudEntity;
    @EJB
    private AltaPatronalHelperLocal altaPatronalHelper;
    @EJB
    private MovimientoPatronalBusinessRemote movimientoPatronalBusiness;

    @Override
    public void concluirAltaPatronal(String registroPatronal, Long idSolicitud) {
        log.info("concluirAltaPatronal registro patronal: {} idSolicitud: {}", registroPatronal, idSolicitud);
        try {
            Solicitud solicitud = new Solicitud();
            solicitud.setSolicitudId(idSolicitud);
            solicitud = solicitudEntity.consultar(solicitud,true);
            concluirAltaPatronal(registroPatronal, solicitud);
        } catch(SolicitudNoEncontradaException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

	@Override
	public void concluirAltaPatronal(String registroPatronal,
			Solicitud solicitud) {
		this.log.debug(" ########## concluirAltaPatronal ......");
		MovimientoPatronalType movimientoPatronalType = altaPatronalHelper.createMovimientoPatronalAlta(solicitud);
        enviarMovimientoPatronal(registroPatronal, movimientoPatronalType);
	}
	
	@Override
	public void reportarMovimientoAltaPatronal(String registroPatronal,
			SujetoObligado sujetoTramite) {
		this.log.debug(" ########## reportarAltaPatronal ......");
		MovimientoPatronalType movimientoPatronalType = altaPatronalHelper.creaMovimiento(sujetoTramite, null);
        enviarMovimientoPatronal(registroPatronal, movimientoPatronalType);
	}
	
	
	private void enviarMovimientoPatronal(String registroPatronal, MovimientoPatronalType movimientoPatronalType){
		int digito = Integer.parseInt(registroPatronal.substring(registroPatronal.length() - 1));
        String numeroRP = registroPatronal.substring(0, registroPatronal.length() - 1);
        movimientoPatronalType.setDigitoVerificador(digito);
        movimientoPatronalType.setRegistroPatronal(numeroRP);
        movimientoPatronalBusiness.enviarModificacionPatronal(movimientoPatronalType);
	}
	
}
