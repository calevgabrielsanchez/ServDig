package mx.gob.imss.ctirss.delta.model.gestion.integracion.asignacion.util;

import java.util.Date;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.asignacion.MovimientoAsignacionType;

import static mx.gob.imss.ctirss.delta.model.gestion.integracion.common.OperacionesRegistros.safeNull;
import static mx.gob.imss.ctirss.delta.model.gestion.integracion.common.OperacionesRegistros.generarNombre;

public class MovimientoAsignacionTypeBuilder {

    private MovimientoAsignacionType movimientoAsignacionType;

    private String nombre;
    private String primerApellido;
    private String segundoApellido;

    public MovimientoAsignacionTypeBuilder() {
        movimientoAsignacionType = new MovimientoAsignacionType();
    }

    public MovimientoAsignacionType build() {

        String n = generarNombre(
                safeNull(nombre),
                safeNull(primerApellido),
                safeNull(segundoApellido));

        movimientoAsignacionType.setNombre(n);

        return movimientoAsignacionType;
    }

    public MovimientoAsignacionTypeBuilder withCizOrigen(int cizOrigen) {
        movimientoAsignacionType.setCizOrigen(cizOrigen);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withDelOrigen(int delOrigen) {
        movimientoAsignacionType.setDelOrigen(delOrigen);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withSubdelOrigen(int subdelOrigen) {
        movimientoAsignacionType.setSubdelOrigen(subdelOrigen);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withCodEnvio(int codEnvio) {
        movimientoAsignacionType.setCodEnvio(codEnvio);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withCodRetorno(int codRetorno) {
        movimientoAsignacionType.setCodRetorno(codRetorno);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withCondicion(int condicion) {
        movimientoAsignacionType.setCondicion(condicion);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withTpMovto(int tpMovto) {
        movimientoAsignacionType.setTpMovto(tpMovto);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withOpcionMovto44(int opcionMovto44) {
        movimientoAsignacionType.setOpcionMovto44(opcionMovto44);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withNss(String nss) {
        movimientoAsignacionType.setNss(nss);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withDigver(int digver) {
        movimientoAsignacionType.setDigver(digver);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withSexo(int sexo) {
        movimientoAsignacionType.setSexo(sexo);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withMesNac(int mesNac) {
        movimientoAsignacionType.setMesNac(mesNac);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withLugarNac(int lugarNac) {
        movimientoAsignacionType.setLugarNac(lugarNac);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withNssC(String nssC) {
        movimientoAsignacionType.setNssC(nssC);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withDigverC(int digverC) {
        movimientoAsignacionType.setDigverC(digverC);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withIdUsuario(String idUsuario) {
        movimientoAsignacionType.setIdUsuario(idUsuario);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withNombreCond(String nombreCond) {
        movimientoAsignacionType.setNombreCond(nombreCond);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withTpError(String tpError) {
        movimientoAsignacionType.setTpError(tpError);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withUmf(int umf) {
        movimientoAsignacionType.setUmf(umf);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withOrigen(int origen) {
        movimientoAsignacionType.setOrigen(origen);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withFechaMovto(Date fechaMovto) {
        movimientoAsignacionType.setFechaMovto(fechaMovto);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withCurp(String curp) {
        movimientoAsignacionType.setCurp(curp);
        return this;
    }

    public MovimientoAsignacionTypeBuilder withNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public MovimientoAsignacionTypeBuilder withPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
        return this;
    }

    public MovimientoAsignacionTypeBuilder withSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
        return this;
    }

    private String safeNull(String val) {
        String value = val;
        if (val == null) {
            value = "";
        }
        return value;
    }

}
