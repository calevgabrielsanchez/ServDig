package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.wizard;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.CriptoUtilities;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroCvroUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroIvroUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.WebServiceCallerController;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.SeguroIndividualServices;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.*;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.comun.Modalidad;
import mx.gob.imss.digital.modelo.domicilio.Asentamiento;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.medio.contacto.CorreoElectronico;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.TipoPersona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.solicitud.EstadoSolicitud;
import mx.gob.imss.digital.modelo.solicitud.OrigenSolicitud;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;
import mx.gob.imss.digital.modelo.solicitud.TipoSolicitud;
import mx.gob.imss.digital.modelo.tramite.EstadoTramite;
import mx.gob.imss.digital.modelo.tramite.TipoTramite;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod40;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.ws.client.core.WebServiceTemplate;

@Controller
@SessionAttributes(value = { "tramiteSeguro", "tramites",
        "idPersonaSolicitante", "solicitante", "datosCalculo",
        "domicilioSeguro", "domicilioOtraUbicacion", "datosCotizacion",
        "solicitud", "fechaBajaMora", "idSeguroRenovacion" })
@RequestMapping(value = "/wizard/continuacionVoluntaria/renovacion")
public class WizardRenovacionModalidad40Controller extends WebServiceCallerController {

    private static final String KEY_NSS_MOD40 = "nss";
    private static final String KEY_NSS_CIFRADO_MOD40 = "nssCifrado";
    private static final Integer NUM_SALARIOS = 25;

    @Autowired
    @Qualifier("webServiceValidaPersonaContVoluntariaRenova")
    private WebServiceTemplate webServiceValidaPersonaContVoluntaria;
    
    
    @Autowired
    @Qualifier("webServiceObtenerSalarioMinimoDfPorFecha")
    private WebServiceTemplate webServiceObtenerSalarioMinimoDfPorFecha;
    
    @Autowired
    @Qualifier("webServiceCotizaCompraPersonaContVoluntaria")
    private WebServiceTemplate webServiceCotizaCompraPersonaContVoluntaria;

    @Autowired
    @Qualifier("webServiceDomicilio")
    private WebServiceTemplate webServiceDomicilio;

    @Autowired
    @Qualifier("webServiceValidaCompraPersonaContVoluntaria")
    private WebServiceTemplate webServiceValidaCompraPersonaContVoluntaria;
    
    @Autowired
    @Qualifier("webServiceSolicitudSeguroIvro")
    private WebServiceTemplate webServiceSolicitudSeguroIvro;

    @Autowired
    private SeguroIndividualServices seguroIndividualServices;

    @Autowired
    private SeguroCvroUtil seguroCvroUtil;

    @Autowired
    @Qualifier("parametrosServiceBusiness")
    private ParametrosServiceBusinessRemote parametrosServiceBusinessRemote;

    @ModelAttribute("tramiteSeguro")
    public TramiteSeguroIvroMod40 getTramiteSeguro() {
        return new TramiteSeguroIvroMod40();
    }

    @ModelAttribute("tramites")
    public List<TramiteSeguroIvroMod40> getTramites() {
        return new ArrayList<TramiteSeguroIvroMod40>();
    }

    @ModelAttribute("idPersonaSolicitante")
    public Long getIdPersonaSolicitante() {
        return new Long(0);
    }

    @ModelAttribute("solicitante")
    public Fisica getSolicitante() {
        return new Fisica();
    }

    @ModelAttribute("domicilioSeguro")
    public Domicilio getDomicilioSeguro() {
        return new Domicilio();
    }

    @ModelAttribute("domicilioOtraUbicacion")
    public Domicilio getDomicilioOtraUbicacion() {
        return new Domicilio();
    }

    @ModelAttribute("datosCalculo")
    public DatosCalculoCuota getDatosCalculo() {
        return new DatosCalculoCuota();
    }

    @ModelAttribute("datosCotizacion")
    public DatosCalculoCuota getDatosCotizacion() {
        return new DatosCalculoCuota();
    }

    @ModelAttribute("solicitud")
    public Solicitud getSolicitud() {
        return new Solicitud();
    }
    
    @ModelAttribute("idSeguroRenovacion")
    public Long getIdSeguroRenovacion() {
        return new Long(0);
    }
    
    
    @ModelAttribute("fechaBajaMora")
    public Date getFechaBaja() {
        return new Date();
    }

