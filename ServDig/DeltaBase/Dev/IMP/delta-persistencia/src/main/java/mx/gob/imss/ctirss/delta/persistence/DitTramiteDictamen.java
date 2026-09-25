package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DIT_TRAMITE_DICTAMEN")
public class DitTramiteDictamen implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4890280339779364485L;
	@EmbeddedId
	private DitTramiteDictamenPK id;
	@ManyToOne
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO",insertable=false,updatable=false)
	private DitPatronSujetoObligado ditPatronSujetoObligado;
	@Column(name= "CVE_ID_TRAMITE",insertable=false,updatable=false)
	private Long cveIdTramite;
	@Column(name= "CVE_ID_EJER_FISCAL",insertable=false,updatable=false)
	private Long cveIdEjercicioFiscal;
	@Column(name= "CVE_ID_PATRON_DICTAMEN", nullable = false)
	private Long cveIdPatronDictamen;
	

	public DitTramiteDictamenPK getId() {
		return id;
	}

	public void setId(DitTramiteDictamenPK id) {
		this.id = id;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(
			DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}

	public Long getCveIdTramite() {
		return cveIdTramite;
	}
	
	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}
	
	public Long getCveIdEjercicioFiscal() {
		return cveIdEjercicioFiscal;
	}
	
	public void setCveIdEjercicioFiscal(Long cveIdEjercicioFiscal) {
		this.cveIdEjercicioFiscal = cveIdEjercicioFiscal;
	}
	
	public Long getCveIdPatronDictamen() {
		return cveIdPatronDictamen;
	}
	
	public void setCveIdPatronDictamen(Long cveIdPatronDictamen) {
		this.cveIdPatronDictamen = cveIdPatronDictamen;
	}

}