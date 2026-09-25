package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.siscob;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.CptCreinc14ImssRcv;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HCopEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HRcvEstadoCuenta;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.siscob.HistPatronesConvenioImssrcv;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.util.PersistenceUnitSISCOBServiceEntity;



@Local(value = SiscobServiceEntityLocal.class)
/*@Stateless(name = "siscobServiceEntity", mappedName = "siscobServiceEntity")*/
@Stateless
public class SiscobServiceEntity extends PersistenceUnitSISCOBServiceEntity implements SiscobServiceEntityLocal {
	
	private static final Logger log = LoggerFactory.getLogger(SiscobServiceEntity.class);
	private static final BigDecimal valorMinimo = new BigDecimal("0.00");

	@Override
	public List<CptCreinc14ImssRcv> getCptCreinc14ImssRcv(String nrp, Long numCredito, Long periodoCredito) throws Exception {
		
		log.debug("llegando a hacer la consulta de getCptCreinc14ImssRcv con nrp {}", nrp);
		try {
			Criteria criteria = getSession().createCriteria(CptCreinc14ImssRcv.class);
			criteria.add(Restrictions.eq("regPatronal", nrp.substring(0,8)));
			criteria.add(Restrictions.eq("modalidad", nrp.substring(8)));
			criteria.add(Restrictions.eq("numCredito", new BigDecimal(numCredito.longValue())));
			criteria.add(Restrictions.eq("numPeriodoCredito", new BigDecimal(periodoCredito.longValue())));
			@SuppressWarnings("unchecked")
			List<CptCreinc14ImssRcv> listCptCreinc14ImssRcv = criteria.list();
			return listCptCreinc14ImssRcv;
		}catch(Exception e) {
			log.error("ocurio un erro en la consulta del getCptCreinc14ImssRcv" ,e );
			throw e;
		}
		
	
	}
	
	@Override
	public List<HistPatronesConvenioImssrcv> getHistPatronesConvenioImssrcv(String nrp, Long numCredito, Long periodoCredito)
						throws Exception {
		
		log.debug("llegando a hacer la consulta de getHistPatronesConvenioImssrcv con nrp {}", nrp);
	      Calendar calendar180 = Calendar.getInstance();
	      calendar180.setTime(new Date()); 
	      calendar180.add(Calendar.DATE, -180);  
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        sdf.setCalendar(calendar180);
        log.debug("la fecha a consultar es " + sdf.getCalendar().getTime());
        try {  
	        Criteria criteria = getSession().createCriteria(HistPatronesConvenioImssrcv.class);
			criteria.add(Restrictions.eq("regPatronal", nrp.substring(0,8)));
			criteria.add(Restrictions.eq("modalidad", nrp.substring(8)));
			criteria.add(Restrictions.eq("numCredito", new BigDecimal(numCredito.longValue())));
			criteria.add(Restrictions.eq("numPeriodoCredito", new BigDecimal(periodoCredito.longValue())));
			criteria.add(Restrictions.between("fecMovto", sdf.getCalendar().getTime(), new Date()));
			@SuppressWarnings("unchecked")
			List<HistPatronesConvenioImssrcv> listHistPatronesConvenioImssrcv = criteria.list();
			return listHistPatronesConvenioImssrcv;
		}catch(Exception e) {
			log.error("ocurio un erro en la consulta del getHistPatronesConvenioImssrcv" ,e );
			throw e;
		}
        
	}


	@Override
	public List<HCopEstadoCuenta> getHCopEstadoCuenta(String nrp) throws Exception {
		log.debug("llegando a hacer la consulta de getHCopEstadoCuenta con nrp {}", nrp);
		try {
			
			Criteria criteria = getSession().createCriteria(HCopEstadoCuenta.class, "cop");
			criteria.add(Restrictions.eq("cop.id.crPat", nrp.substring(0,8)));
			criteria.add(Restrictions.eq("cop.id.crMod", nrp.substring(8)));
			
			Criterion valorMinimocrTotalAdeudo = Restrictions.gt("cop.crTotalAdeudo", valorMinimo);
			Criterion valorMinimocrAcuEje = Restrictions.gt("cop.crAcuEje", valorMinimo);
			LogicalExpression orExp = Restrictions.or(valorMinimocrTotalAdeudo,valorMinimocrAcuEje);
			criteria.add(orExp);
			@SuppressWarnings("unchecked")
			List<HCopEstadoCuenta> listHCopEstadoCuenta = criteria.list();
			return listHCopEstadoCuenta;
		}catch(Exception e) {
			log.error("ocurio un erro en la consulta del getHCopEstadoCuenta" ,e );
			throw e;
		}
	}


	@Override
	public List<HRcvEstadoCuenta> getHRcvEstadoCuenta(String nrp) throws Exception {
		log.debug("llegando a hacer la consulta de getHRcvEstadoCuenta con nrp {}", nrp 
				+ " con sub" + nrp.substring(0,8) + " el otro" + nrp.substring(8));
		try {	
			
			Criteria criteria = getSession().createCriteria(HRcvEstadoCuenta.class, "rcv");
			criteria.add(Restrictions.eq("rcv.id.crPat", nrp.substring(0,8)));
			criteria.add(Restrictions.eq("rcv.id.crMod", nrp.substring(8)));
			
			Criterion valorMinimocrTotalAdeudo = Restrictions.gt("rcv.rcrSalTotal", valorMinimo);
			Criterion valorMinimocrAcuEje = Restrictions.gt("rcv.rcrAcuEje", valorMinimo);
			LogicalExpression orExp = Restrictions.or(valorMinimocrTotalAdeudo,valorMinimocrAcuEje);
			criteria.add(orExp);
			//criteria.add(Restrictions.gt("rcv.rcrAcuEje", valorMinimo));
			@SuppressWarnings("unchecked")
			List<HRcvEstadoCuenta> listHRcvEstadoCuenta = criteria.list();
			return listHRcvEstadoCuenta;
			
		}catch(Exception e) {
			log.error("ocurio un erro en la consulta del getHRcvEstadoCuenta" ,e );
			throw e;
		}
	}

}