    @RequestMapping(value = "/alta/init/{idSeguro}/{idPersona}/{nssCifrado}", method = RequestMethod.GET)
    public String altaInit(Model model, SessionStatus sessionStatus,
            HttpSession session,@PathVariable String idSeguro, @PathVariable Long idPersona,
            @PathVariable String nssCifrado) {

        String nss = null;
        if (!nssCifrado.equals("-1")) {
            Map<String, Object> result = seguroCvroUtil.descifrarNss(session,
                    null, nssCifrado);
            nss = (String) result.get(KEY_NSS_MOD40);
            nssCifrado = (String) result.get(KEY_NSS_CIFRADO_MOD40);
        } else {
            nssCifrado = null;
        }
        
        model.addAttribute("ID_CIFRADO", idSeguro);

        log.info("idSeguro: "+idSeguro);
		Long idSeguroDecoded = 0l;
		try {
			idSeguroDecoded = Long.valueOf( CriptoUtilities.getIdFromUrl(idSeguro));
			System.out.println("idSeguroDecoded "+idSeguroDecoded);
			
		} catch (Exception e) {
			this.log.error("Error  al descifrar el id "+e);
			e.printStackTrace();
		}

        Fisica persona = new Fisica();
        persona.setIdPersona(idPersona);

        TipoPersona tipoPersona = new TipoPersona();
        tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
        persona.setTipoPersona(tipoPersona);
        persona.setNssCifrado(nssCifrado);
        persona.setNss(nss);

        TramiteSeguroIvroMod40 tramiteSeguro = new TramiteSeguroIvroMod40();
        tramiteSeguro.setSolicitante(persona);
        tramiteSeguro.setRenovacion(true);
        List<TramiteSeguroIvroMod40> tramites = new ArrayList<TramiteSeguroIvroMod40>();
        tramites.add(tramiteSeguro);

        model.addAttribute("tramiteSeguro", tramiteSeguro);
        model.addAttribute("tramites", tramites);
        model.addAttribute("idPersonaSolicitante", idPersona);
        model.addAttribute("solicitante", persona);
        model.addAttribute("idSeguroRenovacion", idSeguroDecoded);

        
        this.log.info("El idPersona es: " + persona.getIdPersona()
                +" con NSS: " + persona.getNss()
                +" y el ID SEGURO es: " +idSeguroDecoded);
        
        try {
            DatosCalculoCuota calculo = callWebService(
                    webServiceValidaPersonaContVoluntaria, persona,
                    DatosCalculoCuota.class);
            SeguroIvro seguro = seguroIndividualServices
            .getDetalleSeguro(idSeguroDecoded);        
            Date fechaBajaMora=seguroCvroUtil.obtenerFechaInicioMora(seguro.getCompra().getPagos());

            String FECHA_LIBERA_SEGS_CVRO_RENOVA = parametrosServiceBusinessRemote.obtenerParametroDeConfiguracion(ParametroSistemaEnum.FECHA_LIBERA_SEGS_CVRO_RENOVA.getCodigo());

            Date fechaLiberaSegsCvroRenova = new SimpleDateFormat("dd/MM/yyyy").parse(FECHA_LIBERA_SEGS_CVRO_RENOVA);

            if(fechaBajaMora.before(fechaLiberaSegsCvroRenova)){
                model.addAttribute("error", "No es posible realizar tu renovaci\u00F3n, por favor acude a tu subdelegaci\u00F3n");
            }else {
                model.addAttribute("fechaBajaMora", fechaBajaMora);
                model.addAttribute("datosCalculo", calculo);

                if (!calculo.getErrorFormGeneral().equals("")) {
                    this.log.info(" --- MENSAJE DEL WEBSERVICE: "
                            + calculo.getErrorFormGeneral() + " ---");
                    model.addAttribute("error", calculo.getErrorFormGeneral());
                }
            }
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrio un error al intentar validar los datos del solicitante.");
            log.error("--- Ocurrio un ERROR al intentar validar los datos del solicitante. ---", e);
        }

        return "wizardSeguroCVROInicioRenovacion";
    }
    
    private Date getPreviousDate(Date fechaOriginal) {
    	if(fechaOriginal != null){
    		Calendar cal = Calendar.getInstance();
            cal.setTime(fechaOriginal);
            cal.add(Calendar.DATE, -1);
            Date dateBefore1Day = cal.getTime();
            log.info("Fecha con dia previo:" + dateBefore1Day);
            return dateBefore1Day;
    	}
        log.info("La Fecha original es nula");
        return fechaOriginal;
    }

