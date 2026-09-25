package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CCPVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CFPCVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CFPOVO;

@SuppressWarnings("rawtypes")
public class DescargaCFPC extends AbstractReportesCaratula {
	
	
	public DescargaCFPC(List myList, String templateXLS, String outfileName,
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
			Reportes reporteFechaOfiPromocion = new Reportes(13, "C");
			Reportes reporteFechaOfiNotificacion = new Reportes(13, "D");
			Reportes reporteFechaOfiCancelacion= new Reportes(13, "E");
			Reportes reporteMotivoCancelacion= new Reportes(13, "F");
			Reportes reporteObservaciones= new Reportes(13, "G");
			
			Reportes delegacion = new Reportes(6, "B");
			Reportes subdelegacion = new Reportes(7, "B");
			Reportes fechaActual = new Reportes(7, "G");
			
			delegacion.setElemento(validaNull(sDelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CFPC);
			
			subdelegacion.setElemento(validaNull(sSubdelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CFPC);
			
			fechaActual.setElemento(getFechaActual()
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CFPC);
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				CFPCVO currentItem = null;
				
				while(iter.hasNext()){
					currentItem = new CFPCVO((Object[])iter.next());
					
					reporteFolio.setElemento(currentItem.getFolioCorreccion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFPC);
					
					reporteRP.setElemento(currentItem.getRegistroPatronal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFPC);
					
					reporteFechaOfiPromocion.setElemento(currentItem.getFechaOficioPromocion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFPC);
					
					reporteFechaOfiNotificacion.setElemento(currentItem.getFechaNotificacionOficio()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFPC);
					
					reporteFechaOfiCancelacion.setElemento(currentItem.getFechaCancelada()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFPC);
					
					reporteMotivoCancelacion.setElemento(currentItem.getMotivoCancelacion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFPC);
					
					reporteObservaciones.setElemento(currentItem.getObservaciones()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFPC);
				}
				myList.clear();
				
				reporteFolio.agregarAResourceData(reXLS.getResourceData());
				reporteRP.agregarAResourceData(reXLS.getResourceData());
				reporteFechaOfiPromocion.agregarAResourceData(reXLS.getResourceData());
				reporteFechaOfiNotificacion.agregarAResourceData(reXLS.getResourceData());
				reporteFechaOfiCancelacion.agregarAResourceData(reXLS.getResourceData());
				reporteMotivoCancelacion.agregarAResourceData(reXLS.getResourceData());
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
