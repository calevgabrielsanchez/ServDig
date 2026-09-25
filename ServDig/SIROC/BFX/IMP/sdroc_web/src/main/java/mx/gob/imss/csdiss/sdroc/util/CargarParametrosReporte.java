/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.util;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import mx.gob.imss.csdiss.sdroc.dto.AvisoObraDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;

/**
 * @author francisco.rodriguez
 *
 */
public class CargarParametrosReporte {

	/**
	 * @param informacionObra
	 * @param informacionObra
	 * @param tipoReporte
	 * @param pathImg
	 * @return
	 */
	public HashMap<String, Object> cargarParametrosReporte(Object datosRegistro, InformacionIncidenciaDTO informacionIncidencia, int tipoReporte, String pathImg, BufferedImage image_qr,
			String fechaFormato){
		SimpleDateFormat forma = new SimpleDateFormat("dd/MM/yyyy");
		HashMap<String, Object> param = new HashMap<String, Object>();
		InformacionObraDTO informacionObra = null;
		//String direccion = null;
		String cadenaOriginal = null;
		String PATRON_INTERMEDIARIO = "4";
		String PATRON_SUB_ESPECIALIZADO = "5";


		if(ReporteEnum.AVISO_UBICACION_OBRA.getValor() == tipoReporte
				|| ReporteEnum.AVISO_UBICACION_OBRA_ACUSE.getValor() == tipoReporte){
			AvisoObraDTO datosRegistroAviso = (AvisoObraDTO)datosRegistro;
			
			param.put(ParametroReporte.IMAGE_DIR.getValor(), pathImg);
			param.put(ParametroReporte.FOLIO.getValor(), datosRegistroAviso.getFolio());
			param.put(ParametroReporte.NUMERO_REGISTRO.getValor(), datosRegistroAviso.getCveRegistroAvisoObra().toString());
	        param.put(ParametroReporte.NUMERO_ACUERDO.getValor(), "XXX");
	        param.put(ParametroReporte.DIA_ACUERDO.getValor(), "XXX");
	        param.put(ParametroReporte.NUMERO_ARTICULO.getValor(), "XXX");
	        param.put(ParametroReporte.DELEGACION.getValor(), datosRegistroAviso.getSubDelegacionDTO().getDelegacionDTO().getNomDelegacion());
	        param.put(ParametroReporte.SUB_DELEGACION.getValor(), datosRegistroAviso.getSubDelegacionDTO().getNomSubdelegacion());
	        param.put(ParametroReporte.RAZON_SOCIAL.getValor(), datosRegistroAviso.getInformacionPatronDTO().getRefRazonSocial());
	        param.put(ParametroReporte.REGISTRO_PATRONAL.getValor(), datosRegistroAviso.getInformacionPatronDTO().getCveRegPatronal());
	        param.put(ParametroReporte.RFC.getValor(), datosRegistroAviso.getInformacionPatronDTO().getCveRfc());
	        param.put(ParametroReporte.TIPO_PATRON.getValor(), datosRegistroAviso.getInformacionPatronDTO().getTipoPatronDTO().getDesTipoPatron());
            param.put(ParametroReporte.UBICACION.getValor(), construyeDireccion(datosRegistroAviso));
            param.put(ParametroReporte.SELLO_DIGITAL_ACUSE.getValor(), datosRegistroAviso.getRefSelloDigital());
     		param.put(ParametroReporte.CADENA_FIRMA.getValor(), datosRegistroAviso.getRefSelloDigital());
     		cadenaOriginal = datosRegistroAviso.getRefCadenaOriginal();
     		cadenaOriginal = cadenaOriginal.replaceFirst("%NOMBRE_TRAMITE%", "|Tr\u00E1mite: Aviso de ubicaci\u00F3n de obra de construcci\u00F3n");
     		cadenaOriginal = cadenaOriginal.replaceFirst("%NUM_REG_OBRA%", "|Aviso de ubicaci\u00F3n: " + datosRegistroAviso.getCveRegistroAvisoObra());
     		cadenaOriginal = cadenaOriginal.replaceFirst("%FECHA_ACTUAL%", "Fecha: " + fechaFormato);
     		param.put(ParametroReporte.IMAGEN_QR.getValor(), image_qr);
     		datosRegistroAviso.setRefCadenaOriginal(cadenaOriginal);
     		param.put(ParametroReporte.CADENA_ORIGINAL_ACUSE.getValor(), cadenaOriginal);
		} else {
			informacionObra = (InformacionObraDTO)datosRegistro;
			param.put(ParametroReporte.IMAGE_DIR.getValor(), pathImg);
			param.put(ParametroReporte.FOLIO.getValor(), (informacionObra != null && informacionObra.getFolio() != null) ? informacionObra.getFolio() 
					: informacionIncidencia != null ? informacionIncidencia.getFolio(): null);
			param.put(ParametroReporte.NUMERO_REGISTRO.getValor(), informacionObra.getCveRegistroObra().toString());
	        param.put(ParametroReporte.NUMERO_ACUERDO.getValor(), "XXX");
	        param.put(ParametroReporte.DIA_ACUERDO.getValor(), "XXX");
	        param.put(ParametroReporte.NUMERO_ARTICULO.getValor(), "XXX");
	        param.put(ParametroReporte.DELEGACION.getValor(), informacionObra.getSubDelegacionDTO().getDelegacionDTO().getNomDelegacion());
	        param.put(ParametroReporte.SUB_DELEGACION.getValor(), informacionObra.getSubDelegacionDTO().getNomSubdelegacion());
	        param.put(ParametroReporte.RAZON_SOCIAL.getValor(), informacionObra.getInformacionPatronDTO().getRefRazonSocial());
	        param.put(ParametroReporte.REGISTRO_PATRONAL.getValor(), informacionObra.getInformacionPatronDTO().getCveRegPatronal());
	        param.put(ParametroReporte.RFC.getValor(), informacionObra.getInformacionPatronDTO().getCveRfc());
	        param.put(ParametroReporte.TIPO_PATRON.getValor(), informacionObra.getInformacionPatronDTO().getTipoPatronDTO().getDesTipoPatron());
	        param.put(ParametroReporte.CLASE_OBRA.getValor(),  String.valueOf(informacionObra.getTipoObraDTO().getClasificacionObraDTO().getCveClasificacionObra())); 
	        
	        String desObra = informacionObra.getTipoObraDTO().getDesTipoObra();
			String desObjetoContrato = informacionObra.getObjetoContratoDTO().getDesObjetoContrato();

	        if(desObra.equalsIgnoreCase("Seleccione ...") || desObra.toUpperCase().startsWith("SIN TIPO")){
	        	//Se valida si se debe concatenar la descripcion del otro objeto contrato
				if (desObjetoContrato.equalsIgnoreCase("Otro")){
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> se concatena otro objeto contrato cargarParametrosReporte 90");
					desObra =  desObjetoContrato.concat("-").concat(String.valueOf(informacionObra.getRefOtroObjetoContrato()));
				} else {
					desObra = desObjetoContrato;
				}
	        }

	        param.put(ParametroReporte.OC_SUBESP.getValor(), informacionObra.getDesObjetoContratoSubEsp());
	        param.put(ParametroReporte.TIPO_OBRA.getValor(), desObra);//TIpo obra puede ser tipo de obra u objeto del contrato para usuario intermediario
	        param.put(ParametroReporte.TIPO_OBRA_TITULO.getValor(), String.valueOf(informacionObra.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron()));
	        param.put(ParametroReporte.FEC_INICIO.getValor(), forma.format(informacionObra.getFecIniObra()));
	        param.put(ParametroReporte.FEC_TERMINO.getValor(), forma.format(informacionObra.getFecFinObra()));     	
	     	param.put(ParametroReporte.MONTO.getValor(), informacionObra.getImpObra());
	     	param.put(ParametroReporte.SUPERFICIE.getValor(), informacionObra.getRefSupConstruccion());
	     	cadenaOriginal = informacionObra.getRefCadenaOriginal() != null ? informacionObra.getRefCadenaOriginal() : informacionIncidencia.getRefCadenaOriginal();

	     	Long cveTipoPatron = informacionObra.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron();

			if (cveTipoPatron != 4L){
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> patron no es intermediario se respetan etiquetas cargarParametrosReporte");
				cadenaOriginal = cadenaOriginal.replaceFirst("%NOMBRE_TRAMITE%", "|Tr\u00E1mite: Registro de obra de construcci\u00F3n");
				cadenaOriginal = cadenaOriginal.replaceFirst("%NUM_REG_OBRA%", "|N\u00FAmero de registro de obra: " + informacionObra.getCveRegistroObra());
			} else {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> patron intermediario cambio de etiquetas cargarParametrosReporte 106");
				cadenaOriginal = cadenaOriginal.replaceFirst("%NOMBRE_TRAMITE%", "|Tr\u00E1mite: Recibo de Intermediario");
				cadenaOriginal = cadenaOriginal.replaceFirst("%NUM_REG_OBRA%", "|N\u00FAmero de registro de intermediario: " + informacionObra.getCveRegistroObra());
			}

	     	cadenaOriginal = cadenaOriginal.replaceFirst("%FECHA_ACTUAL%", "Fecha: " + fechaFormato);
	     	informacionObra.setRefCadenaOriginal(cadenaOriginal);
	     	param.put(ParametroReporte.CADENA_ORIGINAL_ACUSE.getValor(), cadenaOriginal);

	     	//Carga de parametros REPSE y num aprox de trabajadores
			param.put(ParametroReporte.NUM_APROX_TRABAJADORES.getValor(), informacionObra.getNumAproxTrabajadores());
			param.put(ParametroReporte.NUMERO_REPSE.getValor(),informacionObra.getNumRegStps());
		}
		
		param = cargarParametrosReporteContinuacion(param, datosRegistro, informacionIncidencia, tipoReporte, informacionObra);
		
		if(ReporteEnum.REGISTRO_OBRA_ACUSE.getValor() == tipoReporte){
			param.put(ParametroReporte.TIPO_TITULO.getValor(), "2");
     		param.put(ParametroReporte.SELLO_DIGITAL_ACUSE.getValor(), informacionObra.getRefSelloDigital());
     		param.put(ParametroReporte.IMAGEN_QR.getValor(), image_qr);
     	}else{
     		if(informacionObra != null){
     			param.put(ParametroReporte.TIPO_TITULO.getValor(), "1");
         		param.put(ParametroReporte.CADENA_FIRMA.getValor(), informacionObra.getRefSelloDigital());	
     		}     		
     	}
		
		if(ReporteEnum.TERMINACION_ACUSE.getValor() == tipoReporte
				|| ReporteEnum.CANCELACION_ACUSE.getValor() == tipoReporte
				|| ReporteEnum.REANUDACION_ACUSE.getValor() == tipoReporte
				|| ReporteEnum.SUSPENSION_ACUSE.getValor() == tipoReporte
				|| ReporteEnum.REPORTE_BIMESTRAL_ACUSE.getValor() == tipoReporte
				|| ReporteEnum.ACTUALIZACION_ACUSE.getValor() == tipoReporte){
			param.put(ParametroReporte.TIPO_TITULO.getValor(), "2");
     		param.put(ParametroReporte.SELLO_DIGITAL_ACUSE.getValor(), informacionIncidencia.getRefSelloDigital());
     		param.put(ParametroReporte.IMAGEN_QR.getValor(), image_qr);
     	}else{
     		if(informacionIncidencia != null){
	     		param.put(ParametroReporte.TIPO_TITULO.getValor(), "1");
	     		param.put(ParametroReporte.CADENA_FIRMA.getValor(), informacionIncidencia.getRefSelloDigital());
     		}
     	}
		
		if(informacionObra.getIndRegPatAmparo()!= null){
			System.out.println("identificador idRgAmparo "+informacionObra.getIndRegPatAmparo());
			param.put(ParametroReporte.IND_REG_PAT_AMPARO.getValor(),String.valueOf(informacionObra.getIndRegPatAmparo()));
		}
			
		
		
		return param;
	}
	
	
	
