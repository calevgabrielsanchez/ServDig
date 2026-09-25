package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.movPat.wizard;

public enum AvisosVentanilla {
    FUSION(21, 7, 
        "ADICIONALMENTE, DEBER&Aacute; PRESENTAR EL TRA&Aacute;MITE DE AVISO DE MODIFICACI&Oacute;N AL REGISTRO PATRONAL POR FUSI&Oacute;N, POR LO CUAL DEBER&Aacute; LLEVAR LA SIGUIENTE DOCUMENTACI&Oacute;N:"),

    SUBSTITUCION_PATRONAL(20, 5, 
        "ADICIONALMENTE, DEBER&Aacute; PRESENTAR EL TR&Aacute;MITE DE AVISO DE MODIFICACI&Oacute;N AL REGISTRO PATRONAL POR SUSTITUCI&Oacute;N PATRONAL, POR LO CUAL DEBER&Aacute; LLEVAR LA SIGUIENTE DOCUMENTACI&Oacute;N:"),

    CAMBIO_DE_DOMICILIO_MISMO_MUNICIPIO(7, 5,
        "ADICIONALMENTE, DEBER&Aacute; PRESENTAR EL TR&Aacute;MITE DE AVISO DE MODIFICACI&Oacute;N AL REGISTRO PATRONAL POR CAMBIO DE DOMICILIO, POR LO CUAL DEBER&Aacute; LLEVAR LA SIGUIENTE DOCUMENTACI&Oacute;N:"),

    RENUDACION_DE_ACTIVIDADES(22, 5,
        "ADICIONALMENTE, DEBER&Aacute; PRESENTAR EL TR&Aacute;MITE DE AVISO DE MODIFICACI&Oacute;N AL REGISTRO PATRONAL POR REANUDACI&Oacute;N DE ACTIVIDADES, POR LO CUAL DEBER&Aacute; LLEVAR LA SIGUIENTE DOCUMENTACI&Oacute;N:"),

    CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO(176, 7,
        "ADICIONALMENTE, DEBER&Aacute; PRESENTAR EL TR&Aacute;MITE DE AVISO DE MODIFICACI&Oacute;N AL REGISTRO PATRONAL POR CAMBIO DE DOMICILIO, POR LO CUAL DEBER&Aacute; LLEVAR LA SIGUIENTE DOCUMENTACI&Oacute;N:"),

    Ecision_para_la_Empresa(19, 5,
        "ADICIONALMENTE, DEBER&Aacute; PRESENTAR EL TR&Aacute;MITE DE AVISO DE MODIFICACI&Oacute;N AL REGISTRO PATRONAL POR ESCISI&Oacute;N, POR LO CUAL DEBER&Aacute; LLEVAR LA SIGUIENTE DOCUMENTACI&Oacute;N:");

    private final Integer idTramite;
    private final int position;
    private final String aviso;

    private AvisosVentanilla(Integer idTramite, int position, String aviso) {
        this.idTramite = idTramite;
        this.position = position;
        this.aviso = aviso;
    }

    public Integer getIdTramite() {
        return idTramite;
    }

    public int getPosition() {
        return position;
    }

    public String getAviso() {
        return aviso;
    }

    public static AvisosVentanilla findByIdtramite(Integer idTramite) {
        for (AvisosVentanilla aviso : values()) {
            if (aviso.idTramite.equals(idTramite)) {
                return aviso;
            }
        }
        return null;
    }
}
