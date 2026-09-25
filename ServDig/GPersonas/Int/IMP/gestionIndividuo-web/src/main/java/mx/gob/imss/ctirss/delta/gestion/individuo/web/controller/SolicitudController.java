/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:SolicitudController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.individuo.web.controller
 *  @Fecha:22/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.RazonRechazoTramite;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Lucio Duran Silva
 * @author Cesar Garcia Mauricio
 * 
 */
@Controller
@RequestMapping(value = "/solicitud")
public class SolicitudController extends AbstractController {

    @Autowired
    private transient SolicitudPersonaBusinessRemote solicitudPersonaBusiness; // NOPMD
    private static final String COMMAND_NAME = "solicitud";

	@RequestMapping(value = "/validar", method = RequestMethod.GET)
	public String validar(final HttpSession session, HttpServletRequest request) {
		session.setAttribute("origen", "bs");

		UsuarioSSO sso = this.procesarUsuarioSSO(request);

		/*
		 * Procesamos la información del openSSO
		 */
		if (sso != null) {
			ServletContext context = session.getServletContext();
			String rolVentanilla = context.getInitParameter("rolVentanilla");
	        String rolInternet = context.getInitParameter("rolInternet");
	        String[] arrayRolVentanilla = {rolVentanilla};
	        String[] arrayRolInternet = {rolInternet};

			Usuario usuario = new Usuario();
			usuario.setUsuario(sso.getNombre());

			PerfilUsuario pu = new PerfilUsuario();
			if (this.checkGrantedAuthorities(arrayRolVentanilla)) {
				pu.setIdPerfilUsuario(100L);
			} else if (this.checkGrantedAuthorities(arrayRolInternet)) {
				pu.setIdPerfilUsuario(200L);
			}
			pu.setDescripcion(sso.getPerfil());
			usuario.setPerfilUsuario(pu);

			UsuarioFuncionario uf = new UsuarioFuncionario();
			if (sso.getDelegacion() != null) {
				uf.setDelegacion(new Delegacion());
				uf.getDelegacion().setId(sso.getDelegacion().longValue());
			}
			if (sso.getSubdelegacion() != null) {
				usuario.setCveIdSubdelegacion(sso.getSubdelegacion().longValue());
				uf.setSubdelegacion(new Subdelegacion());
				uf.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			}
			uf.setUsuario(usuario);
			usuario.setUsuarioFuncionario(uf);

			// Subimos a la sesion la informacion del usuario
			session.setAttribute(KEY_USUARIO, usuario);
			this.setFechaSistema(session);
		}

		return "busquedaSolicitud";
	}

    @RequestMapping(value = "/busqueda/{idSolicitud}", method = RequestMethod.GET)
    public ModelAndView buscarGet(final @PathVariable String idSolicitud) {
        return buscar(idSolicitud);
    }

    @RequestMapping(value = "/busqueda/{idSolicitud}", method = RequestMethod.POST)
    public ModelAndView buscar(final @PathVariable String idSolicitud) {
        log.debug("/buscar idSolicitud: " + idSolicitud);
        final ModelAndView mav = new ModelAndView();
        String vista;
        Long idSol = null; // NOPMD
        try {
            idSol = Long.valueOf(idSolicitud);
        } catch (NumberFormatException e) {
            final String msgFolioNotNumber = "Folio no valido " + idSolicitud;
            log.error(msgFolioNotNumber);
            mav.addObject("mensaje", msgFolioNotNumber);
            mav.setViewName("mensajes");
            return mav;
        }
        Solicitud solicitudPers = null;
        try {
            solicitudPers = solicitudPersonaBusiness.getSolicitud(idSol);
        } catch (SolicitudNoEncontradaException e) {
            mav.addObject("mensaje", e.getMessage());
            mav.setViewName("mensajes");
            return mav;
        }
        log.debug("resultado busqueda solicitud: " + solicitudPers);

        mav.addAllObjects(getModel(solicitudPers));
        if (mav.getModel().containsKey("statusMsg")) {
            mav.addObject("mensaje", mav.getModel().get("statusMsg"));
            vista = "mensajes";
        } else {
            vista = "resultadoBusquedaSolicitud";
        }

        mav.setViewName(vista);
        log.info("Redirige a vista " + vista);
        return mav;
    }

