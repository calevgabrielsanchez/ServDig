package mx.gob.imss.ctirss.correccion.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrtInvitacion;

@Entity
@Table(name="CRT_INVITACION")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrtInvitacion extends AbstractCrtInvitacion{
	
	@Transient
	public String fechaIncial;
	
	@Transient
	public String fechaFinal;
	
	@Transient
	public String folioPromocion;
	
	@Transient
	public String tipoPrograma;
	
	@Transient
	public String patron;
	
	@Transient
	public String regPatronal;
	
	@Transient
	public String fechaEmision;
	
	@Transient
	public String cveTemp;
	
	@Transient
	public String razonSocial;
	
	@Transient
	public String folioAntecedente;
	
	@Transient
	public String cveTipocorr;
	
	@Transient
	public String folioTemp;
	
	@Transient
	public String rarioRP;
	
	@Transient
	public Long cveFkPatron;
	
	@Transient
	public String cveFkPatronTemp;
	
	@Transient
	public String fechaOfInvitacionTx;
	
	@Transient
	public String funcionSeguimiento;

	@Transient
	public String domicilio;
	
	@Transient
	public String fechaNotOficioTx;
	
	@Transient
	public String fechaDerSubdelegacionTx;
	
	@Transient
	public String fechaCancelacionTx;
	
	@Transient
	public String fechaAisoDictamenTx;
	
	@Transient
	public String fechaPeriodoIniDicTx;
	
	@Transient
	public String fechaPeriodoFinDicTx;
	
	@Transient
	public String fechaPaiTx;
	
	@Transient
	public String rolUsuario;
	
	@Transient
	public String fechaCorreccionTx;
	
	@Transient
	public String fechaInicioCorreccionTx;
	
	@Transient
	public String fechaFinCorreccionTx;
	
	@Transient
	public String domicilioObra;
	
	
	@Transient
	public String nombreUsuario;
	
	
	
	@Transient
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	@Transient
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	@Transient
	public String getCveTipocorr() {
		return cveTipocorr;
	}
	
	@Transient
	public void setCveTipocorr(String cveTipocorr) {
		this.cveTipocorr = cveTipocorr;
	}

	@Transient
	public String getFolioTemp() {
		return folioTemp;
	}

	@Transient
	public void setFolioTemp(String folioTemp) {
		this.folioTemp = folioTemp;
	}

	@Transient
	public String getFolioAntecedente() {
		return folioAntecedente;
	}

	@Transient
	public void setFolioAntecedente(String folioAntecedente) {
		this.folioAntecedente = folioAntecedente;
	}

	@Transient
	public String getRazonSocial() {
		return razonSocial;
	}

	@Transient
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	@Transient
	public String getCveTemp() {
		return cveTemp;
	}

	@Transient
	public void setCveTemp(String cveTemp) {
		this.cveTemp = cveTemp;
	}
	
	@Transient
	public String fechaDeteccion;

	public CrtInvitacion(String parametro) {
		super();
		
	}

	public CrtInvitacion(BigDecimal cveInvitacion,
			BigDecimal cvePromocion, BigDecimal cveDeteccion,
			BigDecimal cveFkSubdelegacion, String nuOficioinv,
			Date fecFechaoficioinv, Date fecFechaemision, Date fecFechanotifi,
			Date fecFechareg, String cveUsuario, String nuFolioInvitacion) {
		super(cveInvitacion, cvePromocion, cveDeteccion, cveFkSubdelegacion,
				nuOficioinv, fecFechaoficioinv, fecFechaemision, fecFechanotifi,
				fecFechareg, cveUsuario, nuFolioInvitacion);
		// TODO Auto-generated constructor stub
	}

	public CrtInvitacion(BigDecimal cveInvitacion) {
		super(cveInvitacion);
		// TODO Auto-generated constructor stub
	}

	public CrtInvitacion() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor para Auditor
	 * @author Enrique Duran JImenez
	 * @since 25/05/2012
	 */
	public CrtInvitacion(BigDecimal cveInvitacion, String nuFolioInvitacion, SatPatron fkPatron,
			Date fecPeriodoIni, Date fecPeriodoFin, Date fecFechaemision) {
		super(cveInvitacion, nuFolioInvitacion, fkPatron, fecPeriodoIni, fecPeriodoFin, fecFechaemision);
	}
	
	
	@Transient
	public String getRegPatronal() {
		return regPatronal;
	}
	@Transient
	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}

	@Transient
	public String getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	@Transient
	public String getFechaIncial() {
		return fechaIncial;
	}

	public void setFechaIncial(String fechaIncial) {
		this.fechaIncial = fechaIncial;
	}

	@Transient
	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	@Transient
	public String getFolioPromocion() {
		return folioPromocion;
	}

	public void setFolioPromocion(String folioPromocion) {
		this.folioPromocion = folioPromocion;
	}

	@Transient
	public String getTipoPrograma() {
		return tipoPrograma;
	}

	public void setTipoPrograma(String tipoPrograma) {
		this.tipoPrograma = tipoPrograma;
	}

	@Transient
	public String getPatron() {
		return patron;
	}

	public void setPatron(String patron) {
		this.patron = patron;
	}

	/**
	 * @return the rarioRP
	 */
	@Transient
	public String getRarioRP() {
		return rarioRP;
	}

	/**
	 * @param rarioRP the rarioRP to set
	 */
	@Transient
	public void setRarioRP(String rarioRP) {
		this.rarioRP = rarioRP;
	}

	/**
	 * @return the cveFkPatron
	 */
	@Transient
	public Long getCveFkPatron() {
		return cveFkPatron;
	}

	/**
	 * @param cveFkPatron the cveFkPatron to set
	 */
	@Transient
	public void setCveFkPatron(Long cveFkPatron) {
		this.cveFkPatron = cveFkPatron;
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
	 * @return the fechaOfInvitacionTx
	 */
	@Transient
	public String getFechaOfInvitacionTx() {
		return fechaOfInvitacionTx;
	}

	/**
	 * @param fechaOfInvitacionTx the fechaOfInvitacionTx to set
	 */
	@Transient
	public void setFechaOfInvitacionTx(String fechaOfInvitacionTx) {
		this.fechaOfInvitacionTx = fechaOfInvitacionTx;
	}

	/**
	 * @return the funcionSeguimiento
	 */
	@Transient
	public String getFuncionSeguimiento() {
		return funcionSeguimiento;
	}

	/**
	 * @param funcionSeguimiento the funcionSeguimiento to set
	 */
	@Transient
	public void setFuncionSeguimiento(String funcionSeguimiento) {
		this.funcionSeguimiento = funcionSeguimiento;
	}
	
	@Transient
	public String getDomicilio() {
		return domicilio;
	}

	@Transient
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * @return the fechaNotOficioTx
	 */
	@Transient
	public String getFechaNotOficioTx() {
		return fechaNotOficioTx;
	}

	/**
	 * @param fechaNotOficioTx the fechaNotOficioTx to set
	 */
	@Transient
	public void setFechaNotOficioTx(String fechaNotOficioTx) {
		this.fechaNotOficioTx = fechaNotOficioTx;
	}

	/**
	 * @return the fechaDerSubdelegacionTx
	 */
	@Transient
	public String getFechaDerSubdelegacionTx() {
		return fechaDerSubdelegacionTx;
	}

	/**
	 * @param fechaDerSubdelegacionTx the fechaDerSubdelegacionTx to set
	 */
	@Transient
	public void setFechaDerSubdelegacionTx(String fechaDerSubdelegacionTx) {
		this.fechaDerSubdelegacionTx = fechaDerSubdelegacionTx;
	}

	/**
	 * @return the fechaCancelacionTx
	 */
	@Transient
	public String getFechaCancelacionTx() {
		return fechaCancelacionTx;
	}

	/**
	 * @param fechaCancelacionTx the fechaCancelacionTx to set
	 */
	@Transient
	public void setFechaCancelacionTx(String fechaCancelacionTx) {
		this.fechaCancelacionTx = fechaCancelacionTx;
	}

	/**
	 * @return the fechaAisoDictamenTX
	 */
	@Transient
	public String getFechaAisoDictamenTx() {
		return fechaAisoDictamenTx;
	}

	/**
	 * @param fechaAisoDictamenTX the fechaAisoDictamenTX to set
	 */
	@Transient
	public void setFechaAisoDictamenTx(String fechaAisoDictamenTx) {
		this.fechaAisoDictamenTx = fechaAisoDictamenTx;
	}

	/**
	 * @return the fechaPeriodoIniDiTXc
	 */
	@Transient
	public String getFechaPeriodoIniDicTx() {
		return fechaPeriodoIniDicTx;
	}

	/**
	 * @param fechaPeriodoIniDiTXc the fechaPeriodoIniDiTXc to set
	 */
	@Transient
	public void setFechaPeriodoIniDicTx(String fechaPeriodoIniDicTx) {
		this.fechaPeriodoIniDicTx = fechaPeriodoIniDicTx;
	}

	/**
	 * @return the fechaPeriodoFinDicTX
	 */
	@Transient
	public String getFechaPeriodoFinDicTx() {
		return fechaPeriodoFinDicTx;
	}

	/**
	 * @param fechaPeriodoFinDicTX the fechaPeriodoFinDicTX to set
	 */
	@Transient
	public void setFechaPeriodoFinDicTx(String fechaPeriodoFinDicTx) {
		this.fechaPeriodoFinDicTx = fechaPeriodoFinDicTx;
	}

	/**
	 * @return the fechaPaiTx
	 */
	@Transient
	public String getFechaPaiTx() {
		return fechaPaiTx;
	}
	/**
	 * @param fechaPaiTx the fechaPaiTx to set
	 */
	@Transient
	public void setFechaPaiTx(String fechaPaiTx) {
		this.fechaPaiTx = fechaPaiTx;
	}

	/**
	 * @return the rolUsuario
	 */
	@Transient
	public String getRolUsuario() {
		return rolUsuario;
	}
	/**
	 * @param rolUsuario the rolUsuario to set
	 */
	@Transient
	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}

	/**
	 * @return the fechaCorreccionTx
	 */
	@Transient
	public String getFechaCorreccionTx() {
		return fechaCorreccionTx;
	}
	/**
	 * @param fechaCorreccionTx the fechaCorreccionTx to set
	 */
	@Transient
	public void setFechaCorreccionTx(String fechaCorreccionTx) {
		this.fechaCorreccionTx = fechaCorreccionTx;
	}

	/**
	 * @return the fechaInicioCorreccionTx
	 */
	@Transient
	public String getFechaInicioCorreccionTx() {
		return fechaInicioCorreccionTx;
	}
	/**
	 * @param fechaInicioCorreccionTx the fechaInicioCorreccionTx to set
	 */
	@Transient
	public void setFechaInicioCorreccionTx(String fechaInicioCorreccionTx) {
		this.fechaInicioCorreccionTx = fechaInicioCorreccionTx;
	}

	/**
	 * @return the fechaFinCorreccionTx
	 */
	@Transient
	public String getFechaFinCorreccionTx() {
		return fechaFinCorreccionTx;
	}
	/**
	 * @param fechaFinCorreccionTx the fechaFinCorreccionTx to set
	 */
	@Transient
	public void setFechaFinCorreccionTx(String fechaFinCorreccionTx) {
		this.fechaFinCorreccionTx = fechaFinCorreccionTx;
	}
	
	@Transient
	public void setDomicilioObra(String domicilioObra){
		this.domicilioObra = domicilioObra;
	}
	
	@Transient
	public String getDomicilioObra(){
		return this.domicilioObra;
	}
	
	@Transient
	public String getFechaDeteccion() {
		return fechaDeteccion;
	}
	
	@Transient
	public void setFechaDeteccion(String fechaDeteccion) {
		this.fechaDeteccion = fechaDeteccion;
	}
	
}
