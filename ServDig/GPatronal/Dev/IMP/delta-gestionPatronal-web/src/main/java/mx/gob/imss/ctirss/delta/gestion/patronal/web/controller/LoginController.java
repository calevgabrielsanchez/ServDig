/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta-gestionPatronal
 *  @Archivo:LoginController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.patronal.web.controller
 *  @Fecha:07/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Lucio Duran Silva
 *
 */
@Controller
@RequestMapping(value="/login")
public class LoginController extends AbstractController {
	
	@Autowired
	SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	
	
	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;

	@Autowired
	private PersonaMoralBusinessRemote personaMoralBusiness;
	
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	
	@Autowired
	private AfiliacionServiceBusinessRemote afiliacionBusiness;
	
	@RequestMapping(method={RequestMethod.GET, RequestMethod.POST})
    public String login(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

	@RequestMapping(value = "/iniciar", method = RequestMethod.POST)
	public String iniciar(@ModelAttribute Socio sujetoOrigen,
			BindingResult result, Model model, SessionStatus status,
			HttpSession session, HttpServletRequest request) {
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
		return this.entrar(usuario, result, model, status, session, request);
	}

	@RequestMapping(value = "/entrarSO", method = {RequestMethod.GET, RequestMethod.POST})
	public String entrar(@ModelAttribute Usuario usuario, BindingResult result,
			Model model, SessionStatus status, HttpSession session, HttpServletRequest request) {
		model.addAttribute("socio", new Socio());
		model.addAttribute("representanteLegal", new RepresentanteLegal());
		model.addAttribute("sujetoObligado",new SujetoObligado());
		model.addAttribute("filtroSolicitud",new FiltroSolicitud());
		model.addAttribute("rolPatronSujetoObligado", CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue());

		UsuarioSSO sso = this.procesarUsuarioSSO(request);

		/*
		 * Procesamos la información del openSSO
		 */
		String forward="buscar.patron.rfc";
		if (sso != null) {
			ServletContext context = session.getServletContext();
			String rolRepresentanteLegal = context.getInitParameter("rolRepresentanteLegal");
	        String rolSujetoObligado = context.getInitParameter("rolSujetoObligado");
	        String[] arrayRolRepresentanteLegal = {rolRepresentanteLegal};
	        String[] arrayRolSujetoObligado = {rolSujetoObligado};

			usuario.setUsuario(sso.getNombre().toUpperCase());
			
			Socio socio = new Socio();

			PerfilUsuario pu = new PerfilUsuario();
			pu.setDescripcion(sso.getPerfil());

			UsuarioFuncionario uf = new UsuarioFuncionario();
			if (sso.getDelegacion() != null) {
				uf.setDelegacion(new Delegacion());
				uf.getDelegacion().setId(sso.getDelegacion().longValue());
			}
			if (sso.getSubdelegacion() != null) {
				Subdelegacion subdel = afiliacionBusiness.obtenerSubdelegacion(sso.getSubdelegacion().longValue());
				usuario.setCveIdSubdelegacion(sso.getSubdelegacion().longValue());
				uf.setSubdelegacion(new Subdelegacion());
				uf.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
				uf.getSubdelegacion().setClave(subdel.getClave());
				uf.getSubdelegacion().setDescripcion(subdel.getDescripcion());
				uf.getSubdelegacion().setDelegacion(new Delegacion());
				uf.getSubdelegacion().getDelegacion().setClave(subdel.getClave());
				uf.getSubdelegacion().getDelegacion().setDescripcion(subdel.getDescripcion());
				uf.getSubdelegacion().getDelegacion().setId(subdel.getId());
			}
			uf.setUsuario(usuario);
			usuario.setUsuarioFuncionario(uf);

			if (this.checkGrantedAuthorities(arrayRolSujetoObligado)) {
				pu.setIdPerfilUsuario( CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue());
				usuario.setPerfilUsuario(pu);
				Moral patronSujetoObligadoMoral = personaMoralBusiness.getPersonaMoral(sso.getIdPersona().longValue());

				if (patronSujetoObligadoMoral != null) {
					if (patronSujetoObligadoMoral.getRfc() != null) {
						forward = obtenerDetalleSujetoObligadoPorRFC(model, patronSujetoObligadoMoral.getRfc(), usuario, socio, session);
					} else {
						throw new RuntimeException("El patr\u00F3n no tiene ning\u00FAn RFC asignado");
					}
				} else {
					try {
						Fisica patronSujetoObligadoFisica = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());
						if (patronSujetoObligadoFisica != null) {
							if (patronSujetoObligadoFisica.getRfc() != null) {
								forward = obtenerDetalleSujetoObligadoPorRFC(model, patronSujetoObligadoFisica.getRfc(), usuario, socio, session);
							} else {
								throw new RuntimeException("El patr\u00F3n no tiene ning\u00FAn RFC asignado");
							}
						} else {
							throw new RuntimeException("El usuario no est\u00E1 registrado");
						}
					} catch (PersonaFisicaNoEncontradaException e) {
						throw new RuntimeException(e);
					}
				}
			} else if (this.checkGrantedAuthorities(arrayRolRepresentanteLegal)) {
				usuario.setFisica((Fisica) sujetoObligadoService.obtenerPersonaPorIdentificador(sso.getIdPersona().longValue()));
				pu.setIdPerfilUsuario(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue());
				usuario.setPerfilUsuario(pu);
				forward="buscar.patron.rfc";
			} else {
				// TODO Considerar Perfil por default
				pu.setIdPerfilUsuario(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());
				Fisica persona = (Fisica)sujetoObligadoService.obtenerPersonaPorIdentificador(sso.getIdPersona().longValue());
				usuario.setNomNombre(persona.getNombre());
				usuario.setNomPaterno(persona.getPrimerApellido());
				usuario.setNomMaterno(persona.getSegundoApellido());
				usuario.setFisica(persona);
				usuario.setPerfilUsuario(pu);
				forward="buscar.patron.rfc";
			}

			// Subimos a la sesion la informacion del usuario
			session.setAttribute(KEY_USUARIO, usuario);
			this.setFechaSistema(session);
		}

		if (usuario.getUsuario().equals("TFISICO")) {
			session.setAttribute("typeLogin", "2");
		} else {
			session.setAttribute("typeLogin", "1");
		}

		usuario.setCveIdUsuario("1L");
		this.setFechaSistema(session);
		model.addAttribute("isRL", usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue()));
        return forward;
    }
	
	private String obtenerDetalleSujetoObligadoPorRFC(Model model, String rfc, Usuario usuario, Socio socio, HttpSession session) {
		System.out.println("****************************\n************************************buscar Sujeto Obligado por RFC");
		
		socio.setRfc(rfc);
		
		SujetoObligado sujetoObligado = new SujetoObligado();
		TipoPersonaFiscal tipoPersonaFiscal = obtenerTipoPersonaFiscal(rfc);
		
		sujetoObligado.setTipoPersonaFiscal(tipoPersonaFiscal);
		boolean bFisica = tipoPersonaFiscal.equals(TipoPersonaFiscal.FISICA);
		if (bFisica) {
			Fisica pFisica = new Fisica();
			pFisica.setRfc(rfc);
			usuario.setFisica(pFisica);
			sujetoObligado.setFisica(pFisica);
		} else {
			Moral pMoral = new Moral();
			pMoral.setRfc(rfc);
			usuario.setMoral(pMoral);
			sujetoObligado.setMoral(pMoral);
		}
		List<SujetoObligado> sujetosObligados;
		try {
			sujetosObligados = sujetoObligadoService
					.obtenerDetalleSujetoObligado(sujetoObligado);
			sujetoObligado=sujetosObligados.get(0);
			
			if (bFisica) {
				session.setAttribute("cveIdPatronSO", sujetoObligado.getFisica().getIdPersona());
				usuario.setFisica(sujetoObligado.getFisica());
			} else {
				session.setAttribute("cveIdPatronSO", sujetoObligado.getMoral().getIdPersona());
				usuario.setMoral(sujetoObligado.getMoral());
			}
			session.setAttribute("usuario", usuario);
			sujetoObligado.setSujetosObligados(sujetosObligados);
		} catch (AbstractException e) {
			e.printStackTrace();
			return "buscar.patron.rfc";
		}
		
		model.addAttribute("sujetoObligado", sujetoObligado);
		model.addAttribute("bFisica", bFisica);
		
		model.addAttribute("isOperadosIMSS",usuario.getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue()));
		session.setAttribute("sujetoTramiteForRepLegal", sujetoObligado);
		Solicitud solicitudActiva = solicitudServiceBusiness.obtenerSolicitudActiva(sujetoObligado, 
			TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, usuario, null);
		model.addAttribute("existeSolicitudActiva", solicitudActiva!=null);

		return "mostrar.so.afiliacion";
	}
	
	
	
	
	private TipoPersonaFiscal obtenerTipoPersonaFiscal(String rfc) {
		System.err.println("Se proceso el RFC: "+rfc);
		
		if (rfc.length() == 13)
			return TipoPersonaFiscal.FISICA;
		else if (rfc.length() == 12)
			return TipoPersonaFiscal.MORAL;
		else
			return null;
	}
	
}
