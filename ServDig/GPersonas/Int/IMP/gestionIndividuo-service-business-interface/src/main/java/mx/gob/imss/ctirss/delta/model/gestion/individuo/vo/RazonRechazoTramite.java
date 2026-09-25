package mx.gob.imss.ctirss.delta.model.gestion.individuo.vo;

public class RazonRechazoTramite { // implements CatalogInMemory {
    public static final String DOCUMENTOS_INCOMPLETOS = "Documentos probatorios incompletos";
    public static final String DOCUMENTOS_APOCRIFOS = "Documentos ap\u00F3crifos";
    public static final String IMPROCEDENCIA = "Improcedencia";
    public static final String SOLICITUD_CANCELADA = "Solicitud Cancelada";

    public static final Long DOCUMENTOS_INCOMPLETOS_CVE = 2L;
    public static final Long DOCUMENTOS_APOCRIFOS_CVE = 3L;
    public static final Long IMPROCEDENCIA_CVE = 4L;
    public static final Long SOLICITUD_CANCELADA_CVE = 5L;

    // private static final Logger LOG;
    //
    // static {
    // LOG = LoggerFactory.getLogger(RazonRechazoTramite.class);
    // }

    /*
     * Mapa para el manejo de indices de las constantes definidas en esta clase.
     */
    // public static final Map<Integer, String> INDEX = new HashMap<Integer,
    // String>();

    // static {
    // INDEX.put(0, DEFAULT_OPTION);
    // INDEX.put(2, DOCUMENTOS_INCOMPLETOS);
    // INDEX.put(3, DOCUMENTOS_APOCRIFOS);
    // INDEX.put(4, IMPROCEDENCIA);
    // INDEX.put(5, SOLICITUD_CANCELADA);
    // }

    // public List<SelectBean> getSelectOptions() {
    // final Set<Entry<Integer, String>> entries = INDEX.entrySet();
    // final List<SelectBean> combo = new ArrayList<SelectBean>();
    //
    // for (Entry<Integer, String> entry : entries) {
    // LOG.trace(entry.toString());
    // combo.add(new SelectBean(entry.getKey(), entry.getValue()));
    // }
    //
    // LOG.trace("combo: " + combo);
    // return combo;
    // }

}
