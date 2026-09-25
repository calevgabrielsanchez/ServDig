/**
 * Paquete que aplica para todos los eventos relacionados con el 
 * seguimiento del estudio de correccian. 
 */
package mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtCoppagada;
import mx.gob.imss.ctirss.correccion.model.CrtPresentacorr;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtRevPagos;

/**
 * Nos permite realizar las acciones ABC dentro de la tabla CRT_REVPAGOS.
 * Esta tabla es inicializada la primera vez que entra el auditor a generar
 * pagos o revisar los realizados por el patron generando una copia de
 * CRT_COPPAGADAS.<br><br>
 * 
 * Los pagos pueden ser realizados tomando en cuenta las siguientes 
 * combinaciones: <br>
 * 
 * PagosVO.TIPO_PAGO_COP = 1<br>
 * PagosVO.TIPO_PAGO_RCV = 2<br>
 * PagosVO.TIPO_PAGO_MA = 3<br>
 * PagosVO.TIPO_PAGO_COPRCV = 4<br>
 * PagosVO.TIPO_PAGO_COPMA = 5<br>
 * PagosVO.TIPO_PAGO_COPRCVMA = 6<br><br>
 * 
 * En caso de que no se utilice los pagos de alguna seccian estas entraran
 * a la base de datos como NULL. <br><br>
 * 
 * <b>
 * 	  Las operaciones de modificacian y borrado unicamente deberan
 *    estar disponibles para los perfiles superiores a auditor.
 * </b>
 * 
 * @see CrtRevPagos
 * @see CrtCoppagada
 * @see PagosVO
 * @version 1.1.0
 * @author Marco Antonio Nieto Plett
 */

@MappedSuperclass
public abstract class AbstractCrtRevPagos extends AbstractModel{
	
	/**
	 * Elemento de serializacian, no afecta en el proceso.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Consecutivo de la base de datos, secuencia generada
	 * a travaz del manejador de base de datos.<br><br>
	 * 
	 * Nombre de la secuencia asociada: SEQ_CVE_REVPAGOS
	 */
	@Id
	@SequenceGenerator(name="SEQ_CVE_REVPAGOS_GENERATOR", sequenceName="SEQ_CVE_REVPAGOS")
	@GeneratedValue(generator="SEQ_CVE_REVPAGOS_GENERATOR")
	@Column(name="CVE_REVPAGOS")
	private Integer cveRevpagos;
	
	/**
	 * Clave asignada al momento de realizar la presentacian
	 * de la correccian.
	 * @see CrtPresentacorr
	 * 
	 */
	@Column(name="CVE_PRESENTACORR")
	private Integer cvePresentacorr; 
	
	/**
	 * Clave asignada a cada registro patronal relacionado
	 * con una solicitud de correccian.
	 * @see CrtAnexosolcorrpat
	 */
	
	@Column(name="CVE_ANEXOSOLCORRPAT")
	private Integer cveAnexosolcorrpat;
	
	/**
	 * Periodo que involucra la presentacian/solicitud
	 * de la correccian.
	 * @see CrcEjercicio
	 */
	@Column(name="CVE_EJERCICIO")
	private Integer cveEjercicio;
	 
	/**
	 * Indica el mes y aao en el cual se registra el pago.
	 * Su formato es YYYYdd
	 */
	@Column(name="NUM_PERIODO")
	private Integer numPeriodo;
	 
	@Column(name="NUM_PERIODO_RCV")
	private Integer numPeriodoRCV;
	
	
	
	
	/**
	 * Suerte principal pagada de la COP
	 */
	@Column(name="IMP_COPSP")
	private BigDecimal impCopsp;
	
	/**
	 * Actualizacian pagada de la COP
	 */
	@Column(name="IMP_COPACT")
	private BigDecimal impCopact;
	
	/**
	 *Recargos pagados de la COP
	 */
	@Column(name="IMP_COPREC")
	private BigDecimal impCoprec;
	
	/**
	 * Importe total pagado y ya calculado
	 * de las COP
	 */
	@Column(name="IMP_COPTOT")
	private BigDecimal impCoptot;
	
	/**
	 * Multas pagadas en las COP
	 */
	@Column(name="IMP_COPMULTA")
	private BigDecimal impCopmulta;
	
