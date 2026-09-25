/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import mx.gob.imss.ctirss.delta.cobranza.entity.ICancelacionServiceLocal;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorAlConsultarTimbradosException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorConvertirXMLTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.NoExistenDatosParaCancelarException;
import mx.gob.imss.ctirss.delta.cobranza.model.ProcOdiCompFisc;
import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.ICancelarTimbradoUtilityRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.cobranza.model.LoteCFDI;

/**
 * @author vanderluk
 *
 */
@WebService
@Stateless(mappedName="cancelacionTimbradoServiceBusiness")
public class CancelacionTimbradoServiceBusiness implements ICancelacionTimbradoServiceRemote
{

	private static Logger LOG = null;
	
	static {
		LOG = LoggerFactory.getLogger(CancelacionTimbradoServiceBusiness.class);
	}
	
	@EJB
	ICancelacionServiceLocal cancelacionServiceLocal;
		
	@EJB
	ICancelarTimbradoUtilityRemote utility;

	@Override
	public LoteCFDI[] cancelaTimbradoPorFechaDeProceso(String fechaProceso) {

		LoteCFDI[]  loteCFDI = null;
		RegistroCFDI[] responseCFDI = null;
		
		List<RegistroCFDI> listcfdis = new ArrayList<RegistroCFDI>();
		LOG.info("Iniciando el proceso de cancelacion para la fecha de proceso : " + fechaProceso);		
		
		try {
			
			List<ProcOdiCompFisc> registros = 
			cancelacionServiceLocal.obtenerRegistrosParaCancelar(fechaProceso);
			
			List<String> UUIDs = new ArrayList<String>();
			
			Iterator<ProcOdiCompFisc> it = registros.iterator();
			RegistroCFDI registro = null;
			while(it.hasNext()){
				ProcOdiCompFisc p = it.next();
				try {
					String UUID = utility.getUUIDFromXML(p.getCfdiXml());
					registro = new RegistroCFDI();
					registro.setCveRegistro(p.getCveRegistro());
					registro.setUuid(UUID);
					
					listcfdis.add(registro);
				} catch (ErrorConvertirXMLTimbradoException e) {
					LOG.error("Error al obtener el UUID");
				}
			}
			
		} catch (NoExistenDatosParaCancelarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorAlConsultarTimbradosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		responseCFDI = listcfdis.toArray(new RegistroCFDI[listcfdis.size()]);
		/**Despues de obtener los folios de los XML se agregan en los campos UUID**/
		try {
			
			cancelacionServiceLocal.actualizaFolios(responseCFDI);
		} catch (ErrorAlConsultarTimbradosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//obtenerFoliosParaCancelarPorFecha(fechaProceso);
		
		return loteCFDI;
		
	}
	
	public LoteCFDI[] obtenerFoliosParaCancelarPorFecha(String fechaProceso) {
		LoteCFDI[] lotesCFDI = null;
		List<ProcOdiCompFisc> foliosParaCancelar = new ArrayList<ProcOdiCompFisc>();
		
		LOG.info("Iniciando el proceso de obtencion de folios por UUID y codigo de respuesta: " + fechaProceso);
		try {
			foliosParaCancelar =  cancelacionServiceLocal.obtenerFoliosParaCancelarPorUUID(fechaProceso);
			LOG.info("Folios obtenidos para cancelar: "+ foliosParaCancelar.size());
			lotesCFDI = utility.armaLotesRegistrosTimbrados(foliosParaCancelar);
		} catch (NoExistenDatosParaCancelarException e) {
			e.printStackTrace();
		} catch (ErrorAlConsultarTimbradosException e) {
			e.printStackTrace();
		}
		return lotesCFDI;
	}

  /**
    * Servicio que actualiza los estatus de los RegistrosCFDI cancelados  
	* @param fecha
	* @return
	* @throws ErrorAlConsultarTimbradosException
	*/
	public void actualizaRegistroCFDICancelado(LoteCFDI[] lotesCancelados) {
		LOG.info("Inicia proceso de la actualizacion, de la cancelacion por lotes de timbrado.");
		LOG.info("Tamano de la lista de folios CFDI a actualizar: "+lotesCancelados.length);
		for(int index = 0; index < lotesCancelados.length; index++) {
			try {
				if (lotesCancelados[index].getRegistros() != null) {
					for (int i = 0; i < lotesCancelados[index].getRegistros().length; i++) {
						if (lotesCancelados[index].getRegistros()[i]!= null) {
							LOG.info("REGISTROS: " +lotesCancelados[index].getRegistros()[i].getCveRegistro() + " " + lotesCancelados[index].getRegistros()[i].getUuid() );
							cancelacionServiceLocal.actualizaRegistroCFDICancelado(lotesCancelados[index].getRegistros()[i]);
						}
					}
				}
			} catch (ErrorAlConsultarTimbradosException e) {
				e.printStackTrace();
			}
		}
		LOG.info("Finaliza proceso de la actualizacion, de la cancelacion por lotes de timbrado.");
	}
	
}
