package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CCPVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.PDVO;

@SuppressWarnings("rawtypes")
public class DescargaPD extends AbstractReportesCaratula {
	
	
	public DescargaPD(List myList, String templateXLS, String outfileName,
									HttpServletRequest request, HttpServletResponse response){
		
		setMyList(myList);
		setTemplateXLS(templateXLS);
		setOutfileName(outfileName);
		setRequest(request);
		setResponse(response);
		setReXLS();
		
	}
	
	public String obtenerReporte(){
		return obtenerReporte(null,null);
	}
	public String obtenerReporte(String sDelegacion, String sSubdelegacion){
		
		if(!myList.isEmpty()){
			
			
			Reportes idPago= new Reportes(13, "A");
			Reportes folioCorreccion= new Reportes(13, "B");
			Reportes proceso= new Reportes(13, "C");
			Reportes registroPatronal= new Reportes(13, "D");
			Reportes folioSUA= new Reportes(13, "E");
			Reportes ordenIngreso= new Reportes(13, "F");
			Reportes nuCredito= new Reportes(13, "G");
			Reportes fechaPago= new Reportes(13, "H");
			Reportes periodo= new Reportes(13, "I");
			Reportes SP= new Reportes(13, "J");
			Reportes act= new Reportes(13, "K");
			Reportes rec= new Reportes(13, "L");
			Reportes total= new Reportes(13, "M");
			Reportes multas= new Reportes(13, "N");
			Reportes RCVperiodo= new Reportes(13, "O");
			Reportes RCVSP= new Reportes(13, "P");
			Reportes RCVAct= new Reportes(13, "Q");
			Reportes RCVRec= new Reportes(13, "R");
			Reportes RCVTotal= new Reportes(13, "S");
			
			
			Reportes delegacion = new Reportes(7, "C");
			Reportes subdelegacion = new Reportes(7, "I");
			Reportes fechaActual = new Reportes(7, "Q");
			
			delegacion.setElemento(validaNull(sDelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
			
			subdelegacion.setElemento(validaNull(sSubdelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
			
			fechaActual.setElemento(getFechaActual()
					 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				PDVO currentItem = null;
				
				while(iter.hasNext()){
					currentItem = new PDVO((Object[])iter.next());
					
				
				
			
					idPago.setElemento(currentItem.getIdPago()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					folioCorreccion.setElemento(currentItem.getFolioCorreccion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					proceso.setElemento(currentItem.getProceso()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					registroPatronal.setElemento(currentItem.getRegistroPatronal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					folioSUA.setElemento(currentItem.getFolioSUA()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					ordenIngreso.setElemento(currentItem.getOrdenIngreso()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					nuCredito.setElemento(currentItem.getNuCredito()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					fechaPago.setElemento(currentItem.getFechaPago()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					periodo.setElemento(currentItem.getPeriodo()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					SP.setElemento(currentItem.getSP()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					act.setElemento(currentItem.getAct()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					rec.setElemento(currentItem.getRec()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
										
					total.setElemento(currentItem.getTotal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					multas.setElemento(currentItem.getMultas()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					RCVperiodo.setElemento(currentItem.getRCVperiodo()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					RCVSP.setElemento(currentItem.getRCVSP()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					
					RCVAct.setElemento(currentItem.getRCVAct()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					RCVRec.setElemento(currentItem.getRCVRec()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					RCVTotal.setElemento(currentItem.getRCVTotal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_PD);
					
					

				}
				myList.clear();
				
				idPago.agregarAResourceData(reXLS.getResourceData());
				folioCorreccion.agregarAResourceData(reXLS.getResourceData());
				proceso.agregarAResourceData(reXLS.getResourceData());
				registroPatronal.agregarAResourceData(reXLS.getResourceData());
				
				folioSUA.agregarAResourceData(reXLS.getResourceData());
				ordenIngreso.agregarAResourceData(reXLS.getResourceData());
				nuCredito.agregarAResourceData(reXLS.getResourceData());
				fechaPago.agregarAResourceData(reXLS.getResourceData());
				periodo.agregarAResourceData(reXLS.getResourceData());
				SP.agregarAResourceData(reXLS.getResourceData());
				act.agregarAResourceData(reXLS.getResourceData());
				rec.agregarAResourceData(reXLS.getResourceData());
				total.agregarAResourceData(reXLS.getResourceData());
				multas.agregarAResourceData(reXLS.getResourceData());
				RCVperiodo.agregarAResourceData(reXLS.getResourceData());
				RCVSP.agregarAResourceData(reXLS.getResourceData());
				RCVAct.agregarAResourceData(reXLS.getResourceData());
				RCVRec.agregarAResourceData(reXLS.getResourceData());
				RCVTotal.agregarAResourceData(reXLS.getResourceData());
				
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
