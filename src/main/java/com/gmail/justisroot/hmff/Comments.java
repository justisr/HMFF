package com.gmail.justisroot.hmff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Comments implements Iterable<String> {

	private final List<String> comments = new ArrayList<>();

	/**
	 * Create a new {@link Comments} instance with the provided values.
	 *
	 * @param comments The {@code String} contents to populate this {@link Comments} instance
	 */
	public Comments(String... comments) {
		append(comments);
	}

	/**
	 * Append the provided values to the current {@link Comments} contents.
	 *
	 * @param comments The contents to append to these comments
	 *
	 * @return this {@link Comments} instance
	 */
	public Comments append(String... comments) {
		for (int i = 0; i < comments.length; i++) this.comments.add(comments[i]);
		return this;
	}

	/**
	 * Append the provided values to the current {@link Comments} contents.
	 *
	 * @param comments The contents to append to these comments
	 *
	 * @return this {@link Comments} instance
	 */
	public Comments append(List<String> comments) {
		this.comments.addAll(comments);
		return this;
	}

	/**
	 * Clear this {@link Comments} instance of its values.
	 *
	 * @return this {@link Comments} instance
	 */
	public Comments clear() {
		this.comments.clear();
		return this;
	}

	/**
	 * Set the contents of this {@link Comments} instance to the provided values.
	 *
	 * @param comments The {@code String} contents to replace this {@link Comments} current contents
	 *
	 * @return this {@link Comments} instance
	 */
	public Comments set(String... comments) {
		return clear().append(comments);
	}

	/**
	 * Set the contents of this {@link Comments} instance to the provided values.
	 *
	 * @param comments The {@code String} contents to replace this {@link Comments} current contents
	 *
	 * @return this {@link Comments} instance
	 */
	public Comments set(List<String> comments) {
		return clear().append(comments);
	}

	/**
	 * Create a {@code String} array representation of this {@link Comments} instance.
	 *
	 * @return a new {@code String} array representing the comment contents
	 */
	public String[] toArray() {
		return this.comments.toArray(new String[this.comments.size()]);
	}

	/**
	 * Get the {@code String} list backing this {@link Comments} instance.
	 *
	 * @return a {@code List} of strings representing the comment contents
	 */
	public List<String> list() {
		return this.comments;
	}

	/**
	 *  Get an {@link Iterator} for this {@link Comments} {@code String} contents.
	 *
	 *  @return a {@code String} {@link Iterator} for the comment contents
	 */
	@Override
	public Iterator<String> iterator() {
		return this.comments.iterator();
	}

}
