/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorAlConsultarTimbradosException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorConvertirXMLTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.NoExistenDatosParaCancelarException;
import mx.gob.imss.ctirss.delta.cobranza.model.LoteCFDI;
import mx.gob.imss.ctirss.delta.cobranza.model.PagoType;
import mx.gob.imss.ctirss.delta.cobranza.model.ProcOdiCompFisc;
import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vanderluk
 *
 */
@Stateless
public class CancelarTimbradoUtility implements ICancelarTimbradoUtilityRemote {

	private static Logger LOG = null;
	public static final Class[] MARSHALLING_CLASSES;
	private static final JaxbUtil JAXB_UTIL;
	
	
	static {
		LOG = LoggerFactory.getLogger(CancelarTimbradoUtility.class);
		MARSHALLING_CLASSES = new Class[] { PagoType.class};
		JAXB_UTIL = new JaxbUtil(MARSHALLING_CLASSES);
	}
	

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.cobranza.service.utility.ICancelarTimbradoUtilityRemote#getUUIDFromXML(java.lang.String)
	 */
	@Override
	public String getUUIDFromXML(String xmlTimbrado) throws ErrorConvertirXMLTimbradoException {
		// TODO Auto-generated method stub
		LOG.debug("Obteniendo el UUID de" + xmlTimbrado);
		String UUID = null;
		
		PagoType pago = (PagoType)JAXB_UTIL.xmlToObject(xmlTimbrado);
		UUID = pago.getUuid();
		LOG.info("UUID recuperado : " + UUID);
		
		return UUID;
	}
	
	
	/**
	 * Servicio encargado de armar los lotes a procesar (cada lote con un maximo de 500 registros)
	 * @param fechaProceso  totalFolios
	 * @return
	 * @throws ErrorAlConsultarTimbradosException
	 * @throws NoExistenDatosParaCancelarException 
	 */	
	@Override
	public LoteCFDI[] armaLotesRegistrosTimbrados(List<ProcOdiCompFisc> foliosParaCancelar) throws ErrorAlConsultarTimbradosException, NoExistenDatosParaCancelarException {
		int totalFolios = foliosParaCancelar.size() ;
		int contadorRegistros = 0;
		int contadorLotes = 0;
		int foliosPorLote = REGISTROS_POR_LOTE;			
		RegistroCFDI registro = null;
		RegistroCFDI[] registroCFDI = new RegistroCFDI[foliosPorLote];
		LoteCFDI lote = null;
		int totalLotes = totalFolios % foliosPorLote == 0 ? totalFolios / foliosPorLote : totalFolios / foliosPorLote + 1;
		LOG.info("Total Folios::: " + totalFolios + " Folios Lotes ::: " + totalLotes);
		LoteCFDI[] lotes = new LoteCFDI[totalLotes];
		
		try {
			for (int contador = 0; contador <= foliosParaCancelar.size(); contador ++) {	
				if (contador == 0) {
					ProcOdiCompFisc proOdiUUID = foliosParaCancelar.get(contador);				
					registro = new RegistroCFDI();
					registro.setCveRegistro(proOdiUUID.getCveRegistro());
					registro.setUuid(proOdiUUID.getUuid());				
					registroCFDI[contadorRegistros] = registro;
					//LOG.info("Creo Registro 0:: " + registro.getCveRegistro() + " Contador Registros:: " + contadorRegistros);
					contadorRegistros++;										
				} else if (contador%foliosPorLote != 0 && contador != foliosParaCancelar.size()) {
					ProcOdiCompFisc proOdiUUID = foliosParaCancelar.get(contador);				
					registro = new RegistroCFDI();
					registro.setCveRegistro(proOdiUUID.getCveRegistro());
					registro.setUuid(proOdiUUID.getUuid());				
					registroCFDI[contadorRegistros] = registro;
					//LOG.info("Creo Registro:: " + registro.getCveRegistro() + " Contador Registros:: " + contadorRegistros);
					contadorRegistros++;
				} else {
					lote = new LoteCFDI();
					lote.setRegistros(registroCFDI);
					lote.setFechaLoteProceso(new Date());
					lotes[contadorLotes] = lote;
					registroCFDI = new RegistroCFDI[foliosPorLote];
					contadorRegistros = 0;
					contadorLotes++;
//					LOG.info("Creo lote::: " + contadorLotes) ;
					
//					LOG.info("ContadorLotes:: " + contadorLotes + " size registroCFDI:: " + registroCFDI.length + "  size lotes:: " + lotes.length);
					if (contador%foliosPorLote == 0 && contador != foliosParaCancelar.size()) {
						ProcOdiCompFisc proOdiUUID = foliosParaCancelar.get(contador);				
						registro = new RegistroCFDI();
						registro.setCveRegistro(proOdiUUID.getCveRegistro());
						registro.setUuid(proOdiUUID.getUuid());				
						registroCFDI[contadorRegistros] = registro;
						//LOG.info("Creo Registro cuando == 0:: " + registro.getCveRegistro() + " Contador Registros:: " + contadorRegistros);
						contadorRegistros++;
					}
				}
			}
		} catch(NullPointerException npe) {
			npe.printStackTrace();			
		}
		return lotes;
	}
	
	/**
	 * Metodo que nos ayuda a obtener el total de los lotes
	 * @param lotesCFDI
	 * @return
	 * @throws
	 * @throws  
	 */	
	public void verificaNumeroDeFoliosPorRegistroCFDI(LoteCFDI[] lotesCFDI){	
		LOG.info("Verificando el numero de lotes: " + lotesCFDI.length);
		for(int i = 0; i < lotesCFDI.length; i ++){
			LOG.info("Deben contener 500 registros en la  prueba son 50 " + lotesCFDI[i].getRegistros().length);
		}
	}
}
