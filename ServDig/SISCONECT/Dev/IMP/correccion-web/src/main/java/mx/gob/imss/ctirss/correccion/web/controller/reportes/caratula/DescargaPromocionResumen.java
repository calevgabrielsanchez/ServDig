package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CPPDResumenVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CPPDVO;

/**
 * 
 * @author Saúl Rosales Piedragil
 * @date 31/07/2012
 * @version 1.0.0
 * Objeto Visual para generación de reporte en excel de Resumen de promoción
 */
@SuppressWarnings("rawtypes")
public class DescargaPromocionResumen extends AbstractReportesCaratula {
	CPPDResumenVO registroDeTotales;
	
	/**
	 * @author Saúl Rosales Piedragil
	 * @param myList lista de objetos con arreglos cada uno representando un registro en BD
	 * @param templateXLS
	 * @param outfileName nombre del archivo plantilla en excel
	 * @param request objeto que representa la petición http
	 * @param response objeto que representa la respuesta http
	 * @since 31/07/2012
	 * Constructor que inicializa las propiedades
	 */
	public DescargaPromocionResumen(List myList, String templateXLS, String outfileName,
									HttpServletRequest request, HttpServletResponse response){
		
		setMyList(myList);
		setTemplateXLS(templateXLS);
		setOutfileName(outfileName);
		setRequest(request);
		setResponse(response);
		setReXLS();
		registroDeTotales = new CPPDResumenVO();
		registroDeTotales.setDescripcion("TOTALES");
        registroDeTotales.setTotTipo("0.00");
        registroDeTotales.setTotCOPConvSP("0.00");
        registroDeTotales.setTotTCOPSP("0.00");
        registroDeTotales.setTotTCOPAct("0.00");
        registroDeTotales.setTotTCOPRec("0.00");
        registroDeTotales.setTotTCOPTotal("0.00");
        registroDeTotales.setTotTCOPMultas("0.00");
        registroDeTotales.setTotTrabRevisados("0.00");
        registroDeTotales.setTotTrabOmisos("0.00");
        registroDeTotales.setTotTrabSub("0.00");
        registroDeTotales.setTotTCOPSPPagada("0.00");
        registroDeTotales.setTotTCOPPendientePago("0.00");
        registroDeTotales.setTotRCVConvSP("0.00");
        registroDeTotales.setTotTRCVSP("0.00");
        registroDeTotales.setTotTRCVAct("0.00");
        registroDeTotales.setTotTRCVRec("0.00");
        registroDeTotales.setTotTRCVTotal("0.00");
	}
	
	public String obtenerReporte(){
		return obtenerReporte(null,null);
	}
	
