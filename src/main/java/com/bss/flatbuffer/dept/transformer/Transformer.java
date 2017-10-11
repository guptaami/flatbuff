package com.bss.flatbuffer.dept.transformer;

/**
 * 
 * @author amit
 *
 * @param <T>
 */
public interface Transformer<T> {
	byte[] serialize(final T t);

	T deserialize(final byte[] stream);
}
