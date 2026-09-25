package mx.gob.imss.distss.derechohabientes.adimss.service.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliar;
import mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss.AdtCredencial;

@Stateless
public class ObtenerFotografiaEntity extends AbstractServiceEntity implements
		ObtenerFotografiaEntityLocal {

	@Override
	public String getTest() {

		return "Yeah mans";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdtCredencial> getImagenId(String nss, int calidad) {
		List<AdtCredencial> credenciales = new ArrayList<AdtCredencial>();
		Query query = em.createQuery("select g from AdtCredencial g "
				+ "where  g.id.numNssAseg='" + nss + "'"
				+ " and g.adcCalidadDerechohabient.cveCalidadAseg=" + calidad);
		credenciales = query.getResultList();
		return credenciales;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdtCredencial> getImagenId(String nss, int calidad, String nombre, String apePat, String apeMat){
		List<AdtCredencial> credenciales = new ArrayList<AdtCredencial>();
		StringBuilder st = new StringBuilder();
		st.append("select g from AdtCredencial g");
		st.append(" where  g.id.numNssAseg='" + nss + "'");
		st.append(" and g.adtPersonaCredencializada.nomNombre='" + nombre.toUpperCase() + "'");
		st.append(" and g.adtPersonaCredencializada.nomApellPat='" + apePat.toUpperCase() + "'");
		st.append(" and g.adtPersonaCredencializada.nomApellMat='" + apeMat.toUpperCase() + "'");
		if(calidad == 11 || calidad == 12){
			st.append("' and g.adcCalidadDerechohabient.cveCalidadAseg=" + calidad);
		}
		Query query = em.createQuery(st.toString());
		credenciales = query.getResultList();
		return credenciales;
	}

}
