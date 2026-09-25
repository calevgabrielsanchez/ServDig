package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CCPVO;

@SuppressWarnings("rawtypes")
public class DescargaCCP extends AbstractReportesCaratula {
	
	
	public DescargaCCP(List myList, String templateXLS, String outfileName,
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
	
	public String obtenerReporte(String sDeleg,String sSudelega){
		
		if(!myList.isEmpty()){
			
			Reportes reporteFolio = new Reportes(13, "A");
			Reportes reporteRP = new Reportes(13, "B");
			Reportes reporteFecha = new Reportes(13, "C");
			Reportes reporteTipoCorr = new Reportes(13, "D");
			
			Reportes delegacion = new Reportes(6, "B");
			Reportes subdelegacion = new Reportes(7, "B");
			Reportes fechaActual = new Reportes(7, "D");
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				CCPVO currentItem = null;
				
				delegacion.setElemento(validaNull(sDeleg)
						 ,ArchivosXLSCaratula.SHEET_CONTROL_CCP);
				
				subdelegacion.setElemento(validaNull(sSudelega)
						 ,ArchivosXLSCaratula.SHEET_CONTROL_CCP);
				
				fechaActual.setElemento(getFechaActual()
						 ,ArchivosXLSCaratula.SHEET_CONTROL_CCP);
				
				while(iter.hasNext()){
					currentItem = new CCPVO((Object[])iter.next());
					
					reporteFolio.setElemento(currentItem.getFolioCorreccion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CCP);
					
					reporteRP.setElemento(currentItem.getRegistroPatronal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CCP);
					
					reporteFecha.setElemento(currentItem.getFecha()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CCP);
					
					reporteTipoCorr.setElemento(currentItem.getTipoCorreccion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CCP);
				}
				myList.clear();
				
				reporteFolio.agregarAResourceData(reXLS.getResourceData());
				reporteRP.agregarAResourceData(reXLS.getResourceData());
				reporteFecha.agregarAResourceData(reXLS.getResourceData());
				reporteTipoCorr.agregarAResourceData(reXLS.getResourceData());
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
