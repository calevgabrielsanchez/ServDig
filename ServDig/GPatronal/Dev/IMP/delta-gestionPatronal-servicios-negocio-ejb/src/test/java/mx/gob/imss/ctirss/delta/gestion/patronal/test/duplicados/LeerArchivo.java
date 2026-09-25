package mx.gob.imss.ctirss.delta.gestion.patronal.test.duplicados;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class LeerArchivo {
    public static void main(String[] args) {
    	LeerArchivo lee = new LeerArchivo();
 //   	lee.leeArchivos();
    	lee.generaResumenReporte();
    }
    
    private void generaResumenReporte(){
       	System.out.println("::: Comenzando a revisar");
    	List<String> lineas = new ArrayList<String>();
    	List<String> folios = new ArrayList<String>();
    	
        try {
        	int cont = 1;
            Scanner iBDTU = new Scanner(new FileInputStream("c:\\salidasLogs\\ReporteDuplicados-16112023.dsv"));
            while (iBDTU.hasNextLine()) {
            	//System.out.println("Leyendo linea " + cont);
            	String linBDTU = iBDTU.nextLine();
//            	System.out.println(linBDTU);
                String[] lBDTU = linBDTU.split("\\|");
                folios.add(lBDTU[0]);
            }
            iBDTU.close();         
            Set<String> set = new LinkedHashSet<String>(folios);
            List<String> foliosUnicos = new ArrayList<String>(set);
            System.out.println("Folios unicos: " + foliosUnicos.size());

            for (Iterator<String> iterator = foliosUnicos.iterator(); iterator.hasNext();) {
				String folAct = iterator.next();			
				iBDTU = new Scanner(new FileInputStream("c:\\salidasLogs\\ReporteDuplicados-16112023.dsv"));
				List<String> rps = new ArrayList<String>();
				List<String> docs = new ArrayList<String>();
				String[] lineaOriginal = null;
	            while (iBDTU.hasNextLine()) {
	            	String linBDTU = iBDTU.nextLine();
	                String[] lBDTU = linBDTU.split("\\|");
	                if(lBDTU[0].equals(folAct)) {
	                	rps.add(lBDTU[2]);
	                	docs.add(lBDTU[6]);
		                if(lineaOriginal == null) {
		                	lineaOriginal = lBDTU;
		                }
	                }
	            }
	            iBDTU.close(); 
	            Set<String> rpsH = new LinkedHashSet<String>(rps);
	            Set<String> docsH = new LinkedHashSet<String>(docs);            
	            List<String> rpsU = new ArrayList<String>(rpsH);
	            List<String> docsU = new ArrayList<String>(docsH);
	            String rpS = "";
	            String docS = "";
	            for (Iterator<String> iterator2 = rpsU.iterator(); iterator2.hasNext();) {
					rpS += iterator2.next() + ",";				
				}
	            for (Iterator<String> iterator2 = docsU.iterator(); iterator2.hasNext();) {
	            	docS += iterator2.next() + ",";				
				}

	            rpS = rpS.substring(0, rpS.length()-1);
	            docS = docS.substring(0, docS.length()-1);
	            
	            lineaOriginal[2] = rpS;
	            lineaOriginal[6] = docS;
	            String lineaFinal = "";
	            for (int i = 0; i < lineaOriginal.length; i++) {
	            	lineaFinal += lineaOriginal[i];// + c
	            }
	            lineaFinal = lineaFinal.substring(0, lineaFinal.length()-1);
	            lineas.add(lineaFinal);
			}
            
            System.out.println("Genere " + lineas.size() + ", lineas finales");

//            for (Iterator<String> iterator = lineas.iterator(); iterator.hasNext();) {
//				String string = iterator.next();
//				System.out.println(string);
//			}
            
			File f;
			f = new File("c:\\\\salidasLogs\\\\duplicadosBDTU.txt");
			// Escritura
			try {
				FileWriter w = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(w);
				PrintWriter wr = new PrintWriter(bw);
				wr.write("Encabezado");// escribimos en el archivo
				
	            for (Iterator<String> iterator = lineas.iterator(); iterator.hasNext();) {
					String lin = (String) iterator.next();
					wr.append(lin +"\n"); // concatenamos en el archivo sin borrar lo existente
					//System.out.println(lin);
				}
				
				// ahora cerramos los flujos de canales de datos, al cerrarlos el archivo
				// quedará guardado con información escrita
				// de no hacerlo no se escribirá nada en el archivo
				wr.close();
				bw.close();
			} catch (IOException e) {
			}           
            
            
            
            System.out.println("::: Termine");
        } catch (Exception ex) {
            ex.printStackTrace();
        }    	
    }

    private void leeArchivos(){
       	System.out.println("::: Comenzando a revisar");
    	List<String> lista = new ArrayList<String>();
        try {
        	int cont = 1;
            Scanner iBDTU = new Scanner(new FileInputStream("c:\\salidasLogs\\duplicadosBDTU.txt"));
            while (iBDTU.hasNextLine()) {
            	//System.out.println("Leyendo linea " + cont);
            	String linBDTU = iBDTU.nextLine();
//            	System.out.println(linBDTU);
                String[] lBDTU = linBDTU.split("\\|");
//System.out.println(lBDTU.length);

                String rpBDTU = lBDTU[2];
            	Scanner iIDSE = new Scanner(new File("c:\\salidasLogs\\patronesIDSE.txt"));
            	boolean enc = false;
                while (iIDSE.hasNextLine()) {
                	String linIDSE = iIDSE.nextLine();
                    //String[] lIDSE = linIDSE.split("\\|");
                    //String rpIDSE = lIDSE[2];
                	if(rpBDTU.equals(linIDSE.trim())) {
                		lista.add(linBDTU + "|" + "SI");
                		enc = true;
                		break;
                	}
                }
                iIDSE.close();
                if(!enc) {
                	lista.add(linBDTU + "|" + "NO");
                }
                cont += 1;
            }
            iBDTU.close();         
            
            
			File f;
			f = new File("c:\\\\salidasLogs\\\\duplicadosBDTU-IDSE.txt");
			// Escritura
			try {
				FileWriter w = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(w);
				PrintWriter wr = new PrintWriter(bw);
				wr.write("Encabezado");// escribimos en el archivo
				
	            System.out.println("::: Lista final, tamaño: " + lista.size());
	            for (Iterator<String> iterator = lista.iterator(); iterator.hasNext();) {
					String lin = (String) iterator.next();
					wr.append(lin +"\n"); // concatenamos en el archivo sin borrar lo existente
					//System.out.println(lin);
				}
				
				// ahora cerramos los flujos de canales de datos, al cerrarlos el archivo
				// quedará guardado con información escrita
				// de no hacerlo no se escribirá nada en el archivo
				wr.close();
				bw.close();
			} catch (IOException e) {
			}
			;
            
            
            
            
            

            System.out.println("::: Termine");
        } catch (Exception ex) {
            ex.printStackTrace();
        }    	
    }

	public static void writeResult(String writeFileName, String text) {
		try {
			FileWriter fileWriter = new FileWriter(writeFileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.newLine();
			bufferedWriter.write(text);
			// Always close files.
			bufferedWriter.close();

		} catch (IOException ex) {
			System.out.println("Error writing to file '" + writeFileName + "'");
		}
	}

}