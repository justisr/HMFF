package com.gmail.justisroot.hmff;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInfo;

public abstract class FileTest {

	static final String TEST_FOLDER = "target" + File.separator + MethodHandles.lookup().lookupClass().getSimpleName() + " output" + File.separator;

	HMFF hmff;

	String fileContents() {
		try {
			return Files.readAllLines(hmff.getFile().toPath(), StandardCharsets.UTF_8).stream().collect(Collectors.joining("\n"));
		} catch (IOException e) {
			return "ERROR";
		}
	}

	@Order(0)
	@BeforeEach
	public void createFile(TestInfo info) {
		hmff = new HMFF(new File(TEST_FOLDER + info.getDisplayName().replace(File.separatorChar, '|') + ".hmff"));
	}

	@AfterAll
	public static void cleanup() {
		File folder = new File(TEST_FOLDER);
		for(String s : folder.list()) new File(TEST_FOLDER + s).delete();
		folder.delete();
	}

	static final <T> T o(T o) {
		System.out.println("----------------------------------------");
		System.out.println(o.toString());
		System.out.println("----------------------------------------");
		return o;
	}

}
