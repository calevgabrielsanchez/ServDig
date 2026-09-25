(:: pragma bea:global-element-parameter parameter="$trabajadorActivoRequest" element="ns0:validarSinCotizarPatronesRequest" location="../schema/ServiceContract/ValidacionImssSinCotizarPatrones.xsd" ::)
(:: pragma bea:global-element-return element="ns2:operacionRequest" location="../wsdl/ConsPatronesVigXAsigService.wsdl" ::)

declare namespace ns2 = "http://mx/gob/imss/webservices/patrones/servicio";
declare namespace ns1 = "http://patrones.webservices.imss.gob.mx";
declare namespace ns0 = "http://mx.gob.imss.delta.global.service/";
declare namespace xf = "http://tempuri.org/RISS/Transformaciones/XQuery/TransformaTrabajadorActivoRequest/";

declare function xf:TransformaTrabajadorActivoRequest($trabajadorActivoRequest as element(ns0:validarSinCotizarPatronesRequest))
    as element(ns2:operacionRequest) {
        <ns2:operacionRequest>
            <ns2:entrada>
            	<ns1:cveAsignacionNSS>{ xs:int($trabajadorActivoRequest/ns0:cveAsignacion) }</ns1:cveAsignacionNSS>
            </ns2:entrada>
        </ns2:operacionRequest>
};

declare variable $trabajadorActivoRequest as element(ns0:validarSinCotizarPatronesRequest) external;

xf:TransformaTrabajadorActivoRequest($trabajadorActivoRequest)