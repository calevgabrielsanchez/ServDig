<html lang="es">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<!-- jQuery -->
<script type="text/javascript" src="http://vanderluk.lan/resources/js/jquery/jquery.js"></script>
<!-- JSON -->
<script type="text/javascript" src="http://vanderluk.lan/resources/js/json/json2.js"></script>
<script type="text/javascript" src="http://vanderluk.lan/resources/js/json/json.min.js"></script>

<!-- Cometd -->
<script type="text/javascript" src="http://vanderluk.lan/resources/js/org/cometd.js"></script>
<script type="text/javascript" src="http://vanderluk.lan/resources/js/org/cometd/AckExtension.js"></script>
<script type="text/javascript" src="http://vanderluk.lan/resources/js/org/cometd/ReloadExtension.js"></script>
<script type="text/javascript" src="http://vanderluk.lan/resources/js/org/cometd/TimeStampExtension.js"></script>
<script type="text/javascript" src="http://vanderluk.lan/resources/js/org/cometd/TimeSyncExtension.js"></script>

<!-- Cometd and Jquery-->

<script type="text/javascript" src="http://vanderluk.lan/resources/js/jquery/comet/jquery.cookie.js"></script>
<script type="text/javascript" src="http://vanderluk.lan/resources/js/jquery/comet/jquery.cometd.js"></script>
<script type="text/javascript" src="http://vanderluk.lan/resources/js/jquery/comet/jquery.cometd-ack.js"></script>
<script type="text/javascript" src="http://vanderluk.lan/resources/js/jquery/comet/jquery.cometd-reload.js"></script>
<script type="text/javascript" src="http://vanderluk.lan/resources/js/jquery/comet/jquery.cometd-timestamp.js"></script>
<script type="text/javascript" src="http://vanderluk.lan/resources/js/jquery/comet/jquery.cometd-timesync.js"></script>

<!-- JGrowl -->
<script type="text/javascript" src="http://vanderluk.lan/resources/js/jquery/comet/jquery.jgrowl.js"></script>



<script>
	
	
var _initialConfiguration = {
		url : 'http://localhost:7001/portal-comet-web/cometd',
		logLevel : 'info'
	};
	
	
	
	



  // when the server has lost the state of this client
    function _metaHandshake(handshake)
    {
        if (handshake.successful === true)
        {
	//		alert("subscribiendo ...");
	//	    $.cometd.subscribe("/hello/lucio",_refresh);
	
			$.cometd.batch(function(){
				//_initialSubscriber();		
			});
	
        }
    }

function _initialSubscriber(){
					
										$.cometd.subscribe("/comet/delta/publicar/modificacion/portlets",_refresh);
					$.cometd.publish(  '/comet/delta/publicar/modificacion/portlets', {
					systemMessage : 'Comet Service is initialized'
					});
	
};


function _metaConnect(message) {

}
	
function  _refresh( message){
	alert("refrescando .." + message);
}	
	
	
$.cometd.websocketEnabled = false;	

$(window).unload(function() {
	$.cometd.disconnect(true);
});

$.cometd.configure(_initialConfiguration);

$.cometd.addListener('/meta/handshake', _metaHandshake);
// $.cometd.addListener('/meta/connect', _metaConnect);



$.cometd.handshake();





$('#boton').live('click' , function(){
	
	alert('publicando...');
		$.cometd.publish( '/comet/delta/publicar/modificacion/portlets', {"data": "content"});	
});



$('#subscribe').live('click' , function(){
	
	alert('subscribiendo...');
	$.cometd.subscribe("/comet/delta/publicar/modificacion/portlets",_refresh);
});
</script>

</head>


<body>
<h2>Hello World!</h2>

<button id="boton"> Enviar mensaje ...</button>
<button id="subscribe"> subscribirse  ...</button>


</body>
</html>
