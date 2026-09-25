(:: pragma bea:global-element-parameter parameter="$movimientoPatronal1" element="ns0:MovimientoPatronal" location="../schemas/movimiento06Sindo.xsd" ::)
(:: pragma bea:mfl-element-return type="AltaPatronal@" location="../mfl/Mov01AltaPatronal.mfl" ::)

declare namespace xf = "http://tempuri.org/Movimiento06SINDOProject/xquery/Movimiento01AltaXML_mfl/";
declare namespace ns0 = "http://www.mx.gob.imss.ctirss.delta/movimiento06Sindo";
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

declare function xf:Movimiento01AltaXML_mfl($movimientoPatronal1 as element(ns0:MovimientoPatronal))
    as element() {
        <AltaPatronal>
            <MovimientoAltaPatronal>
                <delegacionOrigen>{ data($movimientoPatronal1/ns0:delegacionOrigen) }</delegacionOrigen>
                <subdelegacionOrigen>{ data($movimientoPatronal1/ns0:subdelegacionOrigen) }</subdelegacionOrigen>
                <claveAplicacion>{ data($movimientoPatronal1/ns0:claveAplicacion) }</claveAplicacion>
                <tipoMovimiento>{ data($movimientoPatronal1/ns0:tipoMovimiento) }</tipoMovimiento>
                <origenMovimiento>{ data($movimientoPatronal1/ns0:origenMovimiento) }</origenMovimiento>
                <numeroFolio>{ data($movimientoPatronal1/ns0:numeroFolio) }</numeroFolio>
                <registroPatronal>{ data($movimientoPatronal1/ns0:registroPatronal) }</registroPatronal>
                <digitoVerificador>{ data($movimientoPatronal1/ns0:digitoVerificador) }</digitoVerificador>
                <fechaMovimiento>{ data(concat(fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 9, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 6, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 1, 4)))}</fechaMovimiento>
                <fechaRecepcionMovimiento>{ data(concat(fn:substring(xs:string($movimientoPatronal1/ns0:fechaRecepcion), 9, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaRecepcion), 6, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaRecepcion), 1, 4)))}</fechaRecepcionMovimiento>
                <claveUnica>{ data($movimientoPatronal1/ns0:curp) }</claveUnica>
                <subrogacionServicio>{ data($movimientoPatronal1/ns0:subrogacionServicio) }</subrogacionServicio>
                <claveMunicipio>{ data($movimientoPatronal1/ns0:claveMunicipio) }</claveMunicipio>
                <nombrePatron>{ data(fn:substring(xf:clean_chars($movimientoPatronal1/ns0:nombrePatron), 1, 80)) }</nombrePatron>
                <domicilioPatron>{ data(fn:substring(xf:clean_chars($movimientoPatronal1/ns0:domicilioPatron), 1, 40)) }</domicilioPatron>
                <codigoPostal>{ data($movimientoPatronal1/ns0:codigoPostal) }</codigoPostal>
                <localidadPatron>{ data(fn:substring(xf:clean_chars($movimientoPatronal1/ns0:localidad), 1, 40))}</localidadPatron>
                <giro>{ data(fn:substring(xf:clean_chars($movimientoPatronal1/ns0:giro), 1, 40)) }</giro>
                <clase>{ data($movimientoPatronal1/ns0:clase) }</clase>
                <fraccion>{data( concat(xs:string($movimientoPatronal1/ns0:division),
                            xs:string($movimientoPatronal1/ns0:grupo),
                            xs:string(delta:pad-integer-to-length($movimientoPatronal1/ns0:fraccion,2) )))}</fraccion>
                <prima>{ data(xs:int(fn:round(100000 * $movimientoPatronal1/ns0:prima))) }</prima>
                <causaBaja>{ data(xf:clean_chars($movimientoPatronal1/ns0:causa))}</causaBaja>
                <fechaCambioClasificacion>{ data($movimientoPatronal1/ns0:fechaCambioCla) }</fechaCambioClasificacion>
                <tipoCotizacion>1</tipoCotizacion>
                <nombrePatronalCorp>{ data(xf:clean_chars($movimientoPatronal1/ns0:rfc)) }</nombrePatronalCorp>
                <tipoPago>{ data($movimientoPatronal1/ns0:tipoPago)}</tipoPago>
                <mesEmision>{ data($movimientoPatronal1/ns0:mesEmi)}</mesEmision>
            </MovimientoAltaPatronal>
        </AltaPatronal>
};

declare variable $movimientoPatronal1 as element(ns0:MovimientoPatronal) external;

xf:Movimiento01AltaXML_mfl($movimientoPatronal1)