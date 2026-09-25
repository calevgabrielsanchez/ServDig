package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.delta.persistence.SpcDiagnosticoCtaindividual;

@Entity
@Table(name="SPT_DIAGNOSTICOS_INDICADORES")
@NamedQuery(name = "SptDiagnosticosIndicadores.findAll", query = "SELECT s FROM SptDiagnosticosIndicadores s")
public class SptDiagnosticosIndicadores  implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDIAGNOSTICOSINDICADORES", sequenceName = "SEQ_SPTDIAGNOSTICOSINDICADORES")
	@GeneratedValue(generator = "SEQ_SPTDIAGNOSTICOSINDICADORES")
	@Column(name="CVE_DIAGNOSTICO_INDICADORES")
	private long cveDiagnosticoIndicadores;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_FLUJO", precision=1)
	private int indFlujo;

	@Column(name="IND_TIPO_PROCESO", precision=1)
	private int indTipoProceso;

	//bi-directional many-to-one association to SpcDiagnosticoCtaindividual
    @ManyToOne
	@JoinColumn(name="ID_DIAGNOSTICO_CTAINDIVIDUAL")
	private SpcDiagnosticoCtaindividual spcDiagnosticoCtaindividual;

    public SptDiagnosticosIndicadores() {
    }

	public long getCveDiagnosticoIndicadores() {
		return this.cveDiagnosticoIndicadores;
	}

	public void setCveDiagnosticoIndicadores(long cveDiagnosticoIndicadores) {
		this.cveDiagnosticoIndicadores = cveDiagnosticoIndicadores;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public int getIndFlujo() {
		return this.indFlujo;
	}

	public void setIndFlujo(int indFlujo) {
		this.indFlujo = indFlujo;
	}

	public int getIndTipoProceso() {
		return this.indTipoProceso;
	}

	public void setIndTipoProceso(int indTipoProceso) {
		this.indTipoProceso = indTipoProceso;
	}

	public SpcDiagnosticoCtaindividual getSpcDiagnosticoCtaindividual() {
		return this.spcDiagnosticoCtaindividual;
	}

	public void setSpcDiagnosticoCtaindividual(SpcDiagnosticoCtaindividual spcDiagnosticoCtaindividual) {
		this.spcDiagnosticoCtaindividual = spcDiagnosticoCtaindividual;
	}

}
