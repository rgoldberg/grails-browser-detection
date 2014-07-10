package org.geeks.browserdetection

class VersionHelperTests extends GroovyTestCase {

	void testVersionEquals(){
		assert VersionHelper.equals("1.23.2", "1.23.2")
		assert !VersionHelper.equals("1.23.2", "1.23.21")
		assert !VersionHelper.equals("1.23.2", "2.23.2")
		assert VersionHelper.equals("1.23.2", "1.23.2.0")
		assert VersionHelper.equals("1.2.*", "1.2.3")
		assert VersionHelper.equals("1.2.3", "1.2.*")
		assert VersionHelper.equals("1.2.31", "1.2.3*")
	}

	void testCompare(){
		assert 0 == VersionHelper.compare("1.2.3", "1.2.3")
		assert 0 == VersionHelper.compare("5.23.2.0.0", "5.23.2")
		assert 1 == VersionHelper.compare("1.2.3.4", "1.2.3")
		assert 1 == VersionHelper.compare("1.2.3.4", "1.2.3.1")
		assert -1 == VersionHelper.compare("4", "5")
		assert -1 == VersionHelper.compare("4.1", "5.1")
	}
}