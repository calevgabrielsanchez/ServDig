package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CCPVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CSCVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.RFCPAVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.RFPPCVO;

@SuppressWarnings("rawtypes")
public class DescargaCSC extends AbstractReportesCaratula {
	
	
	public DescargaCSC(List myList, String templateXLS, String outfileName,
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
			
			Reportes reporteFolio = new Reportes(13, "A");
			Reportes reporteRP = new Reportes(13, "B");
			Reportes fechaSolicitudPresentada = new Reportes(13, "C");
			Reportes fechaSolicitudAceptada = new Reportes(13, "D");
			Reportes fechaSolicitudRechazada = new Reportes(13, "E");
			Reportes motivoRechazo = new Reportes(13, "F");
			
			Reportes delegacion = new Reportes(6, "B");
			Reportes subdelegacion = new Reportes(7, "B");
			Reportes fechaActual = new Reportes(7, "F");
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				CSCVO currentItem = null;
				
				delegacion.setElemento(validaNull(sDelegacion)
						 ,ArchivosXLSCaratula.SHEET_CONTROL_CSC);
				
				subdelegacion.setElemento(validaNull(sSubdelegacion)
						 ,ArchivosXLSCaratula.SHEET_CONTROL_CSC);
				
				fechaActual.setElemento(getFechaActual()
						 ,ArchivosXLSCaratula.SHEET_CONTROL_CSC);
				
				while(iter.hasNext()){
					currentItem = new CSCVO((Object[])iter.next());
					
					reporteFolio.setElemento(currentItem.getFolioCorreccion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CSC);
					
					reporteRP.setElemento(currentItem.getRegistroPatronal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CSC);
					
					fechaSolicitudPresentada.setElemento(currentItem.getFechaSolicitudPresentada()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CSC);
					
					fechaSolicitudAceptada.setElemento(currentItem.getFechaSolicitudAceptada()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CSC);
					
					fechaSolicitudRechazada.setElemento(currentItem.getFechaSolicitudRechazada()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CSC);
					
					motivoRechazo.setElemento(currentItem.getMotivoRechazo()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CSC);
				}
				myList.clear();
				
				reporteFolio.agregarAResourceData(reXLS.getResourceData());
				reporteRP.agregarAResourceData(reXLS.getResourceData());
				fechaSolicitudPresentada.agregarAResourceData(reXLS.getResourceData());
				fechaSolicitudAceptada.agregarAResourceData(reXLS.getResourceData());
				fechaSolicitudRechazada.agregarAResourceData(reXLS.getResourceData());
				motivoRechazo.agregarAResourceData(reXLS.getResourceData());
				
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

