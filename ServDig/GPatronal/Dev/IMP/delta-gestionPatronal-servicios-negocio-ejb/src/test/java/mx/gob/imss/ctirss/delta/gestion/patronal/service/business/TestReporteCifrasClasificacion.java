package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import mx.gob.imss.ctirss.delta.gestion.patronal.test.ReporteCifrasClasificacion;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.ReporteCifrasDTO;

public class TestReporteCifrasClasificacion {
	
	private BufferedReader obj;
	private StringBuffer datosTabla = new StringBuffer();
	private ReporteCifrasDTO datoTabla = null;	

	public static void main(String[] args) {
		ReporteCifrasClasificacion test = new ReporteCifrasClasificacion();
		test.obtenerReporteCifras();

	}

	//@Test
	public void obtenerReporteCifras() {
		
		System.out.println("INFO SE ESTA CREANDO EL TEST");
		String nameFile = "Fusion"+".txt";
		String urlExcel = "C:\\Users\\jonathan.sanchez\\Downloads\\"+nameFile;
		try {
			obtenerDatosCifras(urlExcel);
			
			sendText(datosTabla.toString(), nameFile);
			System.out.println("CREADO EL DOCUMENTO");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
	
	private void obtenerDatosCifras(String rutaExcel) throws IOException{
		File f = new File(rutaExcel);
		
//		obj = new BufferedReader(new FileReader(f));
//		
//		Object[] lines = obj.lines().toArray();
//		
//	
//		for(int i = 1; i < lines.length;i++) {
//			
//			String[] row = lines[i].toString().split("\\|");
//			this.generarFila1(row, i);
//			this.generarFila(row);
//			
//		}
	
	}
	
	private void sendText(String contenido, String nameFile){
        try {
            String ruta = "C://Users/jonathan.sanchez/Downloads/corregido_"+nameFile;
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	private void generarFila(String[] row) {
		ReporteCifrasDTO datoTabla = null;

		for (int j = 13; j < row.length; j++) {

			if(j >= 67) { break; }
			

			datoTabla  = new ReporteCifrasDTO((String) row[0], (String) row[1], (String) row[2],
					(String)row[3] , (String) row[4] , (String) row[5],
					(String) row[6], (String) row[67], (String) row[68]);
			
			if(row[j]!=null && !row[j].isEmpty()) {

				datoTabla.setRegistroPatronalSustituido((String) row[j++]);
				datoTabla.setClasePatSustituido((String) row[j++]);
				datoTabla.setFraccionPatSustituido((String)row[j++]);
				datoTabla.setPrimaPatSustituido((String)row[j++]);
				datoTabla.setDelegacionPatSustituido((String) row[j++]);
				datoTabla.setSubDelegacionPatSustituido((String) row[j]);
			
				datosTabla.append(datoTabla.toString());
				}
			else {
				break;
			}
			
		}
		
	}
	
	
	private void generarFila1(String[] row, int i) {
		System.out.println("FILA RECORRIDA [" + (i+1)+"]");
		datoTabla  = new ReporteCifrasDTO((String) row[0], (String) row[1], (String) row[2],
				(String)row[3] , (String) row[4] , (String) row[5],
				(String) row[6], (String) row[67], (String) row[68]);
		
		if(row[7] != null && !row[7].isEmpty()) {
			this.datoTabla.setRegistroPatronalSustituido((String) row[7]);
			this.datoTabla.setClasePatSustituido((String) row[8]);
			this.datoTabla.setFraccionPatSustituido((String) row[9]);
			this.datoTabla.setPrimaPatSustituido((String) row[10]);
			this.datoTabla.setDelegacionPatSustituido((String) row[11]);
			this.datoTabla.setSubDelegacionPatSustituido((String) row[12]);
		
			datosTabla.append(datoTabla.toString());
		} else {
			datosTabla.append(datoTabla.toString());
		}
	}	
	
	

}
