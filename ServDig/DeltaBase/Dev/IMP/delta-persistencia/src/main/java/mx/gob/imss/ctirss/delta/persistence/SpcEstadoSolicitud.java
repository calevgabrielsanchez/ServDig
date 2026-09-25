package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SPC_ESTADO_SOLICITUD database table.
 * 
 */
@Entity
@Table(name="SPC_ESTADO_SOLICITUD")
@NamedQuery(name="SpcEstadoSolicitud.findAll", query="SELECT s FROM SpcEstadoSolicitud s")
public class SpcEstadoSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_ESTADO_SOLICITUD_IDESTADOSOLICITUD_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_ESTADO_SOLICITUD_IDESTADOSOLICITUD_GENERATOR")
	@Column(name="ID_ESTADO_SOLICITUD")
	private String idEstadoSolicitud;

	@Column(name="DES_ESTADO_SOLICITUD")
	private String desEstadoSolicitud;

	public SpcEstadoSolicitud() {
	}

	public String getIdEstadoSolicitud() {
		return this.idEstadoSolicitud;
	}

	public void setIdEstadoSolicitud(String idEstadoSolicitud) {
		this.idEstadoSolicitud = idEstadoSolicitud;
	}

	public String getDesEstadoSolicitud() {
		return this.desEstadoSolicitud;
	}

	public void setDesEstadoSolicitud(String desEstadoSolicitud) {
		this.desEstadoSolicitud = desEstadoSolicitud;
	}

}