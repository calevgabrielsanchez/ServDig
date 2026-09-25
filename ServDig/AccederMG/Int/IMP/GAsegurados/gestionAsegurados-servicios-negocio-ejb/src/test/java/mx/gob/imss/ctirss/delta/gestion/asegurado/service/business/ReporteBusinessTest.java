package mx.gob.imss.ctirss.delta.gestion.asegurado.service.business;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ReporteBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.test.EJBLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReporteBusinessTest {

    private static final Logger LOG;
    private static final Long SOLICITUD_ID = 1677L;

    static {
        LOG = LoggerFactory.getLogger(ReporteBusinessTest.class);
    }
    
    @Test
    public void getImagenNSS() {
    	
    	ReporteBusinessRemote reporteBusiness  = EJBLocator.getReporteBusiness();
    	
    	byte[] byteArray = reporteBusiness.getCredencialNSS("03148501731");
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("C:\\nss.png");
			fos.write(byteArray);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void getComAsignacion() {
    	
    	SolicitudBusinessRemote ejbsolicitud = EJBLocator.getSolicitudBusiness();
    	ReporteBusinessRemote reporteBusiness  = EJBLocator.getReporteBusiness();
    	Solicitud solicitud = new Solicitud();
    	solicitud.setNoFolioSolicitud("14446894339413599486");
    	try {
			solicitud  = ejbsolicitud.consultarFolio(solicitud);			
			byte[] byteArray = reporteBusiness.getComprobanteAsignacion(solicitud);
			
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("C:\\ComprobanteAsignacion.pdf");
				fos.write(byteArray);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void getComAsignacionQR() {
    	
    	SolicitudBusinessRemote ejbsolicitud = EJBLocator.getSolicitudBusiness();
    	ReporteBusinessRemote reporteBusiness  = EJBLocator.getReporteBusiness();
    	Solicitud solicitud = new Solicitud();
    	solicitud.setNoFolioSolicitud("14446894339413599486");
    	try {
			solicitud  = ejbsolicitud.consultarFolio(solicitud);
			/*
			TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) solicitud.getTramites().get(0);
			tramiteAsegurado.getFisica().setNombre("klksajdhklasjhdlkashdklashd");
			tramiteAsegurado.getFisica().setPrimerApellido("ppppppppppppppppppppppppp ppppp");
			tramiteAsegurado.getFisica().setSegundoApellido("ssssssssssssssssssssssssssssssss ss");
			
			solicitud.setTramites(new ArrayList<Tramite>());
			solicitud.getTramites().add(tramiteAsegurado);*/
			byte[] byteArray = reporteBusiness.getComprobanteAsignacionSimpleConQR(solicitud);
			
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("C:\\ComprobanteAsignacionQR.pdf");
				fos.write(byteArray);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void getComLocalizacion() {
    	SolicitudBusinessRemote ejbsolicitud = EJBLocator.getSolicitudBusiness();
    	ReporteBusinessRemote reporteBusiness  = EJBLocator.getReporteBusiness();
    	Solicitud solicitud = new Solicitud();
    	solicitud.setNoFolioSolicitud("14447879497583599869");
    	try {
			solicitud  = ejbsolicitud.consultarFolio(solicitud);
			byte[] byteArray = reporteBusiness.getComprobanteRecuperacion(solicitud);
			
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("C:\\ComprobanteLocalizacion.pdf");
				fos.write(byteArray);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @Test
    public void getComCambioCurp() {
    	SolicitudBusinessRemote ejbsolicitud = EJBLocator.getSolicitudBusiness();
    	ServiceBusinessRemote serviceB = EJBLocator.getServiceBusiness();
    	Solicitud solicitud = new Solicitud();
    	solicitud.setNoFolioSolicitud("14448435695863599955");
    	try {
			solicitud  = ejbsolicitud.consultarFolio(solicitud);
			TramiteActualizacionAsegurado tramite = (TramiteActualizacionAsegurado) solicitud.getTramites().get(0);
			FirmaElectronica firma = new FirmaElectronica();
			if(firma != null) {
				firma.setCadenaOriginal(solicitud.getCadenaOriginal());
				firma.setSecuenciaNotaria(solicitud.getSecuenciaDeNotaria());
				firma.setRecibo(solicitud.getSelloDigital());
				firma.setSerialCertificado(solicitud.getNumeroSerieCertificado());
			}
			
			byte[] byteArray;
				byteArray = (byte[])serviceB.getAcuseActualizacionDatos(solicitud.getNoFolioSolicitud(), "Actualizacion datos curp", firma, tramite);

			
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("C:\\ComprobanteCurp.pdf");
				fos.write(byteArray);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void getComVacio() {
    	ReporteBusinessRemote reporteBusiness  = EJBLocator.getReporteBusiness();
    	byte[] byteArray = reporteBusiness.getComprobanteVacio();
			
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("C:\\ComprobanteAsignacion.pdf");
				fos.write(byteArray);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    @Test
    public void getComprobanteOperacion() throws SolicitudNoEncontradaException, JRException, IOException {
        JasperCompileManager.compileReportToFile("src/main/resources/reportes/ReporteComprobanteAltaFisica.jrxml", "src/main/resources/reportes/ReporteComprobanteAltaFisica.jasper");
        final OutputStream osReport = new FileOutputStream("ReporteTest.pdf");
        try {
            osReport.write(EJBLocator.getReporteBusiness().getComprobanteOperacion(SOLICITUD_ID));
        } catch (Exception e) {
            LOG.error("Error al escribir el reporte a archivo.", e);
        }
        osReport.flush();
        osReport.close();
    }

}
