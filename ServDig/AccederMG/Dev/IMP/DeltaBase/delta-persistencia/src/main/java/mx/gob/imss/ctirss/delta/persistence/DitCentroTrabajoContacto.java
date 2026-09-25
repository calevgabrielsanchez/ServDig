package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIT_CENTRO_TRABAJO_CONTACTO database table.
 * 
 */
@Entity
@Table(name="DIT_CENTRO_TRABAJO_CONTACTO")
public class DitCentroTrabajoContacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitCentroTrabajoContactoPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitFormaContacto
    @ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="CVE_ID_FORMA_CONTACTO", insertable=false, updatable=false)
	private DitFormaContacto ditFormaContacto;

	//bi-directional many-to-one association to DitPatronSujetoObligado
    @ManyToOne
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO", insertable=false, updatable=false)
	private DitPatronSujetoObligado ditPatronSujetoObligado;

    public DitCentroTrabajoContacto() {
    }

	public DitCentroTrabajoContactoPK getId() {
		return this.id;
	}

	public void setId(DitCentroTrabajoContactoPK id) {
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

	public DitFormaContacto getDitFormaContacto() {
		return this.ditFormaContacto;
	}

	public void setDitFormaContacto(DitFormaContacto ditFormaContacto) {
		this.ditFormaContacto = ditFormaContacto;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
}