/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.imss.ctirss.clasificador.model.business.Grupo;
import mx.gob.imss.ctirss.clasificador.repository.GrupoRepository;

/**
 * @author lucio
 *
 */
@Service
public class GrupoServicePOJOImpl implements GrupoService {
	
	
	
	
	private GrupoRepository grupoRepository;

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.clasificador.service.GrupoService#cargarGruposPorDivision(int)
	 */
	public List<Grupo> cargarGruposPorDivision(int cveDivision) {
		// TODO Auto-generated method stub
		return this.grupoRepository.cargarGruposPorDivision(cveDivision);
	}

	/**
	 * @param grupoRepository the grupoRepository to set
	 */
	@Autowired
	public void setGrupoRepository(GrupoRepository grupoRepository) {
		this.grupoRepository = grupoRepository;
	}

}
