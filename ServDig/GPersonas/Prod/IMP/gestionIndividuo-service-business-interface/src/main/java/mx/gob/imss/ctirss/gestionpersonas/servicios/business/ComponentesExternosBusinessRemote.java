package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.UsuarioTransfer;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;


@Remote
public interface ComponentesExternosBusinessRemote {

	List<DocumentoProbatorio> getDocumentosProbatoriosPersona(Persona persona);
	
	void altaDomicilios(Persona persona) throws DomicilioNoValidoException;

	List<MedioContacto> getMediosContactoPersona(Persona persona);
	
	void getDomiciliosPersona(Persona persona);

	
	/**
	 * Servicio que hace uso de los componentes de domicilios para dar de alta
	 * domicilios y relacionarlos a la persona indicada. Se requiere que tanto
	 * la lista de domicilio como el id de la persona no estén vacios.
	 * 
	 * @param persona
	 * @throws DomicilioNoValidoException
	 */
	void guardarYAsociarDomiciliosPersona(Persona persona)
			throws DomicilioNoValidoException;

	/**
	 * Servicio que hace uso de los componentes de medios para dar de alta
	 * medios y relacionarlos a la persona indicada. Se requiere que tanto
	 * la lista de medios como el id de la persona no estén vacios.
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
	 * Obtiene el reporte de historia laboral y genera la solicitud asociada
	 * @param nss
	 * @return archivo pdf en byte[]
	 */
	byte[] obtenerReporteDeHistoriaLaboral(String nss, Usuario usuario);
	
	/**
	 * Servicio que se encarga de recuperar los datos basicos de un usuario de seguridad 
	 * @param curp del usuario a recuperar
	 * @return Usuario con los datos basicos seteados
	 * @throws EsquemaSegurdiadException
	 * @throws UsuarioNoEncontradoException
	 */
	Usuario recuperaUsuarioEsquemaSeguridadByCURP(String curp)	throws EsquemaSegurdiadException,UsuarioNoEncontradoException;
	
	
	public String crearUsuarioService(UsuarioTransfer usuarioDTO) throws EsquemaSegurdiadException;
}
