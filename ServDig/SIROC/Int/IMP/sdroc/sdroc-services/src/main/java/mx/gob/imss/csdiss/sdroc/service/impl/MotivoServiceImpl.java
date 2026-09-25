package mx.gob.imss.csdiss.sdroc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.dto.MotivoDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocMotivo;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.MotivoDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.MotivoService;


/**
 * 
 * Clase que implementa la interface ParametroService que permite obtener los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Service
@Transactional
public class MotivoServiceImpl implements MotivoService  {
	
	private static final Logger log = LoggerFactory.getLogger(MotivoServiceImpl.class);
	
	@Autowired
	MotivoDao motivoDao;

	@Override
	public List<MotivoDTO> consultarMotivosPorTipoIncidencia(Long cveTipoIncidencia) throws BusinessException {
		
		List<MotivoDTO> listaMotivosDTO = new ArrayList<MotivoDTO>();
		
		List<RocMotivo> listaRocMotivos = motivoDao.findByTipoIncidencia(cveTipoIncidencia);
		for (RocMotivo rocMotivo : listaRocMotivos) {
			listaMotivosDTO.add(rocMotivo.toConvertMotivoDTO());
		}
		
		return listaMotivosDTO;
		
	}

	


}
