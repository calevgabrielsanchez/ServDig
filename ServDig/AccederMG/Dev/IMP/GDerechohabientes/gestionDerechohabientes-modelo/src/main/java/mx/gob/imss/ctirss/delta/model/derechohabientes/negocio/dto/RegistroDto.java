package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

/**
 * 
 * @author Juan Manuel Marquez
 *
 */
public class RegistroDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String SES_NAME="miRegistro";
	
	private TramiteRegistroDerechohabiente tramiteRegistro;
	private Usuario miUsuario;
	private AsignacionNSS datosAsegurado;
	private RazonRegistro tipoRegistro;
	private Domicilio domicilio;	
	private RechazoDto resultado;
	private SujetoObligado sujetoObligado;
	private EntidadFederativa lugarNacimientoAseg;
	private Sexo sexoAseg;
	private int proceso;
	private String conAsegurado;
	private CabezaGrupoFamiliar cabezaGpoFam;
	private String vigencia;
	private String mc;
	private boolean patronImss;
	private RequisitosDTO requisitos;
	private String elemento;
	private String adicional;
	private Boolean errorBusqueda;
	private int requisito;
	

	public TramiteRegistroDerechohabiente getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistroDerechohabiente tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getSesName() {
		return SES_NAME;
	}

	/**
	 * @return the resultado
	 */
	public RechazoDto getResultado() {
		return resultado;
	}

	/**
	 * @param resultado the resultado to set
	 */
	public void setResultado(RechazoDto resultado) {
		this.resultado = resultado;
	}

	/**
	 * @return the sujetoObligado
	 */
	public SujetoObligado getSujetoObligado() {
		return sujetoObligado;
	}

	/**
	 * @param sujetoObligado the sujetoObligado to set
	 */
	public void setSujetoObligado(SujetoObligado sujetoObligado) {
		this.sujetoObligado = sujetoObligado;
	}

	/**
	 * @return the lugarNacimientoAseg
	 */
	public EntidadFederativa getLugarNacimientoAseg() {
		return lugarNacimientoAseg;
	}

	/**
	 * @param lugarNacimientoAseg the lugarNacimientoAseg to set
	 */
	public void setLugarNacimientoAseg(EntidadFederativa lugarNacimientoAseg) {
		this.lugarNacimientoAseg = lugarNacimientoAseg;
	}

	/**
	 * @return the sexoAseg
	 */
	public Sexo getSexoAseg() {
		return sexoAseg;
	}

	/**
	 * @param sexoAseg the sexoAseg to set
	 */
	public void setSexoAseg(Sexo sexoAseg) {
		this.sexoAseg = sexoAseg;
	}

	/**
	 * @return the proceso
	 */
	public int getProceso() {
		return proceso;
	}

	/**
	 * @param proceso the proceso to set
	 */
	public void setProceso(int proceso) {
		this.proceso = proceso;
	}



	/**
	 * @return the miUsuario
	 */
	public Usuario getMiUsuario() {
		return miUsuario;
	}

	/**
	 * @param miUsuario the miUsuario to set
	 */
	public void setMiUsuario(Usuario miUsuario) {
		this.miUsuario = miUsuario;
	}

	/**
	 * @return the datosAsegurado
	 */
	public AsignacionNSS getDatosAsegurado() {
		return datosAsegurado;
	}

	/**
	 * @param datosAsegurado the datosAsegurado to set
	 */
	public void setDatosAsegurado(AsignacionNSS datosAsegurado) {
		this.datosAsegurado = datosAsegurado;
	}

	/**
	 * @return the conAsegurado
	 */
	public String getConAsegurado() {
		return conAsegurado;
	}

	/**
	 * @param conAsegurado the conAsegurado to set
	 */
	public void setConAsegurado(String conAsegurado) {
		this.conAsegurado = conAsegurado;
	}

	/**
	 * @return the cabezaGpoFam
	 */
	public CabezaGrupoFamiliar getCabezaGpoFam() {
		return cabezaGpoFam;
	}

	/**
	 * @param cabezaGpoFam the cabezaGpoFam to set
	 */
	public void setCabezaGpoFam(CabezaGrupoFamiliar cabezaGpoFam) {
		this.cabezaGpoFam = cabezaGpoFam;
	}

	/**
	 * @return the vigencia
	 */
	public String getVigencia() {
		return vigencia;
	}

	/**
	 * @param vigencia the vigencia to set
	 */
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	/**
	 * @return the mc
	 */
	public String getMc() {
		return mc;
	}

	/**
	 * @param mc the mc to set
	 */
	public void setMc(String mc) {
		this.mc = mc;
	}

	/**
	 * @return the patronImss
	 */
	public boolean isPatronImss() {
		return patronImss;
	}

	/**
	 * @param patronImss the patronImss to set
	 */
	public void setPatronImss(boolean patronImss) {
		this.patronImss = patronImss;
	}

	/**
	 * @return the requisitos
	 */
	public RequisitosDTO getRequisitos() {
		return requisitos;
	}

	/**
	 * @param requisitos the requisitos to set
	 */
	public void setRequisitos(RequisitosDTO requisitos) {
		this.requisitos = requisitos;
	}

	/**
	 * @return the elemento
	 */
	public String getElemento() {
		return elemento;
	}

	/**
	 * @param elemento the elemento to set
	 */
	public void setElemento(String elemento) {
		this.elemento = elemento;
	}

	public String getAdicional() {
		return adicional;
	}

	public void setAdicional(String adicional) {
		this.adicional = adicional;
	}

	public Boolean getErrorBusqueda() {
		return errorBusqueda;
	}

	public void setErrorBusqueda(Boolean errorBusqueda) {
		this.errorBusqueda = errorBusqueda;
	}

	public RazonRegistro getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(RazonRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public int getRequisito() {
		return requisito;
	}

	public void setRequisito(int requisito) {
		this.requisito = requisito;
	}

	
	
}
