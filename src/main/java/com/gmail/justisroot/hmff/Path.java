package com.gmail.justisroot.hmff;

import java.util.Optional;

final class Path {

	private final String[] path;

	private int index;

	Path(String[] path) {
		this.path = path;
	}

	boolean hasNext() {
		return path.length - index > 0;
	}

	Optional<String> next() {
		if (index < path.length) return Optional.of(path[index++]);
		return Optional.empty();
	}

}
