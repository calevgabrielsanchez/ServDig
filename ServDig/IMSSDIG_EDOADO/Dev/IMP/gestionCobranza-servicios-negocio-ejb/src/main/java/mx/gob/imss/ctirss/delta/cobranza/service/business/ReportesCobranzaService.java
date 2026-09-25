package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.cobranza.enums.TipoPatronCobranzaEnum;
import mx.gob.imss.ctirss.delta.cobranza.enums.TotalesCobranzaEnum;
import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Credito;
import mx.gob.imss.ctirss.delta.cobranza.modelo.CreditoRCV;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Factor;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;
import mx.gob.imss.ctirss.delta.cobranza.modelo.SituacionCobro;
import mx.gob.imss.ctirss.delta.cobranza.modelo.SituacionCobroRcv;
import mx.gob.imss.ctirss.delta.cobranza.modelo.TipoCobro;
import mx.gob.imss.ctirss.delta.cobranza.modelo.TipoCobroRcv;
import mx.gob.imss.ctirss.delta.cobranza.service.entity.CreditosEntityLocal;
import mx.gob.imss.ctirss.delta.cobranza.service.entity.FactorEntityLocal;
import mx.gob.imss.ctirss.delta.cobranza.service.entity.FechaCierreEdoAdoEntityLocal;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.ReportesCobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.ReporteUtilityServiceLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

@Stateless( name = "reportesCobranzaService", mappedName = "reportesCobranzaService")
public class ReportesCobranzaService extends AbstractService implements ReportesCobranzaServiceRemote, ReportesCobranzaServiceLocal {

	private final int REPORTE_SITUACION_COBRO = 1;
	private final int REPORTE_SITUACION_COBRO_RCV = 2;
	private final int REPORTE_TIPO_COBRO = 3;
	private final int REPORTE_TIPO_COBRO_RCV = 4;
	
	private final String DESC_REPORTE_SITUACION_COBRO = "Situación de cobro cuotas IMSS";
	private final String DESC_REPORTE_SITUACION_COBRO_RCV = "Situación de cobro cuotas RCV";
	private final String DESC_REPORTE_TIPO_COBRO = "Motivo de cobro cuotas IMSS";
	private final String DESC_REPORTE_TIPO_COBRO_RCV = "Motivo de cobro cuotas RCV";
	private final String ERROR_FECHA_CIERRE = "No fue posible generar el reporte debido a un error al consultar la fecha de cierre.";
	private final String SIN_ADEUDO = "El patr&oacute;n no tiene adeudo.";
	private final String ERROR_SELLADO = "Ocurri&oacute; un error al intentar sellar el documento.";
	
	@EJB CreditosEntityLocal creditosEntityLocal;
	@EJB FactorEntityLocal factorEntityLocal;
	@EJB FechaCierreEdoAdoEntityLocal fechaCierreEdoAdoEntityLocal;
	@EJB PatronCobranzaServiceLocal patronCobranzaServiceLocal;
	@EJB ReporteUtilityServiceLocal reporteUtilityServiceLocal;
	@EJB(name="firmaDigitalBusiness",mappedName="firmaDigitalBusiness") FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	@EJB(name="solicitudBusiness", mappedName="solicitudBusiness") SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB(name="sujetoObligadoServiceBusiness", mappedName = "sujetoObligadoServiceBusiness") SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote;
	
