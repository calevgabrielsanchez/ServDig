/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCamino;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCarretera;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAdministracion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAsentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDerechoTransito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoMargen;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoTerminoGeneral;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.enums.*;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.comun.Modalidad;
import mx.gob.imss.digital.modelo.cuestionario.PersonaCuestionario;
import mx.gob.imss.digital.modelo.domicilio.Camino;
import mx.gob.imss.digital.modelo.domicilio.Carretera;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.medio.contacto.MedioContacto;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.solicitud.EstadoSolicitud;
import mx.gob.imss.digital.modelo.solicitud.FirmaElectronica;
import mx.gob.imss.digital.modelo.solicitud.OrigenSolicitud;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;
import mx.gob.imss.digital.modelo.solicitud.TipoSolicitud;
import mx.gob.imss.digital.modelo.tramite.EstadoTramite;
import mx.gob.imss.digital.modelo.tramite.TipoTramite;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NOVUTECK1
 *
 */
public abstract class SeguroIvroUtil{
	
	/**
     * logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SeguroIvroUtil.class);
    
    /**
     * Constante que indica que la aplicacion corre en el ambiente de internet
     */
    public static final String KEY_ORIGEN_ID_CONTEXT = "ORIGEN_APP_ID";

    /** The Constant VIEW_DETALLE_SEGURO. */
    public static final String VIEW_DETALLE_SEGURO = "wizardDetalleSeguroContenido";
    
    /* Constantes para mostrar que falta informacion dependiendo si es de municipio o entidad federativa*/
    private static final  String MESSAGE_MUNICIPIO = "Es necesario ingresar la informaci\u00F3n del Municipio";
    private static final String MESSAGE_ENTIDAD_FEDERATIVA = "Es necesario ingresar la informaci\u00F3n de la Entidad Federativa";

    private static final Long MODALIDAD_CERO = 0L;

    //Constructor
	private SeguroIvroUtil(){}
       
    
    /**
     * Indica si dado los seguros qu tiene una persona puede comprar
     * @param seguros los seugros asociados a una persona
     * @return true si la persona puede comprar seguros
     */
    public static final boolean puedeComprarSeguro(SeguroIvro[] seguros){
        boolean comprar = false;
        // Si no hay seguros activamos la accion de compra
        if (seguros.length == 0) {
            comprar = true;
        } else {
            SeguroIvro seguro = seguros[0];
            if ((seguro.getEstadoSeguro().getIdEstadoSeguro() == EstadoSeguroIvroEnum.CANCELADO_RISS.getId()
                    || seguro.getEstadoSeguro().getIdEstadoSeguro() == EstadoSeguroIvroEnum.VENCIDO.getId()
                    || seguro.getEstadoSeguro().getIdEstadoSeguro() == EstadoSeguroIvroEnum.CONCLUIDO.getId())) {
                comprar = true;
            }
        } 
        return comprar;
    }

    /**
     * Indica si dado los seguros que tiene una persona son Renovacion
     * @param seguros los seguros asociados a una persona
     * @return true si la persona puede comprar seguros
     */
    public static final boolean enRenovacion(SeguroIvro[] seguros){
        boolean esRenovacion = false;
        // Si no hay seguros activamos la accion de compra

        SeguroIvro seguro = seguros[0];
        if ((seguro.getEnRenovacion() != null && seguro.getEnRenovacion())){
            esRenovacion = true;
        }

        return esRenovacion;
    }
    
    /**
     * Indica si los seguros asociados auna persona pueden ser renovados
     * @param seguros los seguros asociados
     * @return ture si esta en periodo de renovacion
     */
    public static final boolean puedeRenovarSeguro(SeguroIvro[] seguros) {
        boolean renovar = false;

        if (seguros.length > 0) {
            SeguroIvro seguro = seguros[0];
            Calendar fechaIniRenovaion = Calendar.getInstance();
    		Calendar fechaFinRenovaion = Calendar.getInstance();
    		Calendar hoy = Calendar.getInstance();
    		hoy.setTime(new Date());
    		
    		fechaFinRenovaion.setTime(seguro.getFechaFin());
    		fechaFinRenovaion.set(Calendar.DAY_OF_MONTH, 1);
    		fechaFinRenovaion.add(Calendar.MONTH, 1);
    		fechaFinRenovaion.set(Calendar.DATE, +30);
    		
                //El usuario tiene 30 dias previos a su fin de vigencia para renovar
                //por ejemplo si su seguro tiene fecha de fin de vigencia del 31 de diciembre
                //la fecha en la que puede iniciar su renovacion es el 2 de diciembre
                //el 1 de diciembre no puede renovar aun
    		fechaIniRenovaion.setTime(seguro.getFechaFin());
    		fechaIniRenovaion.add(Calendar.DAY_OF_YEAR, -29);
		fechaIniRenovaion = DateUtils
				.truncate(fechaIniRenovaion, Calendar.DATE);
    		renovar =(hoy.after(fechaIniRenovaion) && hoy.before(fechaFinRenovaion))
    				|| DateUtils.isSameDay(hoy, fechaIniRenovaion)|| DateUtils.isSameDay(hoy, fechaFinRenovaion);
        if( renovar && 
             ( seguro.getEstadoSeguro().getIdEstadoSeguro() == 2  // Activo
          || seguro.getEstadoSeguro().getIdEstadoSeguro() == 5  ) ) // Concluido   {
          return true;
        }

        return false;
    }
        
