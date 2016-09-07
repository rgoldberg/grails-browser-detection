# grails-browser-detection
[![Build Status](https://travis-ci.org/mathifonseca/grails-browser-detection.svg)](https://travis-ci.org/mathifonseca/grails-browser-detection)

This plugin provides a service and tag library for browser detection. It parses User-Agent in order to identify client's browser, operating system, etc. It depends on [user-agent-utils](https://github.com/HaraldWalker/user-agent-utils) library from [HaraldWalker](https://github.com/HaraldWalker).
If you want to contribute, report issues or just check the code. You can find it at GitHub.

Here are some of the things that you can currently do with this plugin. All this operations can be used from GSPs or you can use the `UserAgentIdentService` anywhere in your code.

Using `UserAgentIdentService`:

```groovy
class TestController {

    def userAgentIdentService
  
    def index() {
        if (userAgentIdentService.isMobile()) {
            println 'Hello mobile device!'
            if (userAgentIdentService.isWindowsPhone()) {
                println 'Wow! Does this still exist?'
            }
        } else {
            println 'Hello desktop browser!'
            if (userAgentIdentService.isInternetExplorer()) {
                println 'Redirecting to Chrome download page...'
            }
        }
    }

}
```

Detecting Browsers

```gsp
<browser:isMsie> This is Internet Explorer </browser:isMsie>
<browser:isSafari> This is Safari </browser:isSafari>
<browser:isChrome> This is Chrome </browser:isChrome>
<browser:isFirefox> This is Firefox </browser:isFirefox>
<browser:isOpera> This is Opera </browser:isOpera>
```

Detecting Devices

```gsp
<browser:isiPhone> This is iPhone </browser:isiPhone>
<browser:isiPad> This is iPad </browser:isiPad>
<browser:isMobile> Mobile phones or Android, iPhone, iPad, iPod, Blackberry, etc. </browser:isMobile>
```

Detecting Operative Systems

```gsp
<browser:isWindows> This is Windows </browser:isWindows>
```

Other operations

You can use the following structure that emulates switch behavior:

```gsp
<browser:choice>
	<browser:isChrome></browser:isChrome>
	<browser:isIE6></browser:isIE6>
	<browser:isIE7></browser:isIE7>
	<browser:otherwise></browser:otherwise>
</browser:choice>
```

Or the one below:

```gsp
<browser:isSafari versionGreater="5">
	This text is rendered if Safari version is greater than 5.
	For example, 5.0.1, 5.1
</browser:isSafari>
<browser:isFirefox version="3.*">
	It works for all Firefox versions like 3.1, 3.6 and so on
</browser:isFirefox>
<browser:isMsie versionLower="7">
	Internet Explorer 5.0, Internet Explorer 6.0
</browser:isMsie>
```

At the moment, wildcards are allowed only for version attribute. Be aware that 5.1 is greater than 5 and 5.0 equals to 5.

All of these taglibs have their negative assert by starting with `isNot`. For example:
```gsp
<browser:isNotSafari>
	This text is rendered if the browser IS NOT Safari.
</browser:isNotSafari>
<browser:isNotFirefox>
	This text is rendered if the browser IS NOT Firefox.
</browser:isNotFirefox>
<browser:isNotMsie>
	This text is rendered if the browser IS NOT Internet Explorer.
</browser:isNotMsie>
<browser:isNotChrome>
	This text is rendered if the browser IS NOT Chrome.
</browser:isNotChrome>
```
