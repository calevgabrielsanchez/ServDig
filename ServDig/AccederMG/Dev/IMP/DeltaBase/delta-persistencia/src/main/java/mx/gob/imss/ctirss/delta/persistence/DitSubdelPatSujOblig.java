package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Hugo Martinez
 * The persistent class for the DIT_DELSUB_PAT_SUJ_OBLIG database table.
 */
@Entity
@Table(name="DIT_DELSUB_PAT_SUJ_OBLIG")
public class DitSubdelPatSujOblig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7040257129397553836L;

	@Id
	@Column(name = "CVE_ID_PATRON_SUJETO_OBLIGADO", unique = true, nullable = false, precision = 22)
	private long cveIdPatronSujetoObligado;
	
	//bi-directional many-to-one association to DicSubdelegacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;
	
	@Column(name="IND_MIGR_DELSUB", length=255)
	private Integer indMigrDelsub;
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
    
    @OneToOne
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO", insertable=false, updatable=false)
	private DitPatronSujetoObligado ditPatronSujetoObligado;
    
	public long getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}

	public void setCveIdPatronSujetoObligado(long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

	public DicSubdelegacion getDicSubdelegacion() {
		return dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}

	public Integer getIndMigrDelsub() {
		return indMigrDelsub;
	}

	public void setIndMigrDelsub(Integer indMigrDelsub) {
		this.indMigrDelsub = indMigrDelsub;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(
			DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
    
	
	
}
