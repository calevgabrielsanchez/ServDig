package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.math.BigDecimal;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Clasificacion extends AbstractModel {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 154009046915199848L;
	/**
	 * 
	 * @author Hugo Armando Mart-nez Cham-nica
	 * 
	 */
	private Long id;
	private Date fecPresentacion;
	private Date fecEfecto;
	private Integer indTransportePropio;
	private Integer indTransporteAjeno;
	private Integer indDistribuyeEntrega;
	private Integer indServiciosATerceros;
	private Integer indPrestaServicioPersonal;
	private Integer indRegPatClase;
	private String giro;
	private Long cveIdFraccionClase;
	private BigDecimal numCentrosTraba;
	private Fraccion fraccion;
	private Integer indProductorCana;
	
	private SujetoObligado sujetoObligado;
	
	private String msgError;
	
	private BigDecimal primaSRTActual;
	private BigDecimal primaSRTFusionSust;
	private BigDecimal primaSRTSugerida;
	private BigDecimal primaSugerida;
	private String indPrimaSugerida;
	private String indSolSimilares;
	private String indBaja;
	private Boolean auditoria;
	private Integer indRegPatAmparo;
	
	
	//valores para guardar los datos capturados para el calculo de la prima en el tramite de fusión WO436058
	private CalculoPrimaMovPat calculoPrimaMovPat;

	public String getIndBaja() {
		return indBaja;
	}

	public void setIndBaja(String indBaja) {
		this.indBaja = indBaja;
	}

	public String getIndSolSimilares() {
		return indSolSimilares;
	}

	public void setIndSolSimilares(String indSolSimilares) {
		this.indSolSimilares = indSolSimilares;
	}

	public BigDecimal getPrimaSRTSugerida() {
		return primaSRTSugerida;
	}

	public void setPrimaSRTSugerida(BigDecimal primaSRTSugerida) {
		this.primaSRTSugerida = primaSRTSugerida;
	}

	public String getIndPrimaSugerida() {
		return indPrimaSugerida;
	}

	public void setIndPrimaSugerida(String indPrimaSugerida) {
		this.indPrimaSugerida = indPrimaSugerida;
	}

	public Integer getIndRegPatAmparo() {
		return indRegPatAmparo;
	}

	public void setIndRegPatAmparo(Integer indRegPatAmparo) {
		this.indRegPatAmparo = indRegPatAmparo;
	}

	public BigDecimal getPrimaSRTFusionSust() {
		return primaSRTFusionSust;
	}

	public void setPrimaSRTFusionSust(BigDecimal primaSRTFusionSust) {
		this.primaSRTFusionSust = primaSRTFusionSust;
	}

	public Boolean getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Boolean auditoria) {
		this.auditoria = auditoria;
	}

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the fecPresentacion
	 */
	public Date getFecPresentacion() {
		return fecPresentacion;
	}

	/**
	 * @param fecPresentacion the fecPresentacion to set
	 */
	public void setFecPresentacion(Date fecPresentacion) {
		this.fecPresentacion = fecPresentacion;
	}
	
	/**
	 * @return the fecEfecto
	 */
	public Date getFecEfecto() {
		return fecEfecto;
	}

	/**
	 * @param fecEfecto the fecEfecto to set
	 */
	public void setFecEfecto(Date fecEfecto) {
		this.fecEfecto = fecEfecto;
	}

	/**
	 * @return the indTransportePropio
	 */
	public Integer getIndTransportePropio() {
		return indTransportePropio;
	}

	/**
	 * @param indTransportePropio the indTransportePropio to set
	 */
	public void setIndTransportePropio(Integer indTransportePropio) {
		this.indTransportePropio = indTransportePropio;
	}

	/**
	 * @return the indTransporteAjeno
	 */
	public Integer getIndTransporteAjeno() {
		return indTransporteAjeno;
	}

	/**
	 * @param indTransporteAjeno the indTransporteAjeno to set
	 */
	public void setIndTransporteAjeno(Integer indTransporteAjeno) {
		this.indTransporteAjeno = indTransporteAjeno;
	}

	/**
	 * @return the indDistribuyeEntrega
	 */
	public Integer getIndDistribuyeEntrega() {
		return indDistribuyeEntrega;
	}

	/**
	 * @param indDistribuyeEntrega the indDistribuyeEntrega to set
	 */
	public void setIndDistribuyeEntrega(Integer indDistribuyeEntrega) {
		this.indDistribuyeEntrega = indDistribuyeEntrega;
	}

	/**
	 * @return the indServiciosATerceros
	 */
	public Integer getIndServiciosATerceros() {
		return indServiciosATerceros;
	}

	/**
	 * @param indServiciosATerceros the indServiciosATerceros to set
	 */
	public void setIndServiciosATerceros(Integer indServiciosATerceros) {
		this.indServiciosATerceros = indServiciosATerceros;
	}

	/**
	 * @return the indPrestaServicioPersonal
	 */
	public Integer getIndPrestaServicioPersonal() {
		return indPrestaServicioPersonal;
	}

	/**
	 * @param indPrestaServicioPersonal the indPrestaServicioPersonal to set
	 */
	public void setIndPrestaServicioPersonal(Integer indPrestaServicioPersonal) {
		this.indPrestaServicioPersonal = indPrestaServicioPersonal;
	}

	/**
	 * @return the indRegPatClase
	 */
	public Integer getIndRegPatClase() {
		return indRegPatClase;
	}

	/**
	 * @param indRegPatClase the indRegPatClase to set
	 */
	public void setIndRegPatClase(Integer indRegPatClase) {
		this.indRegPatClase = indRegPatClase;
	}

	/**
	 * @return the giro
	 */
	public String getGiro() {
		return giro;
	}

	/**
	 * @param giro the giro to set
	 */
	public void setGiro(String giro) {
		this.giro = giro;
	}

	/**
	 * @return the fraccion
	 */
	public Fraccion getFraccion() {
		return fraccion;
	}

	/**
	 * @param fraccion the fraccion to set
	 */
	public void setFraccion(Fraccion fraccion) {
		this.fraccion = fraccion;
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
	
	public Long getCveIdFraccionClase() {
		return cveIdFraccionClase;
	}

	public void setCveIdFraccionClase(Long cveIdFraccionClase) {
		this.cveIdFraccionClase = cveIdFraccionClase;
	}
	
	
	public BigDecimal getPrimaSRTActual() {
		return primaSRTActual;
	}

	public void setPrimaSRTActual(BigDecimal primaSRTActual) {
		this.primaSRTActual = primaSRTActual;
	}

	public BigDecimal getNumCentrosTraba() {
		return numCentrosTraba;
	}

	public void setNumCentrosTraba(BigDecimal numCentrosTraba) {
		this.numCentrosTraba = numCentrosTraba;
	}
	
	public Integer getIndProductorCana() {
		return indProductorCana;
	}

	public void setIndProductorCana(Integer indProductoCana) {
		this.indProductorCana = indProductoCana;
	}

	/**
	 * @return the primaSugerida
	 */
	public BigDecimal getPrimaSugerida() {
		return primaSugerida;
	}

	/**
	 * @param primaSugerida the primaSugerida to set
	 */
	public void setPrimaSugerida(BigDecimal primaSugerida) {
		this.primaSugerida = primaSugerida;
	}
	
	
	public CalculoPrimaMovPat getCalculoPrimaMovPat() {
		return calculoPrimaMovPat;
	}

	public void setCalculoPrimaMovPat(CalculoPrimaMovPat calculoPrimaMovPat) {
		this.calculoPrimaMovPat = calculoPrimaMovPat;
	}
	

	@Override
	public String toString() {
		return "Clasificacion [id=" + id + ", fecPresentacion=" + fecPresentacion + ", fecEfecto=" + fecEfecto
				+ ", indTransportePropio=" + indTransportePropio + ", indTransporteAjeno=" + indTransporteAjeno
				+ ", indDistribuyeEntrega=" + indDistribuyeEntrega + ", indServiciosATerceros=" + indServiciosATerceros
				+ ", indPrestaServicioPersonal=" + indPrestaServicioPersonal + ", indRegPatClase=" + indRegPatClase
				+ ", giro=" + giro + ", cveIdFraccionClase=" + cveIdFraccionClase + ", numCentrosTraba="
				+ numCentrosTraba + ", fraccion=" + fraccion + ", indProductorCana=" + indProductorCana
				+ ", sujetoObligado=" + sujetoObligado + ", msgError=" + msgError + ", primaSRTActual=" + primaSRTActual
				+ ", primaSRTFusionSust=" + primaSRTFusionSust + ", primaSRTSugerida=" + primaSRTSugerida
				+ ", primaSugerida=" + primaSugerida + ", indPrimaSugerida=" + indPrimaSugerida + ", indSolSimilares="
				+ indSolSimilares + ", indBaja=" + indBaja + ", auditoria=" + auditoria + ", indRegPatAmparo="
				+ indRegPatAmparo + ", calculoPrimaMovPat=" + calculoPrimaMovPat + "]";
	}
	
}
