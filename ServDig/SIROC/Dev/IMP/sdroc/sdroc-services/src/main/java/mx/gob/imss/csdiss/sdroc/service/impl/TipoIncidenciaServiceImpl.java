package mx.gob.imss.csdiss.sdroc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.dto.TipoIncidenciaDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoIncidencia;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoIncidenciaDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoIncidenciaService;


/**
 * 
 * Clase que implementa la interface ParametroService que permite obtener los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Service
@Transactional
public class TipoIncidenciaServiceImpl implements TipoIncidenciaService  {
	
	private static final Logger log = LoggerFactory.getLogger(TipoIncidenciaServiceImpl.class);
	
	@Autowired
	TipoIncidenciaDao tipoIncidenciaDao;


	@Override
	public List<TipoIncidenciaDTO> consultarTodosTiposIncidencias() throws BusinessException {
		List<TipoIncidenciaDTO> listaTiposDeIncidenciasDTO = new ArrayList<TipoIncidenciaDTO>();
		List<RocTipoIncidencia> listaTiposDeObras =  tipoIncidenciaDao.findAll();	
		for (RocTipoIncidencia rocTipoIncidencia : listaTiposDeObras) {
			listaTiposDeIncidenciasDTO.add(rocTipoIncidencia.toConvertTipoIncidenciaDTO());
		}
		
		return listaTiposDeIncidenciasDTO;
	}
	


}
