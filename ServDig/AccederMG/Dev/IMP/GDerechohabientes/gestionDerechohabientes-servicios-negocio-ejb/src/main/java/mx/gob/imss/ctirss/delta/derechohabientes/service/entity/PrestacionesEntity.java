package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.PrestacionParserLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Prestacion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.PrestacionPorModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicPrestacionDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DicPrestacionModDerechohab;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Stateless( name = "prestacionesEntity", mappedName = "prestacionesEntity")
public class PrestacionesEntity extends AbstractServiceEntity implements
		PrestacionesEntityLocal {

	@EJB
	private PrestacionParserLocal prestacionParserLocal;
	
	
	@Override
	public Prestacion getPrestacion(Long idPrestacion) {
		Prestacion prestacion = null;
		
		if(idPrestacion != null) {
			Criteria queryPres = this.getSession().createCriteria(DicPrestacionDerechohab.class);
			queryPres.add(Restrictions.eq("cveIdPrestacionDerechohab", idPrestacion));
			
			DicPrestacionDerechohab dicPre = (DicPrestacionDerechohab)queryPres.uniqueResult();
			
			if(dicPre != null) {
				prestacion = prestacionParserLocal.persistToModel(dicPre);
			}
		}
		
		return prestacion;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Prestacion> getCatalogoPrestaciones() {
		
		List<Prestacion> prestaciones = null;
		Criteria queryPres = this.getSession().createCriteria(DicPrestacionDerechohab.class);
		
		List<DicPrestacionDerechohab> listDicPres = queryPres.list();
		if(listDicPres != null && !listDicPres.isEmpty()) {
			prestaciones = prestacionParserLocal.persistToModelList(listDicPres);
		}
		
		return prestaciones;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PrestacionPorModalidad> getPrestacionesPorModalidad(
			Long idModalidad) {
		
		List<PrestacionPorModalidad> prestaciones = null;
		
		if(idModalidad != null) {
			Criteria query = this.getSession().createCriteria(DicPrestacionModDerechohab.class);
			query.createAlias("dicModalidad", "mod");
			query.add(Restrictions.eq("mod.cveIdModalidad", idModalidad));
			
			List<DicPrestacionModDerechohab> dicPrest = query.list();
			
			if(dicPrest != null && !dicPrest.isEmpty()) {
				prestaciones = prestacionParserLocal.persistToModelListPresPorMod(dicPrest);
			}
		}
		
		return prestaciones;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PrestacionPorModalidad> getPrestacionesPorModalidades(
			List<Long> idsModalidades) {
		List<PrestacionPorModalidad> prestaciones = null;
		
		if(idsModalidades != null && !idsModalidades.isEmpty()) {
			Criteria query = this.getSession().createCriteria(DicPrestacionModDerechohab.class);
			query.createAlias("dicModalidad", "mod");
			query.add(Restrictions.in("mod.cveIdModalidad", idsModalidades));
			
			List<DicPrestacionModDerechohab> dicPrest = query.list();
			
			if(dicPrest != null && !dicPrest.isEmpty()) {
				prestaciones = prestacionParserLocal.persistToModelListPresPorMod(dicPrest);
			}
		}
		
		return prestaciones;
	}
	
	

}
