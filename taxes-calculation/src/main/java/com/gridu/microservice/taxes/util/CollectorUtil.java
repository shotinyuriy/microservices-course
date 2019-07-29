package com.gridu.microservice.taxes.util;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectorUtil {

	public static <T> Collector<T, ?, T> getFirst() {
		return Collectors.collectingAndThen(Collectors.toList(), list -> {
			if (list.size() != 1) {
				throw new IllegalArgumentException();
			}
			return list.get(0);
		});
	}
}