	@SuppressWarnings("unchecked")
	@Override
	public byte[] getReporteTipoCobro(String nrp) throws EstadoAdeudoException {
		byte[] reporte = null;
		Patron patron = this.getPatron(nrp);
		//Se obtienen los datos del reporte
		Map<String, Object> datosReporte = this.getDatosReporteEstadoCuentaTrabajoAdeudoTipoCobro(patron);
		
		if(datosReporte != null) {
			//Se sella la informacion y se crea un objeto de firma digital para su posterior guardado
			FirmaElectronica firma = obtenerDatosSellado(patron, datosReporte, REPORTE_TIPO_COBRO);
			//Esta lista sera el datasource
			List<TipoCobro> creditos = (List<TipoCobro>) datosReporte.get("dataSource");
			
			if(creditos == null || creditos.isEmpty()) {
				throw new EstadoAdeudoException(SIN_ADEUDO);
			}
			
			try{
				reporte = reporteUtilityServiceLocal.getReporteTipoCobro(datosReporte, patron);
			} catch(Exception e) {
				log.debug("Error al generar el reporte",e);
				throw new EstadoAdeudoException("No fue posible generer el reporte");
			}
			
			if(reporte != null) {
				
				this.crearSolicitudYFirma(datosReporte,nrp, TipoTramiteEnum.IMPRESION_TIPO_COBRO, firma);
				firmaDigitalBusinessRemote.guardarArchivoFirmado(firma.getSecuenciaNotaria(), "motivoCobro"+nrp+".pdf", reporte);
			
			}
		}
		return reporte;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public byte[] getReporteTipoCobroRcv(String nrp)
			throws EstadoAdeudoException {
		Patron patron = this.getPatron(nrp);
		byte[] reporte = null;
		//Se obtienen los datos del reporte
		Map<String, Object> datosReporte = this.getDatosReporteEstadoCuentaTrabajoAdeudoTipoCobroRCV(patron);
		
		if(datosReporte != null) {
			//Se sella la informacion y se crea un objeto de firma digital para su posterior guardado
			FirmaElectronica firma = obtenerDatosSellado(patron, datosReporte, REPORTE_TIPO_COBRO_RCV);
			List<TipoCobroRcv> creditos = (List<TipoCobroRcv>) datosReporte.get("dataSource");
			
			if(creditos == null || creditos.isEmpty()) {
				throw new EstadoAdeudoException(SIN_ADEUDO);
			}
			
			try{
				reporte = reporteUtilityServiceLocal.getReporteTipoCobroRCV(datosReporte, patron);
			} catch(Exception e) {
				log.debug("Error al generar el reporte",e);
				throw new EstadoAdeudoException("No fue posible generer el reporte");
			}
			
			if(reporte != null) {
				
				this.crearSolicitudYFirma(datosReporte, nrp, TipoTramiteEnum.IMPRESION_TIPO_COBRO_RCV,firma);
				firmaDigitalBusinessRemote.guardarArchivoFirmado(firma.getSecuenciaNotaria(), "motivoCobroRcv"+nrp+".pdf", reporte);
			}
		}
		
		return reporte;
	}

	private Patron getPatron(String nrp) throws EstadoAdeudoException{
		
		String rp = nrp.substring(0, 8);
		String modalidad = nrp.substring(8,10);
		
		System.out.println("rp: " + rp);
		System.out.println("modalidad: " + modalidad);
		
		Patron patron = new Patron();
		patron.setRegPatronal(rp);
		patron.setCveModalidad(modalidad);
		
		try {
			patron = patronCobranzaServiceLocal.getPatron(patron.getRegPatronal(), patron.getCveModalidad());

			if (patron == null) {
				throw new EstadoAdeudoException("No se encontr\u00F3 al patr\u00F3n");
			}
		}catch(Exception e) {
			throw new EstadoAdeudoException("No se encontr\u00F3 al patr\u00F3n");
		}
		
		return patron;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String , Object> getDatosReporteEstadoCuentaTrabajoAdeudoTipoCobroRCV(Patron patron)  throws EstadoAdeudoException{
		
		List<TipoCobroRcv> collectionTipoCobro = null;
		Date fechaInicioReporte = new Date();
		Map<String , Object> resultado = null;
		
		String fechaCierre = null;
		
		try{
			fechaCierre = fechaCierreEdoAdoEntityLocal.getUltimaFechaCorte();
		} catch(Exception e) {
			throw new EstadoAdeudoException(ERROR_FECHA_CIERRE);
		}
		
		resultado = this.getCreditosRcv(patron);
		List<CreditoRCV> creditos = (List<CreditoRCV>) resultado.get(TotalesCobranzaEnum.DATA_SOURCE.getKey());
		
		if(!creditos.isEmpty()) {
			collectionTipoCobro = this.ordenaCreditosTipoCobroRcv(creditos);
			
			resultado.remove(TotalesCobranzaEnum.DATA_SOURCE.getKey());
			resultado.put(TotalesCobranzaEnum.DATA_SOURCE.getKey(), collectionTipoCobro);
			resultado.put("FECHA_CIERRE", fechaCierre);
			resultado.put("fechaInicioReporte", fechaInicioReporte);
		}
		
		return resultado;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String , Object> getDatosReporteEstadoCuentaTrabajoAdeudoSituacionCobroRCV(
			Patron patron)  throws EstadoAdeudoException{
		List<SituacionCobroRcv> dataSource = null;
		Date fechaInicioReporte = new Date();
		Map<String , Object> resultado = null;
		
		String fechaCierre = null;
		
		try{
			fechaCierre = fechaCierreEdoAdoEntityLocal.getUltimaFechaCorte();
		} catch(Exception e) {
			throw new EstadoAdeudoException(ERROR_FECHA_CIERRE);
		}
		
		resultado = this.getCreditosRcv(patron);
		FirmaElectronica firma = this.obtenerDatosSellado(patron, resultado, REPORTE_SITUACION_COBRO_RCV);
		
		List<CreditoRCV> creditos = (List<CreditoRCV>) resultado.get(TotalesCobranzaEnum.DATA_SOURCE.getKey());
		
		if(!creditos.isEmpty()) {
			dataSource = this.ordenaCreditosSituacionCobroRcv(creditos);
			
			resultado.remove(TotalesCobranzaEnum.DATA_SOURCE.getKey());
			resultado.put(TotalesCobranzaEnum.DATA_SOURCE.getKey(), dataSource);
			resultado.put("FECHA_CIERRE", fechaCierre);
			resultado.put("fechaInicioReporte", fechaInicioReporte);
			
			this.crearSolicitudYFirma(resultado, patron.getRegPatronal()+patron.getCveModalidad(), TipoTramiteEnum.IMPRESON_SITUACION_COBRO_RCV, firma);

		}
		
		return resultado;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDatosReporteEstadoCuentaTrabajoAdeudoTipoCobro(
			Patron patron)  throws EstadoAdeudoException{
		List<TipoCobro> collectionTipoCobro = null;
		Date fechaInicioReporte = new Date();
		Map<String , Object> resultado = null;
		
		String fechaCierre = null;
		
		try{
			fechaCierre = fechaCierreEdoAdoEntityLocal.getUltimaFechaCorte();
		} catch(Exception e) {
			
			log.info("ERROR FechaCorte: "+e.getMessage());
			e.printStackTrace();
			
			throw new EstadoAdeudoException(ERROR_FECHA_CIERRE);
		}
		
		resultado = this.getCreditos(patron);
		
		List<Credito> creditos = (List<Credito>) resultado.get(TotalesCobranzaEnum.DATA_SOURCE.getKey());
		
		if(!creditos.isEmpty()) {
			collectionTipoCobro = this.ordenaCreditosTipoCobro(creditos);
			
			resultado.remove(TotalesCobranzaEnum.DATA_SOURCE.getKey());
			resultado.put(TotalesCobranzaEnum.DATA_SOURCE.getKey(), collectionTipoCobro);
			resultado.put("FECHA_CIERRE", fechaCierre);
			resultado.put("fechaInicioReporte", fechaInicioReporte);
		}
		
		return resultado;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDatosReporteEstadoCuentaTrabajoAdeudoSituacionCobro(
			Patron patron)  throws EstadoAdeudoException{
		List<SituacionCobro> dataSource = null;
		Date fechaInicioReporte = new Date();
		Map<String , Object> resultado = null;
		
		String fechaCierre = null;
		
		try{
			fechaCierre = fechaCierreEdoAdoEntityLocal.getUltimaFechaCorte();
		} catch(Exception e) {
			throw new EstadoAdeudoException(ERROR_FECHA_CIERRE);
		}
		
		resultado = this.getCreditos(patron);
		FirmaElectronica firma = this.obtenerDatosSellado(patron, resultado, REPORTE_SITUACION_COBRO);
		
		List<Credito> creditos = (List<Credito>) resultado.get(TotalesCobranzaEnum.DATA_SOURCE.getKey());
		
		if(!creditos.isEmpty()) {
			
			dataSource = this.ordenaCreditosSituacionCobro(creditos);
			
			resultado.remove(TotalesCobranzaEnum.DATA_SOURCE.getKey());
			resultado.put(TotalesCobranzaEnum.DATA_SOURCE.getKey(), dataSource);
			resultado.put("FECHA_CIERRE", fechaCierre);
			resultado.put("fechaInicioReporte", fechaInicioReporte);
			
			this.crearSolicitudYFirma(resultado, patron.getRegPatronal()+patron.getCveModalidad(), TipoTramiteEnum.IMPRESION_SITUACION_ADEUDO, firma);

		}
		
		return resultado;
	}

	/**
	 * 
	 * @param patron
	 * @return
	 */
	public Map<String,Object> getCreditos(Patron patron) {
		
		List<Credito> creditoBs = null;
		DecimalFormat to2Dec = new DecimalFormat("###,###,##0.00");
		DecimalFormat to4Dec = new DecimalFormat("##0.0000");
		List<Credito> creditosTotal = new ArrayList<Credito>();
		List<Factor> factorBs = null;
		Map<String , Object> resultado = new HashMap<String, Object>();
		Double totalSalTot = 0.0;
		Double totalEnfermedadesMaternidad = 0.0;
		Double totalSalIV = 0.0;
		Double totalSalRt = 0.0;
		Double totalSalGuar = 0.0;
		Double totalAct = 0.0;
		Double totalInt = 0.0;
		Double porcentajeRecargos = 0.0; 
		
		Double sumaTotal = 0.0;
		Double sumaTotales = 0.0;
		
		if(patron != null) {
			
			factorBs = factorEntityLocal.findFactorAllManMapping();
			
			String registroPatronal = patron.getRegPatronal();
			String modalidad = patron.getCveModalidad();
			
			if(patron.getCveTipoPatron() != null && patron.getCveTipoPatron().equals(TipoPatronCobranzaEnum.CORPORATIVO.getId())){
	            	creditoBs = creditosEntityLocal.findCreditReportCorp(registroPatronal, modalidad);
	        }else{
	            	creditoBs = creditosEntityLocal.findCreditReportOther(registroPatronal, modalidad);
	        }
	        
			if(creditoBs != null) {
				for (Credito credito : creditoBs) { //Barre la lista de creditos
					Double totalAdeudo = new Double(credito.getCrTotalAdeudo());
		        	if(totalAdeudo > 0.00) // Si total adeudo es mayor de cero
					{
						int crDoc = Integer.parseInt(credito.getCrDoc());
						Double enfermedadesMaternidad = 
								Double.valueOf(credito.getCrSalEyMFija()) + 
								Double.valueOf(credito.getCrSalEyMAdy()) + 
								Double.valueOf(credito.getCrSalEyMDin()) + 
								Double.valueOf(credito.getCrSaleyMPen());
						
						totalEnfermedadesMaternidad += enfermedadesMaternidad;
						totalSalIV += Double.valueOf(credito.getCrSalIV());
						totalSalRt += Double.valueOf(credito.getCrSalRt());
						totalSalGuar += Double.valueOf(credito.getCrSalGuar());
						totalSalTot += Double.valueOf(credito.getCrSalTot());
						totalAct += Double.valueOf(credito.getAct());
						totalInt += Double.valueOf(credito.getCrInt());
						
						
						sumaTotal = Double.valueOf(credito.getCrSalTot()) + Double.valueOf(credito.getAct()) + Double.valueOf(credito.getCrInt());
						
						credito.setEnfermedadesMaternidad(to2Dec.format(enfermedadesMaternidad));
						credito.setPorcentajeRecargos(to4Dec.format(0));
		    			credito.setFactorAct(to4Dec.format(new Double(credito.getFactorAct())));
		    			porcentajeRecargos=0.0;
		    			for (int i = 0; i < factorBs.size(); i++) {
		        			if(factorBs.get(i).getPeriodo().equals(credito.getCrPer() )) {
		        				
		        				for (int j = i; j < factorBs.size(); j++)
		        				{
		        					porcentajeRecargos+=new Double(factorBs.get(j).getRecargos());
		        					credito.setPorcentajeRecargos(to4Dec.format(porcentajeRecargos));// 1.a
		        				}
		        					//credito.setPorcentajeRecargos(new Double(credito.getPorcentajeRecargos())+);
		                            //credito.setFactorAct(to4Dec.format(new Double(factorBs.get(i).getFacAct())));// 1.b
		                      }
						}
		    			porcentajeRecargos=0.0;
		    			/*if (
		        				crDoc != 0 
		        				&& crDoc != 8
		        				&& crDoc != 70
		        				&& crDoc != 71
		        				&& crDoc != 80 && crDoc != 81 && crDoc != 82 && crDoc != 83 && crDoc != 84 && crDoc != 85 && crDoc != 86 && crDoc != 87 && crDoc != 88 && crDoc != 89)
						{
		        			
							Double factAct = new Double(credito.getFactorAct());
							if(factAct <= 0.0)
							{
								credito.setFactorAct(to4Dec.format(1));
							}
						}*/
		        		
		        		if(	(crDoc == 0 || crDoc == 70 || crDoc == 71) && (credito.getCrFecNot().equals("1980-01-01")))
		        		{ 
		    					credito.setPorcentajeRecargos("No aplica");//2.a
		    					//credito.setFactorAct("No aplica");//2.b
		    			}
		        		
		        		if(	crDoc == 8 )
		        		{ 
		    					credito.setPorcentajeRecargos("No aplica");//3.a
		    					//credito.setFactorAct("No aplica");//3.b
		    			}
		        		
		        		if(	crDoc >= 80 && crDoc <= 89)
		        		{
		        			credito.setPorcentajeRecargos("No aplica");//4.a
		        		}
		        		
		        		/*if ((crDoc >= 80 && crDoc <= 89) && (credito.getCrFecNot().equals("1980-01-01")))
		        		{
		        			credito.setFactorAct("No aplica");//4.b
		        		}*/
						credito.setAct(to2Dec.format(new Double(credito.getAct())));
						credito.setCrSalEyMAdy(to2Dec.format(new Double(credito.getCrSalEyMAdy())));
						credito.setCrSalEyMDin(to2Dec.format(new Double(credito.getCrSalEyMDin())));
						credito.setCrSalEyMFija(to2Dec.format(new Double(credito.getCrSalEyMFija())));
						credito.setCrSalGuar(to2Dec.format(new Double(credito.getCrSalGuar())));
						credito.setCrSalIV(to2Dec.format(new Double(credito.getCrSalIV())));
						credito.setCrSalRt(to2Dec.format(new Double(credito.getCrSalRt())));
						credito.setCrSalTot(to2Dec.format(new Double(credito.getCrSalTot())));
						credito.setCrSaleyMPen(to2Dec.format(new Double(credito.getCrSaleyMPen())));
						credito.setSaldoMulta(to2Dec.format(0));
						credito.setSaldoActMulta(to2Dec.format(0));
			        		
						credito.setSumaTotal(to2Dec.format(sumaTotal));
						credito.setCrInt(to2Dec.format(new Double(credito.getCrInt())));
						credito.setCrTotalAdeudo(to2Dec.format(new Double(credito.getCrTotalAdeudo())));
						//Se modifica la manera en la cual se muestra el crédito AAAAMM -> MMAAAAA
						credito.setCrPer(credito.getCrPer().toString().substring(4, 6)+"-"+credito.getCrPer().toString().substring(0, 4));
						
		        				
						creditosTotal.add(credito);
					}
				}
			}
		}
		
		resultado.put(TotalesCobranzaEnum.TOTAL_ENF_MAT.getKey(), totalEnfermedadesMaternidad);
		resultado.put(TotalesCobranzaEnum.TOTAL_SAL_IV.getKey(), totalSalIV);
		resultado.put(TotalesCobranzaEnum.TOTAL_SAL_RT.getKey(), totalSalRt);
		resultado.put(TotalesCobranzaEnum.TOTAL_SAL_GUAR.getKey(), totalSalGuar);
		resultado.put(TotalesCobranzaEnum.TOTAL_SAL_TOT.getKey(), totalSalTot);
		resultado.put(TotalesCobranzaEnum.TOTAL_ACT.getKey(), totalAct);
		resultado.put(TotalesCobranzaEnum.TOTAL_INT.getKey(), totalInt);
		resultado.put(TotalesCobranzaEnum.PORCENTAJE_RECARGOS.getKey(), porcentajeRecargos);
		sumaTotales = totalSalTot + totalAct + totalInt;
		
		if(sumaTotales.equals(0.00)) {
			resultado.put(TotalesCobranzaEnum.DATA_SOURCE.getKey(), new ArrayList<Credito>());
		} else {
			resultado.put(TotalesCobranzaEnum.DATA_SOURCE.getKey(), creditosTotal);
		}
		
		resultado.put(TotalesCobranzaEnum.SUMA_TOTALES.getKey(), sumaTotales);
		
		return resultado;
	}
	
	/**
	 * 
	 * @param patron
	 * @return
	 */
	public Map<String, Object> getCreditosRcv(Patron patron) {
		
		List<CreditoRCV> creditoBs = null;
		DecimalFormat to2Dec = new DecimalFormat("###,###,##0.00");
		DecimalFormat to4Dec = new DecimalFormat("##0.0000");
		List<CreditoRCV> creditosTotal = new ArrayList<CreditoRCV>();
		Double totalCyV = 0.0;
		Double totalSalRet = 0.0;
		Double totalSalTot = 0.0;
		Double totalActua = 0.0;
		Double totalRecar = 0.0;
		
		Double sumaTotal = 0.0;
		Double sumaTotales = 0.0;
		
		Map<String,Object> resultado = new HashMap<String, Object>();
		
		//Verificamos que el patron no venga null
		if(patron != null) {
			String registroPatronal = patron.getRegPatronal();
			String modalidad = patron.getCveModalidad();
			
			//Verificamos que el patron sea de tipo Corporativo
			if(patron.getCveTipoPatron() != null && patron.getCveTipoPatron().equals(TipoPatronCobranzaEnum.CORPORATIVO.getId())) {
				creditoBs = creditosEntityLocal.findCreditReportCorpRcv(registroPatronal, modalidad);
			} else {
				creditoBs = creditosEntityLocal.findCreditReportOtherRcv(registroPatronal, modalidad);
			}
			
			if(creditoBs != null && !creditoBs.isEmpty()) {
				for (CreditoRCV credito : creditoBs) { //Barre la lista de creditos
					
					Double totalAdeudo = new Double(credito.getTotalAdeudo());
					
		        	if(totalAdeudo > 0.00) // Si total adeudo es mayor de cero
					{
        				int crDoc = Integer.parseInt(credito.gettDocumento());

    					totalCyV += Double.valueOf(credito.getCyv());
    					totalSalRet += Double.valueOf(credito.getCrSalRet());
    					totalSalTot += Double.valueOf(credito.getSaldoTotal());			
    					totalActua += Double.valueOf(credito.getActua());
    					totalRecar += Double.valueOf(credito.getRecar());
    					credito.setPorcentajeRecargos(to4Dec.format(0));
	        			credito.setFacAct(to4Dec.format(new Double(credito.getFacAct())));
	        			credito.setPorcentajeRecargos(to4Dec.format(new Double(credito.getFacRec())));
	        			
	        			sumaTotal = Double.valueOf(credito.getSaldoTotal()) + Double.valueOf(credito.getActua()) + Double.valueOf(credito.getRecar());
	        			
    	        		/*if (
    	        				crDoc != 0 
    	        				&& crDoc != 8
    	        				&& crDoc != 70
    	        				&& crDoc != 71
    	        				&& crDoc != 80 && crDoc != 81 && crDoc != 82 && crDoc != 83 && crDoc != 84 && crDoc != 85 && crDoc != 86 && crDoc != 87 && crDoc != 88 && crDoc != 89)
    					{
    	        			
    	        			
    	        			

    					}*/
    	        		
    	        		if(	(crDoc == 0 || crDoc == 70 || crDoc == 71) && (credito.getFecNot().equals("1980-01-01")))
    	        		{ 
	        					credito.setPorcentajeRecargos("No aplica");//2.a
	        					credito.setFacAct("No aplica");//2.b
	        			}
    	        		
    	        		if(	crDoc == 8 )
    	        		{ 
	        					credito.setPorcentajeRecargos("No aplica");//3.a
	        					credito.setFacAct("No aplica");//3.b
	        			}
    	        		
    	        		if(	crDoc >= 80 && crDoc <= 89)
    	        		{
    	        			credito.setPorcentajeRecargos("No aplica");//4.a
    	        		}
    	        		
    	        		if ((crDoc >= 80 && crDoc <= 89) && (credito.getFecNot().equals("1980-01-01")))
    	        		{
    	        			credito.setFacAct("No aplica");//4.b
    	        		}
    	        		
    					credito.setActua(to2Dec.format(new Double(credito.getActua())));
    					credito.setCyv(to2Dec.format(new Double(credito.getCyv())));
    					credito.setCrSalRet(to2Dec.format(new Double(credito.getCrSalRet())));
    					credito.setSaldoTotal(to2Dec.format(new Double(credito.getSaldoTotal())));
    					credito.setSaldoMulta(to2Dec.format(0));
    					credito.setSaldoActMulta(to2Dec.format(0));
    					credito.setSumaTotal(to2Dec.format(sumaTotal));
        	        		
        				credito.setRecar(to2Dec.format(new Double(credito.getRecar())));
        				credito.setTotalAdeudo(to2Dec.format(new Double(credito.getTotalAdeudo())));
        				//Se modifica la manera en la cual se muestra el crédito AAAAMM -> MMAAAAA
        				credito.setPeriodo(credito.getPeriodo().toString().substring(4, 6)+"-"+credito.getPeriodo().toString().substring(0, 4));
        				
        				creditosTotal.add(credito);
					}
				}
			}
		}
		
		resultado.put(TotalesCobranzaEnum.TOTAL_CYV.getKey(), totalCyV);
		resultado.put(TotalesCobranzaEnum.TOTAL_SAL_RET.getKey(), totalSalRet);
		resultado.put(TotalesCobranzaEnum.TOTAL_SAL_TOT.getKey(), totalSalTot);
		resultado.put(TotalesCobranzaEnum.TOTAL_ACTUA.getKey(), totalActua);
		resultado.put(TotalesCobranzaEnum.TOTAL_RECAR.getKey(), totalRecar);
		sumaTotales = totalSalTot + totalActua + totalRecar;
		
		if(sumaTotales.equals(0.00)) {
			resultado.put(TotalesCobranzaEnum.DATA_SOURCE.getKey(), new ArrayList<CreditoRCV>());
		} else {
			resultado.put(TotalesCobranzaEnum.DATA_SOURCE.getKey(), creditosTotal);
		}
		
		resultado.put(TotalesCobranzaEnum.SUMA_TOTALES.getKey(), sumaTotales);
		
		return resultado;
	}
	
	private List<TipoCobroRcv> ordenaCreditosTipoCobroRcv(List<CreditoRCV> creditosTotal) {
		List<TipoCobroRcv> tipoCobrosTotal = new ArrayList<TipoCobroRcv>();
		DecimalFormat to2Dec = new DecimalFormat("###,###,##0.00");
		Double totalSalTot = 0.0;
		Double totalActua = 0.0;
		Double totalRecar = 0.0;
		Double sumaTotales = 0.0;
		Map<String,String> titles = new HashMap<String,String>();
		//titles.put("3","Diferencias en Pago"); MEF:Solicitud de cambio 24/May/2012
		titles.put("3","Diferencias Seguros RCV");
		titles.put("4","Avisos Extemporáneos");
		titles.put("5_61","Resultados de Auditoría");
		titles.put("6a","Emisión Bimestral");
		//titles.put("6b","Omisión de Pago");
		titles.put("6b","Omisión Seguros RCV");
		//titles.put("6c","Propuesta de Pago");//Se agrega a solicitud de Jorge Romero 06/01/2012 :MEF:Solicitud de cambio 24/May/2012
		titles.put("6c","Propuesta Seguros RCV");
		titles.put("6d","Omisión de Pago");//Se agrega a solicitud de Jorge Romero 06/01/2012
		titles.put("50_55","Resultados de Dictamen");
		titles.put("0","Capitales Constitutivos");
		//titles.put("8","Recargos Mor. No Documentados");
		titles.put("8","Recargos Moratorios No Documentados");
		
		Map<String,TipoCobroRcv> tipoCobros = new HashMap<String,TipoCobroRcv>();
		Collection<CreditoRCV> creditos;
		TipoCobroRcv tipoCobro;
	
		for(CreditoRCV credito : creditosTotal){
			int crDoc = Integer.parseInt(credito.gettDocumento());
			String strDoc = credito.gettDocumento();
			/*Primero se validan los tipos de documentos para generar las llaves adecuadas para acceder a titles*/
			if(crDoc == 5 || crDoc == 56 || crDoc == 57 || crDoc == 58 || crDoc == 59 || crDoc == 60 || crDoc == 61)
			{	strDoc = "5_61";}
			
			if(crDoc == 50 || crDoc == 51 || crDoc == 52 || crDoc == 53 || crDoc == 54 || crDoc == 55)
			{	strDoc = "50_55";}
 
			if(crDoc == 6){
				strDoc = "6b"; 
				if(credito.getCredito().substring(2, 3).equals("6")) { 
					strDoc = "6c"; 
				}
				if(credito.getCredito().substring(2, 3).equals("6")||credito.getCredito().substring(2, 3).equals("7")) { 
				
					if (!credito.getIncAct().equals("0"))
						strDoc = "6d";
				}
			}
			
			if(titles.containsKey(strDoc)){ //Se valida que tipo_documento(strDoc) del crédito sea válido.
				if(tipoCobros.containsKey(strDoc)){ //Si existe, se extrae TipoCobro y la colección de Creditos
					tipoCobro = tipoCobros.get(strDoc);
					creditos  = tipoCobro.getCreditos();
				}else{ //Si no, se crean nuevos objectos para ser agregados al mapa tipoCobros
					tipoCobro = new TipoCobroRcv();
					creditos  = new ArrayList<CreditoRCV>();
					tipoCobro.setTipoCobro(titles.get(strDoc));
					tipoCobro.setCrDoc(credito.gettDocumento());
				}
				
				creditos.add(credito);
				tipoCobro.setCreditos(creditos);
				tipoCobros.put(strDoc, tipoCobro);
			}
		}
        
		for(TipoCobroRcv tipCobro : tipoCobros.values()){ //Se valida que al menos exista un credito para reportar
			if(tipCobro.getCreditos().size() >= 1 ) {
				List<CreditoRCV> creditosT = (List<CreditoRCV>)tipCobro.getCreditos();
				
				for(CreditoRCV cred: creditosT) {
					totalSalTot+=Double.valueOf(cred.getSaldoTotal().replace(",", ""));
					totalActua+=Double.valueOf(cred.getActua().replace(",", ""));
					totalRecar+=Double.valueOf(cred.getRecar().replace(",", ""));
				}
				
				sumaTotales = totalSalTot + totalActua + totalRecar;
				
				tipCobro.setSubTotalCuotas(to2Dec.format(totalSalTot));
				tipCobro.setSubTotalAct(to2Dec.format(totalActua));
				tipCobro.setSubTotalRec(to2Dec.format(totalRecar));
				tipCobro.setSubTotal(to2Dec.format(sumaTotales));
				
				tipoCobrosTotal.add(tipCobro);
				
				totalSalTot=0.0;
				totalActua=0.0;
				totalRecar=0.0;
			}
		}

	    
		return tipoCobrosTotal;
	}
	
	private List<SituacionCobroRcv> ordenaCreditosSituacionCobroRcv(List<CreditoRCV> creditosTotal) {
		List<SituacionCobroRcv> situacionCobroTotal = new ArrayList<SituacionCobroRcv>();

		Map<Integer,String> titles = new HashMap<Integer,String>();
		titles.put(0,"Propuestas Emitidas");
		titles.put(1,"Por Notificar");
		titles.put(2,"Notificado");
		titles.put(3,"Aclaración para Ajuste");
		titles.put(5,"Solicitud de Convenio");
		titles.put(6,"Inconformidades");
		titles.put(7,"Efectividad de Fianza");
		titles.put(8,"Cobranza Especial");
		titles.put(9,"En Localización");
		titles.put(10,"Sustitución Patronal Por Dictaminar");
		titles.put(11,"Compensación de Adeudos");
		titles.put(12,"Huelga");
		titles.put(13,"Procuración de Cobro");
		titles.put(14,"Juicios");
		titles.put(15,"Recurso de Revocación");
		titles.put(16,"Insolvencia");
		titles.put(18,"Certificación de Pago");
		titles.put(19,"Concurso Mercantil");
		titles.put(20,"Actos Admvos. Auditoría Por Notificar");
		titles.put(21,"Aclaración Ante Auditoría");
		titles.put(22,"Cumplimiento de Laudo o Sentencia");
		titles.put(23,"Multas en Trámite de Condonación");
		titles.put(25,"Pagos en Conciliación");
		titles.put(26,"Responsabilidad Solidaria");
		titles.put(27,"Autodeterminados");
		titles.put(31,"Inicio de Tramitación");
		titles.put(32,"Embargo Bien Mueble Deposit. Ajena IMSS");
		titles.put(33,"Embargo Bien Mueble Deposit. IMSS");
		titles.put(34,"Embargo Bien Inmueble");
		titles.put(35,"Embargo de Negociación");
		titles.put(36,"Empresas Administradas");
		titles.put(37,"Bienes Valuados para Remate");
		titles.put(38,"En Autorización para Remate");
		titles.put(39,"Primera Almoneda");
		titles.put(40,"Segunda Almoneda");
		titles.put(41,"Venta Fuera de Subasta");
		titles.put(42,"Constitución de Garantía");
		titles.put(43,"Embargo Cuentas Bancarias");
		titles.put(51,"Convenio Pago inmediato con Cancelación");
		titles.put(52,"Convenio con Plazo");
		titles.put(53,"Convenio con Plazo y Condonación");
		titles.put(54,"Reestructuración");
		titles.put(55,"Rehabilitación");

		Map<Integer,SituacionCobroRcv> sitCobros = new HashMap<Integer,SituacionCobroRcv>();
		Collection<CreditoRCV> creditos;
		SituacionCobroRcv sitCobro;
		
		for(CreditoRCV credito : creditosTotal){
			int crInc = Integer.parseInt(credito.getIncAct());

			if(titles.containsKey(crInc)){ //Se valida que el tipo de incidencia(crInc) del crédito sea válido.
				if(sitCobros.containsKey(crInc)){ //Si existe, se extrae SituacionCobro y la colección de Creditos
					sitCobro = sitCobros.get(crInc);
					creditos = sitCobro.getCreditos();
				}else{ //Si no, se crean nuevos objectos para ser agregados al mapa situacionCobros
					sitCobro	 = new SituacionCobroRcv();
					creditos = new ArrayList<CreditoRCV>();
					sitCobro.setSituacionCobro(titles.get(crInc));
					sitCobro.setCrInc(credito.getIncAct());
				}
				
				creditos.add(credito);
				sitCobro.setCreditos(creditos);
				sitCobros.put(crInc, sitCobro);
			}
		}

		for(SituacionCobroRcv situacionCobro : sitCobros.values()){ //Se valida que al menos exista un credito para reportar
			if(situacionCobro.getCreditos().size() >= 1 ) situacionCobroTotal.add(situacionCobro);
		}
   
		return situacionCobroTotal;
	}
	
	/**
	 * @param creditosTotal
	 * @return
	 */
	private List<SituacionCobro> ordenaCreditosSituacionCobro(List<Credito> creditosTotal) {
		List<SituacionCobro> situacionCobroTotal = new ArrayList<SituacionCobro>();

		Map<Integer,String> titles = new HashMap<Integer,String>();
		titles.put(0,"Propuestas Emitidas");
		titles.put(1,"Por Notificar");
		titles.put(2,"Notificado");
		titles.put(3,"Aclaración para Ajuste");
		titles.put(5,"Solicitud de Convenio");
		titles.put(6,"Inconformidades");
		titles.put(7,"Efectividad de Fianza");
		titles.put(8,"Cobranza Especial");
		titles.put(9,"En Localización");
		titles.put(10,"Sustitución Patronal Por Dictaminar");
		titles.put(11,"Compensación de Adeudos");
		titles.put(12,"Huelga");
		titles.put(13,"Procuración de Cobro");
		titles.put(14,"Juicios");
		titles.put(15,"Recurso de Revocación");
		titles.put(16,"Insolvencia");
		titles.put(18,"Certificación de Pago");
		titles.put(19,"Concurso Mercantil");
		titles.put(20,"Actos Admvos. Auditoría Por Notificar");
		titles.put(21,"Aclaración Ante Auditoría");
		titles.put(22,"Cumplimiento de Laudo o Sentencia");
		titles.put(23,"Multas en Trámite de Condonación");
		titles.put(25,"Pagos en Conciliación");
		titles.put(26,"Responsabilidad Solidaria");
		titles.put(27,"Autodeterminados");
		titles.put(31,"Inicio de Tramitación");
		titles.put(32,"Embargo Bien Mueble Deposit. Ajena IMSS");
		titles.put(33,"Embargo Bien Mueble Deposit. IMSS");
		titles.put(34,"Embargo Bien Inmueble");
		titles.put(35,"Embargo de Negociación");
		titles.put(36,"Empresas Administradas");
		titles.put(37,"Bienes Valuados para Remate");
		titles.put(38,"En Autorización para Remate");
		titles.put(39,"Primera Almoneda");
		titles.put(40,"Segunda Almoneda");
		titles.put(41,"Venta Fuera de Subasta");
		titles.put(42,"Constitución de Garantía");
		titles.put(43,"Embargo Cuentas Bancarias");
		titles.put(51,"Convenio Pago inmediato con Cancelación");
		titles.put(52,"Convenio con Plazo");
		titles.put(53,"Convenio con Plazo y Condonación");
		titles.put(54,"Reestructuración");
		titles.put(55,"Rehabilitación");

		Map<Integer,SituacionCobro> sitCobros = new HashMap<Integer,SituacionCobro>();
		Collection<Credito> creditos;
		SituacionCobro sitCobro;
		
		for(Credito credito : creditosTotal){
			int crInc = Integer.parseInt(credito.getCrIncAct());

			if(titles.containsKey(crInc)){ //Se valida que el tipo de incidencia(crInc) del crédito sea válido.
				if(sitCobros.containsKey(crInc)){ //Si existe, se extrae SituacionCobro y la colección de Creditos
					sitCobro = sitCobros.get(crInc);
					creditos = sitCobro.getCreditos();
				}else{ //Si no, se crean nuevos objectos para ser agregados al mapa situacionCobros
					sitCobro	 = new SituacionCobro();
					creditos = new ArrayList<Credito>();
					sitCobro.setSituacionCobro(titles.get(crInc));
					sitCobro.setCrInc(credito.getCrIncAct());
				}
				
				creditos.add(credito);
				sitCobro.setCreditos(creditos);
				sitCobros.put(crInc, sitCobro);
			}
		}

		for(SituacionCobro situacionCobro : sitCobros.values()){ //Se valida que al menos exista un credito para reportar
			if(situacionCobro.getCreditos().size() >= 1 ) situacionCobroTotal.add(situacionCobro);
		}
   
		return situacionCobroTotal;
	}
	
	private List<TipoCobro> ordenaCreditosTipoCobro(List<Credito> creditosTotal) {
		List<TipoCobro> tipoCobrosTotal = new ArrayList<TipoCobro>();
		DecimalFormat to2Dec = new DecimalFormat("###,###,##0.00");
		Double totalSalTot = 0.0;
		Double totalActua = 0.0;
		Double totalRecar = 0.0;
		Double sumaTotales = 0.0;
		Map<String,String> titles = new HashMap<String,String>();	
		titles.put("2a","Emisión Mensual");
		//titles.put("2b","Omisión de Pago"); 
		titles.put("2b","Omisión Seguros IMSS"); //MEF Solicitud cambio 24/May/2012
		//titles.put("2c","Propuesta de Pago");//Se cambia a solicitud de Jorge Romero 06/01/2012
		titles.put("2c","Propuesta Seguros IMSS"); //MEF Solicitud cambio 24/May/2012
		titles.put("2d","Omisión de Pago");//Se cambia a solicitud de Jorge Romero 06/01/2012
		//titles.put("3","Diferencias en Pago");
		titles.put("3","Diferencias Seguros IMSS"); //MEF Solicitud cambio 24/May/2012
		titles.put("4","Avisos Extemporáneos");
		titles.put("5_61","Resultados de Auditoría");
		titles.put("50_55","Resultados de Dictamen");
		//titles.put("80","Multa por Omisión de Pago Seguros IMSS");
		titles.put("80","Multa por Omisión Seguros IMSS");//MEF Solicitud cambio 24/May/2012
		//titles.put("81","Multa por Omisión de Pago Seguros RCV");
		titles.put("81","Multa por Omisión Seguros RCV");//MEF Solicitud cambio 24/May/2012
		//titles.put("82a","Multa por Diferencias en Pago Seguros IMSS");
		titles.put("82a","Multa por Diferencias Seguros IMSS");//MEF Solicitud cambio 24/May/2012
		//titles.put("82b","Multa por Diferencias en Pago Seguros RCV");
		titles.put("82b","Multa por Diferencias Seguros RCV");//MEF Solicitud cambio 24/May/2012
		titles.put("83","Multa por Diferencias Riesgo de Trabajo");
		titles.put("84","Multa por Determinación R.T. Omisa o Extemporánea");
		titles.put("85","Multa por Determinar con Datos Falsos");
		titles.put("86","Multa por Impedir el Proc. Admvo. Ejecución");
		titles.put("87","Multa por Actos u Omisiones");
		titles.put("88","Multa por Actos u Omisiones");
		titles.put("89","Multa por Actos u Omisiones");
		titles.put("0","Capitales Constitutivos");
		//titles.put("8","Recargos Mor. No Documentados");
		titles.put("8","Recargos Moratorios No Documentados");//MEF Solicitud cambio 24/May/2012
		//titles.put("70","Gastos Médicos no Derechohabientes");
		titles.put("70","Gastos Médicos de no Derechohabientes");//MEF Solicitud cambio 24/May/2012
		titles.put("71","Gastos Inscripción Improcedente");

		Map<String,TipoCobro> tipoCobros = new HashMap<String,TipoCobro>();
		Collection<Credito> creditos;
		TipoCobro tipoCobro;
	
		for(Credito credito : creditosTotal){
			int crDoc = Integer.parseInt(credito.getCrDoc());
			String strDoc = credito.getCrDoc();
			/*Primero se validan los tipos de documentos para generar las llaves adecuadas para acceder a titles*/
			/*if(crDoc == 2){			
				if(!credito.getCrCred().substring(2, 3).equals("2")) {
					strDoc += "a";
				}else {
					strDoc += "b";
				}
			}*/
			//Cambio solicitado por Jorge Romero 06/01/2012
			if(crDoc == 2){			
				if(credito.getCrCred().substring(2, 3).equals("2")||credito.getCrCred().substring(2, 3).equals("9")) {
					strDoc += "d";
				}else if(credito.getCrCred().substring(2, 3).equals("1")) {
					strDoc += "c";
				}else {
					strDoc += "b";
				}
			}
			
			if(crDoc == 5 || crDoc == 56 || crDoc == 57 || crDoc == 58 || crDoc == 59 || crDoc == 60 || crDoc == 61)
			{	strDoc = "5_61";}
			
			if(crDoc == 50 || crDoc == 51 || crDoc == 52 || crDoc == 53 || crDoc == 54 || crDoc == 55)
			{	strDoc = "50_55";}

			if(crDoc == 82){
				double cuotaFija = Double.parseDouble(credito.getCrSalEyMFija().replaceAll(",", ""));
				double excedente = Double.parseDouble(credito.getCrSalEyMAdy().replaceAll(",", ""));
				if(cuotaFija>0.0) {
					strDoc += "a";

				} else if(excedente>0.0) {
					strDoc += "b";
				}
			}
			
			if(titles.containsKey(strDoc)){ //Se valida que tipo_documento(strDoc) del crédito sea válido.
				if(tipoCobros.containsKey(strDoc)){ //Si existe, se extrae TipoCobro y la colección de Creditos
					tipoCobro = tipoCobros.get(strDoc);
					creditos  = tipoCobro.getCreditos();
				}else{ //Si no, se crean nuevos objectos para ser agregados al mapa tipoCobros
					tipoCobro = new TipoCobro();
					creditos  = new ArrayList<Credito>();
					tipoCobro.setTipoCobro(titles.get(strDoc));
					tipoCobro.setCrDoc(credito.getCrDoc());
				}
				
				creditos.add(credito);
				tipoCobro.setCreditos(creditos);
				tipoCobros.put(strDoc, tipoCobro);
			}
		}
		
		
		
		for(TipoCobro tipCobro : tipoCobros.values()){ //Se valida que al menos exista un credito para reportar
			if(tipCobro.getCreditos().size() >= 1 ){
				List<Credito> creditosT = (List<Credito>)tipCobro.getCreditos();
				
				for(Credito cred: creditosT) {
					totalSalTot+=Double.valueOf(cred.getCrSalTot().replace(",", ""));
					totalActua+=Double.valueOf(cred.getAct().replace(",", ""));
					totalRecar+=Double.valueOf(cred.getCrInt().replace(",", ""));
				}
				
				sumaTotales = totalSalTot + totalActua + totalRecar;
				
				tipCobro.setSubTotalCuotas(to2Dec.format(totalSalTot));
				tipCobro.setSubTotalAct(to2Dec.format(totalActua));
				tipCobro.setSubTotalRec(to2Dec.format(totalRecar));
				tipCobro.setSubTotal(to2Dec.format(sumaTotales));
				tipoCobrosTotal.add(tipCobro);
				
				totalSalTot = 0.0;
				totalActua=0.0;
				totalRecar=0.0;
			}
		}
   
		return tipoCobrosTotal;
	}

	private FirmaElectronica obtenerDatosSellado(Patron patron,Map<String,Object> totales, int tipoReporte) throws EstadoAdeudoException{
		FirmaElectronica firmaElectronica = null;
		Locale locMEX = new Locale("es", "MX");
		Date fechaDelReporte = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss",locMEX);
		DecimalFormat to2Dec = new DecimalFormat("###,###,##0.00");
		String cadenaOriginal = "||Invocante:portalimssdigital";
	
		switch(tipoReporte) {
			case REPORTE_SITUACION_COBRO: 
				cadenaOriginal+= "|Tipo de reporte:" + DESC_REPORTE_SITUACION_COBRO;
				cadenaOriginal+="|Importe total cuotas:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_SAL_TOT.getKey()));
				cadenaOriginal+="|Importe actualizacion:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_ACT.getKey()));
				cadenaOriginal+="|Importe recargos:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_INT.getKey()));
				cadenaOriginal+="|Importe total:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.SUMA_TOTALES.getKey()));
			break;
			case REPORTE_SITUACION_COBRO_RCV:
				cadenaOriginal+= "|Tipo de reporte:" + DESC_REPORTE_SITUACION_COBRO_RCV;
				cadenaOriginal+="|Importe total cuotas:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_SAL_TOT.getKey()));
				cadenaOriginal+="|Importe actualizacion:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_ACTUA.getKey()));
				cadenaOriginal+="|Importe recargos:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_RECAR.getKey()));
				cadenaOriginal+="|Importe total:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.SUMA_TOTALES.getKey()));
			break;
			case REPORTE_TIPO_COBRO:
				cadenaOriginal+= "|Tipo de reporte:" + DESC_REPORTE_TIPO_COBRO;
				cadenaOriginal+="|Importe total cuotas:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_SAL_TOT.getKey()));
				cadenaOriginal+="|Importe actualizacion:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_ACT.getKey()));
				cadenaOriginal+="|Importe recargos:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_INT.getKey()));
				cadenaOriginal+="|Importe total:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.SUMA_TOTALES.getKey()));
			break;
			case REPORTE_TIPO_COBRO_RCV:
				cadenaOriginal+= "|Tipo de reporte:" + DESC_REPORTE_TIPO_COBRO_RCV;
				log.warn("Importe de actualizacion: " + TotalesCobranzaEnum.TOTAL_SAL_TOT.getKey());
				cadenaOriginal+="|Importe total cuotas:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_SAL_TOT.getKey()));
				cadenaOriginal+="|Importe actualizacion:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_ACTUA.getKey()));
				cadenaOriginal+="|Importe recargos:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.TOTAL_RECAR.getKey()));
				cadenaOriginal+="|Importe total:"+to2Dec.format((Double)totales.get(TotalesCobranzaEnum.SUMA_TOTALES.getKey()));
			break;
		}
		
		cadenaOriginal += "|Fecha:" + sdf.format(fechaDelReporte);
		System.out.println("------------------------------------>Patron" + patron);
		cadenaOriginal += "|Numero de registro patronal:"+patron.getRegPatronal()+patron.getCveModalidad();
		cadenaOriginal += "|Nombre o razón social:"+patron.getRazonSocial();
		
		if(patron.getRfc() != null) {
			cadenaOriginal += "|RFC:"+patron.getRfc();
		}
		cadenaOriginal += "||";
		
		cadenaOriginal = cadenaOriginal.replaceAll("'", "\'");
		cadenaOriginal = cadenaOriginal.replaceAll("\"", "\\\"");
		
		
		totales.put("cadenaOriginal", cadenaOriginal);
		
		RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal, null, patron.getRfc());

		if(selloDigital != null) {
			
			if(StringUtils.isBlank(selloDigital.getSello())) {
				throw new EstadoAdeudoException(ERROR_SELLADO);
			}
			
			//Se crea el objeto de firma digital
            firmaElectronica = new FirmaElectronica();
            firmaElectronica.setCadenaOriginal(cadenaOriginal);
            firmaElectronica.setReciboNotarial(selloDigital.getTramite());
            firmaElectronica.setSecuenciaNotaria(selloDigital.getTramite());
            firmaElectronica.setSerialCertificado(selloDigital.getNoSerie());
            firmaElectronica.setRecibo(selloDigital.getSello());
            firmaElectronica.setUrlAcuseFirma("");
            firmaElectronica.setIniciaVigenciaCertificado(new Date());
            firmaElectronica.setFinVigenciaCertificado(new Date());
            
			totales.put("selloDigital", selloDigital.getSello());
			totales.put("secuenciaNotaria", selloDigital.getTramite());
			totales.put("numeroSerie", selloDigital.getNoSerie());
			
		} else {
			throw new EstadoAdeudoException(ERROR_SELLADO);
		}
		totales.put("fechaReporteTerminado", fechaDelReporte);
		
		return firmaElectronica;
	}
	
	private Solicitud crearSolicitudYFirma(Map<String,Object> cadenas, String nrp, TipoTramiteEnum tipoTramite,FirmaElectronica firma) throws EstadoAdeudoException {
		
		Solicitud solicitud  = new Solicitud();
		SujetoObligado sujetoObligado = null;
		
		try{
			sujetoObligado = sujetoObligadoServiceBusinessRemote.consultarPorNumeroRegistroPatronal(nrp);
		} catch(Exception e) {
			throw new EstadoAdeudoException("No se encontr&oacute; al patr&oacute;n");
		}
		
		if(sujetoObligado == null) {
			throw new EstadoAdeudoException("No se encontr&oacute; al patr&oacute;n");
		}
		
		Date fechaActual = (Date)cadenas.get("fechaInicioReporte");
		Date fechaConclusion = (Date)cadenas.get("fechaReporteTerminado");

		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setFechaConclusion(fechaConclusion);
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.IMPRESION_EDO_ADEUDO.getValor().longValue());
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.setSubdelegacion(sujetoObligado.getSubdelegacion());
		
		
		TramiteSujetoObligado tramiteSO = new TramiteSujetoObligado();
		
		tramiteSO.setEstadoTramite(new EstadoTramite());
		tramiteSO.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		tramiteSO.getEstadoTramite().setDescripcion(EstadoTramiteEnum.CERRADO.getDescripcion());
		tramiteSO.setTipoTramite(new TipoTramite());
		tramiteSO.getTipoTramite().setIdTipoTramite(tipoTramite.getCodigo());
		tramiteSO.setFechaTramite(fechaActual);
		tramiteSO.setFechaPresentacion(fechaActual);
		tramiteSO.setFechaConclusion(fechaConclusion);
		tramiteSO.setIndRatificado(false);
		tramiteSO.setSujetoObligado(sujetoObligado);
		
		solicitud.getTramites().add(tramiteSO);
		
		try {
			solicitud = solicitudBusinessRemote.crear(solicitud);
		} catch (SolicitudNoValidaException e) {
			log.error("Error al guardar la solicitud",e);
			throw new EstadoAdeudoException("No fue posible guardar la solicitud de estado de adeudo");
		}
		
		if(firma != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firma);
		}
		
		return solicitud;
	}
}