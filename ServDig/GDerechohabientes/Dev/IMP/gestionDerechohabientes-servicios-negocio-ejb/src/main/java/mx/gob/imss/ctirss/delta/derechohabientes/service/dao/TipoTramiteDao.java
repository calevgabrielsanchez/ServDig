package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;



import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;



/**
 * @author Victor Camacho
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Stateless(name = "tipoTramiteDao", mappedName = "tipoTramiteDao")
public class TipoTramiteDao implements TipoTramiteDaoLocal {

	@PersistenceContext (unitName="deltaPersistenceUnit")
	private EntityManager em;

	@Override
	public DicTipoTramite findTipoTramite(Long idTipoTramite) {
		DicTipoTramite dicTipoTramite=null;
		dicTipoTramite = em.find(DicTipoTramite.class, idTipoTramite);
		return dicTipoTramite;
	}
	


}
