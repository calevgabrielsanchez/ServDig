package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


@XmlRegistry
public class ObjectFactory {

    //private final static QName _Cancelacion_QNAME = new QName("http://cancelacfd.sat.gob.mx", "Cancelacion");
    //private final static QName _Acuse_QNAME = new QName("", "Acuse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.schema.cancelacion
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReferenceType }
     *
     */
    
     public CancelacionType createCancelacionType() {
        return new CancelacionType();
    }
     
    public ReferenceType createReferenceType() {
        return new ReferenceType();
    }

    /**
     * Create an instance of {@link SignedInfoType }
     *
     */
    public SignedInfoType createSignedInfoType() {
        return new SignedInfoType();
    }

    /**
     * Create an instance of {@link CanonicalizationMethodType }
     *
     */
    public CanonicalizationMethodType createCanonicalizationMethodType() {
        return new CanonicalizationMethodType();
    }

    /**
     * Create an instance of {@link TransformType }
     *
     */
    public TransformType createTransformType() {
        return new TransformType();
    }

    /**
     * Create an instance of {@link KeyValueType }
     *
     */
    public KeyValueType createKeyValueType() {
        return new KeyValueType();
    }

    /**
     * Create an instance of {@link TransformsType }
     *
     */
    public TransformsType createTransformsType() {
        return new TransformsType();
    }

    /**
     * Create an instance of {@link RSAKeyValueType }
     *
     */
    public RSAKeyValueType createRSAKeyValueType() {
        return new RSAKeyValueType();
    }

    /**
     * Create an instance of {@link KeyInfoType }
     *
     */
    public KeyInfoType createKeyInfoType() {
        return new KeyInfoType();
    }

    /**
     * Create an instance of {@link FoliosType }
     *
     */
    public FoliosType createFoliosType() {
        return new FoliosType();
    }

    /**
     * Create an instance of {@link DigestMethodType }
     *
     */
    public DigestMethodType createDigestMethodType() {
        return new DigestMethodType();
    }
    

    /**
     * Create an instance of {@link SignatureMethodType }
     *
     */
    public SignatureMethodType createSignatureMethodType() {
        return new SignatureMethodType();
    }

    /**
     * Create an instance of {@link SignatureType }
     *
     */
    public SignatureType createSignatureType() {
        return new SignatureType();
    }

    /**
     * Create an instance of {@link SignatureType }
     *
     */
    public X509DataType createX509DataType() {
        return new X509DataType();
    }

    public X509IssuerSerialType createX509IssuerSerialType() {
        return new X509IssuerSerialType();
    }
}
