package mx.gob.imss.csdiss.sdroc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.dto.DelegacionDTO;
import mx.gob.imss.csdiss.sdroc.dto.SubDelegacionDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocSubdelegacion;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.SubdelegacionDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.SubdelegacionService;

/**
 * 
 * Clase que implementa la interface ParametroService que permite obtener los
 * parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Service
@Transactional
public class SubdelegacionServiceImpl implements SubdelegacionService {

	private static final Logger log = LoggerFactory.getLogger(SubdelegacionServiceImpl.class);

	@Autowired
	SubdelegacionDao subdelegacionDao;

	@Override
	public SubDelegacionDTO consultarSubdelegacionPorCveCodigo(Long cveCodigo) throws BusinessException {
		SubDelegacionDTO subDelegacionDTO = null;
		RocSubdelegacion rocSubdelegacion = null;
		rocSubdelegacion = subdelegacionDao.findByCveCodigoSubdelegacion(cveCodigo);
		if (rocSubdelegacion != null)
			subDelegacionDTO = rocSubdelegacion.toConvertSubdelegacionDTO();
		return subDelegacionDTO;
	}

	@Override
	public List<SubDelegacionDTO> consultarTodasSubdelegaciones() throws BusinessException {

		List<SubDelegacionDTO> listaSubdelegaciones = new ArrayList<SubDelegacionDTO>();
		List<RocSubdelegacion> listaRocSubdelegacion = null;
		listaRocSubdelegacion = subdelegacionDao.findAll();
		if (listaRocSubdelegacion != null) {
			for (RocSubdelegacion rocSubdelegacion : listaRocSubdelegacion) {
				listaSubdelegaciones.add(rocSubdelegacion.toConvertSubdelegacionDTO());
			}
		}

		return listaSubdelegaciones;
	}

	@Override
	public List<SubDelegacionDTO> consultarSubdelegacionesImssByCp(String cveCodigoPostal) throws BusinessException {

		List<SubDelegacionDTO> listaSubdelegaciones = new ArrayList<SubDelegacionDTO>();
		List<Object[]> listaObjetosResultado = null;

		listaObjetosResultado = subdelegacionDao.findSubdelegacionImssByCp(cveCodigoPostal);
		
		if (listaObjetosResultado != null) {
			Object[] objetoResultado = listaObjetosResultado.get(0);
			System.out.println("La clave de la delegacion es : " + objetoResultado[0] + " y el nombre es " + objetoResultado[1]);
			SubDelegacionDTO subDelegacionDTO = new SubDelegacionDTO();
			subDelegacionDTO.setCveSubdelegacion(((Long) objetoResultado[0]));
			subDelegacionDTO.setNomSubdelegacion((String) objetoResultado[1]);
			subDelegacionDTO.setDelegacionDTO(new DelegacionDTO((Long) objetoResultado[2], (String) objetoResultado[3]));
			
			listaSubdelegaciones.add(subDelegacionDTO);
		}

		return listaSubdelegaciones;
	}

}
