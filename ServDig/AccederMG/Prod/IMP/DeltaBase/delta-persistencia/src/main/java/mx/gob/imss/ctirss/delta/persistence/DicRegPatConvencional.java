package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIC_REG_PAT_CONVENCIONAL database table.
 * 
 */
@Entity
@Table(name="DIC_REG_PAT_CONVENCIONAL")
public class DicRegPatConvencional implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DicRegPatConvencionalPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicDelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_DELEGACION", insertable=false, updatable=false)
	private DicDelegacion dicDelegacion;

	//bi-directional many-to-one association to DicModalidad
    @ManyToOne
	@JoinColumn(name="CVE_ID_MODALIDAD", insertable=false, updatable=false)
	private DicModalidad dicModalidad;

	//bi-directional many-to-one association to DitPatronSujetoObligado
    @ManyToOne
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO", insertable=false, updatable=false)
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	//bi-directional many-to-one association to DicSubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_SUBDELEGACION", insertable=false, updatable=false)
	private DicSubdelegacion dicSubdelegacion;

    public DicRegPatConvencional() {
    }

	public DicRegPatConvencionalPK getId() {
		return this.id;
	}

	public void setId(DicRegPatConvencionalPK id) {
		this.id = id;
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

	public DicDelegacion getDicDelegacion() {
		return this.dicDelegacion;
	}

	public void setDicDelegacion(DicDelegacion dicDelegacion) {
		this.dicDelegacion = dicDelegacion;
	}
	
	public DicModalidad getDicModalidad() {
		return this.dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DicSubdelegacion getDicSubdelegacion() {
		return this.dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}
	
}