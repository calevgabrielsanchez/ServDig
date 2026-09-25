package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.TipoTramiteConversorLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTramiteModalidad;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "tramiteModalidadEntity", mappedName = "tramiteModalidadEntity")
public class TramiteModalidadEntity extends AbstractServiceEntity implements
		TramiteModalidadEntityLocal {
	
	@EJB 
	private TipoTramiteConversorLocal tipoTramiteConversorLocal;
	
	@Override
	public List<TipoTramite> getTipoTramiteByModalidades(
			List<Modalidad> modalidades) {
		
		List<TipoTramite> tipoTramites = null;
		List<Long> idModalidades = new ArrayList<Long>();
		
		for(Modalidad modalidad: modalidades) {
			idModalidades.add(modalidad.getIdModalidad());
		}
		
		Criteria query = this.getSession().createCriteria(DicTramiteModalidad.class);
		query.setProjection(Projections.distinct(Projections.property("dicTipoTramite")));
		query.createAlias("dicModalidad", "modalidad");
		
		if(idModalidades.size() == 1) {
			query.add(Restrictions.eq("modalidad.cveIdModalidad", idModalidades.get(0)));
		} else {
			query.add(Restrictions.in("modalidad.cveIdModalidad", idModalidades));
		}
		
		
		query.add(Restrictions.isNull("fecRegistroBaja"));
		
		List<DicTipoTramite> dicTiposTramites = (List<DicTipoTramite>) query.list();
		tipoTramites = tipoTramiteConversorLocal.entityToModelList(dicTiposTramites);
		
		return tipoTramites;
	}

}