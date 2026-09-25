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


/**
 * The persistent class for the DIC_TIPO_RESPUESTA database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_RESPUESTA")
public class DicTipoRespuesta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_TIPO_RESPUESTA_CVEIDTIPORESPUESTA_GENERATOR", sequenceName="SEQ_DICTIPORESPUESTA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIC_TIPO_RESPUESTA_CVEIDTIPORESPUESTA_GENERATOR")
	@Column(name="CVE_ID_TIPO_RESPUESTA")
	private long cveIdTipoRespuesta;

	@Column(name="DES_ELEMENTO_HTML")
	private String desElementoHtml;

	@Column(name="DES_TIPO_RESPUESTA")
	private String desTipoRespuesta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    public DicTipoRespuesta() {
    }

	public long getCveIdTipoRespuesta() {
		return this.cveIdTipoRespuesta;
	}

	public void setCveIdTipoRespuesta(long cveIdTipoRespuesta) {
		this.cveIdTipoRespuesta = cveIdTipoRespuesta;
	}

	public String getDesElementoHtml() {
		return this.desElementoHtml;
	}

	public void setDesElementoHtml(String desElementoHtml) {
		this.desElementoHtml = desElementoHtml;
	}

	public String getDesTipoRespuesta() {
		return this.desTipoRespuesta;
	}

	public void setDesTipoRespuesta(String desTipoRespuesta) {
		this.desTipoRespuesta = desTipoRespuesta;
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