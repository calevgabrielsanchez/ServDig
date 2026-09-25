/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

/**
* @author Eduardo Gonzalez
* @since 05/10/2012
*/
public class AnalisisClasificacionEmpresas extends AbstractModel {
    
	private static final long serialVersionUID = 1L;
	
	private Long cveIdAnalisis;
	private Long cveIdEstatus;
    private Date fechaRegistro;
    private Date fechaAutorizacion;
    private Date fechaLugarDeExpedicion;
    private String tipoAnalisis;
    private String delegado;
    private String actividadDetectada;
    private String claveUsuarioAsignado;
    private Solicitud solicitud;
    private List<Comentario> comentarios;
    private Articulo articulo;
    private EstatusAnalisisEnum estatus;

    private Clasificacion clasificacionPropuesta;
    private Clasificacion clasificacionAnterior;
    private Clasificacion clasificacionActual;
    private List<EstatusAnalisisModel> historicoEstatus;
    private String descEstatus;
    private Long cveIdRol;
    private List<ElementoBitacora> comentariosDetalle;
    private Long indModAut;
    private Long cveIdDelegacion;
    private Long cveIdSubdelegacion;   
    
    private Date fechaPresentacion;
    private Date fechaEfecto;
    
    private String tipoCausaAnalisis;
    private Long cveIdGrupoAnalisisCe;
    
    private Boolean indActivo;
    private Boolean indRegistraCausa;
    
    private TramiteSujetoObligado tramite;
    
    public List<ElementoBitacoraOmision> getOmisionActual() {
		return omisionActual;
	}
	public void setOmisionActual(List<ElementoBitacoraOmision> omisionActual) {
		this.omisionActual = omisionActual;
	}
	private String urlClemFirma;
    
    private BigDecimal primaSugerida;

	private List<ElementoBitacoraOmision> histOmisiones;


	private List<ElementoBitacoraOmision> omisionActual;
    
