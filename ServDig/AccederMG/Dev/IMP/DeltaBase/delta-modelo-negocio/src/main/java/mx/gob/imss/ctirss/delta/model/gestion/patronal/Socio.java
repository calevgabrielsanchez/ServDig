/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Proyecto: delta
 *  @Archivo:Socio.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.gestion.patronal
 *  @Fecha:18/04/2012
 */
package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;

public class Socio extends ItemClasificacion {

	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = -7425903853157492605L;
	// CAMPOS COMUNES PARA SOCIOS FISICOS Y MORALES

	private Long idPersona;
	private Long idSocio;
	private String registroPatronal;
	private Long cveIdPatronSujetoObligado;
	private String rfc;
	private String tipoPersonaFiscalPatron;
	private Long idPersonaMoralPatron;
	private String telefonoFijo;
	private String extension;
	private String telefonoMovil;
	private String email;
	private String emailAlterno;
	private Boolean esNacional;
	private Boolean esDomicilioNacional;
	private Boolean esPersonaFisica;

	// SOLO SOCIOS FISICOS

	private String curp;
	private String primerApellido;
	private String segundoApellido;
	private String nombres;
	
	private Fisica personaFisica;

	// SOLO SOCIOS MORALES

	private String denominacionRazonSocial;
	private String tipoSociedad;
	
	private Moral personaMoral;


	// SOLO SOCIO EXTRANJERO
	private String localidadColonia;
	private String paisYCiudad;
	private String estadoProvincia;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Persona persona = new Persona();
	private String nombreRazonSocial;
	private TipoAccionAfectacionEnum accion;
	private DomicilioFiscal domicilioFiscal;
	private EscrituraConstitutiva escrituraConstitutiva;
	
	// Seccion Socio Fideicomiso
	private int numeroInstrumetoProtocolizacion;
	private String notariaCorreduria;
	private EntidadFederativa estado;
	private Date fechaExpedicionContrato;
	
	// estructura final
	// fideicomiso, extiende de persona moral y con lo que tenga el caso de uso
	// extrangero, con loqu tenga el C.U
	
	private List<MedioContacto> mediosContacto = new ArrayList<MedioContacto>();
	
	private TipoPersona tipoSocio;
	private String domicilioReporte;
	
	private TramiteFisica tramiteFisica;
	
	private String rfcPersonaMoralPatron;
	
	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc
	 *            the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	
	
	/**
	 * @return the telefonoFijo
	 */
	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	/**
	 * @param telefonoFijo
	 *            the telefonoFijo to set
	 */
	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return the telefonoMovil
	 */
	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	/**
	 * @param telefonoMovil
	 *            the telefonoMovil to set
	 */
	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the emailAlterno
	 */
	public String getEmailAlterno() {
		return emailAlterno;
	}

	/**
	 * @param emailAlterno
	 *            the emailAlterno to set
	 */
	public void setEmailAlterno(String emailAlterno) {
		this.emailAlterno = emailAlterno;
	}

	/**
	 * @return the localidadColonia
	 */
	public String getLocalidadColonia() {
		return localidadColonia;
	}

	/**
	 * @param localidadColonia
	 *            the localidadColonia to set
	 */
	public void setLocalidadColonia(String localidadColonia) {
		this.localidadColonia = localidadColonia;
	}

	/**
	 * @return the paisYCiudad
	 */
	public String getPaisYCiudad() {
		return paisYCiudad;
	}

	/**
	 * @param paisYCiudad
	 *            the paisYCiudad to set
	 */
	public void setPaisYCiudad(String paisYCiudad) {
		this.paisYCiudad = paisYCiudad;
	}

	/**
	 * @return the estadoProvincia
	 */
	public String getEstadoProvincia() {
		return estadoProvincia;
	}

	/**
	 * @param estadoProvincia
	 *            the estadoProvincia to set
	 */
	public void setEstadoProvincia(String estadoProvincia) {
		this.estadoProvincia = estadoProvincia;
	}

