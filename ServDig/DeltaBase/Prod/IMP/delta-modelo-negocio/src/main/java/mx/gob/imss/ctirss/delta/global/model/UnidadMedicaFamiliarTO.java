package mx.gob.imss.ctirss.delta.global.model;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ClavePresupuestal;
import mx.gob.imss.ctirss.delta.model.derechohabiente.NivelAtencion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoUMF;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;

public class UnidadMedicaFamiliarTO extends AbstractModel {
	
	private static final long serialVersionUID = -1L;
	
	protected Subdelegacion subdelegacion;
	protected TipoUMF tipoUMF;
	protected ClavePresupuestal clavePresupuestal;
	protected NivelAtencion nivelAtencion;
	protected Long idUMF;
	protected String descripcion;
	protected BigDecimal generacionCita;
	protected BigDecimal noConsultorio;
	protected String nombreCorto;
	protected BigDecimal noEconomico;
	protected String desDireccion;
	
	
	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}
	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	public TipoUMF getTipoUMF() {
		return tipoUMF;
	}
	public void setTipoUMF(TipoUMF tipoUMF) {
		this.tipoUMF = tipoUMF;
	}
	public ClavePresupuestal getClavePresupuestal() {
		return clavePresupuestal;
	}
	public void setClavePresupuestal(ClavePresupuestal clavePresupuestal) {
		this.clavePresupuestal = clavePresupuestal;
	}
	public NivelAtencion getNivelAtencion() {
		return nivelAtencion;
	}
	public void setNivelAtencion(NivelAtencion nivelAtencion) {
		this.nivelAtencion = nivelAtencion;
	}
	public Long getIdUMF() {
		return idUMF;
	}
	public void setIdUMF(Long idUMF) {
		this.idUMF = idUMF;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getGeneracionCita() {
		return generacionCita;
	}
	public void setGeneracionCita(BigDecimal generacionCita) {
		this.generacionCita = generacionCita;
	}
	public BigDecimal getNoConsultorio() {
		return noConsultorio;
	}
	public void setNoConsultorio(BigDecimal noConsultorio) {
		this.noConsultorio = noConsultorio;
	}
	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	public BigDecimal getNoEconomico() {
		return noEconomico;
	}
	public void setNoEconomico(BigDecimal noEconomico) {
		this.noEconomico = noEconomico;
	}
	public String getDesDireccion() {
		return desDireccion;
	}
	public void setDesDireccion(String desDireccion) {
		this.desDireccion = desDireccion;
	}


}
