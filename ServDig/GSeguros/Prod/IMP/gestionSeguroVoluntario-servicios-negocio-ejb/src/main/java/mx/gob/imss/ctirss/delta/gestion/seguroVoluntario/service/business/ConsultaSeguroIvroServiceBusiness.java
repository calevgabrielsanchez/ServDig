/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.*;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSeguroIvroEnum;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.medio.contacto.MedioContacto;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NOVUTECK1
 *
 */
@Stateless(name = "consultaSeguroIvroServiceBusiness", mappedName = "consultaSeguroIvroServiceBusiness")
public class ConsultaSeguroIvroServiceBusiness implements ConsultaSeguroIvroServiceRemote {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConsultaSeguroIvroServiceBusiness.class);

    private static final Long PENDIENTE_DE_PAGO = 1L;
    /**
     * Servicio local de consulta de seguros
     */
    @EJB
    private ConsultaSeguroIvroLocal consultaSeguroIvroLocal;
    /**
     * Servicio local de calculo de vigencias
     */
    @EJB
    private VigenciaIvroServiceLocal vigenciaIvroServiceLocal;
    /**
     * Seguro migrados
     */
    @EJB
    private ConsultaBeneficiarioMigradoLocal consultaMigrado;
    /**
     * Servicoi para la consulta de personas fisicas
     */
    @EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;

    /**
     * Servici para el envio de correos.
     */
    @EJB
    private EnviaCorreoLocal enviaCorreoEntity;

    @EJB(name = "componentesExternosBusiness", mappedName = "componentesExternosBusiness")
    private ComponentesExternosBusinessRemote componentesExternosBusiness;

    @EJB(name = "domicilioServiceBusiness", mappedName = "domicilioServiceBusiness")
    private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;

    /**
     * Busca el ultimo seguro individual asociado a una persona o el que este
     * activo en periodo de renovacion y el nuevo por pagar
     *
     * @param persona la persona a buscar sus seguros individuales
     * @return La lista de seguros encontrados
     */
    public SegurosIvro buscaUltimosSegurosIndividual(Persona persona) {
        LOGGER.debug("Buscando ultimos segurosIndividuales");
        List<SeguroIvro> seguros = consultaSeguroIvroLocal.buscaUltimosSegurosIndividual(persona);
        // Si solo contiene un seguro verificamos que se encuentre enperiodo de
        // renovacion
        if (seguros.size() > 0) {

            SeguroIvro seguro = seguros.get(0);
            LOGGER.info("seguro : "+seguro.getCveIdSeguroIvro());
            try {
            	// TODO DesHabilitar cuando la renovacion aplique
                Boolean renovacion = vigenciaIvroServiceLocal.isPeriodoRenovacion(seguro);
//                renovacion = false;
                seguro.setEnRenovacion(renovacion);

                if (renovacion) {
                    Boolean extemporanea = vigenciaIvroServiceLocal.isRenovacionExtemporanea(seguro);
                    seguro.setExtemporanea(extemporanea);
                }
                seguros = new ArrayList<SeguroIvro>();
                seguros.add(seguro);
            } catch (IvroException e) {
                LOGGER.warn("Error al verificar si es renovacion ", e);
            }
        }
        LOGGER.debug("Regresando Seguros individuales");
        return convierteDeLista(seguros);
    }

    /**
     * Se valida si el ultimo seguro anterior esta en estado concluido o vencido para quitar beneficio RISS
     * @param idPersona
     * @return
     */
    @Override
    public boolean validaSeguroAnteriorVencidoCancelado(Long idPersona){

        Persona persona = new Persona();
        persona.setIdPersona(idPersona);
        LOGGER.debug("Buscando ultimos segurosIndividuales");
        List<SeguroIvro> seguros = consultaSeguroIvroLocal.buscaUltimosSegurosIndividual(persona);

        if (seguros!=null && seguros.size() > 0) {
            SeguroIvro seguro = seguros.get(0);
            LOGGER.info("Seguro: "+seguro.getCveIdSeguroIvro());

            if(seguro!=null &&
                (seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.VENCIDO.getId())
                || (seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.CANCELADO.getId())))){
                LOGGER.info("El seguro anterior es vencido o cancelado");
                return true;
            }
        }

        LOGGER.info("El seguro anterior no esta vencido o concluido");
        return false;
    }

    /**
     * Busca todos los seguros activos asociados a un patron, o los que estene
     * el mes de renovacion extemporania y que no esten renovados a´n
     *
     * @param persona Los datos del patron asociado a los segurps
     * @return los seguros domesticos encontrados
     */
    public SegurosIvro buscaSegurosDomesticoPatron(Persona persona) {
       
        List<SeguroIvro> seguros = consultaSeguroIvroLocal.buscaSegurosDomesticoPatron(persona);
        List<SeguroIvro> nuevos = new ArrayList<SeguroIvro>();
        for (SeguroIvro seguro : seguros) {
            try {
                // TODO DesHabilitar cuando la renovacion aplique
                Boolean renovacion = vigenciaIvroServiceLocal.isPeriodoRenovacion(seguro);
                renovacion = false;

                seguro.setEnRenovacion(renovacion);

                if (seguro.getTramite() == null) {
                    seguro.setTramite(new TramiteSeguroIvro());
                    Fisica personaMig = consultaMigrado.buscaBeneficiarioSeguro(seguro);
                    try {
                        mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica fisica = personaFisicaServiceBusiness
                                .localizarPersonaFisicaPorNss(personaMig.getNss());
                        personaMig.setNombre(generaNombre(fisica.getNombre(),
                                fisica.getPrimerApellido(), fisica.getSegundoApellido()));
                    } catch (Exception e) {
                        LOGGER.warn("No se encontro nombre del empleado");
                    }
                    seguro.getTramite().setBeneficiarios(new Fisica[]{personaMig});
                }
                nuevos.add(seguro);
            } catch (IvroException e) {
                LOGGER.warn("Error al verificar si es renovacion ", e);
            }
        }
        return convierteDeLista(nuevos);
    }

    /**
     * Busca todos los seguros familiares asociados a un solicitante
     *
     * @param persona - con el idPersona setteado
     * @return los seguros familiares encontrados
     */
    @Override
    public SegurosIvro buscaSegurosFamiliares(Persona persona) {
        List<SeguroIvro> seguros = consultaSeguroIvroLocal.buscaSegurosFamiliares(persona);
        List<SeguroIvro> segurosPersona = new ArrayList<SeguroIvro>();

        for (SeguroIvro seguro : seguros) {
            if (seguro.getTramite() == null) {
                /*
				 * En caso de que el seguro sea migrado, no se tiene trámite asociado,
				 * por lo tanto, se tiene que buscar los beneficiarios a mano
                 */
                seguro.setTramite(new TramiteSeguroIvro());

                Fisica personaMig = consultaMigrado.buscaBeneficiarioSeguro(seguro);

                try {
                    mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica fisica = personaFisicaServiceBusiness
                            .localizarPersonaFisicaPorNss(personaMig.getNss());
                    personaMig.setNombre(generaNombre(fisica.getNombre(),
                            fisica.getPrimerApellido(),
                            fisica.getSegundoApellido()));
                } catch (Exception e) {
                    LOGGER.warn("No se encontro nombre del empleado");
                }

                seguro.getTramite().setBeneficiarios(new Fisica[]{personaMig});
            }
            segurosPersona.add(seguro);
        }
        if (segurosPersona.size() > 0) {
            SeguroIvro seguro = seguros.get(0);
            try {
                Boolean renovacion = vigenciaIvroServiceLocal
                        .isPeriodoRenovacionSSF(seguro);
                seguro.setEnRenovacion(renovacion);
                if (renovacion) {
                    Boolean extemporanea = vigenciaIvroServiceLocal
                            .isRenovacionExtemporaneaSSF(seguro);
                    seguro.setExtemporanea(extemporanea);
                }
                if (!(seguro.getEstadoSeguro().getIdEstadoSeguro() == EstadoSeguroIvroEnum.ACTIVO
                        .getId()
                        || seguro.getEstadoSeguro().getIdEstadoSeguro() == PENDIENTE_DE_PAGO
                        || renovacion)) {
                    segurosPersona = new ArrayList<SeguroIvro>();
                }
            } catch (IvroException e) {
                LOGGER.warn("Error al verificar si es renovacion ", e);
            }
        }
        return convierteDeLista(segurosPersona);
    }

    /**
     * Busca todos los seguros de Continuación Voluntaria asociados a un
     * solicitante
     *
     * @param persona - con el idPersona setteado
     * @return los seguros familiares encontrados
     */
    @Override
    public SegurosIvro buscaSegurosCVRO(Persona persona) {
        List<SeguroIvro> seguros = consultaSeguroIvroLocal.buscaSegurosCVRO(persona);
        List<SeguroIvro> nuevos = new ArrayList<SeguroIvro>();

        for (SeguroIvro seguro : seguros) {
            if (seguro.getTramite() == null) {
                /*
				 * En caso de que el seguro sea migrado, no se tiene trámite asociado,
				 * por lo tanto, se tiene que buscar los beneficiarios a mano
                 */
                seguro.setTramite(new TramiteSeguroIvro());

                Fisica personaMig = consultaMigrado.buscaBeneficiarioSeguro(seguro);

                try {
                    mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica fisica = personaFisicaServiceBusiness
                            .localizarPersonaFisicaPorNss(personaMig.getNss());
                    personaMig.setNombre(generaNombre(fisica.getNombre(),
                            fisica.getPrimerApellido(),
                            fisica.getSegundoApellido()));
                } catch (Exception e) {
                    LOGGER.warn("No se encontro nombre del empleado");
                }

                seguro.getTramite().setBeneficiarios(new Fisica[]{personaMig});
            }
            nuevos.add(seguro);
        }

        return convierteDeLista(nuevos);
    }

    /**
     * Busca el seguro por su identificador
     *
     * @param seguro el seguro a buscar
     * @return el seguro encontrado
     */
    public SeguroIvro buscaSeguro(SeguroIvro seguro) {
        return consultaSeguroIvroLocal.buscaSeguroPorIdValidaPago(seguro.getCveIdSeguroIvro());
    }

    /**
     * Busca el seguro por su identificador
     *
     * @param seguro el seguro a buscar
     * @return el seguro encontrado
     */
    public SeguroIvro buscaSeguroDetalle(SeguroIvro seguro) {
		
    	try {
    		
        	LOGGER.info("va a entrar en la validacion del detalle Tramite");
    		consultaSeguroIvroLocal.corrigeDetalleTramitePorSeguroIndividual(seguro.getCveIdSeguroIvro());
    		LOGGER.info("Termina correctamente con el ditDetalleTramite actualizado.");
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al corregir el tramite: "+e.getMessage());
			//e.printStackTrace();
		}
    	
        return consultaSeguroIvroLocal.buscaSeguroPorId(seguro.getCveIdSeguroIvro());
    }

    @Override
    public SeguroIvro buscaSeguroConEmailNotificable(SeguroIvro seguro) {
        SeguroIvro seguroIvro = consultaSeguroIvroLocal.buscaSeguroPorId(seguro.getCveIdSeguroIvro());

        String correo = enviaCorreoEntity.obtenCorreo(seguroIvro.getTitular());
        MedioContacto medioContacto = new MedioContacto();
        medioContacto.setDesFormaContacto(correo);
        seguroIvro.getTitular().setMediosContacto(new MedioContacto[]{medioContacto});

        return seguroIvro;
    }

    /**
     * Genera un objeto SegurosIvro a partir de la lista de seguros
     *
     * @param seguros la lista de seguros encontrados
     * @return regresa el objeto de SegurosIvro
     */
    private SegurosIvro convierteDeLista(List<SeguroIvro> seguros) {
        SegurosIvro respuesta = new SegurosIvro();
        if (seguros != null && !seguros.isEmpty()) {
            respuesta.setSeguroIvro(seguros.toArray(new SeguroIvro[seguros.size()]));
        }
        return respuesta;
    }

    /**
     * Concatena los nombres de una persona,
     *
     * @param nombre el nombre de la persona
     * @param primApellido el primer apellido de la persona
     * @param segApellido el segundo apellido de la persona
     * @return el nombre concatenado (Nombre PrimerApellido SegundoApellido)
     */
    private String generaNombre(String nombre, String primApellido, String segApellido) {
        StringBuilder nombreCompleto = new StringBuilder(StringUtils.trimToEmpty(nombre));
        nombreCompleto.append(" ").append(StringUtils.trimToEmpty(primApellido)).append(" ")
                .append(StringUtils.trimToEmpty(segApellido));
        return nombreCompleto.toString();
    }

    /**
     * Busca los seguros que se les debe generar un nuevo pago del mes (LC)
     * @return Lista de id's de los seguros para generarles la nueva LC
     */
    @Override
    public List<Long> buscaSegurosCvroLCAutomatica(){
        return this.consultaSeguroIvroLocal.buscaSegurosCvroLCAutomatica();
    }

    /**
     * Busca los seguros que se les debe generar la baja mensual cvro, validando que su último pago haya sido cubierto
     * @return Lista de id's de los seguros para generarles la baja mensual
     */
    @Override
    public List<Long> buscaSegurosBajaMensualCvro(){
        return this.consultaSeguroIvroLocal.buscaSegurosBajaMensualCvro();
    }

    /**
     * Busca los seguros cvro que se darán de baja por mora debido a que ya aplican sus 2 últimos pagos como vencidos
     * @return Lista de id's de los seguros para actualizarlos a baja por mora
     */
    @Override
    public List<Long> buscaSegurosBajaPorMora(){
        return this.consultaSeguroIvroLocal.buscaSegurosBajaPorMora();
    }

    @Override
    public SegurosIvro buscaSegurosCvroLineaCapturaAutomatica() {

        List<SeguroIvro> segurosList = this.consultaSeguroIvroLocal.buscaSegurosCvroLineaCapturaAutomatica();

        //Le agregamos al titular los correos a los que se notificará
        String correo = "";
        for (SeguroIvro seg : segurosList) {
            correo = enviaCorreoEntity.obtenCorreo(seg.getTitular());
            MedioContacto medioContacto = new MedioContacto();
            medioContacto.setDesFormaContacto(correo);
            seg.getTitular().setMediosContacto(new MedioContacto[]{medioContacto});
        }

        SegurosIvro seguros = new SegurosIvro();
        seguros.setSeguroIvro(segurosList.toArray(new SeguroIvro[segurosList.size()]));

        LOGGER.debug("Se encontraron " + seguros.getSeguroIvro().length
                + " seguros CVRO para renovar");

        return seguros;
    }

    @Override
    public SegurosIvro buscaSegurosActivosMod40() {

        //Se inhibe la búsqueda de seguros para el proceso Bach
        //List<SeguroIvro> segurosList = this.consultaSeguroIvroLocal.buscaSegurosActivosMod40();
        List<SeguroIvro> segurosList = new ArrayList<SeguroIvro>();

        SegurosIvro seguros = new SegurosIvro();
        seguros.setSeguroIvro(segurosList.toArray(new SeguroIvro[segurosList.size()]));

        LOGGER.debug("Se encontraron " + seguros.getSeguroIvro().length
                + " seguros CVRO activos");

        return seguros;
    }

    public Pago buscaUltimoPagoSeguro(long idSeguro) throws SUAException {

        LOGGER.debug("Se va a buscar el ultimo pago del seguro " + idSeguro);

        Pago pago = this.consultaSeguroIvroLocal.buscaUltimoPagoSeguro(idSeguro);

        LOGGER.debug("El ultimo pago del seguro "
                + idSeguro
                + " es "
                + ToStringBuilder.reflectionToString(pago,
                        ToStringStyle.MULTI_LINE_STYLE));

        return pago;
    }

    @Override
    public TramiteSeguroIvro buscaTramiteSeguroIndividual(SeguroIvro seguro) {
        return consultaSeguroIvroLocal.buscaTramiteSeguroIndividual(seguro.getCveIdSeguroIvro());
    }

    @Override
    public void guardarYAsociarDomiciliosPersona(Domicilio domicilio, Long idPersona) throws DomicilioNoValidoException, DomicilioNoLocalizadoException {
        mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica persona = new mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica(idPersona);
        domicilioServiceBusinessRemote.validarDatosDomicilioRecortado(domicilio);
        mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioDelta = domicilioServiceBusinessRemote.complementarLocalidadDomicilioDigRecortado(domicilio);
        persona.setDomicilios(new ArrayList<mx.gob.imss.ctirss.delta.model.domicilio.Domicilio>());
        persona.getDomicilios().add(domicilioDelta);
        componentesExternosBusiness.guardarYAsociarDomiciliosPersona(persona);
    }

    @Override
    public List<Long> buscaSegurosPorConcluir() {
        return consultaSeguroIvroLocal.buscaSegurosPorConcluir();
    }

}
