package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIT_REG_PAT_CONVENCIONAL database table.
 * 
 */
@Entity
@Table(name="DIT_REG_PAT_CONVENCIONAL")
public class DitRegPatConvencional implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitRegPatConvencionalPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicMunicipioImss
    @ManyToOne
	@JoinColumn(name="CVE_ID_MUNICIPIO_IMSS", insertable=false, updatable=false)
	private DicMunicipioImss dicMunicipioImss;

	//bi-directional many-to-one association to DitPatronSujetoObligado
    @ManyToOne
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO", insertable=false, updatable=false)
	private DitPatronSujetoObligado ditPatronSujetoObligado;

  //bi-directional many-to-one association to DicModalidad
    @ManyToOne
	@JoinColumn(name="CVE_ID_MODALIDAD", insertable=false, updatable=false)
	private DicModalidad dicModalidad;

    
    public DitRegPatConvencional() {
    }

	public DitRegPatConvencionalPK getId() {
		return this.id;
	}

	public void setId(DitRegPatConvencionalPK id) {
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

	public DicMunicipioImss getDicMunicipioImss() {
		return this.dicMunicipioImss;
	}

	public void setDicMunicipioImss(DicMunicipioImss dicMunicipioImss) {
		this.dicMunicipioImss = dicMunicipioImss;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DicModalidad getDicModalidad() {
		return this.dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}

}