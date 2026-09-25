package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AsignacionPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.asignacion.MovimientoAsignacionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "asignacionPatronalBusiness", mappedName = "asignacionPatronalBusiness")
public class AsignacionPatronalBusiness extends AbstractServiceBusiness
		implements AsignacionPatronalBusinessRemote {

	private static final Logger log = LoggerFactory
			.getLogger(AsignacionPatronalBusiness.class);

	@Resource(name = "jms/GeneralConnectionFactory")
	private transient ConnectionFactory factory;
	@Resource(name = "jms/AsignNSScanaseDistributedQueue")
	private transient Queue queueAsignacion;
	private transient Connection conn;
	private transient Session session;
	private transient MessageProducer producer;

	

	@Override
	public void encolarMovimientoAsignacion(
			MovimientoAsignacionType movimientoAsignacionType) {
		String resultXML = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(movimientoAsignacionType);
		log.debug("encolando xml {}", resultXML);
		procesarMensaje(conn, session, producer, factory, queueAsignacion, resultXML);
		log.debug("finaliza encolarMovimientoAsignacion");
	}

	@Override
	public String generarXmlMovimientoAsignacion(
			MovimientoAsignacionType movimientoAsignacionType) {

		String resultXML = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(movimientoAsignacionType);
		return resultXML;

	}
	
}
