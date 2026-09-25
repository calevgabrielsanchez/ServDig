package mx.gob.imss.cit.dacvass.servicios.externos.service.util;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.RespuestaDirectorioActivo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.UsuarioDirectorioActivo;

public class ParserUsuarioDirectorioActivoToRest {

	private static final Logger log = LoggerFactory
			.getLogger(ParserUsuarioDirectorioActivoToRest.class);

	public static RespuestaDirectorioActivo parserLdapResponseToModel(Attributes attributes) throws ServiciosRestException {
		log.debug("llege al parser de respuesta ldap to model");
		ValidacionesComunesUtil.validaObjetoRespuestaNulo(attributes, "No se encontro la cuenta en el directorio activo");
		try {
			String distinguishedName = attributes.get("distinguishedName") != null ? attributes.get("distinguishedName").get().toString() : null;
			String cn = attributes.get("cn") != null ? attributes.get("cn").get().toString() : null;
			String mail = attributes.get("mail") != null ? attributes.get("mail").get().toString() : null;
			String name = attributes.get("name") != null ? attributes.get("name").get().toString() : null;
			String userPrincipalName = attributes.get("userPrincipalName") != null ? attributes.get("userPrincipalName").get().toString() : null;
			String displayName = attributes.get("displayName") != null ? attributes.get("displayName").get().toString() : null;
			String givenName = attributes.get("givenName") != null ? attributes.get("givenName").get().toString() : null;
			String sn = attributes.get("sn") != null ? attributes.get("sn").get().toString() : null;
			String description = attributes.get("description") != null ? attributes.get("description").get().toString() : null;
			String department = attributes.get("department") != null ? attributes.get("department").get().toString() : null;
			String employeeID = attributes.get("employeeID") != null ? attributes.get("employeeID").get().toString() : null;
			String employedID = attributes.get("employedID") != null ? attributes.get("employedID").get().toString() : null;

			RespuestaDirectorioActivo user = new RespuestaDirectorioActivo();

			user.setDistinguishedName(distinguishedName);
			user.setCN(cn);
			user.setMail(mail);
			user.setName(name);
			user.setUserPrincipalName(userPrincipalName);
			user.setDisplayName(displayName);
			user.setGivenName(givenName);
			user.setSn(sn);
			user.setDescription(description);
			user.setDepartment(department);
			user.setEmployeeID(employeeID);
			user.setEmployedID(employedID);
			return user;
		}catch (NamingException e) {
			log.error("ocurrio un error  de nombre de los atributos de respuesta en el parser" , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error  de nombre de los atributos de respuesta en el parser de LDAP", e.getMessage()));
		}catch (Exception e) {
			log.error("ocurrio un error desconocido en el parser de respuesta de LDAP " , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error  desconocido en el parsser de respuesta de LDAP", e.getMessage()));
		}

	}
	
	public static UsuarioDirectorioActivo parserRespuestaDirectorioAtivoToRest(RespuestaDirectorioActivo respuesta) throws ServiciosRestException {
		log.debug("llege al parser de parserRespuestaDirectorioAtivoToRest");
		ValidacionesComunesUtil.validaObjetoRespuestaNulo(respuesta, "No se encontro la cuenta en el directorio activo");
		try {
			UsuarioDirectorioActivo usuario = new UsuarioDirectorioActivo();
			usuario.setNombre(respuesta.getGivenName());
			usuario.setPrimerApellido(respuesta.getLastName());
			usuario.setSegundoApellido(respuesta.getMotherLastName());
			usuario.setCorreo(respuesta.getMail());
			usuario.setNomCuenta(respuesta.getUserPrincipalName());
			usuario.setDescripcion(respuesta.getDescription());
			usuario.setInfoDirectorioActivo(respuesta.getDistinguishedName());
			return usuario;
		}catch (Exception e) {
			log.error("ocurrio un error desconocido en el parser de respuesta LDAP to REST " , e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error  desconocido en el parsser de respuesta LDAP to REST", e.getMessage()));
		}
	}


}