	/**
	 * Suerte principal pagada del RCV
	 */
	@Column(name="IMP_RCVSP")
	private BigDecimal impRcvsp;
	
	/**
	 * Actualizacian pagada del RCV
	 */
	@Column(name="IMP_RCVACT")
	private BigDecimal impRcvact;
	
	/**
	 * Recargos pagados del RCV
	 */
	@Column(name="IMP_RCVREC")
	private BigDecimal impRcvrec;
	
	/**
	 * Importe total pagado y ya calculado
	 * de las RCV
	 */
	@Column(name="IMP_RCVTOT")
	private BigDecimal impRcvtot;
	
	/**
	 * Multas pagadas en el RCV
	 */
	@Column(name="IMP_RCVMULTA")
	private BigDecimal impRcvmulta;
		
	/**
	 * Trabajadores regularizados durante
	 * el proceso de presentacian de correccian.<br>
	 * <b>Este campo solo aplica en los movimientos
	 * afiliatorios.</b>
	 */
	@Column(name="NUM_TRABREGULA")
	private Integer numTrabregula;
	
	/**
	 * Altas generadas durante el proceso
	 * de presentacian de correccian.<br>
	 * <b>Este campo solo aplica en los movimientos
	 * afiliatorios.</b>
	 */
	@Column(name="NUM_ALTAS")
	private Integer numAltas;
	
	/**
	 * Bajas generadas durante el proceso
	 * de presentacian de correccian.<br>
	 * <b>Este campo solo aplica en los movimientos
	 * afiliatorios.</b>
	 */
	@Column(name="NUM_BAJAS")
	private Integer numBajas;
	
	/**
	 * Modificaciones a salario generadas durante el proceso
	 * de presentacian de correccian.<br>
	 * <b>Este campo solo aplica en los movimientos
	 * afiliatorios.</b>
	 */
	@Column(name="NUM_MODIFSALARIO")
	private Integer numModifsalario;
	
	/**
	 * Numero de referencia del pago. Excluyente
	 * con Orden de Ingreso.
	 */
	@Column(name="NUM_FOLIOSUA")
	private Integer numFoliosua;
	
	/**
	 * Numero de referencia del pago. Excluyente 
	 * con Folio SUA.
	 */
	@Column(name="NUM_ORDENINGRESO")
	private String numOrdeningreso;
	
	/**
	 * Namero de cradito asignado. Aplica unicamente 
	 * con referencia de orden de ingreso.
	 */
	@Column(name="NUM_CREDITO")
	private String numCredito;
	
	/**
	 * Fecha calendario DD-MM/YYYY la cual
	 * indica cuando se realiza el pago.
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAPAGO")
	private Date fecFechapago; 
	
	/**
	 * Tipo de documento seleccionado por el auditor.<br>
	 * Los tipos disponibles son 53 y 58.
	 */
	@Column(name="ID_TIPODOCTO")
	private Integer idTipodocto;
	
	/**
	 * Identifica si el pago proviene de la cadula
	 * de revisian o de validacian.<br>
	 * 1: Revisian <br>
	 * 2: Validacian
	 * @see CrtRevPagos
	 */
	@Column(name="IND_TIPOPAGO")
	private Integer indTipopago;
	
	/**
	 * Campo de auditoria. Fecha de ingreso o
	 * modificacian del registro.
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg; 
	
	/**
	 * Campo de auditoria.Clave ID del usuario
	 * que ingresa o modifica el registro.
	 */
	@Column(name="CVE_USUARIO")
	private String cveUsuario;
	
	
	@Column(name="FLAG_COB_PAGOS")
	private String flagCobPagos;
	
	/**
	 * Indicador PK de la tabla CRT_RegulaPagos (Fase I).
	 * En caso de que exista este campo no existira:
	 *    - CVE_PRESENTACIONCORR
	 *    - CVE_ANEXOSOLCORRPAT
	 */
	@Column(name="CVE_REGULAPAGOS")
	private Integer cveRegulaPagos;
	
	/**
	 * Retorna el valor consecutivo ID
	 * referenciado al pago realizado.
	 * 
	 * @author Marco A Nieto Plett
	 * @return Namero mayor que cero
	 */
	public Integer getCveRevpagos() {
		return cveRevpagos;
	}

