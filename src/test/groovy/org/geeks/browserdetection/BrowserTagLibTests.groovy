package org.geeks.browserdetection

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(BrowserTagLib)
class BrowserTagLibTests extends Specification {

	void setup() {
		defineBeans {
			userAgentIdentService(UserAgentIdentService)
		}
	}

	void 'for some reason the service is not injected in the first test method, so this is a no-op'() {
		expect: true
	}

	void 'chrome'() {
		when:
		userAgent "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.202 Safari/535.1"

		then:
		"chrome" == applyTemplate(
			"<browser:isFirefox>firefox</browser:isFirefox>" +
			"<browser:isChrome>chrome</browser:isChrome>" +
			"<browser:isMobile>mobile</browser:isMobile>")
	}

	void 'version comparison equal IE6'() {
		when:
		userAgent "Mozilla/4.08 (compatible; MSIE 6.0; Windows NT 5.1)"

		then:
		"MSIE 6.0" == applyTemplate(
			"<browser:isMsie version='6.0'>MSIE 6.0</browser:isMsie>" +
			"<browser:isMsie version='7.0'>MSIE 7.0</browser:isMsie>" +
			"<browser:isFirefox>firefox</browser:isFirefox>" +
			"<browser:isChrome>chrome</browser:isChrome>" +
			"<browser:isMobile>mobile</browser:isMobile>")

		"MSIE 6.0" == applyTemplate("<browser:isIE6>MSIE 6.0</browser:isIE6>")
	}

	void 'IE 7'() {
		when:
		userAgent "Mozilla/5.0 (MSIE 7.0; Macintosh; U; SunOS; X11; gu; SV1; InfoPath.2; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648)"

		then:
		"MSIE 7.0" == applyTemplate("<browser:isIE7>MSIE 7.0</browser:isIE7>")
	}

	void 'IE 7b'() {
		when:
		userAgent "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 6.0)"

		then:
		"MSIE 7.0" == applyTemplate("<browser:isIE7>MSIE 7.0</browser:isIE7>")
	}

	void 'IE 8'() {
		when:
		userAgent "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 5.0; Trident/4.0; InfoPath.1; SV1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 3.0.04506.30)"

		then:
		"MSIE 8.0" == applyTemplate("<browser:isIE8>MSIE 8.0</browser:isIE8>")
	}

	void 'IE 9'() {
		when:
		userAgent "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)"

		then:
		"MSIE 9.0" == applyTemplate("<browser:isIE9 version='9.0'>MSIE 9.0</browser:isIE9>")
	}

	void 'version comparison less IE7'() {
		when:
		userAgent "Mozilla/4.08 (compatible; MSIE 6.0; Windows NT 5.1)"

		then:
		"lower MSIE 7.0" == applyTemplate(
			"<browser:isMsie versionLower='7.0'>lower MSIE 7.0</browser:isMsie>" +
			"<browser:isMsie versionLower='6.0'>lower MSIE 6.0</browser:isMsie>" +
			"<browser:isFirefox>firefox</browser:isFirefox>" +
			"<browser:isChrome>chrome</browser:isChrome>" +
			"<browser:isMobile>mobile</browser:isMobile>")
	}

	void 'version comparison more IE6'() {
		when:
		userAgent "Mozilla/5.0 (MSIE 7.0; Macintosh; U; SunOS; X11; gu; SV1; InfoPath.2; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648)"

		then:
		"greater MSIE 6.0" == applyTemplate(
			"<browser:isMsie versionGreater='6.0'>greater MSIE 6.0</browser:isMsie>" +
			"<browser:isMsie versionGreater='7.0'>greater MSIE 7.0</browser:isMsie>" +
			"<browser:isFirefox>firefox</browser:isFirefox>" +
			"<browser:isChrome>chrome</browser:isChrome>" +
			"<browser:isMobile>mobile</browser:isMobile>")
	}

	void 'version comparison Safari5'() {
		when:
		userAgent "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-HK) AppleWebKit/533.18.1 (KHTML, like Gecko) Version/5.0.2 Safari/533.18.5"

		then:
		"Safari 5.*" == applyTemplate(
			"<browser:isSafari version='5.*'>Safari 5.*</browser:isSafari>" +
			"<browser:isMsie versionGreater='7.0'>greater MSIE 7.0</browser:isMsie>" +
			"<browser:isFirefox>firefox</browser:isFirefox>" +
			"<browser:isChrome>chrome</browser:isChrome>" +
			"<browser:isMobile>mobile</browser:isMobile>")
	}

	void 'other'() {
		when:
		userAgent "undefined (undefined; undefined)"

		then:
		"undefined" == applyTemplate("<browser:isOther>undefined</browser:isOther>")
	}

	private void userAgent(String value) {
		request.addHeader "user-agent", value
	}
}
