package com.gmail.justisroot.hmff;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

interface Transmutable {

	/**
	 * Get the {@link Section} located at the specified path.
	 *
	 * @param path The path to the {@link Section}<br>
	 * e.g {@code ("path", "to", "section")}<br>
	 *
	 * @return An {@linkplain Optional} containing the {@link Section} located at the specified path, or empty if no {@link Section} is present
	 */
	Optional<Section> getSection(String... path);

	/**
	 * Get the {@link Section} located at the specified path, creating one if necessary.
	 *
	 * @param path The path to the {@link Section}<br>
	 * e.g {@code ("path", "to", "section")}
	 *
	 * @return The {@link Section} located at the specified path
	 */
	Section getOrCreateSection(String... path);

	/**
	 * Set the value of the {@link Section} located at the specified path, creating one if necessary.
	 *
	 * @param value The value to set at the specified {@link Section}, represented using {@linkplain Object#toString()}
	 *
	 * @param path The path to the {@link Section} to set the value of
	 */
	void set(Object value, String... path);

	/**
	 * Set the value of the {@link Section} located at the specified path, creating one if necessary.
	 *
	 * @param value The value to set at the specified {@link Section}, represented as:<br>
	 * {@code [entry1, entry2, entry3, etc...]}
	 *
	 * @param path The path to the {@link Section} to set the value of
	 */
	default void set(Object[] value, String... path) {
		set(Arrays.toString(value), path);
	}

	/**
	 * Get a {@code String} representation of the value of the {@link Section} at the specified path.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code String} representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	Optional<String> getString(String... path);

	/**
	 * Get a {@code String} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code String} representation of the value located at the specified path
	 */
	String getOrSetString(String value, String... path);

	/**
	 * Get a {@code String} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code String} representation of the value located at the specified path
	 */
	String getOrSetString(Supplier<String> value, String... path);

