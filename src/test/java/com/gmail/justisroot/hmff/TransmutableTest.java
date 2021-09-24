package com.gmail.justisroot.hmff;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
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
public class TransmutableTest extends FileTest {

	@Test
	@Order(10)
	@DisplayName("Test get Boolean")
	void testGetBoolean() {
		hmff.set(true, "key");
		Optional<Boolean> value = hmff.getBoolean("key");
		assertTrue(value.isPresent(), "Could not validate boolean value");
		assertTrue(value.get());
	}

	@Test
	@Order(20)
	@DisplayName("Test get-set Boolean")
	void testGetOrSetBoolean() {
		Boolean value = hmff.getOrSetBoolean(true, "key");
		assertTrue(value, "Value should have been set");
		value = hmff.getOrSetBoolean(false, "key");
		assertTrue(value, "Existing value should not have changed");
	}

	@Test
	@Order(30)
	@DisplayName("Test get-set supplied Boolean")
	void testGetOrSetSuppliedBoolean() {
		Boolean value = hmff.getOrSetBoolean(() -> true, "key");
		assertTrue(value, "Value should have been set");
		value = hmff.getOrSetBoolean(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return false;
		}, "key");
		assertTrue(value, "Value should not have changed");
	}

	@Test
	@Order(40)
	@DisplayName("Test get Boolean array")
	void testGetBooleanArray() {
		boolean[] array = new boolean[] {true, false, true, false, true};
		hmff.set(array, "key");
		Optional<Boolean[]> value = hmff.getBooleanArray("key");
		assertTrue(value.isPresent(), "Could not validate array");
		assertTrue(value.get().length == array.length, "Array is not the same length");
		for (int i = 0; i < array.length; i++) assertTrue(value.get()[i] == array[i]);
	}

	@Test
	@Order(50)
	@DisplayName("Test get-set Boolean array")
	void testGetOrSetBooleanArray() {
		Boolean[] array = new Boolean[] {true, false, true, false, true};
		Boolean[] value = hmff.getOrSetBooleanArray(array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetBooleanArray(new Boolean[0], "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(60)
	@DisplayName("Test get supplied Boolean array")
	void testGetOrSetSuppliedBooleanArray() {
		Boolean[] array = new Boolean[] {true, false, true, false, true};
		Boolean[] value = hmff.getOrSetBooleanArray(() -> array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetBooleanArray(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return new Boolean[0];
		}, "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(70)
	@DisplayName("Test get Integer")
	void testGetInteger() {
		int set = 70;
		hmff.set(set, "key");
		Optional<Integer> value = hmff.getInteger("key");
		assertTrue(value.isPresent(), "Could not validate value");
		assertTrue(value.get() == set);
	}

	@Test
	@Order(80)
	@DisplayName("Test get-set Integer")
	void testGetOrSetInteger() {
		int set = 80;
		Integer value = hmff.getOrSetInteger(set, "key");
		assertTrue(value == set, "Value should have been set");
		value = hmff.getOrSetInteger(0, "key");
		assertTrue(value == set, "Existing value should not have changed");
	}

	@Test
	@Order(90)
	@DisplayName("Test get-set supplied Integer")
	void testGetOrSetSuppliedInteger() {
		int set = 90;
		Integer value = hmff.getOrSetInteger(() -> set, "key");
		assertTrue(value == set, "Value should have been set");
		value = hmff.getOrSetInteger(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return 0;
		}, "key");
		assertTrue(value == set, "Value should not have changed");
	}

	@Test
	@Order(100)
	@DisplayName("Test get Integer array")
	void testGetIntegerArray() {
		int[] array = new int[] {345, 1, 56, -78, 43};
		hmff.set(array, "key");
		Optional<Integer[]> value = hmff.getIntegerArray("key");
		assertTrue(value.isPresent(), "Could not validate array");
		assertTrue(value.get().length == array.length, "Array is not the same length");
		for (int i = 0; i < array.length; i++) assertTrue(value.get()[i] == array[i]);
	}

	@Test
	@Order(110)
	@DisplayName("Test get-set Integer array")
	void testGetOrSetIntegerArray() {
		Integer[] array = new Integer[] {124, 678, -3621, 47, 12};
		Integer[] value = hmff.getOrSetIntegerArray(array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetIntegerArray(new Integer[0], "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(120)
	@DisplayName("Test get-set supplied Integer array")
	void testGetOrSetSuppliedIntegerArray() {
		Integer[] array = new Integer[] {12, -63, 12, 76, 47};
		Integer[] value = hmff.getOrSetIntegerArray(() -> array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetIntegerArray(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return new Integer[0];
		}, "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(130)
	@DisplayName("Test get Byte")
	void testGetByte() {
		byte set = 70;
		hmff.set(set, "key");
		Optional<Byte> value = hmff.getByte("key");
		assertTrue(value.isPresent(), "Could not validate value");
		assertTrue(value.get() == set);
	}

	@Test
	@Order(140)
	@DisplayName("Test get-set Byte")
	void testGetOrSetByte() {
		byte set = 80;
		Byte value = hmff.getOrSetByte(set, "key");
		assertTrue(value == set, "Value should have been set");
		value = hmff.getOrSetByte((byte) 0, "key");
		assertTrue(value == set, "Existing value should not have changed");
	}

	@Test
	@Order(150)
	@DisplayName("Test get-set supplied Byte")
	void testGetOrSetSuppliedByte() {
		byte set = 90;
		Byte value = hmff.getOrSetByte(() -> set, "key");
		assertTrue(value == set, "Value should have been set");
		value = hmff.getOrSetByte(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return (byte) 0;
		}, "key");
		assertTrue(value == set, "Value should not have changed");
	}

	@Test
	@Order(160)
	@DisplayName("Test get Byte array")
	void testGetIntegerByte() {
		byte[] array = new byte[] {12, 1, 56, 78, -43};
		hmff.set(array, "key");
		Optional<Byte[]> value = hmff.getByteArray("key");
		assertTrue(value.isPresent(), "Could not validate array");
		assertTrue(value.get().length == array.length, "Array is not the same length");
		for (int i = 0; i < array.length; i++) assertTrue(value.get()[i] == array[i]);
	}

	@Test
	@Order(170)
	@DisplayName("Test get-set Byte array")
	void testGetOrSetByteArray() {
		Byte[] array = new Byte[] {124, 14, -23, 47, 12};
		Byte[] value = hmff.getOrSetByteArray(array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetByteArray(new Byte[0], "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(180)
	@DisplayName("Test get-set supplied Byte array")
	void testGetOrSetSuppliedByteArray() {
		Byte[] array = new Byte[] {12, -63, 12, 76, 47};
		Byte[] value = hmff.getOrSetByteArray(() -> array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetByteArray(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return new Byte[0];
		}, "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(190)
	@DisplayName("Test get Long")
	void testGetLong() {
		long set = 70;
		hmff.set(set, "key");
		Optional<Long> value = hmff.getLong("key");
		assertTrue(value.isPresent(), "Could not validate value");
		assertTrue(value.get() == set);
	}

	@Test
	@Order(200)
	@DisplayName("Test get-set Long")
	void testGetOrSetLong() {
		long set = 80;
		Long value = hmff.getOrSetLong(set, "key");
		assertTrue(value == set, "Value should have been set");
		value = hmff.getOrSetLong((long) 0, "key");
		assertTrue(value == set, "Existing value should not have changed");
	}

	@Test
	@Order(210)
	@DisplayName("Test get-set supplied Long")
	void testGetOrSetSuppliedLong() {
		long set = 90;
		Long value = hmff.getOrSetLong(() -> set, "key");
		assertTrue(value == set, "Value should have been set");
		value = hmff.getOrSetLong(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return (long) 0;
		}, "key");
		assertTrue(value == set, "Value should not have changed");
	}

	@Test
	@Order(220)
	@DisplayName("Test get Long array")
	void testGetIntegerLong() {
		long[] array = new long[] {12, 1, 56, 78, -43};
		hmff.set(array, "key");
		Optional<Long[]> value = hmff.getLongArray("key");
		assertTrue(value.isPresent(), "Could not validate array");
		assertTrue(value.get().length == array.length, "Array is not the same length");
		for (int i = 0; i < array.length; i++) assertTrue(value.get()[i] == array[i]);
	}
	@Test
	@Order(230)
	@DisplayName("Test get-set Long array")
	void testGetOrSetLongArray() {
		Long[] array = new Long[] {124L, 14L, -23L, 47L, 12L};
		Long[] value = hmff.getOrSetLongArray(array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetLongArray(new Long[0], "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(240)
	@DisplayName("Test get-set supplied Long array")
	void testGetOrSetSuppliedLongArray() {
		Long[] array = new Long[] {12L, -63L, 12L, 76L, 47L};
		Long[] value = hmff.getOrSetLongArray(() -> array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetLongArray(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return new Long[0];
		}, "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(250)
	@DisplayName("Test get Double")
	void testGetDouble() {
		double set = 70;
		hmff.set(set, "key");
		Optional<Double> value = hmff.getDouble("key");
		assertTrue(value.isPresent(), "Could not validate value");
		assertTrue(value.get() == set);
	}

	@Test
	@Order(260)
	@DisplayName("Test get-set Double")
	void testGetOrSetDouble() {
		double set = 80;
		Double value = hmff.getOrSetDouble(set, "key");
		assertTrue(value == set, "Value should have been set");
		value = hmff.getOrSetDouble((double) 0, "key");
		assertTrue(value == set, "Existing value should not have changed");
	}

	@Test
	@Order(270)
	@DisplayName("Test get-set supplied Double")
	void testGetOrSetSuppliedDouble() {
		double set = 90;
		Double value = hmff.getOrSetDouble(() -> set, "key");
		assertTrue(value == set, "Value should have been set");
		value = hmff.getOrSetDouble(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return (double) 0;
		}, "key");
		assertTrue(value == set, "Value should not have changed");
	}

	@Test
	@Order(280)
	@DisplayName("Test get Double array")
	void testGetIntegerDouble() {
		double[] array = new double[] {12, 1, 56, 78, -43};
		hmff.set(array, "key");
		Optional<Double[]> value = hmff.getDoubleArray("key");
		assertTrue(value.isPresent(), "Could not validate array");
		assertTrue(value.get().length == array.length, "Array is not the same length");
		for (int i = 0; i < array.length; i++) assertTrue(value.get()[i] == array[i]);
	}

	@Test
	@Order(290)
	@DisplayName("Test get-set Double array")
	void testGetOrSetDoubleArray() {
		Double[] array = new Double[] {124.125, 14.536, -23.1241, 47.12451, 12.124};
		Double[] value = hmff.getOrSetDoubleArray(array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetDoubleArray(new Double[0], "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(300)
	@DisplayName("Test get-set supplied Double array")
	void testGetOrSetSuppliedDoubleArray() {
		Double[] array = new Double[] {12.125, -63.125, 12.125, 76.5, 47.23};
		Double[] value = hmff.getOrSetDoubleArray(() -> array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetDoubleArray(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return new Double[0];
		}, "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(250)
	@DisplayName("Test get Float")
	void testGetFloat() {
		float set = 70;
		hmff.set(set, "key");
		Optional<Float> value = hmff.getFloat("key");
		assertTrue(value.isPresent(), "Could not validate value");
		assertTrue(value.get() == set);
	}

	@Test
	@Order(260)
	@DisplayName("Test get-set Float")
	void testGetOrSetFloat() {
		float set = 80;
		Float value = hmff.getOrSetFloat(set, "key");
		assertTrue(value == set, "Value should have been set");
		value = hmff.getOrSetFloat((float) 0, "key");
		assertTrue(value == set, "Existing value should not have changed");
	}

	@Test
	@Order(270)
	@DisplayName("Test get-set supplied Float")
	void testGetOrSetSuppliedFloat() {
		float set = 90;
		Float value = hmff.getOrSetFloat(() -> set, "key");
		assertTrue(value == set, "Value should have been set");
		value = hmff.getOrSetFloat(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return (float) 0;
		}, "key");
		assertTrue(value == set, "Value should not have changed");
	}

	@Test
	@Order(280)
	@DisplayName("Test get Float array")
	void testGetIntegerFloat() {
		float[] array = new float[] {12, 1, 56, 78, -43};
		hmff.set(array, "key");
		Optional<Float[]> value = hmff.getFloatArray("key");
		assertTrue(value.isPresent(), "Could not validate array");
		assertTrue(value.get().length == array.length, "Array is not the same length");
		for (int i = 0; i < array.length; i++) assertTrue(value.get()[i] == array[i]);
	}

	@Test
	@Order(290)
	@DisplayName("Test get-set Float array")
	void testGetOrSetFloatArray() {
		Float[] array = new Float[] {0.323f, 0.3452f, -2350.312f, 47.12451f, 12.124f};
		Float[] value = hmff.getOrSetFloatArray(array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetFloatArray(new Float[0], "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(300)
	@DisplayName("Test get-set supplied Float array")
	void testGetOrSetSuppliedFloatArray() {
		Float[] array = new Float[] {12.125f, -63.125f, 12.125f, 76.5f, 47.23f};
		Float[] value = hmff.getOrSetFloatArray(() -> array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetFloatArray(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return new Float[0];
		}, "key");
		assertTrue(Arrays.equals(array, value));
	}


	@Test
	@Order(310)
	@DisplayName("Test get String")
	void testGetString() {
		String set = "string";
		hmff.set(set, "key");
		Optional<String> value = hmff.getString("key");
		assertTrue(value.isPresent(), "Could not validate value");
		assertTrue(value.get().equals(set));
	}

	@Test
	@Order(320)
	@DisplayName("Test get-set String")
	void testGetOrSetString() {
		String set = "string";
		String value = hmff.getOrSetString(set, "key");
		assertTrue(value.equals(set), "Value should have been set");
		value = hmff.getOrSetString("", "key");
		assertTrue(value.equals(set), "Existing value should not have changed");
	}

	@Test
	@Order(330)
	@DisplayName("Test get-set supplied String")
	void testGetOrSetSuppliedString() {
		String set = "string";
		String value = hmff.getOrSetString(() -> set, "key");
		assertTrue(value.equals(set), "Value should have been set");
		value = hmff.getOrSetString(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return "";
		}, "key");
		assertTrue(value.equals(set), "Value should not have changed");
	}

	@Test
	@Order(340)
	@DisplayName("Test get String array")
	void testGetIntegerString() {
		String[] array = new String[] {"2eadwd", "2artfd", "4fafaa", "j5yadad"};
		hmff.set(array, "key");
		Optional<String[]> value = hmff.getStringArray("key");
		assertTrue(value.isPresent(), "Could not validate array");
		assertTrue(Arrays.equals(array, value.get()), "Arrays should be equal");
	}

	@Test
	@Order(350)
	@DisplayName("Test get-set String array")
	void testGetOrSetStringArray() {
		String[] array = new String[] {"a2tawdw", "w2ar3tgfdwa", "2fadwad", "$5jui0a"};
		String[] value = hmff.getOrSetStringArray(array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetStringArray(new String[0], "key");
		assertTrue(Arrays.equals(array, value));
	}

	@Test
	@Order(360)
	@DisplayName("Test get-set supplied String array")
	void testGetOrSetSuppliedStringArray() {
		String[] array = new String[] {"afhoda", "afoiahwda", "naiofh8a", "oafh8af"};
		String[] value = hmff.getOrSetStringArray(() -> array, "key");
		assertTrue(Arrays.equals(array, value), "Value should have been set");
		value = hmff.getOrSetStringArray(() -> {
			assertTrue(false, "Supplier should not have been needed");
			return new String[0];
		}, "key");
		assertTrue(Arrays.equals(array, value));
	}

}
