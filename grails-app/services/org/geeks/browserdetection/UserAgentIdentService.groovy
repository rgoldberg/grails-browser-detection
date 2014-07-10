package org.geeks.browserdetection

import eu.bitwalker.useragentutils.UserAgent
import eu.bitwalker.useragentutils.Browser
import eu.bitwalker.useragentutils.OperatingSystem
import eu.bitwalker.useragentutils.RenderingEngine
import eu.bitwalker.useragentutils.BrowserType
import eu.bitwalker.useragentutils.Version
import org.codehaus.groovy.grails.web.util.WebUtils

import javax.servlet.http.HttpServletRequest

class UserAgentIdentService {

    static transactional = false

	final static String CHROME = "chrome"
    final static String FIREFOX = "firefox"
    final static String SAFARI = "safari"
    final static String OTHER = "other"
    final static String MSIE = "msie"
    final static String UNKNOWN = "unknown"
    final static String BLACKBERRY = "blackberry"
    final static String SEAMONKEY = "seamonkey"
    final static String OPERA = "opera"

    final static int CLIENT_CHROME = 0
    final static int CLIENT_FIREFOX = 1
    final static int CLIENT_SAFARI = 2
    final static int CLIENT_OTHER = 3
    final static int CLIENT_MSIE = 4
    final static int CLIENT_UNKNOWN = 5
    final static int CLIENT_BLACKBERRY = 6
    final static int CLIENT_SEAMONKEY = 7
    final static int CLIENT_OPERA = 8

	final static def MOBILE_BROWSERS = [OperatingSystem.iOS4_IPHONE, OperatingSystem.iOS5_IPHONE, OperatingSystem.MAC_OS_X_IPAD,
			OperatingSystem.MAC_OS_X_IPHONE, OperatingSystem.MAC_OS_X_IPOD, OperatingSystem.BADA, OperatingSystem.PSP]
	final static def MOBILE_BROWSER_GROUPS = [OperatingSystem.ANDROID, OperatingSystem.BLACKBERRY, OperatingSystem.KINDLE, OperatingSystem.SYMBIAN]
    public static final String USER_AGENT_REQUEST_ATTRIBUTE = "User-Agent"

    HttpServletRequest getRequest(){
        WebUtils.retrieveGrailsWebRequest().currentRequest
    }

	/**
	 * Returns user-agent header value from thread-bound RequestContextHolder
	 */
	String getUserAgentString() {
        request.getHeader(USER_AGENT_REQUEST_ATTRIBUTE) ?: ""
	}

	/**
	 * Returns user-agent header value from the passed request
	 */
    @Deprecated
	String getUserAgentString(HttpServletRequest request) {
		request.getHeader(USER_AGENT_REQUEST_ATTRIBUTE)
	}

	private UserAgent getUserAgent() {
        // Avoid parsing the user-agent multiple times during the same request
        UserAgent userAgent = request.getAttribute('user') as UserAgent
        if(!userAgent) {
            userAgent = UserAgent.parseUserAgentString(userAgentString)
            request.setAttribute('userAgent', userAgent)
        }
        userAgent
	}

	boolean isChrome(ComparisonType comparisonType = null, String version = null) {
		isBrowser(Browser.CHROME, comparisonType, version)
	}

	boolean isFirefox(ComparisonType comparisonType = null, String version = null) {
		isBrowser(Browser.FIREFOX, comparisonType, version)
	}

	boolean isMsie(ComparisonType comparisonType = null, String version = null) {
		// why people use it?
		isBrowser(Browser.IE, comparisonType, version)
	}

	boolean isSafari(ComparisonType comparisonType = null, String version = null) {
		isBrowser(Browser.SAFARI, comparisonType, version)
	}

	boolean isOpera(ComparisonType comparisonType = null, String version = null) {
		isBrowser(Browser.OPERA, comparisonType, version)
	}

	/**
	 * Returns true if browser is unknown
	 */
	boolean isOther() {
		isBrowser(Browser.UNKNOWN)
	}

	private boolean isBrowser(Browser browserForChecking, ComparisonType comparisonType = null,
	                          String version = null){
		UserAgent userAgent = userAgent
		Browser browser = userAgent.browser
        Version browserVersion = userAgent.browserVersion

		// browser checking
		if(!(browser.group == browserForChecking || browser == browserForChecking)){
			return false
		}

		// version checking
		if(version){
			if(!comparisonType){
				throw new IllegalArgumentException("comparisonType should be specified")
			}

			if(comparisonType == ComparisonType.EQUAL){
				return VersionHelper.equals(browserVersion.version, version)
			}

			def compRes = VersionHelper.compare(browserVersion.version, version)

			if(compRes == 1 && comparisonType == ComparisonType.GREATER){
				return true
			}

			if(compRes == -1 && comparisonType == ComparisonType.LOWER){
				return true
			}

			return false
		}

        return true
	}

