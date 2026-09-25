package mx.gob.imss.ctirss.delta.model.clasificacion;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ElementoBitacora extends AbstractModel {

	
	public ElementoBitacora(){}
	
	public ElementoBitacora(String usuario, String acccionRealizada,
			String comentario, String fecha, String registroPatronal) {
		super();
		this.usuario = usuario;
		this.acccionRealizada = acccionRealizada;
		this.comentario = comentario;
		this.fecha = fecha;
		this.registroPatronal = registroPatronal;
	}

	public ElementoBitacora(String usuario, String acccionRealizada,
			String comentario, String fecha, long idAccionrealizada) {
		super();
		this.usuario = usuario;
		this.acccionRealizada = acccionRealizada;
		this.comentario = comentario;
		this.fecha = fecha;
		this.idAccionrealizada = idAccionrealizada;
	}
	
		private String usuario;
		
		private String acccionRealizada;
		
		private String comentario;
		
		private String fecha;
		
		private String registroPatronal;

		private long idAccionrealizada;
		
		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public String getAcccionRealizada() {
			return acccionRealizada;
		}

		public void setAcccionRealizada(String acccionRealizada) {
			this.acccionRealizada = acccionRealizada;
		}

		public String getComentario() {
			return comentario;
		}

		public void setComentario(String comentario) {
			this.comentario = comentario;
		}

		public String getFecha() {
			return fecha;
		}

		public void setFecha(String fecha) {
			this.fecha = fecha;
		}

		public String getRegistroPatronal() {
			return registroPatronal;
		}

		public void setRegistroPatronal(String registroPatronal) {
			this.registroPatronal = registroPatronal;
		}		
		
		public long getIdAccionrealizada() {
			return idAccionrealizada;
		}

		public void setIdAccionrealizada(long idAccionrealizada) {
			this.idAccionrealizada = idAccionrealizada;
		}

		@Override
		public String toString() {
			return "ElementoBitacora [usuario=" + usuario
					+ ", acccionRealizada=" + acccionRealizada
					+ ", comentario=" + comentario + ", fecha=" + fecha
					+ ", registroPatronal=" + registroPatronal
					+ ", idAccionrealizada=" + idAccionrealizada + "]";
		}
		
		
		
}