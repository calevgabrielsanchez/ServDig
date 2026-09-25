package mx.gob.imss.csdiss.sdroc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.dto.AvisoObraDTO;
import mx.gob.imss.csdiss.sdroc.entity.RotAvisoObra;
import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.AvisoObraDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.AvisoObraService;
import mx.gob.imss.csdiss.sdroc.service.util.ConvertUtil;


/**
 * 
 * Clase que implementa la interface **** que permite obtener los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Service
@Transactional
public class AvisoObraServiceImpl implements AvisoObraService  {
	
	private static final Logger log = LoggerFactory.getLogger(AvisoObraServiceImpl.class);
	
	@Autowired
	AvisoObraDao avisoObraDao;

//	@Override
//	public AvisoObraDTO consultarAvisoObraPorCveAvisoObra(Long cveAvisoObra) throws BusinessException{
//		
//		AvisoObraDTO avisoObraDTO = null;
//		RotAvisoObra rotAvisoObra = null;
//		rotAvisoObra = avisoObraDao.findByID(cveAvisoObra);
//		if(rotAvisoObra != null)
//			avisoObraDTO = rotAvisoObra.toConvertAvisoObraDTO();
//		
//		return avisoObraDTO;
//	}
	
	@Override
	public AvisoObraDTO consultarAvisoObraPorCveAvisoObra(String cveAvisoObra) throws BusinessException{
		
		AvisoObraDTO avisoObraDTO = null;
		RotAvisoObra rotAvisoObra = null;
		
		rotAvisoObra = avisoObraDao.findByCveRegistroAvisoObra(cveAvisoObra);
		
		if(rotAvisoObra != null)
			avisoObraDTO = rotAvisoObra.toConvertAvisoObraDTO();
		
		return avisoObraDTO;
	}


	@Override
	public AvisoObraDTO insertarAvisoObra(AvisoObraDTO avisoObraDTO) throws BusinessException{

		RotAvisoObra rotAvisoObra = null;
		Long cveInformacionObra = null;
		String cveRegistroAvisoObra = avisoObraDao.getCveAvisoObra();
		
		rotAvisoObra = ConvertUtil.toConvertRotAvisoObra(avisoObraDTO);		
		rotAvisoObra.setCveRegistroAvisoObra(cveRegistroAvisoObra);
		cveInformacionObra = avisoObraDao.save(rotAvisoObra);
		
		avisoObraDTO.setCveAvisoObra(cveInformacionObra);
		avisoObraDTO.setCveRegistroAvisoObra(cveRegistroAvisoObra);
		return avisoObraDTO;
	}


	@Override
	public List<AvisoObraDTO> consultarAvisosObraPorCveRfc(String cveRfc) throws BusinessException {
		
		List<AvisoObraDTO> listaAvisoObrasDTO = new ArrayList<AvisoObraDTO>();
		List<RotAvisoObra> listaAvisoObras = null;
		
		listaAvisoObras = avisoObraDao.findByRfc(cveRfc);
		for (RotAvisoObra rotAvisoObra : listaAvisoObras) {
			listaAvisoObrasDTO.add(rotAvisoObra.toConvertAvisoObraDTO());
		}		
		
		return listaAvisoObrasDTO;
	}


	@Override
	public AvisoObraDTO consultarAvisoObraPorCveAvisoObra(String cveAvisoObra, String rfc, String registroPatronal)
			throws BusinessException {
		AvisoObraDTO avisoObraDTO = null;
		RotAvisoObra rotAvisoObra = null;
		
		rotAvisoObra = avisoObraDao.findByCveRegistroAvisoObra(cveAvisoObra, rfc, registroPatronal);
		
		if(rotAvisoObra != null)
			avisoObraDTO = rotAvisoObra.toConvertAvisoObraDTO();
		
		return avisoObraDTO;
	}


	@Override
	public void actualizarAvisoObra(Long cveAvisoObra) throws BusinessException {
		avisoObraDao.updateEstatusAvisoObra(cveAvisoObra);
	}

}
