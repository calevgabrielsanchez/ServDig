package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;

import org.junit.Test;

public class DocumentosTest {
	@Test
	public void registrarDocumentos() throws Exception{
		System.out.println("registrarDocumentos. Inicio " + new Date());
		
		List<DocumentoProbatorio> documentos = new ArrayList<DocumentoProbatorio>();

		//ACTA DE NACIMIENTO
		documentos.add(DatosDocumento.getActaNacimiento());
		
		//CURP
		documentos.add(DatosDocumento.getCurpRenapo());
		
		//DA DE ALTA LOS DOCUMENTOS
		List<DocumentoProbatorio> documentosGuardados = EJBLocator.getServiceBusiness().registrarDocumentos(documentos);

		//SE IMPRIMEN LOS RESULTADOS DEL ALTA
		Iterator<DocumentoProbatorio> it = documentosGuardados.iterator();
		DocumentoProbatorio doc = null;
		while (it.hasNext()) {
			doc = it.next();
			System.out.println(doc.toString());
			System.out.println(doc.getIdDocumentoProbatorio());
		}
		
		System.out.println("registrarDocumentos. Final " + new Date());
	}	
}