	/**
	 * Permite ingresar el valor consecutivo ID
	 * referenciado al pago.
	 * @author Marco A Nieto Plett
	 * @param cveRevpagos
	 */
	public void setCveRevpagos(Integer cveRevpagos) {
		this.cveRevpagos = cveRevpagos;
	}

	/**
	 * Permite recuperar el ID asignado a la correccian
	 * durante el proceso de presentacian.
	 * @author Marco Antonio Nieto Plett
	 * @see CrtPresentacorr
	 * @return Namero mayor que cero
	 */
	public Integer getCvePresentacorr() {
		return cvePresentacorr;
	}

	/**
	 * Permite ingresar el ID asignado al momento
	 * de realizar la presentacian de la correccian.
	 * @author Marco Antonio Nieto Plett
	 * @param cvePresentacorr
	 * @see CrtPresentacorr
	 */
	public void setCvePresentacorr(Integer cvePresentacorr) {
		this.cvePresentacorr = cvePresentacorr;
	}

	/**
	 * Otorga el ID asociado a cada registro patronal
	 * asociado a una solicitud de correccian.
	 * @author Marco Antonio Nieto Plett
	 * @see CrtAnexosolcorrpat
	 * @return Namero mayor a cero
	 */
	public Integer getCveAnexosolcorrpat() {
		return cveAnexosolcorrpat;
	}

	/**
	 * Permite recuperar el ID asociado al registro patronal
	 * el cual tiene relacionado la solicitud de la correccian.
	 * @author Marco Antonio Nieto Plett
	 * @see CrtAnexosolcorrpat
	 * @param cveAnexosolcorrpat
	 */
	public void setCveAnexosolcorrpat(Integer cveAnexosolcorrpat) {
		this.cveAnexosolcorrpat = cveAnexosolcorrpat;
	}

	/**
	 * Otorga el periodo (YYYY) asociado a la solicitud
	 * de la correccian.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @see CrcEjercicio
	 * @return Aao en formato (YYYY), no puede ser null.
	 */
	public Integer getCveEjercicio() {
		return cveEjercicio;
	}

	/**
	 * Permite ingresar la clave (Aao) asociado al
	 * registro patronal.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @see CrcEjercicio
	 * @param cveEjercicio ( No Null)
	 */
	public void setCveEjercicio(Integer cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}

	/**
	 * Otorga el periodo al cual aplica el pago realizado.
	 * No puede ser NULL.
	 * @author Marco Antonio Nieto Plett
	 * @return Namero mayor que cero en formato (YYYYMM)
	 */
	public Integer getNumPeriodo() {
		return numPeriodo;
	}

	/**
	 * Permite ingresar el periodo en el cual se realiza
	 * un pago. Su formato es YYYYMM.
	 * @author Marco Antonio Nieto Plett
	 * @param numPeriodo ( Diferente de NULL)
	 */ 
	public void setNumPeriodo(Integer numPeriodo) {
		this.numPeriodo = numPeriodo;
	}

	/**
	 * Importe correspondiente a la suerte principal
	 * de la COP. Este debe de ser mayor a 200 millones
	 * de pesos.
	 * @author Marco Antonio Nieto Plett
	 * @return Cantidad mayor que cero.
	 */
	public BigDecimal getImpCopsp() {
		return impCopsp;
	}

	/**
	 * Permite ingresar el importe de la suerte principal
	 * pagada de la COP.
	 * @author Marco Antonio Nieto Plett
	 * @param impCopsp ( Importe no mayor a 200 millones de pesos)
	 */
	public void setImpCopsp(BigDecimal impCopsp) {
		this.impCopsp = impCopsp;
	}

	/**
	 * Permite recuperar la actualizacian pagada
	 * en una COP.
	 * @author Marco Antonio Nieto Plett
	 * @return Null, Cero o Mayor que cero
	 */
	public BigDecimal getImpCopact() {
		return impCopact;
	}

	/**
	 * Permite ingresar la actualizacian pagada en una
	 * COP. Este puede ser null, cero o mayor que cero.
	 * @author Marco Antonio Nieto Plett
	 * @param impCopact
	 */
	public void setImpCopact(BigDecimal impCopact) {
		this.impCopact = impCopact;
	}

