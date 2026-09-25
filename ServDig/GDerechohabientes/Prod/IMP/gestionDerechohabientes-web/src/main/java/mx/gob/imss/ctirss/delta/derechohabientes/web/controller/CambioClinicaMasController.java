package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CambioMasivoClinicaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.cambioClinicaMas.CambioClinicaMasVb;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cambioClinicaMas")
public class CambioClinicaMasController extends AbstractController{

	private static final String CAMBIO_CLINICA_MAS_COLONIAS = "cambioClinicaMasCol";

	@Autowired
	CambioMasivoClinicaServiceRemote cambioMasivoClinicaServiceRemote;
	@Autowired
	UmfServiceRemote umfServiceRemote;

	@RequestMapping("/inicio")
	public String buscaDelegacionUmf() {
		Log.debug("inicio del cambio de clinic Masivo");
		String fordward = CAMBIO_CLINICA_MAS_COLONIAS;
		return fordward;
	}

	/**
	 * Metodo para obtener los medicos que atienden un consultorio, en un turno, en una umf
	 * @param umfTurno
	 * @return
	 */
	@RequestMapping( value = "/getMedicoPoblacion", method = RequestMethod.POST)
	public @ResponseBody List<MedicoEnTurno> getMedicosUmfTurnoConsultorio(@RequestBody MedicoEnTurno umfTurno) {
		
		List<MedicoEnTurno> medicos = null;
		
		try {
			medicos = umfServiceRemote.findMedicosByUmfTurnoConsultorioConPoblacion(
					umfTurno.getUnidadMedicaFamiliar().getIdUMF(),
					umfTurno.getTurno().getIdTurno(),
					umfTurno.getConsultorio().getIdConsultorio());
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return medicos;
	}
	
	@RequestMapping("/getUmfsPorDelegacion")
	public @ResponseBody
	List<UnidadMedicaFamiliar> buscaUmf(HttpSession ses) {
		
		Usuario usuario = (Usuario) ses.getAttribute(Usuario.SES_NAME);
		log.debug("La umf del usuario es: " + usuario.getIdUmf());
		log.debug("Subdelegacion: " + usuario.getCveIdSubdelegacion());
		List<UnidadMedicaFamiliar> umfCodigoPostals=null;
		try {
			log.debug("Bsqueda de las umfs");
			umfCodigoPostals = this.cambioMasivoClinicaServiceRemote.getUmfBySubDelegacionSinUmf(usuario.getCveIdSubdelegacion(), null);
		} catch (Exception e) {
			log.error("Error inesperado ", e);
		}

		return umfCodigoPostals;
	}
	
	@RequestMapping("/getAsentamientosPorUmf")
	public @ResponseBody List<Asentamiento> getAsentamientosPorUmf(HttpSession ses, @RequestParam("umfOrigen") Long umfOrigen) {
		List<Asentamiento> asentamientosList=null;
		Usuario usuario = (Usuario) ses.getAttribute(Usuario.SES_NAME);
		try {
			asentamientosList=this.cambioMasivoClinicaServiceRemote.getAsentamientosPorUmf(umfOrigen);
		} catch (DerechohabientesBusinessException e) {
			log.error("Error inesperado", e);
		} catch (Exception e) {
			log.error("Error inesperado", e);
		}		
		return asentamientosList;
	}
	
	

	
	@RequestMapping("/getMedicosPorUmf")
	public @ResponseBody List<MedicoEnTurno> getMedicos(@RequestParam(value="idUmf",required=true)Long idUmf) {
		List<MedicoEnTurno> medicoEnTurnos = null;
		// servicio buscar los medicos por umf
		try {
			medicoEnTurnos = this.cambioMasivoClinicaServiceRemote.medicoEnTurnos(idUmf);
		} catch (DerechohabientesBusinessException e) {
			log.error("Error inesperado", e);
		} catch (Exception e) {
			log.error("Error inesperado", e);
		}
		return medicoEnTurnos;
	}
	
	@RequestMapping(value = "/cambioClinica", method = RequestMethod.GET)
	public @ResponseBody
	CambioClinicaMasVb cambioClinica(@RequestParam(value="medicosString",required=true)String medicosString,
						@RequestParam(value="medicosStringV",required=true)String medicosStringV,
						@RequestParam(value="asentamientosString",required=true)String asentamientosString,
						@RequestParam(value="idUmfOrigen",required=true)Long idUmfOrigen,
						@RequestParam(value="idUmfDestino",required=true)Long idUmfDestino,
						HttpSession ses) throws DerechohabientesBusinessException {
		Usuario usuario=(Usuario) ses.getAttribute(Usuario.SES_NAME);
		CambioClinicaMasVb cambioClinicaMasVb=null;
		Iterator<String> iterIdMedico=null;
		Iterator<String> iterIdMedicoV=null;

		Long regMod[]=null;
		List<Asentamiento> asentamientosList = null;
		try {
			asentamientosList = this.cambioMasivoClinicaServiceRemote.getAsentamientosPorUmf(idUmfOrigen);
		} catch (Exception e) {
			log.error("Error inesperado - ", e);
		}

		List<String> idAsentamientos=tokensTransform(asentamientosString);
		List<String> idMedicos=tokensTransform(medicosString);
		List<String> idMedicosV=tokensTransform(medicosStringV);
		iterIdMedico=idMedicos.iterator();
		iterIdMedicoV=idMedicosV.iterator();
		//Listas con los datos necesarios para el cambio de clinica
		List<Asentamiento> asentamientos = new ArrayList<Asentamiento>();
		List<MedicoEnTurno> turnoMatutino = new ArrayList<MedicoEnTurno>();
		List<MedicoEnTurno> turnoVespertino = new ArrayList<MedicoEnTurno>();
		
		for(String idAsentamiento:idAsentamientos){
			MedicoEnTurno matutino = new MedicoEnTurno();
			MedicoEnTurno vespertino = new MedicoEnTurno();
			matutino.setIdMedicoContultorioTurno(new Long(iterIdMedico.next()));
			vespertino.setIdMedicoContultorioTurno(new Long(iterIdMedicoV.next()));
			
			turnoMatutino.add(matutino);
			turnoVespertino.add(vespertino);
			asentamientos.add(findAsentamiento(idAsentamiento,asentamientosList));
	
		}
		
		try {
			//Se setea la umf origen al usuario ya que no tiene umf
			usuario.setIdUmf(idUmfOrigen);
			regMod = this.cambioMasivoClinicaServiceRemote.guardarCambioClinicaMasivo(asentamientos, turnoMatutino, turnoVespertino, usuario, idUmfDestino);
		} catch (Exception e) {
			log.error("Error inesperado", e);
		}
		
		cambioClinicaMasVb=new CambioClinicaMasVb();
		cambioClinicaMasVb.setNumDerMod(String.valueOf(regMod[0]));
		cambioClinicaMasVb.setNumSolMod(String.valueOf(regMod[1]));
		
		return cambioClinicaMasVb;
	}
	
	private List<String> tokensTransform(String entrada){
		List<String> stList=new ArrayList<String>();
		StringTokenizer st=new StringTokenizer(entrada, ",");
		while(st.hasMoreElements()){
			stList.add(st.nextToken());
		}
		return stList;
	}
	
	
	private Asentamiento findAsentamiento(String idAsentamiento, List<Asentamiento> asentamientoList){
		for(Asentamiento asentamiento:asentamientoList){
			if(asentamiento.getClave().equals(idAsentamiento)){
				asentamiento.setPeriodo(1L);
				return asentamiento;
			}
		}
		return null;
	}
}
