package mx.gob.imss.cit.dacvass.servicios.externos.service.util;

import mx.gob.imss.digital.modelo.domicilio.Asentamiento;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.domicilio.EntidadFederativa;
import mx.gob.imss.digital.modelo.domicilio.Localidad;
import mx.gob.imss.digital.modelo.domicilio.Municipio;
import mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento;

public class ParserDomicilioGeograficoToRest {
	
	public static Domicilio parserDomicilioDeltaToDomicilioDigital(mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioModelo) throws Exception{
		
        Domicilio domicilio = new Domicilio();
        domicilio.setCodigoPostal(domicilioModelo.getCodigoPostal().getCodigoPostal());
        domicilio.setIdDomicilio(domicilioModelo.getClave().longValue());
        domicilio.setCalle(domicilioModelo.getCalle());
        domicilio.setColonia(domicilioModelo.getColonia());
        domicilio.setNumExterior1(domicilioModelo.getNumExterior1());
        domicilio.setNumExterior2(domicilioModelo.getNumExterior2());
        domicilio.setNumExteriorAlf(domicilioModelo.getNumExteriorAlf());
        domicilio.setNumInterior(domicilioModelo.getNumInterior());
        domicilio.setNumInteriorAlf(domicilioModelo.getNumInteriorAlf());
        domicilio.setLatitud(domicilioModelo.getLatitud());
        domicilio.setLongitud(domicilioModelo.getLongitud());
        if (domicilioModelo.getVialidadPrimaria() != null) {
            domicilio.setVialidadPrimaria(new mx.gob.imss.digital.modelo.domicilio.Vialidad());
            domicilio.getVialidadPrimaria().setNombre(domicilioModelo.getVialidadPrimaria().getNombre());
        }
        if (domicilioModelo.getAsentamiento() != null) {
            domicilio.setAsentamiento(new mx.gob.imss.digital.modelo.domicilio.Asentamiento());
            domicilio.getAsentamiento().setClave(domicilioModelo.getAsentamiento().getClave());
            domicilio.getAsentamiento().setCodigoPostal(domicilioModelo.getCodigoPostal().getCodigoPostal());
            domicilio.getAsentamiento().setNombre(domicilioModelo.getAsentamiento().getNombre());
            if(domicilioModelo.getAsentamiento().getTipoAsentamiento() != null) {
            	TipoAsentamiento tipoAsentamiento = new TipoAsentamiento();
            	tipoAsentamiento.setClave(domicilioModelo.getAsentamiento().getTipoAsentamiento().getClave());
            	tipoAsentamiento.setDescripcion(domicilioModelo.getAsentamiento().getTipoAsentamiento().getDescripcion());
            	domicilio.getAsentamiento().setTipoAsentamiento(tipoAsentamiento);
            }
            
        }
        mx.gob.imss.ctirss.delta.model.domicilio.Localidad localidad = domicilioModelo
                .getLocalidad() != null ? domicilioModelo.getLocalidad() : domicilioModelo
                        .getAsentamiento().getLocalidad();
        if (localidad != null) {

            if(domicilio.getAsentamiento()==null){
                domicilio.setAsentamiento(new mx.gob.imss.digital.modelo.domicilio.Asentamiento());
                domicilio.getAsentamiento().setClave(domicilioModelo.getAsentamiento().getClave());
            }
            domicilio.getAsentamiento().setLocalidad(new mx.gob.imss.digital.modelo.domicilio.Localidad());
            domicilio.getAsentamiento().getLocalidad().setClave(localidad.getClave());
            domicilio.getAsentamiento().getLocalidad().setNombre(localidad.getNombre());

            domicilio.setLocalidad(new mx.gob.imss.digital.modelo.domicilio.Localidad());
            domicilio.getLocalidad().setClave(localidad.getClave());
            domicilio.getLocalidad().setNombre(localidad.getNombre());
            mx.gob.imss.ctirss.delta.model.domicilio.Municipio municipio = domicilioModelo
                    .getAsentamiento().getMunicipio() != null ? domicilioModelo
                            .getAsentamiento().getMunicipio() : localidad.getMunicipio();

            if (municipio != null) {

                domicilio.getAsentamiento().getLocalidad().setMunicipio(new Municipio());
                domicilio.getAsentamiento().getLocalidad().getMunicipio().setClave(municipio.getClave());
                domicilio.getAsentamiento().getLocalidad().getMunicipio().setNombre(municipio.getNombre());

                domicilio.getLocalidad().setMunicipio(new Municipio());
                domicilio.getLocalidad().getMunicipio().setClave(municipio.getClave());
                domicilio.getLocalidad().getMunicipio().setNombre(municipio.getNombre());
                if (municipio.getEntidadFederativa() != null) {

                    domicilio.getAsentamiento().getLocalidad().getMunicipio()
                            .setEntidadFederativa(new EntidadFederativa());
                    domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa()
                            .setClave(municipio.getEntidadFederativa().getClave());
                    domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa()
                            .setNombre(municipio.getEntidadFederativa().getNombre());

                    domicilio.getLocalidad().getMunicipio()
                            .setEntidadFederativa(new EntidadFederativa());
                    domicilio.getLocalidad().getMunicipio().getEntidadFederativa()
                            .setClave(municipio.getEntidadFederativa().getClave());
                    domicilio.getLocalidad().getMunicipio().getEntidadFederativa()
                            .setNombre(municipio.getEntidadFederativa().getNombre());
                	}
                }

        }

        return domicilio;
		
	}

