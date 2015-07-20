package cl.adexus.isl.spm.domain

import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.isl.spm.helpers.FormatosISLHelper
import cl.adexus.isl.spm.DIEP;
import groovy.xml.MarkupBuilder;

class DIEPSuseso {
	/**
	 * Obtiene la representacion xml del mensaje a enviar a suseso.
	 * @param diep Estructura de datos que contiene la informacion del documento suseso.
	 * @return String xml con la estructura requerida por suseso.
	 */
	def getXmlDocument(DIEP diep){

		if( diep==null )return null

		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)

		// completa el xml con los datos de DIEP
		// xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
		xml.DIEP() {
			//("xmlns:xsi":"http://www.w3.org/2001/XMLSchema-instance","xsi:noNamespaceSchemaLocation":"SIATEP_DIEP_OT_1.0.xsd") {
			ZONA_A() {
				documento() {
					if(isBlank(diep.siniestro.cun)){
						cun(diep.siniestro.cun)
					}
					folio(diep.id)
					fecha_emision(diep.fechaEmision.format("yyyy-MM-dd'T'HH:mm:ss"))
					codigo_org_admin('21')
					codigo_emisor('21')
					codigo_caso(diep.siniestro.id)
					validez('1')
					origen_informacion('1')
				}
			}
			ZONA_B() {
				empleador() {
					
					if( diep.empleador!=null ){
						((diep.empleador.rut!=null)?rut_empleador(formatRun(diep.empleador.rut)):null)
						((diep.empleador.razonSocial!=null)?nombre_empleador(diep.empleador.razonSocial):null)
					}
					
					if (isBlank(diep.direccionEmpleadorTipoCalle)		&&
						isBlank(diep.direccionEmpleadorNombreCalle)		&&
						isBlank(diep.direccionEmpleadorNumero)			&&
						isBlank(diep.direccionEmpleadorComuna)) {
						direccion_empleador() {
							tipo_calle(diep.direccionEmpleadorTipoCalle.codigo)
							nombre_calle(diep.direccionEmpleadorNombreCalle)
							numero(diep.direccionEmpleadorNumero)
							if (isBlank(diep.direccionEmpleadorRestoDireccion))
						    	resto_direccion(diep.direccionEmpleadorRestoDireccion)
							else
								resto_direccion(" ")
							localidad(diep.direccionEmpleadorComuna.provincia.codigo)
							comuna(diep.direccionEmpleadorComuna.codigo)
						}
					}
					
					if(diep.ciiuEmpleador!=null){
						ciiu_empleador(diep.ciiuEmpleador.codigo)
						ciiu_texto(diep.ciiuEmpleador.descripcion)
					}

					
					if( isBlank(diep.nTrabajadoresHombre) && isBlank(diep.nTrabajadoresMujer) ){
						n_trabajadores(diep.nTrabajadoresHombre + diep.nTrabajadoresMujer)
					}

					((diep.nTrabajadoresHombre!=null)?n_trabajadores_hombre(diep.nTrabajadoresHombre):null)
					((diep.nTrabajadoresMujer!=null)?n_trabajadores_mujer(diep.nTrabajadoresMujer):null)
					((diep.tipoEmpresa!=null && diep.tipoEmpresa.codigo!=null)?tipo_empresa(diep.tipoEmpresa.codigo):null)
					
					if(diep.ciiuPrincipal!=null){
						ciiu2_empleador(diep.ciiuPrincipal.codigo)
						ciiu2_texto(diep.ciiuPrincipal.descripcion)
					}
					
					if (diep.propiedadEmpresa != null) {
						if (isBlank(diep.propiedadEmpresa.codigo)) {
							propiedad_empresa(diep.propiedadEmpresa.codigo)
						}
					}
					if (isBlank(diep.telefonoEmpleador)) {
						telefono_empleador() {
							cod_pais('56')
							cod_area('9')
							numero(diep.telefonoEmpleador)
						}
					}

				}
			}
			ZONA_C() {
				empleado() {
					trabajador() {
						if( diep.trabajador!=null ){
							((isBlank(diep.trabajador.apellidoPaterno))?apellido_paterno(diep.trabajador.apellidoPaterno):null)
							((isBlank(diep.trabajador.apellidoMaterno))?apellido_materno(diep.trabajador.apellidoMaterno):null)
							((isBlank(diep.trabajador.nombre))?nombres(diep.trabajador.nombre):null)
							((diep.trabajador.run!=null)?rut(formatRun(diep.trabajador.run)):null)
							((diep.trabajador.fechaNacimiento!=null)?fecha_nacimiento(diep.trabajador.fechaNacimiento.format("yyyy-MM-dd")):null)
							((diep.trabajador.fechaNacimiento!=null)?edad(FechaHoraHelper.diffYears(new Date(), diep.trabajador.fechaNacimiento)):null)
							((diep.trabajador.sexo!=null)?sexo(convertSexo(diep.trabajador.sexo)):null)
						}
						((diep.nacionalidadTrabajador!=null && diep.nacionalidadTrabajador.codigo!=null)?pais_nacionalidad(diep.nacionalidadTrabajador.codigo):null)
					}
					
					if (isBlank(diep.direccionTrabajadorTipoCalle)		&&
						isBlank(diep.direccionTrabajadorNombreCalle)	&&
						isBlank(diep.direccionTrabajadorNumero)			&&
						isBlank(diep.direccionTrabajadorComuna)) {
						direccion_trabajador() {
							tipo_calle(diep.direccionTrabajadorTipoCalle.codigo)
							nombre_calle(diep.direccionTrabajadorNombreCalle)
							numero(diep.direccionTrabajadorNumero)
							if (isBlank(diep.direccionTrabajadorRestoDireccion))
						    	resto_direccion(diep.direccionTrabajadorRestoDireccion)
							else
								resto_direccion(" ")
							localidad(diep.direccionTrabajadorComuna.provincia.codigo)
							comuna(diep.direccionTrabajadorComuna.codigo)
						}
					}
					if (isBlank(diep.profesionTrabajador))
						profesion_trabajador(diep.profesionTrabajador)
					ciuo_trabajador('3141')
					((diep.categoriaOcupacion!=null && diep.categoriaOcupacion.codigo!=null)?categoria_ocupacion(diep.categoriaOcupacion.codigo):null)
					((diep.duracionContrato!=null && diep.duracionContrato.codigo!=null)?duracion_contrato(diep.duracionContrato.codigo):null)
					tipo_dependencia('1')
					((diep.tipoRemuneracion!=null && diep.tipoRemuneracion.codigo!=null)?tipo_remuneracion(diep.tipoRemuneracion.codigo):null)
					((diep.fechaIngresoEmpresa!=null)?fecha_ingreso(diep.fechaIngresoEmpresa.format("yyyy-MM-dd")):null)
				}
			}
			ZONA_E() {
				enfermedad() {
					((diep.sintoma!=null)?sintoma(diep.sintoma):null)
					((diep.fechaSintoma!=null)?fecha_sintoma(diep.fechaSintoma.format("yyyy-MM-dd")):null)
					((diep.parteCuerpo!=null)?parte_cuerpo(diep.parteCuerpo):null)
					((diep.esAntecedentePrevio!=null) ? antecedente_previo( diep.esAntecedentePrevio?"1":"2" ):null)
					((diep.esAntecedenteCompanero!=null) ? antecedente_companero( diep.esAntecedenteCompanero?"1":"2" ):null)
					((diep.descripcionTrabajo!=null)? direccion_trabajo( diep.descripcionTrabajo ):null)
					if (isBlank(diep.puestoTrabajo))
						puesto_trabajo( diep.puestoTrabajo )
					((diep.agenteSospechoso!=null)? agente_sospechoso( diep.agenteSospechoso ):null)
					((diep.fechaAgente!=null)? fecha_agente( diep.fechaAgente.format("yyyy-MM-dd")):null)
				}
			}
			ZONA_F() {
				denunciante() {
					((diep.denunciante!=null)?nombre_denunciante(FormatosISLHelper.nombreCompletoStatic(diep.denunciante)):null)
					((diep.denunciante!=null && diep.denunciante.run!=null)?rut_denunciante(formatRun(diep.denunciante.run)):null)
					((diep.calificacionDenunciante!=null && diep.calificacionDenunciante.codigo!=null)?clasificacion(diep.calificacionDenunciante.codigo):null)
					if (isBlank(diep.telefonoDenunciante)) {
						telefono_denunciante() {
							cod_pais('56')
							cod_area('9')
							numero(diep.telefonoDenunciante)
						}
					}
				}
			}
			ZONA_O() { seguridad() }
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

}
