package mx.gob.imss.ctirss.gestionpersonas.servicios.hlda;

import java.util.List;
import org.junit.Test;
import org.junit.Before;

import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.interfaces.HldaClientServiceRemote;
import mx.gob.imss.ctirss.gestionpersonas.EJBLocator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaVO;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaPatronVO;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaPatronVO;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaDetalleVO;

public class HldaClientServiceBusinessTestIt {

    private static final Logger log = LoggerFactory.getLogger(HldaClientServiceBusinessTestIt.class);

    @Test
    public void testCallHldaEJB() {
        log.info("*****************************");
        HldaClientServiceRemote ejb = EJBLocator.getHldaClientService();
        HldaVO hldaVO = ejb.getHldaVO("30098707869");
        log.info("{}", ToStringBuilder.reflectionToString(hldaVO));
        log.info("*****************************");
        log.info("nss       : {}", hldaVO.getNss());
        log.info("apelPat   : {}", hldaVO.getApelPat());
        log.info("apelMat   : {}", hldaVO.getApelMat());
        log.info("nombre    : {}", hldaVO.getNombre());
        log.info("totSemCot : {}", hldaVO.getTotSemCot());
        log.info("sdo250    : {}", hldaVO.getSdo250());
        log.info("Patrones: ");
        List<HldaPatronVO> patrones = hldaVO.getPatrones();
        for (HldaPatronVO patron:patrones) {
            log.info("nomPat  {}", patron.getNomPat());
            log.info("fecInis {}", patron.getFecInis());
            log.info("fecFini {}", patron.getFecFini());
            log.info("regPat  {}", patron.getRegPat());
            log.info("salIni  {}", patron.getSalIni());
            log.info("salFin  {}", patron.getSalFin());
            log.info("*****************************");
        }

        List<HldaDetalleVO> detalle = hldaVO.getDetalle();
        log.info("Semanas:  ");
        for (HldaDetalleVO detalleVO:detalle) {
            log.info("semana  {}", detalleVO.getSemana());
            log.info("anio    {}", detalleVO.getAnio());
            log.info("sdoProm {}", detalleVO.getSdoProm());
            log.info("sdoDic  {}", detalleVO.getSdoDic());
            log.info("*****************************");
        }

    }
    
    @Test
    public void generaSolicitudSemanasCotizadas(){
    	ComponentesExternosBusinessRemote cmpExternos = EJBLocator.getComponentesExternos();
    	Usuario usuario = new Usuario();
    	usuario.setUsuario("HMARTINEZ");
    	cmpExternos.obtenerReporteDeHistoriaLaboral("43048011613", usuario);
    }
}
