package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CARACTER database table.
 * 
 */
@Entity
@Table(name="DIC_CARACTER")
@OnSearchLlavePrimaria        (atributos={"cveIdCaracter"})
@ComponentComboCampoDescripcion	(atributo="desCaracter")
public class DicCaracter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CARACTER", nullable=false, precision=22)
	private long cveIdCaracter;

	@Column(name="DES_CARACTER", nullable=false, length=255)
	private String desCaracter;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitProrroga
	@OneToMany(mappedBy="dicCaracter")
	private List<DitProrroga> ditProrrogas;

    public DicCaracter() {
    }

	public long getCveIdCaracter() {
		return this.cveIdCaracter;
	}

	public void setCveIdCaracter(long cveIdCaracter) {
		this.cveIdCaracter = cveIdCaracter;
	}

	public String getDesCaracter() {
		return this.desCaracter;
	}

	public void setDesCaracter(String desCaracter) {
		this.desCaracter = desCaracter;
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

	public List<DitProrroga> getDitProrrogas() {
		return this.ditProrrogas;
	}

	public void setDitProrrogas(List<DitProrroga> ditProrrogas) {
		this.ditProrrogas = ditProrrogas;
	}
	
}