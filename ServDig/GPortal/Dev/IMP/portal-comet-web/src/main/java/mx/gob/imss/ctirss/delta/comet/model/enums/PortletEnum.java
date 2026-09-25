package mx.gob.imss.ctirss.delta.comet.model.enums;

public enum PortletEnum {
	
	ID_PORTLET_REPRESENTADOS("representadosLegalesPortlet"),
	ID_PORTLET_REPRESENTANTES_LEGALES("representantesLegalesPortlet"),
	ID_PORTLET_PATRONES_ASOCIADOS("patronesAsociadosPersona"),
	ID_PORTLET_MODIFICACION_CLASIFICACION("patronesClasificacion"),
	ID_PORTLET_SOLICITUDES("solicitudesPersona"),
	ID_PORTLET_SOLICITUDES_PATRON("solicitudesPatron"),
	ID_PORTLET_PERSONA_AUTORIZADA("personasAutorizadas"),
	ID_PORTLET_IVRO("portletSeguroDomestico"),
	ID_DETALLE_IDENTIDAD("detalleIdentidad"),
	ID_DETALLE_SUJETO("detalleSujeto");

	private String codigo;

	private PortletEnum(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
