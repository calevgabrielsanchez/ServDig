package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_CIUDADANO_CURP_CORREO database table.
 * 
 */
@Entity
@Table(name="DIT_CIUDADANO_CURP_CORREO")
@NamedQuery(name="DitCiudadanoCurpCorreo.findAll", query="SELECT d FROM DitCiudadanoCurpCorreo d")
public class DitCiudadanoCurpCorreo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CIUDADANO_CURP_CORREO")
	private long cveIdCiudadanoCurpCorreo;

	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private String fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_ACEPTO_TERMINOS_CONDICIONE")
	private BigDecimal indAceptoTerminosCondicione;

	@Column(name="REF_CORREO_ELECTRONICO")
	private String refCorreoElectronico;

	@Column(name="REF_CURP")
	private String refCurp;

	public DitCiudadanoCurpCorreo() {
	}

	public long getCveIdCiudadanoCurpCorreo() {
		return this.cveIdCiudadanoCurpCorreo;
	}

	public void setCveIdCiudadanoCurpCorreo(long cveIdCiudadanoCurpCorreo) {
		this.cveIdCiudadanoCurpCorreo = cveIdCiudadanoCurpCorreo;
	}

	public String getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(String fecRegistroActualizado) {
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

	public BigDecimal getIndAceptoTerminosCondicione() {
		return this.indAceptoTerminosCondicione;
	}

	public void setIndAceptoTerminosCondicione(BigDecimal indAceptoTerminosCondicione) {
		this.indAceptoTerminosCondicione = indAceptoTerminosCondicione;
	}

	public String getRefCorreoElectronico() {
		return this.refCorreoElectronico;
	}

	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}

	public String getRefCurp() {
		return this.refCurp;
	}

	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}

}