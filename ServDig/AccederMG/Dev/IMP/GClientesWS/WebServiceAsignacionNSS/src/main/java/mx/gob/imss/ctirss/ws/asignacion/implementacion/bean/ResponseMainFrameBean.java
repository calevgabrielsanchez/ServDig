/*
 * Created on 26/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package mx.gob.imss.ctirss.ws.asignacion.implementacion.bean;


/**
 * @author juancho
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResponseMainFrameBean { 
	  
	  private String errorMessage="";
	  private int errorNumber=0;
	  private String strResultado ="";
	 

	  public void setErrorMessage(String msg){
	    this.errorMessage =msg;  
	  }
	  public String getErrorMessage(){
	    return this.errorMessage;
	  }
	  public void setErrorNumber(int no){
	    this.errorNumber =no;  
	  }
	  public int getErrorNumber(){
	    return this.errorNumber;
	  }
	/**
	 * @param strResultado The strResultado to set.
	 */
	
     public void setStrResultado(String strResultado) {
        this.strResultado = this.strResultado + "|" + strResultado;
    }
	/**
	 * @return Returns the strResultado.
	 */
	public String getStrResultado() {
		return strResultado;
	}
	} 