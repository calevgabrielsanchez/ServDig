package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CUESTIONARIO database table.
 * 
 */
@Entity
@Table(name="DIC_CUESTIONARIO")
@OnSearchLlavePrimaria(atributos="cveIdCuestionario")
@ComponentComboCampoDescripcion(atributo="desTitulo")
public class DicCuestionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_CUESTIONARIO_CVEIDCUESTIONARIO_GENERATOR", sequenceName="SEQ_DICCUESTIONARIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIC_CUESTIONARIO_CVEIDCUESTIONARIO_GENERATOR")
	@Column(name="CVE_ID_CUESTIONARIO")
	private long cveIdCuestionario;

	@Column(name="DES_CUESTIONARIO")
	private String desCuestionario;

	@Column(name="DES_TITULO")
	private String desTitulo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicTipoCuestionario
    @ManyToOne
	@JoinColumn(name="CVE_ID_TIPO_CUESTIONARIO")
    @Fetch(FetchMode.JOIN)
	private DicTipoCuestionario dicTipoCuestionario;

	//bi-directional many-to-one association to DicSeccion
	@OneToMany(mappedBy="dicCuestionario", fetch=FetchType.EAGER)
	private List<DicSeccion> dicSecciones;

    public DicCuestionario() {
    }

	public long getCveIdCuestionario() {
		return this.cveIdCuestionario;
	}

	public void setCveIdCuestionario(long cveIdCuestionario) {
		this.cveIdCuestionario = cveIdCuestionario;
	}

	public String getDesCuestionario() {
		return this.desCuestionario;
	}

	public void setDesCuestionario(String desCuestionario) {
		this.desCuestionario = desCuestionario;
	}

	public String getDesTitulo() {
		return this.desTitulo;
	}

	public void setDesTitulo(String desTitulo) {
		this.desTitulo = desTitulo;
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

	public DicTipoCuestionario getDicTipoCuestionario() {
		return this.dicTipoCuestionario;
	}

	public void setDicTipoCuestionario(DicTipoCuestionario dicTipoCuestionario) {
		this.dicTipoCuestionario = dicTipoCuestionario;
	}
	
	public List<DicSeccion> getDicSecciones() {
		return this.dicSecciones;
	}

	public void setDicSecciones(List<DicSeccion> dicSecciones) {
		this.dicSecciones = dicSecciones;
	}
	
}