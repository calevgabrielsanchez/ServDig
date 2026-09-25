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
 * The persistent class for the DIT_BUZON_CLASIFICACION database table.
 * 
 */
@Entity
@Table(name="DIT_BUZON_CLASIFICACION")
public class DitBuzonClasificacion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DIT_BUZON_CLASIFICACION_GENERATOR", sequenceName = "SEQ_DITBUZONCLASIFICACION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_BUZON_CLASIFICACION_GENERATOR")
	@Column(name="CVE_ID_BUZON")
	private long cveIdBuzon;

	@Column(name="REG_PATRON")
	private String regpatron;

	@Column(name="CVE_ID_TIPO_TRAMITE")
	private Long cveIdTipoTramite;

	@Column(name="REF_FOLIO")
	private String refFolio;

	@Column(name="MENSAJE")
	private String mensaje;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
    
    public DitBuzonClasificacion() {
    }

	public long getCveIdBuzon() {
		return cveIdBuzon;
	}

	public void setCveIdBuzon(long cveIdBuzon) {
		this.cveIdBuzon = cveIdBuzon;
	}

	public String getRegpatron() {
		return regpatron;
	}

	public void setRegpatron(String regpatron) {
		this.regpatron = regpatron;
	}

	public Long getCveIdTipoTramite() {
		return cveIdTipoTramite;
	}

	public void setCveIdTipoTramite(Long cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
	}

	public String getRefFolio() {
		return refFolio;
	}

	public void setRefFolio(String refFolio) {
		this.refFolio = refFolio;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

}