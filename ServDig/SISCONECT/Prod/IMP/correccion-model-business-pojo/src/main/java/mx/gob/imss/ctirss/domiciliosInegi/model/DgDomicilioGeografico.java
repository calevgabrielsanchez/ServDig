package mx.gob.imss.ctirss.domiciliosInegi.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.jdbc.util.DDLFormatterImpl;
import org.springframework.aop.aspectj.AspectJAdviceParameterNameDiscoverer.AmbiguousBindingException;
import org.springframework.scheduling.support.PeriodicTrigger;

import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgAsentamientoId;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatLocalidadId;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCatMunicipioId;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgCodigosPostalesId;
import mx.gob.imss.ctirss.domiciliosInegi.base.model.AbstractDgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCamino;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAsentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.math.BigDecimal;


/**
 * The persistent class for the DG_DOMICILIO_GEOGRAFICO database table.
 * 
 */
//@Entity
//@Table(name="DG_DOMICILIO_GEOGRAFICO")
//@OnSearchLlavePrimaria(atributos="domicilioId")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DgDomicilioGeografico extends AbstractDgDomicilioGeografico {
	
	
	private Boolean bloquearEstado;
	
	
	@Transient
	private Domicilio domicilioBDTU;
	
	
	@Transient
	private HashMap<String,Object> listCompDom;

	
	public DgDomicilioGeografico(){setBloquearEstado(true);}
	public DgDomicilioGeografico(LinkedHashMap<String,LinkedHashMap> mapa) {
		setBloquearEstado(true);
		this.setDgAsentamiento(new DgAsentamiento());
		this.setDgCatLocalidad(new DgCatLocalidad());
		this.setDgCatTipoDom(new DgCatTipoDom());
		
		this.setDgCodigosPostales(new DgCodigosPostales());
		
		LinkedHashMap<String,LinkedHashMap> mapDomicilioInegi = (LinkedHashMap)mapa.get("domicilioInegi");
		LinkedHashMap<String,LinkedHashMap> mapDgCatLocalidad = (LinkedHashMap)mapa.get("domicilioInegi").get("dgCatLocalidad");
		LinkedHashMap<String,LinkedHashMap> mapDgCatAmbito = (LinkedHashMap)mapDgCatLocalidad.get("dgCatAmbito");
		LinkedHashMap<String,LinkedHashMap> mapDgMunicipio = (LinkedHashMap)mapDgCatLocalidad.get("dgCatMunicipio");
		LinkedHashMap<String,LinkedHashMap> mapDgAsentamiento = (LinkedHashMap)mapDomicilioInegi.get("dgAsentamiento");
		LinkedHashMap<String,LinkedHashMap> mapDgCatEstado = (LinkedHashMap)mapDgMunicipio.get("dgCatEstado");
		LinkedHashMap<String,LinkedHashMap> mapDgCodigoPostal = (LinkedHashMap)mapa.get("domicilioInegi").get("dgCodigosPostales");
		LinkedHashMap<String,LinkedHashMap> mapDgCatTipoDomicilio = (LinkedHashMap)mapa.get("domicilioInegi").get("dgCatTipoDom");
		
		String currentValue = "" + mapDomicilioInegi.get("cveTipoVial");
		/*
		this.setCveTipoVial(currentValue!=null ? new Integer(currentValue):null);
		
		currentValue = "" + mapDomicilioInegi.get("cveTipoVialRef1");
		this.setCveTipoVialRef1(currentValue!=null ? new Integer(currentValue):null);
		
		currentValue = "" + mapDomicilioInegi.get("cveTipoVialRef2");
		this.setCveTipoVialRef2(currentValue!=null ? new Integer(currentValue):null);
		
		currentValue = "" + mapDomicilioInegi.get("cveTipoVialRef3");
		this.setCveTipoVialRef3(currentValue!=null ? new Integer(currentValue):null);
		
		this.setCveUsuario(null);
		
		currentValue = "" +mapDomicilioInegi.get("cveViaPrin");
		this.setCveViaPrin(currentValue!=null ? new BigDecimal(currentValue):null);
		
		currentValue = "" + mapDomicilioInegi.get("cveViaRef1");
		this.setCveViaRef1(currentValue!=null ? new BigDecimal(currentValue):null);
		
		currentValue = "" + mapDomicilioInegi.get("cveViaRef2");
		this.setCveViaRef2(currentValue!=null ? new BigDecimal(currentValue):null);
		
		currentValue = "" + mapDomicilioInegi.get("cveTipoVialRef3");
		this.setCveViaRef3(currentValue!=null ? new BigDecimal(currentValue):null);
		*/
		this.setDescripc(null);
		
		currentValue = "" + mapDgAsentamiento.get("dgCatTipoAsen").get("cveTipoAsen");
		this.getDgAsentamiento().setDgCatTipoAsen(new DgCatTipoAsen());
		this.getDgAsentamiento().getDgCatTipoAsen().setCveTipoAsen(currentValue!=null ? new Integer(currentValue):null);
		
		currentValue = "" + mapDgAsentamiento.get("id").get("cveAsen");
		this.getDgAsentamiento().setId(new AbstractDgAsentamientoId());
		this.getDgAsentamiento().getId().setCveAsen(currentValue!=null ? currentValue:null);
		
		currentValue = "" + mapDgCatLocalidad.get("id").get("cveLoc");
		this.getDgCatLocalidad().setId(new AbstractDgCatLocalidadId());
		this.getDgCatLocalidad().getId().setCveLoc(currentValue!=null ? currentValue:null);
		
		currentValue = "" + mapDgCatAmbito.get("ambito");
		this.getDgCatLocalidad().setDgCatAmbito(new DgCatAmbito());
		this.getDgCatLocalidad().getDgCatAmbito().setAmbito(currentValue!=null ? new Integer(currentValue):null);
	
		currentValue = "" + mapDgMunicipio.get("id").get("cveMun");
		this.getDgCatLocalidad().setDgCatMunicipio(new DgCatMunicipio());
		this.getDgCatLocalidad().getDgCatMunicipio().setId(new AbstractDgCatMunicipioId());
		this.getDgCatLocalidad().getDgCatMunicipio().getId().setCveMun(currentValue!=null ? currentValue:null);
		
		currentValue = "" + mapDgCatEstado.get("cveEnt");
		this.getDgCatLocalidad().getDgCatMunicipio().setDgCatEstado(new DgCatEstado());
		this.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().setCveEnt(currentValue!=null ? currentValue:null);
		
		currentValue = "" + mapDgCatTipoDomicilio.get("cveTipoDom");
		this.setDgCatTipoDom(new DgCatTipoDom());
		this.getDgCatTipoDom().setCveTipoDom(currentValue!=null ? new Integer(currentValue):null);
		
		currentValue = "" + mapDgCodigoPostal.get("id").get("codigo");
		this.setDgCodigosPostales(new DgCodigosPostales());
		this.getDgCodigosPostales().getId().setCodigo(currentValue!=null ? currentValue:null);
		
		this.setDomGeog(null);		
		this.setFechaHoraAlta(null);
		
		currentValue = "" + mapDomicilioInegi.get("nomVial");
		this.setNomvial(currentValue!=null ? currentValue:"");
		
		currentValue = "" + mapDomicilioInegi.get("numextalf");
		this.setNumextalf(currentValue!=null ? currentValue:"");
		
		currentValue = "" + mapDomicilioInegi.get("numextAnt");
		this.setNumextAnt(currentValue!=null ? currentValue:"");
		
		currentValue = "" + mapDomicilioInegi.get("numextnum");
		this.setNumextnum(currentValue!=null ? new Integer(currentValue):0);
		
		currentValue = "" + mapDomicilioInegi.get("numintalf");
		this.setNumintalf(currentValue!=null ? currentValue:"");
		
		currentValue = "" + mapDomicilioInegi.get("numintnum");
		this.setNumintnum(currentValue!=null ? new Integer(currentValue):0);
		
		currentValue = "" + mapDomicilioInegi.get("hastableKeyDG");
		this.setHastableKeyDG((currentValue!=null ? currentValue:""));

		this.getDgAsentamiento().getId().setCveEnt(this.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
		this.getDgAsentamiento().getId().setCveLoc(this.getDgCatLocalidad().getId().getCveLoc());
		this.getDgAsentamiento().getId().setCveMun(this.getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
		this.getDgAsentamiento().getId().setCvePeriodo(1);
		
		this.getDgCatLocalidad().getId().setCveEnt(this.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
		this.getDgCatLocalidad().getId().setCveMun(this.getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
		this.getDgCatLocalidad().getId().setCvePeriodo(1);
		
		this.getDgCatLocalidad().getDgCatMunicipio().getId().setCveEnt(this.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
		
		this.getDgCodigosPostales().getId().setCveAsen(this.getDgAsentamiento().getId().getCveAsen());
		this.getDgCodigosPostales().getId().setCveEnt(this.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
		this.getDgCodigosPostales().getId().setCveLoc(this.getDgCatLocalidad().getId().getCveLoc());
		this.getDgCodigosPostales().getId().setCveMun(this.getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
		this.getDgCodigosPostales().getId().setCvePeriodo(1);
		this.getDgCodigosPostales().setDgAsentamiento(this.getDgAsentamiento());
	}


	public String imprimeObjeto(){
		return new StringBuffer().append("DgDomicilioGeografico{")
								 .append("codigo:").append(this.getDgCodigosPostales().getId().getCodigo()).append(";\n")
								 .append("}")
								 .toString();
	}
	
	@Transient
	public Boolean getBloquearEstado() {
		return bloquearEstado;
	}
	public void setBloquearEstado(Boolean bloquearEstado) {
		this.bloquearEstado = bloquearEstado;
	}
	
	@Transient
	public Domicilio getDomicilioBDTU() {
		return domicilioBDTU;
	}
	
	//Una vez que se recibe los parametros de la BDTU se llena nuestro objeto local para cuestiones de consulta
	@Transient
	public void setDomicilioBDTU(Domicilio domicilioBDTU) {
		if(domicilioBDTU==null){
			System.out.println("No se puede recuperar el domicilio");
			return;
		}
		this.domicilioBDTU = domicilioBDTU;
		parserDomicilios(this, this.domicilioBDTU);
	}
	
	//Funcion que llena el dato local con los datos contenidos en la bdtu
	private void parserDomicilios(DgDomicilioGeografico domicilio,Domicilio dom){
		listCompDom=new HashMap<String, Object>();
		
		
		DgAsentamiento asentamiento=new DgAsentamiento();
		DgCatLocalidad localidad=new DgCatLocalidad();
		DgCatTipoDom tipoDomicilio=new DgCatTipoDom();
		DgCodigosPostales codigoPostal=new DgCodigosPostales();
		DgCatTipoAsen tipoAsentamiento=new DgCatTipoAsen();
		DgCatMunicipio municipio=new DgCatMunicipio();
		DgCatEstado estado=new DgCatEstado();
		DgCatAmbito ambito=new DgCatAmbito();
		DgCatPeriodo periodo=new DgCatPeriodo();
		
		DgVialidad principal=new DgVialidad();
		DgVialidad vialidad1=new DgVialidad();
		DgVialidad vialidad2=new DgVialidad();
		DgVialidad vialidad3=new DgVialidad();
		
				
		listCompDom.put("asentamiento", asentamiento);		
		listCompDom.put("localidad", localidad);		
		listCompDom.put("municipio", municipio);		
		listCompDom.put("tipoAsentamiento", tipoAsentamiento);
		listCompDom.put("ambito", ambito);
		listCompDom.put("estado", estado);
		listCompDom.put("tipoDomicilio", tipoDomicilio);
		listCompDom.put("codigoPostal", codigoPostal);
		listCompDom.put("periodo", periodo);
		
		
		
		listCompDom.put("vialidadPrincipal", principal);
		listCompDom.put("vialidad1", vialidad1);
		listCompDom.put("vialidad2", vialidad2);
		listCompDom.put("vialidad3", vialidad3);

		
		
		parserAsentamiento(asentamiento, dom.getAsentamiento(), listCompDom);
		parserLocalidad(localidad, dom.getAsentamiento().getLocalidad(), listCompDom);
		
		parserMunicipio(municipio, dom.getAsentamiento().getLocalidad().getMunicipio(), listCompDom);
		parserTipoAsentamiento(tipoAsentamiento, dom.getAsentamiento().getTipoAsentamiento(), listCompDom);
		//parserAmbito(ambito, dom.getAmbito(),listCompDom);No viene en el objeto
		parserEstado(estado,dom.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa(),listCompDom);
		//parserTipoDomicilio(tipoDomicilio, dom.getTipoDomicilio(), listCompDom);No viene
		parserCodigoPostal(codigoPostal, dom.getCodigoPostal(), dom, listCompDom);
		parserPeriodo(periodo, dom, listCompDom);		
			
		parserVialidad(principal, dom.getVialidadPrimaria(), listCompDom);
		parserVialidad(vialidad1, dom.getVialidadReferenciaPrimaria(), listCompDom);
		parserVialidad(vialidad2, dom.getVialidadReferenciaSecundaria(), listCompDom);
		parserVialidad(vialidad3, dom.getVialidadReferenciaPosterior(), listCompDom);
//		parserCamino(dom.getDomicilioCamino());
		
		domicilio.setDgAsentamiento(asentamiento);
		domicilio.setDgCatLocalidad(localidad);
		domicilio.setDgCatTipoDom(tipoDomicilio);
		domicilio.setDgCodigosPostales(codigoPostal);
		domicilio.setDgVialidadByCveViaPrin(principal);
		domicilio.setDgVialidadByCveViaRef1(vialidad1);
		domicilio.setDgVialidadByCveViaRef2(vialidad2);
		domicilio.setDgVialidadByCveViaRef3(vialidad3);
		domicilio.setDomicilioId(dom.getClave());
		domicilio.setNomvial(dom.getVialidadPrimaria()!=null ?dom.getVialidadPrimaria().getNombre():"");
		domicilio.setNumextalf(dom.getNumExteriorAlf()!=null ?dom.getNumExteriorAlf():"");
		domicilio.setNumextnum(dom.getNumExterior1()!=null ? dom.getNumExterior1():0);
		domicilio.setNumintalf(dom.getNumInteriorAlf()!=null ?dom.getNumInteriorAlf():"");
		domicilio.setNumintnum(dom.getNumInterior()!=null?dom.getNumInterior():0);
		domicilio.setNumextAnt(String.valueOf(dom.getNumExterior2()!=null ? dom.getNumExterior2():""));
	
		domicilio.setDomicilioId(dom.getClave());
		domicilio.getDgCatLocalidad().setDgCatMunicipio(municipio);
		
	}
	
	
	private void parserAsentamiento(DgAsentamiento asentamiento,Asentamiento asentBDTU,HashMap<String, Object> objetos){
		if(asentBDTU==null){
			return;
		}
		System.out.println("parserAsentamiento");
		asentamiento.setDgCatLocalidad((DgCatLocalidad) objetos.get("localidad"));
		asentamiento.setDgCatTipoAsen((DgCatTipoAsen) objetos.get("tipoAsentamiento"));
		AbstractDgAsentamientoId id=new AbstractDgAsentamientoId();
		id.setCveAsen(asentBDTU.getClave());
		id.setCveEnt(asentBDTU.getLocalidad().getMunicipio().getEntidadFederativa().getClave());
		id.setCveLoc(asentBDTU.getLocalidad().getClave());
		id.setCveMun(asentBDTU.getLocalidad().getMunicipio().getClave());
		asentamiento.setId(id);
		asentamiento.setNomAsen(asentBDTU.getNombre());	
	}
	
	
	private void parserLocalidad(DgCatLocalidad localidad,Localidad localidadBDTU,HashMap<String, Object> objetos){
			
			if(localidadBDTU==null){
				return;
			}
			System.out.println("parserLocalidad");
			localidad.setNomLoc(localidadBDTU.getNombre());
			localidad.setDgCatAmbito((DgCatAmbito) objetos.get("ambito"));
			localidad.setDgCatMunicipio(((DgCatMunicipio) objetos.get("municipio")));
			localidad.setDgCatPeriodo(((DgCatPeriodo) objetos.get("periodo")));
			AbstractDgCatLocalidadId idLocal=new AbstractDgCatLocalidadId();			
			
			
			idLocal.setCveLoc(localidadBDTU.getClave());
			idLocal.setCveEnt(localidadBDTU.getMunicipio().getEntidadFederativa().getClave());
			idLocal.setCveMun(localidadBDTU.getMunicipio().getClave());
			//idLocal.setCvePeriodo(localidadBDTU.getMunicipio().getEntidadFederativa().get);
			//idLocal.setCvePeriodo(((DgCatPeriodo) objetos.get("periodo")).getCvePeriodo());			
			localidad.setId(idLocal);			
			
	}
	
	
	private void parserMunicipio(DgCatMunicipio municipio,Municipio municipioBDTU,HashMap<String, Object> objetos){
		if(municipioBDTU==null){
			return;
		}
		System.out.println("parserMunicipio");
		municipio.setDgCatEstado((DgCatEstado) objetos.get("estado"));
		municipio.setNomMun(municipioBDTU.getNombre());		
		AbstractDgCatMunicipioId munId=new AbstractDgCatMunicipioId();
		munId.setCveEnt(municipioBDTU.getEntidadFederativa().getClave());
		munId.setCveMun(municipioBDTU.getClave());
		municipio.setId(munId);		
	}
	
	
	
	private void parserTipoAsentamiento(DgCatTipoAsen tipoAsentamiento,TipoAsentamiento tipoAsentamientoBDTU,HashMap<String, Object> objetos){
		if(tipoAsentamientoBDTU==null){
			return;
		}
		System.out.println("parserTipoAsentamiento");
		//tipoAsentamiento.setCveTipoAsen(tipoAsentamientoBDTU.getClave().intValue()); No viene 
		tipoAsentamiento.setNombre(tipoAsentamientoBDTU.getDescripcion());	
		
	}
	
	
	private void parserAmbito(DgCatAmbito ambito,TipoAmbito ambitoBDTU,HashMap<String, Object> objetos){
		if(ambitoBDTU==null){
			return;
		}
		System.out.println("parserAmbito");
		ambito.setAmbito(ambitoBDTU.getClave().intValue());
		ambito.setNombre(ambitoBDTU.getDescripcion());			
	}
	
	private void parserEstado(DgCatEstado estado,EntidadFederativa estadoBDTU,HashMap<String, Object> objetos){
		if(estadoBDTU==null){
			return;
		}
		System.out.println("parserEstado");
		estado.setCveEnt(estadoBDTU.getClave());
		estado.setNomEnt(estadoBDTU.getNombre());		
	}
	
	
	private void parserTipoDomicilio(DgCatTipoDom tipoDom,TipoDomicilio tipoDomBDTU,HashMap<String, Object> objetos){
		if(tipoDomBDTU==null){
			return;
		}
		System.out.println("parserTipoDomicilio");
		tipoDom.setCveTipoDom(tipoDomBDTU.getClave());
		tipoDom.setDescripcion(tipoDomBDTU.getDescripcion());	
	}
	
	private void parserCodigoPostal(DgCodigosPostales codigoPostal,CodigoPostal codigoPostalBDTU,Domicilio domicilio,HashMap<String, Object> objetos){
		if(codigoPostalBDTU==null){
			return;
		}
		System.out.println("parserCodigoPostal");
		codigoPostal.setDgAsentamiento((DgAsentamiento) objetos.get("asentamiento"));
		AbstractDgCodigosPostalesId idPostal=new AbstractDgCodigosPostalesId();
		idPostal.setCodigo(codigoPostalBDTU.getCodigoPostal());
		idPostal.setCveAsen(domicilio.getAsentamiento().getClave());
		idPostal.setCveEnt(domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getClave());
		idPostal.setCveLoc(domicilio.getAsentamiento().getLocalidad().getClave());
		idPostal.setCveMun(domicilio.getAsentamiento().getLocalidad().getMunicipio().getClave());				
		codigoPostal.setId(idPostal);
		
	}
	
	private void parserPeriodo(DgCatPeriodo periodo,Domicilio dom,HashMap<String, Object> objetos){		
		if(dom.getAsentamiento()==null){
			return;
		}
		System.out.println("parserPeriodo");
		Long val=dom.getAsentamiento().getPeriodo();
		periodo.setCvePeriodo(val.intValue());
	}
	
	
	private void parserVialidad(DgVialidad vialidad,Vialidad vialidadBDTU,HashMap<String, Object> objetos){
		System.out.println("parserVialidad Recuperado "+vialidadBDTU);
		if(vialidadBDTU==null){
			return;
		}		
		
		vialidad.setDgCatAmbito((DgCatAmbito) objetos.get("ambito"));
		vialidad.setDgCatLocalidad((DgCatLocalidad) objetos.get("localidad"));
		
		DgCatVialidad catVialidad=new DgCatVialidad();
		if(vialidadBDTU!=null && vialidadBDTU.getTipoVialidad()!=null){
			catVialidad.setCveTipoVial(new BigDecimal(vialidadBDTU.getTipoVialidad().getClave()));
			catVialidad.setDescripcion(vialidadBDTU.getTipoVialidad().getDescripcion());	
		}
		
		
		
		vialidad.setDgCatVialidad(catVialidad);
		if(vialidadBDTU!=null && vialidadBDTU.getClave()!=null){
			vialidad.setCveVia(new Long(vialidadBDTU.getClave()));
		}
			vialidad.setNomVia(vialidadBDTU.getNombre());	
		
				
		
	}
	
	private void parserCamino(DomicilioCamino domicilioCamino){
		System.out.println("Convirtiendo objeto domicilioCamino "+domicilioCamino);
		if(domicilioCamino==null){
			return;
		}else{
			System.out.println("Imprimiendo camino "+domicilioCamino.toString());
		}
	}
}