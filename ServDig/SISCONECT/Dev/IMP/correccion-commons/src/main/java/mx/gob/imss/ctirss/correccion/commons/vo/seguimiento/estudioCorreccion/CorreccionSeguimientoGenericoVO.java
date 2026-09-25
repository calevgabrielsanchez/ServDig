package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.session.UserSession;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Objeto visual el cual nos apoya al momento de encapsular la informacion de los tabs
 * genericos usados en el flujo de Seguimiento de Correccion.
 * 
 *
 * @version 1.0.0
 * @since 26/07/2012
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CorreccionSeguimientoGenericoVO extends AbstractModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer cveSolCorr;
	private Integer cvePresentaCorr;
	private long cveRecepcion;
	private Integer cveStatusRecepcion;
	private String nuFolio;
	private String fecFechaPeriodoIni;
	private String fecFechaPeriodoFin;
	private String fecElaboraPre;
	private String fecElaboraSolCorr;
	private String regPatronal;
	private String cveAnexoSolCorr;
	private String razonSocial;
	private String regObra;
	private String cveIdUsuarioCorr;
//	private Long cveAuditorAsignado;
	private String cveAuditorAsignado;
	private Long cveEjercicio;
	private String nombreFuncionario;
	private Integer cveTipoCorreccion;
	private Integer banderaTipoPago;
	private UserSession user;
	private RevOficiosVO revOficiosVO;
	private RecepcionSeguimientoVO recepcionVO;
	private CedulaRevisionAudVO cedulaRevisionAudVO;
	private DevSubDelegacionSeguimientoCorreccionVO derivSubDelTabVO;
	private RevOficiosVO derivDictamenTabVO;
	private DerivacionFiscalSeguimientoCorrVO derivFiscalTabVO;
	private DerivacionFiscalSeguimientoCorrVO reactivacionTabVO;

	/**
	 * @return the nuFolio
	 */
	public String getNuFolio() {
		return nuFolio;
	}
	/**
	 * @param nuFolio the nuFolio to set
	 */
	public void setNuFolio(String nuFolio) {
		this.nuFolio = nuFolio;
	}
	/**
	 * @return the fecFechaPeriodoIni
	 */
	public String getFecFechaPeriodoIni() {
		return fecFechaPeriodoIni;
	}
	/**
	 * @param fecFechaPeriodoIni the fecFechaPeriodoIni to set
	 */
	public void setFecFechaPeriodoIni(String fecFechaPeriodoIni) {
		this.fecFechaPeriodoIni = fecFechaPeriodoIni;
	}
	/**
	 * @return the fecFechaPeriodoFin
	 */
	public String getFecFechaPeriodoFin() {
		return fecFechaPeriodoFin;
	}
	/**
	 * @param fecFechaPeriodoFin the fecFechaPeriodoFin to set
	 */
	public void setFecFechaPeriodoFin(String fecFechaPeriodoFin) {
		this.fecFechaPeriodoFin = fecFechaPeriodoFin;
	}
	/**
	 * @return the regPatronal
	 */
	public String getRegPatronal() {
		return regPatronal;
	}
	/**
	 * @param regPatronal the regPatronal to set
	 */
	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}
	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * @return the regObra
	 */
	public String getRegObra() {
		return regObra;
	}
	/**
	 * @param regObra the regObra to set
	 */
	public void setRegObra(String regObra) {
		this.regObra = regObra;
	}
	/**
	 * @return the cveSolCorr
	 */
	public Integer getCveSolCorr() {
		return cveSolCorr;
	}
	/**
	 * @param cveSolCorr the cveSolCorr to set
	 */
	public void setCveSolCorr(Integer cveSolCorr) {
		this.cveSolCorr = cveSolCorr;
	}
	/**
	 * Retorna el valor user
	 * @return  user
	 */
	public UserSession getUser() {
		return user;
	}
	/**
	 * Asigna el valor del user al atributo user
	 * @param user 
	 */
	public void setUser(UserSession user) {
		this.user = user;
	}
	/**
	 * Retorna el valor cveIdUsuarioCorr
	 * @return  cveIdUsuarioCorr
	 */
