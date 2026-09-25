package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import java.util.List;

import javax.ejb.Remote;
import javax.naming.AuthenticationException;
import javax.naming.NamingException;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.ResolucionPension;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.renapo.RespuestaWSRenapo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.RespuestaWSSat;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.UsuarioDirectorioActivo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.ConsultaUsuarioDirectorioActivo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.EmpleadoSiapRest;

@Remote
public interface IConsultaServiciosExternosServiceRemote {
	
	/**
	 * Metodo encargado de consultar el servicio REST de pensión envia CURP y recibe un List de resoluciones asociadas a la CURP
	 * @param curp
	 * @return ResolucionPension
	 * @throws ServiciosRestException
	 */
	List<ResolucionPension> getConsultaInfoPension(String curp) throws ServiciosRestException;

	/**
	 * Metodo que consulta la informacion del SIAP por numero de matricula
	 * @param numMatricula
	 * @return
	 * @throws ServiciosRestException
	 */
	EmpleadoSiapRest getEmpleadoSiapByMatricula (Long numMatricula) throws ServiciosRestException;
	
	 /**
	  * Metodo que recupera la persona por RFC del webservices externo del SAT.
	  * @param rfc
	  * @return
	  * @throws ServiciosRestException
	  */
	 RespuestaWSSat getDatosSatByRfc(String rfc) throws ServiciosRestException;
	 
	 /**
	  * Metodo que consulta a la persona en RENAPO
	  * @param curp
	  * @return
	  * @throws ServiciosRestException
	  */
	 RespuestaWSRenapo getDatosPersonaRenapoByCurp(String curp) throws ServiciosRestException;
	 
	 /**
	  * Metodo que consulta una cuenta de usuario en LDAP del INSTITUTO por usuario y dominio
	  * @param claveUsuario
	  * @return
	  * @throws ServiciosRestException
	  */
	 UsuarioDirectorioActivo getDatosUsuarioDirectorioActivo(ConsultaUsuarioDirectorioActivo consulta) throws ServiciosRestException;
	 
	 /**
		 * Servicio que autentica al usuario basado en su dominio
		 * @param consulta
		 * @return UsuarioDirectorioActivo
		 * @throws AuthenticationException
		 * @throws NamingException
		 * @throws Exception
		 */
	 UsuarioDirectorioActivo autenticaUsuarioDirectorioActivo(ConsultaUsuarioDirectorioActivo consulta) throws ServiciosRestException;
	 
}
