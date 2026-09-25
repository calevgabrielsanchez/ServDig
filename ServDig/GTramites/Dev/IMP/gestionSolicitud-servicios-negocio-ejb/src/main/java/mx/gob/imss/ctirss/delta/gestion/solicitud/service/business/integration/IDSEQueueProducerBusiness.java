package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.IDSEQueueProducerRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "idseQProducerBusiness", mappedName = "idseQProducerBusiness")
public class IDSEQueueProducerBusiness 
	extends AbstractServiceBusiness implements IDSEQueueProducerRemote{
	
	private static final Logger log = LoggerFactory
		.getLogger(IDSEQueueProducerBusiness.class);
	
	@Resource(name = "jms/SolicitudConnectionFactory" )
    private transient ConnectionFactory factory;
    @Resource(name = "jms/IDSEComplianceDistributedQueue")
    private transient Queue colaIDSE;    
    private transient Connection conn;
    private transient Session session;
    private transient MessageProducer producer;
    
	@Override
	public void encolarMensajeIDSE(String folioSolicitud) {
			StringBuffer msgIdse = new StringBuffer();			
			msgIdse.append("<mx:JMSProducer_Input xmlns:mx='http://mx.gob.imss.delta.global.services/'>").append("\n");
				msgIdse.append("		<mx:FolioSolicitud>").append(folioSolicitud).append("</mx:FolioSolicitud>").append("\n");
			msgIdse.append("</mx:JMSProducer_Input>").append("\n");
			log.debug("encolando xml {}", msgIdse.toString());
			procesarMensaje(conn, session, producer, factory, colaIDSE, msgIdse.toString());
			log.debug("finaliza encolarMensajeIDSE");
			
	}

}
