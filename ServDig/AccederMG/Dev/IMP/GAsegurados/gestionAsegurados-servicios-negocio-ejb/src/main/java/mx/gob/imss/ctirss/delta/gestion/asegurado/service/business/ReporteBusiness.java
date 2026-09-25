package mx.gob.imss.ctirss.delta.gestion.asegurado.service.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity.AseguradoEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ReporteBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility.ReporteHelperLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;

@Stateless(name = "reporteAseguradoBusiness", mappedName = "reporteAseguradoBusiness")
@Remote(value = ReporteBusinessRemote.class)
public class ReporteBusiness extends AbstractServiceBusiness implements ReporteBusinessRemote {

    @EJB(mappedName = "solicitudBusiness")
    private transient SolicitudBusinessRemote solicitudBusiness;
    @EJB
    private transient FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
    @EJB	
    private transient ReporteHelperLocal reporteHelper;
    @EJB
    private AseguradoEntityLocal aseguradoEntityLocal;

    @Override
	public byte[] getCredencialNSS(String nss) {
    	byte[] imagen = null;
    	log.debug("se generara la tarjeta del NSS: " + nss + " como imagen");
    	AsignacionNSS asignacion = null;
		asignacion = aseguradoEntityLocal.getAsignacionNSSParaTarjeta(nss);
		
		if(asignacion == null) {
			log.error("No se genero la tarjeta del nss : " + nss + " debido a que no se localizo");
			return imagen;
		}
		
		imagen = reporteHelper.reporteSimpleConQRImgane(asignacion);
		
		if(imagen != null) {
			log.debug("La tarjeta del NSS: " + nss + " como imagen fue generada correctamente");
		} else  {
			log.debug("No se tarjeta del NSS: " + nss + ", no se obtuvo el array de bytes");
		}
		return imagen;
	}



	@Override
	public byte[] getCredencialNSS(AsignacionNSS asignacion) {
		
		byte[] imagen = null;
		
		imagen = reporteHelper.reporteSimpleConQRImgane(asignacion);
		
		return imagen;
	}



