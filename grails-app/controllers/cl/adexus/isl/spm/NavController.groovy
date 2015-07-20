package cl.adexus.isl.spm

import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils

class NavController {

	def index (){
		//App home page
	}

	def inbox() {
		flash.areaId = "area0"
		forward (controller: "INBOX")
	}
	
	def area0() {
		flash.areaId = "area0"
	}
	
	def area1() {
		flash.areaId = "area1"
	}

	def area2 () {
		flash.areaId = "area2"
	}
	
	def area4 () {
		flash.areaId = "area4"
	}
	
	def area5 () {
		flash.areaId = "area5"
	}
	
	def area6 () {
		flash.areaId = "area6"
	}
	
	def Seg_Ingreso () {
		flash.areaId = "area4"
		redirect (controller: 'seguimiento', action: 'dp01')
	}
	
	def Seg_Casos () {
		flash.areaId = "area4"
		redirect (controller: 'seguimiento', action: 'dp03')
	}

	def Seg_Casos_ODA () {
		flash.areaId = "area4"
		redirect (controller: 'seguimiento', action: 'dp11')
	}

	def Seg_Carga () {
		flash.areaId = "area4"
		redirect (controller: 'seguimiento', action: 'dp15')
	}
	
	def Seg_Informe_Opa () {
		flash.areaId = "area4"
		redirect (controller: 'InformeOPA', action: 'dp01')
	}
	
	def prestador() {
		flash.areaId = "area1"
		forward (controller: 'prestador')
	}
	
	def prestador_create() {
		flash.areaId = "area1"
		redirect (controller: 'prestador', action: 'create')
	}
	
	def aranceles() {
		flash.areaId = "area1"
		redirect (controller: 'aranceles')
	}
	
	def mantener_aranceles() {
		flash.areaId = "area1"
		redirect (controller: 'aranceles', action: 'mantener_aranceles')
	}

	def lst_centros_salud() {
		flash.areaId = "area1"
		redirect (controller: 'CentroSalud', action: 'listar')
	}
	
	def lst_convenios() {
		flash.areaId = "area1"
		redirect (controller: 'convenio', action: 'listar')
	}

	def SDAAT () { 
		flash.areaId = "area2" 
		forward (controller: 'SDAAT_ident')
	}

	def SDAEP () {
		flash.areaId = "area2" 
		forward (controller: 'SDAEP_previo')
	}

	def DIATWEB () {
		flash.areaId = "area2" 
		forward (controller: 'DIATWEB')
	}

	def DIATEPOA () {
		flash.areaId = "area2"
		forward (controller: 'DIATEPOA')
	}
	
	def siniestro () {
		flash.areaId = "area2"
		forward (controller: 'siniestro')
	}
	
	def bis_ingreso () {
		flash.areaId = "area2"
		forward (controller: 'BIS_ingreso')
	}

	def CM () {
		flash.areaId = "area5"
		forward (controller: 'CM_ingreso')
	}
	
	def FACT () {
		flash.areaId = "area5"
		forward (controller: 'FACT_ingreso')
	}
	
	def OTP_ingreso () {
		flash.areaId = "area6"
		forward (controller: 'OTP_ingreso')
	}
	
	def OTP_analisis () {
		flash.areaId = "area6"
		forward (controller: 'OTP_ingreso', action: 'listDp07')
	}
	
	def OTP_revision () {
		flash.areaId = "area6"
		forward (controller: 'OTP_revision')
	}

	def reingreso () {
		flash.areaId = "area4"
		redirect (controller: 'reingreso', action: 'dp01')
	}

	def evaluar () {
		flash.areaId = "area4"
		redirect (controller: 'reingreso', action: 'dp04')
	}
	
}
