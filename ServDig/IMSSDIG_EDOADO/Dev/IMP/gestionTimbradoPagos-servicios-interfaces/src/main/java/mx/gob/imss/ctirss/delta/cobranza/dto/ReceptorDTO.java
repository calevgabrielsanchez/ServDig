package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;

public class ReceptorDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private UbicacionFiscalDTO domicilio;
    private String rfc;
    private String nombre;
    private String usoCFDI;

//    public UbicacionFiscalDTO getDomicilio() {
//        return domicilio;
//    }
//
//    public void setDomicilio(UbicacionFiscalDTO domicilio) {
//        this.domicilio = domicilio;
//    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	public String getUsoCFDI() {
		return usoCFDI;
	}

	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}
	
}
