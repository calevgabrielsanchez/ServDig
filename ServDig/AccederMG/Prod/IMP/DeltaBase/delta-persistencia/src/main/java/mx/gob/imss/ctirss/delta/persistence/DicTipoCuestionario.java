package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the DIC_TIPO_CUESTIONARIO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_CUESTIONARIO")
@OnSearchLlavePrimaria(atributos="cveIdTipoCuestionario")
@ComponentComboCampoDescripcion(atributo="desTipoCuestionario")
public class DicTipoCuestionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_TIPO_CUESTIONARIO_CVEIDTIPOCUESTIONARIO_GENERATOR", sequenceName="SEQ_DICTIPOCUESTIONARIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIC_TIPO_CUESTIONARIO_CVEIDTIPOCUESTIONARIO_GENERATOR")
	@Column(name="CVE_ID_TIPO_CUESTIONARIO")
	private long cveIdTipoCuestionario;

	@Column(name="DES_TIPO_CUESTIONARIO")
	private String desTipoCuestionario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    public DicTipoCuestionario() {
    }

	public long getCveIdTipoCuestionario() {
		return this.cveIdTipoCuestionario;
	}

	public void setCveIdTipoCuestionario(long cveIdTipoCuestionario) {
		this.cveIdTipoCuestionario = cveIdTipoCuestionario;
	}

	public String getDesTipoCuestionario() {
		return this.desTipoCuestionario;
	}

	public void setDesTipoCuestionario(String desTipoCuestionario) {
		this.desTipoCuestionario = desTipoCuestionario;
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
}