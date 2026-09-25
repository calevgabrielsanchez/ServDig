package mx.gob.imss.ctirss.idse.model;


public class RegistroPatronal implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
		
	private Persona patron;	
	private String razonSocial;
	private String nrp;
	private int  estatus;
	
	//Representante Legal
	private Persona[] representantes;
	
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
	
	private int idOrigenSolicitud;
	
	
	//Atributos de apoyo para procesar movimientos.
	private Long cveRegistroPatronal;
	private Persona RepresentanteLegal;
	
	
	public RegistroPatronal(){
		super();
	}
	
	public RegistroPatronal(String rfc, String nombreRazonSocial, String nombreUsuario, String curp, String domicilioFiscal, String correoElectronico,
			String claveSerial, int tipoPersona, String nrp, String domicilioCentroTrabajo, String telefonoCT, String cveSubdelegacion, String cveDelegacion,
			String cveMunicipio, String localidad, String actividad, String fraccion, int clase, String correoCT, int idOrigenSolicitud){
		super();
		Persona patronHelper = new Persona();
		patronHelper.setRfc(rfc);
		patronHelper.setNombreRazonSocial(nombreRazonSocial);
		patronHelper.setNombreUsuario(nombreUsuario);
		patronHelper.setCurp(curp);
		patronHelper.setDomicilioFiscal(domicilioFiscal);
		patronHelper.setCorreoElectronico(correoElectronico);
		patronHelper.setCertificado(new Certificado());
		patronHelper.getCertificado().setClaveSerial(claveSerial);
		patronHelper.setTipoPersona(tipoPersona);
		this.patron = patronHelper;
		
		this.razonSocial = nombreRazonSocial;
		this.nrp = nrp;
		this.domicilioCentroTrabajo = domicilioCentroTrabajo;
		this.telefono = telefonoCT;
		this.cveSubdelegacion = cveSubdelegacion;
		this.cveDelegacion = cveDelegacion;
		this.cveMunicipio = cveMunicipio;
		this.cveSector = "90";
		this.localidad = localidad;
		this.actividad = actividad;
		this.fraccion = fraccion;
		this.clase = clase;
		this.correo = correoCT;
		
		this.idOrigenSolicitud = idOrigenSolicitud;
	}

	public Persona getPatron() {
		return patron;
	}
	public void setPatron(Persona patron) {
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
	
	public Persona[] getRepresentantes() {
		return representantes;
	}
	public void setRepresentantes(Persona[] representantes) {
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

	
	public Long getCveRegistroPatronal() {
		return cveRegistroPatronal;
	}
	public void setCveRegistroPatronal(Long cveRegistroPatronal) {
		this.cveRegistroPatronal = cveRegistroPatronal;
	}
	public Persona getRepresentanteLegal() {
		return RepresentanteLegal;
	}
	public void setRepresentanteLegal(Persona representanteLegal) {
		RepresentanteLegal = representanteLegal;
	}
	
	public int getIdOrigenSolicitud() {
		return idOrigenSolicitud;
	}
	public void setIdOrigenSolicitud(int idOrigenSolicitud) {
		this.idOrigenSolicitud = idOrigenSolicitud;
	}
}