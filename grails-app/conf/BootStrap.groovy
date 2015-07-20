import java.util.logging.Logger;

import cl.adexus.isl.spm.*

import org.apache.shiro.crypto.hash.Sha256Hash
import grails.converters.JSON;

class BootStrap {

	def fixtureLoader
	
	def init = { servletContext ->
			environments {
				development  {
					/*cargaCodigos()
					testData()*/
				}
				desarrollo_isl {
					/*cargaCodigos()
					testData()*/
				}
				test {
					/*cargaCodigos()*/
				}
				production {
					/*cargaCodigos()*/
				}
			}
		}

	def destroy = {
	}
	
	
	
	
	def addRolePermissions(){
		def permission = new ShiroPermission(permission: "*:*", rol: "Ejecutivo Plataforma")
		permission.save(flush:true)
	}
	
	def cargaCodigos(){
		log.info "Actividad Trabajador"; fixtureLoader.load("actividad_trabajador");
		log.info "Banco"; fixtureLoader.load("banco");
		log.info "Calificacion Denunciante"; fixtureLoader.load("calificacion_denunciante");
		log.info "Categoria Ocupacion"; fixtureLoader.load("categoria_ocupacion");
		log.info "Criterio Gravedad"; fixtureLoader.load("criterio_gravedad");
		log.info "Estructura Juridica"; fixtureLoader.load("estructura_juridica");
		log.info "Etnia"; fixtureLoader.load("etnia");
		log.info "Formato Origen"; fixtureLoader.load("formato_origen");
		log.info "Nacion"; fixtureLoader.load("nacion");
		log.info "Origen Daño"; fixtureLoader.load("origen_danyo");
		log.info "Region.Provincia.Comuna"; fixtureLoader.load("region_provincia_comuna");
		
		log.info "Tipo Accidente Trayecto"; fixtureLoader.load("tipo_accidente_trayecto");
		log.info "Tipo Actividad Economica"; 
			fixtureLoader.load("tipo_actividad_economica_1");
			fixtureLoader.load("tipo_actividad_economica_2");
			fixtureLoader.load("tipo_actividad_economica_3");
			
		log.info "Tipo Calle"; fixtureLoader.load("tipo_calle");
		log.info "Tipo Convenio"; fixtureLoader.load("tipo_convenio");
		log.info "Tipo Centro Salud"; fixtureLoader.load("tipo_centro_salud");
		log.info "Tipo Concepto Reembolso"; fixtureLoader.load("tipo_concepto_reembolso");
		log.info "Tipo Cuenta"; fixtureLoader.load("tipo_cuenta");
		log.info "Tipo Cuenta Médica"; fixtureLoader.load("tipo_cuenta_medica");
		log.info "Tipo Duracion Contrato"; fixtureLoader.load("tipo_duracion_contrato");
		log.info "Tipo Empresa"; fixtureLoader.load("tipo_empresa");
		log.info "Tipo Enfermedad"; fixtureLoader.load("tipo_enfermedad");
		log.info "Tipo Medio Prueba Accidente"; fixtureLoader.load("tipo_medio_prueba_accidente");
		log.info "Tipo Paquete"; fixtureLoader.load("tipo_paquete");
		log.info "Tipo Prestador"; fixtureLoader.load("tipo_prestador");
		log.info "Tipo Propiedad Empresa"; fixtureLoader.load("tipo_propiedad_empresa");
		log.info "Tipo Remuneracion"; fixtureLoader.load("tipo_remuneracion");
		
		log.info "Tipo Evento"; fixtureLoader.load("tipo_evento");
		log.info "Tipo Evento Siniestro"; fixtureLoader.load("tipo_evento_siniestro");
		log.info "Tipo Lateralidad"; fixtureLoader.load("tipo_lateralidad");
		log.info "Origen Siniestro"; fixtureLoader.load("origen_siniestro");
		log.info "Tipo Calificación"; fixtureLoader.load("tipo_calificacion");		
		log.info "Origen Diagnóstico"; fixtureLoader.load("origen_diagnostico");
		log.info "Código Forma Siniestro"; fixtureLoader.load("codigo_forma");
		log.info "Código Agente Accidente"; fixtureLoader.load("codigo_agente_accidente");
		log.info "Código Intencionalidad"; fixtureLoader.load("codigo_intencionalidad");
		log.info "Código Modo Transporte"; fixtureLoader.load("codigo_modo_transporte");
		log.info "Código Papel Lesionado"; fixtureLoader.load("codigo_papel_lesionado");
		log.info "Código Contraparte"; fixtureLoader.load("codigo_contraparte");
		log.info "Código Ubicación Lesión"; fixtureLoader.load("codigo_ubicacion_lesion");
		log.info "Tipo de Antecedente"; fixtureLoader.load("tipo_antecedente");
		log.info "Tipo de Actividad Seguimiento"; fixtureLoader.load("tipo_actividad_seguimiento");
		log.info "Tipo de ODA"; fixtureLoader.load("tipo_oda");
		log.info "Cambio Nivel Seguimiento"; fixtureLoader.load("cambio_nivel_seguimiento");
		
		log.info "Códigos Agente"; 
			fixtureLoader.load("codificacion_agente_0");
			fixtureLoader.load("codificacion_agente_1");
			fixtureLoader.load("codificacion_agente_2");
			fixtureLoader.load("codificacion_agente_3");
			fixtureLoader.load("codificacion_agente_4");
			fixtureLoader.load("codificacion_agente_5");
			fixtureLoader.load("codificacion_agente_6");
			fixtureLoader.load("codificacion_agente_7");
			fixtureLoader.load("codificacion_agente_8");
			fixtureLoader.load("codificacion_agente_9");
			fixtureLoader.load("codificacion_agente_10");
			fixtureLoader.load("codificacion_agente_11");
			fixtureLoader.load("codificacion_agente_12");
			fixtureLoader.load("codificacion_agente_13");
			
		log.info "Códigos CIE-10 de Enfermedades";
			fixtureLoader.load("CIE10_1"); fixtureLoader.load("CIE10_2"); 
			fixtureLoader.load("CIE10_3"); fixtureLoader.load("CIE10_4"); 
			fixtureLoader.load("CIE10_5"); fixtureLoader.load("CIE10_6"); 
			fixtureLoader.load("CIE10_7"); fixtureLoader.load("CIE10_8"); 
			fixtureLoader.load("CIE10_9"); fixtureLoader.load("CIE10_10"); 
			fixtureLoader.load("CIE10_11"); fixtureLoader.load("CIE10_12"); 
			fixtureLoader.load("CIE10_13"); fixtureLoader.load("CIE10_14"); 
			fixtureLoader.load("CIE10_15"); fixtureLoader.load("CIE10_16"); 
			fixtureLoader.load("CIE10_17"); fixtureLoader.load("CIE10_18"); 
			fixtureLoader.load("CIE10_19"); fixtureLoader.load("CIE10_20"); 
			fixtureLoader.load("CIE10_21"); fixtureLoader.load("CIE10_22"); 
			fixtureLoader.load("CIE10_23"); fixtureLoader.load("CIE10_24"); 
			fixtureLoader.load("CIE10_25"); fixtureLoader.load("CIE10_26"); 
			fixtureLoader.load("CIE10_27"); fixtureLoader.load("CIE10_28"); 
		
	}
	
