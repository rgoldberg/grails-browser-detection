package org.geeks.browserdetection

import grails.plugins.Plugin

class BrowserDetectionGrailsPlugin extends Plugin {
    def grailsVersion = "3.0.0 > *"
    def title = "Browser Detection Plugin"
    def author = "Gennady Tsarik, Mathias Fonseca"
    def authorEmail = "mathifonseca@gmail.com"
    def description = 'Helps you detect browsers, versions, language and operating systems from request headers'
    def documentation = "http://grails.org/plugin/browser-detection"
    def license = "APACHE"
    def issueManagement = [url: "https://github.com/mathifonseca/grails-browser-detection/issues" ]
    def scm = [ url: "https://github.com/mathifonseca/grails-browser-detection" ]
    def profiles = ['web']
}
