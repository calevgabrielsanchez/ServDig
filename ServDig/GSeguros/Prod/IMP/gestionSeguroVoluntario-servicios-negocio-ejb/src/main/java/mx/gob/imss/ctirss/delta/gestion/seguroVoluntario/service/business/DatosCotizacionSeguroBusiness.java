/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;

import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.*;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroConstants;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.AreaGeograficaEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.PeriodoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "datosCotizacionSeguroBusiness", mappedName = "datosCotizacionSeguroBusiness")
public class DatosCotizacionSeguroBusiness implements DatosCotizacionSeguroRemote {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DatosCotizacionSeguroBusiness.class);
    /**
     * Servicio de vigencia
     */
    @EJB
    private VigenciaIvroServiceLocal vigenciaIvroServiceLocal;
    /**
     * Servicio de consulta de seguros
     */
    @EJB
    private ConsultaSeguroIvroLocal consultaSeguroIvroLocal;
    /**
     * Servicio para obtener la cotizacion anterior
     */
    @EJB(name = "cotizacionServiceBusiness", mappedName = "cotizacionServiceBusiness")
    private CotizacionServiceRemote cotizacionServiceRemote;
    /**
     * Servicios de parametros
     */
    @EJB
    private ParametrosEntityLocal parametros;
    /**
     * Servicio para la consulta de trabajadores migrados de los viejos seguros
     */
    @EJB
    private ConsultaBeneficiarioMigradoLocal consultaBeneficiarioMigradoLocal;
    /**
     * Servicio para obtener persona
     */
    @EJB(name = "personaBusiness", mappedName = "personaBusiness")
    private PersonaBusinessRemote personaBusiness;

    @EJB
    private DomicilioServiceBusinessRemote domicilioServiceEntityLocal;
    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * DatosCotizacionSeguroRemote
     * #datosCotizacionIndividual(mx.gob.imss.digital.modelo.persona.Persona)
     */
    @Override
    public DatosCalculoCuota datosCotizacionIndividual(Persona persona) throws IvroException {
        DatosCalculoCuota datos = new DatosCalculoCuota();
        mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica personaActual = personaBusiness
                .getPersonaFisica(persona.getIdPersona());
        Fisica fisica = new Fisica();
        fisica.setNss(personaActual.getNss());
        fisica.setIdPersona(persona.getIdPersona());
        fisica.setIdModalidad(ModalidadEnum.TREINTAYCINCO.getId()); // Se fija modalidad para
        // el calculo de individuales para contemplar riss si lo tiene
        PeriodoSeguro periodo = vigenciaIvroServiceLocal.obtenPeriodoSeguroIndividual(fisica);
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(periodo.getFechaIncial());
        Calendar fin = Calendar.getInstance();
        fin.setTime(periodo.getFechaFinal());
        datos.setFechaInicioCalculo(inicio);
        datos.setFechaFinCalculo(fin);
        datos.setRenovacion(periodo.getRenovacion());
        //Para IVRO siempre se agrega la zona del DF
        String zonaSalarial = getZonaSalariaDF();
        datos.setZonaSalarial(zonaSalarial);
//        datos.setZonaSalarial(AreaGeograficaEnum.ZONA_A.getClave());
        if (periodo.getRenovacion()) {
            SeguroIvro seguro = consultaSeguroIvroLocal.getUltimoSeguro(persona);
            copiaRenovacion(datos, seguro);
            if (datos.getModalidad() == ModalidadEnum.TREINTAYCINCO.getId()) {
                datos.setModalidad(0);
            }
        } else {
            datos.setEmpleados(new DatosEmpleado[] {new DatosEmpleado() });
        }
        String zonaSalarialOriginal = parametros.obtenZonaSalarialOriginal(zonaSalarial, ModalidadEnum.CUARENTAYCUATRO);
        datos.setZonaSalarial(zonaSalarialOriginal);

        return datos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * DatosCotizacionSeguroRemote
     * #datosCotizacionDomesticoCompra(mx.gob.imss.digital
     * .modelo.persona.Persona)
     */
    @Override
    public DatosCalculoCuota datosCotizacionDomesticoCompra(Persona persona) throws IvroException {
        DatosCalculoCuota datos = new DatosCalculoCuota();
        Fisica fisica = new Fisica();
        fisica.setIdPersona(persona.getIdPersona());
        PeriodoSeguro periodo = vigenciaIvroServiceLocal.obtenPeriodoSeguroDomestico(fisica);
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(periodo.getFechaIncial());
        Calendar fin = Calendar.getInstance();
        fin.setTime(periodo.getFechaFinal());
        datos.setFechaInicioCalculo(inicio);
        datos.setFechaFinCalculo(fin);
        datos.setRenovacion(periodo.getRenovacion());
        datos.setEmpleados(new DatosEmpleado[] {});
        datos.setModalidad(ModalidadEnum.TREINTAYCUATRO.getId());
        LOGGER.info("Zona Salarial para el DOMESTICO: "+ persona.getErrorFormGeneral());
        BigDecimal salario = null;
        try {
            salario = parametros.getSalarioMinimoIVRO(persona.getErrorFormGeneral());
            String zonaSalarialOriginal = parametros.obtenZonaSalarialOriginal(persona.getErrorFormGeneral(), ModalidadEnum.CUARENTAYCUATRO);
            datos.setZonaSalarial(zonaSalarialOriginal);
            datos.setSalarioMinimo(salario);

            LOGGER.info("SALARIO MINIMO DOMESTICO: "+salario);
            LOGGER.info("Zona salarial original: "+zonaSalarialOriginal);
        }catch (SUAException suae){
            LOGGER.error("Ocurrio un error al recuperar el salario: "+suae.getMessage());
            throw new IvroException(suae.getMessage());
        }

        return datos;
    }

	@Override
	public DatosCalculoCuota datosCotizacionSeguroFamiliar(Persona persona)
			throws IvroException {
		DatosCalculoCuota datos = new DatosCalculoCuota();

		PeriodoSeguro periodo = vigenciaIvroServiceLocal.obtenerPeriodoSeguroFamiliar();
		Calendar inicio = Calendar.getInstance();
		inicio.setTime(periodo.getFechaIncial());
		Calendar fin = Calendar.getInstance();
		fin.setTime(periodo.getFechaFinal());

		datos.setFechaInicioCalculo(inicio);
		datos.setFechaFinCalculo(fin);
		datos.setRenovacion(periodo.getRenovacion());
		datos.setEmpleados(new DatosEmpleado[] {});
		datos.setModalidad(ModalidadEnum.TREINTAYTRES.getId());
		datos.setZonaSalarial(StringUtils.trimToNull(persona.getErrorFormGeneral()) != null
				? persona.getErrorFormGeneral() : AreaGeograficaEnum.ZONA_A.getClave());
		return datos;
	}

	@Override
	public DatosCalculoCuota datosCotizacionContinuacionVoluntaria(
			Persona persona) throws IvroException {
		DatosCalculoCuota datos = new DatosCalculoCuota();

		LOGGER.info("ENTRANDO A DATOS COTIZACION CVRO");

        try {
            PeriodoSeguro periodo = vigenciaIvroServiceLocal.obtenerPeriodoContinuacionVoluntaria();
            Calendar inicio = Calendar.getInstance();
            inicio.setTime(periodo.getFechaIncial());
            Calendar fin = Calendar.getInstance();
            fin.setTime(periodo.getFechaFinal());

            if (persona.getIndRIF() != null && persona.getIndRIF()) {
                inicio.set(Calendar.DATE, 1);
            }

            datos.setFechaInicioCalculo(inicio);
            datos.setFechaFinCalculo(fin);
            datos.setRenovacion(periodo.getRenovacion());
            datos.setEmpleados(new DatosEmpleado[]{});
            datos.setModalidad(ModalidadEnum.CUARENTA.getId());
            String zonaSalarial = null;
            BigDecimal salario = null;
            try {
                if (persona.getErrorFormGeneral() != null || !persona.getErrorFormGeneral().equals(":")) {
                    zonaSalarial = persona.getErrorFormGeneral();
                    LOGGER.info("LA ZONA SALARIA ES: " + zonaSalarial);
                    salario = parametros.getSalarioMinimoCRVO(zonaSalarial);
                    LOGGER.info("Obteniendo el salario: "+salario);
                    String zonaSalarialOriginal = parametros.obtenZonaSalarialOriginal(zonaSalarial, ModalidadEnum.CUARENTA);
                    LOGGER.info("La zona salarial original es: "+zonaSalarialOriginal);
                    datos.setZonaSalarial(zonaSalarialOriginal);
                    datos.setSalarioMinimo(salario);
                } else {
                    LOGGER.info("NO se pudo recuperar la zona Salarial");
                    throw new IvroException("NO se pudo recuperar la zona Salarial");
                }


            } catch (SUAException suae) {
                LOGGER.error("Ocurrio un error al obtener el salario minimo: ",suae);
                throw new IvroException(suae.getMessage());
            }

            datos.setRecargos(vigenciaIvroServiceLocal.aplicaRecargo(ModalidadEnum.CUARENTA.getId()));

            LOGGER.info("Termino la cotizacion correctamente");
        }catch(Exception e){
            LOGGER.error("OCURRIO UN ERROR: ",e);
            throw new IvroException(e.getMessage());
        }

		return datos;
	}

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * DatosCotizacionSeguroRemote
     * #datosCotizacionDomesticoRenovacion(mx.gob.imss
     * .digital.modelo.seguros.SeguroIvro)
     */
    @Override
    public DatosCalculoCuota datosCotizacionDomesticoRenovacion(SeguroIvro seguro)
            throws IvroException {
        DatosCalculoCuota datos = new DatosCalculoCuota();
        PeriodoSeguro periodo = vigenciaIvroServiceLocal.obtenPeriodoRenovacionDomestico(seguro);
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(periodo.getFechaIncial());
        Calendar fin = Calendar.getInstance();
        fin.setTime(periodo.getFechaFinal());
        datos.setFechaInicioCalculo(inicio);
        datos.setFechaFinCalculo(fin);
        datos.setRenovacion(true);
        copiaRenovacion(datos, seguro);
        datos.setModalidad(ModalidadEnum.TREINTAYCUATRO.getId());
        return datos;
    }

    /**
     * Copia los valores de una cotizacion anterior a una nueva cotizacion
     * 
     * @param nuevo
     *            los datos iniciales de calclo y adonde se copiaran los valores
     * @param seguroARenovar
     *            el seguro que vamos a renovar
     * @return los datos copiados de la renovacion anterior
     * @throws IvroException
     *             errores al obtener la informacion de la cotizacion anteror
     */
    private void copiaRenovacion(DatosCalculoCuota nuevo, SeguroIvro seguroARenovar)
            throws IvroException {
        SeguroIvro seguro = consultaSeguroIvroLocal.buscaSeguroPorId(seguroARenovar
                .getCveIdSeguroIvro());
        List<DatosEmpleado> empleadosCopia = new ArrayList<DatosEmpleado>();
        try {

            if (seguro.getCompra() != null && seguro.getCompra().getIdCotizacion() != null) {
                Cotizacion cotizacion = cotizacionServiceRemote.findCotizacion(seguro.getCompra()
                        .getIdCotizacion());
                // nuevo.setNumeroRegistroPatronal(cotizacion.getDetalle().getNumeroRegistroPatronal());
                for (EmpleadoCuota empleado : cotizacion.getDetalle().getEmpleados()) {
                    DatosEmpleado empleadoCopia = new DatosEmpleado();
                    empleadoCopia.setNumeroSeguridadSocial(empleado.getNumeroSeguridadSocial());
                    empleadoCopia.setSalario(empleado.getSalario());
                    empleadosCopia.add(empleadoCopia);
                }
                nuevo.setZonaSalarial(cotizacion.getDetalle().getZonaSalarial());
            } else {
                Map<Fisica, BigDecimal> personaSalario = consultaBeneficiarioMigradoLocal
                        .buscaBeneficiariosSalarioSeguro(seguro);
                for (Entry<Fisica, BigDecimal> benef : personaSalario.entrySet()) {
                    DatosEmpleado empleadoCopia = new DatosEmpleado();
                    empleadoCopia.setNumeroSeguridadSocial(benef.getKey().getNss());
                    empleadoCopia.setSalario(benef.getValue());
                    empleadosCopia.add(empleadoCopia);
                }
                //Cuando no esta bien migrado, que signfica que no hay registro en DIT_SEGURO_IVRO_MIGRADO
                //SE OBTIENE NSS Del seguro encontrado en DIT_SEGURO_IVRO para modalidades 35,43 y 44
                long idModalidadSeguro = seguro.getModalidad().getIdModalidad();
                if(personaSalario.size()<=0 
                		&& (idModalidadSeguro==ModalidadEnum.CUARENTAYTRES.getId()
                				|| idModalidadSeguro==ModalidadEnum.CUARENTAYCUATRO.getId()
                				|| idModalidadSeguro==ModalidadEnum.TREINTAYCINCO.getId()) ){
                	DatosEmpleado empleadoCopia = new DatosEmpleado();
                    empleadoCopia.setNumeroSeguridadSocial(seguro.getTitular().getNss());
                    empleadosCopia.add(empleadoCopia);
                }
                Persona persona = new Persona();
                persona.setIdPersona(seguroARenovar.getTitular().getIdPersona());
                String zonaSalarial = getZonaSalarial(persona);
                nuevo.setZonaSalarial(zonaSalarial);

            }
        } catch (SUAException e) {
            throw new IvroException(IvroConstants.COD_SEG_SIN_COTIZACION,
                    IvroConstants.MSG_SEG_SIN_COTIZACION);
        }
        nuevo.setModalidad(seguro.getModalidad().getIdModalidad());
        nuevo.setEmpleados(empleadosCopia.toArray(new DatosEmpleado[empleadosCopia.size()]));
        BigDecimal salario = null;
        try {
            Persona persona = new Persona();
            persona.setIdPersona(seguroARenovar.getTitular().getIdPersona());
            salario = parametros.getSalarioMinimoIVRO(getZonaSalarial(persona));
        }catch (SUAException suae){
            throw new IvroException(suae.getMessage());
        }
        nuevo.setSalarioMinimo(salario);
        nuevo.setIdEmpleador(seguroARenovar.getTitular().getIdPersona());
        if (seguro.getTramite()!=null && seguro.getTramite().getRegistroPatronal()!=null) {
        	nuevo.setNumeroRegistroPatronal(seguro.getTramite().getRegistroPatronal().getNumeroRegistroPatronal());
        }

    }

	@Override
	public BigDecimal getSalarioMinimoDf(String zonaSalarial) {
        Calendar fecha = Calendar.getInstance();
        return getSalarioMinimoDfPorFecha(zonaSalarial, fecha);
	}

    @Override
    public BigDecimal getSalarioMinimoDfPorFecha(String zonaSalarial, Calendar fecha) {
        BigDecimal salarioMinimo;
        try {
            salarioMinimo = parametros.getSalarioMinimoCDMX(zonaSalarial, fecha);
        } catch (SUAException e) {
            salarioMinimo = BigDecimal.ZERO;
        }

        return salarioMinimo;
    }
    
    @Override
	public BigDecimal getUma(String fecha) {
		BigDecimal uma;
		
		LOGGER.debug("Fecha para obtener UMA " +fecha);
        try {
        	uma = parametros.getUma(fecha);
        	
        	LOGGER.debug("UMA recuperado " +uma);
        } catch (SUAException e) {
        	uma = BigDecimal.ZERO;
        }
        return uma;
	}
	private String getZonaSalariaDF(){
        String CLAVE_ENTIDAD_CDMX = "09";
        String CLAVE_MUNICIPIO_CDMX = "015";

        return CLAVE_ENTIDAD_CDMX+":"+CLAVE_MUNICIPIO_CDMX;
    }

	private String getZonaSalarial(Persona persona) throws IvroException {
        String zonaSalarial = null;
        mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona persona1 = new mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona();
        persona1.setIdPersona(persona.getIdPersona());
        List<Domicilio> domicilios = new ArrayList<Domicilio>();

        try {
            domicilios = domicilioServiceEntityLocal.consultarDomiciliosPersonaFisica(persona1);
            String cveMun = null;
            String cveEnt = null;
            if(domicilios == null && domicilios.size()<=0) {
                throw new IvroException("No se obtuvo resultado");
            }else{
                Domicilio dom = domicilios.get(0);
                if(dom.getAsentamiento()==null){
                    LOGGER.error("No se obtuvo el asentamiento");
                }else{
                    if(dom.getAsentamiento().getLocalidad()!=null){
                        cveMun = dom.getAsentamiento().getLocalidad().getMunicipio().getClave();
                    }else{
                        LOGGER.error("No existe municipio");
                    }
                    if(dom.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa()!=null){
                        cveEnt = dom.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getClave();
                    }else{
                        LOGGER.error("No existe Entidad Federativa");
                    }

                }
            }


            zonaSalarial = cveEnt+":"+cveMun;
        } catch (DomicilioNoLocalizadoException e) {
            LOGGER.error("NO se encontr� un domicilio asociado: ",e);
            throw new IvroException("NO se encontro un domicilio asociado a la persona: "+persona.getIdPersona());
        } catch (Exception e){
            LOGGER.error("ERROR al obtener el domicilio: ",e);
            throw new IvroException("ERROR al obtener el domicilio de la persona: "+persona.getIdPersona()+": "+e);
        }
        return zonaSalarial;
    }

}
