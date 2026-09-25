package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;
import mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula.CTCPCDVO;

@SuppressWarnings("rawtypes")
public class DescargaCTCPCD extends AbstractReportesCaratula {
	
	
	public DescargaCTCPCD(List myList, String templateXLS, String outfileName,
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
			

			Reportes reporteNombre = new Reportes(12, 0);
			Reportes reporteDescripcion = new Reportes(12, 1);
			Reportes reporteFolio = new Reportes(12, 2);
			Reportes reporteAfil15 = new Reportes(12, 3);
			Reportes reporteDesc_origen = new Reportes(12, 4);
			Reportes reporteDesc_criterioSeleccion = new Reportes(12, 5);
			Reportes reporteCve_patron = new Reportes(12, 6);
			Reportes reporteQtyrp = new Reportes(12, 7);
			Reportes reportePeriodoDel = new Reportes(12, 8);
			Reportes reportePeriodoAl = new Reportes(12, 9);
			Reportes reporteTacopconvsp = new Reportes(12, 10);
			Reportes reporteTacopsp = new Reportes(12, 11);
			Reportes reporteTacopact = new Reportes(12, 12);
			Reportes reporteTacoprec = new Reportes(12, 13);
			Reportes reporteTacopTotal = new Reportes(12, 14);
			Reportes reporteTacopMultas = new Reportes(12, 15);
			Reportes reporteTaTrabRev = new Reportes(12, 16);
			Reportes reporteTaTrabajadoresOmisos = new Reportes(12, 17);
			Reportes reporteTaTrabajadoresSubdeclarados = new Reportes(12, 18);
			Reportes reporteTacopsppagada = new Reportes(12, 19);
			Reportes reporteTacopPendientePago = new Reportes(12, 20);
			Reportes reporteTarcvconvsp = new Reportes(12, 21);
			Reportes reporteTarcvsp = new Reportes(12, 22);
			Reportes reporteTarcvact = new Reportes(12, 23);
			Reportes reporteTarcvrec = new Reportes(12, 24);
			Reportes reporteTarcvTotal = new Reportes(12, 25);
			Reportes reporteTarcvMultas = new Reportes(12, 26);
			Reportes reporteAoi = new Reportes(12, 27);
			Reportes reporteAoin = new Reportes(12, 28);
			Reportes reporteAsp = new Reportes(12, 29);
			Reportes reporteAsr = new Reportes(12, 30);
			Reportes reporteAsa = new Reportes(12, 31);
			Reportes reporteAacp = new Reportes(12, 32);
			Reportes reporteAapp = new Reportes(12, 33);
			Reportes reporteAasr = new Reportes(12, 34);
			Reportes reporteTrcopconvsp = new Reportes(12, 35);
			Reportes reporteTrcopsp = new Reportes(12, 36);
			Reportes reporteTrcopact = new Reportes(12, 37);
			Reportes reporteTrcoprec = new Reportes(12, 38);
			Reportes reporteTrcopTotal = new Reportes(12, 39);
			Reportes reporteTrcopMultas = new Reportes(12, 40);
			Reportes reporteTrTrabRev = new Reportes(12, 41);
			Reportes reporteTrTrabajadoresOmisos = new Reportes(12, 42);
			Reportes reporteTrTrabajadoresSubdeclarados = new Reportes(12, 43);
			Reportes reporteTrcopsppagada = new Reportes(12, 44);
			Reportes reporteTrcopPendientePago = new Reportes(12, 45);
			Reportes reporteTrrcvconvsp = new Reportes(12, 46);
			Reportes reporteTrrcvsp = new Reportes(12, 47);
			Reportes reporteTrrcvact = new Reportes(12, 48);
			Reportes reporteTrrcvrec = new Reportes(12, 49);
			Reportes reporteTrrcvTotal = new Reportes(12, 50);
			Reportes reporteTrrcvMultas = new Reportes(12, 51);
			Reportes reporteRpcedraz = new Reportes(12, 52);
			Reportes reporteRcr = new Reportes(12, 53);
			Reportes reporteRccpr = new Reportes(12, 54);
			Reportes reporteRod = new Reportes(12, 55);
			Reportes reporteRnod = new Reportes(12, 56);
			Reportes reporteRvda = new Reportes(12, 57);
			Reportes reporteRvtc = new Reportes(12, 58);
			Reportes reporteRvpp = new Reportes(12, 59);
			Reportes reporteCsd = new Reportes(12, 60);
			Reportes reporteCpai = new Reportes(12, 61);
			Reportes reporteRdc = new Reportes(12, 62);
			Reportes reporteCds = new Reportes(12, 63);
			Reportes reporteCc = new Reportes(12, 64);
			Reportes reporteId_status = new Reportes(12, 65);
			Reportes reporteAuditor = new Reportes(12, 66);
			Reportes reporteCurp = new Reportes(12, 67);
			Reportes reporteMatricula = new Reportes(12, 68);

			Reportes delegacion = new Reportes(5, 25);
			Reportes subdelegacion = new Reportes(5, 32);
			Reportes fechaActual = new Reportes(5, 39);
			
			
			delegacion.setElemento(validaNull(sDelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);			
			subdelegacion.setElemento(validaNull(sSubdelegacion)
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);			
			fechaActual.setElemento(getFechaActual()
					 ,ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			
			
			if(myList!=null && !myList.isEmpty()){
				Iterator iter = myList.iterator();
				CTCPCDVO currentItem = null;
				
				while(iter.hasNext()){
					currentItem = new CTCPCDVO((Object[])iter.next());
					
			        reporteNombre.setElemento(currentItem.getNombre(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteDescripcion.setElemento(currentItem.getDescripcion(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteFolio.setElemento(currentItem.getFolio(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteAfil15.setElemento(currentItem.getAfil15(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteDesc_origen.setElemento(currentItem.getDesc_origen(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteDesc_criterioSeleccion.setElemento(currentItem.getDesc_criterioSeleccion(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteCve_patron.setElemento(currentItem.getCve_patron(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteQtyrp.setElemento(currentItem.getQtyrp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reportePeriodoDel.setElemento(currentItem.getPeriodoDel(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reportePeriodoAl.setElemento(currentItem.getPeriodoAl(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTacopconvsp.setElemento(currentItem.getTacopconvsp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTacopsp.setElemento(currentItem.getTacopsp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTacopact.setElemento(currentItem.getTacopact(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTacoprec.setElemento(currentItem.getTacoprec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTacopTotal.setElemento(currentItem.getTacopTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTacopMultas.setElemento(currentItem.getTacopMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTaTrabRev.setElemento(currentItem.getTaTrabRev(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTaTrabajadoresOmisos.setElemento(currentItem.getTaTrabajadoresOmisos(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTaTrabajadoresSubdeclarados.setElemento(currentItem.getTaTrabajadoresSubdeclarados(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTacopsppagada.setElemento(currentItem.getTacopsppagada(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTacopPendientePago.setElemento(currentItem.getTacopPendientePago(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTarcvconvsp.setElemento(currentItem.getTarcvconvsp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTarcvsp.setElemento(currentItem.getTarcvsp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTarcvact.setElemento(currentItem.getTarcvact(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTarcvrec.setElemento(currentItem.getTarcvrec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTarcvTotal.setElemento(currentItem.getTarcvTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTarcvMultas.setElemento(currentItem.getTarcvMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteAoi.setElemento(currentItem.getAoi(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteAoin.setElemento(currentItem.getAoin(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteAsp.setElemento(currentItem.getAsp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteAsr.setElemento(currentItem.getAsr(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteAsa.setElemento(currentItem.getAsa(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteAacp.setElemento(currentItem.getAacp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteAapp.setElemento(currentItem.getAapp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteAasr.setElemento(currentItem.getAasr(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrcopconvsp.setElemento(currentItem.getTrcopconvsp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrcopsp.setElemento(currentItem.getTrcopsp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrcopact.setElemento(currentItem.getTrcopact(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrcoprec.setElemento(currentItem.getTrcoprec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrcopTotal.setElemento(currentItem.getTrcopTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrcopMultas.setElemento(currentItem.getTrcopMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrTrabRev.setElemento(currentItem.getTrTrabRev(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrTrabajadoresOmisos.setElemento(currentItem.getTrTrabajadoresOmisos(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrTrabajadoresSubdeclarados.setElemento(currentItem.getTrTrabajadoresSubdeclarados(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrcopsppagada.setElemento(currentItem.getTrcopsppagada(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrcopPendientePago.setElemento(currentItem.getTrcopPendientePago(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrrcvconvsp.setElemento(currentItem.getTrrcvconvsp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrrcvsp.setElemento(currentItem.getTrrcvsp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrrcvact.setElemento(currentItem.getTrrcvact(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrrcvrec.setElemento(currentItem.getTrrcvrec(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrrcvTotal.setElemento(currentItem.getTrrcvTotal(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteTrrcvMultas.setElemento(currentItem.getTrrcvMultas(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteRpcedraz.setElemento(currentItem.getRpcedraz(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteRcr.setElemento(currentItem.getRcr(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteRccpr.setElemento(currentItem.getRccpr(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteRod.setElemento(currentItem.getRod(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteRnod.setElemento(currentItem.getRnod(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteRvda.setElemento(currentItem.getRvda(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteRvtc.setElemento(currentItem.getRvtc(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteRvpp.setElemento(currentItem.getRvpp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteCsd.setElemento(currentItem.getCsd(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteCpai.setElemento(currentItem.getCpai(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteRdc.setElemento(currentItem.getRdc(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteCds.setElemento(currentItem.getCds(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteCc.setElemento(currentItem.getCc(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteId_status.setElemento(currentItem.getId_status(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteAuditor.setElemento(currentItem.getAuditor(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteCurp.setElemento(currentItem.getCurp(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
			        reporteMatricula.setElemento(currentItem.getMatricula(),ArchivosXLSCaratula.SHEET_CONTROL_CORRECCION_DETALLE);
				}
				myList.clear();
				
			    reporteNombre.agregarAResourceData(reXLS.getResourceData());
			    reporteDescripcion.agregarAResourceData(reXLS.getResourceData());
			    reporteFolio.agregarAResourceData(reXLS.getResourceData());
			    reporteAfil15.agregarAResourceData(reXLS.getResourceData());
			    reporteDesc_origen.agregarAResourceData(reXLS.getResourceData());
			    reporteDesc_criterioSeleccion.agregarAResourceData(reXLS.getResourceData());
			    reporteCve_patron.agregarAResourceData(reXLS.getResourceData());
			    reporteQtyrp.agregarAResourceData(reXLS.getResourceData());
			    reportePeriodoDel.agregarAResourceData(reXLS.getResourceData());
			    reportePeriodoAl.agregarAResourceData(reXLS.getResourceData());
			    reporteTacopconvsp.agregarAResourceData(reXLS.getResourceData());
			    reporteTacopsp.agregarAResourceData(reXLS.getResourceData());
			    reporteTacopact.agregarAResourceData(reXLS.getResourceData());
			    reporteTacoprec.agregarAResourceData(reXLS.getResourceData());
			    reporteTacopTotal.agregarAResourceData(reXLS.getResourceData());
			    reporteTacopMultas.agregarAResourceData(reXLS.getResourceData());
			    reporteTaTrabRev.agregarAResourceData(reXLS.getResourceData());
			    reporteTaTrabajadoresOmisos.agregarAResourceData(reXLS.getResourceData());
			    reporteTaTrabajadoresSubdeclarados.agregarAResourceData(reXLS.getResourceData());
			    reporteTacopsppagada.agregarAResourceData(reXLS.getResourceData());
			    reporteTacopPendientePago.agregarAResourceData(reXLS.getResourceData());
			    reporteTarcvconvsp.agregarAResourceData(reXLS.getResourceData());
			    reporteTarcvsp.agregarAResourceData(reXLS.getResourceData());
			    reporteTarcvact.agregarAResourceData(reXLS.getResourceData());
			    reporteTarcvrec.agregarAResourceData(reXLS.getResourceData());
			    reporteTarcvTotal.agregarAResourceData(reXLS.getResourceData());
			    reporteTarcvMultas.agregarAResourceData(reXLS.getResourceData());
			    reporteAoi.agregarAResourceData(reXLS.getResourceData());
			    reporteAoin.agregarAResourceData(reXLS.getResourceData());
			    reporteAsp.agregarAResourceData(reXLS.getResourceData());
			    reporteAsr.agregarAResourceData(reXLS.getResourceData());
			    reporteAsa.agregarAResourceData(reXLS.getResourceData());
			    reporteAacp.agregarAResourceData(reXLS.getResourceData());
			    reporteAapp.agregarAResourceData(reXLS.getResourceData());
			    reporteAasr.agregarAResourceData(reXLS.getResourceData());
			    reporteTrcopconvsp.agregarAResourceData(reXLS.getResourceData());
			    reporteTrcopsp.agregarAResourceData(reXLS.getResourceData());
			    reporteTrcopact.agregarAResourceData(reXLS.getResourceData());
			    reporteTrcoprec.agregarAResourceData(reXLS.getResourceData());
			    reporteTrcopTotal.agregarAResourceData(reXLS.getResourceData());
			    reporteTrcopMultas.agregarAResourceData(reXLS.getResourceData());
			    reporteTrTrabRev.agregarAResourceData(reXLS.getResourceData());
			    reporteTrTrabajadoresOmisos.agregarAResourceData(reXLS.getResourceData());
			    reporteTrTrabajadoresSubdeclarados.agregarAResourceData(reXLS.getResourceData());
			    reporteTrcopsppagada.agregarAResourceData(reXLS.getResourceData());
			    reporteTrcopPendientePago.agregarAResourceData(reXLS.getResourceData());
			    reporteTrrcvconvsp.agregarAResourceData(reXLS.getResourceData());
			    reporteTrrcvsp.agregarAResourceData(reXLS.getResourceData());
			    reporteTrrcvact.agregarAResourceData(reXLS.getResourceData());
			    reporteTrrcvrec.agregarAResourceData(reXLS.getResourceData());
			    reporteTrrcvTotal.agregarAResourceData(reXLS.getResourceData());
			    reporteTrrcvMultas.agregarAResourceData(reXLS.getResourceData());
			    reporteRpcedraz.agregarAResourceData(reXLS.getResourceData());
			    reporteRcr.agregarAResourceData(reXLS.getResourceData());
			    reporteRccpr.agregarAResourceData(reXLS.getResourceData());
			    reporteRod.agregarAResourceData(reXLS.getResourceData());
			    reporteRnod.agregarAResourceData(reXLS.getResourceData());
			    reporteRvda.agregarAResourceData(reXLS.getResourceData());
			    reporteRvtc.agregarAResourceData(reXLS.getResourceData());
			    reporteRvpp.agregarAResourceData(reXLS.getResourceData());
			    reporteCsd.agregarAResourceData(reXLS.getResourceData());
			    reporteCpai.agregarAResourceData(reXLS.getResourceData());
			    reporteRdc.agregarAResourceData(reXLS.getResourceData());
			    reporteCds.agregarAResourceData(reXLS.getResourceData());
			    reporteCc.agregarAResourceData(reXLS.getResourceData());
			    reporteId_status.agregarAResourceData(reXLS.getResourceData());
			    reporteAuditor.agregarAResourceData(reXLS.getResourceData());
			    reporteCurp.agregarAResourceData(reXLS.getResourceData());
			    reporteMatricula.agregarAResourceData(reXLS.getResourceData());
				
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
