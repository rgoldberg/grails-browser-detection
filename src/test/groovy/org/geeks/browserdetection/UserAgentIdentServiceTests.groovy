package org.geeks.browserdetection

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import spock.lang.Specification

@TestFor(UserAgentIdentService)
@TestMixin(ControllerUnitTestMixin)
class UserAgentIdentServiceTests extends Specification {

	void 'firefox 3.6.9'() {
		when:
		userAgent "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-GB; rv:1.9.2.9) Gecko/20100824 Firefox/3.6.9 ( .NET CLR 3.5.30729; .NET CLR 4.0.20506)"

		then:
		service.isFirefox()
		!service.isChrome()
		!service.isSafari()
		!service.isiOsDevice()
		!service.isMobile()
		service.getBrowser() == "Firefox 3"
		service.getBrowserVersion() == "3.6.9"
		!service.isOther()
    }

	void testChrome14_0_835_202() {
		when:
		userAgent "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.202 Safari/535.1"

		then:
		!service.isFirefox()
		service.isChrome()
		!service.isSafari()
		!service.isiOsDevice()
		!service.isMobile()
		service.getBrowser() == "Chrome 14"
		service.getBrowserVersion() == "14.0.835.202"
		!service.isOther()
	}

	void testMSIE7() {
		when:
		userAgent "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB6.4; .NET CLR 1.1.4322; FDM; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)"

		then:
		!service.isFirefox()
		!service.isChrome()
		!service.isSafari()
		service.isMsie()
		!service.isiOsDevice()
		!service.isMobile()
		service.getBrowser() == "Internet Explorer 7"
		service.getBrowserVersion() == "7.0"
	}

	void testMSIE6() {
		when:
		userAgent "Mozilla/4.0 (compatible; MSIE 6.0; Windows 98; Rogers Hi·Speed Internet; (R1 1.3))"

		then:
		!service.isFirefox()
		!service.isChrome()
		!service.isSafari()
		service.isMsie()
		!service.isiOsDevice()
		!service.isMobile()
		service.getBrowser() == "Internet Explorer 6"
		service.getBrowserVersion() == "6.0"
	}

	void testIPadSafari4_0_4() {
		when:
		userAgent "Mozilla/5.0(iPad; U; CPU iPhone OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B314 Safari/531.21.10"

		then:
		!service.isFirefox()
		!service.isChrome()
		service.isSafari()
		!service.isMsie()
		service.isiOsDevice()
		service.isiPad()
		service.isMobile()
		service.getBrowser() == "Mobile Safari"
		service.getBrowserVersion() == "4.0.4"
	}

	void testIPhoneSafari4_0_4() {
		when:
		userAgent "Mozilla/5.0 (iPhone; U; CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b Safari/531.21.10"

		then:
		!service.isFirefox()
		!service.isChrome()
		service.isSafari()
		!service.isMsie()
		service.isiOsDevice()
		service.isiPhone()
		service.isMobile()
		service.getBrowser() == "Mobile Safari"
		service.getBrowserVersion() == "4.0.4"
	}

	void testChrome14_0_835_202andVersionChecking() {
		when:
		userAgent "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.202 Safari/535.1"

		then:
		service.isChrome()
		service.isChrome(ComparisonType.EQUAL, "14.0.835.202")
		service.isChrome(ComparisonType.GREATER, "13.0.835")
		service.isChrome(ComparisonType.LOWER, "15")
	}

	void testFirefox9_0andVersionChecking() {
		when:
		userAgent "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0"

		then:
		service.isFirefox()
		service.isFirefox(ComparisonType.EQUAL, "9.0")
		service.isFirefox(ComparisonType.GREATER, "8.0")
		service.isFirefox(ComparisonType.LOWER, "10.10")
	}

	void testOldApi() {
		when:
		userAgent "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0"

		then:
		service.getBrowserType() == UserAgentIdentService.CLIENT_FIREFOX
		service.getBrowserName() == UserAgentIdentService.FIREFOX
	}

	void testIsMobileIPhone() {
		when:
		userAgent "Mozilla/5.0 (iPod; U; CPU iPhone OS 4_3_3 like Mac OS X; ja-jp) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5"

		then:
		service.isMobile()
	}

	void testIsMobileBlackberry() {
		when:
		userAgent "Mozilla/5.0 (BlackBerry; U; BlackBerry 9800; zh-TW) AppleWebKit/534.1+ (KHTML, like Gecko) Version/6.0.0.246 Mobile Safari/534.1+"

		then:
		service.isMobile()
	}

	void testIsMobileAndroid() {
		when:
		userAgent "Mozilla/5.0 (Linux; U; Android 2.3; en-us) AppleWebKit/999+ (KHTML, like Gecko) Safari/999.9"

		then:
		service.isMobile()
	}

	/**
	 * Tests for proper handling of the case of unset user-agent header
	 */
	void testNullUserAgentHeader() {
		expect:
		request.getHeader("user-agent") == null
		!service.isMobile()
		service.isOther()
	}

	void testIsOther() {
		when:
		userAgent "abs; abc"

		then:
		service.isOther()
		!service.isChrome()
		!service.isMobile()
	}

	private void userAgent(String value) {
		request.addHeader "user-agent", value
	}
}
