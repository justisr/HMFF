package com.gmail.justisroot.hmff;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Supplier;

public class Section implements Transmutable {

	static final String TAB = "  ";
	static final char VALUE_SEPARATOR = ':', COMMENT_INDICATOR = '#';

	final Optional<Section> parent;
	final LinkedHashMap<String, Section> children = new LinkedHashMap<>();

	Comments comments = new Comments();
	String key, value;

	Section(String key) {
		this(null, key, "");
	}

	Section(Section parent, String key, String value) {
		this.parent = Optional.ofNullable(parent);
		this.key = key;
		this.value = value;
	}

	/**
	 * Add a child {@link Section} to this {@link Section}.<br>
	 * Equivalent to {@link #addChild(String, String)} where the value is an empty {@code String}.
	 *
	 * @param key The key/name of the child {@link Section}
	 *
	 * @return the new child {@link Section}
	 */
	public Section addChild(String key) {
		return addChild(key, "");
	}

	/**
	 * Add a child {@link Section} to this {@link Section}.
	 *
	 * @param key The key/name of the child {@link Section}
	 *
	 * @param value The value of the child {@link Section}
	 *
	 * @return the new child {@link Section}
	 */
	public Section addChild(String key, String value) {
		return addChild(new Section(this, key, value));
	}

	private Section addChild(Section child) {
		this.children.put(child.getKey(), child);
		return child;
	}

	/**
	 * Get the key/name associated with this {@link Section}.
	 *
	 * @return a {@code String} representation of the key/name associated with this {@link Section}
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Get the value associated with this {@link Section}.
	 *
	 * @return a {@code String} representation of the value associated with this {@link Section}
	 */
	String getValue() {
		return this.value;
	}

	/**
	 * Set the value of this {@link Section}.
	 *
	 * @param value The {@code String} value of this {@link Section}
	 */
	void setValue(String value) {
		this.value = value.replaceAll("[\r\n]+", "\\n");
	}

	/**
	 * Get the {@link Comments} for this {@link Section}.
	 *
	 * @return the {@link Comments} for this {@link Section}
	 */
	public Comments comments() {
		return this.comments;
	}

	/**
	 * Count the number of parents this {@link Section} has.
	 *
	 * @return the number of parents this {@link Section} has
	 */
	public int countParents() {
		int parents = 0;
		for (Optional<Section> parent = this.parent; parent.isPresent(); parent = parent.get().parent()) parents++;
		return parents;
	}

	/**
	 * Get the parent {@link Section} of this {@link Section}.
	 *
	 * @return the {@link Section} directly parenting this one,
	 */
	public Optional<Section> parent() {
		return this.parent;
	}

	/**
	 * Get the children of this {@link Section}.
	 *
	 * @return the names/keys of this {@link Section}'s children, mapped to themselves
	 */
	public Map<String, Section> children() {
		return this.children;
	}

	/**
	 * Get the root {@link Section}
	 *
	 * @return the outermost {@link Section}
	 */
	public Section getRoot() {
		if (this.parent.isPresent()) return parent.get().getRoot();
		return this;
	}

	/**
	 * Create a copy of this {@link} within the children of the provided parent {@link Section}.
	 *
	 * @param parent The {@link Section} to copy this {@link Section} into
	 *
	 * @return the new copy of this {@link Section}, located under the provided parent
	 */
	public Section copyTo(Section parent) {
		Section copied = new Section(parent, this.key, this.value);
		copied.comments().set(this.comments().toArray());
		for (Entry<String, Section> child : this.children.entrySet())
			copied.children().put(child.getKey(), child.getValue().copyTo(copied));
		return parent.addChild(copied);
	}

	/**
	 * Check if the {@link Section} at the specified path exists.
	 *
	 * @param path The path to the {@link Section} in question
	 *
	 * @return true if a {@link Section} exists at the specified path, false otherwise
	 */
	public boolean sectionExists(String... path) {
		return get(new Path(path)).isPresent();
	}

	/**
	 * Replace the key/name of the {@link Section} at the specified path, creating one if necessary.<br>
	 * <b>This will also move the {@link Section} down to the bottom of its siblings.</b>
	 *
	 * @param path The path to the {@link Section} to rename
	 *
	 * @param name The new name of the {@link Section} at the specified path
	 */
	public void renameSection(String name, String... path) {
		rename(name, new Path(path));
	}

	/**
	 * If the {@link Section} at the specified path exists, remove it and all of its children.
	 *
	 * @param path The path to {@link Section} to remove
	 */
	public void removeSection(String... path) {
		remove(new Path(path));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Section> getSection(String... path) {
		return get(new Path(path));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void set(Object value, String... path) {
		getOrCreate(value.toString(), new Path(path)).setValue(value.toString());;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Section getOrCreateSection(String... path) {
		return getOrCreate("", new Path(path));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<String> getString(String... path) {
		return getSection(path).map(Section::getValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getOrSetString(String value, String... path) {
		return getOrCreate(value, new Path(path)).getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getOrSetString(Supplier<String> value, String... path) {
		return getOrCreate(value, new Path(path)).getValue();
	}

	/**
	 * Get a {@code List<String>} representation of the path/value and comments of this {@link Section}, as well as all of its children.<br>
	 * Each entry in the list represents a new line.
	 *
	 * @param tab The number of indents to pad the entries with
	 *
	 * @return A {@code List<String>} representation of the path/value and comments of this {@link Section}, as well as all of its children
	 */
	List<String> getLines(int tab) {
		List<String> contents = new ArrayList<>();
		String indent = new String(new char[tab]).replace("\0", TAB);
		for (String comment : this.comments) contents.add(indent + COMMENT_INDICATOR + " " + comment);
		contents.add(indent + this.key + VALUE_SEPARATOR + " " + this.value);
		if (tab == 0) contents.add(""); // for readability
		for (Section child : this.children.values()) contents.addAll(child.getLines(tab + 1));
		return contents;
	}

	private void setKey(String key) {
		if (!parent.isPresent()) return;
		this.parent.get().children().remove(this.key);
		this.parent.get().children().put(key, this);
		this.key = key;
	}

	private void rename(String key, Path path) {
		if (path.hasNext()) {
			String next = path.next().get();
			if (!this.children.containsKey(next)) return;
			this.children.get(next).rename(key, path);
		} else this.setKey(key);
	}

	private void remove(Path path) {
		if (path.hasNext()) {
			Section child = this.children.get(path.next().get());
			if (child != null) child.remove(path);
		} else this.parent.ifPresent(p -> p.children().remove(this.key));
	}

	private Section getOrCreate(String value, Path path) {
		if (!path.hasNext()) return this;
		String key = path.next().get();
		Section child = this.children.get(key);
		if (child == null && path.hasNext()) child = addChild(key);
		else if (child == null) child = addChild(key, value);
		return child.getOrCreate(value, path);
	}

	private Section getOrCreate(Supplier<String> value, Path path){
		if (!path.hasNext()) return this;
		String key = path.next().get();
		Section child = this.children.get(key);
		if (child == null && path.hasNext()) child = addChild(key);
		else if (child == null) child = addChild(key, value.get());
		return child.getOrCreate(value, path);
	}

	private Optional<Section> get(Path path) {
		if (!path.hasNext()) return Optional.of(this);
		Section child = this.children.get(path.next().get());
		if (child == null) return Optional.empty();
		return child.get(path);
	}

}
