package mx.gob.imss.ctirss.correccion.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.base.model.AbstractSatPatron;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacEntidadfed;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

@Entity
@Table(name="SAT_PATRON")
public class SatPatron extends AbstractSatPatron {
	
	
	@Transient
	private String domicilioCompleto; 
	
	@Transient
	private String subDelegacion; 

	@Transient
	private DgDomicilioGeografico domicilioGeografico = null; 
	

	@Transient
	private String trabajadores;

	@Transient
	private String clase;

	@Transient
	private String fraccion;

	@Transient
	private String prima;

	@Transient
	private String actividad;
	
	@Transient
	private String bandera;
	
	@Transient
	private String cveFkPatronTemp;

	@Transient
	private int cveRespuestaWS;
	
	@Transient
	private String descRespuestaWS;
	
	@Transient
	private String cveTipoMovWS;	
	
//	public String imprimeObjeto(){
//		return new StringBuffer().append("SatPatron{")
//								 .append("registroPatronal:").append(this.getRegistroPatronal()).append(";\n")
//								 .append("domicilioCompleto:").append(this.getDomicilioCompleto()).append(";\n")
//								 .append("}")
//								 .toString();
//	}

	
	public String getRegistroPatronalSD(){
		if(getRegistroPatronal()!=null)
			return getRegistroPatronal().substring(0,getRegistroPatronal().length()-1);
		else
			return "";
	}

	public DgDomicilioGeografico getDomicilioGeografico() {
		return domicilioGeografico;
	}

