package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CCPVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CFCCEVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CFCEVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CPPDResumenVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CTCPCDResumenVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CTCPCDVO;

/**
 * 
 * @author Saúl Rosales Piedragil
 * @date 31/07/2012
 * @version 1.0.0
 * Objeto Visual para generación de reporte en excel de Resumen de corrección
 */
@SuppressWarnings("rawtypes")
public class DescargaCorreccionResumen extends AbstractReportesCaratula {
	CTCPCDResumenVO registroDeTotales;
	
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
	public DescargaCorreccionResumen(List myList, String templateXLS, String outfileName,
									HttpServletRequest request, HttpServletResponse response){
		setMyList(myList);
		setTemplateXLS(templateXLS);
		setOutfileName(outfileName);
		setRequest(request);
		setResponse(response);
		setReXLS();
		registroDeTotales = new CTCPCDResumenVO("TOTALES");
		
	}
	

	public String obtenerReporte(){
		return obtenerReporte(null,null);
	}
	
	/**
	 * @author Saúl Rosales Piedragil
	 * @since 31/07/2012
	 * @param numero1 cadena representando el numero 1 al que se le sumará el numero2
	 * @param numero2 cadena representando el numero 2 a sumar
	 * @return String cadena que representa el numero resultado
	 * Constructor que inicializa las propiedades
	 */
	public String sumaNumerosCadena(String numero1, String numero2){
		BigDecimal bdecCantidad1 = null;
		BigDecimal bdecCantidad2 = null;
        if(numero2 !=null && !numero2.trim().isEmpty()){
        	bdecCantidad1 = new BigDecimal(numero1);
        	bdecCantidad2 = new BigDecimal(numero2);
        	bdecCantidad1=bdecCantidad1.add(bdecCantidad2);
        	return bdecCantidad1.toString();
        }else{
        	return numero1;
        }
	}
	
