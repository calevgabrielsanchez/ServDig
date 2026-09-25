package mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.utils;

import mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.vo.DocumentosByte;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultalRiesgoTrabajoServiceRemote;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class WSUtils {

    public static DocumentosByte documentosByte(String tipo, byte[] documentosBT) {

        DocumentosByte documentos = null;
        if (documentosBT != null) {
            documentos = new DocumentosByte();
            documentos.setTipo(tipo);
            documentos.setDocumento(documentosBT);
        }
        return documentos;
    }

    public static boolean verificaNrp(String nrp) {
        if (nrp == null) {
            return false;
        }
        return Pattern.compile("\\w{10}").matcher(nrp).matches();
    }

    public static boolean verificaRfc(String rfc) {
        if (rfc == null) {
            return false;
        }
        if (Pattern.compile("\\w{12}").matcher(rfc).matches()) {
            return true;
        }
        if (Pattern.compile("\\w{13}").matcher(rfc).matches()) {
            return true;
        }
        return false;
    }

    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        return output;
    }

    public static ConsultalRiesgoTrabajoServiceRemote getBeanDocumentos(WebServiceContext context) {

        ServletContext servletContext = (ServletContext) context.getMessageContext().get("javax.xml.ws.servlet.context");
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        return  (ConsultalRiesgoTrabajoServiceRemote) webApplicationContext.getAutowireCapableBeanFactory().getBean("consultaRTTServiceBusiness");
    }
}
