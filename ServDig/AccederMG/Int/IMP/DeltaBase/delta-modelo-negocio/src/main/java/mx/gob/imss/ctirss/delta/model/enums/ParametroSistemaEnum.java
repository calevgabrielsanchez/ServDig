package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum ParametroSistemaEnum implements Serializable {

	FILE_SYSTEM_SIE("FS_SIE"), 
	FTP_SIE_SERVER("FTP_SIE_SERVER"), 
	FTP_SIE_PORT("FTP_SIE_PORT"), 
	FTP_SIE_USER("FTP_SIE_USER"), 
	FTP_SIE_PWD("FTP_SIE_PWD"), 
	PATRON_RISS_NSS("PATRON_RISS_NSS"), 
	CORREOS_RISS_TO("CORREOS_RISS_TO"), 
	CORREOS_RISS_CC("CORREOS_RISS_CC"), 
	CORREOS_RISS_BCC("CORREOS_RISS_BCC"),
	ID_SITE_IDSE("ID_SITE_IDSE"),
	HABILITA_RISS_PORTAL("HABILITA_RISS_PORTAL"),
	FECHA_INICIO_RIF_IMSS("FECHA_INICIO_RIF_IMSS"),
	CORREOS_SEGUROS_MONITOR_BCC("CORREOS_SEGUROS_MONITOR_BCC"),
	FECHA_LIBERA_SEGS_CVRO_RENOVA("FECHA_LIBERA_SEGS_CVRO_RENOVA")
	;
	
	private String codigo;

	private ParametroSistemaEnum(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return this.codigo;
	}
}
