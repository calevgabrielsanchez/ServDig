package mx.gob.imss.ctirss.correccion.seguimiento.pagos.service.ejb.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.model.CrtCoppagada;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtCobranzaPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtRevPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.pagos.service.ejb.PagosServiceRemote;
import mx.gob.imss.ctirss.correccion.seguimiento.pagos.service.ejb.dao.PagosDAOLocal;

@Stateless(name="pagosService", mappedName = "pagosService")
public class PagosServiceBean <T extends AbstractModel> extends AbstractService implements PagosServiceRemote<T>{
	
	@EJB PagosDAOLocal<T> pagosDao;

	@Override
	public CrtRevPagos saveOrUpdate(CrtRevPagos model) throws SQLException, Exception {
		/*Sumarizar antes de guardar en caso de Movimientos Afiliatorios*/
		
		return pagosDao.saveOrUpdate(model);
	}

	@Override
	public CrtRevPagos delete(CrtRevPagos model) throws SQLException, Exception {
		
		return pagosDao.delete(model);
	}

	@Override
	public CrtRevPagos findById(CrtRevPagos model) throws SQLException, Exception {
		return pagosDao.findById(model);
	}

	@Override
	public CrtRevPagos getSumarizado(String nuFolioCorreccion,Integer tipoPago,Integer cveRegulaPagos) throws SQLException, Exception {
		
		return pagosDao.getSumarizado(nuFolioCorreccion,tipoPago,cveRegulaPagos);
	}

	@Override
	public List<CrtCoppagada> obtenerDatosTablaCOPPagadas(CrtCoppagada model) throws SQLException,
			Exception {
		
		return pagosDao.obtenerDatosTablaCOPPagadas(model);
	}

	@Override
	public List<CrtRevPagos> obtenerListaPagos(CrtRevPagos model) throws SQLException, Exception {
		return pagosDao.obtenerListaPagos(model);
	}

	@Override
	public List<CrtRevPagos> obtenerListaPagosPorRP(Integer cvePresentacion, Integer tipoPago)
			throws SQLException, Exception {
		
		return pagosDao.obtenerListaPagosPorRP(cvePresentacion, tipoPago);
	}

	@Override
	public List<CrtCobranzaPagos> recuperaDetalleCobranza(
			CrtCobranzaPagos cobranzaPagos) {
		// TODO Auto-generated method stub
		
//		cobranzaPagos.setFolioSua("100001");
//		cobranzaPagos.setTipoDocumento("58");
//		cobranzaPagos.setPeriodo(new BigDecimal(201306));
//		cobranzaPagos.setFecPago("19-07-2013");
		
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		
		Date fecha = null;
		try {

		fecha = formatoDelTexto.parse(cobranzaPagos.getFecPago());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		cobranzaPagos.setFecFechapago(fecha);
		return pagosDao.getDetalle(cobranzaPagos);
	}
		

}
