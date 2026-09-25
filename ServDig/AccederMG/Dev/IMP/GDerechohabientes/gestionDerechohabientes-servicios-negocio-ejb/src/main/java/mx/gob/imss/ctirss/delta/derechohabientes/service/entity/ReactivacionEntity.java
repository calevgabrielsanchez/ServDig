package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.ReactivacionParserLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteReactivacionDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DitReactivacionDerechohab;

@Stateless(name = "reactivacionEntity", mappedName = "reactivacionEntity")
public class ReactivacionEntity extends AbstractServiceEntity implements
		ReactivacionEntityLocal {

	@EJB
	private ReactivacionParserLocal reactivacionParserLocal;
	
	@Override
	public TramiteReactivacionDerechohab insertFromTramiteReactivacion(
			TramiteReactivacionDerechohab tramite) {
		
		DitReactivacionDerechohab ditReactivacion = reactivacionParserLocal.tramiteReactivacionToDitReactivacion(tramite);
		
		if(ditReactivacion != null) {
			em.persist(ditReactivacion);
			tramite.setIdReactivacion(ditReactivacion.getCveIdReactivacion());
		}

		return tramite;
	}

}
