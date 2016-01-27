package org.geeks.browserdetection

import spock.lang.Specification

class VersionHelperTests extends Specification {

	void 'version equals'() {
		expect:
		VersionHelper.equals("1.23.2", "1.23.2")
		!VersionHelper.equals("1.23.2", "1.23.21")
		!VersionHelper.equals("1.23.2", "2.23.2")
		VersionHelper.equals("1.23.2", "1.23.2.0")
		VersionHelper.equals("1.2.*", "1.2.3")
		VersionHelper.equals("1.2.3", "1.2.*")
		VersionHelper.equals("1.2.31", "1.2.3*")
	}

	void 'compare'() {
		expect:
		0 == VersionHelper.compare("1.2.3", "1.2.3")
		0 == VersionHelper.compare("5.23.2.0.0", "5.23.2")
		1 == VersionHelper.compare("1.2.3.4", "1.2.3")
		1 == VersionHelper.compare("1.2.3.4", "1.2.3.1")
		-1 == VersionHelper.compare("4", "5")
		-1 == VersionHelper.compare("4.1", "5.1")
	}
}
