package cl.adexus.isl.spm.domain

import groovy.xml.MarkupBuilder
import cl.adexus.helpers.FechaHoraHelper
import cl.adexus.isl.spm.Bis
import cl.adexus.isl.spm.DIAT
import cl.adexus.isl.spm.Diagnostico
import cl.adexus.isl.spm.RECA
import cl.adexus.isl.spm.SDAAT
import cl.adexus.isl.spm.ShiroUser
import cl.adexus.isl.spm.Siniestro

class RECAATSuseso {

	/**
	 * Obtiene la representacion xml del mensaje a enviar a suseso.
	 * @param diat Estructura de datos que contiene la informacion del documento suseso.
	 * @return String xml con la estructura requerida por suseso.
	 */
	def getXmlDocument(RECA reca){
		log.info "Ejecutando metodo getXmlDocument"
		if( reca==null )return null

		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)

		def siniestro = Siniestro.findById(reca.siniestro.id)
		def diat = DIAT.findById(siniestro.diatOA.id)
		def nombre = reca?.creadoPor.split("@")
		def usuario=ShiroUser.findByUsername(nombre[0])
		def diagnosticoDummy
		def diagnosticos
		def derivacion = 2

		//Filtro de los diagnosticos
		if (reca.calificacion.origen.codigo == '1'){
			diagnosticos = Diagnostico.findAllBySiniestroAndEsLaboral(siniestro, true)
			diagnosticoDummy = Diagnostico.findBySiniestroAndEsLaboral(siniestro, true)
		}else{
			diagnosticos = Diagnostico.findAllBySiniestroAndEsLaboral(siniestro, false)
			diagnosticoDummy = Diagnostico.findBySiniestroAndEsLaboral(siniestro, false)
		}

		//Derivacion desde 77bis
		def bis = Bis.findBySiniestro(siniestro)
		if (bis)
			derivacion = 1

