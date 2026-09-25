package mx.gob.imss.cit.cda.web.app.common.model.enums;

/**
 * Enumerador que representa los tipos de solicitantes para registro en
 * ventanilla
 *
 */
public enum TipoSolicitanteEnum {
    
   

    ASEGURADO_PENSIONADO(0, "ASEGURADO"), BENEFICIARIO(1, "BENEFICIARIO"), CONYUGE(
            2, "CONYUGE"), DESCENDIENTE(3, "DESCENDIENTE"), PADRES(4, "PADRES"), CONCUBINO(
            5, "CONCUBINO"), REPRESENTANTE_LEGAL(6, "REPRESENTANTE_LEGAL");
    /**
     * Constructor del enumerador
     * 
     * @param clave
     */
    private TipoSolicitanteEnum(final int id, final String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    private int id;

    /**
     * Desripción de solicitante
     */
    private String descripcion;

    /**
     * 
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    public int getId() {
        return id;
    }
    
    public static TipoSolicitanteEnum getTipoSolicitudEnumByDescripcion(String descripcion){
        for(TipoSolicitanteEnum enum1:TipoSolicitanteEnum.values()){
            if(enum1.getDescripcion().equals(descripcion)){
                return enum1;
            }
        }
        return null;
        
    }
    
    public static TipoSolicitanteEnum getTipoSolicitudEnumById(int id){
        for(TipoSolicitanteEnum enum1:TipoSolicitanteEnum.values()){
            if(enum1.getId()==id){
                return enum1;
            }
        }
        return null;
    }

}
