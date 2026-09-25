(:: pragma bea:global-element-parameter parameter="$proxyRequest" element="ns2:validarSinCotizar24MesesRequest" location="../schema/ServiceContract/ValidacionImssSinCotizar24Meses.xsd" ::)
(:: pragma bea:global-element-return element="ns1:operacionRequest" location="../wsdl/ValidarCotizaNSSService.wsdl" ::)

declare namespace ns2 = "http://mx.gob.imss.delta.global.service/";
declare namespace ns1 = "http://mx/gob/imss/webservices/cotizador/servicio";
declare namespace ns0 = "http://cotizador.webservices.imss.gob.mx";
declare namespace xf = "http://tempuri.org/RISS/Transformaciones/XQuery/TranformaCotiza24MesesPreviosRequest/";

declare function xf:TranformaCotiza24MesesPreviosRequest($proxyRequest as element(ns2:validarSinCotizar24MesesRequest))
    as element(ns1:operacionRequest) {
        <ns1:operacionRequest>
            <ns1:entrada>
                <ns0:cveAsignacionNSS>{ xs:int($proxyRequest/ns2:cveAsignacion) }</ns0:cveAsignacionNSS>
            </ns1:entrada>
        </ns1:operacionRequest>
};

declare variable $proxyRequest as element(ns2:validarSinCotizar24MesesRequest) external;

xf:TranformaCotiza24MesesPreviosRequest($proxyRequest)