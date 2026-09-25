package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;

/**
 * Parser independiente para archivos SINDO de bajas por reingreso a RO.
 *
 * Esta clase extrae la lógica de parsing del EJB para facilitar:
 * - Unit testing sin dependencias del contenedor
 * - Reutilización en otros contextos (batch, validación)
 * - Mantenibilidad del código de parseo
 *
 * @author Sistema Bajas Reingreso RO
 * @version 1.0
 */
public final class SindoParser {

    // Constantes de validación (copiadas del EJB para mantener independencia)
    private static final int NSS_LONGITUD = 11;
    private static final String MODALIDAD_ORIGEN_ESPERADA = "40";
    private static final String SALARIO_CERO = "00000000";

    /**
     * Parsea una línea del archivo SINDO y devuelve un DTO con los datos extraídos.
     *
     * @param linea Línea del archivo SINDO (mínimo 174 caracteres)
     * @return DTO con datos parseados y validados
     * @throws IvroException si la línea es inválida o no cumple las reglas de negocio
     */
    public SindoLineaDTO parseLinea(String linea) throws IvroException {

        if (linea == null || linea.length() < SindoLayout.LONGITUD_MINIMA) {
            throw new IvroException("",
                "Línea SINDO inválida, longitud=" + (linea != null ? linea.length() : 0) +
                ", mínimo=" + SindoLayout.LONGITUD_MINIMA);
        }

        SindoLineaDTO dto = new SindoLineaDTO();

        try {
            // Bloque NSS
            String nss = extraer(linea, SindoLayout.NSS);
            String digVr = extraer(linea, SindoLayout.DIG_VR_NSS);
            dto.nss = (nss + digVr).trim();

            // Bloque origen (Modalidad 40)
            dto.rpOrigenSubdelegacion = extraer(linea, SindoLayout.RP_ORIG_SUB).trim();
            dto.rpOrigenNumero = extraer(linea, SindoLayout.RP_ORIG_NUM).trim();
            dto.modalidadOrigen = extraer(linea, SindoLayout.CVE_MOD_ORIG).trim();
            dto.vrRegPatOrigen = extraer(linea, SindoLayout.DIG_VR_RP_ORIG);

            dto.rpOrigen = construirRP(
                dto.modalidadOrigen,
                dto.rpOrigenNumero,
                extraer(linea, SindoLayout.DIG_VR_RP_ORIG)
            );
            
            dto.rpDestinoCompleto = dto.rpOrigenNumero + dto.modalidadOrigen 
            		+ dto.vrRegPatOrigen
            		;

            dto.tpMovimientoDestino = extraer(linea, SindoLayout.TP_MOVTO_DEST).trim();
            dto.claveMovimiento = extraer(linea, SindoLayout.TP_MOVTO_ORIG).trim();
            dto.nombrePatronOrigen = extraer(linea, SindoLayout.NOM_PATRON_ORIG).trim();

            dto.salarioBaseModalidad40 = parsearSalario(
                extraer(linea, SindoLayout.SALARIO_BASE_40)
            );

            dto.fechaBajaModalidad40 = parsearFechaDDMMAAAA(
                extraer(linea, SindoLayout.F_DD_BAJA_ORIG) +
                extraer(linea, SindoLayout.F_MM_BAJA_ORIG) +
                extraer(linea, SindoLayout.F_AAAA_BAJA_ORIG)
            );

            // Bloque destino (Régimen Obligatorio)
            String destSub = extraer(linea, SindoLayout.RP_DEST_SUB).trim();
            String destNum = extraer(linea, SindoLayout.RP_DEST_NUM).trim();
            String cveModDes = extraer(linea, SindoLayout.CVE_MOD_DEST).trim();
            dto.modalidadDestino = cveModDes;
            dto.rpDestinoSubdelegacion = destSub;
            
            dto.rpDestino = construirRP(
            	cveModDes,
                destNum,
                extraer(linea, SindoLayout.DIG_VR_RP_DEST)
            );

            
            dto.nombrePatronDestino = extraer(linea, SindoLayout.NOM_PATRON_DEST).trim();
            dto.salarioDiarioDestino = parsearSalario(
                extraer(linea, SindoLayout.SALARIO_BASE_RO)
            );

            dto.fechaMovimientoPatron = parsearFechaDDMMAAAA(
                extraer(linea, SindoLayout.F_DD_ALTA_DEST) +
                extraer(linea, SindoLayout.F_MM_ALTA_DEST) +
                extraer(linea, SindoLayout.F_AAAA_ALTA_DEST)
            );

            // Campos opcionales
            if (linea.length() >= SindoLayout.CIZ.fin) {
                dto.ciz = extraer(linea, SindoLayout.CIZ).trim();
            }

            // Validaciones centralizadas
            validarDatosParsedados(dto);

            return dto;

        } catch (IvroException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IvroException("",
                "Error parseando línea SINDO: " + ex.getMessage(), ex);
        }
    }

    /**
     * Extrae un campo del layout usando su descriptor.
     */
    private String extraer(String linea, SindoLayout.Campo campo) {
        return safeSubstring(linea, campo.inicio, campo.fin);
    }

