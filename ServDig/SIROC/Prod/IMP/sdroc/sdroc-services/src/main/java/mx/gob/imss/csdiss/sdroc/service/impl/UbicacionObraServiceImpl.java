package mx.gob.imss.csdiss.sdroc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.exception.BusinessException;
import mx.gob.imss.csdiss.sdroc.orm.dao.DitLlavePatronDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.UbicacionObraService;


/**
 * 
 * Clase que implementa la interface **** que permite obtener los parametros del sistema
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Service
@Transactional
public class UbicacionObraServiceImpl implements UbicacionObraService  {
	
	private static final Logger log = LoggerFactory.getLogger(UbicacionObraServiceImpl.class);
	
	@Autowired
	DitLlavePatronDao ditLlavePatronDao;

	@Override
	public boolean isValidoCpUbicacionObra(String cveRp, String cveCodigoPostal) throws BusinessException {

		int numRegistros = 0;
		boolean isValido = false;
		
		numRegistros = ditLlavePatronDao.findbyRpAndCp(cveRp, cveCodigoPostal);
		if(numRegistros != 0)
			isValido = true;
		return isValido;
	}

	@Override
	public boolean validCircunscripcion(String codigoPostal, Long idDelegacion, Long idSubdelegacion) throws BusinessException {
		
		int numRegistros = 0;
		
		if(codigoPostal == null && idDelegacion != null && idSubdelegacion != null) {
			throw new BusinessException("No se han proporcionado los datos correctos");
		}
		
		numRegistros = ditLlavePatronDao.validCircunscripcionCP(codigoPostal, idDelegacion, idSubdelegacion);
		
		return numRegistros != 0;
	}



}
