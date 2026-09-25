(:: pragma bea:global-element-parameter parameter="$movimientoPatronal1" element="ns0:MovimientoPatronal" location="../schemas/movimiento06Sindo.xsd" ::)
(:: pragma bea:mfl-element-return type="PatronCambioNombre@" location="../mfl/Mov05PatronCambioNombre.mfl" ::)

declare namespace ns0 = "http://www.mx.gob.imss.ctirss.delta/movimiento06Sindo";
declare namespace xf = "http://tempuri.org/Movimiento06SINDOProject/xquery/Movimiento05NombreXML_MFL/";

declare function xf:clean_chars (
    $dirty_string as xs:string?) as xs:string {
  fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(
                                fn:upper-case($dirty_string), "[ñÑ]",
                                "#"),"Á", "A"),"É","E"),"Í","I"),"Ó","O"),"[ÚÜü]","U"), '\r?\n', ' ', 's')
};

declare function xf:Movimiento05NombreXML_MFL($movimientoPatronal1 as element(ns0:MovimientoPatronal))
    as element() {
        <PatronCambioNombre>
            <MovimientoCambioNombre>
                <delegacionOrigen>{ data(xs:string($movimientoPatronal1/ns0:delegacionOrigen)) }</delegacionOrigen>
                <subdelegacionOrigen>{ data(xs:string($movimientoPatronal1/ns0:subdelegacionOrigen)) }</subdelegacionOrigen>
                <claveAplicacion>{ data(xs:string($movimientoPatronal1/ns0:claveAplicacion)) }</claveAplicacion>
                <tipoMovimiento>{ data(xs:string($movimientoPatronal1/ns0:tipoMovimiento)) }</tipoMovimiento>
                <origenMovimiento>6</origenMovimiento>
                <numeroFolio>{ data(xs:string($movimientoPatronal1/ns0:numeroFolio)) }</numeroFolio>
                <registroPatronal>{ data($movimientoPatronal1/ns0:registroPatronal) }</registroPatronal>
                <digitoVerificador>{ data(xs:string($movimientoPatronal1/ns0:digitoVerificador)) }</digitoVerificador>
                <fechaMovimiento>{ data(concat(fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 9, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 6, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 1, 4)))}</fechaMovimiento>
                <fechaRecepcionMovimiento>{ data(concat(fn:substring(xs:string($movimientoPatronal1/ns0:fechaRecepcion), 9, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaRecepcion), 6, 2)
                                , fn:substring(xs:string($movimientoPatronal1/ns0:fechaRecepcion), 1, 4)))}</fechaRecepcionMovimiento>
                <claveUnica>{ data($movimientoPatronal1/ns0:curp) }</claveUnica>
                <nombrePatron>{ data(xf:clean_chars($movimientoPatronal1/ns0:nombrePatron)) }</nombrePatron>
                <rfc>{ data(xf:clean_chars($movimientoPatronal1/ns0:rfc)) }</rfc>
                <nombrePatronalC>{ data(xf:clean_chars($movimientoPatronal1/ns0:nombrePatronalC)) }</nombrePatronalC>
            </MovimientoCambioNombre>
        </PatronCambioNombre>
};

declare variable $movimientoPatronal1 as element(ns0:MovimientoPatronal) external;

xf:Movimiento05NombreXML_MFL($movimientoPatronal1)