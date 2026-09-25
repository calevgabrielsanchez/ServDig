(:: pragma bea:global-element-parameter parameter="$notificacionRequest1" element="ns0:notificacionRequest" location="../schema/ServiceContract/NotificacionGlobalSchema.xsd" ::)
(:: pragma bea:global-element-return element="ns0:publicarComet" location="../wsdl/SolicitudNotificadorBusinessService.wsdl" ::)

declare namespace ns0 = "http://mx.gob.imss.delta.global.service/";
declare namespace xf = "http://tempuri.org/deltaGestionPatronalService/XQuery/requestNotificacionXQT/";

declare function xf:requestNotificacionXQT($notificacionRequest1 as element(ns0:notificacionRequest))
    as element(ns0:publicarComet) {
        <ns0:publicarComet>
            <ns0:arg0>{ data($notificacionRequest1/ns0:idTipoTramite) }</ns0:arg0>
            <ns0:arg1>{ data($notificacionRequest1/ns0:idPersona) }</ns0:arg1>
            <ns0:arg2>{ data($notificacionRequest1/ns0:idTipoPersona) }</ns0:arg2>
            <ns0:arg3>{ data($notificacionRequest1/ns0:numeroRegistroPatronal) }</ns0:arg3>
        </ns0:publicarComet>
};

declare variable $notificacionRequest1 as element(ns0:notificacionRequest) external;

xf:requestNotificacionXQT($notificacionRequest1)