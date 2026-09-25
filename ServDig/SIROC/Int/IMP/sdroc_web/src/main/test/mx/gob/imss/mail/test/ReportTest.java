/**
 * 
 */
package mx.gob.imss.mail.test;

import java.util.HashMap;

import mx.gob.imss.csdiss.sdroc.util.GeneraReporte;
import mx.gob.imss.csdiss.sdroc.util.ParametroReporte;

/**
 * @author daniel.hernandez
 *
 */
public class ReportTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put(ParametroReporte.IMAGE_DIR.getValor(), "C:\\Users\\daniel.hernandez\\Documents\\Registro Obras\\backup_reg_obra\\establo\\SDROC\\src\\main\\webapp\\static\\images\\");
		param.put(ParametroReporte.NUMERO_REGISTRO.getValor(), "152222110001");
		param.put(ParametroReporte.NUMERO_ACUERDO.getValor(), "XXX");
		param.put(ParametroReporte.DIA_ACUERDO.getValor(), "XXX");
		param.put(ParametroReporte.NUMERO_ARTICULO.getValor(), "XXX");
		param.put(ParametroReporte.DELEGACION.getValor(), "Chiapas");
		param.put(ParametroReporte.SUB_DELEGACION.getValor(), "Tapachula");
		param.put(ParametroReporte.RAZON_SOCIAL.getValor(), "CCC Fabricaciones y contrucciones SA de Cv");
		param.put(ParametroReporte.REGISTRO_PATRONAL.getValor(), "A6532106105");
		param.put(ParametroReporte.RFC.getValor(), "CFC920305");
		param.put(ParametroReporte.TIPO_PATRON.getValor(), "1");
		param.put(ParametroReporte.CLASE_OBRA.getValor(), "1".equals("1") ? "Privada" : "Publica");
		param.put(ParametroReporte.TIPO_OBRA_TITULO.getValor(), "Intermediario".equals("Intermediario") ? "4" : "1");
		param.put(ParametroReporte.TIPO_OBRA.getValor(), "1".equals("1") ? "Privada" : "Publica");
		param.put(ParametroReporte.UBICACION.getValor(), "aqui");
		param.put(ParametroReporte.TITULO_PERIODO.getValor(),"4");
		param.put(ParametroReporte.FEC_INICIO.getValor(), "12/10/2016");
		param.put(ParametroReporte.FEC_TERMINO.getValor(), "10/12/2017");
		param.put(ParametroReporte.TITULO_MONTO.getValor(), "Intermediario".equals("Intermediario") ? "4" : "1");
		param.put(ParametroReporte.MONTO.getValor(), new Double(200));
		param.put(ParametroReporte.SUPERFICIE.getValor(), (short)1);
		param.put(ParametroReporte.NUMERO_PROCEDIMIENTO.getValor(), "1232345346r5756768");
		param.put(ParametroReporte.SELLO_DIGITAL_ACUSE.getValor(),
				"asjdasdjasaodasdaosd293292398209482049sdlajsdlkajskldja");
		param.put(ParametroReporte.IMAGEN_QR.getValor(), "QR.jpg");
		param.put(ParametroReporte.CADENA_ORIGINAL_ACUSE.getValor(),
				"asjdasdjasaodasdaosd293292398209482049sdlajsdlkajskldja");
		param.put(ParametroReporte.CADENA_FIRMA.getValor(),
				"asjdasdjasaodasdaosd293292398209482049sdlajsdlkajskldja");
		GeneraReporte generadorReporte = new GeneraReporte();
		
		try {
		String a=	generadorReporte.generaReportePDF("C:\\Users\\daniel.hernandez\\Documents\\Registro Obras\\backup_reg_obra\\establo\\SDROC\\src\\main\\webapp\\static\\report\\","registroObraAcuse", "ACU", param);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		}

}
