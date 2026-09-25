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
 * The persistent class for the DIC_OPCION_PREGUNTA database table.
 * 
 */
@Entity
@Table(name="DIC_OPCION_PREGUNTA")
public class DicOpcionPregunta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_OPCION_PREGUNTA_CVEIDOPCIONPREGUNTA_GENERATOR", sequenceName="SEQ_DICOPCIONPREGUNTA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIC_OPCION_PREGUNTA_CVEIDOPCIONPREGUNTA_GENERATOR")
	@Column(name="CVE_ID_OPCION_PREGUNTA")
	private long cveIdOpcionPregunta;

	@Column(name="DES_OPC_PREGUNTA")
	private String desOpcPregunta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_DEPENDENCIAS")
	private Integer indDependencias;

	@Column(name="NUM_VALOR")
	private Integer numValor;

	//bi-directional many-to-one association to DicDependenciaOpcPregunta
	@OneToMany(mappedBy="dicOpcionPregunta", fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<DicDependenciaOpcPregunta> dicDependenciaOpcPreguntas;

	//bi-directional many-to-one association to DicPregunta
    @ManyToOne
	@JoinColumn(name="CVE_ID_PREGUNTA")
	private DicPregunta dicPregunta;

    public DicOpcionPregunta() {
    }

	public long getCveIdOpcionPregunta() {
		return this.cveIdOpcionPregunta;
	}

	public void setCveIdOpcionPregunta(long cveIdOpcionPregunta) {
		this.cveIdOpcionPregunta = cveIdOpcionPregunta;
	}

	public String getDesOpcPregunta() {
		return this.desOpcPregunta;
	}

	public void setDesOpcPregunta(String desOpcPregunta) {
		this.desOpcPregunta = desOpcPregunta;
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

	public Integer getIndDependencias() {
		return this.indDependencias;
	}

	public void setIndDependencias(Integer indDependencias) {
		this.indDependencias = indDependencias;
	}

	public Integer getNumValor() {
		return this.numValor;
	}

	public void setNumValor(Integer numValor) {
		this.numValor = numValor;
	}

	public List<DicDependenciaOpcPregunta> getDicDependenciaOpcPreguntas() {
		return this.dicDependenciaOpcPreguntas;
	}

	public void setDicDependenciaOpcPreguntas(List<DicDependenciaOpcPregunta> dicDependenciaOpcPreguntas) {
		this.dicDependenciaOpcPreguntas = dicDependenciaOpcPreguntas;
	}
	
	public DicPregunta getDicPregunta() {
		return this.dicPregunta;
	}

	public void setDicPregunta(DicPregunta dicPregunta) {
		this.dicPregunta = dicPregunta;
	}
	
}