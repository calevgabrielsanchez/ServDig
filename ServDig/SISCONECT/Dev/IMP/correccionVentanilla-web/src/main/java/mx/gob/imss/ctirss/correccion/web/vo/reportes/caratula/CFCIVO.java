package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


public class CFCIVO {

	private String folioCorreccion;
	private String registroPatronal;
	private String nombreAuditor;
	private String oficioInvitacion;
	private String folioPromocion;	
	private String observaciones;
	
	
	public CFCIVO(){}
	
	public CFCIVO(Object[] obj){

		int i = 0;
				
		setFolioCorreccion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setRegistroPatronal(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setNombreAuditor(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setOficioInvitacion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");		
		setFolioPromocion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setObservaciones(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");

	}


	public String getFolioCorreccion() {
		return folioCorreccion;
	}


	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}


	public String getRegistroPatronal() {
		return registroPatronal;
	}


	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	

	public String getFolioPromocion() {
		return folioPromocion;
	}

	public void setFolioPromocion(String folioPromocion) {
		this.folioPromocion = folioPromocion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getNombreAuditor() {
		return nombreAuditor;
	}

	public void setNombreAuditor(String nombreAuditor) {
		this.nombreAuditor = nombreAuditor;
	}

	public String getOficioInvitacion() {
		return oficioInvitacion;
	}

	public void setOficioInvitacion(String oficioInvitacion) {
		this.oficioInvitacion = oficioInvitacion;
	}


	
}
