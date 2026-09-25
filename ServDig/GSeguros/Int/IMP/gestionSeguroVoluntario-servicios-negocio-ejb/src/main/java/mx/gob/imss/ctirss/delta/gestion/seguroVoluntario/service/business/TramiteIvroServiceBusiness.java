/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.TramiteIvroServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.TramiteIvroServiceRemote;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;

/**
 * Servicio para la generacion de seguros a partir de su tramite
 * @author NOVUTECK1
 *
 */
@Stateless(name = "tramiteIvroServiceBusiness", mappedName = "tramiteIvroServiceBusiness")
public class TramiteIvroServiceBusiness implements TramiteIvroServiceRemote {

    /**
     * Servicio para el manejo de los tramites
     */
    @EJB
    private TramiteIvroServiceLocal tramiteIvroServiceLocal;
    /**
     * Genera un segur ivro a partir de su ramite
     * @param tramite tramite realizado para solicitar el seguro ivro
     * @return el seguro generado por el tramite
     * @throws IvroException Error al generar el seguro
     */
    @Override
    public SeguroIvro generaSeguro(TramiteSeguroIvro tramite) throws IvroException {        
        return tramiteIvroServiceLocal.generaSeguro(tramite);
    }
    /*
     * (non-Javadoc)
     * @see mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * TramiteIvroServiceRemote#generaSegurosDomesticos(mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro,
     *  mx.gob.imss.digital.modelo.cobranza.Compra[])
     */
    @Override
    public SegurosIvro generaSegurosDomesticos(TramiteSeguroIvro tramite, Compra[] compra)
            throws IvroException {
        List<SeguroIvro> seguros = tramiteIvroServiceLocal.generaSegurosDomesticos(tramite, Arrays.asList(compra));
        SegurosIvro segurosIvro = new SegurosIvro();
        segurosIvro.setSeguroIvro(seguros.toArray(new SeguroIvro[seguros.size()]));
        return segurosIvro;
    }
	@Override
	public SegurosIvro generaSegurosFamiliares(TramiteSeguroIvro tramite,
			Compra[] compra) throws IvroException {
		List<SeguroIvro> seguros = tramiteIvroServiceLocal.generaSegurosFamiliares(tramite, Arrays.asList(compra));
        SegurosIvro segurosIvro = new SegurosIvro();
        segurosIvro.setSeguroIvro(seguros.toArray(new SeguroIvro[seguros.size()]));
        return segurosIvro;
	}

}
