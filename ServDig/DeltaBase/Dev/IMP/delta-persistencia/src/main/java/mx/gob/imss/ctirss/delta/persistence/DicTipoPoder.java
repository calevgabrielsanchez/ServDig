package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_PODER database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_PODER")
@OnSearchLlavePrimaria(atributos="cveIdTipoPoder")
@ComponentComboCampoDescripcion(atributo="desTipoPoder")
public class DicTipoPoder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_PODER", nullable=false, precision=22)
	private long cveIdTipoPoder;

	@Column(name="DES_TIPO_PODER", length=255)
	private String desTipoPoder;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitRepresentanteLegal
	@OneToMany(mappedBy="dicTipoPoder")
	private List<DitRepresentanteLegal> ditRepresentanteLegals;

    public DicTipoPoder() {
    }

	public long getCveIdTipoPoder() {
		return this.cveIdTipoPoder;
	}

	public void setCveIdTipoPoder(long cveIdTipoPoder) {
		this.cveIdTipoPoder = cveIdTipoPoder;
	}

	public String getDesTipoPoder() {
		return this.desTipoPoder;
	}

	public void setDesTipoPoder(String desTipoPoder) {
		this.desTipoPoder = desTipoPoder;
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

	public List<DitRepresentanteLegal> getDitRepresentanteLegals() {
		return this.ditRepresentanteLegals;
	}

	public void setDitRepresentanteLegals(List<DitRepresentanteLegal> ditRepresentanteLegals) {
		this.ditRepresentanteLegals = ditRepresentanteLegals;
	}
	
}