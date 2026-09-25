package mx.imss.estrados.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="NEE_DOCUMENTOS_ADJUNTOS")
public class NeeDocumentosAdjuntos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2160181960224201403L;

	/**
	 * 
	 */
	

	public NeeDocumentosAdjuntos() {
		this.neeNotificaciones=new NeeNotificaciones();
	}
	
	@Id
	@SequenceGenerator(name="NEE_DOCUMENTOS_ADJUNTOS_CVEDOCTOADJUNTO_GENERATOR", sequenceName="SEQ_NEE_CVE_DOCTO_ADJUNTO",  allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NEE_DOCUMENTOS_ADJUNTOS_CVEDOCTOADJUNTO_GENERATOR")
	@Column(name="CVE_DOCTO_ADJUNTO")
	private long cveDoctoAdjunto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_NOTIFICACIONES")
	private NeeNotificaciones neeNotificaciones;
	
	@Column(name="DES_NUM_OFICIO")
	private String desNumOficio;
	
	@Column(name="DES_NOMBRE_ARCHIVO")
	private String desNombreArchivo;
	
	@Column(name="DES_REF_FILESYSTEM")
	private String desRefFilesystem;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_TIPO_ADJUNTO")
	private NeeCatTipoAdjunto neeCatTipoAdjunto;

	public long getCveDoctoAdjunto() {
		return cveDoctoAdjunto;
	}

	public void setCveDoctoAdjunto(long cveDoctoAdjunto) {
		this.cveDoctoAdjunto = cveDoctoAdjunto;
	}

	public NeeNotificaciones getNeeNotificaciones() {
		return neeNotificaciones;
	}

	public void setNeeNotificaciones(NeeNotificaciones neeNotificaciones) {
		this.neeNotificaciones = neeNotificaciones;
	}

	public String getDesNumOficio() {
		return desNumOficio;
	}

	public void setDesNumOficio(String desNumOficio) {
		this.desNumOficio = desNumOficio;
	}

	public String getDesNombreArchivo() {
		return desNombreArchivo;
	}

	public void setDesNombreArchivo(String desNombreArchivo) {
		this.desNombreArchivo = desNombreArchivo;
	}

	public String getDesRefFilesystem() {
		return desRefFilesystem;
	}

	public void setDesRefFilesystem(String desRefFilesystem) {
		this.desRefFilesystem = desRefFilesystem;
	}

	public NeeCatTipoAdjunto getNeeCatTipoAdjunto() {
		return neeCatTipoAdjunto;
	}

	public void setNeeCatTipoAdjunto(NeeCatTipoAdjunto neeCatTipoAdjunto) {
		this.neeCatTipoAdjunto = neeCatTipoAdjunto;
	}

}
