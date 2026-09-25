/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Division;
import mx.gob.imss.ctirss.clasificador.repository.DivisionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucio
 *
 */
@Service
public class DivisionServicePOJOImpl implements DivisionService {

	
	
	
	private DivisionRepository divisioRespository;
	
	
	
	
	

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.service.DivisionService#cargarDivisionesActivas()
	 */
	public List<Division> cargarDivisionesActivas() {
		return this.divisioRespository.cargarDivisionesActivas();
	}









	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.service.DivisionService#cargarDivisionesInactivas()
	 */
	public List<Division> cargarDivisionesInactivas() {
		return this.divisioRespository.cargarDivisionesInactivas();
	}
	
	
	
	/**
	 * @param divisioRespository the divisioRespository to set
	 */
	@Autowired
	public void setDivisioRespository(DivisionRepository divisioRespository) {
		this.divisioRespository = divisioRespository;
	}









	
	
	
	
}
