package mx.gob.imss.distss.derechohabientes.adimss.service.business;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.distss.derechohabientes.adimss.service.entity.ObtenerFotografiaEntityLocal;
import mx.gob.imss.distss.derechohabientes.adimss.service.entity.ObtenerFotografiaEntityVanLocal;
import mx.gob.imss.distss.derechohabientes.adimss.service.interfaces.ObtenerFotografiaServiceBusinessRemote;
import mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss.AdtCredencial;
import mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent.Controladimss;
import mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent.Documentuminterface;
import mx.gob.imss.distss.derechohabientes.adimss.service.utility.Fecha;
import mx.gob.imss.distss.derechohabientes.adimss.service.utility.ImgWS;

import org.apache.commons.codec.binary.Base64;

@Stateless(name = "obtenerFotografiaServiceBusiness", mappedName = "obtenerFotografiaServiceBusiness")
public class ObtenerFotografiaServiceBusiness implements
		ObtenerFotografiaServiceBusinessRemote {

	@EJB
	ObtenerFotografiaEntityLocal obtenerFotografiaEntity;
	
	@EJB
	ObtenerFotografiaEntityVanLocal obtenerFotografiaVanEntity;

	@Override
	public Object obtenerFotografiaAsegurado(String nss,Integer calidad) throws Exception {
		byte[] fotografia = null;
		boolean rsp = false;
		ImgWS imgWS = new ImgWS();

		if (nss != null && !nss.equals("")) {
			List<AdtCredencial> lstImg = obtenerFotografiaEntity.getImagenId(nss, 1);

			if (!lstImg.isEmpty() && lstImg.get(0).getNumOidFoto() != null) {
				// se consulta en vagent
				List<Documentuminterface> lstVAN = obtenerFotografiaVanEntity.getObjetID(nss, 1);
				// numero de credenciales expedidas
				int numCred = lstImg.size() + lstVAN.size();
				// si solo existe un registro de expedicion
				if (numCred == 1) {
					return imgWS.getImg(lstImg.get(0).getNumOidFoto());
				} else {
					// array para almacenar las fechas de expedicion
					Date[] fchCompara = new Date[numCred];
					// se itera sobre las listas obtenidas para extraer las
					// fechas
					int r = 0;
					for (; lstImg.size() > r; r++) {
						fchCompara[r] = lstImg.get(r).getStpExpedicion();
					}
					int v = 0;
					for (int u = r; numCred > u; u++) {// getControlAdimssVAN().getCreatedon()
						fchCompara[u] = lstVAN.get(v).getControladimss().getEnrolstarttime();
						v++;
					}
					// se compara entre los registros obtenidos para presentar
					// la expedicion mas resiente
					Date fchMax = Fecha.maxFecha(fchCompara);
					// se iteran los resgistros del adimss para verificar si
					// alguno se trata de la fecha maxima
					for (int a = 0; lstImg.size() > a; a++) {
						if (fchMax.equals(lstImg.get(a).getStpExpedicion())) {
							return imgWS.getImg(lstImg.get(a).getNumOidFoto());
						}
					}
					// si ninguna de las fechas de los registros de la base
					// normativa corresponde se compara a los de vangent
					if (!rsp) {
						for (int a = 0; lstVAN.size() > a; a++) {
							if (fchMax.equals(lstVAN.get(a).getControladimss().getEnrolstarttime())) {
								return imgWS.getImg(lstVAN.get(a).getDocumentumreference());
							}
						}
					}
				}
			} else {
				// se buscan los datos en vangent
				List<Documentuminterface> lstVAN = obtenerFotografiaVanEntity.getObjetID(nss, 1);
				if (!lstVAN.isEmpty()
						&& lstVAN.get(0).getDocumentumreference() != null) {
					return imgWS.getImg(lstVAN.get(0).getDocumentumreference());
				} else {
					// se obtiene el path
					List<Controladimss> lstPath = obtenerFotografiaVanEntity.getRuta(nss, 1);
					if (!lstPath.isEmpty()) {
						String ruta = "\\\\adimss\\"
								+ lstPath.get(0).getEnrollmentstation().getNaspath().getDescnaspaths()
								+ "\\" + (imgWS.getEnrolStationPath(Long.toString(lstPath.get(0).getEnrollmentstation().getIdenrollmentstation()).length()))
								+ lstPath.get(0).getEnrollmentstation().getIdenrollmentstation()
								+ "\\" + (lstPath.get(0).getNumNssAsegurado().toString().length() == 10 ? "10" : "")
								+ lstPath.get(0).getNumNssAsegurado() + "\\photo\\";
						return imgWS.getImgByPath(ruta);
					}
				}

			}
		}
		return fotografia;
	}
	
	@Override
	public Object obtenerFotografiaDerechohabiente(String nss,Integer calidad,String nombre,String apPaterno,String apMaterno) throws Exception{
		boolean rsp = false;
		ImgWS img = new ImgWS();
				//se obtiene la imagen de la base normativa
				List<AdtCredencial> lstImg = obtenerFotografiaEntity.getImagenId(nss, calidad, nombre, apPaterno, apMaterno);
				if(!lstImg.isEmpty() && lstImg.get(0).getNumOidFoto()!= null){
					List<Documentuminterface> lstVAN = obtenerFotografiaVanEntity.getObjetID(nss, calidad, nombre, apPaterno, apMaterno);
					//numero de credenciales expedidas
					int numCred = lstImg.size() + lstVAN.size();
					if(numCred == 1){
						return img.getImg(lstImg.get(0).getNumOidFoto());
					}else{
						//array para almacenar las fechas de expedicion
						Date [] fchCompara = new Date[numCred]; 
						//se itera sobre las listas obtenidas para extraer las fechas
						int r = 0 ;
						for(; lstImg.size() > r ; r++){
							fchCompara[r] = lstImg.get(r).getStpExpedicion();
						}
						int v = 0;
						for(int u = r ; numCred > u ; u++){//getControlAdimssVAN().getCreatedon()
							fchCompara[u] = lstVAN.get(v).getControladimss().getEnrolstarttime();
							v++;
						}
						//se compara entre los registros obtenidos para presentar la expedicion mas resiente
						Date fchMax = Fecha.maxFecha(fchCompara);
						//se iteran los resgistros del adimss para verificar si alguno se trata de la fecha maxima
						for(int a = 0 ; lstImg.size() > a ; a++){
							if(fchMax.equals(lstImg.get(a).getStpExpedicion())){
								return img.getImg(lstImg.get(a).getNumOidFoto());
							}
						}
						//si ninguna de las fechas de los registros de la base normativa corresponde se compara a los de vangent
						if(!rsp){
							for(int a = 0 ; lstVAN.size() > a ; a++){
								if(fchMax.equals(lstVAN.get(a).getControladimss().getEnrolstarttime())){
									return img.getImg(lstVAN.get(a).getDocumentumreference());			
								}
							}
						}
					}
				}else{
					List<Documentuminterface> lstVAN = obtenerFotografiaVanEntity.getObjetID(nss, calidad, nombre, apPaterno, apMaterno);
					if(!lstVAN.isEmpty() && lstVAN.get(0).getDocumentumreference() != null){
						//se obtiene la foto
						return img.getImg(lstVAN.get(0).getDocumentumreference());
					} else {
						//se obtiene el path
						List<Controladimss> lstPath = obtenerFotografiaVanEntity.getRuta(nss, calidad, nombre, apPaterno, apMaterno);
						if(!lstPath.isEmpty()){
							String ruta = "\\\\adimss\\"+
										lstPath.get(0).getEnrollmentstation().getNaspath().getDescnaspaths()+"\\"+
										(img.getEnrolStationPath(Long.toString(lstPath.get(0).getEnrollmentstation().getIdenrollmentstation()).length()))+
										lstPath.get(0).getEnrollmentstation().getIdenrollmentstation()+"\\"+
										(lstPath.get(0).getNumNssAsegurado().toString().length()==10?"10":"")+
										lstPath.get(0).getNumNssAsegurado()+"\\photo\\";
							return img.getImgByPath(ruta);
						}
					}
				}	  
		return null;
	}

}
