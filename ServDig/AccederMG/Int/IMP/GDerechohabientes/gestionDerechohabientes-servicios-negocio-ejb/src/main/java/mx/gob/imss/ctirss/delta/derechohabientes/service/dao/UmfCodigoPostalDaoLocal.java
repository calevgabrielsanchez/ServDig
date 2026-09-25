package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.CodigoSinUmfException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;

@Local
public interface UmfCodigoPostalDaoLocal {

	/**
	 * 
	 * @param domicilio
	 * @return
	 * @throws DerechohabientesBusinessException 
	 * @throws Exception 
	 */
	List<UnidadMedicaFamiliar> getUmfByCodigoPostal(String codigoPostal ) throws CodigoSinUmfException, DerechohabientesBusinessException, Exception ;
	DicUmf getUMFDomicilio(DgDomicilioGeografico domicilioGeografico) throws DerechohabientesBusinessException, Exception ;
	DitUmfCodPo getUmfCodPosByCodPos(String codigoPostal) throws Exception ;
	DitUmfCodPo getUmfCodPosByIdUmf(Long cveIdUmf) throws Exception;
	List<DitUmfCodPo> getUmfCodPosByIdUmfList(Long cveIdUmf) throws Exception;
	DitUmfCodPo updateUMFCodPos(DitUmfCodPo umfCodPo,Long idUmfNew) throws Exception;
	DitUmfCodPo bajaLogicaUmfCodPo(Long idUmf, Asentamiento asentamientos) throws Exception;
	DitUmfCodPo insertaUMFCodPos(Long idUmf, Asentamiento asentamientos) throws Exception;
	DitUmfCodPo getUmfCodPosByAsentamientoAndUmf(Long idUmf,Asentamiento asentamientos) throws Exception;
	DitUmfCodPo getUMFCodPosDomicilio(DgDomicilioGeografico domicilioGeografico) throws DerechohabientesBusinessException, Exception;
	Boolean existeRelacionEntreUmfYCp(String codigoPostal, Long idUmf);
	
	/**
	 * Obtiene las UMF's en base a si c&oacute;digo postal y el valor del indicadir CFE 
	 * 
	 * @param codigoPostal
	 * @param notEqualIndUmfCfe
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	List<UnidadMedicaFamiliar> getUmfByCodigoPostal(String codigoPostal, Integer notEqualIndUmfCfe)  throws DerechohabientesBusinessException,Exception;
	List<UnidadMedicaFamiliar> getUmfByAsentamiento(Asentamiento asentamiento, Boolean incluirCFE)  throws DerechohabientesBusinessException,Exception;
	
}