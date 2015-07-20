package cl.adexus.isl.spm.domain

import cl.adexus.helpers.FechaHoraHelper;
import cl.adexus.isl.spm.Alme;
import cl.adexus.isl.spm.DIEP;
import cl.adexus.isl.spm.DIAT;
import cl.adexus.isl.spm.Siniestro;
import cl.adexus.isl.spm.PersonaNatural;
import cl.adexus.isl.spm.Diagnostico;
import cl.adexus.isl.spm.ShiroUser;
import groovy.xml.MarkupBuilder;

class ALMESuseso {

	/**
	 * Obtiene la representacion xml del mensaje a enviar a suseso.
	 * @param diat Estructura de datos que contiene la informacion del documento suseso.
	 * @return String xml con la estructura requerida por suseso.
	 */
	def getXmlDocument(Alme alme){

		if( alme==null )return null

		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		def diep
		
		def siniestro = Siniestro.findById(alme?.siniestro?.id)
		
		if (siniestro?.esEnfermedadProfesional)
			diep = DIEP.findById(siniestro.diepOA.id)	
		else
			diep = DIAT.findById(siniestro.diatOA.id)
		
		def diagnosticos = Diagnostico.findById(alme?.diagnostico?.id)
		def unMedico = PersonaNatural.findByRun(alme?.medico?.run)
		//Diagnostico.findAllBySiniestro(siniestro)

		def today = new Date()	
			
		// completa el xml con los datos de RECA
		// xml.mkp.xmlDeclaration(version: "1.0", encoding: "utf-8")
		xml.ALME {
			//("xmlns:xsi":"http://www.w3.org/2001/XMLSchema-instance","xsi:noNamespaceSchemaLocation":"SIATEP_DIEP_OT_1.0.xsd") {
			ZONA_A() {
				documento() {
					cun(alme.siniestro.cun)
					folio(diep?.id)
					fecha_emision(diep.fechaEmision.format("yyyy-MM-dd'T'00:00:00"))
					codigo_org_admin('21')
					codigo_emisor('21')
					codigo_caso(siniestro?.id)
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
			ZONA_G() {
				if (diagnosticos){
					for (d in diagnosticos){
						evaluacion(){
							((d.diagnostico!=null)?diagnostico(d.diagnostico.bytes.encodeBase64().toString()):null)
							((d.cie10!=null)?codigo_diagnostico(d.cie10.codigo):null)
							((d.parte!=null)?ubicacion(d.parte.descripcion.bytes.encodeBase64().toString()):null)
							((d.parte!=null)?codigo_ubicacion(d.parte.codigo):null)
							((d.fechaDiagnostico!=null)?fecha_diagnostico(d.fechaDiagnostico.format("yyyy-MM-dd")):null)
						}
					}
				}
			}
			ZONA_K() { 
				indicaciones_alta_medica() {
					((alme.fechaOtorgamiento!=null)?fecha_alta_medica(alme.fechaOtorgamiento.format("yyyy-MM-dd")):null)
					((alme.tipoAlta!=null)?tipo_alta_medica(alme.tipoAlta):null)
					((isBlank(alme.motivoAlta))?otro_motivo_alta(alme.motivoAlta):null)
					((alme.indicacionEvaluacion!=null)?evaluacion_incapacidad(convertBooleanSiNo(alme.indicacionEvaluacion)):null)
				} 
			}
			ZONA_L() {
				doctor() {
					medico(){
						((unMedico.apellidoPaterno!=null)?apellido_paterno(unMedico.apellidoPaterno):null)
						((unMedico.apellidoMaterno!=null)?apellido_materno(unMedico.apellidoMaterno):null)
						((unMedico.nombre!=null)?nombres(unMedico.nombre):null)
						((unMedico.run!=null)?rut(formatRun(unMedico.run)):null)
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
	def String convertSiNo(String s){
		switch (s){
			case 'Si': return '1'; break
			case 'No': return '2'; break
		}
		return ''
	}
	def String convertBooleanSiNo(Boolean s){
		if (s) {
			return '1';
		} else {
			return '2';
		}
	}
}
