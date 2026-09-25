package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.RequisitosDTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Remote
public interface CorreccionDerechohabienteServiceRemote {

	Solicitud finalizarSolicitudDomicilioClinicaCircunscripcion(Solicitud solicitud) throws DerechohabientesBusinessException,SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException;
	List<Long> guardarTramiteAsignacionUmfDependiente(GrupoFamiliar afectado, Long idSolicitud, Integer patronIMSS, Boolean isRegistro, Boolean asignacionDomicilio) throws DerechohabientesBusinessException, Exception;
	/**
	 * Metodo para guardar un tramite de correccion de derechohabiente
	 * @param grupo
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	TramiteCorreccionDerechohabiente guardarTramiteCorreccionDatosDerechohabienteDependiente(
			GrupoFamiliar grupo, Fisica fisica, Long idSolicitud)
			throws DerechohabientesBusinessException;
	
	List<Tramite> findTramitesAbiertos(AsignacionNSS nss,List<Long> integrantes) throws DerechohabientesBusinessException;
	
	List<GrupoFamiliar> findGrupoFamiliarCorreccion(AsignacionNSS nss,Usuario usuario) throws DerechohabientesBusinessException;
	List<GrupoFamiliar> findGrupoFamiliarCorreccionDatos(Long nss,Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException;
	List<GrupoFamiliar> findGrupoFamiliarCambioMedico(AsignacionNSS nss, Usuario usuario) throws DerechohabientesBusinessException;
	List<GrupoFamiliar> findGrupoFamiliarAsignacionMedico(AsignacionNSS nss, Usuario usuario) throws DerechohabientesBusinessException;
	List<GrupoFamiliar> findGrupoFamiliarSuspencionCircunscripcion(AsignacionNSS asignacionNss, Boolean activa, Usuario usuario) throws DerechohabientesBusinessException;
	
	/**
	 * Este metodo se encarga de regresar los miembros del grupofamiliar que apliquen al tramite de actualizacion de domicilio particular (ACTUALIZACION_DOMICILIO_PARTICULAR=6)
	 * que pueden ser hijos, conyuge y el asegurado que esten en estado vigente
	 * 
	 * @param nss
	 * @param origenSolicitud
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarActualizacionDomicilio(Long nss,Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException;
	
	/**
	 * Este metodo se encarga de regresar los miembros del grupo familiar que apliquen al tramite de cambio de clinica(CAMBIO_CLINICA=36), que pueden ser hijos y conyuge
	 * que esten en estado vigente.
	 * @param nss
	 * @param origenSolicitud
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarCambioClinica(Long nss,Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException;
	
	/**
	 * Este metodo guarda el solicitud de correccion de datos del derechohabiente, puede ser llamado desde el flujo de correccion de datos
	 * y desde la asignacion de domicilio, si es de la asignacion de domicilio tipoTramite tiene que corresponder a ASIGNACION_DE_DOMICILIO_PARTICULAR_DH
	 * @param idDerechohabiente
	 * @param usuario
	 * @param nss
	 * @param correccion
	 * @param origen
	 * @param tipoTramite
	 * @param esAsignacionDomicilio true si es una asignacion de domicilio null o false cuando es una corrección
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Solicitud saveCorreccionDatosDerechohabiente(Long idDerechohabiente, GrupoFamiliar integrante,Usuario usuario, AsignacionNSS nss, TramiteCorreccionDerechohabiente correccion, OrigenSolicitudEnum origen, Boolean esAsignacionDomicilio ) throws DerechohabientesBusinessException;
	Solicitud saveCorreccionDatosDerechohabiente(Long idDerechohabiente,Usuario usuario, AsignacionNSS nss, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	void saveCorreccionDatosDerechohabiente(TramiteCorreccionDerechohabiente tramiteCorreccionDerechohabiente) throws DerechohabientesBusinessException, Exception;
	Solicitud saveCorreccionUmfDerechohabiente(Long idDerechohabiente, Usuario usuario, AsignacionNSS nss, TramiteCorreccionDerechohabiente correccion, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	Solicitud saveCorreccionUmfDerechohabiente(TramiteCorreccionDerechohabiente correccion, Usuario usuario, AsignacionNSS nss, Boolean patronImss, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	Solicitud saveCorreccionMedicoDerechohabiente(Long idDerechohabiente, Usuario usuario, AsignacionNSS nss, TramiteCorreccionDerechohabiente correccion, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	Solicitud saveCorreccionDerechohabienteWeb(Long idDerechohabiente, Usuario usuario, AsignacionNSS nss, TramiteCorreccionDerechohabiente correccion, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	Solicitud saveCircunscripcionAutorizacionDerechohabiente(Long idDerechohabiente, Usuario usuario, AsignacionNSS nss, TramiteCorreccionDerechohabiente correccion, OrigenSolicitudEnum origen) throws DerechohabientesBusinessException;
	Solicitud saveAsignacionMedico(Long idDerechohabiente, Usuario usuario, AsignacionNSS nss,TramiteCorreccionDerechohabiente correccion, OrigenSolicitudEnum origen)
	throws DerechohabientesBusinessException;
	
	TramiteCircunscripcionForanea getCircunscripcionForanea(Long idPersona, AsignacionNSS nss, Boolean activa) throws DerechohabientesBusinessException;
	Solicitud saveCircunscripcionSuspensionDerechohabiente(Long idCircunscripcion, String observaciones, Usuario usuario, AsignacionNSS nss) throws DerechohabientesBusinessException;
	
	Solicitud inicioValidacionCorreccion(Long idSolicitud, Long idAsignacionNss) throws DerechohabientesBusinessException;
	
	/**
	 * Metodo que finaliza el tramite de correccion de datos de un derechohabiente(datos personales, medios de contacto, domiciio)
	 * @param correccion
	 * @param afectado
	 * @param asignacionNSS
	 * @param cabeza
	 * @param personaUsuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws ImpactaAlmacenesWSException
	 */
	TramiteCorreccionDerechohabiente guardarValidacionCorreccionDatos(Solicitud correccion, GrupoFamiliar afectado, AsignacionNSS asignacionNSS, CabezaGrupoFamiliar cabeza,Fisica personaUsuario,
			List<GrupoFamiliar> padresConcubina, List<GrupoFamiliar> integrantesSinDomicilioenUMF) throws DerechohabientesBusinessException,ImpactaAlmacenesWSException;
	
