package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

/**
 * Layout oficial del archivo SINDO de bajas por reingreso a RO.
 *
 * Especificación:
 * - Longitud mínima: 174 caracteres (datos obligatorios)
 * - Longitud máxima: 233 caracteres (con espacios reservados)
 * - Posiciones en base 0 (índice Java)
 *
 * Documentación del layout SINDO posicional para bajas por reingreso a Régimen Obligatorio.
 * Esta clase centraliza la definición de todos los campos, sus posiciones y tipos,
 * proporcionando una única fuente de verdad para el parseo.
 *
 * @author Sistema Bajas por Reingreso RO
 * @version 1.0
 */
public final class SindoLayout {

    /**
     * Descriptor de un campo del layout SINDO.
     * Contiene nombre, posición inicial, posición final y tipo de dato.
     */
    public static final class Campo {
        /** Nombre descriptivo del campo */
        public final String nombre;

        /** Índice inicial en la línea (base 0, inclusive) */
        public final int inicio;

        /** Índice final en la línea (base 0, exclusive para substring) */
        public final int fin;

        /** Tipo de dato: '9' = numérico, 'X' = alfanumérico */
        public final char tipo;

        /**
         * Constructor de campo.
         *
         * @param nombre Nombre descriptivo del campo
         * @param inicio Posición inicial (base 0, inclusive)
         * @param fin Posición final (base 0, exclusive para substring)
         * @param tipo Tipo de dato ('9' = numérico, 'X' = alfanumérico)
         */
        public Campo(String nombre, int inicio, int fin, char tipo) {
            this.nombre = nombre;
            this.inicio = inicio;
            this.fin = fin;
            this.tipo = tipo;
        }

        /**
         * Calcula la longitud del campo.
         *
         * @return Número de caracteres del campo
         */
        public int longitud() {
            return fin - inicio;
        }

        public String toString() {
            return "Campo{" + nombre + ", pos " + inicio + "-" + (fin-1) +
                   ", len=" + longitud() + ", tipo=" + tipo + "}";
        }
    }

    // ========================================
    // BLOQUE 1: NSS (posiciones 1-11 del documento, 0-11 en Java)
    // ========================================

    /** NSS del asegurado (10 dígitos) - Posiciones 1-10 */
    public static final Campo NSS = new Campo("NSS", 0, 10, '9');

    /** Dígito verificador del NSS (1 dígito) - Posición 11 */
    public static final Campo DIG_VR_NSS = new Campo("DIG_VR_NSS", 10, 11, '9');

    // ========================================
    // BLOQUE 2: DATOS ORIGEN (Modalidad 40)
    // ========================================

    /** Subdelegación del registro patronal origen (2 chars) - Posiciones 12-13 */
    public static final Campo RP_ORIG_SUB = new Campo("RP_ORIG_SUB", 11, 13, 'X');

    /** Día de la fecha de baja en Modalidad 40 (2 dígitos) - Posiciones 14-15 */
    public static final Campo F_DD_BAJA_ORIG = new Campo("F_DD_BAJA_ORIG", 13, 15, '9');

    /** Mes de la fecha de baja en Modalidad 40 (2 dígitos) - Posiciones 16-17 */
    public static final Campo F_MM_BAJA_ORIG = new Campo("F_MM_BAJA_ORIG", 15, 17, '9');

    /** Año de la fecha de baja en Modalidad 40 (4 dígitos) - Posiciones 18-21 */
    public static final Campo F_AAAA_BAJA_ORIG = new Campo("F_AAAA_BAJA_ORIG", 17, 21, '9');

    /** Tipo de movimiento origen (2 dígitos, ej: 08) - Posiciones 22-23 */
    public static final Campo TP_MOVTO_ORIG = new Campo("TP_MOVTO_ORIG", 21, 23, '9');

    /** Número del registro patronal origen (8 chars) - Posiciones 24-31 */
    public static final Campo RP_ORIG_NUM = new Campo("RP_ORIG_NUM", 23, 31, 'X');