    public static final boolean puedeRenovarSeguro(SeguroIvro seguro) {
    	boolean renovar = false;
    	// Si no hay seguros activamos la accion de compra
    	if (seguro != null) {
    		Calendar fechaIniRenovaion = Calendar.getInstance();
    		Calendar fechaFinRenovaion = Calendar.getInstance();
    		Calendar hoy = Calendar.getInstance();
    		hoy.setTime(new Date());

    		fechaFinRenovaion.setTime(seguro.getFechaFin());
    		fechaFinRenovaion.set(Calendar.DAY_OF_MONTH, 1);
    		fechaFinRenovaion.add(Calendar.MONTH, 1);
    		fechaFinRenovaion.set(Calendar.DATE, +30);

    		//El usuario tiene 30 dias previos a su fin de vigencia para renovar
    		//por ejemplo si su seguro tiene fecha de fin de vigencia del 31 de diciembre
    		//la fecha en la que puede iniciar su renovacion es el 2 de diciembre
    		//el 1 de diciembre no puede renovar aun
    		fechaIniRenovaion.setTime(seguro.getFechaFin());
    		fechaIniRenovaion.add(Calendar.DAY_OF_YEAR, -29);
    		fechaIniRenovaion = DateUtils
    		.truncate(fechaIniRenovaion, Calendar.DATE);

    		renovar =(hoy.after(fechaIniRenovaion) && hoy.before(fechaFinRenovaion))
    		|| DateUtils.isSameDay(hoy, fechaIniRenovaion)|| DateUtils.isSameDay(hoy, fechaFinRenovaion);

    		if( renovar && 
    				( seguro.getEstadoSeguro().getIdEstadoSeguro() == 2  // Activo
    						|| seguro.getEstadoSeguro().getIdEstadoSeguro() == 5  ) ) // Concluido   {
    			return true;
    	} 
    	return false;
    }
    
    
    /**
     * Indica si los seguros asociados auna persona pueden ser renovados
     * @param seguros los seguros asociados
     * @return ture si esta en periodo de renovacion
     */
    public static final boolean esRenovacionExtemporanea(SeguroIvro[] seguros) {
        boolean extemporanea= false;
        // Si no hay seguros activamos la accion de compra
        if (seguros.length == 1) {
            SeguroIvro seguro = seguros[0];
    		Calendar hoy = Calendar.getInstance();
    		hoy.setTime(new Date());

    		Calendar fechaFinRenovaion = Calendar.getInstance();
    		Calendar fechaFinSeguro = Calendar.getInstance();
    		fechaFinRenovaion.setTime(seguro.getFechaFin());
    		fechaFinRenovaion.set(Calendar.DAY_OF_MONTH, 1);
    		fechaFinRenovaion.add(Calendar.MONTH, 1);
    		fechaFinRenovaion.set(Calendar.DATE, +31);
            
    		fechaFinSeguro.setTime(seguro.getFechaFin());

            extemporanea = hoy.after(fechaFinSeguro) && hoy.before(fechaFinRenovaion);

            LOGGER.info("Extemporanea:: "+extemporanea);
            LOGGER.info("fechaFinSeguro "+fechaFinSeguro);
            LOGGER.info("fechaFinRenovaion "+fechaFinRenovaion);

            if( extemporanea &&
                    ( seguro.getEstadoSeguro().getIdEstadoSeguro() == 2  // Activo
                            || seguro.getEstadoSeguro().getIdEstadoSeguro() == 5  ) ) // Concluido   {
                return true;
        }
        return false;
    }
    
    /**
     * Obtiene la clase del css a ocupar al pintar el estado del tramite
     * @param seguro
     * @return
     */
    public static final String getClaseEstado(SeguroIvro seguro) {
        String claseEstado = "label label-success label-imss label-success-imss";
        long estado = seguro.getEstadoSeguro().getIdEstadoSeguro();
        if (estado == EstadoSeguroIvroEnum.NUEVO.getId()) {
            claseEstado = "label label-warning label-imss label-warning-imss";
        } else if (estado == EstadoSeguroIvroEnum.CANCELADO_RISS.getId() 
                || estado == EstadoSeguroIvroEnum.VENCIDO.getId() 
                || estado == EstadoSeguroIvroEnum.BAJA_POR_MORA.getId()
                || estado == EstadoSeguroIvroEnum.BAJA_A_SOLICITUD_ASEGURADO.getId()
                || estado == EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO.getId()
                || estado == EstadoSeguroIvroEnum.CANCELADO.getId()) 
        {
            claseEstado = "label label-danger label-imss label-danger-imss";
        }
        return claseEstado;
    }
    
