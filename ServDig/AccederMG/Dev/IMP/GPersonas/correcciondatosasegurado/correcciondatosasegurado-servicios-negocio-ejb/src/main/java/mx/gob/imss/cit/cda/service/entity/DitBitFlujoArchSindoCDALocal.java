package mx.gob.imss.cit.cda.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DitBitFlujoArchSindoCDA;

@Local
public interface DitBitFlujoArchSindoCDALocal {

	DitBitFlujoArchSindoCDA guardarBitacoraSINDOCDA( String folio);
	
	DitBitFlujoArchSindoCDA buscarBitacoraSINDOCDA( String folio);
	
}
