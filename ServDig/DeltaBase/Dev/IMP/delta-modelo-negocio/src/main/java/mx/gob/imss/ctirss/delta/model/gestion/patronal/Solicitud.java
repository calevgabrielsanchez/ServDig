package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UMFTurno;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

public class Solicitud extends AbstractModel {

	private static final long serialVersionUID = 1467801938772464994L;
	private String noFolioSolicitud;
	private Date fechaSolicitud;
	private Usuario solicitante;// solicitante = usuario?
	private String observacion;
	private TipoSolicitud tipoSolicitud;
	private UMFTurno umfTurno;
	
	private Date fechaCita;
	private EstadoSolicitud estadoSolicitud;
	private RazonCancelacion razonCancelacion;
	private Fisica aseguradoPensionado;
	private Long id;
	private List<Tramite> tramites;
	private Date fechaBaja;
	
	private String cadenaOriginal;
	private String selloDigital;
	private String secuenciaDeNotaria;
	
	
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	public String getSelloDigital() {
		return selloDigital;
	}

	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}

	public String getSecuenciaDeNotaria() {
		return secuenciaDeNotaria;
	}

	public void setSecuenciaDeNotaria(String secuenciaDeNotaria) {
		this.secuenciaDeNotaria = secuenciaDeNotaria;
	}

	/**
	 * @return the tramites
	 */
	public List<Tramite> getTramites() {
		return tramites;
	}

	/**
	 * @param tramites
	 *            the tramites to set
	 */
	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public String getNoFolioSolicitud() {
		return noFolioSolicitud;
	}

	public void setNoFolioSolicitud(String noFolioSolicitud) {
		this.noFolioSolicitud = noFolioSolicitud;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public TipoSolicitud getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public Date getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(Date fechaCita) {
		this.fechaCita = fechaCita;
	}

	public EstadoSolicitud getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public RazonCancelacion getRazonCancelacion() {
		return razonCancelacion;
	}

	public void setRazonCancelacion(RazonCancelacion razonCancelacion) {
		this.razonCancelacion = razonCancelacion;
	}

	public Fisica getAseguradoPensionado() {
		return aseguradoPensionado;
	}

	public void setAseguradoPensionado(Fisica aseguradoPensionado) {
		this.aseguradoPensionado = aseguradoPensionado;
	}

	/**
	 * @return the fechaBaja
	 */
	public Date getFechaBaja() {
		return fechaBaja;
	}

	/**
	 * @param fechaBaja the fechaBaja to set
	 */
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	/**
	 * @return the umfTurno
	 */
	public UMFTurno getUmfTurno() {
		return umfTurno;
	}

	/**
	 * @param umfTurno the umfTurno to set
	 */
	public void setUmfTurno(UMFTurno umfTurno) {
		this.umfTurno = umfTurno;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Solicitud [fechaSolicitud="+this.fechaSolicitud+"]");
		builder.append("Solicitud [usuario="+this.solicitante+"]");
		builder.append("Solicitud [observacion="+this.observacion+"]");
		builder.append("Solicitud [tipoSolicitud="+this.tipoSolicitud+"]");
		builder.append("Solicitud [fechaCita="+this.fechaCita+"]");
		builder.append("Solicitud [RazonCancelacion="+this.solicitante+"]");
		builder.append("Solicitud [Fisica="+this.aseguradoPensionado+"]");
		builder.append("Solicitud [id="+this.id+"]");
		builder.append("Solicitud [fechaBaja="+this.fechaBaja+"]");
		builder.append("Solicitud [UMFTURNO="+this.umfTurno+"]");
		return builder.toString();
	}
}
