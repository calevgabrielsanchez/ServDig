package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_MOD_SERV_PRES_DERECHOHAB database table.
 * 
 */
@NamedQueries({	
	@NamedQuery(name = "getConDerecho", 
			query = "select ms from DitModServPresDerechohab ms where ms.dicModalidad.cveIdModalidad=:idModalidad and ms.dicServicioPrestDerechohab.cveIdServicioDerechohab=:idServicio"),
	@NamedQuery(name = "findServiciosDerechohabiente", 
					query = "select ms from DitModServPresDerechohab ms where ms.dicModalidad.cveIdModalidad=:idModalidad")		
})
@Entity
@Table(name="DIT_MOD_SERV_PRES_DERECHOHAB")
public class DitModServPresDerechohab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_MOD_SERV_DERECHOHAB")
	private long cveIdModServDerechohab;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_VALOR_SERVICIO")
	private BigDecimal numValorServicio;

	//bi-directional many-to-one association to DicModalidad
    @ManyToOne
	@JoinColumn(name="CVE_ID_MODALIDAD")
	private DicModalidad dicModalidad;

	//bi-directional many-to-one association to DicServicioPrestDerechohab
    @ManyToOne
	@JoinColumn(name="CVE_ID_SERVICIO_DERECHOHAB")
	private DicServicioPrestDerechohab dicServicioPrestDerechohab;

    public DitModServPresDerechohab() {
    }

	public long getCveIdModServDerechohab() {
		return this.cveIdModServDerechohab;
	}

	public void setCveIdModServDerechohab(long cveIdModServDerechohab) {
		this.cveIdModServDerechohab = cveIdModServDerechohab;
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

	public BigDecimal getNumValorServicio() {
		return this.numValorServicio;
	}

	public void setNumValorServicio(BigDecimal numValorServicio) {
		this.numValorServicio = numValorServicio;
	}

	public DicModalidad getDicModalidad() {
		return this.dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}
	
	public DicServicioPrestDerechohab getDicServicioPrestDerechohab() {
		return this.dicServicioPrestDerechohab;
	}

	public void setDicServicioPrestDerechohab(DicServicioPrestDerechohab dicServicioPrestDerechohab) {
		this.dicServicioPrestDerechohab = dicServicioPrestDerechohab;
	}
	
}