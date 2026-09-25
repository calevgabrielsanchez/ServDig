package mx.gob.imss.ctirss.delta.gestion.asegurado.service.business;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.test.EJBLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsultaSolicitudTest {
	private static final Logger LOG;
	private transient String folioSolicitud;

	private transient final SerieServiceBusinessRemote serieServiceBusiness = EJBLocator
			.getSerieServiceBusiness();
	private transient final SolicitudBusinessRemote solicitudBusiness = EJBLocator
			.getSolicitudBusiness();

	static {
		LOG = LoggerFactory.getLogger(ConsultaSolicitudTest.class);
	}

	@Before
	public void setUp() {
		folioSolicitud = "123052012000035228";
	}

	@Test
	public void consultarFolioTest() {
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioSolicitud);

		try {
			Solicitud solicitudFound = solicitudBusiness
					.consultarFolio(solicitud);
			LOG.debug(solicitudFound.toString());
		} catch (SolicitudNoEncontradaException e) {
			LOG.error(e.getMessage());
		}
	}

	@Test
	public void registrarAseguradoTest() throws Exception {

		Fisica asegurado = new Fisica();
		asegurado.setCurp("OERS700712MDFRMN02");

		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		Moral pMoral = new Moral();
		pMoral.setRfc("KCM810226DEA");
		sujetoObligado.setMoral(pMoral);
		sujetoObligado.setCveIdSujetoObligado(77L);

		Asegurado aseguradoNuevo = serieServiceBusiness.generarAsegurado(
				asegurado, sujetoObligado);

		System.out.println(aseguradoNuevo);
	}
}
