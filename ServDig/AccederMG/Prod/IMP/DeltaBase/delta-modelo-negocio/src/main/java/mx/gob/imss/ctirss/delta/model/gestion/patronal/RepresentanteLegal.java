package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;

public class RepresentanteLegal extends ItemClasificacion {
	
	private static final long serialVersionUID = 1L;
	
	private Long cveIdPatronSujetoObligado;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private BigDecimal indEstatus;
	private Set<Long> cveIdRlDomicilioList;
	private Set<Long> cveIdRlFacultadList;
	private List<Facultad> facultades;
	//Datos reales que se emplearán para la version final
	private Long cveIdRepresentanteLegal;
	private Long cveIdMandato;
	private Long cveIdTipoPoder;

	
	//DATOS FINALES DE REPRESENTANTE LEGAL
	private Fisica personaFisica;//Datos del representante
	private Long cveIdPersona;//Identificador de la persona representada
	private BigDecimal indActAdmonDominio;
	private String rupa;
	private TipoPersona tipoPersonaRepresentada;
	private TipoAccionAfectacionEnum accion;
	private List<MedioContacto> mediosContacto = new ArrayList<MedioContacto>();
	
	private TramiteFisica tramiteFisica;
	
	//Se agrega para transportar los datos de representados fisicos y morales
	private Fisica personaFisicaRepresentada;
	private Moral personaMoralRepresentada;
	
	public TipoPersona getTipoPersonaRepresentada() {
		return tipoPersonaRepresentada;
	}

	public void setTipoPersonaRepresentada(TipoPersona tipoPersonaRepresentada) {
		this.tipoPersonaRepresentada = tipoPersonaRepresentada;
	}

	public Long getCveIdRepresentanteLegal() {
		return cveIdRepresentanteLegal;
	}

	public void setCveIdRepresentanteLegal(Long cveIdRepresentanteLegal) {
		this.cveIdRepresentanteLegal = cveIdRepresentanteLegal;
	}

