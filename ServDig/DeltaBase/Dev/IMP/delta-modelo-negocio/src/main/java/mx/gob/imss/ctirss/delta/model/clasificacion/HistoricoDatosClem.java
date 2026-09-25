package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.sql.Timestamp;

public class HistoricoDatosClem extends DatosClem {

	Long cveIdHistDatosClem;
	Long cveIdTipoCausa;
	Timestamp stpHistDatosClem;
	String desCausa;
	
	public Long getCveIdHistDatosClem() {
		return cveIdHistDatosClem;
	}
	public void setCveIdHistDatosClem(Long cveIdHistDatosClem) {
		this.cveIdHistDatosClem = cveIdHistDatosClem;
	}
	public Long getCveIdTipoCausa() {
		return cveIdTipoCausa;
	}
	public void setCveIdTipoCausa(Long cveIdTipoCausa) {
		this.cveIdTipoCausa = cveIdTipoCausa;
	}
	public Timestamp getStpHistDatosClem() {
		return stpHistDatosClem;
	}
	public void setStpHistDatosClem(Timestamp stpHistDatosClem) {
		this.stpHistDatosClem = stpHistDatosClem;
	}
	public String getDesCausa() {
		return desCausa;
	}
	public void setDesCausa(String desCausa) {
		this.desCausa = desCausa;
	}
	@Override
	public String toString() {
		return "HistoricoDatosClem [cveIdHistDatosClem=" + cveIdHistDatosClem
				+ ", cveIdTipoCausa=" + cveIdTipoCausa + ", desCausa="
				+ desCausa + ", stpHistDatosClem=" + stpHistDatosClem + "]";
	}
	
}