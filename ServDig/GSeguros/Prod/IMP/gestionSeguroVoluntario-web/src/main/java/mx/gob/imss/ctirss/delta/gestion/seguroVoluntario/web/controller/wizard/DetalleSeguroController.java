/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.wizard;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROExceptionGenerico;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroIvroUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.vo.InfoPagoObject;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.reporte.GeneradorComprobanteSeguro;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.EnvioEmail;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.SeguroIndividualServices;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaRifSat;
import mx.gob.imss.ctirss.delta.model.enums.*;
//import mx.gob.imss.ctirss.delta.model.gestion.cobranza.PagoCVRO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.Pagos;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.satRiss.DatosRiss;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.*;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.ConvertObjectInfoPago;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.CriptoUtilities;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.vo.InfoPagoObject;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.vo.DatosLinea;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleReingresoRODTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleMoraDTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleBajaExpresaDTO;


/**
 * Controller para la vista de los detalles del seguro
 * @author NOVUTECK1
 *
 */
@Controller
@RequestMapping(value = "/wizard/detalle/seguro/")
public class DetalleSeguroController extends AbstractController {

    private static final String EXPIRES = "Expires";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String CONSTANT_HEADER = "must-revalidate, post-check=0, pre-check=0";
    private static final String PRAGMA = "Pragma";
    private static final String PUBLIC = "public";
    private static final String CONTENT_TYPE = "application/pdf";
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String ATTACHMENT = "attachment; filename=";
    private static final String ENVIAR_CORREO = "enviarCorreo";
    private static final String CORREO_RENOVACION_IVRO = "enviarCorreoRenovacionIVRO";
    private static final String CORREO_COMPRA_IVRO = "enviarCorreoCompraIVRO";
    private static final String CORREO_RENOVACION_SSF = "enviarCorreoRenovacionSSF";
    private static final String CORREO_COMPRA_SSF = "enviarCorreoCompraSSF";
    private static final Long ESTADO_SEGURO_CANCELADO_RISS = 4L;

    /**
     *
     * Servicio para envio de correo electronico
     *
     */
    @Autowired
    private EnvioEmail emailService;
    /**
     * Servicio para obtener las lineas de captura de un pago
     */
    @Autowired
    @Qualifier("webServiceLineasCaptura")
    private WebServiceTemplate webServiceLineasCaptura;
    /**
     * Servicio para generar los comprobantes del seguro
     */
    @Autowired
    private GeneradorComprobanteSeguro comprobanteSeguro;
    /**
     * Servicios para el manejo de seguros
     */
    @Autowired
    private SeguroIndividualServices seguroServices;

    @Autowired
    @Qualifier("seguroIvroServiceBusiness")
    SeguroIvroServiceRemote seguroIvroServiceRemote;

	@Autowired 
	@Qualifier("compraServiceBusiness") 
	CompraServiceRemote compraServiceRemote;
    
    @Autowired
    @Qualifier("personaBusiness")
    PersonaBusinessRemote personaBusinessRemote;

    @Autowired
    @Qualifier("beneficioRissServiceBusiness")
    BeneficioRissServiceBusinessRemote beneficioRissServiceBusinessRemote;
    
    @Autowired
	@Qualifier("webServiceValidaPersonaContVoluntariaRenova")
	private WebServiceTemplate webServiceValidaPersonaContVoluntariaRenova;

