package mx.gob.imss.distss.delta.rtt.service.business;

import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.RespuestaEscrito;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.distss.delta.rtt.service.entity.ConsultaEscritoDesacuerdoLocal;
import mx.gob.imss.distss.delta.rtt.service.interfaces.EscritoDesacuerdoExternoRemote;

import org.apache.commons.lang.StringUtils;

@Stateless(name = "escritoDesacuerdoExterno", mappedName= "escritoDesacuerdoExterno")
public class EscritoDesacuerdoExterno implements EscritoDesacuerdoExternoRemote {

	@EJB(name = "documentoProbatorioServiceBusiness",mappedName = "documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@EJB
	private ConsultaEscritoDesacuerdoLocal consultaEscritoDesacuerdoLocal;
	//expresion regular para validar el formato del folio de recepcion
	private final String REG_EX_FOLIO_RECEPCION =  "ED\\-L\\-\\d{4}\\-\\d{4}\\/\\d{2}";
	
	@Override
	public RespuestaEscrito consultarInfoEscrito(String folioRecepcion) throws RiesgosTrabajoException {
		//respuesta del servicio
		RespuestaEscrito respuesta = new RespuestaEscrito();
		TramiteEscritoDesacuerdo tramiteEscrito = null;
		
		if(StringUtils.isBlank(folioRecepcion)) {
			RiesgosTrabajoException.throwException(1, "El folio de recepcion no puede ser nulo o vacio");
		}
		
		if(folioRecepcion.length() != 17) {
			RiesgosTrabajoException.throwException(1, "El folio de recepcion debe ser de 17 posiciones");
		}
		
		if(!Pattern.matches(REG_EX_FOLIO_RECEPCION, folioRecepcion)){
			RiesgosTrabajoException.throwException(1, "El formato del folio de recepcion es incorrecto");
		}
		
		
		tramiteEscrito = consultaEscritoDesacuerdoLocal.getEscritoSimple(folioRecepcion);
		
		if(tramiteEscrito == null) {
			RiesgosTrabajoException.throwException(2,"No se encontro informacion relacionada al folio " + folioRecepcion);
		}
		//variable para guardar los documentos obtenidos
		List<DocumentoProbatorio> documentos = null;
		//obtenemos la lista de documentos que se adjuntaron y que se generaron
		documentos = documentoProbatorioServiceBusinessRemote.listaDocumentosProbatoriosTramite(tramiteEscrito.getTramiteId());
		//seteamos la lista de documentos en la respuesta
		respuesta.setDocumentos(documentos);
		respuesta.setMotivoDesacuerdo(tramiteEscrito.getMotivoDesacuerdo());
		
		return respuesta;
	}

}
