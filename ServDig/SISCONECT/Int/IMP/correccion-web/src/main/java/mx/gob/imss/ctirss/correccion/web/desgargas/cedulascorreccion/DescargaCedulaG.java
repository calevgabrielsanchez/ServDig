package mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
//import mx.gob.imss.common.utils.readAndExportXLS.process.ReadAndExportXLS;
//import mx.gob.imss.common.utils.readAndExportXLS.vo.Catalog;
import mx.gob.imss.common.utils.readerXLS.proces.ReadAndExportXLS;
import mx.gob.imss.xls.loader.vo.Catalog;
import mx.gob.imss.ctirss.correccion.constantes.CedulasCorreccion; 
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.utils.Functions;

/**
 * Permite tener una interaccion total con los datos de la base de datos y el Excel fisico o cedula.
 * 
 * Se debe de ingresar todos los catalogos correspondientes y posteriormente ejecutar el 
 * metodo generarXLS(String) el cual nos arrojara en pantalla el XLS Generado.
 * 
 * Se recomienda revisar los metodos contenidos en AbstractCedulasCorreccion
 * 
 * 
 * @author Marco Antonio Nieto Plett
 * @see ICedulasCorreccion
 * @see AbstractCedulasCorreccion
 */
public class DescargaCedulaG extends AbstractCedulasCorreccion implements ICedulasCorreccion {


	/**
	 * contiene datos para el encabezado requeridos para la cedula G
	 */
	private CrtSolicitudcorr crtSolicitudcorr;
	
	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargadas las claves de los registros Patronales.
	 */
	private Catalog cveAnexoSolCorr;
	
	
	
	private CrtAnexosolcorrpat patronFiscal;
	
	
	
	/**
	 * 
	 * Inicializa la Cedula G
	 * 
	 * @param request
	 * @param response
	 * @param idFolioCorreccion
	 * @param idCedula
	 * @param lsRegistrosPatronales
	 * @param folioCorreccion TO DO
	 * @param periodoCorreccion TO DO
	 * @throws Exception Cuando idFolioCorreccion,idCedula,reXLS, request or response is null
	 */
	public DescargaCedulaG(HttpServletRequest request,HttpServletResponse response,
							Integer idFolioCorreccion,Integer idCedula,
							List<?> lsRegistrosPatronales, CrtSolicitudcorr crtSolicitudcorr, String folioCorreccion, String periodoCorreccion,CrtAnexosolcorrpat patronFiscal) throws Exception{
		
		this.request = validaRequest(request);
		this.response = validaResponse(response);
		this.idCedula = validaIdCedula(idCedula);
		this.lsRegistrosPatronales = lsRegistrosPatronales;
		this.crtSolicitudcorr = crtSolicitudcorr;
		this.foliCorreccion=folioCorreccion;
		this.patronFiscal=patronFiscal;
		this.periodoCorreccion=periodoCorreccion;
		reXLS = new ReadAndExportXLS(super.getRealBasePath()+
				CedulasCorreccion.RUTA_CEDULAS + CedulasCorreccion.CEDULA_G);
		
		cargaSeguridadArchivo();
		cargaEncabezado();
		cargaRegistrosPatronales();
		cargaClaveAnexoSol();
	}
	
	
	@Override
	public void cargaRegistrosPatronales(){
		registrosPatronales = new Catalog(3, "B");
		
		if(lsRegistrosPatronales!=null && !lsRegistrosPatronales.isEmpty()){
			Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) lsRegistrosPatronales.iterator();
			CrtAnexosolcorrpat currentItem = null;
			
			while(iter.hasNext()){
				currentItem = iter.next();
				System.out.println("REGPATRONAL: " + currentItem.getRegistroPatronal());
				registrosPatronales.setElemento(currentItem.getRegistroPatronal(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
			}
			
		}
		
		registrosPatronales.agregarAResourceData(reXLS.getResourceData());
	}

	

	public void cargaClaveAnexoSol(){
		cveAnexoSolCorr = new Catalog(3, "C");
		
		if(lsRegistrosPatronales!=null && !lsRegistrosPatronales.isEmpty()){
			Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) lsRegistrosPatronales.iterator();
			CrtAnexosolcorrpat currentItem = null;
			
			while(iter.hasNext()){
				currentItem = iter.next();
				cveAnexoSolCorr.setElemento(currentItem.getCveAnexoSolicitudCorrPat().toString(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
			}
			
		}
		
		cveAnexoSolCorr.agregarAResourceData(reXLS.getResourceData());
	}
	
	@Override
	public void cargaEncabezado(){
		encabezado = new Catalog(3,"L");
		String rp="RP";
		encabezado.setElemento(lsRegistrosPatronales!=null?((CrtAnexosolcorrpat)lsRegistrosPatronales.get(0)).getStrDelegacion():"DELEGACION", CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(lsRegistrosPatronales!=null?((CrtAnexosolcorrpat)lsRegistrosPatronales.get(0)).getStrSubdelegacion():"SUB DELEGACION",  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(crtSolicitudcorr.getRazonSocialPatronPrin(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(crtSolicitudcorr.getNuFolio(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(crtSolicitudcorr.getPeriodo().toString(), CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(Functions.dateToString(crtSolicitudcorr.getFecFechaPeriodoIni()).replaceAll("-", "/"),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(Functions.dateToString(crtSolicitudcorr.getFecFechaPeriodoFin()).replaceAll("-", "/"),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		
//		for(Object anexo:lsRegistrosPatronales){
//			CrtAnexosolcorrpat regPatronal=(CrtAnexosolcorrpat) anexo;
//			if(regPatronal.getTipoPatron().equals("F")){
//				System.out.println("Registro Padre Fiscal "+regPatronal.getRegistroPatronal());
//				rp=regPatronal.getRegistroPatronal();
//				break;
//			}
//		}
		
		encabezado.setElemento(patronFiscal.getRegistroPatronal(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		
		encabezado.agregarAResourceData(reXLS.getResourceData());
		
	}
	
	@Override
	public void cargaSeguridadArchivo(){
		
		seguridadArchivo = new Catalog(CedulasCorreccion.CELDA_CONTROL_SEGURIDAD_FILAL,
										CedulasCorreccion.CELDA_CONTROL_SEGURIDAD_COLUMNA);
		
		
		seguridadArchivo.setElemento(crtSolicitudcorr.getNuFolio(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		seguridadArchivo.setElemento(idCedula.toString(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		
		seguridadArchivo.agregarAResourceData(reXLS.getResourceData());
		
		
		nombreArchivo = new Catalog(CedulasCorreccion.CELDA_CONTROL_SEGURIDAD_FILAL, "B");
		nombreArchivo.setElemento(this.getFoliCorreccion().replace("/", "_")+"-"+this.getPeriodoCorreccion(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		nombreArchivo.agregarAResourceData(reXLS.getResourceData());
		
	}
	
}
