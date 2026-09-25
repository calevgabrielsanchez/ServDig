package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the FDT_SUBDELEG database table.
 * 
 */
@Entity
@Table(name="FDT_SUBDELEG")
public class FdtSubdeleg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtSubdelegPK id;

	@Column(name="SDELEG_DESC", length=40)
	private String sdelegDesc;

	@Column(name="TX_DOMICILIO", nullable=false, length=120)
	private String txDomicilio;

	@Column(name="TX_NOMBRE_DEPTO", length=80)
	private String txNombreDepto;

	@Column(name="TX_NOMBRE_TIT", nullable=false, length=80)
	private String txNombreTit;

	//bi-directional many-to-one association to CfcUsuarioint
	@OneToMany(mappedBy="fdtSubdeleg")
	private List<CfcUsuarioint> cfcUsuarioints;

	//bi-directional many-to-one association to CftIntegrafiscaliza
	@OneToMany(mappedBy="fdtSubdeleg")
	private List<CftIntegrafiscaliza> cftIntegrafiscalizas;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="fdtSubdeleg")
	private List<CgtGestionsinadi> cgtGestionsinadis;

	//bi-directional many-to-one association to FdiCpa
	@OneToMany(mappedBy="fdtSubdeleg")
	private List<FdiCpa> fdiCpas;

	//bi-directional many-to-one association to FdiDictamenExistente
	@OneToMany(mappedBy="fdtSubdeleg")
	private List<FdiDictamenExistente> fdiDictamenExistentes;

	//bi-directional many-to-one association to FdiSancion
	@OneToMany(mappedBy="fdtSubdeleg")
	private List<FdiSancion> fdiSancions;

	//bi-directional many-to-one association to FdtPatron
	@OneToMany(mappedBy="fdtSubdeleg")
	private List<FdtPatron> fdtPatrons;

	//bi-directional many-to-one association to FdtPatronHi
	@OneToMany(mappedBy="fdtSubdeleg")
	private List<FdtPatronHi> fdtPatronHis;

	//bi-directional many-to-one association to FdtDeleg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_DELEG_ORIG", nullable=false, insertable=false, updatable=false)
	private FdtDeleg fdtDeleg;

    public FdtSubdeleg() {
    }

	public FdtSubdelegPK getId() {
		return this.id;
	}

	public void setId(FdtSubdelegPK id) {
		this.id = id;
	}
	
	public String getSdelegDesc() {
		return this.sdelegDesc;
	}

	public void setSdelegDesc(String sdelegDesc) {
		this.sdelegDesc = sdelegDesc;
	}

	public String getTxDomicilio() {
		return this.txDomicilio;
	}

	public void setTxDomicilio(String txDomicilio) {
		this.txDomicilio = txDomicilio;
	}

	public String getTxNombreDepto() {
		return this.txNombreDepto;
	}

	public void setTxNombreDepto(String txNombreDepto) {
		this.txNombreDepto = txNombreDepto;
	}

	public String getTxNombreTit() {
		return this.txNombreTit;
	}

	public void setTxNombreTit(String txNombreTit) {
		this.txNombreTit = txNombreTit;
	}

	public List<CfcUsuarioint> getCfcUsuarioints() {
		return this.cfcUsuarioints;
	}

	public void setCfcUsuarioints(List<CfcUsuarioint> cfcUsuarioints) {
		this.cfcUsuarioints = cfcUsuarioints;
	}
	
	public List<CftIntegrafiscaliza> getCftIntegrafiscalizas() {
		return this.cftIntegrafiscalizas;
	}

	public void setCftIntegrafiscalizas(List<CftIntegrafiscaliza> cftIntegrafiscalizas) {
		this.cftIntegrafiscalizas = cftIntegrafiscalizas;
	}
	
	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
	public List<FdiCpa> getFdiCpas() {
		return this.fdiCpas;
	}

	public void setFdiCpas(List<FdiCpa> fdiCpas) {
		this.fdiCpas = fdiCpas;
	}
	
	public List<FdiDictamenExistente> getFdiDictamenExistentes() {
		return this.fdiDictamenExistentes;
	}

	public void setFdiDictamenExistentes(List<FdiDictamenExistente> fdiDictamenExistentes) {
		this.fdiDictamenExistentes = fdiDictamenExistentes;
	}
	
	public List<FdiSancion> getFdiSancions() {
		return this.fdiSancions;
	}

	public void setFdiSancions(List<FdiSancion> fdiSancions) {
		this.fdiSancions = fdiSancions;
	}
	
	public List<FdtPatron> getFdtPatrons() {
		return this.fdtPatrons;
	}

	public void setFdtPatrons(List<FdtPatron> fdtPatrons) {
		this.fdtPatrons = fdtPatrons;
	}
	
	public List<FdtPatronHi> getFdtPatronHis() {
		return this.fdtPatronHis;
	}

	public void setFdtPatronHis(List<FdtPatronHi> fdtPatronHis) {
		this.fdtPatronHis = fdtPatronHis;
	}
	
	public FdtDeleg getFdtDeleg() {
		return this.fdtDeleg;
	}

	public void setFdtDeleg(FdtDeleg fdtDeleg) {
		this.fdtDeleg = fdtDeleg;
	}
	
}