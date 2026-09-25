package mx.gob.imss.ctirss.delta.cobranza.web.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mx.gob.imss.ctirss.delta.cobranza.enums.TotalesCobranzaEnum;
import mx.gob.imss.ctirss.delta.cobranza.modelo.Patron;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.CobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.PatronCobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.cobranza.web.dto.GraficaDto;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/consulta/edoAdeudo/")
public class EstadoAdeudoController extends AbstractController {
	
	@Autowired
	private CobranzaServiceRemote cobranzaServiceRemote;
	@Autowired
	private PatronCobranzaServiceRemote patronCobranzaServiceRemote;
	
	@RequestMapping(value = "/getTotales", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getTotales(@RequestBody Patron patronP) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<GraficaDto> grafImss = null;
		List<GraficaDto> grafRCV = null;
		String totalesImss = "No se encontraron datos";
		String totalesRcv = "No se encontraron datos";
		
		Map<String, Object> imss = null;
		Map<String, Object> rcv = null;
		String nrp= patronP.getRegPatronal();
		if(nrp != null && nrp.length() > 9){
			Patron patron = new Patron();
			
			String rp = nrp.substring(0, 8);
			String modalidad = nrp.substring(8,10);
			
			patron.setRegPatronal(rp);
			patron.setCveModalidad(modalidad);
			
			try {
				patron = patronCobranzaServiceRemote.getPatron(patron.getRegPatronal(), patron.getCveModalidad());
			}catch(Exception e) {
				e.printStackTrace();
				patron = null;
			}
			
			if(patron != null) {
				try {
					imss = cobranzaServiceRemote.getTotalesCreditosImss(patron);
					this.log.debug("Totales IMSS" + imss);
				} catch (Exception e){
					e.printStackTrace();
				}
				
				try {
					rcv = cobranzaServiceRemote.getTotalesCreditosRCVImss(patron);
					this.log.debug("Totales RCV" + rcv);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				if(imss != null) {
					try{
						grafImss = this.getDatosGraficaImss(imss, false);
						totalesImss = "$" + this.getNumeroFormateado((Double)imss.get(TotalesCobranzaEnum.SUMA_TOTALES.getKey()));
					}catch(Exception e){
						this.log.error(e.getMessage(), e);
						e.printStackTrace();
					}
					
					
				}
				
				if(rcv != null) {
					try{
						grafRCV = this.getDatosGraficaImss(rcv, true);
						totalesRcv = "$" + this.getNumeroFormateado((Double)rcv.get(TotalesCobranzaEnum.SUMA_TOTALES.getKey()));
					}catch(Exception e){
						this.log.error(e.getMessage(), e);
						e.printStackTrace();
					}
					
				}
			}
			map.put("totalImss",totalesImss);
			map.put("totalRcv", totalesRcv);
			map.put("totalesImss", grafImss);
			map.put("totalesRCV", grafRCV);
		}
		
		return map;
	}
	
	@RequestMapping(value = "/getTotalesImss")
	public @ResponseBody Map<String, Object> getTotalesIMSS(@RequestParam String nrp) {
		Map<String, Object> resultado = null;
		
		if(nrp != null && nrp.length() > 9){
			Patron patron = new Patron();
			
			String rp = nrp.substring(0, 8);
			String modalidad = nrp.substring(8,10);
			
			patron.setRegPatronal(rp);
			patron.setCveModalidad(modalidad);
			
			resultado = cobranzaServiceRemote.getTotalesCreditosImss(patron);
		}
		return resultado;
	}
	
	@RequestMapping(value = "/getTotalesRCV")
	public @ResponseBody Map<String, Object> getTotalesRCV(@RequestParam String nrp) {
		Map<String, Object> resultado = null;
		
		if(nrp != null && nrp.length() > 9){
			Patron patron = new Patron();
			
			String rp = nrp.substring(0, 8);
			String modalidad = nrp.substring(8,10);
			
			patron.setRegPatronal(rp);
			patron.setCveModalidad(modalidad);
			
			resultado = cobranzaServiceRemote.getTotalesCreditosRCVImss(patron);
		}
		
		return resultado;
	}
	
	private String getNumeroFormateado(Double cantidad) {
		NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es","MX"));
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(true);
		DecimalFormat decim = (DecimalFormat) nf;
		
		return decim.format(cantidad);
	}
	
	private List<GraficaDto> getDatosGraficaImss(Map<String, Object> totales, Boolean rcv) {
		List<GraficaDto> lista = null;
		lista = new ArrayList<GraficaDto>();
		
		NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es","MX"));
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(false);
		DecimalFormat decim = (DecimalFormat) nf;
		
		
		
		Double cantidad = 0.00;
		
		if(rcv) {
			GraficaDto dato = new GraficaDto();
			
			cantidad = (Double) totales.get(TotalesCobranzaEnum.TOTAL_SAL_RET.getKey());
			dato.setLabel("Retiro");
			dato.setValue(Double.parseDouble(decim.format(cantidad)));
			
			lista.add(dato);
			
			cantidad = (Double) totales.get(TotalesCobranzaEnum.TOTAL_CYV.getKey());
			dato = new GraficaDto();
			dato.setLabel("Cesantia y vejez");
			dato.setValue(Double.parseDouble(decim.format(cantidad)));
			
			lista.add(dato);
			
			dato = new GraficaDto();
			cantidad = (Double) totales.get(TotalesCobranzaEnum.TOTAL_ACTUA.getKey());
			dato.setLabel("Actualización");
			dato.setValue(Double.parseDouble(decim.format(cantidad)));
			
			lista.add(dato);
			
			dato = new GraficaDto();
			cantidad = (Double) totales.get(TotalesCobranzaEnum.TOTAL_RECAR.getKey());
			dato.setLabel("Recargos");
			dato.setValue(Double.parseDouble(decim.format(cantidad)));
			
			lista.add(dato);
			
		} else {
			
			GraficaDto dato = new GraficaDto();
			
			cantidad = (Double) totales.get(TotalesCobranzaEnum.TOTAL_ENF_MAT.getKey());
			dato.setLabel("Enfermedades y maternidad");
			dato.setValue(Double.parseDouble(decim.format(cantidad)));
			
			lista.add(dato);
			
			dato = new GraficaDto();
			cantidad = (Double) totales.get(TotalesCobranzaEnum.TOTAL_SAL_GUAR.getKey());
			dato.setLabel("Guarderias y prestaciones sociales");
			dato.setValue(Double.parseDouble(decim.format(cantidad)));
			
			lista.add(dato);
			
			dato = new GraficaDto();
			cantidad = (Double) totales.get(TotalesCobranzaEnum.TOTAL_SAL_IV.getKey());
			dato.setLabel("Invalidez y vida");
			dato.setValue(Double.parseDouble(decim.format(cantidad)));
			
			lista.add(dato);
			
			dato = new GraficaDto();
			cantidad = (Double) totales.get(TotalesCobranzaEnum.TOTAL_SAL_RT.getKey());
			dato.setLabel("Riesgos de trabajo");
			dato.setValue(Double.parseDouble(decim.format(cantidad)));
			
			lista.add(dato);
			
			dato = new GraficaDto();
			cantidad = (Double) totales.get(TotalesCobranzaEnum.TOTAL_ACT.getKey());
			dato.setLabel("Actualización");
			dato.setValue(Double.parseDouble(decim.format(cantidad)));
			
			lista.add(dato);
			
			dato = new GraficaDto();
			cantidad = (Double) totales.get(TotalesCobranzaEnum.TOTAL_INT.getKey());
			dato.setLabel("Recargos");
			dato.setValue(Double.parseDouble(decim.format(cantidad)));
			
			lista.add(dato);
		}
		return lista;
	}
}
