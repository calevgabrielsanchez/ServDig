package mx.gob.imss.csdiss.sdroc.service.interfaces;

public interface BloqueoObraService  {

	
	
	int validaBloqueoObraService(String numeroRegistroObra,String claveSesion);
	public void liberaRegistroObra(String numeroRegistroObra);
	public void reiniciarRegistroObra(String numeroRegistroObra,String claveSesion);
	public int sensaTiempoObra(String numeroRegistroObra,String claveSesion);
	public void liberaObras(String claveSesion);
}