	private boolean isOs(OperatingSystem osForChecking){
		OperatingSystem os = userAgent.operatingSystem

		os.group == osForChecking || os == osForChecking
	}

	boolean isiPhone() {
        OperatingSystem os = userAgent.operatingSystem

		os == OperatingSystem.iOS4_IPHONE || os == OperatingSystem.MAC_OS_X_IPHONE
	}

	boolean isiPad() {
		isOs(OperatingSystem.MAC_OS_X_IPAD)
	}

	boolean isiOsDevice() {
		isOs(OperatingSystem.IOS)
	}

	boolean isAndroid() {
		isOs(OperatingSystem.ANDROID)
	}

	boolean isPalm() {
		isOs(OperatingSystem.PALM)
	}

	boolean isLinux(){
		isOs(OperatingSystem.LINUX)
	}

	boolean isWindows(){
		isOs(OperatingSystem.WINDOWS)
	}

	boolean isOSX(){
		isOs(OperatingSystem.MAC_OS_X)
	}

	boolean isWebkit() {
		userAgent.browser.renderingEngine == RenderingEngine.WEBKIT
	}

	boolean isWindowsMobile() {
		def os = userAgent.operatingSystem

		os == OperatingSystem.WINDOWS_MOBILE || os == OperatingSystem.WINDOWS_MOBILE7
	}

	boolean isBlackberry() {
		isOs(OperatingSystem.BLACKBERRY)
	}

	boolean isSeamonkey() {
		isBrowser(Browser.SEAMONKEY)
	}

	/**
	 * Returns true if client is a mobile phone or any Android device, iPhone, iPad, iPod, PSP, Blackberry, Bada device
	 */
	boolean isMobile() {
		UserAgent userAgent = userAgent
		OperatingSystem os = userAgent.operatingSystem

		userAgent.browser.browserType == BrowserType.MOBILE_BROWSER || os in MOBILE_BROWSERS ||
				(os.group && os.group in MOBILE_BROWSER_GROUPS)
	}

	/**
	 * Returns the browser name.
	 */
	String getBrowser(){
        userAgent.browser.name
	}

	/**
	 * It is left for compatibility reasons. Use {@link #getBrowser() } instead.
	 */
	@Deprecated
	String getBrowserName(){
		switch (getBrowserType()) {
			case CLIENT_FIREFOX:
				return FIREFOX;
			case CLIENT_CHROME:
				return CHROME;
			case CLIENT_SAFARI:
				return SAFARI;
			case CLIENT_SEAMONKEY:
				return SEAMONKEY;
			case CLIENT_MSIE:
				return MSIE;
			case CLIENT_BLACKBERRY:
				return BLACKBERRY;
			case CLIENT_OPERA:
				return OPERA;
			case CLIENT_OTHER:
			case CLIENT_UNKNOWN:
			default:
				return OTHER;
		}
	}

	String getBrowserVersion() {
        userAgent.browserVersion?.version
	}

	String getOperatingSystem() {
        userAgent.operatingSystem?.name
	}

	@Deprecated
	String getPlatform() {
        userAgent.operatingSystem
	}

	@Deprecated
	int getBrowserType() {
		Browser browser = userAgent.browser
		browser = browser.group ? browser.group : browser

		switch (browser){
			case Browser.FIREFOX:
				return CLIENT_FIREFOX;
			case Browser.CHROME:
				return CLIENT_CHROME;
			case Browser.SAFARI:
				return CLIENT_SAFARI;
			case Browser.SEAMONKEY:
				return CLIENT_SEAMONKEY;
			case Browser.IE:
				return CLIENT_MSIE;
			case Browser.OPERA:
				return CLIENT_OPERA;
		}

		if(userAgent.operatingSystem == OperatingSystem.BLACKBERRY){
			return CLIENT_BLACKBERRY
		}

		return CLIENT_OTHER
	}

	/**
	 * It is left for compatibility reasons.
	 */
	@Deprecated
	def getUserAgentInfo() {
        UserAgent userAgent = userAgent

		[
			browserType: getBrowserType(),
			browserVersion: userAgent.browserVersion.version,
			operatingSystem: userAgent.operatingSystem.name,
			platform: "",
			security: "",
			language: "",
			agentString: userAgent.userAgentString
		]
	}
}

public enum ComparisonType {
	LOWER,
	EQUAL,
	GREATER
}