	/**
	 * @author Saúl Rosales Piedragil
	 * @since 31/07/2012
	 * @param registro pojo correspondiente a un registro en BD el cual se sumará en cada uno de sus campos al registro registroDeTotales
	 */
	public void sumaAGranTotal(CPPDResumenVO registro){
		BigDecimal granTotal = null;
		BigDecimal cantidadAgregada = null;
        if(registro.getTotTipo() !=null && !registro.getTotTipo().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTipo());
        	cantidadAgregada = new BigDecimal(registro.getTotTipo());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTipo(granTotal.toString());
        }
        if(registro.getTotCOPConvSP() !=null && !registro.getTotCOPConvSP().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotCOPConvSP());
        	cantidadAgregada = new BigDecimal(registro.getTotCOPConvSP());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotCOPConvSP(granTotal.toString());
        }
        if(registro.getTotTCOPSP() !=null && !registro.getTotTCOPSP().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTCOPSP());
        	cantidadAgregada = new BigDecimal(registro.getTotTCOPSP());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTCOPSP(granTotal.toString());        	
        }
        if(registro.getTotTCOPAct() !=null && !registro.getTotTCOPAct().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTCOPAct());
        	cantidadAgregada = new BigDecimal(registro.getTotTCOPAct());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTCOPAct(granTotal.toString());      
        }
        if(registro.getTotTCOPRec() !=null && !registro.getTotTCOPRec().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTCOPRec());
        	cantidadAgregada = new BigDecimal(registro.getTotTCOPRec());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTCOPRec(granTotal.toString());      
        }
        if(registro.getTotTCOPTotal() !=null && !registro.getTotTCOPTotal().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTCOPTotal());
        	cantidadAgregada = new BigDecimal(registro.getTotTCOPTotal());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTCOPTotal(granTotal.toString());      
        }
        if(registro.getTotTCOPMultas() !=null && !registro.getTotTCOPMultas().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTCOPMultas());
        	cantidadAgregada = new BigDecimal(registro.getTotTCOPMultas());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTCOPMultas(granTotal.toString());      
        }
        if(registro.getTotTrabRevisados() !=null && !registro.getTotTrabRevisados().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTrabRevisados());
        	cantidadAgregada = new BigDecimal(registro.getTotTrabRevisados());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTrabRevisados(granTotal.toString());      
        }
        if(registro.getTotTrabOmisos() !=null && !registro.getTotTrabOmisos().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTrabOmisos());
        	cantidadAgregada = new BigDecimal(registro.getTotTrabOmisos());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTrabOmisos(granTotal.toString());      
        }
        if(registro.getTotTrabSub() !=null && !registro.getTotTrabSub().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTrabSub());
        	cantidadAgregada = new BigDecimal(registro.getTotTrabSub());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTrabSub(granTotal.toString());      
        }
        if(registro.getTotTCOPSPPagada() !=null && !registro.getTotTCOPSPPagada().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTCOPSPPagada());
        	cantidadAgregada = new BigDecimal(registro.getTotTCOPSPPagada());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTCOPSPPagada(granTotal.toString());      
        }
        if(registro.getTotTCOPPendientePago() !=null && !registro.getTotTCOPPendientePago().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTCOPPendientePago());
        	cantidadAgregada = new BigDecimal(registro.getTotTCOPPendientePago());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTCOPPendientePago(granTotal.toString());      
        }
        if(registro.getTotRCVConvSP() !=null && !registro.getTotRCVConvSP().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotRCVConvSP());
        	cantidadAgregada = new BigDecimal(registro.getTotRCVConvSP());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotRCVConvSP(granTotal.toString());      
        }
        if(registro.getTotTRCVSP() !=null && !registro.getTotTRCVSP().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTRCVSP());
        	cantidadAgregada = new BigDecimal(registro.getTotTRCVSP());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTRCVSP(granTotal.toString());      
        }
        if(registro.getTotTRCVAct() !=null && !registro.getTotTRCVAct().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTRCVAct());
        	cantidadAgregada = new BigDecimal(registro.getTotTRCVAct());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTRCVAct(granTotal.toString());      
        }
        if(registro.getTotTRCVRec() !=null && !registro.getTotTRCVRec().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTRCVRec());
        	cantidadAgregada = new BigDecimal(registro.getTotTRCVRec());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTRCVRec(granTotal.toString());      
        }
        if(registro.getTotTRCVTotal() !=null && !registro.getTotTRCVTotal().trim().isEmpty()){
        	granTotal = new BigDecimal(registroDeTotales.getTotTRCVTotal());
        	cantidadAgregada = new BigDecimal(registro.getTotTRCVTotal());
        	granTotal=granTotal.add(cantidadAgregada);
        	registroDeTotales.setTotTRCVTotal(granTotal.toString());      
        }
	}
	
	public String obtenerReporte(String sDelegacion, String sSubdelegacion){
		
		if(!myList.isEmpty()){

	        Reportes reporteDescripcion = new Reportes(13, "A");
	        Reportes reporteTotTipo = new Reportes(13, "B");
	        Reportes reporteTotCOPConvSP = new Reportes(13, "C");
	        Reportes reporteTotTCOPSP = new Reportes(13, "D");
	        Reportes reporteTotTCOPAct = new Reportes(13, "E");
	        Reportes reporteTotTCOPRec = new Reportes(13, "F");
	        Reportes reporteTotTCOPTotal = new Reportes(13, "G");
	        Reportes reporteTotTCOPMultas = new Reportes(13, "H");
	        Reportes reporteTotTrabRevisados = new Reportes(13, "I");
	        Reportes reporteTotTrabOmisos = new Reportes(13, "J");
	        Reportes reporteTotTrabSub = new Reportes(13, "K");
	        Reportes reporteTotTCOPSPPagada = new Reportes(13, "L");
	        Reportes reporteTotTCOPPendientePago = new Reportes(13, "M");
	        Reportes reporteTotRCVConvSP = new Reportes(13, "N");
	        Reportes reporteTotTRCVSP = new Reportes(13, "O");
	        Reportes reporteTotTRCVAct = new Reportes(13, "P");
	        Reportes reporteTotTRCVRec = new Reportes(13, "Q");
	        Reportes reporteTotTRCVTotal = new Reportes(13, "R");
			  			  
			Reportes delegacion = new Reportes(6, "C");
			Reportes subdelegacion = new Reportes(6, "J");
			Reportes fechaActual = new Reportes(6, "Q");
			
			delegacion.setElemento(validaNull(sDelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			
			subdelegacion.setElemento(validaNull(sSubdelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			
			fechaActual.setElemento(getFechaActual()
					 ,ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				CPPDResumenVO currentItem = null;
				
				while(iter.hasNext()){
					currentItem = new CPPDResumenVO((Object[])iter.next());
					sumaAGranTotal(currentItem);
					reporteDescripcion.setElemento(currentItem.getDescripcion(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTipo.setElemento(currentItem.getTotTipo(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotCOPConvSP.setElemento(currentItem.getTotCOPConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTCOPSP.setElemento(currentItem.getTotTCOPSP(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTCOPAct.setElemento(currentItem.getTotTCOPAct(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTCOPRec.setElemento(currentItem.getTotTCOPRec(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTCOPTotal.setElemento(currentItem.getTotTCOPTotal(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTCOPMultas.setElemento(currentItem.getTotTCOPMultas(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTrabRevisados.setElemento(currentItem.getTotTrabRevisados(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTrabOmisos.setElemento(currentItem.getTotTrabOmisos(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTrabSub.setElemento(currentItem.getTotTrabSub(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTCOPSPPagada.setElemento(currentItem.getTotTCOPSPPagada(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTCOPPendientePago.setElemento(currentItem.getTotTCOPPendientePago(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotRCVConvSP.setElemento(currentItem.getTotRCVConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTRCVSP.setElemento(currentItem.getTotTRCVSP(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTRCVAct.setElemento(currentItem.getTotTRCVAct(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTRCVRec.setElemento(currentItem.getTotTRCVRec(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
			        reporteTotTRCVTotal.setElemento(currentItem.getTotTRCVTotal(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
				}
				myList.clear();
				
				
				reporteDescripcion.setElemento(registroDeTotales.getDescripcion(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTipo.setElemento(registroDeTotales.getTotTipo(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotCOPConvSP.setElemento(registroDeTotales.getTotCOPConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTCOPSP.setElemento(registroDeTotales.getTotTCOPSP(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTCOPAct.setElemento(registroDeTotales.getTotTCOPAct(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTCOPRec.setElemento(registroDeTotales.getTotTCOPRec(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTCOPTotal.setElemento(registroDeTotales.getTotTCOPTotal(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTCOPMultas.setElemento(registroDeTotales.getTotTCOPMultas(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTrabRevisados.setElemento(registroDeTotales.getTotTrabRevisados(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTrabOmisos.setElemento(registroDeTotales.getTotTrabOmisos(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTrabSub.setElemento(registroDeTotales.getTotTrabSub(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTCOPSPPagada.setElemento(registroDeTotales.getTotTCOPSPPagada(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTCOPPendientePago.setElemento(registroDeTotales.getTotTCOPPendientePago(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotRCVConvSP.setElemento(registroDeTotales.getTotRCVConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTRCVSP.setElemento(registroDeTotales.getTotTRCVSP(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTRCVAct.setElemento(registroDeTotales.getTotTRCVAct(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTRCVRec.setElemento(registroDeTotales.getTotTRCVRec(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
		        reporteTotTRCVTotal.setElemento(registroDeTotales.getTotTRCVTotal(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_RESUMEN);
				

				reporteDescripcion.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTipo.agregarAResourceData(reXLS.getResourceData());
		        reporteTotCOPConvSP.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTCOPSP.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTCOPAct.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTCOPRec.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTCOPTotal.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTCOPMultas.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTrabRevisados.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTrabOmisos.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTrabSub.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTCOPSPPagada.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTCOPPendientePago.agregarAResourceData(reXLS.getResourceData());
		        reporteTotRCVConvSP.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTRCVSP.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTRCVAct.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTRCVRec.agregarAResourceData(reXLS.getResourceData());
		        reporteTotTRCVTotal.agregarAResourceData(reXLS.getResourceData());
		      
				
				delegacion.agregarAResourceData(reXLS.getResourceData());
				subdelegacion.agregarAResourceData(reXLS.getResourceData());
				fechaActual.agregarAResourceData(reXLS.getResourceData());
				
			}
			 
			return generaReporte();
			
		}else{
			
			return ArchivosXLSCaratula.MENSAJE_REPORTE_LISTA_VACIA;
			
		}
		
	}

}
