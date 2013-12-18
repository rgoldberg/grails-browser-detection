grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"

grails.project.dependency.resolution = {

    inherits("global") {
    }

	log "warn"

	repositories {
		grailsCentral()
    }

    dependencies {
        build "eu.bitwalker.useragentutils:1.12"
    }

	plugins {
        build ":release:3.0.1"
        runtime ":tomcat:7.0.42"
    }

}