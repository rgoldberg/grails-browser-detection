package org.geeks.browserdetection

import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockServletContext
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(UserAgentIdentService)
class UserAgentIdentServiceSpec extends Specification {

    @Unroll
    void "Test various detection mechanisms"() {
        given:
        MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest()
        if(userAgentString) {
            mockHttpRequest.addHeader(service.USER_AGENT_REQUEST_ATTRIBUTE, userAgentString)
        }
        GrailsWebRequest mockWebRequest = new GrailsWebRequest(
                mockHttpRequest,
                new MockHttpServletResponse(),
                new MockServletContext())

        WebUtils.storeGrailsWebRequest(mockWebRequest)

        expect:
        service.isFirefox() == isFirefox
        service.isChrome() == isChrome
        service.isSafari() == isSafari
        service.isMsie() == isMsie
        service.isiOsDevice() == isiOsDevice
        service.isiPad() == isiPad
        service.isMobile() == isMobile
        service.isAndroid() == isAndroid
        service.getBrowser() == browser
        service.getBrowserVersion() == version
        service.isOther() == isOther

        where:
        isFirefox | isChrome | isSafari | isMsie | isiOsDevice | isiPad | isiPhone | isMobile | isAndroid | isOther | browser               | version        | userAgentString
        true      | false    | false    | false  | false       | false  | false    | false    | false     | false   | "Firefox 3"           | "3.6.9"        | "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-GB; rv:1.9.2.9) Gecko/20100824 Firefox/3.6.9 ( .NET CLR 3.5.30729; .NET CLR 4.0.20506)"
        false     | true     | false    | false  | false       | false  | false    | false    | false     | false   | "Chrome 14"           | "14.0.835.202" | "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.202 Safari/535.1"
        false     | false    | false    | true   | false       | false  | false    | false    | false     | false   | "Internet Explorer 7" | "7.0"          | "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB6.4; .NET CLR 1.1.4322; FDM; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)"
        false     | false    | false    | true   | false       | false  | false    | false    | false     | false   | "Internet Explorer 6" | "6.0"          | "Mozilla/4.0 (compatible; MSIE 6.0; Windows 98; Rogers Hi·Speed Internet; (R1 1.3))"
        false     | false    | true     | false  | true        | true   | false    | true     | false     | false   | "Mobile Safari"       | "4.0.4"        | "Mozilla/5.0(iPad; U; CPU iPhone OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B314 Safari/531.21.10"
        false     | false    | true     | false  | true        | false  | true     | true     | false     | false   | "Mobile Safari"       | "4.0.4"        | "Mozilla/5.0 (iPhone; U; CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b Safari/531.21.10"
        false     | false    | true     | false  | true        | false  | true     | true     | false     | false   | "Mobile Safari"       | "5.0.2"        | "Mozilla/5.0 (iPod; U; CPU iPhone OS 4_3_3 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5"
        false     | false    | true     | false  | false       | false  | false    | true     | false     | false   | "Mobile Safari"       | "6.0.0"        | "Mozilla/5.0 (BlackBerry; U; BlackBerry 9800; zh-TW) AppleWebKit/534.1+ (KHTML, like Gecko) Version/6.0.0.246 Mobile Safari/534.1+"
        false     | false    | true     | false  | false       | false  | false    | true     | true      | false   | "Safari"              | null           | "Mozilla/5.0 (Linux; U; Android 2.3; en-us) AppleWebKit/999+ (KHTML, like Gecko) Safari/999.9"
        false     | false    | false    | false  | false       | false  | false    | false    | false     | true    | "Unknown"             | null           | null
        false     | false    | false    | false  | false       | false  | false    | false    | false     | true    | "Unknown"             | null           | "abs; abc"

    }

    void "Testing ComparisonType for chrome" (){
        given:
        MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest()
        mockHttpRequest.addHeader(service.USER_AGENT_REQUEST_ATTRIBUTE, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.202 Safari/535.1")
        GrailsWebRequest mockWebRequest = new GrailsWebRequest(
                mockHttpRequest,
                new MockHttpServletResponse(),
                new MockServletContext())

        WebUtils.storeGrailsWebRequest(mockWebRequest)

        expect:
        service.isChrome()
        service.isChrome(ComparisonType.EQUAL, "14.0.835.202")
        service.isChrome(ComparisonType.GREATER, "13.0.835")
        service.isChrome(ComparisonType.LOWER, "15")

    }

    void "Testing ComparisonType for firefox" (){
        given:
        MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest()
        mockHttpRequest.addHeader(service.USER_AGENT_REQUEST_ATTRIBUTE, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0")
        GrailsWebRequest mockWebRequest = new GrailsWebRequest(
                mockHttpRequest,
                new MockHttpServletResponse(),
                new MockServletContext())

        WebUtils.storeGrailsWebRequest(mockWebRequest)

        expect:
        service.isFirefox()
        service.isFirefox(ComparisonType.EQUAL, "9.0")
        service.isFirefox(ComparisonType.GREATER, "8.0")
        service.isFirefox(ComparisonType.LOWER, "10.10")

    }


	void "test Old Api"(){
        given:
        MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest()
        mockHttpRequest.addHeader(service.USER_AGENT_REQUEST_ATTRIBUTE, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0")
        GrailsWebRequest mockWebRequest = new GrailsWebRequest(
                mockHttpRequest,
                new MockHttpServletResponse(),
                new MockServletContext())

        WebUtils.storeGrailsWebRequest(mockWebRequest)

		assert service.getBrowserType() == service.CLIENT_FIREFOX
		assert service.getBrowserName() == service.FIREFOX
	}

}