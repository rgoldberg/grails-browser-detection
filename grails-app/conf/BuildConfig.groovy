grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {

    inherits("global") {
    }

	log "warn"

	repositories {
		grailsCentral()
        mavenRepo 'http://repository.opencastproject.org/nexus/content/repositories/public/'
    }

    dependencies {
        compile 'bitwalker:UserAgentUtils:1.12'
    }

	plugins {
        build(":release:3.0.1",
                ":rest-client-builder:2.0.1") {
            export = false
        }
        runtime (":tomcat:7.0.52.1") {
            export = false
        }
    }

}