	/**
	 * Importe de recargos pagados en una COP.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Null, cero o mayor que cero.
	 */
	public BigDecimal getImpCoprec() {
		return impCoprec;
	}

	/**
	 * Permite ingresar un recargo al momento
	 * de pagar una COP.Este puede ser null, 
	 * cero o mayor que cero.
	 * @author Marco Antonio Nieto Plett
	 * @param impCoprec
	 */
	public void setImpCoprec(BigDecimal impCoprec) {
		this.impCoprec = impCoprec;
	}

	/**
	 * Permite obtener el total previamente calculado
	 * y almacenado en la base de datos o pantalla
	 * referente a las COP. Incluye la suma de
	 * la SP, ACT, REC y Multas.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Importe mayor que cero.
	 */
	public BigDecimal getImpCoptot() {
		return impCoptot;
	}

	/**
	 * Permite ingresar el total previamente calculado
	 * de los pagos realizados en una COP incluyendo
	 * la SP, ACT, REC y multas.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param impCoptot
	 */
	public void setImpCoptot(BigDecimal impCoptot) {
		this.impCoptot = impCoptot;
	}
	
	/**
	 * Otorga las multas generadas en un 
	 * pago de COP. 
	 * @author Marco Antonio Nieto Plett
	 * @return Null, cero o mayor que cero.
	 */
	public BigDecimal getImpCopmulta() {
		return impCopmulta;
	}

	/**
	 * Permite ingresar las multas generadas en una
	 * COP. Estas pueden ser null, cero o mayor que cero.
	 * @author Marco Antonio Nieto Plett
	 * @param impCopmulta
	 */
	public void setImpCopmulta(BigDecimal impCopmulta) {
		this.impCopmulta = impCopmulta;
	}

	/**
	 * Otorgan la suerte principal asignada al RCV.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Null, cero o mayor que cero.
	 */
	public BigDecimal getImpRcvsp() {
		return impRcvsp;
	}

	/**
	 * Permite ingresar la suerte principal asiganda
	 * al pago de RCV. Este puede ser null, cero
	 * o mayor que cero.
	 * @author Marco Antonio Nieto Plett
	 * @param impRcvsp
	 */
	public void setImpRcvsp(BigDecimal impRcvsp) {
		this.impRcvsp = impRcvsp;
	}

	/**
	 * Permite recuperar el importe asignado al pago
	 * del RCV.
	 * @author Marco Antonio Nieto Plett
	 * @return null, cero o mayor que cero.
	 */
	public BigDecimal getImpRcvact() {
		return impRcvact;
	}

	/**
	 * Permite ingresar el importe asociado a la
	 * actualizacian pagada en un RCV. Este puede ser
	 * Null, Cero o mayor que cero.
	 * @author Marco Antonio Nieto Plett
	 * @param impRcvact
	 */
	public void setImpRcvact(BigDecimal impRcvact) {
		this.impRcvact = impRcvact;
	}

	/**
	 * Permite recuperar los recargos asociados a un
	 * pago de RCV.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return null, cero o mayor que cero.
	 */
	public BigDecimal getImpRcvrec() {
		return impRcvrec;
	}

	/**
	 * Permite ingresar un importe asociado a recargos
	 * correspondientes a un pago de RCV.
	 * Este puede ser null, cero o mayor que cero.
	 * @author Marco Antonio Nieto Plett
	 * @param impRcvrec
	 */
	public void setImpRcvrec(BigDecimal impRcvrec) {
		this.impRcvrec = impRcvrec;
	}

	/**
	 * Otorga un total previamente calculado/almacenado
	 * de un pago RCV, este incluye la suerte principal,
	 * recargos, actualizaciones y multas.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return cero o mayor que cero
	 */
	public BigDecimal getImpRcvtot() {
		return impRcvtot;
	}

	/**
	 * Permite ingresar el importe total pagado en un RCV.
	 * Este es previamente calculado en pantalla o en base
	 * de datos. Incluye la ACT,REC,SP y Multas.
	 * 
	 * Los valores permitidos son: Null, cero o mayor que cero.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param impRcvtot 
	 */
	public void setImpRcvtot(BigDecimal impRcvtot) {
		this.impRcvtot = impRcvtot;
	}

