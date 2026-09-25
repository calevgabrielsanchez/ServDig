package mx.gob.imss.ctirss.delta.cobranza.service.entity;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Credito;
import mx.gob.imss.ctirss.delta.cobranza.modelo.CreditoRCV;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.HCopCreditosTot;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.HRcvCreditosTot;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.CreditosUtilityServiceLocal;

@Stateless(name = "creditosEntity", mappedName = "creditosEntity")
public class CreditosEntity extends AbstractEntity implements CreditosEntityLocal{

	@EJB CreditosUtilityServiceLocal creditosUtilityServiceLocal;
	
	@Override
	public List<Credito> findCreditReportCorp(String regPat, String modalidad) {
		List<Credito> creditos = null;
		
		creditos = this.findCreditResume(regPat, modalidad, true);
		
		return creditos;
	}

	@Override
	public List<Credito> findCreditReportOther(String regPat, String modalidad) {
		List<Credito> creditos = null;
		
		creditos = this.findCreditResume(regPat, modalidad, false);
		
		return creditos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Credito> findCreditResume(String regPat, String modalidad, Boolean resumeCorp) {
		List<Credito> creditos = null;
		List<HCopCreditosTot> hCreditos = null;
		
		Criteria queryCreditos = this.getSession().createCriteria(HCopCreditosTot.class);
		//if(resumeCorp) {
			queryCreditos.add(Restrictions.eq("regPatronal", regPat));
			queryCreditos.add(Restrictions.eq("modalidad", modalidad));
		/*} else {
			queryCreditos.add(Restrictions.eq("regPatronalCor", regPat));
			queryCreditos.add(Restrictions.eq("modalidadCor", modalidad));
		}*/
		
		hCreditos = queryCreditos.list();
		creditos = creditosUtilityServiceLocal.convertListEntiryToListModel(hCreditos);
		
		return creditos;
	}

	@Override
	public List<Credito> findCreditResumeCorp(String regPat, String modalidad) {
		List<Credito> creditos = null;
		
		creditos = this.findCreditResume(regPat, modalidad, true);
		
		return creditos;
	}

	@Override
	public List<Credito> findCreditResumeOther(String regPat, String modalidad) {
		List<Credito> creditos = null;
		
		creditos = this.findCreditResume(regPat, modalidad, false);
		
		return creditos;
	}
	
	@Override
	public List<CreditoRCV> findCreditReportCorpRcv(String regPat,
			String modalidad) {
		List<CreditoRCV> creditosRCV = null;
		
		creditosRCV = this.findCreditResumeRcv(regPat, modalidad, false);
		
		return creditosRCV;
	}

	@Override
	public List<CreditoRCV> findCreditReportOtherRcv(String regPat,
			String modalidad) {
		List<CreditoRCV> creditosRCV = null;
		
		creditosRCV = this.findCreditResumeRcv(regPat, modalidad, true);
		
		return creditosRCV;
	}

	@Override
	public List<CreditoRCV> findCreditResumeRcv(String regPat,
			String modalidad, Boolean patronCorp) {
		
		List<CreditoRCV> creditosRCV = null;
		Criteria queryCreditoRCV = this.getSession().createCriteria(HRcvCreditosTot.class);
		
		//if(!patronCorp) {
			queryCreditoRCV.add(Restrictions.eq("regPatronal", regPat));
			queryCreditoRCV.add(Restrictions.eq("modalidad", modalidad));
		/*} else {
			queryCreditoRCV.add(Restrictions.eq("regPatronalCor", regPat));
			queryCreditoRCV.add(Restrictions.eq("modalidadCor", modalidad));
		}*/
		
		@SuppressWarnings("unchecked")
		List<HRcvCreditosTot> hrCreditos = queryCreditoRCV.list();
		
		if(!hrCreditos.isEmpty()) {
			creditosRCV = creditosUtilityServiceLocal.convertirListEntityToModelRCV(hrCreditos);
		}
		
		return creditosRCV;
	}

	@Override
	public List<CreditoRCV> findCreditResumeCorpRcv(String regPat,
			String modalidad) {
		List<CreditoRCV> creditosRCV = null;
		
		creditosRCV = this.findCreditResumeRcv(regPat, modalidad, false);
		
		return creditosRCV;
	}

	@Override
	public List<CreditoRCV> findCreditResumeOtherRcv(String regPat,
			String modalidad) {
		List<CreditoRCV> creditosRCV = null;
		
		creditosRCV = this.findCreditResumeRcv(regPat, modalidad, true);
		
		return creditosRCV;
	}
}
