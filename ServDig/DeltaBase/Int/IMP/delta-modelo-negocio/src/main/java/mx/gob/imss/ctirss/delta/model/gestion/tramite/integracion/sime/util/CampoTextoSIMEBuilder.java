package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

import java.util.Date;

public class CampoTextoSIMEBuilder {

    private String valorStr;
    private Date valorDate;
    private Integer valorInt;
    private Long valorLong;
    private int longitud;

    public static CampoTextoSIME<?> newCampo(Integer valor, int longitud) {
        return new CampoTextoSIMEBuilder()
            .withValor(valor)
            .withLongitud(longitud)
            .build();
    }

    public static CampoTextoSIME<?> newCampo(Long valor, int longitud) {
        return new CampoTextoSIMEBuilder()
            .withValor(valor)
            .withLongitud(longitud)
            .build();
    }

    public static CampoTextoSIME<?> newCampo(Date valor) {
        return new CampoTextoSIMEBuilder()
            .withValor(valor)
            .build();
    }

    public static CampoTextoSIME<?> newCampo(String valor, int longitud) {
        return new CampoTextoSIMEBuilder()
            .withValor(valor)
            .withLongitud(longitud)
            .build();
    }

    public CampoTextoSIMEBuilder() {
    }

    public CampoTextoSIME<?> build() {
        if (valorDate != null) {
            return new CampoTextoSIMEDate(valorDate);
        }
        if (valorInt != null) {
            return new CampoTextoSIMEInt(valorInt, longitud);
        }
        if (valorLong != null) {
            return new CampoTextoSIMELong(valorLong, longitud);
        }
        return new CampoTextoSIMEString(valorStr, longitud);
    }

    public CampoTextoSIMEBuilder withValor(String valor) {
        valorStr = valor;
        return this;
    }
    public CampoTextoSIMEBuilder withValor(Date valor) {
        valorDate = valor;
        return this;
    }
    public CampoTextoSIMEBuilder withValor(Integer valor) {
        valorInt = valor;
        return this;
    }
    public CampoTextoSIMEBuilder withValor(Long valor) {
        valorLong = valor;
        return this;
    }
    public CampoTextoSIMEBuilder withLongitud(int longitud) {
        this.longitud = longitud;
        return this;
    }

}
