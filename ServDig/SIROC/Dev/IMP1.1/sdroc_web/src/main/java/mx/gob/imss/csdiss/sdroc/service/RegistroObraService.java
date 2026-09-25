/**
 *  Copyright (c)  IMSS - Instituto Mexicano del Seguro Social. Todos los derechos reservados
 *  
 *  @version 1.0
 *  @author 043h68
 */
package mx.gob.imss.csdiss.sdroc.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import mx.gob.imss.csdiss.sdroc.dto.*;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;

/**
 * @author daniel.hernandez
 * 
 */
public interface RegistroObraService {

	/**
	 * Metodo para el registro de la obra de construccion
	 * 
	 * @param datosRegistroObra
	 * @param datosRegistroAviso
	 * @param pathRegistroObraAcuse
	 * @param pathImg
	 * @return String : nombre del reporte generado
	 */
	HashMap<String, Object> registrarObra(InformacionObraDTO datosRegistroObra,
			AvisoObraDTO datosRegistroAviso, String pathRegistroObraAcuse,
			String pathImg, FirmaElectronica firmaElectronica);
	
	byte[] generaAcuseRegistroObra(InformacionObraDTO informacionObraDTO, String pathRegistroObraAcuse, String pathImg, Date date, Boolean generarCadena);
	byte[] generaAcuseAvisoObra(AvisoObraDTO avisoObraDTO, String pathRegistroObraAcuse, String pathImg, Date date, Boolean generarCadena);
//	byte[] registrarObra(InformacionObraDTO datosRegistroObra, AvisoObraDTO datosRegistroAviso,
//			String pathRegistroObraAcuse, String pathImg, FirmaElectronica firmaElectronica);

	/**
	 * Metodo para la consulta del catalogo de tipos de obra.
	 * 
	 * @return lista con los tipos de obra.
	 */
	List<TipoObraDTO> consultarTiposObra(String idTipoObra);

	/**
	 * Consulta todas las obras registradas al RFC y Registro patronnal que se
	 * proporcione.
	 * 
	 * @param rfc
	 * @param regPatronal
	 * @return Lista de obras. Iterable<ObraDTO>
	 * @throws IOException
	 */
	Object consultarObrasPorRFCyRP(String rfc, String regPatronal)
			throws IOException;

	/**
	 * @param rfc
	 * @return
	 * @throws IOException
	 */
	Object consultarObrasPorRFC(String rfc) throws IOException;

	/**
	 * @param cveRegPatronal
	 * @return
	 */
	Object consultaObrasRegistradasPorRegistroPatronal(String cveRegPatronal);

	/**
	 * @param cveRegistroObra
	 * @return
	 */
	Object consultaRegObrasPorNRO(String cveRegistroObra);

	/**
	 * @return
	 */
	Object consultaReporteAvisosUbicacion(String cveRfc);

	// List<TipoObraDTO> obtenerTipoObra() throws IOException;

	/**
	 * @param idTipoIncidencia
	 * @return
	 * @throws IOException
	 */
	List<MotivoDTO> obtenerMotivosPorTipoIncidencia(String idTipoIncidencia)
			throws IOException;

	List<ObjetoContratoDTO> consultarObjetoContrato();

	InformacionObraDTO obtenerInformacionObraPorNumRegObra(
			String numRegistroObra);

	/**
	 * @param cveInformacionObra
	 * @return
	 */
	Object consultaBimestrePorNRO(Long cveInformacionObra);

	/**
	 * @param cveInformacionObra
	 * @return
	 */
	List<InformacionIncidenciaDTO> consultaIncidentesPorNRO(Long cveInformacionObra);

	
	/**
	 * @param informacionObra
	 * @param pathResumenObra
	 * @param pathImg
	 * @return
	 */
	byte[] generaResumenObra(InformacionObraDTO informacionObra, String pathResumenObra, String pathImg);

	/**
	 * 
	 * @param cveRegPatronal
	 * @param rutaPlantilla
	 * @param nombreReporte
	 * @param nombreSubReporte
	 * @param httpSession
	 * @return
	 */
	byte[] exportarExcelRegistrosPatronalesPorRP(String cveRegPatronal, String rutaPlantilla, String nombreReporte, String nombreSubReporte, HttpSession httpSession);
	
	/**
	 * 
	 * @param rfc
	 * @param anio
	 * @param rutaPlantilla
	 * @param nombreReporte
	 * @param nombreSubReporte
	 * @param session
	 * @return
	 */
	byte[] exportarExcelRegistroGeneralObraPorRFCAnio(String rfc, String anio, String rutaPlantilla, String nombreReporte, String nombreSubReporte, HttpSession session);
	
	/**
	 * 
	 * @param rfc
	 * @param rutaPlantilla
	 * @param nombreReporte
	 * @param nombreSubReporte
	 * @param session
	 * @return
	 */
	byte[] exportarExcelAvisoUbicacionObra(String rutaPlantilla, String nombreReporte, String nombreSubReporte, HttpSession session);
	
	/**
	 * 
	 * @param cvRP
	 * @param codigoPostal
	 * @return
	 */
	Boolean validaCpUbicacionObra(String cvRP, String codigoPostal);
	
	Boolean validCircunscripcion(String codigoPostal, Long idDelegacion, Long idSubdelegacion);
	/**
	 * 
	 * @param cveRegistroObra
	 * @return
	 */
	InformacionObraDTO obtenerInformacionObraPorCveInformacionObra(
			Long cveRegistroObra);

	/**
	 * 
	 * @param cveInformacionObra
	 * @return
	 */
	InformacionIncidenciaDTO obtenerInformacionIncidenciaPorCveObra(
			Long cveInformacionObra);

	/**
	 * 
	 * @param cveRP
	 * @param rutaPlantilla
	 * @param nombreReporte
	 * @param nombreSubReporte
	 * @param session
	 * @return
	 */
	byte[] exportarExcelSubcontratos(String cveRP, String rutaPlantilla, String nombreReporte, String nombreSubReporte,
			HttpSession session, String cveRPContratante);

	
	/**
	 * @param cveInformacionObra
	 * @return
	 */
	InformacionIncidenciaDTO consultaBimRepCveInfoObra(Long cveInformacionObra);

	/**
	 * 
	 * @param codigoPostal
	 * @return
	 */
	List<SubDelegacionDTO> obtenerSubDelegacionesPorCodigoPostal(String codigoPostal);

	/**
	 * 
	 * @param cveInformacionObra
	 * @return
	 */
	List<InformacionIncidenciaDTO> consultaIncidenciasPorCveInformacionObra(String cveInformacionObra);
	
	/**
	 * Consulta el bimestre que esta pendiente por presentar.
	 * @param numRegistroObra
	 * @return bimestre
	 */
	String consultarReporteBimestralPresentar(Long cveInformacionObra);

	/**
	 * Consulta los avisos de obra registrados y filtra por la cveAvisoObra
	 * @param numRegObra
	 * @return
	 */
	AvisoObraDTO consultaAvisoUbicacionObra(String numRegObra, String rfc, String registroPatronal);
	
	AvisoObraDTO consultaAvisoUbicacionObra(String numRegObra);

	void actualizarAvisoObra(Long cveAvisoObra);
	
	void actualizaObra(InformacionObraDTO informacionObraDTO);

}