	public Long getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}

	public void setCveIdPatronSujetoObligado(Long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

	public Long getCveIdPersona() {
		return cveIdPersona;
	}

	public void setCveIdPersona(Long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
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
	
	public BigDecimal getIndActAdmonDominio() {
		return indActAdmonDominio;
	}

	public void setIndActAdmonDominio(BigDecimal indActAdmonDominio) {
		this.indActAdmonDominio = indActAdmonDominio;
	}

	public BigDecimal getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(BigDecimal indEstatus) {
		this.indEstatus = indEstatus;
	}

	public String getRupa() {
		return rupa;
	}

	public void setRupa(String rupa) {
		this.rupa = rupa;
	}

	public Long getCveIdMandato() {
		return cveIdMandato;
	}

	public void setCveIdMandato(Long cveIdMandato) {
		this.cveIdMandato = cveIdMandato;
	}

	public Long getCveIdTipoPoder() {
		return cveIdTipoPoder;
	}

	public void setCveIdTipoPoder(Long cveIdTipoPoder) {
		this.cveIdTipoPoder = cveIdTipoPoder;
	}

	public Set<Long> getCveIdRlDomicilioList() {
		return cveIdRlDomicilioList;
	}

	public void setCveIdRlDomicilioList(Set<Long> cveIdRlDomicilioList) {
		this.cveIdRlDomicilioList = cveIdRlDomicilioList;
	}

	public Set<Long> getCveIdRlFacultadList() {
		return cveIdRlFacultadList;
	}

	public void setCveIdRlFacultadList(Set<Long> cveIdRlFacultadList) {
		this.cveIdRlFacultadList = cveIdRlFacultadList;
	}

	/**
	 * @return the personaFisica
	 */
	public Fisica getPersonaFisica() {
		return personaFisica;
	}

	/**
	 * @param personaFisica
	 *            the personaFisica to set
	 */
	public void setPersonaFisica(Fisica personaFisica) {
		this.personaFisica = personaFisica;
	}
	
	/**
	 * @return the facultades
	 */
	public List<Facultad> getFacultades() {
		return facultades;
	}

	/**
	 * @param facultades the facultades to set
	 */
	public void setFacultades(List<Facultad> facultades) {
		this.facultades = facultades;
	}
	
	public List<MedioContacto> getMediosContacto() {
		return mediosContacto;
	}

	public void setMediosContacto(List<MedioContacto> mediosContacto) {
		this.mediosContacto = mediosContacto;
	}
	
	

	public TipoAccionAfectacionEnum getAccion() {
		return accion;
	}

	public void setAccion(TipoAccionAfectacionEnum accion) {
		this.accion = accion;
	}
	
	public String getListMediosContacto(){
		
		StringBuffer sbMediosContacto = new StringBuffer();
		
		for(MedioContacto medioContacto : mediosContacto){
			sbMediosContacto.append(stringMedioContacto(medioContacto));
		}
		
		return sbMediosContacto.toString();
	} 
	
	private String stringMedioContacto(MedioContacto medioContacto) {
		StringBuffer medioContactoRetorno = new StringBuffer();
		switch (medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().intValue()) {
		case 1:
			medioContactoRetorno.append("CORREO ELECTRÓNICO PERSONAL: ")
							.append(medioContacto.getDesFormaContacto())
							.append("; ");
			break;
		case 2:
			medioContactoRetorno.append("TELÉFONO FIJO CON LADA (10 DÍGITOS): ")
							.append(medioContacto.getDesFormaContacto())
							.append("; ");
			break;
		case 3:
			medioContactoRetorno.append("TELÉFONO MÓVIL: ")
							.append(medioContacto.getDesFormaContacto())
							.append("; ");
			break;
		case 4:
			medioContactoRetorno.append("FACEBOOK: ")
							.append(medioContacto.getDesFormaContacto())
							.append("; ");
			break;
		case 5:
			medioContactoRetorno.append("TWITTER: ")
							.append(medioContacto.getDesFormaContacto())
							.append("; ");
			break;
		default:
			break;
		}
		return medioContactoRetorno.toString();
	}
	
	public TramiteFisica getTramiteFisica() {
		return tramiteFisica;
	}

	public void setTramiteFisica(TramiteFisica tramiteFisica) {
		this.tramiteFisica = tramiteFisica;
	}
	
	
	
	public Fisica getPersonaFisicaRepresentada() {
		return personaFisicaRepresentada;
	}

	public void setPersonaFisicaRepresentada(Fisica personaFisicaRepresentada) {
		this.personaFisicaRepresentada = personaFisicaRepresentada;
	}

	public Moral getPersonaMoralRepresentada() {
		return personaMoralRepresentada;
	}

	public void setPersonaMoralRepresentada(Moral personaMoralRepresentada) {
		this.personaMoralRepresentada = personaMoralRepresentada;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RepresentanteLegal [cveIdPatronSujetoObligado=");
		builder.append(cveIdPatronSujetoObligado);
		builder.append(", fecRegistroActualizado=");
		builder.append(fecRegistroActualizado);
		builder.append(", fecRegistroAlta=");
		builder.append(fecRegistroAlta);
		builder.append(", fecRegistroBaja=");
		builder.append(fecRegistroBaja);
		builder.append(", indEstatus=");
		builder.append(indEstatus);
		builder.append(", cveIdRlDomicilioList=");
		builder.append(cveIdRlDomicilioList);
		builder.append(", cveIdRlFacultadList=");
		builder.append(cveIdRlFacultadList);
		builder.append(", facultades=");
		builder.append(facultades);
		builder.append(", cveIdRepresentanteLegal=");
		builder.append(cveIdRepresentanteLegal);
		builder.append(", cveIdMandato=");
		builder.append(cveIdMandato);
		builder.append(", cveIdTipoPoder=");
		builder.append(cveIdTipoPoder);
		builder.append(", personaFisica=");
		builder.append(personaFisica);
		builder.append(", cveIdPersona=");
		builder.append(cveIdPersona);
		builder.append(", indActAdmonDominio=");
		builder.append(indActAdmonDominio);
		builder.append(", rupa=");
		builder.append(rupa);
		builder.append(", tipoPersonaRepresentada=");
		builder.append(tipoPersonaRepresentada);
		builder.append(", accion=");
		builder.append(accion);
		builder.append(", mediosContacto=");
		builder.append(mediosContacto);
		builder.append("]");
		return builder.toString();
	}
}
