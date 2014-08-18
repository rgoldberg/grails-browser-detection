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
        mavenRepo 'https://raw.githubusercontent.com/HaraldWalker/user-agent-utils/mvn-repo/'
    }

    dependencies {
        compile 'bitwalker:UserAgentUtils:1.13'
    }

	plugins {
        build(":release:3.0.1",
                ":rest-client-builder:2.0.1") {
            export = false
        }
        runtime (":tomcat:7.0.54") {
            export = false
        }
    }

}