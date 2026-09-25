/**
 * 
 */
package test;

import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoActualizadaRenapoException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioRegistradoSSOException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.junit.Before;
import org.junit.Test;

/**
 * @author vanderluk
 *
 */
public class ConsultarPersonaFisicaServiceBusinessTest{

	private ConsultaPersonaFisicaServiceBusinessRemote consultaPersonaFisicaServiceBusiness;
	
	@Before
	public void setUp() {
		consultaPersonaFisicaServiceBusiness = EjbLocator.getConsultaPersonaFisicaServiceBusiness();
	}
	
	@Test
	public void validaPersonaRegistroUsuarioTest() {
	
		String curp = "BASJ670602HMCDRS08";
		String rfc = "BASJ6706025U9";
		
		Fisica fisica = new Fisica();
		fisica.setCurp(curp);
		fisica.setRfc(rfc);
		
		try {
			this.consultaPersonaFisicaServiceBusiness.validaPersonaRegistroUsuario(fisica);
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
		} catch (ErrorComparacionDatosRENAPOException e) {
			e.printStackTrace();
		} catch (ErrorComparacionDatosSATException e) {
			e.printStackTrace();
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
		} catch (UsuarioRegistradoSSOException e) {
			e.printStackTrace();
		} catch (DiferenciasRENAPOContraSAT e) {
			e.printStackTrace();
		} catch (CURPNoActualizadaRenapoException e) {
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
		} catch (EsquemaSegurdiadException e) {
			e.printStackTrace();
		}
	}
}