		log.info "siniestro : $siniestro"
		log.info "siniestro.cun : ${siniestro?.cun}"
		log.info "diat.siniestro.cun : ${diat?.siniestro?.cun}"
		// completa el xml con los datos de RECA
		// xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
		xml.RECA {//("xmlns:xsi":"http://www.w3.org/2001/XMLSchema-instance","xsi:noNamespaceSchemaLocation":"SIATEP_DIAT_OT_1.0.xsd") {
			ZONA_A() {
				documento() {
					if(isBlank(diat.siniestro.cun)){
						cun(diat.siniestro.cun)
					}
					folio(diat.id)
					fecha_emision(diat.fechaEmision.format("yyyy-MM-dd'T'HH:mm:ss"))
					codigo_org_admin('21')
					codigo_emisor('21')
					codigo_caso(siniestro.id)
					validez('1')
					origen_informacion('1')

				}
			}
			ZONA_B() {
				empleador() {
					if( diat.empleador!=null ){
						((diat.empleador.rut!=null)?rut_empleador(formatRun(diat.empleador.rut)):null)
						((diat.empleador.razonSocial!=null)?nombre_empleador(diat.empleador.razonSocial):null)
					}

					if (isBlank(diat.direccionEmpleadorTipoCalle)		&&
					isBlank(diat.direccionEmpleadorNombreCalle)		&&
					isBlank(diat.direccionEmpleadorNumero)			&&
					isBlank(diat.direccionEmpleadorComuna)) {
						direccion_empleador() {
							tipo_calle(diat.direccionEmpleadorTipoCalle.codigo)
							nombre_calle(diat.direccionEmpleadorNombreCalle)
							numero(diat.direccionEmpleadorNumero)
							if (isBlank(diat.direccionEmpleadorRestoDireccion))
								resto_direccion(diat.direccionEmpleadorRestoDireccion)
							else
								resto_direccion(" ")
							localidad(diat.direccionEmpleadorComuna.provincia.codigo)
							comuna(diat.direccionEmpleadorComuna.codigo)
						}
					}

					if(diat.ciiuEmpleador!=null){
						ciiu_empleador(diat.ciiuEmpleador.codigo)
						ciiu_texto(diat.ciiuEmpleador.descripcion)
					}

					if( isBlank(diat.nTrabajadoresHombre) && isBlank(diat.nTrabajadoresMujer) ){
						n_trabajadores(diat.nTrabajadoresHombre + diat.nTrabajadoresMujer)
					}

					((diat.nTrabajadoresHombre!=null)?n_trabajadores_hombre(diat.nTrabajadoresHombre):null)
					((diat.nTrabajadoresMujer!=null)?n_trabajadores_mujer(diat.nTrabajadoresMujer):null)
					((diat.tipoEmpresa!=null && diat.tipoEmpresa.codigo!=null)?tipo_empresa(diat.tipoEmpresa.codigo):null)

					if(diat.ciiuPrincipal!=null){
						ciiu2_empleador(diat.ciiuPrincipal.codigo)
						ciiu2_texto(diat.ciiuPrincipal.descripcion)
					}

					if (diat.propiedadEmpresa != null) {
						if (isBlank(diat.propiedadEmpresa.codigo)) {
							propiedad_empresa(diat.propiedadEmpresa.codigo)
						}
					}

					if (isBlank(diat.telefonoEmpleador)) {
						telefono_empleador() {
							cod_pais('56')
							cod_area('9')
							numero(diat.telefonoEmpleador)
						}
					}
				}
			}
			ZONA_C() {
				empleado() {
					trabajador() {
						if( diat.trabajador!=null ){
							((isBlank(diat.trabajador.apellidoPaterno))?apellido_paterno(diat.trabajador.apellidoPaterno):null)
							((isBlank(diat.trabajador.apellidoMaterno))?apellido_materno(diat.trabajador.apellidoMaterno):null)
							((isBlank(diat.trabajador.nombre))?nombres(diat.trabajador.nombre):null)
							((diat.trabajador.run!=null)?rut(formatRun(diat.trabajador.run)):null)
							((diat.trabajador.fechaNacimiento!=null)?fecha_nacimiento(diat.trabajador.fechaNacimiento.format("yyyy-MM-dd")):null)
							((diat.trabajador.fechaNacimiento!=null)?edad(FechaHoraHelper.diffYears(new Date(), diat.trabajador.fechaNacimiento)):null)
							((diat.trabajador.sexo!=null)?sexo(convertSexo(diat.trabajador.sexo)):null)
							//cambio version 1.0.9.7
							((diat.nacionalidadTrabajador!=null && diat.nacionalidadTrabajador.codigo!=null)?pais_nacionalidad(diat.nacionalidadTrabajador.codigo):null)

						}
					}
					// codigo_etnia
					if(diat?.etnia != null && !diat?.etnia?.codigo.equals("0"))
						etnia(diat.etnia.codigo)
					// etnia_otro
					if (isBlank(diat.otroPueblo))
						etnia_otro(diat.otroPueblo)

					if (isBlank(diat.direccionTrabajadorTipoCalle)		&&
					isBlank(diat.direccionTrabajadorNombreCalle)	&&
					isBlank(diat.direccionTrabajadorNumero)			&&
					isBlank(diat.direccionTrabajadorComuna)) {
						direccion_trabajador() {
							tipo_calle(diat.direccionTrabajadorTipoCalle.codigo)
							nombre_calle(diat.direccionTrabajadorNombreCalle)
							numero(diat.direccionTrabajadorNumero)
							if (isBlank(diat.direccionTrabajadorRestoDireccion))
								resto_direccion(diat.direccionTrabajadorRestoDireccion)
							else
								resto_direccion(" ")
							localidad(diat.direccionTrabajadorComuna.provincia.codigo)
							comuna(diat.direccionTrabajadorComuna.codigo)
						}
					}
					if (isBlank(diat.profesionTrabajador))
						profesion_trabajador(diat.profesionTrabajador)
					ciuo_trabajador('3141')
					((diat.categoriaOcupacion!=null && diat.categoriaOcupacion.codigo!=null)?categoria_ocupacion(diat.categoriaOcupacion.codigo):null)
					((diat.duracionContrato!=null && diat.duracionContrato.codigo!=null)?duracion_contrato(diat.duracionContrato.codigo):null)
					tipo_dependencia('1')
					((diat.tipoRemuneracion!=null && diat.tipoRemuneracion.codigo!=null)?tipo_remuneracion(diat.tipoRemuneracion.codigo):null)
					((diat.fechaIngresoEmpresa!=null)?fecha_ingreso(diat.fechaIngresoEmpresa.format("yyyy-MM-dd")):null)

					//telefono trabajador
					if (isBlank(diat.telefonoTrabajador)) {
						telefono_trabajador() {
							cod_pais('56')
							cod_area('9')
							numero(diat.telefonoTrabajador)
						}
					}


					//clasificacion trabajador
					def sdaat = SDAAT.findByDiat(diat, [sort: "id", order: "asc"])
					if(sdaat?.cuestionarioObrero?.actividadTrabajador != null)
						clasificacion_trabajador(sdaat.cuestionarioObrero.actividadTrabajador.codigo)
					//sistema comun
					if(diat?.sistemaSalud)
						sistema_comun(diat.sistemaSalud.codigo)
				}

			}
			ZONA_D() {
				accidente() {
					((diat.fechaAccidente!=null)?fecha_accidente(diat.fechaAccidente.format("yyyy-MM-dd'T'HH:mm:ss")):null)
					((diat.horaIngreso!=null)?hora_ingreso(diat.horaIngreso.format("HH:mm:ss")):null)
					direccion_accidente() {
						tipo_calle('2')
						((diat.direccionAccidenteNombreCalle!=null)?nombre_calle(diat.direccionAccidenteNombreCalle):null)
						numero('0')
						resto_direccion('.')
						((diat.direccionAccidenteComuna!=null && diat.direccionAccidenteComuna.codigo!=null)?localidad(diat.direccionAccidenteComuna.provincia.codigo):null)
						((diat.direccionAccidenteComuna!=null && diat.direccionAccidenteComuna.codigo!=null)?comuna(diat.direccionAccidenteComuna.codigo):null)
					}
					if (isBlank(diat.lugarAccidente))
						lugar_accidente(diat.lugarAccidente)
					if (isBlank(diat.que))
						que(diat.que)
					((diat.como!=null)?como(diat.como):null)
					if (isBlank(diat.trabajoHabitualCual))
						trabajo_habitual_cual(diat.trabajoHabitualCual)
					((diat.esTrabajoHabitual!=null)?trabajo_habitual( ((diat.esTrabajoHabitual)?'1':'2' ) ):null)
					((diat.gravedad!=null && diat.gravedad.codigo!=null)?gravedad(diat.gravedad.codigo):null)
					((diat.esAccidenteTrayecto!=null)?tipo_accidente( ((diat.esAccidenteTrayecto)?'2':'1') ):null)
					((diat.horaSalida!=null)?hora_salida(diat.horaSalida.format("HH:mm:ss")):null)
					if(diat.esAccidenteTrayecto){
						((diat.tipoAccidenteTrayecto!=null && diat.tipoAccidenteTrayecto.codigo!=null)?tipo_accidente_trayecto(diat.tipoAccidenteTrayecto.codigo):null)
					}
					((diat.medioPrueba!=null && diat.medioPrueba.codigo!=null)?medio_prueba(diat.medioPrueba.codigo):null)
					((diat.detallePrueba!=null)?detalle_prueba(diat.detallePrueba):null)
				}
			}


			ZONA_G() {
				if (diagnosticos){
					for (d in diagnosticos){
						evaluacion(){
							((d.diagnostico!=null)?diagnostico(d.diagnostico.toString()):null)
							((d.cie10!=null)?codigo_diagnostico(d.cie10.codigo):null)
							((d.parte!=null)?ubicacion(d.parte.descripcion.toString()):null)
							((d.parte!=null)?codigo_ubicacion(d.parte.codigo):null)
							((d.fechaDiagnostico!=null)?fecha_diagnostico(d.fechaDiagnostico.format("yyyy-MM-dd")):null)
						}
					}
				}
			}
			ZONA_H() {
				resolucion(){
					//TODO: terminar!
					((reca!=null)?num_resol(reca.id):null)
					derivacion77(derivacion)
					((reca.calificacion!=null)?tipo_acc_enf(reca.calificacion.codigo):null)
					((reca.indicacion!=null)?indicaciones(reca.indicacion):null)

					codificacion_accidente(){
						((reca.forma!=null)?codigo_forma(reca.forma.codigo):null)
						((reca.agenteAccidente!=null)?codigo_agente_accidente(reca.agenteAccidente.codigo):null)
						((reca.intencionalidad!=null)?codigo_intencionalidad(reca.intencionalidad.codigo):null)
						/* no vienen en el nuevo esquema entregado en febrero del 2015
						 ((reca.transporte!=null)?codigo_modo_transporte(reca.transporte.codigo):null)
						 ((reca.lesionado!=null)?codigo_papel_lesionado(reca.lesionado.codigo):null)
						 ((reca.contraparte!=null)?codigo_contraparte(reca.contraparte.codigo):null)
						 ((reca.evento!=null)?codigo_tipo_evento(reca.evento.codigo):null)*/
					}
					calificador(){
						((usuario.apellidoPaterno!=null)?apellido_paterno(usuario.apellidoPaterno):null)
						((usuario.apellidoMaterno!=null)?apellido_materno(usuario.apellidoMaterno):null)
						((usuario.nombres!=null)?nombres(usuario.nombres):null)
						((usuario.run!=null)?rut(formatRun(usuario.run)):null)
						((usuario.fechaNacimiento!=null)?fecha_nacimiento(usuario.fechaNacimiento.format("yyyy-MM-dd")):null)
						((usuario.fechaNacimiento!=null)?edad(FechaHoraHelper.diffYears(new Date(), usuario.fechaNacimiento)):null)
						((usuario.sexo!=null)?sexo(usuario.sexo):null)
						((usuario.idNacionalidad!=null)?pais_nacionalidad(usuario.idNacionalidad):null)
					}
				}
			}
			ZONA_O() { seguridad(){
					descripcion("Zona de firma y certificado")
				} }
		}

		// retorna el xml
		def xmlData = writer.toString()

		return xmlData
	}

	def boolean isBlank(value) {
		if (value instanceof String)
			return value != null && !''.equals(value)
		return value != null
	}

	def String formatDate(Date fecha){
		def susesoXmlDateFormat = "yyyy-MM-dd'T'HH:mm:ss"

		return fecha.format(susesoXmlDateFormat)
	}

	def String formatRun(String r){
		if (!r) return null
		return r.substring(0,r.length()-1) + "-" +r.substring(r.length()-1).toUpperCase();
	}

	def String convertSexo(String s){
		switch (s){
			case 'M': return '1'; break
			case 'F': return '2'; break
		}
		return ''
	}
	def String convertSiNo(String s){
		switch (s){
			case 'Si': return '1'; break
			case 'No': return '2'; break
		}
		return ''
	}
}
