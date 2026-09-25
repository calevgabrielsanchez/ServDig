package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.util.InputStreamReader;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.PersonaMoralEntityLocal;

@Stateless(name = "reporteBusiness", mappedName = "reporteBusiness")
public class ReporteBusiness extends AbstractServiceBusiness implements ReporteBusinessLocal, ReporteBusinessRemote {

    private static final String IMSS_HEADER_PATH = "reportes/imss_header.gif";

    private transient final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "mx"));
    private transient final SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm:ss", new Locale("es", "mx"));
    
    @EJB
    private transient SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
    
    @EJB
    private PersonaMoralEntityLocal personaMoralEntity;
    	
    public byte[] getComprobanteOperacion(final Long folioSolicitud, String sNombreUsuarioReporte) throws SolicitudNoEncontradaException {
        byte[] comprobante = null;
        if (folioSolicitud != null) {
            Solicitud solicitud = solicitudPersonaBusiness.getSolicitud(folioSolicitud);
            if (solicitud == null) {
                throw new SolicitudNoEncontradaException(folioSolicitud);
            } else {
            	
            	//***********************************************************************************************
            	//************* ESTO ES UNA SOLUCION TEMPORAL AL USUARIO ASIGNADO AL REPORTE, NO DEBE QUEDAR AQUI
            	//***********************************************************************************************
            	solicitud.setUsuario(sNombreUsuarioReporte);
            	//***********************************************************************************************
            	
                comprobante = reporte(solicitud);
            }
        }
        else{
            throw new SolicitudNoEncontradaException(folioSolicitud);
        }
        return comprobante;
    }
    
    private byte[] reporte(Solicitud solicitud) {
        final List<Tramite> tramites = solicitud.getTramite();
        corrigeDatos(tramites);
        String nombreReporte = "ReporteComprobanteAlta";
        if(tramites.size() > 0) {
            if(tramites.get(0).getPersonaFisica() != null) {
                nombreReporte += "Fisica";
            } else if(tramites.get(0).getPersonaMoral() != null) {
                nombreReporte += "Moral";
            }
        }
        byte[] reporteRespuesta = null; // NOPMD
        try {
            final URL url = Thread.currentThread().getContextClassLoader().getResource("reportes/" + nombreReporte + ".jasper");
            final JasperReport jasReport = (JasperReport) JRLoader.loadObject(url);
            final JasperPrint jasperPrint = JasperFillManager.fillReport(jasReport, getParameters(solicitud), new JRBeanCollectionDataSource(tramites));
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, bos);
            reporteRespuesta = bos.toByteArray();
        } catch (JRException jre) {
        	jre.printStackTrace();
        }
        return reporteRespuesta;
    }

    private void corrigeDatos(final List<Tramite> tramites) {
    	
    	String calificacionesPF = null;
    	
	    for (Tramite tramite : tramites) {
	    	
	    	//VALIDACIONES PARA PERSONA FISICA
	    	if (tramite.getPersonaFisica() != null){
	    		
	    		//ASIGNA DESCRIPCION DE CALIFICACION
	    		if (tramite.getPersonaFisica().getPersonaCalificaciones() != null){
	    			if (tramite.getPersonaFisica().getPersonaCalificaciones().size() > 0){
	    				calificacionesPF = "";
	    				for(PersonaCalificacion personaCalificacion : tramite.getPersonaFisica().getPersonaCalificaciones()){
		    				if (personaCalificacion.getCalificacion() != null){
		    					if (personaCalificacion.getCalificacion().getIdCalificacion() != null){
		    						long iCalificacion = personaCalificacion.getCalificacion().getIdCalificacion().longValue();
		    						if (iCalificacion == 1){
		    							calificacionesPF += CalificacionPersona.CALIFICACION_1_VALIDADO_RENAPO + ", ";
		    						}
		    						else if (iCalificacion == 2){
		    							calificacionesPF += CalificacionPersona.CALIFICACION_2_VALIDADO_SAT + ", ";
		    						}
		    						else if (iCalificacion == 3){
		    							calificacionesPF += CalificacionPersona.CALIFICACION_3_VALIDADO_IMSS + ", ";
		    						}
		    						else if (iCalificacion == 4){
		    							calificacionesPF += CalificacionPersona.CALIFICACION_4_NO_VALIDADO + ", ";
		    						}
		    						else if (iCalificacion == 5){
		    							calificacionesPF += CalificacionPersona.CALIFICACION_5_ENCONTRADO_IMSS + ", ";
		    						}
		    					}
		    				}
	    				}
						
	    		        try{
	    		        	int fin = calificacionesPF.lastIndexOf(",");
	    		        	calificacionesPF = calificacionesPF.substring(0, fin);
	    		        }catch(Exception e){
	    		        	this.log.error(e);
	    		        	calificacionesPF = "Sin calificaci\u00f3 a causa de una excepci\u00f3n: " + e.getMessage();
	    		        }
	    		        
	    		        tramite.getPersonaFisica().setSubEstadosFormateados(calificacionesPF);	    				
						
	    			}
	    		}

	    		//ASIGNA DESCRIPCION DE CALIFICACION
	    		if (tramite.getPersonaFisica().getSexo() != null){
		    		if (tramite.getPersonaFisica().getSexo().getIdSexo() != null){
		    			if (tramite.getPersonaFisica().getSexo().getIdSexo() == 1){
		    				tramite.getPersonaFisica().getSexo().setDescripcion("Hombre");
		    			}
		    			else{
		    				tramite.getPersonaFisica().getSexo().setDescripcion("Mujer");
		    			}
		    		}
	    		}
	    		
	    		//CORRIGE EL FORMATO DE LA FECHA DE NACIMIENTO
	            if( tramite.getPersonaFisica().getFechaNacimiento() != null) {
	            	tramite.getPersonaFisica().setFechaNacimientoFormateada(dateFormat.format(tramite.getPersonaFisica().getFechaNacimiento()));
	            }
	    	}

	    	//VALIDACIONES PARA PERSONA MORAL
	    	if (tramite.getPersonaMoral() != null){
	    		
	    		//ASIGNA DESCRIPCION DE CALIFICACION
	    		if (tramite.getPersonaMoral().getPersonaCalificaciones() != null){
	    			if (tramite.getPersonaMoral().getPersonaCalificaciones().size() > 0){
	    				if (tramite.getPersonaMoral().getPersonaCalificaciones().get(0).getCalificacion() != null){
	    					if (tramite.getPersonaMoral().getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion() != null){
	    						long iCalificacion = tramite.getPersonaMoral().getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion().longValue();
	    						if (iCalificacion == 1){
	    							tramite.getPersonaMoral().setSubEstadosFormateados(CalificacionPersona.CALIFICACION_1_VALIDADO_RENAPO);
	    						}
	    						else if (iCalificacion == 2){
	    							tramite.getPersonaMoral().setSubEstadosFormateados(CalificacionPersona.CALIFICACION_2_VALIDADO_SAT);
	    						}
	    						else if (iCalificacion == 3){
	    							tramite.getPersonaMoral().setSubEstadosFormateados(CalificacionPersona.CALIFICACION_3_VALIDADO_IMSS);
	    						}
	    						else if (iCalificacion == 4){
	    							tramite.getPersonaMoral().setSubEstadosFormateados(CalificacionPersona.CALIFICACION_4_NO_VALIDADO);
	    						}
	    						else if (iCalificacion == 5){
	    							tramite.getPersonaMoral().setSubEstadosFormateados(CalificacionPersona.CALIFICACION_5_ENCONTRADO_IMSS);
	    						}	    						
	    					}
	    				}
	    			}
	    		}
	    		
	    		//ASIGNA DESCRIPCION TIPO SOCIEDAD
	    		if( tramite.getPersonaMoral().getTipoSociedad() != null) {
	    			if( tramite.getPersonaMoral().getTipoSociedad().getIdTipoSociedad() != null) {
	    				TipoSociedad tipoSociedad = personaMoralEntity.getTipoSociedad(tramite.getPersonaMoral().getTipoSociedad().getIdTipoSociedad());
	    				tramite.getPersonaMoral().getTipoSociedad().setDescripcionAbreviada(tipoSociedad.getDescripcionAbreviada());
	    			}
	    		}

	    		//CORRIGE EL FORMATO DE LA FECHA DE CREACION
	            if( tramite.getPersonaMoral().getFechaCreacion() != null) {
	                tramite.getPersonaMoral().setFechaCreacionFormateada(dateFormat.format(tramite.getPersonaMoral().getFechaCreacion()));
	            }
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
        	e.printStackTrace();
        }
        parameters.put("IMSS_HEADER2_PARAM", image);
        final Date fechaImpresion = solicitud.getFechaRegistro();
        parameters.put("FECHA", dateFormat.format(fechaImpresion));
        parameters.put("HORA", timeFormat.format(fechaImpresion));
        parameters.put("FOLIO_OPER", solicitud.getIdSolicitud()); 
        parameters.put("NOMBRE_SOLICITANTE", solicitud.getUsuario() == null ? "" : solicitud.getUsuario());
        parameters.put("ESTATUS_OPER", "En Proceso");
        parameters.put("JUSTIFICACION_LEGAL", "El presente documento se ha generado conforme a las disposiciones legales establecidas por el Instituto Mexicano del Seguro Social");
                
		return parameters;
	}
}
