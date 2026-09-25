(:: pragma bea:global-element-parameter parameter="$movimiento1" element="ns0:MovimientoAsignacion" location="../schemas/movimientoasignacion.xsd" ::)
(:: pragma bea:mfl-element-return type="AsignacionPatron@" location="../mfl/MovimientoAsignacion.mfl" ::)

declare namespace xf = "http://tempuri.org/Movimiento06SINDOProject/xquery/MovimientoAsignacion_mfl/";
declare namespace ns0 = "http://www.mx.gob.imss.ctirss.delta/movimientoasignacion";

declare function xf:clean_chars (
    $dirty_string as xs:string?) as xs:string {
  fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(
                                fn:upper-case($dirty_string), "[ñÑ]",
                                "#"),"Á", "A"),"É","E"),"Í","I"),"Ó","O"),"[ÚÜü]","U"), '\r?\n', ' ', 's')
};

declare function xf:manejar_lugar_invalido (
    $lugarNac as xs:string?) as xs:string {
    fn:replace($lugarNac, '([4-9]\d|3[3-9])', '35', '')
};


declare function xf:MovimientoAsignacion_mfl($movimiento1 as element(ns0:MovimientoAsignacion))
    as element() {
        <AsignacionPatron>
            <Movimiento>  
                <cizOrigen>{ data(xs:string($movimiento1/ns0:cizOrigen)) }</cizOrigen>
                <delOrigen>{ data(xs:string($movimiento1/ns0:delOrigen)) }</delOrigen>
                <subdelOrigen>{  data(xs:string($movimiento1/ns0:subdelOrigen)) }</subdelOrigen>
                <codEnvio>{  data(xs:string($movimiento1/ns0:codEnvio)) }</codEnvio>
                <codRetorno>{ data(xs:string($movimiento1/ns0:codRetorno)) }</codRetorno>
                <condicion>{ data(xs:string($movimiento1/ns0:condicion)) }</condicion>
                <tpMovto>{ data(xs:string($movimiento1/ns0:tpMovto)) }</tpMovto>
                <opcionMovto44>{ data(xs:string($movimiento1/ns0:opcionMovto44)) }</opcionMovto44>
                <nss>{ data(xs:string($movimiento1/ns0:nss)) }</nss>
                <digver>{ data(xs:string($movimiento1/ns0:digver)) }</digver>
                <nombre>{ data(fn:substring(xf:clean_chars(xs:string($movimiento1/ns0:nombre)), 1, 50)) }</nombre>
                <sexo>{  data(xs:string($movimiento1/ns0:sexo)) }</sexo>
                <mesNac>{ data(xs:string($movimiento1/ns0:mesNac)) }</mesNac>
                <lugarNac>{  data(xf:manejar_lugar_invalido(xs:string($movimiento1/ns0:lugarNac))) }</lugarNac>
                <nssC>{  data(xs:string($movimiento1/ns0:nssC)) }</nssC>
                <digverC>{ data(xs:string($movimiento1/ns0:digverC)) }</digverC>
                <nombreCond>{ data(xf:clean_chars(xs:string($movimiento1/ns0:nombreCond))) }</nombreCond>
                <tpError>{ data(xs:string($movimiento1/ns0:tpError)) }</tpError>
                <filler2> { data(xs:string($movimiento1/ns0:idUsuario)) } </filler2>
                <umf>{ data(xs:string($movimiento1/ns0:umf)) }</umf>
                <origen>{ data(xs:string($movimiento1/ns0:origen)) }</origen>
                <fechaMovto>{ data(concat(fn:substring(xs:string($movimiento1/ns0:fechaMovto), 1, 4)
                                , fn:substring(xs:string($movimiento1/ns0:fechaMovto), 6, 2)
                                , fn:substring(xs:string($movimiento1/ns0:fechaMovto), 9, 2))) }</fechaMovto>
                <curp>{  data(xs:string($movimiento1/ns0:curp)) }</curp>
            </Movimiento>
        </AsignacionPatron>
};

declare variable $movimiento1 as element(ns0:MovimientoAsignacion) external;

xf:MovimientoAsignacion_mfl($movimiento1)