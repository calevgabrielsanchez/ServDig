package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_RAZON_RESULTADO database table.
 * 
 */
@Entity
@Table(name="DIC_RAZON_RESULTADO")
@OnSearchLlavePrimaria        (atributos={"cveIdRazonResultado"})
@ComponentComboCampoDescripcion	(atributo="desRazonResultado")
public class DicRazonResultado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_RAZON_RESULTADO", nullable=false, precision=22)
	private Long cveIdRazonResultado;

	@Column(name="DES_RAZON_RESULTADO", nullable=false, length=255)
	private String desRazonResultado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitTramite
	@OneToMany(mappedBy="dicRazonResultado", fetch=FetchType.LAZY)
	private List<DitTramite> ditTramites;

    public DicRazonResultado() {
    }

	public Long getCveIdRazonResultado() {
		return this.cveIdRazonResultado;
	}

	public void setCveIdRazonResultado(Long cveIdRazonResultado) {
		this.cveIdRazonResultado = cveIdRazonResultado;
	}

	public String getDesRazonResultado() {
		return this.desRazonResultado;
	}

	public void setDesRazonResultado(String desRazonResultado) {
		this.desRazonResultado = desRazonResultado;
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

	public List<DitTramite> getDitTramites() {
		return this.ditTramites;
	}

	public void setDitTramites(List<DitTramite> ditTramites) {
		this.ditTramites = ditTramites;
	}
	
}