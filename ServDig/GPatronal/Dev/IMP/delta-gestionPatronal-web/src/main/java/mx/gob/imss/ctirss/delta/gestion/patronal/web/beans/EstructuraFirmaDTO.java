package mx.gob.imss.ctirss.delta.gestion.patronal.web.beans;

import java.io.Serializable;
import java.util.List;

public class EstructuraFirmaDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idTipoSolicitud;
	private String descripcionTipoSolicitud;
	private List<Integer> idTipoTramite;
	private String folioSolicitud;
	private String curp;
	private String rfc;
	private Boolean validarRFC;
	private String registroPatronal;
	private String nombreCompleto;
	private String fechaElectronica;
	private String cad_original;
	private String tipo_operacion;
	private Boolean firma_archivo;
	private String min_archivos;
	private String max_archivos;
	private Boolean mostrarCartaTerminos;
	private RepresentanteDTO[] afectado;
	private RepresentanteDTO[] representados;
	private String tipoAcuse;
	private String acuse;
	
	public EstructuraFirmaDTO(){
		afectado = new RepresentanteDTO[1];
		representados = new RepresentanteDTO[1];
	}
	
	public String getIdTipoSolicitud() {
		return idTipoSolicitud;
	}
	public void setIdTipoSolicitud(String idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
	}
	public String getDescripcionTipoSolicitud() {
		return descripcionTipoSolicitud;
	}
	public void setDescripcionTipoSolicitud(String descripcionTipoSolicitud) {
		this.descripcionTipoSolicitud = descripcionTipoSolicitud;
	}
	public List<Integer> getIdTipoTramite() {
		return idTipoTramite;
	}
	public void setIdTipoTramite(List<Integer> idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
	}
	public String getFolioSolicitud() {
		return folioSolicitud;
	}
	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public Boolean getValidarRFC() {
		return validarRFC;
	}
	public void setValidarRFC(Boolean validarRFC) {
		this.validarRFC = validarRFC;
	}
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getFechaElectronica() {
		return fechaElectronica;
	}
	public void setFechaElectronica(String fechaElectronica) {
		this.fechaElectronica = fechaElectronica;
	}
	public String getCad_original() {
		return cad_original;
	}
	public void setCad_original(String cad_original) {
		this.cad_original = cad_original;
	}
	public String getTipo_operacion() {
		return tipo_operacion;
	}
	public void setTipo_operacion(String tipo_operacion) {
		this.tipo_operacion = tipo_operacion;
	}
	public Boolean getFirma_archivo() {
		return firma_archivo;
	}
	public void setFirma_archivo(Boolean firma_archivo) {
		this.firma_archivo = firma_archivo;
	}
	public String getMin_archivos() {
		return min_archivos;
	}
	public void setMin_archivos(String min_archivos) {
		this.min_archivos = min_archivos;
	}
	public String getMax_archivos() {
		return max_archivos;
	}
	public void setMax_archivos(String max_archivos) {
		this.max_archivos = max_archivos;
	}
	public Boolean getMostrarCartaTerminos() {
		return mostrarCartaTerminos;
	}
	public void setMostrarCartaTerminos(Boolean mostrarCartaTerminos) {
		this.mostrarCartaTerminos = mostrarCartaTerminos;
	}
	
	public String getTipoAcuse() {
		return tipoAcuse;
	}
	public void setTipoAcuse(String tipoAcuse) {
		this.tipoAcuse = tipoAcuse;
	}
	public String getAcuse() {
		return acuse;
	}
	public void setAcuse(String acuse) {
		this.acuse = acuse;
	}
	public RepresentanteDTO[] getAfectado() {
		return afectado;
	}
	public void setAfectado(RepresentanteDTO[] afectado) {
		this.afectado = afectado;
	}
	public RepresentanteDTO[] getRepresentados() {
		return representados;
	}
	public void setRepresentados(RepresentanteDTO[] representados) {
		this.representados = representados;
	}

}