	/**
	 * Permite Recuperar las multas asociadas al pago de un RCV.
	 * Los valores permitidos son: Null, cero o mayor que cero.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Null, cero o mayor que cero
	 */
	public BigDecimal getImpRcvmulta() {
		return impRcvmulta;
	}

	/**
	 * Permite ingresar las multas asociadas al pago de un RCV.
	 * @author Marco Antonio Nieto Plett
	 * @param impRcvmulta (Null, cero o mayor que cero)
	 */
	public void setImpRcvmulta(BigDecimal impRcvmulta) {
		this.impRcvmulta = impRcvmulta;
	}

	/**
	 * Permite recuperar los trabajadores regularizados en un
	 * pago de movimientos afiliatorios.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Numero null, cero o mayor que cero.
	 */
	public Integer getNumTrabregula() {
		return numTrabregula;
	}

	/**
	 * Permite ingresar los trabajadores regularizados durante
	 * un pago de movimientos afiliatorios.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param numTrabregula (null, cero o mayor que cero)
	 */
	public void setNumTrabregula(Integer numTrabregula) {
		this.numTrabregula = numTrabregula;
	}
	
	/**
	 * Permite recuperar el namero de altas de trabajadores
	 * durante un pago de movimientos afiliatorios.
	 * 	
	 * @author Marco Antonio Nieto Plett
	 * @return null, cero o mayor que cero.
	 */
	public Integer getNumAltas() {
		return numAltas;
	}

	/**
	 * Permite ingresar el namero de altas durante los pagos
	 * de movimientos afiliatorios.
	 * @author Marco Antonio Nieto Plett
	 * @param numAltas (Null, cero o mayor que cero)
	 */
	public void setNumAltas(Integer numAltas) {
		this.numAltas = numAltas;
	}

	/**
	 * Otorga el namero de bajas de trabajadores
	 * realizadas durante un pago de movimientos 
	 * afiliatorios.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Null, cero o mayor que cero.
	 */
	public Integer getNumBajas() {
		return numBajas;
	}

	/**
	 * Permite ingresar el namero de bajas de trabajadores
	 * involucradas en un pago de movimientos 
	 * afiliatorios.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * Los valores permitidos son: Null, cero o mayor que cero.
	 * 
	 * @param numBajas
	 */
	public void setNumBajas(Integer numBajas) {
		this.numBajas = numBajas;
	}

	/**
	 * Permite recuperar el namero de trabajadores
	 * que sufrieron modificaciones al salario.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Null, cero o mayor que cero.
	 */
	public Integer getNumModifsalario() {
		return numModifsalario;
	}

	/**
	 * Permite ingresar las modificaciones realizadas
	 * al salario de los trabajadores.
	 * 
	 * Los valores permitidos son: Null, cero o mayor que cero.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param numModifsalario
	 */
	public void setNumModifsalario(Integer numModifsalario) {
		this.numModifsalario = numModifsalario;
	}

	/**
	 * Permite obtener la referencia de pago de FOLIO SUA
	 * este atributo es excluyente con orden de ingreso; 
	 * uno de los dos debe de ser obligatorio.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return null o namero mayor que cero.
	 */
	public Integer getNumFoliosua() {
		return numFoliosua;
	}

	/**
	 * Permite ingresar la referencia de pago de FOLIO SUA
	 * este atributo es excluyente con orden de ingreso; 
	 * uno de los dos debe de ser obligatorio.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param numFoliosua (Null o mayor que cero)
	 */
	public void setNumFoliosua(Integer numFoliosua) {
		this.numFoliosua = numFoliosua;
	}

	/**
	 * Permite obtener la referencia de pago de Orden de Ingreso
	 * este atributo es excluyente con FOLIO SUA; 
	 * uno de los dos debe de ser obligatorio.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return null o cadena de texto
	 */
	public String getNumOrdeningreso() {
		return numOrdeningreso;
	}

	/**
	 * Permite ingresar la referencia de pago de Orden de Ingreso
	 * este atributo es excluyente con FOLIO SUA; 
	 * uno de los dos debe de ser obligatorio.
	 * 
	 * @author Marco Antonio Nieto Plett
	 *@param numOrdeningreso (null o cadena de texto)
	 */
	public void setNumOrdeningreso(String numOrdeningreso) {
		this.numOrdeningreso = numOrdeningreso;
	}

