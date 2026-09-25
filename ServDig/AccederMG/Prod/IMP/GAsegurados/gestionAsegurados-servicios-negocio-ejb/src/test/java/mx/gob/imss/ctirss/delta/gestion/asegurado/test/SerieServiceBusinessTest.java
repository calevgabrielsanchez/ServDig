/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NSSYaExistenteException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SerieNssAgotadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAsignarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorCrearFoliosDeSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorGuardarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NumeroDeSeriePorAnioRegistroExisteException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business.SerieServiceBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.nss.TipoSerie;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vanderluk
 *
 */
public class SerieServiceBusinessTest extends DeltaOpenEJBTestCase{
	private static final Logger LOG;
	//private transient final SerieServiceBusinessRemote serieBusiness = EJBLocator.getSerieBusiness();
	
	static {
		LOG = LoggerFactory.getLogger(SerieServiceBusinessTest.class); 
	}
	
	@Test
	public void testObtenerSeriesActivas() throws Exception{
		
		
		final Object object = initialContext.lookup("serieServiceBusiness");
		
		
		assertNotNull(object);
		assertTrue(object instanceof SerieServiceBusinessLocal);
		
		final SerieServiceBusinessRemote ejb = (SerieServiceBusinessRemote) object;
		assertNotNull(ejb);
		
		
		Long delegacion = 39l;
		
		Long subdelegacion =137l;
		
		List <AsignacionSerieNSS> asignaciones = ejb.obtenerSeriesActivas(delegacion, subdelegacion);
		
		
		AsignacionSerieNSS asignacion = new AsignacionSerieNSS();
		Fisica asegurado = new Fisica();
		
		Delegacion oDelegacion = new Delegacion();
		oDelegacion.setId(delegacion);
		Subdelegacion oSub = new Subdelegacion();
		//oSub.setId(subdelegacion);
		
		Serie serie = new Serie();
		serie.setTipoSerie(new TipoSerie());
		serie.getTipoSerie().setIdTipoSerie(new Integer(1));
		
		asegurado.setFechaNacimiento(new Date());
		
		
		
		asignacion.setDelegacion(oDelegacion);
		asignacion.setSubdelegacion(oSub);
		asignacion.setSerie(serie);
		
		
		ejb.asignarNSS(asignacion, asegurado);
		
		
		System.out.println(asignaciones);
		
	}
	
	
	@Test
	public void testAsignacionDeNss(){
		
		
		Object object = null;
		try {
			object = initialContext.lookup("serieServiceBusiness");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		assertNotNull(object);
		assertTrue(object instanceof SerieServiceBusinessRemote);
		
		final SerieServiceBusinessRemote ejb = (SerieServiceBusinessRemote) object;
		assertNotNull(ejb);
		
		
		TipoSerie tipo = new TipoSerie();
		tipo.setIdTipoSerie(new Integer(3));
		tipo.setDescripcion("ORDINARIA");
		
		Delegacion delegacion = new Delegacion();
		delegacion.setId(new Long(15));
		
		Subdelegacion subdelegacion = new Subdelegacion();
		subdelegacion.setId(new Long(55));
		
		
		Calendar calFechaNacimiento = Calendar.getInstance();
		calFechaNacimiento.set(Calendar.YEAR, 1983);
		Date fechaNacimiento = calFechaNacimiento.getTime();
		
		Calendar calFechaRegistro= Calendar.getInstance();
		calFechaRegistro.set(Calendar.YEAR, 2013);
		Date fechaRegistro = calFechaRegistro.getTime();
		
		
		AsignacionSerieNSS asignacion = new AsignacionSerieNSS();
		Fisica asegurado = new Fisica();
		asegurado.setFechaNacimiento(fechaNacimiento);
		asegurado.setFechaRegistro(fechaRegistro);
		asegurado.setIdPersona(new Long(11));
		
		
		Serie serie = new Serie();
		serie.setTipoSerie(tipo);
		serie.setAnioNacimiento(new Integer(83));
		
		//asignacion.setDelegacion(delegacion);
		//asignacion.setSubdelegacion(subdelegacion);
		
		
		asignacion.setSerie(serie);
		
		
		
		try {

			boolean nssCorrecto = false;
			
			do {
				try {
					ejb.asignarNSS(asignacion, asegurado);
					nssCorrecto = true;
				} catch (NSSYaExistenteException e) {
					e.printStackTrace();
				} catch (SerieNssAgotadaException e) {
					e.printStackTrace();
				}
			} while (!nssCorrecto);
		} catch (NivelDeAsignacionSerieIndefinidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeriesNoLocalizadasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorAlActivarSerieException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	@Test
	public void testCrearSerie() {
		
		
		Object object = null;
		try {
			object = initialContext.lookup("serieServiceBusiness");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		assertNotNull(object);
		assertTrue(object instanceof SerieServiceBusinessRemote);
		
		final SerieServiceBusinessRemote ejb = (SerieServiceBusinessRemote) object;
		assertNotNull(ejb);
		
		
		Long delegacion = 39l;
		Long subdelegacion =137l;
		
		Long anioRegistro = 13l;
		Long numSerie = 99L;
		Integer tipoSerie = 1;
		
		
		
		
		
		
		AsignacionSerieNSS asignacion = new AsignacionSerieNSS();
		
		
		Delegacion oDelegacion = new Delegacion();
		oDelegacion.setId(delegacion);
		Subdelegacion oSub = new Subdelegacion();
		oSub.setId(subdelegacion);
		
		Serie serie = new Serie();
		serie.setTipoSerie(new TipoSerie());
		serie.getTipoSerie().setIdTipoSerie(tipoSerie);
		serie.setAnioRegistro(anioRegistro.intValue());
		serie.setNumSerie(numSerie);
		
		
		
		
		//asignacion.setDelegacion(oDelegacion);
		//asignacion.setSubdelegacion(oSub);
		asignacion.setSerie(serie);
		
		
		try {
			ejb.crearSerie(asignacion);
		} catch (NumeroDeSeriePorAnioRegistroExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorGuardarSerieException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorCrearFoliosDeSerieException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorAsignarSerieException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("--------"+e.getSituacion());
			System.out.println("--------"+ e.getMessage());
		}catch (EJBException e) {
			System.out.println(e.getCause().getMessage());
			
		}
	
		
		
		
		
	}

	@Test
	public void obtenerSeriesAsignacionTest() {
		
		
		
		
		
	}
	
	@Test
	public void testSeriesAsignacionDelegacionTest() {

		Object object = null;
		try {
			object = initialContext.lookup("serieServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		assertNotNull(object);
		assertTrue(object instanceof SerieServiceBusinessRemote);

		final SerieServiceBusinessRemote ejb = (SerieServiceBusinessRemote) object;
		assertNotNull(ejb);

		System.out.println("EJB" + ejb);
		AsignacionSerieNSS asignacionSerieNSS = new AsignacionSerieNSS();

		Delegacion delegacion = new Delegacion();
		delegacion.setId(39L);
		//asignacionSerieNSS.setDelegacion(delegacion);

		Subdelegacion subdelegacion = new Subdelegacion();
		subdelegacion.setId(127L);
		//asignacionSerieNSS.setSubdelegacion(subdelegacion);

		try {
			List<AsignacionSerieNSS> listAsignacion = ejb.obtenerSeriesActivas(
					delegacion.getId(), subdelegacion.getId());
			for (AsignacionSerieNSS asignacion : listAsignacion) {
				System.out.println("Num Serie: " + asignacion.getSerie().getNumSerie());
				System.out.println("Tipo Serie: " + asignacion.getSerie().getTipoSerie().getDescripcion());
				System.out.println("Del: " + asignacion.getDelegacion());
				System.out.println("SubDel: " + asignacion.getSubdelegacion());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegistrarAseguradoTest() throws Exception {		
		Object serieServiceBusinessContext = null;

		try {
			serieServiceBusinessContext = initialContext.lookup("serieServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		assertNotNull(serieServiceBusinessContext);
		assertTrue(serieServiceBusinessContext instanceof SerieServiceBusinessRemote);

		final SerieServiceBusinessRemote serieServiceBusiness = (SerieServiceBusinessRemote) serieServiceBusinessContext;
		assertNotNull(serieServiceBusiness);

		System.out.println("serieServiceBusiness: " + serieServiceBusiness);

		Fisica asegurado = new Fisica();
		asegurado.setCurp("GAOM950424HMCRRS00");

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
