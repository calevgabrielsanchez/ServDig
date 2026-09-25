package mx.gob.imss.cit.cda.service.autorizar.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DitBitFlujoArchSindoCDA;

@Local
public interface AutorizarSolicitudLocal {

    DitBitFlujoArchSindoCDA guardarBitacoraSINDOCDA(String folio);

    DitBitFlujoArchSindoCDA buscarBitacoraSINDOCDA(String folio);

}
