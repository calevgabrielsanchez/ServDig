package mx.gob.imss.cdsss.delta.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cdsss.delta.portal.controller.validator.DomicilioUmfValidator;
import mx.gob.imss.cdsss.delta.portal.utils.Constants;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CambioClinicaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.UmfDomicilioDTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "derechohabientes/tramite/cambioClinica")
public class CambioClinicaDomController extends AbstractController {

    private final Log log = LogFactory.getLog(getClass());

    private final String MENSAJE_CANCELACION_SOLICITUD = "Se cancela la solicitud debido a que se inicia una nueva en portal ciudadano";

    @Autowired
    private CambioClinicaServiceRemote cambioClinicaServiceRemote;

    @Autowired
    private SolicitudBusinessRemote solicitudBusinessRemote;

    @Autowired
    private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;

    @Autowired
    private GeneraDocumentosAsincronos generaDocumentosAsincronos;

    @Autowired
    private DocumentosServiceRemote documentosServiceRemote;

    @RequestMapping(value = "/inicio")
    public String inicio(Model model, HttpSession session, HttpServletRequest request){

        GrupoFamiliar asegurado = (GrupoFamiliar) session.getAttribute(Constants.KEY_DATOS_ASEGURADO);

        //mandamos el titulo del tramite
        model.addAttribute("tituloTramite", "derechohabientes.tramite.cambioClinica.title");

        //return "redirect:"+SELECCION_UMF_PATH;
        return "seleccionUmfConDomicilio";
    }

