package mx.gob.imss.ctirss.delta.model.asegurado.cda;


public enum EstadoTramiteReporteCDA {
	
	EN_REGISTRO (1,"EN REGISTRO","INICIADO"),
	ATENDIDA (2,"ATENDIDA","CERRADO"),
	POR_AUTORIZAR (3,"POR AUTORIZAR","EN ESPERA DE AUTORIZACI\u00D3N"),
	ASIGNADA (4,"ASIGNADA","EN ESPERA POR TRAMITADOR"),
	REASIGNADA (-1,"REASIGNADA","EN ESPERA POR TRAMITADOR"),
	INFO_SOLICITADA (5,"INFORMACI\u00D3N ADICIONAL REQUERIDA","EN ESPERA POR DERECHOHABIENTE"),
	ABANDONADA (9,"CANCELADA","BAJA POR IMPROCEDENCIA "),
	CANCELADA (7,"ABANDONADA","CANCELADO"),
	ENVIADA_SINDO (37,"ENVIADA SINDO","ENVIA CERTIFICACI\u00D3N CON SINDO "),
	PROCESO_ATENCION (58,"PROCESO DE ATENCI\u00D3N","EN AN\u00C1LISIS"),
	RECHAZADA (70,"RECHAZADA","RECHAZADO"),
	AUTORIZADA (75,"AUTORIZADA","AN\u00C1LISIS COMPLETADO"),	
	ERROR_SINDO (85,"ERROR SINDO","Error SINDO"),
	VENCIDA (86,"VENCIDA","Vencido"),
	SIN_RESPONSABLE (87,"SIN RESPONSABLE","SIN RESPONSABLE"),
	OPERADA (88,"OPERADA","Procesada SINDO"),
	ATENDIDA_POR_DERECHOHABIENTE (89,"ATENDIDA POR DERECHOHABIENTE","ATENDIDO DERECHOHABIENTE")
	;
	
	private Integer idEstado;
	private String estadoNegocio;
	private String estadoBD;
	
	
	
	private EstadoTramiteReporteCDA(Integer idEstado, String estadoNegocio, String estadoBD) {
		this.idEstado = idEstado;
		this.estadoNegocio = estadoNegocio;
		this.estadoBD = estadoBD;
	}

	
	public static EstadoTramiteReporteCDA parseIdToEstadoNegocioCDA(String id){
		EstadoTramiteReporteCDA result = null;
		Integer idTmp = Integer.parseInt(id);
		for(EstadoTramiteReporteCDA item : EstadoTramiteReporteCDA.values()){
			if(item.getIdEstado().equals(idTmp)){
				result = item;
			}
		}
		return result;
	}
	
	public static EstadoTramiteReporteCDA parseEstadoNegocioCDAToId(String descripcion){
		EstadoTramiteReporteCDA result = null;
		for(EstadoTramiteReporteCDA item : EstadoTramiteReporteCDA.values()){
			if(item.getEstadoNegocio().equals(descripcion)){
				result = item;
			}
		}
		return result;
	}
	
	public static EstadoTramiteReporteCDA parseEstadoDBbToId(String descripcion){
		EstadoTramiteReporteCDA result = null;
		for(EstadoTramiteReporteCDA item : EstadoTramiteReporteCDA.values()){
			if(item.getEstadoBD().equals(descripcion)){
				result = item;
			}
		}
		return result;
	}

	
	public Integer getIdEstado() {
		return idEstado;
	}


	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}


	public String getEstadoBD() {
		return estadoBD;
	}


	public void setEstadoBD(String estadoBD) {
		this.estadoBD = estadoBD;
	}


	public String getEstadoNegocio() {
		return estadoNegocio;
	}


	public void setEstadoNegocio(String estadoNegocio) {
		this.estadoNegocio = estadoNegocio;
	}
	
}
