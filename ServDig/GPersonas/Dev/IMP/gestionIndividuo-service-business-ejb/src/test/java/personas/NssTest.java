package personas;

import java.util.Date;
import java.util.List;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import org.junit.Test;

import test.EjbLocator;

public class NssTest {

    @Test
    public void getSeriesNss() {
		System.out.println("getSeriesNss. Inicio " + new Date());
        //List<Serie> listaSeries = EjbLocator.getPersonaBusiness().getSeriesNss(39L, 137L);
		List<Serie> listaSeries = EjbLocator.getPersonaBusiness().getSeriesNss(null, null);
        
        if (listaSeries != null){
        	System.out.println("Series encontradas para la delegacion/subdelegacion");
        	for (Serie serie: listaSeries){
        		System.out.println("Serie: " + serie);
        	}
        }
		System.out.println("getSeriesNss. Final " + new Date());
    }
    
    /**
     * Mi primer puto test
     */
    @Test
    public void getSerie(){
    	Serie serie = new Serie();
    	serie.setIdSerie(2L);
    	
    	serie = EjbLocator.getPersonaBusiness().getSerie(serie);
    	
    	System.out.println("Serie --> " + serie);
    	
    }
}
