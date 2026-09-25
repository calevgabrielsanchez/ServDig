package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * The persistent class for the DIC_PREGUNTA database table.
 * 
 */
@Entity
@Table(name="DIC_PREGUNTA")
public class DicPregunta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_PREGUNTA_CVEIDPREGUNTA_GENERATOR", sequenceName="SEQ_DICPREGUNTA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIC_PREGUNTA_CVEIDPREGUNTA_GENERATOR")
	@Column(name="CVE_ID_PREGUNTA")
	private long cveIdPregunta;

	@Column(name="DES_PREGUNTA")
	private String desPregunta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_OBLIGATORIA")
	private Integer indObligatoria;

	@Column(name="NUM_PREGUNTA")
	private String numPregunta;

	//bi-directional many-to-one association to DicOpcionPregunta
	@OneToMany(mappedBy="dicPregunta", fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<DicOpcionPregunta> dicOpcionPreguntas;

	//bi-directional many-to-one association to DicSeccion
    @ManyToOne
	@JoinColumn(name="CVE_ID_SECCION")
	private DicSeccion dicSeccion;

	//bi-directional many-to-one association to DicTipoRespuesta
    @ManyToOne
	@JoinColumn(name="CVE_ID_TIPO_RESPUESTA")
	private DicTipoRespuesta dicTipoRespuesta;

    public DicPregunta() {
    }

	public long getCveIdPregunta() {
		return this.cveIdPregunta;
	}

	public void setCveIdPregunta(long cveIdPregunta) {
		this.cveIdPregunta = cveIdPregunta;
	}

	public String getDesPregunta() {
		return this.desPregunta;
	}

	public void setDesPregunta(String desPregunta) {
		this.desPregunta = desPregunta;
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

	public Integer getIndObligatoria() {
		return this.indObligatoria;
	}

	public void setIndObligatoria(Integer indObligatoria) {
		this.indObligatoria = indObligatoria;
	}

	public String getNumPregunta() {
		return this.numPregunta;
	}

	public void setNumPregunta(String numPregunta) {
		this.numPregunta = numPregunta;
	}
	
	public List<DicOpcionPregunta> getDicOpcionPreguntas() {
		return this.dicOpcionPreguntas;
	}

	public void setDicOpcionPreguntas(List<DicOpcionPregunta> dicOpcionPreguntas) {
		this.dicOpcionPreguntas = dicOpcionPreguntas;
	}
	
	public DicSeccion getDicSeccion() {
		return this.dicSeccion;
	}

	public void setDicSeccion(DicSeccion dicSeccion) {
		this.dicSeccion = dicSeccion;
	}
	
	public DicTipoRespuesta getDicTipoRespuesta() {
		return this.dicTipoRespuesta;
	}

	public void setDicTipoRespuesta(DicTipoRespuesta dicTipoRespuesta) {
		this.dicTipoRespuesta = dicTipoRespuesta;
	}
	
}