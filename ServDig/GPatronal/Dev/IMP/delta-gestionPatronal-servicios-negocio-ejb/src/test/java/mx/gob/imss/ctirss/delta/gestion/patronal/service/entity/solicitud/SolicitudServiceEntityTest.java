package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolicitudServiceEntityTest {
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(SolicitudServiceEntityTest.class);
	}

	public static void main(String[] args) {
		Object object = EJBLocator.getServiceBusiness();

		Assert.assertNotNull(object);
		Assert.assertTrue(object instanceof SolicitudServiceBusinessRemote);

		final SolicitudServiceBusinessRemote ejb = (SolicitudServiceBusinessRemote) object;
		Assert.assertNotNull(ejb);

		TipoPersonaFiscal tipo = TipoPersonaFiscal.FISICA;
		Long idPersona = 4313962L;
		TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.TODAS;
		Solicitud solicitud = ejb.obtenerSolicitudEnCapturaPorPersona(
				idPersona, tipo, tipoSolicitud);
		LOG.info(solicitud.toString());
	}

	@Test
	public void testObtenerSolicitudEnCapturaPorPersona() throws Exception {
		Object object = EJBLocator.getServiceBusiness();

		Assert.assertNotNull(object);
		Assert.assertTrue(object instanceof SolicitudServiceBusinessRemote);

		final SolicitudServiceBusinessRemote ejb = (SolicitudServiceBusinessRemote) object;
		Assert.assertNotNull(ejb);

		TipoPersonaFiscal tipo = TipoPersonaFiscal.FISICA;
		Long idPersona = 4313962L;
		TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.TODAS;
		Solicitud solicitud = ejb.obtenerSolicitudEnCapturaPorPersona(
				idPersona, tipo, tipoSolicitud);
		LOG.info(solicitud.toString());
	}

}
