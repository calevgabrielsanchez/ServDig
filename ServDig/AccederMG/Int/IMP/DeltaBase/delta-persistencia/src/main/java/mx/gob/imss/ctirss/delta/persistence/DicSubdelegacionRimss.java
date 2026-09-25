package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_SUBDELEGACION_RIMS database table.
 * 
 */
@Entity
@Table(name="DIC_SUBDELEGACION_RIMSS")
@OnSearchLlavePrimaria(atributos="cveIdSubRimss")
@ComponentComboCampoDescripcion(atributo="desDeleg")
public class DicSubdelegacionRimss implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_SUB_RIMSS", nullable=false, precision=22)
	private long cveIdSubRimss;

	@Column(name="CVE_DELEGACION", nullable=false, precision=22)
	private long cveDelegacion;
	
	@Column(name="CVE_SUBDELEGACION", nullable=false, precision=22)
	private long cveSubDelegacion;

	@Column(name="DES_DELEG", length=255)
	private String desDeleg;
	
	@Column(name="DES_SUBDELEGACION", length=255)
	private String desSubDelegacion;
	
	@Column(name="TIPO", length=255)
	private String tipo;
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public DicSubdelegacionRimss() {
		
	}

	public long getCveIdSubRimss() {
		return cveIdSubRimss;
	}

	public void setCveIdSubRimss(long cveIdSubRimss) {
		this.cveIdSubRimss = cveIdSubRimss;
	}

	public long getCveDelegacion() {
		return cveDelegacion;
	}

	public void setCveDelegacion(long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public long getCveSubDelegacion() {
		return cveSubDelegacion;
	}

	public void setCveSubDelegacion(long cveSubDelegacion) {
		this.cveSubDelegacion = cveSubDelegacion;
	}

	public String getDesDeleg() {
		return desDeleg;
	}

	public void setDesDeleg(String desDeleg) {
		this.desDeleg = desDeleg;
	}

	public String getDesSubDelegacion() {
		return desSubDelegacion;
	}

	public void setDesSubDelegacion(String desSubDelegacion) {
		this.desSubDelegacion = desSubDelegacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
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
	
}