package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.SindoParser.SindoLineaDTO;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests unitarios para SindoParser.
 *
 * Validación exhaustiva del parser sin dependencias del contenedor EJB.
 * Casos de prueba basados en archivos SINDO reales del sistema.
 *
 * @author Sistema Bajas Reingreso RO
 * @version 1.0
 */
public class SindoParserTest {

    private SindoParser parser;
    private SimpleDateFormat sdf;

    @Before
    public void setUp() {
        parser = new SindoParser();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    // ========================================
    // CASOS EXITOSOS
    // ========================================

    @Test
    public void parseLinea_lineaValidaCompleta_devuelveDTOCorrecto() throws Exception {
        // Línea basada en archivo real REINGRESOS_RO_20181231_2L.txt
        String linea = construirLineaValida(
            "4389612569", "7",  // NSS
            "31", "12", "2018",  // Fecha baja Mod 40
            "D5615071", "40",    // RP origen, modalidad
            "MARIO CARDENAS CEPEDA",
            "00000000",          // Salario Mod 40 (cero)
            "01", "12", "2018",  // Fecha alta RO
            "00000000", "10",    // RP destino, modalidad
            "SIN PATRON",
            "00000000"           // Salario RO (cero)
        );

        SindoLineaDTO dto = parser.parseLinea(linea);

        // Validar NSS
        assertEquals("43896125697", dto.nss);

        // Validar modalidades
        assertEquals("40", dto.modalidadOrigen);
        assertEquals("10", dto.modalidadDestino);

        // Validar fechas
        assertEquals("31/12/2018", sdf.format(dto.fechaBajaModalidad40));
        assertEquals("01/12/2018", sdf.format(dto.fechaMovimientoPatron));

        // Validar patrones
        assertEquals("MARIO CARDENAS CEPEDA", dto.nombrePatronOrigen.trim());
        assertEquals("SIN PATRON", dto.nombrePatronDestino.trim());

        // Validar salarios (deben ser null porque son cero)
        assertNull(dto.salarioBaseModalidad40);
        assertNull(dto.salarioDiarioDestino);
    }
    
    
    
    @Test
    public void parseLinea_RegistroPatronalCompleto_1ro() throws Exception {
    	// Result expected
        SindoLineaDTO expected = new SindoLineaDTO();
        expected.rpDestinoCompleto = "Y5699999404";
    	
        // Test
    	String linea = "17745604003560101202602Y5699999404CONTINUACION VOLUNTARIA EN EL REGIMEN OBLIGATORIO 00029254560201202608Y5654135101INOBAZZ PHARMA SA DE CV                           000330601";
        SindoLineaDTO got = parser.parseLinea(linea);

        //Asserts
        assertEquals(expected.rpDestinoCompleto, got.rpDestinoCompleto);        
    }

    
    @Test
    public void parseLinea_RegistroPatronalCompleto_2do() throws Exception {
    	// Result expected
        SindoLineaDTO expected = new SindoLineaDTO();
        expected.rpDestinoCompleto = "Y6299999406";
    	
        // Test
    	String linea = "17806019521063110202502Y6299999406CONTINUACION VOLUNTARIA EN EL REGIMEN OBLIGATORIO 00271425110111202508Y6672110100DISTRIBUIDORA TEXTIL EL FUERTE SA DE CV           000292541";
        SindoLineaDTO got = parser.parseLinea(linea);

        //Asserts
        assertEquals(expected.rpDestinoCompleto, got.rpDestinoCompleto);        
    }

    
    
    @Test
    public void parseLinea_RegistroPatronalCompleto_3ro() throws Exception {
    	// Result expected
        SindoLineaDTO expected = new SindoLineaDTO();
        expected.rpDestinoCompleto = "C5399999403";
    	
        // Test
    	String linea = "39967202191060211202502C5399999403CONTINUACION VOLUNTARIA EN EL REGIMEN OBLIGATORIO 00024893060311202508M4924004100RAYMUNDO COSS MORA                                000717131";
        SindoLineaDTO got = parser.parseLinea(linea);

        //Asserts
        assertEquals(expected.rpDestinoCompleto, got.rpDestinoCompleto);        
    }

    
    
    
    @Test
    public void parseLinea_conSalariosValidos_parseaCorrectamente() throws Exception {
        String linea = construirLineaValida(
            "1234567890", "1",
            "15", "03", "2024",
            "A1234567", "40",
            "Patron Origen Ejemplo",
            "00120000",  // 1200.00
            "20", "05", "2024",
            "B9876543", "10",
            "Patron Destino Ejemplo",
            "00150000"   // 1500.00
        );

        SindoLineaDTO dto = parser.parseLinea(linea);

        // Validar salarios parseados correctamente (2 decimales implícitos)
        assertNotNull(dto.salarioBaseModalidad40);
        assertEquals("1200.00", dto.salarioBaseModalidad40.toPlainString());

        assertNotNull(dto.salarioDiarioDestino);
        assertEquals("1500.00", dto.salarioDiarioDestino.toPlainString());
    }

    @Test
    public void parseLinea_conRegistrosPatronalesCompletos_construyeCorrectamente() throws Exception {
        String linea = construirLineaBase();

        // Modificar RPs para verificar construcción correcta
        linea = reemplazar(linea, SindoLayout.RP_ORIG_SUB, "31");
        linea = reemplazar(linea, SindoLayout.RP_ORIG_NUM, "D5615071");
        linea = reemplazar(linea, SindoLayout.DIG_VR_RP_ORIG, "4");

        linea = reemplazar(linea, SindoLayout.RP_DEST_SUB, "08");
        linea = reemplazar(linea, SindoLayout.RP_DEST_NUM, "00000000");
        linea = reemplazar(linea, SindoLayout.DIG_VR_RP_DEST, "1");

        SindoLineaDTO dto = parser.parseLinea(linea);

        // Validar construcción del RP completo
        assertEquals("31D56150714", dto.rpOrigen);
assertEquals("08000000001", dto.rpDestino);    }

    // ========================================
    // CASOS DE ERROR - VALIDACIONES NSS
    // ========================================

    @Test(expected = IvroException.class)
    public void parseLinea_nssCorto_lanzaExcepcion() throws Exception {
        String linea = construirLineaBase();
        // NSS con solo 8 dígitos en lugar de 11
        linea = reemplazar(linea, SindoLayout.NSS, "12345678  ");
        linea = reemplazar(linea, SindoLayout.DIG_VR_NSS, " ");
        parser.parseLinea(linea);
    }

    @Test(expected = IvroException.class)
    public void parseLinea_nssVacio_lanzaExcepcion() throws Exception {
        String linea = construirLineaBase();
        linea = reemplazar(linea, SindoLayout.NSS, "          ");
        linea = reemplazar(linea, SindoLayout.DIG_VR_NSS, " ");
        parser.parseLinea(linea);
    }

    // ========================================
    // CASOS DE ERROR - VALIDACIONES MODALIDAD
    // ========================================

    @Test(expected = IvroException.class)
    public void parseLinea_modalidadOrigenDistintaDe40_lanzaExcepcion() throws Exception {
        String linea = construirLineaBase();
        // Modalidad origen debe ser "40", probamos con "10"
        linea = reemplazar(linea, SindoLayout.CVE_MOD_ORIG, "10");
        parser.parseLinea(linea);
    }

    @Test(expected = IvroException.class)
    public void parseLinea_modalidadOrigen33_lanzaExcepcion() throws Exception {
        String linea = construirLineaBase();
        linea = reemplazar(linea, SindoLayout.CVE_MOD_ORIG, "33");
        parser.parseLinea(linea);
    }

    // ========================================
    // CASOS DE ERROR - VALIDACIONES FECHAS
    // ========================================

    @Test(expected = IvroException.class)
    public void parseLinea_fechaBajaModalidad40Vacia_lanzaExcepcion() throws Exception {
        String linea = construirLineaBase();
        linea = reemplazar(linea, SindoLayout.F_DD_BAJA_ORIG, "00");
        linea = reemplazar(linea, SindoLayout.F_MM_BAJA_ORIG, "00");
        linea = reemplazar(linea, SindoLayout.F_AAAA_BAJA_ORIG, "0000");
        parser.parseLinea(linea);
    }

    @Test(expected = IvroException.class)
    public void parseLinea_fechaReingresoROVacia_lanzaExcepcion() throws Exception {
        String linea = construirLineaBase();
        linea = reemplazar(linea, SindoLayout.F_DD_ALTA_DEST, "00");
        linea = reemplazar(linea, SindoLayout.F_MM_ALTA_DEST, "00");
        linea = reemplazar(linea, SindoLayout.F_AAAA_ALTA_DEST, "0000");
        parser.parseLinea(linea);
    }

    @Test(expected = IvroException.class)
    public void parseLinea_fechaBajaInvalida_lanzaExcepcion() throws Exception {
        String linea = construirLineaBase();
        // Fecha inválida: día 32
        linea = reemplazar(linea, SindoLayout.F_DD_BAJA_ORIG, "32");
        parser.parseLinea(linea);
    }

    // ========================================
    // CASOS DE ERROR - LONGITUD
    // ========================================

    @Test(expected = IvroException.class)
    public void parseLinea_lineaMuyCorta_lanzaExcepcion() throws Exception {
        parser.parseLinea("LINEA_CORTA");
    }

    @Test(expected = IvroException.class)
    public void parseLinea_lineaVacia_lanzaExcepcion() throws Exception {
        parser.parseLinea("");
    }

    @Test(expected = IvroException.class)
    public void parseLinea_lineaNull_lanzaExcepcion() throws Exception {
        parser.parseLinea(null);
    }

    // ========================================
    // CASOS ESPECIALES - SALARIOS
    // ========================================

    @Test
    public void parseLinea_salarioCero_devuelveNull() throws Exception {
        String linea = construirLineaBase();
        linea = reemplazar(linea, SindoLayout.SALARIO_BASE_40, "00000000");
        linea = reemplazar(linea, SindoLayout.SALARIO_BASE_RO, "00000000");

        SindoLineaDTO dto = parser.parseLinea(linea);

        assertNull("Salario Mod 40 cero debe ser null", dto.salarioBaseModalidad40);
        assertNull("Salario RO cero debe ser null", dto.salarioDiarioDestino);
    }

    @Test
    public void parseLinea_salarioMinimo_parseaCorrectamente() throws Exception {
        String linea = construirLineaBase();
        // Salario mínimo: 0.01
        linea = reemplazar(linea, SindoLayout.SALARIO_BASE_40, "00000001");

        SindoLineaDTO dto = parser.parseLinea(linea);

        assertNotNull(dto.salarioBaseModalidad40);
        assertEquals("0.01", dto.salarioBaseModalidad40.toPlainString());
    }

    @Test
    public void parseLinea_salarioMaximo_parseaCorrectamente() throws Exception {
        String linea = construirLineaBase();
        // Salario grande: 999,999.99
        linea = reemplazar(linea, SindoLayout.SALARIO_BASE_RO, "99999999");

        SindoLineaDTO dto = parser.parseLinea(linea);

        assertNotNull(dto.salarioDiarioDestino);
        assertEquals("999999.99", dto.salarioDiarioDestino.toPlainString());
    }

    // ========================================
    // CASOS ESPECIALES - CAMPOS OPCIONALES
    // ========================================

    @Test
    public void parseLinea_conCIZ_parseaCorrectamente() throws Exception {
        // Crear línea con longitud suficiente para CIZ
        String linea = construirLineaBase();
        char[] extended = new char[SindoLayout.LONGITUD_MAXIMA];
        java.util.Arrays.fill(extended, ' ');
        System.arraycopy(linea.toCharArray(), 0, extended, 0, linea.length());

        String lineaExtendida = new String(extended);
        lineaExtendida = reemplazar(lineaExtendida, SindoLayout.CIZ, "2");

        SindoLineaDTO dto = parser.parseLinea(lineaExtendida);

        assertEquals("2", dto.ciz);
    }

    @Test
    public void parseLinea_sinCIZ_devuelveCIZNull() throws Exception {
        String linea = construirLineaBase();  // Longitud mínima, sin CIZ

        SindoLineaDTO dto = parser.parseLinea(linea);

        assertNull(dto.ciz);
    }

    // ========================================
    // HELPERS PARA CONSTRUCCIÓN DE LÍNEAS
    // ========================================

    /**
     * Construye una línea SINDO válida con datos mínimos.
     */
    private String construirLineaBase() {
        char[] buffer = new char[SindoLayout.LONGITUD_MINIMA];
        java.util.Arrays.fill(buffer, ' ');
        String base = new String(buffer);

        // NSS válido
        base = reemplazar(base, SindoLayout.NSS, "1234567890");
        base = reemplazar(base, SindoLayout.DIG_VR_NSS, "1");

        // Origen (Modalidad 40)
        base = reemplazar(base, SindoLayout.RP_ORIG_SUB, "31");
        base = reemplazar(base, SindoLayout.RP_ORIG_NUM, "A1234567");
        base = reemplazar(base, SindoLayout.DIG_VR_RP_ORIG, "4");
        base = reemplazar(base, SindoLayout.CVE_MOD_ORIG, "40");
        base = reemplazar(base, SindoLayout.NOM_PATRON_ORIG, "PATRON ORIGEN");
        base = reemplazar(base, SindoLayout.SALARIO_BASE_40, "00100000");
        base = reemplazar(base, SindoLayout.F_DD_BAJA_ORIG, "15");
        base = reemplazar(base, SindoLayout.F_MM_BAJA_ORIG, "03");
        base = reemplazar(base, SindoLayout.F_AAAA_BAJA_ORIG, "2024");
        base = reemplazar(base, SindoLayout.TP_MOVTO_ORIG, "08");

        // Destino (RO)
        base = reemplazar(base, SindoLayout.RP_DEST_SUB, "08");
        base = reemplazar(base, SindoLayout.RP_DEST_NUM, "B9876543");
        base = reemplazar(base, SindoLayout.DIG_VR_RP_DEST, "2");
        base = reemplazar(base, SindoLayout.CVE_MOD_DEST, "10");
        base = reemplazar(base, SindoLayout.NOM_PATRON_DEST, "PATRON DESTINO");
        base = reemplazar(base, SindoLayout.SALARIO_BASE_RO, "00120000");
        base = reemplazar(base, SindoLayout.F_DD_ALTA_DEST, "20");
        base = reemplazar(base, SindoLayout.F_MM_ALTA_DEST, "05");
        base = reemplazar(base, SindoLayout.F_AAAA_ALTA_DEST, "2024");
        base = reemplazar(base, SindoLayout.TP_MOVTO_DEST, "01");

        return base;
    }

    /**
     * Construye una línea SINDO válida con parámetros personalizados.
     */
    private String construirLineaValida(
            String nss10, String nssDigito,
            String ddBaja, String mmBaja, String aaaaBaja,
            String rpOrigNum, String modOrigen,
            String patronOrigen,
            String salarioMod40,
            String ddAlta, String mmAlta, String aaaaAlta,
            String rpDestNum, String modDestino,
            String patronDestino,
            String salarioRO) {

        char[] buffer = new char[SindoLayout.LONGITUD_MINIMA];
        java.util.Arrays.fill(buffer, ' ');
        String linea = new String(buffer);

        linea = reemplazar(linea, SindoLayout.NSS, nss10);
        linea = reemplazar(linea, SindoLayout.DIG_VR_NSS, nssDigito);

        linea = reemplazar(linea, SindoLayout.RP_ORIG_SUB, "31");
        linea = reemplazar(linea, SindoLayout.RP_ORIG_NUM, rpOrigNum);
        linea = reemplazar(linea, SindoLayout.DIG_VR_RP_ORIG, "4");
        linea = reemplazar(linea, SindoLayout.CVE_MOD_ORIG, modOrigen);
        linea = reemplazar(linea, SindoLayout.NOM_PATRON_ORIG, patronOrigen);
        linea = reemplazar(linea, SindoLayout.SALARIO_BASE_40, salarioMod40);
        linea = reemplazar(linea, SindoLayout.F_DD_BAJA_ORIG, ddBaja);
        linea = reemplazar(linea, SindoLayout.F_MM_BAJA_ORIG, mmBaja);
        linea = reemplazar(linea, SindoLayout.F_AAAA_BAJA_ORIG, aaaaBaja);
        linea = reemplazar(linea, SindoLayout.TP_MOVTO_ORIG, "08");

        linea = reemplazar(linea, SindoLayout.RP_DEST_SUB, "08");
        linea = reemplazar(linea, SindoLayout.RP_DEST_NUM, rpDestNum);
        linea = reemplazar(linea, SindoLayout.DIG_VR_RP_DEST, "1");
        linea = reemplazar(linea, SindoLayout.CVE_MOD_DEST, modDestino);
        linea = reemplazar(linea, SindoLayout.NOM_PATRON_DEST, patronDestino);
        linea = reemplazar(linea, SindoLayout.SALARIO_BASE_RO, salarioRO);
        linea = reemplazar(linea, SindoLayout.F_DD_ALTA_DEST, ddAlta);
        linea = reemplazar(linea, SindoLayout.F_MM_ALTA_DEST, mmAlta);
        linea = reemplazar(linea, SindoLayout.F_AAAA_ALTA_DEST, aaaaAlta);
        linea = reemplazar(linea, SindoLayout.TP_MOVTO_DEST, "01");

        return linea;
    }

    /**
     * Reemplaza un campo en una línea SINDO.
     */
    private String reemplazar(String linea, SindoLayout.Campo campo, String valor) {
        StringBuilder sb = new StringBuilder(linea);
        int start = campo.inicio;
        int end = Math.min(start + valor.length(), campo.fin);

        sb.replace(start, end, valor);
        return sb.toString();
    }
}
