(:: pragma bea:global-element-parameter parameter="$serviceResponse" element="ns0:validarDerechoBeneficioRifResponse" location="../schema/ServiceContract/ValidarDerechoBeneficioSATSchema.xsd" ::)
(:: pragma bea:global-element-return element="ns1:validarRifSatResponse" location="../schema/ServiceContract/ValidacionRifSatSchema.xsd" ::)

declare namespace ns1 = "http://mx.gob.imss.delta.global.service/";
declare namespace ns0 = "http://sat.gob.mx/ws/ValidarDerechoBeneficioRif";
declare namespace xf = "http://tempuri.org/RISS/Transformaciones/XQuery/TransformaValidaRIFResponse/";

declare function xf:TransformaValidaRIFResponse($serviceResponse as element(ns0:validarDerechoBeneficioRifResponse))
    as element(ns1:validarRifSatResponse) {
        <ns1:validarRifSatResponse>
            <ns1:rfcVigente>{ data($serviceResponse/contribuyente/rfcVigente) }</ns1:rfcVigente>
            <ns1:indicadorDerechoBeneficio>{ data($serviceResponse/contribuyente/indicadorDerechoBeneficio) }</ns1:indicadorDerechoBeneficio>
            <ns1:indicadorApartadoC>{ data($serviceResponse/contribuyente/indicadorApartadoC) }</ns1:indicadorApartadoC>
            <ns1:curp>{ data($serviceResponse/contribuyente/curp) }</ns1:curp>
            <ns1:fechaInicioRIF>{ data($serviceResponse/contribuyente/fechaInicioRIF) }</ns1:fechaInicioRIF>
            <ns1:exito>{ data($serviceResponse/mensaje/exito) }</ns1:exito>
            <ns1:claveError>{ data($serviceResponse/mensaje/claveError) }</ns1:claveError>
            <ns1:descripcion>{ data($serviceResponse/mensaje/descripcion) }</ns1:descripcion>
        </ns1:validarRifSatResponse>
};

declare variable $serviceResponse as element(ns0:validarDerechoBeneficioRifResponse) external;

xf:TransformaValidaRIFResponse($serviceResponse)