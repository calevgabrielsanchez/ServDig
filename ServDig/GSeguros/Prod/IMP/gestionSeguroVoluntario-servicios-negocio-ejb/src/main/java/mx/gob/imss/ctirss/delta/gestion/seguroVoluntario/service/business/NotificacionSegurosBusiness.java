/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaTablaCorreosLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.EnviaCorreoLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.NotificacionSegurosRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoOperacionNotificacionIVROEnum;
import mx.gob.imss.ctirss.delta.persistence.DitBitCorreosSivro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "notificacionSegurosBusiness", mappedName = "notificacionSegurosBusiness")
public class NotificacionSegurosBusiness implements NotificacionSegurosRemote {

	private static final Integer ENCOLADO_EXITOSO = 2;
	private static final Integer ERROR_ENCOLAR = 3;

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionSegurosBusiness.class);

    /**
     * Servicio para la consulta de seguros
     */
    @EJB
    private ConsultaSeguroIvroLocal consultaSeguros;
    /**
     * Servici para el envio de correos.
     */
    @EJB
    private EnviaCorreoLocal enviaCorreo;


    @EJB
    private ConsultaTablaCorreosLocal consultaTablaCorreos;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * NotificacionSegurosRemote#notificaRenovacion()
     */
    @Override
    public void notificaRenovacion() {
        List<SeguroIvro> seguros = consultaSeguros.buscaSegurosPorRenovar();
        enviaCorreo.enviaCorreos(seguros, TipoOperacionNotificacionIVROEnum
                .RECORDATORIO_DE_RENOVACION.getCodigo());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * NotificacionSegurosRemote#notificaProximoVencimiento()
     */
    @Override
    public void notificaProximoVencimiento() {
        List<SeguroIvro> seguros = consultaSeguros.buscaSegurosPorVencer();
        enviaCorreo.enviaCorreos(seguros, TipoOperacionNotificacionIVROEnum
                .RECORDATORIO_DE_PAGO.getCodigo());
    }
    
    @Override
    public void enviaCorreo(SeguroIvro seguro, List<String> correos, int tipo, Map<String, byte[]>adjuntos){
        enviaCorreo.enviaCorreo(seguro, correos, tipo, adjuntos);
    }

    @Override
    public void enviaCorreo(SeguroIvro seguro, int tipo) {
        enviaCorreo.enviaCorreo(seguro, tipo);
    }

    @Override
    public void enviaCorreoTipoOperacion(SeguroIvro seguro, int tipoOperacion) throws Exception{
    	LOGGER.info("se inicia el proceso de encolado para el seguro: "+seguro.getCveIdSeguroIvro()
    				+" tipo de operacion: "+tipoOperacion);
        enviaCorreo.enviaNotificacion(seguro,tipoOperacion);
    }

    @Override
	public String procesoEnviaCorreosDiario() {
		List<DitBitCorreosSivro> listaCorreosPorEnviar;

		try{
		LOGGER.info("Ingresando al business");
		listaCorreosPorEnviar = consultaTablaCorreos.getSeguroIVROEstatusEnvio();

		if ((listaCorreosPorEnviar==null)||(listaCorreosPorEnviar.size()==0)){
			LOGGER.info("NO se recuperaron registros para procesar");
				return new String("NO se recuperaron registros para procesar");
		}else{
			LOGGER.info("La lista obtuvo "+listaCorreosPorEnviar.size()+ " registros");
				int procesados = 0,erroneos = 0;
			for(DitBitCorreosSivro temp:listaCorreosPorEnviar){
				SeguroIvro seguro = new SeguroIvro();
				seguro.setCveIdSeguroIvro(temp.getCveIdSeguroIvro());
				int tipoOperacion = temp.getTipOperacion();

		    	try{
		    		this.enviaCorreoTipoOperacion(seguro, tipoOperacion);
		    		consultaTablaCorreos.actualizaSeguroIVROPorCveSeguro(temp.getCveIdBitCorreosSivro(), ENCOLADO_EXITOSO, null);
			    		procesados++;
		    	}catch(Exception e){
		    		LOGGER.error("Hubo un error al procesar el encolamiento: ",e);
		    		consultaTablaCorreos.actualizaSeguroIVROPorCveSeguro(temp.getCveIdBitCorreosSivro(), ERROR_ENCOLAR, e.getMessage());
			    		erroneos++;
		    	}
			}
				LOGGER.info("Hubo "+listaCorreosPorEnviar.size()+ " registros: Procesados: "+procesados+", Erroneos: "+erroneos);
				return new String("Hubo "+listaCorreosPorEnviar.size()+ " registros: Procesados: "+procesados+", Erroneos: "+erroneos);
			}
		}catch(IvroException ivroe){
			return ivroe.getMessage();
		}catch(Exception e){
			return e.getMessage();
		}

    }
}
