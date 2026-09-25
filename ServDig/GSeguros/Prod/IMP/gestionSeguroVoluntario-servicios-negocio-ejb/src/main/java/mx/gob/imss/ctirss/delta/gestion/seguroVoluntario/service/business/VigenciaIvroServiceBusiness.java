/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.VigenciaIvroServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.VigenciaIvroServiceRemote;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.seguros.PeriodoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

import mx.gob.imss.digital.modelo.seguros.AsignacionNssIvro;
import javax.persistence.NoResultException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaLocal;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;

/**
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "vigenciaIvroServiceBusiness", mappedName = "vigenciaIvroServiceBusiness")
public class VigenciaIvroServiceBusiness implements VigenciaIvroServiceRemote {

    @EJB
    private VigenciaIvroServiceLocal vigenciaIvroServiceLocal;
    
    @EJB
    private ValidaVigenciaLocal validaVigenciaLocal;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * VigenciaIvroServiceRemote
     * #obtenPeriodoSeguroIndividual(mx.gob.imss.digital.modelo.persona.Fisica)
     */
    @Override
    public PeriodoSeguro obtenPeriodoSeguroIndividual(Fisica persona) throws IvroException {
        return vigenciaIvroServiceLocal.obtenPeriodoSeguroIndividual(persona);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * VigenciaIvroServiceRemote
     * #obtenPeriodoSeguroDomestico(mx.gob.imss.digital.modelo.persona.Persona)
     */
    @Override
    public PeriodoSeguro obtenPeriodoSeguroDomestico(Fisica persona) throws IvroException {
        return vigenciaIvroServiceLocal.obtenPeriodoSeguroDomestico(persona);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * VigenciaIvroServiceRemote
     * #obtenPeriodoRenovacionDomestico(mx.gob.imss.digital
     * .modelo.seguros.SeguroIvro)
     */
    @Override
    public PeriodoSeguro obtenPeriodoRenovacionDomestico(SeguroIvro seguro) throws IvroException {
        return vigenciaIvroServiceLocal.obtenPeriodoRenovacionDomestico(seguro);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * VigenciaIvroServiceRemote
     * #isPeriodoRenovacion(mx.gob.imss.digital.modelo.persona.Persona)
     */
    @Override
    public Boolean isPeriodoRenovacion(SeguroIvro seguro) throws IvroException {
        return vigenciaIvroServiceLocal.isPeriodoRenovacion(seguro);
    }
    
    @Override
    public AsignacionNssIvro obtenerAsignacionNss(Long idPersona) throws NoResultException , IvroException {
        return vigenciaIvroServiceLocal.obtenerAsignacionNss(idPersona);
    }
    
    @Override
    public AsignacionNssIvro obtenerAsignacionNss(String numNss) throws NoResultException , IvroException {
        return vigenciaIvroServiceLocal.obtenerAsignacionNss(numNss);
    }

    @Override
    public RespuestaValidacionTrabajador validaVigenciaSeguroFamiliarRenovacion(VigenciaSeguroFamiliar vigenciaSeguroFamiliar) {
        return validaVigenciaLocal.validaVigenciaSeguroFamiliarRenovacion(vigenciaSeguroFamiliar);
    }

    @Override
    public Date obtenerFechaFinRenovacionIvro(SeguroIvro seguro) {
        return vigenciaIvroServiceLocal.obtenerFechaFinRenovacionIvro(seguro);
    }
    
    
    @Override
    public RespuestaValidacionTrabajador validaVigenciaTrabajadorDomesticoRenovacion(
            VigenciaTrabajdor vigenciaTrabajador) {
        return validaVigenciaLocal.validaVigenciaTrabajadorDomesticoRenovacion(vigenciaTrabajador);
    }

    
}
