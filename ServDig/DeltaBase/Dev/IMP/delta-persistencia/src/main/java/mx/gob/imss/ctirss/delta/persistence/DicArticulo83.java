package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ARTICULO_83 database table.
 * 
 */
@Entity
@Table(name="DIC_ARTICULO_83")
@OnSearchLlavePrimaria(atributos={"cveIdArticulo83"})
@ComponentComboCampoDescripcion(atributo="desArticulo")
public class DicArticulo83 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ARTICULO_83", nullable=false, precision=30)
	private long cveIdArticulo83;

	@Column(name="DES_ARTICULO", length=600)
	private String desArticulo;
	
	@Column(name="TIEMPO_ESPERA")
	private Integer tiempoEspera;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;


    public DicArticulo83() {
    }

	public long getCveIdArticulo83() {
		return cveIdArticulo83;
	}

	public void setCveIdArticulo83(long cveIdArticulo83) {
		this.cveIdArticulo83 = cveIdArticulo83;
	}

	public String getDesArticulo() {
		return desArticulo;
	}

	public void setDesArticulo(String desArticulo) {
		this.desArticulo = desArticulo;
	}

	public Integer getTiempoEspera() {
		return tiempoEspera;
	}

	public void setTiempoEspera(Integer tiempoEspera) {
		this.tiempoEspera = tiempoEspera;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	
}