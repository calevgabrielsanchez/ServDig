(:: pragma bea:global-element-parameter parameter="$consultarDatosGeneralesResponse1" element="ns8:consultarDatosGeneralesResponse" location="../wsdl/DeltaSolicitudBusinessService.wsdl" ::)
(:: pragma bea:schema-type-return type="ns2:SolicitudTO" location="../schema/XMLModel/gblModel.xsd" ::)

declare namespace ns2 = "gblModel:mx.gob.imss.ctirss.delta.global.model";
declare namespace ns1 = "java:mx.gob.imss.ctirss.delta.framework.base.model";
declare namespace ns4 = "javaUtil:java.util";
declare namespace ns3 = "http://schemas.xmlsoap.org/soap/encoding/";
declare namespace ns0 = "solicitud:mx.gob.imss.ctirss.delta.model.gestion.solicitud";
declare namespace ns9 = "java:mx.gob.imss.ctirss.delta.global.model";
declare namespace ns5 = "fwbm:mx.gob.imss.ctirss.delta.framework.base.model";
declare namespace ns6 = "java:java.util";
declare namespace xf = "http://tempuri.org/deltaGestionPatronalService/XQuery/ConsultaSolicitudResponseXQT/";
declare namespace ns7 = "java:mx.gob.imss.ctirss.delta.model.gestion.solicitud";
declare namespace ns8 = "http://mx.gob.imss.delta.global.service/";
declare namespace ns19 = "java:mx.gob.imss.ctirss.delta.model.gestion.patronal";
declare namespace ns28 = "java:mx.gob.imss.ctirss.delta.model.gestion.individuo";
declare namespace jsol = "java:mx.gob.imss.ctirss.delta.model.gestion.solicitud";

declare function xf:ConsultaSolicitudResponseXQT($consultarDatosGeneralesResponse1 as element(ns8:consultarDatosGeneralesResponse))
    as element() {
        <ns2:SolicitudTO>
            <ns2:SolicitudId>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:SolicitudId) }</ns2:SolicitudId>
            <ns2:NoFolioSolicitud>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:NoFolioSolicitud) }</ns2:NoFolioSolicitud>
            <ns2:TipoSolicitud>
                <ns0:IdTipoSolicitud>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:TipoSolicitud/ns7:IdTipoSolicitud) }</ns0:IdTipoSolicitud>
            </ns2:TipoSolicitud>
            <ns2:Persona>
                <ns2:IdPersona>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Persona/ns9:IdPersona) }</ns2:IdPersona>
                <ns2:CorreoDeNotificaciones>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Persona/ns9:CorreoDeNotificaciones) }</ns2:CorreoDeNotificaciones>
                <ns2:TipoPersona>
                    <ns2:IdTipoPersona>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Persona/ns9:TipoPersona/ns28:IdTipoPersona) }</ns2:IdTipoPersona>
                </ns2:TipoPersona>
            </ns2:Persona>
            <ns2:RegistroPatronal>
                <ns2:IdRegistroPatronal>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:RegistroPatronal/ns9:IdRegistroPatronal) }</ns2:IdRegistroPatronal>
                <ns2:NumeroRegistro>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:RegistroPatronal/ns9:NumeroRegistro) }</ns2:NumeroRegistro>
                <ns2:Modalidad>
                    <ns2:IdModalidad>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:RegistroPatronal/ns9:Modalidad/ns19:IdModalidad) }</ns2:IdModalidad>
                    <ns2:NumModalidad>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:RegistroPatronal/ns9:Modalidad/ns19:NumModalidad) }</ns2:NumModalidad>
                </ns2:Modalidad>
                <ns2:DigVerificador>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:RegistroPatronal/ns9:DigVerificador) }</ns2:DigVerificador>
                <ns2:Patron>
                    <ns2:Persona>
                        <ns2:IdPersona>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:RegistroPatronal/ns9:Patron/ns9:Persona/ns9:IdPersona) }</ns2:IdPersona>
                        <ns2:CorreoDeNotificaciones>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:RegistroPatronal/ns9:Patron/ns9:Persona/ns9:CorreoDeNotificaciones) }</ns2:CorreoDeNotificaciones>
                        <ns2:TipoPersona>
                            <ns2:IdTipoPersona>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:RegistroPatronal/ns9:Patron/ns9:Persona/ns9:TipoPersona/ns28:IdTipoPersona) }</ns2:IdTipoPersona>
                        </ns2:TipoPersona>
                    </ns2:Persona>
                </ns2:Patron>
            </ns2:RegistroPatronal>
            <pkcs7Bean>
                <claveSerial>{data($consultarDatosGeneralesResponse1/ns8:return/ns9:Certificado/jsol:ClaveSerial)}</claveSerial>
                <nombreCompleto>{data($consultarDatosGeneralesResponse1/ns8:return/ns9:Certificado/jsol:NombreCompleto)}</nombreCompleto>
                <estatusFiel>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Certificado/jsol:EstatusFiel) }</estatusFiel>
                <correoElectronico>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Certificado/jsol:CorreoElectronico) }</correoElectronico>
                <curpFiel>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Certificado/jsol:CurpFiel) }</curpFiel>
                <nombreUsuario>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Certificado/jsol:NombreUsuario) }</nombreUsuario>
                <rfcAsociado>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Certificado/jsol:RfcAsociado) }</rfcAsociado>
                <fechaValidaInicio>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Certificado/jsol:FechaValidaInicio) }</fechaValidaInicio>
                <fechaValidaFin>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Certificado/jsol:FechaValidaFin) }</fechaValidaFin>
                <telefono>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Certificado/jsol:Telefono) }</telefono>
                <idRol>{ data($consultarDatosGeneralesResponse1/ns8:return/ns9:Certificado/jsol:IdRol) }</idRol>
            </pkcs7Bean>
            {
                let $Tramites := $consultarDatosGeneralesResponse1/ns8:return/ns9:Tramites

  for $item in $Tramites/item
                  return 
                         <ns2:TramiteTO> 
                                        <ns2:TramiteId>{data($item/ns9:TramiteId)}</ns2:TramiteId>
                                         <ns2:TipoTramiteTO>
                                                  <ns2:IdTipoTramite>{data($item/ns9:TipoTramite/ns9:IdTipoTramite)}</ns2:IdTipoTramite>
                                         </ns2:TipoTramiteTO>
                                         <ns2:CveMunicipioImss>{data($item/ns9:CveMunicipioImss)}</ns2:CveMunicipioImss>
                                         <ns2:CveModalidad>{data($item/ns9:CveModalidad)}</ns2:CveModalidad>
                          </ns2:TramiteTO>
}
            
            
        </ns2:SolicitudTO>
};

declare variable $consultarDatosGeneralesResponse1 as element(ns8:consultarDatosGeneralesResponse) external;

xf:ConsultaSolicitudResponseXQT($consultarDatosGeneralesResponse1)