    /**
     * Inits the modificar datos persona.
     *
     * @param model the model
     * @param session the session
     * @param request the request
     * @param idSeguro the id seguro
     * @return the string
     */
    @RequestMapping(value = "/{idSeguro}", method = RequestMethod.GET)
    public String initModalidad(Model model, HttpSession session,
            HttpServletRequest request, @PathVariable String idSeguro) {
    	
    	System.out.println("desde initmodalidad");
    	model.addAttribute("ID_CIFRADO", idSeguro);

		Long idSeguroDecoded = 0l;
		try {
			idSeguroDecoded = Long.valueOf( CriptoUtilities.getIdFromUrl(idSeguro));
			System.out.println("idSeguroDecoded "+idSeguroDecoded);
			
		} catch (Exception e) {
			this.log.error("Error  al descifrar el id "+e);
			e.printStackTrace();
		}
    	
        String view = SeguroIvroUtil.VIEW_DETALLE_SEGURO;

        SeguroIvro seguro = seguroServices.getDetalleSeguro(idSeguroDecoded);
        seguro.setFechaFin(SeguroIvroUtil.getFechaFinalSeguro(seguro));

        /*
         * Se genera el objeto usuario para la validaci�n del filtro
         */
        Long cvePersona = seguro.getTitular().getIdPersona();
        Fisica persona = personaBusinessRemote.getPersonaFisica(cvePersona);
        session.setAttribute("usuario", persona);
        /*
         *  Se ordena de forma ascendente la lista de pagos por id
         */
        
        model.addAttribute("seguro", seguro);
        model.addAttribute("domestico", seguro.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId());

        String claseEstado = SeguroIvroUtil.getClaseEstado(seguro);

        model.addAttribute("claseEstado", claseEstado);

        Long ambiente = SeguroIvroUtil.getAmbiente(request);
        model.addAttribute("ventanilla", OrigenSolicitudEnum.VENTANILLA.getId().equals(ambiente));

        /*
		 * Se agrega validacion que checa si la modalidad es 33 o 40 para
		 * responder una vista diferente a la comun que se tiene para seguros
		 * dometicos/individual
         */
        long tipoModalidad = seguro.getModalidad().getIdModalidad();
        String numModalidad = ModalidadEnum.fromId(tipoModalidad).getNumModalidad();

        this.log.debug("Se va a mostrar el detalle de seguro [id=" + idSeguro
                + "] con modalidad " + numModalidad + " renovacion " + seguro.getTramite().getRenovacion());
        if (seguro.getTramite().getRenovacion() && seguro.getCompra() != null
                && seguro.getCompra().getPagos() != null && seguro.getCompra().getPagos().length > 0) {
            model.addAttribute("imprimir", puedeImprimir(seguro.getCompra().getPagos()));
        }else if(!seguro.getTramite().getRenovacion()){
        	model.addAttribute("imprimir", puedeImprimir(seguro.getCompra().getPagos()));
        	model.addAttribute("enRenovacionTh", seguro.getTramite().getRenovacion());
        }
        if (seguro.getTramite() != null && seguro.getTramite().getRenovacion() != null) {
            model.addAttribute("enRenovacion", seguro.getTramite().getRenovacion());
            model.addAttribute("enRenovacionTh", seguro.getTramite().getRenovacion());

        } else {
            model.addAttribute("enRenovacion", false);
            model.addAttribute("enRenovacionTh", false);
        }
        if (tipoModalidad == ModalidadEnum.TREINTAYTRES.getId()) {
            view = "wizardSeguroFamiliarDetalle";
        } else if (tipoModalidad == ModalidadEnum.CUARENTA.getId()) {
            if (seguro.getEstadoSeguro().getIdEstadoSeguro() == EstadoSeguroIvroEnum.BAJA_POR_MORA.getId()) {
                //ECU 4.1.3B MUESTRA EL DETALLE DE RENOVACION
                view = "wizardSeguroCVRODetalleRenovacion";
            } else {
                //detalle normal
                Boolean mensajeExito = (Boolean) session.getAttribute("mostrarMensajeExito");
                if (mensajeExito == null) {
                    mensajeExito = false;
                }
                session.removeAttribute("mostrarMensajeExito");

                model.addAttribute("mensajeExito", mensajeExito);

                view = "wizardSeguroCVRODetalle";
            }
        }

        /*
		 * Se ordena de forma ascendente la lista de pagos por fecha limite de
		 * pago
         */
        SeguroIvroUtil.ordenarPagosPorFechaLimitePago(seguro);

        if((tipoModalidad == ModalidadEnum.TREINTAYCINCO.getId() ||
                tipoModalidad == ModalidadEnum.CUARENTAYTRES.getId() ||
                    tipoModalidad == ModalidadEnum.CUARENTAYCUATRO.getId()) &&
                        seguro.getCompra().getFormaPago() == FormaPagoEnum.BIMESTRAL.getId()){
            seguro = seguroIvroServiceRemote.confirmaPagosDeSeguro(seguro);
            model.addAttribute("seguro", seguro);
        }
		
        // Inicia generacion JSON multipagos    
        
		InfoPagoObject infoPagoObject = new InfoPagoObject();
		
		String claveAplicativo = null;
		
		if (tipoModalidad == ModalidadEnum.TREINTAYCINCO.getId() ||
					tipoModalidad == ModalidadEnum.CUARENTAYTRES.getId() ||
                    tipoModalidad == ModalidadEnum.CUARENTAYCUATRO.getId()){
                    	claveAplicativo = "IVRO";	
        } else if (tipoModalidad == ModalidadEnum.TREINTAYTRES.getId()) {
                    	claveAplicativo = "SSF";
        } else if (tipoModalidad == ModalidadEnum.CUARENTA.getId()) {
                    	claveAplicativo = "CVRO";
        }
		infoPagoObject.setClaveAplicativo(claveAplicativo);
		List<DatosLinea> datosLineas = new ArrayList<DatosLinea>();
		Date today = new Date();
		
		for (Pago pago : seguro.getCompra().getPagos()) {

				log.info("tipoModalidad:" + tipoModalidad);
				log.info("limite de pago:" + pago.getFechaLimitePago());
				log.info("es imprimible:" + pago.getImprimible());
				log.info("Estado pago:" + pago.getEstadoPago());
				log.info("LC: " + pago.getLineaCaptura());
	
			if (
					(tipoModalidad == ModalidadEnum.CUARENTA.getId() || tipoModalidad == ModalidadEnum.TREINTAYTRES.getId()) && 
					today.getTime() < (pago.getFechaLimitePago().getTime() + 86400000L)
					&& (pago.getImprimible() || (pago.getEstadoPago().getIdEstadoPago() != EstadoPagoEnum.PAGADO.getId()
					&& pago.getEstadoPago().getIdEstadoPago() != EstadoPagoEnum.VENCIDO.getId()))) {

				if (pago.getLineaCaptura() != null && !pago.getLineaCaptura().isEmpty()) {

					datosLineas.add(generaDatosLinea(pago));
				}
			} else if (
					(tipoModalidad == ModalidadEnum.TREINTAYCINCO.getId() ||
					tipoModalidad == ModalidadEnum.CUARENTAYTRES.getId() ||
                    tipoModalidad == ModalidadEnum.CUARENTAYCUATRO.getId()) &&
					today.getTime() < (pago.getFechaLimitePago().getTime() + 86400000L)
					&& (pago.getImprimible() && (pago.getEstadoPago().getIdEstadoPago() != EstadoPagoEnum.PAGADO.getId()
					&& pago.getEstadoPago().getIdEstadoPago() != EstadoPagoEnum.VENCIDO.getId()))) {
				
				if (pago.getLineaCaptura() != null && !pago.getLineaCaptura().isEmpty()) {
					
					datosLineas.add(generaDatosLinea(pago));
				}
			}
			else {
				log.info("La modalidad no aplica para hacer multipago");
			}
		}
	
		 if(!datosLineas.isEmpty()) {
	            infoPagoObject.setDatosLinea(datosLineas);
	            ConvertObjectInfoPago convert = new ConvertObjectInfoPago();
	            String jsonInfoPago = convert.getJsonInfoPago(infoPagoObject);
	            model.addAttribute("jsonInfoPago", jsonInfoPago);
	            
	            log.info(jsonInfoPago);
	        }
			
// ========== REQUERIMIENTO 6: Consulta detalle reingreso RO ==========
		 try {
			    String tipoBajaActual = seguroIvroServiceRemote.obtenerTipoBajaActual(idSeguroDecoded);
			    model.addAttribute("tipoBajaActual", tipoBajaActual);  // "MORA", "REINGRESO_RO", "EXPRESA" o null
			 
			    // Solo cargar detalle si est� en baja por MORA
			    if ("MORA".equals(tipoBajaActual)) {
			        DetalleMoraDTO detalleMora = seguroIvroServiceRemote.obtenerDetalleMoraParaJSP(idSeguroDecoded);
			        if (detalleMora != null) {
			            model.addAttribute("detalleMora", detalleMora);
						this.log.debug("Detalle MORA agregado al modelo para seguro: " + idSeguro);
			 
			        } 
			    } else if(String.valueOf(EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO.getId()).equals(tipoBajaActual)) {
					DetalleReingresoRODTO detalleReingreso =
			        seguroIvroServiceRemote.obtenerDetalleReingresoParaJSP(idSeguroDecoded);
			 
					if (detalleReingreso != null) {
						model.addAttribute("detalleReingresoRO", detalleReingreso);
						// no se muestra la columna descargar si el seguro
						//tiene estatus de cancelado
						model.addAttribute("imprimir", false);
						this.log.debug("Detalle reingreso RO agregado al modelo para seguro: " + idSeguro);
					}
				} else if(String.valueOf(EstadoSeguroIvroEnum.BAJA_A_SOLICITUD_ASEGURADO.getId()).equals(tipoBajaActual)) {
					this.log.info("baja expresa *******");
					DetalleBajaExpresaDTO detalleBajaExpresa =
					        seguroIvroServiceRemote.obtenerDetalleBajaExpresaParaJSP(idSeguroDecoded);
					 
							if (detalleBajaExpresa != null) {
								model.addAttribute("detalleBajaExpresa", detalleBajaExpresa);
								// no se muestra la columna descargar si el seguro
								//tiene estatus de cancelado
								model.addAttribute("imprimir", false);
								model.addAttribute("ID_CIFRADO", idSeguro);
								this.log.debug("Detalle baja expresa agregado al modelo para seguro: " + idSeguro);
							}
						}
			} catch (Exception ex) {
			    this.log.warn("Error obteniendo detalle baja: " + ex.getMessage());
			}
// ========== FIN REQUERIMIENTO 6 ==========		
			
        return view;
    }
    
