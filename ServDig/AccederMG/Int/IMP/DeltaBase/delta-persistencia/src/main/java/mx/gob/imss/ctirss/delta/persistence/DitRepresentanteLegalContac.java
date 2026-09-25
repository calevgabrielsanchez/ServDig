package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIT_REPRESENTANTE_LEGAL_CONTAC database table.
 * 
 */
@Entity
@Table(name="DIT_REPRESENTANTE_LEGAL_CONTAC")
public class DitRepresentanteLegalContac implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitRepresentanteLegalContacPK id;

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
    @ManyToOne
	@JoinColumn(name="CVE_ID_FORMA_CONTACTO", insertable=false, updatable=false)
	private DitFormaContacto ditFormaContacto;

	//bi-directional many-to-one association to DitRepresentanteLegal
    @ManyToOne
	@JoinColumn(name="CVE_ID_REPRESENTANTE_LEGAL", insertable=false, updatable=false)
	private DitRepresentanteLegal ditRepresentanteLegal;

    public DitRepresentanteLegalContac() {
    }

	public DitRepresentanteLegalContacPK getId() {
		return this.id;
	}

	public void setId(DitRepresentanteLegalContacPK id) {
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
	
	public DitRepresentanteLegal getDitRepresentanteLegal() {
		return this.ditRepresentanteLegal;
	}

	public void setDitRepresentanteLegal(DitRepresentanteLegal ditRepresentanteLegal) {
		this.ditRepresentanteLegal = ditRepresentanteLegal;
	}
	
}