    @RequestMapping(value = "/comunes/agregarDomicilio")
    public String comunSolicitarDomicilio(Model model,
            HttpServletRequest request, HttpSession session,
            @ModelAttribute("solicitante") Fisica solicitante,
            @ModelAttribute("idPersonaSolicitante") Long idPersona,
            @ModelAttribute("datosCalculo") DatosCalculoCuota datosCalculo) {

        Persona persona = new Persona();
        persona.setIdPersona(idPersona);

        String nombreSolicitante = request.getParameter("nombreSolicitante");
        if (nombreSolicitante != null) {
            solicitante.setNombre(nombreSolicitante);
        }

        String mailSolicitante = request.getParameter("correoSolicitante");
        if (mailSolicitante != null) {
            CorreoElectronico correoElectronico = new CorreoElectronico();
            correoElectronico.setCorreo(mailSolicitante);
            solicitante.setCorreoElectronico(correoElectronico);
        }
        String zonaSalarialCompra = null;
        try {
            Domicilio domicilio = callWebService(webServiceDomicilio, persona,
                    Domicilio.class);

            solicitante.setIdPersona(idPersona);
            solicitante.setDomicilioParticular(domicilio);

            zonaSalarialCompra = this.validaDomicilio(domicilio);

        } catch (Exception e) {
            model.addAttribute("error", "Ocurrio un error al intentar obtener el domicilio del solicitante.");
            log.error("********** Ocurrio un error al intentar obtener el domicilio del solicitante.. **********", e);
        }
        session.setAttribute("zonaSalarialCompra",zonaSalarialCompra);
        model.addAttribute("datosCalculo", datosCalculo);
        model.addAttribute("solicitante", solicitante);
        return "wizardSeguroCVROConfirmarDomicilio";
    }

