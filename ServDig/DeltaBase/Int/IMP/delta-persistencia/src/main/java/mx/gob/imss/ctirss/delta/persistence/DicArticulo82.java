package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ARTICULO_82 database table.
 * 
 */
@Entity
@Table(name="DIC_ARTICULO_82")
@OnSearchLlavePrimaria(atributos={"cveIdArticulo82"})
@ComponentComboCampoDescripcion(atributo="desArticulo")
public class DicArticulo82 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ARTICULO_82", nullable=false, precision=30)
	private long cveIdArticulo82;

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


    public DicArticulo82() {
    }


	public long getCveIdArticulo82() {
		return cveIdArticulo82;
	}


	public void setCveIdArticulo82(long cveIdArticulo82) {
		this.cveIdArticulo82 = cveIdArticulo82;
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