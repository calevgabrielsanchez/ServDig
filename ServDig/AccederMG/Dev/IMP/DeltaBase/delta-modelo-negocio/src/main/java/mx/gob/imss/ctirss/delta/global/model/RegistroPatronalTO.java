package mx.gob.imss.ctirss.delta.global.model;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;

public class RegistroPatronalTO extends AbstractModel{
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -4785862048868463637L;
	
	private Long idRegistroPatronal;
	private PatronTO patron;
	private CentroTrabajoTO centroTrabajo;
	private String nombreComercial;
	private String cveMunicipioImss;
	private Integer sector;
	private String domicilioCompleto;
	private String localidadSINDO;
	/**
	 * Conformacion del registro patronal
	 */
	private String numeroRegistro;
	protected Modalidad modalidad;
	private String digVerificador;

	private ClasificacionTO clasificacion = new ClasificacionTO();
	
	/**
	 * Datos de la actividad economica
	 */
	private ProcesoTO proceso = new ProcesoTO();
	private List<BienTO> bienes = new ArrayList<BienTO>();
	private List<PersonalTO> personal = new ArrayList<PersonalTO>();
	private List<ProductoTO> productos = new ArrayList<ProductoTO>();
	private List<MaquinariaEquipoTO> equipos = new ArrayList<MaquinariaEquipoTO>();
	private List<EquipoTransporteTO> equiposTransporte = new ArrayList<EquipoTransporteTO>();
	private List<MateriaPrimaTO> materiaPrimaMateriales = new ArrayList<MateriaPrimaTO>();
	private Subdelegacion subdelegacion;	
	private Integer cuentaConTransporte = new Integer(0);
	private String desAfectacion;
	private String desUsosBienes;
	
	
	public Long getIdRegistroPatronal() {
		return idRegistroPatronal;
	}
	public void setIdRegistroPatronal(Long idRegistroPatronal) {
		this.idRegistroPatronal = idRegistroPatronal;
	}
	public CentroTrabajoTO getCentroTrabajo() {
		return centroTrabajo;
	}
	public void setCentroTrabajo(CentroTrabajoTO centroTrabajo) {
		this.centroTrabajo = centroTrabajo;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	public Modalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}
	public String getDigVerificador() {
		return digVerificador;
	}
	public void setDigVerificador(String digVerificador) {
		this.digVerificador = digVerificador;
	}
	public ClasificacionTO getClasificacion() {
		return clasificacion;
	}
	public void setClasificacion(ClasificacionTO clasificacion) {
		this.clasificacion = clasificacion;
	}
	public ProcesoTO getProceso() {
		return proceso;
	}
	public void setProceso(ProcesoTO proceso) {
		this.proceso = proceso;
	}
	public List<BienTO> getBienes() {
		return bienes;
	}
	public void setBienes(List<BienTO> bienes) {
		this.bienes = bienes;
	}
	public List<PersonalTO> getPersonal() {
		return personal;
	}
	public void setPersonal(List<PersonalTO> personal) {
		this.personal = personal;
	}
	public List<ProductoTO> getProductos() {
		return productos;
	}
	public void setProductos(List<ProductoTO> productos) {
		this.productos = productos;
	}
	public List<MaquinariaEquipoTO> getEquipos() {
		return equipos;
	}
	public void setEquipos(List<MaquinariaEquipoTO> equipos) {
		this.equipos = equipos;
	}
	public List<EquipoTransporteTO> getEquiposTransporte() {
		return equiposTransporte;
	}
	public void setEquiposTransporte(List<EquipoTransporteTO> equiposTransporte) {
		this.equiposTransporte = equiposTransporte;
	}
	public List<MateriaPrimaTO> getMateriaPrimaMateriales() {
		return materiaPrimaMateriales;
	}
	public void setMateriaPrimaMateriales(
			List<MateriaPrimaTO> materiaPrimaMateriales) {
		this.materiaPrimaMateriales = materiaPrimaMateriales;
	}
	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}
	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	public Integer getCuentaConTransporte() {
		return cuentaConTransporte;
	}
	public void setCuentaConTransporte(Integer cuentaConTransporte) {
		this.cuentaConTransporte = cuentaConTransporte;
	}
	public String getDesAfectacion() {
		return desAfectacion;
	}
	public void setDesAfectacion(String desAfectacion) {
		this.desAfectacion = desAfectacion;
	}
	public String getDesUsosBienes() {
		return desUsosBienes;
	}
	public void setDesUsosBienes(String desUsosBienes) {
		this.desUsosBienes = desUsosBienes;
	}
	public PatronTO getPatron() {
		return patron;
	}
	public void setPatron(PatronTO patron) {
		this.patron = patron;
	}
	public String getCveMunicipioImss() {
		return cveMunicipioImss;
	}
	public void setCveMunicipioImss(String cveMunicipioImss) {
		this.cveMunicipioImss = cveMunicipioImss;
	}
	public Integer getSector() {
		return sector;
	}
	public void setSector(Integer sector) {
		this.sector = sector;
	}
	public String getDomicilioCompleto() {
		return domicilioCompleto;
	}
	public void setDomicilioCompleto(String domicilioCompleto) {
		this.domicilioCompleto = domicilioCompleto;
	}
	public String getLocalidadSINDO() {
		return localidadSINDO;
	}
	public void setLocalidadSINDO(String localidadSINDO) {
		this.localidadSINDO = localidadSINDO;
	}
	
}
