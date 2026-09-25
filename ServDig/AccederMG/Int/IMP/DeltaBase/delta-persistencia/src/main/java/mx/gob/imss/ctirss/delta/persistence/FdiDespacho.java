package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FDI_DESPACHO database table.
 * 
 */
@Entity
@Table(name="FDI_DESPACHO")
public class FdiDespacho implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CV_RFC", nullable=false, length=18)
	private String cvRfc;

	@Column(name="CV_LADA", precision=22)
	private BigDecimal cvLada;

	@Column(name="IND_DOMICILIADO", precision=22)
	private BigDecimal indDomiciliado;

	@Column(name="NU_ASENTAMIENTO", precision=22)
	private BigDecimal nuAsentamiento;

	@Column(name="NU_CP", length=6)
	private String nuCp;

	@Column(name="NU_DESP_SAT", length=40)
	private String nuDespSat;

	@Column(name="NU_EXTENSION", length=7)
	private String nuExtension;

	@Column(name="NU_EXTERIOR", length=50)
	private String nuExterior;

	@Column(name="NU_INTERIOR", length=50)
	private String nuInterior;

	@Column(name="NU_TELEFONO", length=50)
	private String nuTelefono;

	@Column(name="TX_CALLE", length=50)
	private String txCalle;

	@Column(name="TX_COLONIA", length=50)
	private String txColonia;

	@Column(name="TX_CORREO", length=80)
	private String txCorreo;

	@Column(name="TX_RAZON_SOCIAL", nullable=false, length=100)
	private String txRazonSocial;

	//bi-directional many-to-one association to FiMunicipiosImssInegi
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		//@JoinColumn(name="ENT_FED", nullable=false),
		@JoinColumn(name="ID_MUNICIPIO", referencedColumnName="ID_MUNI_INEGI_IMSS", nullable=false)
		})
	private FiMunicipiosImssInegi fiMunicipiosImssInegi;

	//bi-directional many-to-one association to FdiDespachoDom
	@OneToMany(mappedBy="fdiDespacho")
	private List<FdiDespachoDom> fdiDespachoDoms;

    public FdiDespacho() {
    }

	public String getCvRfc() {
		return this.cvRfc;
	}

	public void setCvRfc(String cvRfc) {
		this.cvRfc = cvRfc;
	}

	public BigDecimal getCvLada() {
		return this.cvLada;
	}

	public void setCvLada(BigDecimal cvLada) {
		this.cvLada = cvLada;
	}

	public BigDecimal getIndDomiciliado() {
		return this.indDomiciliado;
	}

	public void setIndDomiciliado(BigDecimal indDomiciliado) {
		this.indDomiciliado = indDomiciliado;
	}

	public BigDecimal getNuAsentamiento() {
		return this.nuAsentamiento;
	}

	public void setNuAsentamiento(BigDecimal nuAsentamiento) {
		this.nuAsentamiento = nuAsentamiento;
	}

	public String getNuCp() {
		return this.nuCp;
	}

	public void setNuCp(String nuCp) {
		this.nuCp = nuCp;
	}

	public String getNuDespSat() {
		return this.nuDespSat;
	}

	public void setNuDespSat(String nuDespSat) {
		this.nuDespSat = nuDespSat;
	}

	public String getNuExtension() {
		return this.nuExtension;
	}

	public void setNuExtension(String nuExtension) {
		this.nuExtension = nuExtension;
	}

	public String getNuExterior() {
		return this.nuExterior;
	}

	public void setNuExterior(String nuExterior) {
		this.nuExterior = nuExterior;
	}

	public String getNuInterior() {
		return this.nuInterior;
	}

	public void setNuInterior(String nuInterior) {
		this.nuInterior = nuInterior;
	}

	public String getNuTelefono() {
		return this.nuTelefono;
	}

	public void setNuTelefono(String nuTelefono) {
		this.nuTelefono = nuTelefono;
	}

	public String getTxCalle() {
		return this.txCalle;
	}

	public void setTxCalle(String txCalle) {
		this.txCalle = txCalle;
	}

	public String getTxColonia() {
		return this.txColonia;
	}

	public void setTxColonia(String txColonia) {
		this.txColonia = txColonia;
	}

	public String getTxCorreo() {
		return this.txCorreo;
	}

	public void setTxCorreo(String txCorreo) {
		this.txCorreo = txCorreo;
	}

	public String getTxRazonSocial() {
		return this.txRazonSocial;
	}

	public void setTxRazonSocial(String txRazonSocial) {
		this.txRazonSocial = txRazonSocial;
	}

	public FiMunicipiosImssInegi getFiMunicipiosImssInegi() {
		return this.fiMunicipiosImssInegi;
	}

	public void setFiMunicipiosImssInegi(FiMunicipiosImssInegi fiMunicipiosImssInegi) {
		this.fiMunicipiosImssInegi = fiMunicipiosImssInegi;
	}
	
	public List<FdiDespachoDom> getFdiDespachoDoms() {
		return this.fdiDespachoDoms;
	}

	public void setFdiDespachoDoms(List<FdiDespachoDom> fdiDespachoDoms) {
		this.fdiDespachoDoms = fdiDespachoDoms;
	}
	
}