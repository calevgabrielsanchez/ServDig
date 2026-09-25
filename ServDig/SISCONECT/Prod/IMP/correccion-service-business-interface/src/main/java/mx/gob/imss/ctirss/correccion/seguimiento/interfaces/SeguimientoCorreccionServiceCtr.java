package mx.gob.imss.ctirss.correccion.seguimiento.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CedulaRevisionAudVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CedulaValidacionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CorreccionSeguimientoGenericoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.DevSubDelegacionSeguimientoCorreccionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.RecepcionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.model.CrtPresentacorr;
import mx.gob.imss.ctirss.correccion.model.CrtRevCedRevision;
import mx.gob.imss.ctirss.correccion.model.CrtRevDerivAFisca;
import mx.gob.imss.ctirss.correccion.model.CrtRevDerivASubd;
import mx.gob.imss.ctirss.correccion.model.CrtRevOficios;
import mx.gob.imss.ctirss.correccion.model.CrtRevRecepcion;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

public interface SeguimientoCorreccionServiceCtr {
	

	
	public CorreccionSeguimientoGenericoVO generaCedulaRevision(CorreccionSeguimientoGenericoVO clase);
	public CedulaRevisionAudVO  getRubros(CorreccionSeguimientoGenericoVO clase);
	public CorreccionSeguimientoGenericoVO  guardaCedulaRevision(CorreccionSeguimientoGenericoVO clase, boolean isSupervisor);
	public CrtRevCedRevision  consultaCedulaRevision(CorreccionSeguimientoGenericoVO clase);
	public String  finalizaCedulaRevision(CorreccionSeguimientoGenericoVO clase);
	public CedulaRevisionAudVO determinaEstadoRecepcion(CedulaRevisionAudVO cedulaRev, CorreccionSeguimientoGenericoVO clase);
	public boolean autorizaCedulaRevision(CorreccionSeguimientoGenericoVO clase);
	public String rechazaCedulaRevision(CorreccionSeguimientoGenericoVO clase);
	public List<Object> getRegPatronales(CorreccionSeguimientoGenericoVO clase);
	/**
	 * Metodo que genera la rececpion del seguimiento de correccion de acuerdo a las reglas 
	 * de negocio establecidas
	 * @author Oscar German Beltran Ortega
	 * @version 1.0.0
	 */
	public CorreccionSeguimientoGenericoVO generaRecepcion(CorreccionSeguimientoGenericoVO vo);
	public CrtRevOficios consultaRevOficiosPorClave(CrtRevOficios crtRevOficios);
	
	//public CrtRevRecepcion consultaRecepcion(RecepcionSeguimientoVO recepcionSeguimientoVO);
	public CrtRevRecepcion consultaRecepcion(Integer idPresentaCorreccion, Integer tipoPagoRecepcion);
	public CrtRevOficios guardaReqDoc(CrtRevOficios crtRevOficios);
	public CrtPresentacorr consultaCrtPresentacorrPorClave(CrtPresentacorr crtPresentacorr);
	public CrtRevOficios consultaRevOficiosPorCveSolCorr(CrtRevOficios crtRevOficios);
	public CrtRevDerivASubd registraCrtRevDerivASubd(DevSubDelegacionSeguimientoCorreccionVO derivSubDelTabVO, DgDomicilioGeografico dom,UserSession user);
	public CrtRevDerivASubd consultaCrtRevDerivASubdCveSolCorr(Integer cveSolCorr);

	public CrtRevDerivAFisca guardaDerivAFisca(CrtRevDerivAFisca crtRevDerivAFisca);
	public CrtRevDerivAFisca consultaDerivAFisca(Long cveRevDerivAFis);
	public CrtRevDerivAFisca buscaDerivAFiscaPorSolicitud(Integer cveSolicitud);
	public CrtRevDerivAFisca reactivaSolicitud(CrtRevDerivAFisca derivacion);
	
	public CrtRevOficios consultaRevOficiosPorCveSolCorrOR(CrtRevOficios crtRevOficios);
	public List consultaCedulaValidacionConsolidado(CorreccionSeguimientoGenericoVO clase);
	
	/**
	 * Metodo que genera la cedula de validacion del seguimiento de correccion de acuerdo a las reglas 
	 * de negocio establecidas
	 * @author Oscar German Beltran Ortega
	 * @version 1.0.0
	 */
	public CedulaValidacionVO generaCedulaValidacion(CedulaValidacionVO cedulaValidacionVO);	
	public CedulaValidacionVO recuperaDetalleCedulaValidacion(CedulaValidacionVO cedulaValidacionVO);
	public List<Long> recuperaEjercicios(Integer claveSolCorr);
	public CedulaValidacionVO finalizaCedulaValidacion(CedulaValidacionVO cedulaValidacionVO);	
	public String autorizaCedulaValidacion(CedulaValidacionVO cedulaValidacionVO);	
	public String rechazaCedulaValidacion(CedulaValidacionVO cedulaValidacionVO);	
	public void guardaEstatusRecepcion(Integer cvePresentaCorr, Integer estatus);
	public void actualizaEstatusCorreccion(Integer cveSolicitudCorr, Integer estatus);
}
