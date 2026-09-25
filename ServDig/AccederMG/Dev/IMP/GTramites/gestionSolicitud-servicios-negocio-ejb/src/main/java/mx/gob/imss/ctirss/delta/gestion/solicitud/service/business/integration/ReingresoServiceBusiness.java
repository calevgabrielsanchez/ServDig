package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ReingresoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.reingreso.MovimientoReingresoType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name="reingresoServiceBusiness", mappedName="reingresoServiceBusiness")
public class ReingresoServiceBusiness extends AbstractServiceBusiness 
implements ReingresoServiceBusinessRemote {

    private static final Logger log = LoggerFactory
    	.getLogger(ReingresoServiceBusiness.class);

    @Resource(name = "jms/GeneralConnectionFactory")
    private transient ConnectionFactory factory;
    @Resource(name = "jms/ReingresoNSScanaseDistributedQueue")
    private transient Queue queueReingreso;
    private transient Connection connection;
    private transient Session session;
    private transient MessageProducer producer;

    
    
    public void encolarMovimientoReingreso(MovimientoReingresoType movimientoReingresoType) {
    	String resultXML = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(movimientoReingresoType);
        log.debug("encolando xml {}", resultXML);
		procesarMensaje(connection, session, producer, factory, queueReingreso, resultXML);
		log.debug("finaliza encolarMovimientoReingreso");

    }
    
}