	/**
	 * Get a {@code String} array representation of the value of the {@link Section} at the specified path.
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code String} array representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<String[]> getStringArray(String... path) {
		Optional<String> value = getString(path).map(String::trim);
		if (!value.isPresent()) return Optional.empty();
		return Optional.of(splitStream(value.get()).toArray(String[]::new));
	}

	/**
	 * Get a {@code String} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code String} array representation of the value of the {@link Section} at the specified path
	 */
	default String[] getOrSetStringArray(String[] value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value), path)).toArray(String[]::new);
	}

	/**
	 * Get a {@code String} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code String} array representation of the value of the {@link Section} at the specified path
	 */
	default String[] getOrSetStringArray(Supplier<String[]> value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value.get()), path)).toArray(String[]::new);
	}

	/**
	 * Set the value of the {@link Section} located at the specified path, creating one if necessary.
	 *
	 * @param value The value to set at the specified {@link Section}, represented as:<br>
	 * {@code [entry1, entry2, entry3, etc...]}
	 *
	 * @param path The path to the {@link Section} to set the value of
	 */
	default void set(boolean[] value, String... path) {
		set(Arrays.toString(value), path);
	}

	/**
	 * Get a {@code Boolean} representation of the value of the {@link Section} at the specified path.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Boolean} representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Boolean> getBoolean(String... path) {
		return getString(path).map(String::trim).map(Boolean::parseBoolean);
	}

	/**
	 * Get a {@code Boolean} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Boolean} representation of the value located at the specified path
	 */
	default Boolean getOrSetBoolean(Boolean value, String... path) {
		return Boolean.parseBoolean(getOrSetString(value.toString(), path).trim());
	}

	/**
	 * Get a {@code Boolean} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Boolean} representation of the value located at the specified path
	 */
	default Boolean getOrSetBoolean(Supplier<Boolean> value, String... path) {
		return Boolean.parseBoolean(getOrSetString(() -> value.get().toString(), path).trim());
	}

	/**
	 * Get a {@code Boolean} array representation of the value of the {@link Section} at the specified path.
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Boolean} array representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Boolean[]> getBooleanArray(String... path) {
		Optional<String> value = getString(path).map(String::trim);
		if (!value.isPresent()) return Optional.empty();
		return Optional.of(splitStream(value.get()).map(Boolean::parseBoolean).toArray(Boolean[]::new));
	}

	/**
	 * Get a {@code Boolean} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Boolean} array representation of the value of the {@link Section} at the specified path
	 */
	default Boolean[] getOrSetBooleanArray(Boolean[] value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value), path)).map(Boolean::parseBoolean).toArray(Boolean[]::new);
	}

	/**
	 * Get a {@code Boolean} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Boolean} array representation of the value of the {@link Section} at the specified path
	 */
	default Boolean[] getOrSetBooleanArray(Supplier<Boolean[]> value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value.get()), path)).map(Boolean::parseBoolean).toArray(Boolean[]::new);
	}

	/**
	 * Set the value of the {@link Section} located at the specified path, creating one if necessary.
	 *
	 * @param value The value to set at the specified {@link Section}, represented as:<br>
	 * {@code [entry1, entry2, entry3, etc...]}
	 *
	 * @param path The path to the {@link Section} to set the value of
	 */
	default void set(int[] value, String... path) {
		set(Arrays.toString(value), path);
	}

	/**
	 * Get a {@code Integer} representation of the value of the {@link Section} at the specified path.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Integer} representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Integer> getInteger(String... path) {
		return parseNumber(getString(path).map(String::trim), Integer::parseInt);
	}

	/**
	 * Get a {@code Integer} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Integer} representation of the value located at the specified path
	 */
	default Integer getOrSetInteger(Integer value, String... path) {
		return Integer.parseInt(getOrSetString(value.toString(), path).trim());
	}

	/**
	 * Get a {@code Integer} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Integer} representation of the value located at the specified path
	 */
	default Integer getOrSetInteger(Supplier<Integer> value, String... path) {
		return Integer.parseInt(getOrSetString(() -> value.get().toString(), path).trim());
	}

	/**
	 * Get a {@code Integer} array representation of the value of the {@link Section} at the specified path.
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Integer} array representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Integer[]> getIntegerArray(String... path) {
		Optional<String> value = getString(path).map(String::trim);
		if (!value.isPresent()) return Optional.empty();
		return Optional.of(splitStream(value.get()).map(Integer::parseInt).toArray(Integer[]::new));
	}

	/**
	 * Get a {@code Integer} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Integer} array representation of the value of the {@link Section} at the specified path
	 */
	default Integer[] getOrSetIntegerArray(Integer[] value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value), path)).map(Integer::parseInt).toArray(Integer[]::new);
	}

	/**
	 * Get a {@code Integer} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Integer} array representation of the value of the {@link Section} at the specified path
	 */
	default Integer[] getOrSetIntegerArray(Supplier<Integer[]> value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value.get()), path)).map(Integer::parseInt).toArray(Integer[]::new);
	}

	/**
	 * Set the value of the {@link Section} located at the specified path, creating one if necessary.
	 *
	 * @param value The value to set at the specified {@link Section}, represented as:<br>
	 * {@code [entry1, entry2, entry3, etc...]}
	 *
	 * @param path The path to the {@link Section} to set the value of
	 */
	default void set(byte[] value, String... path) {
		set(Arrays.toString(value), path);
	}

	/**
	 * Get a {@code Byte} representation of the value of the {@link Section} at the specified path.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Byte} representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Byte> getByte(String... path) {
		return parseNumber(getString(path).map(String::trim), Byte::parseByte);
	}

	/**
	 * Get a {@code Byte} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Byte} representation of the value located at the specified path
	 */
	default Byte getOrSetByte(Byte value, String... path) {
		return Byte.parseByte(getOrSetString(value.toString(), path).trim());
	}

	/**
	 * Get a {@code Byte} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Byte} representation of the value located at the specified path
	 */
	default Byte getOrSetByte(Supplier<Byte> value, String... path) {
		return Byte.parseByte(getOrSetString(() -> value.get().toString(), path).trim());
	}

	/**
	 * Get a {@code Byte} array representation of the value of the {@link Section} at the specified path.
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Byte} array representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Byte[]> getByteArray(String... path) {
		Optional<String> value = getString(path).map(String::trim);
		if (!value.isPresent()) return Optional.empty();
		return Optional.of(splitStream(value.get()).map(Byte::parseByte).toArray(Byte[]::new));
	}

	/**
	 * Get a {@code Byte} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Byte} array representation of the value of the {@link Section} at the specified path
	 */
	default Byte[] getOrSetByteArray(Byte[] value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value), path)).map(Byte::parseByte).toArray(Byte[]::new);
	}

	/**
	 * Get a {@code Byte} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Byte} array representation of the value of the {@link Section} at the specified path
	 */
	default Byte[] getOrSetByteArray(Supplier<Byte[]> value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value.get()), path)).map(Byte::parseByte).toArray(Byte[]::new);
	}

	/**
	 * Set the value of the {@link Section} located at the specified path, creating one if necessary.
	 *
	 * @param value The value to set at the specified {@link Section}, represented as:<br>
	 * {@code [entry1, entry2, entry3, etc...]}
	 *
	 * @param path The path to the {@link Section} to set the value of
	 */
	default void set(long[] value, String... path) {
		set(Arrays.toString(value), path);
	}

	/**
	 * Get a {@code Long} representation of the value of the {@link Section} at the specified path.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Long} representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Long> getLong(String... path) {
		return parseNumber(getString(path).map(String::trim), Long::parseLong);
	}

	/**
	 * Get a {@code Long} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Long} representation of the value located at the specified path
	 */
	default Long getOrSetLong(Long value, String... path) {
		return Long.parseLong(getOrSetString(value.toString(), path).trim());
	}

	/**
	 * Get a {@code Long} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Long} representation of the value located at the specified path
	 */
	default Long getOrSetLong(Supplier<Long> value, String... path) {
		return Long.parseLong(getOrSetString(() -> value.get().toString(), path).trim());
	}

	/**
	 * Get a {@code Long} array representation of the value of the {@link Section} at the specified path.
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Long} array representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Long[]> getLongArray(String... path) {
		Optional<String> value = getString(path).map(String::trim);
		if (!value.isPresent()) return Optional.empty();
		return Optional.of(splitStream(value.get()).map(Long::parseLong).toArray(Long[]::new));
	}

	/**
	 * Get a {@code Long} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Long} array representation of the value of the {@link Section} at the specified path
	 */
	default Long[] getOrSetLongArray(Long[] value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value), path)).map(Long::parseLong).toArray(Long[]::new);
	}

	/**
	 * Get a {@code Long} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Long} array representation of the value of the {@link Section} at the specified path
	 */
	default Long[] getOrSetLongArray(Supplier<Long[]> value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value.get()), path)).map(Long::parseLong).toArray(Long[]::new);
	}

	/**
	 * Set the value of the {@link Section} located at the specified path, creating one if necessary.
	 *
	 * @param value The value to set at the specified {@link Section}, represented as:<br>
	 * {@code [entry1, entry2, entry3, etc...]}
	 *
	 * @param path The path to the {@link Section} to set the value of
	 */
	default void set(double[] value, String... path) {
		set(Arrays.toString(value), path);
	}

	/**
	 * Get a {@code Double} representation of the value of the {@link Section} at the specified path.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Double} representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Double> getDouble(String... path) {
		return parseNumber(getString(path).map(String::trim), Double::parseDouble);
	}

	/**
	 * Get a {@code Double} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Double} representation of the value located at the specified path
	 */
	default Double getOrSetDouble(Double value, String... path) {
		return Double.parseDouble(getOrSetString(value.toString(), path).trim());
	}

	/**
	 * Get a {@code Double} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Double} representation of the value located at the specified path
	 */
	default Double getOrSetDouble(Supplier<Double> value, String... path) {
		return Double.parseDouble(getOrSetString(() -> value.get().toString(), path).trim());
	}

	/**
	 * Get a {@code Double} array representation of the value of the {@link Section} at the specified path.
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Double} array representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Double[]> getDoubleArray(String... path) {
		Optional<String> value = getString(path).map(String::trim);
		if (!value.isPresent()) return Optional.empty();
		return Optional.of(splitStream(value.get()).map(Double::parseDouble).toArray(Double[]::new));
	}

	/**
	 * Get a {@code Double} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Double} array representation of the value of the {@link Section} at the specified path
	 */
	default Double[] getOrSetDoubleArray(Double[] value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value), path)).map(Double::parseDouble).toArray(Double[]::new);
	}

	/**
	 * Get a {@code Double} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Double} array representation of the value of the {@link Section} at the specified path
	 */
	default Double[] getOrSetDoubleArray(Supplier<Double[]> value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value.get()), path)).map(Double::parseDouble).toArray(Double[]::new);
	}

	/**
	 * Set the value of the {@link Section} located at the specified path, creating one if necessary.
	 *
	 * @param value The value to set at the specified {@link Section}, represented as:<br>
	 * {@code [entry1, entry2, entry3, etc...]}
	 *
	 * @param path The path to the {@link Section} to set the value of
	 */
	default void set(float[] value, String... path) {
		set(Arrays.toString(value), path);
	}

	/**
	 * Get a {@code Float} representation of the value of the {@link Section} at the specified path.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Float} representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Float> getFloat(String... path) {
		return parseNumber(getString(path).map(String::trim), Float::parseFloat);
	}

	/**
	 * Get a {@code Float} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Float} representation of the value located at the specified path
	 */
	default Float getOrSetFloat(Float value, String... path) {
		return Float.parseFloat(getOrSetString(value.toString(), path).trim());
	}

	/**
	 * Get a {@code Float} representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Float} representation of the value located at the specified path
	 */
	default Float getOrSetFloat(Supplier<Float> value, String... path) {
		return Float.parseFloat(getOrSetString(() -> value.get().toString(), path).trim());
	}

	/**
	 * Get a {@code Float} array representation of the value of the {@link Section} at the specified path.
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return An {@linkplain Optional} containing a {@code Float} array representation of the value of the {@link Section} at the specified path, or empty if the {@link Section} doesn't exist
	 */
	default Optional<Float[]> getFloatArray(String... path) {
		Optional<String> value = getString(path).map(String::trim);
		if (!value.isPresent()) return Optional.empty();
		return Optional.of(splitStream(value.get()).map(Float::parseFloat).toArray(Float[]::new));
	}

	/**
	 * Get a {@code Float} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value The value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Float} array representation of the value of the {@link Section} at the specified path
	 */
	default Float[] getOrSetFloatArray(Float[] value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value), path)).map(Float::parseFloat).toArray(Float[]::new);
	}

	/**
	 * Get a {@code Float} array representation of the value of the {@link Section} at the specified path, creating one with the specified value if necessary.<br>
	 * <br>
	 * If the format {@code [s1, s2, s3]} is found, it will be used to parse, otherwise the list elements will be split using whitespace.
	 *
	 * @param value A {@linkplain Supplier} to call for the value to set and use if a {@link Section} does not yet exist at the specified location
	 *
	 * @param path The path to the {@link Section} where the value is located
	 *
	 * @return A {@code Float} array representation of the value of the {@link Section} at the specified path
	 */
	default Float[] getOrSetFloatArray(Supplier<Float[]> value, String... path) {
		return splitStream(getOrSetString(() -> Arrays.toString(value.get()), path)).map(Float::parseFloat).toArray(Float[]::new);
	}

	/** Split a {@code String} representation of an array into a {@code Stream} of {@code String} entries **/
	private static Stream<String> splitStream(String value) {
		if (value.startsWith("[") && value.endsWith("]")) {
			return Stream.of(value.substring(1, value.length() - 1).split(",\\s?"));
		} else return Stream.of(value.split("\\s+"));
	}

	/** Parse a {@code Number} out of a {@code String} given the provided parse function **/
	private static <T extends Number> Optional<T> parseNumber(Optional<String> number, Function<String, T> function) {
		try {
			return number.map(function);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

}
