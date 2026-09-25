/**
 * 
 */
package mx.gob.imss.digital.modelo.sindo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Modelo de datos para el los movimientos de sindo
 * 
 * @author NOVUTECK1
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movimientoTrabajadorSindo", namespace = "http://mx.gob.imss.digital.modelo.sindo")
@XmlRootElement(name = "movimientoTrabajadorSindo", namespace = "http://mx.gob.imss.digital.modelo.sindo")
public class MovimientoTrabajadorSindo implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    private int delOrig;

    private int subOrig;

    private int cveAplic;

    private int tpMovto;

    private int origenMov;

    private int numFolio;

    private int argumento;

    private String regPatron;

    private int digVrPat;

    
    private Date fMovto;

    
    private Date fRecepMovi;

    private String cveUnica;

    private int idSubrServ;

    private int idEventual;

    private String numSegSoc;

    private int digVrNss;

    private String nomAseg;

    private int idExtemp;

    private int reducPago;

    private int extODel;

    private BigDecimal salBase;

    private BigDecimal salInfonavit;

    private int tpSalario;

    private int sexo;

    private int mesNac;

    private int lugarNac;

    private int umf;

    private int autPerm;

    private int delDest;

    private int subDest;

    private int tpDerech;

    private int aaNac;

    private int situacion;

    private String tsalODel;

    private String nombreDh;

    private int mesNacAp;

    private int nssCorr;

    private int digVrNssCorr;

    private String nomAsegC;

    private int tpPens;

    private String alfGuar;

    private int numGuar;

    private int condicion;

    private String locMpio;

    private int tpProrroga;

    private int fecTerProrr;

    private int idPd;

    private int ciz;
    /**
     * @return the delOrig
     */
    public int getDelOrig() {
        return delOrig;
    }

    /**
     * @param delOrig
     *            the delOrig to set
     */
    public void setDelOrig(int delOrig) {
        this.delOrig = delOrig;
    }

    /**
     * @return the subOrig
     */
    public int getSubOrig() {
        return subOrig;
    }

    /**
     * @param subOrig
     *            the subOrig to set
     */
    public void setSubOrig(int subOrig) {
        this.subOrig = subOrig;
    }

    /**
     * @return the cveAplic
     */
    public int getCveAplic() {
        return cveAplic;
    }

    /**
     * @param cveAplic
     *            the cveAplic to set
     */
    public void setCveAplic(int cveAplic) {
        this.cveAplic = cveAplic;
    }

    /**
     * @return the tpMovto
     */
    public int getTpMovto() {
        return tpMovto;
    }

    /**
     * @param tpMovto
     *            the tpMovto to set
     */
    public void setTpMovto(int tpMovto) {
        this.tpMovto = tpMovto;
    }

    /**
     * @return the origenMov
     */
    public int getOrigenMov() {
        return origenMov;
    }

    /**
     * @param origenMov
     *            the origenMov to set
     */
    public void setOrigenMov(int origenMov) {
        this.origenMov = origenMov;
    }

    /**
     * @return the numFolio
     */
    public int getNumFolio() {
        return numFolio;
    }

    /**
     * @param numFolio
     *            the numFolio to set
     */
    public void setNumFolio(int numFolio) {
        this.numFolio = numFolio;
    }

    /**
     * @return the argumento
     */
    public int getArgumento() {
        return argumento;
    }

    /**
     * @param argumento
     *            the argumento to set
     */
    public void setArgumento(int argumento) {
        this.argumento = argumento;
    }

    /**
     * @return the regPatron
     */
    public String getRegPatron() {
        return regPatron;
    }

    /**
     * @param regPatron
     *            the regPatron to set
     */
    public void setRegPatron(String regPatron) {
        this.regPatron = regPatron;
    }

    /**
     * @return the digVrPat
     */
    public int getDigVrPat() {
        return digVrPat;
    }

    /**
     * @param digVrPat
     *            the digVrPat to set
     */
    public void setDigVrPat(int digVrPat) {
        this.digVrPat = digVrPat;
    }

    /**
     * @return the fMovto
     */
    public Date getfMovto() {
        return fMovto;
    }

    /**
     * @param fMovto
     *            the fMovto to set
     */
    public void setfMovto(Date fMovto) {
        this.fMovto = fMovto;
    }

    /**
     * @return the fRecepMovi
     */
    public Date getfRecepMovi() {
        return fRecepMovi;
    }

    /**
     * @param fRecepMovi
     *            the fRecepMovi to set
     */
    public void setfRecepMovi(Date fRecepMovi) {
        this.fRecepMovi = fRecepMovi;
    }

    /**
     * @return the cveUnica
     */
    public String getCveUnica() {
        return cveUnica;
    }

    /**
     * @param cveUnica
     *            the cveUnica to set
     */
    public void setCveUnica(String cveUnica) {
        this.cveUnica = cveUnica;
    }

    /**
     * @return the idSubrServ
     */
    public int getIdSubrServ() {
        return idSubrServ;
    }

    /**
     * @param idSubrServ
     *            the idSubrServ to set
     */
    public void setIdSubrServ(int idSubrServ) {
        this.idSubrServ = idSubrServ;
    }

    /**
     * @return the idEventual
     */
    public int getIdEventual() {
        return idEventual;
    }

    /**
     * @param idEventual
     *            the idEventual to set
     */
    public void setIdEventual(int idEventual) {
        this.idEventual = idEventual;
    }

    /**
     * @return the numSegSoc
     */
    public String getNumSegSoc() {
        return numSegSoc;
    }

    /**
     * @param numSegSoc
     *            the numSegSoc to set
     */
    public void setNumSegSoc(String numSegSoc) {
        this.numSegSoc = numSegSoc;
    }

    /**
     * @return the digVrNss
     */
    public int getDigVrNss() {
        return digVrNss;
    }

    /**
     * @param digVrNss
     *            the digVrNss to set
     */
    public void setDigVrNss(int digVrNss) {
        this.digVrNss = digVrNss;
    }

    /**
     * @return the nomAseg
     */
    public String getNomAseg() {
        return nomAseg;
    }

    /**
     * @param nomAseg
     *            the nomAseg to set
     */
    public void setNomAseg(String nomAseg) {
        this.nomAseg = nomAseg;
    }

    /**
     * @return the idExtemp
     */
    public int getIdExtemp() {
        return idExtemp;
    }

    /**
     * @param idExtemp
     *            the idExtemp to set
     */
    public void setIdExtemp(int idExtemp) {
        this.idExtemp = idExtemp;
    }

    /**
     * @return the reducPago
     */
    public int getReducPago() {
        return reducPago;
    }

    /**
     * @param reducPago
     *            the reducPago to set
     */
    public void setReducPago(int reducPago) {
        this.reducPago = reducPago;
    }

    /**
     * @return the extODel
     */
    public int getExtODel() {
        return extODel;
    }

    /**
     * @param extODel
     *            the extODel to set
     */
    public void setExtODel(int extODel) {
        this.extODel = extODel;
    }

    /**
     * @return the salBase
     */
    public BigDecimal getSalBase() {
        return salBase;
    }

    /**
     * @param salBase
     *            the salBase to set
     */
    public void setSalBase(BigDecimal salBase) {
        this.salBase = salBase;
    }

    /**
     * @return the salInfonavit
     */
    public BigDecimal getSalInfonavit() {
        return salInfonavit;
    }

    /**
     * @param salInfonavit
     *            the salInfonavit to set
     */
    public void setSalInfonavit(BigDecimal salInfonavit) {
        this.salInfonavit = salInfonavit;
    }

    /**
     * @return the tpSalario
     */
    public int getTpSalario() {
        return tpSalario;
    }

    /**
     * @param tpSalario
     *            the tpSalario to set
     */
    public void setTpSalario(int tpSalario) {
        this.tpSalario = tpSalario;
    }

    /**
     * @return the sexo
     */
    public int getSexo() {
        return sexo;
    }

    /**
     * @param sexo
     *            the sexo to set
     */
    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the mesNac
     */
    public int getMesNac() {
        return mesNac;
    }

    /**
     * @param mesNac
     *            the mesNac to set
     */
    public void setMesNac(int mesNac) {
        this.mesNac = mesNac;
    }

    /**
     * @return the lugarNac
     */
    public int getLugarNac() {
        return lugarNac;
    }

    /**
     * @param lugarNac
     *            the lugarNac to set
     */
    public void setLugarNac(int lugarNac) {
        this.lugarNac = lugarNac;
    }

    /**
     * @return the umf
     */
    public int getUmf() {
        return umf;
    }

    /**
     * @param umf
     *            the umf to set
     */
    public void setUmf(int umf) {
        this.umf = umf;
    }

    /**
     * @return the autPerm
     */
    public int getAutPerm() {
        return autPerm;
    }

    /**
     * @param autPerm
     *            the autPerm to set
     */
    public void setAutPerm(int autPerm) {
        this.autPerm = autPerm;
    }

    /**
     * @return the delDest
     */
    public int getDelDest() {
        return delDest;
    }

    /**
     * @param delDest
     *            the delDest to set
     */
    public void setDelDest(int delDest) {
        this.delDest = delDest;
    }

    /**
     * @return the subDest
     */
    public int getSubDest() {
        return subDest;
    }

    /**
     * @param subDest
     *            the subDest to set
     */
    public void setSubDest(int subDest) {
        this.subDest = subDest;
    }

    /**
     * @return the tpDerech
     */
    public int getTpDerech() {
        return tpDerech;
    }

    /**
     * @param tpDerech
     *            the tpDerech to set
     */
    public void setTpDerech(int tpDerech) {
        this.tpDerech = tpDerech;
    }

    /**
     * @return the aaNac
     */
    public int getAaNac() {
        return aaNac;
    }

    /**
     * @param aaNac
     *            the aaNac to set
     */
    public void setAaNac(int aaNac) {
        this.aaNac = aaNac;
    }

    /**
     * @return the situacion
     */
    public int getSituacion() {
        return situacion;
    }

    /**
     * @param situacion
     *            the situacion to set
     */
    public void setSituacion(int situacion) {
        this.situacion = situacion;
    }

    /**
     * @return the tsalODel
     */
    public String getTsalODel() {
        return tsalODel;
    }

    /**
     * @param tsalODel
     *            the tsalODel to set
     */
    public void setTsalODel(String tsalODel) {
        this.tsalODel = tsalODel;
    }

    /**
     * @return the nombreDh
     */
    public String getNombreDh() {
        return nombreDh;
    }

    /**
     * @param nombreDh
     *            the nombreDh to set
     */
    public void setNombreDh(String nombreDh) {
        this.nombreDh = nombreDh;
    }

    /**
     * @return the mesNacAp
     */
    public int getMesNacAp() {
        return mesNacAp;
    }

    /**
     * @param mesNacAp
     *            the mesNacAp to set
     */
    public void setMesNacAp(int mesNacAp) {
        this.mesNacAp = mesNacAp;
    }

    /**
     * @return the nssCorr
     */
    public int getNssCorr() {
        return nssCorr;
    }

    /**
     * @param nssCorr
     *            the nssCorr to set
     */
    public void setNssCorr(int nssCorr) {
        this.nssCorr = nssCorr;
    }

    /**
     * @return the digVrNssCorr
     */
    public int getDigVrNssCorr() {
        return digVrNssCorr;
    }

    /**
     * @param digVrNssCorr
     *            the digVrNssCorr to set
     */
    public void setDigVrNssCorr(int digVrNssCorr) {
        this.digVrNssCorr = digVrNssCorr;
    }

    /**
     * @return the nomAsegC
     */
    public String getNomAsegC() {
        return nomAsegC;
    }

    /**
     * @param nomAsegC
     *            the nomAsegC to set
     */
    public void setNomAsegC(String nomAsegC) {
        this.nomAsegC = nomAsegC;
    }

    /**
     * @return the tpPens
     */
    public int getTpPens() {
        return tpPens;
    }

    /**
     * @param tpPens
     *            the tpPens to set
     */
    public void setTpPens(int tpPens) {
        this.tpPens = tpPens;
    }

    /**
     * @return the alfGuar
     */
    public String getAlfGuar() {
        return alfGuar;
    }

    /**
     * @param alfGuar
     *            the alfGuar to set
     */
    public void setAlfGuar(String alfGuar) {
        this.alfGuar = alfGuar;
    }

    /**
     * @return the numGuar
     */
    public int getNumGuar() {
        return numGuar;
    }

    /**
     * @param numGuar
     *            the numGuar to set
     */
    public void setNumGuar(int numGuar) {
        this.numGuar = numGuar;
    }

    /**
     * @return the condicion
     */
    public int getCondicion() {
        return condicion;
    }

    /**
     * @param condicion
     *            the condicion to set
     */
    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }

    /**
     * @return the locMpio
     */
    public String getLocMpio() {
        return locMpio;
    }

    /**
     * @param locMpio
     *            the locMpio to set
     */
    public void setLocMpio(String locMpio) {
        this.locMpio = locMpio;
    }

    /**
     * @return the tpProrroga
     */
    public int getTpProrroga() {
        return tpProrroga;
    }

    /**
     * @param tpProrroga
     *            the tpProrroga to set
     */
    public void setTpProrroga(int tpProrroga) {
        this.tpProrroga = tpProrroga;
    }

    /**
     * @return the fecTerProrr
     */
    public int getFecTerProrr() {
        return fecTerProrr;
    }

    /**
     * @param fecTerProrr
     *            the fecTerProrr to set
     */
    public void setFecTerProrr(int fecTerProrr) {
        this.fecTerProrr = fecTerProrr;
    }

    /**
     * @return the idPd
     */
    public int getIdPd() {
        return idPd;
    }

    /**
     * @param idPd
     *            the idPd to set
     */
    public void setIdPd(int idPd) {
        this.idPd = idPd;
    }

    /**
     * @return the ciz
     */
    public int getCiz() {
        return ciz;
    }

    /**
     * @param ciz the ciz to set
     */
    public void setCiz(int ciz) {
        this.ciz = ciz;
    }

    
}
