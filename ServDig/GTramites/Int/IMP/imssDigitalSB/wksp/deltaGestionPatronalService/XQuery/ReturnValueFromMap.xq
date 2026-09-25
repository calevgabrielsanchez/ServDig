xquery version "1.0" encoding "Cp1252";
(:: pragma bea:local-element-parameter parameter="$map" type="ns0:EmailPayload/ns0:Parameters" location="../schema/ServiceContract/EmailRequestSchema.xsd" ::)

declare namespace xf = "http://tempuri.org/deltaGestionPatronalService/XQuery/ReturnValueFromMap/";
declare namespace ns0 = "http://mx.gob.imss.email.model";


declare function xf:ReturnValueFromMap($map as element(), $keyName as xs:string) as xs:string {
        let $defaultValue := ""
        let $value := for $entry in $map/ns0:entry 
        		where(some $keyMap in $entry/ns0:key satisfies $keyMap/text()=$keyName)
        			return $entry/ns0:value/text()
        
        
        return if (exists($value))
        	then
            	      $value
        	else
        	      $defaultValue
       
}; 


declare variable $map as element() external;
declare variable $keyName as xs:string external;

xf:ReturnValueFromMap($map,
    $keyName)