	Solicitud guardarValidacionCambioMedico(Solicitud correccion, Fisica usuarioPersona, AsignacionNSS nss) throws DerechohabientesBusinessException;
	Solicitud guardarValidacionCambioUmf(Solicitud correccion, Fisica usuarioPersona, Boolean patronImss,AsignacionNSS nss) throws DerechohabientesBusinessException;
	TramiteCircunscripcionForanea saveValidarCircunscripcionSuspension(Long idCircunscripcion, Usuario usuario, AsignacionNSS nss) throws DerechohabientesBusinessException;
	TramiteCircunscripcionForanea saveValidacionAutorizacionDerechohabiente(Long idSolicitud, Usuario usuario, AsignacionNSS nss) throws DerechohabientesBusinessException;
	TramiteCircunscripcionForanea saveValidacionSuspensionDerechohabiente(TramiteCircunscripcionForanea suspension, Usuario usuario, AsignacionNSS nss) throws DerechohabientesBusinessException;
	Solicitud saveValidacionAsignacionMedico(Solicitud correccion,AsignacionNSS nss, Fisica personaUsuario)throws DerechohabientesBusinessException;
	Solicitud getCircunscripcionTramite(Long idSolicitud) throws DerechohabientesBusinessException;
	boolean validarDatosDerechohabiente(TramiteCorreccionDerechohabiente correccion,AsignacionNSS nss) throws DerechohabientesBusinessException;
	List<Long> guardarTramiteCambioClinicaDependiente(TramiteCorreccionDerechohabiente correccion, Fisica usuario, AsignacionNSS nss, Boolean patronImss,Date fechaCambioMTC, Long idSolicitud, GrupoFamiliar afectado, Boolean asignacionDomicilio) throws DerechohabientesBusinessException;
	void guardarTramiteCambioMedicoDependiente(TramiteCorreccionDerechohabiente correccion, Fisica usuario, Long idSolicitud, AsignacionNSS nss) throws DerechohabientesBusinessException;
	List<Long> guardarTramiteCircunscripcionDependiente(TramiteCircunscripcionForanea circunscripcion, Usuario usuario, AsignacionNSS nss, Long idSolicitud, GrupoFamiliar afectado) throws DerechohabientesBusinessException;
	boolean validaEstadoDerechohabientes(Date fechaNacimiento, Long idSexo, Long idParentesco,boolean patronIMSS) throws DerechohabientesBusinessException, Exception;
	TramiteCorreccionDerechohabiente getTramitePendienteByAutorizar(Long idTramite) throws DerechohabientesBusinessException;
	Solicitud saveSolicitudAceptada(Long idSolicitud,AsignacionNSS nss, Fisica personaUsuario) throws DerechohabientesBusinessException;
	Solicitud finalizarSolicitudCorreccionDatos(Solicitud solicitud) throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, DerechohabientesBusinessException;
	/**
	 * Este metodo localiza a una persona fisica en RENAPO con la CURP (Invoca a LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote#LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote)
	 * @param curp
	 * @return
	 * @throws CURPNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceRenapoCurpException
	 * @throws ErrorValidacionDatosConsultaEnEntidaExternaException
	 */
	Fisica localizarPersonaFisicaEnRENAPOxCURP(String curp) throws CURPNoLocalizadoEnEntidadExternaException, ClienteWebserviceRenapoCurpException, ErrorValidacionDatosConsultaEnEntidaExternaException;
	
