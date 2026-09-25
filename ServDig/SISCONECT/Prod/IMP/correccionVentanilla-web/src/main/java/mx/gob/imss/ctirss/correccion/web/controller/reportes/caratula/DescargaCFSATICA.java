package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CFSATICAVO;

@SuppressWarnings("rawtypes")
public class DescargaCFSATICA extends AbstractReportesCaratula {
	
	
	public DescargaCFSATICA(List myList, String templateXLS, String outfileName,
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
			Reportes reporteNombre = new Reportes(13, "C");
			Reportes reporteUbicacion = new Reportes(13, "D");
			Reportes reporteTipoObra = new Reportes(13, "E");
			Reportes reporteNoRO = new Reportes(13, "F");
			Reportes reporteOPE = new Reportes(13, "G");
			Reportes reporteNoOficio = new Reportes(13, "H");
			Reportes reporteNOP = new Reportes(13, "I");
			Reportes reporteSE = new Reportes(13, "J");
			Reportes reporteCTC = new Reportes(13, "K");
			Reportes reportePEA = new Reportes(13, "L");
			Reportes reporteETR = new Reportes(13, "M");
			Reportes reporteAOP = new Reportes(13, "N");
			Reportes reportePAI = new Reportes(13, "O");
			Reportes reporteObservaciones = new Reportes(13, "P");
			
			Reportes delegacion = new Reportes(6, "B");
			Reportes subdelegacion = new Reportes(7, "B");
			Reportes fechaActual = new Reportes(7, "P");
			
			delegacion.setElemento(validaNull(sDelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
			
			subdelegacion.setElemento(validaNull(sSubdelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
			
			fechaActual.setElemento(getFechaActual()
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				CFSATICAVO currentItem = null;
				
				while(iter.hasNext()){
					currentItem = new CFSATICAVO((Object[])iter.next());
					
					reporteFolio.setElemento(currentItem.getFolioCorreccion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reporteRP.setElemento(currentItem.getRegistroPatronal()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reporteNombre.setElemento(currentItem.getNombre()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reporteUbicacion.setElemento(currentItem.getUbicacion()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reporteTipoObra.setElemento(currentItem.getTipoObra()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);

					reporteNoRO.setElemento(currentItem.getNoRO()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);

					reporteOPE.setElemento(currentItem.getOPE()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reporteNoOficio.setElemento(currentItem.getNoOficio()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reporteNOP.setElemento(currentItem.getNOP()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reporteSE.setElemento(currentItem.getSE()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reporteCTC.setElemento(currentItem.getCTC()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reportePEA.setElemento(currentItem.getPEA()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reporteETR.setElemento(currentItem.getETR()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reporteAOP.setElemento(currentItem.getAOP()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reportePAI.setElemento(currentItem.getPAI()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
					reporteObservaciones.setElemento(currentItem.getObservaciones()
							 ,ArchivosXLSCaratula.SHEET_CONTROL_CFSATICA);
					
				}
				myList.clear();
				
				reporteFolio.agregarAResourceData(reXLS.getResourceData());
				reporteRP.agregarAResourceData(reXLS.getResourceData());
				reporteNombre.agregarAResourceData(reXLS.getResourceData());
				reporteUbicacion.agregarAResourceData(reXLS.getResourceData());
				reporteTipoObra.agregarAResourceData(reXLS.getResourceData());
				reporteNoRO.agregarAResourceData(reXLS.getResourceData());
				reporteOPE.agregarAResourceData(reXLS.getResourceData());
				reporteNoOficio.agregarAResourceData(reXLS.getResourceData());
				reporteNOP.agregarAResourceData(reXLS.getResourceData());
				reporteSE.agregarAResourceData(reXLS.getResourceData());
				reporteCTC.agregarAResourceData(reXLS.getResourceData());
				reportePEA.agregarAResourceData(reXLS.getResourceData());
				reporteETR.agregarAResourceData(reXLS.getResourceData());
				reporteAOP.agregarAResourceData(reXLS.getResourceData());
				reportePAI.agregarAResourceData(reXLS.getResourceData());
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
