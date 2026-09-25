package mx.gob.imss.cit.ws.externo.boveda.documental.utils;

import java.util.Properties;

public enum TipoDocumentalBovedaEnum {
	
	TIPO_DOCUMENTAL_TSPI("boveda.tspi.tipodocumental",null);

	private String id;
	private String descripcion;
	private static final String AMBIENTE_ACTIVO = "enviroment.active";
	private static final String PATH_ENVIROMENT = "/enviromentConfig.properties";
	private static Properties enviroment;
	private static Properties properties;
	
	private TipoDocumentalBovedaEnum(String id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		if(descripcion == null) {
			init();
		}
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	private void init() {
		if (enviroment == null) {
			enviroment = new Properties();
			try {
				enviroment.load(RutaBovedaEnum.class.getResourceAsStream(PATH_ENVIROMENT));
				String ambienteActivo = (String)enviroment.getProperty(AMBIENTE_ACTIVO);
				properties = new Properties();
				properties.load(RutaBovedaEnum.class.getResourceAsStream("/config"+ambienteActivo+".properties"));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.descripcion = (String) properties.get(this.id);
	}
}
