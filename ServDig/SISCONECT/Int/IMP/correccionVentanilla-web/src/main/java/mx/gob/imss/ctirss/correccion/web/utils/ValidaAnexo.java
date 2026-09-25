package mx.gob.imss.ctirss.correccion.web.utils;

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
	    private Integer cveEjercicio;
	    
	    //private String HOJA_VALIDADA= "3";
	    private final String HOJA_VALIDADA_CON_CAMBIOS= "2";
	    private final String HOJA_SIN_VALIDADAR= "1";
	    
	    
	    public ValidaAnexo(InputStream is,String folioAvisoEsperado,Integer idAnexoEsperado) throws BiffException, IOException, Exception{
	        
	        if(is==null) throw new Exception("Los parámetros otorgados no son válidos");
	        
	        this.workbook = Workbook.getWorkbook(is);
	        this.folioAviso = folioAvisoEsperado;
	        this.idAnexo = idAnexoEsperado;
	        
	    }
	   
	    public Boolean validaByFolioAvisoIdAnexo() throws Exception{
	        
	        sheet = workbook.getSheet(0);
	        
	        try{
	            sheet.getCell("A1").getContents();
	        }catch(Exception e){
	            throw new Exception("El folio no fue declarado dentro del excel");
	        }
	        
	        try{
	            sheet.getCell("A2").getContents();
	        }catch(Exception e){
	            throw new Exception("Tipo de Cédula no fue declarada dentro del excel");
	        }
	        
	        if(!sheet.getCell("A1").getContents().equalsIgnoreCase(folioAviso))
	            throw new Exception("El folio de corrección no coincide al ingresado en pantalla");
	        
	        if(!sheet.getCell("A2").getContents().equalsIgnoreCase(idAnexo.toString()))
	            throw new Exception("El tipo de Cédula Presentada no coincide al seleccionado en pantalla");
	        
	        if(sheet.getCell("A30")==null)
	            throw new Exception("La versión del archivo de excel descargado no es la última, favor de descargarlo nuevamente");
	        else if(sheet.getCell("A30").getContents().equalsIgnoreCase(this.HOJA_SIN_VALIDADAR))
	            throw new Exception("El archivo no ha sido validado de forma correcta, favor de validar cada una de las hojas");
	        else if(sheet.getCell("A30").getContents().equalsIgnoreCase(this.HOJA_VALIDADA_CON_CAMBIOS))
	            throw new Exception("Se han detectado cambios en la información posterior a su validación, favor de validar las hojas nuevamente");
	      
		     
	        
	        if(sheet.getCell("L7")==null || sheet.getCell("L7").getContents().equalsIgnoreCase(""))
	            throw new Exception("El ejercicio de su archivo no fue registrado, descargar nuevamente el archivo.");
	        
	        this.cveEjercicio = new Integer(sheet.getCell("L7").getContents());
	        
	        return true;
	    }

		public Integer getCveEjercicio() {
			return cveEjercicio;
		}

		public void setCveEjercicio(Integer cveEjercicio) {
			this.cveEjercicio = cveEjercicio;
		}
}