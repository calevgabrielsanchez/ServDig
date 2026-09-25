package mx.gob.imss.ctirss.delta.model.gestion.integracion.patronal;


public class RegistroIDSE implements java.io.Serializable {

	private static final long serialVersionUID = -7523826801945527373L;
	
	private PersonaIDSE patron;
	private String razonSocial;
	private String nrp;	
	private int estatus;
	private int idTipoTramite;
	private int idOrigenSolicitud;
	
	//Representante Legal
	private PersonaIDSE[] representantes;
		
	//Atributos Centro de trabajo
	private String domicilioCentroTrabajo;
	private String telefono;
	private String cveSubdelegacion;
	private String cveDelegacion;
	private String cveMunicipio;
	private String cveSector;
	private String localidad;
	private String actividad;
	private String fraccion;
	private Integer clase;
	private String correo;	
	

	public PersonaIDSE getPatron() {
		return patron;
	}
	public void setPatron(PersonaIDSE patron) {
		this.patron = patron;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNrp() {
		return nrp;
	}
	public void setNrp(String nrp) {
		this.nrp = nrp;
	}
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	public int getIdTipoTramite() {
		return idTipoTramite;
	}
	public void setIdTipoTramite(int idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
	}		
	public int getIdOrigenSolicitud() {
		return idOrigenSolicitud;
	}
	public void setIdOrigenSolicitud(int idOrigenSolicitud) {
		this.idOrigenSolicitud = idOrigenSolicitud;
	}
	public PersonaIDSE[] getRepresentantes() {
		return representantes;
	}
	public void setRepresentantes(PersonaIDSE[] representantes) {
		this.representantes = representantes != null ? representantes.clone() : null;
	}
	public String getDomicilioCentroTrabajo() {
		return domicilioCentroTrabajo;
	}
	public void setDomicilioCentroTrabajo(String domicilioCentroTrabajo) {
		this.domicilioCentroTrabajo = domicilioCentroTrabajo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCveSubdelegacion() {
		return cveSubdelegacion;
	}
	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}
	public String getCveDelegacion() {
		return cveDelegacion;
	}
	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public String getCveMunicipio() {
		return cveMunicipio;
	}
	public void setCveMunicipio(String cveMunicipio) {
		this.cveMunicipio = cveMunicipio;
	}
	public String getCveSector() {
		return cveSector;
	}
	public void setCveSector(String cveSector) {
		this.cveSector = cveSector;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getFraccion() {
		return fraccion;
	}
	public void setFraccion(String fraccion) {
		this.fraccion = fraccion;
	}
	public Integer getClase() {
		return clase;
	}
	public void setClase(Integer clase) {
		this.clase = clase;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
}