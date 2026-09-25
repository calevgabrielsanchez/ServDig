$(document).ready(function() {
    resolveCountry = function (callback) {
        var countryInfo = {};
        //1;CD;COD;COUNTRY
        $.get( "https://ip2c.org/s", function(data) {
            if (data) {
                var elems	=	data.split(";");
                if (elems.length != 4) {
                    countryInfo.status = elems[0];
                    countryInfo.cd = "NA";
                    countryInfo.cod = "NA";
                    countryInfo.country = "NA";
                } else {
                    countryInfo.status = elems[0];
                    countryInfo.cd = elems[1];
                    countryInfo.cod = elems[2];
                    countryInfo.country = elems[3];
                }
            }
        })
            .done(function() {
                console.log("---> COUNTRY SUCCESS = " + JSON.stringify(countryInfo));
                callback(countryInfo);
            })
            .fail(function() {
                countryInfo.status = "2";
                countryInfo.cd = "NA";
                countryInfo.cod = "NA";
                countryInfo.country = "NA";
                console.log("---> COUNTRY FAIL = " + JSON.stringify(countryInfo));
                callback(countryInfo)
            });

        var nomPais = JSON.stringify(countryInfo);
        return nomPais;
    };
    var insertCountry = function(o) {
        if (o) {

            var idSolicitud = $('#idSolicitud').val();
            var solicitudJSON = {
                cveIdSolicitud : idSolicitud,
                cvePaisOrigen : o.cod
            };

            console.log("solicitudJson: "+solicitudJSON);
            $.ajax({
                url: "${mvn.url.static.resource.path.country}/resources/sime_origen_solicitudes",
                type: "POST",
                crossDomain: true,
                data: JSON.stringify(solicitudJSON),
                contentType : "application/json; charset=UTF-8",
                dataType : "json",
                success:function(result){
                    console.log(JSON.stringify(result));
                },
                error:function(xhr,status,error){
                    console.log(status);
                }
            });

        }
    };
    resolveCountry(insertCountry);
});