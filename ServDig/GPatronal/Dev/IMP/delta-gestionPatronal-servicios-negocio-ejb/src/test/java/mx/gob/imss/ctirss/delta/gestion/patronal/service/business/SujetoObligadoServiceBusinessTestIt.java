package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class SujetoObligadoServiceBusinessTestIt {
    private static final Logger log = LoggerFactory.getLogger(SujetoObligadoServiceBusinessTestIt.class);

    private SujetoObligadoServiceBusinessRemote service;

    @Before
    public void before() throws NamingException {
        service = EjbLocator.getSujetoObligadoServiceBusiness();
        log.debug("Obtuve servicio EJB: {}", service);
    }

    @Test
    public void obtenerListaPatronesPorPersona(){
    	log.debug("::: Iniciando, " + new Date());
    	Persona p = new Persona();
    	p.setIdPersona(new Long(3754478)); // walmart stage 3794562, walmart produccion 588596
    	//3478720 - 4065334
		p.setTipoPersona(new TipoPersona());
		p.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);						
    	
    	try {
			List<SujetoObligado> lSO = service.getListaPatronesPorPersona(p);
			log.debug("::: Obtuve " + lSO.size() + " rp's");
			for (Iterator<SujetoObligado> iterator = lSO.iterator(); iterator.hasNext();) {
				SujetoObligado so = iterator.next();
				log.debug(so.getNumeroRegistroPatronal() + " - " + so.getNombreComercial());
			}
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
    	
    	log.debug("::: FIN, " + new Date());
    }
    
//    @Test
    public void testConsultarPorRegistroPatronal() {
        log.debug("Prueba EJB consultar por registro patronal");
        try {
        	SujetoObligado  so = service.consultarPorRegistroPatronalBasic("Y7226415106", TipoPersonaFiscal.MORAL);
        	log.debug("SO obtenido con exito");
        	if(so != null) {
            	log.debug(so.toString());
        	}else {
        		log.debug("El sujeto obligado es NULL");
        	}
        }catch(Exception e) {
    		e.printStackTrace();    	}
    }
    
    
//    @Test
    public void testEnviarMovimientoCambioNombre() throws GestionPatronalBusinessException {
        log.debug("Prueba de enviar Movimiento de cambio de nombre");
        service.enviarMovimientoCambioNombre("Y5450901102", "IGNACIO ESPINOSA VELAZQUEZ");
    }

  // @Test
  //  @org.junit.Ignore
    public void testReEnviarMovimiento04() throws GestionPatronalBusinessException {
        service.reEnviarMovimiento04("Y5838205101");
        service.reEnviarMovimiento04("Y5450901102");
    }

   // @Test
   // @org.junit.Ignore
    public void testActualizarDenominacionRazonSocialPFisica() throws GestionPatronalBusinessException {
        SujetoObligado sujetoObligado = new SujetoObligado();
        sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
        Long l = 3L;
        sujetoObligado.setCveIdSujetoObligado(l);
        sujetoObligado.setNombreComercial("NOMBRECOMERCIAL");
        Modalidad modalidad = new Modalidad();
        modalidad.setNumModalidad("10");
        sujetoObligado.setModalidad(modalidad);
        sujetoObligado.setDigVerificador("9");
        Fisica fisica = new Fisica();
        fisica.setIdPersona(24653636L);
        fisica.setCveFisica(3L);
        fisica.setNombre("FOO");
        fisica.setPrimerApellido("BAR");
        fisica.setPrimerApellido("FOOBAR");
        fisica.setSegundoApellido("BAR");
        fisica.setRfc("FOOB110170HHH");
        fisica.setCurp("BAFO070HMCRPB08");
        sujetoObligado.setFisica(fisica);
        sujetoObligado.setSubdelegacion(createSubdelegacion());
        service.actualizarDenominacionRazonSocial(sujetoObligado, new Usuario());
    }

   // @Test
   // @org.junit.Ignore
    public void testActualizarDenominacionRazonSocialPMoral() throws GestionPatronalBusinessException {
        SujetoObligado sujetoObligado = new SujetoObligado();
        sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
        Long l = 58L;
        Moral moral = new Moral();
        moral.setRazonSocial("NUEVARAZONSOCIAL");
        moral.setIdPersona(78400L);
        moral.setRfc("AAP110707ST5");
        TipoSociedad tipoSociedad = new TipoSociedad();
        tipoSociedad.setIdTipoSociedad(18L);
        moral.setTipoSociedad(tipoSociedad);
        sujetoObligado.setCveIdSujetoObligado(l);
        sujetoObligado.setNombreComercial("NOMBRECOMERCIAL");
        sujetoObligado.setDigVerificador("8");
        Modalidad modalidad = new Modalidad();
        modalidad.setNumModalidad("10");
        sujetoObligado.setModalidad(modalidad);
        sujetoObligado.setMoral(moral);
        sujetoObligado.setSubdelegacion(createSubdelegacion());
        service.actualizarDenominacionRazonSocial(sujetoObligado, new Usuario());
    }

    private Subdelegacion createSubdelegacion() {
         Subdelegacion subdelegacion = new Subdelegacion();
         subdelegacion.setClave("42");
         subdelegacion.setId(128L);
         Delegacion delegacion = new Delegacion();
         delegacion.setClave("42");
         delegacion.setId(35L);
         subdelegacion.setDelegacion(delegacion);
         delegacion.setCiz(1);

         return subdelegacion;
    }
    
   // @Test
    public void generaRepSemanas(){
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		try {
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			JasperReport report = JasperCompileManager
					.compileReport(new ClassPathResource("comprobantes/Semanas_Cotizadas.jrxml")
							.getInputStream());
			JasperPrint print;
			List<HldaVO> info = new ArrayList<HldaVO>();
			
			
			HldaVO hlda = new HldaVO();
			hlda.setApelMat("CHAMONICA");
			hlda.setApelPat("MARTINEZ");
			hlda.setNombre("HUGO ARMANDO");
			hlda.setNss("43048011613");
			hlda.setTotSemCot(520);
			
			
			info.add(hlda);
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(info);
			
			print = JasperFillManager.fillReport(report, parametros, beanDS);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);
			
			exporter.exportReport();
			
			File pdf = File.createTempFile("output", ".pdf");
			JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdf));
			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

