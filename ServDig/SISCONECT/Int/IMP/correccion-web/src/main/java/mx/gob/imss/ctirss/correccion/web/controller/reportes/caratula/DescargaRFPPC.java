package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CCPVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.RFPPCVO;

@SuppressWarnings("rawtypes")
public class DescargaRFPPC extends AbstractReportesCaratula {
	
	
	public DescargaRFPPC(List myList, String templateXLS, String outfileName,
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
			Reportes fechaNotificacion = new Reportes(13, "D");
			Reportes diasHabilesTranscurridos = new Reportes(13, "E");
			
			Reportes delegacion = new Reportes(6, "B");
			Reportes subdelegacion = new Reportes(7, "B");
			Reportes fechaActual = new Reportes(7, "D");
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				RFPPCVO currentItem = null;
				
				
				delegacion.setElemento(validaNull(sDelegacion)
						 ,ArchivosXLSCaratula.SHEET_CONTROL_RFPPC);
				
				subdelegacion.setElemento(validaNull(sSubdelegacion)
						 ,ArchivosXLSCaratula.SHEET_CONTROL_RFPPC);
				
				fechaActual.setElemento(getFechaActual()
						 ,ArchivosXLSCaratula.SHEET_CONTROL_RFPPC);
				
				while(iter.hasNext()){
					currentItem = new RFPPCVO((Object[])iter.next());
					
					reporteFolio.setElemento(currentItem.getFolioCorreccion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_RFPPC);
					
					reporteRP.setElemento(currentItem.getRegistroPatronal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_RFPPC);
					
					nombre.setElemento(currentItem.getNombre()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_RFPPC);
					
					fechaNotificacion.setElemento(currentItem.getFechaNotificaicon()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_RFPPC);
					
					diasHabilesTranscurridos.setElemento(currentItem.getDiasHabilesTranscurridos()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_RFPPC);
				}
				myList.clear();
				
				reporteFolio.agregarAResourceData(reXLS.getResourceData());
				reporteRP.agregarAResourceData(reXLS.getResourceData());
				nombre.agregarAResourceData(reXLS.getResourceData());
				fechaNotificacion.agregarAResourceData(reXLS.getResourceData());
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
