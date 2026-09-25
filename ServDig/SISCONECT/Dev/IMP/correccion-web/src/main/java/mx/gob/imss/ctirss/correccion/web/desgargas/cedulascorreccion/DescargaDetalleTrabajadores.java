package mx.gob.imss.ctirss.correccion.web.desgargas.cedulascorreccion;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import mx.gob.imss.common.utils.readAndExportXLS.process.ReadAndExportXLS;
//import mx.gob.imss.common.utils.readAndExportXLS.vo.Catalog;
import mx.gob.imss.common.utils.readerXLS.proces.ReadAndExportXLS;
import mx.gob.imss.xls.loader.vo.Catalog;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcGrupoCategoria;
import mx.gob.imss.ctirss.correccion.constantes.CedulasCorreccion;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.utils.Functions;



public class DescargaDetalleTrabajadores extends AbstractCedulasCorreccion  implements ICedulasCorreccion{

	/**
	 * contiene datos para el encabezado requeridos para la cedula Trabajadores
	 */
	private CrtSolicitudcorr crtSolicitudcorr;
	
	/**
	 * contiene datos para el encabezado requeridos para la cedula Trabajadores
	 */
	private SatPatron patron;
	
	/**
	 * Trabajadores requeridos para la cedula O
	 */
	private List<CrcTrabajadores> lsTrabajadores;
	
	
	private List<CrcGrupoCategoria> categorias;
	
	
	private Catalog id_trabajador;
	
	
	private Catalog id_categoria;
	
	
	private Catalog descripcion_categoria;
	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargados los nombres de los trabajadores
	 */
	private Catalog nss;
	
	/**
	 * Permite controlar la psicion y hoja en donde seran
	 * cargados los nombres de los trabajadores
	 */
	private Catalog nombre;
	
	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargados los rfc de los trabajadores
	 */
	private Catalog rfc;
	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargados los ejercicios de los registros Patronales
	 */
	private Catalog ejercicio;
	
	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargados los id de los registros Patronales
	 */
	private Catalog idRegistroPatronal;
	
	
	
	
	

