/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.solicitud;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Cesar Garcia Mauricio
 * @Proyecto: delta
 * @Archivo: EstadoSolicitudEnum.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.solicitud
 * @Fecha: 06 Junio 2012 17:21
 */
public enum EstadoSolicitudEnum {

    REGISTRADA(1, "Registrada"), ATENDIDA(2, "Atendida"), CANCELADA(3, "Cancelada"), 
    VALIDADA(4, "Validada"), PENDIENTE_AUTORIZACION(5, "En proceso"), EDICION_VENTANILLA(6,"En Ventanilla"),
    EDICION_BACKOFFICE(7,"En Backoffice"), PROCESADA_EN_LINEA(8,"Procesada en línea"), 
    PROCESADA_BACKOFFICE(9,"Procesada en backoffice"), PROCESADA_VENTANILLA(10,"Procesada en ventanilla"),
    RECHAZADA(11,"Rechazada"),PRESENTARSE_EN_VENTANILLA(12,"Por presentarse en ventanilla"), PARA_PROCESAR_BACKOFFICE(13,"Para procesar en backoffice"),
    TODOS(-1,"Todos");

    private Integer codigo;
    private String descripcion;

    private EstadoSolicitudEnum(final Integer codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    private final static Map<String, EstadoSolicitudEnum> hashNames = new HashMap<String, EstadoSolicitudEnum>();
	private final static Map<Integer,EstadoSolicitudEnum> hashCodes = new HashMap<Integer,EstadoSolicitudEnum>();
	
	static{ 
		for(EstadoSolicitudEnum estado : EstadoSolicitudEnum.values()){
			hashNames.put(estado.name(), estado);
			hashCodes.put(estado.getCodigo(), estado);
		}
	}
	
	public static EstadoSolicitudEnum obtenerEnumByName(String name){
		return hashNames.get(name);
	}
	
	public static EstadoSolicitudEnum obternerEnumById(Integer codigo){
		return hashCodes.get(codigo);
	}
}
