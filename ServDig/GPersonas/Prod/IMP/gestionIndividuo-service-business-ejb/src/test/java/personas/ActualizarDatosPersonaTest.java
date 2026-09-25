package personas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import test.EjbLocator;


public class ActualizarDatosPersonaTest {

	private PersonaFisicaServiceBusinessRemote  personaFisicaServiceBusiness;
	
	@Before
	public void setUp() {
		personaFisicaServiceBusiness = EjbLocator.getPersonaFisicaServiceBusinessRemote();
	}
	
	private List<Fisica> prepararDatosIniciales(){
    	Map<String, String> parameters = new HashMap<String, String>();
    	//parameters.put("  RFC      ","   NSSS     ");
    	
    	/*parameters.put("BODJ900518NLA","05149085994");
    	parameters.put("BUJJ640328147","12816454867");
    	parameters.put("CARS850926NK5","17148531597");
    	parameters.put("CUPN901211CT5","05149089806");
    	parameters.put("DULA650828NS5","24926528175");
    	parameters.put("EAPR7902065K6","90987936417");
    	parameters.put("FOGR730504S14","24917372450");
    	parameters.put("GAGS8101257K8","57968101568");
    	parameters.put("GEPA6801177UA","23856804929");
    	parameters.put("GOLG711118JZ6","23887178459");
    	parameters.put("GUAV7406245P9","32917405469");
    	parameters.put("GUOA790706JW8","12937710023");
    	parameters.put("JIOR670808HB6","32836729718");
    	parameters.put("LABJ821122CA1","41018207377");
    	parameters.put("LELA710212IL4","23897176824");
    	parameters.put("LOMC7307129L9","52897310711");
    	parameters.put("LOMR590208NG7","43755930690");
    	parameters.put("MAGD601026ML9","03146040096");
    	parameters.put("MUGC640712C96","24806400776");
    	parameters.put("OEBD780317AX3","08147825361");
    	parameters.put("OEGM700924983","57907048730");
    	parameters.put("PECL791102ET5","13947989706");
    	parameters.put("PEMR7011304Q3","03147059079");
    	parameters.put("PIFJ641211CK1","33856404380");
    	parameters.put("RAMC590917NS0","03145941765");
    	parameters.put("ROVC771215F17","57937709210");
    	parameters.put("SALL781227I44","08147831120");
    	parameters.put("SAOJ960628RV3","46149638853");
    	parameters.put("SOPS8902128S3","05148974578");
    	parameters.put("TUDG760320TI1","84007602042");
    	parameters.put("VAMI750913Q24","05147500952");
    	parameters.put("VECV850212MZ9","45028503162");
    	parameters.put("VERG641228ET8","25946404339");*/
    	
    	return trasnformarPF(parameters);
    }
	
    @Test
    public void procesarActualizacionesPF() {    	
    	List<Fisica> listaPF  = prepararDatosIniciales();
    	if(!CollectionUtils.isEmpty(listaPF)){
    		personaFisicaServiceBusiness.procesarActualizacionesPF(listaPF);			
    	}
    }

    
    
    private List<Fisica> trasnformarPF(Map<String, String> parameters){
    	List<Fisica> listaPFs = new ArrayList<Fisica>();
    	for (Map.Entry<String, String> entry : parameters.entrySet()) {
    		Fisica pf = new Fisica();
    		pf.setRfc(entry.getKey());
    		pf.setNss(entry.getValue());
    		listaPFs.add(pf);
    	}
    	return listaPFs;
    }
    
}