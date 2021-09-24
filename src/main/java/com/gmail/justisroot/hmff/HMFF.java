package com.gmail.justisroot.hmff;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HMFF extends Section {

	static final Pattern NON_VALUE_MATCH_REGEX = Pattern.compile(".*?" + VALUE_SEPARATOR + "\\s?");

	private File file;

	/**
	 * Create a {@link HMFF} interpretation for the provided {@linkplain File}, creating one if necessary.
	 *
	 * @param file The {@linkplain File} to wrap and interpret
	 */
	public HMFF(File file) {
		super(file.getName().substring(0, file.getName().indexOf(".") > 0 ? file.getName().indexOf(".") : 0));
		this.file = file;
		reload();
	}

	/**
	 * The root {@link Section} is always empty. Only sub-sections have values.
	 */
	@Override
	void setValue(String value) { }

	/**
	 * {@inheritDoc}<br>
	 * <br>
	 * Only sub-sections have values, so leaving {@code path} blank will produce no results.
	 */
	@Override
	public void set(Object value, String... path) {
		super.set(value, path);
	}

	/**
	 * Get the raw {@linkplain File} object wrapped by this {@link HMFF} instance.<br>
	 *
	 * @return The {@linkplain File} wrapped by this {@link HMFF} instance.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Set the raw {@linkplain File} associated with this {@link HMFF} instance.<br>
	 *
	 * @param file The file to wrap
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Load the file's contents into memory.<br>
	 * <br>
	 * Overwrites any existing contents in memory.<br>
	 * Gets the file from disk or generates one if it doesn't exist.
	 *
	 * @return true if the file contents were successfully loaded into memory, false otherwise
	 */
	public boolean reload() {
		try {
			ensure(this.file);
			RandomAccessFile raf = new RandomAccessFile(this.file, "r");
			MappedByteBuffer buffer = raf.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, raf.length()).load();
			byte[] array = new byte[buffer.capacity()];
			buffer.get(array);
			raf.close();
			return reload(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(array))));
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Load the contents of the {@linkplain BufferedReader} into memory.<br>
	 * <br>
	 * Overwrites any existing contents in memory.<br>
	 *
	 * @param reader The reader to load into memory
	 *
	 * @return true if the reader contents were successfully loaded into memory, false otherwise
	 */
	public boolean reload(BufferedReader reader) {
		try {
			if (!reader.ready()) return false;
			List<String> comments = new ArrayList<>();
			Section parent = this;
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				String trimmed = line.trim();
				if (trimmed.isEmpty()) continue;
				else if (trimmed.charAt(0) == COMMENT_INDICATOR) comments.add(line.substring(1));
				else {
					parent = parentFor(line, parent).addChild(trimmed.substring(0, trimmed.indexOf(":")), NON_VALUE_MATCH_REGEX.matcher(line).replaceFirst(""));
					parent.comments().set(comments);
					comments.clear();
				}
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private static Section parentFor(String line, Section current) {
		for (Section parent = current; parent.parent().isPresent(); parent = parent.parent().get()) {
			if (parent instanceof HMFF) return parent;
			String indent = new String(new char[parent.countParents()]).replace("\0", TAB);
			if (line.startsWith(indent)) return parent;
		}
		return current.getRoot();
	}

	public List<String> getLines() {
		List<String> contents = new ArrayList<>();
		for (Section child : children.values()) contents.addAll(child.getLines(0));
		for (String comment : this.comments) contents.add(COMMENT_INDICATOR + " " + comment);
		return Collections.unmodifiableList(contents);
	}

	/**
	 * Get a {@code String} representation of the in-memory structure.<br>
	 * <br>
	 * This is how the file will appear when the memory contents are saved.
	 *
	 * @return {@code String} representation of the in-memory structure.
	 */
	@Override
	public String toString() {
		return getLines().stream().collect(Collectors.joining("\n"));
	}

	/**
	 * Writes all the current contents of memory to disk.<br>
	 * Overwrites any and all existing contents of the file.
	 *
	 * @return true if the file write was successful, false otherwise
	 */
	public boolean save() {
		List<String> lines = getLines();
		return save(lines.toArray(new String[lines.size()]));
	}

	/**
	 * Writes all the provided lines to disk.<br>
	 * Overwrites any and all existing contents of the file.
	 *
	 * @param lines The lines to write to disk
	 *
	 * @return true if the file write was successful, false otherwise
	 */
	public boolean save(String... lines) {
		return save(true, lines);
	}

	/**
	 * Writes all the provided lines to disk.<br>
	 * If the target file has contents and overwrite is not true, does nothing.
	 *
	 * @param overwrite Determines whether the file should be written to, if not empty
	 *
	 * @param lines The lines to save to disk
	 *
	 * @return true if the file write was successful, false otherwise or if the file has contents and overwrite was false
	 */
	public boolean save(boolean overwrite, String... lines) {
		ensure(this.file);
		if (this.file.length() > 0 && !overwrite) return false;
		try (FileWriter fw = new FileWriter(this.file, false)) {
			for (int i = 0; i < lines.length; i++) {
				if (i + 1 == lines.length) fw.write(lines[i]);
				else fw.write(lines[i] + "\n");
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Writes the contents of the provided {@linkplain InputStream} to disk.<br>
	 * Overwrites any and all existing contents of the file.
	 *
	 * @param is The {@linkplain InputStream} to write to disk
	 *
	 * @return true if the file write was successful, false otherwise
	 */
	public boolean save(InputStream is) {
		return save(true, is);
	}

	/**
	 * Writes the contents of the provided {@linkplain InputStream} to disk.<br>
	 * If the target file has contents and overwrite is not true, does nothing.
	 *
	 * @param overwrite Determines whether the file should be written to, if not empty
	 *
	 * @param is The {@linkplain InputStream} to write to disk
	 *
	 * @return true if the file write was successful, false otherwise or if the file has contents and overwrite was false
	 */
	public boolean save(boolean overwrite, InputStream is) {
		if (is == null || !overwrite && file.length() > 0) return false;
		ensure(this.file);
		try {
			Files.copy(is, file.getAbsoluteFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private static void ensure(File file) {
		if (file.exists()) return;
		try {
			Optional.ofNullable(file.getParentFile()).ifPresent(p -> p.mkdirs());
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
