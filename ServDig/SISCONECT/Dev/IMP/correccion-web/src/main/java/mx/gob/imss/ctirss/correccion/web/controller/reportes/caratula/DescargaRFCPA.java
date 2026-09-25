package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CCPVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.RFCPAVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.RFPPCVO;

@SuppressWarnings("rawtypes")
public class DescargaRFCPA extends AbstractReportesCaratula {
	
	
	public DescargaRFCPA(List myList, String templateXLS, String outfileName,
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
	public String obtenerReporte(String sDelegacion,String sSubdelegacion){
		
		if(!myList.isEmpty()){
			
			Reportes reporteFolio = new Reportes(13, "A");
			Reportes reporteRP = new Reportes(13, "B");
			Reportes nombre = new Reportes(13, "C");
			Reportes fechaSolicitudAutoirzada = new Reportes(13, "D");
			Reportes diasHabilesTranscurridos = new Reportes(13, "E");
			
			Reportes delegacion = new Reportes(6, "C");
			Reportes subdelegacion = new Reportes(7, "C");
			Reportes fechaActual = new Reportes(7, "E");
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				RFCPAVO currentItem = null;
				
				delegacion.setElemento(validaNull(sDelegacion)
						 ,ArchivosXLSCaratula.SHEET_CONTROL_RFCPA);
				
				subdelegacion.setElemento(validaNull(sSubdelegacion)
						 ,ArchivosXLSCaratula.SHEET_CONTROL_RFCPA);
				
				fechaActual.setElemento(getFechaActual()
						 ,ArchivosXLSCaratula.SHEET_CONTROL_RFCPA);
				
				while(iter.hasNext()){
					currentItem = new RFCPAVO((Object[])iter.next());
					
					reporteFolio.setElemento(currentItem.getFolioCorreccion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_RFCPA);
					
					reporteRP.setElemento(currentItem.getRegistroPatronal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_RFCPA);
					
					nombre.setElemento(currentItem.getNombre()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_RFCPA);
					
					fechaSolicitudAutoirzada.setElemento(currentItem.getFechaSolicitudAutorizada()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_RFCPA);
					
					diasHabilesTranscurridos.setElemento(currentItem.getDiasHabilesTranscurridos()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_RFCPA);
				}
				myList.clear();
				
				reporteFolio.agregarAResourceData(reXLS.getResourceData());
				reporteRP.agregarAResourceData(reXLS.getResourceData());
				nombre.agregarAResourceData(reXLS.getResourceData());
				fechaSolicitudAutoirzada.agregarAResourceData(reXLS.getResourceData());
				diasHabilesTranscurridos.agregarAResourceData(reXLS.getResourceData());
			
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
