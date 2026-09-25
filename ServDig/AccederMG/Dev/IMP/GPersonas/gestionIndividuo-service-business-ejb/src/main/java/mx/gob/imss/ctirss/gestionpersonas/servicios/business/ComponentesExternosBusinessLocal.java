package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.UsuarioDTO;

@Local
public interface ComponentesExternosBusinessLocal {
	
	Map<String, Boolean> getRolesPorPersona(Long idPersona);
	
	TramiteCorreccionDerechohabiente saveTramiteCorreccionDatosDerechohabiente(GrupoFamiliar grupo, Fisica fisica, Long idSolicitud);
	
	List<GrupoFamiliar> obtenerGruposPorIdPersonaYPersonaInteresada(Long idPersona, Long idPersonaInteresada);
	
	Boolean registradoComoDerechohabiente(Long idPersona, Boolean activo);
	
    void altaDomicilios(Persona persona) throws DomicilioNoValidoException;
    
    void altaMediosContacto(Persona persona);
    
    void altaDocumentosProbatorios(Persona persona) throws RegistrarDocumentoProbatorioException;    
    
    List<DocumentoProbatorio> getDocumentosProbatoriosPersona(Persona persona);
    
    void getDomiciliosPersona(Persona persona);
    
    List<MedioContacto> getMediosContactoPersona(Persona persona);
    
    void getDomicilioFiscalPersona(Persona persona);

    void getMediosContactoFiscalesPersona(Persona persona);
    
    
    /**
     * Metodo que crea un usuario en el esquema de seguridad 
     * @param fisica objeto con los datos de la persona a dar de alta
     * @param cveIdsolicitud recive el número de solicitud con el cual se creo el registro
     * @return String con la respuesta del servicio de alta
     * @throws EsquemaSegurdiadException
     */
    String crearUsuarioEsquemaSeguridad(Usuario usuario,
			Long cveIdsolicitud) throws EsquemaSegurdiadException;
	
    
    /**
     * Servicio encargado de eliminar un usuario del esquema de seguridad
     * @param curp con el valor de la llave a eliminar
     * @return true si fue exitosa la eliminacion del registro
     * @throws EsquemaSegurdiadException
     */
    boolean eliminaUsuarioEsquemaSeguridadByCURP(String curp) throws EsquemaSegurdiadException;
    
    /**
     * Servicio para consultar si existe una cuenta en el esquema de seguridad
     * @param curp recibe un string con el curp de la cuenta a buscar
     * @return true si existe la cuenta, false en caso contrario
     * @throws EsquemaSegurdiadException
     */
	boolean existeUsuarioEsquemaSeguridadByCURP(String curp) throws  EsquemaSegurdiadException;
	
	
	/**
	 * Servicio que se encarga de recuperar los datos basicos de un usuario de seguridad 
	 * @param curp del usuario a recuperar
	 * @return Usuario con los datos basicos seteados
	 * @throws EsquemaSegurdiadException
	 * @throws UsuarioNoEncontradoException
	 */
	Usuario recuperaUsuarioEsquemaSeguridadByCURP(String curp)	throws EsquemaSegurdiadException,
	UsuarioNoEncontradoException;
	
	
	/**
	 * Servicio para actualizar un usuario en esquema de seguridad
	 * @param objFisica recibe los datos de la persona fisica
	 * @throws EsquemaSegurdiadException
	 */
	void actualizaUsuarioEsquemaSeguridad(Usuario objUsuario) throws EsquemaSegurdiadException;
	
	/**
	 * Servicio que hace uso de los componentes de domicilios para dar de alta
	 * domicilios y relacionarlos a la persona indicada. Se requiere que tanto
	 * la lista de domicilio como el id de la persona no est?n vacios.
	 * 
	 * @param persona
	 * @throws DomicilioNoValidoException
	 */
	void guardarYAsociarDomiciliosPersona(Persona persona)
			throws DomicilioNoValidoException;
	
	/**
	 * Servicio que hace uso de los componentes de medios para dar de alta
	 * medios y relacionarlos a la persona indicada. Se requiere que tanto
	 * la lista de medios como el id de la persona no est?n vacios.
	 * 
	 * @param persona
	 * @throws DomicilioNoValidoException
	 */
	void guardarYAsociarMediosContactoPersona(Persona persona);
	
	/**
	 * Servicio que hace uso de los componentes de domicilios, para obtener
	 * del domicilio particular de la persona.
	 * 
	 * @param persona
	 * @return
	 */
	List<Domicilio> obtenerDomicilioParticularPersona(Persona persona);
	
	/**
     * Metodo que crea un usuario en el esquema de seguridad 
     * @param fisica objeto con los datos de la persona a dar de alta
     * @param cveIdsolicitud recive el número de solicitud con el cual se creo el registro
     * @return String con la respuesta del servicio de alta
     * @throws EsquemaSegurdiadException
     */
    String crearUsuarioEsquemaSeguridad(Fisica usuario, Usuario usuarioDto) throws EsquemaSegurdiadException;
    

    boolean regenerarPasswordEsquemaSeguridad(String curp, String serial) throws EsquemaSegurdiadException;
    
}
