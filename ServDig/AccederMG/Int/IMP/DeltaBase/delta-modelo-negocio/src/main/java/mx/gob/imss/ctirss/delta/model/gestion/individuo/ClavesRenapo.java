package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.util.HashMap;
import java.util.Map;

public class ClavesRenapo {
    public static final Map<String, Integer> FROM_DESC_TO_CVE_ENT_FED_NAC_MAP = new HashMap<String, Integer>();
    public static final String[] DESCRIPCION_ENT_FED_ARRY = new String[] { "", "AS", "BC", "BS", "CC", "CL", "CM", "CS", "CH", "DF", "DG", "GT", "GR", "HG", "JC", "MC", "MN", "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TS", "TL", "VZ", "YN", "ZS", "NE", "SE" };

    static {
        Integer cveIndex = 0;
        // CONVERSION ENTIDAD FEDERATIVA
        for (String descripcionEntFed : DESCRIPCION_ENT_FED_ARRY) {
            FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.put(descripcionEntFed, cveIndex);
            if (cveIndex == 33) {
                FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.put(descripcionEntFed, 35);
            }
            if (cveIndex == 34) {
                FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.put(descripcionEntFed, 99);
            }
            cveIndex++;
        }
    }

}
