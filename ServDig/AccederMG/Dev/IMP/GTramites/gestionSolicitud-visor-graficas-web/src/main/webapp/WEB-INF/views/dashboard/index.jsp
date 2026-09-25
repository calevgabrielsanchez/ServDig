<%@ include file="../general/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="es">

<head>
	<meta charset="iso-8859-1">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black-translucent" names="apple-mobile-web-app-status-bar-style">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	
	<title>Dashboard IMSS Digital</title>
	
	<link rel="apple-touch-icon"
		href="<spring:url value="/static/resources/imagenes/icon/ios7/Icon-60.png" htmlEscape="true" />" />
	<link rel="apple-touch-icon" sizes="76x76"
		href="<spring:url value="/static/resources/imagenes/icon/ios7/Icon-76.png" htmlEscape="true" />" />
	<link rel="apple-touch-icon" sizes="120x120"
		href="<spring:url value="/static/resources/imagenes/icon/ios7/Icon-60@2x.png" htmlEscape="true" />" />
	<link rel="apple-touch-icon" sizes="152x152"
		href="<spring:url value="/static/resources/imagenes/icon/ios7/Icon-76@2x.png" htmlEscape="true" />" />
	<link rel="apple-touch-icon-precomposed"
		href="<spring:url value="/static/resources/imagenes/icon/android/drawable-xxhdpi/ic_launcher.png" htmlEscape="true" />" />
	
	<link href="<spring:url value="/static/resources/dashboard/css/bootstrap.min.css" htmlEscape="true" />" rel="stylesheet">
	<link href="<spring:url value="/static/resources/dashboard/font-awesome/css/font-awesome.css" htmlEscape="true" />"
		rel="stylesheet">
	<link href="<spring:url value="/static/resources/dashboard/js/plugins/chartJs/legend/css/demo.css" htmlEscape="true" />" rel="stylesheet">

	<link href="<spring:url value="/static/resources/dashboard/css/plugins/datapicker/datepicker3.css" htmlEscape="true" />" rel="stylesheet">
	
	<link href="<spring:url value="/static/resources/dashboard/css/animate.css" htmlEscape="true" />" rel="stylesheet">
	<link href="<spring:url value="/static/resources/dashboard/css/style.css" htmlEscape="true" />" rel="stylesheet">
	
	<link href="<spring:url value="/static/resources/dashboard/datatables/css/dataTables.bootstrap.css" htmlEscape="true" />" rel="stylesheet">
	<link href="<spring:url value="/static/resources/dashboard/datatables/css/dataTables.responsive.css" htmlEscape="true" />" rel="stylesheet">
	
	<script src="<spring:url value="/static/resources/dashboard/js/plugins/modernizr/modernizr.js" htmlEscape="true" />"></script>
</head>

