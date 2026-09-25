package mx.gob.imss.ctirss.delta.model.enums;


public enum MensajesBovedaCDAEnum {
	
	MSJ_EX001("EX-001","Error al adjuntar Documento"),
	MSJ_EX002("EX-002","Error al descargar Documento"),
	MSJ_EX003("EX-003","Error al eliminar Documento"),
	MSJ_BP5001("BP-5001", "Operaci\u00f3n realizada con \u00e9xito"),
	MSJ_BP5002("BP-5002", "Ha ocurrido un error al contactar el repositorio documental"),
	MSJ_BP5003("BP-5003", "Ha ocurrido un error al guardar el documento en el repositorio"),
	MSJ_BP5004("BP-5004", "El Identificador del documento no existe en el repositorio"),
	MSJ_BP5005("BP-5005", "Ya existe un documento con el mismo nombre"),
	MSJ_BP5006("BP-5006", "Ha ocurrido un error al eliminar el documento en el repositorio"),
	MSJ_BP5007("BP-5007", "Ha ocurrido un error al actualizar el documento en el repositorio"),
	MSJ_BP5008("BP-5008", "Ha ocurrido un error al consultar el documento en el repositorio"),
	MSJ_BP5009("BP-5009", "No existen documentos coincidentes en el repositorio con los valores de b\u00fasqueda"),
	MSJ_BP5010("BP-5010", "El folio del tr\u00e1mite es obligatorio"),
	MSJ_BP5011("BP-5011", "La ruta no existe, favor de validar la informaci\u00f3n"),
	MSJ_BP5012("BP-5012", "El documento no puede estar vac\u00edo, favor de validar la informaci\u00f3n");

	private String codigo;
	private String descripcion;

	private MensajesBovedaCDAEnum(String codigo, String descripcion) {
		
	    this.setCodigo(codigo);
		this.setDescripcion(descripcion);
		
	}

    public String getCodigo() {
        return codigo;
    }


    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    public String getDescripcion () {
        return descripcion ;
    }

    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public static String obtenerDescripcionPorCodigo (String codigo){
        String descripcion = "";
        for(MensajesBovedaCDAEnum mensajesBovedaCDAEnum:MensajesBovedaCDAEnum.values()){
            if(mensajesBovedaCDAEnum.getCodigo().equalsIgnoreCase(codigo)){
                descripcion =  mensajesBovedaCDAEnum.getDescripcion();
            }
        }
        return descripcion;
    }
    
    public static String obtenerMensajeErrorPorCodigo (String codigo){
        String descripcion = "";
        for(MensajesBovedaCDAEnum mensajesBovedaCDAEnum:MensajesBovedaCDAEnum.values()){
            if(mensajesBovedaCDAEnum.getCodigo().equalsIgnoreCase(codigo)){
            	StringBuilder stb = new StringBuilder();
            	stb.append(codigo).append(" - ").append(mensajesBovedaCDAEnum.getDescripcion());
            	descripcion= stb.toString();
            }
        }
        return descripcion;
    }

	
}