    private String validaDomicilio(Domicilio domicilio) {
        String cveMun;
        String cveEnt;
        if(domicilio!=null){
            log.info("El domicilio a revisar es: "+domicilio.getIdDomicilio());
            if(domicilio.getLocalidad()!=null){
                log.info("La localidad es: "+domicilio.getLocalidad().getClave());
                if(domicilio.getLocalidad().getMunicipio()!=null){
                    mx.gob.imss.digital.modelo.domicilio.Municipio municipio =
                            domicilio.getLocalidad().getMunicipio();
                    if(municipio.getEntidadFederativa()!=null){
                        log.info("Se recupera la zona salarial de la compra: ");
                        cveMun = municipio.getClave();
                        cveEnt = municipio.getEntidadFederativa().getClave();
                        String zonaSalarial = cveEnt+":"+cveMun;
                        log.info("zonaSalarial: "+zonaSalarial);
                        return zonaSalarial;
                    }
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/comunes/otraUbicacion")
    public String comunOtraUbicacion(Model model) {

        model.addAttribute("domicilioAlterno",
                new mx.gob.imss.ctirss.delta.model.domicilio.Domicilio());

        return "wizardCvroAgregarDomicilioRenovacion";
    }
    
    @RequestMapping(value = "/comunes/agregarDomicilioNueva", method = RequestMethod.POST)
    public String comunAgregarDomicilio(
            Model model,
            @ModelAttribute mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio) {

        domicilio
                .setCodigoPostal(domicilio.getAsentamiento().getCodigoPostal());
        domicilio.setLocalidad(domicilio.getAsentamiento().getLocalidad());
        domicilio.getVialidadPrimaria().setNombre(domicilio.getCalle());

        Domicilio domicilioOtraUbicacion = SeguroIvroUtil
                .convertirDomicilioAImssDigital(domicilio);

        domicilioOtraUbicacion.getAsentamiento().setMunicipio(
                domicilioOtraUbicacion.getAsentamiento().getLocalidad()
                        .getMunicipio());
        domicilioOtraUbicacion.setColonia(domicilioOtraUbicacion
                .getAsentamiento().getNombre());

        log.info("********* VALOR DEL DOMICILIO OTRA UBICACION: " + domicilioOtraUbicacion.toString() + " **********");

        model.addAttribute("domicilioOtraUbicacion", domicilioOtraUbicacion);

        return "wizardSeguroCVROConfirmarDomicilio";
    }
    
    @RequestMapping(value = "/comunes/datosInscripcion", method = RequestMethod.POST)
    public String datosInscripcion(
            Model model,
            HttpSession session,
            @ModelAttribute("datosCalculo") DatosCalculoCuota datosCalculo,
            @ModelAttribute("tramiteSeguro") TramiteSeguroIvroMod40 tramiteSeguro,
            @ModelAttribute("domicilioOtraUbicacion") Domicilio domicilioOtraUbicacion,
            @ModelAttribute("domicilioSeguro") Domicilio domicilioSeguro,
            @ModelAttribute("solicitante") Fisica solicitante,
            @ModelAttribute("fechaBajaMora") Date fechaBajaMora) {

        BigDecimal sdiMin = null;
        BigDecimal salMax = null;
        BigDecimal sdiUltReg = BigDecimal.ZERO;

        if (tramiteSeguro.getDomicilioSeguro().getIdDomicilio() != null) {
            domicilioSeguro = solicitante.getDomicilioParticular();
            log.info("**** Se continua con domicilio original: "
                    + "Colonia: " + domicilioSeguro.getColonia()
                    + "Calle: " + domicilioSeguro.getCalle()
                    + "Numero Exterior: " + domicilioSeguro.getNumExteriorAlf()
                    + "Numero Interior: " + domicilioSeguro.getNumInteriorAlf() + " ***");
        } else {
            domicilioSeguro = domicilioOtraUbicacion;
            /* Elimina caracteres no permitidos en un documento xml*/
            seguroCvroUtil.eliminarCaracteresNoPermitidosDomicilio(domicilioSeguro);
            log.info("**** Se continua con domicilio nuevo" + domicilioSeguro.getCalle() + " ****");
        }

        domicilioSeguro = this.validaAsentamiento(domicilioSeguro);

        solicitante.setDomicilioParticular(domicilioSeguro);
        try {
            sdiUltReg = datosCalculo.getEmpleados()[0].getMovimientos()[0].getSalarioMod40();
            this.log.info(" --- Ultimo Salario registrado:  " + sdiUltReg + " ---");

        } catch (Exception ex) {
            this.log.error(" --- Ultimo Salario registrado no disponible: " + sdiUltReg + " ---");
        }
        try {
            BigDecimal uma = this.seguroIndividualServices.obtenerUmaPorFecha(fechaBajaMora);
            this.log.info(" --- UMA: " + uma + " ---");
            // WEB SERVICE QUE OBTIENE EL SALARIO MINIMO VIGENTE DEL DF
//            BigDecimal salMin = this.seguroIndividualServices.obtenerSalarioMinimoVigenteDF("A");
//            this.log.info(" --- salMin: " + salMin + " ---");

            DatosCalculoCuota validaCompra = callWebService(
                    webServiceValidaCompraPersonaContVoluntaria, solicitante,
                    DatosCalculoCuota.class);
            BigDecimal salMin = validaCompra.getSalarioMinimo();
            this.log.info(" --- SMV: " + salMin + " ---");

            // DEBE SER EL SALARIO MINIMO ACORDE AL DF
            salMax = seguroCvroUtil.calcularSalaraioMaximo(uma);

            this.log.info(" --- Ultimo Salario registrado antes de validar:  "
                    + sdiUltReg + " ---");

            if (sdiUltReg != null) {
                int resultadoPrimeraCondicion;
                int resultadoSegundaCondicion;

                resultadoPrimeraCondicion = sdiUltReg.compareTo(salMin);
                resultadoSegundaCondicion = sdiUltReg.compareTo(salMax);

                if (resultadoPrimeraCondicion == 1 && resultadoSegundaCondicion == -1) {
                    //Si sdiUltReg > salMin y sdiUltReg < salMax
                    sdiMin = sdiUltReg;
                } else if (resultadoSegundaCondicion == 0) {
                    //Si sdiUltReg = salMax
                    sdiMin = salMax;
                } else if (resultadoSegundaCondicion == 1) {
                    //Si sdiUltReg > salMax
                    sdiMin = salMax;
                } else {
                    //Si sdiUltReg < salMin|
                    sdiMin = salMin;
                }

                //Si el ultimo salario no es null pero es cero se asigna el salario minimo
                if(sdiUltReg.compareTo(BigDecimal.ZERO)<=0){
                    sdiUltReg = sdiMin;
                }

            }else{
                //Si sdiUltReg no fue definido
                sdiMin = salMin;
                sdiUltReg = sdiMin;
            }

            if (!validaCompra.getErrorFormGeneral().equals("")) {
                this.log.info(" --- MENSAJE DEL WEBSERVICE: "
                        + validaCompra.getErrorFormGeneral() + " ---");
                model.addAttribute("error", validaCompra.getErrorFormGeneral());
            }
            model.addAttribute("datosCotizacion", validaCompra);
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrio un error al intentar validar los datos del solicitante.");
            log.error("********** Ocurrio un error al intentar validar los datos del solicitante. **********", e);
        }

        model.addAttribute("sdiUltReg",seguroCvroUtil.formatCurrency(sdiUltReg));
        model.addAttribute("sdiMin", seguroCvroUtil.formatCurrency(sdiMin));
        model.addAttribute("sdiMax", seguroCvroUtil.formatCurrency(salMax));
        model.addAttribute("solicitante", solicitante);
        model.addAttribute("datosCalculo", datosCalculo);

        return "wizardSeguroCVRODatosInscripcionRenovacion";
    }
    
    @RequestMapping(value = "/comunes/confirmarDatos", method = RequestMethod.POST)
    public String confirmarDatos(
            Model model,
            HttpServletRequest request,
            HttpSession session,
            @ModelAttribute("datosCalculo") DatosCalculoCuota datosCalculo,
            @ModelAttribute("datosCotizacion") DatosCalculoCuota datosCotizacion,
            @ModelAttribute("solicitante") Fisica solicitante,
            @ModelAttribute("tramiteSeguro") TramiteSeguroIvroMod40 tramiteSeguro,
            @ModelAttribute("fechaBajaMora") Date fechaBajaMora,
            @ModelAttribute("idSeguroRenovacion") Long idSeguroRenovacion) {

        String view;
        boolean recargoPorFechaBaja = true;

        String salarioCotizar = request.getParameter("salario");
        this.log.info(" --- Salario a cotizar: " + salarioCotizar + " ---");

        Date fechaBaja;
        BigDecimal ultSdi;
        String sdiUltRegFormat = "";
        try {
            fechaBaja = fechaBajaMora;
            ultSdi = datosCalculo.getEmpleados()[0].getMovimientos()[0].getSalarioMod40();
            this.log.info(" --- Ultimo Salario registrado:  " + ultSdi + " ---");
            sdiUltRegFormat = seguroCvroUtil.formatCurrency(ultSdi);
        } catch (Exception ex) {
            fechaBaja = null;
            ultSdi = null;
        }

        /* Solicitud del CVRO a la fecha de: */
        Date fechaTramite = fechaBaja;
        this.log.info(" --- Tipo: RENOVACION y la Solicitud es a la fecha de baja: " + fechaBaja + " ---");

        List<Fisica> integrantes = new ArrayList<Fisica>();
        integrantes.add(solicitante);

        try {

            String zonaSalarialCompra = (String)session.getAttribute("zonaSalarialCompra");

            if(zonaSalarialCompra!=null){
                if(datosCotizacion.getZonaSalarial()!=null &&
                        !datosCotizacion.getZonaSalarial().equals(zonaSalarialCompra)){
                    log.info("La zona salarial es diferente: ");
                    String zonaSalarialCapturada = datosCotizacion.getZonaSalarial();
                    datosCotizacion.setZonaSalarial(zonaSalarialCapturada+";"+zonaSalarialCompra);
                }else{
                    log.info("La zona salarial es igual, por lo que no se guarda");
                }
            }

            Cotizacion cotizacionSolicitante = this.generarCotizacion(
                    solicitante, integrantes, datosCotizacion, salarioCotizar,
                    recargoPorFechaBaja, fechaTramite, ultSdi);

            this.log.info(" --- Cotizacion: " + cotizacionSolicitante.getIdCotizacion() 
                    + "\n --- Concepto: " + cotizacionSolicitante.getConcepto()
                    + "\n --- Fecha Inicio: " + cotizacionSolicitante.getDetalle().getFechaInicioCalculo()
                    + "\n --- Fecha Fin: " + cotizacionSolicitante.getDetalle().getFechaFinCalculo()
                    + "\n --- Salario a cotizar: " + salarioCotizar);

            tramiteSeguro.setBeneficiarios(integrantes.toArray(new Fisica[0]));
            tramiteSeguro.setCotizacion(cotizacionSolicitante);
            tramiteSeguro.setIdSeguroAnterior(idSeguroRenovacion);

            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO
                    .getId());
            tramiteSeguro.setEstadoTramite(estadoTramite);

            Modalidad modalidad = new Modalidad();
            modalidad.setIdModalidad(datosCalculo.getModalidad());
            tramiteSeguro.setModalidad(modalidad);

            tramiteSeguro.setPersona(solicitante);


            TipoTramite tipoTramite = new TipoTramite();
            tipoTramite
                    .setIdTipoTramite(TipoTramiteEnum.RENOVACION_CONTINUACION_VOLUNTARIA
                            .getCodigo());
            tramiteSeguro.setTipoTramite(tipoTramite);

            Solicitud solicitud = new Solicitud();
            solicitud
                    .setTramite(new TramiteSeguroIvroMod40[] { tramiteSeguro });
            solicitante.setIdPersona(solicitante.getIdPersona());

            OrigenSolicitud origenSolicitud = new OrigenSolicitud();
            origenSolicitud.setIdOrigenSolicitud(SeguroIvroUtil
                    .getAmbiente(request));
            solicitud.setOrigenSolicitud(origenSolicitud);

            TipoSolicitud tipoSolicitud = new TipoSolicitud();
            tipoSolicitud
                    .setIdTipoSolicitud(TipoSolicitudEnum.CONTINUACION_VOLUNTARIA_REGIMEN_OBLIGATORIO
                            .getId());
            solicitud.setTipoSolicitud(tipoSolicitud);

            EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
            estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA
                    .getId().intValue());
            solicitud.setEstadoSolicitud(estadoSolicitud);
            solicitud.setFechaRegistro(new Date());

            log.info("********** ID PERSONA: " + ((TramiteSeguroIvroMod40) solicitud.getTramite()[0]).getPersona().getIdPersona() + " **********");

            // Se agrega usuario
            String strUsuario;
            Long ambiente = SeguroIvroUtil.getAmbiente(request);

            if (!OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(ambiente)) {
                UsuarioSSO sso = this.procesarUsuarioSSO(request);
                strUsuario = sso.getCurp();
            } else {
                strUsuario = seguroIndividualServices
                        .obtenerCurpPorIdPersona(solicitante.getIdPersona());
            }
            solicitud.setUsuario(strUsuario);

            Solicitud solicitudResultado = new Solicitud();
            try {
                solicitudResultado = callWebService(
                        webServiceSolicitudSeguroIvro, solicitud,
                        Solicitud.class, new Class[] { Solicitud.class,
                                TramiteSeguroIvroMod40.class });



            } catch (Exception e) {
                model.addAttribute("error", "Ocurrio un error al intentar registrar la solicitud.");
                log.error("********** Ocurrio un error al intentar registrar la solicitud.", e);
                view = "wizardContinuacionVoluntariaAltaInit";
            }

            if (solicitudResultado.getErrorFormGeneral() != null
                    && !solicitudResultado.getErrorFormGeneral().trim()
                            .isEmpty()) {
                model.addAttribute("error",
                        solicitudResultado.getErrorFormGeneral());
                view = "wizardSeguroCVROInicioRenovacion";
            }

            solicitud.setIdSolicitud(solicitudResultado.getIdSolicitud());
            solicitud.setNumSolicitud(solicitudResultado.getNumSolicitud());
            solicitud.getTramite()[0].setTramiteId(solicitudResultado
                    .getTramite()[0].getTramiteId());

//            log.info("********** ID SOLICITUD: " + solicitud.getIdSolicitud() + " **********");
            log.info("--- El ID PERSONA del solicitante es: " + solicitante.getIdPersona() + "con el ID SOLICITUD: " + solicitud.getIdSolicitud());

            seguroCvroUtil.generarCadenaOriginalyFirma(solicitud, solicitante, session);

            model.addAttribute("periodos", cotizacionSolicitante.getDetalle()
                    .getEmpleados()[0].getPeriodos());
            model.addAttribute("solicitud", solicitud);
            session.setAttribute("solicitud", solicitud);
            model.addAttribute("solicitante", solicitante);
            model.addAttribute("fechaSolicitud", solicitud.getFechaRegistro());

            model.addAttribute("fechaBaja", fechaBaja);
            
//            log.info("Fecha fechaBajaMora antes de modificacion:"+fechaBaja);
            log.info("El ID PERSONA: "+ solicitante.getIdPersona() + " con FechaBajaMora antes de modificacion es: " + fechaBaja);
            /** resta un dia **/
            Date fechaPreviaBajaMora = getPreviousDate(fechaBaja);
            
            model.addAttribute("fechaPreviaBajaMora", fechaPreviaBajaMora);
            model.addAttribute("ultSdi", sdiUltRegFormat);

            model.addAttribute("sbc", salarioCotizar);
            /**Dado que es una renovacion, se levanta boolean para el DetalleController*/
            session.setAttribute("enviarCorreo", Boolean.TRUE);

            return "wizardSeguroCVROConfirmarDatosRenovacion";

        } catch (IVROServiceException e) {
            log.error("********** Ocurrio un error al intentar generar la cotizacion. **********", e);
            model.addAttribute("error", e.getMessage());
            view = "wizardSeguroCVROInicioRenovacion";
        }

        return view;
    }

    private Cotizacion generarCotizacion(Fisica solicitante,
            List<Fisica> integrantes, DatosCalculoCuota datosCalculo,
            String sdi, boolean recargoPorFechaBaja, Date fechaTramite,
            BigDecimal ultimoSalarioRegistrado)
            throws IVROServiceException {

        String sueldoS = sdi;
        BigDecimal sueldoDiarioTrabajador = new BigDecimal(
                StringUtils.isBlank(sueldoS) ? "0" : sueldoS);

        DatosCalculoCuota dcc = new DatosCalculoCuota();
        Calendar calendarTemporal = Calendar.getInstance();
        calendarTemporal.setTime(fechaTramite);
        dcc.setFechaInicioCalculo(calendarTemporal);
        dcc.setAplicaRecargoPorFechaBaja(true);
        dcc.setRecargos(true);
        this.log.info(" -- Aplica recargos por Baja por mora: "
                + dcc.getAplicaRecargoPorFechaBaja());
        this.log.info(" -- Fecha de BAJA para el calculo: "
                + dcc.getFechaInicioCalculo());
        dcc.setFechaFinCalculo(datosCalculo.getFechaFinCalculo());
        dcc.setNumeroRegistroPatronal(datosCalculo.getNumeroRegistroPatronal());
        dcc.setModalidad(datosCalculo.getModalidad());
        dcc.setZonaSalarial(datosCalculo.getZonaSalarial());
        dcc.setRenovacion(true);
        dcc.setAplicaCuestionario(datosCalculo.getAplicaCuestionario());

        // Se agrega el ultimoSalarioRegistrado
        dcc.setSalarioMinimo(ultimoSalarioRegistrado);
        dcc.setIdEmpleador(solicitante.getIdPersona());

        // Datos del integrante
        List<DatosEmpleado> empleados = new ArrayList<DatosEmpleado>();

        for (Fisica integrante : integrantes) {
            DatosEmpleado empleado = new DatosEmpleado();
            empleado.setNumeroSeguridadSocial(integrante.getNss());
            empleado.setSalario(sueldoDiarioTrabajador);
            empleados.add(empleado);
            empleado.setEdad(0);
            empleado.setParentesco(-1L);
        }

        dcc.setEmpleados(empleados.toArray(new DatosEmpleado[0]));

        Cotizacion cotizacionIntegrante = new Cotizacion();
        try {
            log.info("********** Enviando datos: Modalidad: "
                    + dcc.getModalidad() + "\nFechaInicioCalculo: "
                    + dcc.getFechaInicioCalculo() + "\nFechaFinCalculo: "
                    + dcc.getFechaFinCalculo() + "\nZonaSalarial: "
                    + dcc.getZonaSalarial() + "\nNSSEmpleado: "
                    + dcc.getEmpleados()[0].getNumeroSeguridadSocial()
                    + "\nSalarioDiarioEmpleado: "
                    + dcc.getEmpleados()[0].getSalario() + "\nAplicaRecargo:"
                    + dcc.getRecargos() + "\nAplicaRecargoPorFechaBaja:"
                    + dcc.getAplicaRecargoPorFechaBaja() + "\nSalarioMinimoRegistrado:"
                    + dcc.getSalarioMinimo());

            cotizacionIntegrante = callWebService(
                    webServiceCotizaCompraPersonaContVoluntaria, dcc,
                    Cotizacion.class);
        } catch (Exception e) {
            String error = "Ocurrio un error al intentar realizar la cotizacion del integrante con NSS. Intenta nuevamente.";

            log.error(error, e);
            throw new IVROServiceException(error);
        }

        if (cotizacionIntegrante.getErrorFormGeneral() != null
                && !cotizacionIntegrante.getErrorFormGeneral().trim().isEmpty()) {
            log.error(cotizacionIntegrante.getErrorFormGeneral());
            throw new IVROServiceException(
                    cotizacionIntegrante.getErrorFormGeneral());
        }

        if (cotizacionIntegrante.getDetalle() != null
                && cotizacionIntegrante.getDetalle().getEmpleados().length > 0) {
            Arrays.sort(cotizacionIntegrante.getDetalle().getEmpleados()[0]
                    .getPeriodos(), new Comparator<PeriodoCuota>() {
                @Override
                public int compare(PeriodoCuota o1, PeriodoCuota o2) {
                    if (o1.getOrden() < o2.getOrden()) {
                        return -1;
                    } else if (o1.getOrden() > o2.getOrden()) {
                        return 1;
                    }
                    return 0;
                }
            });
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss");

            for (PeriodoCuota periodoCuota : cotizacionIntegrante.getDetalle()
                    .getEmpleados()[0].getPeriodos()) {
                try {
                    if (periodoCuota.getSalarioPeriodo() == null
                            || periodoCuota.getSalarioPeriodo().equals(
                                    BigDecimal.ZERO)) {
                        Map response = callWebServiceSimpleParameter(
                                webServiceObtenerSalarioMinimoDfPorFecha,
                                "<mx:fecha xmlns:mx=\"http://mx.gob.imss.digital.modelo.seguros\">"
                                        + format.format(periodoCuota
                                                .getInicioPeriodo().getTime())
                                        + "</mx:fecha>", Map.class);

                        BigDecimal salarioMinimo = new BigDecimal(response.get(
                                "salarioMinimo").toString());
                        BigDecimal salarioMaxPermit = salarioMinimo
                                .multiply(BigDecimal.valueOf(NUM_SALARIOS));

                        if (new BigDecimal(sueldoS).compareTo(salarioMaxPermit) > 0) {
                            // Si el salario puesto es mayor al salario Max
                            // pemitido
                            // Se le pone el maximo (NUM_SALARIOS veces el
                            // salario minimo de ese tiempo)
                            periodoCuota.setSalarioPeriodo(salarioMaxPermit);
                        } else if (salarioMinimo.compareTo(new BigDecimal(
                                sueldoS)) > 0) {
                            periodoCuota.setSalarioPeriodo(salarioMinimo);
                        }
                        log.debug("No se obtuvo salario base de cotizacion, se realiza calculo manual: " + periodoCuota.getSalarioPeriodo());
                    } else {
                        log.debug("Se obtiene salario base de cotizacion: "+ periodoCuota.getSalarioPeriodo());
                    }
                } catch (Exception e) {
                    String error = "Ocurrio un error al intentar consultar el salario minimo.";
                    log.error(error, e);
                    throw new IVROServiceException(error);
                }
            }
        } else {
            String error = "Ocurrio un error al intentar realizar la cotizacion. Intenta nuevamente.";

            log.error(error);
            throw new IVROServiceException(error);
        }

        return cotizacionIntegrante;
    }


    private Domicilio validaAsentamiento(Domicilio domicilio){

            if(domicilio.getAsentamiento()!=null){
                if(domicilio.getAsentamiento().getLocalidad()==null){
                    log.info("Existe asentamiento pero no tiene localidad");
                    if(domicilio.getLocalidad()!=null) {
                        log.info("Existe una localidad en /domicilio/localidad  se setea en Asentamiento");
                        domicilio.getAsentamiento().setLocalidad(domicilio.getLocalidad());
                    }else{
                        log.error("No hay localidad para asignar");
                    }

                }else{
                    log.info("Existe un asentamiento y tiene localidad :"+domicilio.getAsentamiento().getLocalidad().getClave());
                }

            }else{
                log.info("No existe Asentamiento");
                if(domicilio.getLocalidad()!=null){
                    log.info("Existe una localidad en /domicilio/localidad  se genera en Asentamiento");
                    domicilio.setAsentamiento(new Asentamiento());
                    domicilio.getAsentamiento().setPeriodo(0L);
                    domicilio.getAsentamiento().setLocalidad(domicilio.getLocalidad());
                }
            }

        return domicilio;
    }
}
