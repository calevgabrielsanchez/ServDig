package mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



//import mx.gob.imss.common.utils.readAndExportXLS.process.ReadAndExportXLS;
//import mx.gob.imss.common.utils.readAndExportXLS.vo.Catalog;
import mx.gob.imss.common.utils.readerXLS.proces.ReadAndExportXLS;
import mx.gob.imss.xls.loader.vo.Catalog;


import mx.gob.imss.ctirss.correccion.catalogos.model.CrcGastos;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
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
public class DescargaCedulaA extends AbstractCedulasCorreccion implements ICedulasCorreccion {

	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargadas las percepciones asignadas.
	 */
	private Catalog percepciones;
	
	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargadas los gastos asignadas.
	 */
	private Catalog gastos;
	
	/**
	 * Percepciones/Conceptos requeridos para la cedula A
	 */
	private List<CrcPercepciones> lsPercepciones;
	
	/**
	 * Gastos/Cuentas de mayor requeridos para la cedula A
	 */
	private List<CrcGastos> lsGastos;
	
	/**
	 * contiene datos para el encabezado requeridos para la cedula A
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
	 * Inicializa la Cedula A
	 * 
	 * @param request
	 * @param response
	 * @param idFolioCorreccion
	 * @param idCedula
	 * @param lsPercepciones
	 * @param lsGastos
	 * @param lsRegistrosPatronales
	 * @param folioCorreccion TODO
	 * @param ejercicioCorreccion TODO
	 * @throws Exception Cuando idFolioCorreccion,idCedula,reXLS, request or response is null
	 */
	public DescargaCedulaA(HttpServletRequest request,HttpServletResponse response,
							Integer idFolioCorreccion,Integer idCedula,
							List<CrcPercepciones> lsPercepciones, 
							List<CrcGastos> lsGastos,
							List<?> lsRegistrosPatronales,CrtSolicitudcorr crtSolicitudcorr, String folioCorreccion, String periodoCorreccion,CrtAnexosolcorrpat patronFiscal) throws Exception{
		
		this.request = validaRequest(request);
		this.response = validaResponse(response);
		this.idCedula = validaIdCedula(idCedula);
		this.lsPercepciones = lsPercepciones;
		this.lsGastos = lsGastos;
		this.lsRegistrosPatronales = lsRegistrosPatronales;
		this.crtSolicitudcorr = crtSolicitudcorr;
		this.foliCorreccion=folioCorreccion;
		this.patronFiscal=patronFiscal;
		this.periodoCorreccion=periodoCorreccion;
		reXLS = new ReadAndExportXLS(super.getRealBasePath()+
				CedulasCorreccion.RUTA_CEDULAS + CedulasCorreccion.CEDULA_A);
		
		cargaSeguridadArchivo();
		cargaEncabezado();
		cargaRegistrosPatronales();
		cargaPercepciones();
		cargaGastos();
		cargaClaveAnexoSol();
	}
	
	
	
	/** 	
	 * Permite ingresar las percepciones al XLS
	 */
	private void cargaPercepciones(){
		percepciones = new Catalog(3, "C");
		
		if(lsPercepciones!=null && !lsPercepciones.isEmpty()){
			Iterator<CrcPercepciones> iter = lsPercepciones.iterator();
			CrcPercepciones currentItem = null;
			
			while(iter.hasNext()){
				currentItem = iter.next();
				percepciones.setElemento(formaCatalogoLabelId(currentItem.getTxRemuneracion(),
										  					  currentItem.getCvePercepcion().toString()
															 ),CedulasCorreccion.CONTROL_SHEET_CEDULA);
			}
			
		}
		
		percepciones.agregarAResourceData(reXLS.getResourceData());
	}
	
	/**
	 * Permite ingresar los gastos al XLS
	 */
	private void cargaGastos(){
		gastos = new Catalog(3, "D");
		
		if(lsGastos!=null && !lsGastos.isEmpty()){
			Iterator<CrcGastos> iter = lsGastos.iterator();
			CrcGastos currentItem = null;
			
			while(iter.hasNext()){
				currentItem = iter.next();
				gastos.setElemento(formaCatalogoLabelId(currentItem.getTxGasto(),
										  					  currentItem.getCveGasto().toString()
															 ),CedulasCorreccion.CONTROL_SHEET_CEDULA);
			}
			
		}
		
		gastos.agregarAResourceData(reXLS.getResourceData());
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
		cveAnexoSolCorr = new Catalog(3, "E");
		
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



	public CrtAnexosolcorrpat getPatronFiscal() {
		return patronFiscal;
	}



	public void setPatronFiscal(CrtAnexosolcorrpat patronFiscal) {
		this.patronFiscal = patronFiscal;
	}



	
	
	
	
}
