package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.cobranza.dto.CfdiRelacionadoDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.CfdiRelacionadosDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.ComplementoDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.ConceptoDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.ConceptosDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.DeduccionDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.EmisorDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.ImpuestosDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.NominaDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.PercepcionDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.ReceptorDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.RegimenDTO;
import mx.gob.imss.ctirss.delta.cobranza.model.Comprobante;
import mx.gob.imss.ctirss.delta.cobranza.model.ObjectFactory;
import mx.gob.imss.ctirss.delta.cobranza.model.TUbicacion;
import mx.gob.imss.ctirss.delta.cobranza.model.TUbicacionFiscal;
import mx.gob.imss.ctirss.delta.cobranza.model.common.Nomina;

public class ComprobanteFiscalXMLPlantillaServiceBusiness {
	
	private static final Logger LOG;
	static{
		LOG = LoggerFactory.getLogger(ComprobanteFiscalPagoServiceBusiness.class);
	}

   /**
	* @param Emisor
	* @param EmisorDTO
	* @param ObjectFactory 
	* @return Emisor
	* @throws 
	*/	
    public static Comprobante.Emisor getEmisor(Comprobante.Emisor emisor, EmisorDTO emisorDTO, ObjectFactory of){

        if(emisor == null){
            emisor = of.createComprobanteEmisor();
        }
        emisor.setNombre(emisorDTO.getNombre());
        emisor.setRfc(emisorDTO.getRfc());
        emisor.setRegimenFiscal(emisorDTO.getRegimenFiscal());

        //DomicilioFiscal
//        TUbicacionFiscal uf = of.createTUbicacionFiscal();
//        uf.setCalle(emisorDTO.getDomicilioFiscal().getCalle());
//        uf.setNoExterior(emisorDTO.getDomicilioFiscal().getNoExterior()); 
//        uf.setColonia(emisorDTO.getDomicilioFiscal().getColonia());
//        uf.setMunicipio(emisorDTO.getDomicilioFiscal().getMunicipio()); 
//        uf.setEstado(emisorDTO.getDomicilioFiscal().getEstado()); 
//        uf.setPais(emisorDTO.getDomicilioFiscal().getPais()); 
//        uf.setCodigoPostal(emisorDTO.getDomicilioFiscal().getCodigoPostal());
//        emisor.setDomicilioFiscal(uf);
        //Lugar de Expedición
//        TUbicacion u = of.createTUbicacion();
//        u.setCalle(emisorDTO.getExpedidoEn().getCalle());
//        u.setNoExterior(emisorDTO.getExpedidoEn().getNoExterior());
//        u.setColonia(emisorDTO.getExpedidoEn().getColonia()); 
//        u.setEstado(emisorDTO.getExpedidoEn().getEstado());
//        u.setPais(emisorDTO.getExpedidoEn().getPais()); 
//        u.setCodigoPostal(emisorDTO.getExpedidoEn().getCodigoPostal());
//        emisor.setExpedidoEn(u); 
        //Régimen Fiscal
//        Comprobante.Emisor.RegimenFiscal rf = of.createComprobanteEmisorRegimenFiscal();
//        
//        for(int i = 0; i<emisorDTO.getRegimenFiscal().length;i++){
//        	
//        	
//        	
//        	RegimenDTO regimen = (emisorDTO.getRegimenFiscal())[i];
//        	if(regimen != null){
//        		rf.setRegimen( regimen.getRegimen());
//        	}
//        	
//            
//            emisor.getRegimenFiscal().add(rf);
//        }
        
        return emisor;
    }
    
    /**
	* @param Receptor
	* @param ReceptorDTO
	* @param ObjectFactory
	* @return Receptor
	* @throws 
	*/    
    public static Comprobante.Receptor getReceptor(Comprobante.Receptor receptor, ReceptorDTO receptorDTO, ObjectFactory of) {
        
        if(receptor == null){
            receptor = of.createComprobanteReceptor();
        }
        //Atributos
        receptor.setNombre(receptorDTO.getNombre());
        receptor.setRfc(receptorDTO.getRfc());
        receptor.setUsoCFDI(receptorDTO.getUsoCFDI());
        //Ubicación del receptor
//        TUbicacion uf = of.createTUbicacion();
//        uf.setCalle(receptorDTO.getDomicilio().getCalle());
//        uf.setNoExterior(receptorDTO.getDomicilio().getNoExterior()); 
//        uf.setNoInterior(receptorDTO.getDomicilio().getNoInterior());
//        uf.setColonia(receptorDTO.getDomicilio().getColonia()); 
//        uf.setMunicipio(receptorDTO.getDomicilio().getMunicipio()); 
//        uf.setEstado(receptorDTO.getDomicilio().getEstado()); 
//        uf.setPais(receptorDTO.getDomicilio().getPais()); 
//        receptor.setDomicilio(uf);
        return receptor;
    }
    
