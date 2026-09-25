package mx.imss.ctirss.web.utils;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ValidaAnexo {
	   private Workbook workbook;
	    private Sheet sheet;
	    
	    private String folioAviso;
	    private Integer idAnexo;
	    
	    public ValidaAnexo(InputStream is,String folioAvisoEsperado,Integer idAnexoEsperado) throws BiffException, IOException, Exception{
	        
	        if(is==null) throw new Exception("Los par�metros otorgados no son v�lidos");
	        
	        this.workbook = Workbook.getWorkbook(is);
	        this.folioAviso = folioAvisoEsperado;
	        this.idAnexo = idAnexoEsperado;
	        
	    }
	   
	    public Boolean validaByFolioAvisoIdAnexo() throws Exception{
	        
	        sheet = workbook.getSheet(0);
	        
	        try{
	            sheet.getCell("A1").getContents();
	        }catch(Exception e){
	            throw new Exception("El folio no fue declarado dentro del excel: error 750");
	        }
	        
	        try{
	            sheet.getCell("A2").getContents();
	        }catch(Exception e){
	            throw new Exception("Tipo de Cedula no fue declarada dentro del excel: error 750");
	        }
	        
	        if(!sheet.getCell("A1").getContents().equalsIgnoreCase(folioAviso))
	            throw new Exception("El folio de correccion no coincide al ingresado en pantalla");
	        
	        if(!sheet.getCell("A2").getContents().equalsIgnoreCase(idAnexo.toString()))
	            throw new Exception("El tipo de Cedula Presentada no coincide al seleccionado en pantalla");
	        
	        return true;
	    }
}
