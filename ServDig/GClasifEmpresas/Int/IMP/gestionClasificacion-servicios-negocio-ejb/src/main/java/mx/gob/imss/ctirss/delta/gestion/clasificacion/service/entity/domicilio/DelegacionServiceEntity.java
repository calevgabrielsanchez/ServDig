/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DelegacionServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;

@Stateless
public class DelegacionServiceEntity extends AbstractServiceEntity implements
		DelegacionServiceEntityLocal {

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio.DelegacionServiceEntityLocal#consultaDelegacionPorId(mx.gob.imss.ctirss.delta.model.domicilio.Delegacion)
	 */
	@Override
	public Delegacion consultaPorId(Delegacion delegacion) 
			throws Exception {
		
		this.log.debug("consultaDelegacionPorId [ "+ delegacion +"]");
				
		StringBuffer sql = new StringBuffer();
		sql.append(" from DicDelegacion d where d.cveIdDelegacion = :id ");

		org.hibernate.Query query = this.getSession().createQuery(sql.toString());
		
		query.setParameter("id", delegacion.getId());
		
		List<DicDelegacion> list = query.list();
		if(list == null || list.isEmpty()){
			throw new DomicilioNoLocalizadoException();
		}else{
			DicDelegacion dicDelegacion = list.get(0);		
			delegacion.setId(dicDelegacion.getCveIdDelegacion());
			delegacion.setClave(dicDelegacion.getClaveDelegacion());
			delegacion.setDescripcion(dicDelegacion.getDesDeleg());
		}
		
		return delegacion;
	}

	@Override
	public List<Delegacion> consultaDelegaciones(Long cveIdDelegacion){
		List<Delegacion> lstDelegacion=new ArrayList<Delegacion>();
		Delegacion delegacion=null;
		List<DicDelegacion> delegacions=new ArrayList<DicDelegacion>();
		Query query=null;
		try{
			query=em.createQuery("from DicDelegacion");
			delegacions=(List<DicDelegacion>)query.getResultList();
			for(DicDelegacion d:delegacions){
				delegacion=new Delegacion();
				delegacion.setClave(String.valueOf(d.getCveIdDelegacion()));
				delegacion.setDescripcion(d.getDesDeleg());
				lstDelegacion.add(delegacion);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return lstDelegacion;
	}
}