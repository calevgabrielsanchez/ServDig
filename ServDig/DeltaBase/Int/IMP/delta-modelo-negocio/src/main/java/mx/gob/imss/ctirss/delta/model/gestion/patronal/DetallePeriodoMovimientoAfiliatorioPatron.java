package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class DetallePeriodoMovimientoAfiliatorioPatron extends AbstractModel{
	
	
	private static final long serialVersionUID = -634165917009833195L;
	private PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio;
	private SujetoObligado sujetoObligado;
	private Long cveIdPatronGeneral;
	
	public Long getCveIdPatronGeneral() {
		return cveIdPatronGeneral;
	}

	public void setCveIdPatronGeneral(Long cveIdPatronGeneral) {
		this.cveIdPatronGeneral = cveIdPatronGeneral;
	}

	public DetallePeriodoMovimientoAfiliatorioPatron(){
		
	};
	
	public PeriodoMovimientoAfiliatorio getPeriodoMovimientoAfiliatorio() {
		return periodoMovimientoAfiliatorio;
	}
	public void setPeriodoMovimientoAfiliatorio(PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio) {
		this.periodoMovimientoAfiliatorio = periodoMovimientoAfiliatorio;
	}
	public SujetoObligado getSujetoObligado() {
		return sujetoObligado;
	}
	public void setSujetoObligado(SujetoObligado sujetoObligado) {
		this.sujetoObligado = sujetoObligado;
	}
	
	

}