    private Map<String, ?> getModel(final Solicitud solicitud) {

        final Map<String, Object> model = new HashMap<String, Object>();

        if (solicitud != null) {
            solicitud.setFechaRegistroFormateada(fechaRegistroSolicitudFormat.format(solicitud.getFechaRegistro()));
            String statusMsg = null;
            if (EstadoSolicitud.REGISTRADA.equals(solicitud.getIdEstadoSolicitud())) {
                statusMsg = "La solicitud " + solicitud.getIdSolicitud() + " no est\u00E1 lista para procesarse";
            } else if (EstadoSolicitud.EN_PROCESO.equals(solicitud.getIdEstadoSolicitud())) {
                model.putAll(procesarSolicitudValidada(solicitud));
            } else if (EstadoSolicitud.ATENDIDA.equals(solicitud.getIdEstadoSolicitud())) {
                statusMsg = "La solicitud " + solicitud.getIdSolicitud() + " ya fue Atendida";
            } else if (EstadoSolicitud.CANCELADA.equals(solicitud.getIdEstadoSolicitud())) {
                statusMsg = "La solicitud " + solicitud.getIdSolicitud() + " fue cancelada";
            } else {
                statusMsg = "Estado de solicitud no contemplado: " + solicitud.getIdEstadoSolicitud();
                log.warn("Estado de solicitud no contemplado: " + solicitud.getIdEstadoSolicitud());
            }

            if (statusMsg != null) {
                model.put("statusMsg", statusMsg);
            }

        }

        return model;

    }

    private Map<String, Object> procesarSolicitudValidada(final Solicitud solicitud) {
        final Map<String, Object> model = new HashMap<String, Object>();
        final List<Tramite> tramites = solicitud.getTramite();
        final List<Tramite> tramitesPendientes = new ArrayList<Tramite>();
        String tipoPersona = null;
        for (Tramite tramite : tramites) {
            if (esTramitePendiente(tramite)) {
                if (tramite.getPersonaFisica() != null) {
                    tramite.getPersonaFisica().setFechaNacimientoFormateada(formatFechaNacimiento(tramite.getPersonaFisica().getFechaNacimiento()));
                } else if (tramite.getPersonaMoral() != null) {
                    tramite.getPersonaMoral().setFechaCreacionFormateada(formatFechaNacimiento(tramite.getPersonaMoral().getFechaCreacion()));
                } else {
                    log.warn("No existe persona asociada al tramite!");
                }
                tramitesPendientes.add(tramite);
            }
            if (tramite.getPersonaFisica() != null) {
                tipoPersona = "fisica";
            } else if (tramite.getPersonaMoral() != null) {
                tipoPersona = "moral";
            } else {
                log.info("Tipo de persona no definido");
            }
        }

        solicitud.getTramite().clear();
        if (tramitesPendientes.isEmpty()) {
            model.put("msgSolicitudSinTramitesPorValidar", "No existen tr\u00E1mites por validar para esta solicitud");
            model.put("hayTramitesPendientes", Boolean.FALSE);
        } else {
            model.put("hayTramitesPendientes", Boolean.TRUE);
            solicitud.getTramite().addAll(tramitesPendientes);
        }

        model.put(COMMAND_NAME, solicitud);
        model.put("tipoPersona", tipoPersona);
        return model;
    }

