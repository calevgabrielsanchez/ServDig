package mx.gob.imss.csdiss.sdroc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.dto.TipoPersonaDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoPersona;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoPersonaDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoPersonaService;


/**
 * 
 * Clase que implementa la interface ParametroService que permite obtener los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Service
@Transactional
public class TipoPatronServiceImpl implements TipoPersonaService  {
	
	private static final Logger log = LoggerFactory.getLogger(TipoPatronServiceImpl.class);
	
	@Autowired
	TipoPersonaDao tipoPersonaDao;


	@Override
	public List<TipoPersonaDTO> consultarTodosTiposPersonas() throws BusinessException {
		List<TipoPersonaDTO> listaTiposDePersonasDTO = new ArrayList<TipoPersonaDTO>();
		List<RocTipoPersona> listaTiposPersonas = tipoPersonaDao.findAll();
		for (RocTipoPersona rocTipoPersona : listaTiposPersonas) {
			listaTiposDePersonasDTO.add(rocTipoPersona.toConvertTipoPersonaDTO());
		}
	
		return listaTiposDePersonasDTO;
	}
	


}
