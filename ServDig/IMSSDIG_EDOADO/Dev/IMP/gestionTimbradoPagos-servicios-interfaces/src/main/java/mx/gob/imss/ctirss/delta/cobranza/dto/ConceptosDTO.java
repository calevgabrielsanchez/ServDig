package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConceptosDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConceptoDTO[] conceptos;

    public ConceptoDTO[] getConceptos() {
        
        return conceptos;
    }

    public void setConceptos(ConceptoDTO[] conceptos) {
        this.conceptos = conceptos;
    }    
}
