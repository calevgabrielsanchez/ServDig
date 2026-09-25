package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIC_EJECUTORES database table.
 * 
 */
@Entity
@Table(name="DIC_EJECUTORES")
public class DicEjecutore implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DicEjecutorePK id;

	@Column(name="CVE_TIPO_CON", length=1)
	private String cveTipoCon;

	@Column(name="CVE_USUARIO", length=8)
	private String cveUsuario;

	@Column(name="CVE_VIGENCIA", length=1)
	private String cveVigencia;

	@Column(name="DES_NOM_EJEC", length=50)
	private String desNomEjec;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CAPTURA")
	private Date fecCaptura;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FINAL")
	private Date fecFinal;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO")
	private Date fecInicio;

    @Temporal( TemporalType.DATE)
	@Column(name="HORA_CAPTURA")
	private Date horaCaptura;

    public DicEjecutore() {
    }

	public DicEjecutorePK getId() {
		return this.id;
	}

	public void setId(DicEjecutorePK id) {
		this.id = id;
	}
	
	public String getCveTipoCon() {
		return this.cveTipoCon;
	}

	public void setCveTipoCon(String cveTipoCon) {
		this.cveTipoCon = cveTipoCon;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public String getCveVigencia() {
		return this.cveVigencia;
	}

	public void setCveVigencia(String cveVigencia) {
		this.cveVigencia = cveVigencia;
	}

	public String getDesNomEjec() {
		return this.desNomEjec;
	}

	public void setDesNomEjec(String desNomEjec) {
		this.desNomEjec = desNomEjec;
	}

	public Date getFecCaptura() {
		return this.fecCaptura;
	}

	public void setFecCaptura(Date fecCaptura) {
		this.fecCaptura = fecCaptura;
	}

	public Date getFecFinal() {
		return this.fecFinal;
	}

	public void setFecFinal(Date fecFinal) {
		this.fecFinal = fecFinal;
	}

	public Date getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getHoraCaptura() {
		return this.horaCaptura;
	}

	public void setHoraCaptura(Date horaCaptura) {
		this.horaCaptura = horaCaptura;
	}

}