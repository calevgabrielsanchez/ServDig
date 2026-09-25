package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CCPVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CPVO;

@SuppressWarnings("rawtypes")
public class DescargaCP extends AbstractReportesCaratula {
	
	
	public DescargaCP(List myList, String templateXLS, String outfileName,
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
			
			Reportes reporteFolio = new Reportes(12, "A");
			Reportes reporteRP = new Reportes(12, "B");
			Reportes reporteFechaVencimiento = new Reportes(12, "C");
		
			Reportes delegacion = new Reportes(6, "B");
			Reportes subdelegacion = new Reportes(7, "B");
			Reportes fechaActual = new Reportes(7, "C");
			
			delegacion.setElemento(validaNull(sDelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CP);
			
			subdelegacion.setElemento(validaNull(sSubdelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CP);
			
			fechaActual.setElemento(getFechaActual()
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CP);
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				CPVO currentItem = null;
				
				while(iter.hasNext()){
					currentItem = new CPVO((Object[])iter.next());
					
					reporteFolio.setElemento(currentItem.getFolioCorreccion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CP);
					
					reporteRP.setElemento(currentItem.getRegistroPatronal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CP);
					
					reporteFechaVencimiento.setElemento(currentItem.getFechaVencimiento()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CP);
					
					
				}
				myList.clear();
				
				reporteFolio.agregarAResourceData(reXLS.getResourceData());
				reporteRP.agregarAResourceData(reXLS.getResourceData());
				reporteFechaVencimiento.agregarAResourceData(reXLS.getResourceData());

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