    private DatosLinea generaDatosLinea(Pago pago) {
        DatosLinea datosLinea = new DatosLinea();
        datosLinea.setLineaCaptura(pago.getLineaCaptura());
        datosLinea.setNumFolioSua(String.valueOf(pago.getSuaPago().getPatron().getFolioSUA()));
        datosLinea.setMontoPagar(pago.getMonto().doubleValue());
        datosLinea.setRegistroPatronal(pago.getSuaPago().getPatron().getRegistroPatronalIMSS());
        datosLinea.setNombreRegistroPatronal(pago.getSuaPago().getPatron().getNombreORazonSocial());

        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(pago.getFechaInicioPeriodo());

        int mes = 1 + calendar.get(Calendar.MONTH);
        int anio = calendar.get(Calendar.YEAR);

        Formatter fmt = new Formatter();

        datosLinea.setNumPeriodoAseguramiento(anio + fmt.format("%02d", mes).toString());
        datosLinea.setIdCotizacion(pago.getIdPago());
        
        return datosLinea;
    }
    
    @RequestMapping(value = "/domestico/{idSeguro}", method = RequestMethod.POST)
    public String initDomesticoModalidad(Model model, HttpSession session,
            HttpServletRequest request, @PathVariable Long idSeguro) {

        String nssCifrado = request.getParameter("nssCifrado");
        String cadenaVacia = "";

        if (nssCifrado.equals(cadenaVacia)) {
            nssCifrado = "-1";
        }

        String view = SeguroIvroUtil.VIEW_DETALLE_SEGURO;

        SeguroIvro seguro = seguroServices.getDetalleSeguro(idSeguro);
        seguro.setFechaFin(SeguroIvroUtil.getFechaFinalSeguro(seguro));

        /*
		 * Se ordena de forma ascendente la lista de pagos por fecha limite de
		 * pago
         */
        SeguroIvroUtil.ordenarPagosPorFechaLimitePago(seguro);

        if(seguro.getCompra().getFormaPago() == FormaPagoEnum.BIMESTRAL.getId()) {
            seguro = seguroIvroServiceRemote.confirmaPagosDeSeguro(seguro);
        }

        model.addAttribute("seguro", seguro);
        model.addAttribute("domestico", seguro.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId());

        String claseEstado = SeguroIvroUtil.getClaseEstado(seguro);
        model.addAttribute("claseEstado", claseEstado);

        Long ambiente = SeguroIvroUtil.getAmbiente(request);
        model.addAttribute("ventanilla", OrigenSolicitudEnum.VENTANILLA.getId().equals(ambiente));
        model.addAttribute("idPersona", request.getParameter("idPersona"));
        model.addAttribute("rfc", request.getParameter("rfc"));
        model.addAttribute("nssCifrado", nssCifrado);

        return view;
    }

    @RequestMapping(value = "/correoElectronico/{idPago}/{cveIdSeguroIvro}", method = {
        RequestMethod.POST, RequestMethod.GET})
    public String enviarCorreo(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response,
            @PathVariable Long idPago, @PathVariable Long cveIdSeguroIvro) {

        String view = SeguroIvroUtil.VIEW_DETALLE_SEGURO;

        //Invocar servicio de correo
        SeguroIvro seguro = seguroServices.getDetalleSeguro(cveIdSeguroIvro);
        Pago pagoLC = getPagoLC(idPago);

        if (seguro != null && pagoLC != null) {
            List<SeguroIvro> seguroLista = new ArrayList<SeguroIvro>();
            seguroLista.add(seguro);
            log.info("seguro no es nulo" + seguro.toString() + " y la linea de captura no esta  vacia " + pagoLC.getLineaCaptura());
            log.info("Modalidad: " + seguro.getModalidad().getNumModalidad());
            String correo = "";
            List<String> correos = new ArrayList<String>();
            if (seguro.getModalidad().getNumModalidad().equals(ModalidadEnum.TREINTAYTRES.getNumModalidad())) {
                correo = (String) session.getAttribute("correoseg33");
            } else {
                correo = (String) session.getAttribute("correoIVRO");
                correos = (List<String>) session.getAttribute("listaCorreosIVRO");
            }
            log.info(correo);
            List<Pago> pagosLC = new ArrayList<Pago>();
            pagosLC.add(pagoLC);
            if (seguro.getTitular() != null
                    && seguro.getTitular().getCorreoElectronico() == null) {
                if (correo != null) {
                    correos.add(correo);
                    enviarCorreoAsegurado(seguroLista, pagosLC, false, correos);
                } else if (!correos.isEmpty()) {
                    enviarCorreoAsegurado(seguroLista, pagosLC, false, correos);
                }
            } else {
                correos.add(seguro.getTitular().getCorreoElectronico().getCorreo());
                enviarCorreoAsegurado(seguroLista, pagosLC, false, correos);

            }

        } else {
            log.info("No se obtuvo seguro ni linea de captura");
        }

        return view;

    }

