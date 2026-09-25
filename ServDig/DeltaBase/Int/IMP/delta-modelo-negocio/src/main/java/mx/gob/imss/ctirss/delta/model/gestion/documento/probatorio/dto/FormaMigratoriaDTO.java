package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto;



public class FormaMigratoriaDTO  {

	

	private String numeroDoc;
	private String paisOrigen;
	private String fechaExpedicion;
	private String fechaVencimiento;
	private String calidadMigratoria;
	private String nombrePaisOrigen;
	private String nombreCalidadMigratoria;

	public String getNumeroDoc() {
		return numeroDoc;
	}

	public void setNumeroDoc(String numeroDoc) {
		this.numeroDoc = numeroDoc;
	}

	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	
	public String getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(String fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}
	
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public String getCalidadMigratoria() {
		return calidadMigratoria;
	}
	
	public void setCalidadMigratoria(String calidadMigratoria) {
		this.calidadMigratoria = calidadMigratoria;
	}

	public String getNombrePaisOrigen() {
		return nombrePaisOrigen;
	}

	public void setNombrePaisOrigen(String nombrePaisOrigen) {
		this.nombrePaisOrigen = nombrePaisOrigen;
	}

	public String getNombreCalidadMigratoria() {
		return nombreCalidadMigratoria;
	}

	public void setNombreCalidadMigratoria(String nombreCalidadMigratoria) {
		this.nombreCalidadMigratoria = nombreCalidadMigratoria;
	}
	
	
}

