package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.util;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;
import java.util.Date;

public class MovimientoPatronalTypeBuilder {

    private MovimientoPatronalType movimientoPatronalType;

    public MovimientoPatronalType build() {
        return movimientoPatronalType;
    }

    public MovimientoPatronalTypeBuilder() {
        movimientoPatronalType = new MovimientoPatronalType();
    }

    public MovimientoPatronalTypeBuilder withCiz(int ciz) {
        movimientoPatronalType.setCiz(ciz);
        return this;
    }

    public MovimientoPatronalTypeBuilder withDelegacionOrigen(int
	  delegacionOrigen) {
	  movimientoPatronalType.setDelegacionOrigen(delegacionOrigen); return this;
	}

    public MovimientoPatronalTypeBuilder withSubdelegacionOrigen(int subdelegacionOrigen) {
        movimientoPatronalType.setSubdelegacionOrigen(subdelegacionOrigen);
        return this;
    }
    public MovimientoPatronalTypeBuilder withClaveAplicacion(int claveAplicacion) {
        movimientoPatronalType.setClaveAplicacion(claveAplicacion);
        return this;
    }
    public MovimientoPatronalTypeBuilder withTipoMovimiento(int tipoMovimiento) {
        movimientoPatronalType.setTipoMovimiento(tipoMovimiento);
        return this;
    }
    public MovimientoPatronalTypeBuilder withOrigenMovimiento(int origenMovimiento) {
        movimientoPatronalType.setOrigenMovimiento(origenMovimiento);
        return this;
    }
    public MovimientoPatronalTypeBuilder withNumeroFolio(String numeroFolio) {
        movimientoPatronalType.setNumeroFolio(numeroFolio);
        return this;
    }
    public MovimientoPatronalTypeBuilder withRegistroPatronal(String registroPatronal) {
        movimientoPatronalType.setRegistroPatronal(registroPatronal);
        return this;
    }
    public MovimientoPatronalTypeBuilder withDigitoVerificador(int digitoVerificador) {
        movimientoPatronalType.setDigitoVerificador(digitoVerificador);
        return this;
    }
    public MovimientoPatronalTypeBuilder withFechaMovimiento(Date fechaMovimiento) {
        movimientoPatronalType.setFechaMovimiento(fechaMovimiento);
        return this;
    }
    public MovimientoPatronalTypeBuilder withGiro(String giro) {
        movimientoPatronalType.setGiro(giro);
        return this;
    }
    public MovimientoPatronalTypeBuilder withClase(int clase) {
        movimientoPatronalType.setClase(clase);
        return this;
    }
    public MovimientoPatronalTypeBuilder withFraccion(int fraccion) {
        movimientoPatronalType.setFraccion(fraccion);
        return this;
    }
    public MovimientoPatronalTypeBuilder withDivision(int division) {
        movimientoPatronalType.setDivision(division);
        return this;
    }
    public MovimientoPatronalTypeBuilder withGrupo(int grupo) {
        movimientoPatronalType.setGrupo(grupo);
        return this;
    }
    public MovimientoPatronalTypeBuilder withPrima(double prima) {
        movimientoPatronalType.setPrima(prima);
        return this;
    }
    public MovimientoPatronalTypeBuilder withCausa(int causa) {
        movimientoPatronalType.setCausa(causa);
        return this;
    }
    public MovimientoPatronalTypeBuilder withNombrePatron(String nombrePatron) {
        movimientoPatronalType.setNombrePatron(nombrePatron);
        return this;
    }
    public MovimientoPatronalTypeBuilder withNombrePatronalC(String nombrePatronalC) {
        movimientoPatronalType.setNombrePatronalC(nombrePatronalC);
        return this;
    }
    public MovimientoPatronalTypeBuilder withClaveMunicipio(String claveMunicipio) {
        movimientoPatronalType.setClaveMunicipio(claveMunicipio);
        return this;
    }
    public MovimientoPatronalTypeBuilder withDomicilioPatron(String domicilioPatron) {
        movimientoPatronalType.setDomicilioPatron(domicilioPatron);
        return this;
    }
    public MovimientoPatronalTypeBuilder withCodigoPostal(String codigoPostal) {
        movimientoPatronalType.setCodigoPostal(codigoPostal);
        return this;
    }
    public MovimientoPatronalTypeBuilder withLocalidad(String localidad) {
        movimientoPatronalType.setLocalidad(localidad);
        return this;
    }
    public MovimientoPatronalTypeBuilder withCurp(String curp) {
        movimientoPatronalType.setCurp(curp);
        return this;
    }
    public MovimientoPatronalTypeBuilder withRfc(String rfc) {
        movimientoPatronalType.setRfc(rfc);
        return this;
    }

    public MovimientoPatronalTypeBuilder withFechaRecepcion(Date fechaRecepcion) {
        movimientoPatronalType.setFechaRecepcion(fechaRecepcion);
        return this;
    }

    public MovimientoPatronalTypeBuilder withSubrogacionServicio(int subrogacionServicio) {
        movimientoPatronalType.setSubrogacionServicio(subrogacionServicio);
        return this;
    }

    public MovimientoPatronalTypeBuilder withFechaCambioCla(int fechaCambioCla) {
        movimientoPatronalType.setFechaCambioCla(fechaCambioCla);
        return this;
    }

    public MovimientoPatronalTypeBuilder withTipoPago(int tipoPago) {
        movimientoPatronalType.setTipoPago(tipoPago);
        return this;
    }

    public MovimientoPatronalTypeBuilder withMesEmi(int mesEmi) {
        movimientoPatronalType.setMesEmi(mesEmi);
        return this;
    }

    public MovimientoPatronalTypeBuilder withArgumento(String argumento) {
    	movimientoPatronalType.setArgumento(argumento);
    	return this;
    }

    public MovimientoPatronalTypeBuilder withPsp(int psp) {
        movimientoPatronalType.setPsp(psp);
        return this;
    }

}
