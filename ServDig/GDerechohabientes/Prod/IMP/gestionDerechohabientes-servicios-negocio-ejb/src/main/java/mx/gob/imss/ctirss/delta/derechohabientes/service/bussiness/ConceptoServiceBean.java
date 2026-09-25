package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.jws.WebMethod;
import javax.jws.WebService;

import mx.gob.imss.ctirss.delta.model.derechohabientes.ConceptoDTO;


@WebService(name = "ConceptoServiceBean", serviceName = "ConceptoServiceBean", targetNamespace = "urn:ConceptoServiceBean")
public class ConceptoServiceBean { // implements IConceptoService {

	public ConceptoServiceBean() {

	}

	@WebMethod
	public String procesa(final String xml) {
		// TODO Auto-generated method stub
		return "Hi!";
	}

	@WebMethod
	public ConceptoDTO procesaDTO(final ConceptoDTO dto) {
		// TODO Auto-generated method stub
		dto.setNombre(dto.getNombre().concat("Modificado en EJB."));
		return dto;
	}

}
