package mx.gob.imss.ctirss.sso.admonusuarios.cte;

public abstract class Constantes {
	
	public static final String ACTIVO = "Active";
	
	public static final String INACTIVO = "Inactive";
	
	public static final String USUARIOS_DN_BASE = "base.dn.usuarios";
    
    public static String ROLES_DN_BASE = "base.dn.roles";
   
	public static int SIN_VALOR = -99;
	
	public static final int TIPO_MOV_REGISTRO = 1;
	public static final int TIPO_MOV_MODIFICACION = 2;
	public static final int TIPO_MOV_BAJA = 3;
	public static final int TIPO_MOV_BAJA_CURP = 4;
	public static final int TIPO_MOV_RECUPERACION = 5;
	public static final int TIPO_MOV_AUTORIZACION = 6;
	public static final int TIPO_MOV_REENVIO_NOTIF = 7;
	public static final int TIPO_MOV_RECHAZO_CUENTA = 8;
	public static final int TIPO_MOV_ACTIVA_CUENTA = 9;
	
	public static final int PUESTO_ANALISTA_DICTAMEN = 129;
	
	public enum ESTATUS{
		SOLICITADO(1), 
		AUTORIZADO(2),
		RECHAZADO(3),
		BAJA(4),
		REACTIVADO(5),
		BAJA_CAMBIO_CURP(6),
		CAMBIO_DATOS_PERSONALES(7),
		CAMBIO_AREA_ADSCRIPCION(8),
		CAMBIO_PERFIL(9),
		CAMBIO_MODULO(10);
		
		private long opcion;

		private ESTATUS(long opcion){
			this.opcion = opcion;
		}

		public long getOpcion() {
			return opcion;
		}

		public void setOpcion(long opcion) {
			this.opcion = opcion;
		}
	}
}
