/**
 * 
 */
package mx.gob.imss.ctirss.correccion.firma.service.interfaces;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTramiteMensajes;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtTramitePresentado;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.ConfigTramite;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.ParamSolicitudTramite;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractDocumentoElectronicoModel;
import mx.gob.imss.ctirss.correccion.framework.exception.comun.ServicioRemotoNoDisponibleException;
import mx.gob.imss.ctirss.correccion.framework.exception.firma.CertificadoInvalidoException;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.correccion.bean.RespuestaFirmadoSimple;

/**
 * Servicio para el soporte de la firma electronica dentro del sistema.
 * 
 * @author vaguirre
 * 
 */
public interface FirmaElectronicaService {
	/**
	 * Matodo para validar que el certificado usado para firmar esta vigente y
	 * es reconocido por la entidad certificadora.
	 * 
	 * @param doc
	 *            Documento recian firmado. Campos requeridos:
	 *            <ul>
	 *            <li>tipoCertificado</li>
	 *            <li>firmaElectronica</li>
	 *            </ul>
	 * @return El objeto que entro con valor en <b>folioNotarial</b>, el cual
	 *         representa el folio de la notaria, este valor tiene que ser
	 *         persistido en la BD.
	 * @throws ServicioRemotoNoDisponibleException
	 *             En caso de que el <i>web service</i> no este disponible o se
	 *             comporte de manera inesperada.
	 * @throws CertificadoInvalidoException
	 *             Cuando el cerficado con el que se firmo el documento no es
	 *             valido.
	 */
	AbstractDocumentoElectronicoModel validarCertificado(
			AbstractDocumentoElectronicoModel doc)
			throws ServicioRemotoNoDisponibleException,
			CertificadoInvalidoException;
	
	public void guardarTramitePresentado(CrtTramitePresentado tramitePresentado);
	
	public CrcTramiteMensajes recuperaMensaje(CrcTramiteMensajes mensaje);
	
	public CrtTramitePresentado buscaTramitePresentado(CrtTramitePresentado crtTramitePresentado, UserSession usrSession);
	
	//public String getSelloDigital(String cadenaOriginal);
	public RespuestaFirmadoSimple getSelloDigital(CrtSolicitudcorr solicitud);
	
	public void guardarArchivoFirmado(String secuenciaNotaria, Archivo archivo);
	
	public RespuestaFirmadoSimple getSelloDigital(String cadenaOriginal,String secuenciaNotaria, String rfc); 
	
	
	
	
	public SujetoObligado recuperaSujetoObligado(CrtSolicitudcorr rp);	
	public ParamSolicitudTramite generaParametro(CrtSolicitudcorr solicitud,CrtTramitePresentado tramite,SujetoObligado sujetoObligado);	
	public Solicitud generaNuevaSolicitudTramite(ParamSolicitudTramite paramSolicitudTramite);
	public Solicitud guardarSolicitudTramite(Solicitud solicitud, ParamSolicitudTramite parametro);
	
	public Solicitud agregaNuevoTramite(Long  cveSolicitudBDTU, String rp, ConfigTramite configTramite);
	public ConfigTramite generaConfiguracionProrroga();
	public ConfigTramite generaConfiguracionPresentacion();
	public ConfigTramite generaConfiguracionSolicitudCorreccion();
	public void cerrarSolicitudBDTU(Long  cveSolicitudBDTU);
}