	def testUsers(){
		def admin = new ShiroUser(username: "admin", passwordHash: new Sha256Hash("admin").toHex())
		admin.addToPermissions("*:*")
		admin.save(flush:true)
	}
	
	def testData(){
		log.info "Test Data::Persona Natural"; fixtureLoader.load("_test_persona_natural");
		log.info "Test Data::Persona Juridica"; fixtureLoader.load("_test_persona_juridica");
		log.info "Test Data::Prestador"; fixtureLoader.load("_test_prestador");
		log.info "Test Data::Centro Salud"; fixtureLoader.load("_test_centro_salud");
		log.info "Test Data::Convenio"; fixtureLoader.load("_test_convenio");
		log.info "Test Data::Grupo"; fixtureLoader.load("_test_grupo");
		log.info "Test Data::Subgrupo"; fixtureLoader.load("_test_subgrupo");
		log.info "Test Data::Arancel Base"; fixtureLoader.load("_test_arancel_base");
		log.info "Test Data::Centro Salud En Convenio"; fixtureLoader.load("_test_centro_salud_en_convenio");
		log.info "Test Data::Arancel En Convenio"; fixtureLoader.load("_test_arancel_en_convenio");
		log.info "Test Data::Siniestro"; fixtureLoader.load("_test_siniestro");	
		log.info "Test Data::DIAT"; fixtureLoader.load("_test_diat");
		log.info "Test Data::DIEP"; fixtureLoader.load("_test_diep");
		log.info "Test Data::OPA"; fixtureLoader.load("_test_opa");
		log.info "Test Data::OPAEP"; fixtureLoader.load("_test_opaep");
		log.info "Test Data::ODA"; fixtureLoader.load("_test_oda");
		log.info "Test Data::Factura"; fixtureLoader.load("_test_factura");
		log.info "Test Data::Error Factura"; fixtureLoader.load("_test_error_factura");
		log.info "Test Data::Cuenta Medica"; fixtureLoader.load("_test_cuenta_medica");
		log.info "Test Data::Detalle Factura"; fixtureLoader.load("_test_detalle_factura");
		log.info "Test Data::Error Detalle Factura"; fixtureLoader.load("_test_error_detalle_factura");
		log.info "Test Data::Detalle Cuenta Medica"; fixtureLoader.load("_test_detalle_cuenta_medica");
		log.info "Test Data::Error Detalle Cuenta Medica"; fixtureLoader.load("_test_error_detalle_cuenta_medica");
		log.info "Test Data::Nota Credito"; fixtureLoader.load("_test_nota_credito");
		log.info "Test Data::Detalle Nota Credito"; fixtureLoader.load("_test_detalle_nota_credito");
		log.info "Test Data::Cuestionario Complejidad"; fixtureLoader.load("_test_cuestionario_complejidad");
		//log.info "Test Data::SDAAT"; fixtureLoader.load("_test_sdaat");
		log.info "Test Data::RECA"; fixtureLoader.load("_test_reca");
		log.info "Test Data::Antecedentes Adicionales"; fixtureLoader.load("_test_antecedente_adicional");
		log.info "Test Data::Informe OPA"; fixtureLoader.load("_test_informe_opa");
		log.info "Test Data::Diagnosticos"; fixtureLoader.load("_test_diagnosticos");
		log.info "Test Data::Reembolso"; fixtureLoader.load("_test_reembolso");
		log.info "Test Data::Detalle Gastos Reembolso"; fixtureLoader.load("_test_detalle_gastos_reembolso");
		log.info "Test Data::ALLA"; fixtureLoader.load("_test_alla");
		log.info "Test Data::ALME"; fixtureLoader.load("_test_alme");
		log.info "Test Data::RELA"; fixtureLoader.load("_test_rela");
		//log.info "Test Data::77BIS"; fixtureLoader.load("_test_77bis");
	}
}
