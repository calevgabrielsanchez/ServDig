package mx.gob.imss.csdiss.sdroc.ejb;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.imss.csdiss.sdroc.entity.RotInformacionObra;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionObraDao;
import mx.gob.imss.csdiss.sdroc.service.interfaces.BloqueoObraService;
import mx.gob.imss.csdiss.sdroc.service.interfaces.InformacionObraService;

@Interceptors(SpringBeanAutowiringInterceptor.class)
@Stateless(name = "bloqueObraService", mappedName = "bloqueObraService")
@Remote(BloqueoObraService.class)
public class BloqueoObraServiceImpl implements BloqueoObraService,Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	InformacionObraDao informacionObraDao;
	
	
	public int validaBloqueoObraService(String numeroRegistroObra,String claveSesion) {
		// TODO Auto-generated method stub
		RotInformacionObra valor=informacionObraDao.consultaBloqueoRegistroObra(numeroRegistroObra);		
		if(valor==null){//NO existe obra
			return -1;
		}
		//Esta libre
		if(valor.getCveIdUsuarioBloqueo()==null){			
			informacionObraDao.updateBloqueo(claveSesion,String.valueOf(valor.getCveInformacionObra()));
			//actualizamos el bloqueo
			return 1;
		}else{
			//Se verifica que el usuario sea el mismo
			if(valor.getCveIdUsuarioBloqueo().equals(claveSesion)){
				//Se realiza el calculo de media hr sobre el mismo usuario				
				int minutos=diferenciaEnMinutos(new Date(), valor.getFecBloqueoObra());
				if(minutos<25){
					return 1;
				}else if(minutos>=25 && minutos<=30){
					return 2;//Peticion del usuario
				}else{
					informacionObraDao.updateBloqueo(null,String.valueOf(valor.getCveInformacionObra()));
					return 0;
				}			
			}else{
				//La esta usando otro usuario se verifica el tiempo del otro usaurio
				
				int minutos=diferenciaEnMinutos(new Date(), valor.getFecBloqueoObra());
				if(minutos>=30){
					informacionObraDao.updateBloqueo(claveSesion,String.valueOf(valor.getCveInformacionObra()));
					return 1;
				}else{
					return 0;
				}	
			}
			
		}
	}
	
	
	private  int diferenciaEnMinutos(Date fechaMayor, Date fechaMenor) {
		long diferenciaEn_ms = fechaMayor.getTime()-fechaMenor.getTime();
		long minutos = diferenciaEn_ms / (1000 * 60);
		return (int) minutos;
		}
	

	
	public void liberaRegistroObra(String numeroRegistroObra){
		RotInformacionObra valor=informacionObraDao.consultaBloqueoRegistroObra(numeroRegistroObra);		
		informacionObraDao.updateBloqueo(null, String.valueOf(valor.getCveInformacionObra()));
	}

	public void reiniciarRegistroObra(String numeroRegistroObra,String claveSesion){
		RotInformacionObra valor=informacionObraDao.consultaBloqueoRegistroObra(numeroRegistroObra);		
		informacionObraDao.updateBloqueo(claveSesion, String.valueOf(valor.getCveInformacionObra()));
	}
	
	public int sensaTiempoObra(String numeroRegistroObra,String claveSesion){
		RotInformacionObra obra=informacionObraDao.consultaBloqueoRegistroObra(numeroRegistroObra);
		int minutos=0;
		if(obra.getCveIdUsuarioBloqueo().equals(claveSesion)){			
			minutos=diferenciaEnMinutos(new Date(), obra.getFecBloqueoObra());
			if(minutos<25){
				return 1;
			}else if(minutos>=25 && minutos<=30){
				return 2;
			}else{
				return 0;
			}
		}		
		return minutos;
	}
	
	
	public void liberaObras(String claveSesion){
		informacionObraDao.liberaObras(claveSesion);		
	}

}
