package mx.gob.imss.cit.cda.service.test;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.cuentaindividual.utility.CuentaIndividualNssUtilityLocal;
import mx.gob.imss.cit.cda.service.cuentaindividual.utility.CuentaIndividualUtilityLocal;
import mx.gob.imss.cit.cda.service.cuentaindividual.utility.CuentaIndividualWsUtilityLocal;
import mx.gob.imss.cit.cda.service.entity.DetalleNssCdaLocal;
import mx.gob.imss.cit.cda.service.entity.MovimientoCuentaIndividualLocal;
import mx.gob.imss.cit.cda.service.interfaces.test.CuentaIndividualTestRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;

@Stateless(name = "cuentaIndividualTestBusiness", mappedName = "cuentaIndividualTestBusiness")
public class CuentaIndividualTestBusiness extends AbstractServiceUtility implements CuentaIndividualTestRemote {
    
    @EJB
    DetalleNssCdaLocal DetalleNssCdaLocal;
    
    @EJB
    CuentaIndividualUtilityLocal cuentaIndividualUtilityLocal;
    
    @EJB
    CuentaIndividualWsUtilityLocal cuentaIndividualWsUtilityLocal;
    
    @EJB
    MovimientoCuentaIndividualLocal MovimientoCuentaIndividualEntity;
    
    @EJB
    CuentaIndividualNssUtilityLocal cuentaIndividualNssUtilityLocal;

    @Override
    public void getListNssByFolio(String folio) {
        
        List<DitDetalleNss> detalles =DetalleNssCdaLocal.getListNssByFolio(folio); 
        
        for (DitDetalleNss nss : detalles) {
            System.out.println("NSS ENCONTRADO: " +nss.getNss());
            
        }
        
    }
    
    @Override
    public void persistirPeriodo(CuentaIndividualNss cuentaIndividualNss){
        cuentaIndividualUtilityLocal.guardarPeriodos(cuentaIndividualNss);
    }
    
    
    @Override
    public void persistirPeriodosModificados(CuentaIndividualNss cuentaIndividualNss){
        
    }
    
    @Override
    public List<PeriodosRegistroPatronal> buscarPeriodosRegistroPatronalPorNss(String nss) {
        //List<PeriodosRegistroPatronal> listaPeriodos = cuentaIndividualWsUtilityLocal.buscarPeriodosRegistroPatronalPorNss(nss);
        return null;
    }

    @Override
    public Long obtenerConsecutivo(Long nss){
        return MovimientoCuentaIndividualEntity.obtenerConsecutivoMovimientos(nss);
    }
    
    @Override
    public void obtenerMovimientosByFolio(String folio){
        List<DitCorreccionCtaIndCda> movimientos = MovimientoCuentaIndividualEntity.obtenerMovimientosByFolio(folio);
        for (DitCorreccionCtaIndCda movimiento: movimientos){
            System.out.println("Origen: " + movimiento.getCveIdMovOperOrigen().getCveIdMovCorreccion() );
            System.out.println("Destino: " + movimiento.getCveIdMovOperDestino().getCveIdMovCorreccion());
            System.out.println("NSS Origen: " + movimiento.getCveDetalleNssOperOrigen().getCveDetalleNss() );
            System.out.println("NSS Destino: " + movimiento.getCveDetalleNssOperDestino().getCveDetalleNss());
        }
    }
    
    @Override 
    public void guardarAclaraciones(String folio){
    	try {
    		cuentaIndividualUtilityLocal.guardarMovimientosAclaracionCuentaIndividual(folio);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @Override
    public void obtenerTipoTramiteAclaracion(String folio){
        cuentaIndividualUtilityLocal.obtenerMovimientosAclaracion(folio);
    }
    
    @Override
    public List<String> obtenerTramitesPorFolio(String folio){
       
        List <String> tramites = cuentaIndividualUtilityLocal.obtenerMovimientosAclaracion(folio);
        
        for (String tramite: tramites){
            System.out.println(tramite);
        }
        
        return tramites;
    }
    
    @Override 
    public List<String> obtenerTramitesPorFolioyNss(String folio, String nss){
        
        List <String> tramites = cuentaIndividualNssUtilityLocal.getMovimientosAclaracionByFolioNss(folio, nss);
        
        for (String tramite: tramites){
            System.out.println(tramite);
        }
        
        return tramites;
    }
}
