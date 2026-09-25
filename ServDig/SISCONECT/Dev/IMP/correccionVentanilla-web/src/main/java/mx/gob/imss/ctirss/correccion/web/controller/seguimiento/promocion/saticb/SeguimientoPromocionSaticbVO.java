/**
 * SeguimientoPromocionSaticbVO.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.saticb
 * @project correccion-web
 */
package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.saticb;

import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SeguimientoEstatusObraVO;
import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;



/**
 * Objeto visual el cual nos apoya al momento de encapsular la informacion de la pantalla
 * manejada en el flujo de Seguimiento de promocion SATIC B
 * 
 * @author Oscar Beltran Ortega
 * @version 1.0.1
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeguimientoPromocionSaticbVO extends ControlTabs {
	
	
	private String cvePromocion;
	
	private SeguimientoSaticbTabVO segSaticTabVo;
	private SegAutAvisoDictSaticbTabVO segAvisoDictamenVo;
	private SeguimientoEstatusObraVO segEstatusObraVo;
	private DgDomicilioGeografico domicilioGeografico;
	
	
	private String desCriterioSeleccion;
	private String numFolioPromocion;
	private String fechaOficioPromocion;
	private String numOficioPromocion;
	
	private String registroPatronal;
	private String nomRazonSocialPatron;
	private String callePatron;
	private String coloniaPatron;
	private String numExteriorPatron;
	private String numInteriorPatron;
	private String codigoPostalPatron;
	
	private String txtSaticb;
	private String registroObra;
	private String calleObra;
	private String coloniaObra;
	private String numExteriorObra;
	private String numInteriorObra;
	private String codigoPostalObra;
	private String cvePatron;
	private String cveEstatus;
	private String nombreFuncionario;
	
	private String rolUsuario;
	

	/**
	 * Retorna el valor segSaticTabVo
	 * @return  segSaticTabVo
	 */
	public SeguimientoSaticbTabVO getSegSaticTabVo() {
		return segSaticTabVo;
	}

	/**
	 * Asigna el valor del segSaticTabVo al atributo segSaticTabVo
	 * @param segSaticTabVo 
	 */
	public void setSegSaticTabVo(SeguimientoSaticbTabVO segSaticTabVo) {
		this.segSaticTabVo = segSaticTabVo;
	}

