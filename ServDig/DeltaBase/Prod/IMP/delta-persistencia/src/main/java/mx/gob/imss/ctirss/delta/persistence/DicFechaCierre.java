package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;



/**
 * The persistent class for the DIC_FECHA_CIERRE database table.
 * 
 */
@Entity
@Table(name="DIC_FECHA_CIERRE")
public class DicFechaCierre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_MOV", nullable=false)
	private Date fecMov;

	@Column(name="CVE_ESTATUS_CIE_DIA", length=1)
	private String cveEstatusCieDia;

	@Column(name="CVE_ESTATUS_CIE_MES", length=1)
	private String cveEstatusCieMes;

	@Column(name="CVE_ULTIMA", length=1)
	private String cveUltima;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ACT")
	private Date fecAct;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CIERRE")
	private Date fecCierre;

    @Temporal( TemporalType.DATE)
	@Column(name="HORA_ACT")
	private Date horaAct;

	@Column(name="NUM_ESTATUS_MOV", precision=1)
	private BigDecimal numEstatusMov;

	@Column(name="NUM_FOL_MOV", precision=6)
	private BigDecimal numFolMov;

    public DicFechaCierre() {
    }

	public Date getFecMov() {
		return this.fecMov;
	}

	public void setFecMov(Date fecMov) {
		this.fecMov = fecMov;
	}

	public String getCveEstatusCieDia() {
		return this.cveEstatusCieDia;
	}

	public void setCveEstatusCieDia(String cveEstatusCieDia) {
		this.cveEstatusCieDia = cveEstatusCieDia;
	}

	public String getCveEstatusCieMes() {
		return this.cveEstatusCieMes;
	}

	public void setCveEstatusCieMes(String cveEstatusCieMes) {
		this.cveEstatusCieMes = cveEstatusCieMes;
	}

	public String getCveUltima() {
		return this.cveUltima;
	}

	public void setCveUltima(String cveUltima) {
		this.cveUltima = cveUltima;
	}

	public Date getFecAct() {
		return this.fecAct;
	}

	public void setFecAct(Date fecAct) {
		this.fecAct = fecAct;
	}

	public Date getFecCierre() {
		return this.fecCierre;
	}

	public void setFecCierre(Date fecCierre) {
		this.fecCierre = fecCierre;
	}

	public Date getHoraAct() {
		return this.horaAct;
	}

	public void setHoraAct(Date horaAct) {
		this.horaAct = horaAct;
	}

	public BigDecimal getNumEstatusMov() {
		return this.numEstatusMov;
	}

	public void setNumEstatusMov(BigDecimal numEstatusMov) {
		this.numEstatusMov = numEstatusMov;
	}

	public BigDecimal getNumFolMov() {
		return this.numFolMov;
	}

	public void setNumFolMov(BigDecimal numFolMov) {
		this.numFolMov = numFolMov;
	}

}