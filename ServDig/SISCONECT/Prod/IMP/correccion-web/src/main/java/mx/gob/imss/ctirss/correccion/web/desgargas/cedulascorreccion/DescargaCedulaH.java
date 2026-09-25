package mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
//import mx.gob.imss.common.utils.readAndExportXLS.process.ReadAndExportXLS;
//import mx.gob.imss.common.utils.readAndExportXLS.vo.Catalog;
import mx.gob.imss.common.utils.readerXLS.proces.ReadAndExportXLS;
import mx.gob.imss.xls.loader.vo.Catalog;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcGrupoCategoria;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcMes;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtEjertrabajador;
import mx.gob.imss.ctirss.correccion.constantes.CedulasCorreccion; 
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;

import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
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
public class DescargaCedulaH extends AbstractCedulasCorreccion implements ICedulasCorreccion {

	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargadas las percepciones asignadas.
	 */
	private Catalog percepciones;
	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargadas las nss de los trabajadores
	 */
	private Catalog nss;
	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargados los nombres de los trabajadores
	 */
	private Catalog nombre;
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
	private Catalog rfc;
	/**
	 * Permite controlar la psicion y hoja en donde seran
	 * cargados la antiguedad de los trabajadores
	 */
	private Catalog antiguedad;
	/**
	 * Permite controlar la psicion y hoja en donde seran
	 * cargadas las categorias paternos de los trabajadores
	 */
	private Catalog categoria;
	
	/**
	 * Permite controlar la psicion y hoja en donde seran
	 * cargados las patrones asociados a los trabajadores
	 */
	private Catalog regPatron;
	/**
	 * Permite asociar a cada trabajador con su respectiva
	 * clave
	 */
	private Catalog cveTrabajador;
	
	/**
	 * Percepciones/Conceptos requeridos para la cedula H
	 */
	private List<CrcPercepciones> lsPercepciones;
	
	/**
	 * Trabajadores requeridos para la cedula H
	 */
	private List<CrcTrabajadores> lsTrabajadores;
	
	
	private List<AbstractModel> categorias;
	
	
	/**
	 * contiene datos para el encabezado requeridos para la cedula H
	 */
	private CrtSolicitudcorr crtSolicitudcorr;
	
	/**
	 * Meses requeridos para la cedula H
	 */
	private List<CrcMes> lsMeses;
	/**
	 * Permite cargar los meses en la cedula H
	 */
	private Catalog mes;
	
	/**
	 * Permite cargar los id de los meses 1 enero 12 diciembre
	 */
	
	private Catalog idMes;
	
	
	private HashMap<Integer,String> categoriasDes;
	
	
	private CrtAnexosolcorrpat patronFiscal;
	/**
	 * 
	 * Inicializa la Cedula H
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
	public DescargaCedulaH(HttpServletRequest request,HttpServletResponse response,
							Integer idFolioCorreccion,Integer idCedula,
							List<CrcPercepciones> lsPercepciones, List<CrcTrabajadores> lsTrabajadores, 
							List<?> lsRegistrosPatronales,CrtSolicitudcorr crtSolicitudcorr,  List<CrcMes> lsMeses, String folioCorreccion, String periodoCorreccion,List<AbstractModel> categorias,CrtAnexosolcorrpat patronFiscal) throws Exception{
		
		this.request = validaRequest(request);
		this.response = validaResponse(response);
		this.idCedula = validaIdCedula(idCedula);
		this.lsPercepciones = lsPercepciones;
		this.lsTrabajadores = lsTrabajadores;
		this.lsRegistrosPatronales = lsRegistrosPatronales;
		this.crtSolicitudcorr = crtSolicitudcorr;
		this.lsMeses = lsMeses;
		this.categorias=categorias;
		this.patronFiscal=patronFiscal;
		this.foliCorreccion=folioCorreccion;
		this.periodoCorreccion=periodoCorreccion;
		reXLS = new ReadAndExportXLS(super.getRealBasePath()+
				CedulasCorreccion.RUTA_CEDULAS + CedulasCorreccion.CEDULA_H);
		recuperaCategoria();
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
		cveTrabajador = new Catalog(3, "P");
		nss = new Catalog(3, "Q");
		apellidoPaterno= new Catalog(3, "R");
		apellidoMaterno= new Catalog(3, "S");
		nombre= new Catalog(3, "T");
		rfc= new Catalog(3, "U");
		antiguedad= new Catalog(3, "V");
		categoria= new Catalog(3, "W");	
		mes = new Catalog(3, "X");
		idMes = new Catalog(3,"Y");
		
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
					
						if(lsMeses!=null && !lsMeses.isEmpty()){
							Iterator<CrcMes> iterMes =  (Iterator<CrcMes>) lsMeses.iterator();
							CrcMes currentItemMes = null;

							while(iterMes.hasNext()){
								currentItemMes = iterMes.next();
								
								regPatron.setElemento(currentItem.getRegistroPatronal(), CedulasCorreccion.CONTROL_SHEET_CEDULA);
								cveTrabajador.setElemento(currentItemTrabajador.getCveEjertrab().toString(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
								nss.setElemento(currentItemTrabajador.getCrcTrabajadores().getNuNss(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
								apellidoPaterno.setElemento(currentItemTrabajador.getCrcTrabajadores().getApPaternoAsegurado(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
								apellidoMaterno.setElemento(currentItemTrabajador.getCrcTrabajadores().getApMaternoAsegurado(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
								nombre.setElemento(currentItemTrabajador.getCrcTrabajadores().getNombreAsegurado(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
								rfc.setElemento(currentItemTrabajador.getCrcTrabajadores().getTxRfc(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
								antiguedad.setElemento(currentItemTrabajador.getNuAntiguedadAnios().toString(),CedulasCorreccion.CONTROL_SHEET_CEDULA);
								categoria.setElemento(getDesCategoria(currentItemTrabajador.getCveCategoria().intValue()),CedulasCorreccion.CONTROL_SHEET_CEDULA);
								mes.setElemento(currentItemMes.getTxMes().toUpperCase(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
								idMes.setElemento(currentItemMes.getCveMes().toString(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
							} // Fin while Meses
						} // Fin if lsMeses
							
					}// trabajadores
					
				}
			
				
			}
			
		}
		
	
		regPatron.agregarAResourceData(reXLS.getResourceData());
		cveTrabajador.agregarAResourceData(reXLS.getResourceData());
		nss.agregarAResourceData(reXLS.getResourceData());
		apellidoPaterno.agregarAResourceData(reXLS.getResourceData());
		apellidoMaterno.agregarAResourceData(reXLS.getResourceData());
		nombre.agregarAResourceData(reXLS.getResourceData());
		rfc.agregarAResourceData(reXLS.getResourceData());
		antiguedad.agregarAResourceData(reXLS.getResourceData());
		categoria.agregarAResourceData(reXLS.getResourceData());
		mes.agregarAResourceData(reXLS.getResourceData());
		idMes.agregarAResourceData(reXLS.getResourceData());
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
	
	
	
	public void recuperaCategoria(){
		HashMap<Integer,String> cves=new HashMap<Integer,String>();
		for(AbstractModel crc:this.categorias){			
			CrcGrupoCategoria a=(CrcGrupoCategoria) crc;
			cves.put(a.getCveGrupoCategoria(),a.getTxGrupoCategoria());			
		}
		this.categoriasDes=cves;		
	}
	
	public String getDesCategoria(int cve){
		return this.categoriasDes.get(cve);
		
	}
	
	
}