	public HashMap<String, Object> cargarParametrosReporteContinuacion(HashMap<String, Object> param, Object datosRegistro, 
			InformacionIncidenciaDTO informacionIncidencia, int tipoReporte,InformacionObraDTO informacionObra){
		SimpleDateFormat forma = new SimpleDateFormat("dd/MM/yyyy");
		
		if(ReporteEnum.REGISTRO_OBRA.getValor() == tipoReporte
				||ReporteEnum.REGISTRO_OBRA_ACUSE.getValor() == tipoReporte){
			
			param.put(ParametroReporte.TITULO_MONTO.getValor(), String.valueOf(informacionObra.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron()));            
			param.put(ParametroReporte.TITULO_PERIODO.getValor(), String.valueOf(informacionObra.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron()));                 
	     	param.put(ParametroReporte.NUMERO_PROCEDIMIENTO.getValor(), informacionObra.getNumProcedimiento());
            param.put(ParametroReporte.UBICACION.getValor(), construyeDireccion(informacionObra));	     	
	     	
		} else if(ReporteEnum.ACTUALIZACION.getValor() == tipoReporte
				|| ReporteEnum.ACTUALIZACION_ACUSE.getValor() == tipoReporte){
			
	     	param.put(ParametroReporte.NUMERO_PROCEDIMIENTO.getValor(), informacionObra.getNumProcedimiento());    
	     	param.put(ParametroReporte.MOTIVO_ACTUALIZACION.getValor(), informacionIncidencia.getMotivoTipoIncidenciaDTO().getMotivoDTO().getDesMotivo());
	     	param.put(ParametroReporte.UBICACION.getValor(), construyeDireccion(informacionObra));

		} else if(ReporteEnum.REPORTE_BIMESTRAL.getValor() == tipoReporte
				|| ReporteEnum.REPORTE_BIMESTRAL_ACUSE.getValor() == tipoReporte){
			            
			param.put(ParametroReporte.TITULO_PERIODO.getValor(), String.valueOf(informacionObra.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron()));
	     	param.put(ParametroReporte.NUMERO_PROCEDIMIENTO.getValor(), informacionObra.getNumProcedimiento());
	     	
	     	String bimestre = "0".concat(String.valueOf(informacionIncidencia.getCalendarioReporteDTO().getCveBimCalendario()))
	     			.concat("-")
	     			.concat(String.valueOf(informacionIncidencia.getNumAnio()));
	     	
	     	param.put(ParametroReporte.BIMESTRE_ANO_PRESENTAR.getValor(), bimestre);
	     	param.remove(ParametroReporte.MONTO.getValor());
	     	param.put(ParametroReporte.MONTO.getValor(), informacionObra.getImpEjercido());
	     	param.put(ParametroReporte.UBICACION.getValor(), construyeDireccion(informacionObra));

		} else if(ReporteEnum.SUSPENSION.getValor() == tipoReporte
				|| ReporteEnum.SUSPENSION_ACUSE.getValor() == tipoReporte){

            param.put(ParametroReporte.TITULO_PERIODO.getValor(), String.valueOf(informacionObra.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron()));
            param.put(ParametroReporte.UBICACION.getValor(), construyeDireccion(informacionObra));
	     	param.put(ParametroReporte.NUMERO_PROCEDIMIENTO.getValor(), informacionObra.getNumProcedimiento()); 
	     	
	     	String bimestre = "0";
	     	param.put(ParametroReporte.BIMESTRE_ANO_PRESENTAR.getValor(), bimestre);
	     	param.put(ParametroReporte.FECHA_SUSPENSION_OBRA.getValor(), forma.format(informacionIncidencia.getFecSuspencion()));
	     	param.put(ParametroReporte.MOTIVO_SUSPENSION.getValor(), informacionIncidencia.getMotivoTipoIncidenciaDTO().getMotivoDTO().getDesMotivo());
	     	param.remove(ParametroReporte.MONTO.getValor());
	     	param.put(ParametroReporte.MONTO.getValor(), informacionIncidencia.getImpEjercido());
	     	
		} else if(ReporteEnum.REANUDACION.getValor() == tipoReporte
				|| ReporteEnum.REANUDACION_ACUSE.getValor() == tipoReporte){

			param.put(ParametroReporte.TITULO_MONTO.getValor(), String.valueOf(informacionObra.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron()));
            param.put(ParametroReporte.FEC_REANUDACION.getValor(), forma.format(informacionIncidencia.getFecReanudacion())); //VALIDAR CAMPO           
	     	param.put(ParametroReporte.NUMERO_PROCEDIMIENTO.getValor(),  informacionObra.getNumProcedimiento());	     	
	     	param.put(ParametroReporte.MOTIVO_ACTUALIZACION.getValor(), informacionIncidencia.getMotivoTipoIncidenciaDTO().getMotivoDTO().getDesMotivo());
	     	param.put(ParametroReporte.UBICACION.getValor(), construyeDireccion(informacionObra));

		} else if(ReporteEnum.CANCELACION.getValor() == tipoReporte
				|| ReporteEnum.CANCELACION_ACUSE.getValor() == tipoReporte){
           
            param.put(ParametroReporte.TITULO_PERIODO.getValor(), String.valueOf(informacionObra.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron()));
	     	param.put(ParametroReporte.NUMERO_PROCEDIMIENTO.getValor(), informacionObra.getNumProcedimiento());
	     	param.put(ParametroReporte.FECHA_CANCELACION_OBRA.getValor(), forma.format(informacionIncidencia.getFecCanObra()));	     	
	     	param.put(ParametroReporte.MOTIVO_CANCELACION.getValor(), informacionIncidencia.getMotivoTipoIncidenciaDTO().getMotivoDTO().getDesMotivo());
	     	param.put(ParametroReporte.UBICACION.getValor(), construyeDireccion(informacionObra));
	     	param.put(ParametroReporte.MONTO.getValor(), informacionIncidencia.getImpEjercido());
		} else if(ReporteEnum.TERMINACION.getValor() == tipoReporte
				|| ReporteEnum.TERMINACION_ACUSE.getValor() == tipoReporte){
           
            param.put(ParametroReporte.UBICACION.getValor(), construyeDireccion(informacionObra));            
	     	param.put(ParametroReporte.NUMERO_PROCEDIMIENTO.getValor(), String.valueOf(informacionObra.getNumProcedimiento()));
	     	param.put(ParametroReporte.MONTO.getValor(), informacionIncidencia.getImpEjercido());
	     	param.put(ParametroReporte.FEC_TERMINA.getValor(), informacionIncidencia.getFecFinObra());
	     	param.put(ParametroReporte.SUPERFICIE_TERMINA.getValor(), informacionIncidencia.getRefSupConstruccion());
		}
		
		return param;
	}
	
	
	
	
	public HashMap<String, Object> cargarParametrosResumenObra(InformacionObraDTO informacionObra, List<InformacionIncidenciaDTO>  listInformacionIncidencias, String bimestre, String pathImg, String cveRegObraPrincipal){
		SimpleDateFormat forma = new SimpleDateFormat("dd/MM/yyyy");
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		param.put(ParametroReporte.IMAGE_DIR.getValor(), pathImg);
		param.put(ParametroReporte.NUMERO_REGISTRO.getValor(), informacionObra.getCveRegistroObra().toString());
        param.put(ParametroReporte.DELEGACION.getValor(), informacionObra.getSubDelegacionDTO().getDelegacionDTO().getNomDelegacion());
        param.put(ParametroReporte.SUB_DELEGACION.getValor(), informacionObra.getSubDelegacionDTO().getNomSubdelegacion());
        param.put(ParametroReporte.RAZON_SOCIAL.getValor(), informacionObra.getInformacionPatronDTO().getRefRazonSocial());
        param.put(ParametroReporte.REGISTRO_PATRONAL.getValor(), informacionObra.getInformacionPatronDTO().getCveRegPatronal());
        param.put(ParametroReporte.RFC.getValor(), informacionObra.getInformacionPatronDTO().getCveRfc());               

        String desObra = informacionObra.getTipoObraDTO().getDesTipoObra();
        
        if(desObra.equalsIgnoreCase("Seleccione ...") || desObra.toUpperCase().startsWith("SIN TIPO")){
        	desObra = informacionObra.getObjetoContratoDTO().getDesObjetoContrato();
        }
        
        param.put(ParametroReporte.TIPO_OBRA.getValor(), desObra);
        param.put(ParametroReporte.TIPO_OBRA_TITULO.getValor(), String.valueOf(informacionObra.getInformacionPatronDTO().getTipoPatronDTO().getCveTipoPatron()));
        param.put(ParametroReporte.CVE_TIPO_OBRA.getValor(), String.valueOf(informacionObra.getTipoObraDTO().getClasificacionObraDTO().getCveClasificacionObra()));
        param.put(ParametroReporte.CLASE_OBRA.getValor(),  String.valueOf(informacionObra.getTipoObraDTO().getClasificacionObraDTO().getCveClasificacionObra()));
        param.put(ParametroReporte.TIPO_PATRON.getValor(), informacionObra.getInformacionPatronDTO().getTipoPatronDTO().getDesTipoPatron());
        param.put(ParametroReporte.FEC_INICIO.getValor(), forma.format(informacionObra.getFecIniObra()));
        param.put(ParametroReporte.FEC_TERMINO.getValor(), forma.format(informacionObra.getFecFinObra()));     	
     	param.put(ParametroReporte.MONTO.getValor(), informacionObra.getImpObra());
     	param.put(ParametroReporte.SUPERFICIE.getValor(), informacionObra.getRefSupConstruccion());     	
     	param.put(ParametroReporte.FEC_REGISTRO.getValor(), forma.format(informacionObra.getStpCreaReg()));
     	param.put(ParametroReporte.NUMERO_REGISTRO_CONTRA.getValor(), cveRegObraPrincipal!=null?cveRegObraPrincipal:"");
     	param.put(ParametroReporte.CALLE.getValor(), informacionObra.getUbicacionObraDTO().getCalle());
     	param.put(ParametroReporte.NUM_EXT.getValor(), informacionObra.getUbicacionObraDTO().getNumExterior());
     	param.put(ParametroReporte.NUM_INT.getValor(), informacionObra.getUbicacionObraDTO().getNumInterior());
     	param.put(ParametroReporte.COLONIA.getValor(), informacionObra.getUbicacionObraDTO().getRefColonia());
     	param.put(ParametroReporte.MUNICIPIO.getValor(), informacionObra.getUbicacionObraDTO().getRefMunicipio());
     	param.put(ParametroReporte.ENTIDAD.getValor(), informacionObra.getUbicacionObraDTO().getRefEntidad());
     	param.put(ParametroReporte.CP.getValor(), informacionObra.getUbicacionObraDTO().getCodigoPostal());
     	param.put(ParametroReporte.OBSERVACIONES.getValor(), informacionObra.getUbicacionObraDTO().getRefObservacion());
     	param.put(ParametroReporte.NUMERO_PROCEDIMIENTO.getValor(), informacionObra.getNumProcedimiento()!=null?informacionObra.getNumProcedimiento():"");
     	param.put(ParametroReporte.MONTO_EJERCIDO.getValor(), informacionObra.getImpEjercido());
     	param.put(ParametroReporte.BIMESTRE.getValor(), bimestre);
     	param.put(ParametroReporte.OBSERVA_REGISTRO.getValor(), informacionObra.getRefObservacion());
     	param.put(ParametroReporte.UBICACION.getValor(), construyeDireccion(informacionObra));
     	
     	for(InformacionIncidenciaDTO incidencia : listInformacionIncidencias){
     		if(incidencia.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO()
					.getCveTipoIncidencia() == IncidenciasEnum.ACTUALIZACION.getValor()){
     			param.put(ParametroReporte.FEC_ACT.getValor(), obtenerFechaComoString(incidencia.getFecActualizacion()));
     			param.put(ParametroReporte.FEC_TER_ACT.getValor(),  obtenerFechaComoString(incidencia.getFecFinObra()));
     			param.put(ParametroReporte.MONTO_ACT.getValor(), incidencia.getImpObra());
     			param.put(ParametroReporte.SUPERFICIE_ACT.getValor(), incidencia.getRefSupConstruccion());
     			param.put(ParametroReporte.MOTIVO_ACT.getValor(), incidencia.getMotivoTipoIncidenciaDTO().getMotivoDTO().getDesMotivo());
     		} else if(incidencia.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO()
					.getCveTipoIncidencia() == IncidenciasEnum.SUSPENSION.getValor()){
     			param.put(ParametroReporte.FEC_SUS.getValor(),  obtenerFechaComoString(incidencia.getFecSuspencion()));
     			param.put(ParametroReporte.MONTO_SUS.getValor(), incidencia.getImpEjercido());
     			param.put(ParametroReporte.MOTIVO_SUS.getValor(), incidencia.getMotivoTipoIncidenciaDTO().getMotivoDTO().getDesMotivo());
     		} else if(incidencia.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO()
					.getCveTipoIncidencia() == IncidenciasEnum.REANUDACION.getValor()){
     			param.put(ParametroReporte.FEC_REANU.getValor(), obtenerFechaComoString(incidencia.getFecReanudacion()));
     			param.put(ParametroReporte.MONTO_EJER_REANU.getValor(), incidencia.getImpEjercido());
     			param.put(ParametroReporte.FEC_TER_REANU.getValor(),  obtenerFechaComoString(incidencia.getFecFinObra()));
     			param.put(ParametroReporte.MONTO_REANU.getValor(), incidencia.getImpObra());
     			param.put(ParametroReporte.SUPERFICIE_REANU.getValor(), incidencia.getRefSupConstruccion());
     			param.put(ParametroReporte.MOTIVO_REANU.getValor(), incidencia.getMotivoTipoIncidenciaDTO().getMotivoDTO().getDesMotivo());
     		} else if(incidencia.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO()
					.getCveTipoIncidencia() == IncidenciasEnum.CANCELACION.getValor()){
     			param.put(ParametroReporte.FEC_CANCELA.getValor(),  obtenerFechaComoString(incidencia.getFecCanObra()));
     			param.put(ParametroReporte.MONTO_CANCELA.getValor(), incidencia.getImpEjercido());
     			param.put(ParametroReporte.MOTIVO_CANCELA.getValor(), incidencia.getMotivoTipoIncidenciaDTO().getMotivoDTO().getDesMotivo());
     		} else if(incidencia.getMotivoTipoIncidenciaDTO().getTipoIncidenciaDTO()
					.getCveTipoIncidencia() == IncidenciasEnum.TERMINACION.getValor()){
     			param.put(ParametroReporte.FEC_TERMINA.getValor(),  obtenerFechaComoString(incidencia.getFecFinObra()));
     			param.put(ParametroReporte.MONTO_TERMINA.getValor(), incidencia.getImpEjercido());
     			param.put(ParametroReporte.SUPERFICIE_TERMINA.getValor(), incidencia.getRefSupConstruccion());
     		}
     	}
		return param;
	}
	
	
	private String obtenerFechaComoString(Date fecha){
		String result = null;
		SimpleDateFormat forma = new SimpleDateFormat("dd/MM/yyyy");
		if(fecha != null){
			result = forma.format(fecha);
		}else{
			result = "";
		}
		return result;
	}
	
	
	private Object construyeDireccion(Object informacionRegistro) {
		StringBuilder direccion = new StringBuilder();
		
		if(informacionRegistro instanceof InformacionObraDTO) {
			direccion = construyeDireccionSiInformacionRegistroEsInformacionObraDTO(informacionRegistro);
	        
		} else if(informacionRegistro instanceof AvisoObraDTO) {
			direccion = construyeDireccionSiInformacionRegistroEsAvisoObraDTO(informacionRegistro);
		}
		
		return direccion.toString().toUpperCase();
	}
	
