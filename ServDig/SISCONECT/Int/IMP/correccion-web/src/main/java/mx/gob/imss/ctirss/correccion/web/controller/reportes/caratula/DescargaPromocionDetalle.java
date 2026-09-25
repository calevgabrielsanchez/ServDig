package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CPPDVO;

@SuppressWarnings("rawtypes")
public class DescargaPromocionDetalle extends AbstractReportesCaratula {
	
	
	public DescargaPromocionDetalle(List myList, String templateXLS, String outfileName,
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
			
			  Reportes reporteNombre = new Reportes(13, "A");
			  Reportes reporteIdTipo = new Reportes(13, "B");
			  Reportes reporteDescripcion = new Reportes(13, "C");
			  Reportes reporteDescOrigen = new Reportes(13, "D");
			  Reportes reporteCriterioSeleccion = new Reportes(13, "E");
			  Reportes reporteFolio = new Reportes(13, "F");
			  Reportes reporteAfil15 = new Reportes(13, "G");
			  Reportes reporteCvePatron = new Reportes(13, "H");
			  Reportes reporteOpe = new Reportes(13, "I");
			  Reportes reporteNop = new Reportes(13, "J");
			  Reportes reporteAop = new Reportes(13, "K");
			  Reportes reporteSp = new Reportes(13, "L");
			  Reportes reporteOi = new Reportes(13, "M");
			  Reportes reportePr = new Reportes(13, "N");
			  Reportes reporteCr = new Reportes(13, "O");
			  Reportes reportePai = new Reportes(13, "P");
			  Reportes reporteC = new Reportes(13, "Q");
			  Reportes reportePeriodoDel = new Reportes(13, "R");
			  Reportes reportePeriodoAl = new Reportes(13, "S");
			  Reportes reportePorcentajeAvance = new Reportes(13, "T");
			  Reportes reportePorcentajeRegularizado = new Reportes(13, "U");
			  Reportes reporteNoConvenio = new Reportes(13, "V");
			  Reportes reporteNoParcialidades = new Reportes(13, "W");
			  Reportes reporteCopconvsp = new Reportes(13, "X");
			  Reportes reporteTcopsp = new Reportes(13, "Y");
			  Reportes reporteTcopact = new Reportes(13, "Z");
			  Reportes reporteTcoprec = new Reportes(13, "AA");
			  Reportes reporteTcoptotal = new Reportes(13, "AB");
			  Reportes reporteTcopMultas = new Reportes(13, "AC");
			  Reportes reporteTrabRevisados = new Reportes(13, "AD");
			  Reportes reporteTrabOmisos = new Reportes(13, "AE");
			  Reportes reporteTrabSubddeclarados = new Reportes(13, "AF");
			  Reportes reporteTcopspPagada = new Reportes(13, "AG");
			  Reportes reporteTcopPendientePago = new Reportes(13, "AH");
			  Reportes reporteRcvconvsp = new Reportes(13, "AI");
			  Reportes reporteTrcvsp = new Reportes(13, "AJ");
			  Reportes reporteTrcvact = new Reportes(13, "AK");
			  Reportes reporteTrcvrec = new Reportes(13, "AL");
			  Reportes reporteTrcvTotal = new Reportes(13, "AM");
			  Reportes reporteObservaciones = new Reportes(13, "AN");

			Reportes delegacion = new Reportes(6, "L");
			Reportes subdelegacion = new Reportes(6, "V");
			Reportes fechaActual = new Reportes(6, "AB");
			
			delegacion.setElemento(validaNull(sDelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
			
			subdelegacion.setElemento(validaNull(sSubdelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
			
			fechaActual.setElemento(getFechaActual()
					 ,ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
			
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				CPPDVO currentItem = null;
				
				while(iter.hasNext()){
					currentItem = new CPPDVO((Object[])iter.next());
					
					  reporteNombre.setElemento(currentItem.getNombre(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteIdTipo.setElemento(currentItem.getIdTipo(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteDescripcion.setElemento(currentItem.getDescripcion(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteDescOrigen.setElemento(currentItem.getDescOrigen(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteCriterioSeleccion.setElemento(currentItem.getCriterioSeleccion(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteFolio.setElemento(currentItem.getFolio(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteAfil15.setElemento(currentItem.getAfil15(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteCvePatron.setElemento(currentItem.getCvePatron(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteOpe.setElemento(currentItem.getOpe(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteNop.setElemento(currentItem.getNop(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteAop.setElemento(currentItem.getAop(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteSp.setElemento(currentItem.getSp(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteOi.setElemento(currentItem.getOi(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reportePr.setElemento(currentItem.getPr(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteCr.setElemento(currentItem.getCr(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reportePai.setElemento(currentItem.getPai(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteC.setElemento(currentItem.getC(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reportePeriodoDel.setElemento(currentItem.getPeriodoDel(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reportePeriodoAl.setElemento(currentItem.getPeriodoAl(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reportePorcentajeAvance.setElemento(currentItem.getPorcentajeAvance(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reportePorcentajeRegularizado.setElemento(currentItem.getPorcentajeRegularizado(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteNoConvenio.setElemento(currentItem.getNoConvenio(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteNoParcialidades.setElemento(currentItem.getNoParcialidades(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteCopconvsp.setElemento(currentItem.getCopconvsp(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTcopsp.setElemento(currentItem.getTcopsp(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTcopact.setElemento(currentItem.getTcopact(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTcoprec.setElemento(currentItem.getTcoprec(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTcoptotal.setElemento(currentItem.getTcoptotal(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTcopMultas.setElemento(currentItem.getTcopMultas(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTrabRevisados.setElemento(currentItem.getTrabRevisados(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTrabOmisos.setElemento(currentItem.getTrabOmisos(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTrabSubddeclarados.setElemento(currentItem.getTrabSubddeclarados(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTcopspPagada.setElemento(currentItem.getTcopspPagada(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTcopPendientePago.setElemento(currentItem.getTcopPendientePago(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteRcvconvsp.setElemento(currentItem.getRcvconvsp(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTrcvsp.setElemento(currentItem.getTrcvsp(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTrcvact.setElemento(currentItem.getTrcvact(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTrcvrec.setElemento(currentItem.getTrcvrec(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteTrcvTotal.setElemento(currentItem.getTrcvTotal(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);
					  reporteObservaciones.setElemento(currentItem.getObservaciones(),ArchivosXLSCaratula.SHEET_CONTROL_PROMOCION_DETALLE);


				}
				myList.clear();
				
				  reporteNombre.agregarAResourceData(reXLS.getResourceData());
				  reporteIdTipo.agregarAResourceData(reXLS.getResourceData());
				  reporteDescripcion.agregarAResourceData(reXLS.getResourceData());
				  reporteDescOrigen.agregarAResourceData(reXLS.getResourceData());
				  reporteCriterioSeleccion.agregarAResourceData(reXLS.getResourceData());
				  reporteFolio.agregarAResourceData(reXLS.getResourceData());
				  reporteAfil15.agregarAResourceData(reXLS.getResourceData());
				  reporteCvePatron.agregarAResourceData(reXLS.getResourceData());
				  reporteOpe.agregarAResourceData(reXLS.getResourceData());
				  reporteNop.agregarAResourceData(reXLS.getResourceData());
				  reporteAop.agregarAResourceData(reXLS.getResourceData());
				  reporteSp.agregarAResourceData(reXLS.getResourceData());
				  reporteOi.agregarAResourceData(reXLS.getResourceData());
				  reportePr.agregarAResourceData(reXLS.getResourceData());
				  reporteCr.agregarAResourceData(reXLS.getResourceData());
				  reportePai.agregarAResourceData(reXLS.getResourceData());
				  reporteC.agregarAResourceData(reXLS.getResourceData());
				  reportePeriodoDel.agregarAResourceData(reXLS.getResourceData());
				  reportePeriodoAl.agregarAResourceData(reXLS.getResourceData());
				  reportePorcentajeAvance.agregarAResourceData(reXLS.getResourceData());
				  reportePorcentajeRegularizado.agregarAResourceData(reXLS.getResourceData());
				  reporteNoConvenio.agregarAResourceData(reXLS.getResourceData());
				  reporteNoParcialidades.agregarAResourceData(reXLS.getResourceData());
				  reporteCopconvsp.agregarAResourceData(reXLS.getResourceData());
				  reporteTcopsp.agregarAResourceData(reXLS.getResourceData());
				  reporteTcopact.agregarAResourceData(reXLS.getResourceData());
				  reporteTcoprec.agregarAResourceData(reXLS.getResourceData());
				  reporteTcoptotal.agregarAResourceData(reXLS.getResourceData());
				  reporteTcopMultas.agregarAResourceData(reXLS.getResourceData());
				  reporteTrabRevisados.agregarAResourceData(reXLS.getResourceData());
				  reporteTrabOmisos.agregarAResourceData(reXLS.getResourceData());
				  reporteTrabSubddeclarados.agregarAResourceData(reXLS.getResourceData());
				  reporteTcopspPagada.agregarAResourceData(reXLS.getResourceData());
				  reporteTcopPendientePago.agregarAResourceData(reXLS.getResourceData());
				  reporteRcvconvsp.agregarAResourceData(reXLS.getResourceData());
				  reporteTrcvsp.agregarAResourceData(reXLS.getResourceData());
				  reporteTrcvact.agregarAResourceData(reXLS.getResourceData());
				  reporteTrcvrec.agregarAResourceData(reXLS.getResourceData());
				  reporteTrcvTotal.agregarAResourceData(reXLS.getResourceData());
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