    private void enviarCorreoAsegurado(List<SeguroIvro> seguroLista, List<Pago> lineasCaptura, boolean esEnvioAutomatico, List<String> correos) {
        log.info("enviar correo AUTOMATICO SSF entra a enviarCorreoAsegurado ########## ");
        if (correos != null && !correos.isEmpty()) {
            Map<String, byte[]> adjunto = new HashMap<String, byte[]>();
            SeguroIvro seguroTitular = null;
            /**
             * Adjuntando documentos
             */
            String extension = ".pdf";
            if (!esEnvioAutomatico) {
                if (seguroLista.get(0).getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
                    DocumentoSeguro documento = comprobanteSeguro.generaComprobante(seguroLista.get(0), OrigenSolicitudEnum.INTERNET.getId());
                    adjunto.put(documento.getNombreArchivo(), documento.getArchivo());
                }
                for (Pago lineaCaptura : lineasCaptura) {
                    adjunto.put(lineaCaptura.getLineaCaptura() + extension, lineaCaptura.getPdf());
                    log.info("********************************ENVIO SELECCIONADO***************************************************");
                    log.info("Linea de Captura a enviar: " + lineaCaptura.getLineaCaptura());
                    log.info("*****************************************************************************************************");
                }
                seguroTitular = seguroLista.get(0);
            } else {
                /**
                 * Obtencion del comprobante
                 */
                int numDocumento = 0;
                for (SeguroIvro seguro : seguroLista) {

                    if (seguro.getTitular().getNss().equals(seguro.getTramite().getBeneficiarios()[0].getNss())) {
                        seguroTitular = seguro;
                    }
                    DocumentoSeguro documento = comprobanteSeguro.generaComprobante(seguro, OrigenSolicitudEnum.INTERNET.getId());
                    /**
                     * Adjuntando Comprobante
                     */
                    if (numDocumento == 0) {
                        adjunto.put(documento.getNombreArchivo(), documento.getArchivo());
                        log.info("Comprobante a enviar:      " + documento.getNombreArchivo());
                    } else {
                        adjunto.put(documento.getNombreArchivo().replace(".pdf", numDocumento + ".pdf"), documento.getArchivo());
                    }
                    /**
                     * Validando Lineas de captura a enviar
                     */
                    numDocumento++;
                }
                for (Pago lineaCaptura : lineasCaptura) {
                    boolean antesFechaLimite = esAntesDeFechaLimiteDePago(lineaCaptura.getFechaLimitePago());
                    log.info("Linea de Captura:              " + lineaCaptura.getLineaCaptura());
                    log.info("Antes de Fecha limite de pago: " + antesFechaLimite);
                    log.info("Estado del Pago:			   " + lineaCaptura.getEstadoPago().getDescripcion());
                    log.info("Imprimible:	         		   " + lineaCaptura.getImprimible());
                    imprimeLineaCaptura(antesFechaLimite, lineaCaptura, extension, adjunto);
                }
            }
            try {
                if (seguroTitular != null) {
                    log.info("llamar a  seguroServices.enviaCorreo AUTOMATICO SSF########## ");
                    seguroServices.enviaCorreo(seguroTitular, correos, TipoOperacionNotificacionIVROEnum.FIN_TRAMITE.getCodigo(), adjunto);
                }
            } catch (Exception e) {
                log.error(e);
            }

        } else {
            log.error("No existen destinatarios");
        }
    }

    private void imprimeLineaCaptura(boolean antesFechaLimite, Pago lineaCaptura, String extension, Map<String, byte[]> adjunto) {
        if (antesFechaLimite && (lineaCaptura.getImprimible() && (EstadoPagoEnum.PAGADO.getId() != lineaCaptura.getEstadoPago().getIdEstadoPago()
                && EstadoPagoEnum.VENCIDO.getId() != lineaCaptura.getEstadoPago().getIdEstadoPago()))) {
            adjunto.put(lineaCaptura.getLineaCaptura() + extension, lineaCaptura.getPdf());
            log.info("********************************ENVIO AUTOMATICO***************************************************");
            log.info("Linea de Captura a enviar: " + lineaCaptura.getLineaCaptura());
            log.info("***************************************************************************************************");
        }
    }

