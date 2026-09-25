package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_SERVICIO_PREST_DERECHOHAB database table.
 * 
 */
@Entity
@Table(name="DIC_SERVICIO_PREST_DERECHOHAB")
public class DicServicioPrestDerechohab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_SERVICIO_DERECHOHAB")
	private long cveIdServicioDerechohab;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_SERVICIO_PENSION")
	private BigDecimal indServicioPension;

	@Column(name="NOM_SERVICIO_DERECHOHAB")
	private String nomServicioDerechohab;

	//bi-directional many-to-one association to DitModServPresDerechohab
	@OneToMany(mappedBy="dicServicioPrestDerechohab")
	private List<DitModServPresDerechohab> ditModServPresDerechohabs;

    public DicServicioPrestDerechohab() {
    }

	public long getCveIdServicioDerechohab() {
		return this.cveIdServicioDerechohab;
	}

	public void setCveIdServicioDerechohab(long cveIdServicioDerechohab) {
		this.cveIdServicioDerechohab = cveIdServicioDerechohab;
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

	public BigDecimal getIndServicioPension() {
		return this.indServicioPension;
	}

	public void setIndServicioPension(BigDecimal indServicioPension) {
		this.indServicioPension = indServicioPension;
	}

	public String getNomServicioDerechohab() {
		return this.nomServicioDerechohab;
	}

	public void setNomServicioDerechohab(String nomServicioDerechohab) {
		this.nomServicioDerechohab = nomServicioDerechohab;
	}

	public List<DitModServPresDerechohab> getDitModServPresDerechohabs() {
		return this.ditModServPresDerechohabs;
	}

	public void setDitModServPresDerechohabs(List<DitModServPresDerechohab> ditModServPresDerechohabs) {
		this.ditModServPresDerechohabs = ditModServPresDerechohabs;
	}
	
}