	public void setDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.domicilioGeografico = dgDomicilioGeografico;
	}

	public String getTrabajadores() {
		return trabajadores;
	}

	public void setTrabajadores(String trabajadores) {
		this.trabajadores = trabajadores;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getFraccion() {
		return fraccion;
	}

	public void setFraccion(String fraccion) {
		this.fraccion = fraccion;
	}

	public String getPrima() {
		return prima;
	}

	public void setPrima(String prima) {
		this.prima = prima;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getDomicilioCompleto() {
		String dom = "";
		if(domicilioGeografico!=null)
		{
			String numExtCompleto ="";
			String numIntCompleto ="";
			
			numExtCompleto = (this.domicilioGeografico.getNumextnum()!=null?this.domicilioGeografico.getNumextnum()+"":"");
			numExtCompleto+= (this.domicilioGeografico.getNumextalf()!=null?("-"+this.domicilioGeografico.getNumextalf())+"":"");
			
			numIntCompleto = (this.domicilioGeografico.getNumintnum()!=null?this.domicilioGeografico.getNumintnum()+"":"");
			numIntCompleto+= (this.domicilioGeografico.getNumintalf()!=null?("-"+this.domicilioGeografico.getNumintalf())+"":"");
			
			dom = dom + this.domicilioGeografico.getDgVialidadByCveViaPrin().getNomVia();
			dom = dom +" " + numExtCompleto;
			dom = dom +" "+ numIntCompleto;
			dom = dom +" "+this.domicilioGeografico.getDgAsentamiento().getNomAsen();
			dom = dom +" "+this.domicilioGeografico.getDgCatLocalidad().getNomLoc();
			dom = dom +" "+this.domicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getNomMun();
			dom = dom +" "+this.domicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt();
			dom = dom +" "+this.domicilioGeografico.getDgCodigosPostales().getId().getCodigo();
		}
		else if(this.getUbicacion()!=null)
		{
			if(this.getUbicacion().getCalle()!=null)
				dom = dom +this.getUbicacion().getCalle();
			if(this.getUbicacion().getNumeroExterior()!=null)
				dom = dom +" "+this.getUbicacion().getNumeroExterior();
			if(this.getUbicacion().getNumeroInterior()!=null)
				dom = dom +" "+this.getUbicacion().getNumeroInterior();
			if(this.getUbicacion().getColonia()!=null)
				dom = dom +" "+this.getUbicacion().getColonia();
			if(this.getUbicacion().getMunicipio()!=null)
				dom = dom +" "+this.getUbicacion().getMunicipio().getNombre();
			if(this.getUbicacion().getMunicipio().getSacEntidadFederativa()!=null)
				dom = dom +" "+this.getUbicacion().getMunicipio().getSacEntidadFederativa().getNomNombre();
			if(this.getUbicacion()!=null)
				dom = dom +" "+this.getUbicacion().getCodigoPostal();
		}
		return dom;
	}

	
	@Transient
	SatUbicacion ubicacion;

	@Transient
	public String getSubDelegacion() {
		return subDelegacion;
	}

	@Transient
	public void setSubDelegacion(String subDelegacion) {
		this.subDelegacion = subDelegacion;
	}
	
	@Transient
	public String getBandera() {
		return bandera;
	}

	@Transient
	public void setBandera(String bandera) {
		this.bandera = bandera;
	}

	public SatUbicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(SatUbicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public String getCalle(){
		if(domicilioGeografico!=null)
			return this.domicilioGeografico.getNomvial();
		else
			if(this.getUbicacion()!=null)
				return this.getUbicacion().getCalle();
			else
				return "";
	}
	
	public String getNumeroExterior(){
		if(domicilioGeografico!=null){
			String numExt = this.domicilioGeografico.getNumextnum()!=null?this.domicilioGeografico.getNumextnum()+"":"";
			numExt += this.domicilioGeografico.getNumextalf()!=null? "-"+this.domicilioGeografico.getNumextalf()+"":"";
			return numExt;
		}else
			if(this.getUbicacion()!=null)
				return this.getUbicacion().getNumeroExterior();
			else
				return "";
	}

	public String getNumeroInterior(){
		if(domicilioGeografico!=null){
			String numInt = this.domicilioGeografico.getNumintnum()!=null?this.domicilioGeografico.getNumintnum()+"":"";
			numInt += this.domicilioGeografico.getNumintalf()!=null? "-"+this.domicilioGeografico.getNumintalf()+"":"";
			return numInt;
		}else
			if(this.getUbicacion()!=null)
				return this.getUbicacion().getNumeroInterior();
			else
				return "";
	}
	
	public String getColonia(){
		if(domicilioGeografico!=null)
			return this.domicilioGeografico.getDgAsentamiento().getNomAsen();
		else
			if(this.getUbicacion()!=null)
				return this.getUbicacion().getColonia();
			else
				return "";
	}
	
	public String getMunicipio(){
		if(domicilioGeografico!=null)
			return this.domicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getNomMun();
		else
			if(this.getUbicacion()!=null)
				return this.getUbicacion().getMunicipio().getNombre();
			else
				return "";
	}
	
	public String getLocalidad(){
		if(domicilioGeografico!=null)
			return this.domicilioGeografico.getDgCatLocalidad().getNomLoc();
		else
			return "";
	}
	
	
	public String getEntidadFederativa(){
		if(domicilioGeografico!=null)
			return this.domicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt();
		else
			if(this.getUbicacion()!=null)
				return this.getUbicacion().getMunicipio().getSacEntidadFederativa().getNomNombre();
			else
				return "";
	}
	
	public String getCodigoPostal(){
		if(domicilioGeografico!=null)
			return this.domicilioGeografico.getDgCodigosPostales().getId().getCodigo();
		else
			if(this.getUbicacion()!=null)
				return this.getUbicacion().getCodigoPostal();
			else
				return "";
	}

	/**
	 * @return the cveFkPatronTemp
	 */
	@Transient
	public String getCveFkPatronTemp() {
		return cveFkPatronTemp;
	}

	/**
	 * @param cveFkPatronTemp the cveFkPatronTemp to set
	 */
	@Transient
	public void setCveFkPatronTemp(String cveFkPatronTemp) {
		this.cveFkPatronTemp = cveFkPatronTemp;
	}

	/**
	 * Devuelve la clave de respuesta del web service
	 * 
	 * @return the cveRespuestaWS
	 */
	public int getCveRespuestaWS() {
		return cveRespuestaWS;
	}

	/**
	 * Asigna la clave de respuesta del web service
	 * 
	 * @param cveRespuestaWS
	 */
	public void setCveRespuestaWS(int cveRespuestaWS) {
		this.cveRespuestaWS = cveRespuestaWS;
	}

	/**
	 * Devuelve la descripcion de la respuesta del web service
	 * 
	 * @return descRespuestaWS
	 */
	public String getDescRespuestaWS() {
		return descRespuestaWS;
	}

	/**
	 * Asigna la descripcion de la respuesta del web service
	 * 
	 * @param descRespuestaWS
	 */
	public void setDescRespuestaWS(String descRespuestaWS) {
		this.descRespuestaWS = descRespuestaWS;
	}

	/**
	 * Devuelve la clave del tipo de movimiento del web service
	 * 
	 * @return cveTipoMovWS
	 */
	public String getCveTipoMovWS() {
		return cveTipoMovWS;
	}

	/**
	 * Asigna la clave del tipo de movimiento del web service
	 * 
	 * @param cveTipoMovWS
	 */
	public void setCveTipoMovWS(String cveTipoMovWS) {
		this.cveTipoMovWS = cveTipoMovWS;
	}
	
	
}
