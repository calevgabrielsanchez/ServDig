package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import mx.gob.imss.ctirss.correccion.model.CrtRevDerivAFisca;
import mx.gob.imss.ctirss.correccion.model.CrtRevDerivASubd;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudOficios;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

public class OperacionCorreccionVO {

	
	private String usuario;
	private boolean puedeReactivar;
	private boolean finalizaProceso;
	private boolean  presentada;
	private boolean  existe;
	
	private CrtSolicitudcorr solicitud;
	private CrtRevDerivASubd derivaSubdelegacion;
	private Domicilio domicilioSubdelegacion;
	private CrtRevDerivAFisca derivaFiscalizacion;
	
	private CrtSolicitudOficios derivacionDicatmen;
	private CrtSolicitudOficios cancelacion;
	private String numeroFolio;
	private String estado;
	private String mensaje;
	private String tipoOperacion;
	
	//Deriva a subdelegacion
	private String cveDomicilio;
	private String fechaDerivacionSubdelegacion;
	private String cveDelegacionDestino;
	private String cveSubdelegacionDestino;
	private String folioOficioDerivacion;
	private String funcionarioRegistraDerSub;
	
	
	
	//Deriva a fiscalizacion
	private String folioOficioFiscalizacion;
	private String fechaDerivacionFiscaliozacion;
	private String funcionario;
	
	
	//Reactivacion
	private String fechaSolicitudReactivacion;
	private String fechaEnvioSolicitudNormativo;
	private String fechaReactivacion;
	private String numeroOficio;
	private String numeroOficioReactivacion;
	private String funcionarioReactivacion;
	private String observaciones;
	
	
	
	//Derivar a dictamen
	private String fechaAutDeAvisoDictamen;
	private String ejercicioDictaminar;
	private String numerAviso;
	private String funcionarioRegistraDictamen;
	
	//Cancelacion
	private String referenciaCancelacion;
	private String fechaCancelacion;
	private String motivoCancelacion;
	
