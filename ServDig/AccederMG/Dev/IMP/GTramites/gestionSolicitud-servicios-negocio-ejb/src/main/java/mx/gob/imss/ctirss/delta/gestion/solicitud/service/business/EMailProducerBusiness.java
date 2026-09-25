package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "EMailQProducer", mappedName = "EMailQProducer")
public class EMailProducerBusiness extends AbstractServiceBusiness implements EMailProducer{
	
	private static final Logger log = LoggerFactory
		.getLogger(EMailProducerBusiness.class);
	
	@Resource(name = "jms/EmailConnectionFactory" )
    private transient ConnectionFactory eMailFactory;
    @Resource(name = "jms/MsgEmailDistributedQueue")
    private transient Queue eMailQueue;

    private transient Connection conn;
    private transient Session session;
    private transient MessageProducer producer;
    
    
	
	
	
	@Override
	public void agendarCorreoElectronico(EmailPayloadType emailRequest){
		String xmlMessage = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(emailRequest);
		//log.debug("encolando xml {}", xmlMessage);
		procesarMensaje(conn, session, producer, eMailFactory, eMailQueue, xmlMessage);
		log.debug("finaliza agendarCorreoElectronico");		
	}

	@Override
	public void agendarCorreoElectronicoAcute(EmailPayloadType emailRequest){
		String xmlMessage = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(emailRequest);
		//log.debug("encolando xml {}", xmlMessage);
		xmlMessage = xmlMessage.replace("&amp;","&");
		procesarMensaje(conn, session, producer, eMailFactory, eMailQueue, xmlMessage);
		log.debug("finaliza agendarCorreoElectronico");		
	}
	
	@Override
	protected void procesamientoExtra(TextMessage message) throws JMSException{
		String mensaje = message.getText();
		mensaje = mensaje.replaceAll("&lt;", "<");
		mensaje = mensaje.replaceAll("&gt;", ">");
		message.setText(mensaje);
		log.error("Mensaje HTML Parse: "+mensaje);
	}

}
