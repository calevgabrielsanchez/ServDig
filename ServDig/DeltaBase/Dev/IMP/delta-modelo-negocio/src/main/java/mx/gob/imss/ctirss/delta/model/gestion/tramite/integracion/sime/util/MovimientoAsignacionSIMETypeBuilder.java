package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

import java.util.Date;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.MovimientoAsignacionSIMEType;

public class MovimientoAsignacionSIMETypeBuilder {

    private MovimientoAsignacionSIMEType movimientoAsignacionSIMEType;

    public MovimientoAsignacionSIMEType build() {
        return movimientoAsignacionSIMEType;
    }

    public MovimientoAsignacionSIMETypeBuilder() {
        movimientoAsignacionSIMEType = new MovimientoAsignacionSIMEType();
    }

    public MovimientoAsignacionSIMETypeBuilder withRegistroPatronal(String registroPatronal) {
        movimientoAsignacionSIMEType.setRegistroPatronal(escapeSpecialChars(registroPatronal));
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withNss(long nss) {
        movimientoAsignacionSIMEType.setNss(nss);
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withPrimerApellido(String primerApellido) {
        movimientoAsignacionSIMEType.setPrimerApellido(escapeSpecialChars(primerApellido));
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withSegundoApellido(String segundoApellido) {
        movimientoAsignacionSIMEType.setSegundoApellido(escapeSpecialChars(segundoApellido));
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withNombre(String nombre) {
        movimientoAsignacionSIMEType.setNombre(escapeSpecialChars(nombre));
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withSalarioBase(int salarioBase) {
        movimientoAsignacionSIMEType.setSalarioBase(salarioBase);
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withCampoGenerico(String campoGenerico) {
        movimientoAsignacionSIMEType.setCampoGenerico(escapeSpecialChars(campoGenerico));
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withTipoTrabajor(int tipoTrabajor) {
        movimientoAsignacionSIMEType.setTipoTrabajor(tipoTrabajor);
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withTipoSalario(int tipoSalario) {
        movimientoAsignacionSIMEType.setTipoSalario(tipoSalario);
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withJornadaReducida(int jornadaReducida) {
        movimientoAsignacionSIMEType.setJornadaReducida(jornadaReducida);
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withFechaMovimiento(Date fechaMovimiento) {
        movimientoAsignacionSIMEType.setFechaMovimiento(fechaMovimiento);
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withUnidadMedica(int unidadMedica) {
        movimientoAsignacionSIMEType.setUnidadMedica(unidadMedica);
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withCampoGenerico2(String campoGenerico2) {
        movimientoAsignacionSIMEType.setCampoGenerico2(escapeSpecialChars(campoGenerico2));
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withTipoMovimiento(int tipoMovimiento) {
        movimientoAsignacionSIMEType.setTipoMovimiento(tipoMovimiento);
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withGuia(int guia) {
        movimientoAsignacionSIMEType.setGuia(guia);
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withClave(String clave) {
        movimientoAsignacionSIMEType.setClave(escapeSpecialChars(clave));
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withCampoGenerico3(String campoGenerico3) {
        movimientoAsignacionSIMEType.setCampoGenerico3(escapeSpecialChars(campoGenerico3));
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withCurp(String curp) {
        movimientoAsignacionSIMEType.setCurp(escapeSpecialChars(curp));
        return this;
    }
    public MovimientoAsignacionSIMETypeBuilder withIdentificadorFormato(int identificadorFormato) {
        movimientoAsignacionSIMEType.setIdentificadorFormato(identificadorFormato);
        return this;
    }

    private String escapeSpecialChars(String str) {
        return str.toUpperCase()
            .replaceAll("[\u00D1\u00F1]", "#")
            .replaceAll("(?i)[\u00C1\u00C4]", "A")
            .replaceAll("(?i)[\u00C9\u00CB]", "E")
            .replaceAll("(?i)[\u00CD\u00CF]", "I")
            .replaceAll("(?i)[\u00D3\u00D6]", "O")
            .replaceAll("(?i)[\u00DA\u00DC]", "U")
            .replaceAll("\u007E-\u00FF", "")
            .replaceAll("([\\[-\u00FF -/:-@])(\\1)+", "$1");
    }

}
