package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class DatosPersona implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2110197580596670703L;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private Date fechaNacimiento;
	private Date fechaDefuncion;
	private String curp;
    private String nss;
    private String rfc;

    
    private Long cveIdPersona;
    private List<DatosGrupoFamiliar> datosGrupoFamiliarBeneficiario;
    private DatosPersonaRenapo datosPersonaRenapo;
    private DatosAsegurado datosAsegurado;
    private DatosPersonaFisica datosPersonaFisica;
    private List<ResolucionPension> datosPensionado;
	private EntidadFederativa lugarNacimiento;
	private EstadoCivil estadoCivil;
    private Pais pais;
    private Sexo sexo;


    
    
    
    public List<ResolucionPension> getDatosPensionado() {
		return datosPensionado;
	}

	public void setDatosPensionado(List<ResolucionPension> datosPensionado) {
		this.datosPensionado = datosPensionado;
	}

	public DatosAsegurado getDatosAsegurado() {
		return datosAsegurado;
	}

	public void setDatosAsegurado(DatosAsegurado datosAsegurado) {
		this.datosAsegurado = datosAsegurado;
	}

	public DatosPersonaFisica getDatosPersonaFisica() {
		return datosPersonaFisica;
	}

	public void setDatosPersonaFisica(DatosPersonaFisica datosPersonaFisica) {
		this.datosPersonaFisica = datosPersonaFisica;
	}

	public DatosPersonaRenapo getDatosPersonaRenapo() {
		return datosPersonaRenapo;
	}

	public void setDatosPersonaRenapo(DatosPersonaRenapo datosPersonaRenapo) {
		this.datosPersonaRenapo = datosPersonaRenapo;
	}

	public List<DatosGrupoFamiliar> getDatosGrupoFamiliarBeneficiario() {
		return datosGrupoFamiliarBeneficiario;
	}

	public void setDatosGrupoFamiliarBeneficiario(List<DatosGrupoFamiliar> datosGrupoFamiliarBeneficiario) {
		this.datosGrupoFamiliarBeneficiario = datosGrupoFamiliarBeneficiario;
	}

	public Long getCveIdPersona() {
		return cveIdPersona;
	}

	public void setCveIdPersona(Long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
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
    


}
