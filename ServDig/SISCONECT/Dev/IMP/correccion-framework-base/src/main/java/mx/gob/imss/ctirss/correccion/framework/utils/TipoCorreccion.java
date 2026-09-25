/**
 * 
 */
package mx.gob.imss.ctirss.correccion.framework.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vaguirre
 * 
 */
public enum TipoCorreccion {

	SOLICITUD_CORRECCION_ESPONTANEA(1, "CE", 4L), 
	SOLICITUD_CORRECCION_POR_INVITACION(2, "CI", 2L), 
	SATIC_A(3, "SATICA", 7L), 
	SATIC_B(4, "SATICB", 8L), 
	EXHORTO_DE_CONSTRUCCION(5, "EX", 6L), 
	EXHORTO_DE_LO_ORDINARIO(6, "EXO", 5L), 
	SALARIO_BASE_DE_COTIZACION(7, "SBC", 9L), 
	CONTROL_DE_FUENTES_EXTERNAS_DE_INFORMACION_ORDINARIO(8, "DET", null), 
	CONTROL_DETECCION_ORDINARIO(9, "DET", null), 
	INVITACION_CCI(10, "CCI", 2L), 
	INVITACION_CI(11, "CI", 4L),
	SOLICITUD_CORRECCION_ESPONTANEA_CONSTRUCCION(12, "CCE", 4L);

	/* atributos */
	private Integer id;
	/**
	 * Usado para armar el folio.
	 */
	private String prefijoFolio;
	/**
	 * Valor del catalogo <code>Cgc_CatTipo</code>.
	 */
	private Long equivalenciaCaratula;
	private final static Map<Integer, TipoCorreccion> hash = new HashMap<Integer, TipoCorreccion>();;

	private TipoCorreccion(Integer id, String prefijoFolio, Long caratula) {
		this.id = id;
		this.prefijoFolio = prefijoFolio;
		this.equivalenciaCaratula = caratula;
	}

	static {
		TipoCorreccion[] objs = TipoCorreccion.values();
		int pos = 0;
		while (pos < objs.length) {
			hash.put(objs[pos].getId(), objs[pos]);
			pos++;
		}
	}

	/**
	 * 
	 * @param valorIdPredefinido
	 * @return
	 */
	public static TipoCorreccion getById(Long valorIdPredefinido) {
		return hash.get(valorIdPredefinido); 
	}
	
	public static TipoCorreccion getById(Integer valorIdPredefinido) {
		return hash.get(valorIdPredefinido); 
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	public String getIdAsString() {
		return String.valueOf(getId());
	}

	/**
	 * @return the prefijoFolio
	 */
	public String getPrefijoFolio() {
		return prefijoFolio;
	}

	/**
	 * @return the equivalenciaCaratula
	 */
	public Long getEquivalenciaCaratula() {
		return equivalenciaCaratula;
	}

	/**
	 * @param equivalenciaCaratula
	 *            the equivalenciaCaratula to set
	 */
	public void setEquivalenciaCaratula(Long equivalenciaCaratula) {
		this.equivalenciaCaratula = equivalenciaCaratula;
	}

}
