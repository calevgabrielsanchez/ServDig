package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SPC_DIAGNOSTICO_CTAINDIVIDUAL database table.
 * 
 */
@Entity
@Table(name="SPC_DIAGNOSTICO_CTAINDIVIDUAL")
@NamedQuery(name="SpcDiagnosticoCtaindividual.findAll", query="SELECT s FROM SpcDiagnosticoCtaindividual s")
public class SpcDiagnosticoCtaindividual implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_DIAGNOSTICO_CTAINDIVIDUAL_IDDIAGNOSTICOCTAINDIVIDUAL_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_DIAGNOSTICO_CTAINDIVIDUAL_IDDIAGNOSTICOCTAINDIVIDUAL_GENERATOR")
	@Column(name="ID_DIAGNOSTICO_CTAINDIVIDUAL")
	private String idDiagnosticoCtaindividual;

	@Column(name="DES_DIAGNOSTICO_CTAINDIVIDUAL")
	private String desDiagnosticoCtaindividual;

	@Column(name="IND_DIAGNOSTICO_VALIDO")
	private String indDiagnosticoValido;

	public SpcDiagnosticoCtaindividual() {
	}

	public String getIdDiagnosticoCtaindividual() {
		return this.idDiagnosticoCtaindividual;
	}

	public void setIdDiagnosticoCtaindividual(String idDiagnosticoCtaindividual) {
		this.idDiagnosticoCtaindividual = idDiagnosticoCtaindividual;
	}

	public String getDesDiagnosticoCtaindividual() {
		return this.desDiagnosticoCtaindividual;
	}

	public void setDesDiagnosticoCtaindividual(String desDiagnosticoCtaindividual) {
		this.desDiagnosticoCtaindividual = desDiagnosticoCtaindividual;
	}

	public String getIndDiagnosticoValido() {
		return this.indDiagnosticoValido;
	}

	public void setIndDiagnosticoValido(String indDiagnosticoValido) {
		this.indDiagnosticoValido = indDiagnosticoValido;
	}

}