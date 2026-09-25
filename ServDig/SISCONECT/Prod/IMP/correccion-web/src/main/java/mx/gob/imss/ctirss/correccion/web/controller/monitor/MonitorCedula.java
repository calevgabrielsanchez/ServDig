package mx.gob.imss.ctirss.correccion.web.controller.monitor;

import java.util.List;

import mx.gob.imss.ctirss.correccion.model.CrtControlFlujoCedula;

public class MonitorCedula {
	
	private String folioCorreccion;
	private String idArchivoCarga;
	private String method;
    private String mensaje;
    private Integer periodo;
    
    
    private List<CrtControlFlujoCedula> historialAnexos;
	
	
	public String getFolioCorreccion() {
		return folioCorreccion;
	}

	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}
	
	public String getIdArchivoCarga() {
		return idArchivoCarga;
	}

	public void setIdArchivoCarga(String idArchivoCarga) {
		this.idArchivoCarga = idArchivoCarga;
	}

	  
    public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<CrtControlFlujoCedula> getHistorialAnexos() {
		return historialAnexos;
	}

	public void setHistorialAnexos(List<CrtControlFlujoCedula> historialAnexos) {
		this.historialAnexos = historialAnexos;
	}

	  /*METODOS VIRTUALES*/
	 /**Cedula A
     * */
	 public CrtControlFlujoCedula getCedulaA(){
	        if(getHistorialAnexos().size()>0)
	            return getHistorialAnexos().get(0);
	        else return new CrtControlFlujoCedula();
	    }
	   
	  /**Cedula G
	     * */
	    public CrtControlFlujoCedula getCedulaG(){
	        if(getHistorialAnexos().size()>1)
	            return getHistorialAnexos().get(1);
	        else return new CrtControlFlujoCedula();
	    }
	    
	    /**Cedula H
	     * */
	    public CrtControlFlujoCedula getCedulaH(){
	        if(getHistorialAnexos().size()>2)
	            return getHistorialAnexos().get(2);
	        else return new CrtControlFlujoCedula();
	    }
	    
	    /**Cedula I
	     * */
	    public CrtControlFlujoCedula getCedulaI(){
	        if(getHistorialAnexos().size()>3)
	            return getHistorialAnexos().get(3);
	        else return new CrtControlFlujoCedula();
	    }
	    
	    /**Cedula O
	     * */
	    public CrtControlFlujoCedula getCedulaO(){
	        if(getHistorialAnexos().size()>4)
	            return getHistorialAnexos().get(4);
	        else return new CrtControlFlujoCedula();
	    }
	    
	    
	    /**Cedula Q
	     * */
	    public CrtControlFlujoCedula getCedulaQ(){
	        if(getHistorialAnexos().size()>5)
	            return getHistorialAnexos().get(5);
	        else return new CrtControlFlujoCedula();
	    }
	    
	    /**Cedula Trabajadores
	     * */
	    public CrtControlFlujoCedula getCedulaTrabajadores(){
	        if(getHistorialAnexos().size()>6)
	            return getHistorialAnexos().get(6);
	        else return new CrtControlFlujoCedula();
	    }
	    
	    /**COPs Pagadas
	     * */
	    public CrtControlFlujoCedula getCedulaCopsPagadas(){
	        if(getHistorialAnexos().size()>7)
	            return getHistorialAnexos().get(7);
	        else return new CrtControlFlujoCedula();
	    }

		public Integer getPeriodo() {
			return periodo;
		}

		public void setPeriodo(Integer periodo) {
			this.periodo = periodo;
		}
	
}
