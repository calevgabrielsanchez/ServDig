/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.MotorBeneficiosBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servico para obtener los beneficio de una persona.
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "motorBeneficiosBusinessEntity", mappedName = "motorBeneficiosBusinessEntity")
public class MotorBeneficiosBusinessEntity implements MotorBeneficiosBusinessLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MotorBeneficiosBusinessEntity.class);
    /**
     * Lista de modalidades que aplican beneficios del patron a sus trabajadores
     */
    private static final ModalidadEnum[] BENEFICIOS_EMPLEADOS = {ModalidadEnum.DIEZ,
            ModalidadEnum.TRECE};
    /**
     * Lista de modalidades que aplican dbeneficios directamente como auto
     * asegurados para la ModalidadEnum.TREINTAYCUATRO, 
     * no de be aplicar la consulta de beneficio
     */
    private static final ModalidadEnum[] BENEFICIOS_AUTO_ASEGURADOS = {
            ModalidadEnum.TREINTAYCINCO,  ModalidadEnum.CUARENTAYTRES,
            ModalidadEnum.CUARENTAYCUATRO};
    /**
     * Servicio para la busqueda de beneficios
     */
    @EJB(name = "beneficioRissServiceBusiness", mappedName = "beneficioRissServiceBusiness")
    private BeneficioRissServiceBusinessRemote beneficioRissServiceBusinessRemote;

    /**
     * Busca el beneficio asociado a un trabajador si aplca por su modaliad
     * 
     * @param valores  los datos del trabajador y patron para consultar el beneficio
     * @return EL beneficio que le corresponde
     */    
    public Beneficio buscarBeneficioTrabajador(ValoresCalculoEmpleado valores) {
        long modalidad = valores.getModalidad();
        Date inicio = valores.getFechaInicioCalculo().getTime();
        Date fin = valores.getFechaFinCalculo().getTime();
        Beneficio beneficio = null;
        ModalidadEnum modalidadEnum = ModalidadEnum.fromId(modalidad);
        if (ArrayUtils.contains(BENEFICIOS_EMPLEADOS, modalidadEnum)) {
            String registroPatronal = valores.getNumeroRegistroPatronal();
            LOGGER.debug("calcularDatosCutoa modalidad: {}, NRP: {}", modalidad, registroPatronal);
            beneficio = obtenerBeneficioPorNRP(registroPatronal, inicio, fin);
        } else if (ArrayUtils.contains(BENEFICIOS_AUTO_ASEGURADOS, modalidadEnum)) {
            String nss = valores.getEmpleado().getNumeroSeguridadSocial();
            LOGGER.debug("calcularDatosCutoa modalidad: {}, NSS: {}", modalidad, nss);
            beneficio = obtenerBeneficioPorNSS(nss, inicio, fin);
        }

        return beneficio;
    }

    /**
     * MEtodo utilitario para consultar un beneficio por registro patronal,
     * regresando null si no se encuentra
     * 
     * @param registroPatronal numero de identificacion para un patron
     * @param inicio fecha de inicio para la consulta de beneficios
     * @param fin fecha final de la consulta de beneficio
     * @return El benefico encontrado
     */
    private Beneficio obtenerBeneficioPorNRP(String registroPatronal, Date inicio, Date fin) {
        try {
            return beneficioRissServiceBusinessRemote.obtenerBeneficioPorNRP(registroPatronal,
                    inicio, fin);
        } catch (BeneficioRissException e) {
            LOGGER.info("No se encontro beneficio para el NPR {} con el mensaje {}",
                    registroPatronal, e.getMessage());
        }
        return null;
    }

    /**
     * Busca el beneficio en un periodo de tiempo por un nss regresando null si
     * no lo encuentra
     * 
     * @param nss NUmero de seguridad social del trabajador
     * @param inicio fecha de inicio para la busqueda de beneficios
     * @param fin fecha final de la busqueda de beneficios
     * @return el beneficio encontrado
     */
    private Beneficio obtenerBeneficioPorNSS(String nss, Date inicio, Date fin) {
        try {
        	Beneficio beneficio = beneficioRissServiceBusinessRemote.obtenerBeneficioPorNSS(nss, inicio, fin);
            if(beneficio!=null && beneficio.getEstadoBeneficio()!=null 
            		&& beneficio.getEstadoBeneficio().getIdEstadoBeneficio()!=null &&
            		beneficio.getEstadoBeneficio().getIdEstadoBeneficio().equals(
            				EstadoBeneficioEnum.ACTIVO.getClave())){
            	return beneficio;
            }else {
            	LOGGER.info("No se encontro beneficio ACTIVO para el NSS: "+nss);
            	return null;
            }
        } catch (BeneficioRissException e) {
            LOGGER.info("No se encontro beneficio para el NSS {} con el mensaje {}", nss,
                    e.getMessage());
        }
        return null;
    }

}
