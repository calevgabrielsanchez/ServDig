package mx.gob.imss.ctirss.correccion.seguimiento.pagos.service.ejb.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.model.CrtCoppagada;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtCobranzaPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtRevPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.PagosConsultasSQL;

@Stateless
public class PagosDAOBean <T extends AbstractModel> extends AbstractRespository implements PagosDAOLocal<T>{
	
	/**
	 * Logger
	 */
	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(PagosDAOBean.class);

	@Override
	public CrtRevPagos saveOrUpdate(CrtRevPagos model) throws SQLException, Exception {
		
		try{
			
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();
			model.setExito("El registro se ha procesado correctamente");
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getCause());
		}
		
		return model;
		
	}

	@Override
	public CrtRevPagos delete(CrtRevPagos model) throws SQLException, Exception {
		try{
			
			model = findById(model);
		
			this.getSession().delete(model);
			this.getSession().flush();
			model.setExito("El registro se ha eliminado correctamente");
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getCause());
		}
		
		return model;
	}

	@Override
	public CrtRevPagos findById(CrtRevPagos model) throws SQLException, Exception {
		Criteria criteria = this.getSession().createCriteria(CrtRevPagos.class)
				 .add(Restrictions.eq("cveRevpagos", model.getCveRevpagos()));
					
		return (CrtRevPagos) criteria.list().get(0);
	}

	@Override
	public CrtRevPagos getSumarizado(String nuFolioCorreccion, Integer tipoPago,Integer cveRegulaPago) throws SQLException, Exception {
		String SQL = null;		
		
		if(cveRegulaPago==null || cveRegulaPago.intValue()<=0){
			SQL = PagosConsultasSQL.SUM_PAGOS_REV_TOTAL;
			SQL = SQL.replace("{1}", nuFolioCorreccion);
			SQL = SQL.replace("{2}", tipoPago.toString());
			
		}else{
			SQL = PagosConsultasSQL.SUM_PAGOS_PROMO_TOTAL;
			SQL = SQL.replace("{1}", cveRegulaPago.toString());
			SQL = SQL.replace("{2}", tipoPago.toString());
		}
		
		List<?> ls = this.getSession().createSQLQuery(SQL).list();
		
		CrtRevPagos model = new CrtRevPagos();
		if(!ls.isEmpty()){
			Object[] obj = (Object[])ls.get(0);
			int index = 0;
			
			model.setImpCopsp(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpCopact(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpCoprec(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpCopmulta(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpCoptot(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			
			model.setImpRcvsp(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpRcvact(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpRcvrec(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpRcvmulta(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpRcvtot(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			
			model.setNumTrabregula(Integer.valueOf(String.valueOf(obj[index++])));
			model.setNumAltas(Integer.valueOf(String.valueOf(obj[index++])));
			model.setNumBajas(Integer.valueOf(String.valueOf(obj[index++])));
			model.setNumModifsalario(Integer.valueOf(String.valueOf(obj[index++])));
				
		}
		
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrtCoppagada> obtenerDatosTablaCOPPagadas(CrtCoppagada model) throws SQLException,
			Exception {
		
		Criteria criteria = this.getSession().createCriteria(CrtCoppagada.class) 
				 .add(Restrictions.in("cveAnexoSolCorrPat", model.getIdsAnexoSolCorrPatConcat()));
					
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrtRevPagos> obtenerListaPagos(CrtRevPagos model) throws SQLException, Exception {
		Criteria criteria = this.getSession().createCriteria(CrtRevPagos.class);
		
		if( model.getIndTipopago().intValue() == CrtRevPagos.PAGO_CEDULA_REVISION.intValue() || 
				model.getIndTipopago().intValue() == CrtRevPagos.PAGO_CEDULA_VALIDACION.intValue()){
			criteria.add(Restrictions.eq("cvePresentacorr", model.getCvePresentacorr()))
			 .add(Restrictions.eq("indTipopago", model.getIndTipopago()))
			 .add(Restrictions.isNull("cveRegulaPagos"));
		}else if( model.getIndTipopago().intValue() == CrtRevPagos.PAGO_PROMOCION.intValue()){
			
			criteria.add(Restrictions.eq("indTipopago", model.getIndTipopago()))
			 .add(Restrictions.eq("cveRegulaPagos", model.getCveRegulaPagos()))
			.add(Restrictions.isNull("cvePresentacorr"));
		}
				 
		
		return criteria.list();
	}

	@Override
	public List<CrtRevPagos> obtenerListaPagosPorRP(Integer cvePresentacion, Integer tipoPago)
			throws SQLException, Exception {
		
		String SQL = PagosConsultasSQL.SUM_PAGOS_REV_RPS_INSCRITOS;
		SQL = SQL.replace("{1}", cvePresentacion.toString());
		SQL = SQL.replace("{2}", tipoPago.toString());
		List<?> ls = this.getSession().createSQLQuery(SQL).list();
		List<CrtRevPagos> lsPagos = new ArrayList<CrtRevPagos>();
		CrtRevPagos model = new CrtRevPagos();
		if(!ls.isEmpty()){
			Object[] obj = (Object[])ls.get(0);
			int index = 0;
			
			model.setRegistroPatronal(String.valueOf(obj[index++]));
			model.setImpCopsp(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpCopact(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpCoprec(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpCopmulta(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpCoptot(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			
			model.setImpRcvsp(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpRcvact(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpRcvrec(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpRcvmulta(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			model.setImpRcvtot(BigDecimal.valueOf(Double.valueOf(String.valueOf(obj[index++]))));
			
			model.setNumTrabregula(Integer.valueOf(String.valueOf(obj[index++])));
			model.setNumAltas(Integer.valueOf(String.valueOf(obj[index++])));
			model.setNumBajas(Integer.valueOf(String.valueOf(obj[index++])));
			model.setNumModifsalario(Integer.valueOf(String.valueOf(obj[index++])));
			
			lsPagos.add(model);
				
		}
		
		return lsPagos;
	}

	@Override
	public List<CrtCobranzaPagos> getDetalle(CrtCobranzaPagos cobranzaPagos) {
		// TODO Auto-generated method stub
		StringBuilder query=new StringBuilder();
		//query.append("FROM CrtCobranzaPagos pa where pa.folioSua=:folioSua and pa.tipoDocumento=:tipoDocumento and pa.periodo=:periodo and pa.fecFechapago=:fecFechapago");
		//query.append("FROM CrtCobranzaPagos pa where pa.folioSua=:folioSua and (pa.tipoDocumento=:tipoDocumento or pa.tipoDocCOP=:tipoDocumento or pa.tipoDocRCV=:tipoDocumento ) and pa.periodo=:periodo and pa.fecFechapago=:fecFechapago");
		query.append("FROM CrtCobranzaPagos pa where pa.folioSua=:folioSua and pa.periodo=:periodo and pa.fecFechapago=:fecFechapago");
		System.out.println(query.toString());
		Query que=this.getSession().createQuery(query.toString());
		que.setParameter("folioSua", cobranzaPagos.getFolioSua());
		//que.setParameter("tipoDocumento", cobranzaPagos.getTipoDocumento());
		que.setParameter("periodo", cobranzaPagos.getPeriodo());
		que.setParameter("fecFechapago", cobranzaPagos.getFecFechapago());
		List lista=que.list();
		logger.info("Tam  "+lista.size());
		
		return lista;
	}

	@Override
	public List<CrtCobranzaPagos> recuperaDetalleCobranza(
			CrtCobranzaPagos cobranzaPagos) {
		// TODO Auto-generated method stub
		return null;
	}
		
}
