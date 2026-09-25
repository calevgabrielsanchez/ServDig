package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CCPVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CFCEVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CFCIVO;

@SuppressWarnings("rawtypes")
public class DescargaCFCI extends AbstractReportesCaratula {
	
	
	public DescargaCFCI(List myList, String templateXLS, String outfileName,
									HttpServletRequest request, HttpServletResponse response){
		
		setMyList(myList);
		setTemplateXLS(templateXLS);
		setOutfileName(outfileName);
		setRequest(request);
		setResponse(response);
		setReXLS();
		
	}
	
	public String obtenerReporte(String sDelegacion, String sSubdelegacion){
		
		if(!myList.isEmpty()){
			
			Reportes reporteFolio = new Reportes(13, "A");
			Reportes reporteRP = new Reportes(13, "B");
			Reportes reporteNombreAuditor = new Reportes(13, "C");
			Reportes reporteOficioInvitacion = new Reportes(13, "D");
			Reportes reporteFolioPromocion = new Reportes(13, "E");
			Reportes reporteObservaciones = new Reportes(13, "F");
		
			
			Reportes delegacion = new Reportes(6, "B");
			Reportes subdelegacion = new Reportes(7, "B");
			Reportes fechaActual = new Reportes(7, "F");
			
			delegacion.setElemento(validaNull(sDelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CFCI);
			
			subdelegacion.setElemento(validaNull(sSubdelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CFCI);
			
			fechaActual.setElemento(getFechaActual()
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CFCI);
					 
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				CFCIVO currentItem = null;
				
				while(iter.hasNext()){
					currentItem = new CFCIVO((Object[])iter.next());
					
					reporteFolio.setElemento(currentItem.getFolioCorreccion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFCI);
					
					reporteRP.setElemento(currentItem.getRegistroPatronal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFCI);
					
					reporteNombreAuditor.setElemento(currentItem.getNombreAuditor()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFCI);
					
					reporteOficioInvitacion.setElemento(currentItem.getOficioInvitacion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFCI);
					
					reporteFolioPromocion.setElemento(currentItem.getFolioPromocion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFCI);
					
					reporteObservaciones.setElemento(currentItem.getObservaciones()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFCI);
				}
				myList.clear();
				
				reporteFolio.agregarAResourceData(reXLS.getResourceData());
				reporteRP.agregarAResourceData(reXLS.getResourceData());
				reporteNombreAuditor.agregarAResourceData(reXLS.getResourceData());
				reporteOficioInvitacion.agregarAResourceData(reXLS.getResourceData());
				reporteFolioPromocion.agregarAResourceData(reXLS.getResourceData());
				reporteObservaciones.agregarAResourceData(reXLS.getResourceData());
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