    @RequestMapping(value = "/lc/pago/{idPago}", method = {
        RequestMethod.POST, RequestMethod.GET})
    public String printLineaCaptura(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response, @PathVariable Long idPago) {

            Fisica persona;
        try {
            log.info("Se consulta la persona por el idPago: "+idPago);
            persona = seguroIvroServiceRemote.findPersonabyPago(idPago);

            if(persona!=null){

                log.info("persona not null: "+persona.getIdPersona());
                RespuestaRifSat respuestaRifSat = beneficioRissServiceBusinessRemote.validaEstadoBeneficio(persona,true);
                log.info("respuestaRifSat claveError: "+respuestaRifSat.getClaveError());

                SegurosIvro segurosIvro = seguroServices.obtenSeguroIndividual(persona.getIdPersona());

                SeguroIvro[] seguros = segurosIvro!=null && segurosIvro.getSeguroIvro() != null
                        ? segurosIvro.getSeguroIvro() : new SeguroIvro[]{};

                
                

                if(seguros!=null && seguros.length!=0) {
                                	
                    this.log.warn(" -- Es un IVRO activo");

                    if (respuestaRifSat.getClaveError() == 1) {

                        this.log.warn(" -- Perdio el beneficio dentro del periodo del seguro activo, se procede a cancelar::::::");

                        try {

                            MotivoCancelacionBeneficioEnum motivo = validaErrorEnum(respuestaRifSat);
                            seguroServices.cancelaBeneficioRiss(seguros[0], motivo);

                            Persona personaCancelar = new Persona();
                            personaCancelar.setIdPersona(persona.getIdPersona());
                            personaCancelar.setRfc(persona.getRfc());

                            seguroIvroServiceRemote.cancelaSeguroRiss(personaCancelar);
                            log.info("Se cancelo el beneficio y el seguro asociado a la persona");

                        } catch (IvroException e) {
                            log.error("Se presento error en WS ivro: " + e.getMessage());
                        } catch (IVROServiceException e) {
                            log.error("Se presento error en WS ivro: " + e.getMessage());
                        } catch (Exception e) {
                            log.error("Se presento error en WS ivro: " + e.getMessage());
                        }

                        response.reset();
                        response.setHeader(EXPIRES, "0");
                        response.setHeader(CACHE_CONTROL, "no-cache");
                        response.setContentType("text/html; charset=UTF-8");

                        PrintWriter out = response.getWriter();
                        out.println("<link type=\"text/css\" href=\"/delta/resources/estilos/bootstrap/bootstrap.min.css\" rel=\"stylesheet\" />");
                        out.println("<html><body><div style=\"text-align: center;\" class=\"alert alert-danger\"><h4>"
                                + "Su beneficio ha sido cancelado debido a: " + respuestaRifSat.getMotivoDeRechazo()
                                + " Por lo que no se puede generar la l&iacute;nea de captura, favor de acudir a su subdelegaci&oacute;n"
                                + " o inicie un nuevo tr&aacute;mite de compra.</h4></div> </body></html>");
                        response.setStatus(HttpServletResponse.SC_OK);

                        log.info("Se regresa el error a la pantalla");
                        return null;

                    } else {
                        log.info("No tiene beneficio activo, se revisa el estado del seguro");
                        try {
                            if (seguros[0] != null && seguros[0].getEstadoSeguro().getIdEstadoSeguro().equals(ESTADO_SEGURO_CANCELADO_RISS)) {
                                log.info("El seguro esta cancelado previamente por una cancelacion del beneficio RISS");
                                response.reset();
                                response.setHeader(EXPIRES, "0");
                                response.setHeader(CACHE_CONTROL, "no-cache");
                                response.setContentType("text/html; charset=UTF-8");

                                PrintWriter out = response.getWriter();
                                out.println("<link type=\"text/css\" href=\"/delta/resources/estilos/bootstrap/bootstrap.min.css\" rel=\"stylesheet\" />");
                                out.println("<html><body><div style=\"text-align: center;\" class=\"alert alert-danger\"><h4>"
                                        + "Su beneficio ah sido cancelado, por lo que no se puede generar la l&iacute;nea de captura, " +
                                        "favor de acudir a su subdelegaci&oacute;n o inicie un nuevo tr&aacute;mite de compra.</h4></div> </body></html>");
                                response.setStatus(HttpServletResponse.SC_OK);

                                log.info("Seguro ya esta dado de baja por RISS");
                                return null;
                            }

                        } catch (Exception e) {
                            log.error("Se presento error en WS ivro: " + e.getMessage());
                        }
                    }

                }else{
                    this.log.warn(" -- Es seguro tipo CVRO -- No se debe evaluar RISS");
                }

                
                //nueva validacion para RO
                try {
        			DatosCalculoCuota calculo = callWebService(webServiceValidaPersonaContVoluntariaRenova, persona, DatosCalculoCuota.class);
        			
        			if(calculo.getErrorFormGeneral().contains("debido a que te encuentras vigente")) {
        			
        				this.log.error(" --- MENSAJE DEL WEBSERVICE: "+calculo.getErrorFormGeneral());
        				 log.info("El seguro esta cancelado previamente porque se detecto que esta inscrito en el regimen obligatorio.");
                         response.reset();
                         response.setHeader(EXPIRES, "0");
                         response.setHeader(CACHE_CONTROL, "no-cache");
                         response.setContentType("text/html; charset=UTF-8");

                         PrintWriter out = response.getWriter();
                         out.println("<link type=\"text/css\" href=\"/delta/resources/estilos/bootstrap/bootstrap.min.css\" rel=\"stylesheet\" />");
                         out.println("<html><body><div style=\"text-align: center;\" class=\"alert alert-danger\"><h4>"
                                 + "No se puede descargar su l�nea de captura debido a que Usted se encuentra inscrito en el R�gimen Obligatorio, " +
                                 "favor de acudir a su subdelegaci&oacute;n.</h4></div> </body></html>");
                         response.setStatus(HttpServletResponse.SC_OK);

                         log.info("Ya no se puede descargar la LC porque se encuentra en RO");
                         return null;
        				
        			}
        		} catch (Exception e) {
        			log.error("Ocurrio un errror al validar el regimen obligatorio: ", e);
        			e.printStackTrace();
        		}
                
                
            }else{
                log.info("No se recupero Persona");
            }

        } catch (IvroException e) {
            log.error("Ocurrio un error: ",e);
            e.printStackTrace();
        } catch (Exception e) {
            log.error("Ocurrio un error: ",e);
            e.printStackTrace();
        }
        
        log.info("Se continua con el flujo normal de LC");
        Pago pagoLC = getPagoLC(idPago);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            if (pagoLC.getPdf() != null) {
                baos.write(pagoLC.getPdf());
                response.reset();
                response.setHeader(EXPIRES, "0");
                response.setHeader(CACHE_CONTROL, CONSTANT_HEADER);
                response.setHeader(PRAGMA, PUBLIC);
                response.setContentType(CONTENT_TYPE);
                response.addHeader(CONTENT_DISPOSITION, ATTACHMENT
                        + pagoLC.getLineaCaptura() + ".pdf");
                response.setContentLength(baos.toByteArray().length);
                response.getOutputStream().write(baos.toByteArray(), 0, baos.toByteArray().length);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } else if (pagoLC.getPdf() == null) {

                this.log.warn(" -- PDF no esta contenido en la respuesta del WS::::::");
                response.reset();
                response.setHeader(EXPIRES, "0");
                response.setHeader(CACHE_CONTROL, "no-cache");
                response.setContentType("text/html; charset=UTF-8");

                PrintWriter out = response.getWriter();
                out.println("<link type=\"text/css\" href=\"/delta/resources/estilos/bootstrap/bootstrap.min.css\" rel=\"stylesheet\" />");
                out.println("<html><body><div style=\"text-align: center;\" class=\"alert alert-danger\"><h4>"
                        + pagoLC.getLineaCaptura()
                        + "</h4></div></body></html>");

                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            log.error(e);
        }

        return null;
    }

