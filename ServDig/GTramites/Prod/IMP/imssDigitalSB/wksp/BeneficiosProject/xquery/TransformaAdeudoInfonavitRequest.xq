(:: pragma bea:global-element-parameter parameter="$proxyRequest" element="ns1:validarRissInfonavitRequest" location="../schema/ServiceContract/ValidacionRissInfonavitSchema.xsd" ::)
(:: pragma bea:global-element-return element="ns0:consultaAdeudos" location="../schema/ServiceContract/ConsultaAdeudosInfonavitSchema" ::)

declare namespace ns1 = "http://mx.gob.imss.delta.global.service/";
declare namespace ns0 = "http://services.infonavit.org.mx/riss";
declare namespace xf = "http://tempuri.org/RISS/Transformaciones/XQuery/TransformaAdeudoInfonavitRequest/";

declare function xf:TransformaAdeudoInfonavitRequest($proxyRequest as element(ns1:validarRissInfonavitRequest))
    as element(ns0:consultaAdeudos) {
        <ns0:consultaAdeudos>
            <RequestAdeudosVO>
                <nrp>{ data($proxyRequest/ns1:nrp) }</nrp>
                <nss>{ data($proxyRequest/ns1:nss) }</nss>
                <rfc>{ data($proxyRequest/ns1:rfc) }</rfc>
            </RequestAdeudosVO>
        </ns0:consultaAdeudos>
};

declare variable $proxyRequest as element(ns1:validarRissInfonavitRequest) external;

xf:TransformaAdeudoInfonavitRequest($proxyRequest)