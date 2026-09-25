package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DG_DOMICILIOS_CAMINOS database table.
 * 
 */
@Entity
@Table(name="DG_DOMICILIOS_CAMINOS")
public class DgDomiciliosCamino implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DOMICILIO_ID", nullable=false, precision=10)
	private long domicilioId;

	@Column(length=255)
	private String cadenamiento;

	@Column(length=255)
	private String destino;

	@Column(nullable=false, length=240)
	private String nomvial;

	@Column(length=255)
	private String origen;

	//bi-directional many-to-one association to DgCatTermGen
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_TER", nullable=false)
	private DgCatTermGen dgCatTermGen;

	//bi-directional many-to-one association to DgCatMargen
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_MARGEN")
	private DgCatMargen dgCatMargen;

	//bi-directional one-to-one association to DgDomicilioGeografico
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOMICILIO_ID", nullable=false, insertable=false, updatable=false)
	private DgDomicilioGeografico dgDomicilioGeografico;

    public DgDomiciliosCamino() {
    }

	public long getDomicilioId() {
		return this.domicilioId;
	}

	public void setDomicilioId(long domicilioId) {
		this.domicilioId = domicilioId;
	}

	public String getCadenamiento() {
		return this.cadenamiento;
	}

	public void setCadenamiento(String cadenamiento) {
		this.cadenamiento = cadenamiento;
	}

	public String getDestino() {
		return this.destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getNomvial() {
		return this.nomvial;
	}

	public void setNomvial(String nomvial) {
		this.nomvial = nomvial;
	}

	public String getOrigen() {
		return this.origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public DgCatTermGen getDgCatTermGen() {
		return this.dgCatTermGen;
	}

	public void setDgCatTermGen(DgCatTermGen dgCatTermGen) {
		this.dgCatTermGen = dgCatTermGen;
	}
	
	public DgCatMargen getDgCatMargen() {
		return this.dgCatMargen;
	}

	public void setDgCatMargen(DgCatMargen dgCatMargen) {
		this.dgCatMargen = dgCatMargen;
	}
	
	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return this.dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}
	
}