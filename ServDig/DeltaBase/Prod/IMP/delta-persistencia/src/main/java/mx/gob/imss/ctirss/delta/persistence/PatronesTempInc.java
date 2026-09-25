package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PATRONES_TEMP_INC database table.
 * 
 */
@Entity
@Table(name="PATRONES_TEMP_INC")
public class PatronesTempInc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ADIC_PENS", length=6)
	private String adicPens;

	@Column(name="ANIO_CVE_MODAL", length=2)
	private String anioCveModal;

	@Column(length=50)
	private String calle;

	@Column(name="CAUSA_BAJA", length=1)
	private String causaBaja;

	@Column(name="COD_POS", length=5)
	private String codPos;

	@Column(length=50)
	private String colonia;

	@Column(length=18)
	private String curp;

	@Column(name="CVE_CIZ", length=1)
	private String cveCiz;

	@Column(name="CVE_DELEGACION", length=2)
	private String cveDelegacion;

	@Column(name="CVE_DELEGACION_CTL", length=2)
	private String cveDelegacionCtl;

	@Column(name="CVE_EXTENSION", length=9)
	private String cveExtension;

	@Column(name="CVE_LADA", length=6)
	private String cveLada;

	@Column(name="CVE_MODALIDAD", length=2)
	private String cveModalidad;

	@Column(name="CVE_MODALIDAD_U", length=2)
	private String cveModalidadU;

	@Column(name="CVE_MUNICIPIO", length=3)
	private String cveMunicipio;

	@Id
	@Column(name="CVE_PATRON", length=8)
	private String cvePatron;

	@Column(name="CVE_PATRON_U", length=8)
	private String cvePatronU;

	@Column(name="CVE_SUBDELEGACION", length=2)
	private String cveSubdelegacion;

	@Column(name="CVE_SUBDELEGACION_CTL", length=2)
	private String cveSubdelegacionCtl;

	@Column(name="CVE_TIPO_MOVTO", length=1)
	private String cveTipoMovto;

	@Column(name="DIG_VERIFICADOR", length=1)
	private String digVerificador;

	@Column(length=40)
	private String domicilio;

	@Column(length=30)
	private String email;

	@Column(length=15)
	private String fax;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ACT")
	private Date fecAct;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ACT_CONTADORES")
	private Date fecActContadores;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INI_HUELGA")
	private Date fecIniHuelga;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVTO")
	private Date fecMovto;

	@Column(length=40)
	private String giro;

	@Column(name="ID_HUELGA", length=1)
	private String idHuelga;

	@Column(name="ID_SUBR_SERV", length=1)
	private String idSubrServ;

	@Column(name="IND_ORIGEN", length=1)
	private String indOrigen;

	@Column(name="IND_PAT_OUTS", length=1)
	private String indPatOuts;

	@Column(name="IND_TIP_MOD32", length=1)
	private String indTipMod32;

	@Column(name="IND_TIP_MOVTO_MAC", length=1)
	private String indTipMovtoMac;

	@Column(length=40)
	private String localidad;

	@Column(name="MES_EMISION", length=2)
	private String mesEmision;

	@Column(name="N_REL_MPIO", length=8)
	private String nRelMpio;

	@Column(length=80)
	private String nombre;

	@Column(name="NUM_EXTERIOR", length=10)
	private String numExterior;

	@Column(name="NUM_INTERIOR", length=10)
	private String numInterior;

	@Column(name="NUM_TRA_MEX_EXTR", precision=6)
	private BigDecimal numTraMexExtr;

	@Column(name="NUM_TRA_VIG_CONS", precision=6)
	private BigDecimal numTraVigCons;

	@Column(name="NUM_TRA_VIG_EVEN", precision=6)
	private BigDecimal numTraVigEven;

	@Column(name="NUM_TRA_VIG_PERM", precision=6)
	private BigDecimal numTraVigPerm;

	@Column(name="PERIODO_EMISION", length=1)
	private String periodoEmision;

	@Column(name="PORC_AUS", length=3)
	private String porcAus;

	@Column(name="REF_NODEFINIDOS", length=115)
	private String refNodefinidos;

	@Column(name="REG_ORIG", length=732)
	private String regOrig;

	@Column(name="REPRE_LEGAL", length=50)
	private String repreLegal;

	@Column(length=13)
	private String rfc;

	@Column(name="SECTOR_NOTIFICACION", length=3)
	private String sectorNotificacion;

	@Column(length=15)
	private String telefono;

	@Column(name="TP_COTIZ", length=1)
	private String tpCotiz;

	@Column(name="TP_PAGO", length=1)
	private String tpPago;

	@Column(name="TP_PAT_MOD33", length=1)
	private String tpPatMod33;

	@Column(name="TP_PROVEE", length=2)
	private String tpProvee;

	@Column(name="ZONA_LADA", length=3)
	private String zonaLada;

    public PatronesTempInc() {
    }

	public String getAdicPens() {
		return this.adicPens;
	}

	public void setAdicPens(String adicPens) {
		this.adicPens = adicPens;
	}

	public String getAnioCveModal() {
		return this.anioCveModal;
	}

	public void setAnioCveModal(String anioCveModal) {
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

	public String getCodPos() {
		return this.codPos;
	}

	public void setCodPos(String codPos) {
		this.codPos = codPos;
	}

	public String getColonia() {
		return this.colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getCveCiz() {
		return this.cveCiz;
	}

	public void setCveCiz(String cveCiz) {
		this.cveCiz = cveCiz;
	}

	public String getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getCveDelegacionCtl() {
		return this.cveDelegacionCtl;
	}

	public void setCveDelegacionCtl(String cveDelegacionCtl) {
		this.cveDelegacionCtl = cveDelegacionCtl;
	}

	public String getCveExtension() {
		return this.cveExtension;
	}

	public void setCveExtension(String cveExtension) {
		this.cveExtension = cveExtension;
	}

	public String getCveLada() {
		return this.cveLada;
	}

	public void setCveLada(String cveLada) {
		this.cveLada = cveLada;
	}

	public String getCveModalidad() {
		return this.cveModalidad;
	}

	public void setCveModalidad(String cveModalidad) {
		this.cveModalidad = cveModalidad;
	}

	public String getCveModalidadU() {
		return this.cveModalidadU;
	}

	public void setCveModalidadU(String cveModalidadU) {
		this.cveModalidadU = cveModalidadU;
	}

	public String getCveMunicipio() {
		return this.cveMunicipio;
	}

	public void setCveMunicipio(String cveMunicipio) {
		this.cveMunicipio = cveMunicipio;
	}

	public String getCvePatron() {
		return this.cvePatron;
	}

	public void setCvePatron(String cvePatron) {
		this.cvePatron = cvePatron;
	}

	public String getCvePatronU() {
		return this.cvePatronU;
	}

	public void setCvePatronU(String cvePatronU) {
		this.cvePatronU = cvePatronU;
	}

	public String getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}

	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public String getCveSubdelegacionCtl() {
		return this.cveSubdelegacionCtl;
	}

	public void setCveSubdelegacionCtl(String cveSubdelegacionCtl) {
		this.cveSubdelegacionCtl = cveSubdelegacionCtl;
	}

	public String getCveTipoMovto() {
		return this.cveTipoMovto;
	}

	public void setCveTipoMovto(String cveTipoMovto) {
		this.cveTipoMovto = cveTipoMovto;
	}

	public String getDigVerificador() {
		return this.digVerificador;
	}

	public void setDigVerificador(String digVerificador) {
		this.digVerificador = digVerificador;
	}

	public String getDomicilio() {
		return this.domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Date getFecAct() {
		return this.fecAct;
	}

	public void setFecAct(Date fecAct) {
		this.fecAct = fecAct;
	}

	public Date getFecActContadores() {
		return this.fecActContadores;
	}

	public void setFecActContadores(Date fecActContadores) {
		this.fecActContadores = fecActContadores;
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

	public String getIndOrigen() {
		return this.indOrigen;
	}

	public void setIndOrigen(String indOrigen) {
		this.indOrigen = indOrigen;
	}

	public String getIndPatOuts() {
		return this.indPatOuts;
	}

	public void setIndPatOuts(String indPatOuts) {
		this.indPatOuts = indPatOuts;
	}

	public String getIndTipMod32() {
		return this.indTipMod32;
	}

	public void setIndTipMod32(String indTipMod32) {
		this.indTipMod32 = indTipMod32;
	}

	public String getIndTipMovtoMac() {
		return this.indTipMovtoMac;
	}

	public void setIndTipMovtoMac(String indTipMovtoMac) {
		this.indTipMovtoMac = indTipMovtoMac;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getMesEmision() {
		return this.mesEmision;
	}

	public void setMesEmision(String mesEmision) {
		this.mesEmision = mesEmision;
	}

	public String getNRelMpio() {
		return this.nRelMpio;
	}

	public void setNRelMpio(String nRelMpio) {
		this.nRelMpio = nRelMpio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public BigDecimal getNumTraMexExtr() {
		return this.numTraMexExtr;
	}

	public void setNumTraMexExtr(BigDecimal numTraMexExtr) {
		this.numTraMexExtr = numTraMexExtr;
	}

	public BigDecimal getNumTraVigCons() {
		return this.numTraVigCons;
	}

	public void setNumTraVigCons(BigDecimal numTraVigCons) {
		this.numTraVigCons = numTraVigCons;
	}

	public BigDecimal getNumTraVigEven() {
		return this.numTraVigEven;
	}

	public void setNumTraVigEven(BigDecimal numTraVigEven) {
		this.numTraVigEven = numTraVigEven;
	}

	public BigDecimal getNumTraVigPerm() {
		return this.numTraVigPerm;
	}

	public void setNumTraVigPerm(BigDecimal numTraVigPerm) {
		this.numTraVigPerm = numTraVigPerm;
	}

	public String getPeriodoEmision() {
		return this.periodoEmision;
	}

	public void setPeriodoEmision(String periodoEmision) {
		this.periodoEmision = periodoEmision;
	}

	public String getPorcAus() {
		return this.porcAus;
	}

	public void setPorcAus(String porcAus) {
		this.porcAus = porcAus;
	}

	public String getRefNodefinidos() {
		return this.refNodefinidos;
	}

	public void setRefNodefinidos(String refNodefinidos) {
		this.refNodefinidos = refNodefinidos;
	}

	public String getRegOrig() {
		return this.regOrig;
	}

	public void setRegOrig(String regOrig) {
		this.regOrig = regOrig;
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

	public String getSectorNotificacion() {
		return this.sectorNotificacion;
	}

	public void setSectorNotificacion(String sectorNotificacion) {
		this.sectorNotificacion = sectorNotificacion;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
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

	public String getTpProvee() {
		return this.tpProvee;
	}

	public void setTpProvee(String tpProvee) {
		this.tpProvee = tpProvee;
	}

	public String getZonaLada() {
		return this.zonaLada;
	}

	public void setZonaLada(String zonaLada) {
		this.zonaLada = zonaLada;
	}

}