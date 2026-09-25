package reportes;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.util.InputStreamReader;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ReporteBusinessRemote;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.junit.Test;

import test.DeltaOpenEJBTestCase;

public class reportes extends DeltaOpenEJBTestCase{
	
    private static final String IMSS_HEADER_PATH = "reportes/imss_header.gif";


    private transient final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "mx"));
    private transient final SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss", new Locale("es", "mx"));
    
	@Test
	public void testGeneraReportePersonaFisica(){
		
		Object object = null;
		try {
			object = initialContext.lookup("reporteBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		
		assertNotNull(object);
		assertTrue(object instanceof ReporteBusinessRemote);
		
		final ReporteBusinessRemote ejb = (ReporteBusinessRemote) object;
		assertNotNull(ejb);
		
		Long folioSolicitud = new Long(3649);
		String sNombreUsuarioReporte = "USUARIO TEST";
		
		try {
			byte[] bytesReporte = ejb.getComprobanteOperacion(folioSolicitud, sNombreUsuarioReporte);
			FileOutputStream fos = new FileOutputStream("C:\\Comprobante.pdf");
			fos.write(bytesReporte);
			
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
    public byte[] reporte(final Solicitud solicitud) {
        final List<Tramite> tramites = solicitud.getTramite();
        formatFecha(tramites);
        String nombreReporte = "ReporteComprobanteAlta";
        if(!tramites.isEmpty()) {
            if(tramites.get(0).getPersonaFisica() != null) {
                nombreReporte += "Fisica";
            } else if(tramites.get(0).getPersonaMoral() != null) {
                nombreReporte += "Moral";
            }
        }
        byte[] reporteRespuesta = null; // NOPMD
        try {
            JasperCompileManager.compileReportToFile("src/main/resources/reportes/" + nombreReporte + ".jrxml", "src/main/resources/reportes/" + nombreReporte + ".jasper");
            final URL url = Thread.currentThread().getContextClassLoader().getResource("reportes/" + nombreReporte + ".jasper");
            final JasperReport jasReport = (JasperReport) JRLoader.loadObject(url);
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasReport, getParameters(solicitud), new JRBeanCollectionDataSource(tramites));
            JasperViewer.viewReport(jasperPrint);
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, bos);
            reporteRespuesta = bos.toByteArray();
        } catch (JRException jre) {
        	jre.printStackTrace();
        }
        return reporteRespuesta;
    }

    private void formatFecha(final List<Tramite> tramites) {
	    for (Tramite tramite : tramites) {
            if(tramite.getPersonaFisica() != null) {
                tramite.getPersonaFisica().setFechaNacimientoFormateada(dateFormat.format(tramite.getPersonaFisica().getFechaNacimiento()));
            } else if(tramite.getPersonaMoral() != null) {
                tramite.getPersonaMoral().setFechaCreacionFormateada(dateFormat.format(tramite.getPersonaMoral().getFechaCreacion()));
            } else {
            }
        }
    }

    private Map<String, Object> getParameters(final Solicitud solicitud) {
		final Map<String, Object> parameters = new HashMap<String, Object>();
		Image image = null;
        try {
            image = ImageIO.read(new InputStreamReader(IMSS_HEADER_PATH).getInputStream());
            parameters.put("IMSS_HEADER_PARAM", new InputStreamReader(IMSS_HEADER_PATH).getInputStream());
        } catch (IOException e) {
        }
        parameters.put("IMSS_HEADER2_PARAM", image);
        final Date fechaImpresion = solicitud.getFechaRegistro();
        parameters.put("FECHA", dateFormat.format(fechaImpresion));
        parameters.put("HORA", timeFormat.format(fechaImpresion));
        parameters.put("FOLIO_OPER", solicitud.getIdSolicitud());
        parameters.put("NOMBRE_SOLICITANTE", solicitud.getUsuario());
        parameters.put("ESTATUS_OPER", "En Proceso");
        parameters.put("JUSTIFICACION_LEGAL", "En virtud del acuerdo de colaboración existente entre el Instituto Mexicano del Seguro Social y Novutek, se ha acordado revisar avances de los diversos proyectos al finalizar cada semana, ...");
		return parameters;
	}

    public static void main(final String[] args) throws IOException {        
        final Solicitud solicitud = new Solicitud();
        solicitud.setFechaRegistro(new Date());
        boolean testPersonaMoral = false;
        if (testPersonaMoral) {
            solicitud.getTramite().add(agregarPersonaMoral());
        } else {
            for (int idTramite = 0; idTramite < 3; idTramite++) {
                agregarTramiteSolicitud(solicitud, idTramite);
            }
        }
        new reportes().reporte(solicitud);
    }

    private static Tramite agregarPersonaMoral() {
        final Moral personaMoral = new Moral();
        
        personaMoral.setIdPersona(123L);
        personaMoral.setRazonSocial("Test!!!");
        personaMoral.setFechaCreacion(new Date());
        personaMoral.setActaConstitutiva("cta const");
        personaMoral.setRfc("GRFDRF652322Jhgg");
        //personaMoral.setDesTipoSociedad("Tipo Sociedad");
        PersonaCalificacion persCalif = new PersonaCalificacion();
        Calificacion calificacion = new Calificacion();
        persCalif.setCalificacion(calificacion);
        personaMoral.getPersonaCalificaciones().add(persCalif);
        personaMoral.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion("No validado");
        final Tramite tramite = new Tramite();
        tramite.setIdTramite(132L);
        tramite.setPersonaMoral(personaMoral);
        return tramite;
    }

    private static void agregarTramiteSolicitud(final Solicitud solicitud, final int index) {
        final Tramite tramite2 = new Tramite();
        tramite2.setIdTramite(index + 0L);
        tramite2.setFechaTramite(new Date());
        tramite2.setObservacion("Ninguna, por ahora");
        Sexo sexo = new Sexo();
        sexo.setIdSexo(2);
        sexo.setDescripcion("MUJER");
        
        final Fisica personaFisica = new Fisica();
        personaFisica.setIdPersona(index + 0L);
        personaFisica.setNombre("Joaco");
        personaFisica.setPrimerApellido("Ponte");
        personaFisica.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion("NO_VALIDADO");
        
        personaFisica.setSexo(sexo);
        personaFisica.setFechaNacimiento(new Date());
        personaFisica.getLugarNacimiento().setClave("9");
        personaFisica.getLugarNacimiento().setNombre("Distrito Federal");
        tramite2.setPersonaFisica(personaFisica);
        solicitud.getTramite().add(tramite2);
    }	

}
