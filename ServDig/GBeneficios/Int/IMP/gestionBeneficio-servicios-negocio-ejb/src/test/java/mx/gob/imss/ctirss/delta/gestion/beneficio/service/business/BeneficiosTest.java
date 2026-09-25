package mx.gob.imss.ctirss.delta.gestion.beneficio.service.business;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.PersonaNoValidaBeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceImssRissException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.business.base.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.ReportesBeneficiosBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.DescuentoBeneficio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class BeneficiosTest {

	private static final Logger log = LoggerFactory
			.getLogger(BeneficiosTest.class);

	private BeneficioRissServiceBusinessRemote beneficioRissServiceBusiness;
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	private ReportesBeneficiosBusinessRemote reportesBeneficiosBusinessRemote;
	private SolicitudBusinessRemote solicitudBusinessRemote;

	@Before
	public void setUp() {
		beneficioRissServiceBusiness = EjbLocator.getBeneficioRissServiceBusiness();
		personaFisicaServiceBusiness = EjbLocator.getPersonaFisicaServiceBusiness();
		reportesBeneficiosBusinessRemote = EjbLocator.getReporteBeneficioBusiness();
		solicitudBusinessRemote = EjbLocator.getSolicitudBusinessRemote();
	}
	
	@Test
	public void pruebaReporte() {
		Solicitud solicitud = new Solicitud();
		
		try {
			solicitud.setNoFolioSolicitud("146427947909640188840");
			solicitud = solicitudBusinessRemote.consultarFolio(solicitud);
			
			byte[] doctoGenerado = reportesBeneficiosBusinessRemote.generarReporteBeneficioRiss(solicitud);
			
			FileOutputStream fos = null;
			
			try {
				fos = new FileOutputStream("d:\\pruebasReportes\\comprobanteRISSGOBMX_A.pdf");
				fos.write(doctoGenerado);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void habilitarRiss() {
		Long idOrigenSolicitud = OrigenSolicitudEnum.INTERNET.getId();
		boolean habilita = beneficioRissServiceBusiness.habilitarRissPortal(idOrigenSolicitud);
		log.debug(" HABILITA RISS: "  + habilita);
	}
	
    //@Test
    public void procesarActualizacionesPF() {    	
    	List<Fisica> listaPF  = prepararDatosIniciales();
    	if(!CollectionUtils.isEmpty(listaPF)){
    		personaFisicaServiceBusiness.procesarActualizacionesPF(listaPF);			
    	}
    }

    //@Test
	public void obtenerBeneficioPorNRP() {		
		Calendar fechaIni = new GregorianCalendar();
		fechaIni.set(2015, Calendar.APRIL, 15);
		Calendar fechaFin = new GregorianCalendar();
		fechaFin.set(2018, Calendar.NOVEMBER, 25);		
		try {
			Beneficio beneficio = beneficioRissServiceBusiness.obtenerBeneficioPorNRP("Y5236304100", fechaIni.getTime(), fechaFin.getTime());
			log.debug("BENEFICIO-NRP "  + beneficio.getIdBeneficio());
			log.debug("BENEFICIO (idSolicitud) "  + beneficio.getIdSolicitud());
			log.debug("BENEFICIO (rfc) "  + beneficio.getRfc());
			log.debug("BENEFICIO (IndicadorApartadoC) "  + beneficio.isIndicadorApartadoC());
			for(DescuentoBeneficio descuento : beneficio.getListaDescuentosBeneficio()){
				log.debug("% DESCUENTO "  + descuento.getPorcentajeDescuento());
				log.debug("PERIDO " + descuento.getFecInicio()  + " - "+  descuento.getFechaFin());				
			}
		} catch (BeneficioRissException e) {
			log.error(e.getMessage());
		}	
	}
	
	//@Test
	public void obtenerBeneficioPorNssPatron() {		
		Calendar fechaIni = new GregorianCalendar();
		fechaIni.set(2017, Calendar.AUGUST, 20);
		Calendar fechaFin = new GregorianCalendar();
		fechaFin.set(2018, Calendar.JANUARY, 1);
		try {
			Beneficio beneficio = beneficioRissServiceBusiness.obtenerBeneficioPorNSS("03146590041", fechaIni.getTime(), fechaFin.getTime());
			log.debug("BENEFICIO-NSS-PATRON "  + beneficio.getIdBeneficio());
			log.debug("BENEFICIO (idSolicitud) "  + beneficio.getIdSolicitud());
			log.debug("BENEFICIO (rfc) "  + beneficio.getRfc());
			log.debug("BENEFICIO (curp) "  + beneficio.getCurp());
			log.debug("BENEFICIO (IndicadorApartadoC) "  + beneficio.isIndicadorApartadoC());
			log.debug("BENEFICIO (ListaNRPsMod10y13) "  + beneficio.getListaNRPsMod10y13());			
			for(DescuentoBeneficio descuento : beneficio.getListaDescuentosBeneficio()){
				log.debug("% DESCUENTO "  + descuento.getPorcentajeDescuento());
				log.debug("PERIDO " + descuento.getFecInicio()  + " - "+  descuento.getFechaFin());				
			}
		} catch (BeneficioRissException e) {
			log.error(e.getMessage());
		}	
	}
	                                                      
	//@Test
	public void obtenerBeneficioPorNSS() {		
		Calendar fechaIni = new GregorianCalendar();
		fechaIni.set(2017, Calendar.AUGUST, 20);
		Calendar fechaFin = new GregorianCalendar();
		fechaFin.set(2018, Calendar.JANUARY, 1);		
		try {
			Beneficio beneficio = beneficioRissServiceBusiness.obtenerBeneficioPorNSS("45947613209", fechaIni.getTime(), fechaFin.getTime());
			log.debug("BENEFICIO-NSS-PF "  + beneficio.getIdBeneficio());
			log.debug("BENEFICIO (idSolicitud) "  + beneficio.getIdSolicitud());
			log.debug("BENEFICIO (rfc) "  + beneficio.getRfc());
			log.debug("BENEFICIO (curp) "  + beneficio.getCurp());
			log.debug("BENEFICIO (IndicadorApartadoC) "  + beneficio.isIndicadorApartadoC());
			log.debug("BENEFICIO (ListaNRPsMod10y13) "  + beneficio.getListaNRPsMod10y13());
			for(DescuentoBeneficio descuento : beneficio.getListaDescuentosBeneficio()){
				log.debug("% DESCUENTO "  + descuento.getPorcentajeDescuento());
				log.debug("PERIDO " + descuento.getFecInicio()  + " - "+  descuento.getFechaFin());				
			}
		} catch (BeneficioRissException e) {
			log.error(e.getMessage());
		}	
	}
	
	//@Test
	public void crearBeneficioNss() {
		
		String lstNss[] = { "01937323309", "06775400531", "07128700015",
				"11007611996", "11775882985", "11967000552", "11967829992",
				"13118607103", "14099310337", "14947662426", "17846425449",
				"21876880242", "28897133725", "28068400044", "30098707869",
				"33967753543", "34078308193", "37907413951", "43048215347",
				"43048011613", "45876321220", "48068738524", "48087910070",
				"56937741371", "60927438741", "55896700840", "54937841606",
				"71856502258", "71815401238", "71794501297", "71915850086",
				"71947104205", "72023200479", "71884800302", "71998018619",
				"75866800826", "72886943058", "75937740019", "78836509758",
				"84805002858", "90897229820", "92068610507", "94086902312",
				"94058406771", "34147700016" };
		
		Fisica fisica = null;
		List<SujetoObligado> sujObligados = null;
		Solicitud solicitud = null;
		
		for (String nss : lstNss) {

			try {
				fisica = this.personaFisicaServiceBusiness
						.localizarPersonaFisicaPorNss(nss);
				
				sujObligados = this.beneficioRissServiceBusiness
						.obtenerSujetosObligadosParaBeneficio(fisica.getRfc());
				
				Beneficio beneficio = beneficioRissServiceBusiness
					.obtenerPersonaBeneficioVentanilla(fisica, sujObligados);
				solicitud = beneficioRissServiceBusiness.crearSolicitudRiss(
						beneficio, null, OrigenSolicitudEnum.VENTANILLA.getId());
				beneficio = beneficioRissServiceBusiness.validarSolicitudRiss(solicitud, beneficio);
				
				beneficioRissServiceBusiness.procesarSolicitudRiss(solicitud.getSolicitudId());
				
				log.info(nss + "|" + beneficio.getTipoApartado());
				
			} catch (PersonasNoLocalizadasException e) {
				log.error(nss + "|" + e.getMessage());
			} catch (NssRelacionadoVariasPersonasException e) {
				log.error(nss + "|" + e.getMessage());
			} catch (PersonaNoValidaBeneficioRissException e) {
				log.error(nss + "|" + e.getMessage());
			} catch (SolicitudNoValidaException e) {
				log.error(nss + "|" + e.getMessage());
			} catch (BeneficioRissException e) {
				log.error(nss + "|" + e.getMessage());
			} catch (ClienteWebserviceImssRissException e) {
				log.error(nss + "|" + e.getMessage());
			}

		}
	}
	
	//@Test
	public void validarDerechoBeneficioInternet() {

		String rfc = "PEOL871113DF3";
		long idPersona = 59371696;

		Beneficio beneficio = new Beneficio();
		beneficio.setFisica(new Fisica());
		beneficio.getFisica().setRfc(rfc);
		beneficio.getFisica().setIdPersona(idPersona);
		
		Solicitud solicitud = null;
		OrigenSolicitudEnum origenSolicitud = OrigenSolicitudEnum.INTERNET;
		try {
			beneficio = beneficioRissServiceBusiness
					.obtenerPersonaBeneficio(beneficio.getFisica(), origenSolicitud.getId(), false);

			solicitud = this.beneficioRissServiceBusiness.crearSolicitudRiss(
					beneficio, null, OrigenSolicitudEnum.INTERNET.getId());
			
			beneficio = beneficioRissServiceBusiness.validarSolicitudRiss(
					solicitud, beneficio);
			
			System.out.println("BENEFICIO -> " + beneficio);
			
			/*
			 * Se cancela la solicitud para no dejar las solicitues REGISTRADAS,
			 * las n veces que se ejecute el test
			 */
			beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, "SOLICITUD CANCELADA POR USUARIO DE INTERNET", false);	
			
		} catch (PersonaNoValidaBeneficioRissException e) {
			e.printStackTrace();
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
		} catch (BeneficioRissException e) {
			e.printStackTrace();
			String msgMotivoRechazo = null;
			
			if (StringUtils.isBlank(e.getMessage())) {
				msgMotivoRechazo = "Ha ocurrido un error inesperado";
			} else {
				msgMotivoRechazo = e.getMessage();
			}
			
			try {
				beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
			} catch (AbstractException ae) {
				ae.printStackTrace();
			}			
		} catch (ClienteWebserviceImssRissException e) {
			e.printStackTrace();
			String msgMotivoRechazo = null;
			
			if (StringUtils.isBlank(e.getMessage())) {
				msgMotivoRechazo = "Ha ocurrido un error inesperado";
			} else {
				msgMotivoRechazo = e.getMessage();
			}
			
			try {
				beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
			} catch (AbstractException ae) {
				ae.printStackTrace();
			}		
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		}
	}
	
    private List<Fisica> trasnformarPF(Map<String, String> parameters){
    	List<Fisica> listaPFs = new ArrayList<Fisica>();
    	for (Map.Entry<String, String> entry : parameters.entrySet()) {
    		Fisica pf = new Fisica();
    		pf.setRfc(entry.getKey());
    		pf.setNss(entry.getValue());
    		listaPFs.add(pf);
    	}
    	return listaPFs;
    }
    
	private List<Fisica> prepararDatosIniciales(){
    	Map<String, String> parameters = new HashMap<String, String>();
    	parameters.put("  RFC      ","   NSS     ");
    	
    	parameters.put("OIAM830425TD7","66699900666");
    	
    	
    	return trasnformarPF(parameters);
    }
}