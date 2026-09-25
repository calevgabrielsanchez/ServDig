package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class PdfUtils {
	
	private static final String FONT1=BaseFont.HELVETICA;
	private static final String FONT2=BaseFont.WINANSI;
	private static final boolean ENBEDED=true;
	private static final int FONTSIZE=50;
	private static final float X=5;
	private static final float Y=5;
	private static final String ENC_TYPE_MD5="MD5";
	private static final String FILE_TYPE_PDF="pdf";
	
	
	public static String getMD5(byte[] doc)  {
        String mD5=null;
        MessageDigest mD=null;
		try {
			mD = MessageDigest.getInstance(ENC_TYPE_MD5);
			mD.reset();
			mD.update(doc);
			
			
			BigInteger number = new BigInteger(1,mD.digest());
			mD5=number.toString(16).substring(0, 20);
							
    	}catch (Exception e){

    		e.printStackTrace();
    	}
    	return mD5;
    }
    
 
    public static String getProductoMD5(byte[] digitalizacion){
    	StringBuffer mD5=new StringBuffer();
    	mD5.append(getMD5(digitalizacion));
    	return mD5.toString();
    }
   

    
    public static boolean compareMD5Names(byte[] digitalizacion,String md5){
    	boolean resp=false;
    	String originalName=md5;
    	if(originalName!=null){
    		String compName = getProductoMD5(digitalizacion);
    		if(originalName.equals(compName)){
    			resp=true;
    		}
    	}	
    	return resp;
    }


	public static byte[] analizeTypeToTransformImgtoPdf(String tipoDoc,byte[] digitalizacion){	
		tipoDoc=tipoDoc.toLowerCase();
		//Si no es pdf
		if(!tipoDoc.equals(FILE_TYPE_PDF)){
			digitalizacion=convertImageToPdf(digitalizacion);
			
		}
		return digitalizacion;
	}
	
	public static byte[]  addWatermark(byte[] doc, byte[] image,String texto){
		 
			
			OutputStream out=null;
			byte[] result=doc;
		    try 
		    {
		    	
		      out=new ByteArrayOutputStream();
		      PdfReader reader = new PdfReader(doc);
		      int n = reader.getNumberOfPages();

		      // Create a stamper that will copy the document to a new file
		      PdfStamper stamp = new PdfStamper(reader, out);
		      int i = 1;
		      //PdfContentByte under;
		      PdfContentByte over;

		      Image img = null;
		      img = Image.getInstance(image);
		 
		      BaseFont bf = BaseFont.createFont(FONT1, FONT2, ENBEDED);

		      img.setAbsolutePosition(X, Y);
		      img.setTransparency(new int[]{ 0xF0, 0xFF});
		      while (i < n+1) 
		      {
		        // Watermark under the existing page
		        //under = stamp.getUnderContent(i);
		       // under.addImage(img);
		      
		        // Text over the existing page
		        over = stamp.getOverContent(i);
		       
		        over.addImage(img);//add image
		        over.beginText();
		        over.setFontAndSize(bf, FONTSIZE);
		        over.showText(texto);
		        over.endText();
		        i++;
		      }
		     
		      reader.close();
		      out.close();
		      stamp.close();
		    
		      result=convertFileOutputStreamToByteArray(out);
		      
		    }
		    catch (Exception e) 
		    {
		
		    	e.printStackTrace();
		    	
		    } 
		    return  result;
		  }
	
	
	public static byte[] convertImageToPdf(byte[] imgBytes) {
		
		byte[] result=imgBytes;
	
		try{
			OutputStream outFile=new ByteArrayOutputStream();
			
			Document document = new Document();  
			//Get the input image to Convert to PDF
		    Image image=Image.getInstance(imgBytes);
		    //scala la imagen a que entre en el documento sin deformarla
		    image.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());
		    image.setAbsolutePosition(0, 0);
			PdfWriter.getInstance(document, outFile);            
			document.open();               
			document.add(image);
			document.close();

		    result=	convertFileOutputStreamToByteArray(outFile);
		}
		catch (Exception e){
		    

		    e.printStackTrace();
		}
		return result;
	}

	private static byte[] convertFileOutputStreamToByteArray(OutputStream out){
		return ((ByteArrayOutputStream)out).toByteArray();
	}
	
	
}