	/**
	 * Permite obtener el namero de cradito otorgado al
	 * momento de realizar un pago por orden de ingreso.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Null en caso de FOLIO SUA, Cadena de texto
	 * en cualquier otro caso.
	 */
	public String getNumCredito() {
		return numCredito;
	}

	/**
	 * Permite ingresar el namero de cradito otorgado al
	 * momento de realizar un pago por orden de ingreso.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param numCredito (Null en caso de pago por FOLIO SUA
	 * cadena de texto en cualquier otro caso)
	 */
	public void setNumCredito(String numCredito) {
		this.numCredito = numCredito;
	}

	/**
	 * Permite obtener la fecha real en la que se realizar el pago.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return YYYY-MM-DD
	 */
	public Date getFecFechapago() {
		return fecFechapago;
	}

	/**
	 * Permite ingresar la fecha real en la que se realiza el pago.
	 * @author Marco Antonio Nieto Plett
	 * @param fecFechapago
	 */
	public void setFecFechapago(Date fecFechapago) {
		this.fecFechapago = fecFechapago;
	}

	/**
	 * Permite recuperar el tipo de documento que afecta al pago.
	 * @author Marco Antonio Nieto Plett
	 * @return Namero 53 o 58.
	 */
	public Integer getIdTipodocto() {
		return idTipodocto;
	}
	
	/**
	 * Permite ingresar el tipo de documento que afecta el pago.
	 * @author Marco Antonio Nieto Plett 
	 * @param idTipodocto (53 o 58)
	 */
	public void setIdTipodocto(Integer idTipodocto) {
		this.idTipodocto = idTipodocto;
	}

	/**
	 * Permite obtener que tipo de pago se esta realizando.<br><br>
	 * 1: Cadula de Revisian <br>
	 * 2: Cadula de Validacian
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @see CrtRevPagos
	 * @return tipo de pago
	 */
	public Integer getIndTipopago() {
		return indTipopago;
	}

	/**
	 * Permite Ingresar que tipo de pago se esta realizando.<br><br>
	 * 1: Cadula de Revisian <br>
	 * 2: Cadula de Validacian
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @see CrtRevPagos
	 * @param indTipoPago
	 */
	public void setIndTipopago(Integer indTipopago) {
		this.indTipopago = indTipopago;
	}

	/**
	 * Permite recuperar la fecha en la cual se modifica
	 * por ultima vez el registro.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return YYYY-MM-DD
	 */
	public Date getFecFechareg() {
		return fecFechareg;
	}

	/**
	 * Permite ingresar la fecha en la cual esta
	 * sufriendo modificacian el registro.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param fecFechareg
	 */
	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	/**
	 * Permite recuperar la clave del usuario que
	 * genera una insercian o actualizacian
	 * en el registro.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return ID del usuario que afecto el regristro.
	 */
	public String getCveUsuario() {
		return cveUsuario;
	}

	/**
	 * Permite ingresar la clave del usuario que
	 * genera una insercian o actualizacian
	 * en el registro.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param cveUsuario
	 */
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	/**
	 * Permite Obtener la llave primaria que 
	 * proviene de la tabla CRT_REGULAPAGOS.
	 * 
	 * @return null en caso de que no sea
	 *         pagos de regula pagos.
	 */
	public Integer getCveRegulaPagos() {
		return cveRegulaPagos;
	}

	/**
	 * Permite ingrsar la llave primaria que
	 * afecta a un pago de fase II.
	 * 
	 * @param cveRegulaPagos
	 */
	public void setCveRegulaPagos(Integer cveRegulaPagos) {
		this.cveRegulaPagos = cveRegulaPagos;
	}

	public String getFlagCobPagos() {
		return flagCobPagos;
	}

	public void setFlagCobPagos(String flagCobPagos) {
		this.flagCobPagos = flagCobPagos;
	}

	public Integer getNumPeriodoRCV() {
		return numPeriodoRCV;
	}

	public void setNumPeriodoRCV(Integer numPeriodoRCV) {
		this.numPeriodoRCV = numPeriodoRCV;
	}

	
}
