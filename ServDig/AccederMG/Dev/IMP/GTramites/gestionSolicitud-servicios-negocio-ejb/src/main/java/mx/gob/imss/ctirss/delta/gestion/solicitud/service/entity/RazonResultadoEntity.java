package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.SolicitudConversorLocal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DicParametros;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;

@Stateless(mappedName = "razonResultadoEntity")
public class RazonResultadoEntity extends AbstractServiceEntity implements RazonResultadoEntityLocal {

	@EJB
	private transient SolicitudConversorLocal solicitudConversor;
	
	@Override
	public List<RazonResultado> obtenerRazones() {
		
		List<RazonResultado> razones = null;
		
		String sqlQuery = "from DicRazonResultado";
		
		Query query = em.createQuery(sqlQuery);
		
		@SuppressWarnings("unchecked")
		List<DicRazonResultado> razonesModel = query.getResultList();
		
		if (razonesModel != null && !razonesModel.isEmpty()) {
			
			razones = new ArrayList<RazonResultado>();
			for (DicRazonResultado razon : razonesModel) {
				razones.add(solicitudConversor.convertirRazonResultado(razon));
			}
		}
		
		return razones;
	}

	@Override
	public List<RazonResultado> obtenerRazones(List<Long> idRazones) throws Exception {
		List<RazonResultado> razones = null;
		
		Criteria queryRazones = this.getSession().createCriteria(DicRazonResultado.class);
		queryRazones.add(Restrictions.in("cveIdRazonResultado", idRazones));
		
		@SuppressWarnings("unchecked")
		List<DicRazonResultado> razonesModel = queryRazones.list();
		
		if (razonesModel != null && !razonesModel.isEmpty()) {
			
			razones = new ArrayList<RazonResultado>();
			for (DicRazonResultado razon : razonesModel) {
				razones.add(solicitudConversor.convertirRazonResultado(razon));
			}
		}
	
			
		return razones;
	}



	
}