	/**
	 * @return the idSocio
	 */
	public Long getIdSocio() {
		return idSocio;
	}

	/**
	 * @param idSocio
	 *            the idSocio to set
	 */
	public void setIdSocio(Long idSocio) {
		this.idSocio = idSocio;
	}

	/**
	 * @return the cveIdPatronSujetoObligado
	 */
	public Long getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}

	/**
	 * @param cveIdPatronSujetoObligado
	 *            the cveIdPatronSujetoObligado to set
	 */
	public void setCveIdPatronSujetoObligado(
			Long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

	/**
	 * @return the esNacional
	 */
	public Boolean getEsNacional() {
		return esNacional;
	}

	/**
	 * @param esNacional
	 *            the esNacional to set
	 */
	public void setEsNacional(Boolean esNacional) {
		this.esNacional = esNacional;
	}

	/**
	 * @return the esPersonaFisica
	 */
	public Boolean getEsPersonaFisica() {
		return esPersonaFisica;
	}

	/**
	 * @param esPersonaFisica
	 *            the esPersonaFisica to set
	 */
	public void setEsPersonaFisica(Boolean esPersonaFisica) {
		this.esPersonaFisica = esPersonaFisica;
	}

	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp
	 *            the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * @param primerApellido
	 *            the primerApellido to set
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * @return the segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * @param segundoApellido
	 *            the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * @param nombres
	 *            the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * @return the denominacionRazonSocial
	 */
	public String getDenominacionRazonSocial() {
		return denominacionRazonSocial;
	}

	/**
	 * @param denominacionRazonSocial
	 *            the denominacionRazonSocial to set
	 */
	public void setDenominacionRazonSocial(String denominacionRazonSocial) {
		this.denominacionRazonSocial = denominacionRazonSocial;
	}

	/**
	 * @return the tipoSociedad
	 */
	public String getTipoSociedad() {
		return tipoSociedad;
	}

	/**
	 * @param tipoSociedad
	 *            the tipoSociedad to set
	 */
	public void setTipoSociedad(String tipoSociedad) {
		this.tipoSociedad = tipoSociedad;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the registroPatronal
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	/**
	 * @param registroPatronal the registroPatronal to set
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Boolean getEsDomicilioNacional() {
		return esDomicilioNacional;
	}

	public void setEsDomicilioNacional(Boolean esDomicilioNacional) {
		this.esDomicilioNacional = esDomicilioNacional;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	
	public List<MedioContacto> getMediosContacto() {
		return mediosContacto;
	}

	public void setMediosContacto(List<MedioContacto> mediosContacto) {
		this.mediosContacto = mediosContacto;
	}
	
	public TipoPersona getTipoSocio() {
		return tipoSocio;
	}

	public void setTipoSocio(TipoPersona tipoSocio) {
		this.tipoSocio = tipoSocio;
	}

	public TipoAccionAfectacionEnum getAccion() {
		return accion;
	}

	public void setAccion(TipoAccionAfectacionEnum accion) {
		this.accion = accion;
	}
	
	/**
	 * @return the domicilioFiscal
	 */
	public DomicilioFiscal getDomicilioFiscal() {
		return domicilioFiscal;
	}

	/**
	 * @param domicilioFiscal the domicilioFiscal to set
	 */
	public void setDomicilioFiscal(DomicilioFiscal domicilioFiscal) {
		this.domicilioFiscal = domicilioFiscal;
	}

	public EscrituraConstitutiva getEscrituraConstitutiva() {
		return escrituraConstitutiva;
	}

	public void setEscrituraConstitutiva(EscrituraConstitutiva escrituraConstitutiva) {
		this.escrituraConstitutiva = escrituraConstitutiva;
	}

	public String getNotariaCorreduria() {
		return notariaCorreduria;
	}

	public void setNotariaCorreduria(String notariaCorreduria) {
		this.notariaCorreduria = notariaCorreduria;
	}

	public EntidadFederativa getEstado() {
		return estado;
	}

	public void setEstado(EntidadFederativa estado) {
		this.estado = estado;
	}

	public Date getFechaExpedicionContrato() {
		return fechaExpedicionContrato;
	}

	public void setFechaExpedicionContrato(Date fechaExpedicionContrato) {
		this.fechaExpedicionContrato = fechaExpedicionContrato;
	}

	public int getNumeroInstrumetoProtocolizacion() {
		return numeroInstrumetoProtocolizacion;
	}

	public void setNumeroInstrumetoProtocolizacion(
			int numeroInstrumetoProtocolizacion) {
		this.numeroInstrumetoProtocolizacion = numeroInstrumetoProtocolizacion;
	}

	public String getTipoPersonaFiscalPatron() {
		return tipoPersonaFiscalPatron;
	}

	public void setTipoPersonaFiscalPatron(String tipoPersonaFiscalPatron) {
		this.tipoPersonaFiscalPatron = tipoPersonaFiscalPatron;
	}

	public Long getIdPersonaMoralPatron() {
		return idPersonaMoralPatron;
	}

	public void setIdPersonaMoralPatron(Long idPersonaMoralPatron) {
		this.idPersonaMoralPatron = idPersonaMoralPatron;
	}
	
	public String getDomicilioReporte() {
		StringBuffer domicilioString = new StringBuffer();
		if (domicilioFiscal!=null) {
			domicilioString.append("CALLE ").append(domicilioFiscal.getCalle()!=null?domicilioFiscal.getCalle():"")
							.append("; NÚMERO EXTERIOR: ").append(domicilioFiscal.getNumExteriorAlf()!=null?domicilioFiscal.getNumExteriorAlf():"")
							.append(domicilioFiscal.getNumInteriorAlf()!=null?"; NÚMERO INTERIOR: ":"")
							.append(domicilioFiscal.getNumInteriorAlf()!=null?domicilioFiscal.getNumInteriorAlf():"")
							.append("; LOCALIDAD O COLONIA ").append(domicilioFiscal.getColonia()!=null?domicilioFiscal.getColonia():"")
							.append("; CÓDIGO POSTAL: ").append(domicilioFiscal.getCodigoPostal()!=null?domicilioFiscal.getCodigoPostal():"");
			if (domicilioFiscal.getAsentamiento()!=null && domicilioFiscal.getAsentamiento().getLocalidad()!=null && domicilioFiscal.getAsentamiento().getLocalidad().getMunicipio()!=null) {
					domicilioString.append("; ENTIDAD FEDERATIVA: ").append(domicilioFiscal.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa())
									.append("; MUNICIPIO O DELEGACIÓN: ").append(domicilioFiscal.getAsentamiento().getLocalidad().getMunicipio().getNombre())
									.append("; ");
			}
			
			for (MedioContacto medioContacto : mediosContacto) {				
				switch (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().intValue()) {
				case 1:
					domicilioString.append("CORREO ELECTRÓNICO PERSONAL: ")
									.append(medioContacto.getDesFormaContacto())
									.append("; ");
					break;
				case 2:
					domicilioString.append("TELÉFONO FIJO CON LADA (10 DÍGITOS): ")
									.append(medioContacto.getDesFormaContacto())
									.append("; ");
					break;
				case 3:
					domicilioString.append("TELÉFONO MÓVIL: ")
									.append(medioContacto.getDesFormaContacto())
									.append("; ");
					break;
				case 4:
					domicilioString.append("FACEBOOK: ")
									.append(medioContacto.getDesFormaContacto())
									.append("; ");
					break;
				case 5:
					domicilioString.append("TWITTER: ")
									.append(medioContacto.getDesFormaContacto())
									.append("; ");
					break;
				default:
					break;
				}				
			}
			
			domicilioReporte=domicilioString.toString();
		}		
		
		return domicilioReporte;
	}

	public void setDomicilioReporte(String domicilioReporte) {
		this.domicilioReporte = domicilioReporte;
	}

	public Fisica getPersonaFisica() {
		return personaFisica;
	}

	public void setPersonaFisica(Fisica personaFisica) {
		this.personaFisica = personaFisica;
	}

	public Moral getPersonaMoral() {
		return personaMoral;
	}

	public void setPersonaMoral(Moral personaMoral) {
		this.personaMoral = personaMoral;
	}

	public TramiteFisica getTramiteFisica() {
		return tramiteFisica;
	}
	
	public void setTramiteFisica(TramiteFisica tramiteFisica) {
		this.tramiteFisica = tramiteFisica;
	}
	
	public String getRfcPersonaMoralPatron() {
		return rfcPersonaMoralPatron;
	}

	public void setRfcPersonaMoralPatron(String rfcPersonaMoralPatron) {
		this.rfcPersonaMoralPatron = rfcPersonaMoralPatron;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Socio [idPersona=");
		builder.append(idPersona);
		builder.append(", idSocio=");
		builder.append(idSocio);
		builder.append(", registroPatronal=");
		builder.append(registroPatronal);
		builder.append(", cveIdPatronSujetoObligado=");
		builder.append(cveIdPatronSujetoObligado);
		builder.append(", rfc=");
		builder.append(rfc);
		builder.append(", tipoPersonaFiscalPatron=");
		builder.append(tipoPersonaFiscalPatron);
		builder.append(", idPersonaMoralPatron=");
		builder.append(idPersonaMoralPatron);
		builder.append(", telefonoFijo=");
		builder.append(telefonoFijo);
		builder.append(", extension=");
		builder.append(extension);
		builder.append(", telefonoMovil=");
		builder.append(telefonoMovil);
		builder.append(", email=");
		builder.append(email);
		builder.append(", emailAlterno=");
		builder.append(emailAlterno);
		builder.append(", esNacional=");
		builder.append(esNacional);
		builder.append(", esDomicilioNacional=");
		builder.append(esDomicilioNacional);
		builder.append(", esPersonaFisica=");
		builder.append(esPersonaFisica);
		builder.append(", curp=");
		builder.append(curp);
		builder.append(", primerApellido=");
		builder.append(primerApellido);
		builder.append(", segundoApellido=");
		builder.append(segundoApellido);
		builder.append(", nombres=");
		builder.append(nombres);
		builder.append(", personaFisica=");
		builder.append(personaFisica);
		builder.append(", denominacionRazonSocial=");
		builder.append(denominacionRazonSocial);
		builder.append(", tipoSociedad=");
		builder.append(tipoSociedad);
		builder.append(", personaMoral=");
		builder.append(personaMoral);
		builder.append(", localidadColonia=");
		builder.append(localidadColonia);
		builder.append(", paisYCiudad=");
		builder.append(paisYCiudad);
		builder.append(", estadoProvincia=");
		builder.append(estadoProvincia);
		builder.append(", fecRegistroActualizado=");
		builder.append(fecRegistroActualizado);
		builder.append(", fecRegistroAlta=");
		builder.append(fecRegistroAlta);
		builder.append(", fecRegistroBaja=");
		builder.append(fecRegistroBaja);
		builder.append(", persona=");
		builder.append(persona);
		builder.append(", nombreRazonSocial=");
		builder.append(nombreRazonSocial);
		builder.append(", accion=");
		builder.append(accion);
		builder.append(", domicilioFiscal=");
		builder.append(domicilioFiscal);
		builder.append(", escrituraConstitutiva=");
		builder.append(escrituraConstitutiva);
		builder.append(", numeroInstrumetoProtocolizacion=");
		builder.append(numeroInstrumetoProtocolizacion);
		builder.append(", notariaCorreduria=");
		builder.append(notariaCorreduria);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", fechaExpedicionContrato=");
		builder.append(fechaExpedicionContrato);
		builder.append(", mediosContacto=");
		builder.append(mediosContacto);
		builder.append(", tipoSocio=");
		builder.append(tipoSocio);
		builder.append(", domicilioReporte=");
		builder.append(domicilioReporte);
		builder.append("]");
		return builder.toString();
	}
}