<body class="top-navigation fixed-nav">
	<div id="wrapper">
		<div id="page-wrapper" class="gray-bg">
			<div class="row border-bottom">
				<nav style="margin-bottom: 0" role="navigation" class="navbar navbar-fixed-top">
					<div class="navbar-header" style="display: inline; float: left;">
						<a href="#" class="navbar-brand">IMSS Digital</a>
					</div>
					<ul class="nav navbar-top-links navbar-right">
						<li>
							<a href="#" class="refresh-global">
								<i class="fa fa-refresh"></i>
							</a>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
								<i class="fa fa-calendar"></i>
								<span id="periodo-global" class="label label-info" style="font-size: 15px;">HOY</span>
							</a>
							<ul class="dropdown-menu dropdown-periodo-global" role="menu">
								<li>
									<a href="#" periodo="1">HOY</a>
								</li>
								<li>
									<a href="#" periodo="2">SEMANA ACTUAL</a>
								</li>
								<li>
									<a href="#" periodo="3">MES ACTUAL</a>
								</li>
								<li>
									<a href="#" periodo="4">AÑO ACTUAL</a>
								</li>
								<li>
									<a href="#" periodo="5">PERSONALIZADO</a>
								</li>
							</ul>
						</li>
					</ul>
					<div class="row" id="rangoFechasContainer" style="display: none;">
						<div class="col-lg-4 col-lg-offset-4  col-md-5 col-md-offset-3 
								col-sm-6 col-sm-offset-1 col-xs-12">
							<div class="form-group" id="data_5">
								<label class="font-noraml">Rango de fechas</label>
								<div class="input-daterange input-group" id="datepicker">
									<div class="input-group date">
										<span class="input-group-addon white-read-only">
											<i class="fa fa-calendar"></i>
										</span>
										<input type="text" class="input-sm form-control white-read-only" id="fechaInicio" readonly="readonly" />
									</div>
									<span class="input-group-addon">a</span>
									<div class="input-group date">
										<span class="input-group-addon white-read-only">
											<i class="fa fa-calendar"></i>
										</span>
										<input type="text" class="input-sm form-control white-read-only" id="fechaFin" readonly="readonly" />
									</div>
								</div>
								<button type="button" id="btnRangoFechas" 
									class="btn btn-primary btn-sm" style="margin-top: 5px;"
									periodo="5">CONSULTAR</button>
							</div>
						</div>
					</div>
				</nav>
			</div>

			<div class="row  border-bottom white-bg dashboard-header panel-dashboard panel-gran-total">
				<div class="row">
					<div class="col-xs-8">
						<h2>Total hist&oacute;rico</h2>
					</div>
					<div class="col-xs-4">
						<div class="ibox-tools">
							<a href="#" class="refresh-contadores-tramite">
								<i class="fa fa-refresh"></i>
							</a>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 col-sm-12">
						<div class="widget navy-bg no-padding">
							<div class="p-m">
								<h1 class="m-xs m-l-none" id="granTotal">-</h1>
								<h3 class="font-bold no-margins">Operaciones atendidas</h3>
								<small>Gran total de operaciones atendidas en IMSS Digital.</small>
							</div>
							<div class="p-m" id="granTotalOrigenes">
								<div class="progress"></div>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-sm-12">
						<small>Top 5 de operaciones atendidas</small>
						<ul id="top5TramitesGlobal" class="list-group clear-list m-t">
							
						</ul>
					</div>
				</div>				
				<div class="row">
					<div class="col-sm-2" style="text-align: left;">
						<img class="loading" width="15" height="15" alt="" src="${staticResourcesPath}/imagenes/loading.gif" style="display: none;">
					</div>
					<div class="col-sm-10">
						<div class="pull-right">
							<small>
								<i class="fa fa-clock-o"> </i>
								Actualizado
								<label class="updatedOn">-</label>
							</small>
						</div>
					</div>
				</div>
			</div>

			<div class="wrapper-content">
				<div class="container-fluid">					
					<div class="row">
						<div class="col-lg-3 col-md-6 col-sm-12">
							<div id="asignacionNss" class="panel panel-default panel-dashboard panel-contadores" idTramite="43|85" periodo="">
								<div class="panel-heading">
									<div class="row">
										<div class="col-sm-9 col-xs-8">
											<h4 class="no-margins">N&uacute;mero de Seguridad Social</h4>
										</div>
										<div class="col-sm-3 col-xs-4">
											<div class="ibox-tools">
												<a href="#" class="refresh-contadores-tramite">
													<i class="fa fa-refresh"></i>
												</a>
												<a class="dropdown-toggle" data-toggle="dropdown" href="#">
													<i class="fa fa-calendar"></i>
												</a>
												<ul class="dropdown-menu dropdown-periodo">
													<li>
														<a href="#" periodo="1">HOY</a>
													</li>
													<li>
														<a href="#" periodo="2">SEMANA ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="3">MES ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="4">AÑO ACTUAL</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="row">
										<div class="col-sm-12 pull-right">
											<span class="label label-info pull-right label-periodo">-</span>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<h1 class="no-margins cifra">-</h1>
										</div>

									</div>
									<div class="row">
										<div class="col-xs-12">
											<small>Operaciones con NSS</small>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<div class="stat-percent font-bold text-muted">
												<label>-%</label>
												<i class="fa fa-square-o hidden-xs"></i>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-body -->
								<div class="panel-footer">
									<div class="row">
										<div class="col-sm-2" style="text-align: left;">
											<img class="loading" width="15" height="15" alt="" src="${staticResourcesPath}/imagenes/loading.gif" style="display: none;">
										</div>
										<div class="col-sm-10">
											<small>
												<i class="fa fa-clock-o"> </i>
												Actualizado
												<label class="updatedOn"></label>
											</small>
										</div>
									</div>
								</div>
								<!-- /.panel-footer -->
							</div>
							<!-- /.panel -->
						</div>
						<div class="col-lg-3 col-md-6 col-sm-12">
							<div id="altasPatrones" class="panel panel-default panel-dashboard panel-contadores" idTramite="1|120" periodo="">
								<div class="panel-heading">
									<div class="row">
										<div class="col-sm-9 col-xs-8">
											<h4 class="no-margins">Altas Patronales</h4>
										</div>
										<div class="col-sm-3 col-xs-4">
											<div class="ibox-tools">
												<a href="#" class="refresh-contadores-tramite">
													<i class="fa fa-refresh"></i>
												</a>
												<a class="dropdown-toggle" data-toggle="dropdown" href="#">
													<i class="fa fa-calendar"></i>
												</a>
												<ul class="dropdown-menu dropdown-periodo">
													<li>
														<a href="#" periodo="1">HOY</a>
													</li>
													<li>
														<a href="#" periodo="2">SEMANA ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="3">MES ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="4">AÑO ACTUAL</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="row">
										<div class="col-sm-12 pull-right">
											<span class="label label-info pull-right label-periodo">-</span>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<h1 class="no-margins cifra">-</h1>
										</div>

									</div>
									<div class="row">
										<div class="col-xs-12">
											<small>Nuevos patrones</small>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<div class="stat-percent font-bold text-muted">
												<label>-%</label>
												<i class="fa fa-square-o hidden-xs"></i>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-body -->
								<div class="panel-footer">
									<div class="row">
										<div class="col-sm-2" style="text-align: left;">
											<img class="loading" width="15" height="15" alt="" src="${staticResourcesPath}/imagenes/loading.gif" style="display: none;">
										</div>
										<div class="col-sm-10">
											<small>
												<i class="fa fa-clock-o"> </i>
												Actualizado
												<label class="updatedOn"></label>
											</small>
										</div>
									</div>
								</div>
								<!-- /.panel-footer -->
							</div>
							<!-- /.panel -->
						</div>
						<div class="col-lg-3 col-md-6 col-sm-12">
							<div id="registrosDH" class="panel panel-default panel-dashboard panel-contadores" idTramite="44|45|46|47|48|49" periodo="">
								<div class="panel-heading">
									<div class="row">
										<div class="col-sm-9 col-xs-8">
											<h4 class="no-margins">Registro de Derechohabientes en Cl&iacute;nica</h4>
										</div>
										<div class="col-sm-3 col-xs-4">
											<div class="ibox-tools">
												<a href="#" class="refresh-contadores-tramite">
													<i class="fa fa-refresh"></i>
												</a>
												<a class="dropdown-toggle" data-toggle="dropdown" href="#">
													<i class="fa fa-calendar"></i>
												</a>
												<ul class="dropdown-menu dropdown-periodo">
													<li>
														<a href="#" periodo="1">HOY</a>
													</li>
													<li>
														<a href="#" periodo="2">SEMANA ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="3">MES ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="4">AÑO ACTUAL</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="row">
										<div class="col-sm-12 pull-right">
											<span class="label label-info pull-right label-periodo">-</span>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<h1 class="no-margins cifra">-</h1>
										</div>

									</div>
									<div class="row">
										<div class="col-xs-12">
											<small>Registros de derechohabientes en cl&iacute;nica</small>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<div class="stat-percent font-bold text-muted">
												<label>-%</label>
												<i class="fa fa-square-o hidden-xs"></i>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-body -->
								<div class="panel-footer">
									<div class="row">
										<div class="col-sm-2" style="text-align: left;">
											<img class="loading" width="15" height="15" alt="" src="${staticResourcesPath}/imagenes/loading.gif" style="display: none;">
										</div>
										<div class="col-sm-10">
											<small>
												<i class="fa fa-clock-o"> </i>
												Actualizado
												<label class="updatedOn"></label>
											</small>
										</div>
									</div>
								</div>
								<!-- /.panel-footer -->
							</div>
							<!-- /.panel -->
						</div>
						<div class="col-lg-3 col-md-6 col-sm-12">
							<div id="bajasDH" class="panel panel-default panel-dashboard panel-contadores" idTramite="25|26|27|28" periodo="">
								<div class="panel-heading">
									<div class="row">
										<div class="col-sm-9 col-xs-8">
											<h4 class="no-margins">Baja de Derechohabientes en Cl&iacute;nica</h4>
										</div>
										<div class="col-sm-3 col-xs-4">
											<div class="ibox-tools">
												<a href="#" class="refresh-contadores-tramite">
													<i class="fa fa-refresh"></i>
												</a>
												<a class="dropdown-toggle" data-toggle="dropdown" href="#">
													<i class="fa fa-calendar"></i>
												</a>
												<ul class="dropdown-menu dropdown-periodo">
													<li>
														<a href="#" periodo="1">HOY</a>
													</li>
													<li>
														<a href="#" periodo="2">SEMANA ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="3">MES ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="4">AÑO ACTUAL</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="row">
										<div class="col-sm-12 pull-right">
											<span class="label label-info pull-right label-periodo">-</span>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<h1 class="no-margins cifra">-</h1>
										</div>

									</div>
									<div class="row">
										<div class="col-xs-12">
											<small>Bajas de derechohabientes en cl&iacute;nica</small>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12">
											<div class="stat-percent font-bold text-muted">
												<label>-%</label>
												<i class="fa fa-square-o hidden-xs"></i>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-body -->
								<div class="panel-footer">
									<div class="row">
										<div class="col-sm-2" style="text-align: left;">
											<img class="loading" width="15" height="15" alt="" src="${staticResourcesPath}/imagenes/loading.gif" style="display: none;">
										</div>
										<div class="col-sm-10">
											<small>
												<i class="fa fa-clock-o"> </i>
												Actualizado
												<label class="updatedOn"></label>
											</small>
										</div>
									</div>
								</div>
								<!-- /.panel-footer -->
							</div>
							<!-- /.panel -->
						</div>
					</div>

					<div class="row">
						<div class="col-lg-8">
							<div class="panel panel-default panel-dashboard panel-cifras-origen" periodo="">
								<div class="panel-heading">
									<div class="row">
										<div class="col-sm-9 col-xs-8">
											<h4 class="no-margins">Operaciones recibidas por Origen</h4>
										</div>
										<div class="col-sm-3 col-xs-4">
											<div class="ibox-tools">
												<a href="#" class="refresh-contadores-tramite">
													<i class="fa fa-refresh"></i>
												</a>
												<a class="dropdown-toggle" data-toggle="dropdown" href="#">
													<i class="fa fa-calendar"></i>
												</a>
												<ul class="dropdown-menu dropdown-periodo">
													<li>
														<a href="#" periodo="1">HOY</a>
													</li>
													<li>
														<a href="#" periodo="2">SEMANA ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="3">MES ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="4">AÑO ACTUAL</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-12 pull-right">
											<span class="label label-info pull-right label-periodo">-</span>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="row" id="amChartPorOrigenWrapper">
												<div class="col-lg-12">
													<div id="amChartPorOrigen" style="width: 100%; height: 570px; background-color: #FFFFFF;"></div>
												</div>
											</div>
																						
											<div class="row m-t-sm" id="amChartPorOrigenHoyWrapper">
												<div class="col-sm-12">
													<div id="amChartPorOrigenHoy" style="width: 100%; height: 450px; background-color: #FFFFFF;"></div>
												</div>
												<div class="col-sm-12 custom-legend" id="amChartPorOrigenHoyLegend">
												</div>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-body -->
								<div class="panel-footer">
									<div class="row">
										<div class="col-sm-2" style="text-align: left;">
											<img class="loading" width="15" height="15" alt="" src="${staticResourcesPath}/imagenes/loading.gif" style="display: none;">
										</div>
										<div class="col-sm-10">
											<small>
												<i class="fa fa-clock-o"> </i>
												Actualizado
												<label class="updatedOn"></label>
											</small>
										</div>
									</div>
								</div>
								<!-- /.panel-footer -->
							</div>
							<!-- /.panel -->
						</div>
						<div class="col-lg-4">
							<div class="panel panel-default panel-dashboard panel-ranking-tramites" periodo="">
								<div class="panel-heading">
									<div class="row">
										<div class="col-sm-9 col-xs-8">
											<h4 class="no-margins">Top 5 de Operaciones</h4>
										</div>
										<div class="col-sm-3 col-xs-4">
											<div class="ibox-tools">
												<a href="#" class="refresh-contadores-tramite">
													<i class="fa fa-refresh"></i>
												</a>
												<a class="dropdown-toggle" data-toggle="dropdown" href="#">
													<i class="fa fa-calendar"></i>
												</a>
												<ul class="dropdown-menu dropdown-periodo">
													<li>
														<a href="#" periodo="1">HOY</a>
													</li>
													<li>
														<a href="#" periodo="2">SEMANA ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="3">MES ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="4">AÑO ACTUAL</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-4">
											<div class="ibox-tools" style="float: left;">
												<a href="#" id="btnTablaRanking">
													<i class="fa fa-table"></i>
												</a>
												<a href="#" id="btnGraficaRanking">
													<i class="fa fa-pie-chart"></i>
												</a>
											</div>
										</div>
										<div class="col-xs-8 pull-right">
											<span class="label label-info pull-right label-periodo">-</span>
										</div>
									</div>
									<div class="row grafica-ranking m-t-sm">
										<div class="col-md-12">
											<div class="row">
												<div class="col-sm-12">
													<div id="amChartRanking" style="width: 100%; height: 450px; background-color: #FFFFFF;"></div>
												</div>
												<div class="col-sm-12 custom-legend" id="amChartRankingLegend">
												</div>
											</div>
											
										</div>
									</div>
								</div>
								<!-- /.panel-body -->
								<table class="table tbl-cifras-ranking" id="tblCifrasRanking" style="display: none;">
									<tbody></tbody>
								</table>
								<div class="panel-footer">
									<div class="row">
										<div class="col-sm-2" style="text-align: left;">
											<img class="loading" width="15" height="15" alt="" src="${staticResourcesPath}/imagenes/loading.gif" style="display: none;">
										</div>
										<div class="col-sm-10">
											<small>
												<i class="fa fa-clock-o"> </i>
												Actualizado
												<label class="updatedOn"></label>
											</small>
										</div>
									</div>
								</div>
								<!-- /.panel-footer -->
							</div>
							<!-- /.panel -->
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="panel panel-default panel-dashboard panel-cifras-atendidas-periodos" periodo="">
								<div class="panel-heading">
									<div class="row">
										<div class="col-sm-9 col-xs-8">
											<h4 class="no-margins">Acumulado en el periodo (actual y anterior)</h4>
										</div>
										<div class="col-sm-3 col-xs-4">
											<div class="ibox-tools">
												<a href="#" class="refresh-contadores-tramite">
													<i class="fa fa-refresh"></i>
												</a>
												<a class="dropdown-toggle" data-toggle="dropdown" href="#">
													<i class="fa fa-calendar"></i>
												</a>
												<ul class="dropdown-menu dropdown-periodo">
													<li>
														<a href="#" periodo="1">HOY</a>
													</li>
													<li>
														<a href="#" periodo="2">SEMANA ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="3">MES ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="4">AÑO ACTUAL</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-12 pull-right">
											<span class="label label-info pull-right label-periodo">-</span>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<ul class="stat-list">
												<li id="periodoActual">
													<h2 class="no-margins"></h2>
													<small>
														Operaciones atendidas
														<span></span>
													</small>
													<div class="progress progress-mini">
														<div class="progress-bar"></div>
													</div>
												</li>
												<li id="periodoAnterior">
													<h2 class="no-margins"></h2>
													<small>
														Operaciones atendidas
														<span></span>
													</small>
													<div class="progress progress-mini">
														<div class="progress-bar"></div>
													</div>
												</li>
											</ul>
										</div>
									</div>
								</div>
								<!-- /.panel-body -->
								<div class="panel-footer">
									<div class="row">
										<div class="col-sm-2" style="text-align: left;">
											<img class="loading" width="15" height="15" alt="" src="${staticResourcesPath}/imagenes/loading.gif" style="display: none;">
										</div>
										<div class="col-sm-10">
											<small>
												<i class="fa fa-clock-o"> </i>
												Actualizado
												<label class="updatedOn"></label>
											</small>
										</div>
									</div>
								</div>
								<!-- /.panel-footer -->
							</div>
							<!-- /.panel -->
						</div>
					</div>

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default panel-dashboard panel-cifras-tramites-origen" periodo="">
								<div class="panel-heading">
									<div class="row">
										<div class="col-sm-9 col-xs-8">
											<h4 class="no-margins">Total Operaciones</h4>
										</div>
										<div class="col-sm-3 col-xs-4">
											<div class="ibox-tools">
												<a href="#" class="refresh-contadores-tramite">
													<i class="fa fa-refresh"></i>
												</a>
												<a class="dropdown-toggle" data-toggle="dropdown" href="#">
													<i class="fa fa-calendar"></i>
												</a>
												<ul class="dropdown-menu dropdown-periodo">
													<li>
														<a href="#" periodo="1">HOY</a>
													</li>
													<li>
														<a href="#" periodo="2">SEMANA ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="3">MES ACTUAL</a>
													</li>
													<li>
														<a href="#" periodo="4">AÑO ACTUAL</a>
													</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="row" style="margin-bottom: 15px;">
										<div class="col-xs-12 pull-right">
											<span class="label label-info pull-right label-periodo">-</span>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<table class="table table-striped no-wrap" id="tblTramitesOrigen" style="width: 100%;">
												<thead>
													<tr>
														<th style="width: 5%;"></th>
														<th>Operaci&oacute;n</th>
														<th>Origen</th>
														<th>Total</th>
														<th>Porcentaje</th>
													</tr>
												</thead>
												<tbody>

												</tbody>
											</table>
										</div>
									</div>
								</div>
								<!-- /.panel-body -->
								<div class="panel-footer">
									<div class="row">
										<div class="col-sm-2" style="text-align: left;">
											<img class="loading" width="15" height="15" alt="" src="${staticResourcesPath}/imagenes/loading.gif" style="display: none;">
										</div>
										<div class="col-sm-10">
											<small>
												<i class="fa fa-clock-o"> </i>
												Actualizado
												<label class="updatedOn"></label>
											</small>
										</div>
									</div>
								</div>
								<!-- /.panel-footer -->
							</div>
							<!-- /.panel -->
						</div>
					</div>
				</div>
			</div>
			<div class="footer">
				<div class="pull-right">
					<img src="http://serviciosdigitales.imss.gob.mx/delta/resources/imagenes/logo_d.png"
						style="padding: 7px 16px 0px 0px;" />
				</div>
			</div>

		</div>
	</div>

	<script>
    	var context_path = '<%= request.getContextPath()%>';
    	var pathToImages = context_path + "/static/resources/dashboard/amcharts/images/";
    </script>

	<!-- Mainly scripts -->
	<script src="<spring:url value="/static/resources/dashboard/js/jquery-2.1.1.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/js/jquery-ui-1.10.4.min.js" htmlEscape="true" />"></script>
	<script src="${staticResourcesPath}/js/jquery/jquery-post-json.js"></script>
	<script src="<spring:url value="/static/resources/dashboard/js/bootstrap.min.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/js/plugins/metisMenu/jquery.metisMenu.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/js/plugins/slimscroll/jquery.slimscroll.min.js" htmlEscape="true" />"></script>


	<!-- Custom and plugin javascript -->
	<script src="<spring:url value="/static/resources/dashboard/js/inspinia.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/js/plugins/pace/pace.min.js" htmlEscape="true" />"></script>


	<!-- ChartJS-->
	<script src="<spring:url value="/static/resources/dashboard/js/plugins/chartJs/Chart.min.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/js/plugins/chartJs/legend/src/legend.js" htmlEscape="true" />"></script>
	
	<!-- AmCharts -->
	<script src="<spring:url value="/static/resources/dashboard/amcharts/amcharts.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/amcharts/pie.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/amcharts/serial.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/amcharts/themes/light.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/amcharts/plugins/responsive/responsive.min.js" htmlEscape="true" />"></script>
	
	<!-- Peity -->
	<script src="<spring:url value="/static/resources/dashboard/js/plugins/peity/jquery.peity.min.js" htmlEscape="true" />"></script>

	<!-- Datatables -->
	<script src="<spring:url value="/static/resources/dashboard/datatables/js/jquery.dataTables.min.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/datatables/js/dataTables.bootstrap.min.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/datatables/js/dataTables.responsive.js" htmlEscape="true" />"></script>
	
	<!-- Data picker -->
	<script src="<spring:url value="/static/resources/dashboard/js/plugins/datapicker/bootstrap-datepicker.js" htmlEscape="true" />"></script>
	<script src="<spring:url value="/static/resources/dashboard/js/plugins/datapicker/bootstrap-datepicker.es.js" htmlEscape="true" />"></script>
	
	<script src="<spring:url value="/static/resources/dashboard/js/dashboard.js" htmlEscape="true" />"></script>

</body>

</html>
