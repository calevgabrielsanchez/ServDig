package mx.gob.imss.ctirss.ws.asignacion.implementacion.exception; 

public class SindoException extends Exception { 
    static final long serialVersionUID=1L;
    private int codigoError;
    private String mensajeError;
  
    public SindoException(){
    }
    
    public SindoException(String msg, int code){
      super(code+" "+msg);
      this.codigoError = code;
      this.mensajeError = msg;
    }
    public SindoException(String msg){
      super(msg);
      this.mensajeError = msg;
    }
    public SindoException(int code){
      super(""+code);
      this.codigoError = code;
    }
    
    public void setMensaje(String msg){
      this.mensajeError = msg;
    }
    public String getMensaje(){
      return mensajeError;  
    }
    public void setCodigoError(int code){
      this.codigoError = code;
    }
    public int getCodigoError(){
      return codigoError;  
    }
} 