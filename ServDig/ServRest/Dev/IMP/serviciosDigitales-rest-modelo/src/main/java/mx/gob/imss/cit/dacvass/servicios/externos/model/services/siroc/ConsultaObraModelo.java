package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConsultaObraModelo extends ObraGeneralExtPrto implements
		Serializable {

	private static final long serialVersionUID = 1159701182023313710L;


	private String cveEntidad;

	private String curp;
	
	private String refObservacion;
	
	private String refObservacion1;
	
	private String deClasificacionObra;
	
	private BigDecimal numeroExteriorO;
	
	private BigDecimal impObra;
	
	private BigDecimal refSupConstruccion;
	
	private String periodoConstruccion;
	
	private String  numProcedimiento;
	
	private BigDecimal numActualiza;
	
	private String numRegStps;
	
	private String refObjcontSubesp;
	
	private BigDecimal numAproxTrabajadores;
	
	private String avisoBloqueo;
	
	private String refOtroObjetoContrato;
	
	private String desEstatusObra;
	
	private BigDecimal idInformacionIncidencia;

	private Date fecRegistroAlta;
	
	private Date fecRegistroActualizado;
	
	private Date fecSuspension;
	
	private Date fecReanudacion;
	

	public String getCveEntidad() {
		return cveEntidad;
	}

	public void setCveEntidad(String cveEntidad) {
		this.cveEntidad = cveEntidad;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getRefObservacion() {
		return refObservacion;
	}

	public void setRefObservacion(String refObservacion) {
		this.refObservacion = refObservacion;
	}

	public String getDeClasificacionObra() {
		return deClasificacionObra;
	}

	public void setDeClasificacionObra(String deClasificacionObra) {
		this.deClasificacionObra = deClasificacionObra;
	}

	public BigDecimal getImpObra() {
		return impObra;
	}

	public void setImpObra(BigDecimal impObra) {
		this.impObra = impObra;
	}

	public BigDecimal getRefSupConstruccion() {
		return refSupConstruccion;
	}

	public void setRefSupConstruccion(BigDecimal refSupConstruccion) {
		this.refSupConstruccion = refSupConstruccion;
	}

	public String getPeriodoConstruccion() {
		return periodoConstruccion;
	}

	public void setPeriodoConstruccion(String periodoConstruccion) {
		this.periodoConstruccion = periodoConstruccion;
	}

	public String getNumProcedimiento() {
		return numProcedimiento;
	}

	public void setNumProcedimiento(String numProcedimiento) {
		this.numProcedimiento = numProcedimiento;
	}

	public BigDecimal getNumActualiza() {
		return numActualiza;
	}

	public void setNumActualiza(BigDecimal numActualiza) {
		this.numActualiza = numActualiza;
	}

	public String getNumRegStps() {
		return numRegStps;
	}

	public void setNumRegStps(String numRegStps) {
		this.numRegStps = numRegStps;
	}

	public String getRefObjcontSubesp() {
		return refObjcontSubesp;
	}

	public void setRefObjcontSubesp(String refObjcontSubesp) {
		this.refObjcontSubesp = refObjcontSubesp;
	}

	public BigDecimal getNumAproxTrabajadores() {
		return numAproxTrabajadores;
	}

	public void setNumAproxTrabajadores(BigDecimal numAproxTrabajadores) {
		this.numAproxTrabajadores = numAproxTrabajadores;
	}

	public String getAvisoBloqueo() {
		return avisoBloqueo;
	}

	public void setAvisoBloqueo(String avisoBloqueo) {
		this.avisoBloqueo = avisoBloqueo;
	}

	public String getRefOtroObjetoContrato() {
		return refOtroObjetoContrato;
	}

	public void setRefOtroObjetoContrato(String refOtroObjetoContrato) {
		this.refOtroObjetoContrato = refOtroObjetoContrato;
	}

	public String getDesEstatusObra() {
		return desEstatusObra;
	}

	public void setDesEstatusObra(String desEstatusObra) {
		this.desEstatusObra = desEstatusObra;
	}

	public BigDecimal getIdInformacionIncidencia() {
		return idInformacionIncidencia;
	}

	public void setIdInformacionIncidencia(BigDecimal idInformacionIncidencia) {
		this.idInformacionIncidencia = idInformacionIncidencia;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public BigDecimal getNumeroExteriorO() {
		return numeroExteriorO;
	}

	public void setNumeroExteriorO(BigDecimal numeroExteriorO) {
		this.numeroExteriorO = numeroExteriorO;
	}

	public String getRefObservacion1() {
		return refObservacion1;
	}

	public void setRefObservacion1(String refObservacion1) {
		this.refObservacion1 = refObservacion1;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecSuspension() {
		return fecSuspension;
	}

	public void setFecSuspension(Date fecSuspension) {
		this.fecSuspension = fecSuspension;
	}

	public Date getFecReanudacion() {
		return fecReanudacion;
	}

	public void setFecReanudacion(Date fecReanudacion) {
		this.fecReanudacion = fecReanudacion;
	}
	

	

}
