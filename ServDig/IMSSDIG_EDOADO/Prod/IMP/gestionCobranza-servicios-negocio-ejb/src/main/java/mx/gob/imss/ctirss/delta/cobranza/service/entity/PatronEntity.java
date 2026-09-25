package mx.gob.imss.ctirss.delta.cobranza.service.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.DCopPatrone;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.PatronUtilityServiceLocal;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "patronEntity", mappedName = "patronEntity")
public class PatronEntity extends AbstractEntity implements PatronEntityLocal {

	@EJB PatronUtilityServiceLocal patronUtilityServiceLocal;
	
	
	@Override
	public Patron getPatron(String regPat, String modalidad) {
		
		Patron patron = null;
		Criteria queryPatron = this.getSession().createCriteria(DCopPatrone.class);
		queryPatron.add(Restrictions.eq("cvePatron", regPat));
		queryPatron.add(Restrictions.eq("cveModalidad", modalidad));
		
		DCopPatrone dPatron = null;
		
		try {
			dPatron = (DCopPatrone) queryPatron.uniqueResult();
		} catch(NonUniqueResultException e) {
			e.printStackTrace();
		}
		
		if(dPatron != null) {
			patron = patronUtilityServiceLocal.convertEntityToModel(dPatron);
		}
		
		return patron;
	}

	@Override
	public List<Patron> findRegPatManMapping(String regPat, String modalidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> findAsociados(String regPat, String modalidad) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append(" UNIQUE((REG_PATRONAL || MODALIDAD)) REG_PAT");
		sql.append(" FROM ");
		sql.append(" H_COP_CREDITOS_TOT ");
		sql.append(" WHERE T_NOTIFICACION = 2 ");
		sql.append(" AND (REG_PATRONAL_COR = '");
		sql.append(regPat);
		sql.append("' and ");
		sql.append("MODALIDAD_COR = '");
		sql.append(modalidad);
		sql.append("')");
		
		sql.append(" UNION ");
		
		sql.append("SELECT ");
		sql.append(" UNIQUE((REG_PATRONAL || MODALIDAD)) REG_PAT");
		sql.append(" FROM ");
		sql.append(" H_RCV_CREDITOS_TOT ");
		sql.append(" WHERE T_NOTIFICACION = 2 ");
		sql.append(" AND (REG_PATRONAL_COR = '");
		sql.append(regPat);
		sql.append("' and ");
		sql.append("MODALIDAD_COR = '");
		sql.append(modalidad);
		sql.append("')");

		List<String> rows = null;
		 
		try{
		   Query query = this.em.createNativeQuery(sql.toString());
		   rows = query.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Map<String,String> regsPats = new HashMap<String,String>();

		if(!rows.isEmpty()) {
			System.out.println("ROWS: "+rows.size());
			for(String row: rows) {
				String regpat = row.substring(0, row.length()-2);
				System.out.println("row: "+row);
				System.out.println("regpat: "+regpat);
				regsPats.put(regpat,regpat);
			}
			
		}
		
		return regsPats;
	}

	public int obtenerTotalAdeudosPorRegistroPatronal(String regPatron){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT SUM(a.creditosFiscales) FROM( ");
		sql.append("  SELECT count(*) as creditosFiscales FROM H_COP_CREDITOS_TOT t ");
		sql.append("  WHERE INC_ACT  between 31 and 43 ");
		//sql.append("  WHERE (INC_ACT  between 31 and 43 ");
		//sql.append("  OR (inc_act = 2 and T_DOCUMENTO  = 2 and substr(CREDITO,3,1) = 2 )) ");
		sql.append("  AND REG_Patronal = :regPatron ");		
		sql.append(" UNION SELECT count(*) as creditosFiscales FROM H_RCV_CREDITOS_TOT ");
		sql.append("  WHERE INC_ACT  between 31 and 43 ");
		//sql.append("  WHERE (INC_ACT  between 31 and 43 ");
		//sql.append("  OR (inc_act = 2 and T_DOCUMENTO  = 6 and substr(CREDITO,3,1) = 7 )) ");
		sql.append("  AND REG_Patronal = :regPatron ) a ");		
		Query query = this.em.createNativeQuery(sql.toString());
		query.setParameter("regPatron", regPatron);
		BigDecimal totalAdeudos = (BigDecimal)query.getSingleResult();
		return totalAdeudos.intValue();
	}
	
}
