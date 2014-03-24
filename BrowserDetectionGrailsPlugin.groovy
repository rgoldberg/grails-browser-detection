class BrowserDetectionGrailsPlugin {
    def version = "1.0.0"
    def grailsVersion = "2.3 > *"

    def author = "Gennady Tsarik"
    def authorEmail = "vare6gin@gmail.com"
    def title = "Browser detection"
    def description = '''\\
This plugin provides service and tag library for browser detection. You can know what is the browser, version, operating system and language specified in request headers.
'''
    def documentation = "http://grails.org/plugin/browser-detection"

	def dependsOn = [:]
	def pluginExcludes = [
			"grails-app/views/error.gsp",
			"grails-app/views/index.gsp"
	]
}