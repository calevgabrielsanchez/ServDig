package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;

public class DomicilioCorto extends Domicilio implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Subdelegacion subdelegacion;
	private MotivoAclaracionVO motivoAclaracionVO;

	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}

	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	public MotivoAclaracionVO getMotivoAclaracionVO() {
		return motivoAclaracionVO;
	}

	public void setMotivoAclaracionVO(MotivoAclaracionVO motivoAclaracionVO) {
		this.motivoAclaracionVO = motivoAclaracionVO;
	}

}
