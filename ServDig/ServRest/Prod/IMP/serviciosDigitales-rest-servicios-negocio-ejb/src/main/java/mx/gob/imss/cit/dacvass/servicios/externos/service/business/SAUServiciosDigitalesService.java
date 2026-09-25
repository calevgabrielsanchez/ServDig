package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sau.Usuario;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISAUServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ParserUsuarioSAUEntityToRest;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.AdmonUsuariosService;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.ResponsableDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.ResponsablesDelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.WSPersonalSubdelegacionService;

@Stateless(name = "sauServiciosDigitalesService", mappedName = "sauServiciosDigitalesService")
public class SAUServiciosDigitalesService implements
		ISAUServiciosDigitalesServiceRemote {

	private static Logger log = LoggerFactory
			.getLogger(SAUServiciosDigitalesService.class);

	@Override
	public Usuario buscarUsuarioPorUid(String curp)
			throws ServiciosRestException {
		AdmonUsuariosService service = new AdmonUsuariosService();
		try {
			UsuarioDTO usuarioDTO = service.getAdmonUsuariosPort()
					.obtenUsuario(curp);
			Usuario usuario = null;
			if (usuarioDTO != null) {
				usuario = new Usuario();
				BeanUtils.copyProperties(usuarioDTO, usuario);
			}
			return usuario;
		} catch (Exception e) {
			log.error("Ocurrio un erro al realizar la busqueda del usuario "
					+ curp, e);
			throw new ServiciosRestException(new ErrorResponseBean(
					ErrorResponseBean.codigo500,
					ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado en la consulta de usuario"
							+ e.getMessage(), e.getMessage()));
		}
	}

	@Override
	public List<Usuario> buscarUsuario(Usuario usuario)
			throws ServiciosRestException {
		List<Usuario> usuarios = null;
		WSPersonalSubdelegacionService service = new WSPersonalSubdelegacionService();
		try {
			ResponsablesDelegacionDTO usuarioDTO = service
					.getWSPersonalSubdelegacionPort()
					.recuperaResponsablesDelegacion(
							usuario.getClaveDelegacion(),
							usuario.getClaveSubDelegacion(),
							usuario.getRoles(), 17);
			Usuario usuarioSalida = null;
			if (usuarioDTO != null && usuarioDTO.getResponsables() != null
					&& !usuarioDTO.getResponsables().isEmpty()) {
				usuarios = new ArrayList<Usuario>();
				for (ResponsableDTO responsable : usuarioDTO.getResponsables()) {
					usuarioSalida = ParserUsuarioSAUEntityToRest
							.parserUsuarioEntityToRest(responsable, usuario);
					if (usuario.getRoles().contains("74")
							&& usuario.getUidAuditorAsignado() != null) {
						if (usuario.getUidAuditorAsignado() != null
								&& !usuario.getUidAuditorAsignado().equals(
										responsable.getCurp())) {
							usuarios.add(usuarioSalida);
						}
					} 
					else if (usuario.getRoles().contains("75")) {
						if(usuarioSalida.getPuesto().equals("AUDITOR")) {
							usuarioSalida.setPuesto("CENSOR");
						}
						usuarios.add(usuarioSalida);
					}
					else {
						usuarios.add(usuarioSalida);
					}
				}				
			}
			return usuarios;
		} catch (Exception e) {
			log.error("Ocurrio un erro al realizar la busqueda del usuario ", e);
			throw new ServiciosRestException(new ErrorResponseBean(
					ErrorResponseBean.codigo500,
					ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error inesperado en la consulta de usuario"
							+ e.getMessage(), e.getMessage()));
		}
	}
	
	@Override
	public List<Usuario> buscarUsuarioGenral(Usuario usuario) throws ServiciosRestException {
		List<Usuario> usuarios = null;
		WSPersonalSubdelegacionService service = new WSPersonalSubdelegacionService();
		try {
			ResponsablesDelegacionDTO usuarioDTO = service.getWSPersonalSubdelegacionPort()
					.recuperaResponsablesDelegacion(usuario.getClaveDelegacion(), usuario.getClaveSubDelegacion(),
							usuario.getRoles(), usuario.getModulo());
			Usuario usuarioSalida = null;
			if (usuarioDTO != null && usuarioDTO.getResponsables() != null && !usuarioDTO.getResponsables().isEmpty()) {
				usuarios = new ArrayList<Usuario>();
				for (ResponsableDTO responsable : usuarioDTO.getResponsables()) {
					Usuario usuario1 = buscarUsuarioPorUid(responsable.getCurp());
					
					if (usuario1.isActivo()) {
						usuarioSalida = ParserUsuarioSAUEntityToRest.parserUsuarioEntityToRest(responsable, usuario);
						usuarioSalida.setActivo(true);
						usuarios.add(usuarioSalida);
					}
				}
			}
			return usuarios;
		} catch (Exception e) {
			log.error("Ocurrio un erro al realizar la busqueda del usuario ", e);
			throw new ServiciosRestException(
					new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
							"Ocurrio un error inesperado en la consulta de usuario" + e.getMessage(), e.getMessage()));
		}
	}

}