	public StringBuilder construyeDireccionSiInformacionRegistroEsInformacionObraDTO(Object informacionRegistro){
		StringBuilder direccion = new StringBuilder();
		InformacionObraDTO informacionObra = (InformacionObraDTO) informacionRegistro; 
     	if(informacionObra.getUbicacionObraDTO().getCalle() != null)
     		direccion.append(informacionObra.getUbicacionObraDTO().getCalle());
     	direccion.append(" EXT.");
     	if(informacionObra.getUbicacionObraDTO().getNumExterior() != null)
     		direccion.append(" ").append(informacionObra.getUbicacionObraDTO().getNumExterior());
     	if(informacionObra.getUbicacionObraDTO().getNumExteriorAlf() != null && !informacionObra.getUbicacionObraDTO().getNumExteriorAlf().equals(""))
     		direccion.append(" ").append(informacionObra.getUbicacionObraDTO().getNumExteriorAlf());
     	else 
     		if(informacionObra.getUbicacionObraDTO().getNumExterior() == null || informacionObra.getUbicacionObraDTO().getNumExterior().equals(""))
     			direccion.append(" SN");
     	direccion.append(" INT.");
     	if(informacionObra.getUbicacionObraDTO().getNumInterior()!= null && !informacionObra.getUbicacionObraDTO().getNumInterior().equals("0"))
        	direccion.append(" ").append(informacionObra.getUbicacionObraDTO().getNumInterior());
     	if(informacionObra.getUbicacionObraDTO().getNumInteriorAlf() != null && !informacionObra.getUbicacionObraDTO().getNumInteriorAlf().equals(""))
     		direccion.append(" ").append(informacionObra.getUbicacionObraDTO().getNumInteriorAlf());
     	else 
     		if(informacionObra.getUbicacionObraDTO().getNumInterior()== null || informacionObra.getUbicacionObraDTO().getNumInterior().equals("0"))
     			direccion.append(" SN");
     	if(informacionObra.getUbicacionObraDTO().getRefColonia() != null)
     		direccion.append(", ").append(informacionObra.getUbicacionObraDTO().getRefColonia()).append(", ");
        if(informacionObra.getUbicacionObraDTO().getRefMunicipio() != null)            
        	direccion.append(informacionObra.getUbicacionObraDTO().getRefMunicipio()).append(", ");
        if(informacionObra.getUbicacionObraDTO().getRefEntidad() != null)
    		direccion.append(informacionObra.getUbicacionObraDTO().getRefEntidad()).append(", ");
        if(informacionObra.getUbicacionObraDTO().getCodigoPostal() != null)
        	direccion.append("CP ").append(informacionObra.getUbicacionObraDTO().getCodigoPostal());
        
        return direccion;
		
	}	
	
