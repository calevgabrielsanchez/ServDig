package mx.gob.imss.csdiss.sdroc.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.dto.TipoObraDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocTipoObra;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoObraDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.TipoObraService;


/**
 * 
 * Clase que implementa la interface ParametroService que permite obtener los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Service
@Transactional
public class TipoObraServiceImpl implements TipoObraService  {
	
	private static final Logger log = LoggerFactory.getLogger(TipoObraServiceImpl.class);
	
	@Autowired(required=true)
	TipoObraDao tipoObraDao;

	@Override
	public List<TipoObraDTO> consultarTiposDeObras() throws BusinessException {
		
		
		List<TipoObraDTO> listaTiposDeObrasDTO = new ArrayList<TipoObraDTO>();
		List<RocTipoObra> listaTiposDeObras =  tipoObraDao.findAll();	
		for (RocTipoObra rocTipoObra : listaTiposDeObras) {
			listaTiposDeObrasDTO.add(rocTipoObra.toConvertTipoObraDTO());
		}
		
		return listaTiposDeObrasDTO;
	}

	@Override
	public List<TipoObraDTO> consultarTiposDeObrasPorClasificacionObra(Long cveClasificacionObra) throws BusinessException {
		List<TipoObraDTO> listaTiposDeObrasDTO = new ArrayList<TipoObraDTO>();
		List<RocTipoObra> listaTiposDeObras =  tipoObraDao.findByCveClasificacionObra(cveClasificacionObra);	
		for (RocTipoObra rocTipoObra : listaTiposDeObras) {
			listaTiposDeObrasDTO.add(rocTipoObra.toConvertTipoObraDTO());
		}
		
		return listaTiposDeObrasDTO;	}
	


}
