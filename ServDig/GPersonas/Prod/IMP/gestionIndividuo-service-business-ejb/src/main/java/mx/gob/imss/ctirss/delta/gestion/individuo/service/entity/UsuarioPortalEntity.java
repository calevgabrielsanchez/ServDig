package mx.gob.imss.ctirss.delta.gestion.individuo.service.entity;

import java.util.Date;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.persistence.DitDatosPersonaSat;
//import mx.gob.imss.ctirss.delta.persistence.DitPerPortalCiudadano;
import mx.gob.imss.ctirss.delta.persistence.DitPerPortalCiudadano;

@Stateless(name = "usuarioPortalEntity", mappedName = "usuarioPortalEntity")
public class UsuarioPortalEntity extends AbstractServiceEntity implements
		UsuarioPortalEntityLocal {

	@Override
	public void insertarDatosCuenta(Usuario usuario, Long idSolicitud) {
		
		DitPerPortalCiudadano ditPer = this.convertUsuarioToEntity(usuario);
		ditPer.setFecRegistroAlta(new Date());
		ditPer.setCveIdSolicitud(idSolicitud);
		em.persist(ditPer);

	}

	private DitPerPortalCiudadano convertUsuarioToEntity(Usuario usuario) {
		DitPerPortalCiudadano ditPerPortal = new DitPerPortalCiudadano();
		ditPerPortal.setCve_id_persona(usuario.getFisica().getIdPersona());
		ditPerPortal.setDescCorreoElectronico(usuario.getCorreo());
		ditPerPortal.setNumTelefono(usuario.getTelefono());
		
		return ditPerPortal;
	}

	@Override
	public DitPerPortalCiudadano recuperaPersona(Long cveIdPersona) {
		// TODO Auto-generated method stub
		Criteria criteria = this.getSession().createCriteria(
				DitPerPortalCiudadano.class);
		criteria.add(Restrictions.eq("cve_id_persona",cveIdPersona));
		return (DitPerPortalCiudadano) criteria.uniqueResult();
	}
}
