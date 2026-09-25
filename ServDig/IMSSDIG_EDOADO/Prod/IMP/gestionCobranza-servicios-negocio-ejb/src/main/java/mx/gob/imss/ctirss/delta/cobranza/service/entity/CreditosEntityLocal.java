package mx.gob.imss.ctirss.delta.cobranza.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Credito;
import mx.gob.imss.ctirss.delta.cobranza.modelo.CreditoRCV;

@Local
public interface CreditosEntityLocal {

	List<Credito> findCreditReportCorp(String regPat, String modalidad);
	List<Credito> findCreditReportOther(String regPat, String modalidad);

	List<Credito> findCreditResume(String regPat,String modalidad, Boolean resumeCorp);
	
	List<Credito> findCreditResumeCorp(String regPat, String modalidad);
	List<Credito> findCreditResumeOther(String regPat, String modalidad);
	
	/** Créditos RCV */
	List<CreditoRCV> findCreditReportCorpRcv(String regPat, String modalidad);
	List<CreditoRCV> findCreditReportOtherRcv(String regPat, String modalidad);
	
	List<CreditoRCV> findCreditResumeRcv(String regPat,String modalidad, Boolean patronCorp);
	
	List<CreditoRCV> findCreditResumeCorpRcv(String regPat, String modalidad);
	List<CreditoRCV> findCreditResumeOtherRcv(String regPat, String modalidad);
	
}
