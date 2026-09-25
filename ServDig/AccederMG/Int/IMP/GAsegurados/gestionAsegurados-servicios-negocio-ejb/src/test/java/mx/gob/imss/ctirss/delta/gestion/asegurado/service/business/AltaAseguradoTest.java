package mx.gob.imss.ctirss.delta.gestion.asegurado.service.business;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.GenerarNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.ModuloOrigenAsignacionEnum;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AltaAseguradoTest {

	public ServiceBusinessRemote ejb;

	@BeforeSuite
	public void setUp() {

		ejb = EJBLocator.getServiceBusiness();

		System.setProperty("dataproviderthreadcount", "5");
	}

	@Test(threadPoolSize = 5, invocationCount = 1, alwaysRun = true, singleThreaded = false, dataProvider = "test1")
	public void test(String curp)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			GenerarNSSException, ErrorComparacionDatosRENAPOException,
			SolicitudNoValidaException, SolicitudException,
			UmfNoLocalizadaException, SolicitudNoEncontradaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			DomicilioNoValidoException,
			NivelDeAsignacionSerieIndefinidoException,
			SeriesNoLocalizadasException, ErrorAlActivarSerieException,
			TramiteNoEncontradoException, PersonaSinCalificacionesException {
		
		long threadId = Thread.currentThread().getId();
		System.out.println("HILO -> " + threadId + " CURP -> " + curp);
		
		Fisica fisica = new Fisica();
		fisica.setCurp(curp);
		
		Usuario usuario = new Usuario();
		usuario.setUsuario(curp);
		
		Domicilio domicilio = new Domicilio();
		CodigoPostal cp = new CodigoPostal();
		cp.setCodigoPostal("06600");
		domicilio.setCodigoPostal(cp);
		fisica.getDomicilios().add(domicilio);
	
		ejb.generarNSSUnSoloPaso(fisica, null, ModuloOrigenAsignacionEnum.VENTANILLA, usuario);
	}

	@DataProvider(name = "test1", parallel = true)
	public Object[][] createAseguradosData() {
		return new Object[][] {
				new Object[] { "SAEM860110HDFNSR01" },
				new Object[] { "BEBN441031HMCLCM03" }};
	}
}