    private Boolean esTramitePendiente(final Tramite tramite) {
        Boolean esTramitePendiente;
        if (tramite == null) {
            esTramitePendiente = Boolean.FALSE;
        } else {
            final Boolean esTramiteRegistrado = EstadoTramite.REGISTRADO.equals(tramite.getIdEstadoTramite());
            Boolean esPersonaFisicaNoValidada = Boolean.FALSE;
            if (tramite.getPersonaFisica() != null && tramite.getPersonaFisica().getPersonaCalificaciones() != null && !tramite.getPersonaFisica().getPersonaCalificaciones().isEmpty()) {
                log.debug("La calificacion de la persona es " + tramite.getPersonaFisica().getPersonaCalificaciones().get(0).getCalificacion());
                esPersonaFisicaNoValidada = CalificacionPersona.NO_VALIDADO.equals(Utilerias.convertir(tramite.getPersonaFisica().getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion()));
            }
            Boolean esPersonaMoralNoValidada = Boolean.FALSE;
            if (tramite.getPersonaMoral() != null && tramite.getPersonaMoral().getPersonaCalificaciones() != null && !tramite.getPersonaMoral().getPersonaCalificaciones().isEmpty()) {
                log.debug("La calificacion de la persona es " + tramite.getPersonaMoral().getPersonaCalificaciones().get(0).getCalificacion()); 
                esPersonaMoralNoValidada = CalificacionPersona.NO_VALIDADO.equals(Utilerias.convertir(tramite.getPersonaMoral().getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion()));
            }
            final Boolean esPersonaNoValidada = esPersonaFisicaNoValidada || esPersonaMoralNoValidada;
            esTramitePendiente = esTramiteRegistrado && esPersonaNoValidada;
            log.trace("esTramiteRegistrado: " + esTramiteRegistrado);
            log.trace("esPersonaNoValidada: " + esPersonaNoValidada);
        }
        log.trace("esTramitePendiente: " + esTramitePendiente);
        return esTramitePendiente;
    }

    // TODO este es el mismo metodo que el que esta en
    // RegistroPersonaFisicaController: hay que unificarlos y ponerlos en otra
    // clase de utilerias...
    private String formatFechaNacimiento(final Date fechaNacimiento) {
        String fechaNacimientoStr = null; // NOPMD
        if (fechaNacimiento != null) {
            fechaNacimientoStr = formatoFechaNacimiento.format(fechaNacimiento);
        }
        return fechaNacimientoStr;
    }

    @RequestMapping(value = "/procesar_tramites", method = RequestMethod.POST)
    public String procesarTramites(final @ModelAttribute Solicitud solicitudForm, final BindingResult result, final Model model) throws Exception {
        log.debug("solicitudForm:\n" + solicitudForm);

        String viewName = null;
        if (result.hasErrors()) {
            log.debug("ERRORES DE VALIDACION:\n " + result);
            model.addAttribute("statusMsg", "");
            viewName = "resultadoBusquedaSolicitud";
        } else {
            Solicitud solicitud = null;
            try {
                solicitud = solicitudPersonaBusiness.getSolicitud(solicitudForm.getIdSolicitud());
            } catch (SolicitudNoEncontradaException e) {
                model.addAttribute("mensaje", e.getMessage());
                return "mensajes";
            }
            
            
            try{
            	
            
            
            log.debug("Solicitud Desde BD:\n" + solicitud);
            // Copia valores de formulario hacia el XML del objeto solicitud
            // recuperado:
            final List<Tramite> tramitesForm = solicitudForm.getTramite();
            final List<Tramite> tramites = solicitud.getTramite(); // NOPMD

            for (Tramite tramiteForm : tramitesForm) {
                updateTramite(getTramiteById(tramites, tramiteForm.getIdTramite()), tramiteForm);
            }

            solicitud.setIdEstadoSolicitud(EstadoSolicitud.REGISTRADA);
            solicitud.setDesEstadoSolicitud(EstadoSolicitud.ESTADO_1_REGISTRADA);

            log.debug("Solicitud Actualizada:\n" + solicitud);

            solicitudPersonaBusiness.procesarSolicitudExistente(solicitud);

            model.addAttribute("mensajeExito", "La solicitud con el folio " + solicitudForm.getIdSolicitud() + " se proces\u00f3 correctamente");
            
            viewName = "busquedaSolicitud";
            }catch (Exception e) {
            	this.log.error(e.getMessage(), e);
            	
			}
        }

        return viewName;
    }

