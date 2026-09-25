package mx.gob.imss.ctirss.idse.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import mx.gob.imss.ctirss.idse.model.Certificado;
import mx.gob.imss.ctirss.idse.model.Persona;
import mx.gob.imss.ctirss.idse.model.RegistroPatronal;
import mx.gob.imss.ctirss.idse.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.idse.service.interfaces.RegistroPatronalIdseServiceBusinessRemote;

import org.junit.Before;
import org.junit.Test;

public class RegistroPatronalIdseTest{
	
	RegistroPatronalIdseServiceBusinessRemote registroPatronalIdseServiceBusiness;
		
	@Before
	public void setUp() {
		registroPatronalIdseServiceBusiness = EJBLocator.getRegistroPatronalIdseServiceBusiness();		
	}
	
	@Test
	public void test() {
		int prueba = 0;
		
		if(prueba==1){
			altaPatronalPatronPFconRL();
		}else if(prueba==2){
			altaPatronalPatronPFsinRL();
		}else if(prueba==3){
			altaPatronalPatronPM();
		}else if(prueba==4){
			altaPatronalFull();
		}else if(prueba==5){
			desasociarRegistroPatronal();
		}else if(prueba==6){
			desasociarRepresentanteLegal();
		}else if(prueba==7){
			asociarRepresentanteLegal();
		}else{

		}
				
	}
	
