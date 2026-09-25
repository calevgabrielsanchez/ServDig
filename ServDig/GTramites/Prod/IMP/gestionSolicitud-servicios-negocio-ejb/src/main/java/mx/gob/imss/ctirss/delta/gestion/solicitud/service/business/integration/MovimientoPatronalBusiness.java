/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lucio Duran Silva
 *
 */





@Stateless(name = "movimientoPatronalBusiness", mappedName = "movimientoPatronalBusiness")
public class MovimientoPatronalBusiness extends AbstractServiceBusiness
		implements MovimientoPatronalBusinessRemote {
		
	private static final Logger log = LoggerFactory
		.getLogger(MovimientoPatronalBusiness.class);
	
	@Resource(name = "jms/GeneralConnectionFactory" )
    private transient ConnectionFactory factory;
    @Resource(name = "jms/SindoSendFileDistributedQueue")
    private transient Queue colaTramites;
    private transient Connection conn;
    private transient Session session;
    private transient MessageProducer producer;    
    
	
	@Override
	public void enviarModificacionPatronal(MovimientoPatronalType movimiento) {
		String xmlMovimiento = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(movimiento);
		log.debug("encolando xml {}", xmlMovimiento);
		procesarMensaje(conn, session, producer, factory, colaTramites, xmlMovimiento);
		log.debug("finaliza encolarMovimientoAsignacion");
	}
	
}
