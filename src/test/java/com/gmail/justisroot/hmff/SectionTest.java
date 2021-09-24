package com.gmail.justisroot.hmff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class SectionTest {

	private Section section;

	@BeforeEach
	public void createSection() {
		this.section = new Section("root");
		section.addChild("parent", "parentvalue").addChild("child", "childvalue");
	}

	@Test
	@DisplayName("Test get create")
	public void testGetCreate() {
		section.getOrCreateSection("parent", "sibling");
		assertTrue(section.sectionExists("parent", "sibling"));
	}

	@Test
	@DisplayName("Test root")
	public void testRoot() {
		assertEquals(section.getSection("parent", "child").get().getRoot(), section, "The root of the parent's child should be the root");
	}

	@Test
	@DisplayName("Test section exists")
	public void testSectionExists() {
		assertTrue(section.sectionExists("parent", "child"), "The section should exist");
	}

	@Test
	@DisplayName("Test rename section")
	public void testRenameSection() {
		Section child = section.getSection("parent", "child").get();
		section.renameSection("kid", "parent", "child");
		assertEquals("kid", child.key, "Child section was not renamed");
	}

	@Test
	@DisplayName("Test copy to")
	public void testCopyTo() {
		Section child = section.getOrCreateSection("parent", "child", "grandchild");
		child.setValue("grandchildvalue");
		child.parent.get().copyTo(section);
		assertEquals("childvalue", section.getString("parent", "child").get(), "Child section should remain");
		assertEquals("childvalue", section.getString("child").get(), "Child section should have been copied");
		assertEquals("grandchildvalue", section.getString("parent", "child", "grandchild").get(), "Grandchild section should remain");
		assertEquals("grandchildvalue", section.getString("child", "grandchild").get(), "Grandchild section should have been copied");
	}


}
