
package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddendasDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<AddendaDTO> addendas;

    public List<AddendaDTO> getAddendas() {
        if(addendas== null){
            addendas = new ArrayList<AddendaDTO>();
        }
        return addendas;
    }

    public void setAddendas(List<AddendaDTO> addendas) {
        this.addendas = addendas;
    }
}
