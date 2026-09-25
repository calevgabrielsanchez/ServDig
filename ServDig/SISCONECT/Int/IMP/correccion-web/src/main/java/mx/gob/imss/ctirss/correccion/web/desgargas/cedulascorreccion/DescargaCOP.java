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
import mx.gob.imss.ctirss.correccion.constantes.CedulasCorreccion;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.utils.Functions;



public class DescargaCOP extends AbstractCedulasCorreccion  implements ICedulasCorreccion{

	/**
	 * contiene datos para el encabezado requeridos para la cedula Trabajadores
	 */
	private CrtSolicitudcorr crtSolicitudcorr;
	
	/**
	 * contiene datos para el encabezado requeridos para la cedula Trabajadores
	 */
	private SatPatron patron;
	
	private CrtAnexosolcorrpat patronFiscal;
	/**
	 * Permite controlar la posicion y hoja en donde seran
	 * cargados los id de los registros Patronales
	 */
	private Catalog idRegistroPatronal;
	
	

	public DescargaCOP(HttpServletRequest request,HttpServletResponse response, 
			Integer idCedula,List<CrtAnexosolcorrpat> lstAnexoSol, SatPatron patronPrincipal, 
			CrtSolicitudcorr solicitud, String folioCorrecion, String periodoCorreccion ,CrtAnexosolcorrpat patronFiscal) throws Exception{
		this.idCedula = validaIdCedula(idCedula);
		this.request = validaRequest(request);
		this.response = validaResponse(response);
		this.crtSolicitudcorr = solicitud;
		this.patron = patronPrincipal;
		this.lsRegistrosPatronales = lstAnexoSol;
		this.foliCorreccion=folioCorrecion;
		this.periodoCorreccion=periodoCorreccion;
		this.patronFiscal=patronFiscal;
		
		
		reXLS = new ReadAndExportXLS(super.getRealBasePath() +
				CedulasCorreccion.RUTA_CEDULAS + CedulasCorreccion.CEDULA_COP_PAGADA);
		
		cargaSeguridadArchivo();
		cargaEncabezado();
		cargaRegistrosPatronales();
	}

	
	public void cargaEncabezado() {

		String delegacion = "DELEGACION";
		String subDelegacion = "DELEGACION";
		
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
		
		
		
		encabezado.setElemento(patronFiscal.getRegistroPatronal(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		encabezado.setElemento(Functions.dateToString(crtSolicitudcorr.getFecFechaElacoracionCorreccion()).replaceAll("-", "/"),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		
		encabezado.agregarAResourceData(reXLS.getResourceData());
		
	}


	public void cargaRegistrosPatronales() {

		registrosPatronales = new Catalog(1, "AB");
		idRegistroPatronal = new Catalog(1, "AA");
				
		
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
						registrosPatronales.setElemento(currentItem.getRegistroPatronal(),  CedulasCorreccion.COP_HOJA_CONTROL);
						idRegistroPatronal.setElemento(currentEjercicio.getCveAcexoCorrPat().toString(), CedulasCorreccion.COP_HOJA_CONTROL);
												
					}
				}
				
			}

		}

		registrosPatronales.agregarAResourceData(reXLS.getResourceData());
		idRegistroPatronal.agregarAResourceData(reXLS.getResourceData());
		
	}

	
	
	public void cargaSeguridadArchivo() {

		seguridadArchivo = new Catalog(CedulasCorreccion.CELDA_CONTROL_SEGURIDAD_FILAL, CedulasCorreccion.CELDA_CONTROL_SEGURIDAD_COLUMNA);
		seguridadArchivo.setElemento(crtSolicitudcorr.getNuFolio(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		seguridadArchivo.setElemento(idCedula.toString(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		seguridadArchivo.agregarAResourceData(reXLS.getResourceData());
		
		
		nombreArchivo = new Catalog(CedulasCorreccion.CELDA_CONTROL_SEGURIDAD_FILAL, "Z");
		nombreArchivo.setElemento(this.getFoliCorreccion().replace("/", "_")+"-"+this.getPeriodoCorreccion(),  CedulasCorreccion.CONTROL_SHEET_CEDULA);
		nombreArchivo.agregarAResourceData(reXLS.getResourceData());
	}

}
