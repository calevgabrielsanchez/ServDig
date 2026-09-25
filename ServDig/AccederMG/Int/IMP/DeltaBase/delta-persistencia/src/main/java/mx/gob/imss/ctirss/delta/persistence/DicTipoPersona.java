package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_PERSONA database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_PERSONA")
@OnSearchLlavePrimaria(atributos="cveIdTipoPersona")
@ComponentComboCampoDescripcion(atributo="desTipoPersona")
public class DicTipoPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_PERSONA", nullable=false, precision=22)
	private long cveIdTipoPersona;

	@Column(name="DES_TIPO_PERSONA", nullable=false, length=255)
	private String desTipoPersona;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicTipoDomicilio
	@OneToMany(mappedBy="dicTipoPersona")
	private List<DicTipoDomicilio> dicTipoDomicilios;

	// bi-directional many-to-one association to DitLlavePatron
	@OneToMany(mappedBy = "dicTipoPersona", fetch=FetchType.LAZY)
	private List<DitLlavePatron> ditLlavePatrones;

    public DicTipoPersona() {
    }

	public long getCveIdTipoPersona() {
		return this.cveIdTipoPersona;
	}

	public void setCveIdTipoPersona(long cveIdTipoPersona) {
		this.cveIdTipoPersona = cveIdTipoPersona;
	}

	public String getDesTipoPersona() {
		return this.desTipoPersona;
	}

	public void setDesTipoPersona(String desTipoPersona) {
		this.desTipoPersona = desTipoPersona;
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

	public List<DicTipoDomicilio> getDicTipoDomicilios() {
		return this.dicTipoDomicilios;
	}

	public void setDicTipoDomicilios(List<DicTipoDomicilio> dicTipoDomicilios) {
		this.dicTipoDomicilios = dicTipoDomicilios;
	}

	public List<DitLlavePatron> getDitLlavePatrones() {
		return ditLlavePatrones;
	}

	public void setDitLlavePatrones(List<DitLlavePatron> ditLlavePatrones) {
		this.ditLlavePatrones = ditLlavePatrones;
	}
}