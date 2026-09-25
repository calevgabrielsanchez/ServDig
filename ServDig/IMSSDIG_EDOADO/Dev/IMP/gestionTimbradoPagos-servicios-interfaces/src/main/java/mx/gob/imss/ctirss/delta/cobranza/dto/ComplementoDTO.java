
package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComplementoDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Object> complementos;

    public List<Object> getComplementos() {
        if(complementos==null){
            complementos = new ArrayList<Object>();
        }
        return complementos;
    }

    public void setComplementos(List<Object> complementos) {
        this.complementos = complementos;
    }
    
    
}
