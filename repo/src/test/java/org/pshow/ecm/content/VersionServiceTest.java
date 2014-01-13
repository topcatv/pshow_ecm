package org.pshow.ecm.content;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pshow.ecm.SpringTransactionalTestCase;
import org.pshow.ecm.content.exception.CheckInException;
import org.pshow.ecm.content.metadata.ContentSchemaHolder;
import org.pshow.ecm.content.model.PropertyValue;
import org.pshow.ecm.content.model.Version;
import org.pshow.ecm.content.model.VersionHistory;
import org.pshow.ecm.content.model.VersionStrategy;
import org.pshow.ecm.utils.TestDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class VersionServiceTest extends SpringTransactionalTestCase {
	private VersionService versionService;
	private ContentService contentService;
	private ContentSchemaHolder csh;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private String createDefaultWorkspace() {
		String workspace_name = "default";
		if (contentService.findWorkspace(workspace_name) == null) {
			contentService.createWorkspace(workspace_name);
		}
		return workspace_name;
	}

	@Test
	public void testGetVersion() {
		String contentId = createContent();
		boolean checkOut = versionService.checkOut(contentId);
		String label = "测试版本";
		Version checkIn = null;
		if (checkOut) {
			checkIn = versionService.checkIn(contentId, label,
					VersionStrategy.major);
		}
		Version version = versionService.getVersion(contentId, label);

		assertNotNull(checkIn);
		assertEquals(checkIn, version);
	}

	@Test
	public void testCheckOut() {
		String contentId = createContent();
		boolean checkOut = versionService.checkOut(contentId);

		assertTrue(checkOut);

		checkOut = versionService.checkOut(contentId);

		assertFalse(checkOut);
	}

	@Test
	public void testCancelCheckOut() {
		String contentId = createContent();
		boolean cancelCheckOut = versionService.cancelCheckOut(contentId);

		assertFalse(cancelCheckOut);

		boolean checkOut = versionService.checkOut(contentId);

		assertTrue(checkOut);

		cancelCheckOut = versionService.cancelCheckOut(contentId);

		assertTrue(cancelCheckOut);
	}

	private String createContent() {
		String workspace_name = createDefaultWorkspace();
		String type = "test:TestType";
		String parentId = contentService.getRoot(workspace_name);
		String name = "版本";
		Map<String, PropertyValue> properties = TestDataLoader.loadData(
				"test_version", csh);
		String contentId = contentService.createContent(type, parentId, name,
				properties);
		return contentId;
	}

	@Test(expected=CheckInException.class)
	public void testCheckIn() {
		String contentId = createContent();
		String comments = "comments for first";
		try {
			versionService.checkIn(contentId, comments, VersionStrategy.major);
			fail("not to here");
		} catch (IllegalStateException e) {
			assertEquals("content wasn't checkout state", e.getMessage());
		}

		boolean checkOut = versionService.checkOut(contentId);
		if (checkOut) {
			Version checkIn = versionService.checkIn(contentId, comments,
					VersionStrategy.major);
			assertNotNull(checkIn);
			Version version = versionService.getVersion(contentId, comments);
			assertEquals(checkIn, version);
			assertEquals("1.0", version.getNumber());
		} else {
			fail("checkout fail");
		}
		
		checkOut = versionService.checkOut(contentId);
		if (checkOut) {
			comments = "comment for second";
			Version checkIn = versionService.checkIn(contentId, comments,
					VersionStrategy.minor);
			assertNotNull(checkIn);
			Version version = versionService.getVersion(contentId, comments);
			assertEquals(checkIn, version);
			assertEquals("1.1", version.getNumber());
		} else {
			fail("checkout fail");
		}
		
		checkOut = versionService.checkOut(contentId);
		if (checkOut) {
			//同样的 comments抛出异常
			versionService.checkIn(contentId, comments,	VersionStrategy.minor);
		} else {
			fail("checkout fail");
		}
	}

	@Test
	public void testGetVersionHistory() {
		String contentId = createContent();
		String comments = "comments for first";
		boolean checkOut = versionService.checkOut(contentId);
		Version first = null;
		if (checkOut) {
			first = versionService.checkIn(contentId, comments,
					VersionStrategy.major);
			assertNotNull(first);
			Version version = versionService.getVersion(contentId, comments);
			assertEquals(first, version);
			assertEquals("1.0", version.getNumber());
		} else {
			fail("checkout fail");
		}
		checkOut = versionService.checkOut(contentId);
		Version second = null;
		if (checkOut) {
			comments = "comments for second";
			second = versionService.checkIn(contentId, comments,
					VersionStrategy.minor);
			assertNotNull(second);
			Version version = versionService.getVersion(contentId, comments);
			assertEquals(second, version);
			assertEquals("1.1", version.getNumber());
		} else {
			fail("checkout fail");
		}
		VersionHistory versionHistory = versionService.getVersionHistory(contentId);
		assertNotNull(versionHistory);
		assertEquals(2, versionHistory.getAllVersions().size());
		assertEquals(first, versionHistory.getFirstVersion());
		assertEquals(second, versionHistory.getLastVersion());
		assertEquals(first, versionHistory.getVersion("1.0"));
		assertEquals(second, versionHistory.getVersion("1.1"));
	}

	@Test
	public void testRestore() {
		String contentId = createContent();
		String comments = "comments for first";
		boolean checkOut = versionService.checkOut(contentId);
		Version first = null;
		if (checkOut) {
			first = versionService.checkIn(contentId, comments,
					VersionStrategy.major);
			assertNotNull(first);
			Version version = versionService.getVersion(contentId, comments);
			assertEquals(first, version);
			assertEquals("1.0", version.getNumber());
		} else {
			fail("checkout fail");
		}
		checkOut = versionService.checkOut(contentId);
		Version second = null;
		if (checkOut) {
			comments = "comments for second";
			second = versionService.checkIn(contentId, comments,
					VersionStrategy.minor);
			
			PropertyValue value = new PropertyValue("test001");
			contentService.setProperty(contentId, "test:username", value);
			
			assertNotNull(second);
			Version version = versionService.getVersion(contentId, comments);
			assertEquals(second, version);
			assertEquals("1.1", version.getNumber());
			PropertyValue property = contentService.getProperty(contentId, "test:username");
			
			assertEquals(value, property);
		} else {
			fail("checkout fail");
		}
		
		//restor默认版本升级，升级主版本，label和版本号相同
		versionService.restore(contentId, "comments for first");
		
		PropertyValue property = contentService.getProperty(contentId, "test:username");
		assertEquals(new PropertyValue("中文试试"), property);
		
		VersionHistory versionHistory = versionService.getVersionHistory(contentId);
		assertEquals(3, versionHistory.getAllVersions().size());
		assertEquals("2.1", versionHistory.getLastVersion().getLabel());
		assertEquals("2.1", versionHistory.getLastVersion().getNumber());
	}

	@Autowired
	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}

	@Autowired
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	@Autowired
	public void setCsh(ContentSchemaHolder csh) {
		this.csh = csh;
	}

}
