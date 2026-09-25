package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FI_MUNICIPIOS_IMSS_INEGI database table.
 * 
 */
@Entity
@Table(name="FI_MUNICIPIOS_IMSS_INEGI")
public class FiMunicipiosImssInegi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_MUNI_INEGI_IMSS", nullable=false, precision=22)
	private long idMuniInegiImss;

	@Column(name="ENT_FED_INEGI", nullable=false, precision=2)
	private BigDecimal entFedInegi;

	@Column(name="ID_MUNICIPIO_IMSS", length=50)
	private String idMunicipioImss;

	@Column(name="TX_MUNICIPIO_INEGI", length=90)
	private String txMunicipioInegi;

	//bi-directional many-to-one association to FdiColegio
	@OneToMany(mappedBy="fiMunicipiosImssInegi")
	private List<FdiColegio> fdiColegios;

	//bi-directional many-to-one association to FdiCpa
	@OneToMany(mappedBy="fiMunicipiosImssInegi")
	private List<FdiCpa> fdiCpas;

	//bi-directional many-to-one association to FdiDespacho
	@OneToMany(mappedBy="fiMunicipiosImssInegi")
	private List<FdiDespacho> fdiDespachos;

	//bi-directional many-to-one association to FiEntFed
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ENT_FED_IMSS")
	private FiEntFed fiEntFed;

    public FiMunicipiosImssInegi() {
    }

	public long getIdMuniInegiImss() {
		return this.idMuniInegiImss;
	}

	public void setIdMuniInegiImss(long idMuniInegiImss) {
		this.idMuniInegiImss = idMuniInegiImss;
	}

	public BigDecimal getEntFedInegi() {
		return this.entFedInegi;
	}

	public void setEntFedInegi(BigDecimal entFedInegi) {
		this.entFedInegi = entFedInegi;
	}

	public String getIdMunicipioImss() {
		return this.idMunicipioImss;
	}

	public void setIdMunicipioImss(String idMunicipioImss) {
		this.idMunicipioImss = idMunicipioImss;
	}

	public String getTxMunicipioInegi() {
		return this.txMunicipioInegi;
	}

	public void setTxMunicipioInegi(String txMunicipioInegi) {
		this.txMunicipioInegi = txMunicipioInegi;
	}

	public List<FdiColegio> getFdiColegios() {
		return this.fdiColegios;
	}

	public void setFdiColegios(List<FdiColegio> fdiColegios) {
		this.fdiColegios = fdiColegios;
	}
	
	public List<FdiCpa> getFdiCpas() {
		return this.fdiCpas;
	}

	public void setFdiCpas(List<FdiCpa> fdiCpas) {
		this.fdiCpas = fdiCpas;
	}
	
	public List<FdiDespacho> getFdiDespachos() {
		return this.fdiDespachos;
	}

	public void setFdiDespachos(List<FdiDespacho> fdiDespachos) {
		this.fdiDespachos = fdiDespachos;
	}
	
	public FiEntFed getFiEntFed() {
		return this.fiEntFed;
	}

	public void setFiEntFed(FiEntFed fiEntFed) {
		this.fiEntFed = fiEntFed;
	}
	
}