	public DescargaDetalleTrabajadores(HttpServletRequest request,HttpServletResponse response, 
			Integer idCedula,List<CrtAnexosolcorrpat> lstAnexoSol, SatPatron patronPrincipal, 
			CrtSolicitudcorr solicitud, List<CrcTrabajadores>  empleados, List<CrcGrupoCategoria> categorias, String folioCorreccion, String periodoCorreccion ) throws Exception{
		this.idCedula = validaIdCedula(idCedula);
		this.request = validaRequest(request);
		this.response = validaResponse(response);
		this.crtSolicitudcorr = solicitud;
		this.patron = patronPrincipal;
		this.lsRegistrosPatronales = lstAnexoSol;
		this.lsTrabajadores = empleados;
		this.categorias=categorias;
		this.foliCorreccion=folioCorreccion;
		this.periodoCorreccion=periodoCorreccion;
		
		reXLS = new ReadAndExportXLS(super.getRealBasePath()+
				CedulasCorreccion.RUTA_CEDULAS + CedulasCorreccion.CEDULA_DETALLE_TRABAJADORES);
		
		cargaSeguridadArchivo();
		cargaEncabezado();
		cargaRegistrosPatronales();
		cargaTrabajadores();
		cargaCategorias();
	}

	
	public void cargaEncabezado() {
		// TODO Auto-generated method stub
		String delegacion = "DELEGACION";
		String subDelegacion = "DELEGACION";
		String rp="RP";
		if(patron.getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().getNomNombre()!=null){
			delegacion = patron.getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().getNomNombre();
		}
		
		if(patron.getUbicacion().getMunicipio().getSacSubdelegacion().getNomNombre()!=null){
			subDelegacion = patron.getUbicacion().getMunicipio().getSacSubdelegacion().getNomNombre();
		}
		
		encabezado = new Catalog(3,"L");
		encabezado.setElemento(delegacion, CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(subDelegacion,  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(crtSolicitudcorr.getRazonSocialPatronPrin(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(crtSolicitudcorr.getNuFolio(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(crtSolicitudcorr.getPeriodo().toString(), CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(Functions.dateToString(crtSolicitudcorr.getFecFechaPeriodoIni()).replaceAll("-", "/"), CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(Functions.dateToString(crtSolicitudcorr.getFecFechaPeriodoFin()).replaceAll("-", "/"),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
//		System.out.println("Lista "+lsRegistrosPatronales.size());
//		for(Object anexo:lsRegistrosPatronales){
//				
//					CrtAnexosolcorrpat regPatronal=(CrtAnexosolcorrpat) anexo;
//					System.out.println("Val "+regPatronal.getCveAnexoSolicitudCorrPat());
//					if(regPatronal.getTipoPatron().equals("F")){
//						System.out.println("Registro Padre Fiscal "+regPatronal.getRegistroPatronal());
//						rp=regPatronal.getRegistroPatronal();
//						break;
//					}
//				}
		
		encabezado.setElemento(this.patron.getRegistroPatronal(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		
		encabezado.agregarAResourceData(reXLS.getResourceData());
		
	}


	public void cargaRegistrosPatronales() {
		// TODO Auto-generated method stub
		registrosPatronales = new Catalog(1, "AB");
		ejercicio = new Catalog(1, "AC");
		idRegistroPatronal = new Catalog(1, "AD");
		Catalog registrosPatronalesUniq = new Catalog(1, "AF");
		
		String allRPs = "";
		if(lsRegistrosPatronales!=null && !lsRegistrosPatronales.isEmpty()){
			Iterator<CrtAnexosolcorrpat> iter =  (Iterator<CrtAnexosolcorrpat>) lsRegistrosPatronales.iterator();
			CrtAnexosolcorrpat currentItem = null;

			while(iter.hasNext()){
				currentItem = iter.next();

				if(currentItem.getEjerciciosSolicitud()!=null && !currentItem.getEjerciciosSolicitud().isEmpty()){
					Iterator<CrcEjercicio> iterEjercicio =  (Iterator<CrcEjercicio>) currentItem.getEjerciciosSolicitud().iterator();
					CrcEjercicio currentEjercicio = null;

					while(iterEjercicio.hasNext()){
						currentEjercicio = iterEjercicio.next();
						registrosPatronales.setElemento(currentItem.getRegistroPatronal(),  CedulasCorreccion.DT_HOJA_CONTROL);
						ejercicio.setElemento(currentEjercicio.getCveEjercicio().toString(),  CedulasCorreccion.DT_HOJA_CONTROL);
						idRegistroPatronal.setElemento(currentEjercicio.getCveAcexoCorrPat().toString(), CedulasCorreccion.DT_HOJA_CONTROL);
												
					}
				}
				
				registrosPatronalesUniq.setElemento(currentItem.getRegistroPatronal(),  CedulasCorreccion.DT_HOJA_CONTROL);
			}

		}

		registrosPatronales.agregarAResourceData(reXLS.getResourceData());
		ejercicio.agregarAResourceData(reXLS.getResourceData());
		idRegistroPatronal.agregarAResourceData(reXLS.getResourceData());
		registrosPatronalesUniq.agregarAResourceData(reXLS.getResourceData());

	}

	
	private String getNombreCompleto(String nombre, String apPat, String apMat){
		
		return (nombre!=null ? nombre :"") +" "+(apPat!=null ? apPat:"" )+" "+ (apMat!=null ? apMat :"");
	}

	/**
	 * Permite ingresar las percepciones al XLS
	 */
	private void cargaTrabajadores(){

		id_trabajador = new Catalog(1, "S");
		nombre= new Catalog(1, "T");
		nss = new Catalog(1, "U");
		rfc = new Catalog(1, "V");
		
		


		if(lsTrabajadores!=null && !lsTrabajadores.isEmpty()){
			Iterator<CrcTrabajadores> iter =  (Iterator<CrcTrabajadores>) lsTrabajadores.iterator();
			CrcTrabajadores currentItem = null;

			while(iter.hasNext()){				
				currentItem = iter.next();			

				id_trabajador.setElemento(currentItem.getCveTrabajador().toString(),  CedulasCorreccion.DT_HOJA_CONTROL);
				nss.setElemento(currentItem.getNuNss(),CedulasCorreccion.DT_HOJA_CONTROL);
				rfc.setElemento(currentItem.getTxRfc(),CedulasCorreccion.DT_HOJA_CONTROL);
				nombre.setElemento(getNombreCompleto(currentItem.getNombreAsegurado(),
													 currentItem.getApPaternoAsegurado(),
													 currentItem.getApMaternoAsegurado()),CedulasCorreccion.DT_HOJA_CONTROL);
						
						
				
				
			}

		}


		id_trabajador.agregarAResourceData(reXLS.getResourceData());
		nss.agregarAResourceData(reXLS.getResourceData());
		rfc.agregarAResourceData(reXLS.getResourceData());
		nombre.agregarAResourceData(reXLS.getResourceData());
		
	

	}


	private void cargaCategorias(){
		
		
		
		id_categoria = new Catalog(1, "X");
		descripcion_categoria=new Catalog(1, "W");

		if(categorias!=null && !categorias.isEmpty()){
			Iterator<CrcGrupoCategoria> iter =  (Iterator<CrcGrupoCategoria>) categorias.iterator();
			CrcGrupoCategoria currentItem = null;

			while(iter.hasNext()){				
				currentItem = iter.next();
				id_categoria.setElemento(currentItem.getCveGrupoCategoria().toString(),  CedulasCorreccion.DT_HOJA_CONTROL);
				descripcion_categoria.setElemento(currentItem.getTxGrupoCategoria(),  CedulasCorreccion.DT_HOJA_CONTROL);
			}

		}
		id_categoria.agregarAResourceData(reXLS.getResourceData());
		descripcion_categoria.agregarAResourceData(reXLS.getResourceData());

	}
	
	
	public void cargaSeguridadArchivo() {

		seguridadArchivo = new Catalog(CedulasCorreccion.CELDA_CONTROL_SEGURIDAD_FILAL, CedulasCorreccion.CELDA_CONTROL_SEGURIDAD_COLUMNA);
		seguridadArchivo.setElemento(crtSolicitudcorr.getNuFolio(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		seguridadArchivo.setElemento(idCedula.toString(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		seguridadArchivo.agregarAResourceData(reXLS.getResourceData());
		
		
		nombreArchivo = new Catalog(CedulasCorreccion.CELDA_CONTROL_SEGURIDAD_FILAL, "B");
		nombreArchivo.setElemento(this.getFoliCorreccion().replace("/", "_")+"-"+this.getPeriodoCorreccion(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		nombreArchivo.agregarAResourceData(reXLS.getResourceData());
		
		
		
	}


	public Catalog getNombreArchivo() {
		return nombreArchivo;
	}


	public void setNombreArchivo(Catalog nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}


	public List<CrcGrupoCategoria> getCategorias() {
		return categorias;
	}


	public void setCategorias(List<CrcGrupoCategoria> categorias) {
		this.categorias = categorias;
	}

	
	
}