	public StringBuilder construyeDireccionSiInformacionRegistroEsAvisoObraDTO(Object informacionRegistro){
		StringBuilder direccion = new StringBuilder();
		AvisoObraDTO avisoObra = (AvisoObraDTO) informacionRegistro; 
		
		if(avisoObra.getUbicacionObraDTO().getCalle() != null)
     		direccion.append(avisoObra.getUbicacionObraDTO().getCalle());
     	direccion.append(" EXT.");
     	if(avisoObra.getUbicacionObraDTO().getNumExterior() != null)
     		direccion.append(" ").append(avisoObra.getUbicacionObraDTO().getNumExterior());
     	if(avisoObra.getUbicacionObraDTO().getNumExteriorAlf() != null )
     		direccion.append(" ").append(avisoObra.getUbicacionObraDTO().getNumExteriorAlf());
     	else 
     		if(avisoObra.getUbicacionObraDTO().getNumExterior() == null || avisoObra.getUbicacionObraDTO().getNumExterior().equals(""))
     			direccion.append(" SN");
     	direccion.append(" INT.");
     	if(avisoObra.getUbicacionObraDTO().getNumInterior()!= null && !avisoObra.getUbicacionObraDTO().getNumInterior().equals("0"))
        	direccion.append(" ").append(avisoObra.getUbicacionObraDTO().getNumInterior());
     	if(avisoObra.getUbicacionObraDTO().getNumInteriorAlf() != null && !avisoObra.getUbicacionObraDTO().getNumInteriorAlf().equals(""))
     		direccion.append(" ").append(avisoObra.getUbicacionObraDTO().getNumInteriorAlf());
     	else 
     		if(avisoObra.getUbicacionObraDTO().getNumInterior()== null || avisoObra.getUbicacionObraDTO().getNumInterior().equals("0"))
     			direccion.append(" SN");
     	if(avisoObra.getUbicacionObraDTO().getRefColonia() != null)
     		direccion.append(", ").append(avisoObra.getUbicacionObraDTO().getRefColonia()).append(", ");
        if(avisoObra.getUbicacionObraDTO().getRefMunicipio() != null)            
        	direccion.append(avisoObra.getUbicacionObraDTO().getRefMunicipio()).append(", ");
        if(avisoObra.getUbicacionObraDTO().getRefEntidad() != null)
    		direccion.append(avisoObra.getUbicacionObraDTO().getRefEntidad()).append(", ");
        if(avisoObra.getUbicacionObraDTO().getCodigoPostal() != null)
        	direccion.append("CP ").append(avisoObra.getUbicacionObraDTO().getCodigoPostal());
		
        return direccion;
	}
}
