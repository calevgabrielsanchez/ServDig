package mx.gob.imss.ctirss.delta.gestion.beneficio.service.business;

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.business.base.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcesarSolicitudBeneficioTest {

	private static final Logger log = LoggerFactory
	.getLogger(ProcesarSolicitudBeneficioTest.class);

	private BeneficioRissServiceBusinessRemote beneficioRissServiceBusinessRemote;

	@Before
	public void setUp() {
		beneficioRissServiceBusinessRemote = EjbLocator.getBeneficioRissServiceBusiness();
	}

	
	@Test
	public void testprocesarSolicitudRissSO() {
		Long idSolicitud = 10028L;
		
		try {
			beneficioRissServiceBusinessRemote.procesarSolicitudRiss(idSolicitud);
		} catch (BeneficioRissException e) {
			log.error(e.getMessage());
		}		
	}
	
	@Test
	public void testprocesarSolicitudRissPF() {
		Long idSolicitud = 10027L;
			
		try {
			beneficioRissServiceBusinessRemote.procesarSolicitudRiss(idSolicitud);
		} catch (BeneficioRissException e) {
			log.error(e.getMessage());
		}		
	}
	
	@Test
	public void testProcesarNuevaAltaPatronal() {
		OrigenSolicitudEnum origenSolicitud = OrigenSolicitudEnum.INTERNET;
		//Para probar, verificar que NO este asociado el SO 6729465 
		// en DitPatSujObligBeneficio
		SujetoObligado so = new SujetoObligado();
		so.setCveIdSujetoObligado(6729465L);
		so.setNumeroRegistroPatronal("Y5846417");
		so.setDigVerificador("2");
		so.setModalidad(new Modalidad());
		so.getModalidad().setNumModalidad("10");
		
		so.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		so.setFisica(new Fisica());
		so.getFisica().setCveFisica(3106301L);
		so.getFisica().setIdPersona(84501612L);
		so.getFisica().setRfc("VERA650124DH8");
		so.getFisica().setCurp("VERA650124HTSRZR04");
		so.getFisica().setNss("03146590041");

		log.debug("ALTA PATRONAL CON ID "  + so.getCveIdSujetoObligado());
		try {
			beneficioRissServiceBusinessRemote.heredarBeneficioAltaPatronal(so, origenSolicitud.getId());
		} catch (BeneficioRissException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("FIN TEST");
	}
	
}
