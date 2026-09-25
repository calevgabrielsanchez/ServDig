package mx.gob.imss.csdiss.sdroc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.dto.ObjetoContratoDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocObjetoContrato;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.ObjetoContratoDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.ObjetoContratoService;


/**
 * 
 * Clase que implementa la interface ParametroService que permite obtener los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Service
@Transactional
public class ObjetoContratoServiceImpl implements ObjetoContratoService  {
	
	private static final Logger log = LoggerFactory.getLogger(ObjetoContratoServiceImpl.class);
	
	@Autowired
	ObjetoContratoDao objetoContratoDao;


	@Override
	public List<ObjetoContratoDTO> consultarTodosObjetosContrato() throws BusinessException {
		
		List<ObjetoContratoDTO> listaObjetosContratoDTO = new ArrayList<ObjetoContratoDTO>();
		List<RocObjetoContrato> listaRocObjetosContrato = objetoContratoDao.findAll();

		final Long CONTRATO_MANO_OBRA = 1L;

		for (RocObjetoContrato rocObjetoContrato : listaRocObjetosContrato) {

			if(rocObjetoContrato.getCveObjetoContrato() != CONTRATO_MANO_OBRA ){
				listaObjetosContratoDTO.add(rocObjetoContrato.toConvertObjetoContratoDTO());
			}
		}
		Collections.sort(listaObjetosContratoDTO);
		return listaObjetosContratoDTO;
	}
	


}
