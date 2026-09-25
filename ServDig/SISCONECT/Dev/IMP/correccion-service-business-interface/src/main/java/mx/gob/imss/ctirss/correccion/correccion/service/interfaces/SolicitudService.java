package mx.gob.imss.ctirss.correccion.correccion.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.TipoCorreccion;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
/**
 * 
 * @author mnieto
 *
 * @param <T>
 */
public interface SolicitudService <T extends AbstractModel>{
	
	public DatosSalidaPaginador<T> pagina(List params);
 
	public DatosSalidaPaginador<T> paginaSolicitudes(List params);

	public List addPatron(List patrones,SatPatron patron);

	public List delPatron(List patrones,String patron);

	public List consultaSolicitudesPendientes(Long codigoDelegacion,Long codigoSubDelegacion,Long idSubdelegacion);

	public CrtSolicitudcorr guardar(CrtSolicitudcorr solicitud);
	
	public CrtSolicitudcorr actualizar(CrtSolicitudcorr solicitud);
	
	public CrtSolicitudcorr consultarPatrones(CrtSolicitudcorr patrones);
	
	public boolean validaSolicitud(CrtSolicitudcorr solicitud);
	
	public List validaSolicitudResult(CrtSolicitudcorr solicitud);

	public CrtSolicitudcorr consultarFolio(CrtSolicitudcorr solicitud);	
	
	public CrtSolicitudcorr consultarFolioRegPat(CrtSolicitudcorr solicitud);

	public CrtSolicitudcorr consultarPorId(Integer cveSolicitud);
	
	public List consultarSolicitudesById(Integer integer);
	
	public List consultarSolicitudesById(Integer integer,Long idSubdelegacion);
	
	public List<CrtAnexosolcorrpat> consultarAnexoSolicitudes(Integer claveSolicitud, Long cveEjercicio);
	

	public List<CrtAnexosolcorrpat> consultarAnexos(Integer claveSolicitud,String tipoPatron);
	
	public void autorizarSolicitudes();
	
	public CrtSolicitudcorr getSolicitudDetalles(CrtSolicitudcorr solicitud, String tipoPatron);
	
	public CrtSolicitudcorr consultaPorFolioAuditorAsignado(CrtSolicitudcorr solicitud);
	
	public List<CrtAnexosolcorrpat> consultarAnexoSolicitudesReport(Integer claveSolicitud);
	
	public String generaFolioTemporal(CrtSolicitudcorr solicitud,TipoCorreccion tipoCorreccion);
	

}
