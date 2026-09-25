package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import mx.gob.imss.ctirss.delta.beneficio.model.MovimientoRissType;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EncolarMovimientoRissBusinessRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "encolarMovimientoRissBusiness", mappedName = "encolarMovimientoRissBusiness")
public class EncolarMovimientoRissBusiness extends AbstractServiceBusiness
	implements EncolarMovimientoRissBusinessRemote{
	
	private static final Logger log = LoggerFactory
		.getLogger(EncolarMovimientoRissBusiness.class);
	
	@Resource(name = "jms/GeneralConnectionFactory")
	private transient ConnectionFactory factory;
	@Resource(name = "jms/MovimientoRissDistributedQueue")
	private transient Queue queueMovimientoRiss;
	private transient Connection conn;
	private transient Session session;
	private transient MessageProducer producer;

	
	

	@Override
	public void encolarMovimientoRiss(MovimientoRissType movimientoRissType) {
		String xmlMessage = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(movimientoRissType);
		log.debug("Encolando xml {}", xmlMessage);
		procesarMensaje(conn, session, producer, factory, queueMovimientoRiss, xmlMessage);
		log.debug("EncolarMovimientoRissBusiness encolarMovimientoRiss");
	}
	
	@Override
	public void encolarMovimientosRiss(List<MovimientoRissType> listaMovimientosRissType){
		
		for(MovimientoRissType movimientoRissType : listaMovimientosRissType){
			encolarMovimientoRiss(movimientoRissType);
		}
	}
	
}
