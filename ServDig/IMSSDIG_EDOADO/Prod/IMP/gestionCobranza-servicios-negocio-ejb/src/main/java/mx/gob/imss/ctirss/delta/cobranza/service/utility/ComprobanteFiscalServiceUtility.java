package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceComprobanteFiscalException;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.enums.RegimenFiscalEnum;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.Comprobante;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdiV4.ComprobanteV4;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.Concepto;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.Impuesto;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.Pago;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.TimbreFiscalDigital;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.pago.PagoType;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;
import mx.gob.imss.service.pagoscfdi.implementacion.ClienteWebserviceComprobanteFiscal;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

@Stateless(name = "comprobanteFiscalServiceUtility", mappedName = "comprobanteFiscalServiceUtility")
public class ComprobanteFiscalServiceUtility extends AbstractServiceUtility
		implements ComprobanteFiscalServiceUtilityLocal {

	private static final String PATH_SUBREPORTES_FACTURAE 	= "reportes/factura/";
	private static final String PATH_REPORTE_FACTURAE 		= PATH_SUBREPORTES_FACTURAE+"FacturaElectronica.jasper";
    private static final String PATH_REPORTE_FACTURA_V4 		= PATH_SUBREPORTES_FACTURAE+"FacturaElectronica_v4.jasper";
	

	@Override
	public List<Pago> obtenerPagos(Pago pagoFiscal) throws ClienteWebserviceComprobanteFiscalException {
		ClienteWebserviceComprobanteFiscal clienteWs = new ClienteWebserviceComprobanteFiscal();

		log.info("obtenerPagos inicio");
		
		
		List<Pago> listaPago = clienteWs.obtenerComprobanteFiscal(pagoFiscal.getPeriodo(),
				pagoFiscal.getNumeroRegistroPatronal());
		
		if( listaPago!=null && !CollectionUtils.isEmpty(listaPago)){
			
			
			log.info("obtenerPagos regreso del WS: "+listaPago.size());
			
			for(Pago pago : listaPago){
				pago.setNumeroRegistroPatronal(pagoFiscal.getNumeroRegistroPatronal());
				pago.setRfc(pagoFiscal.getRfc());
				pago.setPeriodo(pagoFiscal.getPeriodo());
				
				Integer er = pago.getEntidadRecaudadora();
				log.info("Mm cambio 1: ER "+er);
				
				String xmlComprobanteFiscal;
				String xmlComprobante = pago.getXmlComprobante();
				
				log.info("------------------>Pago WsComprobanteFiscal: " + xmlComprobante);
				// Se elimina tag de cometario (XML)
				if (xmlComprobante.startsWith("<![CDATA[")) {
					String xmlPagoFiscalSinFormato = xmlComprobante.substring(9, xmlComprobante.length() - 3);
					
					log.info("------------------>Pago Respuesta (Sin Formato): " + xmlPagoFiscalSinFormato);
					String xmlPagoFiscal = unificarFormatoXml(xmlPagoFiscalSinFormato);
					PagoType pagoRespuesta = (PagoType) JaxbUtil.xmlToObject(xmlPagoFiscal);
				if(StringUtils.isBlank(pagoFiscal.getRfc())){
					pago.setRfc(pagoRespuesta.getRfc());
				}

					String strFechaPago = pagoRespuesta.getFechaPago();
					if (StringUtils.isNotBlank(strFechaPago )) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date fechaPago = sdf.parse(strFechaPago);
							pago.setFechaPago(fechaPago);
						} catch (ParseException e) {
							log.error("Formato de fecha de pago invalida. Se mandara vacia");
						}
					}

					xmlComprobanteFiscal = pagoRespuesta.getXmlTimbradoSAT();
				} else {
					xmlComprobanteFiscal = xmlComprobante;
				}

				log.info("------------------>XML Comprobante: " + xmlComprobanteFiscal);
				if (StringUtils.isNotBlank(xmlComprobanteFiscal)) {
					System.out.println("----------------->" +  xmlComprobanteFiscal);
					
					int posicionVersion32 = xmlComprobanteFiscal.indexOf("version");
					int posicionVersion33 = xmlComprobanteFiscal.indexOf("Version");
					
					String valorVersion = null;
					
					if (posicionVersion32 != -1) {
						System.out.println("Version CDFI 3.2");
						valorVersion = xmlComprobanteFiscal.substring(posicionVersion32 + 9, posicionVersion32 + 9 + 3);
					} else if (posicionVersion33 != -1) {
						System.out.println("Version CDFI 3.3 y 4.0");
						valorVersion = xmlComprobanteFiscal.substring(posicionVersion33 + 9, posicionVersion33 + 9 + 3);
					}
					
					if (valorVersion.equals("3.2")) {
						// Convierte el XML al objeto Pago
						Comprobante comprobante32 = (Comprobante) mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.xmlToObject(xmlComprobanteFiscal);
						
						// Atributos del Comprobante
						pago.setLugarExpedicion(comprobante32.getLugarExpedicion());
						pago.setMetodoDePago(comprobante32.getMetodoDePago());
						pago.setFormaDePago(comprobante32.getFormaDePago());
						pago.setTotal(Double.valueOf(comprobante32.getTotal().doubleValue()));
						pago.setTotalConLetra(Utilerias.totalConLetras(comprobante32.getTotal()));
						pago.setSubTotal(comprobante32.getSubTotal().doubleValue());
						//pago.setSerieCertificadoSAT(comprobante32.getCertificado());
						//pago.setSelloSAT(comprobante32.getSello());
						pago.setFechaHoraEmision(comprobante32.getFecha().toString());
						pago.setSerieCertificadoCSD(comprobante32.getNoCertificado());
						pago.setSelloDigitalCFDI(comprobante32.getSello());

						// Emisor
						pago.setRfcEmisor(comprobante32.getEmisor().getRfc());
						pago.setRegimenFiscal(comprobante32.getEmisor().getRegimenFiscal().get(0).getRegimen());
						
						// Receptor
						pago.setRfcReceptor(comprobante32.getReceptor().getRfc());

						// Conceptos
						List<Concepto> listConceptos = new ArrayList<Concepto>();
						for (Comprobante.Conceptos.Concepto item : comprobante32.getConceptos().getConcepto()) {
							Concepto concepto = new Concepto();
							concepto.setImporte(item.getImporte().doubleValue());
							concepto.setValorUnitario(item.getValorUnitario().doubleValue());
							concepto.setDescripcion(item.getDescripcion());
							concepto.setUnidad(item.getUnidad());
							concepto.setCantidad(item.getCantidad().intValue());
							listConceptos.add(concepto);
						}
						
						// Impuesto retenidos
						List<Impuesto> listImpRet = new ArrayList<Impuesto>();
						if (comprobante32.getImpuestos().getRetenciones() != null
								&& comprobante32.getImpuestos().getRetenciones().getRetencion() != null) {
							for (Comprobante.Impuestos.Retenciones.Retencion impReten : comprobante32
									.getImpuestos().getRetenciones().getRetencion()) {
								Impuesto impuesto = new Impuesto();
								impuesto.setImporte(impReten.getImporte().doubleValue());
								impuesto.setDescripcion(impReten.getImpuesto());

								listImpRet.add(impuesto); 
							}
						}
						
						// Impuesto traslados
						List<Impuesto> listImpTras = new ArrayList<Impuesto>();
						if (comprobante32.getImpuestos().getTraslados() != null
								&& comprobante32.getImpuestos().getTraslados().getTraslado() != null) {
							for (Comprobante.Impuestos.Traslados.Traslado impTras : comprobante32
									.getImpuestos().getTraslados().getTraslado()) {
								Impuesto impuesto = new Impuesto();
								impuesto.setImporte(impTras.getImporte().doubleValue());
								impuesto.setDescripcion(impTras.getImpuesto());

								listImpTras.add(impuesto);
							}
						}
						
						//Timbre Fiscal
						if (comprobante32.getComplemento() != null
								&& comprobante32.getComplemento().getAny() != null
								&& !comprobante32.getComplemento().getAny().isEmpty()) {
							try {
								List listComplemento = comprobante32.getComplemento().getAny();
								TimbreFiscalDigital timbreFiscal = (TimbreFiscalDigital) listComplemento.get(0);

								pago.setFolioFiscal(timbreFiscal.getUUID());
								pago.setSelloSAT(timbreFiscal.getSelloSAT());								
								pago.setSerieCertificadoSAT(timbreFiscal.getNoCertificadoSAT());
								pago.setFechaHoraCertificacion(timbreFiscal.getFechaTimbrado().toString());
								
								StringBuffer sbCadenaOriginalSat = new StringBuffer();
								sbCadenaOriginalSat.append("||")
										.append(timbreFiscal.getVersion()).append("|")
										.append(timbreFiscal.getUUID()).append("|")
										.append(timbreFiscal.getFechaTimbrado()).append("|")
										.append(timbreFiscal.getSelloCFD()).append("|")
										.append(timbreFiscal.getNoCertificadoSAT()).append("||");
								pago.setCadenaOriginalSAT(sbCadenaOriginalSat.toString());
							} catch (Exception e) {
								log.error(e);
							}
						}
						
						pago.setConceptos(listConceptos);
						pago.setImpuestosRetenidos(listImpRet);
						pago.setImpuestosTrasladados(listImpTras);
					
						xmlComprobanteFiscal = xmlComprobanteFiscal.replaceAll("&amp;", "&");
						String rfcReceptorSinFormato =  pago.getRfcReceptor();
						pago.setRfcReceptor(rfcReceptorSinFormato.replaceAll("&amp;", "&"));

						if(!StringUtils.contains(xmlComprobanteFiscal, "<?xml version=\"1.0\"")){
							xmlComprobanteFiscal = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xmlComprobanteFiscal;
						} 
						pago.setXmlComprobante(xmlComprobanteFiscal);
						
					} else if (valorVersion.equals("3.3") ) {
						log.info("ver 3.3");
						// Convierte el XML al objeto Pago
						Comprobante comprobante = (Comprobante) mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.xmlToObject(xmlComprobanteFiscal);

						// Atributos del Comprobante
						pago.setLugarExpedicion(comprobante.getLugarExpedicion());
						pago.setMetodoDePago(comprobante.getMetodoDePago33());
						pago.setFormaDePago(comprobante.getFormaDePago33());
						pago.setTotal(Double.valueOf(comprobante.getTotal33().doubleValue()));
						pago.setTotalConLetra(Utilerias.totalConLetras(comprobante.getTotal33()));
						pago.setSubTotal(comprobante.getSubTotal33().doubleValue());
						//pago.setSerieCertificadoSAT(comprobante.getCertificado());
						//pago.setSelloSAT(comprobante.getSello());
						pago.setFechaHoraEmision(comprobante.getFecha33().toString());
						pago.setSerieCertificadoCSD(comprobante.getNoCertificado33());
						pago.setSelloDigitalCFDI(comprobante.getSello33());

						// Emisor
						pago.setRfcEmisor(comprobante.getEmisor().getRfc33());
						pago.setRegimenFiscal(comprobante.getEmisor().getRegimenFiscal33());

						// Receptor
						pago.setRfcReceptor(comprobante.getReceptor().getRfc33());

						// Conceptos
						List<Concepto> listConceptos = new ArrayList<Concepto>();
						for (Comprobante.Conceptos.Concepto item : comprobante.getConceptos().getConcepto()) {
							Concepto concepto = new Concepto();
							concepto.setImporte(item.getImporte33().doubleValue());
							concepto.setValorUnitario(item.getValorUnitario33().doubleValue());
							concepto.setDescripcion(item.getDescripcion33());
							concepto.setUnidad(item.getUnidad33());
							concepto.setCantidad(item.getCantidad33().intValue());
							listConceptos.add(concepto);
						}
						
						// Impuesto retenidos
						List<Impuesto> listImpRet = new ArrayList<Impuesto>();
						if (comprobante.getImpuestos().getRetenciones() != null
								&& comprobante.getImpuestos().getRetenciones().getRetencion() != null) {
							for (Comprobante.Impuestos.Retenciones.Retencion impReten : comprobante
									.getImpuestos().getRetenciones().getRetencion()) {
								Impuesto impuesto = new Impuesto();
								impuesto.setImporte(impReten.getImporte().doubleValue());
								impuesto.setDescripcion(impReten.getImpuesto());

								listImpRet.add(impuesto); 
							}
						}
						
						// Impuesto traslados
						List<Impuesto> listImpTras = new ArrayList<Impuesto>();
						if (comprobante.getImpuestos().getTraslados() != null
								&& comprobante.getImpuestos().getTraslados().getTraslado() != null) {
							for (Comprobante.Impuestos.Traslados.Traslado impTras : comprobante
									.getImpuestos().getTraslados().getTraslado()) {
								Impuesto impuesto = new Impuesto();
								impuesto.setImporte(impTras.getImporte().doubleValue());
								impuesto.setDescripcion(impTras.getImpuesto());

								listImpTras.add(impuesto);
							}
						}
						
						//Timbre Fiscal
						if (comprobante.getComplemento() != null
								&& comprobante.getComplemento().getAny() != null
								&& !comprobante.getComplemento().getAny().isEmpty()) {
							try {
								List listComplemento = comprobante.getComplemento().getAny();
								TimbreFiscalDigital timbreFiscal = (TimbreFiscalDigital) listComplemento.get(0);

								pago.setFolioFiscal(timbreFiscal.getUUID());
								pago.setSelloSAT(timbreFiscal.getSelloSAT33());
								pago.setSerieCertificadoSAT(timbreFiscal.getNoCertificadoSAT33());
								pago.setFechaHoraCertificacion(timbreFiscal.getFechaTimbrado().toString());
								
								StringBuffer sbCadenaOriginalSat = new StringBuffer();
								sbCadenaOriginalSat.append("||")
										.append(timbreFiscal.getVersion()).append("|")
										.append(timbreFiscal.getUUID()).append("|")
										.append(timbreFiscal.getFechaTimbrado()).append("|")
										.append(timbreFiscal.getSelloCFD33()).append("|")
										.append(timbreFiscal.getNoCertificadoSAT()).append("||");
								pago.setCadenaOriginalSAT(sbCadenaOriginalSat.toString());
							} catch (Exception e) {
								log.error(e);
							}
						}
						
						pago.setConceptos(listConceptos);
						pago.setImpuestosRetenidos(listImpRet);
						pago.setImpuestosTrasladados(listImpTras);
					
						xmlComprobanteFiscal = xmlComprobanteFiscal.replaceAll("&amp;", "&");
						String rfcReceptorSinFormato =  pago.getRfcReceptor();
						pago.setRfcReceptor(rfcReceptorSinFormato.replaceAll("&amp;", "&"));

						if(!StringUtils.contains(xmlComprobanteFiscal, "<?xml version=\"1.0\"")){
							xmlComprobanteFiscal = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xmlComprobanteFiscal;
						} 
						pago.setXmlComprobante(xmlComprobanteFiscal);


					}else if (valorVersion.equals("4.0")) {
                        log.info("nueva version 4");

                        String xmlComprobanteFiscalV4 = xmlComprobanteFiscal.replace("Comprobante","ComprobanteV4");
                        log.info("xmlV4: "+xmlComprobanteFiscalV4);
                        // Convierte el XML al objeto Pago
                        ComprobanteV4 comprobante = (ComprobanteV4) mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.xmlToObject(xmlComprobanteFiscalV4);

                        // Atributos del Comprobante
                        pago.setLugarExpedicion(comprobante.getLugarExpedicion());
                        pago.setMetodoDePago(comprobante.getMetodoDePago33());
                        pago.setFormaDePago(comprobante.getFormaDePago33());
                        pago.setTotal(Double.valueOf(comprobante.getTotal33().doubleValue()));
                        pago.setTotalConLetra(Utilerias.totalConLetras(comprobante.getTotal33()));
                        pago.setSubTotal(comprobante.getSubTotal33().doubleValue());
                        //pago.setSerieCertificadoSAT(comprobante.getCertificado());
                        //pago.setSelloSAT(comprobante.getSello());
                        pago.setFechaHoraEmision(comprobante.getFecha33().toString());
                        pago.setSerieCertificadoCSD(comprobante.getNoCertificado33());
                        pago.setSelloDigitalCFDI(comprobante.getSello33());

                        // Emisor
                        pago.setRfcEmisor(comprobante.getEmisor().getRfc33());
                        pago.setRegimenFiscal(comprobante.getEmisor().getRegimenFiscal33());

                        // Receptor
                        pago.setRfcReceptor(comprobante.getReceptor().getRfc33());

                        // Conceptos
                        List<Concepto> listConceptos = new ArrayList<Concepto>();
                        for (ComprobanteV4.Conceptos.Concepto item : comprobante.getConceptos().getConcepto()) {
                            Concepto concepto = new Concepto();
                            concepto.setImporte(item.getImporte33().doubleValue());
                            concepto.setValorUnitario(item.getValorUnitario33().doubleValue());
                            
                            log.info("Mm cambio 1: er "+er);
                            if(er!=null && er.equals(0)) {
                            	concepto.setDescripcion("Pago diferente a cuotas obrero patronales");
                            }else {
                            	concepto.setDescripcion(item.getDescripcion33());
                            }
                            
                            concepto.setUnidad(item.getUnidad33());
                            concepto.setCantidad(item.getCantidad33().intValue());
                            listConceptos.add(concepto);
                        }

                        // Impuesto retenidos
                        List<Impuesto> listImpRet = new ArrayList<Impuesto>();
                        if (comprobante.getImpuestos().getRetenciones() != null
                                && comprobante.getImpuestos().getRetenciones().getRetencion() != null) {
                            for (ComprobanteV4.Impuestos.Retenciones.Retencion impReten : comprobante
                                    .getImpuestos().getRetenciones().getRetencion()) {
                                Impuesto impuesto = new Impuesto();
                                impuesto.setImporte(impReten.getImporte().doubleValue());
                                impuesto.setDescripcion(impReten.getImpuesto());

                                listImpRet.add(impuesto);
                            }
                        }

                        // Impuesto traslados
                        List<Impuesto> listImpTras = new ArrayList<Impuesto>();
                        if (comprobante.getImpuestos().getTraslados() != null
                                && comprobante.getImpuestos().getTraslados().getTraslado() != null) {
                            for (ComprobanteV4.Impuestos.Traslados.Traslado impTras : comprobante
                                    .getImpuestos().getTraslados().getTraslado()) {
                                Impuesto impuesto = new Impuesto();
                                impuesto.setImporte(impTras.getImporte().doubleValue());
                                impuesto.setDescripcion(impTras.getImpuesto());

                                listImpTras.add(impuesto);
                            }
                        }

                        //Timbre Fiscal
                        if (comprobante.getComplemento() != null
                                && comprobante.getComplemento().getAny() != null
                                && !comprobante.getComplemento().getAny().isEmpty()) {
                            try {
                                List listComplemento = comprobante.getComplemento().getAny();
                                TimbreFiscalDigital timbreFiscal = (TimbreFiscalDigital) listComplemento.get(0);

                                pago.setFolioFiscal(timbreFiscal.getUUID());
                                pago.setSelloSAT(timbreFiscal.getSelloSAT33());
                                pago.setSerieCertificadoSAT(timbreFiscal.getNoCertificadoSAT33());
                                pago.setFechaHoraCertificacion(timbreFiscal.getFechaTimbrado().toString());

                                StringBuffer sbCadenaOriginalSat = new StringBuffer();
                                sbCadenaOriginalSat.append("||")
                                        .append(timbreFiscal.getVersion()).append("|")
                                        .append(timbreFiscal.getUUID()).append("|")
                                        .append(timbreFiscal.getFechaTimbrado()).append("|")
                                        .append(timbreFiscal.getSelloCFD33()).append("|")
                                        .append(timbreFiscal.getNoCertificadoSAT()).append("||");
                                pago.setCadenaOriginalSAT(sbCadenaOriginalSat.toString());
                            } catch (Exception e) {
                                log.error(e);
                            }
                        }

                        pago.setConceptos(listConceptos);
                        pago.setImpuestosRetenidos(listImpRet);
                        pago.setImpuestosTrasladados(listImpTras);

                        xmlComprobanteFiscalV4 = xmlComprobanteFiscalV4.replaceAll("&amp;", "&");
                        String rfcReceptorSinFormato =  pago.getRfcReceptor();
                        pago.setRfcReceptor(rfcReceptorSinFormato.replaceAll("&amp;", "&"));

                        xmlComprobanteFiscalV4 = xmlComprobanteFiscal.replace("ComprobanteV4","Comprobante");
                        
                        
                        if(!StringUtils.contains(xmlComprobanteFiscalV4, "<?xml version=\"1.0\"")){
                        	xmlComprobanteFiscalV4 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xmlComprobanteFiscalV4;
                        }
                        
                        log.info(" XML ACTUALIZADO V4: "+xmlComprobanteFiscalV4);
                        
                        pago.setXmlComprobante(xmlComprobanteFiscalV4);
                    }
				}

			}
		}else {
			log.info("listaPago es null "+listaPago);
		}

		return listaPago;
	}
	
	private String unificarFormatoXml(String xmlPagoFiscalSinFormato) {
		String xmlPagoFiscalConFormato;
		String tagXmlTimbradoInicio = "<mx:xmlTimbradoSAT>";
		String tagXmlTimbradoFin = "</mx:xmlTimbradoSAT>";
		
		int indiceTagXmlTimbradoInicio = xmlPagoFiscalSinFormato.indexOf(tagXmlTimbradoInicio);
		int indiceTagXmlTimbradoFin = xmlPagoFiscalSinFormato.indexOf(tagXmlTimbradoFin);

		int indiceComprobanteXmlInicio = indiceTagXmlTimbradoInicio + tagXmlTimbradoInicio.length();

		log.info("indiceTagXmlTimbradoInicio: "+indiceTagXmlTimbradoInicio);
        log.info("indiceTagXmlTimbradoFin: "+indiceTagXmlTimbradoFin);
        log.info("indiceComprobanteXmlInicio: "+indiceComprobanteXmlInicio);

		// Se obtiene el contenido del tag  mx:xmlTimbradoSAT
		String xmlComprobanteXml = xmlPagoFiscalSinFormato.substring(indiceComprobanteXmlInicio, indiceTagXmlTimbradoFin);

		if(xmlComprobanteXml.contains("&lt;cfdi:Comprobante")){
			xmlPagoFiscalConFormato = xmlPagoFiscalSinFormato;
		} else {
			xmlPagoFiscalConFormato = xmlPagoFiscalSinFormato.replaceAll("<mx:xmlTimbradoSAT>", "<mx:xmlTimbradoSAT><![CDATA[");
			xmlPagoFiscalConFormato = xmlPagoFiscalConFormato.replace("</mx:xmlTimbradoSAT>", "]]></mx:xmlTimbradoSAT>");
		}

		if(xmlPagoFiscalConFormato.contains("&")){
			log.info("Escapo caracter &");
			xmlPagoFiscalConFormato= xmlPagoFiscalConFormato.replaceAll("&(?!lt;)", "&amp;");
		}
		
		log.info("------------------>Pago Respuesta (CON Formato): " + xmlPagoFiscalConFormato);
		return xmlPagoFiscalConFormato;
	}

	@Override
	public Map<String, Object> descargarFacturaElectronica(Pago pago, BufferedImage imagenCodeQR){
		Map<String, Object> mapaFacturaElectronica = new HashMap<String, Object>();		
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			Map<String, Object> parameters = getParametrosFacturaElectronica(pago, imagenCodeQR);

			String path_reporte=PATH_REPORTE_FACTURAE;
			if(isVersion4(pago)){
			    log.info("Entrando a la version 4");
                path_reporte=PATH_REPORTE_FACTURA_V4;
                parameters = getParametrosFacturaV4(pago,parameters);
            }else{
				log.info("No entro a la version 4");
			}

			JasperReport report = (JasperReport) JRLoader.loadObject( 
				new ClassPathResource(path_reporte).getInputStream());
			JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
			exporter.exportReport();
			mapaFacturaElectronica.put("facturaElectronica", byteArrayOutputStream.toByteArray());
			mapaFacturaElectronica.put("nombreFacturaElectronica", 
				getNombreReporteFacturaElectronica(pago));			
		} catch (JRException e) {
			log.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return mapaFacturaElectronica;
	}
	
	@Override
	public String generarCadenaCodigoQR(Pago pago) {
		DecimalFormat df = new DecimalFormat("$ #,##0.000000");
		StringBuffer codigoQR = new StringBuffer();
		if(pago.getTotal()==null)
			pago.setTotal(0.0D);
		String totalFormat = df.format(pago.getTotal());
		totalFormat=totalFormat.replace("$","").replace(",","");		
		codigoQR.append("?re=");
		codigoQR.append(pago.getRfcEmisor()==null?"":pago.getRfcEmisor());
		codigoQR.append("&rr=");
		codigoQR.append(pago.getRfcReceptor()==null?"":pago.getRfcReceptor());
		codigoQR.append("&tt=");
		codigoQR.append(totalFormat.trim());
		codigoQR.append("&id=");
		codigoQR.append(pago.getFolioFiscal()==null?"":pago.getFolioFiscal());
		return codigoQR.toString();
	}
	
	private Map<String, Object> getParametrosFacturaElectronica(Pago pago, BufferedImage imagenCodeQR){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SUBREPORT_DIR", new ClassPathResource(PATH_SUBREPORTES_FACTURAE).getPath());
		parameters.put("IMAGENES_DIR", new ClassPathResource("reportes/").getPath());
		parameters.put("imagenQR", imagenCodeQR);		
		parameters.put("rp", getNRPFormateado(pago.getNumeroRegistroPatronal()));
		parameters.put("periodo", pago.getPeriodo());
		parameters.put("folioSua", pago.getFolioSua());
		parameters.put("fechaPago", pago.getFechaPago());		
		parameters.put("folioFiscal", pago.getFolioFiscal());
		parameters.put("serieCertificadoCSD", pago.getSerieCertificadoCSD());
		parameters.put("lugarExpedicion", pago.getLugarExpedicion() == null ? " " : pago.getLugarExpedicion().equals("06600") ? "MEXICO" : pago.getLugarExpedicion());
		parameters.put("fechaHoraEmision", pago.getFechaHoraEmision()==null?"":pago.getFechaHoraEmision());
		parameters.put("nombreRazonSocial", pago.getNombreRazonSocial());		
		parameters.put("rfcReceptor", pago.getRfcReceptor());
		parameters.put("listaConceptos", eliminarConceptosEnCero(pago.getConceptos()));
		parameters.put("totalConLetra", pago.getTotalConLetra());
		parameters.put("total", pago.getTotal());
		parameters.put("subTotal", pago.getSubTotal());
		parameters.put("metodoDePago", pago.getMetodoDePago());
		
		ResourceBundle resourceBundle = ResourceBundleConfiguration.getResourceBundle();
		String strFormaDePago = " ";
		
		if (pago.getFormaDePago().equals("PAGO EN UNA SOLA EXHIBICION")) {
			strFormaDePago = "Metodo de pago: 99 Otros";
		} else {
			strFormaDePago = resourceBundle.getString("desFormaPago_"+pago.getFormaDePago());
		}
		parameters.put("formaDePago", strFormaDePago);
		
		parameters.put("listaImpuestos", getImpuestosTotales(pago));	
		parameters.put("selloDigitalCFDI",  pago.getSelloDigitalCFDI());
		parameters.put("selloSAT",  pago.getSelloSAT());
		parameters.put("cadenaOriginalSAT",  pago.getCadenaOriginalSAT());
		parameters.put("serieCertificadoSAT",  pago.getSerieCertificadoSAT());
		parameters.put("fechaHoraCertificacion",  pago.getFechaHoraCertificacion());
		parameters.put("entidadRecaudadora", pago.getEntidadRecaudadora().toString());
		
		return parameters;
	}
	
	private List<Impuesto> getImpuestosTotales(Pago pago){
		List<Impuesto> listaTotalImpuestos = new ArrayList<Impuesto>();
		//Impuestos trasladados
		listaTotalImpuestos=pago.getImpuestosTrasladados();
		//Impuestos retenidos
		if(!CollectionUtils.isEmpty(pago.getImpuestosRetenidos())){
			if(!CollectionUtils.isEmpty(listaTotalImpuestos)){
				listaTotalImpuestos.addAll(pago.getImpuestosRetenidos());
			}else{
				listaTotalImpuestos=pago.getImpuestosRetenidos();
			}
		}				
		return eliminarImpuestosEnCero(listaTotalImpuestos);
	}
	
	private String getNombreReporteFacturaElectronica(Pago pago){
		StringBuffer nombreReporte = new StringBuffer();
		nombreReporte.append("FacturaElectronica_");
		nombreReporte.append(pago.getFolioSua()==null?"":pago.getFolioSua());
		nombreReporte.append(".pdf");
		return nombreReporte.toString();
	}
	
	private String getNRPFormateado(String numeroRegistroPatronal){
		if(numeroRegistroPatronal!=null && numeroRegistroPatronal.length()>=8){
			StringBuffer nrp = new StringBuffer();
			int iCaracteres = numeroRegistroPatronal.length();
			nrp.append(numeroRegistroPatronal.substring(0, 8));
			if(iCaracteres>8){				
				nrp.append("-");
				nrp.append(numeroRegistroPatronal.substring(8, 10));
				if(iCaracteres>10){
					nrp.append("-");
					nrp.append(numeroRegistroPatronal.substring(10));
				}
			}
			return nrp.toString();
		}
		return null;
	}
	
	private List<Impuesto> eliminarImpuestosEnCero(List<Impuesto> impuestos){
		if(!CollectionUtils.isEmpty(impuestos)){
			List<Impuesto> listaImpuestos = new ArrayList<Impuesto>();			
			for(Impuesto impuesto : impuestos){
				if(!(esValorCero(impuesto.getImporte()))){
					listaImpuestos.add(impuesto);
				}
			}
			return listaImpuestos;
		}
		return null;
	}
	
	private List<Concepto> eliminarConceptosEnCero(List<Concepto> conceptos){
		if(!CollectionUtils.isEmpty(conceptos)){
			List<Concepto> listaConcepto = new ArrayList<Concepto>();			
			for(Concepto concepto : conceptos){
				if(!(esValorCero(concepto.getImporte()) 
						|| esValorCero(concepto.getValorUnitario()))){
					listaConcepto.add(concepto);
				}
			}
			return listaConcepto;
		}
		return null;
	}
	
	private boolean esValorCero(Double cifra){
		if(cifra==null){
			return true;
		}else{
			if((cifra.compareTo(0.00D)<=0)	){
				return true;
			}	
		}
		return false;
	}


	private Boolean isVersion4(Pago pago){

        String xmlComprobanteFiscal;
        String xmlComprobante = pago.getXmlComprobante();

        log.info("------------------>Pago WsComprobanteFiscal: " + xmlComprobante);
        // Se elimina tag de cometario (XML)
        if (xmlComprobante.startsWith("<![CDATA[")) {
            String xmlPagoFiscalSinFormato = xmlComprobante.substring(9, xmlComprobante.length() - 3);

            log.info("------------------>Pago Respuesta (Sin Formato): " + xmlPagoFiscalSinFormato);
            String xmlPagoFiscal = unificarFormatoXml(xmlPagoFiscalSinFormato);
            PagoType pagoRespuesta = (PagoType) JaxbUtil.xmlToObject(xmlPagoFiscal);

            xmlComprobanteFiscal = pagoRespuesta.getXmlTimbradoSAT();
        } else {
            xmlComprobanteFiscal = xmlComprobante;
        }

        if (StringUtils.isNotBlank(xmlComprobanteFiscal)) {
            System.out.println("----------------->" + xmlComprobanteFiscal);

            int posicionVersion40 = xmlComprobanteFiscal.indexOf("Version");

            String valorVersion = null;

            if (posicionVersion40 != -1) {
                System.out.println("Comprobando Version");
                valorVersion = xmlComprobanteFiscal.substring(posicionVersion40 + 9, posicionVersion40 + 9 + 3);

                if (valorVersion.equals("4.0")) {
                    return true;
                }
            }



        }
	    return false;
	}

    private Map<String, Object> getParametrosFacturaV4(Pago pago, Map<String, Object> parameters) {

	    try {
	        String xmlComprobanteFiscalV4 = pago.getXmlComprobante();
	        log.info("xmlV4_: " + xmlComprobanteFiscalV4);
	
	        String xmlComprobanteFiscalV4Temp = xmlComprobanteFiscalV4.replace("Comprobante","ComprobanteV4");
	        
	        ComprobanteV4 comprobante = (ComprobanteV4) mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.xmlToObject(xmlComprobanteFiscalV4Temp);
	        
	        Integer numRegFis = Integer.parseInt(comprobante.getReceptor().getRegimenFiscalReceptor());
	        log.info("numRegFis: " + numRegFis);
	
	        String regimenFiscal = numRegFis + " " + RegimenFiscalEnum.obtenerEnumById(numRegFis).getDesc();
	        log.info("regimenFiscal: " + regimenFiscal);
	        parameters.put("regimenFiscal", regimenFiscal);
	        parameters.put("cp", comprobante.getReceptor().getDomicilioFiscalReceptor());
	        //Para la version 4 se sobreescribe el nombre o razon social por el proporcionado por el servicio del SAT
	        String nombreRazonSocial = comprobante.getReceptor().getNombre33();
	        nombreRazonSocial = nombreRazonSocial.replace("&#209;", "Ñ");
	        nombreRazonSocial = nombreRazonSocial.replace("&amp;#209;", "Ñ");
			parameters.put("nombreRazonSocial", nombreRazonSocial);
	
	    }catch(Exception e){
	        log.error("Ocurrio un error al agregar los valores V4: "+e.getMessage());
	        e.printStackTrace();
	        parameters.put("regimenFiscal", null);
	        parameters.put("cp", null);
	
	    }

        return parameters;

    }
    

    }
