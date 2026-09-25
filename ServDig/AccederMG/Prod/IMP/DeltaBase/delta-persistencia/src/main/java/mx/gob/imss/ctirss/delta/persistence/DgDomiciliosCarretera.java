package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DG_DOMICILIOS_CARRETERAS database table.
 * 
 */
@Entity
@Table(name="DG_DOMICILIOS_CARRETERAS")
public class DgDomiciliosCarretera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DOMICILIO_ID", nullable=false, precision=10)
	private long domicilioId;

	@Column(length=255)
	private String cadenamiento;

	@Column(length=255)
	private String destino;

	@Column(nullable=false, length=255)
	private String nomvial;

	@Column(length=255)
	private String origen;
	
	@Column(name = "CODIGO", precision = 5, scale = 0)
	private Integer codigo;
	
	
	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	//bi-directional many-to-one association to DgCatDerechosTransito
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_CDT")
	private DgCatDerechosTransito dgCatDerechosTransito;

	//bi-directional many-to-one association to DgCatAdministracion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_CAC")
	private DgCatAdministracion dgCatAdministracion;

	//bi-directional one-to-one association to DgDomicilioGeografico
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOMICILIO_ID", nullable=false, insertable=false, updatable=false)
	private DgDomicilioGeografico dgDomicilioGeografico;
	
	//bi-directional many-to-one association to DgCatTermGen
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_TER", nullable=false)
	private DgCatTermGen dgCatTermGen;


    public DgCatTermGen getDgCatTermGen() {
		return dgCatTermGen;
	}

	public void setDgCatTermGen(DgCatTermGen dgCatTermGen) {
		this.dgCatTermGen = dgCatTermGen;
	}

	public DgDomiciliosCarretera() {
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

	public DgCatDerechosTransito getDgCatDerechosTransito() {
		return this.dgCatDerechosTransito;
	}

	public void setDgCatDerechosTransito(DgCatDerechosTransito dgCatDerechosTransito) {
		this.dgCatDerechosTransito = dgCatDerechosTransito;
	}
	
	public DgCatAdministracion getDgCatAdministracion() {
		return this.dgCatAdministracion;
	}

	public void setDgCatAdministracion(DgCatAdministracion dgCatAdministracion) {
		this.dgCatAdministracion = dgCatAdministracion;
	}
	
	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return this.dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}
	
}