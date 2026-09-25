package mx.gob.imss.cit.cda.web.constants;

public enum RolUsuarioEnum {

    AUTORIZADOR_OCE("JEFE DE OFICINA DE CLASIFICACION DE EMPRESAS","AUTORIZADOR", "atencionAutorizador",10), 
    AUTORIZADOR_DAV("JEFE DE DEPARTAMENTO AFILIACION VIGENCIA", "AUTORIZADOR","atencionAutorizador",11),
    AUTORIZADOR_DAV2("JEFE DE OFICINA DE AFILIACION", "AUTORIZADOR","atencionAutorizador",12),
    VENTANILLA("VENTANILLA", "RESPONSABLE","atencionResponsable",13),
    TIT_COORD_AFILIACION("TITULAR DE LA COORDINACION DE AFILIACION", "NORMATIVO","visorReportes", 1),
    TIT_AFILIACION_OBLIGATORIO("TITULAR DE LA DIVISION DE AFILIACION AL REGIMEN OBLIGATORIO", "NORMATIVO","visorReportes",2),
    TIT_INSCRIP_ASEG("TITULAR DE LA SUBJEFATURA DE DIVISION DE INSCRIPCION DE ASEGURADOS", "NORMATIVO","visorReportes",3),
    TIT_SOPORTE_AFILIACION("TITULAR DE LA DIVISION DE SOPORTE A LOS PROCESOS DE AFILIACION", "NORMATIVO","visorReportes",4),
    JEFE_COORD_AFILIACION("JEFE DE AREA EN LA COORDINACION DE AFILIACION", "NORMATIVO","visorReportes",5),
    ANALISTA_COORD_AFLICIACION("ANALISTA EN LA COORDINACION DE AFILIACION", "NORMATIVO","visorReportes",6),
    JAC("JEFE DE SERVICIOS DE AFILIACION Y COBRANZA","JAC","visorReportes",7),
    SUPERVISOR("JEFE DE DEPARTAMENTO DE SUPERVISION DE AFILIACION VIGENCIA","SUPERVISOR","visorReportes",8),
    SUBDELEGADO("SUBDELEGADO","SUBDELEGADO","visorReportes",9);
    

    private String rol;
    private String descripcion;
    private String defaultUrl;
    private int nivel;
    
    

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    private RolUsuarioEnum() {
    }

    private RolUsuarioEnum(String rol, String descripcion, String defaultUrl, int nivel) {
        this.rol = rol;
        this.descripcion = descripcion;
        this.defaultUrl = defaultUrl;
        this.nivel = nivel;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }
    
     public static RolUsuarioEnum fromRol(String... text) {

         if (text.length > 1) {
             for (String p : text) {
                 for (RolUsuarioEnum b : RolUsuarioEnum.values()) {
                     if (b.getRol().trim().equalsIgnoreCase(p.trim())) {
                         return b;
                     }
                 }
             }
         } else {
             for (RolUsuarioEnum b : RolUsuarioEnum.values()) {
                 if (b.getRol().trim().equalsIgnoreCase(text[0].trim())) {
                     return b;
                 }
             }
         }
         return null;
     }


}
