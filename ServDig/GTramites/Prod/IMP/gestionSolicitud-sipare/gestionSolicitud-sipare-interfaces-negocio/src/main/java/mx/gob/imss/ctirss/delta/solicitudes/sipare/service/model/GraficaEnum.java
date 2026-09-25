package mx.gob.imss.ctirss.delta.solicitudes.sipare.service.model;

public enum GraficaEnum {

	TOTAL_PATRONES_SIPARE("patronesSipare", 0, null, null, null, null, null,
			null, 0, true),

	TOTAL_PATRONES_SIPARE_MES(
			"patronesSipareMes",
			2,
			"Total de Registro de Patrones en SIPARE",
			"Gráfica que presenta el total mensual de los patrones registrados en SIPARE a la fecha.",
			"/gestionSolicitud-visor-graficas-web/grafica/patronesSipareMes",
			"fecha", new String[] { "value" },
			new String[] { "ALTA DE PATRONES SIPARE" }, 2, true),

	TOTAL_PATRONES_SIPARE_DIA(
			"patronesSipareDia",
			2,
			"Total de Registro de Patrones en SIPARE",
			"Gráfica que presenta el total diario en el mes actual de los patrones registrados en SIPARE.",
			"/gestionSolicitud-visor-graficas-web/grafica/patronesSipareDia",
			"fecha", new String[] { "value" },
			new String[] { "ALTA DE PATRONES SIPARE" }, 1, true);

	// Identificador para cada gráfica
	private String identificador;
	// 1=barras, 2=líneas, 3=dona
	private int tipo;
	private String nombre;
	private String descripcion;
	private String url;
	private String xKey;
	private String[] yKeys;
	private String[] labels;
	// 1=Por dia, 2=Por mes, 3=Por anio
	private int tipoFormatoEjeX;
	// TRUE=Mostrar hover en gráfica, FALSE=Mostrar detalle en tabla
	private boolean mostrarDetalleDia;

	private GraficaEnum(String identificador, int tipo, String nombre,
			String descripcion, String url, String xKey, String[] yKeys,
			String[] labels, int tipoFormatoEjeX, boolean mostrarDetalleDia) {
		this.identificador = identificador;
		this.tipo = tipo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.url = url;
		this.xKey = xKey;
		this.yKeys = yKeys;
		this.labels = labels;
		this.tipoFormatoEjeX = tipoFormatoEjeX;
		this.mostrarDetalleDia = mostrarDetalleDia;
	}

	public String getIdentificador() {
		return identificador;
	}

	public int getTipo() {
		return tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getUrl() {
		return url;
	}

	public String getxKey() {
		return xKey;
	}

	public String[] getyKeys() {
		return yKeys;
	}

	public String[] getLabels() {
		return labels;
	}

	public int getTipoFormatoEjeX() {
		return tipoFormatoEjeX;
	}

	public boolean isMostrarDetalleDia() {
		return mostrarDetalleDia;
	}
}