	@Override
	public byte[] getComprobanteAsignacionSimpleConQR(Solicitud solicitud) {
		
  		OrigenSolicitudEnum solicitudOrigen = OrigenSolicitudEnum.getById(solicitud.getOrigenSolicitud().getIdTipoSolicitud());

    	 byte[] comprobante = null; // NOPMD
         String cadenaOriginal = null;
         
         if (solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
             log.debug("Id Tipo de tramite: " + solicitud.getTramites().get(0).getTipoTramite().getIdTipoTramite());
         }
         
         for(Tramite tramite: solicitud.getTramites()) {
         	if(tramite instanceof TramiteAsegurado) {
         		final TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
                 String rfc = tramiteAsegurado.getFisica().getRfc();
                 
                 if(solicitud.getSecuenciaDeNotaria() == null) {
                  	if (solicitudOrigen.equals(OrigenSolicitudEnum.PORTAL_IMSS_LLAVE)) {
                        cadenaOriginal = this.getCadenaOriginalLlave(tramiteAsegurado, solicitud.getFechaSolicitud(), solicitud.getNoFolioSolicitud(), false);

                	}
                	else {
                        cadenaOriginal = this.getCadenaOriginal(tramiteAsegurado, solicitud.getFechaSolicitud(), solicitud.getNoFolioSolicitud(), false);
                	}
                    RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal,null,rfc);
                    solicitud.setCadenaOriginal(cadenaOriginal);
                    
                    
                    if(selloDigital != null) {
                        solicitud.setSelloDigital(selloDigital.getSello());
                        solicitud.setSecuenciaDeNotaria(selloDigital.getTramite());
                        solicitud.setNumeroSerieCertificado(selloDigital.getNoSerie());
                        
                        //Se crea el objeto de firma digital
                        FirmaElectronica firmaElectronica = new FirmaElectronica();
                        firmaElectronica.setCadenaOriginal(cadenaOriginal);
                        firmaElectronica.setReciboNotarial(selloDigital.getTramite());
                        firmaElectronica.setSecuenciaNotaria(selloDigital.getTramite());
                        firmaElectronica.setSerialCertificado(selloDigital.getNoSerie());
                        firmaElectronica.setRecibo(selloDigital.getSello());
                        firmaElectronica.setUrlAcuseFirma("");
                        firmaElectronica.setIniciaVigenciaCertificado(new Date());
                        firmaElectronica.setFinVigenciaCertificado(new Date());
                        log.error("Sello Digital: " + selloDigital);
                        firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
                    }
                 }
                 
                 log.error("Cadena Original: " + cadenaOriginal);
                
                 log.error("Id persona: " + (tramiteAsegurado.getFisica() == null ? "" : tramiteAsegurado.getFisica().getIdPersona()));
                 comprobante = reporteHelper.reporteSimpleConQR(solicitud);
                 
                 if(solicitud.getSecuenciaDeNotaria() != null) {
                 	firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "tarjetaNSS.pdf", comprobante);
                 }
         	}
         }
         
         return comprobante;
	}



	@Override
	public byte[] getComprobanteAsignacion(Solicitud solicitud)
			throws SolicitudNoEncontradaException {
    	 byte[] comprobante = null; // NOPMD
         String cadenaOriginal = null;
         
  		OrigenSolicitudEnum solicitudOrigen = OrigenSolicitudEnum.getById(solicitud.getOrigenSolicitud().getIdTipoSolicitud());
         
         if (solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
             log.debug("Id Tipo de tramite: " + solicitud.getTramites().get(0).getTipoTramite().getIdTipoTramite());
         }
         
         for(Tramite tramite: solicitud.getTramites()) {
         	if(tramite instanceof TramiteAsegurado) {
         		final TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
                 String rfc = tramiteAsegurado.getFisica().getRfc();
                 
                 if(solicitud.getSecuenciaDeNotaria() == null) {
                 	if (solicitudOrigen.equals(OrigenSolicitudEnum.PORTAL_IMSS_LLAVE)) {
                        cadenaOriginal = this.getCadenaOriginalLlave(tramiteAsegurado, solicitud.getFechaSolicitud(), solicitud.getNoFolioSolicitud(), false);
                	}
                	else {
                        cadenaOriginal = this.getCadenaOriginal(tramiteAsegurado, solicitud.getFechaSolicitud(), solicitud.getNoFolioSolicitud(), false);
                	}
                 	RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal,null,rfc);
                    solicitud.setCadenaOriginal(cadenaOriginal);
                    
                    
                    if(selloDigital != null) {
                        solicitud.setSelloDigital(selloDigital.getSello());
                        solicitud.setSecuenciaDeNotaria(selloDigital.getTramite());
                        solicitud.setNumeroSerieCertificado(selloDigital.getNoSerie());
                        
                        //Se crea el objeto de firma digital
                        FirmaElectronica firmaElectronica = new FirmaElectronica();
                        firmaElectronica.setCadenaOriginal(cadenaOriginal);
                        firmaElectronica.setReciboNotarial(selloDigital.getTramite());
                        firmaElectronica.setSecuenciaNotaria(selloDigital.getTramite());
                        firmaElectronica.setSerialCertificado(selloDigital.getNoSerie());
                        firmaElectronica.setRecibo(selloDigital.getSello());
                        firmaElectronica.setUrlAcuseFirma("");
                        firmaElectronica.setIniciaVigenciaCertificado(new Date());
                        firmaElectronica.setFinVigenciaCertificado(new Date());
                        log.error("Sello Digital: " + selloDigital);
                        firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
                    }
                 }
                 
                 log.error("Cadena Original: " + cadenaOriginal);
                
                 log.error("Id persona: " + (tramiteAsegurado.getFisica() == null ? "" : tramiteAsegurado.getFisica().getIdPersona()));
                 comprobante = reporteHelper.reporte(solicitud);
                 
                 if(solicitud.getSecuenciaDeNotaria() != null) {
                 	firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "comprobanteAsignacionNss.pdf", comprobante);
                 }
         	}
         }
         
         return comprobante;
	}



	/**
     * @param folioSolicitud
     * @return
     * @throws SolicitudNoEncontradaException
     */
    @Override
    public byte[] getComprobanteOperacion(final Long folioSolicitud) throws SolicitudNoEncontradaException {
        byte[] comprobante = null; // NOPMD
        String cadenaOriginal = null;
        if (folioSolicitud == null) {
            log.debug("El folioSolicitud debe ser no nulo");
        } else {
            final Solicitud solicitud = solicitudBusiness.consultar(new Solicitud(folioSolicitud));
            
            
            if (solicitud == null) {
                throw new SolicitudNoEncontradaException(folioSolicitud);
            } else {
                if (solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
                    log.debug("Id Tipo de tramite: " + solicitud.getTramites().get(0).getTipoTramite().getIdTipoTramite());
                }
                
                for(Tramite tramite: solicitud.getTramites()) {
                	if(tramite instanceof TramiteAsegurado) {
                		final TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
                        String rfc = tramiteAsegurado.getFisica().getRfc();
                        
                        if(solicitud.getSecuenciaDeNotaria() == null) {
	                        cadenaOriginal = this.getCadenaOriginal(tramiteAsegurado, solicitud.getFechaSolicitud(), solicitud.getNoFolioSolicitud(), false);
	                        RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal,null,rfc);
	                        solicitud.setCadenaOriginal(cadenaOriginal);
	                        
	                        
	                        if(selloDigital != null) {
		                        solicitud.setSelloDigital(selloDigital.getSello());
		                        solicitud.setSecuenciaDeNotaria(selloDigital.getTramite());
		                        solicitud.setNumeroSerieCertificado(selloDigital.getNoSerie());
		                        
		                        //Se crea el objeto de firma digital
		                        FirmaElectronica firmaElectronica = new FirmaElectronica();
		                        firmaElectronica.setCadenaOriginal(cadenaOriginal);
		                        firmaElectronica.setReciboNotarial(selloDigital.getTramite());
		                        firmaElectronica.setSecuenciaNotaria(selloDigital.getTramite());
		                        firmaElectronica.setSerialCertificado(selloDigital.getNoSerie());
		                        firmaElectronica.setRecibo(selloDigital.getSello());
		                        firmaElectronica.setUrlAcuseFirma("");
		                        firmaElectronica.setIniciaVigenciaCertificado(new Date());
		                        firmaElectronica.setFinVigenciaCertificado(new Date());
		                        log.error("Sello Digital: " + selloDigital);
		                        firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
	                        }
                        }
                        
                        log.error("Cadena Original: " + cadenaOriginal);
                       
                        log.error("Id persona: " + (tramiteAsegurado.getFisica() == null ? "" : tramiteAsegurado.getFisica().getIdPersona()));
                        comprobante = reporteHelper.reporte(solicitud);
                        
                        if(solicitud.getSecuenciaDeNotaria() != null) {
                        	firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "comprobanteAsignacionNss.pdf", comprobante);
                        }
                	}
                }
            }
        }
        return comprobante;
    }

    

	@Override
	public byte[] getComprobanteRecuperacion(Solicitud solicitud)
			throws SolicitudNoEncontradaException {
  		OrigenSolicitudEnum solicitudOrigen = OrigenSolicitudEnum.getById(solicitud.getOrigenSolicitud().getIdTipoSolicitud());
		byte[] comprobante = null; // NOPMD
        String cadenaOriginal = null;
            
            if (solicitud == null) {
                throw new SolicitudNoEncontradaException("");
            } else {
                if (solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
                    log.debug("Id Tipo de tramite: " + solicitud.getTramites().get(0).getTipoTramite().getIdTipoTramite());
                }
                
                for(Tramite tramite: solicitud.getTramites()) {
                	if(tramite instanceof TramiteAsegurado) {
                		final TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
                        String rfc = tramiteAsegurado.getFisica().getRfc();
                        
                        if(solicitud.getSecuenciaDeNotaria() == null) {
                        	if (solicitudOrigen.equals(OrigenSolicitudEnum.PORTAL_IMSS_LLAVE)) {
                            	cadenaOriginal = this.getCadenaOriginalLlave(tramiteAsegurado, solicitud.getFechaSolicitud(), solicitud.getNoFolioSolicitud(), true);
                        	}
                        	else {
                            	cadenaOriginal = this.getCadenaOriginal(tramiteAsegurado, solicitud.getFechaSolicitud(), solicitud.getNoFolioSolicitud(), true);
                        	}
                            RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal,null,rfc);
	                        solicitud.setCadenaOriginal(cadenaOriginal);
	                        
	                        
	                        if(selloDigital != null) {
		                        solicitud.setSelloDigital(selloDigital.getSello());
		                        solicitud.setSecuenciaDeNotaria(selloDigital.getTramite());
		                        solicitud.setNumeroSerieCertificado(selloDigital.getNoSerie());
		                        
		                        //Se crea el objeto de firma digital
		                        FirmaElectronica firmaElectronica = new FirmaElectronica();
		                        firmaElectronica.setCadenaOriginal(cadenaOriginal);
		                        firmaElectronica.setReciboNotarial(selloDigital.getTramite());
		                        firmaElectronica.setSecuenciaNotaria(selloDigital.getTramite());
		                        firmaElectronica.setSerialCertificado(selloDigital.getNoSerie());
		                        firmaElectronica.setRecibo(selloDigital.getSello());
		                        firmaElectronica.setUrlAcuseFirma("");
		                        firmaElectronica.setIniciaVigenciaCertificado(new Date());
		                        firmaElectronica.setFinVigenciaCertificado(new Date());
		                        log.error("Sello Digital: " + selloDigital);
		                        firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
	                        }
                        }
                        
                        log.error("Cadena Original: " + cadenaOriginal);
                       
                        log.error("Id persona: " + (tramiteAsegurado.getFisica() == null ? "" : tramiteAsegurado.getFisica().getIdPersona()));
                        comprobante = reporteHelper.reporteLocalizacionNss(solicitud);
                        
                        if(solicitud.getSecuenciaDeNotaria() != null) {
                        	firmaDigitalBusinessRemote.guardarArchivoFirmado(solicitud.getSecuenciaDeNotaria(), "comprobanteAsignacionNss.pdf", comprobante);
                        }
                	}
                }
            }
        
        return comprobante;
	}



	@Override
	public Map<String, Object> getReportModel(Long folioSolicitud)throws SolicitudNoEncontradaException {
		Map<String, Object> repModel = null; // NOPMD
		String cadenaOriginal=null;
		
        if (folioSolicitud == null) {
            log.debug("El folioSolicitud debe ser no nulo");
        } else {
            final Solicitud solicitud = solicitudBusiness.consultar(new Solicitud(folioSolicitud));
            if (solicitud == null) {
                throw new SolicitudNoEncontradaException(folioSolicitud);
            } else {

				for (Tramite tramite : solicitud.getTramites()) {
					if (tramite instanceof TramiteAsegurado) {
						final TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
						String rfc = tramiteAsegurado.getFisica().getRfc();
						
						if(solicitud.getSecuenciaDeNotaria() == null) {
							cadenaOriginal = this.getCadenaOriginal(tramiteAsegurado, solicitud.getFechaSolicitud(), solicitud.getNoFolioSolicitud(), false);
							RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal,null,rfc);
							solicitud.setCadenaOriginal(cadenaOriginal);


							if(selloDigital != null) {
								solicitud.setSelloDigital(selloDigital.getSello());
								solicitud.setSecuenciaDeNotaria(selloDigital.getTramite());
								solicitud.setNumeroSerieCertificado(selloDigital.getNoSerie());

								//Se crea el objeto de firma digital
								FirmaElectronica firmaElectronica = new FirmaElectronica();
								firmaElectronica.setCadenaOriginal(cadenaOriginal);
								firmaElectronica.setReciboNotarial(selloDigital.getTramite());
								firmaElectronica.setSecuenciaNotaria(selloDigital.getTramite());
								firmaElectronica.setSerialCertificado(selloDigital.getNoSerie());
								firmaElectronica.setRecibo(selloDigital.getSello());
								firmaElectronica.setUrlAcuseFirma("");
								firmaElectronica.setIniciaVigenciaCertificado(new Date());
								firmaElectronica.setFinVigenciaCertificado(new Date());
								log.error("Sello Digital: " + selloDigital);
								firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
							}
						}
						
						log.debug("Id persona: " + tramiteAsegurado.getFisica() == null ? null
								: tramiteAsegurado.getFisica().getIdPersona());
						repModel = reporteHelper.getModel(solicitud);
					}

				}

				/*
				 * Se quita la imagen del mapa que se devuelve, ya que el
				 * reporte requiere un InputStream y como el InputStream no es
				 * serializable no se puede regresar dentro del mapa.
				 */
				repModel.remove("IMSS_HEADER_PARAM");
			}
        }
        return repModel;
    }
	
	@Override
	public Map<String, Object> getReportModelRecuperado(TramiteAsegurado tramiteAsegurado,Solicitud solicitud) {
		Map<String, Object> repModel = null;
         
		String cadenaOriginal = this.getCadenaOriginal(tramiteAsegurado, solicitud.getFechaPresentacion(),solicitud.getNoFolioSolicitud(), true);
		RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal,null,tramiteAsegurado.getFisica().getRfc());
		
		String selloD = "";
		String secuencia = "";
		String serie = "";
		
		if(selloDigital != null) {
			selloD = selloDigital.getSello();
			secuencia = selloDigital.getId();
			serie = selloDigital.getNoSerie();
		}
		repModel = reporteHelper.getModelRecuperado(tramiteAsegurado, cadenaOriginal, selloD, secuencia, serie, solicitud);
            	
    	/* 
    	 * Se quita la imagen del mapa que se devuelve, ya que el reporte
    	 * requiere un InputStream y como el InputStream no es serializable
    	 * no se puede regresar dentro del mapa.
    	 */
        repModel.remove("IMSS_HEADER_PARAM");
        
        return repModel;
    }
	
	
    @Override
	public byte[] getComprobanteInterno(Long folioSolicitud)
			throws SolicitudNoEncontradaException {
    	
		return null;
	}


	/**
	 * 191807 200912 Este metodo es similar al de arriba (el cual procesa los
	 * internos o por ventanilla), solo que este procesa el reporte PDF externo
	 * (por internet)
	 * 
	 * @param folioSolicitud
	 * @return
	 * @throws SolicitudNoEncontradaException
	 */
    public Map<String, Object> getReportModelExterno(Long folioSolicitud) throws SolicitudNoEncontradaException{
		Map<String, Object> repModel = null; // NOPMD
        if (folioSolicitud == null) {
            log.debug("El folioSolicitud debe ser no nulo");
        } else {
            final Solicitud solicitud = solicitudBusiness.consultar(new Solicitud(folioSolicitud));
            if (solicitud == null) {
                throw new SolicitudNoEncontradaException(folioSolicitud);
            } else {
            	
            	for (Tramite tramite : solicitud.getTramites()) {
					if (tramite instanceof TramiteAsegurado) {
						  final TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
		                    
		                    String cadenaOriginal = this.getCadenaOriginal(tramiteAsegurado, solicitud.getFechaSolicitud(),solicitud.getNoFolioSolicitud(), false);
		                    RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal, null, tramiteAsegurado.getFisica().getRfc());
		                    if(selloDigital != null) {
								 solicitud.setSelloDigital(selloDigital.getSello());
								 solicitud.setSecuenciaDeNotaria(selloDigital.getId());
								 solicitud.setNumeroSerieCertificado(selloDigital.getNoSerie());
		                     }
		                    log.debug("Id persona: " + tramiteAsegurado.getFisica() == null ? null : tramiteAsegurado.getFisica().getIdPersona());
		                    repModel = reporteHelper.getModelExterno(solicitud);
					}
				}
            }
        }
        return repModel;
    }
    
    private String getCadenaOriginal(TramiteAsegurado tramite,Date fecha, String folioSolicitud,Boolean recuperado) {
    	
    	Locale locMEX = new Locale("es", "MX");
    	
    	String nombre = tramite.getFisica().getNombre() != null ? tramite.getFisica().getNombre().trim() : "";
    	String apellidoP = tramite.getFisica().getPrimerApellido() != null ? tramite.getFisica().getPrimerApellido().trim() : "";
    	String apellidoM = tramite.getFisica().getSegundoApellido() != null ? tramite.getFisica().getSegundoApellido().trim() : "";
    	String nombreCompleto = nombre +" "+ apellidoP +" "+ apellidoM;
    	
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss",locMEX);
    	String cadenaOriginal = "||Invocante:portalimssdigital";
    	cadenaOriginal += "|Tipo de tr\u00E1mite:" + (recuperado ? "Localizaci\u00F3n de NSS" : "Asignaci\u00F3n de NSS");
    	cadenaOriginal += "|Fecha:"+sdf.format(fecha);
    	cadenaOriginal += "|Folio:"+folioSolicitud;
    	cadenaOriginal += "|RFC:"+(tramite.getFisica().getRfc() != null ? tramite.getFisica().getRfc() : "");
    	cadenaOriginal += "|Nombre o Razon Social:"+nombreCompleto;
    	cadenaOriginal += "|Curp:"+(tramite.getFisica().getCurp() != null ? tramite.getFisica().getCurp() : "");
    	cadenaOriginal += "|N\u00FAmero Registro Patronal:";
    	cadenaOriginal += "|N\u00FAmero de Seguridad Social:"+tramite.getFisica().getNss()+"||";
    	
    	return cadenaOriginal;
    }

    
    private String getCadenaOriginalLlave(TramiteAsegurado tramite,Date fecha, String folioSolicitud,Boolean recuperado) {
    	
    	
    	
    	Locale locMEX = new Locale("es", "MX");
    	
    	String nombre = tramite.getFisica().getNombre() != null ? tramite.getFisica().getNombre().trim() : "";
    	String apellidoP = tramite.getFisica().getPrimerApellido() != null ? tramite.getFisica().getPrimerApellido().trim() : "";
    	String apellidoM = tramite.getFisica().getSegundoApellido() != null ? tramite.getFisica().getSegundoApellido().trim() : "";
    	String nombreCompleto = nombre +" "+ apellidoP +" "+ apellidoM;
    	
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss",locMEX);
    	String cadenaOriginal = "||Invocante:Llave (LI)";
    	cadenaOriginal += "|Tipo de tr\u00E1mite:" + (recuperado ? "Localizaci\u00F3n de NSS" : "Asignaci\u00F3n de NSS");
    	cadenaOriginal += "|Fecha:"+sdf.format(fecha);
    	cadenaOriginal += "|Folio:"+folioSolicitud;
    	cadenaOriginal += "|RFC:"+(tramite.getFisica().getRfc() != null ? tramite.getFisica().getRfc() : "");
    	cadenaOriginal += "|Nombre o Razon Social:"+nombreCompleto;
    	cadenaOriginal += "|Curp:"+(tramite.getFisica().getCurp() != null ? tramite.getFisica().getCurp() : "");
    	cadenaOriginal += "|N\u00FAmero Registro Patronal:";
    	cadenaOriginal += "|N\u00FAmero de Seguridad Social:"+tramite.getFisica().getNss()+"||";
    	
    	return cadenaOriginal;
    }


	@Override
	public byte[] getComprobanteVacio() {
		return reporteHelper.getReporteVacio();
	}
    
    

}