	/**
	 * @return the cveIdAnalisis
	 */
	public Long getCveIdAnalisis() {
		return cveIdAnalisis;
	}
	/**
	 * @param cveIdAnalisis the cveIdAnalisis to set
	 */
	public void setCveIdAnalisis(Long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return the fechaAutorizacion
	 */
	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}
	/**
	 * @param fechaAutorizacion the fechaAutorizacion to set
	 */
	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}
	/**
	 * @return the fechaLugarDeExpedicion
	 */
	public Date getFechaLugarDeExpedicion() {
		return fechaLugarDeExpedicion;
	}
	/**
	 * @param fechaLugarDeExpedicion the fechaLugarDeExpedicion to set
	 */
	public void setFechaLugarDeExpedicion(Date fechaLugarDeExpedicion) {
		this.fechaLugarDeExpedicion = fechaLugarDeExpedicion;
	}
	/**
	 * @return the tipoAnalisis
	 */
	public String getTipoAnalisis() {
		return tipoAnalisis;
	}
	/**
	 * @param tipoAnalisis the tipoAnalisis to set
	 */
	public void setTipoAnalisis(String tipoAnalisis) {
		this.tipoAnalisis = tipoAnalisis;
	}
	/**
	 * @return the delegado
	 */
	public String getDelegado() {
		return delegado;
	}
	/**
	 * @param delegado the delegado to set
	 */
	public void setDelegado(String delegado) {
		this.delegado = delegado;
	}
	/**
	 * @return the actividadDetectada
	 */
	public String getActividadDetectada() {
		return actividadDetectada;
	}
	/**
	 * @param actividadDetectada the actividadDetectada to set
	 */
	public void setActividadDetectada(String actividadDetectada) {
		this.actividadDetectada = actividadDetectada;
	}
	/**
	 * @return the claveUsuarioAsignado
	 */
	public String getClaveUsuarioAsignado() {
		return claveUsuarioAsignado;
	}
	/**
	 * @param claveUsuarioAsignado the claveUsuarioAsignado to set
	 */
	public void setClaveUsuarioAsignado(String claveUsuarioAsignado) {
		this.claveUsuarioAsignado = claveUsuarioAsignado;
	}
	/**
	 * @return the solicitud
	 */
	public Solicitud getSolicitud() {
		return solicitud;
	}
	/**
	 * @param solicitud the solicitud to set
	 */
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}
	/**
	 * @return the comentarios
	 */
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	/**
	 * @return the articulo
	 */
	public Articulo getArticulo() {
		return articulo;
	}
	/**
	 * @param articulo the articulo to set
	 */
	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
	/**
	 * @return the estatus
	 */
	public EstatusAnalisisEnum getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(EstatusAnalisisEnum estatus) {
		this.estatus = estatus;
	}
    
	public Long getCveIdEstatus() {
		return cveIdEstatus;
	}
	public void setCveIdEstatus(Long cveIdEstatus) {
		this.cveIdEstatus = cveIdEstatus;
	}	
	
	public Clasificacion getClasificacionPropuesta() {
		return clasificacionPropuesta;
	}
	public void setClasificacionPropuesta(Clasificacion clasificacionPropuesta) {
		this.clasificacionPropuesta = clasificacionPropuesta;
	}
	public Clasificacion getClasificacionAnterior() {
		return clasificacionAnterior;
	}
	public void setClasificacionAnterior(Clasificacion clasificacionAnterior) {
		this.clasificacionAnterior = clasificacionAnterior;
	}		
	public List<EstatusAnalisisModel> getHistoricoEstatus() {
		return historicoEstatus;
	}
	public void setHistoricoEstatus(List<EstatusAnalisisModel> historicoEstatus) {
		this.historicoEstatus = historicoEstatus;
	}		
	public String getDescEstatus() {
		return descEstatus;
	}
	public void setDescEstatus(String descEstatus) {
		this.descEstatus = descEstatus;
	}
		
	public Long getCveIdRol() {
		return cveIdRol;
	}
	public void setCveIdRol(Long cveIdRol) {
		this.cveIdRol = cveIdRol;
	}
	/**
	 * @return the comentariosDetalle
	 */
	public List<ElementoBitacora> getComentariosDetalle() {
		return comentariosDetalle;
	}
	/**
	 * @param comentariosDetalle the comentariosDetalle to set
	 */
	public void setComentariosDetalle(List<ElementoBitacora> comentariosDetalle) {
		this.comentariosDetalle = comentariosDetalle;
	}
		
	public Long getIndModAut() {
		return indModAut;
	}
	public void setIndModAut(Long indModAut) {
		this.indModAut = indModAut;
	}
	
	public Long getCveIdDelegacion() {
		return cveIdDelegacion;
	}
	public void setCveIdDelegacion(Long cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}
	public Long getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}
	public void setCveIdSubdelegacion(Long cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}
	public Clasificacion getClasificacionActual() {
		return clasificacionActual;
	}
	public void setClasificacionActual(Clasificacion clasificacionActual) {
		this.clasificacionActual = clasificacionActual;
	}
	
	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public Date getFechaEfecto() {
		return fechaEfecto;
	}
	public void setFechaEfecto(Date fechaEfecto) {
		this.fechaEfecto = fechaEfecto;
	}
	public String getTipoCausaAnalisis() {
		return tipoCausaAnalisis;
	}
	public void setTipoCausaAnalisis(String tipoCausaAnalisis) {
		this.tipoCausaAnalisis = tipoCausaAnalisis;
	}
	
	public Long getCveIdGrupoAnalisisCe() {
		return cveIdGrupoAnalisisCe;
	}
	public void setCveIdGrupoAnalisisCe(Long cveIdGrupoAnalisisCe) {
		this.cveIdGrupoAnalisisCe = cveIdGrupoAnalisisCe;
	}
	public Boolean getIndActivo() {
		return indActivo;
	}

	public void setIndActivo(Boolean indActivo) {
		this.indActivo = indActivo;
	}
	public Boolean getIndRegistraCausa() {
		return indRegistraCausa;
	}
	public void setIndRegistraCausa(Boolean indRegistraCausa){
		this.indRegistraCausa = indRegistraCausa;
	}
	public TramiteSujetoObligado getTramite() {
		return tramite;
	}
	public void setTramite(TramiteSujetoObligado tramite) {
		this.tramite = tramite;
	}
	public String getUrlClemFirma() {
		return urlClemFirma;
	}
	public void setUrlClemFirma(String urlClemFirma) {
		this.urlClemFirma = urlClemFirma;
	}

	public BigDecimal getPrimaSugerida() {
		return primaSugerida;
	}

	public void setPrimaSugerida(BigDecimal primaSugerida) {
		this.primaSugerida = primaSugerida;
	}
	public List<ElementoBitacoraOmision> getHistOmisiones() {
		return histOmisiones;
	}
	public void setHistOmisiones(List<ElementoBitacoraOmision> histOmisiones) {
		this.histOmisiones = histOmisiones;
	}

	
	
}
