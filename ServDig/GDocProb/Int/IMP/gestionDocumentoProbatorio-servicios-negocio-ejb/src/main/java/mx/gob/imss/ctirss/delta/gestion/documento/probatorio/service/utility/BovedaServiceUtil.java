package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.cit.clienteswebservices.boveda.legada.AltaDocumento;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.Atributo;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.Documento;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.EntradaAlta;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.EntradaBaja;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.EntradaConsulta;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.LegadoPort;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.LegadoPortService;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.SalidaAlta;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.SalidaBaja;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.SalidaConsulta;
/*
import mx.gob.imss.cit.ClienteWebServiceBoveda.AltaDocumento;
import mx.gob.imss.cit.ClienteWebServiceBoveda.Atributo;
import mx.gob.imss.cit.ClienteWebServiceBoveda.EntradaAlta;
import mx.gob.imss.cit.ClienteWebServiceBoveda.SalidaAlta;
import mx.gob.imss.cit.ClienteWebServiceBoveda.EntradaConsulta;
import mx.gob.imss.cit.ClienteWebServiceBoveda.EntradaBaja;
import mx.gob.imss.cit.ClienteWebServiceBoveda.SalidaConsulta;
import mx.gob.imss.cit.ClienteWebServiceBoveda.Documento;
import mx.gob.imss.cit.ClienteWebServiceBoveda.SalidaBaja;
import mx.gob.imss.cit.ClienteWebServiceBoveda.IESServicio;
import mx.gob.imss.cit.ClienteWebServiceBoveda.IESServicioSoap;
*/
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.dto.DatosBoveda;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;

@Stateless(name = "bovedaServiceUtilLocal", mappedName = "bovedaServiceUtilLocal")
public class BovedaServiceUtil extends AbstractServiceUtility implements BovedaServiceUtilLocal {
	
	@Override
	public DocumentoProbatorio guardarDocumentoBoveda(DocumentoProbatorio documento, DatosBoveda datosBoveda) throws DocumentoProbatorioException{
		  long tiempoInicio = new Date().getTime();
	        SalidaAlta respuesta = null;
	        if (documento != null) {
	            try {
	                AltaDocumento altaDocumento = new AltaDocumento();
	                EntradaAlta entradaAlta = new EntradaAlta();
	                entradaAlta.setDocumento(documento.getDigitalizacion());
	                entradaAlta.setRuta(datosBoveda.getRutaBoveda());
	                entradaAlta.setTipo(datosBoveda.getTipoDocumentos());
	                entradaAlta.setTipoDocumental(datosBoveda.getTipoDocumental());

	                List<Atributo> atributos = new ArrayList<Atributo>();
	                Atributo atributoNombreArchivo = new Atributo();
	                atributoNombreArchivo.setNombre("name");
	                atributoNombreArchivo.setValor(documento.getNomNombreDocumento());
	                atributos.add(atributoNombreArchivo);

	                Atributo atributoFolioTramite = new Atributo();
	                atributoFolioTramite.setNombre("folioTramite");
	                atributoFolioTramite.setValor(datosBoveda.getFolio());
	                atributos.add(atributoFolioTramite);


	                Atributo atributoTipoDocuemento = new Atributo();
	                atributoTipoDocuemento.setNombre("tipodocumento");
	                atributoTipoDocuemento.setValor(documento.getDocumentoPorTipo().getIdDocumentoPorTipo()+"");
	                atributos.add(atributoTipoDocuemento);

	                log.debug("Los datos adicionales en el EJB son " + datosBoveda.getDatosAdicionales());
	                /**
	                 * Verificamos si se tienen mas atributos adicionales que enviar a boveda
	                 * como puede ser el registro patronal, RFC y los que se soliciten 
	                 */
	                if(datosBoveda.getDatosAdicionales() != null) {
	                	Iterator it = datosBoveda.getDatosAdicionales().entrySet().iterator();
	                    while (it.hasNext()) {
	                        Map.Entry pair = (Map.Entry)it.next();
	                        log.debug("anadire el atributo " + pair.getKey() + " con valor " + pair.getValue());
	                        Atributo atributoAdicional = new Atributo();
	                        atributoAdicional.setNombre((String)pair.getKey());
	                        atributoAdicional.setValor((String)pair.getValue());
	                        atributos.add(atributoAdicional);
	                    }
	                }
	                entradaAlta.getAtributo().addAll(atributos);
	                altaDocumento.setEntrada(entradaAlta);
	                
	                respuesta = this.adjuntarDocumento(altaDocumento);
	                
	                if(!respuesta.isExito()) {
	                	throw new DocumentoProbatorioException(respuesta.getDescripcion(), respuesta.getClave());
	                } else if (respuesta.getIdDocumento() == null){
	                	throw new DocumentoProbatorioException("-0001", "Por el momento no es posible adjuntar los documentos, intentalo m&aacute;s tarde por favor.");
	                }
	                documento.setBovedaDocId(respuesta.getIdDocumento());
	                log.debug("El id del documento es " + documento.getBovedaDocId());
	                
	            } catch(DocumentoProbatorioException e){
	            	throw e;
	            }catch (Exception e) {
	                log.error("Error al dar de alta el archivo", e);
	                throw new DocumentoProbatorioException("-0001", "Por el momento no es posible adjuntar los documentos, intentalo m&aacute;s tarde por favor.");
	            }
	        } else {
	            log.debug("El servicio de alta de documento no fue invocado, debido a que se recibio una peticion nula");
	        }
	        long timeFinal = new Date().getTime();
	        log.debug("el tiempo total es: " + (timeFinal - tiempoInicio) / 1000 + " segundos");
	        return documento;
	}

