package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.reingreso.util;

import static mx.gob.imss.ctirss.delta.model.gestion.integracion.common.OperacionesRegistros.generarNombre;
import static mx.gob.imss.ctirss.delta.model.gestion.integracion.common.OperacionesRegistros.safeNull;

import java.math.BigDecimal;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.reingreso.MovimientoReingresoType;

public class MovimientoReingresoTypeBuilder {

    private MovimientoReingresoType movimientoReingresoType;

    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String nombreAsegC;
    private String primerApellidoAsegC;
    private String segundoApellidoAsegC;
    private String nombreDh;
    private String primerApellidoDh;
    private String segundoApellidoDh;

    public MovimientoReingresoTypeBuilder () {
        movimientoReingresoType = new MovimientoReingresoType();
    }

    public MovimientoReingresoType build() {

        String n = generarNombre(safeNull(nombre),
                safeNull(primerApellido),
                safeNull(segundoApellido));

        movimientoReingresoType.setNomAseg(n);

        n = generarNombre(safeNull(nombreAsegC),
                safeNull(primerApellidoAsegC),
                safeNull(segundoApellidoAsegC));

        if (!n.equals("$$")) {//No se agregan nombres vacios
            movimientoReingresoType.setNomAsegC(n);
        }

        n = generarNombre(safeNull(nombreDh),
                safeNull(primerApellidoDh),
                safeNull(segundoApellidoDh));

        if (!n.equals("$$")) {//No se agregan nombres vacios
            movimientoReingresoType.setNombreDh(n);
        }

        return movimientoReingresoType;
    }

    public MovimientoReingresoTypeBuilder withDelOrig(int delOrig) {
        movimientoReingresoType.setDelOrig(delOrig);
        return this;
    }

    public MovimientoReingresoTypeBuilder withSubOrig(int subOrig) {
        movimientoReingresoType.setSubOrig(subOrig);
        return this;
    }

    public MovimientoReingresoTypeBuilder withCveAplic(int cveAplic) {
        movimientoReingresoType.setCveAplic(cveAplic);
        return this;
    }

    public MovimientoReingresoTypeBuilder withTpMovto(int tpMovto) {
        movimientoReingresoType.setTpMovto(tpMovto);
        return this;
    }

    public MovimientoReingresoTypeBuilder withOrigenMov(int origenMov) {
        movimientoReingresoType.setOrigenMov(origenMov);
        return this;
    }

    public MovimientoReingresoTypeBuilder withNumFolio(int numFolio) {
        movimientoReingresoType.setNumFolio(numFolio);
        return this;
    }

    public MovimientoReingresoTypeBuilder withArgumento(int argumento) {
        movimientoReingresoType.setArgumento(argumento);
        return this;
    }

    public MovimientoReingresoTypeBuilder withRegPatron(String regPatron) {
        movimientoReingresoType.setRegPatron(regPatron);
        return this;
    }

    public MovimientoReingresoTypeBuilder withDigVrPat(int digVrPat) {
        movimientoReingresoType.setDigVrPat(digVrPat);
        return this;
    }

    public MovimientoReingresoTypeBuilder withFMovto(Date fMovto) {
        movimientoReingresoType.setFMovto(fMovto);
        return this;
    }

    public MovimientoReingresoTypeBuilder withFRecepMovi(Date fRecepMovi) {
        movimientoReingresoType.setFRecepMovi(fRecepMovi);
        return this;
    }

    public MovimientoReingresoTypeBuilder withCveUnica(String cveUnica) {
        movimientoReingresoType.setCveUnica(cveUnica);
        return this;
    }

    public MovimientoReingresoTypeBuilder withIdSubrServ(int idSubrServ) {
        movimientoReingresoType.setIdSubrServ(idSubrServ);
        return this;
    }

    public MovimientoReingresoTypeBuilder withIdEventual(int idEventual) {
        movimientoReingresoType.setIdEventual(idEventual);
        return this;
    }

    public MovimientoReingresoTypeBuilder withNumSegSoc(String numSegSoc) {
        movimientoReingresoType.setNumSegSoc(numSegSoc);
        return this;
    }

    public MovimientoReingresoTypeBuilder withDigVrNss(int digVrNss) {
        movimientoReingresoType.setDigVrNss(digVrNss);
        return this;
    }

    public MovimientoReingresoTypeBuilder withIdExtemp(int idExtemp) {
        movimientoReingresoType.setIdExtemp(idExtemp);
        return this;
    }

    public MovimientoReingresoTypeBuilder withReducPago(int reducPago) {
        movimientoReingresoType.setReducPago(reducPago);
        return this;
    }

    public MovimientoReingresoTypeBuilder withExtODel(int extODel) {
        movimientoReingresoType.setExtODel(extODel);
        return this;
    }

    public MovimientoReingresoTypeBuilder withSalBase(BigDecimal salBase) {
        movimientoReingresoType.setSalBase(salBase);
        return this;
    }

    public MovimientoReingresoTypeBuilder withSalInfonavit(BigDecimal salInfonavit) {
        movimientoReingresoType.setSalInfonavit(salInfonavit);
        return this;
    }

