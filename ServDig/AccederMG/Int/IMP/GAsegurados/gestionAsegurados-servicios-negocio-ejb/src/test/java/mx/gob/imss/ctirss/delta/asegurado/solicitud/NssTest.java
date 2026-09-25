package mx.gob.imss.ctirss.delta.asegurado.solicitud;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.AseguradoServiciosExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.test.EJBLocator;
import mx.gob.imss.ctirss.delta.global.model.UnidadMedicaFamiliarTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ClavePresupuestal;
import mx.gob.imss.ctirss.delta.model.derechohabiente.NivelAtencion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoUMF;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NssTest {

	@Autowired AseguradoServiciosExternosRemote nss;
	
    @Test
    public void getSeriesNss() {
		System.out.println("getSeriesNss. Inicio " + new Date());
        //List<Serie> listaSeries = EjbLocator.getPersonaBusiness().getSeriesNss(39L, 137L);
		List<Serie> listaSeries = EJBLocator.getPersonaBusiness().getSeriesNss(null, null);
        
        if (listaSeries != null){
        	System.out.println("Series encontradas para la delegacion/subdelegacion");
        	for (Serie serie: listaSeries){
        		System.out.println("Serie: " + serie);
        	}
        }
		System.out.println("getSeriesNss. Final " + new Date());
    }
    
    @Test
    public void generaNss() {
		System.out.println("generaNss. Inicio " + new Date());
		System.out.println("nss: " + EJBLocator.getServiceBusiness().generaNss(1L, 1L, 12L) );
		System.out.println("generaNss. Final " + new Date());
    }
    
    @Test
    public void altaPeronsaFisicaNss() {
		System.out.println("altaPeronsaFisicaNss. Inicio " + new Date());
		
		Fisica fisica = new Fisica();
		fisica.setIdPersona(25128999L);
		
		Serie serie = new Serie();
		serie.setAnioRegistro(1);
		serie.setIdSerie(1L);
		
		System.out.println("nss: " + EJBLocator.getServiceBusiness().altaPersonaNss(fisica, serie) );
		System.out.println("altaPeronsaFisicaNss. Final " + new Date());
    }    
    
 @Test
 public void testAsignarNSS(){
	
		String curp="BEAR840124HMCRPB04";
		String correo="pablo.bombela@imss.gob.mx";
		UnidadMedicaFamiliarTO UMF = new UnidadMedicaFamiliarTO();
		UMF.setIdUMF(43L);
		UMF.setClavePresupuestal(new ClavePresupuestal());
		UMF.getClavePresupuestal().setClavePresupuestal("10");
		UMF.getClavePresupuestal().setIdClavePresupuestal(10l);
		UMF.setNivelAtencion(new NivelAtencion());
		UMF.getNivelAtencion().setIdNivelAtencion(10l);
		UMF.setNoEconomico(new BigDecimal("1"));
		
		Subdelegacion subdel = new Subdelegacion();
		subdel.setClave("10");
		subdel.setId(10l);
		
		Delegacion del = new Delegacion();
		del.setClave("10");
		del.setId(10l);
		del.setCiz(1);
		subdel.setDelegacion(del);
		
		UMF.setSubdelegacion(subdel);
		UMF.setTipoUMF(new TipoUMF());
		UMF.getTipoUMF().setIdTipoUMF(new BigInteger("1"));
		
		AseguradoServiciosExternosRemote aser = EJBLocator.getServiciosExternosAseguradoService();
		
		try {
			UMF.setIdUMF(38L);
			aser.asignarNSS(curp,correo, UMF, null);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
								
	}
	
   
    
}
