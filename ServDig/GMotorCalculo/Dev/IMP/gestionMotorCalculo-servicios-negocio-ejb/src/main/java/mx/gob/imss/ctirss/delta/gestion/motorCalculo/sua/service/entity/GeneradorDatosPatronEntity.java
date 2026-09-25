/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.entity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.MotorBeneficiosBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces.GeneradorDatosPatronLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util.SuaUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.AreaGeograficaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.Patron;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para generar patrones SUA a partir de su registro patronal
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "generadorDatosPatronEntity", mappedName = "generadorDatosPatronEntity")
public class GeneradorDatosPatronEntity implements GeneradorDatosPatronLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneradorDatosPatronEntity.class);
    /**
     * Tipo de documento default
     */
    private static final int TIPO_DOCUMENTO = 01;
    /**
     * Tipo de cotizacion default
     */
    private static final int TIPO_COTIZACION = 03;
    /**
     * Valor para el convenio de reembolso
     */
    private static final String CONVENIO_REEMBOLSO = "N";
    /**
     * Apartodo beneficio
     */
    private static final String APARTADO = "A";
    /**
     * Usuario dummy riss
     */
    private static final String USUARIO_RISS  = "IMSSDIGI";
    /**
     * Ejb para el motor de beneficios
     */
    @EJB
    private MotorBeneficiosBusinessLocal motorBusinessLocal;

    /**
     * GEnera el registro de un patron para el archivo SUA
     * @param datos Objeto con los valores de calculo en el registro
     * @param sujeto Entidad que representa las personas fisicas o morales
     * @param periodo PEriodo en el cual se realizaron los calculos de cobro
     * @return El registro con los datos de un patron para el archivo SUA
     * @throws SUAException Si ocurren error al generar el registro del patron
     */
    @Override
    public Patron generaPatron(CalculoCuota datos, SujetoObligado sujeto, PeriodoCuota periodo, 
            EmpleadoCuota empleado, String nrp35) throws SUAException {
        LOGGER.debug("Generando patron");

        String cadena = "";
        if(datos.getConBeneficio() != null && datos.getConBeneficio().booleanValue()) {
            ValoresCalculoEmpleado valores = new ValoresCalculoEmpleado();
            valores.setFechaFinCalculo(periodo.getFinPeriodo());
            valores.setFechaInicioCalculo(periodo.getInicioPeriodo());
            valores.setModalidad(datos.getModalidad());
            valores.setEmpleado(new DatosEmpleado());
            valores.getEmpleado().setNumeroSeguridadSocial(empleado.getNumeroSeguridadSocial());
            Beneficio beneficio = motorBusinessLocal.buscarBeneficioTrabajador(valores);
            BigDecimal porcentaje = beneficio.getDescuentoActual().getPorcentajeDescuento();
            cadena = getCadenaRiss(porcentaje, empleado.getNumeroSeguridadSocial());
        }
        Patron patron = construyePatron(sujeto, cadena);
        String zona = StringUtils.trimToNull(datos.getZonaSalarial()) != null ? datos.getZonaSalarial() 
                : AreaGeograficaEnum.ZONA_A.getClave();
        patron.setAreaGeograficaSalariosMinimos(zona);
        patron.setNumeroDeTrabajadoresCotizantes(datos.getEmpleados().length);
        if (StringUtils.trimToNull(nrp35) != null) {
            patron.setRegistroPatronalIMSS(nrp35);
        } else {
            patron.setRegistroPatronalIMSS(datos.getNumeroRegistroPatronal());
        }
        patron.setPeriodoDePago(getPeriodoPago(periodo.getFinPeriodo()));
        
        return patron;
    }

    /**
     * Construye un patron a partir del sujeto oblida que se encuentre en BD
     * 
     * @param sujeto LA entidad con los datos de un patron (Persona fisica o moral)
     * @return El registro de un patron para el archivo SUA
     * @throws SUAException Errors al armar el patron
     */
    private Patron construyePatron(SujetoObligado sujeto, String cadena) throws SUAException {
        Patron patron = new Patron();
        agregaValoresIniciales(patron);
        Subdelegacion subdelegacion = sujeto.getSubdelegacion();
        patron.setDelegacionDelIMSS(SuaUtil.getClaveEntidad(subdelegacion.getDelegacion()
                .getClave()));
        patron.setSubdelegacionDelIMSS(SuaUtil.getClaveEntidad(subdelegacion.getClave()));        
        patron.setFiller(SuaUtil.FILLER);
        patron.setPrimaDeRiesgoDeTrabajo(sujeto.getClasificacion().getPrimaSRTActual());
        patron.setActividadEconomica(sujeto.getClasificacion().getGiro());
        LOGGER.debug("PATRON A GENERAR DATOS DE DOMICILIO {}", ReflectionToStringBuilder.toString(sujeto));
        agregaDatosDireccion(patron, sujeto.getCntroTrabajo(), sujeto.getMunicipioIMSS(), cadena);
        agregaDatosPersona(sujeto, patron);
        return patron;
    }

    /**
     * MEtodo que agrega los valores iniciales o default para el registro de
     * patron Estos valores son default para el IVRO, cuando se agregen mas
     * modalidades, algnos de estos valores deberian ser obtenidos de la entrada
     * 
     * @param patron Registro al cual se agregan los valores iniciales
     */
    private void agregaValoresIniciales(Patron patron) {
        // estos valores se generan con defaults hasta que otras
        // modalidades necesiten agregarlos especificamente
        patron.setConvenioDeReembolsoDeSubsidios(CONVENIO_REEMBOLSO);
        patron.setFechaDeLaPrimaDeRiesgoDeTrabajo(new Date());     
        patron.setNumeroDeCreditoIMSS(BigDecimal.ZERO.longValue());
        patron.setPorcentajeDeAportacionINFONAVIT(BigDecimal.ZERO);
        patron.setTipoDocumento(TIPO_DOCUMENTO);
        patron.setTipoDeCotizacion(TIPO_COTIZACION);
    }

    /**
     * Agrega los datos del domicilio al patron
     * 
     * @param patron Registro al cual se agregaran los datos del domicilio
     * @param centroTrabajo Objeto con los datos del domicilio de un patron
     * @throws SUAException Error con datos incompletos
     */
    private void agregaDatosDireccion(Patron patron, CentroTrabajo centroTrabajo, 
            MunicipioIMSS municipio, String cadena) throws SUAException {
    	String calle = centroTrabajo.getVialidadPrimaria()!=null 
    			? StringUtils.isNotBlank(centroTrabajo.getVialidadPrimaria().getNombre()) ? centroTrabajo.getVialidadPrimaria().getNombre()   : StringUtils.isNotBlank(centroTrabajo.getCalle()) ? centroTrabajo.getCalle() : "CONOCIDA"
    			: StringUtils.isNotBlank(centroTrabajo.getCalle()) ? centroTrabajo.getCalle() : "CONOCIDA";
    	StringBuffer calleNoColonia = new StringBuffer();
    	calleNoColonia.append(calle);
    	if(SuaUtil.getValorNoNulo(centroTrabajo.getNumExterior1())!=null) {
    		calleNoColonia.append(" ").append( centroTrabajo.getNumExterior1() );
    	}
    	if(StringUtils.isNotBlank(centroTrabajo.getNumExteriorAlf())) {
    		calleNoColonia.append(" ").append(SuaUtil.getCadenaNoNula(centroTrabajo.getNumExteriorAlf()));
    	}
    	
    	calleNoColonia.append(SuaUtil.getCadenaNoNula(centroTrabajo.getColonia()));

    	String calleNo = calleNoColonia.toString().replaceAll(",", "");
        patron.setCalleNoYColonia(SuaUtil.getCadenaNoNula(cadena) + calleNo);
        patron.setCodigoPostal(centroTrabajo.getCodigoPostal() != null 
                ? SuaUtil.getClaveEntidad(centroTrabajo.getCodigoPostal().getCodigoPostal()) : 0);
        if (centroTrabajo.getLocalidad() != null || (centroTrabajo.getAsentamiento() != null 
                && centroTrabajo.getAsentamiento().getLocalidad() != null)) {
            Localidad localidad = centroTrabajo.getLocalidad() != null ? centroTrabajo.getLocalidad()
                    : centroTrabajo.getAsentamiento().getLocalidad();
            patron.setEntidadFederativa(SuaUtil.getClaveEntidad(localidad.getMunicipio()
                    .getEntidadFederativa().getClave()));
            patron.setPoblacionYMunicipioODelegacionPolitica(localidad.getMunicipio().getNombre());
            patron.setMunicipio(localidad.getMunicipio().getClave());
            
        } else if (municipio != null) {
            patron.setPoblacionYMunicipioODelegacionPolitica(municipio.getDescMunicipio());
            patron.setMunicipio(municipio.getCvecMunicipioSINDO());
            
        }else {            
            throw new SUAException(SUAConstants.COD_NO_LOCALIDAD, SUAConstants.MSG_NO_LOCALIDAD);
        }
        patron.setTelefono(0);

    }

    /**
     * Agrega los datos que estan asociados a una persona y varian si es moral o
     * fisica
     * 
     * @param sujeto Persona de la cual se obtiene la informacion para el registro de patron, 
     * esta puede ser fisica o maral 
     * @param patron Registro al cual se agregan los datos del patron obtenids del sujeto obligado
     */
    private void agregaDatosPersona(SujetoObligado sujeto, Patron patron) {
        if (sujeto.getFisica() != null) {
            Fisica persona = sujeto.getFisica();
            patron.setNombreORazonSocial(persona.getNombreCompleto());
            patron.setRfcPatron(persona.getRfc());
        } else {
            Moral persona = sujeto.getMoral();
            patron.setNombreORazonSocial(persona.getRazonSocial());
            if(persona.getRfc() != null) {
                patron.setRfcPatron(StringUtils.leftPad(persona.getRfc(), 13, ' '));
            }            
        }
    }   
    
    /**
     * Obtiene el valor de la fecha a agregar como periodo de pago, debido a que en estos seguros hay que pagar 
     * por bimestre para agregar los valores de RCV y Viviendo aunque sea un mes non
     * @param fechaFinCalculo la fecha final del periodo de pago
     * @return La fecha a utilizar como periodo de pago
     */
    private Date getPeriodoPago(Calendar fechaFinCalculo) {
        Calendar periodoPago = (Calendar) fechaFinCalculo.clone();
        if (periodoPago.get(Calendar.MONTH) % 2 == 0) {
            periodoPago.add(Calendar.MONTH, 1);
        }
        return periodoPago.getTime();
    }
    
    /**
     * GEnera la cadena que pide sue para cuenado es con beneficio riss
     * @param porcentaje porcentaje de descuento
     * @param nss numero de segurdad del trabajadr
     * @return la cadena riss
     */
    private String getCadenaRiss(BigDecimal porcentaje, String nss) {
        StringBuilder cadena = new StringBuilder("");
        cadena.append(APARTADO).append(USUARIO_RISS).append(" ").append(porcentaje.intValue())
        .append(" ").append(nss).append(" ");
        return cadena.toString();
    }
}
