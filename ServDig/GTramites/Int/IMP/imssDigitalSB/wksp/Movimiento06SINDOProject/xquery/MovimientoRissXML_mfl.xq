(:: pragma bea:global-element-parameter parameter="$movimientoRiss1" element="ns0:MovimientoRiss" location="../schemas/Movimiento_riss.xsd" ::)
(:: pragma bea:mfl-element-return type="Riss@" location="../mfl/Movimiento_riss.mfl" ::)

declare namespace ns0 = "http://www.mx.gob.imss.ctirss.delta/movimientoRiss";
declare namespace xf = "http://tempuri.org/Movimiento06SINDOProject/xquery/MovimientoRissXML_mfl/";
declare namespace delta = "http://mx.gob.ctirss.delta";

declare function xf:clean_chars (
    $dirty_string as xs:string?) as xs:string {
  xf:remover_caracteres_conflictivos(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(
                                fn:upper-case($dirty_string), "[ñÑ]",
                                "#"),"[ÁáÄä]", "A"),"[ÉéËë]","E"),"[ÍíÏï]","I"),"[ÓóÖö]","O"),"[ÚúÜü]","U"), '\r?\n', ' ', 's'), '\s\s*', ' '))

};

declare function xf:remover_caracteres_conflictivos (
    $dirty_string as xs:string) as xs:string {
    fn:replace(fn:replace($dirty_string, "([ -/:-@&#91;-&#255;])(\1)+", "$1"), "[&#166;-&#255;]", "", "")
};

declare function delta:date-to-str($dateToConvert as xs:string) as xs:string {
    if(fn:string-length($dateToConvert)>9)then(
		concat(fn:substring(xs:string($dateToConvert), 1, 4)
        	,'-',fn:substring(xs:string($dateToConvert), 6, 2)
            ,'-',fn:substring(xs:string($dateToConvert), 9, 2))
    )else (
    	' '
    )
};



declare function xf:MovimientoRissXML_mfl($movimientoRiss1 as element(ns0:MovimientoRiss))
as element() {
    <Riss>
        <MovimientoRiss>
            <regPatAlf>{ data(fn:substring($movimientoRiss1/ns0:regPatAlf, 1, 8)) }</regPatAlf>
            <regPatMod>{ data($movimientoRiss1/ns0:regPatMod) }</regPatMod> 
            <regPatDv>{ data(fn:substring($movimientoRiss1/ns0:regPatDv,1,1)) }</regPatDv>
            <rfc>{ data(fn:substring(xf:clean_chars($movimientoRiss1/ns0:rfc),1,13)) }</rfc> 
            <nss>{ data(fn:substring(xf:clean_chars($movimientoRiss1/ns0:nss),1,11)) }</nss>             
            <curp>{ data(fn:substring(xf:clean_chars($movimientoRiss1/ns0:curp),1,18)) }</curp> 
            <tipPatPerFis>{ data(fn:substring($movimientoRiss1/ns0:tipPatPerFis,1,1)) }</tipPatPerFis> 
            <consecutivo>{ data($movimientoRiss1/ns0:consecutivo) }</consecutivo>            
            <fecIniRif>{ data(if (fn:empty($movimientoRiss1/ns0:fecIniRif)) then(' ')else (
    			delta:date-to-str(xs:string($movimientoRiss1/ns0:fecIniRif))) ) }</fecIniRif>
            <fecBajaRif>{ data(if (fn:empty($movimientoRiss1/ns0:fecBajaRif)) then(' ')else (
    			delta:date-to-str(xs:string($movimientoRiss1/ns0:fecBajaRif))) ) }</fecBajaRif>
            <fecIniRiss>{ data(if (fn:empty($movimientoRiss1/ns0:fecIniRiss)) then(' ')else (
    			delta:date-to-str(xs:string($movimientoRiss1/ns0:fecIniRiss))) ) }</fecIniRiss>
    		<fecBajaRiss>{ data(if (fn:empty($movimientoRiss1/ns0:fecBajaRiss)) then(' ')else (
    			delta:date-to-str(xs:string($movimientoRiss1/ns0:fecBajaRiss))) ) }</fecBajaRiss>
            <motBaja>{ data($movimientoRiss1/ns0:motBaja) }</motBaja> 
            <porDescAnioFiscal>{ data($movimientoRiss1/ns0:porDescAnioFiscal) }</porDescAnioFiscal>            
            <fecMovto>{ data(if (fn:empty($movimientoRiss1/ns0:fecMovto)) then(' ')else (
    			delta:date-to-str(xs:string($movimientoRiss1/ns0:fecMovto))) ) }</fecMovto>
        </MovimientoRiss>
    </Riss>
};

declare variable $movimientoRiss1 as element(ns0:MovimientoRiss) external;

xf:MovimientoRissXML_mfl($movimientoRiss1)