package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CCPVO;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.TDVO;

@SuppressWarnings("rawtypes")
public class DescargaTD extends AbstractReportesCaratula {
	
	
	public DescargaTD(List myList, String templateXLS, String outfileName,
									HttpServletRequest request, HttpServletResponse response){
		
		setMyList(myList);
		setTemplateXLS(templateXLS);
		setOutfileName(outfileName);
		setRequest(request);
		setResponse(response);
		setReXLS();
		
	}
	
	public String obtenerReporte(){
		return obtenerReporte(null, null);
	}
	public String obtenerReporte(String sDelegacion, String sSubdelegacion){
		
		if(!myList.isEmpty()){
			
			Reportes folioCorreccion= new Reportes(12, "A");
			Reportes registroPatronal= new Reportes(12, "B");
			Reportes proceso= new Reportes(12, "C");
			Reportes trabajadoresRevisados= new Reportes(12, "D");
			Reportes trabajadoresOmisos= new Reportes(12, "E");
			Reportes trabajadoresSubDeclarados= new Reportes(12, "F");
			Reportes trabajadoresRegularizados= new Reportes(12, "G");
			
			Reportes delegacion = new Reportes(6, "C");
			Reportes subdelegacion = new Reportes(7, "C");
			Reportes fechaActual = new Reportes(7, "F");
			
			delegacion.setElemento(validaNull(sDelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_TD);
			
			subdelegacion.setElemento(validaNull(sSubdelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_TD);
			
			fechaActual.setElemento(getFechaActual()
					 ,ArchivosXLSCaratula.SHEET_CONTROL_TD);
			
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				TDVO currentItem = null;
				
				while(iter.hasNext()){
					currentItem = new TDVO((Object[])iter.next());
					
					folioCorreccion.setElemento(currentItem.getFolioCorreccion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_TD);
					
					registroPatronal.setElemento(currentItem.getRegistroPatronal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_TD);
					
					proceso.setElemento(currentItem.getProceso()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_TD);
					
					trabajadoresRevisados.setElemento(currentItem.getTrabajadoresRevisados()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_TD);
					
					trabajadoresOmisos.setElemento(currentItem.getTrabajadoresOmisos()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_TD);
					
					trabajadoresSubDeclarados.setElemento(currentItem.getTrabajadoresSubDeclarados()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_TD);
					
					trabajadoresRegularizados.setElemento(currentItem.getTrabajadoresRegularizados()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_TD);
				}
				myList.clear();
				
				folioCorreccion.agregarAResourceData(reXLS.getResourceData());
				registroPatronal.agregarAResourceData(reXLS.getResourceData());
				proceso.agregarAResourceData(reXLS.getResourceData());
				trabajadoresRevisados.agregarAResourceData(reXLS.getResourceData());
				trabajadoresOmisos.agregarAResourceData(reXLS.getResourceData());
				trabajadoresSubDeclarados.agregarAResourceData(reXLS.getResourceData());
				trabajadoresRegularizados.agregarAResourceData(reXLS.getResourceData());
				
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
