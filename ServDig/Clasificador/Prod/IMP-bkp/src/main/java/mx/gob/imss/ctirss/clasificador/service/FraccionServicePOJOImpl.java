/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;
import mx.gob.imss.ctirss.clasificador.repository.FraccionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucio
 *
 */
@Service
public class FraccionServicePOJOImpl implements FraccionService {
	
	
	
	
	private FraccionRepository fraccionRepository;

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.service.FraccionService#obtenerFraccionesActivasPorGrupo(int)
	 */
	public List<Fraccion> obtenerFraccionesActivasPorGrupo(int cveGrupo, String sSearch  , int iDisplayLength , int iDisplayStart ) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param fraccionRepository the fraccionRepository to set
	 */
	@Autowired
	public void setFraccionRepository(FraccionRepository fraccionRepository) {
		this.fraccionRepository = fraccionRepository;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.service.FraccionService#obtenerFraccionPorClave(int)
	 */
	public Fraccion obtenerFraccionPorClave(String desFraccion) {
		
		return this.fraccionRepository.obtenerFraccionPorClave(desFraccion);
	}

}