	/**
	 * @author Saúl Rosales Piedragil
	 * @since 31/07/2012
	 * @param pojo correspondiente a un registro en BD el cual se sumará en cada uno de sus campos al registro registroDeTotales
	 */
	public void sumaAGranTotal(CTCPCDResumenVO registro){
        registroDeTotales.setTotTipo(sumaNumerosCadena(registroDeTotales.getTotTipo(), registro.getTotTipo()));
        registroDeTotales.setTotQtyRP(sumaNumerosCadena(registroDeTotales.getTotQtyRP(), registro.getTotQtyRP()));
        registroDeTotales.setTotACOPConvSP(sumaNumerosCadena(registroDeTotales.getTotACOPConvSP(), registro.getTotACOPConvSP()));
        registroDeTotales.setTotTACOPSP(sumaNumerosCadena(registroDeTotales.getTotTACOPSP(), registro.getTotTACOPSP()));
        registroDeTotales.setTotTACOPAct(sumaNumerosCadena(registroDeTotales.getTotTACOPAct(), registro.getTotTACOPAct()));
        registroDeTotales.setTotTACOPRec(sumaNumerosCadena(registroDeTotales.getTotTACOPRec(), registro.getTotTACOPRec()));
        registroDeTotales.setTotTACOPTotal(sumaNumerosCadena(registroDeTotales.getTotTACOPTotal(), registro.getTotTACOPTotal()));
        registroDeTotales.setTotTACOPMultas(sumaNumerosCadena(registroDeTotales.getTotTACOPMultas(), registro.getTotTACOPMultas()));
        registroDeTotales.setTotATrabRevisados(sumaNumerosCadena(registroDeTotales.getTotATrabRevisados(), registro.getTotATrabRevisados()));
        registroDeTotales.setTotATrabOmisos(sumaNumerosCadena(registroDeTotales.getTotATrabOmisos(), registro.getTotATrabOmisos()));
        registroDeTotales.setTotATrabSub(sumaNumerosCadena(registroDeTotales.getTotATrabSub(), registro.getTotATrabSub()));
        registroDeTotales.setTotTACOPSPPagada(sumaNumerosCadena(registroDeTotales.getTotTACOPSPPagada(), registro.getTotTACOPSPPagada()));
        registroDeTotales.setTotTACOPPendientePago(sumaNumerosCadena(registroDeTotales.getTotTACOPPendientePago(), registro.getTotTACOPPendientePago()));
        registroDeTotales.setTotARCVConvSP(sumaNumerosCadena(registroDeTotales.getTotARCVConvSP(), registro.getTotARCVConvSP()));
        registroDeTotales.setTotTARCVSP(sumaNumerosCadena(registroDeTotales.getTotTARCVSP(), registro.getTotTARCVSP()));
        registroDeTotales.setTotTARCVAct(sumaNumerosCadena(registroDeTotales.getTotTARCVAct(), registro.getTotTARCVAct()));
        registroDeTotales.setTotTARCVRec(sumaNumerosCadena(registroDeTotales.getTotTARCVRec(), registro.getTotTARCVRec()));
        registroDeTotales.setTotTARCVTotal(sumaNumerosCadena(registroDeTotales.getTotTARCVTotal(), registro.getTotTARCVTotal()));
        registroDeTotales.setTotTARCVMultas(sumaNumerosCadena(registroDeTotales.getTotTARCVMultas(), registro.getTotTARCVMultas()));
        registroDeTotales.setTotRCOPConvSP(sumaNumerosCadena(registroDeTotales.getTotRCOPConvSP(), registro.getTotRCOPConvSP()));
        registroDeTotales.setTotTRCOPSP(sumaNumerosCadena(registroDeTotales.getTotTRCOPSP(), registro.getTotTRCOPSP()));
        registroDeTotales.setTotTRCOPAct(sumaNumerosCadena(registroDeTotales.getTotTRCOPAct(), registro.getTotTRCOPAct()));
        registroDeTotales.setTotTRCOPRec(sumaNumerosCadena(registroDeTotales.getTotTRCOPRec(), registro.getTotTRCOPRec()));
        registroDeTotales.setTotTRCOPTotal(sumaNumerosCadena(registroDeTotales.getTotTRCOPTotal(), registro.getTotTRCOPTotal()));
        registroDeTotales.setTotTRCOPMultas(sumaNumerosCadena(registroDeTotales.getTotTRCOPMultas(), registro.getTotTRCOPMultas()));
        registroDeTotales.setTotRTrabRev(sumaNumerosCadena(registroDeTotales.getTotRTrabRev(), registro.getTotRTrabRev()));
        registroDeTotales.setTotRTrabOmisos(sumaNumerosCadena(registroDeTotales.getTotRTrabOmisos(), registro.getTotRTrabOmisos()));
        registroDeTotales.setTotRTrabSub(sumaNumerosCadena(registroDeTotales.getTotRTrabSub(), registro.getTotRTrabSub()));
        registroDeTotales.setTotTRCOPSPPagada(sumaNumerosCadena(registroDeTotales.getTotTRCOPSPPagada(), registro.getTotTRCOPSPPagada()));
        registroDeTotales.setTotTRCOPPendientePago(sumaNumerosCadena(registroDeTotales.getTotTRCOPPendientePago(), registro.getTotTRCOPPendientePago()));
        registroDeTotales.setTotRRCVConvSP(sumaNumerosCadena(registroDeTotales.getTotRRCVConvSP(), registro.getTotRRCVConvSP()));
        registroDeTotales.setTotTRRCVSP(sumaNumerosCadena(registroDeTotales.getTotTRRCVSP(), registro.getTotTRRCVSP()));
        registroDeTotales.setTotTRRCVAct(sumaNumerosCadena(registroDeTotales.getTotTRRCVAct(), registro.getTotTRRCVAct()));
        registroDeTotales.setTotTRRCVRec(sumaNumerosCadena(registroDeTotales.getTotTRRCVRec(), registro.getTotTRRCVRec()));
        registroDeTotales.setTotTRRCVTotal(sumaNumerosCadena(registroDeTotales.getTotTRRCVTotal(), registro.getTotTRRCVTotal()));
        registroDeTotales.setTotTRRCVMultas(sumaNumerosCadena(registroDeTotales.getTotTRRCVMultas(), registro.getTotTRRCVMultas()));
	}
	