    /**
     * Substring seguro que maneja límites y padding.
     */
    private String safeSubstring(String str, int start, int end) {
        if (str == null || start >= str.length()) {
            return "";
        }
        int actualEnd = Math.min(end, str.length());
        String result = str.substring(start, actualEnd);

        // Padding si es necesario
        int expected = end - start;
        if (result.length() < expected) {
            result = String.format("%-" + expected + "s", result);
        }
        return result;
    }

   private BigDecimal parsearSalario(String salarioStr) {
    if (salarioStr == null) {
        return null;
    }

    salarioStr = salarioStr.trim();

    if (salarioStr.isEmpty() || SALARIO_CERO.equals(salarioStr)) {
        return null;
    }

    try {
        BigDecimal valor = new BigDecimal(salarioStr);
        valor = valor.movePointLeft(2); // 2 decimales implícitos
        return valor.setScale(2, java.math.RoundingMode.UNNECESSARY);
    } catch (NumberFormatException ex) {
        return null;
    }
}

    /**
     * Construye el registro patronal completo.
     */
    private String construirRP(String modalidad, String numero, String digitoVerificador) {
        return  numero + modalidad + digitoVerificador;
    }

    /**
     * Valida que los datos parseados cumplan las reglas de negocio.
     */
    private void validarDatosParsedados(SindoLineaDTO dto) throws IvroException {

        if (dto.nss == null || dto.nss.length() != NSS_LONGITUD) {
            throw new IvroException("", "NSS inválido: " + dto.nss);
        }

        if (!MODALIDAD_ORIGEN_ESPERADA.equals(dto.modalidadOrigen)) {
            throw new IvroException("",
                "Modalidad origen debe ser " + MODALIDAD_ORIGEN_ESPERADA +
                ", recibido: " + dto.modalidadOrigen);
        }

        if (dto.fechaBajaModalidad40 == null) {
            throw new IvroException("", "Fecha baja Modalidad 40 inválida o vacía");
        }

        if (dto.fechaMovimientoPatron == null) {
            throw new IvroException("", "Fecha reingreso RO inválida o vacía");
        }
    }

    /**
     * Parsea fecha en formato DDMMAAAA.
     *
     * @param fecha String de 8 caracteres (ej: "15032024")
     * @return Date o null si es inválida/vacía
     */
    private Date parsearFechaDDMMAAAA(String fecha) throws ParseException {
        if (fecha == null || fecha.trim().isEmpty() || "00000000".equals(fecha.trim())) {
            return null;
        }
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		sdf.setLenient(false);
		return sdf.parse(fecha.trim());
    }

    /**
     * DTO interno que contiene los datos parseados de una línea SINDO.
     * Puede ser estático para permitir uso fuera del parser.
     */
    public static class SindoLineaDTO {
        // NSS
        public String nss;

        // Datos origen (Modalidad 40)
        public String rpDestinoSubdelegacion;
        public String rpOrigenSubdelegacion;
        public String rpOrigenNumero;
        public String rpOrigen;
        public String modalidadOrigen;
        public String claveMovimiento;
        public String tpMovimientoDestino;
        public String nombrePatronOrigen;
        public BigDecimal salarioBaseModalidad40;
        public Date fechaBajaModalidad40;

        // Datos destino (Régimen Obligatorio)
        public String rpDestino;
        //un registro patronal se divide en 3 partes, pero "rpDestinoCompleto" mantiene las 3 juntas
        public String rpDestinoCompleto;
        public String modalidadDestino;
        public String nombrePatronDestino;
        public BigDecimal salarioDiarioDestino;
        public Date fechaMovimientoPatron;
        
        
        //=============== Digitos verificadores ===============
        
        // extraido usando SindoLayout.DIG_VR_RP_ORIG sobre una linea SINDO
        public String vrRegPatOrigen = "";
        

        // Opcionales
        public String ciz;

        public SindoLineaDTO() {
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("SindoLineaDTO {");
            sb.append("nss='").append(nss).append('\'');
            sb.append(", rpDestinoSubdelegacion='").append(rpDestinoSubdelegacion).append('\'');
            sb.append(", rpOrigenSubdelegacion='").append(rpOrigenSubdelegacion).append('\'');
            sb.append(", rpOrigenNumero='").append(rpOrigenNumero).append('\'');
            sb.append(", rpOrigen='").append(rpOrigen).append('\'');
            sb.append(", modalidadOrigen='").append(modalidadOrigen).append('\'');
            sb.append(", claveMovimiento='").append(claveMovimiento).append('\'');
            sb.append(", tpMovimientoDestino='").append(tpMovimientoDestino).append('\'');
            sb.append(", nombrePatronOrigen='").append(nombrePatronOrigen).append('\'');
            sb.append(", salarioBaseModalidad40=").append(salarioBaseModalidad40);
            sb.append(", fechaBajaModalidad40=").append(fechaBajaModalidad40);
            sb.append(", rpDestino='").append(rpDestino).append('\'');
            sb.append(", rpDestinoCompleto='").append(rpDestinoCompleto).append('\'');
            sb.append(", modalidadDestino='").append(modalidadDestino).append('\'');
            sb.append(", nombrePatronDestino='").append(nombrePatronDestino).append('\'');
            sb.append(", salarioDiarioDestino=").append(salarioDiarioDestino);
            sb.append(", fechaMovimientoPatron=").append(fechaMovimientoPatron);
            sb.append(", vrRegPatOrigen='").append(vrRegPatOrigen).append('\'');
            sb.append(", ciz='").append(ciz).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
