
package mx.gob.imss.cit.clienteServiciosComunes.ws.commonschema;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.cit.ws.commonschema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.cit.ws.commonschema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SGBDE }
     * 
     */
    public SGBDE createSGBDE() {
        return new SGBDE();
    }

    /**
     * Create an instance of {@link GovernanceHeaderResponse }
     * 
     */
    public GovernanceHeaderResponse createGovernanceHeaderResponse() {
        return new GovernanceHeaderResponse();
    }

    /**
     * Create an instance of {@link GovernanceHeaderRequest }
     * 
     */
    public GovernanceHeaderRequest createGovernanceHeaderRequest() {
        return new GovernanceHeaderRequest();
    }

    /**
     * Create an instance of {@link SGBDS }
     * 
     */
    public SGBDS createSGBDS() {
        return new SGBDS();
    }

}
