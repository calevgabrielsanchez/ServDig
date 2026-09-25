(:: pragma bea:global-element-parameter parameter="$operacionResponse" element="ns2:operacionResponse" location="../wsdl/ConsPatronesVigXAsigService.wsdl" ::)
(:: pragma bea:global-element-return element="ns0:validarSinCotizarPatronesResponse" location="../schema/ServiceContract/ValidacionImssSinCotizarPatrones.xsd" ::)

declare namespace ns2 = "http://mx/gob/imss/webservices/patrones/servicio";
declare namespace ns1 = "http://patrones.webservices.imss.gob.mx";
declare namespace ns0 = "http://mx.gob.imss.delta.global.service/";
declare namespace xf = "http://tempuri.org/RISS/Transformaciones/XQuery/TransformaTrabajadorActivoResponse/";

declare function xf:TransformaTrabajadorActivoResponse($operacionResponse as element(ns2:operacionResponse))
    as element(ns0:validarSinCotizarPatronesResponse) {
        <ns0:validarSinCotizarPatronesResponse>
            <ns0:indicadorCotizaPatrones>
            	{
            	if (fn:exists($operacionResponse/ns2:salida/ns1:patronesActivos)) 
                then
                    (xs:boolean('1'))
                else 
                    (xs:boolean('0'))
                }
            </ns0:indicadorCotizaPatrones>
            <ns0:exito>{ xs:int($operacionResponse/ns2:salida/ns1:codigo) }</ns0:exito>
            <ns0:claveError>{ xs:int($operacionResponse/ns2:salida/ns1:codigo) }</ns0:claveError>
            <ns0:descripcion>{ data($operacionResponse/ns2:salida/ns1:mensaje) }</ns0:descripcion>
        </ns0:validarSinCotizarPatronesResponse>
};

declare variable $operacionResponse as element(ns2:operacionResponse) external;

xf:TransformaTrabajadorActivoResponse($operacionResponse)