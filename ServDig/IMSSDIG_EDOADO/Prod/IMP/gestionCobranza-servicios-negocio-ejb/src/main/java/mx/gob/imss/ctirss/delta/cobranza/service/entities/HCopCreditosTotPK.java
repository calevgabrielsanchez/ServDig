package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class HCopCreditosTotPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1739423211430597689L;

	@Column(name = "CREDITO")
	private String credito;
	
	@Column(name="REG_PATRONAL")
	private String regPatronal;

	@Column(name="REG_PATRONAL_COR")
	private String regPatronalCor;
	
	@Column(name = "MODALIDAD")
	private String modalidad;

	@Column(name="MODALIDAD_COR")
	private String modalidadCor;
	
	@Column(name = "PERIODO")
	private Long periodo;

	public String getCredito() {
		return credito;
	}

	public void setCredito(String credito) {
		this.credito = credito;
	}

	public String getRegPatronal() {
		return regPatronal;
	}

	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}

	public String getRegPatronalCor() {
		return regPatronalCor;
	}

	public void setRegPatronalCor(String regPatronalCor) {
		this.regPatronalCor = regPatronalCor;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getModalidadCor() {
		return modalidadCor;
	}

	public void setModalidadCor(String modalidadCor) {
		this.modalidadCor = modalidadCor;
	}

	public Long getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}
	
	
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (this.getClass() != o.getClass()) { return false; }
        HCopCreditosTotPK other = (HCopCreditosTotPK)o;
        return new EqualsBuilder()
                .append(this.credito, other.credito)
                .append(this.regPatronal, other.regPatronal)
                .append(this.regPatronalCor, other.regPatronalCor)
                .append(this.modalidad, other.modalidad)
                .append(this.modalidadCor, other.modalidadCor)
                .append(this.periodo, other.periodo)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 43)
                .append(credito)
                .append(regPatronal)
                .append(regPatronalCor)
                .append(modalidad)
                .append(modalidadCor)
                .append(periodo)
                .hashCode();
    }

}
