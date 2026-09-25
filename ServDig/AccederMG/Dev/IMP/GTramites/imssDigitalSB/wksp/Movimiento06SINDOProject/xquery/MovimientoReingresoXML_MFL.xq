xquery version "1.0" encoding "Cp1252";
(:: pragma bea:global-element-parameter parameter="$movimientoReingreso1" element="ns0:MovimientoReingreso" location="../schemas/movimientoReingreso.xsd" ::)
(:: pragma bea:mfl-element-return type="MovimientoReingreso@" location="../mfl/MovimientoReingreso.mfl" ::)

declare namespace xf = "http://tempuri.org/Movimiento06SINDOProject/xquery/MovimientoReingresoXML_MFL/";
declare namespace ns0 = "http://www.mx.gob.imss.ctirss.delta/integracion_reingreso";
declare namespace delta = "http://mx.gob.ctirss.delta";

declare function delta:date-to-str($dateToConvert as xs:string) as xs:string {
    if ($dateToConvert) then(
        concat(fn:substring(xs:string($dateToConvert), 9, 2)
                        , fn:substring(xs:string($dateToConvert), 6, 2)
                        , fn:substring(xs:string($dateToConvert), 1, 4))
    )
    else ('')
};

declare function xf:MovimientoReingresoXML_MFL($movimientoReingreso1 as element(ns0:MovimientoReingreso))
    as element() {
        <MovimientoReingreso>
            <Reingreso>
                <delOrig>{ data(xs:string($movimientoReingreso1/ns0:delOrig)) }</delOrig>
                <subOrig>{ data(xs:string($movimientoReingreso1/ns0:subOrig)) }</subOrig>
                <cveAplic>{ data(xs:string($movimientoReingreso1/ns0:cveAplic)) }</cveAplic>
                <tpMovto>{ data(xs:string($movimientoReingreso1/ns0:tpMovto)) }</tpMovto>
                <origenMov>{ data(xs:string($movimientoReingreso1/ns0:origenMov)) }</origenMov>
                <numFolio>{ data(xs:string($movimientoReingreso1/ns0:numFolio)) }</numFolio>
                <argumento>{ data(xs:string($movimientoReingreso1/ns0:argumento)) }</argumento>
                <regPatron>{ data(xs:string($movimientoReingreso1/ns0:regPatron)) }</regPatron>
                <digVrPat>{ data(xs:string($movimientoReingreso1/ns0:digVrPat)) }</digVrPat>
                <fMovto> { data(delta:date-to-str(xs:string($movimientoReingreso1/ns0:fMovto))) } </fMovto>
                <fRecepMovi>{ data(delta:date-to-str(xs:string($movimientoReingreso1/ns0:fRecepMovi))) }</fRecepMovi>
                <cveUnica>{ data(xs:string($movimientoReingreso1/ns0:cveUnica)) }</cveUnica>
                <idSubrServ>{ data(xs:string($movimientoReingreso1/ns0:idSubrServ)) }</idSubrServ>
                <idEventual>{ data(xs:string($movimientoReingreso1/ns0:idEventual)) }</idEventual>
                <numSegSoc>{ data(xs:string($movimientoReingreso1/ns0:numSegSoc)) }</numSegSoc>
                <digVrNss>{ data(xs:string($movimientoReingreso1/ns0:digVrNss)) }</digVrNss>
                <nomAseg>{ data(xs:string($movimientoReingreso1/ns0:nomAseg)) }</nomAseg>
                <idExtemp>{ data(xs:string($movimientoReingreso1/ns0:idExtemp)) }</idExtemp>
                <reducPago>{ data(xs:string($movimientoReingreso1/ns0:reducPago)) }</reducPago>
                <extODel>{ data(xs:string($movimientoReingreso1/ns0:extODel)) }</extODel>
                <salBase>{ data(xs:int(fn:round(100 * $movimientoReingreso1/ns0:salBase))) }</salBase>
                <salInfonavit>{ data(xs:int(fn:round(100 * $movimientoReingreso1/ns0:salInfonavit))) }</salInfonavit>
                <tpSalario>{ data(xs:string($movimientoReingreso1/ns0:tpSalario)) }</tpSalario>
                <sexo>{ data(xs:string($movimientoReingreso1/ns0:sexo)) }</sexo>
                <mesNac>{ data(xs:string($movimientoReingreso1/ns0:mesNac)) }</mesNac>
                <lugarNac>{ data(xs:string($movimientoReingreso1/ns0:lugarNac)) }</lugarNac>
                <umf>{ data(xs:string($movimientoReingreso1/ns0:umf)) }</umf>
                <autPerm>{ data(xs:string($movimientoReingreso1/ns0:autPerm)) }</autPerm>
                <delDest>{ data(xs:string($movimientoReingreso1/ns0:delDest)) }</delDest>
                <subDest>{ data(xs:string($movimientoReingreso1/ns0:subDest)) }</subDest>
                <tpDerech>{ data(xs:string($movimientoReingreso1/ns0:tpDerech)) }</tpDerech>
                <aaNac>{ data(xs:string($movimientoReingreso1/ns0:aaNac)) }</aaNac>
                <situacion>{ data(xs:string($movimientoReingreso1/ns0:situacion)) }</situacion>
                <tsalODel>{ data(xs:string($movimientoReingreso1/ns0:tsalODel)) }</tsalODel>
                <nombreDh>{ data(xs:string($movimientoReingreso1/ns0:nombreDh)) }</nombreDh>
                <mesNacAp>{ data(xs:string($movimientoReingreso1/ns0:mesNacAp)) }</mesNacAp>
                <nssCorr>{ data(xs:string($movimientoReingreso1/ns0:nssCorr)) }</nssCorr>
                <digVrNssCorr>{ data(xs:string($movimientoReingreso1/ns0:digVrNssCorr)) }</digVrNssCorr>
                <nomAsegC>{ data(xs:string($movimientoReingreso1/ns0:nomAsegC)) }</nomAsegC>
                <tpPens>{ data(xs:string($movimientoReingreso1/ns0:tpPens)) }</tpPens>
                <alfGuar>{ data(xs:string($movimientoReingreso1/ns0:alfGuar)) }</alfGuar>
                <numGuar>{ data(xs:string($movimientoReingreso1/ns0:numGuar)) }</numGuar>
                <condicion>{ data(xs:string($movimientoReingreso1/ns0:condicion)) }</condicion>
                <locMpio>{ data(xs:string($movimientoReingreso1/ns0:locMpio)) }</locMpio>
                <tpProrroga>{ data(xs:string($movimientoReingreso1/ns0:tpProrroga)) }</tpProrroga>
                <fecTerProrr>17171000</fecTerProrr>
                <idPd>{ data(xs:string($movimientoReingreso1/ns0:idPd)) }</idPd>
            </Reingreso>
        </MovimientoReingreso>
};

declare variable $movimientoReingreso1 as element(ns0:MovimientoReingreso) external;

xf:MovimientoReingresoXML_MFL($movimientoReingreso1)