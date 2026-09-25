package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIC_SUBDEL_COMPATIBLES database table.
 * 
 */
@Entity
@Table(name="DIC_SUBDEL_COMPATIBLES")
public class DicSubdelCompatible implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DicSubdelCompatiblePK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicSubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_SUBDELEGACION_ORIGEN", insertable=false, updatable=false)
	private DicSubdelegacion dicSubdelegacionOrigen;

	//bi-directional many-to-one association to DicSubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_SUBDELEGACION_DESTINO", insertable=false, updatable=false)
	private DicSubdelegacion dicSubdelegacionDestino;

    public DicSubdelCompatible() {
    }

	public DicSubdelCompatiblePK getId() {
		return this.id;
	}

	public void setId(DicSubdelCompatiblePK id) {
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

	public DicSubdelegacion getDicSubdelegacionOrigen() {
		return dicSubdelegacionOrigen;
	}

	public void setDicSubdelegacionOrigen(DicSubdelegacion dicSubdelegacionOrigen) {
		this.dicSubdelegacionOrigen = dicSubdelegacionOrigen;
	}

	public DicSubdelegacion getDicSubdelegacionDestino() {
		return dicSubdelegacionDestino;
	}

	public void setDicSubdelegacionDestino(DicSubdelegacion dicSubdelegacionDestino) {
		this.dicSubdelegacionDestino = dicSubdelegacionDestino;
	}

	
}