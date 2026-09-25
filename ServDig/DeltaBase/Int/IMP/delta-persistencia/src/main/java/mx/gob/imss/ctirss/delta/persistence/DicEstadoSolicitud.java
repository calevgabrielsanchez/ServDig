package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;



/**
 * The persistent class for the DIC_ESTADO_SOLICITUD database table.
 * 
 */
@Entity
@Table(name="DIC_ESTADO_SOLICITUD")
@OnSearchLlavePrimaria(atributos="cveIdEstadoSolicitud")
@ComponentComboCampoDescripcion(atributo="desEstadoSolicitud")
public class DicEstadoSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ESTADO_SOLICITUD", nullable=false, precision=22)
	private Long cveIdEstadoSolicitud;

	@Column(name="DES_ESTADO_SOLICITUD", nullable=false, length=255)
	private String desEstadoSolicitud;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitSolicitud
	@OneToMany(mappedBy="dicEstadoSolicitud")
	private List<DitSolicitud> ditSolicituds;
	
	//bi-directional many-to-one association to DitSolicitud
	
	//@OneToMany(mappedBy="dicEstadoSolicitud")
	@Transient
	private List<DitBitacoraSegSolicitud> ditBitacoraSegSolicitudes;

	//bi-directional many-to-one association to DitSolicitudSeguimiento
	@OneToMany(mappedBy="dicEstadoSolicitud")
	private List<DitSolicitudSeguimiento> ditSolicitudSeguimientos;

    public DicEstadoSolicitud() {
    }

    public DicEstadoSolicitud(Long cveIdEstadoSolicitud) {
        this.cveIdEstadoSolicitud = cveIdEstadoSolicitud;
    }
    
	public Long getCveIdEstadoSolicitud() {
		return this.cveIdEstadoSolicitud;
	}

	public void setCveIdEstadoSolicitud(Long cveIdEstadoSolicitud) {
		this.cveIdEstadoSolicitud = cveIdEstadoSolicitud;
	}

	public String getDesEstadoSolicitud() {
		return this.desEstadoSolicitud;
	}

	public void setDesEstadoSolicitud(String desEstadoSolicitud) {
		this.desEstadoSolicitud = desEstadoSolicitud;
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

	public List<DitSolicitud> getDitSolicituds() {
		return this.ditSolicituds;
	}

	public void setDitSolicituds(List<DitSolicitud> ditSolicituds) {
		this.ditSolicituds = ditSolicituds;
	}
	
	public List<DitSolicitudSeguimiento> getDitSolicitudSeguimientos() {
		return this.ditSolicitudSeguimientos;
	}

	public void setDitSolicitudSeguimientos(List<DitSolicitudSeguimiento> ditSolicitudSeguimientos) {
		this.ditSolicitudSeguimientos = ditSolicitudSeguimientos;
	}

	/**
	 * @return the ditBitacoraSegSolicitudes
	 */
	public List<DitBitacoraSegSolicitud> getDitBitacoraSegSolicitudes() {
		return ditBitacoraSegSolicitudes;
	}

	/**
	 * @param ditBitacoraSegSolicitudes the ditBitacoraSegSolicitudes to set
	 */
	public void setDitBitacoraSegSolicitudes(
			List<DitBitacoraSegSolicitud> ditBitacoraSegSolicitudes) {
		this.ditBitacoraSegSolicitudes = ditBitacoraSegSolicitudes;
	}
	
}