package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import mx.gob.imss.ctirss.delta.cobranza.dto.CfdiRelacionadoDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.CfdiRelacionadosDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.ComprobanteDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.ConceptoDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.ConceptosDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.EmisorDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.ImpuestosDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.PagoReferenciadoDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.ReceptorDTO;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionComprobanteException;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.Constantes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ComprobanteFiscalPagoServiceBusiness {
	
	private static final Logger LOG;
	static{
		LOG = LoggerFactory.getLogger(ComprobanteFiscalPagoServiceBusiness.class);
	}
	
   /**
	* Metodo que construye los componenetes del comprobante con los parametos obtenidos
	* @param PagoReferenciadoDTO objeto con los datos ingresados para crear el comprobante fiscal
	* @return ComprobanteDTO objeto creado con los componentes del comprobante fiscal
	* @throws Exception
    */	
    public static ComprobanteDTO generaComprobante(PagoReferenciadoDTO pagosPorPatron) throws ErrorEnGeneracionComprobanteException {
            LOG.info("########## CREANDO COMPONENTES DEL COMPROBANTE FISCAL ##########");
	        DecimalFormat mf = new DecimalFormat("#####.##");
	        ComprobanteDTO comprobante = new ComprobanteDTO();
	        Date date = new GregorianCalendar().getTime();
	        BigDecimal total = null;
	        
	        total = (pagosPorPatron.getSubTotalIMSS() != null ? pagosPorPatron.getSubTotalIMSS() : new BigDecimal(0))
	                .add(pagosPorPatron.getSubTotRCV() != null ? pagosPorPatron.getSubTotRCV() : new BigDecimal(0))
	                .add(pagosPorPatron.getActIMSS() != null ? pagosPorPatron.getActIMSS() : new BigDecimal(0))
	                .add(pagosPorPatron.getActRCV() != null ? pagosPorPatron.getActRCV() : new BigDecimal(0))
	                .add(pagosPorPatron.getRecIMMS() != null ? pagosPorPatron.getRecIMMS() : new BigDecimal(0))
	                .add(pagosPorPatron.getRecRCV() != null ? pagosPorPatron.getRecRCV() : new BigDecimal(0));
	      
	        comprobante.setVersion(Constantes.VERSION_COMPROBANTE);
	        comprobante.setFecha(date);
	        comprobante.setFormaDePago(pagosPorPatron.getFormaPago());
	        if (pagosPorPatron.getTipoRelacion().equals("04")) {
	        	comprobante.setCfdiRelacionados(creaCfdiRelacionados(pagosPorPatron));
			}
	        comprobante.setTipoDeComprobante(Constantes.TIPO_COMPROBANTE_INGRESO);
	        comprobante.setMetodoDePago(Constantes.METODO_PAGO);
	        comprobante.setLugarExpedicion(Constantes.LUGAR_EXPEDICION);
	        comprobante.setMoneda(Constantes.MONEDA);
	        comprobante.setSerie(Constantes.SERIE);
	        comprobante.setEmisor(creaEmisor(pagosPorPatron));
	        comprobante.setReceptor(creaReceptor(pagosPorPatron));
	        comprobante.setConceptos(creaConceptos(pagosPorPatron));
	        comprobante.setImpuestos(creaImpuestos(pagosPorPatron));
	        comprobante.setSubTotal(new BigDecimal(mf.format(total)));
	        comprobante.setTotal(new BigDecimal(mf.format(total)));
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
	        String strFolio = pagosPorPatron.getPeriodo()+"-"+pagosPorPatron.getRegistroPatronal()+"-"+simpleDateFormat.format(pagosPorPatron.getFecPago())+"-"+pagosPorPatron.getFolioSUA();
	        comprobante.setFolio(strFolio);
	        LOG.info("########## SE CREARON LOS COMPONENTES EMISOR - RECEPTOR - CONCEPTOS - IMPUESTOS DEL COMPROBANTE FISCAL ##########");
	     return comprobante;
    }
    
    /**
	* Metodo que construye los CFDI Relacionados con los parametros ingresados
	* @param PagoReferenciadoDTO objeto con los datos ingresados para crear el comprobante fiscal
	* @return CfdiRelacionadosDTO regresa un objeto con los datos de los CFDI Relacionados como parte del comprobante fiscal
	* @throws 
    */
    private static CfdiRelacionadosDTO creaCfdiRelacionados(PagoReferenciadoDTO pagoReferenciado) {
                
    	CfdiRelacionadosDTO cfdiRelacionadosDTO = new CfdiRelacionadosDTO();
    	CfdiRelacionadoDTO[] listCfdiRelacionadoDTOs = new CfdiRelacionadoDTO[1];
    	CfdiRelacionadoDTO cfdiRelacionadoDTO = new CfdiRelacionadoDTO();
        
    	cfdiRelacionadosDTO.setTipoRelacion(pagoReferenciado.getTipoRelacion());
    	cfdiRelacionadoDTO.setUuid(pagoReferenciado.getUuid());
    	listCfdiRelacionadoDTOs[0] = cfdiRelacionadoDTO;
    	cfdiRelacionadosDTO.setListCfdiRelacionadoDTOs(listCfdiRelacionadoDTOs);
       
        return cfdiRelacionadosDTO;
    }
    
    /**
	* Metodo que construye el emisor con los parametros ingresados
	* @param PagoReferenciadoDTO objeto con los datos ingresados para crear el comprobante fiscal
	* @return EmisorDTO regresa un objeto con los datos del emisor como parte del comprobante fiscal
	* @throws 
    */
    private static EmisorDTO creaEmisor(PagoReferenciadoDTO pagoReferenciado)throws ErrorEnGeneracionComprobanteException {
                
        EmisorDTO emisor = new EmisorDTO();
        
        emisor.setNombre(Constantes.NOMBRE_IMSS);
        emisor.setRfc(Constantes.RFC_IMSS);
        emisor.setRegimenFiscal(Constantes.REGIMEN_FISCAL);

        //Agreagar Regimen Fiscal
//        RegimenDTO rf = new RegimenDTO();
//        rf.setRegimen(Constantes.REGIMEN_SIMPLIFICADO_IMSS);
        
//        List<RegimenDTO> regimenes = new ArrayList<RegimenDTO>();
//        regimenes.add(rf);
        
//        emisor.setRegimenFiscal(regimenes.toArray(new RegimenDTO[regimenes.size()]));
        
        //Agregar DomicilioFiscal
//        UbicacionFiscalDTO uf = new UbicacionFiscalDTO();
//        uf.setCalle(Constantes.CALLE_IMSS);
//        uf.setNoExterior(Constantes.NO_EXTERIOR_IMSS); 
//        uf.setColonia(Constantes.COLONIA_IMSS);
//        uf.setMunicipio(Constantes.MUNICIPIO_IMSS); 
//        uf.setEstado(Constantes.ESTADO_IMSS); 
//        uf.setPais(Constantes.LUGAR_EXPEDICION); 
//        uf.setCodigoPostal(Constantes.CP_IMSS);
//        emisor.setDomicilioFiscal(uf);
        
        //Agregar Lugar de Expedicion
//        UbicacionFiscalDTO u = new UbicacionFiscalDTO();
//        u.setCalle(Constantes.CALLE_IMSS);
//        u.setNoExterior(Constantes.NO_EXTERIOR_IMSS);
//        u.setColonia(Constantes.COLONIA_IMSS); 
//        u.setEstado(Constantes.ESTADO_IMSS);
//        u.setPais(Constantes.LUGAR_EXPEDICION); 
//        u.setCodigoPostal(Constantes.CP_IMSS);
//        emisor.setExpedidoEn(u);
       
        return emisor;
    }
    
    /**
	* Metodo que construye el receptor con los parametros ingresados
	* @param PagoReferenciadoDTO objeto con los datos ingresados para crear el comprobante fiscal
	* @return ReceptorDTO regresa un objeto con los datos del receptor como parte del comprobante fiscal
	* @throws 
    */
    private static ReceptorDTO creaReceptor(PagoReferenciadoDTO pagoReferenciado) throws ErrorEnGeneracionComprobanteException {
        ReceptorDTO receptor = new ReceptorDTO();
        receptor.setRfc(pagoReferenciado.getRfc());
        receptor.setNombre(pagoReferenciado.getRazonSocial());
        receptor.setUsoCFDI(Constantes.USO_CFDI);
//        UbicacionFiscalDTO uf = new UbicacionFiscalDTO();
//        uf.setPais(Constantes.PAIS);
//        receptor.setDomicilio(uf);
        return receptor;
    }
      
    /**
	* Metodo que construye los conceptos con los parametros ingresados
	* @param PagoReferenciadoDTO objeto con los datos ingresados para crear el comprobante fiscal 
	* @return ConceptosDTO regresa una lista de los conceptos del comprobante fiscal
	* @throws 
    */      
    private static ConceptosDTO creaConceptos(PagoReferenciadoDTO pagoReferenciado) throws ErrorEnGeneracionComprobanteException {
    	DecimalFormat mf = new DecimalFormat("#####.##");
    	ConceptosDTO cps = new ConceptosDTO();
	    List<ConceptoDTO> listaConceptos = new ArrayList<ConceptoDTO>();
	      
	    if (pagoReferenciado.getSubTotalIMSS() != null && !pagoReferenciado.getSubTotalIMSS().equals(BigDecimal.ZERO)) {
	    	ConceptoDTO conceptoSubTotalIMSS = new ConceptoDTO();
		    conceptoSubTotalIMSS.setCantidad(new BigDecimal(Constantes.CONCEPTO_CANTIDAD));
//		    conceptoSubTotalIMSS.setUnidad(Constantes.CONCEPTO_UNIDAD);
		    conceptoSubTotalIMSS.setClaveProdServ(Constantes.CLAVE_PROD_SERV);
		    conceptoSubTotalIMSS.setClaveUnidad(Constantes.CLAVE_UNIDAD);
		    conceptoSubTotalIMSS.setDescripcion(Constantes.CONCEPTO_DESCRIPCION_SubTotIMSS);
		    conceptoSubTotalIMSS.setImporte(new BigDecimal(mf.format(pagoReferenciado.getSubTotalIMSS())));
		    conceptoSubTotalIMSS.setValorUnitario(new BigDecimal(mf.format(pagoReferenciado.getSubTotalIMSS())));
		    listaConceptos.add(conceptoSubTotalIMSS);
	    }
	    
	    if (pagoReferenciado.getSubTotRCV() != null && !pagoReferenciado.getSubTotRCV().equals(BigDecimal.ZERO)) {
	    	ConceptoDTO conceptoSubTotalRCV = new ConceptoDTO();
		    conceptoSubTotalRCV.setCantidad(new BigDecimal(Constantes.CONCEPTO_CANTIDAD));
//		    conceptoSubTotalRCV.setUnidad(Constantes.CONCEPTO_UNIDAD);
		    conceptoSubTotalRCV.setClaveProdServ(Constantes.CLAVE_PROD_SERV);
		    conceptoSubTotalRCV.setClaveUnidad(Constantes.CLAVE_UNIDAD);
		    conceptoSubTotalRCV.setDescripcion(Constantes.CONCEPTO_DESCRIPCION_SubTotRCV);
		    conceptoSubTotalRCV.setImporte(new BigDecimal(mf.format(pagoReferenciado.getSubTotRCV())));
		    conceptoSubTotalRCV.setValorUnitario(new BigDecimal(mf.format(pagoReferenciado.getSubTotRCV())));
		    listaConceptos.add(conceptoSubTotalRCV);
	    }
	    
	    if (pagoReferenciado.getActIMSS() != null && !pagoReferenciado.getActIMSS().equals(BigDecimal.ZERO)) {
	    	ConceptoDTO conceptoActIMSS = new ConceptoDTO();
		    conceptoActIMSS.setCantidad(new BigDecimal(Constantes.CONCEPTO_CANTIDAD));
//		    conceptoActIMSS.setUnidad(Constantes.CONCEPTO_UNIDAD);
		    conceptoActIMSS.setClaveProdServ(Constantes.CLAVE_PROD_SERV);
		    conceptoActIMSS.setClaveUnidad(Constantes.CLAVE_UNIDAD);
		    conceptoActIMSS.setDescripcion(Constantes.CONCEPTO_DESCRIPCION_ActIMSS);
		    conceptoActIMSS.setImporte(new BigDecimal(mf.format(pagoReferenciado.getActIMSS())));
		    conceptoActIMSS.setValorUnitario(new BigDecimal(mf.format(pagoReferenciado.getActIMSS())));
		    listaConceptos.add(conceptoActIMSS);
	    }
	    
	    if (pagoReferenciado.getActRCV() != null && !pagoReferenciado.getActRCV().equals(BigDecimal.ZERO)) {
	    	ConceptoDTO conceptoActRCV = new ConceptoDTO();
		    conceptoActRCV.setCantidad(new BigDecimal(Constantes.CONCEPTO_CANTIDAD));
//		    conceptoActRCV.setUnidad(Constantes.CONCEPTO_UNIDAD);
		    conceptoActRCV.setClaveProdServ(Constantes.CLAVE_PROD_SERV);
		    conceptoActRCV.setClaveUnidad(Constantes.CLAVE_UNIDAD);
		    conceptoActRCV.setDescripcion(Constantes.CONCEPTO_DESCRIPCION_ActRCV);
		    conceptoActRCV.setImporte(new BigDecimal(mf.format(pagoReferenciado.getActRCV())));
		    conceptoActRCV.setValorUnitario(new BigDecimal(mf.format(pagoReferenciado.getActRCV())));
		    listaConceptos.add(conceptoActRCV);
	    }
	    
	    if (pagoReferenciado.getRecIMMS() != null && !pagoReferenciado.getRecIMMS().equals(BigDecimal.ZERO)) {
	    	ConceptoDTO conceptoRecIMSS = new ConceptoDTO();
		    conceptoRecIMSS.setCantidad(new BigDecimal(Constantes.CONCEPTO_CANTIDAD));
//		    conceptoRecIMSS.setUnidad(Constantes.CONCEPTO_UNIDAD);
		    conceptoRecIMSS.setClaveProdServ(Constantes.CLAVE_PROD_SERV);
		    conceptoRecIMSS.setClaveUnidad(Constantes.CLAVE_UNIDAD);
		    conceptoRecIMSS.setDescripcion(Constantes.CONCEPTO_DESCRIPCION_RecIMSS);
		    conceptoRecIMSS.setImporte(new BigDecimal(mf.format(pagoReferenciado.getRecIMMS())));
		    conceptoRecIMSS.setValorUnitario(new BigDecimal(mf.format(pagoReferenciado.getRecIMMS())));
		    listaConceptos.add(conceptoRecIMSS);
	    }
	    
	    if (pagoReferenciado.getRecRCV() != null && !pagoReferenciado.getRecRCV().equals(BigDecimal.ZERO)) {
	    	ConceptoDTO conceptoRecRCV = new ConceptoDTO();
		    conceptoRecRCV.setCantidad(new BigDecimal(Constantes.CONCEPTO_CANTIDAD));
//		    conceptoRecRCV.setUnidad(Constantes.CONCEPTO_UNIDAD);
		    conceptoRecRCV.setClaveProdServ(Constantes.CLAVE_PROD_SERV);
		    conceptoRecRCV.setClaveUnidad(Constantes.CLAVE_UNIDAD);
		    conceptoRecRCV.setDescripcion(Constantes.CONCEPTO_DESCRIPCION_RecRCV);
		    conceptoRecRCV.setImporte(new BigDecimal(mf.format(pagoReferenciado.getRecRCV())));         
		    conceptoRecRCV.setValorUnitario(new BigDecimal(mf.format(pagoReferenciado.getRecRCV())));
		    listaConceptos.add(conceptoRecRCV);
	    }
	    
	    cps.setConceptos(listaConceptos.toArray(new ConceptoDTO[listaConceptos.size()]));
	      
        return cps;
     }
   
    /**
	* Metodo que construye los impuestos con los parametros ingresados
	* @param PagoReferenciadoDTO objeto con los datos ingresados para crear el comprobante fiscal
	* @return ImpuestosDTO regresa una lista con los impuestos que se obtuvieron del objeto PagoReferenciado
	* @throws 
    */  
     private static ImpuestosDTO creaImpuestos(PagoReferenciadoDTO pagoReferenciado) throws ErrorEnGeneracionComprobanteException {
        //DecimalFormat mf = new DecimalFormat("###.##");
        
        ImpuestosDTO impuestos = new ImpuestosDTO();        
        return impuestos;
    }
}
