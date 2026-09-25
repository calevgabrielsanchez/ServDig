package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_MAQUINARIA_EQUIPO database table.
 * 
 */
@Entity
@Table(name="DIT_MAQUINARIA_EQUIPO")
public class DitMaquinariaEquipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_MAQUINARIA_EQUIPO_GENERATOR", sequenceName = "SEQ_DITMAQUINARIAEQUIPO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_MAQUINARIA_EQUIPO_GENERATOR")
	@Column(name="CVE_ID_MAQUINARIA_EQUIPO", nullable=false, precision=22)
	private Long cveIdMaquinariaEquipo;

	@Column(name="DES_CAPACIDAD_POTENCIA", length=255)
	private String desCapacidadPotencia;

	@Column(name="DES_NOMBRE", length=100)
	private String desNombre;

	@Column(name="DES_USO", length=255)
	private String desUso;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_UNIDADES", precision=22)
	private BigDecimal numUnidades;

	//bi-directional many-to-one association to DicTipoMaquinariaEquipo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_MAQUINARIA_EQUIPO")
	private DicTipoMaquinariaEquipo dicTipoMaquinariaEquipo;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

    public DitMaquinariaEquipo() {
    }

	public Long getCveIdMaquinariaEquipo() {
		return this.cveIdMaquinariaEquipo;
	}

	public void setCveIdMaquinariaEquipo(Long cveIdMaquinariaEquipo) {
		this.cveIdMaquinariaEquipo = cveIdMaquinariaEquipo;
	}

	public String getDesCapacidadPotencia() {
		return this.desCapacidadPotencia;
	}

	public void setDesCapacidadPotencia(String desCapacidadPotencia) {
		this.desCapacidadPotencia = desCapacidadPotencia;
	}

	public String getDesNombre() {
		return this.desNombre;
	}

	public void setDesNombre(String desNombre) {
		this.desNombre = desNombre;
	}

	public String getDesUso() {
		return this.desUso;
	}

	public void setDesUso(String desUso) {
		this.desUso = desUso;
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

	public BigDecimal getNumUnidades() {
		return this.numUnidades;
	}

	public void setNumUnidades(BigDecimal numUnidades) {
		this.numUnidades = numUnidades;
	}

	public DicTipoMaquinariaEquipo getDicTipoMaquinariaEquipo() {
		return this.dicTipoMaquinariaEquipo;
	}

	public void setDicTipoMaquinariaEquipo(DicTipoMaquinariaEquipo dicTipoMaquinariaEquipo) {
		this.dicTipoMaquinariaEquipo = dicTipoMaquinariaEquipo;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
}