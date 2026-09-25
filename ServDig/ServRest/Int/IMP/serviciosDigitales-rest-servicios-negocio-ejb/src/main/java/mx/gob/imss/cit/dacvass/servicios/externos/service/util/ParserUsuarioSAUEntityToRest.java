package mx.gob.imss.cit.dacvass.servicios.externos.service.util;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sau.Usuario;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.ResponsableDTO;

@Resource
public class ParserUsuarioSAUEntityToRest {

	private static final Logger log = LoggerFactory
			.getLogger(ParserUsuarioSAUEntityToRest.class);

	public static Usuario parserUsuarioEntityToRest(
			ResponsableDTO responsable, Usuario usuarioEntrada)
			throws ServiciosRestException {

		try {
			Usuario usuario = new Usuario();
			usuario.setApellidoMaterno(responsable.getSegundoApellido());
			usuario.setApellidoPaterno(responsable.getPrimerApellido());
			usuario.setNombres(responsable.getNombre());
			usuario.setClaveDelegacion(usuarioEntrada.getClaveDelegacion());
			usuario.setClaveSubDelegacion(usuarioEntrada
					.getClaveSubDelegacion());
			usuario.setUid(responsable.getCurp());
			usuario.setPuesto(responsable.getPuesto());
			usuario.setUidAuditorAsignado(usuarioEntrada
					.getUidAuditorAsignado());
			return usuario;
		} catch (Exception e) {
			log.error("ocurio un erro al parsear el objeto CptCreinc14ImssRcv",
					e);
			throw new ServiciosRestException(new ErrorResponseBean(
					ErrorResponseBean.codigo500,
					ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error la parsear el objeto CptCreinc14ImssRcv",
					e.getMessage()));
		}

	}

}
