xquery version "1.0" encoding "Cp1252";
(:: pragma bea:global-element-parameter parameter="$movimientoIdse1" element="ns0:MovimientoIdse" location="../../Movimiento06SINDOProject/schemas/MovimientoIdse.xsd" ::)
(:: pragma bea:mfl-element-return type="MovimientoIdse@" location="../../Movimiento06SINDOProject/mfl/MovimientoIdse.mfl" ::)

declare namespace xf = "http://tempuri.org/Movimiento06SINDOProject/xquery/MovimientoIDS_XML_MFL/";
declare namespace ns0 = "http://www.mx.gob.imss.ctirss.delta.idse/idse_schema";

declare function xf:MovimientoIDS_XML_MFL($movimientoIdse1 as element(ns0:MovimientoIdse))
    as element() {
        <MovimientoIdse>
            <IdseReingresoBaja>
                <registroPatronal>{ data($movimientoIdse1/ns0:registroPatronal) }</registroPatronal>
                <digitoVerificadorRP>{ data($movimientoIdse1/ns0:digitoVerificadorRP) }</digitoVerificadorRP>
                <nSS>{ data($movimientoIdse1/ns0:nSS) }</nSS>
                <digitoVerificadorNSS>{ data($movimientoIdse1/ns0:digitoVerificadorNSS) }</digitoVerificadorNSS>
                <primerApellido>{ data($movimientoIdse1/ns0:primerApellido) }</primerApellido>
                <segundoApellido>{ data($movimientoIdse1/ns0:segundoApellido) }</segundoApellido>
                <nombreDelEstudiante>{ data($movimientoIdse1/ns0:nombreDelEstudiante) }</nombreDelEstudiante>
                <salarioDiarioIntegrado>{ data($movimientoIdse1/ns0:salarioDiarioIntegrado) }</salarioDiarioIntegrado>
                <campoEnBlanco>{ data($movimientoIdse1/ns0:campoEnBlanco) }</campoEnBlanco>
                <tipoDeTrabajador>{ data($movimientoIdse1/ns0:tipoDeTrabajador) }</tipoDeTrabajador>
                <tipoDeSalario>{ data($movimientoIdse1/ns0:tipoDeSalario) }</tipoDeSalario>
                <semanaOJornadaReducida>{ data($movimientoIdse1/ns0:semanaOJornadaReducida) }</semanaOJornadaReducida>
                <fechaDelMovimiento>{ data($movimientoIdse1/ns0:fechaDelMovimiento) }</fechaDelMovimiento>
                <unidadDeMedicinaFamiliar>{ data($movimientoIdse1/ns0:unidadDeMedicinaFamiliar) }</unidadDeMedicinaFamiliar>
                <campoEnBlanco2>{ data($movimientoIdse1/ns0:campoEnBlanco2) }</campoEnBlanco2>
                <tipoDeMovimiento>{ data($movimientoIdse1/ns0:tipoDeMovimiento) }</tipoDeMovimiento>
                <campoEnBlanco3>{ data($movimientoIdse1/ns0:campoEnBlanco3) }</campoEnBlanco3>
                <claveTrabajador>{ data($movimientoIdse1/ns0:claveTrabajador) }</claveTrabajador>
                <causaDebaja>{ data($movimientoIdse1/ns0:causaDebaja) }</causaDebaja>
                <curp_o_Rfc>{ data($movimientoIdse1/ns0:curp_o_Rfc) }</curp_o_Rfc>
                <idFormato>{ data($movimientoIdse1/ns0:idFormato) }</idFormato>
            </IdseReingresoBaja>
        </MovimientoIdse>
};

declare variable $movimientoIdse1 as element(ns0:MovimientoIdse) external;

xf:MovimientoIDS_XML_MFL($movimientoIdse1)