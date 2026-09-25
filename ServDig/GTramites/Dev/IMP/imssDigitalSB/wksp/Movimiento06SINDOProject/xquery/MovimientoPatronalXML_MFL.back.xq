(:: pragma bea:global-element-parameter parameter="$movimientoPatronal1" element="ns0:MovimientoPatronal" location="../schemas/movimiento06Sindo.xsd" ::)
(:: pragma bea:mfl-element-return type="MovimientoPatronal@" location="../mfl/MovimientoPatronalXML_txt.mfl" ::)

declare namespace ns0 = "http://www.mx.gob.imss.ctirss.delta/movimiento06Sindo";
declare namespace xf = "http://tempuri.org/Movimiento06SINDOProject/xquery/MovimientoPatronalXML_MFL/";

declare function xf:MovimientoPatronalXML_MFL($movimientoPatronal1 as element(ns0:MovimientoPatronal))
    as element() {
        <MovimientoPatronal>
            <Movimiento>
                <delegacionOrigen>{ data(xs:string($movimientoPatronal1/ns0:delegacionOrigen)) }</delegacionOrigen>
                <subdelegacionOrigen>{ data(xs:string($movimientoPatronal1/ns0:subdelegacionOrigen)) }</subdelegacionOrigen>
                <claveAplicacion>{ data(xs:string($movimientoPatronal1/ns0:claveAplicacion)) }</claveAplicacion>
                <tipoMovimiento>{ data(xs:string($movimientoPatronal1/ns0:tipoMovimiento)) }</tipoMovimiento>
                <origenMovimiento>{ data(xs:string($movimientoPatronal1/ns0:origenMovimiento)) }</origenMovimiento>
                <registroPatronal>{ data($movimientoPatronal1/ns0:registroPatronal) }</registroPatronal>
                <digitoVerificador>{ data(xs:string($movimientoPatronal1/ns0:digitoVerificador)) }</digitoVerificador>
                <fechaMovimiento>{ data(concat(fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 1, 4)
                            , fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 6, 2)
                            , fn:substring(xs:string($movimientoPatronal1/ns0:fechaMovimiento), 9, 2)))}</fechaMovimiento>
                <giro>{ data($movimientoPatronal1/ns0:giro) }</giro>
                <clase>{ data(xs:string($movimientoPatronal1/ns0:clase)) }</clase>
                <fraccion>{ data(xs:string($movimientoPatronal1/ns0:fraccion)) }</fraccion>
                <prima>{ data(xs:int(fn:round(100 * $movimientoPatronal1/ns0:prima))) }</prima>
                <causa>{ data(xs:string($movimientoPatronal1/ns0:causa)) }</causa>
            </Movimiento>
        </MovimientoPatronal>
};

declare variable $movimientoPatronal1 as element(ns0:MovimientoPatronal) external;

xf:MovimientoPatronalXML_MFL($movimientoPatronal1)