//	public Long getCveIdUsuarioCorr() {
//		return cveIdUsuarioCorr;
//	}
//	/**
//	 * Asigna el valor del cveIdUsuarioCorr al atributo cveIdUsuarioCorr
//	 * @param cveIdUsuarioCorr 
//	 */
//	public void setCveIdUsuarioCorr(Long cveIdUsuarioCorr) {
//		this.cveIdUsuarioCorr = cveIdUsuarioCorr;
//	}
	
	
//	/**
//	 * Retorna el valor cveAuditorAsignado
//	 * @return  cveAuditorAsignado
//	 */
//	public Long getCveAuditorAsignado() {
//		return cveAuditorAsignado;
//	}
//	/**
//	 * Asigna el valor del cveAuditorAsignado al atributo cveAuditorAsignado
//	 * @param cveAuditorAsignado 
//	 */
//	public void setCveAuditorAsignado(Long cveAuditorAsignado) {
//		this.cveAuditorAsignado = cveAuditorAsignado;
//	}
	
	/**
	 * Retorna el valor cveAuditorAsignado
	 * @return  cveAuditorAsignado
	 */
	public String getCveAuditorAsignado() {
		return cveAuditorAsignado;
	}
	public String getCveIdUsuarioCorr() {
		return cveIdUsuarioCorr;
	}
	public void setCveIdUsuarioCorr(String cveIdUsuarioCorr) {
		this.cveIdUsuarioCorr = cveIdUsuarioCorr;
	}
	/**
	 * Asigna el valor del cveAuditorAsignado al atributo cveAuditorAsignado
	 * @param cveAuditorAsignado 
	 */
	public void setCveAuditorAsignado(String cveAuditorAsignado) {
		this.cveAuditorAsignado = cveAuditorAsignado;
	}
	
	/**
	 * Retorna el valor recepcionVO
	 * @return  recepcionVO
	 */
	public RecepcionSeguimientoVO getRecepcionVO() {
		return recepcionVO;
	}
	/**
	 * Asigna el valor del recepcionVO al atributo recepcionVO
	 * @param recepcionVO 
	 */
	public void setRecepcionVO(RecepcionSeguimientoVO recepcionVO) {
		this.recepcionVO = recepcionVO;
	}
	
	
	/**
	 * Retorna el valor cvePresentaCorr
	 * @return  cvePresentaCorr
	 */
	public Integer getCvePresentaCorr() {
		return cvePresentaCorr;
	}
	/**
	 * Asigna el valor del cvePresentaCorr al atributo cvePresentaCorr
	 * @param cvePresentaCorr 
	 */
	public void setCvePresentaCorr(Integer cvePresentaCorr) {
		this.cvePresentaCorr = cvePresentaCorr;
	}
	/**
	 * Retorna el valor cveTipoCorreccion
	 * @return  cveTipoCorreccion
	 */
	public Integer getCveTipoCorreccion() {
		return cveTipoCorreccion;
	}
	/**
	 * Asigna el valor del cveTipoCorreccion al atributo cveTipoCorreccion
	 * @param cveTipoCorreccion 
	 */
	public void setCveTipoCorreccion(Integer cveTipoCorreccion) {
		this.cveTipoCorreccion = cveTipoCorreccion;
	}
	
	
	/**
	 * @return the cedulaRevisionAudVO
	 */
	public CedulaRevisionAudVO getCedulaRevisionAudVO() {
		return cedulaRevisionAudVO;
	}
	/**
	 * @param cedulaRevisionAudVO the cedulaRevisionAudVO to set
	 */
	public void setCedulaRevisionAudVO(CedulaRevisionAudVO cedulaRevisionAudVO) {
		this.cedulaRevisionAudVO = cedulaRevisionAudVO;
	}
	/**
	 * @return the cveEjercicio
	 */
	public Long getCveEjercicio() {
		return cveEjercicio;
	}
	/**
	 * @param cveEjercicio the cveEjercicio to set
	 */
	public void setCveEjercicio(Long cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}
	public DevSubDelegacionSeguimientoCorreccionVO getDerivSubDelTabVO() {
		return derivSubDelTabVO;
	}
	public void setDerivSubDelTabVO(DevSubDelegacionSeguimientoCorreccionVO derivSubDelTabVO) {
		this.derivSubDelTabVO = derivSubDelTabVO;
	}
	public String getNombreFuncionario() {
		return nombreFuncionario;
	}
	public void setNombreFuncionario(String nombreFuncionario) {
		this.nombreFuncionario = nombreFuncionario;
	}
	public RevOficiosVO getDerivDictamenTabVO() {
		return derivDictamenTabVO;
	}
	public void setDerivDictamenTabVO(RevOficiosVO derivDictamenTabVO) {
		this.derivDictamenTabVO = derivDictamenTabVO;
	}
	public DerivacionFiscalSeguimientoCorrVO getDerivFiscalTabVO() {
		return derivFiscalTabVO;
	}
	public void setDerivFiscalTabVO(DerivacionFiscalSeguimientoCorrVO derivFiscalTabVO) {
		this.derivFiscalTabVO = derivFiscalTabVO;
	}
	public DerivacionFiscalSeguimientoCorrVO getReactivacionTabVO() {
		return reactivacionTabVO;
	}
	public void setReactivacionTabVO(DerivacionFiscalSeguimientoCorrVO reactivacionTabVO) {
		this.reactivacionTabVO = reactivacionTabVO;
	}
	
	/**
	 * @return the revOficiosVO
	 */
	public RevOficiosVO getRevOficiosVO() {
		return revOficiosVO;
	}
	/**
	 * @param revOficiosVO the revOficiosVO to set
	 */
	public void setRevOficiosVO(RevOficiosVO revOficiosVO) {
		this.revOficiosVO = revOficiosVO;
	}
	public String getCveAnexoSolCorr() {
		return cveAnexoSolCorr;
	}
	public void setCveAnexoSolCorr(String cveAnexoSolCorr) {
		this.cveAnexoSolCorr = cveAnexoSolCorr;
	}
	public long getCveRecepcion() {
		return cveRecepcion;
	}
	public void setCveRecepcion(long cveRecepcion) {
		this.cveRecepcion = cveRecepcion;
	}
	/**
	 * Retorna el valor banderaTipoPago
	 * @return  banderaTipoPago
	 */
	public Integer getBanderaTipoPago() {
		return banderaTipoPago;
	}
	/**
	 * Asigna el valor del banderaTipoPago al atributo banderaTipoPago
	 * @param banderaTipoPago 
	 */
	public void setBanderaTipoPago(Integer banderaTipoPago) {
		this.banderaTipoPago = banderaTipoPago;
	}
	/**
	 * Retorna el valor fecElaboraPre
	 * @return  fecElaboraPre
	 */
	public String getFecElaboraPre() {
		return fecElaboraPre;
	}
	/**
	 * Asigna el valor del fecElaboraPre al atributo fecElaboraPre
	 * @param fecElaboraPre 
	 */
	public void setFecElaboraPre(String fecElaboraPre) {
		this.fecElaboraPre = fecElaboraPre;
	}
	
	public Integer getCveStatusRecepcion() {
		return cveStatusRecepcion;
	}
	public void setCveStatusRecepcion(Integer cveStatusRecepcion) {
		this.cveStatusRecepcion = cveStatusRecepcion;
	}
	/**
	 * Retorna el valor fecElaboraSolCorr
	 * @return  fecElaboraSolCorr
	 */
	public String getFecElaboraSolCorr() {
		return fecElaboraSolCorr;
	}
	/**
	 * Asigna el valor del fecElaboraSolCorr al atributo fecElaboraSolCorr
	 * @param fecElaboraSolCorr 
	 */
	public void setFecElaboraSolCorr(String fecElaboraSolCorr) {
		this.fecElaboraSolCorr = fecElaboraSolCorr;
	}
	
	
}
