package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.EstadoDerechohabienteParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.SubEstadoDerechohabienteParserServiceLocal;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DicSubestadoDerechohabiente;

@Stateless( name = "estadoDerechohabienteEntity", mappedName = "estadoDerechohabienteEntity")
public class EstadoDerechohabienteEntity extends AbstractServiceEntity
		implements EstadoDerechohabienteEntityLocal {

	@EJB EstadoDerechohabienteParserServiceLocal estadoDerechohabienteParserServiceLocal;
	@EJB SubEstadoDerechohabienteParserServiceLocal subEstadoDerechohabienteParserServiceLocal;
	
	@Override
	public EstadoDerechohabiente getEstadoDerechohabiente(Long idEstado)  throws DerechohabientesBusinessException {
		EstadoDerechohabiente estado = null;
		DicEstadoDerechohabiente dicEstado = em.find(DicEstadoDerechohabiente.class, idEstado);
		
		if(dicEstado != null) {
			estado = estadoDerechohabienteParserServiceLocal.persisToModel(dicEstado);
		}
		
		return estado;
	}

	@Override
	public SubEstadoDerechohabiente getSubEstadoDerechohabiente(Long idSubEstado) throws DerechohabientesBusinessException {
		SubEstadoDerechohabiente subEstado = null;
		
		DicSubestadoDerechohabiente dicSubEstado = em.find(DicSubestadoDerechohabiente.class, idSubEstado);
		
		if(dicSubEstado != null) {
			subEstado = subEstadoDerechohabienteParserServiceLocal.persisToModel(dicSubEstado);
		}
		
		return subEstado;
	}

}
