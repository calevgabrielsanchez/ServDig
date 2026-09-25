package mx.gob.imss.csdiss.sdroc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.dto.TipoRegistroDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoRegistro;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoRegistroDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoRegistroService;


/**
 * 
 * Clase que implementa la interface ParametroService que permite obtener los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Service
@Transactional
public class TipoRegistroServiceImpl implements TipoRegistroService  {
	
	private static final Logger log = LoggerFactory.getLogger(TipoRegistroServiceImpl.class);
	
	@Autowired
	TipoRegistroDao tipoRegistroDao;



	@Override
	public List<TipoRegistroDTO> consultarTodosTiposRegistros() throws BusinessException {
		List<TipoRegistroDTO> listaTiposDeRegistrosDTO = new ArrayList<TipoRegistroDTO>();
		List<RocTipoRegistro> listaTiposRegistros = tipoRegistroDao.findAll();
		for (RocTipoRegistro rocTipoRegistro : listaTiposRegistros) {
			listaTiposDeRegistrosDTO.add(rocTipoRegistro.toConvertTipoRegistroDTO());
		}
		return listaTiposDeRegistrosDTO;
	}
	


}
