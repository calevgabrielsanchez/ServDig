package mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
//import mx.gob.imss.common.utils.readAndExportXLS.process.ReadAndExportXLS;
//import mx.gob.imss.common.utils.readAndExportXLS.vo.Catalog;
import mx.gob.imss.common.utils.readerXLS.proces.ReadAndExportXLS;
import mx.gob.imss.xls.loader.vo.Catalog;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtEjertrabajador;
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
public class DescargaCedulaI extends AbstractCedulasCorreccion implements ICedulasCorreccion {

	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargadas las percepciones asignadas.
	 */
	private Catalog percepciones;
	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargadas las nss de los trabajadores
	 */
	private Catalog id_trabajador;
	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargados los nombres de los trabajadores
	 */
	private Catalog nss;
	/**
	 * Permite controlar la psicion y hoja en donde seran
	 * cargados los apellidos paternos de los trabajadores
	 */
	private Catalog apellidoPaterno;
	/**
	 * Permite controlar la psicion y hoja en donde seran
	 * cargados los apellidos maternos de los trabajadores
	 */
	private Catalog apellidoMaterno;
	/**
	 * Permite controlar la psicion y hoja en donde seran
	 * cargados los rfc de los trabajadores
	 */
	private Catalog nombre;
	
	/**
	 * Percepciones/Conceptos requeridos para la cedula I
	 */
	private List<CrcPercepciones> lsPercepciones;
	
	/**
	 * Trabajadores requeridos para la cedula I
	 */
	private List<CrcTrabajadores> lsTrabajadores;
	
	/**
	 * contiene datos para el encabezado requeridos para la cedula I
	 */
	private CrtSolicitudcorr crtSolicitudcorr;
	
	private Catalog regPatron;
	/**
	 * Permite asociar a cada trabajador con su respectiva
	 * clave
	 */
	private CrtAnexosolcorrpat patronFiscal;
//	private List<?> lsTrabajadores;
	/**
	 * 
	 * Inicializa la Cedula I
	 * 
	 * @param request
	 * @param response
	 * @param idFolioCorreccion
	 * @param idCedula
	 * @param lsPercepciones
	 * @param lsTrabajadores
	 * @param lsRegistrosPatronales
	 * @param folioCorreccion TODO
	 * @param periodoCorreccion TODO
	 * @throws Exception Cuando idFolioCorreccion,idCedula,reXLS, request or response is null
	 */
	public DescargaCedulaI(HttpServletRequest request,HttpServletResponse response,
							Integer idFolioCorreccion,Integer idCedula,
							List<CrcPercepciones> lsPercepciones, List<CrcTrabajadores> lsTrabajadores, 
							List<?> lsRegistrosPatronales, CrtSolicitudcorr crtSolicitudcorr, String folioCorreccion, String periodoCorreccion,CrtAnexosolcorrpat patronFiscal) throws Exception{
		
		this.request = validaRequest(request);
		this.response = validaResponse(response);
		this.idCedula = validaIdCedula(idCedula);
		this.lsPercepciones = lsPercepciones;
		this.lsTrabajadores = lsTrabajadores;
		this.lsRegistrosPatronales = lsRegistrosPatronales;
		this.crtSolicitudcorr = crtSolicitudcorr;
		this.foliCorreccion=folioCorreccion;
		this.periodoCorreccion=periodoCorreccion;
		this.patronFiscal=patronFiscal;
		reXLS = new ReadAndExportXLS(super.getRealBasePath()+
				CedulasCorreccion.RUTA_CEDULAS + CedulasCorreccion.CEDULA_I);
		
		cargaSeguridadArchivo();
		cargaEncabezado();
		cargaRegistrosPatronales();
		cargaPercepciones();
		cargaTrabajadores();
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
	 * Permite ingresar las percepciones al XLS
	 */
	private void cargaTrabajadores(){
		regPatron = new Catalog(3, "O");
		id_trabajador = new Catalog(3, "P");
		nss = new Catalog(3, "Q");
		apellidoPaterno= new Catalog(3, "R");
		apellidoMaterno= new Catalog(3, "S");
		nombre= new Catalog(3, "T");		
		
	
		
		if(lsRegistrosPatronales!=null && !lsRegistrosPatronales.isEmpty()){
			Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) lsRegistrosPatronales.iterator();
			CrtAnexosolcorrpat currentItem = null;
			
			while(iter.hasNext()){				
				currentItem = iter.next();
				
				if(currentItem.getTrabajadores()!=null && !currentItem.getTrabajadores().isEmpty()){
					Iterator<CrtEjertrabajador> iterTrabajador = currentItem.getTrabajadores().iterator();
					CrtEjertrabajador currentItemTrabajador = null;
					
					while(iterTrabajador.hasNext()){				
						currentItemTrabajador = iterTrabajador.next();
						
						regPatron.setElemento(currentItem.getRegistroPatronal(), CedulasCorreccion.CONTROL_SHEET_CEDULA);
						id_trabajador.setElemento(currentItemTrabajador.getCveEjertrab().toString(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
						nss.setElemento(currentItemTrabajador.getCrcTrabajadores().getNuNss(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
						apellidoPaterno.setElemento(currentItemTrabajador.getCrcTrabajadores().getApPaternoAsegurado(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
						apellidoMaterno.setElemento(currentItemTrabajador.getCrcTrabajadores().getApMaternoAsegurado(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
						nombre.setElemento(currentItemTrabajador.getCrcTrabajadores().getNombreAsegurado(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
					}
					
				}
			
				
			}
			
		}
		
		
	/**
		if(lsTrabajadores!=null && !lsTrabajadores.isEmpty()){
			Iterator<CrcTrabajadores> iter = lsTrabajadores.iterator();
			CrcTrabajadores currentItem = null;
			
			while(iter.hasNext()){
				currentItem = iter.next();
				id_trabajador.setElemento(""+currentItem.getCveTrabajador(), CedulasCorreccion.CONTROL_SHEET_CEDULA);
				nss.setElemento(currentItem.getNuNss(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
				apellidoPaterno.setElemento(currentItem.getaPaterno(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
				apellidoMaterno.setElemento(currentItem.getaMaterno(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
				nombre.setElemento(currentItem.getNombre(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
			}
			
		}
		
		*/
		
		regPatron.agregarAResourceData(reXLS.getResourceData());
		id_trabajador.agregarAResourceData(reXLS.getResourceData());
		nss.agregarAResourceData(reXLS.getResourceData());
		apellidoPaterno.agregarAResourceData(reXLS.getResourceData());
		apellidoMaterno.agregarAResourceData(reXLS.getResourceData());
		nombre.agregarAResourceData(reXLS.getResourceData());
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
