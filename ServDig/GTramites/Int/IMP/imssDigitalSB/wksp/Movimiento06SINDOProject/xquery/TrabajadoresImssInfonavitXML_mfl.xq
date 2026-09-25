(:: pragma bea:global-element-parameter parameter="$trabajadoresImssInfonavit1" element="ns0:TrabajadoresImssInfonavit" location="../schemas/TrabajadoresImssInfonavit.xsd" ::)
(:: pragma bea:mfl-element-return type="TrabajadoresImssInfonavit@" location="../mfl/TrabajadoresImssInfonavit.mfl" ::)

declare namespace ns0 = "http://www.mx.gob.imss.ctirss.delta/trabajadoresImssInfonavit";
declare namespace xf = "http://tempuri.org/Movimiento06SINDOProject/xquery/TrabajadoresImssInfonavitXML_mfl/";

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

declare function xf:TrabajadoresImssInfonavitXML_mfl($trabajadoresImssInfonavit1 as element(ns0:TrabajadoresImssInfonavit))
    as element() {
        <TrabajadoresImssInfonavit>
            <TrabajadoresInfonavit>            	
            	<tipPatPerFis>{ data(fn:substring($trabajadoresImssInfonavit1/ns0:tipPatPerFis,1,1)) }</tipPatPerFis>
            	<nss>{ data(fn:substring($trabajadoresImssInfonavit1/ns0:nss,1,11)) }</nss>            	
            	<curp>{ data(fn:substring(xf:clean_chars($trabajadoresImssInfonavit1/ns0:curp),1,18)) }</curp>
            	<rfc>{ data(fn:substring(xf:clean_chars($trabajadoresImssInfonavit1/ns0:rfc),1,13)) }</rfc>       
                <marcaBeneficio>{ data($trabajadoresImssInfonavit1/ns0:marcaBeneficio) }</marcaBeneficio>                
            </TrabajadoresInfonavit>
        </TrabajadoresImssInfonavit>
};

declare variable $trabajadoresImssInfonavit1 as element(ns0:TrabajadoresImssInfonavit) external;

xf:TrabajadoresImssInfonavitXML_mfl($trabajadoresImssInfonavit1)