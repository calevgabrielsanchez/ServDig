package mx.gob.imss.ctirss.correccion.web.controller.reportes.caratula;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Reportes;
import mx.gob.imss.ctirss.correccion.constantes.ArchivosXLSCaratula;



@SuppressWarnings("rawtypes")
public class DescargaResumenResultados extends AbstractReportesCaratula {

	private List listaCOP;
	private List listaRCV;
	private List listaTrabajadores;
	
	public DescargaResumenResultados(List myList, String templateXLS, String outfileName,
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
	
		generaSeccionCOP(sDelegacion, sSubdelegacion);
		generaSeccionRCV();
		generaSeccionTrabajadores();
		return generaReporte();
		
	}
	
	
	public void generaSeccionCOP(String sDelegacion, String sSubdelegacion){
		
		
		char [] columnas={'C','D','E','F'};
		BigDecimal totalCOP_SP=BigDecimal.ZERO;
		BigDecimal totalCOP_Act=BigDecimal.ZERO;
		BigDecimal totalCOP_Rec=BigDecimal.ZERO;
		BigDecimal totalCOP_Tot=BigDecimal.ZERO;

		int con=0;
	
		Reportes delegacion = new Reportes(6, "C");
		Reportes subdelegacion = new Reportes(7, "C");
		Reportes fechaActual = new Reportes(7, "H");
		Reportes totalCOP=new Reportes(12, "G");
		Reportes totalCOPRevision=new Reportes(12, "H");
		Reportes totalCOPGranTotal=new Reportes(12, "I");
		Object[] registroAutodeterminacion=null;
		Object[] registroCorreccion=null;
		
		delegacion.setElemento(validaNull(sDelegacion)
				 ,ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		
		subdelegacion.setElemento(validaNull(sSubdelegacion)
				 ,ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		
		fechaActual.setElemento(getFechaActual()
				 ,ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		
	
		if(listaCOP!=null && !listaCOP.isEmpty()){
			Iterator iterator = listaCOP.iterator();
			int registro=0;
			while(iterator.hasNext()){
				Object[] obj=(Object[]) iterator.next();
				generaCOPByColumna(String.valueOf(columnas[con++]),obj);					
				if(registro==0){
				    registroAutodeterminacion=obj;				
				}else if(registro==1){
					registroCorreccion=obj;
				}else if(registro==2 || registro==3){
					totalCOP_SP=totalCOP_SP.add(validaBigDecimal((BigDecimal) obj[0]));
					totalCOP_Act=totalCOP_Act.add(validaBigDecimal((BigDecimal) obj[1]));
					totalCOP_Rec=totalCOP_Rec.add(validaBigDecimal((BigDecimal) obj[2]));
					
				}
				registro++;
			}
			totalCOP_Tot=totalCOP_Tot.add(totalCOP_SP).add(totalCOP_Act).add(totalCOP_Rec);
			
			totalCOP.setElemento(formatNumero(totalCOP_SP), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalCOP.setElemento(formatNumero(totalCOP_Act), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalCOP.setElemento(formatNumero(totalCOP_Rec), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalCOP.setElemento(formatNumero(totalCOP_Tot), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			
			BigDecimal spTotalRev=totalCOP_SP.add(validaBigDecimal((BigDecimal) registroCorreccion[0]));
			BigDecimal actTotalRev=totalCOP_Act.add(validaBigDecimal((BigDecimal) registroCorreccion[1]));
			BigDecimal recTotalRev=totalCOP_Rec.add(validaBigDecimal((BigDecimal) registroCorreccion[2]));
			BigDecimal totTotalRev=spTotalRev.add(actTotalRev).add(recTotalRev);
			
			totalCOPRevision.setElemento(formatNumero(spTotalRev), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalCOPRevision.setElemento(formatNumero(actTotalRev), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalCOPRevision.setElemento(formatNumero(recTotalRev), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalCOPRevision.setElemento(formatNumero(spTotalRev.add(actTotalRev).add(recTotalRev)), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		
			totalCOPGranTotal.setElemento(formatNumero((spTotalRev).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[0]))), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalCOPGranTotal.setElemento(formatNumero((actTotalRev).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[1]))), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalCOPGranTotal.setElemento(formatNumero((recTotalRev).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[2]))), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			
			BigDecimal autodete=(validaBigDecimal((BigDecimal)registroAutodeterminacion[0])).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[1])).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[2]));
			totalCOPGranTotal.setElemento(formatNumero((totTotalRev).add(autodete)), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		
		}
		
		
		delegacion.agregarAResourceData(reXLS.getResourceData());
		subdelegacion.agregarAResourceData(reXLS.getResourceData());
		fechaActual.agregarAResourceData(reXLS.getResourceData());
		totalCOP.agregarAResourceData(reXLS.getResourceData());
		totalCOPRevision.agregarAResourceData(reXLS.getResourceData());		
		totalCOPGranTotal.agregarAResourceData(reXLS.getResourceData());		
		
		
	}
	
	
public void generaSeccionRCV(){
		
		
		char [] columnas={'C','D','E','F'};
		BigDecimal totalRCV_SP=BigDecimal.ZERO;
		BigDecimal totalRCV_Act=BigDecimal.ZERO;
		BigDecimal totalRCV_Rec=BigDecimal.ZERO;
		BigDecimal totalRCV_Tot=BigDecimal.ZERO;

		int con=0;
	
	
		
		Reportes totalRCV=new Reportes(16, "G");
		Reportes totalRCVRevision=new Reportes(16, "H");
		Reportes totalRCVGranTotal=new Reportes(16, "I");
		Object[] registroAutodeterminacion=null;
		Object[] registroCorreccion=null;
		

		
	
		if(listaRCV!=null && !listaRCV.isEmpty()){
			Iterator iterator = listaRCV.iterator();
			int registro=0;
			while(iterator.hasNext()){
				Object[] obj=(Object[]) iterator.next();
				generaRCVByColumna(String.valueOf(columnas[con++]),obj);					
				if(registro==0){
				    registroAutodeterminacion=obj;				
				}else if(registro==1){
					registroCorreccion=obj;
				}else if(registro==2 || registro==3){
					totalRCV_SP=totalRCV_SP.add(validaBigDecimal((BigDecimal) obj[0]));
					totalRCV_Act=totalRCV_Act.add(validaBigDecimal((BigDecimal) obj[1]));
					totalRCV_Rec=totalRCV_Rec.add(validaBigDecimal((BigDecimal) obj[2]));
					
				}
				registro++;
			}
			totalRCV_Tot=totalRCV_Tot.add(totalRCV_SP).add(totalRCV_Act).add(totalRCV_Rec);
			
			totalRCV.setElemento(formatNumero(totalRCV_SP), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalRCV.setElemento(formatNumero(totalRCV_Act), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalRCV.setElemento(formatNumero(totalRCV_Rec), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalRCV.setElemento(formatNumero(totalRCV_Tot), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			
			BigDecimal spTotalRev=totalRCV_SP.add(validaBigDecimal((BigDecimal) registroCorreccion[0]));
			BigDecimal actTotalRev=totalRCV_Act.add(validaBigDecimal((BigDecimal) registroCorreccion[1]));
			BigDecimal recTotalRev=totalRCV_Rec.add(validaBigDecimal((BigDecimal) registroCorreccion[2]));
			BigDecimal totTotalRev=spTotalRev.add(actTotalRev).add(recTotalRev);
			
			totalRCVRevision.setElemento(formatNumero(spTotalRev), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalRCVRevision.setElemento(formatNumero(actTotalRev), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalRCVRevision.setElemento(formatNumero(recTotalRev), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalRCVRevision.setElemento(formatNumero(totalRCV_Rec.add(totTotalRev)), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		
			totalRCVGranTotal.setElemento(formatNumero((spTotalRev).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[0]))), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalRCVGranTotal.setElemento(formatNumero((actTotalRev).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[1]))), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			totalRCVGranTotal.setElemento(formatNumero((recTotalRev).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[2]))), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
			
			BigDecimal autodete=(validaBigDecimal((BigDecimal)registroAutodeterminacion[0])).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[1])).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[2]));
			totalRCVGranTotal.setElemento(formatNumero((totTotalRev).add(autodete)), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		
		}
		

		totalRCV.agregarAResourceData(reXLS.getResourceData());
		totalRCVRevision.agregarAResourceData(reXLS.getResourceData());		
		totalRCVGranTotal.agregarAResourceData(reXLS.getResourceData());		
		
		
	}

public void generaSeccionTrabajadores(){
	
	
	char [] columnas={'C','D','E','F'};
	BigDecimal totalTrab_Revisados=BigDecimal.ZERO;
	BigDecimal totalTrab_Omisos=BigDecimal.ZERO;
	BigDecimal totalTrab_Subdeclarados=BigDecimal.ZERO;
	BigDecimal totalTrab_Regularizados=BigDecimal.ZERO;

	int con=0;


	
	Reportes totalTrabRe=new Reportes(20, "G");
	Reportes totalTrabRevision=new Reportes(20, "H");
	Reportes totalTrabGranTotal=new Reportes(20, "I");
	Object[] registroAutodeterminacion=null;
	Object[] registroCorreccion=null;
	
	


	if(listaTrabajadores!=null && !listaTrabajadores.isEmpty()){
		Iterator iterator = listaTrabajadores.iterator();
		int registro=0;
		while(iterator.hasNext()){
			Object[] obj=(Object[]) iterator.next();
			generaTrabajadoresByColumna(String.valueOf(columnas[con++]),obj);					
			if(registro==0){
			    registroAutodeterminacion=obj;				
			}else if(registro==1){
				registroCorreccion=obj;
			}else if(registro==2 || registro==3){
				totalTrab_Revisados=totalTrab_Revisados.add(validaBigDecimal((BigDecimal) obj[0]));
				totalTrab_Omisos=totalTrab_Omisos.add(validaBigDecimal((BigDecimal) obj[1]));
				totalTrab_Subdeclarados=totalTrab_Subdeclarados.add(validaBigDecimal((BigDecimal) obj[2]));
				
			}
			registro++;
		}
		
		totalTrab_Regularizados=totalTrab_Regularizados.add(totalTrab_Omisos).add(totalTrab_Subdeclarados);
		
		totalTrabRe.setElemento(formatNumero(totalTrab_Revisados), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		totalTrabRe.setElemento(formatNumero(totalTrab_Omisos), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		totalTrabRe.setElemento(formatNumero(totalTrab_Subdeclarados), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		totalTrabRe.setElemento(formatNumero(totalTrab_Regularizados), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		
		BigDecimal revisadosTotalRev=totalTrab_Revisados.add(validaBigDecimal((BigDecimal) registroCorreccion[0]));
		BigDecimal omisosTotalRev=totalTrab_Omisos.add(validaBigDecimal((BigDecimal) registroCorreccion[1]));
		BigDecimal subdeclaradosTotalRev=totalTrab_Subdeclarados.add(validaBigDecimal((BigDecimal) registroCorreccion[2]));
		BigDecimal totTotalRev=omisosTotalRev.add(subdeclaradosTotalRev);
		
		totalTrabRevision.setElemento(formatNumero(revisadosTotalRev), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		totalTrabRevision.setElemento(formatNumero(omisosTotalRev), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		totalTrabRevision.setElemento(formatNumero(subdeclaradosTotalRev), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		totalTrabRevision.setElemento(formatNumero(totalTrab_Subdeclarados.add(totTotalRev)), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
	
		totalTrabGranTotal.setElemento(formatNumero((revisadosTotalRev).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[0]))), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		totalTrabGranTotal.setElemento(formatNumero((omisosTotalRev).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[1]))), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		totalTrabGranTotal.setElemento(formatNumero((subdeclaradosTotalRev).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[2]))), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		
		BigDecimal regularizadosAutodete=(validaBigDecimal((BigDecimal)registroAutodeterminacion[1])).add(validaBigDecimal((BigDecimal)registroAutodeterminacion[2]));
		totalTrabGranTotal.setElemento(formatNumero((totTotalRev).add(regularizadosAutodete)), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
	
	}
	

	totalTrabRe.agregarAResourceData(reXLS.getResourceData());
	totalTrabRevision.agregarAResourceData(reXLS.getResourceData());		
	totalTrabGranTotal.agregarAResourceData(reXLS.getResourceData());		
	
	
}

	
	public void generaCOPByColumna(String columna,Object[] registro){
		Reportes cop = new Reportes(12, columna);
		
		BigDecimal sp=validaBigDecimal((BigDecimal) registro[0]);
		BigDecimal act=validaBigDecimal((BigDecimal) registro[1]);
		BigDecimal rec=validaBigDecimal((BigDecimal) registro[2]);
		
		cop.setElemento(formatNumero((BigDecimal)registro[0]), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.setElemento(formatNumero((BigDecimal)registro[1]), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.setElemento(formatNumero((BigDecimal)registro[2]), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.setElemento(formatNumero((BigDecimal)sp.add(act).add(rec)), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.agregarAResourceData(reXLS.getResourceData());
	}

	public void generaRCVByColumna(String columna,Object[] registro){
		Reportes cop = new Reportes(16, columna);
		
		BigDecimal sp=validaBigDecimal((BigDecimal) registro[0]);
		BigDecimal act=validaBigDecimal((BigDecimal) registro[1]);
		BigDecimal rec=validaBigDecimal((BigDecimal) registro[2]);
		
		cop.setElemento(formatNumero((BigDecimal) registro[0]), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.setElemento(formatNumero((BigDecimal)registro[1]), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.setElemento(formatNumero((BigDecimal)registro[2]), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.setElemento(formatNumero((BigDecimal)sp.add(act).add(rec)), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.agregarAResourceData(reXLS.getResourceData());
	}

	public void generaTrabajadoresByColumna(String columna,Object[] registro){
		Reportes cop = new Reportes(20, columna);
		
		BigDecimal revisados=validaBigDecimal((BigDecimal)  registro[0]);
		BigDecimal omisos=validaBigDecimal((BigDecimal) registro[1]);
		BigDecimal subdeclarados=validaBigDecimal((BigDecimal) registro[2]);
		
		cop.setElemento(formatNumero((BigDecimal)registro[0]), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.setElemento(formatNumero((BigDecimal)registro[1]), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.setElemento(formatNumero((BigDecimal)registro[2]), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.setElemento(formatNumero((BigDecimal)omisos.add(subdeclarados)), ArchivosXLSCaratula.SHEET_RESUMEN_DE_RESULTADOS);
		cop.agregarAResourceData(reXLS.getResourceData());
	}
	
	
	private String formatNumero(BigDecimal numero){
		if(numero==null){
			numero=BigDecimal.ZERO;
		}
	    NumberFormat formatter = new DecimalFormat("#,###,###.####");
	    return formatter.format(numero);
		
	}
	
	private BigDecimal validaBigDecimal(BigDecimal numero){
		if(numero==null){
			return BigDecimal.ZERO;
		}else{
			return numero;
		}
	}
	
	
	public void generaCOPTotales(Object[] registro){
		
		
	}
	
	public List getListaCOP() {
		return listaCOP;
	}

	public void setListaCOP(List listaCOP) {
		this.listaCOP = listaCOP;
	}

	public List getListaRCV() {
		return listaRCV;
	}

	public void setListaRCV(List listaRCV) {
		this.listaRCV = listaRCV;
	}

	public List getListaTrabajadores() {
		return listaTrabajadores;
	}

	public void setListaTrabajadores(List listaTrabajadores) {
		this.listaTrabajadores = listaTrabajadores;
	}

	
	
}
