(:: pragma bea:global-element-parameter parameter="$adeudosInfonavitResponse" element="ns0:consultaAdeudosResponse" location="../schema/ServiceContract/ConsultaAdeudosInfonavitSchema" ::)
(:: pragma bea:global-element-return element="ns1:validarRissInfonavitResponse" location="../schema/ServiceContract/ValidacionRissInfonavitSchema.xsd" ::)

declare namespace ns1 = "http://mx.gob.imss.delta.global.service/";
declare namespace ns0 = "http://services.infonavit.org.mx/riss";
declare namespace xf = "http://tempuri.org/RISS/Transformaciones/XQuery/TransformaAdeudoInfonavitResponse/";

declare function xf:TransformaAdeudoInfonavitResponse($adeudosInfonavitResponse as element(ns0:consultaAdeudosResponse))
    as element(ns1:validarRissInfonavitResponse) {
        <ns1:validarRissInfonavitResponse>
            <ns1:indicadorDerechoBeneficio>{ data($adeudosInfonavitResponse/ResponseAdeudosVO/indicadorDerechoBeneficio) }</ns1:indicadorDerechoBeneficio>
            <ns1:motivoDeRechazo>{ data($adeudosInfonavitResponse/ResponseAdeudosVO/descripcion) }</ns1:motivoDeRechazo>
            <ns1:exito>{ data($adeudosInfonavitResponse/ResponseAdeudosVO/exito) }</ns1:exito>
            <ns1:claveError>{ data($adeudosInfonavitResponse/ResponseAdeudosVO/claveError) }</ns1:claveError>
            <ns1:descripcion>{ data($adeudosInfonavitResponse/ResponseAdeudosVO/descripcion) }</ns1:descripcion>
        </ns1:validarRissInfonavitResponse>
};

declare variable $adeudosInfonavitResponse as element(ns0:consultaAdeudosResponse) external;

xf:TransformaAdeudoInfonavitResponse($adeudosInfonavitResponse)