package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum RespuestaOpinion32DEnum {

	POSITIVA(1, true, "Positiva"),
	NEGATIVA_ADEUDOS_HUELGA(2, false, "Negativa"),
	NEGATIVA_SIN_ADEUDOS_HUELGA(3, false, "Negativa"),
	NEGATIVA_ADEUDOS(4, false, "Negativa"),
	PATRON_SIN_TRABAJADORES(5, true, "Sin Opinión"),
	NEGATIVA_ADEUDOS_BAJA(6, false, "Negativa"),
	PATRONES_BAJA_SIN_ADEUDO(7, true, "Sin Opinión"),
	SIN_PATRONES(8, true,"Sin Opinión"),
	// Esta se utiliza sólo para la cadena original
	NEGATIVA(-1, false, "Negativa");

	private int id;
	private boolean opinion;
	private String desc;

	private RespuestaOpinion32DEnum(int id, boolean opinion, String desc) {
		this.id = id;
		this.opinion = opinion;
		this.desc = desc;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return the opinion
	 */
	public boolean isOpinion() {
		return opinion;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	private final static Map<String, RespuestaOpinion32DEnum> hashNames = new HashMap<String, RespuestaOpinion32DEnum>();
	private final static Map<Integer, RespuestaOpinion32DEnum> hashCodes = new HashMap<Integer, RespuestaOpinion32DEnum>();

	static {
		for (RespuestaOpinion32DEnum tramite : RespuestaOpinion32DEnum.values()) {
			hashNames.put(tramite.getDesc(), tramite);
			hashCodes.put(tramite.getId(), tramite);
		}
	}

	public static RespuestaOpinion32DEnum obtenerEnumByName(String name){
		return hashNames.get(name);
	}
	
	public static RespuestaOpinion32DEnum obternerEnumById(Integer codigo){
		return hashCodes.get(codigo);
	}

}
