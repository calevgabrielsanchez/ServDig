<%@ include file="../general/taglibs.jsp"%>

<script>
	var firmaDigitalParams = ${firmaDigitalParams};
	
	$(document).ready(function() {
		// Condiciones iniciales
		$("#btnIniciarFirma").attr("disabled", true);

		$("#chkTerminos").click(function() {
			if ($(this).is(':checked')) {
				$("#btnIniciarFirma").removeAttr("disabled");
			} else {
				$("#btnIniciarFirma").attr("disabled", true);
			}
		});

		$("#btnCancelarFirma").click(function() {
			parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			});

			parent.FirmaDigitalCtrl.cerrar();
		});

		// Transformar los datos a json
		var json =JSON.stringify(firmaDigitalParams);
		$('#params').val(json);

		// Colocar action al formulario de Firma
		var urlAction = parent.FirmaDigitalCtrl.urlFormularioFirma;
		document.getElementById('firmarForm').action = urlAction;
	});
</script>

<style>
.site_position_center_fixed {
    width: 930px;
}

.contenedor .pie .opciones {
	float: left;
}

.contenedor .pie .controles {
	float: right;
}

.ui-button-primary.disabled, .ui-button-primary[disabled] {
    box-shadow: none;
    cursor: default;
    opacity: 0.35;
}
</style>

