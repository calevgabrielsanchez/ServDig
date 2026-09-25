package mx.gob.imss.ctirss.sso.admonusuarios.renapo.implementacion;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.sso.admonusuarios.renapo.cliente.CurpKioscosBean;
import mx.gob.imss.ctirss.sso.admonusuarios.renapo.cliente.ParametrosConsulta;
import mx.gob.imss.ctirss.sso.admonusuarios.renapo.cliente.WsimsscurpSoap;
import mx.gob.imss.ctirss.sso.admonusuarios.renapo.cliente.WsimsscurpSoapQSService;
import mx.gob.imss.ctirss.sso.admonusuarios.renapo.util.PropertiesConfigUtils;
import mx.gob.imss.ctirss.sso.admonusuarios.renapo.util.SauAdminConstants;

public class DatosWebServiceParametrosCurp extends Thread {

	private static final Logger logger = Logger.getLogger(DatosWebServiceParametrosCurp.class);

	protected static final String URL = PropertiesConfigUtils.getPropertyService(
			SauAdminConstants.WS_RENAPO_URL, new Object());
	protected static final String URL_BP = PropertiesConfigUtils.getPropertyService(
			SauAdminConstants.WS_RENAPO_URL_BP, new Object());
	protected static final String QNAME = PropertiesConfigUtils.getPropertyService(
			SauAdminConstants.WS_RENAPO_QNAME, new Object());
	protected static final String QNAME_SERVICE = PropertiesConfigUtils.getPropertyService(
			SauAdminConstants.WS_RENAPO_SERVICE, new Object());

	private static URL url = null;
	private static QName qName = null;

	private CurpKioscosBean respuesta = null;

	private boolean existeErrorServicio = false;

	private ParametrosConsulta parametrosEntrada;

	static {
		init_params();
	}

	private static void init_params() {
		try {
			url = new URL(URL);
			qName = new QName(QNAME, QNAME_SERVICE);
			logger.info("::: WebServiceParametros.URL=" + url);
		} catch (MalformedURLException malformedURLException) {
			logger.error(malformedURLException.getMessage(), malformedURLException);
		}
	}

	public void run() {
		logger.info("... Inicio del thread. " + new Date());
		try {
			WsimsscurpSoapQSService soapService = new WsimsscurpSoapQSService(url, qName);
			WsimsscurpSoap servicioRenapo = soapService.getWsimsscurpSoapQSPort();
			
			BindingProvider bp = (BindingProvider) servicioRenapo;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, URL_BP);

			logger.info("::: WebServiceParametros. Thread. El proceso inicia consulta a WS de RENAPO. "
					+ ", timestamp: " + new Date() + ", thread name: " + Thread.currentThread().getName() + ", thread id: "
					+ Thread.currentThread().getId());
			this.respuesta = servicioRenapo.consultaCURP(this.parametrosEntrada);
			logger.info("::: WebServiceParametros. Thread. Finaliza la consulta a WS de RENAPO satisfactoriamente. "
					+ ", timestamp: " + new Date());
		} catch (Exception ex) {
			logger.error("::: WebServiceParametros. Thread. Se genero un error al accesar al webservice de RENAPO. " + ex.getMessage());
			this.existeErrorServicio = true;
			ex.printStackTrace();
		}
		logger.info("... Fin del thread. " + new Date());
	}

	public CurpKioscosBean getRespuesta() {
		return this.respuesta;
	}

	public void setRespuesta(CurpKioscosBean respuesta) {
		this.respuesta = respuesta;
	}

	public boolean isExisteErrorServicio() {
		return this.existeErrorServicio;
	}

	public void setExisteErrorServicio(boolean existeErrorServicio) {
		this.existeErrorServicio = existeErrorServicio;
	}

	public ParametrosConsulta getParametrosEntrada() {
		return this.parametrosEntrada;
	}

	public void setParametrosEntrada(ParametrosConsulta parametrosEntrada) {
		this.parametrosEntrada = parametrosEntrada;
	}

}
