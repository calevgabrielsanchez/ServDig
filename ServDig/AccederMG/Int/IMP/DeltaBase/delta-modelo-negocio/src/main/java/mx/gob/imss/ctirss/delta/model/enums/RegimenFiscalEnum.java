package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum RegimenFiscalEnum {

	REGIMEN_601(601, "General de Ley Personas Morales"),
    REGIMEN_603(603, "Personas Morales con Fines no Lucrativos"),
    REGIMEN_605(605, "Sueldos y Salarios e Ingresos Asimilados a Salarios"),
    REGIMEN_606(606, "Arrendamiento"),
    REGIMEN_607(607, "Régimen de Enajenación o Adquisición de Bienes"),
    REGIMEN_608(608, "Demás ingresos"),
    REGIMEN_610(610, "Residentes en el Extranjero sin Establecimiento Permanente en México"),
    REGIMEN_611(611, "Ingresos por Dividendos (socios y accionistas)"),
    REGIMEN_612(612, "Personas Físicas con Actividades Empresariales y Profesionales"),
    REGIMEN_614(614, "Ingresos por intereses"),
    REGIMEN_615(615, "Régimen de los ingresos por obtención de premios"),
    REGIMEN_616(616, "Sin obligaciones fiscales"),
    REGIMEN_620(620, "Sociedades Cooperativas de Producción que optan por diferir sus ingresos"),
    REGIMEN_621(621, "Incorporación Fiscal"),
    REGIMEN_622(622, "Actividades Agrícolas, Ganaderas, Silvícolas y Pesqueras"),
    REGIMEN_623(623, "Opcional para Grupos de Sociedades"),
    REGIMEN_624(624, "Coordinados"),
    REGIMEN_625(625, "Régimen de las Actividades Empresariales con ingresos a través de Plataformas Tecnológicas"),
    REGIMEN_626(626, "Régimen Simplificado de Confianza");




	private final static Map<Integer, RegimenFiscalEnum> hashCodes = new HashMap<Integer, RegimenFiscalEnum>();

	static {
		for (RegimenFiscalEnum estado : RegimenFiscalEnum.values()) {
			hashCodes.put(estado.getClave(), estado);
		}
	}

	private int clave;
	private String desc;

	private RegimenFiscalEnum(int clave, String desc) {
		this.clave = clave;
		this.desc = desc;
	}

	/**
	 * @return the clave
	 */
	public int getClave() {
		return clave;
	}

	public String getDesc() {
		return desc;
	}

	public static RegimenFiscalEnum obtenerEnumById(Integer codigo) {
		return hashCodes.get(codigo);
	}
}
