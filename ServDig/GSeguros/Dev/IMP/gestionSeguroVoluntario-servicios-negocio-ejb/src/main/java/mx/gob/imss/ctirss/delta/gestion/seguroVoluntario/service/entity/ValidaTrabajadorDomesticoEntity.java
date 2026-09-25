/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaTrabajadorDomesticoLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroFactory;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;

/**
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "validaTrabajadorDomesticoEntity", mappedName = "validaTrabajadorDomesticoEntity")
public class ValidaTrabajadorDomesticoEntity implements ValidaTrabajadorDomesticoLocal {

    /**
     * LOgger de la clase
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ValidaTrabajadorDomesticoEntity.class);
    /**
     * Servicio para la consulta de patrones
     */
    @EJB(name = "sujetoObligadoServiceBusiness", mappedName = "sujetoObligadoServiceBusiness")
    private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
    /**
     * Consulta de seguros
     */
    @EJB
    private ConsultaSeguroIvroLocal consultaSeguroIvro;

    private static final String MENSAJE = "El trabajador ya cuenta con un seguro contratado.";

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ValidaTrabajadorDomesticoLocal
     * #validaTrabajadorSeguroAnterior(java.lang.String, java.lang.String)
     */
    @Override
    public RespuestaValidacionTrabajador validaTrabajadorSeguroAnterior(Long idEmpleador, String nrp, String nss) {
        RespuestaValidacionTrabajador validacion = new RespuestaValidacionTrabajador();
        validacion.setValido(Boolean.TRUE);
        Long idPersona = idEmpleador!=null ? idEmpleador : getIdPersona(nrp);
        LOGGER.error("idEmpleador: "+idPersona);
        if (idPersona != null) {
            Persona persona = new Persona();
            persona.setIdPersona(idPersona);
            List<SeguroIvro> seguros = consultaSeguroIvro.buscaSegurosPersona(persona,
                    IvroFactory.generaModalidades(IvroFactory.MOD_DOMESTICO),
                    IvroFactory.estadosSeguro(true));
            for (SeguroIvro seguro : seguros) {
                if (!trabajadorValido(nss, seguro)) {
                    validacion.setValido(Boolean.FALSE);
                    validacion.setMensajeValidacion(MENSAJE);
                    break;
                }
            }
        }
        return validacion;
    }
    
    
    @Override
    public RespuestaValidacionTrabajador validaTrabajadorSeguroRenueva(Long idEmpleador, String nrp, String nss) {
        RespuestaValidacionTrabajador validacion = new RespuestaValidacionTrabajador();
        validacion.setValido(Boolean.TRUE);
        Long idPersona = idEmpleador!=null ? idEmpleador : getIdPersona(nrp);
        LOGGER.error("idEmpleador: "+idPersona);
        if (idPersona != null) {
            Persona persona = new Persona();
            persona.setIdPersona(idPersona);
            List<SeguroIvro> seguros = consultaSeguroIvro.buscaSegurosPersona(persona,
                    IvroFactory.generaModalidades(IvroFactory.MOD_DOMESTICO),
                    IvroFactory.estadosSeguro(true));
        }
        return validacion;
    }


    /**
     * Obtiene el id de persona a partir del nrp
     * 
     * @param nrp el numero de registro patronal
     * @return el identificador de la persona
     */
    private Long getIdPersona(String nrp) {
        Long idPersona = null;
        try {
            SujetoObligado patron = sujetoObligadoServiceBusiness
                    .consultarPorNumeroRegistroPatronal(nrp);
            idPersona = patron.getFisica().getIdPersona();
        } catch (Exception e) {
            LOGGER.error("No se encontraron los datos del titular");
        }

        return idPersona;
    }

    /**
     * VErifica si un trabajador domestico ya cuenta con un seguro contratado
     * @param nss el numero de seguridad del trabajador
     * @param seguro el seguro a validar
     * @return true si el trabajador puede contratar un seguro
     */
    private Boolean trabajadorValido(String nss, SeguroIvro seguro) {
        Boolean valido = Boolean.TRUE;
        if (seguro.getTramite() != null && seguro.getTramite().getBeneficiarios() != null) {
            Fisica trabajador = seguro.getTramite().getBeneficiarios()[0];
            // si el trabajador ya tiene un seguro verificamos que no este en
            // renovacion
            if (StringUtils.equals(nss, StringUtils.trimToEmpty(trabajador.getNss()))) {
                Calendar hoy = Calendar.getInstance();
                Calendar fechaIniRenov = Calendar.getInstance();
                Calendar fechaFinRenov = Calendar.getInstance();

                fechaIniRenov.setTime(seguro.getFechaFin());
                fechaFinRenov.setTime(seguro.getFechaFin());
                //El inicio de la renovación del seguro sería 1 mes antes del fin del seguro
                fechaIniRenov.add(Calendar.MONTH,-1);

                //Al final de periodo de renovacón le aumentamos un mes por si fuera extemporaneo
                fechaFinRenov.add(Calendar.MONTH,1);

                //Si hoy está entre el periodo de renovación del seguro del trabajador
                valido = hoy.after(fechaIniRenov) && hoy.before(fechaFinRenov);
            }
        }
        return valido;
    }
}