<div class="contenedor">
	<div class="contenido" style="width: 100%;">
		<fieldset>
			<legend>
				<strong>&nbsp;Firma Digital con FIEL&nbsp;</strong>
			</legend>

			<!-- Terminos y condiciones -->
			<p>
							<strong>CARTA DE T&Eacute;RMINOS Y CONDICIONES PARA UTILIZAR LA FIRMA ELECTR&Oacute;NICA AVANZADA DE UN REPRESENTANTE LEGAL,
							 PARA PRESENTAR ACTOS ANTE EL IMSS EN REPRESENTACI&Oacute;N DE UNA PERSONA F&Iacute;SICA O MORAL.</strong>
						</p>


						<p>
							"Con fundamento en las "Reglas de car&aacute;cter general para el uso de la firma electr&oacute;nica avanzada, cuyo certificado digital
							 sea emitido por el Servicio de Administraci&oacute;n Tributaria, en los actos que se realicen ante el Instituto Mexicano del 
							 Seguro Social", publicadas en el Diario Oficial de la Federaci&oacute;n el 14 de Noviembre de 2013, los particulares, ya sean 
							 personas f&iacute;sicas o morales, podr&aacute;n optar por realizar los actos que señala la Ley del Seguro Social, sus Reglamentos y 
							 dem&aacute;s disposiciones que de ella emanen, de manera electr&oacute;nica y firm&aacute;ndola directamente con su firma electr&oacute;nica avanzada
							  (FIEL) o la de su representante legal, cuyo certificado digital est&eacute; vigente y haya sido emitido por el Servicio de 
							  Administraci&oacute;n Tributaria (SAT), siempre que el Instituto Mexicano del Seguro Social (IMSS) ponga a disposici&oacute;n de los 
							  interesados las herramientas tecnol&oacute;gicas necesarias para ello."
						</p>
						
						<p>La FIEL sustituye la firma aut&oacute;grafa del firmante y producir&aacute; los mismos efectos que las leyes otorgan a los documentos 
						con firma aut&oacute;grafa, teniendo el mismo valor probatorio. Asimismo, con el uso de la FIEL se tiene por reconocida la garant&iacute;a 
						de la autor&iacute;a del firmante y de la integridad de los documentos electr&oacute;nicos que se firmen con ella y, por ende, el contenido
						 de los mismos no podr&aacute; desconocerse ni admitir&aacute; prueba en contrario.
						</p>

						<p>
							La expedici&oacute;n de los certificados digitales y la generaci&oacute;n de las claves p&uacute;blicas y privadas que conforman la FIEL de 
							personas f&iacute;sicas y morales, se regir&aacute;n por el C&oacute;digo Fiscal de la Federaci&oacute;n, su reglamentaci&oacute;n secundaria en la materia y, 
							en su caso, por la Ley de la Firma Electr&oacute;nica Avanzada. Por lo tanto, la expedici&oacute;n de los certificados digitales, su 
							renovaci&oacute;n, revocaci&oacute;n y dem&aacute;s tr&aacute;mites relacionados con la FIEL, se deber&aacute;n realizar ante el SAT, cumpliendo con los 
							requisitos y procedimientos establecidos en la normatividad aplicable.
						</p>
						
						<p>
							En caso de p&eacute;rdida, robo o destrucci&oacute;n de la FIEL, o cualquier otro evento que ponga en riesgo la confidencialidad de los 
							certificados electr&oacute;nicos, las llaves o claves que conforman la FIEL, la persona f&iacute;sica o moral, bajo su absoluta responsabilidad,
							 deber&aacute; proceder con su inmediata revocaci&oacute;n o reposici&oacute;n ante el SAT, sujet&aacute;ndose a los procesos y lineamientos que el mismo 
							 determine.
						</p>
						
						<p>
							Los particulares, ya sean personas f&iacute;sicas o morales, que opten por realizar actos ante el IMSS a trav&eacute;s de la FIEL de sus representantes
							legales, deber&aacute;n firmar mancomunadamente, con sus respectivas FIEL y en momentos sucesivos inmediatos, el presente documento, manifestando, 
							bajo protesta de decir verdad, lo siguiente:
						</p>
						
						<ol>
							<li>
							Reconocen que es de su exclusiva responsabilidad el resguardo del certificado digital y la confidencialidad de la clave privada que conforman
							 sus respectivas FIEL, con el fin de evitar la utilizaci&oacute;n no autorizada de las mismas. Por lo tanto, mediante la firma del presente documento
							  con la FIEL, se tendr&aacute; por v&aacute;lido y sin que se admita prueba en contrario, el v&iacute;nculo entre cada firmante, sea persona moral o f&iacute;sica, y 
							  los datos que fueron utilizados para la creaci&oacute;n de la respectiva FIEL; por lo cual, la aceptaci&oacute;n del presente documento ser&aacute;n imputables
							   a los titulares de los certificados digitales que se hayan utilizado.
							</li>
							
							<li>
								El firmado del presente documento con la FIEL ser&aacute; considerado hecho leg&iacute;tima y aut&eacute;nticamente por los firmantes y, en caso de personas morales,
								 por el administrador &uacute;nico, el Presidente del Consejo de Administraci&oacute;n o la persona o personas, cualquiera que sea el nombre con el que se
								  les designe, que tengan conferida la direcci&oacute;n general, la gerencia general o la administraci&oacute;n de la persona moral de que se trate, en el
								   momento en el que se presentaron los documentos digitales. 
							</li>
							
							<li>
								Lo anterior no admitir&aacute; prueba en contrario ante el IMSS y el titular del certificado digital ser&aacute; responsable de las consecuencias jur&iacute;dicas
								 que derive de la aceptaci&oacute;n del presente documento.
							</li>
							
							<li>
								La persona f&iacute;sica o moral que sea titular del certificado digital que se utilice para firmar el presente documento en car&aacute;cter de representado,
								tendr&aacute; dicho car&aacute;cter ante el IMSS y, en este acto, autoriza a realizar actos por su nombre y cuenta ante el IMSS, a la persona f&iacute;sica que es
								titular del certificado digital que se utiliza para firmar este documento en car&aacute;cter de representante legal, quien efectivamente detenta esa
								  representaci&oacute;n, contando con poder suficiente y debidamente otorgado conforme a la legislaci&oacute;n civil, para realizar actos ante el IMSS, seg&uacute;n
								  lo requiere la legislaci&oacute;n y normativa aplicable. 
							</li>
							
							<li>
								La persona f&iacute;sica que es titular del certificado digital que se utiliza para firmar este documento en car&aacute;cter de representante legal, tendr&aacute; dicho car&aacute;cter ante el IMSS y, en este acto, acepta realizar mediante el uso de su propia FIEL, actos por cuenta y nombre de su representado ante el IMSS en tanto no sean revocadas sus facultades de representaci&oacute;n.
							</li>
							
							<li>
								Los actos que firme el representante legal con su FIEL, a nombre y cuenta del representado, ser&aacute;n considerados hechos leg&iacute;tima y aut&eacute;nticamente por el firmante, siendo el representado el &uacute;nico responsable ante el IMSS de las consecuencias jur&iacute;dicas que deriven de los actos que haya realizado el representante legal. Lo anterior, no admitir&aacute; prueba en contrario ante el IMSS, sin perjuicio de las acciones civiles o penales que pueda seguir el representado en contra de su representante legal por cualquier acto u omisi&oacute;n indebido en el cumplimiento de su mandato.
							</li>
							
							<li>
								Los particulares, por s&iacute; mismos o a trav&eacute;s de sus representantes legales, podr&aacute;n en cualquier momento, dejar sin efectos la autorizaci&oacute;n del uso de la FIEL de sus representantes legales para realizar actos ante el IMSS por cuenta y nombre de ellos; para lo cual, firmar&aacute;n con su respectiva FIEL la baja correspondiente que el Instituto ponga a su disposici&oacute;n para tal efecto.
							</li>
							
							<li>
								El representado en este acto expresamente ratifica todos los actos que por su nombre y cuenta, realice ante el Instituto su representante legal mediante el uso de la FIEL, en tanto no se le revoque la autorizaci&oacute;n correspondiente ante el IMSS, en los t&eacute;rminos establecidos en el presente documento. Por lo anterior, queda bajo la absoluta responsabilidad del representado dejar sin efectos la autorizaci&oacute;n del uso de la FIEL de sus representantes legales para realizar actos ante el IMSS por cuenta y nombre de &eacute;l, cuando los poderes de los mismos hayan sido revocados o cuando as&iacute; convenga a sus intereses.
							</li>
							
							<li>
								La falta de vigencia o revocaci&oacute;n del certificado digital que ampara la FIEL del representante legal, no eximir&aacute; a la persona f&iacute;sica o moral de cumplir con sus obligaciones ante el IMSS; por lo cual, ser&aacute; su responsabilidad realizar las gestiones necesarias para cumplir cabalmente con las mismas, de conformidad con la Ley del Seguro Social, sus Reglamentos y dem&aacute;s disposiciones aplicables.
							</li>
						</ol>
						
						<p>
							Los firmantes del presente documento declaran, bajo protesta de decir verdad, que las manifestaciones anteriores son ciertas, y que conocen de las penas en que incurren quienes declaran falsamente ante una autoridad distinta de la judicial, y que son sabedores de las dem&aacute;s responsabilidades civiles y administrativas que se pudieran derivar por ello.
						</p>

			<br/>
		</fieldset>
		<br/>
		<input type="checkbox" id="chkTerminos" >
		Declaramos que hemos le&iacute;do y conocemos los t&eacute;rminos y condiciones, as&iacute; como las "Reglas de car&aacute;cter general para el uso de la Firma Electr&oacute;nica
		 Avanzada, cuyo certificado digital sea emitido por el Servicio de Administraci&oacute;n Tributaria, en los actos que se realicen ante el Instituto 
		 Mexicano del Seguro Social", y que voluntariamente aceptamos los alcances legales de los mismos, mediante nuestras firmas electr&oacute;nicas FIEL. 
		<br /><br />
	</div>
	<div class="pie">
		<div class="opciones">
		</div>
		<div class="controles">
		<form method="post" target="formFirmaDigital" id="firmarForm">
			<input type="hidden" id="params" name="params" readonly="readonly" />
			<input type="submit" id="btnIniciarFirma" class="ui-button-primary ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" value="Aceptar">
			<input type="button" id="btnCancelarFirma" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" value="Cancelar"/>
		</form>
		</div>
	</div>
</div>
