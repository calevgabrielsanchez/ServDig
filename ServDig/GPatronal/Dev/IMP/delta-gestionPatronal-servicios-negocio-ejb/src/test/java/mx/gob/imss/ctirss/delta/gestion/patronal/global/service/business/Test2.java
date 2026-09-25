package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Test2 {

	public static void main(String[] args) {




        String numeroComoTexto = "2.593"; // ejemplo con solo 2 decimales

        // Convertir el String a BigDecimal
        BigDecimal bd = new BigDecimal(numeroComoTexto);

        // Truncar o completar a 5 decimales sin redondear
        bd = bd.setScale(5, RoundingMode.DOWN);

        // Formatear como cadena con 5 decimales fijos
        DecimalFormat df = new DecimalFormat("0.00000");
        String resultado = df.format(bd.doubleValue());

        System.out.println("Número formateadooo: " + resultado);


	}

}
