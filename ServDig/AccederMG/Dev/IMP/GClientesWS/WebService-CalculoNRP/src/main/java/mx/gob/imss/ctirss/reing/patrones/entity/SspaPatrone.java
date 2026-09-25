package mx.gob.imss.ctirss.reing.patrones.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SSPA_PATRONES database table.
 * 
 */
@Entity
@Table(name="SSPA_PATRONES")
public class SspaPatrone implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SspaPatronePK id;

	@Column(name="ADIC_PENS")
	private BigDecimal adicPens;

	@Column(name="ANIO_CVE_MODAL")
	private BigDecimal anioCveModal;

	private String calle;

	@Column(name="CAUSA_BAJA")
	private String causaBaja;

	private String colonia;

	@Column(name="CURP_PAT")
	private String curpPat;

	@Column(name="CVE_DELEG_ORIG")
	private BigDecimal cveDelegOrig;

	@Column(name="CVE_EXTENSION")
	private BigDecimal cveExtension;

	@Column(name="CVE_LADA")
	private BigDecimal cveLada;

	@Column(name="CVE_MODAL_U")
	private BigDecimal cveModalU;

	@Column(name="CVE_MPIO")
	private String cveMpio;

	@Column(name="DELEG_CTL_EM")
	private BigDecimal delegCtlEm;

	@Column(name="DIG_VER_PAT")
	private String digVerPat;

	@Column(name="E_MAIL")
	private String eMail;

	@Column(name="ESTADO_PROCESO")
	private BigDecimal estadoProceso;

	@Column(name="ESTADO_PROCESO_MAC")
	private BigDecimal estadoProcesoMac;

	private BigDecimal fax;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ACT")
	private Date fecAct;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INI_HUELGA")
	private Date fecIniHuelga;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVTO")
	private Date fecMovto;

	private String giro;

	@Column(name="ID_HUELGA")
	private String idHuelga;

	@Column(name="ID_SUBR_SERV")
	private String idSubrServ;

	@Column(name="KEY_AUTENTIF")
	private String keyAutentif;

	@Column(name="MES_EMIS")
	private BigDecimal mesEmis;

	@Column(name="MP_TP_MOVTO")
	private String mpTpMovto;

	@Column(name="N_REL_MPIO")
	private BigDecimal nRelMpio;

	@Column(name="NUM_EXTERIOR")
	private String numExterior;

	@Column(name="NUM_INTERIOR")
	private String numInterior;

	@Column(name="PA_COD_POS")
	private BigDecimal paCodPos;

	@Column(name="PA_DOMICILIO")
	private String paDomicilio;

	@Column(name="PA_LOCALIDAD")
	private String paLocalidad;

	@Column(name="PA_NOMBRE")
	private String paNombre;

	@Column(name="PERIODO_EMIS")
	private String periodoEmis;

	@Column(name="PORC_AUS")
	private BigDecimal porcAus;

	@Column(name="REG_PAT_U")
	private String regPatU;

	@Column(name="REPRE_LEGAL")
	private String repreLegal;

	private String rfc;

	@Column(name="SDELEG_CTL_EM")
	private BigDecimal sdelegCtlEm;

	@Column(name="SDELEG_ORIG")
	private BigDecimal sdelegOrig;

	@Column(name="SEC_NOTIF")
	private BigDecimal secNotif;

	private BigDecimal telefono;

	@Column(name="TP_COTIZ")
	private String tpCotiz;

	@Column(name="TP_PAGO")
	private String tpPago;

	@Column(name="TP_PAT_MOD33")
	private String tpPatMod33;

	@Column(name="TP_PROVEE")
	private BigDecimal tpProvee;

	@Column(name="TRAB_MEX_EXTRAN")
	private BigDecimal trabMexExtran;

	@Column(name="TRAB_VIG_CONS")
	private BigDecimal trabVigCons;

	@Column(name="TRAB_VIG_EVE")
	private BigDecimal trabVigEve;

	@Column(name="TRAB_VIG_PER")
	private BigDecimal trabVigPer;

	@Column(name="ZONA_LADA")
	private String zonaLada;

    public SspaPatrone() {
    }

	public SspaPatronePK getId() {
		return this.id;
	}

	public void setId(SspaPatronePK id) {
		this.id = id;
	}
	
	public BigDecimal getAdicPens() {
		return this.adicPens;
	}

	public void setAdicPens(BigDecimal adicPens) {
		this.adicPens = adicPens;
	}

	public BigDecimal getAnioCveModal() {
		return this.anioCveModal;
	}

	public void setAnioCveModal(BigDecimal anioCveModal) {
		this.anioCveModal = anioCveModal;
	}

	public String getCalle() {
		return this.calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCausaBaja() {
		return this.causaBaja;
	}

	public void setCausaBaja(String causaBaja) {
		this.causaBaja = causaBaja;
	}

	public String getColonia() {
		return this.colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCurpPat() {
		return this.curpPat;
	}

	public void setCurpPat(String curpPat) {
		this.curpPat = curpPat;
	}

	public BigDecimal getCveDelegOrig() {
		return this.cveDelegOrig;
	}

	public void setCveDelegOrig(BigDecimal cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}

	public BigDecimal getCveExtension() {
		return this.cveExtension;
	}

	public void setCveExtension(BigDecimal cveExtension) {
		this.cveExtension = cveExtension;
	}

	public BigDecimal getCveLada() {
		return this.cveLada;
	}

	public void setCveLada(BigDecimal cveLada) {
		this.cveLada = cveLada;
	}

	public BigDecimal getCveModalU() {
		return this.cveModalU;
	}

	public void setCveModalU(BigDecimal cveModalU) {
		this.cveModalU = cveModalU;
	}

	public String getCveMpio() {
		return this.cveMpio;
	}

	public void setCveMpio(String cveMpio) {
		this.cveMpio = cveMpio;
	}

	public BigDecimal getDelegCtlEm() {
		return this.delegCtlEm;
	}

	public void setDelegCtlEm(BigDecimal delegCtlEm) {
		this.delegCtlEm = delegCtlEm;
	}

	public String getDigVerPat() {
		return this.digVerPat;
	}

	public void setDigVerPat(String digVerPat) {
		this.digVerPat = digVerPat;
	}

	public String getEMail() {
		return this.eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public BigDecimal getEstadoProceso() {
		return this.estadoProceso;
	}

	public void setEstadoProceso(BigDecimal estadoProceso) {
		this.estadoProceso = estadoProceso;
	}

	public BigDecimal getEstadoProcesoMac() {
		return this.estadoProcesoMac;
	}

	public void setEstadoProcesoMac(BigDecimal estadoProcesoMac) {
		this.estadoProcesoMac = estadoProcesoMac;
	}

	public BigDecimal getFax() {
		return this.fax;
	}

	public void setFax(BigDecimal fax) {
		this.fax = fax;
	}

	public Date getFecAct() {
		return this.fecAct;
	}

	public void setFecAct(Date fecAct) {
		this.fecAct = fecAct;
	}

	public Date getFecIniHuelga() {
		return this.fecIniHuelga;
	}

	public void setFecIniHuelga(Date fecIniHuelga) {
		this.fecIniHuelga = fecIniHuelga;
	}

	public Date getFecMovto() {
		return this.fecMovto;
	}

	public void setFecMovto(Date fecMovto) {
		this.fecMovto = fecMovto;
	}

	public String getGiro() {
		return this.giro;
	}

	public void setGiro(String giro) {
		this.giro = giro;
	}

	public String getIdHuelga() {
		return this.idHuelga;
	}

	public void setIdHuelga(String idHuelga) {
		this.idHuelga = idHuelga;
	}

	public String getIdSubrServ() {
		return this.idSubrServ;
	}

	public void setIdSubrServ(String idSubrServ) {
		this.idSubrServ = idSubrServ;
	}

	public String getKeyAutentif() {
		return this.keyAutentif;
	}

	public void setKeyAutentif(String keyAutentif) {
		this.keyAutentif = keyAutentif;
	}

	public BigDecimal getMesEmis() {
		return this.mesEmis;
	}

	public void setMesEmis(BigDecimal mesEmis) {
		this.mesEmis = mesEmis;
	}

	public String getMpTpMovto() {
		return this.mpTpMovto;
	}

	public void setMpTpMovto(String mpTpMovto) {
		this.mpTpMovto = mpTpMovto;
	}

	public BigDecimal getNRelMpio() {
		return this.nRelMpio;
	}

	public void setNRelMpio(BigDecimal nRelMpio) {
		this.nRelMpio = nRelMpio;
	}

	public String getNumExterior() {
		return this.numExterior;
	}

	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}

	public String getNumInterior() {
		return this.numInterior;
	}

	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}

	public BigDecimal getPaCodPos() {
		return this.paCodPos;
	}

	public void setPaCodPos(BigDecimal paCodPos) {
		this.paCodPos = paCodPos;
	}

	public String getPaDomicilio() {
		return this.paDomicilio;
	}

	public void setPaDomicilio(String paDomicilio) {
		this.paDomicilio = paDomicilio;
	}

	public String getPaLocalidad() {
		return this.paLocalidad;
	}

	public void setPaLocalidad(String paLocalidad) {
		this.paLocalidad = paLocalidad;
	}

	public String getPaNombre() {
		return this.paNombre;
	}

	public void setPaNombre(String paNombre) {
		this.paNombre = paNombre;
	}

	public String getPeriodoEmis() {
		return this.periodoEmis;
	}

	public void setPeriodoEmis(String periodoEmis) {
		this.periodoEmis = periodoEmis;
	}

	public BigDecimal getPorcAus() {
		return this.porcAus;
	}

	public void setPorcAus(BigDecimal porcAus) {
		this.porcAus = porcAus;
	}

	public String getRegPatU() {
		return this.regPatU;
	}

	public void setRegPatU(String regPatU) {
		this.regPatU = regPatU;
	}

	public String getRepreLegal() {
		return this.repreLegal;
	}

	public void setRepreLegal(String repreLegal) {
		this.repreLegal = repreLegal;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public BigDecimal getSdelegCtlEm() {
		return this.sdelegCtlEm;
	}

	public void setSdelegCtlEm(BigDecimal sdelegCtlEm) {
		this.sdelegCtlEm = sdelegCtlEm;
	}

	public BigDecimal getSdelegOrig() {
		return this.sdelegOrig;
	}

	public void setSdelegOrig(BigDecimal sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}

	public BigDecimal getSecNotif() {
		return this.secNotif;
	}

	public void setSecNotif(BigDecimal secNotif) {
		this.secNotif = secNotif;
	}

	public BigDecimal getTelefono() {
		return this.telefono;
	}

	public void setTelefono(BigDecimal telefono) {
		this.telefono = telefono;
	}

	public String getTpCotiz() {
		return this.tpCotiz;
	}

	public void setTpCotiz(String tpCotiz) {
		this.tpCotiz = tpCotiz;
	}

	public String getTpPago() {
		return this.tpPago;
	}

	public void setTpPago(String tpPago) {
		this.tpPago = tpPago;
	}

	public String getTpPatMod33() {
		return this.tpPatMod33;
	}

	public void setTpPatMod33(String tpPatMod33) {
		this.tpPatMod33 = tpPatMod33;
	}

	public BigDecimal getTpProvee() {
		return this.tpProvee;
	}

	public void setTpProvee(BigDecimal tpProvee) {
		this.tpProvee = tpProvee;
	}

	public BigDecimal getTrabMexExtran() {
		return this.trabMexExtran;
	}

	public void setTrabMexExtran(BigDecimal trabMexExtran) {
		this.trabMexExtran = trabMexExtran;
	}

	public BigDecimal getTrabVigCons() {
		return this.trabVigCons;
	}

	public void setTrabVigCons(BigDecimal trabVigCons) {
		this.trabVigCons = trabVigCons;
	}

	public BigDecimal getTrabVigEve() {
		return this.trabVigEve;
	}

	public void setTrabVigEve(BigDecimal trabVigEve) {
		this.trabVigEve = trabVigEve;
	}

	public BigDecimal getTrabVigPer() {
		return this.trabVigPer;
	}

	public void setTrabVigPer(BigDecimal trabVigPer) {
		this.trabVigPer = trabVigPer;
	}

	public String getZonaLada() {
		return this.zonaLada;
	}

	public void setZonaLada(String zonaLada) {
		this.zonaLada = zonaLada;
	}

}