    /** Clave de modalidad origen (2 dígitos, siempre "40") - Posiciones 32-33 */
    public static final Campo CVE_MOD_ORIG = new Campo("CVE_MOD_ORIG", 31, 33, '9');

    /** Dígito verificador del RP origen (1 dígito) - Posición 34 */
    public static final Campo DIG_VR_RP_ORIG = new Campo("DIG_VR_RP_ORIG", 33, 34, '9');

    /** Nombre del patrón origen (50 chars) - Posiciones 35-84 */
    public static final Campo NOM_PATRON_ORIG = new Campo("NOM_PATRON_ORIG", 34, 84, 'X');

    /** Salario base de cotización en Mod 40 (8 dígitos, 2 decimales implícitos) - Posiciones 85-92 */
    public static final Campo SALARIO_BASE_40 = new Campo("SALARIO_BASE_40", 84, 92, '9');

    // ========================================
    // BLOQUE 3: DATOS DESTINO (Régimen Obligatorio)
    // ========================================

    /** Subdelegación del registro patronal destino (2 chars) - Posiciones 93-94 */
    public static final Campo RP_DEST_SUB = new Campo("RP_DEST_SUB", 92, 94, 'X');

    /** Día de la fecha de alta en RO (2 dígitos) - Posiciones 95-96 */
    public static final Campo F_DD_ALTA_DEST = new Campo("F_DD_ALTA_DEST", 94, 96, '9');

    /** Mes de la fecha de alta en RO (2 dígitos) - Posiciones 97-98 */
    public static final Campo F_MM_ALTA_DEST = new Campo("F_MM_ALTA_DEST", 96, 98, '9');

    /** Año de la fecha de alta en RO (4 dígitos) - Posiciones 99-102 */
    public static final Campo F_AAAA_ALTA_DEST = new Campo("F_AAAA_ALTA_DEST", 98, 102, '9');

    /** Tipo de movimiento destino (2 dígitos, ej: 01, 08) - Posiciones 103-104 */
    public static final Campo TP_MOVTO_DEST = new Campo("TP_MOVTO_DEST", 102, 104, '9');

    /** Número del registro patronal destino (8 chars) - Posiciones 105-112 */
    public static final Campo RP_DEST_NUM = new Campo("RP_DEST_NUM", 104, 112, 'X');

    /** Clave de modalidad destino (2 dígitos, ej: 10, 13, 17) - Posiciones 113-114 */
    public static final Campo CVE_MOD_DEST = new Campo("CVE_MOD_DEST", 112, 114, '9');

    /** Dígito verificador del RP destino (1 dígito) - Posición 115 */
    public static final Campo DIG_VR_RP_DEST = new Campo("DIG_VR_RP_DEST", 114, 115, '9');

    /** Nombre del patrón destino (50 chars) - Posiciones 116-165 */
    public static final Campo NOM_PATRON_DEST = new Campo("NOM_PATRON_DEST", 115, 165, 'X');

    /** Salario base de cotización en RO (8 dígitos, 2 decimales implícitos) - Posiciones 167-174 */
    public static final Campo SALARIO_BASE_RO = new Campo("SALARIO_BASE_RO", 166, 174, '9');

    // ========================================
    // BLOQUE 4: CAMPOS OPCIONALES
    // ========================================

    /** CIZ - Índice/Zona (1 dígito, valores 1-3) - Posición 175 */
    public static final Campo CIZ = new Campo("CIZ", 174, 175, '9');

    // NOTA: Posiciones 176-233 son espacios reservados (no se definen campos)

    // ========================================
    // CONSTANTES DE LONGITUD
    // ========================================

    /** Longitud mínima de una línea SINDO válida (datos obligatorios) */
    public static final int LONGITUD_MINIMA = 174;

    /** Longitud máxima de una línea SINDO (con espacios reservados) */
    public static final int LONGITUD_MAXIMA = 233;

    /**
     * Constructor privado (clase utilitaria).
     * No se permite instanciar esta clase.
     */
    private SindoLayout() {
        throw new AssertionError("No se puede instanciar SindoLayout");
    }
}
