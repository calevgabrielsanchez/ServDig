package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="emisor")
public class EmisorDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private UbicacionFiscalDTO domicilioFiscal;
//    private UbicacionFiscalDTO expedidoEn;
//    private RegimenDTO[] regimenFiscal;
    private String rfc;
    private String nombre;
    private String regimenFiscal;

//    public UbicacionFiscalDTO getDomicilioFiscal() {
//        return domicilioFiscal;
//    }
//
//    public void setDomicilioFiscal(UbicacionFiscalDTO domicilioFiscal) {
//        this.domicilioFiscal = domicilioFiscal;
//    }
//
//    public UbicacionFiscalDTO getExpedidoEn() {
//        return expedidoEn;
//    }
//
//    public void setExpedidoEn(UbicacionFiscalDTO expedidoEn) {
//        this.expedidoEn = expedidoEn;
//    }
//
//    public RegimenDTO[] getRegimenFiscal() {
//        
//        return regimenFiscal;
//    }
//
//    public void setRegimenFiscal(RegimenDTO[] regimenFiscal) {
//        this.regimenFiscal = regimenFiscal;
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

	public String getRegimenFiscal() {
		return regimenFiscal;
	}

	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}
    
}
