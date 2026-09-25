package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.io.IOException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/hlda")
public class HLDAController extends AbstractController {

	@Autowired
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	@RequestMapping(value = "/reporte", method = RequestMethod.GET)
	public void generarReporteSemanasCotizadas(
			final @RequestParam("nss") String nssCifrado,
			final HttpServletRequest request, final HttpServletResponse response) {

		String nss = null;
		byte[] reporteHLDA = null;
		Usuario usuario = getUsuarioSesion(this.procesarUsuarioSSO(request));

		try {
			nss = Base64Cipher.descrifrar(nssCifrado);

			reporteHLDA = this.componentesExternosBusiness
					.obtenerReporteDeHistoriaLaboral(nss, usuario);

			if (reporteHLDA != null) {
				log.debug("El reporte de HLDA para el NSS [" + nss
						+ "] no es nulo");

				response.addHeader("Accept-Ranges", "bytes");
				response.addHeader("Cache-Control", "public");
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.addHeader("expires", "0");
				response.addHeader("Content-disposition", "inline;filename="
						+ "SemanasCotizadas_" + nss + ".pdf");
				response.setContentLength(reporteHLDA.length);
				response.getOutputStream().write(reporteHLDA);
				response.getOutputStream().close();
			} else {
				this.log.warn("No hay documento de HLDA para el NSS [" + nss
						+ "]");
			}
		} catch (IOException e) {
			this.log.error(e);
		} catch (InvalidKeyException e) {
			this.log.error(e);
		} catch (IllegalBlockSizeException e) {
			this.log.error(e);
		} catch (BadPaddingException e) {
			this.log.error(e);
		}

		this.log.debug("Termina generación de reporte HLDA para el NSS [" + nss
				+ "]");
	}
	
	private Usuario getUsuarioSesion(UsuarioSSO usuarioSSO) {
		Usuario usuario = null;

		if (usuarioSSO != null && usuarioSSO.getIdPersona() != null) {
			usuario = new Usuario();
			usuario.setUsuario(usuarioSSO.getNombre());

			PerfilUsuario pu = new PerfilUsuario();
			pu.setDescripcion(usuarioSSO.getNombre());
			usuario.setPerfilUsuario(pu);

			if (usuarioSSO.getDelegacion() != null
					&& usuarioSSO.getSubdelegacion() != null) {
				UsuarioFuncionario uf = new UsuarioFuncionario();
				uf.setDelegacion(new Delegacion());
				uf.getDelegacion()
						.setId(usuarioSSO.getDelegacion().longValue());
				uf.setSubdelegacion(new Subdelegacion());
				uf.setUsuario(usuario);
				uf.getSubdelegacion().setId(
						usuarioSSO.getSubdelegacion().longValue());
				usuario.setUsuarioFuncionario(uf);
			}

			usuario.setCveIdUsuario(usuarioSSO.getIdPersona().toString());
		}

		return usuario;
	}
}
