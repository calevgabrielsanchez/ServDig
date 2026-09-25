/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;


import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaPatronPlataformaLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.PatronPlataformasDigitalesUtil;
import mx.gob.imss.ctirss.delta.model.gestion.seguro.PatronPlataformasDigitales;
import mx.gob.imss.ctirss.delta.persistence.PptPatronPlataforma;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.VigenciaContVoluntaria;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para validar las vigencias de una person, si se encuentra activa y con 
 * que modalidades se encuentra
 * @author NOVUTECK1
 *
 */
@Stateless(name = "validaVigenciaBusinesss", mappedName = "validaVigenciaBusinesss")
public class ValidaVigenciaBusinesss implements ValidaVigenciaRemote {
    
	/**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidaVigenciaBusinesss.class);
	
	/**
     * Servicio local de plataformas digitales
     */
    @EJB
    private ConsultaPatronPlataformaLocal consultaPatronPlataformaLocal;
	
    /**
     * Servicio local de validacion de vigencias
     */
    @EJB
    private ValidaVigenciaLocal validaVigenciaLocal;

    @Override
    public RespuestaValidacionTrabajador validaVigenciaTrabajador(
            VigenciaTrabajdor vigenciaTrabajador) {
        return validaVigenciaLocal.validaVigenciaTrabajador(vigenciaTrabajador);
    }

    @Override
    public RespuestaValidacionTrabajador validaContinuarVigenciaTrabajador(VigenciaTrabajdor vigenciaTrabajador) {
        return validaVigenciaLocal.validaContinuarVigenciaTrabajador(vigenciaTrabajador);
    }
    
    @Override
    public RespuestaValidacionTrabajador validaVigenciaTrabajadorRenovacion(
            VigenciaTrabajdor vigenciaTrabajador) {
        return validaVigenciaLocal.validaVigenciaTrabajadorRenovacion(vigenciaTrabajador);
    }
    
    @Override
    public RespuestaValidacionTrabajador validaVigenciaTrabajadorDomesticoRenovacion(
            VigenciaTrabajdor vigenciaTrabajador) {
        return validaVigenciaLocal.validaVigenciaTrabajadorDomesticoRenovacion(vigenciaTrabajador);
    }

	@Override
	public RespuestaValidacionTrabajador validaVigenciaSeguroFamiliar(
			VigenciaSeguroFamiliar vigenciaSeguroFamiliar) {
		return validaVigenciaLocal.validaVigenciaSeguroFamiliar(vigenciaSeguroFamiliar);
	}
  @Override
	public RespuestaValidacionTrabajador validaVigenciaSeguroFamiliarRenovacion(
			VigenciaSeguroFamiliar vigenciaSeguroFamiliar) {
		return validaVigenciaLocal.validaVigenciaSeguroFamiliarRenovacion(vigenciaSeguroFamiliar);
	}

	@Override
	public RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntaria(
			VigenciaContVoluntaria vigenciaContVoluntaria) {
		return validaVigenciaLocal.validaVigenciaContinuacionVoluntaria(vigenciaContVoluntaria);
	}
  @Override
	public RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntariaRenovacion(
			VigenciaContVoluntaria vigenciaContVoluntaria) {
	  
	  		List<PptPatronPlataforma> listPatronesPlataformasDig = new ArrayList<PptPatronPlataforma>();
	  
	  		try {
	  			listPatronesPlataformasDig = consultaPatronPlataformaLocal.getPttPatronPlataformaActivos();
	  			
	  			if (listPatronesPlataformasDig!= null && listPatronesPlataformasDig.size()>0) {
	  				LOGGER.debug("SI recupera la lista de patrones: "+listPatronesPlataformasDig.size());
	  			}
			} catch (IvroException e) {
				LOGGER.error("ERROR al consultar la lista de patrones: "+e.getMessage());
				e.printStackTrace();
			}
	  
		return validaVigenciaLocal.validaVigenciaContinuacionVoluntariaRenovacion(listPatronesPlataformasDig,vigenciaContVoluntaria);
	}

    /*
     * (non-Javadoc)
     *
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaRemote
     * #revisaCancelaSeguroCambiadoMod40(mx.gob.imss.digital.modelo.sindo.VigenciaContVoluntaria)
     */
    @Override
    public Boolean revisaCancelaSeguroCambiadoMod40(VigenciaContVoluntaria vigenciaContVoluntaria) {
        return validaVigenciaLocal.revisaCancelaSeguroCambiadoMod40(vigenciaContVoluntaria);
    }

    
	@Override
	public List<PatronPlataformasDigitales> consultaPatronesPlataformasDigitales() {
		
		List<PatronPlataformasDigitales> listPatronesPlataformasDig = new ArrayList<PatronPlataformasDigitales>();
		  
  		try {
  			List<PptPatronPlataforma> listPatronesActivos = consultaPatronPlataformaLocal.getPttPatronPlataformaActivos();
  			
  			if (listPatronesActivos!= null && listPatronesActivos.size()>0) {
  				LOGGER.info("Recupera la lista de patrones: "+listPatronesActivos.size());
  				for(PptPatronPlataforma elemento : listPatronesActivos){
  					listPatronesPlataformasDig.add(PatronPlataformasDigitalesUtil.transformaPatronPlataformaDigToModel(elemento));
  				}
  			}
		} catch (IvroException e) {
			
			LOGGER.error("ERROR al consultar la lista de patrones: "+e.getMessage());
			e.printStackTrace();
		}
		
		return listPatronesPlataformasDig;
	}
    
}
