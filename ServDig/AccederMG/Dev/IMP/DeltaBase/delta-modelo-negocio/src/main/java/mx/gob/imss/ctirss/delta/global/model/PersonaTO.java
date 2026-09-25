package mx.gob.imss.ctirss.delta.global.model;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;

public class PersonaTO extends AbstractModel{
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 58994513159578752L;
	
	/**
	 * Datos genreales
	 */
	private Long idPersona;
	private TipoPersona tipoPersona;
	private String rfc;
	
	/**
	 * Datos de la persona fisica
	 */
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private Date fechaNacimiento;
	private EntidadFederativa lugarNacimiento;
	private Date fechaDefuncion;
	private EstadoCivil estadoCivil;
	private String curp;
    private Pais pais;
    private Sexo sexo;
	private String nss;
    
	/**
     * Datos de la persona Moral
     */
    private TipoSociedad tipoSociedad;
    private String razonSocial;
    private EscrituraConstitutiva escrituraConstitutiva;
	private RegistroSindicato registroSindicato;
	private String correoDeNotificaciones;
	private String domicilioFiscalCompleto;
	private String localidad;
	private String entidadFederativa;
	private String municipio;

	

	private PersonaCalificacionTO calificaciones;
	private MensajeProcesoTO mensajes;
	
	
	
	
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}

	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(TipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public EntidadFederativa getLugarNacimiento() {
		return lugarNacimiento;
	}
	public void setLugarNacimiento(EntidadFederativa lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}
	public Date getFechaDefuncion() {
		return fechaDefuncion;
	}
	public void setFechaDefuncion(Date fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public TipoSociedad getTipoSociedad() {
		return tipoSociedad;
	}
	public void setTipoSociedad(TipoSociedad tipoSociedad) {
		this.tipoSociedad = tipoSociedad;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public EscrituraConstitutiva getEscrituraConstitutiva() {
		return escrituraConstitutiva;
	}
	public void setEscrituraConstitutiva(EscrituraConstitutiva escrituraConstitutiva) {
		this.escrituraConstitutiva = escrituraConstitutiva;
	}
	public RegistroSindicato getRegistroSindicato() {
		return registroSindicato;
	}
	public void setRegistroSindicato(RegistroSindicato registroSindicato) {
		this.registroSindicato = registroSindicato;
	}
	public String getCorreoDeNotificaciones() {
		return correoDeNotificaciones;
	}
	public void setCorreoDeNotificaciones(String correoDeNotificaciones) {
		this.correoDeNotificaciones = correoDeNotificaciones;
	}
	public String getDomicilioFiscalCompleto() {
		return domicilioFiscalCompleto;
	}
	public void setDomicilioFiscalCompleto(String domicilioFiscalCompleto) {
		this.domicilioFiscalCompleto = domicilioFiscalCompleto;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public PersonaCalificacionTO getCalificaciones() {
		return calificaciones;
	}
	public void setCalificaciones(PersonaCalificacionTO calificaciones) {
		this.calificaciones = calificaciones;
	}
	public MensajeProcesoTO getMensajes() {
		return mensajes;
	}
	public void setMensajes(MensajeProcesoTO mensajes) {
		this.mensajes = mensajes;
	}
	
}
