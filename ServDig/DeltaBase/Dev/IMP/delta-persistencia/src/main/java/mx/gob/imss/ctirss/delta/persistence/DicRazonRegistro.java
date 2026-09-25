package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the DIC_RAZON_REGISTRO database table.
 * 
 */
@Entity
@Table(name="DIC_RAZON_REGISTRO")
@OnSearchLlavePrimaria        (atributos={"cveRazonRegistro"})
@ComponentComboCampoDescripcion	(atributo="desRazonRegistro")
public class DicRazonRegistro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_RAZON_REGISTRO", nullable=false, precision=22)
	private long cveRazonRegistro;

	@Column(name="DES_RAZON_REGISTRO", nullable=false, length=100)
	private String desRazonRegistro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitRegistroDerechohabiente
	@OneToMany(mappedBy="dicRazonRegistro")
	private List<DitRegistroDerechohabiente> ditRegistroDerechohabientes;

    public DicRazonRegistro() {
    }

	public long getCveRazonRegistro() {
		return this.cveRazonRegistro;
	}

	public void setCveRazonRegistro(long cveRazonRegistro) {
		this.cveRazonRegistro = cveRazonRegistro;
	}

	public String getDesRazonRegistro() {
		return this.desRazonRegistro;
	}

	public void setDesRazonRegistro(String desRazonRegistro) {
		this.desRazonRegistro = desRazonRegistro;
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

	public List<DitRegistroDerechohabiente> getDitRegistroDerechohabientes() {
		return this.ditRegistroDerechohabientes;
	}

	public void setDitRegistroDerechohabientes(List<DitRegistroDerechohabiente> ditRegistroDerechohabientes) {
		this.ditRegistroDerechohabientes = ditRegistroDerechohabientes;
	}
	
}