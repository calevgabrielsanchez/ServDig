/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:SubDelegacionServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio;

import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;

import org.hibernate.Query;

@Stateless
public class SubDelegacionServiceEntity extends AbstractServiceEntity implements
		SubDelegacionServiceEntityLocal {

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio.SubDelegacionServiceEntityLocal#consultaSubDelegacionPorId(mx.gob.imss.ctirss.delta.model.domicilio.SubDelegacion)
	 */
	@Override
	public Subdelegacion consultaPorId(Subdelegacion subdelegacion) 
			throws Exception {
		
		this.log.debug("consultaSubDelegacionPorId [ "+ subdelegacion +"]");
				
		StringBuffer sql = new StringBuffer();
		sql.append(" from DicSubdelegacion sd where sd.cveIdSubdelegacion = :idSD ");

		Query query = this.getSession().createQuery(sql.toString());
		query.setParameter("idSD", subdelegacion.getId());
		
		List<DicSubdelegacion> list = query.list();
		if(list == null || list.isEmpty()){
			throw new Exception();
		}else{
			DicSubdelegacion dicSubdelegacion = list.get(0);
			subdelegacion.setId(dicSubdelegacion.getCveIdSubdelegacion());
			subdelegacion.setClave(dicSubdelegacion.getClaveSubdelegacion());
			subdelegacion.setDescripcion(dicSubdelegacion.getDesSubdelegacion());
		}
		return subdelegacion;
	}

}