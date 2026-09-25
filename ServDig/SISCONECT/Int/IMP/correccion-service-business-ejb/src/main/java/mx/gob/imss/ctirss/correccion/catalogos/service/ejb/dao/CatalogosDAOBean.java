package mx.gob.imss.ctirss.correccion.catalogos.service.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.model.CgcReglaNegocio;

import org.hibernate.Query;

@Stateless
public class CatalogosDAOBean extends AbstractRespository implements
		CatalogosDAOLocal {

	public String getTipoObraById(Integer id) {

		String sql = "select new java.lang.String(c.desTipoobra) from mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoobra c where c.cvePkTipobra = "
				+ id;
		Query query = this.getSession().createQuery(sql);
		return (query.list().size() > 0 ? (String) query.list().get(0) : "");
	}

	public String getFaseObraById(Integer id) {

		String sql = "select new java.lang.String(c.desFaseconstruccion) from mx.gob.imss.ctirss.correccion.catalogos.model.CrcFaseconstruccion c where c.cvePkFaseconst = "
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

		String sql = "select new java.lang.Long(c.cvePK) from mx.gob.imss.ctirss.correccion.model.SatPatron c where substr(c.registroPatronal,1,10) = '"
				+ regPat + "'";
		Query query = this.getSession().createQuery(sql);
		return (query.list().size() > 0 ? (Long) query.list().get(0)
				: new Long(0));

	}

	public String getNombreIncidenciaById(Integer id) {

		String sql = "select new java.lang.String(c.desTipincidenc) from mx.gob.imss.ctirss.correccion.catalogos.model.SacTipincidenc c where c.cvePk = "
				+ id;
		Query query = this.getSession().createQuery(sql);
		return (query.list().size() > 0 ? (String) query.list().get(0) : "");
	}

	public CgcCatcriterioseleccion getIdTipoAndIdOrigenByIdCriterioSeleccion(
			Integer criterio) {
		CgcCatcriterioseleccion cgcCatcriterioseleccion = new CgcCatcriterioseleccion();
		String sql = "from CgcCatcriterioseleccion c where c.idCriterioseleccion = "
				+ criterio;
		ArrayList lista = (ArrayList) this.getSession().createSQLQuery(sql)
				.list();
		if (lista != null && lista.size() > 0) {
			cgcCatcriterioseleccion = (CgcCatcriterioseleccion) lista.get(0);
		}
		return cgcCatcriterioseleccion;
	}

	@Override
	public List<CrcTipoCorr> getTiposCorreccion(Long tipoCorr) {
		String sql = " from CrcTipoCorr c where c.idTipocorr = " + tipoCorr;
		Query query = this.getSession().createQuery(sql);
		return query.list();
	}

}