    @RequestMapping(value = "/validacionesCP", method = RequestMethod.POST)
    public @ResponseBody Map<String, ?> validarCodigoPostal(final @RequestBody UmfDomicilioDTO oForm, final HttpServletResponse response, final HttpSession session) {

        Map<String, Object> result = new HashMap<String, Object>();
        final Errors errors = new BindException(oForm, "model");
        new DomicilioUmfValidator().validateCodigoPostal(oForm, errors);

        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ?> validarFormulario(final @RequestBody UmfDomicilioDTO oForm, final HttpServletResponse response, HttpSession session, Model model) {
        log.trace("entramos a WizardRegistroDerechohabiente para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));

        //Obtenemos los datos de session
        GrupoFamiliar asegurado = (GrupoFamiliar) session.getAttribute(Constants.KEY_DATOS_ASEGURADO);
        AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(Constants.KEY_ASIGNACION_NSS);

        Map<String, Object> result = new HashMap<String, Object>();
        boolean exito = true;
        String mensaje = "";
        final Errors errors = new BindException(oForm, "model");

        new DomicilioUmfValidator().validateVersionDomicilio(oForm, errors);

        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        } else {
            try {
                    //Verificamos la existencia de solicitud
                    Map<String, Object> requisitos = requisitosMinimosServiceRemote.obtenerSolicitudCambioClinica(asignacionNSS, OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
                    if (requisitos != null && requisitos.get("solicitudActiva") != null) {
                        Solicitud solicitudActiva = (Solicitud) requisitos.get("solicitudActiva");
                        this.cancelarSolicitud(solicitudActiva, MENSAJE_CANCELACION_SOLICITUD);
                    }

                    //Revisamos si se está cambiando a la misma clínica, de ser así se detiene el tramite
                    if(oForm.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF().equals(asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF())){
                        exito = false;
                        mensaje = "La cl&iacute;nica localizada con el c&oacute;digo postal ingresado, es la misma que ya tiene registrada.";
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    exito = false;
                    mensaje = e.getMessage();
                }
        }

        result.put("correcto", exito);
        result.put("mensaje", mensaje);
        return result;
    }

    @RequestMapping( value = "/finalizar", method = RequestMethod.POST)
    public @ResponseBody Map<String, ?>  finalizarCambioClinica(@RequestBody UmfDomicilioDTO oForm, HttpSession session, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();
        boolean correcto = true;
        String mensaje = "";

        //Obtenemos los datos de session
        CabezaGrupoFamiliar cabeza = (CabezaGrupoFamiliar) session.getAttribute(Constants.KEY_CABEZA_GRUPO);
        GrupoFamiliar asegurado = (GrupoFamiliar) session.getAttribute(Constants.KEY_DATOS_ASEGURADO);
        AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute(Constants.KEY_ASIGNACION_NSS);
        Solicitud solicitud = null;


        TramiteCorreccionDerechohabiente derechohabiente = llenarTramiteRegistro(session, asegurado, asignacionNSS, oForm);

        try {

            //Se buscan a los paddres y concubinas del asegurado
            List<GrupoFamiliar> padresConcubinas = cambioClinicaServiceRemote.getPadresConcubinasParaCambio(asignacionNSS, cabeza.getPatronImss());
            Usuario usuario = new Usuario();
            usuario.setUsuario(asignacionNSS.getCurp());
            solicitud = cambioClinicaServiceRemote.crearSolicitudCambioClinica(derechohabiente, asegurado, asignacionNSS, cabeza,
                    false, padresConcubinas, usuario, OrigenSolicitudEnum.PORTAL_CIUDADANO);


            //verificaremos si es necesarios realizar el cambio de medico en la clinica destino
            Map<String, Object> validacionesCambio = cambioClinicaServiceRemote.getFechaCambioYDatosCambioMedico(
                    asignacionNSS, derechohabiente.getMedicoEnTurno(), derechohabiente.getCandidatosCambioClinica()
                    , false, null, false);

            //finalizamos la solicitud de cambio de clinica
            solicitud = cambioClinicaServiceRemote.finalizaSolicitudCambioClinica(solicitud, asignacionNSS, cabeza, validacionesCambio);

            session.setAttribute(Constants.KEY_SOLICITUD, solicitud);
            session.setAttribute(Constants.KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
            session.setAttribute(Constants.KEY_DATOS_DOM_UMF, oForm);

            //generamos lo documentos necesarios
            generaDocumentosAsincronos.generaDocumentosAsincrono(solicitud, session);

            if(solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
                for(Tramite t : solicitud.getTramites()){
                    if(t.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo())){
                        //Generamos el reporte de cambio de cl?nica
                        TramiteCorreccionDerechohabiente tramite = (TramiteCorreccionDerechohabiente)t;

                        tramite.setMedicoEnTurnoNuevo(oForm.getMedicoEnTurno());
                        tramite.setDomicilioAnterior(asegurado.getDomicilio());
                        tramite.setDomicilio(oForm.getDomicilio());
                        byte[] docto = (byte[])documentosServiceRemote.getDocumentoCambioClinica(solicitud.getNoFolioSolicitud(),tramite.getTipoTramite().getDescripcion(),solicitud.getFirmaElectronica(), tramite);
                        
                        session.setAttribute(Constants.KEY_ACUSE_TRAMITE, docto);
                        break;
                    }
                }
            }
        } catch (DerechohabientesBusinessException e) {
            correcto = false;
            mensaje = e.getMessage();
            errorAlFinalizar(solicitud, e);
        } catch (ImpactaAlmacenesWSException e) {
            correcto = false;
            mensaje = e.getMessage();
            errorAlFinalizar(solicitud, e);
        } catch (Exception e) {
            correcto = false;
            mensaje = e.getMessage();
            errorAlFinalizar(solicitud, e);
        }

        result.put("correcto",correcto);
        result.put("mensaje",mensaje);

        return result;
    }

    private void errorAlFinalizar(Solicitud solicitud, Exception e){
            log.error("Error al finalizar el tramite cambio de clinica", e);
            if(solicitud != null){
                this.cancelarSolicitud(solicitud, e.getMessage());
            }
    }

    private void cancelarSolicitud(Solicitud solicitud, String observaciones) {

        if(solicitud != null && solicitud.getSolicitudId() != null) {
            try {
                solicitudBusinessRemote.cancelarSolicitud(solicitud.getSolicitudId(), 5L,1L, null, observaciones);
            } catch (SolicitudException e) {
                e.printStackTrace();
            }
        }
    }

    private String procesarError(Model model,String mensaje) {
        Fisica fisica = new Fisica();
        fisica.setErrorFormGeneral(mensaje);
        model.addAttribute("fisica", fisica);

        return "derechohabientes";

    }

    private TramiteCorreccionDerechohabiente llenarTramiteRegistro(HttpSession session, GrupoFamiliar asegurado, AsignacionNSS asignacionNSS, UmfDomicilioDTO oForm) {
        TramiteCorreccionDerechohabiente tramite = new TramiteCorreccionDerechohabiente();

        tramite.setIdPersona(asegurado.getDerechohabiente().getIdPersona());

        tramite.setDatosAsegurado(asignacionNSS);
        //Establecemos los datos de la persona a registrar
        tramite.setFisica(asignacionNSS);

        tramite.setUsuario(new Usuario());
        tramite.getUsuario().setUsuario(asignacionNSS.getCurp());

        tramite.setPaso(1L);

        TipoTramite tipoTramite = new TipoTramite();
        tipoTramite.setIdTipoTramite(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo());
        tramite.setTipoTramite(tipoTramite);

        tramite.setMedicoEnTurno(oForm.getMedicoEnTurno());
        tramite.setDomicilio(oForm.getDomicilio());

        return tramite;
    }
}