    private void updateTramite(final Tramite tramite, final Tramite tramiteForm) {
        // Copia valores de formulario (tramiteForm) hacia el tramite recuperado
        tramite.setObservacion(tramiteForm.getObservacion());
        if (tramite.getPersonaFisica() != null && tramite.getPersonaFisica().getPersonaCalificaciones() != null && !tramite.getPersonaFisica().getPersonaCalificaciones().isEmpty()) {
            tramite.getPersonaFisica().getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(CalificacionPersona.VALIDADO_IMSS.longValue());
            tramite.getPersonaFisica().getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_3_VALIDADO_IMSS);
        } else if (tramite.getPersonaMoral() != null && tramite.getPersonaMoral().getPersonaCalificaciones() != null && !tramite.getPersonaMoral().getPersonaCalificaciones().isEmpty()) {
            tramite.getPersonaMoral().getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(CalificacionPersona.VALIDADO_IMSS.longValue());
            tramite.getPersonaMoral().getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_3_VALIDADO_IMSS);
        } else {
            log.warn("Tramite sin persona o null person.");
        }
        if (Boolean.parseBoolean(tramiteForm.getTramitadorValidaDatos())) {
            tramite.setIndResultado(1L);
        } else {
            // Persona rechazada:
            tramite.setIndResultado(0L);
            tramite.setIdRazonResultado(tramiteForm.getIdRazonResultado());
            log.debug("IdRazonResultado: " + tramiteForm.getIdRazonResultado());
            if (tramiteForm.getIdRazonResultado() == RazonRechazoTramite.DOCUMENTOS_INCOMPLETOS_CVE) {
                tramite.setDesRazonResultado(RazonRechazoTramite.DOCUMENTOS_INCOMPLETOS);
            } else if (tramiteForm.getIdRazonResultado() == RazonRechazoTramite.DOCUMENTOS_APOCRIFOS_CVE) {
                tramite.setDesRazonResultado(RazonRechazoTramite.DOCUMENTOS_APOCRIFOS);
            } else if (tramiteForm.getIdRazonResultado() == RazonRechazoTramite.IMPROCEDENCIA_CVE) {
                tramite.setDesRazonResultado(RazonRechazoTramite.IMPROCEDENCIA);
            } else if (tramiteForm.getIdRazonResultado() == RazonRechazoTramite.SOLICITUD_CANCELADA_CVE) {
                tramite.setDesRazonResultado(RazonRechazoTramite.SOLICITUD_CANCELADA);
            } else {
                log.info("Razon de rechazo no contemplada: " + tramiteForm.getIdRazonResultado());
            }

            log.debug("DesRazonResultado: " + tramite.getDesRazonResultado());

            tramite.setIdEstadoTramite(EstadoTramite.CERRADO);
            tramite.setDesEstadoTramite(EstadoTramite.ESTADO_3_CERRADO);
        }
    }

    private Tramite getTramiteById(final List<Tramite> tramites, final Long idTramite) {
        Tramite result = null; // NOPMD
        for (Tramite tramite : tramites) {
            if (idTramite.equals(tramite.getIdTramite())) {
                result = tramite;
                break;
            }
        }
        return result;
    }

    private transient final DateFormat formatoFechaNacimiento = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "mx"));
    private transient final DateFormat fechaRegistroSolicitudFormat = new SimpleDateFormat("dd/MM/yyyy");
    // TODO considerar usar al mismo DateFormat para esto de arriba...

}
