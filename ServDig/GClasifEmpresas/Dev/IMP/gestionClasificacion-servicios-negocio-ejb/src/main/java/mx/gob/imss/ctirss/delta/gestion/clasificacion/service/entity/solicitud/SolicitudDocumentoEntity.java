/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:SolicitudDocumentoEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.solicitud
 *  @Fecha:25/10/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.solicitud;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.clasificacion.DocumentosAnalisis;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoPorTipo;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;

@Stateless
public class SolicitudDocumentoEntity extends AbstractServiceEntity implements SolicitudDocumentoEntityLocal{

	@SuppressWarnings("unchecked")
	@Override
	public DocumentosAnalisis obtenerDocumento(Long idSolicitud){
		DocumentosAnalisis documentosAnalisis=new DocumentosAnalisis();

		/*
		//Documento Aviso
		documentosAnalisis2=obtenerComprobanteTramite(idSolicitud);
		documentosAnalisis.setIdAviso(documentosAnalisis2.getIdAviso());
		documentosAnalisis.setRefAviso(documentosAnalisis2.getRefAviso());
		
		//Documento CLEM
		//
		documentosAnalisis.setIdClem(documentosAnalisis2.getIdClem());
		documentosAnalisis.setRefClem(documentosAnalisis2.getRefClem());
		
		*/
		
		
		return documentosAnalisis;
	}

	/**
	 * Método de Prueba para Insertar un PDF a la BD
	 */
	@Override
	public void pruebaaInsertarDocumentoProbatorio() {
		DitDocumentoProbatorio entity=new DitDocumentoProbatorio();
		//em.merge(entity);
		/**
		 * Para Actualización por Documentos Oficiales
		 * 536150 - TIP
		 * 536200 - ARP
		 */
		//entity.setCveIdDocumentoProbatorio(536200L);
		/**
		 * En este fragmento se proecede a Transformar el PDF a ByteArrayOutputStream  
		 */
		File file = new File("C:\\Users\\HLARA\\Downloads\\EjemploDocs\\PM_generaARP_A0938941109.pdf");//TIP
		//File file = new File("C:\\Users\\HLARA\\Downloads\\EjemploDocs\\ARP-Q1612662104.pdf");//ARP
		
		FileInputStream fis =null;
        try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        	System.out.println(ex.getMessage());
        }
        /**
         * Aquí termina la conversión del PDF a ByteArrayOutputStream
         */
        entity.setRefDocumentoDigitalizado(bos.toByteArray());
        entity.setFecExpedicion(new Date());
        entity.setFecRegistroAlta(new Date());
        DitDocumentoPorTipo ditDocumentoPorTipo=new DitDocumentoPorTipo();
        ditDocumentoPorTipo.setCveIdDoctoProbPorTipo(47L);
        entity.setDitDocumentoPorTipo(ditDocumentoPorTipo);
        //46L - TIP
        //47L - ARP
        em.persist(entity);
        //em.merge(entity);
	}
	
	/**
	 * Método de Prueba para Obtener un PDF de DitDocumentoProbatorio
	 */
	@Override
	public DocumentoProbatorio pruebaaObtenerDocumentoProbatorio() {
		DocumentoProbatorio documentoProbatorio=new DocumentoProbatorio();
		DitDocumentoProbatorio entity=new DitDocumentoProbatorio();
		Query query=null;
		query=em.createQuery("from DitDocumentoProbatorio dp where dp.cveIdDocumentoProbatorio=536100");
		entity=(DitDocumentoProbatorio)query.getSingleResult();
		
		documentoProbatorio.setIdDocumentoProbatorio(entity.getCveIdDocumentoProbatorio().intValue());
		documentoProbatorio.setDigitalizacion(entity.getRefDocumentoDigitalizado());
		return documentoProbatorio;
	}
}