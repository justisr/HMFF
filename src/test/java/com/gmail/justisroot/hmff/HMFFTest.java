package com.gmail.justisroot.hmff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class HMFFTest extends FileTest {

	@Test
	@Order(10)
	@DisplayName("Test setting and getting root value")
	void testSetGetRootValue() {
		hmff.set("value");
		Optional<String> value = hmff.getString();
		assertTrue(value.isPresent());
		assertEquals("", value.get(), "Root value \"" + value + "\" should be empty");
	}

	@Test
	@Order(20)
	@DisplayName("Test saving root value")
	void testSaveRootValue() {
		hmff.save();
		assertEquals("", fileContents(), "File contents \"" + hmff + "\" should be empty");
	}

	@Test
	@Order(30)
	@DisplayName("Test setting and getting key/value pair")
	void testSetGet() {
		hmff.set("value", "key");
		Optional<String> value = hmff.getString("key");
		assertTrue(value.isPresent());
		assertEquals("value", value.get());
	}

	@Test
	@Order(40)
	@DisplayName("Test saving key/value pair")
	void testSave() {
		testSetGet();
		hmff.save();
		assertEquals("key: value", fileContents());
	}

	@Test
	@Order(50)
	@DisplayName("Test getting and setting inner key/value pair")
	void testGetSetInner() {
		testSetGet();
		hmff.set("inner value", "key", "inner");
		Optional<String> value = hmff.getString("key", "inner");
		assertTrue(value.isPresent());
		assertEquals("inner value", value.get());
	}

	@Test
	@Order(60)
	@DisplayName("Test section removal")
	void removal() {
		hmff.set("removed", "for", "removal");
		hmff.removeSection("for");
		assertTrue(hmff.getSection("for").isEmpty(), "Section should have been removed, but is still present");
	}

	@Test
	@Order(70)
	@DisplayName("Test saving with inner key/value pair")
	void testSaveInner() {
		testGetSetInner();
		hmff.save();
		assertEquals("key: value\n\n  inner: inner value", fileContents());
	}

	private void validateReload() {
		hmff.reload();
		Optional<String> value = hmff.getString("key");
		assertTrue(value.isPresent());
		assertEquals("value", value.get());
		value = hmff.getString("key", "inner");
		assertTrue(value.isPresent());
		assertEquals("inner value", value.get());
	}

	@Test
	@Order(80)
	@DisplayName("Test reload with inner key/value pair")
	void testReload() {
		testSaveInner();
		validateReload();
	}

	@Test
	@Order(90)
	@DisplayName("Test InputStream save and re-get")
	void saveUsingInputStream() throws IOException {
		testSaveInner();
		FileInputStream fis = new FileInputStream(hmff.getFile());
		hmff.save(new ByteArrayInputStream(fis.readAllBytes()));
		fis.close();
		validateReload();
	}

}
