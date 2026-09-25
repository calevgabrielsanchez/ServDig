package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoPorTipo;

@Stateless
public class DocumentoPorTipoConversor implements
		DocumentoPorTipoConversorLocal {

	@Override
	public DocumentoPorTipo convertirEntityToModel(DitDocumentoPorTipo documento) {
		DocumentoPorTipo documentoPorTipo = null;
		
		if(documento != null) {
			documentoPorTipo = new DocumentoPorTipo();
			documentoPorTipo.setIdDocumentoPorTipo(documento.getCveIdDoctoProbPorTipo());
			documentoPorTipo.setDocumento(new Documento());
			documentoPorTipo.getDocumento().setCveIdDocumento(documento.getDicDocumento().getCveIdDocumento());
			documentoPorTipo.getDocumento().setDesDocumento(documento.getDicDocumento().getDesDocumento());
		}
		
		return documentoPorTipo;
	}

	@Override
	public List<DocumentoPorTipo> convertirEntityToModelList(
			List<DitDocumentoPorTipo> lista) {
		List<DocumentoPorTipo> listaDocumentos = new ArrayList<DocumentoPorTipo>();
		
		if(lista != null) {
			for(DitDocumentoPorTipo ditDocumento: lista) {
				listaDocumentos.add(this.convertirEntityToModel(ditDocumento));
			}
		}
		return listaDocumentos;
	}

}
