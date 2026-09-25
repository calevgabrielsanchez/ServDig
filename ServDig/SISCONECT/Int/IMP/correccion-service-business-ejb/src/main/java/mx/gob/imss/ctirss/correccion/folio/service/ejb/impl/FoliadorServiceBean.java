/**
 * 
 */
package mx.gob.imss.ctirss.correccion.folio.service.ejb.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.deteccion.service.ejb.dao.DeteccionDAOLocal;
import mx.gob.imss.ctirss.correccion.folio.service.ejb.FoliadorServiceLocal;
import mx.gob.imss.ctirss.correccion.framework.utils.TipoCorreccion;

import org.apache.log4j.Logger;

/**
 * @author vaguirre
 * 
 */
@Stateless
public class FoliadorServiceBean implements FoliadorServiceLocal {
	@EJB
	private DeteccionDAOLocal<CrtNroFolio> detecionDao;
	private final static String SEPARADOR = "/";
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(FoliadorServiceBean.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.correccion.folio.service.interfaces.FoliadorService
	 * #recuperarSiguienteFolio(java.lang.Long, java.lang.Long,
	 * java.lang.Integer, java.lang.Long)
	 */
	@Override
	
	public String recuperarSiguienteFolio(Long cveDel, Long cveSubDel,
			Integer anio, TipoCorreccion tipoDocumento) {
		CrtNroFolio nroFolio = this.detecionDao.obtieneFolios(cveDel,
				cveSubDel, String.valueOf(anio), tipoDocumento.getId());
		nroFolio.setNumNumero(new BigDecimal(nroFolio.getNumNumero()
				.longValue() + 1));
		System.out.println("NumeroFolio "+nroFolio.getCvePkFolio());
		if (nroFolio.getNumNumero().longValue() == 1) {
			this.detecionDao.agrega(nroFolio);
		} else {
			this.detecionDao.modifica(nroFolio);
		}
		final StringBuffer folio = new StringBuffer();
		folio.append(new DecimalFormat("00").format(cveDel));
		folio.append(new DecimalFormat("00").format(cveSubDel));
		folio.append(SEPARADOR);
		folio.append(tipoDocumento.getPrefijoFolio());
		folio.append(SEPARADOR);
		folio.append(anio);
		folio.append(SEPARADOR);
		folio.append(new DecimalFormat("0000").format(nroFolio.getNumNumero()));
		System.out.println("Folio Generado :[" + folio + "]");
		return folio.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.correccion.folio.service.interfaces.FoliadorService
	 * #recuperarSiguienteFolio(java.lang.Long, java.lang.Long, java.util.Date,
	 * java.lang.Long)
	 */
	@Override
	public String recuperarSiguienteFolio(Long cveDel, Long cveSubDel,Date fechaAnio, TipoCorreccion tipoDocumento) {
		final Calendar auxCal = Calendar.getInstance();
		auxCal.setTime(fechaAnio);
		return this.recuperarSiguienteFolio(cveDel, cveSubDel,auxCal.get(Calendar.YEAR), tipoDocumento);
	}

	@Override
	public String recuperarSiguienteFolio(String cveDel, String cveSubDel,
			Integer anio, TipoCorreccion tipoDocumento) {
		return this.recuperarSiguienteFolio(Long.parseLong(cveDel), Long.parseLong(cveSubDel), anio, tipoDocumento);
	}
	
	
	
	

	@Override
	public String recuperarSiguienteFolio(String cveDel, String cveSubDel,
			Date fechaAnio, TipoCorreccion tipoDocumento) {
		final Calendar auxCal = Calendar.getInstance();
		auxCal.setTime(fechaAnio);
		return this.recuperarSiguienteFolio(cveDel, cveSubDel,
				auxCal.get(Calendar.YEAR), tipoDocumento);
	}

	@Override
	public String recuperarFolioSiguienteSinActualizar(Long cveDel, Long cveSubDel,
			Integer anio, TipoCorreccion tipoDocumento){
		// TODO Auto-generated method stub
		System.out.println("Generando Folio Temporal");
		CrtNroFolio nroFolio = this.detecionDao.obtieneFolios(cveDel,
				cveSubDel, String.valueOf(anio), tipoDocumento.getId());
		nroFolio.setNumNumero(new BigDecimal(nroFolio.getNumNumero()
				.longValue() + 1));
		
		final StringBuffer folio = new StringBuffer();
		folio.append(new DecimalFormat("00").format(cveDel));
		folio.append(new DecimalFormat("00").format(cveSubDel));
		folio.append(SEPARADOR);
		folio.append(tipoDocumento.getPrefijoFolio());
		folio.append(SEPARADOR);
		folio.append(anio);
		folio.append(SEPARADOR);
		folio.append(new DecimalFormat("0000").format(nroFolio.getNumNumero()));
		logger.debug("Folio Generado :[" + folio + "]");
		return folio.toString();
	}

}