    /**
	* @param Conceptos
	* @param ConceptosDTO
	* @param ObjectFactory
	* @return 
	* @throws 
	*/    
    public static Comprobante.Conceptos getConceptos(Comprobante.Conceptos conceptos, ConceptosDTO conceptosDTO, ObjectFactory of) {       
       
        if(conceptos == null){
            conceptos = of.createComprobanteConceptos();
        }
        
        List<Comprobante.Conceptos.Concepto> list = conceptos.getConcepto(); 
        
        
        
        
        for(int i=0; i < conceptosDTO.getConceptos().length;i++) {
        	
            Comprobante.Conceptos.Concepto c1 = of.createComprobanteConceptosConcepto();            
            
            ConceptoDTO c = conceptosDTO.getConceptos()[i];
            
            if(c != null){
            	c1.setCantidad(c.getCantidad());
            	c1.setClaveProdServ(c.getClaveProdServ());
            	c1.setClaveUnidad(c.getClaveUnidad());
                c1.setDescripcion(c.getDescripcion());
                c1.setValorUnitario(c.getValorUnitario());
                c1.setImporte(c.getImporte());
            }
            
            
            
            
            list.add(c1);
        }        
        return conceptos;
    }
    
    /**
	* @param CfdiRelacionados
	* @param CfdiRelacionadosDTO
	* @param ObjectFactory
	* @return 
	* @throws 
	*/    
	public static Comprobante.CfdiRelacionados getCfdiRelacionados(Comprobante.CfdiRelacionados cfdiRelacionados, CfdiRelacionadosDTO cfdiRelacionadosDTO, ObjectFactory of) {
		if (cfdiRelacionadosDTO != null) {
			
			if (cfdiRelacionados == null) {
	        	cfdiRelacionados = of.createComprobanteCfdiRelacionados();
	        }
			
			cfdiRelacionados.setTipoRelacion(cfdiRelacionadosDTO.getTipoRelacion());
			
	        List<Comprobante.CfdiRelacionados.CfdiRelacionado> list = cfdiRelacionados.getCfdiRelacionado();
	        
	        for(int i=0; i < cfdiRelacionadosDTO.getListCfdiRelacionadoDTOs().length; i++) {
	        	
	        	Comprobante.CfdiRelacionados.CfdiRelacionado c1 = of.createComprobanteCfdiRelacionadosCfdiRelacionado();            
	            
	            CfdiRelacionadoDTO c = cfdiRelacionadosDTO.getListCfdiRelacionadoDTOs()[i];
	            
	            if (c != null) {
	            	c1.setUuid(c.getUuid());
	            }
	            
	            list.add(c1);
	        }        
		}
        return cfdiRelacionados;
    }
    
    /**
	* @param Impuestos
	* @param ImpuestosDTO
	* @param ObjectFactory
	* @return 
	* @throws 
	*/    
    public static Comprobante.Impuestos getImpuestos(Comprobante.Impuestos impuestos, ImpuestosDTO impuestosDTO, ObjectFactory of) {

        if(impuestos == null){
            impuestos = of.createComprobanteImpuestos();
        }
        
        /*El usuario pidio que se eliminaran los nodos de traslados y retenciones
        Comprobante.Impuestos.Traslados trs = of.createComprobanteImpuestosTraslados();
        Comprobante.Impuestos.Retenciones rtn = of.createComprobanteImpuestosRetenciones();
        
        List<Comprobante.Impuestos.Traslados.Traslado> list = trs.getTraslado();
        List<Comprobante.Impuestos.Retenciones.Retencion> listR = rtn.getRetencion();
        Comprobante.Impuestos.Traslados.Traslado t1 = of.createComprobanteImpuestosTrasladosTraslado();
        Comprobante.Impuestos.Retenciones.Retencion r1 = of.createComprobanteImpuestosRetencionesRetencion();
        
        for(int i=0;i<impuestosDTO.getTraslados().getTraslados().size();i++){
            t1.setImporte(impuestosDTO.getTraslados().getTraslados().get(i).getImporte());
            t1.setImpuesto(impuestosDTO.getTraslados().getTraslados().get(i).getImpuesto());
            t1.setTasa(impuestosDTO.getTraslados().getTraslados().get(i).getTasa());
            list.add(t1);
        }
        
        for(int i=0;i<impuestosDTO.getRetenciones().getRetenciones().size();i++){
            r1.setImporte(impuestosDTO.getRetenciones().getRetenciones().get(i).getImporte());
            r1.setImpuesto(impuestosDTO.getRetenciones().getRetenciones().get(i).getImpuesto());
            listR.add(r1);
        }
        
        impuestos.setRetenciones(rtn);
        impuestos.setTraslados(trs);*/
        //impuestos.setTotalImpuestosRetenidos(impuestosDTO.getTotalImpuestosRetenidos());
        //impuestos.setTotalImpuestosTrasladados(impuestosDTO.getTotalImpuestosTrasladados());
        
        return impuestos;
    }