	private void desasociarRegistroPatronal() {
		try {
			RegistroPatronal registroPatronal = new RegistroPatronal();
			registroPatronal.setNrp("A0666999101");
			registroPatronal.setPatron(getPatronPF(""));					
			registroPatronalIdseServiceBusiness.desasociarRegistroPatronal(registroPatronal);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private void desasociarRepresentanteLegal() {
		try {	
			RegistroPatronal registroPatronal = new RegistroPatronal();		
			registroPatronal.setPatron(getPatronPF(""));
			List<Persona> listaRL = new ArrayList<Persona>();
			listaRL.add(getRepresentanteLegalPF());
			Persona[] representantes = (Persona[]) listaRL.toArray(new Persona[listaRL.size()]);
			registroPatronal.setRepresentantes(representantes);
			registroPatronalIdseServiceBusiness.desasociarRepresentanteLegal(registroPatronal);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private void asociarRepresentanteLegal() {
		try {	
			RegistroPatronal registroPatronal = new RegistroPatronal();		
			registroPatronal.setPatron(getPatronPF(""));
			List<Persona> listaRL = new ArrayList<Persona>();
			listaRL.add(getRepresentanteLegalNuevo());
			Persona[] representantes = (Persona[]) listaRL.toArray(new Persona[listaRL.size()]);
			registroPatronal.setRepresentantes(representantes);			
			registroPatronalIdseServiceBusiness.asociarRepresentanteLegal(registroPatronal);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private void altaPatronalPatronPFconRL() {				
		RegistroPatronal registroPatronal = new RegistroPatronal();		
		registroPatronal.setDomicilioCentroTrabajo("Domicilio Centro Trabajo T1");
		registroPatronal.setNrp("A0666999101");
		registroPatronal.setRazonSocial("Patron1 Paterno Materno");
		registroPatronal.setPatron(getPatronPF(registroPatronal.getRazonSocial()));
		List<Persona> listaRL = new ArrayList<Persona>();
		listaRL.add(getRepresentanteLegalPF());
		Persona[] representantes = (Persona[]) listaRL.toArray(new Persona[listaRL.size()]);
		registroPatronal.setRepresentantes(representantes);
		
		try {			
			registroPatronalIdseServiceBusiness.altaRegistroPatronal(registroPatronal);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	private void altaPatronalPatronPFsinRL() {
		RegistroPatronal registroPatronal = new RegistroPatronal();		
		registroPatronal.setDomicilioCentroTrabajo("Domicilio Centro Trabajo T2");
		registroPatronal.setNrp("A0666999102");
		registroPatronal.setRazonSocial("Patron1 Paterno Materno");
		registroPatronal.setPatron(getPatronPF(registroPatronal.getRazonSocial()));
		registroPatronal.setRepresentanteLegal(null);
		registroPatronal.setRepresentantes(null);
		try {			
			registroPatronalIdseServiceBusiness.altaRegistroPatronal(registroPatronal);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private void altaPatronalPatronPM() {
		RegistroPatronal registroPatronal = new RegistroPatronal();
		registroPatronal.setDomicilioCentroTrabajo("Domicilio Centro Trabajo T3");
		registroPatronal.setNrp("A0666999103");
		registroPatronal.setRazonSocial("NOVUTEK T1 S.A. DE C.V.");
		registroPatronal.setPatron(getPatronPM(registroPatronal.getRazonSocial()));
		List<Persona> listaRL = new ArrayList<Persona>();
		listaRL.add(getRepresentanteLegalPM());
		Persona[] representantes = (Persona[]) listaRL.toArray(new Persona[listaRL.size()]);
		registroPatronal.setRepresentantes(representantes);
		
		try {			
			registroPatronalIdseServiceBusiness.altaRegistroPatronal(registroPatronal);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	private void altaPatronalFull() {
		altaPatronalPatronPFconRL();
		altaPatronalPatronPFsinRL();
		altaPatronalPatronPM();	
		//Nuevo nrp, con mismo patron pm y rl
		RegistroPatronal registroPatronal = new RegistroPatronal();
		registroPatronal.setDomicilioCentroTrabajo("Domicilio Centro Trabajo T3");
		registroPatronal.setNrp("A0666999104");
		registroPatronal.setRazonSocial("NOVUTEK T1 S.A. DE C.V.");
		registroPatronal.setPatron(getPatronPM(registroPatronal.getRazonSocial()));
		
		List<Persona> listaRL = new ArrayList<Persona>();
		listaRL.add(getRepresentanteLegalPM());
		Persona[] representantes = (Persona[]) listaRL.toArray(new Persona[listaRL.size()]);
		registroPatronal.setRepresentantes(representantes);
		registroPatronal.setRepresentanteLegal(null);
		try {			
			registroPatronalIdseServiceBusiness.altaRegistroPatronal(registroPatronal);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}		
		RegistroPatronal registroPatronal1 = new RegistroPatronal();
		registroPatronal1.setDomicilioCentroTrabajo("Domicilio Centro Trabajo T1");
		registroPatronal1.setNrp("A0666999105");
		registroPatronal1.setRazonSocial("NOVUTEK T1 S.A. DE C.V.");
		registroPatronal1.setPatron(getPatronPM(registroPatronal.getRazonSocial()));
		
		listaRL = new ArrayList<Persona>();
		listaRL.add(getRepresentanteLegalPM());
		representantes = (Persona[]) listaRL.toArray(new Persona[listaRL.size()]);
		registroPatronal.setRepresentantes(representantes);
		registroPatronal.setRepresentanteLegal(null);
		
		try {			
			registroPatronalIdseServiceBusiness.altaRegistroPatronal(registroPatronal1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}		
	}
	

	private Persona getPatronPF(String rs){
		Persona patron = new Persona();		
		patron.setCertificado(new Certificado());
		patron.getCertificado().setClaveSerial("00001000000666010666");
		patron.setCorreoElectronico("patron_pft1@gmail.com");
		patron.setCurp("OIAM830425HHGRLR01");
		patron.setDomicilioFiscal("Domicilio Fiscal Patron PFT1");
		patron.setNombreRazonSocial(rs);
		patron.setNombreUsuario("OIAM830425TD1");
		patron.setRfc("OIAM830425TD1");
		patron.setTipoPersona(TipoPersonaEnum.FISICA.getId());
		return patron;
	}
	
	private Persona getPatronPM(String rs){
		Persona patron = new Persona();		
		patron.setCertificado(new Certificado());
		patron.getCertificado().setClaveSerial("00001000000999010666");
		patron.setCorreoElectronico("patron_pmt1@gmail.com");
		patron.setCurp(null);
		patron.setDomicilioFiscal("Domicilio Fiscal Patron PMT1");
		patron.setNombreRazonSocial(rs);
		patron.setNombreUsuario("PSP990201PVA");
		patron.setRfc("PSP990201PVA");
		patron.setTipoPersona(TipoPersonaEnum.MORAL.getId());		
		return patron;
	}
	
	private Persona getRepresentanteLegalPF(){
		Persona represetanteL = new Persona();		
		represetanteL.setCertificado(new Certificado());
		represetanteL.getCertificado().setClaveSerial("00001000000333010666");
		represetanteL.setCorreoElectronico("representanteLegal_t1@gmail.com");
		represetanteL.setCurp("VERA650124HTSRZR01");
		represetanteL.setDomicilioFiscal("Domicilio Fiscal RepresentanteLegal T1");
		represetanteL.setNombreRazonSocial("Representante1 Paterno Materno");
		represetanteL.setNombreUsuario("VERA650124DH1");
		represetanteL.setRfc("VERA650124DH1");
		represetanteL.setTipoPersona(TipoPersonaEnum.FISICA.getId());		
		return represetanteL;
	}
	
	private Persona getRepresentanteLegalPM(){
		Persona represetanteL = new Persona();		
		represetanteL.setCertificado(new Certificado());
		represetanteL.getCertificado().setClaveSerial("00001000000333020666");
		represetanteL.setCorreoElectronico("representanteLegal_t2@gmail.com");
		represetanteL.setCurp("VERA650124HTSRZR02");
		represetanteL.setDomicilioFiscal("Domicilio Fiscal RepresentanteLegal T2");
		represetanteL.setNombreRazonSocial("Representante2 Paterno Materno");
		represetanteL.setNombreUsuario("VERA650124DH2");
		represetanteL.setRfc("VERA650124DH2");
		represetanteL.setTipoPersona(TipoPersonaEnum.FISICA.getId());		
		return represetanteL;
	}
	
	private Persona getRepresentanteLegalNuevo(){
		Persona represetanteL = new Persona();		
		represetanteL.setCertificado(new Certificado());
		represetanteL.getCertificado().setClaveSerial("00001000000333050666");
		represetanteL.setCorreoElectronico("representanteLegal_tN@gmail.com");
		represetanteL.setCurp("VERA650124HTSRZR66");
		represetanteL.setDomicilioFiscal("Domicilio Fiscal RepresentanteLegal TN");
		represetanteL.setNombreRazonSocial("RepresentanteNuevo Paterno Materno");
		represetanteL.setNombreUsuario("VERA650124DH9");
		represetanteL.setRfc("VERA650124DH9");
		represetanteL.setTipoPersona(TipoPersonaEnum.FISICA.getId());		
		return represetanteL;
	}
	
}