	/**
	 * Este metodo se encarga de guardar una solicitud de tipo TramiteCorreccionDerechohabiente
	 * @param derechohabiente, objeto con la información del derechohabiente al que se le esta realizando el tramite
	 * @param usuario
	 * @param nss objeto de tipo AsignacionNSS
	 * @param correccion, objeto que contiene la informacion del tipo de tramite
	 * @param tipoCorreccion, se refiere al identificador del tipo de tramite que sera guardado
	 * @param origen, se refiere a si la solicitud esta siendo emitida desde internet o ventanilla
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	public Solicitud guardarSolicitudCorreccionDerechohabiente(GrupoFamiliar derechohabiente,
			Usuario usuario, AsignacionNSS nss,
			TramiteCorreccionDerechohabiente correccion, TipoTramiteEnum tipoCorreccion, OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException;
	
	/**
	 * Este metodo se encarga de finalizar la solicitud de asignacion de domicilio, la cual consiste en cerrar propiamente la solcitud y el tramite correspondiente
	 * ademas de actualizar el domicilio en el grupo familiar del derechohabiente (DIT_GRUPO_FAMILIAR). 
	 * @author juan.osorioal
	 * @param solicitud Debe ser un objeto de tipo Solicitud con al menos su ID
	 * @return
	 */
	public Solicitud finalizarSolicitudAsignacionDomicilio(Solicitud solicitud)
				throws SolicitudNoEncontradaException, DomicilioNoLocalizadoException;
	
	/**
	 * Este metodo se encarga de finalizar la solicitud de actualizacion de domicilio, la cual consiste en cerrar propiamente la solcitud y el tramite correspondiente
	 * ademas de actualizar la informacion del domicilio geografico al cual esta relacionado el miembro del grupo familiar por medio de la relacion DIT_GRUPO_FAMILIAR, DIT_PERSONAF_DOM
	 * DG_DOMIICLIO_GEOGRAFICO (DG_DOMIICLIO_GEOGRAFICO es donde se actualiza la informacion). 
	 * @author juan.osorioal
	 * @param solicitud Debe ser un objeto de tipo Solicitud con al menos su ID
	 * @return
	 */
	public Solicitud finalizarSolicitudActualizacionDomicilio(Solicitud solicitud)
				throws SolicitudNoEncontradaException, DomicilioNoLocalizadoException;
	
	/**
	 * Este metodo se encarga de finalizar la solcitud de cambio de UMF destino
	 * @param solicitud
	 * @return
	 * @throws SolicitudNoEncontradaException
	 * @throws DomicilioNoLocalizadoException
	 */
	public Solicitud finalizarSolicitudCambioClinica(Solicitud solicitud)
				throws SolicitudNoEncontradaException, DomicilioNoLocalizadoException;
	
	
	/**
	 * Metodo que se expone para modificar la información de un objeto derechohabiente 
	 * @param integrante
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	void updateIntegrante(GrupoFamiliar integrante) throws DerechohabientesBusinessException,Exception;
	
	
	/**
	 * Valida los requisitos que debe cumplir un derechohabiente para realizar una correcci&oacute;n de datos
	 * 
	 * @param integrante Información del derechohabiente
	 * @param nss Asignaci&oacute;n del derechohabiente
	 * @param patronImss Si su patr&oacute;n es el IMSS
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	RequisitosDTO requisitosCorreccion(GrupoFamiliar integrante, AsignacionNSS nss, CabezaGrupoFamiliar cabeza, Boolean consultarMod, List<Long> idsModalidades) throws DerechohabientesBusinessException, Exception;
	
	
	public FirmaElectronica generaFirmaElectronica(AsignacionNSS asignacionNSS,Solicitud solicitud, String nombreTramite) throws DocumentoException;
	
}
