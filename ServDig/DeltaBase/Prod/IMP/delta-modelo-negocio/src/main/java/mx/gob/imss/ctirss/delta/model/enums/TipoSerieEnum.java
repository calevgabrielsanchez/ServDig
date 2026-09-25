package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoSerieEnum {

	ORDINARIA(1, "ORDINARIA"), FALLECIDOS(2, "FALLECIDOS"), HOMONIMIA(3,
			"HOMONIMIA"), MEXICANOS_EXTRANJERO(4, "MEXICANOS EN EL EXTRANJERO"), ARTICULO_125(
			5, "ARTICULO 125");

	private int clave;
	private String descripcion;

	private final static Map<Integer, TipoSerieEnum> hashCodes = new HashMap<Integer, TipoSerieEnum>();

	static {
		for (TipoSerieEnum tipoSerie : TipoSerieEnum.values()) {
			hashCodes.put(tipoSerie.getClave(), tipoSerie);
		}
	}

	private TipoSerieEnum(int clave, String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}

	public int getClave() {
		return clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static TipoSerieEnum obtenerEnumById(Integer codigo) {
		return hashCodes.get(codigo);
	}
}
