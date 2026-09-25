(:: pragma bea:global-element-parameter parameter="$proxyRequest" element="ns1:validarRifSatRequest" location="../schema/ServiceContract/ValidacionRifSatSchema.xsd" ::)
(:: pragma bea:global-element-return element="ns0:validarDerechoBeneficioRifRequest" location="../schema/ServiceContract/ValidarDerechoBeneficioSATSchema.xsd" ::)

declare namespace ns1 = "http://mx.gob.imss.delta.global.service/";
declare namespace ns0 = "http://sat.gob.mx/ws/ValidarDerechoBeneficioRif";
declare namespace xf = "http://tempuri.org/RISS/Transformaciones/XQuery/TransformaValidRIFRequest/";

declare function xf:TransformaValidRIFRequest($proxyRequest as element(ns1:validarRifSatRequest))
    as element(ns0:validarDerechoBeneficioRifRequest) {
        <ns0:validarDerechoBeneficioRifRequest>
            <rfc>{ data($proxyRequest/ns1:rfc) }</rfc>
            <indicadorInstitucionConsulta>{ data($proxyRequest/ns1:indicadorInstitucionConsulta) }</indicadorInstitucionConsulta>
        </ns0:validarDerechoBeneficioRifRequest>
};

declare variable $proxyRequest as element(ns1:validarRifSatRequest) external;

xf:TransformaValidRIFRequest($proxyRequest)