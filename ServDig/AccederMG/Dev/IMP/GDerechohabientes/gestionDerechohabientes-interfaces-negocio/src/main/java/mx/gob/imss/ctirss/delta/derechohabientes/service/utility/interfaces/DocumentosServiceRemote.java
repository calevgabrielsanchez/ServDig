/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ComprobanteVigenciaDerechosDTO;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;

/**
 * @author ghdolores
 * 
 */
@Remote
public interface DocumentosServiceRemote {
    public Object getDocumentoCambioClinica(String folioSolicitud, String titulo, FirmaElectronica fe, TramiteCorreccionDerechohabiente tramite) throws DerechohabientesBusinessException;
	Object getDocumentoAcuseDeRecibo(String folioSolicitud, String titulo, FirmaElectronica fe, GrupoFamiliar gf)throws DerechohabientesBusinessException;
	Object getDocumentoSav002(AsignacionNSS nss,Long idSolicitud,String titulo) throws DerechohabientesBusinessException, Exception;
	public Object getRechazoSolicitud(AsignacionNSS nss,String titulo, Long idTipoTramite) throws DerechohabientesBusinessException, Exception;
	Object getDocumentoRegistroDerechohabientes(AsignacionNSS nss, FirmaElectronica firmaElectronica, Long idTramite,String titulo, Usuario usuario)
	throws DerechohabientesBusinessException , Exception;
	
	public Object getCartillaNacionalSalud(Long idDerechohabiente,AsignacionNSS nss, FirmaElectronica firmaElectronica) throws DerechohabientesBusinessException, Exception;
	public Object generaSav002(AsignacionNSS nss, FirmaElectronica firmaElectronica,Usuario usuario, Long idTramite, Long idOrigenSolicitud, Boolean registro) throws DerechohabientesBusinessException, Exception;
	public Object getDocumentoSav001(AsignacionNSS nss) throws DerechohabientesBusinessException, Exception;

    Object generaSav002v2(AsignacionNSS nss,FirmaElectronica firmaElectronica, Usuario usuario, Long idTramite, Long idOrigenSolicitud, Boolean registro) throws DerechohabientesBusinessException, Exception;

    public Object getDocumentoSav005(AsignacionNSS nss, FirmaElectronica firmaElectronica, Long idPersona, Long idEstadoTramite, Long idOrigenSolicitud, Usuario usuario)throws DerechohabientesBusinessException, Exception;
	public Object getDocumentoSav006(AsignacionNSS nss, FirmaElectronica firmaElectronica, Usuario usuario, Long id, Integer identificadorReporte) throws DerechohabientesBusinessException, Exception;
	public Object getDocumentoSav007(AsignacionNSS nss, FirmaElectronica firmaElectronica, Long idPersona)	throws DerechohabientesBusinessException, Exception;
	public Object getDocumentoSav010(Long idTramite) throws DerechohabientesBusinessException, Exception;
	Object getDocumentoSav011(AsignacionNSS nss, FirmaElectronica firmaElectronica, Usuario usuario, Long idBeneficiario) throws DerechohabientesBusinessException, Exception;
	public Object getDocumentoSav017(Long idPersona, AsignacionNSS nss, FirmaElectronica firmaElectronica,  Boolean autorizacion, TramiteCircunscripcionForanea circunscripcion, Long idOrigenSolicitud) throws DerechohabientesBusinessException, Exception; 
	
	
	public List<GrupoFamiliar> getGrupoFamiliarSav007(Long nss) throws DerechohabientesBusinessException, Exception;
	public List<GrupoFamiliar> getGrupoFamiliarSav005(Long nss) throws DerechohabientesBusinessException, Exception;
	Object getDocumentoBajaDerechohabientes(AsignacionNSS nss,Long idTramite,String titulo) throws DerechohabientesBusinessException, Exception;
	TramiteProrroga getProrrogaActiva();
	Object getComprobanteVigenciaDerechos(AsignacionNSS nss, FirmaElectronica firmaElectronica) throws DerechohabientesBusinessException, Exception;
	Object getComprobanteVigenciaDerechos(AsignacionNSS nss, FirmaElectronica firmaElectronica, Usuario usuario) throws DerechohabientesBusinessException, Exception;
	Object getDocumentoReporte4305A(AsignacionNSS nss, FirmaElectronica firmaElectronica, Usuario usuario, Long idOrigenSolicitud)throws DerechohabientesBusinessException , Exception;
	Object getDocumentosProrroga(AsignacionNSS nss, FirmaElectronica firmaElectronica, Long idTramite,Long idDerechohabiente)
	throws DerechohabientesBusinessException, Exception;
	List<ComprobanteVigenciaDerechosDTO> getDatosVigenciaDerechos(AsignacionNSS nss) throws DerechohabientesBusinessException, Exception;
	Object getDocumentosCambioDatos(AsignacionNSS nss, FirmaElectronica firmaElectronica, Long idTramite,
			String titulo, Usuario usuario) throws DerechohabientesBusinessException, Exception;
	Object getDocumentosCambioClinica(AsignacionNSS nss, FirmaElectronica firmaElectronica, Long idTramite,Usuario usuario,
			String titulo) throws DerechohabientesBusinessException, Exception;
	Object getDocumentosCircunscripcion(AsignacionNSS nss, FirmaElectronica firmaElectronica, Long idTramite,boolean autorizacion,
			String titulo, Usuario usuario) throws DerechohabientesBusinessException , Exception;
	Object getDocumentosCuestionario(AsignacionNSS nss,Long idTramite ) throws DerechohabientesBusinessException, Exception;
	Object getDocumentosCambioConsultorio(AsignacionNSS nss, FirmaElectronica firmaElectronica, Long idTramite,Usuario usuario,
			String titulo) throws DerechohabientesBusinessException, Exception;
	Object getDocumentosSuspencionCircunscripcion(AsignacionNSS nss,Long idTramite,Long idPersona,
			String titulo, Usuario usuario) throws DerechohabientesBusinessException, Exception;
	Object getDocumentoRegistroDerechohabientesDep (AsignacionNSS nss, FirmaElectronica firmaElectronica, Long idTramite,List<Long> personas,Long tipoTramite,
			String titulo, Usuario usuario) throws DerechohabientesBusinessException, Exception;
	
	public Object concatenarByteStream( List<ByteArrayOutputStream>  byteArrayOutputStream, boolean paginate );
	
	Object getConstanciaVigenciaInternet(AsignacionNSS nss, FirmaElectronica firmaElectronica, Usuario usuario) throws DerechohabientesBusinessException, Exception;
	
	Object getConstanciaVigenciaInternetRecortado(AsignacionNSS nss, FirmaElectronica firmaElectronica, Usuario usuario) throws DerechohabientesBusinessException, Exception;
	
	Object getConstanciaVigenciaWS(AsignacionNSS nss, FirmaElectronica firmaElectronica, Usuario usuario) throws DerechohabientesBusinessException, Exception;
	
	Object generarComprobanteTramiteAdministrativo(Solicitud solicitud) throws Exception;

	public Object getCartillasNacionalSalud(List<GrupoFamiliar> integrantes, AsignacionNSS nss, FirmaElectronica firmaElectronica) throws DerechohabientesBusinessException, Exception;
}
