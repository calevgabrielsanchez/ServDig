package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Credito;
import mx.gob.imss.ctirss.delta.cobranza.modelo.CreditoRCV;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.HCopCreditosTot;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.HRcvCreditosTot;

@Stateless(name = "creditosUtilityService", mappedName = "creditosUtilityService")
public class CreditosUtilityService implements CreditosUtilityServiceLocal {

	@Override
	public Credito convertEntityToModel(HCopCreditosTot hCredito) {
		Credito credito = null;
		
		if(hCredito != null){
			credito = new Credito();
			credito.setCrPat(hCredito.getRegPatronal());
			credito.setCrMod(hCredito.getModalidad());
			credito.setCrPer(hCredito.getPeriodo().toString());
			credito.setCrTotalAdeudo(hCredito.getTotalAdeudo().toString());
			credito.setCrSalEyMFija(hCredito.getSalEymFija().toString());
			credito.setCrSalEyMAdy(hCredito.getSalEymAdy().toString());
			credito.setCrSalEyMDin(hCredito.getSalEymDin().toString());
			credito.setCrSaleyMPen(hCredito.getSaleymPen().toString());
			credito.setAct(hCredito.getActualizacion().toString());
			credito.setCrSalGuar(hCredito.getSalGuar().toString());
			credito.setCrSalIV(hCredito.getSalIv().toString());
			credito.setCrSalRt(hCredito.getSalRt().toString());
			credito.setCrSalTot(hCredito.getSalTot().toString());
			credito.setCrInt(hCredito.getIntereses().toString());
			credito.setFactorAct(hCredito.getFactorAct().toString());
			credito.setCrIncAct(hCredito.getIncAct().toString());
			credito.setCrFecNot(hCredito.getFecNot().toString().substring(0,10));
			credito.setCrFecAlta(hCredito.getFecAlta().toString().substring(0,10));
			credito.setCrDoc(hCredito.getTDocumento().toString());
			credito.setCrCred(hCredito.getCredito());
			credito.setRegPatCor(hCredito.getRegPatronalCor());
			credito.setModCor(hCredito.getModalidadCor());
			credito.setTipoNotificacion(hCredito.getTNotificacion().toString());
			credito.setIntAcu(hCredito.getInteAcum().toString());
			credito.setActAcu(hCredito.getActuAcu().toString());

		}
		
		return credito;
	}

	@Override
	public List<Credito> convertListEntiryToListModel(
			List<HCopCreditosTot> hCreditos) {
		List<Credito> creditos = null;
		
		if(hCreditos != null && !hCreditos.isEmpty()) {
			creditos = new ArrayList<Credito>();
			
			for(HCopCreditosTot hCredito: hCreditos) {
				Credito credito = this.convertEntityToModel(hCredito);
				creditos.add(credito);
			}
		}
		
		return creditos;
	}

	@Override
	public CreditoRCV convertirEntityToModelRCV(HRcvCreditosTot hRCredito) {
		CreditoRCV creditoRCV = null;
		if(hRCredito != null) {
			creditoRCV = new CreditoRCV();
			creditoRCV.setTotalAdeudo(hRCredito.getTotalAdeudo().toString());
			creditoRCV.setActua(hRCredito.getActua().toString());
			creditoRCV.setSaldoTotal(hRCredito.getSaldoTotal().toString());
			creditoRCV.setRecar(hRCredito.getRecar().toString());
			creditoRCV.setFecNot(hRCredito.getFecNot().toString().substring(0,10));
			creditoRCV.settDocumento(hRCredito.getTDocumento().toString());
			creditoRCV.setCredito(hRCredito.getCredito().toString());
			creditoRCV.setRegPatronal(hRCredito.getRegPatronal());
			creditoRCV.setModalidad(hRCredito.getModalidad());
			creditoRCV.setPeriodo(hRCredito.getPeriodo().toString());
			creditoRCV.setCyv(hRCredito.getCyv().toString());
			creditoRCV.setCrSalRet(hRCredito.getCrSalRet().toString());
			creditoRCV.setIncAct(hRCredito.getIncAct().toString());
			creditoRCV.setFecAlta(hRCredito.getFecAlta().toString().substring(0,10));
			creditoRCV.setRegPatronalCor(hRCredito.getRegPatronalCor());
			creditoRCV.setModalidadCor(hRCredito.getModalidadCor());
			creditoRCV.settNotificacion(hRCredito.getTNotificacion().toString());
			creditoRCV.setFacAct(hRCredito.getFacAct().toString());
			creditoRCV.setFacRec(hRCredito.getFacRec().toString());
		}
		
		return creditoRCV;
	}

	@Override
	public List<CreditoRCV> convertirListEntityToModelRCV(
			List<HRcvCreditosTot> hRCreditos) {
		List<CreditoRCV> creditos = null;
		
		if(hRCreditos != null && !hRCreditos.isEmpty()) {
			creditos = new ArrayList<CreditoRCV>();
			
			for(HRcvCreditosTot hrCredito: hRCreditos){
				CreditoRCV credito = this.convertirEntityToModelRCV(hrCredito);
				creditos.add(credito);
			}
		}
		
		return creditos;
	}

	
}
