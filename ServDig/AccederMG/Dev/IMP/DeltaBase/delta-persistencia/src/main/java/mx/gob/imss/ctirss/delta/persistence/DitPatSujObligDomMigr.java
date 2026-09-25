package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIT_PAT_SUJ_OBLIG_DOM_MIGR database table.
 * 
 */
@Entity
@Table(name="DIT_PAT_SUJ_OBLIG_DOM_MIGR")
public class DitPatSujObligDomMigr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private long cveIdPatronSujetoObligado;

	@Column(name="DES_DOMICILIO")
	private String desDomicilio;

	@Column(name="DES_LOCALIDAD")
	private String desLocalidad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="REF_CODIGO_POSTAL")
	private String refCodigoPostal;

	//bi-directional one-to-one association to DitPatronSujetoObligado
	@OneToOne
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

    public DitPatSujObligDomMigr() {
    }

	public long getCveIdPatronSujetoObligado() {
		return this.cveIdPatronSujetoObligado;
	}

	public void setCveIdPatronSujetoObligado(long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

	public String getDesDomicilio() {
		return this.desDomicilio;
	}

	public void setDesDomicilio(String desDomicilio) {
		this.desDomicilio = desDomicilio;
	}

	public String getDesLocalidad() {
		return this.desLocalidad;
	}

	public void setDesLocalidad(String desLocalidad) {
		this.desLocalidad = desLocalidad;
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

	public String getRefCodigoPostal() {
		return this.refCodigoPostal;
	}

	public void setRefCodigoPostal(String refCodigoPostal) {
		this.refCodigoPostal = refCodigoPostal;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
}