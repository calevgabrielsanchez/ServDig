declare namespace xf = "http://tempuri.org/deltaGestionPatronalService/ReemplazarAcentosXQT/";

declare function xf:clean_chars (
    $dirty_string as xs:string?) as xs:string {
  fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(fn:replace(
                                fn:upper-case($dirty_string), "[ñÑ]",
                                "#"),"Á", "A"),"É","E"),"Í","I"),"Ó","O"),"[ÚÜü]","U"), '\r?\n', ' ', 's'), '\s\s*', ' ')
};


declare function xf:ReemplazarAcentosXQT($dirtyData as xs:string)
    as xs:string {
        xf:clean_chars($dirtyData)
	};

declare variable $dirtyData as xs:string external;

xf:ReemplazarAcentosXQT($dirtyData)