	public static Asentamiento parserAsentamientoDeltaToDigital(mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento asentamientoModelo) throws Exception{
		
		Asentamiento asentamientoDigital = new Asentamiento();
		asentamientoDigital.setClave(asentamientoModelo.getClave());
		asentamientoDigital.setNombre(asentamientoModelo.getNombre());
		asentamientoDigital.setCodigoPostal(asentamientoModelo.getCodigoPostal().getCodigoPostal());
		 if(asentamientoModelo.getTipoAsentamiento() != null) {
         	TipoAsentamiento tipoAsentamiento = new TipoAsentamiento();
         	tipoAsentamiento.setClave(asentamientoModelo.getTipoAsentamiento().getClave());
         	tipoAsentamiento.setDescripcion(asentamientoModelo.getTipoAsentamiento().getDescripcion());
         	asentamientoDigital.setTipoAsentamiento(tipoAsentamiento);
         }
		
		 
		asentamientoDigital.setMunicipio((new Municipio()));
		asentamientoDigital.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		asentamientoDigital.getMunicipio().getEntidadFederativa().setClave(asentamientoModelo.getLocalidad().getMunicipio().getEntidadFederativa().getClave());
		asentamientoDigital.getMunicipio().getEntidadFederativa().setNombre(asentamientoModelo.getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
		asentamientoDigital.getMunicipio().setClave(asentamientoModelo.getLocalidad().getMunicipio().getClave());
		asentamientoDigital.getMunicipio().setNombre(asentamientoModelo.getLocalidad().getMunicipio().getNombre());
		
//TODO se comenta la localidad ya que actualmente no la considera la consulta		
//		asentamientoDigital.setLocalidad(new mx.gob.imss.digital.modelo.domicilio.Localidad());
//		asentamientoDigital.getLocalidad().setClave(asentamientoModelo.getLocalidad().getClave());
//		asentamientoDigital.getLocalidad().setNombre(asentamientoModelo.getLocalidad().getNombre());
//		asentamientoDigital.getLocalidad().setMunicipio(asentamientoDigital.getMunicipio());
//		
		return asentamientoDigital;
	}
	
	
	public static Localidad parserLocalidadDeltaToDigital(mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.Localidad localidadModelo) throws Exception{
		Localidad localidadDigital = new Localidad();
		localidadDigital.setClave(localidadModelo.getClave());
		localidadDigital.setNombre(localidadModelo.getNombre());
		localidadDigital.setMunicipio((new Municipio()));
		localidadDigital.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		localidadDigital.getMunicipio().getEntidadFederativa().setClave(localidadModelo.getMunicipio().getEntidadFederativa().getClave());
		localidadDigital.getMunicipio().getEntidadFederativa().setNombre(localidadModelo.getMunicipio().getEntidadFederativa().getNombre());
		localidadDigital.getMunicipio().setClave(localidadModelo.getMunicipio().getClave());
		localidadDigital.getMunicipio().setNombre(localidadModelo.getMunicipio().getNombre());
		return localidadDigital;
	}

}