	public OperacionCorreccionVO(){
		this.mensaje="";
	}
	public String getNumeroFolio() {
		return numeroFolio;
	}
	public void setNumeroFolio(String numeroFolio) {
		this.numeroFolio = numeroFolio;
	}
	public String getCveDomicilio() {
		return cveDomicilio;
	}
	public void setCveDomicilio(String cveDomicilio) {
		this.cveDomicilio = cveDomicilio;
	}
	public String getFechaDerivacionSubdelegacion() {
		return fechaDerivacionSubdelegacion;
	}
	public void setFechaDerivacionSubdelegacion(String fechaDerivacionSubdelegacion) {
		this.fechaDerivacionSubdelegacion = fechaDerivacionSubdelegacion;
	}
	public String getCveDelegacionDestino() {
		return cveDelegacionDestino;
	}
	public void setCveDelegacionDestino(String cveDelegacionDestino) {
		this.cveDelegacionDestino = cveDelegacionDestino;
	}
	public String getCveSubdelegacionDestino() {
		return cveSubdelegacionDestino;
	}
	public void setCveSubdelegacionDestino(String cveSubdelegacionDestino) {
		this.cveSubdelegacionDestino = cveSubdelegacionDestino;
	}
	public String getFolioOficioDerivacion() {
		return folioOficioDerivacion;
	}
	public void setFolioOficioDerivacion(String folioOficioDerivacion) {
		this.folioOficioDerivacion = folioOficioDerivacion;
	}
	public String getFuncionarioRegistraDerSub() {
		return funcionarioRegistraDerSub;
	}
	public void setFuncionarioRegistraDerSub(String funcionarioRegistraDerSub) {
		this.funcionarioRegistraDerSub = funcionarioRegistraDerSub;
	}
	public String getFolioOficioFiscalizacion() {
		return folioOficioFiscalizacion;
	}
	public void setFolioOficioFiscalizacion(String folioOficioFiscalizacion) {
		this.folioOficioFiscalizacion = folioOficioFiscalizacion;
	}
	public String getFechaDerivacionFiscaliozacion() {
		return fechaDerivacionFiscaliozacion;
	}
	public void setFechaDerivacionFiscaliozacion(
			String fechaDerivacionFiscaliozacion) {
		this.fechaDerivacionFiscaliozacion = fechaDerivacionFiscaliozacion;
	}
	public String getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}
	public String getFechaSolicitudReactivacion() {
		return fechaSolicitudReactivacion;
	}
	public void setFechaSolicitudReactivacion(String fechaSolicitudReactivacion) {
		this.fechaSolicitudReactivacion = fechaSolicitudReactivacion;
	}
	public String getFechaEnvioSolicitudNormativo() {
		return fechaEnvioSolicitudNormativo;
	}
	public void setFechaEnvioSolicitudNormativo(String fechaEnvioSolicitudNormativo) {
		this.fechaEnvioSolicitudNormativo = fechaEnvioSolicitudNormativo;
	}
	public String getFechaReactivacion() {
		return fechaReactivacion;
	}
	public void setFechaReactivacion(String fechaReactivacion) {
		this.fechaReactivacion = fechaReactivacion;
	}
	public String getNumeroOficio() {
		return numeroOficio;
	}
	public void setNumeroOficio(String numeroOficio) {
		this.numeroOficio = numeroOficio;
	}
	public String getNumeroOficioReactivacion() {
		return numeroOficioReactivacion;
	}
	public void setNumeroOficioReactivacion(String numeroOficioReactivacion) {
		this.numeroOficioReactivacion = numeroOficioReactivacion;
	}
	public String getFuncionarioReactivacion() {
		return funcionarioReactivacion;
	}
	public void setFuncionarioReactivacion(String funcionarioReactivacion) {
		this.funcionarioReactivacion = funcionarioReactivacion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getFechaAutDeAvisoDictamen() {
		return fechaAutDeAvisoDictamen;
	}
	public void setFechaAutDeAvisoDictamen(String fechaAutDeAvisoDictamen) {
		this.fechaAutDeAvisoDictamen = fechaAutDeAvisoDictamen;
	}
	public String getEjercicioDictaminar() {
		return ejercicioDictaminar;
	}
	public void setEjercicioDictaminar(String ejercicioDictaminar) {
		this.ejercicioDictaminar = ejercicioDictaminar;
	}
	public String getNumerAviso() {
		return numerAviso;
	}
	public void setNumerAviso(String numerAviso) {
		this.numerAviso = numerAviso;
	}
	public String getFuncionarioRegistraDictamen() {
		return funcionarioRegistraDictamen;
	}
	public void setFuncionarioRegistraDictamen(String funcionarioRegistraDictamen) {
		this.funcionarioRegistraDictamen = funcionarioRegistraDictamen;
	}
	public String getReferenciaCancelacion() {
		return referenciaCancelacion;
	}
	public void setReferenciaCancelacion(String referenciaCancelacion) {
		this.referenciaCancelacion = referenciaCancelacion;
	}
	public String getFechaCancelacion() {
		return fechaCancelacion;
	}
	public void setFechaCancelacion(String fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}
	public String getMotivoCancelacion() {
		return motivoCancelacion;
	}
	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	public CrtSolicitudcorr getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(CrtSolicitudcorr solicitud) {
		this.solicitud = solicitud;
	}
	public CrtRevDerivASubd getDerivaSubdelegacion() {
		return derivaSubdelegacion;
	}
	public void setDerivaSubdelegacion(CrtRevDerivASubd derivaSubdelegacion) {
		this.derivaSubdelegacion = derivaSubdelegacion;
	}
	public CrtRevDerivAFisca getDerivaFiscalizacion() {
		return derivaFiscalizacion;
	}
	public void setDerivaFiscalizacion(CrtRevDerivAFisca derivaFiscalizacion) {
		this.derivaFiscalizacion = derivaFiscalizacion;
	}
	public CrtSolicitudOficios getDerivacionDicatmen() {
		return derivacionDicatmen;
	}
	public void setDerivacionDicatmen(CrtSolicitudOficios derivacionDicatmen) {
		this.derivacionDicatmen = derivacionDicatmen;
	}
	public CrtSolicitudOficios getCancelacion() {
		return cancelacion;
	}
	public void setCancelacion(CrtSolicitudOficios cancelacion) {
		this.cancelacion = cancelacion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Domicilio getDomicilioSubdelegacion() {
		return domicilioSubdelegacion;
	}
	public void setDomicilioSubdelegacion(Domicilio domicilioSubdelegacion) {
		this.domicilioSubdelegacion = domicilioSubdelegacion;
	}
	public boolean isPuedeReactivar() {
		return puedeReactivar;
	}
	public void setPuedeReactivar(boolean puedeReactivar) {
		this.puedeReactivar = puedeReactivar;
	}
	public boolean isFinalizaProceso() {
		return finalizaProceso;
	}
	public void setFinalizaProceso(boolean finalizaProceso) {
		this.finalizaProceso = finalizaProceso;
	}
	public boolean isPresentada() {
		return presentada;
	}
	public void setPresentada(boolean presentada) {
		this.presentada = presentada;
	}
	public boolean isExiste() {
		return existe;
	}
	public void setExiste(boolean existe) {
		this.existe = existe;
	}
	
	
	 
	
	
}