    private MotivoCancelacionBeneficioEnum validaErrorEnum(RespuestaRifSat respuestaRifSat){

        String motivoCancelacion = respuestaRifSat.getMotivoDeRechazo();
        MotivoCancelacionBeneficioEnum motivoRespuesta = null;

        if(motivoCancelacion.contains("SAT")) {
            motivoRespuesta = MotivoCancelacionBeneficioEnum.POR_SAT;
        }else if(motivoCancelacion.contains("INFONAVIT")) {
            motivoRespuesta = MotivoCancelacionBeneficioEnum.POR_INFONAVIT;
        }else {
            log.info("Baja sin motivo coincidente");
        }

        return motivoRespuesta;
    }

    @RequestMapping(value = "/reporteComp/{idSeguro}", method = {
        RequestMethod.POST})
    public String printComprobante(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response, @PathVariable 
            String idSeguro) {
    	
    	System.out.println("Desde imprimir comprobante ");
    	System.out.println(idSeguro);
    	
    	Long idSeguroDecoded = 0l;
    	
    	try {
    		idSeguroDecoded = Long.valueOf( CriptoUtilities.getIdFromUrl(idSeguro));    		
    	}catch(Exception e) {
    		this.log.error("error al descifrar en printcomprobante "+e);
    	}
    	    	
    	this.log.info("id para consultar: "+idSeguroDecoded);
    	

        SeguroIvro seguro = new SeguroIvro();
        //seguro.setCveIdSeguroIvro(idSeguro);
        seguro.setCveIdSeguroIvro(idSeguroDecoded);
        DocumentoSeguro documento = comprobanteSeguro.generaComprobante(seguro, OrigenSolicitudEnum.INTERNET.getId());

        try {
            response.reset();
            response.setHeader(EXPIRES, "0");
            response.setHeader(CACHE_CONTROL, CONSTANT_HEADER);
            response.setHeader(PRAGMA, PUBLIC);
            response.setContentType(CONTENT_TYPE);
            response.addHeader(CONTENT_DISPOSITION, ATTACHMENT + documento.getNombreArchivo());
            response.setContentLength(documento.getArchivo().length);
            response.getOutputStream().write(documento.getArchivo(), 0, documento.getArchivo().length);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error(e);
        }

        return null;
    }

//    @RequestMapping(value = "/listaPagos/{idSeguro}", method = {
//            RequestMethod.POST, RequestMethod.GET})
//    public String listaPagos(Model model, HttpSession session,
//                                   HttpServletRequest request, HttpServletResponse response, @PathVariable Long idSeguro) {
//
////        SeguroIvro seguro = new SeguroIvro();
////        seguro.setCveIdSeguroIvro(idSeguro);
//
//        //DocumentoSeguro documento = comprobanteSeguro.generaComprobante(seguro, OrigenSolicitudEnum.INTERNET.getId());
//        String view = "wizardSeguroCVROPagos";
//        try {
//
//            log.info("Entrando al servicio de pagos cvro");
//            List<PagoCVRO> pagos = seguroServices.getListaPagosPersona(idSeguro);
//
//            if(pagos!=null&& !pagos.isEmpty()){
//                log.info("pagos: "+pagos.size());
//            }else{
//                log.info("pagos null");
//            }
//
//            model.addAttribute("pagos", pagos);
//            log.info("servicio de pagos cvro: fin");
//
//        } catch (Exception e) {
//            log.error(e);
//        }
//
//        return view;
//    }

    @RequestMapping(value = "/cuestionario/{idSeguro}", method = {
        RequestMethod.POST, RequestMethod.GET})
    public String printCuestionario(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response, @PathVariable Long idSeguro) {

        SeguroIvro seguro = new SeguroIvro();
        seguro.setCveIdSeguroIvro(idSeguro);
        SegurosIvro seguros = new SegurosIvro();
        seguros.setOrigen(SeguroIvroUtil.getAmbiente(request));
        seguros.setSeguroIvro(new SeguroIvro[]{seguro});
        DocumentoSeguro documento = comprobanteSeguro.generaCuestionarios(seguros);

        try {

            response.reset();
            response.setHeader(EXPIRES, "0");
            response.setHeader(CACHE_CONTROL, CONSTANT_HEADER);
            response.setHeader(PRAGMA, PUBLIC);
            response.setContentType(CONTENT_TYPE);
            response.addHeader(CONTENT_DISPOSITION, ATTACHMENT + documento.getNombreArchivo());
            response.setContentLength(documento.getArchivo().length);
            response.getOutputStream().write(documento.getArchivo(), 0, documento.getArchivo().length);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error(e);
        }

        return null;
    }