	@Override
	public DocumentoProbatorio getDocumento(String idDocumento, DatosBoveda datosBoveda) throws DocumentoProbatorioException {
		
		DocumentoProbatorio docto = null;
		
		if(StringUtils.isBlank(idDocumento)){
			throw new DocumentoProbatorioException("Es necesario el id del documento", "001");
		}
		
		EntradaConsulta entradaConsulta = new EntradaConsulta();
		entradaConsulta.setTipoDocumental(datosBoveda.getTipoDocumental());
		Atributo atributoId = new Atributo();
		atributoId.setNombre("objectId");
		atributoId.setValor(idDocumento);
		entradaConsulta.getAtributo().add(atributoId);
		
		SalidaConsulta respuesta = this.getDocumento(entradaConsulta);
		
		if(!respuesta.isExito()) {
			throw new DocumentoProbatorioException(respuesta.getDescripcion(), respuesta.getClave());
		} else {
			docto = new DocumentoProbatorio();
			docto.setBovedaDocId(idDocumento);
			Documento documento = respuesta.getDocumento().get(0);
			docto.setNomNombreDocumento(documento.getNombre());
			docto.setDigitalizacion(documento.getContenido());
		}
		return docto;
	}

	@Override
	public void eliminarDocumentoBoveda(DocumentoProbatorio documento) throws DocumentoProbatorioException {
		
		if(documento == null || StringUtils.isBlank(documento.getBovedaDocId())){
			throw new DocumentoProbatorioException("Es necesario el id del documento", "001");
		}
		
		EntradaBaja entradaBaja = new EntradaBaja();
		entradaBaja.setIdDocumento(documento.getBovedaDocId());
		
		SalidaBaja salidaBaja = this.eliminarDocumento(entradaBaja);
		if(!salidaBaja.isExito()) {
			throw new DocumentoProbatorioException(salidaBaja.getDescripcion(), salidaBaja.getClave());
		}
		
	}

	private SalidaConsulta getDocumento(EntradaConsulta entradaConsulta) {
		SalidaConsulta salidaConsulta = null;
		LegadoPort soap = this.getSOAP();
		
		salidaConsulta = soap.consultaDocumento(entradaConsulta);
		
		log.debug("La salida es la siguiente exito: " + salidaConsulta.isExito());
		log.debug("La salida es la clave: " + salidaConsulta.getClave());
		
		return salidaConsulta;
	}
	
	private SalidaAlta adjuntarDocumento( AltaDocumento altaDocumento) {
		
		LegadoPort soap = this.getSOAP();
		SalidaAlta salidaAlta = soap.altaDocumento(altaDocumento.getEntrada());
		
		log.debug("La salida es la siguiente exito: " + salidaAlta.isExito());
		log.debug("La salida es la clave: " + salidaAlta.getClave());
		log.debug("La salida es idDocumento: " + salidaAlta.getIdDocumento());
		log.debug("La salida es descripcion: " + salidaAlta.getDescripcion());
		
		return salidaAlta;
	}
	
	private SalidaBaja eliminarDocumento(EntradaBaja entradaBaja) {
		LegadoPort soap = this.getSOAP();
		SalidaBaja salidaBaja = soap.bajaDocumento(entradaBaja);
		
		log.debug("La salida es la siguiente exito: " + salidaBaja.isExito());
		log.debug("La salida es la clave: " + salidaBaja.getClave());
		log.debug("La salida es idDocumento: " + salidaBaja.getIdDocumento());
		log.debug("La salida es descripcion: " + salidaBaja.getDescripcion());
		
		return salidaBaja;
	}
	
	/**
	private IESServicioSoap getSOAP() {
		IESServicio service = new IESServicio();
		IESServicioSoap soap = service.getIESServicioSoap();
		
		return soap;
	}**/
	private LegadoPort getSOAP() {
		LegadoPortService serice = new LegadoPortService();
		LegadoPort port = serice.getLegadoPortSoap11();
		return port;
	}
}
