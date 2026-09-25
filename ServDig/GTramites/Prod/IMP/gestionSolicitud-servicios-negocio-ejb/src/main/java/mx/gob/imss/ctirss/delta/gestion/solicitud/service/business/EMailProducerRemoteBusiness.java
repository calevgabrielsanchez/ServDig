package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducerRemote;
import mx.gob.imss.ctirss.delta.model.email.EmailPayloadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;

@Stateless(name = "eMailProducerRemoteBusiness", mappedName = "eMailProducerRemoteBusiness")
public class EMailProducerRemoteBusiness extends AbstractServiceBusiness implements EMailProducerRemote{

	private static final Logger log = LoggerFactory
		.getLogger(EMailProducerRemoteBusiness.class);

	@Resource(name = "jms/EmailConnectionFactory" )
    private transient ConnectionFactory eMailFactory;
    @Resource(name = "jms/MsgEmailDistributedQueue")
    private transient Queue eMailQueue;

    private transient Connection conn;
    private transient Session session;
    private transient MessageProducer producer;


	@Override
	public boolean registrarCorreoElectronico(EmailPayloadType emailRequest){

		String parameters = emailRequest.getParameters();


        mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType request = new mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType();

        copyEmailPayloadType(request,emailRequest);


		String xmlMessage = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(request);
		//log.debug("encolando xml {}", xmlMessage);
		procesarMensaje(conn, session, producer, eMailFactory, eMailQueue, xmlMessage);
		log.debug("finaliza agendarCorreoElectronico");
		return true;
	}

	@Override
	protected void procesamientoExtra(TextMessage message) throws JMSException{
		String mensaje = message.getText();
		mensaje = mensaje.replaceAll("&lt;", "<");
		mensaje = mensaje.replaceAll("&gt;", ">");
		message.setText(mensaje);
		log.error("Mensaje HTML Parse: "+mensaje);
	}

	private void copyEmailPayloadType(mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType receptor, EmailPayloadType emisor){

	    log.info("Se procede a copiar los parametros del objeto emisor al objero original");

	    receptor.setBcc(emisor.getBcc());
//	    receptor.setAttachment(emisor.getAttachment());
//	    receptor.setAttachments(emisor.getAttachments());
	    receptor.setCc(emisor.getCc());
	    receptor.setContent(emisor.getContent());
	    receptor.setContentType(emisor.getContentType());
	    receptor.setFromAccountName(emisor.getFromAccountName());
	    receptor.setReplyToAddress(emisor.getReplyToAddress());
	    receptor.setSubject(emisor.getSubject());
	    receptor.setTo(emisor.getTo());

	    Map<String,String> parameters = new HashMap<String, String>();
	    String parametros = emisor.getParameters();

	    String[] parametrosEmisor = parametros.split("#@#");

	    if(parametrosEmisor != null && parametrosEmisor.length>=2){
	        for(int i=0; i<parametrosEmisor.length;i=i+2){
	            parameters.put(parametrosEmisor[i],parametrosEmisor[i+1]);
            }
        }

        receptor.setParameters(parameters);

    }
}
