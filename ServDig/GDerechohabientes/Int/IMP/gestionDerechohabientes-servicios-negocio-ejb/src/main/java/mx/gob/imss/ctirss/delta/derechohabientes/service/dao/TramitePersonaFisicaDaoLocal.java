/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav005DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav007DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav017DTO;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

/**
 * @author ghdolores
 *
 */
@Local 
public interface TramitePersonaFisicaDaoLocal {
	public Sav007DTO getUltimoTramiteSAV007(AsignacionNSS nss, Long idPersona, List<Long> tiposTramite) throws DerechohabientesBusinessException, Exception;
	public Sav005DTO getUltimoTramiteSAV005(AsignacionNSS nss, Long idPersona, List<Long> tiposTramite, Long idEstadoTramite, Long idOrigenSolicitud) throws DerechohabientesBusinessException, Exception;
	public Sav017DTO getUltimoTramiteSAV017(AsignacionNSS nss, Long idPersona, List<Long> tiposTramite) throws DerechohabientesBusinessException, Exception;
	public List<Tramite> getTramitesPersonas(List<Long> personas, List<Long> estados, Long idPrsonaAsegurado) throws DerechohabientesBusinessException, Exception;
	public List<Tramite> getTramitePersona(List<Long> idPersona ,List<Long> tipoTramite,List<Long> estadoTramite,Long indResultado,Long razonResultado, Long idPersonaInt, Boolean enLista) throws Exception;
	public Tramite getUltimoTramiteAbierto(Long idPersona , Long idPersonaAseguradoPensionado) throws Exception;
	public List<Tramite> getTramitesAbierto(List<Long> idPersonas , Long idPersonaAseguradoPensionado) throws Exception;
	Tramite saveTramite(Tramite tramite) throws DerechohabientesBusinessException, Exception;
	public Tramite saveTramiteInternet (Tramite tramite) throws DerechohabientesBusinessException, Exception;
	void otroSaveTramite(Tramite tramite) throws DerechohabientesBusinessException, Exception;
	void updateTramite(Tramite tramite) throws DerechohabientesBusinessException, Exception;
	void saveCorreccionDerechohabiente(TramiteCorreccionDerechohabiente correccion) throws DerechohabientesBusinessException, Exception;
	void updateCorreccionDerechohabiente(TramiteCorreccionDerechohabiente correccion) throws DerechohabientesBusinessException, Exception;
	void saveCircunscripcionForanea(TramiteCircunscripcionForanea circunscripcion) throws DerechohabientesBusinessException, Exception;
	void updateCircunscripcionForanea(TramiteCircunscripcionForanea circunscripcion) throws DerechohabientesBusinessException, Exception;
	TramiteRegistroDerechohabiente getTramiteRegistro(Long idTramite) throws DerechohabientesBusinessException, Exception;
	TramiteCircunscripcionForanea getCircunscripcionForanea(Long idTramite) throws DerechohabientesBusinessException, Exception;
	TramiteCircunscripcionForanea getSuspencionCircunscripcionForane(Long idTramiteSuspencion) throws DerechohabientesBusinessException, Exception;
	TramiteCircunscripcionForanea getCircunscripcionForanea(Long idPersona, AsignacionNSS nss, boolean autorizacion) throws DerechohabientesBusinessException, Exception;
	TramiteCorreccionDerechohabiente getTramiteCorreccion(Long idTramite) throws DerechohabientesBusinessException, Exception;
	TramiteProrroga getTramiteProrroga(Long idTramite) throws DerechohabientesBusinessException, Exception;
	Tramite getUltimoTramitePersona(Long idPersona,List<Long> tiposTramite) throws DerechohabientesBusinessException, Exception;
	List<Tramite>  getTramitaByEstado(Long idPersona,Long idTipoTramite) throws DerechohabientesBusinessException, Exception;
	Tramite getTramite(Long idTramite) throws DerechohabientesBusinessException, Exception;
	public Tramite getTramiteInternet(Long idTramite) throws DerechohabientesBusinessException, Exception;
	Object getDocumentoProbatorioProrroga(Long idTramite,long tipoTramite) throws Exception;
	public Tramite saveTramitePersonaFisica (Tramite tramite) throws DerechohabientesBusinessException, Exception;
	public DitTramitePersonaFisica getUltimoTramiteByEstado(Long idPersona,
			List<Long> tiposTramite, Long estado) throws Exception;
	public Tramite saveDetalleTramite(Long cveTramite, String detalleTramiteXML) throws Exception;
	public void actualizaDetalleTramite(Long idTramite, String detalleTramiteXML) throws Exception;
	public boolean existeTramitePersonaF (Tramite tramite) throws DerechohabientesBusinessException, Exception;
	public List<Tramite> findTramitesXPersona(Long idPersona ,List<Long> estadoTramite) throws DerechohabientesBusinessException, Exception;
	/**
	 * Regresa la descripción del tipo de tramite desde el diccionario de tipos de tramite.
	 * @param idTramite
	 * @return String (dicTipoTramite)
	 * @throws Exception 
	 */
	public String getDescripcionTipoTramiteFromDic(Long idTipoTramite) throws Exception;
}