    /**
     * GEnera la solicitud basica e ivro;
     * @param ambiente
     * @param tramite
     * @param usuario
     * @return
     */
    public static final Solicitud armaSolicitudInicial(Long ambiente, TramiteSeguroIvro tramite, 
    		String usuario) {
        Solicitud solicitud = new Solicitud();
        OrigenSolicitud origen = new OrigenSolicitud();
        
        origen.setIdOrigenSolicitud(ambiente);
        solicitud.setOrigenSolicitud(origen);
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getId().intValue());
        solicitud.setEstadoSolicitud(estadoSolicitud);
        solicitud.setFechaRegistro(new Date());
        TipoSolicitud tipoSolicitud = new TipoSolicitud();
        tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.COMPRA_SEGURO.getId());
        solicitud.setTipoSolicitud(tipoSolicitud);
        solicitud.setUsuario(usuario);
        solicitud.setUsuarioResponsable(usuario);
        solicitud.setTramite(new TramiteSeguroIvro[]{tramite});
        
        return solicitud;
        
        
    }
    
    /**
     * GEnera la solicitud basica e ivro;
     * @param ambiente
     * @param tramite
     * @param usuario
     * @return
     */
    public static final Solicitud armaSolicitudInicialDomestico(Long ambiente, TramiteSeguroIvro tramite, 
    		String usuario) {
        Solicitud solicitud = new Solicitud();
        OrigenSolicitud origen = new OrigenSolicitud();
        
        origen.setIdOrigenSolicitud(ambiente);
        solicitud.setOrigenSolicitud(origen);
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getId().intValue());
        solicitud.setEstadoSolicitud(estadoSolicitud);
        solicitud.setFechaRegistro(new Date());
        TipoSolicitud tipoSolicitud = new TipoSolicitud();
        tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.COMPRA_SEGURO.getId());
        solicitud.setTipoSolicitud(tipoSolicitud);
        solicitud.setUsuario(usuario);
        solicitud.setUsuarioResponsable(usuario);
        solicitud.setTramite(new TramiteSeguroIvro[]{tramite});
        
        
        return solicitud;
        
        
    }
    
    /**
     * GEnera un tramite inicial de Ivro 
     * @param cotizacion
     * @param persona
     * @param cuestionario
     * @return
     */
    public static final TramiteSeguroIvro generaTramiteIvroIndividual(Cotizacion cotizacion, Persona persona, 
            PersonaCuestionario cuestionario) {
        TramiteSeguroIvro tramite = new TramiteSeguroIvro();
        tramite.setAplicaCuestionario(cotizacion.getAplicaCuestionario());
        EstadoTramite estadoTramite = new EstadoTramite();
        estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getId());
        tramite.setEstadoTramite(estadoTramite);
        EmpleadoCuota empleado = cotizacion.getDetalle().getEmpleados()[0];
        Fisica beneficiario = new Fisica();
        beneficiario.setIdPersona(persona.getIdPersona());
        beneficiario.setRfc(persona.getRfc());
        beneficiario.setTipoPersona(persona.getTipoPersona());
        beneficiario.setNss(empleado.getNumeroSeguridadSocial());        
        tramite.setBeneficiarios(new Fisica[]{beneficiario});
        tramite.setCotizacion(cotizacion);
        tramite.setFechaInicio(new Date());
        tramite.setFechaPresentacion(new Date());
        tramite.setFechaTramite(new Date());
        Modalidad modalidad = new Modalidad();
        modalidad.setIdModalidad(cotizacion.getDetalle().getModalidad());
        tramite.setModalidad(modalidad);
        tramite.setPersona(beneficiario);
        TipoTramite tipoTramite = new TipoTramite();
        if(cotizacion.getRenovacion() != null && cotizacion.getRenovacion()) {
            tipoTramite.setIdTipoTramite(TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL.getCodigo()); 
            tipoTramite.setDescripcion(TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL.name());
            tramite.setRenovacion(true);
        } else{
            tipoTramite.setIdTipoTramite(TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo());
            tipoTramite.setDescripcion(TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.name());
            tramite.setRenovacion(false);
        }
        tramite.setRenovacion(cotizacion.getRenovacion());
        tramite.setTipoTramite(tipoTramite);
        tramite.setAplicaCuestionario(false);
        if(cuestionario != null) {
            cuestionario.setNssPersona(beneficiario.getNss());
            cuestionario.setIdPersona(beneficiario.getIdPersona());
            tramite.setCuetionarios(new PersonaCuestionario[]{cuestionario});
            tramite.setAplicaCuestionario(true);
        }
        return tramite;
    }
    
    /**
     * Sobrecarga del metodo anterior, agrega un parametro nuevo qe indica si es una compra inicial, genera un tramite inicial de Ivro 
     * @param cotizacion
     * @param persona
     * @param cuestionario
     * @param esCompra
     * @return
     */
    public static final TramiteSeguroIvro generaTramiteIvroIndividual(Cotizacion cotizacion, Persona persona, 
            PersonaCuestionario cuestionario, boolean esCompra) {
        TramiteSeguroIvro tramite = new TramiteSeguroIvro();
        tramite.setAplicaCuestionario(cotizacion.getAplicaCuestionario());
        EstadoTramite estadoTramite = new EstadoTramite();
        estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getId());
        tramite.setEstadoTramite(estadoTramite);
        EmpleadoCuota empleado = cotizacion.getDetalle().getEmpleados()[0];
        Fisica beneficiario = new Fisica();
        beneficiario.setIdPersona(persona.getIdPersona());
        beneficiario.setRfc(persona.getRfc());
        beneficiario.setTipoPersona(persona.getTipoPersona());
        beneficiario.setNss(empleado.getNumeroSeguridadSocial());        
        tramite.setBeneficiarios(new Fisica[]{beneficiario});
        tramite.setCotizacion(cotizacion);
        tramite.setFechaInicio(new Date());
        tramite.setFechaPresentacion(new Date());
        tramite.setFechaTramite(new Date());
        Modalidad modalidad = new Modalidad();
        modalidad.setIdModalidad(cotizacion.getDetalle().getModalidad());
        tramite.setModalidad(modalidad);
        tramite.setPersona(beneficiario);
        TipoTramite tipoTramite = new TipoTramite();
        if(cotizacion.getRenovacion() != null && cotizacion.getRenovacion() && !esCompra) {
            tipoTramite.setIdTipoTramite(TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL.getCodigo()); 
            tipoTramite.setDescripcion(TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL.name());
            tramite.setRenovacion(true);
        } else{
            tipoTramite.setIdTipoTramite(TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo());
            tipoTramite.setDescripcion(TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.name());
            tramite.setRenovacion(false);
        }
        tramite.setTipoTramite(tipoTramite);
        tramite.setAplicaCuestionario(false);
        if(cuestionario != null) {
            cuestionario.setNssPersona(beneficiario.getNss());
            cuestionario.setIdPersona(beneficiario.getIdPersona());
            tramite.setCuetionarios(new PersonaCuestionario[]{cuestionario});
            tramite.setAplicaCuestionario(true);
        }
        return tramite;
    }
    
    /**
     * Obtiene el detalle de un tramite como string agregando el cdata para no tener probleas en el envio de la informacion
     * @param tramite el traite a parcear
     * @return el string con el detalle del tramite
     */
    public static final String getDetalleTramiteString(TramiteSeguroIvro tramite) {
        
        String detalleTramiteXml = "";
        try {            
            detalleTramiteXml = JaxbUtil.marshaller(tramite);
        } catch (Exception e) {
            LOGGER.warn("No se pudo parsear el tramite ivro");
        }
        StringBuilder detallexml = new StringBuilder();
        detallexml.append("<![CDATA[").append(detalleTramiteXml).append("]]>");
        return detallexml.toString();
    }

    /**
     * 
     * @param solicitud
     * @return
     */
    public static final  FirmaElectronica generarCadenaOriginalyDatosFirma(Solicitud solicitud) {
        Locale locMEX = new Locale("es", "MX");
        DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
        Date fecha = Calendar.getInstance().getTime();
        FirmaElectronica datosEntradaFirma = new FirmaElectronica();
        StringBuilder contenidoAFirmar = new StringBuilder();
        // Inicio
        contenidoAFirmar.append("||");
        contenidoAFirmar.append("Invocante:portalimssdigital|");
        // Denominacion del Tramite o servicio
        contenidoAFirmar.append("Tramite:");
        contenidoAFirmar.append(solicitud.getTramite()[0].getTipoTramite().getDescripcion()).append("|");
        // Fecha Electronica
        String strFechaElectronica = dateFormat.format(fecha);
        contenidoAFirmar.append("Fecha:");
        contenidoAFirmar.append(strFechaElectronica).append("|");
        datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
        datosEntradaFirma.setFechaElectronica(fecha);
        // Folio
        contenidoAFirmar.append("Folio:");
        contenidoAFirmar.append(solicitud.getNumSolicitud()).append("|");
        // RFC
        contenidoAFirmar.append("RFC:");
        Persona persona = solicitud.getTramite()[0].getPersona();
        contenidoAFirmar.append(persona.getRfc()).append("|");
        datosEntradaFirma.setRfc(persona.getRfc());
        datosEntradaFirma.setCadenaOriginal(contenidoAFirmar.toString());
        return datosEntradaFirma;
    }
    
    /**
     * Obtiene el ambiente sobre el cual se esta trabajando
     * @param request
     * @return
     */
    public static final Long getAmbiente(HttpServletRequest request) {
		String origenContext = request.getSession()
			.getServletContext().getInitParameter(KEY_ORIGEN_ID_CONTEXT);
		OrigenSolicitudEnum origenSolicitudEnum = OrigenSolicitudEnum.INTERNET;
		if(StringUtils.isNotEmpty(origenContext) && StringUtils.isNotBlank(origenContext)){
			origenSolicitudEnum = OrigenSolicitudEnum.getById(new Long(origenContext));
		}
		return origenSolicitudEnum.getId();
    }
    
    public static final Date getFechaFinalSeguro(SeguroIvro seguro) {
        Date fechaFinal = seguro.getFechaFin();

        if (seguro.getCompra() != null && seguro.getCompra().getPagos()[0].getConBeneficio()) {
            LOGGER.info("Compra con beneficio {}", seguro.getCompra().getPagos()[0].getConBeneficio());
            Date fechaPago = seguro.getCompra().getPagos()[0].getFechaFinPeriodo();
            for (Pago pago : seguro.getCompra().getPagos()) {
                if (pago.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.PAGADO.getId()) {
                    fechaPago = getFechaMayor(fechaPago, pago.getFechaFinPeriodo());
                }
            }
            fechaFinal = fechaPago;
        }
        return fechaFinal;
    }

    public static final Date getFechaMayor(Date fechaIni, Date fechaFin) {
        Date fechaMayor = fechaIni;
        if (fechaMayor == null) {
            fechaMayor = fechaFin;
        }
        return fechaMayor.after(fechaFin) ? fechaMayor : fechaFin;
    }
    
    public static Domicilio convertirDomicilioAImssDigital(mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioModelo) {
        Domicilio domicilioImssDig;
        try {
            if (domicilioModelo != null) {
                domicilioImssDig = new Domicilio();

                //Preparar Localidad
                mx.gob.imss.digital.modelo.domicilio.Localidad localidad
                        = transformaLocalidad(domicilioModelo.getLocalidad());
                
                /* Para la modalidad 34 la localidad esta en domicilioModelo.getAsentamiento().getLocalidad(), 
                 * por lo que se agrega esta validacion para envitar un nullpointerexception en el copyBeans 
                 */
                if(localidad == null){
                	localidad = transformaLocalidad(domicilioModelo.getAsentamiento().getLocalidad());
                }
                
                //Preparar Asentamiento
                mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento
                        = transformaAsentamiento(domicilioModelo.getAsentamiento());

                //Preparar Camino
                mx.gob.imss.digital.modelo.domicilio.Camino camino
                        = transformaCamino(domicilioModelo.getDomicilioCamino());
                //Preparar Carretera
                mx.gob.imss.digital.modelo.domicilio.Carretera carretera
                        = transformaCarretera(domicilioModelo.getDomicilioCarretera());
                //Preparar TipoDomicilio
                mx.gob.imss.digital.modelo.domicilio.TipoDomicilio tipoDomicilio
                        = transformaTipoDomicilio(domicilioModelo.getTipoDomicilio());
                //Preparar VialidadPrimaria
                mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadPrimaria
                        = transformaVialidad(domicilioModelo.getVialidadPrimaria());
                //Preparar VialidadReferenciaPosterior
                mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadReferenciaPosterior
                        = transformaVialidad(domicilioModelo.getVialidadReferenciaPosterior());
                //Preparar VialidadReferenciaPrimaria
                mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadReferenciaPrimaria
                        = transformaVialidad(domicilioModelo.getVialidadReferenciaPrimaria());
                //Preparar VialidadReferenciaSecundaria
                mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadReferenciaSecundaria
                        = transformaVialidad(domicilioModelo.getVialidadReferenciaSecundaria());
                //Preparar CP
                String codigoPostal = null;
                if (domicilioModelo.getCodigoPostal() != null) {
                    codigoPostal = domicilioModelo.getCodigoPostal().getCodigoPostal();
                }

                //Eliminar referencias
                domicilioModelo.setLocalidad(null);
                domicilioModelo.setAsentamiento(null);
                domicilioModelo.setDomicilioCamino(null);
                domicilioModelo.setDomicilioCarretera(null);
                domicilioModelo.setTipoDomicilio(null);
                domicilioModelo.setVialidadPrimaria(null);
                domicilioModelo.setVialidadReferenciaPosterior(null);
                domicilioModelo.setVialidadReferenciaPrimaria(null);
                domicilioModelo.setVialidadReferenciaSecundaria(null);
                domicilioModelo.setCodigoPostal(null);
                //Quitar informacion NO requerida a transformar
                domicilioModelo.setEstadoAdministracionAnteriorDomicilio(null);
                domicilioModelo.setEstadoAdministracionDomicilio(null);

                copyBeans(domicilioModelo, domicilioImssDig);
                if(domicilioImssDig.getNumExterior1()==0){
                	domicilioImssDig.setNumExterior1(null);
                }
                if(domicilioImssDig.getNumExterior2()==0){
                	domicilioImssDig.setNumExterior2(null);
                }
                if(domicilioImssDig.getNumInterior()==0){
                	domicilioImssDig.setNumInterior(null);
                }

                if (domicilioModelo.getClave() != null) {
                    domicilioImssDig.setIdDomicilio(domicilioModelo.getClave().longValue());
                }
                domicilioImssDig.setLocalidad(localidad);
                domicilioImssDig.setAsentamiento(asentamiento);
                mx.gob.imss.digital.modelo.domicilio.Localidad copiaLocalidad = new mx.gob.imss.digital.modelo.domicilio.Localidad();
                copyBeans(localidad, copiaLocalidad);
                domicilioImssDig.getAsentamiento().setLocalidad(copiaLocalidad);
                domicilioImssDig.setCamino(camino);
                domicilioImssDig.setCarretera(carretera);
                domicilioImssDig.setTipoDomicilio(tipoDomicilio);
                domicilioImssDig.setVialidadPrimaria(vialidadPrimaria);
                domicilioImssDig.setVialidadReferenciaPosterior(vialidadReferenciaPosterior);
                domicilioImssDig.setVialidadReferenciaPrimaria(vialidadReferenciaPrimaria);
                domicilioImssDig.setVialidadReferenciaSecundaria(vialidadReferenciaSecundaria);
                domicilioImssDig.setCodigoPostal(codigoPostal);
                return domicilioImssDig;
            }
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
        return null;
    }
	
	
	
	
	private static mx.gob.imss.digital.modelo.domicilio.TipoDomicilio transformaTipoDomicilio(
			TipoDomicilio tipoDomicilioHelper){		
		
		mx.gob.imss.digital.modelo.domicilio.TipoDomicilio tipoDomicilio = null;
		if(tipoDomicilioHelper !=null 
				&& tipoDomicilioHelper.getClave() !=null){
			tipoDomicilio = new mx.gob.imss.digital.modelo.domicilio.TipoDomicilio();
			tipoDomicilio.setIdTipoDomicilio(tipoDomicilioHelper.getClave().longValue());
			tipoDomicilio.setDescripcion(tipoDomicilioHelper.getDescripcion());
		}
		return tipoDomicilio;
	}

	private static mx.gob.imss.digital.modelo.domicilio.Vialidad transformaVialidad(Vialidad vialidadHelper) 
			throws IllegalAccessException, InvocationTargetException{		
		
		mx.gob.imss.digital.modelo.domicilio.Vialidad vialidad = null;
		if(vialidadHelper != null && vialidadHelper.getClave() != null){
			vialidad = new mx.gob.imss.digital.modelo.domicilio.Vialidad();
			//Preparar TipoVialidad
			mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad = 
				transformaTipoVialidad(vialidadHelper.getTipoVialidad());
			vialidadHelper.setTipoVialidad(null);
			copyBeans(vialidadHelper, vialidad);
			vialidad.setTipoVialidad(tipoVialidad);
		}
		return vialidad;		
	}
		
	private static mx.gob.imss.digital.modelo.domicilio.Camino transformaCamino(DomicilioCamino caminoHelper) 
			throws IllegalAccessException, InvocationTargetException{
		
		mx.gob.imss.digital.modelo.domicilio.Camino camino = null;
		if(caminoHelper != null){
			camino = new mx.gob.imss.digital.modelo.domicilio.Camino();
			//Preparar TipoTerminoGeneral y TipoMargen
			mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral tipoTerminoGeneral 
				= transformaTipoTerminoGeneral(caminoHelper.getTerminoGeneral());
			mx.gob.imss.digital.modelo.domicilio.TipoMargen tipoMargen 
				= transformaTipoMargen(caminoHelper.getMargen());
			//Eliminar referencias
			caminoHelper.setTerminoGeneral(null);
			caminoHelper.setMargen(null);			
			copyBeans(caminoHelper, camino);
			camino.setTerminoGeneral(tipoTerminoGeneral);
			camino.setMargen(tipoMargen);
		}
		return camino;
	}
	
	private static mx.gob.imss.digital.modelo.domicilio.Carretera transformaCarretera(
			DomicilioCarretera carreteraHelper)throws IllegalAccessException, InvocationTargetException{
		
		mx.gob.imss.digital.modelo.domicilio.Carretera carretera = null;
		if(carreteraHelper!=null){
			carretera = new mx.gob.imss.digital.modelo.domicilio.Carretera();		
			//Preparar TipoTerminoGeneral,TipoDerechoTransito y TipoAdministracion
			mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral tipoTerminoGeneral 
				= transformaTipoTerminoGeneral(carreteraHelper.getTerminoGeneral());
			mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito tipoDerechoTransito 
				= transformaTipoDerechoTransito(carreteraHelper.getDerechoTransito());
			mx.gob.imss.digital.modelo.domicilio.TipoAdministracion tipoAdministracion 
				= transformaTipoAdministracion(carreteraHelper.getAdministracion());
			//Eliminar referencias
			carreteraHelper.setTerminoGeneral(null);
			carreteraHelper.setDerechoTransito(null);
			carreteraHelper.setAdministracion(null);			
			copyBeans(carreteraHelper, carretera);
			carretera.setTerminoGeneral(tipoTerminoGeneral);
			carretera.setDerechoTransito(tipoDerechoTransito);
			carretera.setAdministracion(tipoAdministracion);
		}
		return carretera;
	}
	
	private static mx.gob.imss.digital.modelo.domicilio.Localidad transformaLocalidad(
			Localidad localidadHelper)throws IllegalAccessException, InvocationTargetException{
		
		mx.gob.imss.digital.modelo.domicilio.Localidad localidad = null;
		if(localidadHelper != null 
				&& localidadHelper.getClave()!=null){
			localidad = new mx.gob.imss.digital.modelo.domicilio.Localidad();
			//Preparar Municipio
			mx.gob.imss.digital.modelo.domicilio.Municipio municipio 
				= transformaMunicipio(localidadHelper.getMunicipio());
			localidadHelper.setMunicipio(null);			
			localidadHelper.setAsentamientos(null);
			copyBeans(localidadHelper, localidad);
			localidad.setMunicipio(municipio);
		}
		return localidad;
	}
	
	private static mx.gob.imss.digital.modelo.domicilio.Asentamiento transformaAsentamiento(
			Asentamiento asentamientoHelper)throws IllegalAccessException, InvocationTargetException{
		
		mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento = null;
		if(asentamientoHelper != null 
				&& asentamientoHelper.getClave()!=null){
			asentamiento = new mx.gob.imss.digital.modelo.domicilio.Asentamiento();
			//Preparar Localidad
			mx.gob.imss.digital.modelo.domicilio.Localidad localidad 
				= transformaLocalidad(asentamientoHelper.getLocalidad());
			//Preparar Municipio
			mx.gob.imss.digital.modelo.domicilio.Municipio municipio 
				= transformaMunicipio(asentamientoHelper.getMunicipio());
			//Preparar TipoAsentamiento
			mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento tipoAsentamiento 
				= transformaTipoAsentamiento(asentamientoHelper.getTipoAsentamiento());
			//Preparar Municipio
			String codigoPostal = null;
			if (asentamientoHelper.getCodigoPostal() != null) {
				codigoPostal = asentamientoHelper.getCodigoPostal().getCodigoPostal();
			}
			
			asentamientoHelper.setTipoAsentamiento(null);
			asentamientoHelper.setLocalidad(null);
			asentamientoHelper.setMunicipio(null);		
			asentamientoHelper.setCodigoPostal(null);
			copyBeans(asentamientoHelper, asentamiento);
			asentamiento.setTipoAsentamiento(tipoAsentamiento);
			asentamiento.setLocalidad(localidad);
			asentamiento.setMunicipio(municipio);
			asentamiento.setCodigoPostal(codigoPostal);
			
		}
		return asentamiento;
	}
	
	
	private static mx.gob.imss.digital.modelo.domicilio.Municipio transformaMunicipio(
			Municipio municipioHelper)throws IllegalAccessException, InvocationTargetException{
		mx.gob.imss.digital.modelo.domicilio.Municipio municipio = null;
		if(municipioHelper != null 
				&& municipioHelper.getClave()!=null){
			municipio = new mx.gob.imss.digital.modelo.domicilio.Municipio(); 
			//Preparar EntidadFederativa
			mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidadFederativa 
				= transformaEntidadFederativa(municipioHelper.getEntidadFederativa());
			municipioHelper.setEntidadFederativa(null);
			municipioHelper.setLocalidades(null);
			copyBeans(municipioHelper, municipio);
			municipio.setEntidadFederativa(entidadFederativa);
		}
		return municipio;
	}
	
	private static mx.gob.imss.digital.modelo.domicilio.EntidadFederativa transformaEntidadFederativa(
			EntidadFederativa entidadFederativaHelper)throws IllegalAccessException, InvocationTargetException{
		mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidadFederativa = null;
		if(entidadFederativaHelper != null 
				&& entidadFederativaHelper.getClave()!=null){
			entidadFederativa = new mx.gob.imss.digital.modelo.domicilio.EntidadFederativa();
			entidadFederativaHelper.setMunicipios(null);
			copyBeans(entidadFederativaHelper, entidadFederativa);
		}
		return entidadFederativa;
	}
	
	private static mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito transformaTipoDerechoTransito(
			TipoDerechoTransito tipoDerechoTransitoHelper)throws IllegalAccessException, InvocationTargetException{		
		mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito tipoDerechoTransito = null;
		if(tipoDerechoTransitoHelper != null){
			tipoDerechoTransito = new mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito();
			copyBeans(tipoDerechoTransitoHelper, tipoDerechoTransito);
		}		
		return tipoDerechoTransito;
	}
	
	private static mx.gob.imss.digital.modelo.domicilio.TipoAdministracion transformaTipoAdministracion(
			TipoAdministracion tipoAdministracionHelper)throws IllegalAccessException, InvocationTargetException{		
		mx.gob.imss.digital.modelo.domicilio.TipoAdministracion tipoAdministracion = null;
		if(tipoAdministracionHelper != null){
			tipoAdministracion = new mx.gob.imss.digital.modelo.domicilio.TipoAdministracion();
			copyBeans(tipoAdministracionHelper, tipoAdministracion);
		}		
		return tipoAdministracion;
	}
	
	private static mx.gob.imss.digital.modelo.domicilio.TipoMargen transformaTipoMargen(
			TipoMargen tipoMargenHelper)throws IllegalAccessException, InvocationTargetException{		
		mx.gob.imss.digital.modelo.domicilio.TipoMargen tipoMargen = null;
		if(tipoMargenHelper != null){
			tipoMargen = new mx.gob.imss.digital.modelo.domicilio.TipoMargen();
			copyBeans(tipoMargenHelper, tipoMargen);
		}		
		return tipoMargen;
	}

	private static mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral transformaTipoTerminoGeneral(
			TipoTerminoGeneral tipoTerminoGeneralHelper)throws IllegalAccessException, InvocationTargetException{		
		mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral tipoTerminoGeneral = null;
		if(tipoTerminoGeneralHelper != null){
			tipoTerminoGeneral = new mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral();
			copyBeans(tipoTerminoGeneralHelper, tipoTerminoGeneral);
		}		
		return tipoTerminoGeneral;
	}

    private static mx.gob.imss.digital.modelo.domicilio.TipoVialidad transformaTipoVialidad(
            TipoVialidad tipoVialidadHelper) throws IllegalAccessException, InvocationTargetException {
        mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad;
        if (tipoVialidadHelper != null) {
            tipoVialidad = new mx.gob.imss.digital.modelo.domicilio.TipoVialidad();
            copyBeans(tipoVialidadHelper, tipoVialidad);
        } else {
            tipoVialidad = new mx.gob.imss.digital.modelo.domicilio.TipoVialidad();
            tipoVialidad.setClave(5);
            tipoVialidad.setDescripcion("CALLE");
        }
        return tipoVialidad;
    }

	
	
	private static mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento transformaTipoAsentamiento(TipoAsentamiento tipoAsentamientoHelper) 
			throws IllegalAccessException, InvocationTargetException{
		mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento tipoAsentamiento = null;
		if(tipoAsentamientoHelper !=null 
				&& tipoAsentamientoHelper.getClave() !=null){
			tipoAsentamiento = new mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento();
			copyBeans(tipoAsentamientoHelper, tipoAsentamiento);
		}
		return tipoAsentamiento;
	}
    
	protected static void copyBeans(Object beanOrigen, Object beanDestino)
			throws IllegalAccessException, InvocationTargetException {
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
		BeanUtils.copyProperties(beanDestino, beanOrigen);

	}
	
	 /**
     * Indica si se puede cancelar el beneficio riss
     * @param seguros los seguros asociados
     * @return true si se puede cancelar
     */
    public static final boolean cancelarRISS(SeguroIvro[] seguros) {
        boolean cancelar = false;
        // Si no hay seguros activamos la accion de compra
        if (seguros.length == 1) {
            SeguroIvro seguro = seguros[0];
            Calendar dia25= Calendar.getInstance();
            dia25.setTime(seguro.getFechaFin());
            dia25.set(Calendar.DAY_OF_MONTH,26);        
            Calendar hoy = Calendar.getInstance();
            cancelar = !(hoy.before(dia25));
        }
        return cancelar;        
    }
    
    public static final boolean esPosteriorExtemporanea(Date fechaFinRenovOportuna){
    	
    	Date hoy = new Date();
    	Calendar fechaFinRenovExtemporanea =  Calendar.getInstance();
        fechaFinRenovExtemporanea.setTime(fechaFinRenovOportuna);
        fechaFinRenovExtemporanea.add(Calendar.MONTH, 1);
        LOGGER.info("Valor de fechaFinRenovExtemporanea {}", fechaFinRenovExtemporanea.getTime());
        
    	return hoy.after(fechaFinRenovExtemporanea.getTime());
    }
    
        public static Domicilio validarDatosDomicilio(Domicilio domicilio) throws DomicilioNoValidoException {
        List<String> lstErrores = new ArrayList<String>();
        StringBuilder sbErrores = new StringBuilder();
        // Validacion de informacion de Domicilio
        if (domicilio == null) {
            lstErrores.add("Es necesario ingresar la informaci\u00F3n del domicilio particular del asegurado");
        } else {
            // Validacion de Asentamiento
            mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento = domicilio.getAsentamiento();
            if (asentamiento == null || StringUtils.isBlank(asentamiento.getClave())) {
                lstErrores.add("Es necesario ingresar la informaci\u00F3n del Asentamiento");
            }
            //si no viene la localidad dentro del asentamiento se supone que es domicilio completo
            if (asentamiento.getLocalidad() == null) {
                // Validacion codigo postal
                if (StringUtils.isBlank(domicilio.getCodigoPostal())
                        && (domicilio.getAsentamiento() != null && StringUtils.isBlank(domicilio.getAsentamiento().getCodigoPostal()))) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del C\u00F3digo Postal");
                }
                // Validacion de numero
                if ((domicilio.getNumExterior1() == null || domicilio.getNumExterior1() == 0)
                        && StringUtils.isBlank(domicilio.getNumExteriorAlf())) {
                    lstErrores.add("Es necesario ingresar al menos el n\u00FAmero Exterior (Num\u00E9rico o Alfanum\u00E9rico)");
                }
                // Validacion Vialidad Primaria
                try {
                    if (isCaminoVacio(domicilio.getCamino(), asentamiento)
                            && isCarreteraVacia(domicilio.getCarretera(), asentamiento)
                            && isCalleVacia(domicilio.getCalle(), domicilio.getVialidadPrimaria(), asentamiento)) {
                        lstErrores.add("Es necesario ingresar la Vialidad Primaria o Carretera o Camino");
                    }
                } catch (DomicilioNoValidoException e) {
                    lstErrores.add(e.getMessage());
                }

                // Validacion otras vialidades
                mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadRefPrimaria = domicilio.getVialidadReferenciaPrimaria();
                if (vialidadRefPrimaria != null && vialidadRefPrimaria.getClave() != null && vialidadRefPrimaria.getClave() != 0) {
                    mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad = vialidadRefPrimaria.getTipoVialidad();
                    if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
                        lstErrores.add("Es necesario ingresar la informaci\u00F3n del Tipo de Vialidad (Vialidad Referencia Primaria)");
                    }
                }

                mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadRefSecundaria = domicilio.getVialidadReferenciaSecundaria();
                if (vialidadRefSecundaria != null && vialidadRefSecundaria.getClave() != null && vialidadRefSecundaria.getClave() != 0) {
                    mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad = vialidadRefSecundaria.getTipoVialidad();
                    if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
                        lstErrores.add("Es necesario ingresar la informaci\u00F3n del Tipo de Vialidad (Vialidad Referencia Secundaria)");
                    }
                }

                mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadRefPosterior = domicilio.getVialidadReferenciaPosterior();
                if (vialidadRefPosterior != null && vialidadRefPosterior.getClave() != null && vialidadRefPosterior.getClave() != 0) {
                    mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad = vialidadRefPosterior.getTipoVialidad();
                    if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
                        lstErrores.add("Es necesario ingresar la informaci\u00F3n del Tipo de Vialidad (Vialidad Referencia Posterior)");
                    }
                }
            }
        }

        // Se enumeran los errores detectados durante la validacion
        int numErrores = lstErrores.size();
        if (numErrores > 0) {
            for (int index = 0; index < numErrores; index++) {
                if (index == numErrores - 1) {
                    sbErrores.append(lstErrores.get(index));
                } else {
                    sbErrores.append(lstErrores.get(index)).append(", \n");
                }
            }
        }

        String strErrores = sbErrores.toString();
        if (StringUtils.isNotBlank(strErrores)) {
            throw new DomicilioNoValidoException("Se detectaron los siguientes errores al validar los datos ingresados:\n" + strErrores);
        }
        return domicilio;
    }
   
    private static boolean isCaminoVacio(Camino camino, mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento) throws DomicilioNoValidoException {
        List<String> lstErrores = new ArrayList<String>();
        StringBuilder sbErrores = new StringBuilder();
        boolean caminoVacio = false;
        if (camino == null) {
            caminoVacio = true;
        } else {
            mx.gob.imss.digital.modelo.domicilio.TipoMargen margen = camino.getMargen();
            mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral terminoGeneral = camino.getTerminoGeneral();
            if ((margen == null || margen.getClave() == null || margen.getClave() == 0L)
                    && (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L)
                    && StringUtils.isBlank(camino.getCadenamiento()) && StringUtils.isBlank(camino.getOrigen())
                    && StringUtils.isBlank(camino.getDestino())) {
                caminoVacio = true;
            } else {
                if (margen == null || margen.getClave() == null || margen.getClave() == 0L) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del Margen del Camino");
                }
                if (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del Termino General del Camino");
                }
                if (StringUtils.isBlank(camino.getCadenamiento())) {
                    lstErrores.add("Es necesario ingresar el Cadenamiento del Camino");
                }
                if (StringUtils.isBlank(camino.getOrigen())) {
                    lstErrores.add("Es necesario ingresar el Origen del Camino");
                }
                if (StringUtils.isBlank(camino.getDestino())) {
                    lstErrores.add("Es necesario ingresar el Destino del Camino");
                }

                // Validacion de localidad, municipio, entidad federativa
                mx.gob.imss.digital.modelo.domicilio.Localidad localidad = asentamiento.getLocalidad();
                if (localidad == null || StringUtils.isBlank(localidad.getClave())) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Localidad");
                } else {
                    mx.gob.imss.digital.modelo.domicilio.Municipio municipio = localidad.getMunicipio();
                    if (municipio == null || StringUtils.isBlank(municipio.getClave())) {
                        lstErrores.add(MESSAGE_MUNICIPIO);
                    } else {
                        mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();
                        if (entidadFederativa == null || StringUtils.isBlank(entidadFederativa.getClave())) {
                            lstErrores.add(MESSAGE_ENTIDAD_FEDERATIVA);
                        }
                    }
                }
            }
        }

        int numErrores = lstErrores.size();
        if (numErrores > 0) {
            for (int index = 0; index < numErrores; index++) {
                if (index == numErrores - 1) {
                    sbErrores.append(lstErrores.get(index));
                } else {
                    sbErrores.append(lstErrores.get(index)).append(", \n");
                }
            }
        }

        String strErrores = sbErrores.toString();
        if (StringUtils.isNotBlank(strErrores)) {
            throw new DomicilioNoValidoException(strErrores);
        }

        return caminoVacio;
    }

    public static boolean isCarreteraVacia(Carretera carretera, mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento) throws DomicilioNoValidoException {
        List<String> lstErrores = new ArrayList<String>();
        StringBuilder sbErrores = new StringBuilder();
        boolean carreteraVacia = false;

        if (carretera == null) {
            carreteraVacia = true;
        } else {
            mx.gob.imss.digital.modelo.domicilio.TipoAdministracion administracion = carretera.getAdministracion();
            mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito derechoTransito = carretera.getDerechoTransito();
            mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral terminoGeneral = carretera.getTerminoGeneral();

            if ((administracion == null || administracion.getClave() == null || administracion.getClave() == 0L)
                    && (derechoTransito == null || derechoTransito.getClave() == null || derechoTransito.getClave() == 0L)
                    && (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L)
                    && StringUtils.isBlank(carretera.getCadenamiento()) && StringUtils.isBlank(carretera.getOrigen())
                    && StringUtils.isBlank(carretera.getDestino())
                    && (carretera.getCodigoCarretera() == null || carretera.getCodigoCarretera() == 0)) {
                carreteraVacia = true;
            } else {
                if (administracion == null || administracion.getClave() == null || administracion.getClave() == 0L) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Administraci\u00F3n de la Carretera");
                }
                if (derechoTransito == null || derechoTransito.getClave() == null || derechoTransito.getClave() == 0L) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del Derecho de Transito de la Carretera");
                }
                if (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del Termino General de la Carretera");
                }
                if (StringUtils.isBlank(carretera.getCadenamiento())) {
                    lstErrores.add("Es necesario ingresar el Cadenamiento de la Carretera");
                }
                if (StringUtils.isBlank(carretera.getOrigen())) {
                    lstErrores.add("Es necesario ingresar el Origen de la Carretera");
                }
                if (StringUtils.isBlank(carretera.getDestino())) {
                    lstErrores.add("Es necesario ingresar el Destino de la Carretera");
                }
                if (carretera.getCodigoCarretera() == null || carretera.getCodigoCarretera() == 0) {
                    lstErrores.add("Es necesario ingresar el C\u00F3digo de la Carretera");
                }

                // Validacion de localidad, municipio, entidad federativa
                mx.gob.imss.digital.modelo.domicilio.Localidad localidad = asentamiento.getLocalidad();
                if (localidad == null || StringUtils.isBlank(localidad.getClave())) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Localidad");
                } else {
                    mx.gob.imss.digital.modelo.domicilio.Municipio municipio = localidad.getMunicipio();
                    if (municipio == null || StringUtils.isBlank(municipio.getClave())) {
                        lstErrores.add(MESSAGE_MUNICIPIO);
                    } else {
                        mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();
                        if (entidadFederativa == null || StringUtils.isBlank(entidadFederativa.getClave())) {
                            lstErrores.add(MESSAGE_ENTIDAD_FEDERATIVA);
                        }
                    }
                }
            }
        }

        int numErrores = lstErrores.size();
        if (numErrores > 0) {
            for (int index = 0; index < numErrores; index++) {
                if (index == numErrores - 1) {
                    sbErrores.append(lstErrores.get(index));
                } else {
                    sbErrores.append(lstErrores.get(index)).append(", \n");
                }
            }
        }

        String strErrores = sbErrores.toString();
        if (StringUtils.isNotBlank(strErrores)) {
            throw new DomicilioNoValidoException(strErrores);
        }

        return carreteraVacia;
    }

    public static boolean isCalleVacia(String calle, mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadPrimaria, mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento) throws DomicilioNoValidoException {
        List<String> lstErrores = new ArrayList<String>();
        StringBuilder sbErrores = new StringBuilder();
        boolean calleVacia = false;
        if (StringUtils.isBlank(calle)
                && (vialidadPrimaria == null
                || (vialidadPrimaria.getClave() == null && StringUtils.isBlank(vialidadPrimaria.getNombre()))
                || (vialidadPrimaria.getClave() != null && vialidadPrimaria.getClave() == 0 && StringUtils.isBlank(vialidadPrimaria.getNombre())))) {
            calleVacia = true;
        } else if (vialidadPrimaria == null
                || (vialidadPrimaria.getClave() == null && StringUtils.isNotBlank(vialidadPrimaria.getNombre()))
                || (vialidadPrimaria.getClave() != null && vialidadPrimaria.getClave() == 0 && StringUtils.isNotBlank(vialidadPrimaria.getNombre()))) {
            // Validacion de localidad, municipio, entidad federativa
            mx.gob.imss.digital.modelo.domicilio.Localidad localidad = asentamiento.getLocalidad();
            if (localidad == null) {
                lstErrores.add("Es necesario ingresar el Municipio como parametro de la Localidad");
            } else {
                mx.gob.imss.digital.modelo.domicilio.Municipio municipio = localidad.getMunicipio();
                if (municipio == null || StringUtils.isBlank(municipio.getClave())) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del Municipio");
                } else {
                    mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();
                    if (entidadFederativa == null || StringUtils.isBlank(entidadFederativa.getClave())) {
                        lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Entidad Federativa");
                    }
                }
            }
        }

        if (vialidadPrimaria != null) {
            mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad = vialidadPrimaria.getTipoVialidad();
            if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
                lstErrores.add("Es necesario ingresar el Tipo de Vialidad (Vialidad Primaria)");
            }
        }

        int numErrores = lstErrores.size();
        if (numErrores > 0) {
            for (int index = 0; index < numErrores; index++) {
                if (index == numErrores - 1) {
                    sbErrores.append(lstErrores.get(index));
                } else {
                    sbErrores.append(lstErrores.get(index)).append(", \n");
                }
            }
        }

        String strErrores = sbErrores.toString();
        if (StringUtils.isNotBlank(strErrores)) {
            throw new DomicilioNoValidoException(strErrores);
        }
        return calleVacia;
    }
    
    public static SeguroIvro ordenarPagosPorFechaLimitePago(SeguroIvro seguro){
    	
		List<Pago> pagos = new ArrayList<Pago>(Arrays.asList(seguro.getCompra()
				.getPagos()));

		Collections.sort(pagos, new Comparator<Pago>() {
			@Override
			public int compare(Pago o1, Pago o2) {
				int diffFecLim = o1.getFechaLimitePago().compareTo(
						o2.getFechaLimitePago());
				
				if (diffFecLim != 0) {
					return diffFecLim;
				}
				
				int diffFechaInicio = o1.getFechaInicioPeriodo().compareTo(
						o2.getFechaInicioPeriodo());
				
				if (diffFechaInicio != 0) {
					return diffFechaInicio;
				}
				
				return o1.getIdPago().compareTo(o2.getIdPago());
				
			}
		});

		seguro.getCompra().setPagos(pagos.toArray(new Pago[pagos.size()]));
		return seguro;
    }

    /**
     * Obtiene los correos asociados a la persona en una lista 
     * @param personaMC Medios de contacto de al persona
     * @return lista de correos
     */
    public static List<String> obtenerArrayCorreos(Persona personaMC){
    	List<String> correos = new ArrayList<String>();
    	if(personaMC != null && personaMC.getMediosContacto() != null){
    		for(MedioContacto medioContacto:personaMC.getMediosContacto()){
    			/**Tipo 1 es Correo electronico*/
    			if(medioContacto.getTipoMedioContacto().getIdTipoMedioContacto() == 1){
    				correos.add(medioContacto.getDesFormaContacto());
    			}
    		}
    	}
    	return correos;
    }

    /**
     * MEtodo que valida si se trata de una renovacion 35 que
     * pasara a ser una 43 y 44.
     * renovar: FALSE
     * comprar: TRUE
     * @param dcc datos cotizacion que obtuvo el valida persona service
     * @param renovar    si esta en periodo de renovacion oportuna
     * @param comprar    si no es una compra
     * @param seguros
     * @return TRUE si es valido el cambio de modalidad
     */
    public static boolean cambiarRenovacionToCompra(DatosCalculoCuota dcc, boolean renovar, boolean comprar, SeguroIvro[] seguros){

        boolean cambiarModalidad = false;
        long modalidadAComprar = dcc.getModalidad();
            LOGGER.info("modalidadAComprar: "+modalidadAComprar);

        //caso 1: compra es 43 o 44 y se va a comprar 35
        if(modalidadAComprar == ModalidadEnum.TREINTAYCINCO.getId() && renovar && !comprar && seguros.length > 0){
            //Recuperamos el seguro a renovar
            SeguroIvro seguro = seguros[0];
            long idModalidad = seguro.getModalidad().getIdModalidad();
            LOGGER.info("idModalidadSeguro: "+idModalidad);
            //Evaluamos si el seguro a renovar es diferente de 35 para ver si aplica el cambio a compra
            if( idModalidad != ModalidadEnum.TREINTAYCINCO.getId()){
                cambiarModalidad = true;
            }
        }

        //caso 2: compra ES 35 y se va a comprar 43 o 44
        if((modalidadAComprar != ModalidadEnum.TREINTAYCINCO.getId()) && renovar && !comprar && seguros.length > 0){
            //Recuperamos el seguro a renovar
            SeguroIvro seguro = seguros[0];
            long idModalidad = seguro.getModalidad().getIdModalidad();

            //Evaluamos si el seguro a renovar es modalidad 35 para ver si aplica el cambio a compra
            if( idModalidad == ModalidadEnum.TREINTAYCINCO.getId()){
                cambiarModalidad = true;
            }
        }
        return cambiarModalidad;
    }

    /**
     * Regresa el valor de una cadena recibida, si es nula regresa vacio
     * @param valor el valor a validar si es vacio
     * @return el contenido de la cadena, si es nula regresa vacio
     */
    public static String corrigeCadena(String valor) {
        return StringUtils.trimToEmpty(valor).replace("#","Ñ");
    }

    /**
     * Servicio que recupera el ultimo dia del periodo dentro del cual se encuentra la fecha
     * @param fechaAEvaluar
     * @return
     */
    public static String obtenerFechaFinPeriodo(Calendar fechaAEvaluar){
        LOGGER.info("fechaAEvaluar : "+fechaAEvaluar.getTime());

        int month = fechaAEvaluar.get(Calendar.MONTH);
        //YYYYMMDD
        switch (month){
            case 0:fechaAEvaluar.add(Calendar.MONTH,1);
            case 1:fechaAEvaluar.set(Calendar.DAY_OF_MONTH,28);
                break;
            case 2:fechaAEvaluar.add(Calendar.MONTH,1);
            case 3:fechaAEvaluar.set(Calendar.DAY_OF_MONTH,30);
                break;
            case 4:fechaAEvaluar.add(Calendar.MONTH,1);
            case 5:fechaAEvaluar.set(Calendar.DAY_OF_MONTH,30);
                break;
            case 6:fechaAEvaluar.add(Calendar.MONTH,1);
            case 7:fechaAEvaluar.set(Calendar.DAY_OF_MONTH,31);
                break;
            case 8:fechaAEvaluar.add(Calendar.MONTH,1);
            case 9:fechaAEvaluar.set(Calendar.DAY_OF_MONTH,31);
                break;
            case 10:fechaAEvaluar.add(Calendar.MONTH,1);
            case 11:fechaAEvaluar.set(Calendar.DAY_OF_MONTH,31);
                break;
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String datoFinal = formato.format(fechaAEvaluar.getTime());
        LOGGER.info("datoFinal: "+datoFinal);
        return datoFinal;
    }
}
