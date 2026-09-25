package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FDI_COLEGIO database table.
 * 
 */
@Entity
@Table(name="FDI_COLEGIO")
public class FdiColegio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_COLEGIO", nullable=false, precision=22)
	private long idColegio;

	@Column(name="IND_DOMICILIADO", precision=22)
	private BigDecimal indDomiciliado;

	@Column(name="NU_CP", length=6)
	private String nuCp;

	@Column(name="TX_CALLE", length=100)
	private String txCalle;

	@Column(name="TX_COLEGIO", nullable=false, length=120)
	private String txColegio;

	@Column(name="TX_COLONIA", length=100)
	private String txColonia;

	@Column(name="TX_CORREO", length=100)
	private String txCorreo;

	@Column(name="TX_NUM_EXT", length=50)
	private String txNumExt;

	@Column(name="TX_NUM_INT", length=50)
	private String txNumInt;

	@Column(name="TX_TELEFONO", length=40)
	private String txTelefono;

	//bi-directional many-to-one association to FdiAsocColegio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ASOCIACION", nullable=false)
	private FdiAsocColegio fdiAsocColegio;

	//bi-directional many-to-one association to FiMunicipiosImssInegi
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MUNI_INEGI_IMSS")
	private FiMunicipiosImssInegi fiMunicipiosImssInegi;

	//bi-directional many-to-one association to FdiColegioDom
	@OneToMany(mappedBy="fdiColegio")
	private List<FdiColegioDom> fdiColegioDoms;

	//bi-directional many-to-one association to FdiCpa
	@OneToMany(mappedBy="fdiColegio1")
	private List<FdiCpa> fdiCpas1;

	//bi-directional many-to-one association to FdiCpa
	@OneToMany(mappedBy="fdiColegio2")
	private List<FdiCpa> fdiCpas2;

    public FdiColegio() {
    }

	public long getIdColegio() {
		return this.idColegio;
	}

	public void setIdColegio(long idColegio) {
		this.idColegio = idColegio;
	}

	public BigDecimal getIndDomiciliado() {
		return this.indDomiciliado;
	}

	public void setIndDomiciliado(BigDecimal indDomiciliado) {
		this.indDomiciliado = indDomiciliado;
	}

	public String getNuCp() {
		return this.nuCp;
	}

	public void setNuCp(String nuCp) {
		this.nuCp = nuCp;
	}

	public String getTxCalle() {
		return this.txCalle;
	}

	public void setTxCalle(String txCalle) {
		this.txCalle = txCalle;
	}

	public String getTxColegio() {
		return this.txColegio;
	}

	public void setTxColegio(String txColegio) {
		this.txColegio = txColegio;
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

	public String getTxNumExt() {
		return this.txNumExt;
	}

	public void setTxNumExt(String txNumExt) {
		this.txNumExt = txNumExt;
	}

	public String getTxNumInt() {
		return this.txNumInt;
	}

	public void setTxNumInt(String txNumInt) {
		this.txNumInt = txNumInt;
	}

	public String getTxTelefono() {
		return this.txTelefono;
	}

	public void setTxTelefono(String txTelefono) {
		this.txTelefono = txTelefono;
	}

	public FdiAsocColegio getFdiAsocColegio() {
		return this.fdiAsocColegio;
	}

	public void setFdiAsocColegio(FdiAsocColegio fdiAsocColegio) {
		this.fdiAsocColegio = fdiAsocColegio;
	}
	
	public FiMunicipiosImssInegi getFiMunicipiosImssInegi() {
		return this.fiMunicipiosImssInegi;
	}

	public void setFiMunicipiosImssInegi(FiMunicipiosImssInegi fiMunicipiosImssInegi) {
		this.fiMunicipiosImssInegi = fiMunicipiosImssInegi;
	}
	
	public List<FdiColegioDom> getFdiColegioDoms() {
		return this.fdiColegioDoms;
	}

	public void setFdiColegioDoms(List<FdiColegioDom> fdiColegioDoms) {
		this.fdiColegioDoms = fdiColegioDoms;
	}
	
	public List<FdiCpa> getFdiCpas1() {
		return this.fdiCpas1;
	}

	public void setFdiCpas1(List<FdiCpa> fdiCpas1) {
		this.fdiCpas1 = fdiCpas1;
	}
	
	public List<FdiCpa> getFdiCpas2() {
		return this.fdiCpas2;
	}

	public void setFdiCpas2(List<FdiCpa> fdiCpas2) {
		this.fdiCpas2 = fdiCpas2;
	}
	
}