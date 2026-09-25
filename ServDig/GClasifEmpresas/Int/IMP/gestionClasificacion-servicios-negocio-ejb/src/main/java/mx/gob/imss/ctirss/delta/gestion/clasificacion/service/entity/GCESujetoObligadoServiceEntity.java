/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: GCESujetoObligadoServiceEntity.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity
 *  @Fecha: 09/01/2013
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

@Stateless
public class GCESujetoObligadoServiceEntity extends AbstractServiceEntity
		implements GCESujetoObligadoServiceEntityLocal {

	/**
	 * {@inheritDoc}
	 * @see GCESujetoObligadoServiceEntityLocal#obtenerPatronSujetoObligado(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DitPatronSujetoObligado obtenerPatronSujetoObligado(
			final long cveIdPatronSujetoObligado) {
		DitPatronSujetoObligado ditPatronSujetoObligado = null;
		final StringBuilder hql = new StringBuilder(16);
		hql.append(" from ")
		   .append(DitPatronSujetoObligado.class.getName())
		   .append(" dpso ")
		   .append("where dpso.cveIdPatronSujetoObligado = :cveIdPatronSujetoObligado");

		final Query query = em.createQuery(hql.toString());
		query.setParameter("cveIdPatronSujetoObligado", cveIdPatronSujetoObligado);
		final List<DitPatronSujetoObligado> ditPatronSujetoObligados = query
				.getResultList();

		if (!ditPatronSujetoObligados.isEmpty()) {
			ditPatronSujetoObligado = ditPatronSujetoObligados.get(0);
		}

		return ditPatronSujetoObligado;
	}

}
