(:: pragma bea:global-element-parameter parameter="$serviceResponse" element="ns1:operacionResponse" location="../wsdl/ValidarCotizaNSSService.wsdl" ::)
(:: pragma bea:global-element-return element="ns2:validarSinCotizar24MesesResponse" location="../schema/ServiceContract/ValidacionImssSinCotizar24Meses.xsd" ::)

declare namespace ns2 = "http://mx.gob.imss.delta.global.service/";
declare namespace ns1 = "http://mx/gob/imss/webservices/cotizador/servicio";
declare namespace ns0 = "http://cotizador.webservices.imss.gob.mx";
declare namespace xf = "http://tempuri.org/RISS/Transformaciones/XQuery/TransformaCotiza24MesesPreviosResponse/";

declare function xf:TransformaCotiza24MesesPreviosResponse($serviceResponse as element(ns1:operacionResponse))
    as element(ns2:validarSinCotizar24MesesResponse) {
        <ns2:validarSinCotizar24MesesResponse>
            <ns2:indicadorCotizado24Meses>{ xs:boolean($serviceResponse/ns1:salida/ns0:indicadorCotiza) }</ns2:indicadorCotizado24Meses>
            <ns2:exito>{ data($serviceResponse/ns1:salida/ns0:codigo) }</ns2:exito>
            <ns2:claveError>{ data($serviceResponse/ns1:salida/ns0:codigo) }</ns2:claveError>
            <ns2:descripcion>{ data($serviceResponse/ns1:salida/ns0:mensaje) }</ns2:descripcion>
        </ns2:validarSinCotizar24MesesResponse>
};

declare variable $serviceResponse as element(ns1:operacionResponse) external;

xf:TransformaCotiza24MesesPreviosResponse($serviceResponse)