package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;

/**
 * @author Victor Camacho
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Local
public interface TipoTramiteDaoLocal {

	public DicTipoTramite findTipoTramite(Long tipoTramite);
}
