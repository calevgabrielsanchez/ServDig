package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DitCiudadanoCurpCorreo;

@Stateless(name = "portalCiudadanoServiceEntity", mappedName = "portalCiudadanoServiceEntity")
public class PortalCiudadanoServiceEntity extends AbstractServiceEntity implements PortalCiudadanoServiceEntityLocal {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DitCiudadanoCurpCorreo> obtenerCiudadanoPorCorreo(String correo) {
		String sSql = "SELECT s FROM DitCiudadanoCurpCorreo s WHERE s.refCorreoElectronico = '" + correo + "'";
    	Query querySerie = em.createQuery(sSql);
    	return querySerie.getResultList();
	}
	
	
	@Override
	public void actualizarCurpACorreo(String correo, String curp) {
		
		String queryActualizacionCurp = "update DIT_CIUDADANO_CURP_CORREO set REF_CURP = '" + curp + "'";
		queryActualizacionCurp += ", FEC_REGISTRO_ACTUALIZADO = sysdate";
		queryActualizacionCurp += " where REF_CORREO_ELECTRONICO = '" + correo + "'";
		
		
		this.getSession().createSQLQuery(queryActualizacionCurp).executeUpdate();
	}

	@Override
	public DitCiudadanoCurpCorreo guardarCiudadano(DitCiudadanoCurpCorreo ditCiudadano) {
		return em.merge(ditCiudadano);
	}

	@Override
	public DitCiudadanoCurpCorreo getCiudadanoById(Long id) {
		return em.find(DitCiudadanoCurpCorreo.class,id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DitCiudadanoCurpCorreo> obtenerCiudadanoPorCorreoYFechaLimite(String correo, Date fechaLimite) {
		
		String sql = "SELECT s FROM DitCiudadanoCurpCorreo s WHERE s.refCorreoElectronico = :correo AND s.fecRegistroAlta > :fechaLimite ORDER BY s.refCurp";
		
		Query query = em.createQuery(sql);
		
		query.setParameter("correo", correo);
		query.setParameter("fechaLimite", fechaLimite);
		
		return query.getResultList();
	}
	
}