	public String obtenerReporte(String sDelegacion, String sSubdelegacion){
		
		if(!myList.isEmpty()){
			
	          Reportes reporteDescripcion =  new Reportes(14, "A");  
	          Reportes reporteTotTipo =  new Reportes(14, "B");  
	          Reportes reporteTotQtyRP =  new Reportes(14, "C");  
	          Reportes reporteTotACOPConvSP =  new Reportes(14, "D");  
	          Reportes reporteTotTACOPSP =  new Reportes(14, "E");  
	          Reportes reporteTotTACOPAct =  new Reportes(14, "F");  
	          Reportes reporteTotTACOPRec =  new Reportes(14, "G");  
	          Reportes reporteTotTACOPTotal =  new Reportes(14, "H");  
	          Reportes reporteTotTACOPMultas =  new Reportes(14, "I");  
	          Reportes reporteTotATrabRevisados =  new Reportes(14, "J");  
	          Reportes reporteTotATrabOmisos =  new Reportes(14, "K");  
	          Reportes reporteTotATrabSub =  new Reportes(14, "L");  
	          Reportes reporteTotTACOPSPPagada =  new Reportes(14, "M"); 
	          Reportes reporteTotTACOPPendientePago =  new Reportes(14, "N");  
	          Reportes reporteTotARCVConvSP =  new Reportes(14, "O");  
	          Reportes reporteTotTARCVSP =  new Reportes(14, "P");  
	          Reportes reporteTotTARCVAct =  new Reportes(14, "Q");  
	          Reportes reporteTotTARCVRec =  new Reportes(14, "R");  
	          Reportes reporteTotTARCVTotal =  new Reportes(14, "S");  
	          Reportes reporteTotTARCVMultas =  new Reportes(14, "T");  
	          Reportes reporteTotRCOPConvSP =  new Reportes(14, "U");  
	          Reportes reporteTotTRCOPSP =  new Reportes(14, "V");  
	          Reportes reporteTotTRCOPAct =  new Reportes(14, "W");  
	          Reportes reporteTotTRCOPRec =  new Reportes(14, "X");  
	          Reportes reporteTotTRCOPTotal =  new Reportes(14, "Y");  
	          Reportes reporteTotTRCOPMultas =  new Reportes(14, "Z");  
	          Reportes reporteTotRTrabRev =  new Reportes(14, "AA");  
	          Reportes reporteTotRTrabOmisos =  new Reportes(14, "AB");  
	          Reportes reporteTotRTrabSub =  new Reportes(14, "AC");  
	          Reportes reporteTotTRCOPSPPagada =  new Reportes(14, "AD"); 
	          Reportes reporteTotTRCOPPendientePago =  new Reportes(14, "AE");  
	          Reportes reporteTotRRCVConvSP =  new Reportes(14, "AF");  
	          Reportes reporteTotTRRCVSP =  new Reportes(14, "AG");  
	          Reportes reporteTotTRRCVAct =  new Reportes(14, "AH");  
	          Reportes reporteTotTRRCVRec =  new Reportes(14, "AI");  
	          Reportes reporteTotTRRCVTotal =  new Reportes(14, "AJ");  
	          Reportes reporteTotTRRCVMultas =  new Reportes(14, "AK");  

			  Reportes delegacion = new Reportes(6, "L");
			  Reportes subdelegacion = new Reportes(6, "R");
			  Reportes fechaActual = new Reportes(6, "X");
				
				delegacion.setElemento(validaNull(sDelegacion)
						 ,ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);
				
				subdelegacion.setElemento(validaNull(sSubdelegacion)
						 ,ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);
				
				fechaActual.setElemento(getFechaActual()
						 ,ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);
			
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				CTCPCDResumenVO currentItem = null;
				
				while(iter.hasNext()){
					currentItem = new CTCPCDResumenVO((Object[])iter.next());
					sumaAGranTotal(currentItem);
			           reporteDescripcion.setElemento(currentItem.getDescripcion(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTipo.setElemento(currentItem.getTotTipo(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotQtyRP.setElemento(currentItem.getTotQtyRP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotACOPConvSP.setElemento(currentItem.getTotACOPConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTACOPSP.setElemento(currentItem.getTotTACOPSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTACOPAct.setElemento(currentItem.getTotTACOPAct(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTACOPRec.setElemento(currentItem.getTotTACOPRec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTACOPTotal.setElemento(currentItem.getTotTACOPTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTACOPMultas.setElemento(currentItem.getTotTACOPMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotATrabRevisados.setElemento(currentItem.getTotATrabRevisados(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotATrabOmisos.setElemento(currentItem.getTotATrabOmisos(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotATrabSub.setElemento(currentItem.getTotATrabSub(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTACOPSPPagada.setElemento(currentItem.getTotTACOPSPPagada(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN); 
			           reporteTotTACOPPendientePago.setElemento(currentItem.getTotTACOPPendientePago(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotARCVConvSP.setElemento(currentItem.getTotARCVConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTARCVSP.setElemento(currentItem.getTotTARCVSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTARCVAct.setElemento(currentItem.getTotTARCVAct(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTARCVRec.setElemento(currentItem.getTotTARCVRec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTARCVTotal.setElemento(currentItem.getTotTARCVTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTARCVMultas.setElemento(currentItem.getTotTARCVMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotRCOPConvSP.setElemento(currentItem.getTotRCOPConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTRCOPSP.setElemento(currentItem.getTotTRCOPSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTRCOPAct.setElemento(currentItem.getTotTRCOPAct(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTRCOPRec.setElemento(currentItem.getTotTRCOPRec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTRCOPTotal.setElemento(currentItem.getTotTRCOPTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTRCOPMultas.setElemento(currentItem.getTotTRCOPMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotRTrabRev.setElemento(currentItem.getTotRTrabRev(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotRTrabOmisos.setElemento(currentItem.getTotRTrabOmisos(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotRTrabSub.setElemento(currentItem.getTotRTrabSub(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTRCOPSPPagada.setElemento(currentItem.getTotTRCOPSPPagada(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN); 
			           reporteTotTRCOPPendientePago.setElemento(currentItem.getTotTRCOPPendientePago(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotRRCVConvSP.setElemento(currentItem.getTotRRCVConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTRRCVSP.setElemento(currentItem.getTotTRRCVSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTRRCVAct.setElemento(currentItem.getTotTRRCVAct(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTRRCVRec.setElemento(currentItem.getTotTRRCVRec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTRRCVTotal.setElemento(currentItem.getTotTRRCVTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
			           reporteTotTRRCVMultas.setElemento(currentItem.getTotTRRCVMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);

				}
				myList.clear();
				
		           reporteDescripcion.setElemento(registroDeTotales.getDescripcion(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTipo.setElemento(registroDeTotales.getTotTipo(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotQtyRP.setElemento(registroDeTotales.getTotQtyRP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotACOPConvSP.setElemento(registroDeTotales.getTotACOPConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTACOPSP.setElemento(registroDeTotales.getTotTACOPSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTACOPAct.setElemento(registroDeTotales.getTotTACOPAct(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTACOPRec.setElemento(registroDeTotales.getTotTACOPRec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTACOPTotal.setElemento(registroDeTotales.getTotTACOPTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTACOPMultas.setElemento(registroDeTotales.getTotTACOPMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotATrabRevisados.setElemento(registroDeTotales.getTotATrabRevisados(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotATrabOmisos.setElemento(registroDeTotales.getTotATrabOmisos(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotATrabSub.setElemento(registroDeTotales.getTotATrabSub(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTACOPSPPagada.setElemento(registroDeTotales.getTotTACOPSPPagada(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN); 
		           reporteTotTACOPPendientePago.setElemento(registroDeTotales.getTotTACOPPendientePago(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotARCVConvSP.setElemento(registroDeTotales.getTotARCVConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTARCVSP.setElemento(registroDeTotales.getTotTARCVSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTARCVAct.setElemento(registroDeTotales.getTotTARCVAct(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTARCVRec.setElemento(registroDeTotales.getTotTARCVRec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTARCVTotal.setElemento(registroDeTotales.getTotTARCVTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTARCVMultas.setElemento(registroDeTotales.getTotTARCVMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotRCOPConvSP.setElemento(registroDeTotales.getTotRCOPConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTRCOPSP.setElemento(registroDeTotales.getTotTRCOPSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTRCOPAct.setElemento(registroDeTotales.getTotTRCOPAct(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTRCOPRec.setElemento(registroDeTotales.getTotTRCOPRec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTRCOPTotal.setElemento(registroDeTotales.getTotTRCOPTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTRCOPMultas.setElemento(registroDeTotales.getTotTRCOPMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotRTrabRev.setElemento(registroDeTotales.getTotRTrabRev(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotRTrabOmisos.setElemento(registroDeTotales.getTotRTrabOmisos(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotRTrabSub.setElemento(registroDeTotales.getTotRTrabSub(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTRCOPSPPagada.setElemento(registroDeTotales.getTotTRCOPSPPagada(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN); 
		           reporteTotTRCOPPendientePago.setElemento(registroDeTotales.getTotTRCOPPendientePago(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotRRCVConvSP.setElemento(registroDeTotales.getTotRRCVConvSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTRRCVSP.setElemento(registroDeTotales.getTotTRRCVSP(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTRRCVAct.setElemento(registroDeTotales.getTotTRRCVAct(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTRRCVRec.setElemento(registroDeTotales.getTotTRRCVRec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTRRCVTotal.setElemento(registroDeTotales.getTotTRRCVTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);  
		           reporteTotTRRCVMultas.setElemento(registroDeTotales.getTotTRRCVMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_RESUMEN);
		           
		           reporteDescripcion.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTipo.agregarAResourceData(reXLS.getResourceData());
		           reporteTotQtyRP.agregarAResourceData(reXLS.getResourceData());
		           reporteTotACOPConvSP.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTACOPSP.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTACOPAct.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTACOPRec.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTACOPTotal.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTACOPMultas.agregarAResourceData(reXLS.getResourceData());
		           reporteTotATrabRevisados.agregarAResourceData(reXLS.getResourceData());
		           reporteTotATrabOmisos.agregarAResourceData(reXLS.getResourceData());
		           reporteTotATrabSub.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTACOPSPPagada.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTACOPPendientePago.agregarAResourceData(reXLS.getResourceData());
		           reporteTotARCVConvSP.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTARCVSP.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTARCVAct.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTARCVRec.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTARCVTotal.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTARCVMultas.agregarAResourceData(reXLS.getResourceData());
		           reporteTotRCOPConvSP.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRCOPSP.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRCOPAct.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRCOPRec.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRCOPTotal.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRCOPMultas.agregarAResourceData(reXLS.getResourceData());
		           reporteTotRTrabRev.agregarAResourceData(reXLS.getResourceData());
		           reporteTotRTrabOmisos.agregarAResourceData(reXLS.getResourceData());
		           reporteTotRTrabSub.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRCOPSPPagada.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRCOPPendientePago.agregarAResourceData(reXLS.getResourceData());
		           reporteTotRRCVConvSP.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRRCVSP.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRRCVAct.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRRCVRec.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRRCVTotal.agregarAResourceData(reXLS.getResourceData());
		           reporteTotTRRCVMultas.agregarAResourceData(reXLS.getResourceData());
		           
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
