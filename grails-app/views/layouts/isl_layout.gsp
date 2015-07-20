<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><g:layoutTitle default="ISL :: Sistema de Prestaciones Médicas "/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<g:javascript src="yui-3.11.0.min.js" />
			
		<meta name="description" content="ISL Sistema de Prestaciones Médicas" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		
		<!--[if lt IE 8]>
		<link rel="stylesheet" href="${resource(dir: 'font-awesome/css', file: 'font-awesome-ie7.min.css')}" type="text/css">
		<![endif]-->
		
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon" />
		
		<style type="text/css">		
			body {
				background-color:#EEEEEE;
			}
		</style>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'pure-min.css')}" type="text/css" />
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'site-isl.css')}" type="text/css" />
		<link rel="stylesheet" href="${resource(dir: 'font-awesome', file: 'css/font-awesome.min.css')}" type="text/css" />
		<resource:dateChooser />
				
	    <r:layoutResources/>	
	</head>
	
	<body class="yui3-skin-sam"> <!-- class="yui3-skin-sam"  -->
	<div class="content">
		<!-- header -->
		<div class="header">
			<g:meta name="app.version" /> 
			<div class="pure-menu pure-menu-open pure-menu-fixed pure-menu-horizontal">
				<div class="pure-g-r">
					<!-- logo -->
					<div class="pure-u-1-8 pure-logo-layout">
						<g:img dir="images" file="logo.jpg" width="110" height="110" alt="Gobierno de Chile" class="logo-img" />
					</div>
					<!-- user panel -->
					<shiro:isLoggedIn>
					<div class="pure-u-1 pure-cabecera-layout">
						<div class="pure-g-r">
							<div class="panelUsername-separador pure-u-1">
								<div style="width: 85px;float:right;">
									<g:link controller="Auth" action="signOut">Salir <i class="icon-signout"></i></g:link>
								</div>
							</div>
						</div>
						<div class="pure-g-r cienLineH">
							<div class="pure-u-1 panelUsername cienLineH">
								<h5>Bienvenido <i class="icon-user"></i> <shiro:principal/></h5>
							</div>
						</div>
						<div id="menu" class="pure-g-r">
							<div class="pure-u-1">
								<div id="horizontal-menu">
									<ul id="std-menu-items">
									<shiro:hasAnyRole in="[
									'digitador_cm',
									'digitador_detalle_cm',
									'analista_cm',
									'especialista_medico_cm',
									'especialista_convenio_cm',
									'revisor_factura',
									'analista_calificacion_1',
									'analista_calificacion_2',
									'profesional_calificacion',
									'profesional_prevencion',
									'profesional_seguimiento',
									'analista_77bis',
									'calculador_77bis',
									'regularizador_77bis',
									'ingresador_77bis'
									]">
										<li <g:if test="${flash.areaId == 'area0'}"> class="pure-menu-heading"</g:if>>
											<g:link controller="nav" action="inbox" params="[menu:'area0']">Bandeja de Entrada</g:link>
										</li>
									</shiro:hasAnyRole>

									<shiro:hasAnyRole in="['administrador_convenio']">
										<li <g:if test="${flash.areaId == 'area1'}"> class="pure-menu-heading"</g:if>>
										<g:link controller="nav" action="area1" params="[menu:'area1']">Prestadores y convenios	</g:link>
											<ul>
												<li><g:link controller="nav" action="prestador"><i class="gicon icon-fixed-width icon-ambulance"></i> Prestadores </g:link></li>
												<li><g:link controller="nav" action="prestador_create" params="[menu:'area1']"><i class="gicon icon-fixed-width icon-hospital"></i> Nuevo prestador </g:link></li>
												<li><g:link controller="nav" action="aranceles"><i class="gicon icon-fixed-width icon-ambulance"></i> Cargar Arancel Fonasa </g:link></li>	
												<li><g:link controller="nav" action="mantener_aranceles"><i class="gicon icon-fixed-width icon-ambulance"></i> Mantenedor Aranceles </g:link></li>
												<li><g:link controller="nav" action="lst_centros_salud"><i class="gicon icon-fixed-width icon-ambulance"></i> Listar Centros de Salud </g:link></li>
												<li><g:link controller="nav" action="lst_convenios"><i class="gicon icon-fixed-width icon-ambulance"></i> Listar Convenios </g:link></li>
											</ul>
										</li>
									</shiro:hasAnyRole>
									
									<shiro:hasAnyRole in="['ejecutivo_plataforma','prestador','empleador','ingresador_reingreso','ingresador_77bis','consulta_siniestro','regularizador_77bis']">
										<li <g:if test="${flash.areaId == 'area2'}"> class="pure-menu-heading"</g:if> >
										<g:link controller="nav" action="area2"  params="[menu:'area2']">Denuncias y solicitudes</g:link>
											<ul>
												<shiro:hasAnyRole in="['ejecutivo_plataforma','prestador']">
													<li><g:link controller="nav" action="SDAAT"><i class="gicon icon-fixed-width icon-ambulance"></i> Iniciar (SDAAT)</g:link></li>	
												</shiro:hasAnyRole>
												
												<shiro:hasAnyRole in="['ejecutivo_plataforma']">
													<li><g:link controller="nav" action="SDAEP"><i class="gicon icon-fixed-width icon-ambulance"></i> Iniciar (SDAEP)</g:link></li>	
												</shiro:hasAnyRole>
												
												<shiro:hasAnyRole in="['empleador']">
													<li><g:link controller="nav" action="DIATWEB"><i class="gicon icon-fixed-width icon-ambulance"></i> Iniciar (DIATWEB)</g:link></li>	
												</shiro:hasAnyRole>
												
												<shiro:hasAnyRole in="['ejecutivo_plataforma','regularizador_77bis']">
													<li><g:link controller="nav" action="DIATEPOA"><i class="gicon icon-fixed-width icon-file-text"></i> Denuncias OA</g:link></li>
												</shiro:hasAnyRole>
												
												<shiro:hasAnyRole in="['consulta_siniestro']">
													<li><g:link controller="nav" action="siniestro"><i class="gicon icon-fixed-width icon-search"></i> Consultar siniestro</g:link></li>	
												</shiro:hasAnyRole>
												
												<shiro:hasAnyRole in="['ingresador_77bis']">
													<li><g:link controller="nav" action="bis_ingreso"><i class="gicon icon-fixed-width icon-file"></i> Solicitud 77-Bis</g:link></li>	
												</shiro:hasAnyRole>
												
												<shiro:hasAnyRole in="['ingresador_reingreso']">
													<li><g:link controller="nav" action="reingreso"><i class="gicon icon-fixed-width icon-ambulance"></i> Re-Ingreso </g:link></li>
												</shiro:hasAnyRole>
												
											</ul>
										</li>
									</shiro:hasAnyRole>
									
									<shiro:hasAnyRole in="['ejecutivo_seguimiento', 'evaluador_reingreso','digitador_informe_opa','administrativo_carga']">
										<li <g:if test="${flash.areaId == 'area4'}"> class="pure-menu-heading"</g:if> >
										<g:link controller="nav" action="area4"  params="[menu:'area4']">Seguimiento</g:link>
											<ul>
												<shiro:hasAnyRole in="['ejecutivo_seguimiento']">
													<li><g:link controller="nav" action="Seg_Ingreso"><i class="gicon icon-fixed-width icon-ambulance"></i> Casos para Ingreso</g:link></li>
													<li><g:link controller="nav" action="Seg_Casos"><i class="gicon icon-fixed-width icon-ambulance"></i> Casos para Seguimiento</g:link></li>	
													<li><g:link controller="nav" action="Seg_Casos_ODA"><i class="gicon icon-fixed-width icon-ambulance"></i> Casos para Revisar ODA</g:link></li>	
												</shiro:hasAnyRole>	
												<shiro:hasAnyRole in="['evaluador_reingreso']">
													<li><g:link controller="nav" action="evaluar"><i class="gicon icon-fixed-width icon-ambulance"></i> Evaluar Re-Ingreso </g:link></li>
												</shiro:hasAnyRole>
												<shiro:hasAnyRole in="['digitador_informe_opa']">
													<li><g:link controller="nav" action="Seg_Informe_Opa"><i class="gicon icon-fixed-width icon-file-text"></i> Informe OPA</g:link></li>
												</shiro:hasAnyRole>
												<shiro:hasAnyRole in="['administrativo_carga']">
													<li><g:link controller="nav" action="Seg_Carga"><i class="gicon icon-fixed-width icon-file-text"></i> Carga de Reposos y Altas</g:link></li>
												</shiro:hasAnyRole>
											</ul>
										</li>
									</shiro:hasAnyRole>
									
									<shiro:hasAnyRole in="['digitador_cm','revisor_factura']">
										<li <g:if test="${flash.areaId == 'area5'}"> class="pure-menu-heading"</g:if>>
										<g:link controller="nav" action="area5"  params="[menu:'area5']">Cuentas médicas y facturas</g:link>
											<ul>
											<shiro:hasAnyRole in="['digitador_cm']">
												<li><g:link controller="nav" action="CM"><i class="gicon icon-fixed-width icon-file"></i> Ingreso cuenta</g:link></li>
											</shiro:hasAnyRole>
											<shiro:hasAnyRole in="['revisor_factura']">
												<li><g:link controller="nav" action="FACT"><i class="gicon icon-fixed-width icon-file-text"></i> Ingreso factura</g:link></li>
											</shiro:hasAnyRole>
											</ul>
										</li>
									</shiro:hasAnyRole>
									
									<shiro:hasAnyRole in="['digitador_reembolso','analista_reembolso','pagador_reembolso']">
										<li <g:if test="${flash.areaId == 'area6'}"> class="pure-menu-heading"</g:if>>
										<g:link controller="nav" action="area6"  params="[menu:'area6']">Reembolsos</g:link>
											<ul>
												<shiro:hasAnyRole in="['digitador_reembolso']">
												<li><g:link controller="nav" action="OTP_ingreso"><i class="gicon icon-fixed-width icon-file"></i> Ingreso</g:link></li>
												</shiro:hasAnyRole>
												<shiro:hasAnyRole in="['analista_reembolso']">
												<li><g:link controller="nav" action="OTP_analisis"><i class="gicon icon-fixed-width icon-file"></i> Análisis</g:link></li>
												</shiro:hasAnyRole>
												<shiro:hasAnyRole in="['pagador_reembolso']">
												<li><g:link controller="nav" action="OTP_revision"><i class="gicon icon-fixed-width icon-file"></i> Envío a Pago</g:link></li>
												</shiro:hasAnyRole>
												
											</ul>
										</li>
									</shiro:hasAnyRole>
									
									<shiro:hasAnyRole in="['backend']">
											<li <g:if test="${flash.areaId == 'area0'}"> class="pure-menu-heading"</g:if> >
												<g:link controller="nav" action="area0" params="[menu:'area0']">Tareas Administrativas</g:link>
												<ul>
													<li><g:link controller="usuario" action="index"><i class="gicon icon-fixed-width icon-file-text"></i> Datos Usuario</g:link></li>
													<li><g:link controller="backend" action="index"><i class="gicon icon-fixed-width icon-file-text"></i> Reenvio de DIAT</g:link></li>

													<li><g:link controller="tipoCentroSalud" action="list"><i class="gicon icon-fixed-width icon-file-text"></i> Mantenedor Centro de Salud</g:link></li>
													<li><g:link controller="tipoConceptoReembolso" action="list"><i class="gicon icon-fixed-width icon-file-text"></i> Mantenedor Concepto de Reembolso</g:link></li>
													<li><g:link controller="tipoConvenio" action="list"><i class="gicon icon-fixed-width icon-file-text"></i> Mantenedor Convenio</g:link></li>
													<li><g:link controller="tipoCuenta" action="list"><i class="gicon icon-fixed-width icon-file-text"></i> Mantenedor Cuenta</g:link></li>
													<li><g:link controller="tipoPaquete" action="list"><i class="gicon icon-fixed-width icon-file-text"></i> Mantenedor Paquete</g:link></li>
													<li><g:link controller="tipoPrestador" action="list"><i class="gicon icon-fixed-width icon-file-text"></i> Mantenedor Prestador</g:link></li>
													<li><g:link controller="tipoPropiedadEmpresa" action="list"><i class="gicon icon-fixed-width icon-file-text"></i> Mantenedor Propiedad Empresa</g:link></li>
													<li><g:link controller="estructuraJuridica" action="list"><i class="gicon icon-fixed-width icon-file-text"></i> Mantenedor Estructura Jurídica</g:link></li>
													<li><g:link controller="banco" action="list"><i class="gicon icon-fixed-width icon-file-text"></i> Mantenedor Banco</g:link></li>
												</ul>
											</li>
									</shiro:hasAnyRole>
									
									<shiro:hasAnyRole in="['ejecutivo_reporte_rp1', 'ejecutivo_reporte_rp2', 'ejecutivo_reporte_rp3', 'ejecutivo_reporte_rp4', 'ejecutivo_reporte_rp5']">
											<li <g:if test="${flash.areaId == 'area0'}"> class="pure-menu-heading"</g:if> >
												<g:link controller="nav" action="area0" params="[menu:'area0']">Reportes</g:link>
												<ul>
													<shiro:hasAnyRole in="['ejecutivo_reporte_rp1']">
													<li><g:link controller="reportes" action="rp_1" target="_blank"><i class="gicon icon-fixed-width icon-file-text"></i> Proceso 1</g:link></li>
													</shiro:hasAnyRole>
													<shiro:hasAnyRole in="['ejecutivo_reporte_rp2']">
													<li><g:link controller="reportes" action="rp_2" target="_blank"><i class="gicon icon-fixed-width icon-file-text"></i> Proceso 2</g:link></li>
													</shiro:hasAnyRole>
													<shiro:hasAnyRole in="['ejecutivo_reporte_rp3']">
													<li><g:link controller="reportes" action="rp_3" target="_blank"><i class="gicon icon-fixed-width icon-file-text"></i> Proceso 3</g:link></li>
													</shiro:hasAnyRole>
													<shiro:hasAnyRole in="['ejecutivo_reporte_rp4']">
													<li><g:link controller="reportes" action="rp_4" target="_blank"><i class="gicon icon-fixed-width icon-file-text"></i> Proceso 4</g:link></li>
													</shiro:hasAnyRole>
													<shiro:hasAnyRole in="['ejecutivo_reporte_rp5']">
													<li><g:link controller="reportes" action="rp_5" target="_blank"><i class="gicon icon-fixed-width icon-file-text"></i> Proceso 5</g:link></li>
													</shiro:hasAnyRole>
												</ul>
											</li>
									</shiro:hasAnyRole>
									
									
									</ul>
								</div>
							</div>
						</div><!-- #menu -->
					</div><!-- user panel -->
					
					<g:javascript src="yui-modules-build/gallery-sm-menu-templates/gallery-sm-menu-templates.js" />
					<g:javascript>		   
					    YUI({
						    base: '${request.getContextPath()}/js/yui-modules-build/', //'/isl-spm/js/yui-modules-build/' , 
						    modules: {
							    'gallery-sm-menu': {
									path: 'gallery-sm-menu/gallery-sm-menu.js',
									skineable: true
								    }
							},
							classNamePrefix: 'pure'
						}).use('gallery-sm-menu', function (Y) {
						
							var horizontalMenu = new Y.Menu({
								container         : '#horizontal-menu',
								sourceNode        : '#std-menu-items',
								orientation       : 'horizontal',
								hideOnOutsideClick: false,
								hideOnClick       : false
							});
						
							horizontalMenu.render();
							horizontalMenu.show();
						
						});
						// ocultar workaround
					    document.documentElement.className = "yui-pe"; 
					</g:javascript>
					</shiro:isLoggedIn>
				<!-- user panel -->
			</div>
		</div>
			
			
		</div>
		
		<!-- header -->
    	<!-- header -->
    	
		<!-- splash -->
		<div class="splash"></div>
	   <!--splash -->
	   
	   <!-- +++++++++++++++++++++++++++++ WORKAREA ++++++++++++++++++++++++++++++ -->						
		<div class="pure-g-r workarea">		
			<div class="pure-u-1">
            	<g:layoutBody/>
            </div>
			<!-- +++++++++++++++++++++++++ MENSAJES-INFO +++++++++++++++++++++++ -->				
			<g:if test="${flash.mensajes}">
				<div class="pure-u-1 messages-info">
    				<i class="icon-exclamation icon-2x pull-left icon-muted"></i> ${flash.mensajes}
    			</div>
			</g:if>
					
			<!-- +++++++++++++++++++++++++ MENSAJES-INFO +++++++++++++++++++++++ -->	
			<!-- +++++++++++++++++++++++++ MENSAJES-ERROR +++++++++++++++++++++++ -->	
			<g:hasErrors>
				<div class="pure-u-1 messages">
			  		<g:renderErrors as="list" />
				</div>
			</g:hasErrors>
			
			<!-- +++++++++++++++++++++++++ MENSAJES-ERROR +++++++++++++++++++++++ -->	
			
		</div>
		<!-- +++++++++++++++++++++++++++++ WORKAREA +++++++++++++++++++++++++++++ -->
	   
		<div id="yui3-css-stamp" style="position: absolute !important; visibility: hidden !important"></div>

		<!-- footer -->
		<div class="footer" id="footer"> 
			Versión <g:meta name="app.version" />  - Instituto de seguridad laboral - Todos los derechos reservados &copy;
		</div>
		
		<!-- footer -->
	</div>
	
    <r:layoutResources/>
	</body>

</html>
