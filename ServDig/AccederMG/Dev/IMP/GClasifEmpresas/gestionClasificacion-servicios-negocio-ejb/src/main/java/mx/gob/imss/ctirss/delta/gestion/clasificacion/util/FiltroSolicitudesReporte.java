package mx.gob.imss.ctirss.delta.gestion.clasificacion.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import mx.gob.imss.ctirss.cliente.clasificacion.dto.InfoConsultaMacII;


public class FiltroSolicitudesReporte {
	
	

	private ArrayList<InfoConsultaMacII> listaOriginal;
	private ArrayList<InfoConsultaMacII> listaTramiteRP = new ArrayList<InfoConsultaMacII>();
	private ArrayList<InfoConsultaMacII> listaTramiteRPTipoTramite = new ArrayList<InfoConsultaMacII>();

	public FiltroSolicitudesReporte(ArrayList<InfoConsultaMacII> listaOriginal) {

		this.listaOriginal = listaOriginal;
	}

	public ArrayList<InfoConsultaMacII> filtrarSolcitudes() {
		
		ArrayList<InfoConsultaMacII> listaFiltrada = new ArrayList<InfoConsultaMacII>();
		
		for(InfoConsultaMacII infoConsulta : this.listaOriginal) {
			if(buscarRpRegistrado(listaFiltrada, infoConsulta.getRegistroPatronal())) {
				buscarPorRP(infoConsulta.getRegistroPatronal());
				for(InfoConsultaMacII infoConsultaRP : this.listaTramiteRP) {
					if(buscarTramiteRegistrado(listaFiltrada,infoConsultaRP.getCveIdTipoTramite(),
							infoConsultaRP.getRegistroPatronal()))
					listaFiltrada.add(filtrarPorTramite(infoConsultaRP.getCveIdTipoTramite()));
				}
			}
			
		}
		
		return listaFiltrada;
	}

	

	private ArrayList<InfoConsultaMacII> buscarPorRP(String rp) {
		this.listaTramiteRP = new ArrayList<InfoConsultaMacII>();
		for (InfoConsultaMacII info : this.listaOriginal) {
			if(info.getRegistroPatronal().equals(rp)) {
				this.listaTramiteRP.add(info);
			}
		}
		
		
		return this.listaTramiteRP;
	}
	
	private InfoConsultaMacII filtrarPorTramite(Integer cveIdTipoTramite) {
		this.listaTramiteRPTipoTramite = new ArrayList<InfoConsultaMacII>(); 
		for (InfoConsultaMacII info : this.listaTramiteRP) {
			if(info.getCveIdTipoTramite().equals(cveIdTipoTramite) ) {
				this.listaTramiteRPTipoTramite.add(info);
			}
		}
		
		Collections.sort(this.listaTramiteRPTipoTramite, new Comparator<InfoConsultaMacII>() {
			public int compare(InfoConsultaMacII i1, InfoConsultaMacII i2) {
				
				
				Date fecha1 = convertirFecha(i1.getFecRegistro());
				Date fecha2 = convertirFecha(i2.getFecRegistro());
				
				if(i1.getFecRevision() != null) {
					fecha1 = convertirFecha(i1.getFecRevision());
				}
				
				if(i2.getFecRevision() != null) {
					fecha2 = convertirFecha(i2.getFecRevision());
				}
				
				
				if(fecha1!=null && fecha2!=null) {
					return fecha1.compareTo(fecha2);
				}
				else if (fecha1!=null && fecha2==null) {
					return 1;
					
				}
				else{
					return -1;
				}
					
				}
				
			
		});
		
		InfoConsultaMacII infoConsultaMacII = this.listaTramiteRPTipoTramite.get(this.listaTramiteRPTipoTramite.size() -1);
		
		return infoConsultaMacII;
	}

	private boolean buscarRpRegistrado( ArrayList<InfoConsultaMacII> listaInfo, String rp) {

		for (InfoConsultaMacII info : listaInfo) {
			if(info.getRegistroPatronal().equals(rp)) {
//				sendText("RP encontrado repetido:: "+rp);
				return false;
			}
		}
		
		return true;

	}
	
	private boolean buscarTramiteRegistrado(ArrayList<InfoConsultaMacII> listaInfo, Integer cveIdTipoTramite, String rp) {
		
		for (InfoConsultaMacII info : listaInfo) {
			if(info.getCveIdTipoTramite().equals(cveIdTipoTramite) && info.getRegistroPatronal().equals(rp)) {
				
				return false;
			}
		}
		
		return true;

	}
	
	private static Date convertirFecha(String fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			return formato.parse(fecha);
		}catch(ParseException pe) {
			System.out.print(":::::::::::::::::::: NO SE CONVIRTIO LA FECHA CON EXITO ::::::::::::::::::::::::" +fecha);
			return null;
		}
		
	}
	
}

