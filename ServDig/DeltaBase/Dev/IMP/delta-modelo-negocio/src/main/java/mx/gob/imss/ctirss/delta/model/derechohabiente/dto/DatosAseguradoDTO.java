package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.AseguradoDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.BeneficiarioDTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class DatosAseguradoDTO extends AbstractResponseExterno implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8591979280448637911L;
	
	private AseguradoDTO aseguradoDTO;
	
	private DatosAfiliacionDTO datosAfiliacionDTO;
	
	private Domicilio domicilioAsegurado;
	
	private List<MedioContactoDTO> lstMediosContactoAseguradoDTO;
	
	private List<BeneficiarioDTO> lstBeneficiariosDTO;

	public AseguradoDTO getAseguradoDTO() {
		return aseguradoDTO;
	}

	public void setAseguradoDTO(AseguradoDTO aseguradoDTO) {
		this.aseguradoDTO = aseguradoDTO;
	}

	public DatosAfiliacionDTO getDatosAfiliacionDTO() {
		return datosAfiliacionDTO;
	}

	public void setDatosAfiliacionDTO(DatosAfiliacionDTO datosAfiliacionDTO) {
		this.datosAfiliacionDTO = datosAfiliacionDTO;
	}
	
	public Domicilio getDomicilioAsegurado() {
		return domicilioAsegurado;
	}

	public void setDomicilioAsegurado(Domicilio domicilioAsegurado) {
		this.domicilioAsegurado = domicilioAsegurado;
	}

	public List<MedioContactoDTO> getLstMediosContactoAseguradoDTO() {
		return lstMediosContactoAseguradoDTO;
	}

	public void setLstMediosContactoAseguradoDTO(List<MedioContactoDTO> lstMediosContactoAseguradoDTO) {
		this.lstMediosContactoAseguradoDTO = lstMediosContactoAseguradoDTO;
	}

	public List<BeneficiarioDTO> getLstBeneficiariosDTO() {
		return lstBeneficiariosDTO;
	}

	public void setLstBeneficiariosDTO(List<BeneficiarioDTO> lstBeneficiariosDTO) {
		this.lstBeneficiariosDTO = lstBeneficiariosDTO;
	}


}
