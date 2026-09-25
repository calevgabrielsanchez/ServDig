(:: pragma bea:global-element-parameter parameter="$movimientoPatronal1" element="ns0:MovimientoPatronal" location="../schemas/movimiento06Sindo.xsd" ::)
(:: pragma bea:mfl-element-return type="MovimientoPatronal@" location="../mfl/MovimientoPatronalXML_txt.mfl" ::)

declare namespace ns0 = "http://www.mx.gob.imss.ctirss.delta/movimiento06Sindo";
declare namespace xf = "http://tempuri.org/Movimiento06SINDOProject/xquery/MovimientoPatronalXML_MFL/";
declare namespace delta = "http://mx.gob.ctirss.delta";
declare function delta:pad-integer-to-length
( $integerToPad as xs:int,
    $length as xs:integer )  as xs:string {
      
   if ($length < string-length(string($integerToPad)))
   then error(xs:QName('funct:Integer_Longer_Than_Length'))
   else concat
         (delta:repeat-string(
            '0',$length - string-length(string($integerToPad))),
          string($integerToPad))
 };
 
declare function delta:clean_chars (
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
 
declare function xf:MovimientoPatronalXML_MFL($movimientoPatronal1 as element(ns0:MovimientoPatronal))
    as element() {
        <MovimientoPatronal>
            <Movimiento>
                <delegacionOrigen>{ data(xs:string($movimientoPatronal1/ns0:delegacionOrigen)) }</delegacionOrigen>
                <subdelegacionOrigen>{ data(fn:substring(xs:string($movimientoPatronal1/ns0:subdelegacionOrigen),1,2)) }</subdelegacionOrigen>
                <claveAplicacion>{ data(xs:string($movimientoPatronal1/ns0:claveAplicacion)) }</claveAplicacion>
                <tipoMovimiento>{ data(xs:string($movimientoPatronal1/ns0:tipoMovimiento)) }</tipoMovimiento>
                <origenMovimiento>{ data(xs:string($movimientoPatronal1/ns0:origenMovimiento)) }</origenMovimiento>
                <numeroFolio>{data(xs:string($movimientoPatronal1/ns0:numeroFolio))}</numeroFolio>
                <registroPatronal>{ data( fn:substring($movimientoPatronal1/ns0:registroPatronal,1,10)) }</registroPatronal>
                <digitoVerificador>{ data(xs:string($movimientoPatronal1/ns0:digitoVerificador)) }</digitoVerificador>
                <fechaMovimiento>
                    {
                        data(concat(fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 9, 2)
                        , fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 6, 2)
                        , fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 1, 4)))
                    }
				</fechaMovimiento>
                <giro>{ data(delta:clean_chars(fn:substring( $movimientoPatronal1/ns0:giro,1,40))) }</giro>
                <clase>{ data(xs:string($movimientoPatronal1/ns0:clase)) }</clase>
                <fraccion>
                    {data( concat(xs:string($movimientoPatronal1/ns0:division),
                    xs:string($movimientoPatronal1/ns0:grupo),
                    xs:string(delta:pad-integer-to-length($movimientoPatronal1/ns0:fraccion,2) )))}
				</fraccion>
                <prima>{ data(xs:int(fn:round(100000 * $movimientoPatronal1/ns0:prima))) }</prima>
                <causa>{ data(fn:replace($movimientoPatronal1/ns0:causa, '^(\d)$', '0$1')) }</causa>
            </Movimiento>
        </MovimientoPatronal>
};

declare variable $movimientoPatronal1 as element(ns0:MovimientoPatronal) external;

xf:MovimientoPatronalXML_MFL($movimientoPatronal1)