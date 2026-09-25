package mx.imss.estrados.commons;

import java.io.Serializable;

public class Constantes implements Serializable {
	
	private static final long serialVersionUID = 9065625845942730411L;
	
	public static String MASCARA_FECHA="dd-MM-yyyy";
	//public static String rutaArchivos="C:\\Users\\NOVUTECK1\\DocumentosAdjuntos\\";
	public static String rutaArchivos="/share/oracle/denlinea_01_domain/app/estrados/";
	public static String NOMBRE_ACUSE_REGISTRO="Acuse_Registro_Notificacion.pdf";
	public static String NOMBRE_ACUSE_RETIRO="Acuse_Retiro_Notificacion.pdf";
	public static String NOMBRE_ACUSE_PUBLICACION="Acuse_Publicacion_Notificacion.pdf";
	
	//public static String RUTA_REPORTES = "C:\\notificacionEstradosTFS\\estrados-servicios-negocio-ejb\\src\\main\\resources\\";
	//public static String RUTA_MANUAL_USUARIO = "C:\\Users\\NOVUTECK1\\Documents\\Desarrollo\\Estrados\\Manual_de_usuario _ESTRADOS.pdf";
	public static String RUTA_MANUAL_USUARIO = "/share/oracle/denlinea_01_domain/app/estrados/Manual_de_usuario _ESTRADOS.pdf";
	public static String RUTA_REPORTES = "/share/oracle/denlinea_01_domain/app/estrados/";
	public final static int TIPO_REPORTE_REGISTRO = 1;
	public final static int TIPO_REPORTE_PUBLICADA = 2;
	public final static int TIPO_REPORTE_RETIRO = 3;
			
	public final static String USER_LOGIN="userLogin";
	public final static String EXTENSION=".pdf";
	
//	public final static long TIEMPO_INACTIVIDAD=20;
//	public final static long TIEMPO_MAXIMO_SESION=120;
	
	public final static long TIEMPO_INACTIVIDAD=600;
	public final static long TIEMPO_MAXIMO_SESION=3000;
	
	public final static int TIPO_DOC_ACUERDO=1;
	public enum ESTATUS{
		INCOMPLETO(0),
		REGISTRADA(1),
		PUBLICADA(2),
		RETIRADA(3),
		BAJA_LOGICA(4);
		
		private int status;
		
		ESTATUS(int status){
			this.status = status;
		}

		public int getStatus() {
			return status;
		}
	}
	
	public enum TIPO_DOCTO_ADJUNTO{
		ACUERDO(1),
		DOCUMENTO(2),
		OTROS(3);
		private int docto;
		TIPO_DOCTO_ADJUNTO(int docto){
			this.docto = docto;
		}
		public int getDocto(){
			return docto;
		}
	}
}