    /**
	* @param Complemento
	* @param complementoDTO
	* @param 
	* @return 
	* @throws 
	*/
    public static Comprobante.Complemento getComplemento(Comprobante.Complemento complemento, ComplementoDTO complementoDTO, ObjectFactory of){

        if(complemento == null){
            complemento = of.createComprobanteComplemento();
        }
        
        mx.gob.imss.ctirss.delta.cobranza.model.common.ObjectFactory ofNomina = new mx.gob.imss.ctirss.delta.cobranza.model.common.ObjectFactory();
        
        if(complementoDTO.getComplementos() != null){
            for(int i=0;i<complementoDTO.getComplementos().size();i++){
                if(complementoDTO.getComplementos().get(i) instanceof NominaDTO){
                    complemento.getAny().add(getNomina((NominaDTO)complementoDTO.getComplementos().get(i), ofNomina));
                }
            }
        }        
        return complemento;
    }
    
    /**
	* @param ComprobanteDTO
	* @param 
	* @return 
	* @throws 
	*/    
    private static Nomina getNomina( NominaDTO nominaDTO, mx.gob.imss.ctirss.delta.cobranza.model.common.ObjectFactory ofNomina){
        Nomina nomina = ofNomina.createNomina();
                
        Nomina.Percepciones percepciones = ofNomina.createNominaPercepciones();
        List<Nomina.Percepciones.Percepcion> list = percepciones.getPercepcion();
        Nomina.Percepciones.Percepcion perc = ofNomina.createNominaPercepcionesPercepcion();
        Nomina.Deducciones deducciones = ofNomina.createNominaDeducciones();
        List<Nomina.Deducciones.Deduccion> listDed = deducciones.getDeduccion();
        Nomina.Deducciones.Deduccion dedu = ofNomina.createNominaDeduccionesDeduccion();
        
        //Atributos Obligatorios
        nomina.setVersion(nominaDTO.getVersion());
        nomina.setNumEmpleado(nominaDTO.getNumEmpleado());
        nomina.setCURP(nominaDTO.getCurp());
        nomina.setTipoRegimen(nominaDTO.getTipoRegimen());
        nomina.setNumSeguridadSocial(nominaDTO.getNumSeguridadSocial());        
        nomina.setFechaPago(nominaDTO.getFechaPago());
        nomina.setFechaInicialPago(nominaDTO.getFechaInicialPago());
        nomina.setFechaFinalPago(nominaDTO.getFechaFinalPago());
        nomina.setNumDiasPagados(nominaDTO.getNumDiasPagados());
        nomina.setPeriodicidadPago(nominaDTO.getPeriodicidadPago());
        percepciones.setTotalGravado(nominaDTO.getPercepciones().getTotalGravado());
        percepciones.setTotalExento(nominaDTO.getPercepciones().getTotalExento());
        
        
        
        PercepcionDTO[] ps = nominaDTO.getPercepciones().getPercepcion();
        
        
        for(int i=0; i< ps.length ;i++){
        	
        	
        	PercepcionDTO p = ps[i];
        	if(p != null){
        		perc.setTipoPercepcion(p.getTipoPercepcion());
                perc.setClave(p.getClave());
                perc.setConcepto(p.getConcepto());
                perc.setImporteGravado(p.getImporteGravado());
                perc.setImporteExento(p.getImporteExento());
        	}
        	
            
            list.add(perc);
        }
        nomina.setPercepciones(percepciones);
        
        deducciones.setTotalGravado(nominaDTO.getDeducciones().getTotalGravado());
        deducciones.setTotalExento(nominaDTO.getDeducciones().getTotalExento());
        
        
        DeduccionDTO[] ds = nominaDTO.getDeducciones().getDeduccion();
        
        
        for(int i=0; i< ds.length;i++){
        	
        	DeduccionDTO d = ds[i];
        	if(d != null){
        		dedu.setTipoDeduccion(d.getTipoDeduccion());
                dedu.setClave(d.getClave());
                dedu.setConcepto(d.getConcepto());
                dedu.setImporteGravado(d.getImporteGravado());
                dedu.setImporteExento(d.getImporteExento());
        	}
        	
        	
            
            
            listDed.add(dedu);
        }
        nomina.setDeducciones(deducciones);
        return nomina;
    }
}
