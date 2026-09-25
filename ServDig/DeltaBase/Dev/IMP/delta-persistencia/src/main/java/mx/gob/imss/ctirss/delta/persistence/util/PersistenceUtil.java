package mx.gob.imss.ctirss.delta.persistence.util;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.MappingException;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceUtil {
    
    private static final String PACK_PERS_CLASSES;
    private static final String PREFIX_CLASSPATH;
    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(PersistenceUtil.class);
        PREFIX_CLASSPATH = "src/main/java/";
        PACK_PERS_CLASSES = "mx.gob.imss.ctirss.delta.persistence";
    }

    private PersistenceUtil() {
        // TO PMD
    }
    
    /**
     * Metodo main que manda a llamar a los metodos:
     * 
     * --> public void generarScriptSQL() throws MappingException, ClassNotFoundException
     * --> public void generarPersistenceXML()
     * 
     * dependiendo del parametro recibido
     * 
     * @param args
     */
    public static void main(final String args[]) {

        try {
            if (args != null && args.length > 0) {
                if (args[0].equals("p")) {
                    LOG.trace("SE GENERARA EL ARCHIVO persistence.xml");
                    generarPersistenceXML();
                } else if (args[0].equals("s")) {
                    LOG.trace("SE GENERARA EL ARCHIVO db/oracle_ddl.sql");
                    generarScriptSQL();
                }
            } else {
                LOG.info("Uso: debe especificar p o s en la linea de comandos, como arg al metodo main.");
            }
        } catch (Exception e) {
            LOG.error("In main", e);
        }

    }
    
    /**
     * Metodo para generar el SCRIPT SQL
     * @throws MappingException
     * @throws ClassNotFoundException
     */
    public static void generarScriptSQL() throws MappingException, ClassNotFoundException {
        final Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        final Configuration conf = new Configuration();
        conf.setProperties(properties);
        addAnnotatedClasses(conf);
        // addFiles(conf);
        exportSchema(conf);
    }

    private static void exportSchema(final Configuration conf) {
        final File outDir = new File("db");
        if(!outDir.exists()) {
            LOG.info("El directorio de salida del scrip se halla en: " + outDir.getAbsolutePath());
            outDir.mkdir();
        }
        final SchemaExport schemaExport = new SchemaExport(conf);
        schemaExport.setOutputFile("db/oracle_ddl.sql");
        schemaExport.setFormat(true);
        schemaExport.setDelimiter(";");
        schemaExport.create(true, false);
        // new SchemaExport(conf).main(new String[] { "--text", "--format",
        // "--properties=src/test/resources/hibernate.properties",
        // "--output=ddl_mysql.sql"
        // });
    }

    private static void addAnnotatedClasses(final Configuration conf) throws ClassNotFoundException {
        final File packClassFile = new File(PREFIX_CLASSPATH + PACK_PERS_CLASSES.replace('.', '/'));
        LOG.trace("Exists? " + packClassFile.exists());
        // System.out.println("Exists? " + packClassFile.exists());
        final File[] files = packClassFile.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                final String persClassName = PACK_PERS_CLASSES + "." + StringUtils.removeEnd(file.getName(), ".java");
                LOG.trace("persistent class: " + persClassName);
                conf.addAnnotatedClass(Class.forName(persClassName));
            }
        }
    }
    
    /**
     * Metodo para generar el archivo persistence.xml
     */
    public static void generarPersistenceXML(){
        File dirJpa = new File("src/main/java/mx/gob/imss/ctirss/delta/persistence");
        System.out.println(dirJpa.exists());
        for (File classFile : dirJpa.listFiles()) {
            StringBuffer classBuffer = new StringBuffer();
            classBuffer.append("<class>");
            classBuffer.append("mx.gob.imss.ctirss.delta.persistence.");
            classBuffer.append(classFile.getName().replace(".java", ""));
            classBuffer.append("</class>");
            System.out.println(classBuffer.toString());
        }
    }

    public static void generarClassesCollection() {
        File dirJpa = new File("src/main/java/mx/gob/imss/ctirss/delta/persistence");
        System.out.println("Existe el directorio? " + dirJpa.exists());
        for (File classFile : dirJpa.listFiles()) {
            StringBuffer classBuffer = new StringBuffer();
            classBuffer.append("mx.gob.imss.ctirss.delta.persistence.");
            classBuffer.append(classFile.getName().replace(".java", ".class, "));
            System.out.println(classBuffer.toString());
        }
    }

}
