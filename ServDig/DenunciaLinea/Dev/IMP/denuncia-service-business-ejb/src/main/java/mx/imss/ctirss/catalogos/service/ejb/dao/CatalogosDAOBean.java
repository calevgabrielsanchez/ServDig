package mx.imss.ctirss.catalogos.service.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.imss.ctirss.framework.base.repository.AbstractRespository;


import org.hibernate.Query;

@Stateless
public class CatalogosDAOBean extends AbstractRespository implements
		CatalogosDAOLocal {

	public String getTipoObraById(Integer id) {

		String sql = "select new java.lang.String(c.desTipoobra) from mx.imss.ctirss.catalogos.model.CrcTipoobra c where c.cvePkTipobra = "
				+ id;
		Query query = this.getSession().createQuery(sql);
		return (query.list().size() > 0 ? (String) query.list().get(0) : "");
	}

	public String getFaseObraById(Integer id) {

		String sql = "select new java.lang.String(c.desFaseconstruccion) from mx.imss.ctirss.catalogos.model.CrcFaseconstruccion c where c.cvePkFaseconst = "
				+ id;
		Query query = this.getSession().createQuery(sql);
		return (query.list().size() > 0 ? (String) query.list().get(0) : "");
	}

	public String getClaseObraById(Integer id) {

		String sql = "select new java.lang.String()";
		Query query = this.getSession().createQuery(sql);
		return (query.list().size() > 0 ? (String) query.list().get(0) : "");
	}

	public Long getIdPatByRegPat(String regPat) {

		String sql = "select new java.lang.Long(c.cvePK) from mx.imss.ctirss.model.SatPatron c where substr(c.registroPatronal,1,10) = '"
				+ regPat + "'";
		Query query = this.getSession().createQuery(sql);
		return (query.list().size() > 0 ? (Long) query.list().get(0)
				: new Long(0));

	}

	public String getNombreIncidenciaById(Integer id) {

		String sql = "select new java.lang.String(c.desTipincidenc) from mx.imss.ctirss.catalogos.model.SacTipincidenc c where c.cvePk = "
				+ id;
		Query query = this.getSession().createQuery(sql);
		return (query.list().size() > 0 ? (String) query.list().get(0) : "");
	}

	

}
