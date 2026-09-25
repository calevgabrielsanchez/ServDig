/*
 * Esta clase es el controller encargada de administrar las peticiones de los tramites de cambio de: 
 * ASIGNACION_DE_DOMICILIO_PARTICULAR_DH (101), 
 * ACTUALIZACION_DOMICILIO_PARTICULAR(6),
 * CAMBIO_CLINICA(36)
 * La funcionalidad que cubre este Controller es:
 * 1.Obtener informacion del derechohabiente
 * 2.Iniciar tramite
 * 3.Retomar
 * 4.Guardar
 * 5. Finalizar
 * 6. Cancelar
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.FinalizaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.PatronServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.CodigoSinUmfException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.SolicitudesEnProcesoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.AsentamientoNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesModificacionException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.ArgumentosInvalidosException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudEnProcesoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller.validator.DomiciliosValidator;
import mx.gob.imss.ctirss.delta.gestion.domicilio.web.utils.CommonValidator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.PasoRegistroEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.web.validator.AsentamientoValidator;
import mx.gob.imss.ctirss.delta.web.validator.DomicilioConcluirValidator;
import mx.gob.imss.ctirss.delta.web.validator.DomicilioValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/wizard/domicilio/")
public class WizardDomicilioClinicaDerechohabienteController extends AbstractController {

    @Autowired
    private CorreccionDerechohabienteServiceRemote correccionDerechohabienteService;
    @Autowired
    private DerechohabienteServiceRemote derechohabienteServiceRemote;
    @Autowired
    private SolicitudBusinessRemote solicitudBusinessRemote;
    @Autowired
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
    @Autowired
    private DomicilioServiceBusinessRemote domicilioServiceBusiness;
    @Autowired
    private GrupoFamiliarServiceRemote grupoFamiliarService;
    @Autowired
    private PatronServiceRemote patronService;
    @Autowired
    private PersonaBusinessRemote personaBusiness;
    @Autowired
    private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
    @Autowired
    private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
    @Autowired
    private UmfServiceRemote umfService;
    @Autowired
    private FinalizaSolicitudServiceRemote finalizaSolicitudService;


    // Variables de la session
    private final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
    private final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
    private final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
    private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
    private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
    private static final String KEY_DOMICILIO_EXISTENTE = "KEY_DOMICILIO_EXISTENTE";
    private static final String KEY_ASENTAMIENTO = "ASENTAMIENTO";
    private static final String KEY_CAPTURA_NUEVO_DOMICLIO = "nuevoDomicilio";

    // variables validar persona
    private static final String KEY_DOMICILIOS_PERSONA_INTERESADA = "KEY_DOMICILIOS_PERSONA_INTERESADA";
    private static final String KEY_DOMICILIOS_PERSONA = "KEY_DOMICILIOS_PERSONA";
    private static final String KEY_DOMICILIOS_ID_TIPO_TRAMITE = "KEY_DOMICILIOS_ID_TIPO_TRAMITE";
    private static final String KEY_RFC_SOLICITANTE = "rfcPersona";
    private static final String KEY_DOMICILIOS_NUEVO = "KEY_DOMICILIOS_NUEVO";
    private static final String KEY_SOLICITUD = "solicitudRegistro";
    private static final String KEY_DOMICILIOS_GRUPO_FAMILIAR = "KEY_DOMICILIOS_GRUPO_FAMILIAR";
    private static final String KEY_DOMICILIOS_TRAMITE_CLINICA = "KEY_DOMICILIOS_TRAMITE_CLINICA";
    private static final String KEY_REQUIERE_DOCS = "requiereDocs";

    // vistas
    private static final String VIEW_INICIAL = "wizardDomicilioGeneralInit";
    private static final String VIEW_ELECCION_ACTUALIZACION = "wizardDomicilioTipoActualizacion";
    private static final String VIEW_CONTENIDO = "wizardDomicilioGeneralContent";
    private static final String VIEW_DIRECCION = "wizardDomicilioGeneralAbrirDireccion";


    /**
     *  Retoma una solicitud previamente guardada
     *
     *
     * @param model
     * @param solicitud
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/retomar")
    public Object retomarSolicitud(Model model,@ModelAttribute Solicitud solicitud,
                                   HttpSession session, HttpServletRequest request) {


        try {


            // ----------------------------------------------------------
            // Tipo de tramite solicitado al wizard
            // ----------------------------------------------------------
            Long idTipoTramite = (Long)session.getAttribute(KEY_DOMICILIOS_ID_TIPO_TRAMITE);
            requiereDocumentos(session, idTipoTramite);
            // -------------------------------------
            // Consulta de solicitud
            // -------------------------------------
            solicitud = solicitudBusinessRemote.consultar(solicitud);


            if (solicitud != null) {

                Tramite tramite = solicitud.getTramites().get(0);

                if( tramite.getTipoTramite().getIdTipoTramite().longValue() != idTipoTramite )
                    throw new ArgumentosInvalidosException();

                this.validaTipoTramitesPermitidos( idTipoTramite );

                session.setAttribute("FROM_WIZARD",true);
                session.setAttribute(KEY_SOLICITUD, solicitud);
                model.addAttribute("isRetomar", true);
                datosToSession(session, tramite.getTipoTramite().getIdTipoTramite().longValue());

                // -----------------------------------------------------------------
                // Si salio al capturar los datos de cp y asentamiento
                // -----------------------------------------------------------------
                if( tramite instanceof TramiteFisica )
                    return new ModelAndView("forward:/domicilio/nacional/ubicar");


                // ----------------------------------------------------------
                // Se llego a la pantalla de captura de domicilio
                // ----------------------------------------------------------
                TramiteCorreccionDerechohabiente tramiteCorreccion = (TramiteCorreccionDerechohabiente)tramite;

                Domicilio domicilio = tramiteCorreccion.getDomicilio();

                // ---------------------------------------------------------
                // El domicilio guardado en el tramite es el nuevo
                // ---------------------------------------------------------
                if( domicilio != null ){
                    // -------------------------------------------------------------------
                    // Asignamos el domicilio guardado para que se muestren en la forma
                    // los valores guardados previamente
                    // -------------------------------------------------------------------
                    session.setAttribute(KEY_DOMICILIOS_NUEVO, domicilio);
                }


                // ------------------------------------------------
                // Captura de domicilio
                // ------------------------------------------------
                if( tramiteCorreccion.getPaso().equals(PasoRegistroEnum.CAPTURA_DOMICILIO.getId()) )
                    return new ModelAndView("forward:/wizard/domicilio/porCodigoPostal");


                // ----------------------------------------------------------
                // Cambio de clinica, documentos probatorios, firma digital
                // ----------------------------------------------------------
                if( tramiteCorreccion.getPaso().equals(PasoRegistroEnum.CAPTURA_UMF.getId()) )
                    return new ModelAndView("forward:/wizard/domicilio/siguiente");


                request.setAttribute("error",tramiteCorreccion.getPaso());

            } else {
                request.setAttribute("error","No fue posible recuperar la solicitud");
            }




        } catch( ArgumentosInvalidosException e ){
            request.setAttribute("error","El tipo de tramite es invalido");
        } catch (Exception e) {
            request.setAttribute("error","No fue posible recuperar la solicitud");
        }


        model.addAttribute("cambioClinica", false);
        model.addAttribute("domicilio", new Domicilio());
        model.addAttribute("isRetomar", false);
        model.addAttribute("solicitudForm", new Solicitud());


        log.error("AQUI ====================================================");

        return VIEW_CONTENIDO;

    }



    /**
     *
     * Guarda el tramite en la base de datos
     *
     * @param tramite contiene la informacion capturada en pantalla
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> guardarSolicitud(
            @RequestBody TramiteCorreccionDerechohabiente tramiteCorreccion, HttpSession session, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();
        Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
        Tramite tramite = solicitud.getTramites().get(0);

        // --------------------------------------------------------------
        // Si la persona selecciono un nuevo domicilio valido
        // --------------------------------------------------------------
        if( (tramiteCorreccion.getDomicilio() == null) && (session.getAttribute(KEY_DOMICILIOS_NUEVO) != null) )
            tramiteCorreccion.setDomicilio((Domicilio)session.getAttribute(KEY_DOMICILIOS_NUEVO));


        // ------------------------------------------------
        // Si no es retomar
        // ------------------------------------------------
        if( tramite instanceof TramiteFisica ){

            // -------------------------------------------------------------------------------
            // La diferencia entre un tramite y un tramiteFisica son los MDMDatosEntrada
            // -------------------------------------------------------------------------------
            TramiteFisica tramiteFisica = (TramiteFisica) solicitud.getTramites().get(0);

            // ---------------------------------------------------------------------
            // Se asignan los campos generados durante la creacion de la solicitud
            // ---------------------------------------------------------------------
            tramiteCorreccion.setPersona(tramiteFisica.getFisica());
            tramiteCorreccion.setFechaTramite(tramiteFisica.getFechaTramite());
            tramiteCorreccion.setFechaPresentacion(tramiteFisica.getFechaPresentacion());
            tramiteCorreccion.setEstadoTramite(tramiteFisica.getEstadoTramite());
            tramiteCorreccion.setTipoTramite(tramiteFisica.getTipoTramite());
            tramiteCorreccion.setTramiteId(tramiteFisica.getTramiteId());

        }else if( tramite instanceof TramiteCorreccionDerechohabiente ){

            TramiteCorreccionDerechohabiente _tramiteCorreccion = (TramiteCorreccionDerechohabiente)tramite;

            tramiteCorreccion.setPersona(_tramiteCorreccion.getPersona());
            tramiteCorreccion.setFechaTramite(_tramiteCorreccion.getFechaTramite());
            tramiteCorreccion.setFechaPresentacion(_tramiteCorreccion.getFechaPresentacion());
            tramiteCorreccion.setEstadoTramite(_tramiteCorreccion.getEstadoTramite());
            tramiteCorreccion.setTipoTramite(_tramiteCorreccion.getTipoTramite());
            tramiteCorreccion.setTramiteId(_tramiteCorreccion.getTramiteId());


            if( _tramiteCorreccion.getMedicoEnTurno() != null )
                tramiteCorreccion.setMedicoEnTurno(_tramiteCorreccion.getMedicoEnTurno());


        }

        // ---------------------------------------------------------------------
        // En caso de que no se finalize el tramite, la validacion pregunta
        // por este campo
        // ---------------------------------------------------------------------
        //tramiteCorreccion.getDomicilio().setCodigoPostal( tramiteCorreccion.getDomicilio().getAsentamiento().getCodigoPostal() );



        // ----------------------------------------------------------------------
        // Cambio de clinica y asignacion pueden tener domicilio particular y no estar
        // asignado en el grupo familiar
        // ----------------------------------------------------------------------
        if( session.getAttribute(KEY_DOMICILIOS_NUEVO) != null ){

            Domicilio domicilioExistente = (Domicilio)session.getAttribute(KEY_DOMICILIOS_NUEVO);
            if( domicilioExistente.getClave() != null ){
                // --------------------------------------------------------------
                // Si tiene una clave significa que ya estaba en la base y lo
                // estamos actualizando
                // --------------------------------------------------------------
                tramiteCorreccion.getDomicilio().setClave(domicilioExistente.getClave());
            }
        }


        solicitud.getTramites().set(0, tramiteCorreccion);


        try {
            // Actualizamos la solicitud con los nuevos patrones seleccionados
            solicitudBusinessRemote.actualizarTramites(solicitud);
            result.put("mensaje", "Se han guardado correctamente los cambios");
        } catch (SolicitudNoEncontradaException e) {
            this.log.error("error solicitud", e);
            result.put("mensaje","Ocurri&oacute; un error al intentar guardar los cambios");
        } catch (TramiteNoEncontradoException e) {
            this.log.error("error tramite", e);
            result.put("mensaje","Ocurri&oacute; un error al intentar guardar los cambios");
        } catch (Exception e) {
            this.log.error("error desconocido", e);
            result.put("mensaje","Ocurri&oacute; un error al intentar guardar los cambios");
        }

        return result;
    }




    /**
     * Actualiza el estado de una solicitud y todos sus tramites a cancelado
     *
     * @param solicitud
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/solicitud/cancelar", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> cancelarSolicitudProrroga(@RequestBody Solicitud solicitud) {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            solicitud.setEstadoSolicitud(new EstadoSolicitud());
            solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());

            solicitud = solicitudBusinessRemote.actualizarEstados(solicitud);
            result.put("mensaje", "La solicitud fue cancelada correctamente");
            result.put("solicitud", solicitud);

        } catch (SolicitudNoEncontradaException e) {
            this.log.error(e);
            result.put("mensaje", "Hubo un error al cancelar la solicitud: "+ e.getMessage());
        } catch (TramiteNoEncontradoException e) {
            this.log.error(e);
            result.put("mensaje", "Hubo un error al cancelar la solicitud: "+ e.getMessage());
        }

        return result;
    }



    @RequestMapping(value = "/limpiar-session")
    public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {
        session.removeAttribute(KEY_DOMICILIOS_PERSONA);
        session.removeAttribute(KEY_DOMICILIOS_PERSONA_INTERESADA);
        session.removeAttribute(KEY_DOMICILIO_EXISTENTE);
        session.removeAttribute(KEY_DOMICILIOS_ID_TIPO_TRAMITE);
        session.removeAttribute(KEY_DOMICILIOS_GRUPO_FAMILIAR);
        session.removeAttribute("FROM_WIZARD");
        session.removeAttribute(KEY_SOLICITUD);
        session.removeAttribute(KEY_DOMICILIOS_NUEVO);
        session.removeAttribute(KEY_CADENA_ORIGINAL);
        session.removeAttribute(KEY_FIRMA_ELECTRONICA);
        session.removeAttribute(KEY_TIPO_SOLICITUD);
        session.removeAttribute(KEY_TIPO_TRAMITE);
        session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
        session.removeAttribute(KEY_REQUIERE_DOCS);
        session.removeAttribute(KEY_CAPTURA_NUEVO_DOMICLIO);
        return null;
    }

    private String getTipoTramite(Long idTipoTramite) {
        String descTipoTramite = "";
        if (idTipoTramite
                .equals(TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH
                        .getCodigo().longValue())) {
            descTipoTramite = "ASIGNACI\u00d3N DE DOMICILIO PARTICULAR";
        } else if (idTipoTramite
                .equals(TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR
                        .getCodigo().longValue())) {
            descTipoTramite = "ACTUALIZACI\u00d3N DE DOMICILIO PARTICULAR";
        } else if (idTipoTramite
                .equals(TipoTramiteEnum.CAMBIO_CLINICA
                        .getCodigo().longValue())) {
            descTipoTramite = "CAMBIO DE CLINICA";
        }

        return descTipoTramite;
    }


    /**
     *
     *
     *
     * @param usuariosso Obtenido mediante el request. Lo agrega el OpenAM
     * @return
     */
    public Usuario getUsuarioSesion(UsuarioSSO usuariosso) {

        Usuario usuario = new Usuario();
        usuario.setUsuario(usuariosso.getNombre());

        PerfilUsuario pu = new PerfilUsuario();
        pu.setDescripcion(usuariosso.getNombre());
        usuario.setPerfilUsuario(pu);

        // Se crean los objetos necesarios para ligar el usuario con la
        // subdelegacion y delegacion.

        if (usuariosso.getDelegacion() != null && usuariosso.getSubdelegacion() != null) {

            // LUDS Se agrego esta validacion para que si es en caso de un
            // usuario EXTERNO no le llega la delegacion.
            UsuarioFuncionario uf = new UsuarioFuncionario();
            uf.setDelegacion(new Delegacion());
            uf.getDelegacion().setId(usuariosso.getDelegacion().longValue());
            uf.setSubdelegacion(new Subdelegacion());
            uf.setUsuario(usuario);
            uf.getSubdelegacion().setId(usuariosso.getSubdelegacion().longValue());
            usuario.setUsuarioFuncionario(uf);
        }

        usuario.setCveIdUsuario(usuariosso.getCurp());

        return usuario;
    }


    /**
     *
     * Genera un objeto FirmaElectronica
     *
     * @param solicitud
     * @param persona
     * @param session
     */
    private void obtenerDatosAcuse(Solicitud solicitud, Fisica persona, HttpSession session) {

        Locale locMEX = new Locale("es", "MX");
        DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
        String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
        StringBuffer sbnombre = new StringBuffer();
        FirmaElectronica datosAcuse = new FirmaElectronica();


        sbnombre.append(persona.getNombre().trim()).append(" ");
        if (StringUtils.isNotBlank(persona.getPrimerApellido()))
            sbnombre.append( persona.getPrimerApellido()).append(" ");

        if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido()))
            sbnombre.append(persona.getSegundoApellido());


        datosAcuse.setFechaElectronicaFormateada(strFechaElectronica);
        datosAcuse.setFechaElectronica(Calendar.getInstance().getTime());
        datosAcuse.setRfc(persona.getRfc());
        datosAcuse.setNombreCompleto(sbnombre.toString());
        datosAcuse.setCurp(persona.getCurp());

        session.setAttribute(KEY_FIRMA_ELECTRONICA, datosAcuse);

    }


    /**
     * Cadena para agregar a los documentos cuando se firman
     *
     * @param solicitud
     * @param persona
     * @param session
     */
    private void generarCadenaOriginal(Solicitud solicitud, Fisica persona, HttpSession session, GrupoFamiliar grupo) {


        Locale locMEX = new Locale("es", "MX");
        DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
        StringBuffer contenidoAFirmar = new StringBuffer();
        String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
        StringBuffer sbnombre = new StringBuffer();

        Long idTipoTramite = (Long) session.getAttribute(KEY_DOMICILIOS_ID_TIPO_TRAMITE);


        sbnombre.append(persona.getNombre().trim()).append(" ");

        if (StringUtils.isNotBlank(persona.getPrimerApellido()))
            sbnombre.append(persona.getPrimerApellido()).append(" ");

        if (StringUtils.isNotBlank(persona.getSegundoApellido()))
            sbnombre.append( persona.getSegundoApellido());


        contenidoAFirmar.append("||");
        contenidoAFirmar.append("Invocante:portalimssdigital|");
        contenidoAFirmar.append("Tramite:").append(this.getTipoTramite(idTipoTramite)).append("|");
        contenidoAFirmar.append("Fecha:").append(strFechaElectronica).append("|");
        contenidoAFirmar.append("Folio:").append(solicitud.getNoFolioSolicitud()).append("|");
        contenidoAFirmar.append("RFC:").append(persona.getRfc()).append("|");
        contenidoAFirmar.append("Nombre o Razon Social:");
        contenidoAFirmar.append(sbnombre.toString()).append("|");
        contenidoAFirmar.append("CURP:").append(persona.getCurp()).append("|");
        contenidoAFirmar.append("Registro Patronal:|");
        contenidoAFirmar.append("Numero de Seguridad Social:");
        if(grupo != null && grupo.getAsignacionNSS() != null && StringUtils.isNotBlank(grupo.getAsignacionNSS().getNss())) {
            contenidoAFirmar.append(grupo.getAsignacionNSS().getNss());
        }
        contenidoAFirmar.append("||");

        session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());

    }





    /**
     * Controlador de inicio para peticiones que solo indican el id de persona
     *
     * @param model
     * @param session HttpSession
     * @param request HttpServletRequest
     * @param idPersona Long
     * @param idPersonaInteresada Long
     * @return
     */
    @RequestMapping(value = "/{idPersona}/{idPersonaInteresada}")
    public String initWizardDomicilioClinicaDerechohabientePersona(Model model, HttpSession session, HttpServletRequest request
            ,@PathVariable("idPersona") Long idPersona, @PathVariable("idPersonaInteresada") Long idPersonaInteresada) {
        return this.initWizardDomicilioClinicaDerechohabiente(model, session, request, idPersona, idPersonaInteresada, null);
    }

    /**
     * Obtiene las solicitudes registradas y pendientes de actualizacion.
     *
     * @param model
     * @param session HttpSession
     * @param request HttpServletRequest
     * @param idPersona Long
     * @param idPersonaInteresada Long
     * @param idTipoTramite Long
     * @return
     */
    @RequestMapping(value = "/{idPersona}/{idPersonaInteresada}/{idTipoTramite}")
    public String initWizardDomicilioClinicaDerechohabiente(Model model, HttpSession session, HttpServletRequest request
            ,@PathVariable("idPersona") Long idPersona
            ,@PathVariable("idPersonaInteresada") Long idPersonaInteresada
            ,@PathVariable("idTipoTramite") Long idTipoTramite) {

        log.debug("entre a iniciarlizar el tramite de cambio de domicilio JJJJJJJ" );
        // ----------------------------------------
        // Puede ser la persona interesada
        // ----------------------------------------
        Fisica persona 							= null;

        Boolean registrada 						= false;
        Boolean otroTipoSolicitud 				= false;
        Boolean pendienteAutorizacion 			= false;
        Boolean mismoOrigen 					= true;
        Boolean mismoTramite 					= false;
        Boolean tieneDomicilioParticular 		= false;
        Solicitud solicitudRegistrada			= new Solicitud();
        Boolean asignacionDomicilio 			= false;
        Boolean actualizacionDomicilio 			= false;
        Boolean cambioClinica 					= false;
        Boolean esDerechohabiente				= false;
        Boolean tieneDomicilioEnGrupoFamilar  	= false;
        GrupoFamiliar grupoFamiliar 			= null;

        // ---------------------------------------------------------------------
        // Solicitudes del tipo "actualizacion de datos generales"
        // que estan siendo procesadas en la queue y no estan finalizadas
        //
        // Solicitudes de cualquier tipo que no han finalizado
        // ---------------------------------------------------------------------
        List<Solicitud> solicitudesNoFinalizadas = new ArrayList<Solicitud>();


        // -------------------------------------------------------------------
        // El metodo busca personas fisicas, pero recibe personas fiscales
        // -------------------------------------------------------------------
        TipoPersonaFiscal tipoPersona = TipoPersonaFiscal.FISICA;

        try {
            Long idOrigen =  new CommonValidator().getOrigenContext(request);
            log.debug("el origen de la solicitud es" + idOrigen);
            // ---------------------------------------------------------------
            // Verificamos el id de persona
            // Si no se encuentra en DitAsignacionNss el nss es null
            // ---------------------------------------------------------------
            Fisica personaInteresada = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(idPersonaInteresada);
            session.setAttribute(KEY_DOMICILIOS_PERSONA_INTERESADA, personaInteresada);

            if( idPersonaInteresada == idPersona  ){

                // -----------------------------------------------
                // El tramite es para la persona interesada
                // -----------------------------------------------
                persona = personaInteresada;
                session.setAttribute(KEY_DOMICILIOS_PERSONA, personaInteresada);

            }else{

                // -----------------------------------------------
                // El tramite es para algun miembro de su grupo
                // -----------------------------------------------
                persona = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(idPersona);
                session.setAttribute(KEY_DOMICILIOS_PERSONA, persona);

            }


            // --------------------------------------------------
            // Si cuenta con nss es asegurado y que el origen no sea ciudadano o ventanilla
            // --------------------------------------------------
            if( personaInteresada.getNss() != null
                    &&  idOrigen.equals(OrigenSolicitudEnum.INTERNET.getId()) ){
                try{
                    // -----------------------------------------------------------------
                    // Todos los grupos familiares a los que pertenece la persona
                    // -----------------------------------------------------------------
                    List<GrupoFamiliar> grupoFamiliarList =  grupoFamiliarService.getGruposFamiliaresPorPersona(persona.getIdPersona(),null,null);

                    for( GrupoFamiliar grupof : grupoFamiliarList  ){

                        // ------------------------------------------------------------------------------------------
                        // Verificamos que la asignacion del grupo familiar pertenezca a la persona interesada
                        // ------------------------------------------------------------------------------------------
                        if(  grupof.getAsignacionNSS().getIdPersona().equals(personaInteresada.getIdPersona())  ){
                            grupoFamiliar = grupof;
                            break;
                        }
                    }

                    if( grupoFamiliar != null ){
                        esDerechohabiente = true;
                        session.setAttribute(KEY_DOMICILIOS_GRUPO_FAMILIAR, grupoFamiliar);
                    }
                }catch( DerechohabientesBusinessException e ){
                    log.error("GRUPO FAMILIAR ================================================");
                    log.error(e);
                    // ---------------------------------------------------
                    // No se encontro el grupo familiar en el WebService
                    // ---------------------------------------------------
                }catch(NullPointerException e){
                    // -----------------------------------------
                    // La lista de grupos familiares es nula
                    // -----------------------------------------
                }


            }


            tieneDomicilioParticular = this.validaDomicilioParticularPersona(persona, session);


            // ------------------------------------------------------------------------
            // Si no se indica el tipo de tramite se asigna uno en base a si tiene
            // o no domicilio particular
            // ------------------------------------------------------------------------
            if( idTipoTramite == null ){
                if( tieneDomicilioParticular )
                    idTipoTramite = TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo().longValue();
                else
                    idTipoTramite = TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH.getCodigo().longValue();
            }


            this.validaTipoTramitesPermitidos( idTipoTramite );



            if( esDerechohabiente ){

                // -----------------------------------------------------
                // Si el campo cveIdPersonafDom no es nulo en la tabla
                // -----------------------------------------------------
                if( (grupoFamiliar != null) &&  (grupoFamiliar.getCvePersonaDomicilio() != null) && ( grupoFamiliar.getDomicilio() != null )  )
                    tieneDomicilioEnGrupoFamilar = true;

            }






            switch( TipoTramiteEnum.obternerEnumById(idTipoTramite.intValue())  ){
                case ACTUALIZACION_DOMICILIO_PARTICULAR:

                    if( !tieneDomicilioParticular ){
                        throw new DatosInsuficientesModificacionException("El tr&aacute;mite no puede realizarse por que no tiene un domicilio particular asignado");
                    } else {
                        Domicilio domicilio = (Domicilio) session
                                .getAttribute(KEY_DOMICILIO_EXISTENTE);

                        if (domicilio != null && domicilio.getAsentamiento() != null) {
                            session.setAttribute(KEY_ASENTAMIENTO, domicilio.getAsentamiento());
                        }
                    }

                    actualizacionDomicilio = true;
                    break;

                case ASIGNACION_DE_DOMICILIO_PARTICULAR_DH:

                    if( esDerechohabiente ){
                        if( !tieneDomicilioEnGrupoFamilar ){
                            if( tieneDomicilioParticular ){
                                // --------------------------------------------------------------------------------------
                                // No tiene un domicilio asignado en grupo familiar, pero si tiene domicilio particular.
                                // --------------------------------------------------------------------------------------
                                Domicilio domicilio = (Domicilio) session
                                        .getAttribute(KEY_DOMICILIO_EXISTENTE);

                                session.setAttribute(KEY_DOMICILIOS_NUEVO, domicilio);

                                if (domicilio != null && domicilio.getAsentamiento() != null) {
                                    session.setAttribute(KEY_ASENTAMIENTO, domicilio.getAsentamiento());
                                }
                            }
                        }else{
                            if( tieneDomicilioParticular ){
                                // --------------------------------------------------------
                                // Tiene asignado el domicilio particular y domicilio en
                                // grupo familiar
                                // --------------------------------------------------------
                                throw new DatosInsuficientesModificacionException("El tr&aacute;mite no puede realizarse por que ya tiene un domicilio asignado");
                            }
                        }
                    }else{
                        if( tieneDomicilioParticular ){
                            throw new DatosInsuficientesModificacionException("El tr&aacute;mite no puede realizarse por que ya tiene un domicilio asignado");
                        }
                    }


                    asignacionDomicilio = true;
                    break;
                case CAMBIO_CLINICA:

                    // ------------------------------------------------------------
                    // Solo un derechohabiente puede cambiar de clinica
                    // ------------------------------------------------------------
                    if( !esDerechohabiente )
                        throw new DatosInsuficientesModificacionException("El tr&aacute;mite no puede realizarse por que no es un derechohabiente");

                    if( !tieneDomicilioEnGrupoFamilar ){
                        if(tieneDomicilioParticular  ){

                            Domicilio domicilio = (Domicilio) session
                                    .getAttribute(KEY_DOMICILIO_EXISTENTE);

                            session.setAttribute(KEY_DOMICILIOS_NUEVO, domicilio);

                            if (domicilio != null && domicilio.getAsentamiento() != null) {
                                session.setAttribute(KEY_ASENTAMIENTO, domicilio.getAsentamiento());
                            }
                        }
                    }

                    cambioClinica = true;

                    break;
                default:
                    break;
            }


            // ---------------------------------------------------------------
            // Mensaje en front con el tipo de tramite
            // ---------------------------------------------------------------
            model.addAttribute("tipoTramite", this.getTipoTramite(idTipoTramite));



            // ---------------------------------------------
            // Pendientes de autorizacion
            // ---------------------------------------------
            List<Long> tiposSolicitud = new ArrayList<Long>();
            tiposSolicitud.add(TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor().longValue());
            tiposSolicitud.add(TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE.getValor().longValue());

            List<Long> estadosSolicitud = new ArrayList<Long>();
            estadosSolicitud.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo().longValue());
            estadosSolicitud.add(EstadoSolicitudEnum.REGISTRADA.getCodigo().longValue());

            solicitudesNoFinalizadas = solicitudBusinessRemote.obtenerSolicitudPorPersona(idPersona, tipoPersona, tiposSolicitud, estadosSolicitud, false);

            if( !solicitudesNoFinalizadas.isEmpty() ){
                if( (solicitudRegistrada = continenTramitesDomicilio(solicitudesNoFinalizadas, idOrigen)) != null ){

                    if( solicitudRegistrada.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo()) )
                        throw new SolicitudEnProcesoException();
                    registrada = true;
                }
            }



            // --------------------------------------------------------------------
            // En caso de que exista una solicitud registrada obtenemos el tipo
            // de tramite que esta pendiente
            // --------------------------------------------------------------------
            if( registrada ){
                model.addAttribute("tipoTramiteCreado", solicitudRegistrada.getTramites().get(0).getTipoTramite());
                mismoOrigen = new CommonValidator().validarSolicitudMismoOrigen(solicitudRegistrada, idOrigen);
                mismoTramite = this.validaMismoTramiteEnSolicitudes(solicitudRegistrada, idTipoTramite);
            }else{
                solicitudRegistrada = new Solicitud();
            }


        }catch( SolicitudesEnProcesoException e ){

            log.error("Existe una solicitud en ventanilla", e);
            model.addAttribute("error", "Existe una solicitud que no ha sido finalizada en su Unidad M&eacute;dica Familiar. Por favor acuda a la misma para finalizar el tr&aacute;mite");

        }catch( DatosInsuficientesModificacionException e ){

            log.error(e.getSituacion(), e);
            model.addAttribute("error", e.getSituacion());

        }catch( SolicitudEnProcesoException sp ){
            pendienteAutorizacion = true;

        }catch( PersonaFisicaNoEncontradaException pe){

            log.error("El id de persona es invalido", pe);
            model.addAttribute("error", "No fue posible encontrar el registro de la persona");

        }catch( ArgumentosInvalidosException ae ){

            log.error("Tipo de tramite no soportado", ae);
            model.addAttribute("error", "El tipo de tr&aacute;mite es inv&aacute;lido");

        }catch (Exception e) {

            log.error("Ocurrio un error al consultar las solicitudes", e);
            model.addAttribute("error", "No fue posible consultar si el integrante del grupo familiar cuenta con solicitudes registradas");

        }


        session.setAttribute(KEY_DOMICILIOS_ID_TIPO_TRAMITE, idTipoTramite);

        model.addAttribute("idPersona",idPersona);
        model.addAttribute("pendienteAutorizacion",pendienteAutorizacion);
        model.addAttribute("solicitudRegistrada", registrada);
        model.addAttribute("otroTipoSolicitud", otroTipoSolicitud);
        model.addAttribute("mismoOrigen", mismoOrigen);
        model.addAttribute("mismoTramite", mismoTramite);
        model.addAttribute("tieneDomicilioParticular",tieneDomicilioParticular);
        model.addAttribute("idTipoTramite",idTipoTramite.longValue());
        model.addAttribute("asignacionDomicilio",asignacionDomicilio);
        model.addAttribute("actualizacionDomicilio",actualizacionDomicilio);
        model.addAttribute("cambioClinica",cambioClinica);
        model.addAttribute("esDerechohabiente",esDerechohabiente);
        model.addAttribute("solicitudForm", solicitudRegistrada);
        model.addAttribute("tieneDomicilioEnGrupoFamilar",tieneDomicilioEnGrupoFamilar);

        return VIEW_INICIAL;
    }

    /**
     * Muestra pantalla para elegir si el cambio de domicilio es en el mismo asentamiento
     * o en uno diferente
     *
     * @param model
     * @param session
     * @param request
     * @param habilitarCP Boolean variable que indica si quiere actualizar el CP o no
     * @param idPersona Long El id de la persona que solicita el tr&aacute;mite
     * @return
     */
    @RequestMapping(value = "/actualizacion/{idPersona}")
    public String elegirTipoActualizacionDomicilio(Model model,
                                                   HttpSession session, HttpServletRequest request,
                                                   @PathVariable("idPersona") Long idPersona) {

		/*
		 * Se busca si el asentamiento esta en la sesion, de ser asi se mostrara
		 * en la pantalla para ayudar a elegir el tipo de actualizacion a
		 * realizar
		 */

        Asentamiento asentamiento = (Asentamiento) session.getAttribute(KEY_ASENTAMIENTO);

        if (asentamiento != null) {
            session.removeAttribute(KEY_ASENTAMIENTO);
            request.setAttribute(KEY_ASENTAMIENTO, asentamiento);
        }

        model.addAttribute("idPersona",idPersona);

        return VIEW_ELECCION_ACTUALIZACION;
    }


    /**
     * Guarda en la base la solicitud y el tramite iniciales
     *
     * @param model
     * @param session
     * @param request
     * @param habilitarCP Boolean variable que indica si quiere actualizar el CP o no
     * @param idPersona Long El id de la persona que solicita el tr&aacute;mite
     * @return
     */
    @RequestMapping(value = "/crearTramite/{habilitarCP}/{idPersona}")
    public Object crearTramite(Model model, HttpSession session, HttpServletRequest request
            ,@PathVariable("habilitarCP") Boolean habilitarCP
            ,@PathVariable("idPersona") Long idPersona) {


        Fisica persona = (Fisica) session.getAttribute(KEY_DOMICILIOS_PERSONA);
        Fisica personaInteresada = (Fisica) session.getAttribute(KEY_DOMICILIOS_PERSONA_INTERESADA);
        Long idTipoTramite = (Long) session.getAttribute(KEY_DOMICILIOS_ID_TIPO_TRAMITE);
        Usuario usuario = this.getUsuarioSesion(this.procesarUsuarioSSO(request));
        Solicitud solicitud = null;
        Long idOrigen =  new CommonValidator().getOrigenContext(request);

        log.debug("el tipo de trasmite es [" +idTipoTramite+ "]");
        // -----------------------------------------------------------
        // Objeto para dar de alta una solicitud
        //
        // El business no setea la persona interesada
        // -----------------------------------------------------------
        MDMDatosEntrada mdmDatosEntrada = new MDMDatosEntrada();
        mdmDatosEntrada.setIndCapturaDatosRENAPO(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaNombre(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaCURP(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaSexo(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaFechaNacimiento(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaLugarNacimiento(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaDocumentoProbatorio(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaDatosSAT(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaRFC(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaDomicilioFiscal(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaDomicilioParticular(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaMediosContactoFiscales(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaMediosContactoParticular(Boolean.FALSE);
        mdmDatosEntrada.setIndAutorizacion(Boolean.FALSE);
        mdmDatosEntrada.setIndAsignacionDomicilio(Boolean.FALSE);
        mdmDatosEntrada.setIndActualizacionDomicilioDerechohabiente(Boolean.FALSE);
        mdmDatosEntrada.setIndCambioClinica(Boolean.FALSE);
        mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.TRUE);
        mdmDatosEntrada.setOrigen(idOrigen);

        try {

            // ----------------------------------------------------------------------------
            // Validamos la persona enviada con la que se encuentra en sesion
            // ----------------------------------------------------------------------------
            if( persona.getIdPersona().longValue() !=  idPersona.longValue() )
                throw new PersonaNoEncontradaException(idPersona);

            mdmDatosEntrada.setPersonaFisica(persona);


            if( idTipoTramite.longValue() == TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH.getCodigo().longValue() ){
                mdmDatosEntrada.setIndAsignacionDomicilio( Boolean.TRUE );

            }else if( idTipoTramite.longValue() == TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo().longValue() ){
                mdmDatosEntrada.setIndCapturaDomicilioParticular(Boolean.TRUE);

            }else if( idTipoTramite.longValue() == TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue() ){
                mdmDatosEntrada.setIndCambioClinica(Boolean.TRUE);

            }else{

                throw new ArgumentosInvalidosException();

            }


            // ------------------------------------------------------------------------------------------------
            // Si es derechohabiente la solicitud debe de ser del tipo CORRECCION_DATOS_DERECHOHABIENTE.
            // Si no es derechohabiente del tipo ACTUALIZACION_DATOS_GENERALES.
            // ------------------------------------------------------------------------------------------------
            if( session.getAttribute(KEY_DOMICILIOS_GRUPO_FAMILIAR) != null )
                mdmDatosEntrada.setIdTipoSolicitud(TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE.getValor().longValue());
            else
                mdmDatosEntrada.setIdTipoSolicitud(TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor().longValue());


            solicitud = this.solicitudPersonaBusiness.crearTramiteModificacionDatosPersona(mdmDatosEntrada, usuario, personaInteresada.getIdPersona());


        } catch (SolicitudNoValidaException e) {
            log.debug(e);
            // ------------------------------------------------------
            // No se pudo crear la solicitud
            // ------------------------------------------------------

        } catch (PersonaNoEncontradaException e) {
            log.debug(e);
            // ------------------------------------------------------
            // El idPersona no corresponde con el de la sesion
            // ------------------------------------------------------
        } catch (ArgumentosInvalidosException e) {
            log.debug(e);
            // ---------------------------------------------------------
            // El tramite que se desea realizar no puede ser realizado
            // por este controlador
            // ---------------------------------------------------------
        } catch( Exception e ){
            log.debug(e);
        }


        session.setAttribute("FROM_WIZARD",true);
        session.setAttribute(KEY_SOLICITUD, solicitud);
        session.setAttribute(KEY_CAPTURA_NUEVO_DOMICLIO, habilitarCP);
        model.addAttribute("isRetomar", false);

		/*

		if( habilitarCP ){
			// ------------------------------------------------------------------
			// Se envia al wizard donde selecciona su municipio o codigo postal.
			//
			// Cuando seleccione sus datos se evian a los metodos "porCodigoPostal"
			// y "porMunicipio" de este controlador
			// ------------------------------------------------------------------
			return new ModelAndView("forward:/domicilio/nacional/ubicar", "isRetomar",false);

		}else{

			// -------------------------------------------------------------------------
			// Se simula que selecciono el cp y asentamiento
			// -------------------------------------------------------------------------
			return new ModelAndView("forward:/wizard/domicilio/porCodigoPostal", "isRetomar",false);

		}*/

        return new ModelAndView("forward:/wizard/domicilio/porCodigoPostal", "isRetomar",false);


    }






    /**
     * Valida el tipo de tramite requerido
     *
     * Solamente tramites para cambio de domicilio a personas fisicas son permitidos.
     *
     * @param idTipoTramite Long Id del tipo de tramite
     * @throws ArgumentosInvalidosException
     */
    private void validaTipoTramitesPermitidos( Long idTipoTramite ) throws ArgumentosInvalidosException{

        try{

            switch( TipoTramiteEnum.obternerEnumById(idTipoTramite.intValue())  ){
                case ACTUALIZACION_DOMICILIO_PARTICULAR:
                case ASIGNACION_DE_DOMICILIO_PARTICULAR_DH:
                case CAMBIO_CLINICA:
                    break;
                default:
                    log.debug( "======================= validaTipoTramitesPermitidos ========" + TipoTramiteEnum.obternerEnumById(idTipoTramite.intValue()) );
                    throw new ArgumentosInvalidosException();
            }


        }catch(Exception e){
            log.debug(e);
            // ----------------------------------------------------------
            // En caso de que le tipo de tramite no se encuentre en la
            // enumeracion, tambien se lanza la excepcion.
            // ----------------------------------------------------------
            throw new ArgumentosInvalidosException();
        }



    }

    /**
     * Valida si la solicitud contiene un tramite de del tipo requerido
     *
     * @param solicitud {@link Solicitud}
     * @param idTipoTramite Long El id del tipo de tramite
     * @return boolean Si la solicitud contiene el tipo de tramite requerido
     */
    private boolean validaMismoTramiteEnSolicitudes( Solicitud solicitud, Long idTipoTramite){

        try{
            for(Tramite tramite : solicitud.getTramites()){

                if( tramite.getTipoTramite().getIdTipoTramite().equals(idTipoTramite.intValue()) )
                    return true;
            }
        }catch(Exception e){
            log.debug(e);
        }

        return false;
    }

    /**
     * Procesa las llamadas GET al contexto "porCodigoPostal"
     *
     * Asume que la llamada fue hecha por un controlador mediante forward y no
     * por el usuario en una forma; en lugar de esperar el domicilio mediante una
     * forma, se toma el que DEBE esta en sesion
     *
     * @param model
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/porCodigoPostal", method = RequestMethod.GET   )
    public Object ubicarPorCodigoPostalGET( Model model, final HttpSession session,HttpServletRequest request) {
        Domicilio domicilio = null;
        BindingResult result = new BeanPropertyBindingResult(domicilio, "domicilio");

        return this.ubicarPorCodigoPostalPOST(domicilio, result, model, session, request);

    }

    /**
     *
     * Cuando se seleccioanan los campos de cp y asentamiento se le pasan los datos a esta funcion
     *
     * @param domicilio Objeto creado en el paso previo a mostrar los datos de la direccion
     * @param result
     * @param model
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/porCodigoPostal", method =  RequestMethod.POST )
    public Object ubicarPorCodigoPostalPOST(@ModelAttribute Domicilio domicilio,BindingResult result, Model model,
                                            final HttpSession session,HttpServletRequest request) {

        Boolean esNuevoDomicilio = true;
        Asentamiento asentamiento = new Asentamiento();
        Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
        Fisica persona = (Fisica) session.getAttribute(KEY_DOMICILIOS_PERSONA);



        // -----------------------------------------------------------------------------
        // Cuando se selecciona el codigo postal a mano la forma lo asigna en el
        // campo codigo postal de domicilio, pero la validacion lo busca dentro
        // de asignacion
        // -----------------------------------------------------------------------------
        if( (domicilio != null) && (domicilio.getCodigoPostal() != null) ){
            domicilio.getAsentamiento().setCodigoPostal(domicilio.getCodigoPostal());
        }


        if( (domicilio == null) || (domicilio.getAsentamiento() == null) || (domicilio.getAsentamiento().getCodigoPostal() == null)
                || (domicilio.getAsentamiento().getCodigoPostal().getCodigoPostal() == null)){

            // ------------------------------------------------------------------------
            // Si ya se asigno un domicilio nuevo, es por que se retoma la solicitud
            // ------------------------------------------------------------------------
            if( session.getAttribute(KEY_DOMICILIOS_NUEVO) != null ){
                domicilio = (Domicilio) session.getAttribute(KEY_DOMICILIOS_NUEVO);
                log.debug("settee el domicilio de " + KEY_DOMICILIOS_NUEVO);
            }else if(session.getAttribute(KEY_DOMICILIO_EXISTENTE) != null){
                domicilio = (Domicilio)session.getAttribute(KEY_DOMICILIO_EXISTENTE);
                log.debug("settee el domicilio de " + KEY_DOMICILIO_EXISTENTE);
            }else if(session.getAttribute(KEY_DOMICILIOS_GRUPO_FAMILIAR) != null) {
                GrupoFamiliar infoCabezaGF = (GrupoFamiliar)session.getAttribute(KEY_DOMICILIOS_GRUPO_FAMILIAR);
                if(infoCabezaGF.getDomicilio() != null) {
                    log.debug("settee el domicilio de " + KEY_DOMICILIOS_GRUPO_FAMILIAR);
                    domicilio = infoCabezaGF.getDomicilio();
                }

            }

            if(domicilio != null&&domicilio.getAsentamiento()!=null&&
                    domicilio.getAsentamiento().getCodigoPostal()!=null) {
                domicilio.setCodigoPostal(domicilio.getAsentamiento().getCodigoPostal());
                result = new BeanPropertyBindingResult(domicilio, "domicilio");
                esNuevoDomicilio = false;
            }else {
                result = new BeanPropertyBindingResult(domicilio, "domicilio");
            }

        }


	/*
		new DomicilioValidator().validate(domicilio, result);
		if (result.hasErrors()) {
			log.error("VALIDACION DE DOMICILIO ================================");
			log.error(result.getAllErrors());
			log.error(domicilio);
			model.addAttribute("asentamiento", new Asentamiento());
			return "domicilio.nacional";
			//return new ModelAndView("forward:/domicilio/nacional/ubicar", "isRetomar",false);
		}

	*/

        try {


            // ----------------------------------------------------
            // Si cambio su codigo postal
            // ----------------------------------------------------

            TramiteCorreccionDerechohabiente tramite = new TramiteCorreccionDerechohabiente();
            tramite.setPaso(PasoRegistroEnum.CAPTURA_DOMICILIO.getId());

            if( esNuevoDomicilio &&  domicilio!= null){
                asentamiento = this.domicilioServiceBusiness.getAsentamiento(domicilio.getAsentamiento());
                domicilio.setAsentamiento(asentamiento);
                tramite.setDomicilioAnterior(domicilio);
            }
            tramite.setDomicilio(domicilio);
            request.setAttribute(KEY_RFC_SOLICITANTE, persona.getRfc());
            request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
            request.setAttribute("idSolicitud", solicitud.getSolicitudId());


            // ----------------------------------------------------------------------
            // Actualizamos el tramite para saber que llego al paso 1
            // ----------------------------------------------------------------------


            this.guardarSolicitud(tramite, session, request);
            if(domicilio == null) {
                domicilio = new Domicilio();
            }

        } catch (DomicilioNoLocalizadoException e) {
            result.addError(new ObjectError("asentamiento.clave", e.getMessage()));
            this.log.error("ERROR AL GUARDAR LA SOLICITUD DomicilioNoLocalizadoException" ,e);
            //return new ModelAndView("forward:/domicilio/nacional/ubicar", "isRetomar",false);

        } catch (AsentamientoNoLocalizadoException e) {
            result.addError(new ObjectError("asentamiento.clave", e.getMessage()));
            this.log.error(e);
            //return new ModelAndView("forward:/domicilio/nacional/ubicar", "isRetomar",false);
        } catch (NullPointerException e) {
            result.addError(new ObjectError("asentamiento.clave", e.getMessage()));
            this.log.error(e);
//            return new ModelAndView("forward:/domicilio/nacional/ubicar", "isRetomar",false);
        } catch (Exception e) {
            result.addError(new ObjectError("asentamiento.clave", e.getMessage()));
            this.log.error(e);
//            return new ModelAndView("forward:/domicilio/nacional/ubicar", "isRetomar",false);
        }


        model.addAttribute("domicilio", domicilio);
        model.addAttribute("asentamiento", asentamiento);
        model.addAttribute("errorFormGeneral", this.getMessagesError(result));
        //request.setAttribute("FROM_CODIGO_POSTAL", true);
        model.addAttribute("solicitudForm", solicitud);

        // ----------------------------------------------------------------------
        // Vista que muestra el mapa para que pueda seleccionar su direccion
        // ----------------------------------------------------------------------
        return VIEW_DIRECCION;

    }


    /**
     * Recibe los datos cuando el usuario desconoce su cp
     *
     * @param asentamiento
     * @param result
     * @param model
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/porMunicipio", method = RequestMethod.POST)
    public Object ubicarPorMunicipio(@ModelAttribute Asentamiento asentamiento,BindingResult result, Model model,
                                     final HttpSession session, HttpServletRequest request) {

        this.log.debug(" datos complementarios por municipio [" + asentamiento + "]");

        Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);
        Fisica persona = (Fisica) session.getAttribute(KEY_DOMICILIOS_PERSONA);
        Domicilio domicilio = new Domicilio();


        new AsentamientoValidator().validate(asentamiento, result);
        if (result.hasErrors()) {
            model.addAttribute("domicilio", domicilio);
            //model.addAttribute("asentamiento", asentamiento);
            return "domicilio.nacional";
        }


        try {

            asentamiento = this.domicilioServiceBusiness.getAsentamiento(asentamiento);

            this.log.debug("Asentamiento localizado" + asentamiento);

            // -----------------------------------------------
            // Validamos el codigo postal
            // -----------------------------------------------
            CodigoPostal cp = asentamiento.getCodigoPostal();
            if (cp == null) {
                throw new DomicilioNoLocalizadoException();
            } else {
                if (cp.getCodigoPostal() == null) {
                    throw new DomicilioNoLocalizadoException();
                }
            }


            domicilio.setAsentamiento(asentamiento);

            request.setAttribute(KEY_RFC_SOLICITANTE, persona.getRfc());
            request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
            request.setAttribute("idSolicitud", solicitud.getSolicitudId());


            // ----------------------------------------------------------------------
            // Actualizamos el tramite para saber que llego al paso 1
            // ----------------------------------------------------------------------
            TramiteCorreccionDerechohabiente tramite = new TramiteCorreccionDerechohabiente();
            tramite.setDomicilio(domicilio);
            tramite.setPaso(PasoRegistroEnum.CAPTURA_DOMICILIO.getId());
            this.guardarSolicitud(tramite, session, request);


        } catch (DomicilioNoLocalizadoException e) {
            result.rejectValue("clave", "", e.getMessage());
            this.log.error(e);
            return new ModelAndView("forward:/domicilio/nacional/ubicar", "isRetomar",false);
        } catch (AsentamientoNoLocalizadoException e) {
            result.rejectValue("clave", "", e.getMessage());
            this.log.error(e);
            return new ModelAndView("forward:/domicilio/nacional/ubicar", "isRetomar",false);
        } catch (NullPointerException e) {
            result.rejectValue("clave", e.getMessage());
            this.log.error(e);
            return new ModelAndView("forward:/domicilio/nacional/ubicar", "isRetomar",false);
        } catch (Exception e) {
            result.rejectValue("clave", e.getMessage());
            this.log.error(e);
            return new ModelAndView("forward:/domicilio/nacional/ubicar", "isRetomar",false);
        }


        model.addAttribute("domicilio", domicilio);
        request.setAttribute("FROM_MUNICIPIO", true);
        model.addAttribute("asentamiento", asentamiento);
        model.addAttribute("errorFormGeneral", this.getMessagesError(result));
        model.addAttribute("solicitudForm", solicitud);

        // ----------------------------------------------------------------------
        // Vista que muestra el mapa para que pueda seleccionar su direccion
        // ----------------------------------------------------------------------
        return VIEW_DIRECCION;

    }

    /**
     * Valida si la persona cuenta con un domicilio registrado
     *
     * @param persona {@link Persona}
     * @return boolean Si cuenta o no con domicilios asignados
     */
    private boolean validaDomicilioParticularPersona(Fisica persona, HttpSession session ) {

        try{

            for( Domicilio domicilio : persona.getDomicilios() ){

                if( domicilio.getDicTipoDomicilio().getClave().equals( TipoDomicilioEnum.PARTICULAR.getCodigo().intValue() ) ){
                    session.setAttribute(KEY_DOMICILIO_EXISTENTE, domicilio);
                    return true;
                }
            }

        }catch( Exception e ){
            log.error("Error al consultar el domicilio de la persona", e);
        }

        return false;
    }


    /**
     *
     * Valida si la delegacion pertenece al codigo postal.
     *
     * En caso negativo significa un cambio de circunscripcion foranea
     *
     * @param model
     * @param session
     * @param request
     * @param codigoPostal
     * @param idDelegacion
     * @return
     */
    @RequestMapping(value = "/consultarCambioCircunscripcion/{codigoPostal}/{idDelegacion}")
    public Boolean consultarCambioCircunscripcion(@PathVariable("codigoPostal") String codigoPostal, @PathVariable("idDelegacion") Long idDelegacion) {
        try{
            domicilioServiceBusiness.getAsentamientosByDelegacionCodigoPostal(idDelegacion, codigoPostal);
        }catch(DomicilioNoLocalizadoException ex){
            return false;
        }
        return true;
    }



    /**
     * Renderiza los datos seleccionados en el wizard de direcciones en una forma
     * no editable
     *
     * Cuando el usuario termian de seleccionar los campos de su direccion en el wizard,
     * este se redirecciona aqui.
     *
     * @param domicilio
     * @param session
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/siguiente", method = { RequestMethod.GET, RequestMethod.POST} )
    public String siguiente(final HttpSession session, HttpServletRequest request, final Model model) {

        String error = "";
        Long idTipoTramite = (Long) session.getAttribute(KEY_DOMICILIOS_ID_TIPO_TRAMITE);
        Domicilio domicilioNuevo = (Domicilio)session.getAttribute(KEY_DOMICILIOS_NUEVO);
        log.debug("el comdigo postal que auqi llego es [" + domicilioNuevo.getCodigoPostal().getCodigoPostal());
        log.debug("el comdigo postal que auqi llego es del asentamiento[" + domicilioNuevo.getAsentamiento().getCodigoPostal().getCodigoPostal());

        Domicilio domicilioAnterior = (Domicilio)session.getAttribute(KEY_DOMICILIO_EXISTENTE);
        Boolean esDerechohabiente = false;
        Fisica persona = (Fisica) session.getAttribute(KEY_DOMICILIOS_PERSONA);
        Solicitud solicitud = (Solicitud)session.getAttribute(KEY_SOLICITUD);

        //UsuarioSSO usuarioSSO = this.procesarUsuarioSSO(request);
        Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);

        Long umfSeleccionada = -1L;
        Long turnoSeleccionado = -1L;
        Long consultorioSeleccionado = -1L;

        TramiteCorreccionDerechohabiente tramite = new TramiteCorreccionDerechohabiente();
        tramite.setPaso(PasoRegistroEnum.CAPTURA_UMF.getId());
        tramite.setTipoTramite(solicitud.getTramites().get(0).getTipoTramite());
        tramite.setFisica(persona);
        tramite.setTramiteId(solicitud.getTramites().get(0).getTramiteId());
        tramite.setIndSeleccionMedico(1);

        tramite.setDomicilioAnterior(domicilioAnterior);
        tramite.setDomicilio(domicilioNuevo);

        List<UnidadMedicaFamiliar> umfList = null;

        // ----------------------------------------------------------
        // Bandera para mostrar el div de selelccionar clinica
        // ----------------------------------------------------------
        Boolean cambioClinica = true;
        GrupoFamiliar grupoFamiliar = null;
        // --------------------------------------------------
        // Si es derechohabiente puede cambiar de clinica
        // --------------------------------------------------
        if( session.getAttribute(KEY_DOMICILIOS_GRUPO_FAMILIAR) != null ) {
            esDerechohabiente = true;
            grupoFamiliar = (GrupoFamiliar)session.getAttribute(KEY_DOMICILIOS_GRUPO_FAMILIAR);
        }



        // ----------------------------------------------------------------------
        // Actualizamos el tramite para saber que llego al paso 1
        // ----------------------------------------------------------------------
        this.guardarSolicitud(tramite, session, request);


        try{
            // --------------------------------------------------------------
            // Si es actualizacion de domicilio y es derechohabiente
            // validamos el cambio de clinica
            // --------------------------------------------------------------
            if( esDerechohabiente){

                tramite.setIdAsignacionNss(grupoFamiliar.getAsignacionNSS().getIdAsignacionNSS());
                tramite.setParentesco(grupoFamiliar.getParentesco());
                model.addAttribute(KEY_DOMICILIOS_TRAMITE_CLINICA, tramite);

                UnidadMedicaFamiliar umf = grupoFamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar();


                try{

                    // ---------------------------------------------------------------------------------------------------
                    // Obtenemos todas las UMF's para el nuevo codigo postal.
                    // Verificamos que la UMF que tiene asignada el derechohabiente se encuentre en la lista devuelta.
                    // Si la encontramos cargamos sus datos en la vista.
                    // ---------------------------------------------------------------------------------------------------
                    try {
                        umfList = umfService.findUmfByCodigoPostal(domicilioNuevo.getCodigoPostal().getCodigoPostal());
                    } catch (CodigoSinUmfException e) {
                    } catch (DerechohabientesBusinessException e) {
                    } catch (Exception e) {
                    }

                    if( (umfList != null) && !umfList.isEmpty()   ){

                        for( UnidadMedicaFamiliar _umfNuevoCodigo : umfList  ){

                            if( _umfNuevoCodigo.getIdUMF().equals(umf.getIdUMF())   ){

                                umfSeleccionada = umf.getIdUMF();

                                // ---------------------------------------------------------------------------
                                // Se valida el turno y el consultorio que apareceran en la vista por default
                                // ---------------------------------------------------------------------------
                                if( grupoFamiliar.getMedicoEnTurno() != null ){

                                    if( grupoFamiliar.getMedicoEnTurno().getTurno() != null ){
                                        turnoSeleccionado = grupoFamiliar.getMedicoEnTurno().getTurno().getIdTurno();
                                    }

                                    if( grupoFamiliar.getMedicoEnTurno().getConsultorio() != null){
                                        consultorioSeleccionado = grupoFamiliar.getMedicoEnTurno().getConsultorio().getIdConsultorio();
                                    }


                                    // -------------------------------------------------------------------------------------------------
                                    // Si el ultimo cambio de clinica es menor a 365 dias, no puede cambiar el medico y consultorio
                                    // -------------------------------------------------------------------------------------------------
                                    HashMap<String,Object> result = (HashMap<String, Object>) this.buscarMedicoEnTurnoActivo(grupoFamiliar);
                                    log.debug("===================== BUSCAR MEDICO =====================");
                                    log.debug(result);

                                    if( !(Boolean)result.get("error") ){
                                        if( !(Boolean)result.get("cambioPosible") )
                                            tramite.setIndSeleccionMedico(0);
                                    }


                                }



                            }
                        }
                    }

                }catch( NullPointerException np ){
                    log.error("esDerechohabiente ===================================================");
                    log.error(np);
                }


            }else{

                //-------------------------------------------------------------------
                // Soloamente derechohabientes pueden seleccionar o cambiar de UMF
                //-------------------------------------------------------------------
                cambioClinica = false;
            }

        }catch( Exception e ){

            // ---------------------------------------------------------------------
            // Muestra el error y no permite guardar o finalizar el tramite
            // ---------------------------------------------------------------------
            //error = "No fue posible encontrar la Unidad Medica perteneciente a su grupo familiar.";

            if( !esDerechohabiente){
                cambioClinica = false;
            }else{
                cambioClinica = true;
            }

        }


        // --------------------------------------------------------------------------
        // Validamos si el usuario en sesion es el mismo al que se
        // le aplicara el cambio de domicilio.
        //
        // En caso negativo obtenemos los datos del usuario en sesion para firmar
        // los documentos
        // --------------------------------------------------------------------------

        Long idOrigen =  new CommonValidator().getOrigenContext(request);
        if(!idOrigen.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())){
            if( usuario != null && (Integer.parseInt(usuario.getCveIdUsuario()) != persona.getIdPersona().intValue())   ){
                try{
                    persona = serviciosPersonaBusiness.buscarPersonaFisicaWidget(Long.parseLong(usuario.getCveIdUsuario()));
                } catch (PersonaNoEncontradaException e) {
                    // -----------------------------------------------
                    // Es el usuario en sesion, debe estar en la base
                    // -----------------------------------------------
                } catch (PersonaFisicaNoEncontradaException e) {
                    // -----------------------------------------------
                    // Es el usuario en sesion, debe estar en la base
                    // -----------------------------------------------
                }
            }
        }



        // --------------------------------------------
        // Cadena a incluir en los documentos
        // --------------------------------------------
        this.generarCadenaOriginal(solicitud, persona, session, grupoFamiliar);

        // ------------------------------------------------------------
        // Objeto FirmaElectronica con los datos del usuario en sesion
        // ------------------------------------------------------------
        this.obtenerDatosAcuse(solicitud, persona, session);


        model.addAttribute(KEY_DOMICILIOS_TRAMITE_CLINICA, tramite);
        requiereDocumentos(session, tramite.getTipoTramite().getIdTipoTramite().longValue());

        model.addAttribute("cambioClinica", cambioClinica);
        model.addAttribute("domicilio", domicilioNuevo);
        model.addAttribute("isRetomar", false);
        model.addAttribute("error", error);
        model.addAttribute("solicitudForm", solicitud);

        model.addAttribute("umfSeleccionada", umfSeleccionada);
        model.addAttribute("turnoSeleccionado", turnoSeleccionado);
        model.addAttribute("consultorioSeleccionado", consultorioSeleccionado);

        log.debug("el codigo postal quedo como: " + domicilioNuevo.getCodigoPostal());
        String nombreVialidad= DomicilioController.construirNombreVialidadPrimaria(domicilioNuevo);
        domicilioNuevo.setCalle(nombreVialidad);
        if(domicilioNuevo.getVialidadPrimaria()!= null) {
            domicilioNuevo.getVialidadPrimaria().setNombre(nombreVialidad);
        } else {
            domicilioNuevo.setVialidadPrimaria(new Vialidad());
            domicilioNuevo.getVialidadPrimaria().setNombre(nombreVialidad);
        }
        // --------------------------------------------------------------------------------------------------------
        // Datos para el wizard de la firma electronica
        // --------------------------------------------------------------------------------------------------------
        datosToSession(session, idTipoTramite);

        return VIEW_CONTENIDO;


    }

    private void requiereDocumentos(HttpSession session, Long tipoTramite) {
        log.debug("Se buscara si el tramite "+ tipoTramite + " requiere documentos probatorios");
        Boolean requiereDocumentos = false;
        /*
        try {
            requiereDocumentos = documentoProbatorioServiceBusinessRemote.requiereDocumentos(tipoTramite);
        } catch (Exception e) {
            log.error("Ocurrio un error al consultar si el tramite requiere documentos",e);
        }*/

        session.setAttribute(KEY_REQUIERE_DOCS, requiereDocumentos);

    }

    /**
     * Muestra los mensajes en un formato legible para el usuario
     *
     * @param bindingResult
     * @return Cadena con los errores ocurridos; si no existe alguno devuelve una
     * 		   cadena vac&iacute;a
     */
    private String getMessagesError(BindingResult bindingResult){

        StringBuffer errores = new StringBuffer();

        try{
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                errores.append(error.getObjectName() + " - " + error.getDefaultMessage()+" <br /> ");
            }

        }catch(Exception e){
            log.debug(e);
        }

        return errores.toString();

    }


    /**
     * Valida el domicilio capturado por la persona
     *
     * @param domicilio El domicilio capturado por la persona
     * @param response
     * @return
     */
    @RequestMapping(value = "/validarDomicilio", method = RequestMethod.POST)
    public  @ResponseBody Map<String, ? extends Object> validarDomicilio(@RequestBody Domicilio domicilio, HttpServletResponse response, final HttpSession session) {

        Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(domicilio, "model");
        new DomicilioConcluirValidator().validate(domicilio,errors);

        if(errors.hasErrors()){
            procesaErroresDeCaptura(errors, result, response);
        }else{

            // ------------------------------------------------------------------------------
            // Guardamos el domicilio para la siguiente pantalla
            // ------------------------------------------------------------------------------
            if( session.getAttribute(KEY_DOMICILIOS_NUEVO) != null ){

                Domicilio domicilioExistente = (Domicilio)session.getAttribute(KEY_DOMICILIOS_NUEVO);
                if( domicilioExistente.getClave() != null ){

                    // --------------------------------------------------------------
                    // Si tiene una clave significa que ya estaba en la base y lo
                    // estamos actualizando
                    // --------------------------------------------------------------
                    domicilio.setClave(domicilioExistente.getClave());

                }
            }
            log.debug("el codigo postal es [" + domicilio.getCodigoPostal().getCodigoPostal()+ "] y la clave" + domicilio.getClave() );
            session.setAttribute(KEY_DOMICILIOS_NUEVO, domicilio);


        }

        return result;
    }



    /**
     * Valida la informacion capturada en los pasos del wizard
     *
     * @param oForm
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody TramiteCorreccionDerechohabiente oForm,
                                                                         final HttpServletResponse response, final HttpSession session) {

        Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(oForm, "model");

        new DomiciliosValidator().validate(oForm, errors);

        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }

        return result;
    }

    @RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
                                                                   HttpServletResponse response, HttpSession session) {
        session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
        return null;
    }



    /**
     * Este metodo se encarga de finalizar la solicitud y tramite de prorroga.
     * Dependiendo del tipo de prorroga se haran ciertas validaciones, ademas de
     * esto se guardan los documentos capturados y se generan los documentos
     * resultantes.
     *
     * @param tramite
     * @param response
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/finalizar", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> finalizarSolicitud(@RequestBody TramiteCorreccionDerechohabiente tramiteClinica,
                                                                          HttpServletResponse response, HttpServletRequest request,	HttpSession session) {

        Map<String, Object> result = new HashMap<String, Object>();
        GrupoFamiliar grupoFamiliar = null;

        Domicilio domicilioNuevo = (Domicilio)session.getAttribute(KEY_DOMICILIOS_NUEVO);
        FirmaElectronica firma = null;
        Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD);

        Long idOrigen =  new CommonValidator().getOrigenContext(request);
        if(idOrigen.equals(OrigenSolicitudEnum.INTERNET.getId())){
            firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
        }

        // ---------------------------------------------------------------------
        // Actualmente se crea una nueva direccion, no se debe mandar el id
        // ---------------------------------------------------------------------
        domicilioNuevo.setClave(null);

        TramiteCorreccionDerechohabiente tramite = (TramiteCorreccionDerechohabiente)solicitud.getTramites().get(0);
        tramite.setDomicilio(domicilioNuevo);

        // --------------------------------------------------
        // Si es derechohabiente puede cambiar de clinica
        // --------------------------------------------------
        if( session.getAttribute(KEY_DOMICILIOS_GRUPO_FAMILIAR) != null ){
            grupoFamiliar = (GrupoFamiliar) session.getAttribute(KEY_DOMICILIOS_GRUPO_FAMILIAR);
            tramite.setPersona(grupoFamiliar.getDerechohabiente());
            tramite.setMedicoEnTurno(tramiteClinica.getMedicoEnTurno());
        }


        try {

            if( tramiteClinica.getFechaCambioMedico() != null ){
                tramite.setFechaCambioMedico(tramiteClinica.getFechaCambioMedico());
            }else{

                if(grupoFamiliar != null) {
                    Long umfId = grupoFamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF();

                    if( grupoFamiliar.getMedicoEnTurno() != null  && grupoFamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar() != null
                            && grupoFamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF() != null){

                        if(  !umfId.equals(tramiteClinica.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF()) ){
                            tramite.setFechaCambioMedico(new Date());
                        }
                    }

                }
            }


            solicitud.getTramites().set(0, tramite);
            session.setAttribute(KEY_SOLICITUD, solicitud);


            // -------------------------------------------------------------
            // Actualizamos la solicitud con el nuevo domicilio
            // -------------------------------------------------------------
            this.guardarSolicitud(tramite, session, request);

            solicitud.setFirmaElectronica(firma);
            solicitud = correccionDerechohabienteService.finalizarSolicitudDomicilioClinicaCircunscripcion(solicitud);

            //Solo si la persona forma parte de algun grupo familiar se generan documentos resultantes
            if(grupoFamiliar != null) {
                //EFM
                solicitud = solicitudBusinessRemote.consultar(solicitud);
                finalizaSolicitudService.finalizarSolicitudTramites(solicitud, "", grupoFamiliar.getAsignacionNSS());
                solicitudBusinessRemote.guardarDocumentosResultantesPorSolicitud(solicitud);
            }

            result.put("error", false);
            result.put("mensaje", "Su solicitud ha finalizado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", true);
            result.put("mensaje","Ocurri&oacute; un error al intentar guardar los cambios");
        }

        return result;
    }


    /**
     * Verifica si se activan los campos de medico y consultorio para el grupo familiar
     *
     * @param request
     * @param grupoFamiliar
     * @return
     */
    private Map<String,Object> buscarMedicoEnTurnoActivo(GrupoFamiliar grupoFamiliar) {
        return grupoFamiliarService.buscarMedicoEnTurnoActivo(grupoFamiliar);
    }


    /**
     * Busca el tipo de tramite en las solicitudes
     *
     * @param solicitudes
     * @return Si encuentra tramites del tipo que domicilio o colonia regresa true
     * @throws SolicitudesEnProcesoException Cuando el origen es Ventanilla
     */
    private Solicitud continenTramitesDomicilio(List<Solicitud> solicitudes, Long idOrigen) throws SolicitudesEnProcesoException{


        for( Solicitud solicitud: solicitudes  ){

            List<Tramite> tramites = solicitud.getTramites();

            for( Tramite tramite : tramites  ){

                try{
                    validaTipoTramitesPermitidos(tramite.getTipoTramite().getIdTipoTramite().longValue());
                }catch( ArgumentosInvalidosException e){

                    // -------------------------------------------------------------
                    // Si lanza la excepcion es por que no contiene el tipo de
                    // tramites que buscamos
                    // -------------------------------------------------------------
                    continue;
                }

                boolean esMismoOrigen = new CommonValidator().validarSolicitudMismoOrigen(solicitud, idOrigen);
                if(!esMismoOrigen)
                    throw new SolicitudesEnProcesoException();

                // ---------------------------------------------------------------------
                // Regresamos la solicitud solamente con el tramite del tipo buscado
                // ---------------------------------------------------------------------
                List<Tramite> _tramites = new ArrayList<Tramite>();
                _tramites.add(tramite);

                solicitud.setTramites(_tramites);

                return solicitud;

            }


        }

        return null;

    }

    private void datosToSession(final HttpSession session, Long idTipoTramite){
        session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor());
        session.setAttribute(KEY_DESC_TIPO_SOLICITUD, this.getTipoTramite(idTipoTramite));
        List<Long> listTipoTramite = new ArrayList<Long>();
        listTipoTramite.add(idTipoTramite);
        session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
    }
}