    public MovimientoReingresoTypeBuilder withTpSalario(int tpSalario) {
        movimientoReingresoType.setTpSalario(tpSalario);
        return this;
    }

    public MovimientoReingresoTypeBuilder withSexo(int sexo) {
        movimientoReingresoType.setSexo(sexo);
        return this;
    }

    public MovimientoReingresoTypeBuilder withMesNac(int mesNac) {
        movimientoReingresoType.setMesNac(mesNac);
        return this;
    }

    public MovimientoReingresoTypeBuilder withLugarNac(int lugarNac) {
        movimientoReingresoType.setLugarNac(lugarNac);
        return this;
    }

    public MovimientoReingresoTypeBuilder withUmf(int umf) {
        movimientoReingresoType.setUmf(umf);
        return this;
    }

    public MovimientoReingresoTypeBuilder withAutPerm(int autPerm) {
        movimientoReingresoType.setAutPerm(autPerm);
        return this;
    }

    public MovimientoReingresoTypeBuilder withDelDest(int delDest) {
        movimientoReingresoType.setDelDest(delDest);
        return this;
    }

    public MovimientoReingresoTypeBuilder withSubDest(int subDest) {
        movimientoReingresoType.setSubDest(subDest);
        return this;
    }

    public MovimientoReingresoTypeBuilder withTpDerech(int tpDerech) {
        movimientoReingresoType.setTpDerech(tpDerech);
        return this;
    }

    public MovimientoReingresoTypeBuilder withAaNac(int aaNac) {
        movimientoReingresoType.setAaNac(aaNac);
        return this;
    }

    public MovimientoReingresoTypeBuilder withSituacion(int situacion) {
        movimientoReingresoType.setSituacion(situacion);
        return this;
    }

    public MovimientoReingresoTypeBuilder withTsalODel(String tsalODel) {
        movimientoReingresoType.setTsalODel(tsalODel);
        return this;
    }

    public MovimientoReingresoTypeBuilder withMesNacAp(int mesNacAp) {
        movimientoReingresoType.setMesNacAp(mesNacAp);
        return this;
    }

    public MovimientoReingresoTypeBuilder withNssCorr(int nssCorr) {
        movimientoReingresoType.setNssCorr(nssCorr);
        return this;
    }

    public MovimientoReingresoTypeBuilder withDigVrNssCorr(int digVrNssCorr) {
        movimientoReingresoType.setDigVrNssCorr(digVrNssCorr);
        return this;
    }

    public MovimientoReingresoTypeBuilder withTpPens(int tpPens) {
        movimientoReingresoType.setTpPens(tpPens);
        return this;
    }

    public MovimientoReingresoTypeBuilder withAlfGuar(String alfGuar) {
        movimientoReingresoType.setAlfGuar(alfGuar);
        return this;
    }

    public MovimientoReingresoTypeBuilder withNumGuar(int numGuar) {
        movimientoReingresoType.setNumGuar(numGuar);
        return this;
    }

    public MovimientoReingresoTypeBuilder withCondicion(int condicion) {
        movimientoReingresoType.setCondicion(condicion);
        return this;
    }

    public MovimientoReingresoTypeBuilder withLocMpio(String locMpio) {
        movimientoReingresoType.setLocMpio(locMpio);
        return this;
    }

    public MovimientoReingresoTypeBuilder withTpProrroga(int tpProrroga) {
        movimientoReingresoType.setTpProrroga(tpProrroga);
        return this;
    }

    public MovimientoReingresoTypeBuilder withFecTerProrr(int fecTerProrr) {
        movimientoReingresoType.setFecTerProrr(fecTerProrr);
        return this;
    }

    public MovimientoReingresoTypeBuilder withIdPd(int idPd) {
        movimientoReingresoType.setIdPd(idPd);
        return this;
    }

    public MovimientoReingresoTypeBuilder withNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public MovimientoReingresoTypeBuilder withPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
        return this;
    }

    public MovimientoReingresoTypeBuilder withSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
        return this;
    }

    public MovimientoReingresoTypeBuilder withNombreAsegC(String nombreAsegC) {
        this.nombreAsegC = nombreAsegC;
        return this;
    }

    public MovimientoReingresoTypeBuilder withPrimerApellidoAsegC(String primerApellidoAsegC) {
        this.primerApellidoAsegC = primerApellidoAsegC;
        return this;
    }

    public MovimientoReingresoTypeBuilder withSegundoApellidoAsegC(String segundoApellidoAsegC) {
        this.segundoApellidoAsegC = segundoApellidoAsegC;
        return this;
    }

    public MovimientoReingresoTypeBuilder withNombreDh(String nombreDh) {
        this.nombreDh = nombreDh;
        return this;
    }

    public MovimientoReingresoTypeBuilder withPrimerApellidoDh(String primerApellidoDh) {
        this.primerApellidoDh = primerApellidoDh;
        return this;
    }

    public MovimientoReingresoTypeBuilder withSegundoApellidoDh(String segundoApellidoDh) {
        this.segundoApellidoDh = segundoApellidoDh;
        return this;
    }

}
