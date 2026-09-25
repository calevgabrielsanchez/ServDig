(:: pragma bea:global-element-parameter parameter="$movimientoPatronal1" element="ns0:MovimientoPatronal" location="../schemas/movimiento06Sindo.xsd" ::)
(:: pragma bea:mfl-element-return type="MovPatronalCambioDomicilio@" location="../mfl/Mov04CambioDomicilioPatrones.mfl" ::)

declare namespace ns0 = "http://www.mx.gob.imss.ctirss.delta/movimiento06Sindo";
declare namespace xf = "http://tempuri.org/Movimiento06SINDOProject/xquery/Movimiento04DomicilioXML_mfl/";
declare namespace delta = "http://mx.gob.ctirss.delta";

declare function delta:pad-integer-to-length
( $integerToPad as xs:int,
    $length as xs:integer )  as xs:string {
      
   if ($length < string-length(string($integerToPad)))
   then error(xs:QName('functx:Integer_Longer_Than_Length'))
   else concat
         (delta:repeat-string(
            '0',$length - string-length(string($integerToPad))),
          string($integerToPad))
 };


declare function xf:clean_chars (
    $dirty_string as xs:string?) as xs:string {
  xf:remover_caracteres_conflictivos(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(
                                fn:upper-case($dirty_string), "[ñÑ]",
                                "#"),"Á", "A"),"É","E"),"Í","I"),"Ó","O"),"[ÚÜü]","U"), '\r?\n', ' ', 's'), '\s\s*', ' '))
};

declare function xf:remover_caracteres_conflictivos (
    $dirty_string as xs:string) as xs:string {
    fn:replace($dirty_string, "([ -/:-@&#91;-&#255;])(\1)+", "$1")
};

declare function delta:repeat-string
  ( $stringToRepeat as xs:string? ,
    $count as xs:integer )  as xs:string {
      
   string-join((for $i in 1 to $count return $stringToRepeat),
                        '')
 } ; 


declare function xf:Movimiento04DomicilioXML_mfl($movimientoPatronal1 as element(ns0:MovimientoPatronal))
    as element() {
        <MovPatronalCambioDomicilio>
            <MovimientoCambioDomicilio>
                <delegacionOrigen>{ data(xs:string($movimientoPatronal1/ns0:delegacionOrigen)) }</delegacionOrigen>
                <subdelegacionOrigen>{ data(xs:string($movimientoPatronal1/ns0:subdelegacionOrigen)) }</subdelegacionOrigen>
                <claveAplicacion>{ data(xs:string($movimientoPatronal1/ns0:claveAplicacion)) }</claveAplicacion>
                <tipoMovimiento>{ data(xs:string($movimientoPatronal1/ns0:tipoMovimiento)) }</tipoMovimiento>
                <origenMovimiento>{ data(xs:string($movimientoPatronal1/ns0:origenMovimiento)) }</origenMovimiento>
                <numeroFolio>{ data(xs:string($movimientoPatronal1/ns0:numeroFolio)) }</numeroFolio>
                <registroPatronal>{ data($movimientoPatronal1/ns0:registroPatronal) }</registroPatronal>
                <digitoVerificador>{ data(xs:string($movimientoPatronal1/ns0:digitoVerificador)) }</digitoVerificador>
                <fechaMovimiento>{ data(concat(fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 9, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 6, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 1, 4)))}</fechaMovimiento>
                <fechaRecepcionMovimiento>{ data(concat(fn:substring(xs:string($movimientoPatronal1/ns0:fechaRecepcion), 9, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaRecepcion), 6, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaRecepcion), 1, 4)))}</fechaRecepcionMovimiento>
                <claveMunicipio>{ data(xs:string($movimientoPatronal1/ns0:claveMunicipio)) }</claveMunicipio>
                <nombrePatron>{ data(fn:substring(xf:clean_chars($movimientoPatronal1/ns0:nombrePatron), 1, 50)) }</nombrePatron>
                <domicilioPatron>{ data(fn:substring(xf:clean_chars($movimientoPatronal1/ns0:domicilioPatron), 1, 40)) }</domicilioPatron>
                <codigoPostal>{ data($movimientoPatronal1/ns0:codigoPostal) }</codigoPostal>
                <localidadPatron>{ data(fn:substring(xf:clean_chars($movimientoPatronal1/ns0:localidad), 1, 40)) }</localidadPatron>
                <fraccion>{ data(xs:string($movimientoPatronal1/ns0:fraccion)) }</fraccion>
            </MovimientoCambioDomicilio>
        </MovPatronalCambioDomicilio>
};

declare variable $movimientoPatronal1 as element(ns0:MovimientoPatronal) external;

xf:Movimiento04DomicilioXML_mfl($movimientoPatronal1)