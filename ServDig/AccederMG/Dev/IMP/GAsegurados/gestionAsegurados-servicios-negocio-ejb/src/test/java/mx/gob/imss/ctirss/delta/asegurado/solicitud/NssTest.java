package mx.gob.imss.ctirss.delta.asegurado.solicitud;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NSSYaExistenteException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SerieNssAgotadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.AseguradoServiciosExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.test.EJBLocator;
import mx.gob.imss.ctirss.delta.global.model.UnidadMedicaFamiliarTO;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ClavePresupuestal;
import mx.gob.imss.ctirss.delta.model.derechohabiente.NivelAtencion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoUMF;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.nss.TipoSerie;

public class NssTest {

	@Autowired AseguradoServiciosExternosRemote nss;
	private static final int ANIO_MINIMO_SERIE = 14;
	private static final int ANIO_CERO_CERO= 00;
	private static final int ANIO_NOVENTAYNUEVE = 99;

	@Test
	public void getSeriesNss() {
		System.out.println("getSeriesNss. Inicio " + new Date());
		//List<Serie> listaSeries = EjbLocator.getPersonaBusiness().getSeriesNss(39L, 137L);
		List<Serie> listaSeries = EJBLocator.getPersonaBusiness().getSeriesNss(null, null);

		if (listaSeries != null){
			System.out.println("Series encontradas para la delegacion/subdelegacion");
			for (Serie serie: listaSeries){
				System.out.println("Serie: " + serie);
			}
		}
		System.out.println("getSeriesNss. Final " + new Date());
	}

	@Test
	public void generaNss() {
		System.out.println("generaNss. Inicio " + new Date());
		System.out.println("nss: " + EJBLocator.getServiceBusiness().generaNss(1L, 1L, 12L) );
		System.out.println("generaNss. Final " + new Date());
	}

	@Test
	public void altaPeronsaFisicaNss() {
		System.out.println("altaPeronsaFisicaNss. Inicio " + new Date());

		Fisica fisica = new Fisica();
		fisica.setIdPersona(25128999L);

		Serie serie = new Serie();
		serie.setAnioRegistro(1);
		serie.setIdSerie(1L);

		System.out.println("nss: " + EJBLocator.getServiceBusiness().altaPersonaNss(fisica, serie) );
		System.out.println("altaPeronsaFisicaNss. Final " + new Date());
	}    

	@Test
	public void testAsignarNSS(){

		String curp="BEAR840124HMCRPB04";
		String correo="pablo.bombela@imss.gob.mx";
		UnidadMedicaFamiliarTO UMF = new UnidadMedicaFamiliarTO();
		UMF.setIdUMF(43L);
		UMF.setClavePresupuestal(new ClavePresupuestal());
		UMF.getClavePresupuestal().setClavePresupuestal("10");
		UMF.getClavePresupuestal().setIdClavePresupuestal(10l);
		UMF.setNivelAtencion(new NivelAtencion());
		UMF.getNivelAtencion().setIdNivelAtencion(10l);
		UMF.setNoEconomico(new BigDecimal("1"));

		Subdelegacion subdel = new Subdelegacion();
		subdel.setClave("10");
		subdel.setId(10l);

		Delegacion del = new Delegacion();
		del.setClave("10");
		del.setId(10l);
		del.setCiz(1);
		subdel.setDelegacion(del);

		UMF.setSubdelegacion(subdel);
		UMF.setTipoUMF(new TipoUMF());
		UMF.getTipoUMF().setIdTipoUMF(new BigInteger("1"));

		AseguradoServiciosExternosRemote aser = EJBLocator.getServiciosExternosAseguradoService();

		try {
			UMF.setIdUMF(38L);
			aser.asignarNSS(curp,correo, UMF, null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Test
	public void generaNssSerie() {
		System.out.println("generaNss. Inicio " + new Date());
		TipoSerie tipo = new TipoSerie();
		tipo.setIdTipoSerie(new Integer(1));
		tipo.setDescripcion("ORDINARIA");


		Delegacion delegacion = new Delegacion();
		delegacion.setId(new Long(15));

		Subdelegacion subdelegacion = new Subdelegacion();
		subdelegacion.setId(new Long(55));


		Calendar calFechaNacimiento = Calendar.getInstance();
		calFechaNacimiento.set(Calendar.YEAR, 1983);
		Date fechaNacimiento = calFechaNacimiento.getTime();

		Calendar calFechaRegistro= Calendar.getInstance();
		calFechaRegistro.set(Calendar.YEAR, 2025);
		Date fechaRegistro = calFechaRegistro.getTime();


		AsignacionSerieNSS asignacionSerieNSS = new AsignacionSerieNSS();
		Fisica asegurado = new Fisica();
		asegurado.setFechaNacimiento(fechaNacimiento);
		asegurado.setFechaRegistro(fechaRegistro);
		asegurado.setIdPersona(new Long(11));


		Serie serie = new Serie();
		serie.setTipoSerie(tipo);
		serie.setAnioNacimiento(new Integer(83));
		serie.setAnioRegistro(new Integer(25));
		//asignacion.setDelegacion(delegacion);
		//asignacion.setSubdelegacion(subdelegacion);

		SerieServiceBusinessRemote service =  EJBLocator.getSerieServiceBusiness();
		asignacionSerieNSS.setSerie(serie);
		try {

			boolean nssCorrecto = false;

			do {
				try {
					String nss =service.calculaNSS(asignacionSerieNSS, asegurado);
					System.out.println("el NSS calculado" + nss);
					nssCorrecto = true;
				} catch (NSSYaExistenteException e) {
					System.out.println(e.getMessage());
				} catch (SerieNssAgotadaException e) {
					System.out.println("LA SERIE ACTUAL YA LLEGO AL LIMITE SE USA LA DEL ANIO ANTERIOR" );
					if(asignacionSerieNSS.getSerie().getAnioRegistro().intValue() == ANIO_MINIMO_SERIE)
						throw new SeriesNoLocalizadasException();
					asignacionSerieNSS.getSerie().setAnioRegistro(
							asignacionSerieNSS.getSerie().getAnioRegistro().intValue() == ANIO_CERO_CERO?ANIO_NOVENTAYNUEVE:
								asignacionSerieNSS.getSerie().getAnioRegistro().intValue()-1);
				}catch(SeriesNoLocalizadasException ex){
					System.out.println("NO SE ENCONTRO NINGUNA SERIE ACTIVA SE UTILIZARA LA DEL ANIO ANTERIOR" );
					if(asignacionSerieNSS.getSerie().getAnioRegistro().intValue() == ANIO_MINIMO_SERIE)
						throw ex;
					asignacionSerieNSS.getSerie().setAnioRegistro(
							asignacionSerieNSS.getSerie().getAnioRegistro().intValue() == ANIO_CERO_CERO?ANIO_NOVENTAYNUEVE:
								asignacionSerieNSS.getSerie().getAnioRegistro().intValue()-1);
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

		System.out.println("generaNss. Final " + new Date());
	}




}
