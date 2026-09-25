package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DIC_CONSULTORIO_UMF database table.
 * 
 */
@Entity
@Table(name="DIC_CONSULTORIO_UMF")
public class DicConsultorioUmf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_UMF_CONSULTORIO")
	private long cveIdUmfConsultorio;

	@Column(name="CVE_NUM_CONSULTORIO")
	private BigDecimal cveNumConsultorio;

	@Column(name="DES_CONSULTORIO")
	private String desConsultorio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_MATRICULA")
	private String numMatricula;

	//bi-directional many-to-one association to DicUmf
    @ManyToOne
	@JoinColumn(name="CVE_ID_UMF")
	private DicUmf dicUmf;

	//bi-directional many-to-one association to DitUmfConsultorioTurno
	@OneToMany(mappedBy="dicConsultorioUmf")
	private Set<DitUmfConsultorioTurno> ditUmfConsultorioTurnos;
	
	@Column(name = "IND_VIRTUAL")
	private BigDecimal indVirtual;

    public DicConsultorioUmf() {
    }

	public long getCveIdUmfConsultorio() {
		return this.cveIdUmfConsultorio;
	}

	public void setCveIdUmfConsultorio(long cveIdUmfConsultorio) {
		this.cveIdUmfConsultorio = cveIdUmfConsultorio;
	}

	public BigDecimal getCveNumConsultorio() {
		return this.cveNumConsultorio;
	}

	public void setCveNumConsultorio(BigDecimal cveNumConsultorio) {
		this.cveNumConsultorio = cveNumConsultorio;
	}

	public String getDesConsultorio() {
		return this.desConsultorio;
	}

	public void setDesConsultorio(String desConsultorio) {
		this.desConsultorio = desConsultorio;
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

	public String getNumMatricula() {
		return this.numMatricula;
	}

	public void setNumMatricula(String numMatricula) {
		this.numMatricula = numMatricula;
	}

	public DicUmf getDicUmf() {
		return this.dicUmf;
	}

	public void setDicUmf(DicUmf dicUmf) {
		this.dicUmf = dicUmf;
	}
	
	public Set<DitUmfConsultorioTurno> getDitUmfConsultorioTurnos() {
		return this.ditUmfConsultorioTurnos;
	}

	public void setDitUmfConsultorioTurnos(Set<DitUmfConsultorioTurno> ditUmfConsultorioTurnos) {
		this.ditUmfConsultorioTurnos = ditUmfConsultorioTurnos;
	}

	public BigDecimal getIndVirtual() {
		return indVirtual;
	}

	public void setIndVirtual(BigDecimal indVirtual) {
		this.indVirtual = indVirtual;
	}
	
}