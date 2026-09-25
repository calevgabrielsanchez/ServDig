(:: pragma bea:global-element-parameter parameter="$movimientoCorreccionDatosAsegurado1" element="ns0:MovimientoCorreccionDatosAsegurado" location="../schemas/MovCorreccionDatosAsegurado.xsd" ::)
(:: pragma bea:mfl-element-return type="AseguradoCorreccion@" location="../mfl/Movcorrecciones_DatosAsegurado.mfl" ::)

declare namespace xf = "http://tempuri.org/CorreccionesDatosAsegurado/xquery/MovCorreccionesDatosAseguradoXML_mfl/";
declare namespace ns0 = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado";
declare namespace delta = "http://mx.gob.ctirss.delta";

declare function delta:clean_chars(
    $dirty_string as xs:string?) as xs:string {
  fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(
                                fn:upper-case($dirty_string), "[ñÑ]",
                                "#"),"Á", "A"),"É","E"),"Í","I"),"Ó","O"),"[ÚÜü]","U"), '\r?\n', ' ', 's'), '\s\s*', ' ')
};


declare function xf:MovCorreccionesDatosAseguradoXML_mfl($movimientoCorreccionDatosAsegurado1 as element(ns0:MovimientoCorreccionDatosAsegurado))
    as element() {
        <AseguradoCorreccion>
            <MovCorreccionesDatosAsegurado>
                <delOrig>{ data($movimientoCorreccionDatosAsegurado1/ns0:delOrig) }</delOrig>
                <subOrig>{ data($movimientoCorreccionDatosAsegurado1/ns0:subOrig) }</subOrig>
                <cveAplic>{ data($movimientoCorreccionDatosAsegurado1/ns0:cveAplic) }</cveAplic>
                <tpMovto>{ data($movimientoCorreccionDatosAsegurado1/ns0:tpMovto) }</tpMovto>
                <origenMov>{ data($movimientoCorreccionDatosAsegurado1/ns0:origenMov) }</origenMov>
                <numFolio>{ data($movimientoCorreccionDatosAsegurado1/ns0:numFolio) }</numFolio>
                <numSegSoc>{ data($movimientoCorreccionDatosAsegurado1/ns0:numSegSoc) }</numSegSoc>
                <digVrNss>{ data($movimientoCorreccionDatosAsegurado1/ns0:digVrNss) }</digVrNss>
                <nomAseg>{ data(fn:substring(delta:clean_chars($movimientoCorreccionDatosAsegurado1/ns0:nomAseg), 1, 50)) }</nomAseg>
                <sexo>{ data($movimientoCorreccionDatosAsegurado1/ns0:sexo) }</sexo>
                <mesNac>{ data($movimientoCorreccionDatosAsegurado1/ns0:mesNac) }</mesNac>
                <lugarNac>{ data($movimientoCorreccionDatosAsegurado1/ns0:lugarNac) }</lugarNac>
                <nomAsegC>{ data(fn:substring(delta:clean_chars($movimientoCorreccionDatosAsegurado1/ns0:nomAsegC), 1, 50)) }</nomAsegC>
            </MovCorreccionesDatosAsegurado>
        </AseguradoCorreccion>
};

declare variable $movimientoCorreccionDatosAsegurado1 as element(ns0:MovimientoCorreccionDatosAsegurado) external;

xf:MovCorreccionesDatosAseguradoXML_mfl($movimientoCorreccionDatosAsegurado1)