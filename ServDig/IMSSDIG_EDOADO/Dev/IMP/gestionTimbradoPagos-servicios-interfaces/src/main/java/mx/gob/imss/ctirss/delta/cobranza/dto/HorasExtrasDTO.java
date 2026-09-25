
package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;

public class HorasExtrasDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HorasExtraDTO[] horasExtras;

    public HorasExtraDTO[] getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(HorasExtraDTO[] horasExtras) {
        this.horasExtras = horasExtras;
    }
    
    
}
