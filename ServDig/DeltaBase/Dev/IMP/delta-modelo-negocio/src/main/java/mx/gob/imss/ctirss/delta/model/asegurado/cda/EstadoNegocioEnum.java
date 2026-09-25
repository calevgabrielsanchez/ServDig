package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;

public enum EstadoNegocioEnum {
	
	REASIGNADA (-1,"REASIGNADA",EstadoTramiteEnum.EN_ESPERA_TRAMITADOR),
	EN_REGISTRO (1,"EN REGISTRO",EstadoTramiteEnum.INICIADO),
	ATENDIDA (2,"ATENDIDA",EstadoTramiteEnum.CERRADO),
	POR_AUTORIZAR (3,"POR AUTORIZAR",EstadoTramiteEnum.EN_ESPERA_AUTORIZACION),
	ASIGNADA (4,"ASIGNADA",EstadoTramiteEnum.EN_ESPERA_TRAMITADOR),
	INFO_SOLICITADA (5,"INFORMACI\u00D3N SOLICITADA",EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE),
	ABANDONADA (9,"CANCELADA",EstadoTramiteEnum.BAJA_IMPROCEDENCIA),
	CANCELADA (7,"ABANDONADA",EstadoTramiteEnum.CANCELADO),
	ENVIADA_SINDO (37,"ENVIADA SINDO",EstadoTramiteEnum.ENVIA_CERTIFICACION_SINDO),
	PROCESO_ATENCION (58,"PROCESO DE ATENCI\u00D3N",EstadoTramiteEnum.EN_ANALISIS),
	RECHAZADA (70,"RECHAZADA",EstadoTramiteEnum.RECHAZADO),
	AUTORIZADA (75,"AUTORIZADA",EstadoTramiteEnum.ANALISIS_COMPLETADO),	
	ERROR_SINDO (85,"ERROR SINDO",EstadoTramiteEnum.ERROR_SINDO),
	VENCIDA (86,"VENCIDA",EstadoTramiteEnum.VENCIDA),
	SIN_RESPONSABLE(87,"SIN RESPONSABLE",EstadoTramiteEnum.SIN_RESPONSABLE),
	PROCESADO_SINDO(88,"OPERADA", EstadoTramiteEnum.PROCESADO_SINDO),
	INFO_SOLICITADA_ATENDIDA (89,"INFORMACI\u00D3N ADICIONAL REQUERIDA",EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE_ATENDIDA);
	
	
	private Integer codigo;
    private String descripcion;
    private EstadoTramiteEnum estadoTramiteEnum;

    private EstadoNegocioEnum(Integer codigo, String descripcion, EstadoTramiteEnum estadoTramiteEnum) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }   
    
    public EstadoTramiteEnum getEstadoTramiteEnum() {
		return estadoTramiteEnum;
	}

	public void setEstadoTramiteEnum(EstadoTramiteEnum estadoTramiteEnum) {
		this.estadoTramiteEnum = estadoTramiteEnum;
	}

	public static String obtenerDescripcionNegocio(Integer codigoEstadoTramite){
    	String descripcionNegocio = null;
    	for (EstadoNegocioEnum enum1: EstadoNegocioEnum.values()){
    		if(enum1.getCodigo().equals(codigoEstadoTramite)){
    			return descripcionNegocio= enum1.getDescripcion();
    		}
    	}    	
    	return descripcionNegocio;
    }

}
