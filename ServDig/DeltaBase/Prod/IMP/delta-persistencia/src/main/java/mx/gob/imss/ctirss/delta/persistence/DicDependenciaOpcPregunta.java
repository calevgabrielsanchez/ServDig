package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIC_DEPENDENCIA_OPC_PREGUNTA database table.
 * 
 */
@Entity
@Table(name="DIC_DEPENDENCIA_OPC_PREGUNTA")
public class DicDependenciaOpcPregunta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_DEPENDENCIA_OPC_PREGUNTA_CVEIDDEPOPCPREGUNTA_GENERATOR", sequenceName="SEQ_DICDEPENDENCIAOPCPREGUNTA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIC_DEPENDENCIA_OPC_PREGUNTA_CVEIDDEPOPCPREGUNTA_GENERATOR")
	@Column(name="CVE_ID_DEP_OPC_PREGUNTA")
	private long cveIdDepOpcPregunta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicOpcionPregunta
    @ManyToOne
	@JoinColumn(name="CVE_ID_OPCION_PREGUNTA")
	private DicOpcionPregunta dicOpcionPregunta;

	//bi-directional many-to-one association to DicPregunta
    @ManyToOne
	@JoinColumn(name="CVE_ID_PREGUNTA_DEP")
	private DicPregunta dicPregunta;

    public DicDependenciaOpcPregunta() {
    }

	public long getCveIdDepOpcPregunta() {
		return this.cveIdDepOpcPregunta;
	}

	public void setCveIdDepOpcPregunta(long cveIdDepOpcPregunta) {
		this.cveIdDepOpcPregunta = cveIdDepOpcPregunta;
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

	public DicOpcionPregunta getDicOpcionPregunta() {
		return this.dicOpcionPregunta;
	}

	public void setDicOpcionPregunta(DicOpcionPregunta dicOpcionPregunta) {
		this.dicOpcionPregunta = dicOpcionPregunta;
	}
	
	public DicPregunta getDicPregunta() {
		return this.dicPregunta;
	}

	public void setDicPregunta(DicPregunta dicPregunta) {
		this.dicPregunta = dicPregunta;
	}
	
}