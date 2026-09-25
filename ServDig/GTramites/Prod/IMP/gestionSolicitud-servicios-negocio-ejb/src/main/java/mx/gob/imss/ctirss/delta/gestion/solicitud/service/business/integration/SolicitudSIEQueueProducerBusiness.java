package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudSIEQueueProducerRemote;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.global.SolicitudProducerType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "solicitudSIEQProducerBusiness", mappedName = "solicitudSIEQProducerBusiness")
public class SolicitudSIEQueueProducerBusiness extends AbstractServiceBusiness 
implements SolicitudSIEQueueProducerRemote {
	
	private static final Logger log = LoggerFactory
		.getLogger(SolicitudSIEQueueProducerBusiness.class);
	
	@Resource(name = "jms/EstudiantesConnectionFactory" )
    private transient ConnectionFactory factory;
    @Resource(name = "jms/EstudiantesDistributedQueue")
    private transient Queue colaSolicitudes;
    private transient Connection conn;
    private transient Session session;
    private transient MessageProducer producer;
    
    
	
	
	
	@Override
	public void encolarSolicitudAConcluir(String folioSolicitud) {
		SolicitudProducerType messageType = new SolicitudProducerType();
		messageType.setFolioSolicitud(folioSolicitud);
		String xmlMessage = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(messageType);
				
		log.debug("encolando xml {}", xmlMessage);
		procesarMensaje(conn, session, producer, factory, colaSolicitudes, xmlMessage);
		log.debug("finaliza SIE encolarSolicitudAConcluir");
	}
}
