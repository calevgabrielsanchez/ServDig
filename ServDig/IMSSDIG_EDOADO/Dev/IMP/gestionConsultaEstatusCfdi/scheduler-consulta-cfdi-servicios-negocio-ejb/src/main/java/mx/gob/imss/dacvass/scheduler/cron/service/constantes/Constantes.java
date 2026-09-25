package mx.gob.imss.dacvass.scheduler.cron.service.constantes;

import java.io.Serializable;

public class Constantes implements Serializable {
	
	private static final long serialVersionUID = 5735912812952482131L;
	
	public final static String RFC_IMSS = "rfc_imss";
	
	public final static String COMPROBANTE_SATISFACTORIO = "S - Comprobante obtenido satisfactoriamente.";
	
	public final static String ESTADO_VIGENTE = "Vigente";
	public final static String ESTADO_CANCELADO = "Cancelado";
	
	public final static String SIN_ACEPTACION = "Cancelado sin aceptación";
	public final static String CON_ACEPTACION = "Cancelado con aceptación";
	public final static String VENCIDO = "Plazo vencido";
	public final static String RECHAZADA = "Solicitud Rechazada";
	public final static String PROCESO = "En proceso";
	
	public final static String EXPRESION_INVALIDA = "N - 601: La expresión impresa proporcionada no es válida.";
	public final static String COMPROBANTE_NO_ENCONTRADO = "N - 602: Comprobante no encontrado.";
	
}
