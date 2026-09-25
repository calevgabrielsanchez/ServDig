package mx.imss.estrados.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="NEE_CAT_SUJETO_A_NOTIFICAR")
public class NeeCatSujetoANotificar implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5986440599732423078L;

	/**
	 * 
	 */
	

	public NeeCatSujetoANotificar() {
	}
	
	@Id
	@Column(name="CVE_SUJETO_A_NOTIFICAR")
	private Integer cveSujetoANotificar;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_PADRE")
	private NeeCatSujetoANotificar neeCatSujetoANotificar;
	
	@Column(name="DES_DIRIGIDO_A")
	private String desDirigidoA;

	public Integer getCveSujetoANotificar() {
		return cveSujetoANotificar;
	}

	public void setCveSujetoANotificar(Integer cveSujetoANotificar) {
		this.cveSujetoANotificar = cveSujetoANotificar;
	}

	public NeeCatSujetoANotificar getNeeCatSujetoANotificar() {
		return neeCatSujetoANotificar;
	}

	public void setNeeCatSujetoANotificar(
			NeeCatSujetoANotificar neeCatSujetoANotificar) {
		this.neeCatSujetoANotificar = neeCatSujetoANotificar;
	}

	public String getDesDirigidoA() {
		return desDirigidoA;
	}

	public void setDesDirigidoA(String desDirigidoA) {
		this.desDirigidoA = desDirigidoA;
	}

}