//	/**
//	 * Retorna el valor segDerivaFiscaTabVo
//	 * @return  segDerivaFiscaTabVo
//	 */
//	public SegDerivacionFiscalizaGenericoTabVO getSegDerivaFiscaTabVo() {
//		return segDerivaFiscaTabVo;
//	}
//
//	/**
//	 * Asigna el valor del segDerivaFiscaTabVo al atributo segDerivaFiscaTabVo
//	 * @param segDerivaFiscaTabVo 
//	 */
//	public void setSegDerivaFiscaTabVo(
//			SegDerivacionFiscalizaGenericoTabVO segDerivaFiscaTabVo) {
//		this.segDerivaFiscaTabVo = segDerivaFiscaTabVo;
//	}

	/**
	 * Retorna el valor cvePromocion
	 * @return  cvePromocion
	 */
	public String getCvePromocion() {
		return cvePromocion;
	}

	/**
	 * Asigna el valor del cvePromocion al atributo cvePromocion
	 * @param cvePromocion 
	 */
	public void setCvePromocion(String cvePromocion) {
		this.cvePromocion = cvePromocion;
	}

	/**
	 * Retorna el valor segAvisoDictamenVo
	 * @return  segAvisoDictamenVo
	 */
	public SegAutAvisoDictSaticbTabVO getSegAvisoDictamenVo() {
		return segAvisoDictamenVo;
	}

	/**
	 * Asigna el valor del segAvisoDictamenVo al atributo segAvisoDictamenVo
	 * @param segAvisoDictamenVo 
	 */
	public void setSegAvisoDictamenVo(SegAutAvisoDictSaticbTabVO segAvisoDictamenVo) {
		this.segAvisoDictamenVo = segAvisoDictamenVo;
	}

	/**
	 * Retorna el valor segRegularizaObraVo
	 * @return  segRegularizaObraVo
	 */
	/*public SegRegularizarObraGenericoTabVO getSegRegularizaObraVo() {
		return segRegularizaObraVo;
	}*/

	/**
	 * Asigna el valor del segRegularizaObraVo al atributo segRegularizaObraVo
	 * @param segRegularizaObraVo 
	 */
	/*public void setSegRegularizaObraVo(
			SegRegularizarObraGenericoTabVO segRegularizaObraVo) {
		this.segRegularizaObraVo = segRegularizaObraVo;
	}*/

	/**
	 * Retorna el valor desCriterioSeleccion
	 * @return  desCriterioSeleccion
	 */
	public String getDesCriterioSeleccion() {
		return desCriterioSeleccion;
	}

	/**
	 * Asigna el valor del desCriterioSeleccion al atributo desCriterioSeleccion
	 * @param desCriterioSeleccion 
	 */
	public void setDesCriterioSeleccion(String desCriterioSeleccion) {
		this.desCriterioSeleccion = desCriterioSeleccion;
	}

	/**
	 * Retorna el valor numFolioPromocion
	 * @return  numFolioPromocion
	 */
	public String getNumFolioPromocion() {
		return numFolioPromocion;
	}

	/**
	 * Asigna el valor del numFolioPromocion al atributo numFolioPromocion
	 * @param numFolioPromocion 
	 */
	public void setNumFolioPromocion(String numFolioPromocion) {
		this.numFolioPromocion = numFolioPromocion;
	}

	/**
	 * Retorna el valor fechaOficioPromocion
	 * @return  fechaOficioPromocion
	 */
	public String getFechaOficioPromocion() {
		return fechaOficioPromocion;
	}

	/**
	 * Asigna el valor del fechaOficioPromocion al atributo fechaOficioPromocion
	 * @param fechaOficioPromocion 
	 */
	public void setFechaOficioPromocion(String fechaOficioPromocion) {
		this.fechaOficioPromocion = fechaOficioPromocion;
	}

	/**
	 * Retorna el valor numOficioPromocion
	 * @return  numOficioPromocion
	 */
	public String getNumOficioPromocion() {
		return numOficioPromocion;
	}

	/**
	 * Asigna el valor del numOficioPromocion al atributo numOficioPromocion
	 * @param numOficioPromocion 
	 */
	public void setNumOficioPromocion(String numOficioPromocion) {
		this.numOficioPromocion = numOficioPromocion;
	}

	/**
	 * Retorna el valor registroPatronal
	 * @return  registroPatronal
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	/**
	 * Asigna el valor del registroPatronal al atributo registroPatronal
	 * @param registroPatronal 
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	/**
	 * Retorna el valor nomRazonSocialPatron
	 * @return  nomRazonSocialPatron
	 */
	public String getNomRazonSocialPatron() {
		return nomRazonSocialPatron;
	}

	/**
	 * Asigna el valor del nomRazonSocialPatron al atributo nomRazonSocialPatron
	 * @param nomRazonSocialPatron 
	 */
	public void setNomRazonSocialPatron(String nomRazonSocialPatron) {
		this.nomRazonSocialPatron = nomRazonSocialPatron;
	}

	/**
	 * Retorna el valor callePatron
	 * @return  callePatron
	 */
	public String getCallePatron() {
		return callePatron;
	}

	/**
	 * Asigna el valor del callePatron al atributo callePatron
	 * @param callePatron 
	 */
	public void setCallePatron(String callePatron) {
		this.callePatron = callePatron;
	}

	/**
	 * Retorna el valor coloniaPatron
	 * @return  coloniaPatron
	 */
	public String getColoniaPatron() {
		return coloniaPatron;
	}

	/**
	 * Asigna el valor del coloniaPatron al atributo coloniaPatron
	 * @param coloniaPatron 
	 */
	public void setColoniaPatron(String coloniaPatron) {
		this.coloniaPatron = coloniaPatron;
	}

	/**
	 * Retorna el valor numExteriorPatron
	 * @return  numExteriorPatron
	 */
	public String getNumExteriorPatron() {
		return numExteriorPatron;
	}

	/**
	 * Asigna el valor del numExteriorPatron al atributo numExteriorPatron
	 * @param numExteriorPatron 
	 */
	public void setNumExteriorPatron(String numExteriorPatron) {
		this.numExteriorPatron = numExteriorPatron;
	}

	/**
	 * Retorna el valor numInteriorPatron
	 * @return  numInteriorPatron
	 */
	public String getNumInteriorPatron() {
		return numInteriorPatron;
	}

	/**
	 * Asigna el valor del numInteriorPatron al atributo numInteriorPatron
	 * @param numInteriorPatron 
	 */
	public void setNumInteriorPatron(String numInteriorPatron) {
		this.numInteriorPatron = numInteriorPatron;
	}

	/**
	 * Retorna el valor codigoPostalPatron
	 * @return  codigoPostalPatron
	 */
	public String getCodigoPostalPatron() {
		return codigoPostalPatron;
	}

	/**
	 * Asigna el valor del codigoPostalPatron al atributo codigoPostalPatron
	 * @param codigoPostalPatron 
	 */
	public void setCodigoPostalPatron(String codigoPostalPatron) {
		this.codigoPostalPatron = codigoPostalPatron;
	}

	/**
	 * Retorna el valor txtSaticb
	 * @return  txtSaticb
	 */
	public String getTxtSaticb() {
		return txtSaticb;
	}

	/**
	 * Asigna el valor del txtSaticb al atributo txtSaticb
	 * @param txtSaticb 
	 */
	public void setTxtSaticb(String txtSaticb) {
		this.txtSaticb = txtSaticb;
	}

	/**
	 * Retorna el valor registroObra
	 * @return  registroObra
	 */
	public String getRegistroObra() {
		return registroObra;
	}

	/**
	 * Asigna el valor del registroObra al atributo registroObra
	 * @param registroObra 
	 */
	public void setRegistroObra(String registroObra) {
		this.registroObra = registroObra;
	}

	/**
	 * Retorna el valor calleObra
	 * @return  calleObra
	 */
	public String getCalleObra() {
		return calleObra;
	}

	/**
	 * Asigna el valor del calleObra al atributo calleObra
	 * @param calleObra 
	 */
	public void setCalleObra(String calleObra) {
		this.calleObra = calleObra;
	}

	/**
	 * Retorna el valor coloniaObra
	 * @return  coloniaObra
	 */
	public String getColoniaObra() {
		return coloniaObra;
	}

	/**
	 * Asigna el valor del coloniaObra al atributo coloniaObra
	 * @param coloniaObra 
	 */
	public void setColoniaObra(String coloniaObra) {
		this.coloniaObra = coloniaObra;
	}

	/**
	 * Retorna el valor numExteriorObra
	 * @return  numExteriorObra
	 */
	public String getNumExteriorObra() {
		return numExteriorObra;
	}

	/**
	 * Asigna el valor del numExteriorObra al atributo numExteriorObra
	 * @param numExteriorObra 
	 */
	public void setNumExteriorObra(String numExteriorObra) {
		this.numExteriorObra = numExteriorObra;
	}

	/**
	 * Retorna el valor numInteriorObra
	 * @return  numInteriorObra
	 */
	public String getNumInteriorObra() {
		return numInteriorObra;
	}

	/**
	 * Asigna el valor del numInteriorObra al atributo numInteriorObra
	 * @param numInteriorObra 
	 */
	public void setNumInteriorObra(String numInteriorObra) {
		this.numInteriorObra = numInteriorObra;
	}

	/**
	 * Retorna el valor codigoPostalObra
	 * @return  codigoPostalObra
	 */
	public String getCodigoPostalObra() {
		return codigoPostalObra;
	}

	/**
	 * Asigna el valor del codigoPostalObra al atributo codigoPostalObra
	 * @param codigoPostalObra 
	 */
	public void setCodigoPostalObra(String codigoPostalObra) {
		this.codigoPostalObra = codigoPostalObra;
	}

	/**
	 * Retorna el valor cvePatron
	 * @return  cvePatron
	 */
	public String getCvePatron() {
		return cvePatron;
	}

	/**
	 * Asigna el valor del cvePatron al atributo cvePatron
	 * @param cvePatron 
	 */
	public void setCvePatron(String cvePatron) {
		this.cvePatron = cvePatron;
	}

	/**
	 * Retorna el valor cveEstatus
	 * @return  cveEstatus
	 */
	public String getCveEstatus() {
		return cveEstatus;
	}

	/**
	 * Asigna el valor del cveEstatus al atributo cveEstatus
	 * @param cveEstatus 
	 */
	public void setCveEstatus(String cveEstatus) {
		this.cveEstatus = cveEstatus;
	}

	/**
	 * Retorna el valor nombreFuncionario
	 * @return  nombreFuncionario
	 */
	public String getNombreFuncionario() {
		return nombreFuncionario;
	}

	/**
	 * Asigna el valor del nombreFuncionario al atributo nombreFuncionario
	 * @param nombreFuncionario 
	 */
	public void setNombreFuncionario(String nombreFuncionario) {
		this.nombreFuncionario = nombreFuncionario;
	}
	
	

	/**
	 * @return the rolUsuario
	 */
	public String getRolUsuario() {
		return rolUsuario;
	}

	/**
	 * @param rolUsuario the rolUsuario to set
	 */
	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}

	/**
	 * Retorna el valor segEstatusObraVo
	 * @return  segEstatusObraVo
	 */
	public SeguimientoEstatusObraVO getSegEstatusObraVo() {
		return segEstatusObraVo;
	}

	/**
	 * Asigna el valor del segEstatusObraVo al atributo segEstatusObraVo
	 * @param segEstatusObraVo 
	 */
	public void setSegEstatusObraVo(SeguimientoEstatusObraVO segEstatusObraVo) {
		this.segEstatusObraVo = segEstatusObraVo;
	}

	/**
	 * Retorna el valor domicilioGeografico
	 * @return  domicilioGeografico
	 */
	public DgDomicilioGeografico getDomicilioGeografico() {
		return domicilioGeografico;
	}

	/**
	 * Asigna el valor del domicilioGeografico al atributo domicilioGeografico
	 * @param domicilioGeografico 
	 */
	public void setDomicilioGeografico(DgDomicilioGeografico domicilioGeografico) {
		this.domicilioGeografico = domicilioGeografico;
	}

	
	
	
}
