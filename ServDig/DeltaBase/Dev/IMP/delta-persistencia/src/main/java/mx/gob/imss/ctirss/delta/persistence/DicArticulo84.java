package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ARTICULO_84 database table.
 * 
 */
@Entity
@Table(name="DIC_ARTICULO_84")
@OnSearchLlavePrimaria(atributos={"cveIdArticulo84"})
@ComponentComboCampoDescripcion(atributo="desArticulo")
public class DicArticulo84 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ARTICULO_84", nullable=false, precision=30)
	private long cveIdArticulo84;

	@Column(name="DES_ARTICULO", length=600)
	private String desArticulo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;


    public DicArticulo84() {
    }


	public long getCveIdArticulo84() {
		return cveIdArticulo84;
	}


	public void setCveIdArticulo84(long cveIdArticulo82) {
		this.cveIdArticulo84 = cveIdArticulo82;
	}


	public String getDesArticulo() {
		return desArticulo;
	}


	public void setDesArticulo(String desArticulo) {
		this.desArticulo = desArticulo;
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