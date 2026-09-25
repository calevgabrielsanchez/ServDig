package mx.gob.imss.ctirss.delta.comet.model.enums;

public enum CometChannelEnum {
	
	PUBLICAR_MODIF_PERSONA("/comet/delta/persona/modificacion/"),
	AFECTAR_PORTLETS("/comet/delta/portlets/modificacion/"),
	PUBLICAR_MODIF_SOLICITUD("/comet/delta/solicitud/modificacion/"),
	USER_SESSION("/comet/delta/channels/server/session/");

	private String codigo;

	private CometChannelEnum(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
