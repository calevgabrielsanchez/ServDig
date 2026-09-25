package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIT_BENEFICIO")
public class DitBeneficio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_BENEFICIO_CVEIDBENEFICIO_GENERATOR", sequenceName = "SEQ_DITBENEFICIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_BENEFICIO_CVEIDBENEFICIO_GENERATOR")
	@Column(name = "CVE_ID_BENEFICIO")
	private Long cveIdBeneficio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_INICIO_VIGENCIA")
	private Date fecInicioVigencia;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FIN_VIGENCIA")
	private Date fecFinVigencia;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	// bi-directional many-to-one association to DicBeneficioCancelacion
	@ManyToOne
	@JoinColumn(name = "CVE_ID_BENEFICIO_CANCELACION")
	private DicBeneficioCancelacion dicBeneficioCancelacion;

	// bi-directional many-to-one association to DicTipoBeneficio
	@ManyToOne
	@JoinColumn(name = "CVE_ID_TIPO_BENEFICIO")
	private DicTipoBeneficio dicTipoBeneficio;

	// bi-directional many-to-one association to DitPersonaBeneficio
	@OneToMany(mappedBy = "ditBeneficio", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<DitPersonaBeneficio> ditPersonaBeneficios;

	// bi-directional many-to-one association to DitPatSujObligBeneficio
	@OneToMany(mappedBy = "ditBeneficio", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<DitPatSujObligBeneficio> ditPatSujObligBeneficios;

	// bi-directional many-to-one association to DitBeneficioRiss
	@OneToMany(mappedBy = "ditBeneficio", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<DitBeneficioRiss> ditBeneficiosRiss;
	
	// bi-directional many-to-one association to DitDescuentoBeneficio
	@OneToMany(mappedBy = "ditBeneficio", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<DitDescuentoBeneficio> ditDescuentoBeneficios;
	
	public DitBeneficio() {
	}

	public DitBeneficio(Long cveIdBeneficio, Date fecInicioVigencia,
			Date fecFinVigencia, Date fecRegistroAlta, Date fecRegistroBaja,
			Date fecRegistroActualizado,
			DicBeneficioCancelacion dicBeneficioCancelacion,			
			DicTipoBeneficio dicTipoBeneficio) {
		super();
		this.cveIdBeneficio = cveIdBeneficio;
		this.fecInicioVigencia = fecInicioVigencia;
		this.fecFinVigencia = fecFinVigencia;
		this.fecRegistroAlta = fecRegistroAlta;
		this.fecRegistroBaja = fecRegistroBaja;
		this.fecRegistroActualizado = fecRegistroActualizado;
		this.dicBeneficioCancelacion = dicBeneficioCancelacion;		
		this.dicTipoBeneficio = dicTipoBeneficio;
	}

	public Long getCveIdBeneficio() {
		return this.cveIdBeneficio;
	}

	public void setCveIdBeneficio(Long cveIdBeneficio) {
		this.cveIdBeneficio = cveIdBeneficio;
	}

	public Date getFecInicioVigencia() {
		return this.fecInicioVigencia;
	}

	public void setFecInicioVigencia(Date fecInicioVigencia) {
		this.fecInicioVigencia = fecInicioVigencia;
	}

	public Date getFecFinVigencia() {
		return this.fecFinVigencia;
	}

	public void setFecFinVigencia(Date fecFinVigencia) {
		this.fecFinVigencia = fecFinVigencia;
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

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public DicBeneficioCancelacion getDicBeneficioCancelacion() {
		return this.dicBeneficioCancelacion;
	}

	public void setDicBeneficioCancelacion(
			DicBeneficioCancelacion dicBeneficioCancelacion) {
		this.dicBeneficioCancelacion = dicBeneficioCancelacion;
	}

	public DicTipoBeneficio getDicTipoBeneficio() {
		return this.dicTipoBeneficio;
	}

	public void setDicTipoBeneficio(DicTipoBeneficio dicTipoBeneficio) {
		this.dicTipoBeneficio = dicTipoBeneficio;
	}

	public List<DitPersonaBeneficio> getDitPersonaBeneficios() {
		return this.ditPersonaBeneficios;
	}

	public void setDitPersonaBeneficios(
			List<DitPersonaBeneficio> ditPersonaBeneficios) {
		this.ditPersonaBeneficios = ditPersonaBeneficios;
	}

	public List<DitPatSujObligBeneficio> getDitPatSujObligBeneficios() {
		return this.ditPatSujObligBeneficios;
	}

	public void setDitPatSujObligBeneficios(
			List<DitPatSujObligBeneficio> ditPatSujObligBeneficios) {
		this.ditPatSujObligBeneficios = ditPatSujObligBeneficios;
	}

	public List<DitBeneficioRiss> getDitBeneficiosRiss() {
		return ditBeneficiosRiss;
	}

	public void setDitBeneficiosRiss(List<DitBeneficioRiss> ditBeneficiosRiss) {
		this.ditBeneficiosRiss = ditBeneficiosRiss;
	}

	public List<DitDescuentoBeneficio> getDitDescuentoBeneficios() {
		return ditDescuentoBeneficios;
	}

	public void setDitDescuentoBeneficios(
			List<DitDescuentoBeneficio> ditDescuentoBeneficios) {
		this.ditDescuentoBeneficios = ditDescuentoBeneficios;
	}

}