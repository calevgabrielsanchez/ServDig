package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


@MappedSuperclass
public class AbstractCrcTramiteMensajes  extends AbstractModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="CVE_MENSAJE")	
	private Integer cveMensaje;
	
	
	@Column(name="CVE_TRAMITE")
	private Integer cveTramite;
	
	
	@Column(name="DES_MENSAJES")
	private String desMensajes;
	
	
	@Column(name="IND_VIGENCIA")
	private Integer vigencia;


	@Column(name="FEC_FECHAREG")
	private Date fechaRegistro;


	public Integer getCveMensaje() {
		return cveMensaje;
	}


	public void setCveMensaje(Integer cveMensaje) {
		this.cveMensaje = cveMensaje;
	}


	public Integer getCveTramite() {
		return cveTramite;
	}


	public void setCveTramite(Integer cveTramite) {
		this.cveTramite = cveTramite;
	}


	public String getDesMensajes() {
		return desMensajes;
	}


	public void setDesMensajes(String desMensajes) {
		this.desMensajes = desMensajes;
	}


	public Integer getVigencia() {
		return vigencia;
	}


	public void setVigencia(Integer vigencia) {
		this.vigencia = vigencia;
	}


	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	
}

