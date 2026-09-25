package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

public class RegistroPersonaController extends AbstractController {

    @Autowired
    private transient SolicitudPersonaBusinessRemote solicitudPersonaBusiness;

    private transient Integer cveStatusPersona;
    private transient String statusPersona;

    /**
     * 
     * @param role
     * @param personaLst
     * @param model2
     * @return folio de la solicitud
     */
    protected void registrarPersona(final String role, final List<? extends AbstractModel> personaLst, final Model model2) throws Exception {
        LOG.trace("Role de usuario: " + role);
        if (personaLst == null || personaLst.isEmpty()) {
            model2.addAttribute("errorMsg", "No se ha generado la solicitud debido a que no existen tramites en la misma (no se permiten solicitudes vacias)");
        } else {
            final Solicitud solicitud = new Solicitud();
            solicitud.setUsuario("USER-K10"); // TODO To change User here!

            int i = 0;
            LOG.debug("Personas en tramites en la solicitud: ");
            for (AbstractModel model : personaLst) {
            	log.debug("persona[" + i + "] --> " + ReflectionToStringBuilder.toString((Persona)model, ToStringStyle.MULTI_LINE_STYLE));
            	solicitud.getTramite().add(createTramiteAltaPersona(role, (Persona)model));
            	i ++;
            }

            final Solicitud solicitudEnt = solicitudPersonaBusiness.procesarSolicitudNueva(solicitud);
            personaLst.clear();
            LOG.debug("solicitudRespuesta: " + solicitudEnt);
            model2.addAttribute("folio", solicitudEnt.getIdSolicitud());
        }
    }

    private Tramite createTramiteAltaPersona(final String role, final Persona model) {
        final TipoTramite tipoTramite = new TipoTramite();
        final Tramite tramiteAltaPersona = new Tramite();

        setStatusPersona(role);

        if (model instanceof Fisica) {
            final Fisica persona = (Fisica) model;
            tramiteAltaPersona.setPersonaFisica(persona);
            tipoTramite.setIdTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoTramite.REGISTRO_PERSONA.longValue());
            tipoTramite.setDesTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoTramite.TIPO_TRAMITE_1_REGISTRO_PERSONA);
        } else if (model instanceof Moral) {
            final Moral persona = (Moral) model;
            tramiteAltaPersona.setPersonaMoral(persona);
            tipoTramite.setIdTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoTramite.REGISTRO_PERSONA.longValue());
            tipoTramite.setDesTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoTramite.TIPO_TRAMITE_1_REGISTRO_PERSONA);
        }

        Calificacion calificacion = model.getPersonaCalificaciones().get(0).getCalificacion();
        int calificacionId = calificacion.getIdCalificacion().intValue();
        
        if (CalificacionPersona.VALIDADO_SAT.intValue() != calificacionId && CalificacionPersona.VALIDADO_RENAPO.intValue() != calificacionId) {
            calificacion.setIdCalificacion(cveStatusPersona.longValue());
            calificacion.setDescripcion(statusPersona);
        }

        tramiteAltaPersona.setIdTramite(null);
        tramiteAltaPersona.setTipoTramite(tipoTramite);
        return tramiteAltaPersona;
    }

    private void setStatusPersona(final String role) {
        if ("ventanilla".equalsIgnoreCase(role)) {
            cveStatusPersona = CalificacionPersona.VALIDADO_IMSS;
            statusPersona = CalificacionPersona.CALIFICACION_3_VALIDADO_IMSS;
        } else if ("internet".equalsIgnoreCase(role)) {
            cveStatusPersona = CalificacionPersona.NO_VALIDADO;
            statusPersona = CalificacionPersona.CALIFICACION_4_NO_VALIDADO;
        } else {
            LOG.info("Opcion no valida de role. Se usara usuario de internet");
            cveStatusPersona = CalificacionPersona.NO_VALIDADO;
            statusPersona = CalificacionPersona.CALIFICACION_4_NO_VALIDADO;
        }
    }

    protected static final String REG_REPETIDO_MSG;
    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(RegistroPersonaController.class);
        REG_REPETIDO_MSG = "La persona que ha intentado anexar a la solicitud ya se encontraba registrada";
    }

}
