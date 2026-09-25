package mx.gob.imss.ctirss.correccion.seguimiento.service.interfaces.pagos;

import java.sql.SQLException;
import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtCoppagada;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtCobranzaPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtRevPagos;

public interface PagosService <T extends AbstractModel> { 
	public CrtRevPagos saveOrUpdate(CrtRevPagos model) throws SQLException,Exception;
	
	public CrtRevPagos delete(CrtRevPagos model) throws SQLException,Exception;
	
	public CrtRevPagos findById(CrtRevPagos model) throws SQLException,Exception;
	
	public CrtRevPagos getSumarizado(String nuFolioCorreccion, Integer tipoPago, Integer cveRegulaPago) throws SQLException,Exception;
	
	public List<CrtCoppagada> obtenerDatosTablaCOPPagadas(CrtCoppagada model) throws SQLException,Exception;
	
	public List<CrtRevPagos> obtenerListaPagos(CrtRevPagos model) throws SQLException,Exception;
	
	public List<CrtRevPagos> obtenerListaPagosPorRP(Integer cvePresentacion, Integer tipoPago) throws SQLException,Exception;
	
	public List<CrtCobranzaPagos> recuperaDetalleCobranza(CrtCobranzaPagos cobranzaPagos);
	
	
	
	
	
}
