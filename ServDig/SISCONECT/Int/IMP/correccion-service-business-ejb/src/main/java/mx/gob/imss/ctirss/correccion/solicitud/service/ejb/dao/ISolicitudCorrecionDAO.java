package mx.gob.imss.ctirss.correccion.solicitud.service.ejb.dao;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;

public interface ISolicitudCorrecionDAO<T extends AbstractModel> {
	
	/**
	 * Metodo para agregar un elemento al catalogo.
	 * @param model
	 * @return
	 */
	public CrtSolicitudcorr save(CrtSolicitudcorr model);
	public CrtSolicitudcorr update(CrtSolicitudcorr model);
	/**
	 * Metodo para obtener todas los anexosSol de las solicitudes de un folio de correccion por
	 * periodo pero sin tener ejercicios
	 */ 
	public List<CrtAnexosolcorrpat> consultarAnexoSolicitudes(Integer clave, Integer periodo,CrtSolicitudcorr patrones);
	public T consultaPorFolio(T filtro);

	public T consultaPorFolioRegPat(T filtro);
	
	
	
	public List consultarSolicitudesPendientes(Long codigoDel, Long codigoSubDel, Long idSubdelegacion);
	
	public DatosSalidaPaginador<CrtSolicitudcorr> paginarSolicitudesPendientes(DatosEntradaPaginador<CrtSolicitudcorr> input, Long idSubdelegacion);
	
	public int obtenConsecutivoFolio(String idDelegacion,String idSubdelegacion, int anio);
	
	public boolean validaSolicitud(CrtSolicitudcorr solicitud);

	public List validaSolicitudResult(CrtSolicitudcorr solicitud);

	public List consultarSolicitudesById(Integer id);
	
	public List consultarSolicitudesById(Integer id,Long idSubdelegacion);
	/**
	 * Metodo para obtener todas los anexosSol de las solicitudes de un folio de correccion y
	 * ademas los anexosSol tienen un arreglo con todos los ejercicios(CrcEjercicio)
	 * Se puede filtrar por cveEjercio o mandar la cveEjercicio nula para obtener todas las anexoSol
	 */
	public List<CrtAnexosolcorrpat> consultarAnexoSolicitudes(Integer claveSolicitud, Long cveEjercicio);
	
	public T consultaPorClave(T model);
	
	public List getSolicitudDetalles(CrtSolicitudcorr solicitud, String tipoPatron);
	
	public T consultaPorFolioAuditorAsignado(T filtro);
	
	public List<CrtAnexosolcorrpat> consultarAnexoSolicitudesReport(Integer claveSolicitud);
	
}
