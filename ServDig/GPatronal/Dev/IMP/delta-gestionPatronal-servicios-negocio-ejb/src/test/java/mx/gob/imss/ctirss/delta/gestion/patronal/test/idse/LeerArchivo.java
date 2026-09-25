package mx.gob.imss.ctirss.delta.gestion.patronal.test.idse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class LeerArchivo {
    public static void main(String[] args) {
    	List<String> fisL = new ArrayList<String>();
    	List<String> morL = new ArrayList<String>();
        try {
            Scanner input = new Scanner(new File("c:\\salidasLogs\\sincronizaIDSE.txt"));
            int contE = 0;
            int contNE = 0;
            while (input.hasNextLine()) {
            	String lin = input.nextLine();
                String[] l = lin.split("\\|");
                if(l[3].equals("-----")){ // si es persona fisica
                	//busco serial en pf
                	Scanner  iF = new Scanner(new File("c:\\salidasLogs\\pf.txt"));
                	boolean enc = false;
                	while (iF.hasNextLine()) {
                		String[] lf = iF.nextLine().split("\\|");
                		if(lf[1].equals(l[0])){
                			fisL.add(lin + "|" + lf[0]);
                			enc = true;
                			contE +=1;
                			break;
                		}
                	}
            		if(!enc){
            			fisL.add(lin + "|-----");
            			contNE += 1;
            		}
                	iF.close();
                }else{ //si es moral
                	//busco serial en pm
                	Scanner iM = new Scanner(new File("c:\\salidasLogs\\pm.txt"));
                	boolean enc = false;
                	while (iM.hasNextLine()) {
                		String[] lm = iM.nextLine().split("\\|");
                		if(lm[3].equals(l[3])){
                			morL.add(lin + "|" + lm[1] + "|" + lm[0]);
                			enc = true;
                			contE += 1;
                			break;
                		}                		
                	}
                	if(!enc){
                		morL.add(lin + "|-----|-----");
                		contNE += 1;
                	}
                	iM.close();
                }
            }
            input.close();
            
            System.out.println("contE: " + contE);
            System.out.println("contNE: " + contNE);
            
            System.out.println(":::::::::::::: Lineas PF");
            for (Iterator<String> iterator = fisL.iterator(); iterator.hasNext();) {
    			StringBuffer linB = new StringBuffer();
    			linB.append(iterator.next());			
    			writeResult("C:\\salidasLogs\\sincronizaIDSE-Serial.txt", linB.toString());		
			}
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println(":::::::::::::: Lineas PM");
            for (Iterator<String> iterator = morL.iterator(); iterator.hasNext();) {
    			StringBuffer linB = new StringBuffer();
    			linB.append(iterator.next());			
    			writeResult("C:\\salidasLogs\\sincronizaIDSE-Serial.txt", linB.toString());		
			}            
            
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