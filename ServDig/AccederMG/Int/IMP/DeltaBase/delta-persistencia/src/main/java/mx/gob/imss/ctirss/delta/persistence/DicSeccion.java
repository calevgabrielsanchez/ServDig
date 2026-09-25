package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_SECCION database table.
 * 
 */
@Entity
@Table(name="DIC_SECCION")
public class DicSeccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_SECCION_CVEIDSECCION_GENERATOR", sequenceName="SEQ_DICSECCION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIC_SECCION_CVEIDSECCION_GENERATOR")
	@Column(name="CVE_ID_SECCION")
	private long cveIdSeccion;

	@Column(name="DES_SECCION")
	private String desSeccion;

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

	//bi-directional many-to-one association to DicPregunta
	@OneToMany(mappedBy="dicSeccion", fetch=FetchType.EAGER)
	private List<DicPregunta> dicPreguntas;

	//bi-directional many-to-one association to DicCuestionario
    @ManyToOne
	@JoinColumn(name="CVE_ID_CUESTIONARIO")
	private DicCuestionario dicCuestionario;

    public DicSeccion() {
    }

	public long getCveIdSeccion() {
		return this.cveIdSeccion;
	}

	public void setCveIdSeccion(long cveIdSeccion) {
		this.cveIdSeccion = cveIdSeccion;
	}

	public String getDesSeccion() {
		return this.desSeccion;
	}

	public void setDesSeccion(String desSeccion) {
		this.desSeccion = desSeccion;
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

	public List<DicPregunta> getDicPreguntas() {
		return this.dicPreguntas;
	}

	public void setDicPreguntas(List<DicPregunta> dicPreguntas) {
		this.dicPreguntas = dicPreguntas;
	}
	
	public DicCuestionario getDicCuestionario() {
		return this.dicCuestionario;
	}

	public void setDicCuestionario(DicCuestionario dicCuestionario) {
		this.dicCuestionario = dicCuestionario;
	}
	
}