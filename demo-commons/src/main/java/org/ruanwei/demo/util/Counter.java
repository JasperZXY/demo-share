package org.ruanwei.demo.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
	private static final AtomicInteger count = new AtomicInteger(0);

	public static synchronized int  getCount() {
		return count.incrementAndGet();
	}
}
