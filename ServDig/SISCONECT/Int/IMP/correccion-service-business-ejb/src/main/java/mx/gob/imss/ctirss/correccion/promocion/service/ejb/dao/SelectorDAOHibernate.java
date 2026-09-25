package mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.imss.ctirss.correccion.catalogos.model.CgtCatCriterioeleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacTipoObra;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuarioFuncionario;
import mx.gob.imss.ctirss.correccion.model.CgtPromocion;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.GenericHibernateDAO;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@Stateless
public class SelectorDAOHibernate 
		extends GenericHibernateDAO<CrtSelector, Long>
		implements SelectorDAO{
	
	public void setPersistentClass(Class<CrtSelector> p) {
		super.setPersistentClass(p);
	}
	
	public SelectorDAOHibernate() {
		setPersistentClass(CrtSelector.class);
	}

	public void insertaSelector(CrtSelector selector) {
		
	}

	public SacSubdelegacion obtenerSubDelegacion(Long idDelegacion, Long idSubdelegacion) {
		Criteria crit = getSession().createCriteria(SacSubdelegacion.class);
		crit.add(Restrictions.and(Restrictions.eq("cvePk", idSubdelegacion), Restrictions.eq("sacDelegacion.cvePk", idDelegacion)));
		SacSubdelegacion the = (SacSubdelegacion) crit.uniqueResult();
		return the;
	}

	public CgtCatCriterioeleccion obtenerCriterio(Long idCriterio) {
		Criteria crit = getSession().createCriteria(CgtCatCriterioeleccion.class);
		crit.add(Restrictions.eq("idCriterioseleccion", idCriterio));
		return (CgtCatCriterioeleccion) crit.uniqueResult();
	}

	public CrtDeteccion guardarDeteccionCarga(CrtDeteccion deteccion) {
		getSession().saveOrUpdate(deteccion);
		getSession().flush();
		return deteccion;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SegUsuarioFuncionario> cargarCensoresDeteccion(Long idDelegacion, Long idSubDelegacion) {
		
		List lstParser = new ArrayList();
		List lstUsuario = this.getSession().createSQLQuery(" SELECT " +
																  "	 u.CVE_ID_USUARIO " +
																  "  from SEG_USUARIO u " +
																  "  inner join SEG_PERFIL_USUARIO pu on u.CVE_ID_USUARIO = pu.CVE_ID_USUARIO  " +
																  "  inner join SEG_ROL r on pu.CVE_ROL = r.CVE_ROL  " +
																  "  inner join SEG_USUARIO_FUNCIONARIO uf on u.CVE_ID_USUARIO = uf.CVE_ID_USUARIO" +
																  "  where pu.CVE_ROL = 3" +
																  "  and uf.CVE_ID_SUBDELEGACION =" + idSubDelegacion).list();
								
		if(lstUsuario != null && lstUsuario.size() > 0){
			for(int i = 0; i < lstUsuario.size(); i++){
				BigDecimal aux = (BigDecimal) lstUsuario.get(i);
				Long lAux = aux.longValue();
				lstParser.add(lAux);
			}
		}
		Criteria crit = getSession().createCriteria(SegUsuarioFuncionario.class);
		crit.add(Restrictions.in("segUsuario.cveIdUsuario", lstParser));
		return crit.list();
	}

	@Override
	public SacTipoObra obtenerTipoObra(Long id) {
		Criteria crit = getSession().createCriteria(SacTipoObra.class);
		crit.add(Restrictions.eq("idTipoObra", id));
		return (SacTipoObra) crit.uniqueResult();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public CrtNroFolio obtenerSiguienteFolio(Long idDelegacion, Long idSubdelegacion, Integer tipoCorrecion, Date fechaFolio) {
		Criteria crit = getSession().createCriteria(CrtNroFolio.class);
		
		Calendar cal = Calendar.getInstance();
		CrcTipoCorr tipo = new CrcTipoCorr();
		tipo.setCveTipocorr(tipoCorrecion);
		cal.setTime(fechaFolio);
		
		crit.add(Restrictions.and(Restrictions.eq("cveDelegacion", new BigDecimal(idDelegacion)), Restrictions.eq("cveSubdelegacion", new BigDecimal(idSubdelegacion))));
		crit.add(Restrictions.eq("numAnio", new BigDecimal(cal.get(Calendar.YEAR))));
		crit.add(Restrictions.eq("crcTipoCorr", tipo));
		crit.addOrder(Order.desc("numNumero"));
		
		@SuppressWarnings("unchecked")
		List<CrtNroFolio> lista = crit.list();
		CrtNroFolio folio = null;
		
		if(lista != null &&
				lista.size() > 0){
			folio = lista.get(0);
			folio.setNumNumero(folio.getNumNumero().add(new BigDecimal(1)));
			folio.setCrcTipoCorr(tipo);
			getSession().saveOrUpdate(folio);
		}else{
			folio = new CrtNroFolio();
			folio.setCveDelegacion(new BigDecimal(idDelegacion));
			folio.setCveSubdelegacion(new BigDecimal(idSubdelegacion));
			
			folio.setNumAnio(new BigDecimal(cal.get(Calendar.YEAR)));
			folio.setCrcTipoCorr(tipo);
			folio.setNumNumero(new BigDecimal(1));
			getSession().saveOrUpdate(folio);
		}
		
		getSession().flush();
		
		return folio;
	}

	public List<CrtSelector> obtenerCriteriosSeleccion(Long idDelegacion, Long idSubDelegacion, Long idCriterio) {
		StringBuilder builder = new StringBuilder();
		builder.append("select new mx.gob.imss.ctirss.correccion.model.CrtSelector(s.cveSelector,s.cgcCatcriterioseleccion.idCriterioseleccion, s.satPatron.cvePK, s.satPatron.registroPatronal,s.satPatron.razonSocial)");
		builder.append("from mx.gob.imss.ctirss.correccion.model.CrtSelector s ");
		builder.append("where s.cgcCatcriterioseleccion.idCriterioseleccion = :lnCriterioSeleccion and s.sacDelegacion.cvePk = :inDelegacion and ");
		builder.append("s.sacSubdelegacion.cvePk = :inSubDelegacion and s.idPromocionado = 0");
		
		Query query = this.getSession().createQuery(builder.toString());
		query.setParameter("inDelegacion", idDelegacion);
		query.setParameter("inSubDelegacion", idSubDelegacion);
		query.setParameter("lnCriterioSeleccion", idCriterio);
		
		@SuppressWarnings("unchecked")
		List<CrtSelector> criterios = query.list();
		
		return criterios;
	}

	@Override
	public CrtPromocion guardarPromocion(CrtPromocion promocion) {
		getSession().saveOrUpdate(promocion);
		getSession().flush();
		return promocion;
	}

	@Override
	public CrtSelector actualizarSelector(CrtSelector selector) {
		getSession().merge(selector);
		getSession().flush();
		return selector;
	}

	@Override
	public void guardarPromocionReplica(CgtPromocion replica) {
		try{
			getSession().saveOrUpdate(replica);
			getSession().flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean existeNumeroReporteObra(CrtDeteccion deteccion) {

		StringBuilder builder = new StringBuilder();
		builder.append("select d.cveDeteccion ");
		builder.append("from CrtDeteccion d ");
		builder.append("where d.nuReportectrlobra  = '");
		builder.append(deteccion.getNuReportectrlobra()).append("'");
		Query query = this.getSession().createQuery(builder.toString());
		
		@SuppressWarnings("unchecked")
		List<Long> data = query.list();
		
		return (data!=null && !data.isEmpty());
	
	}
}
