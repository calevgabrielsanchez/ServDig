package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DitDoctoReqTramite;

/**
 * @author Victor Camacho
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Local
public interface DoctoReqTramiteDaoLocal {

	
	public List<DitDoctoReqTramite> getListaTramiteDocumentacion(Long cveIdTipoTramite) throws Exception;
	
	
}