    /**
     * Obtiene un pago con la generacion de su linea de captura
     *
     * @param idPago el identificador del pago
     * @return el pago generado
     */
    private Pago getPagoLC(Long idPago) {
        Pago pago = new Pago();
        pago.setIdPago(idPago);
        Pagos pagos = new Pagos();
        pagos.setPago(new Pago[]{pago});
        Pago pagoLC = new Pago();
        String messageErrorPay = "";
        String messageErrorServer = "";
        Pago pagoDB = null;
		
		  try {
		  
		  //Se obtiene pago de BD 
	      pagoDB = compraServiceRemote.findPagoById(idPago);
		  if (pagoDB != null && pagoDB.getPdf() != null && pagoDB.getLineaCaptura() != null) {
			  pago.setPdf(pagoDB.getPdf());
			  pago.setLineaCaptura(pagoDB.getLineaCaptura());
			  log.info("************************** PDF obtenido de la BD");
			  return pago;
		  }
		  log.info("Objeto Pago obtenido de BD: "+pagoDB.getLineaCaptura()); 
		  } 
		  catch (SUAException e){
		  log.error("Error al obtener el pago", e);
		  }
		    
        try {
            String pagosXml = JaxbUtil.marshaller(pagos);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            log.info("Parametros WS Lineas Captura: "+pagosXml);
            
            //bifurcacion 
            if(pagoDB.getLineaCaptura() != null && pagoDB.getPdf()!=null) {
            	
            	log.error("9999 Se recupera la linea de captura y el pdf de BD: "+pagoDB.getLineaCaptura());
            	log.error("9999 se regresa el pago de BD");
            	pagoLC = pagoDB;
            	
            	pagoLC.setLineaCaptura(pagoDB.getLineaCaptura());
            	pagoLC.setPdf(pagoDB.getPdf());
            	
            }else {
            
            	log.error("9999 no se tiene LC o PDF, se consulta el servicio webServiceLineasCaptura para generar un nuevo PDF y LC");
            	
            	webServiceLineasCaptura.sendSourceAndReceiveToResult(new StreamSource(new StringReader(pagosXml)),
                        result);
            	

                String seguroRespuestaXml = writer.toString();
                log.info("Respuesta ws " + seguroRespuestaXml);
                messageErrorServer = seguroRespuestaXml;
                Pagos pagosLC = JaxbUtil.unmarshaller(seguroRespuestaXml, Pagos.class);
                messageErrorPay = pagosLC.getErrorFormGeneral();
                pagoLC = pagosLC.getPago()[0];
                
                //Actualiza PDF en BD
                
                log.error("9999 se recupera nuevo LC y PDF del servicio: "+pagoLC.getLineaCaptura());
                
                pagoDB.setPdf(pagoLC.getPdf());
                pagoDB.setLineaCaptura(pagoLC.getLineaCaptura());
                
                //if(pagoDB.getLineaCaptura() == null) {
                  //  log.info("************************** Se actualiza PDF en BD");
                //}
                compraServiceRemote.actualizaPagoLC(pagoDB);
            }
            	
            
            log.info("Parseo Objeto " + ReflectionToStringBuilder.toString(pagoLC));           
        } catch (Exception e) {
            log.error("Error no controlado ", e);
            pagoLC.setPdf(null);
            if (messageErrorPay.isEmpty()) {
                messageErrorPay = messageErrorServer;
            }
            pagoLC.setLineaCaptura(messageErrorPay);
            return pagoLC;
        }
        return pagoLC;
    }

    @RequestMapping(value = "/sendEmailComprobante/{idSeguro}/{correo}/", method = {
        RequestMethod.POST, RequestMethod.GET})
    public void sendEmailComprobante(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response, @PathVariable Long idSeguro, @PathVariable String correo) {
        Boolean enviarCorreo = (session.getAttribute(ENVIAR_CORREO) != null) ? ((Boolean) session.getAttribute(ENVIAR_CORREO)) : Boolean.FALSE;
        log.info("Enviar correo Continuacion voluntaria: " + enviarCorreo + " para el seguro con id-> " + idSeguro);
        if (enviarCorreo) {
            session.removeAttribute(ENVIAR_CORREO);
            /**
             * Obtencion del comprobante
             */
            SeguroIvro seguro = seguroServices.getDetalleSeguro(idSeguro);
            DocumentoSeguro documento = comprobanteSeguro.generaComprobante(
                    seguro, OrigenSolicitudEnum.INTERNET.getId());
            Map<String, byte[]> adjunto = new HashMap<String, byte[]>();
            adjunto.put(documento.getNombreArchivo(), documento.getArchivo());
            log.info("Comprobante a enviar: " + documento.getNombreArchivo());

            Persona persona = seguro.getTitular();
            List<String> correos = new ArrayList<String>();
            String extension = ".pdf";
            for (Pago pago : seguro.getCompra().getPagos()) {
                Pago pagoAux = getPagoLC(pago.getIdPago());
                pagoAux.setFechaLimitePago(pago.getFechaLimitePago());
                pagoAux.setEstadoPago(pago.getEstadoPago());
                pagoAux.setImprimible(pago.getImprimible());
                adjunto.put(pagoAux.getLineaCaptura() + extension, pagoAux.getPdf());
            }

            if (correo != null) {
                correos.add(correo);
                log.info("Enviando a: " + correo);

                seguroServices.enviaCorreo(seguro, correos, TipoOperacionNotificacionIVROEnum.FIN_TRAMITE.getCodigo(), adjunto);
            } else {
                log.info("El usuario no tiene correo: " + persona.getIdPersona());
            }
        }
    }

