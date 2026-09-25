package mx.gob.imss.csdiss.sdroc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.dto.TipoPatronDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoPatron;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoPatronDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoPatronService;


/**
 * 
 * Clase que implementa la interface ParametroService que permite obtener los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Service
@Transactional
public class TipoPersonaServiceImpl implements TipoPatronService  {
	
	private static final Logger log = LoggerFactory.getLogger(TipoPersonaServiceImpl.class);
	
	@Autowired
	TipoPatronDao tipoPatronDao;


	@Override
	public List<TipoPatronDTO> consultarTodosTiposPatrones() throws BusinessException {
		List<TipoPatronDTO> listaTiposDePatronesDTO = new ArrayList<TipoPatronDTO>();
		List<RocTipoPatron>  listaTiposPatron = tipoPatronDao.findAll();
		for (RocTipoPatron rocTipoPatron : listaTiposPatron) {
			listaTiposDePatronesDTO.add(rocTipoPatron.toConvertTipoPatronDTO());
		}
		
		return listaTiposDePatronesDTO;
	}
	


}
