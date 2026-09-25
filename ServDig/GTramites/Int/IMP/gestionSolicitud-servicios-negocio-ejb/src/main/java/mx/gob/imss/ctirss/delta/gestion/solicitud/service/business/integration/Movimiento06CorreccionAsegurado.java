package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.Movimiento06CorreccionBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "movimiento06CorreccionAsegurado", mappedName = "movimiento06CorreccionAsegurado")
public class Movimiento06CorreccionAsegurado extends AbstractServiceBusiness
		implements Movimiento06CorreccionBusinessRemote {

	private static final Logger log = LoggerFactory
			.getLogger(Movimiento06CorreccionAsegurado.class);

	@Resource(name = "jms/GeneralConnectionFactory")
	private transient ConnectionFactory factory;
	@Resource(name = "jms/CorreccionesAseguradosDistributedQueue")
	private transient Queue queueMov06Aseg;
	private transient Connection conn;
	private transient Session session;
	private transient MessageProducer producer;

	

	@Override
	public void encolarMovimiento06CorrecconAsegurado(
			MovCorreccionesDatosAseguradoType movCorreccionesDatosAseguradoType) {
		String resultXML = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(movCorreccionesDatosAseguradoType);
		log.debug("encolando xml {}", resultXML);
		procesarMensaje(conn, session, producer, factory, queueMov06Aseg, resultXML);
		log.debug("finaliza encolarMovimiento06CorrecconAsegurado");
	}
}
