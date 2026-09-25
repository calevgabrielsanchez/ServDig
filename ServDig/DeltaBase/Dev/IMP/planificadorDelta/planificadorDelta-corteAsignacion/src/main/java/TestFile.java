import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestFile {
	protected static final Log log = LogFactory.getLog(TestFile.class);

	public static void main(String[] args) {
		try {
			// TODO Auto-generated method stub
			String nombreArchivo = "/mtfs/Test/changeset.out";
			File arhivoDescargado = new File(nombreArchivo);

			List<String> lineasArchivos = FileUtils.readLines(arhivoDescargado);
			Long numChangeset = 0L;
			for (String lineaArchivo : lineasArchivos) {
				if (StringUtils.isNotBlank(lineaArchivo)) {
					int longCadena = lineaArchivo.length();
					if (longCadena > 11) {
						String inicioLinea = lineaArchivo.substring(0, 10);
						if (inicioLinea.equals("Changeset:")) {
							String strChangeset = StringUtils.right(lineaArchivo, lineaArchivo.length() - 11);
							numChangeset = Long.valueOf(strChangeset);
						}
					}
					if (StringUtils.contains(lineaArchivo, "010304_")) {
						int index = lineaArchivo.lastIndexOf("010304_");
						
						
						String strRecurso = lineaArchivo.substring(index);
						int endIndex = strRecurso.lastIndexOf("IMP");
						//log.debug(numChangeset + "\t" + strRecurso);
						log.debug(numChangeset + "\t" + strRecurso.substring(0, endIndex + 11));
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