    /**
     * Para enviar el comprobante y la linea de captura de forma automatica al
     * cargar el detalle del Seguro Renovado.
     *
     * @param model
     * @param session
     * @param request
     * @param response
     * @param cveIdSeguroIvro Id del Seguro del cual se enviara comprobante de
     * Seguro y Linea de captura
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/finalizarSeguroDetalle/enviarCorreoElectronico/{cveIdSeguroIvro}", method = {
        RequestMethod.POST, RequestMethod.GET})
    public String enviarComprobanteLineaCapturaCorreo(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response, @PathVariable Long cveIdSeguroIvro) {

        Boolean enviarCorreoIVRO = (session.getAttribute(CORREO_RENOVACION_IVRO) != null) ? ((Boolean) session.getAttribute(CORREO_RENOVACION_IVRO)) : Boolean.FALSE;
        Boolean enviarCorreoCompraIVRO = (session.getAttribute(CORREO_COMPRA_IVRO) != null) ? ((Boolean) session.getAttribute(CORREO_COMPRA_IVRO)) : Boolean.FALSE;
        Boolean enviarCorreoSSF = (session.getAttribute(CORREO_RENOVACION_SSF) != null) ? ((Boolean) session.getAttribute(CORREO_RENOVACION_SSF)) : Boolean.FALSE;

        Boolean enviarCorreoCompraSSF = (session.getAttribute(CORREO_COMPRA_SSF) != null) ?
                ((Boolean) session.getAttribute(CORREO_COMPRA_SSF)) : Boolean.FALSE;

        log.info("enviar correo AUTOMATICO SSF Compra ########## " + enviarCorreoCompraSSF);
        log.info("enviar correo AUTOMATICO SSF Renovacion########## " + enviarCorreoSSF);
        String correo = "";
        List<String> correos = new ArrayList<String>();
        List<Long> cveIdSeguroLista = new ArrayList<Long>();
        if (enviarCorreoIVRO||enviarCorreoCompraIVRO) {
            cveIdSeguroLista.add(cveIdSeguroIvro);
            /*Correo cuando entra por CURP*/
            correo = (String) session.getAttribute("correoIVRO");
            if (correo == null) {
                /*Correos cuando entra por FIEL*/
                correos = (List<String>) session.getAttribute("listaCorreosIVRO");
            } else {
                if (!correo.isEmpty()) {
                    correos.add(correo);
                }
            }
        } else if (enviarCorreoSSF||enviarCorreoCompraSSF) {
            correo = (String) session.getAttribute("correoseg33");
            if (!correo.isEmpty()) {
                correos.add(correo);
            } else {
                log.info("No se tienen correos");
            }
            SegurosIvro segurosFamiliares = (SegurosIvro) session.getAttribute("segurosFamiliares");

            for (SeguroIvro seguro : segurosFamiliares.getSeguroIvro()) {
                if (seguro.getEstadoSeguro().getIdEstadoSeguro() == 1) {
                    cveIdSeguroLista.add(seguro.getCveIdSeguroIvro());
                }
            }

        }
        enviarCorreo(session, cveIdSeguroLista, correos);
        log.info(" enviarCorreoCompraIVRO:  " + enviarCorreoCompraIVRO + " enviarCorreoCompraSSF:  " + enviarCorreoCompraSSF);
        log.info(" enviarCorreoRenovacionIVRO:  " + enviarCorreoIVRO + " enviarCorreoSSF:  " + enviarCorreoSSF);
        log.info("Correos:  " + correos);
        return null;

    }

    private void enviarCorreo(HttpSession session, List<Long> cveIdSeguroIvroLista, List<String> correos) {
        log.info("enviar correo AUTOMATICO SSF entra a enviarCorreo ########## " + correos);
        session.removeAttribute(CORREO_RENOVACION_IVRO);
        session.removeAttribute(CORREO_RENOVACION_SSF);
        session.removeAttribute(CORREO_COMPRA_IVRO);
        session.removeAttribute(CORREO_COMPRA_SSF);
        //Se consulta el seguro para los datos del comprobante y el dato del pago
        List<SeguroIvro> seguroLista = new ArrayList<SeguroIvro>();



        for (Long cveIdSeguroIvro : cveIdSeguroIvroLista) {
            seguroLista.add(seguroServices.getDetalleSeguro(cveIdSeguroIvro));
        }

        List<Pago> pagosConLC = new ArrayList<Pago>();
        if (!seguroLista.isEmpty()) {
            log.info("Se obtienen los pagos con Linea de captura");
            /**
             * Obtiene las lineas de captura
             */
            for (SeguroIvro seguro : seguroLista) {
                for (Pago pago : seguro.getCompra().getPagos()) {
                    Pago pagoAux = getPagoLC(pago.getIdPago());
                    pagoAux.setFechaLimitePago(pago.getFechaLimitePago());
                    pagoAux.setEstadoPago(pago.getEstadoPago());
                    pagoAux.setImprimible(pago.getImprimible());
                    pagosConLC.add(pagoAux);
                }
            }
            enviarCorreoAsegurado(seguroLista, pagosConLC, true, correos);
        }

    }

    private boolean esAntesDeFechaLimiteDePago(Date fechaLimite) {

        Calendar hoy = Calendar.getInstance();
        Calendar limite = Calendar.getInstance();
        limite.setTime(fechaLimite);
        log.info("\nFecha limite original" + limite);
        limite.add(Calendar.HOUR_OF_DAY, 24);
        log.info("Fecha limite mas 1 dia" + limite);

        return hoy.before(limite);
    }

    private Boolean puedeImprimir(Pago[] pagos) {
        Boolean imprimir = Boolean.FALSE;
        Date hoy = new Date();
        for (Pago pago : pagos) {
            imprimir = ((pago.getFechaLimitePago() != null && DateUtils.sumaDias(pago.getFechaLimitePago(), 1).after(hoy))
                    && ((pago.getImprimible() != null && pago.getImprimible())
                    || (pago.getEstadoPago().getIdEstadoPago() != EstadoPagoEnum.VENCIDO.getId()
                    && pago.getEstadoPago().getIdEstadoPago() != EstadoPagoEnum.PAGADO.getId())));
            if (imprimir) {
                break;
            }
        }
        return imprimir;
    }
    
    //Metodo para mandar a obtener las LC y PDF de SIPARE
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/finalizarSeguroDetalle/obtenerLcSipare/{cveIdSeguroIvro}", method = {
        RequestMethod.POST, RequestMethod.GET})
    public String obtenerLcSipare(Model model, HttpSession session,
            HttpServletRequest request, HttpServletResponse response, @PathVariable Long cveIdSeguroIvro) {
    
    	log.info("************************** Entrando al metodo obtenerLcSipare, cveIdSeguroIvro: " + cveIdSeguroIvro);
    	
    	SeguroIvro seguro = seguroServices.getDetalleSeguro(cveIdSeguroIvro);
    	
    	if (seguro != null && seguro.getCompra() != null && seguro.getCompra().getPagos() != null) {
    	
    		for (Pago pago : seguro.getCompra().getPagos()) {
    			if (pago.getLineaCaptura() == null || pago.getLineaCaptura().isEmpty()) {
    				Pago pagoLc = getPagoLC(pago.getIdPago());
    				log.info("************************** LC guardada en BD: " + pagoLc.getLineaCaptura());
    			}
    		}
    	}
    	return null;
    }
    
    
    protected <T> T callWebService(WebServiceTemplate webService, Object source, Class<T> resultClass, Class<?>[] classes) throws Exception {
		T result = null;
		try {
			StringWriter writer = new StringWriter();
			StreamResult streamResult = new StreamResult(writer);
			log.info("********** CADENA DE ENTRADA DEL WEB-SERVICE: " + JaxbUtil.marshaller(source, classes));
			webService.sendSourceAndReceiveToResult(new StreamSource(new StringReader(JaxbUtil.marshaller(source, classes))), streamResult);
			log.info("********** CADENA DE SALIDA DEL WEB-SERVICE: " + writer.toString());
			result = resultClass.newInstance();
			result = JaxbUtil.unmarshaller(writer.toString(), resultClass);
		} catch (InstantiationException e) {
			throw new Exception("No se ha podido crear una instancia de la clase resultado.", e);
		} catch (IllegalAccessException e) {
			throw new Exception("No se ha podido crear una instancia de la clase resultado.", e);
		}
		return result;
	}
	
	protected <T> T callWebService(WebServiceTemplate webService, Object source, Class<T> resultClass) throws Exception {
		return callWebService(webService, source, resultClass, new Class<?>[] {source.getClass()});
	}
    
}    
   