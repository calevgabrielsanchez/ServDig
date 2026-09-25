package mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.util;

import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;
import javax.xml.datatype.XMLGregorianCalendar;

public class MovCorreccionesDatosAseguradoTypeBuilder {

    private MovCorreccionesDatosAseguradoType movCorreccionesDatosAseguradoType;

    public MovCorreccionesDatosAseguradoTypeBuilder () {
        movCorreccionesDatosAseguradoType = new MovCorreccionesDatosAseguradoType();
    }

    public MovCorreccionesDatosAseguradoType build() {
        return movCorreccionesDatosAseguradoType;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withDelOrig(int delOrig) {
        movCorreccionesDatosAseguradoType.setDelOrig(delOrig);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withSubOrig(int subOrig) {
        movCorreccionesDatosAseguradoType.setSubOrig(subOrig);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withCveAplic(int cveAplic) {
        movCorreccionesDatosAseguradoType.setCveAplic(cveAplic);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withTpMovto(int tpMovto) {
        movCorreccionesDatosAseguradoType.setTpMovto(tpMovto);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withOrigenMov(int origenMov) {
        movCorreccionesDatosAseguradoType.setOrigenMov(origenMov);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withNumFolio(String numFolio) {
        movCorreccionesDatosAseguradoType.setNumFolio(numFolio);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withArgumento(int argumento) {
        movCorreccionesDatosAseguradoType.setArgumento(argumento);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withRegPatron(String regPatron) {
        movCorreccionesDatosAseguradoType.setRegPatron(regPatron);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withDigVrPat(int digVrPat) {
        movCorreccionesDatosAseguradoType.setDigVrPat(digVrPat);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withFMovto(XMLGregorianCalendar fMovto) {
        movCorreccionesDatosAseguradoType.setFMovto(fMovto);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withFRecepMovi(XMLGregorianCalendar fRecepMovi) {
        movCorreccionesDatosAseguradoType.setFRecepMovi(fRecepMovi);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withCveUnica(String cveUnica) {
        movCorreccionesDatosAseguradoType.setCveUnica(cveUnica);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withIdSubrServ(int idSubrServ) {
        movCorreccionesDatosAseguradoType.setIdSubrServ(idSubrServ);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withIdEventual(int idEventual) {
        movCorreccionesDatosAseguradoType.setIdEventual(idEventual);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withNumSegSoc(String numSegSoc) {
        movCorreccionesDatosAseguradoType.setNumSegSoc(numSegSoc);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withDigVrNss(int digVrNss) {
        movCorreccionesDatosAseguradoType.setDigVrNss(digVrNss);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withNomAseg(String nomAseg) {
        movCorreccionesDatosAseguradoType.setNomAseg(nomAseg);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withIdExtemp(int idExtemp) {
        movCorreccionesDatosAseguradoType.setIdExtemp(idExtemp);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withReducPago(int reducPago) {
        movCorreccionesDatosAseguradoType.setReducPago(reducPago);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withExtODel(int extODel) {
        movCorreccionesDatosAseguradoType.setExtODel(extODel);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withSalBase(int salBase) {
        movCorreccionesDatosAseguradoType.setSalBase(salBase);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withSalInfonavit(int salInfonavit) {
        movCorreccionesDatosAseguradoType.setSalInfonavit(salInfonavit);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withTpSalario(int tpSalario) {
        movCorreccionesDatosAseguradoType.setTpSalario(tpSalario);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withSexo(int sexo) {
        movCorreccionesDatosAseguradoType.setSexo(sexo);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withMesNac(int mesNac) {
        movCorreccionesDatosAseguradoType.setMesNac(mesNac);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withLugarNac(int lugarNac) {
        movCorreccionesDatosAseguradoType.setLugarNac(lugarNac);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withUmf(int umf) {
        movCorreccionesDatosAseguradoType.setUmf(umf);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withAutPerm(int autPerm) {
        movCorreccionesDatosAseguradoType.setAutPerm(autPerm);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withDelDest(int delDest) {
        movCorreccionesDatosAseguradoType.setDelDest(delDest);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withSubDest(int subDest) {
        movCorreccionesDatosAseguradoType.setSubDest(subDest);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withTpDerech(int tpDerech) {
        movCorreccionesDatosAseguradoType.setTpDerech(tpDerech);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withAaNac(int aaNac) {
        movCorreccionesDatosAseguradoType.setAaNac(aaNac);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withSituacion(int situacion) {
        movCorreccionesDatosAseguradoType.setSituacion(situacion);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withTsalODel(String tsalODel) {
        movCorreccionesDatosAseguradoType.setTsalODel(tsalODel);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withNombreDh(String nombreDh) {
        movCorreccionesDatosAseguradoType.setNombreDh(nombreDh);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withMesNacAp(int mesNacAp) {
        movCorreccionesDatosAseguradoType.setMesNacAp(mesNacAp);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withNssCorr(int nssCorr) {
        movCorreccionesDatosAseguradoType.setNssCorr(nssCorr);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withDigVrNssCorr(int digVrNssCorr) {
        movCorreccionesDatosAseguradoType.setDigVrNssCorr(digVrNssCorr);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withNomAsegC(String nomAsegC) {
        movCorreccionesDatosAseguradoType.setNomAsegC(nomAsegC);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withTpPens(int tpPens) {
        movCorreccionesDatosAseguradoType.setTpPens(tpPens);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withAlfGuar(String alfGuar) {
        movCorreccionesDatosAseguradoType.setAlfGuar(alfGuar);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withNumGuar(int numGuar) {
        movCorreccionesDatosAseguradoType.setNumGuar(numGuar);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withCondicion(int condicion) {
        movCorreccionesDatosAseguradoType.setCondicion(condicion);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withLocMpio(String locMpio) {
        movCorreccionesDatosAseguradoType.setLocMpio(locMpio);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withTpProrroga(int tpProrroga) {
        movCorreccionesDatosAseguradoType.setTpProrroga(tpProrroga);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withFecTerProrr(XMLGregorianCalendar fecTerProrr) {
        movCorreccionesDatosAseguradoType.setFecTerProrr(fecTerProrr);
        return this;
    }

    public MovCorreccionesDatosAseguradoTypeBuilder withIdPd(int idPd) {
        movCorreccionesDatosAseguradoType.setIdPd(idPd);
        return this;
    }

}

