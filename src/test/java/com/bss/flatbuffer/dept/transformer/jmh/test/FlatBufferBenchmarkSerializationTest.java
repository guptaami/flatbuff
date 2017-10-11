package com.bss.flatbuffer.dept.transformer.jmh.test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import com.bss.flatbuffer.dept.business.dto.Department;
import com.bss.flatbuffer.dept.business.dto.Genre;
import com.bss.flatbuffer.dept.business.dto.Subject;
import com.bss.flatbuffer.dept.transformer.DepartmentTransformer;
import com.bss.flatbuffer.dept.transformer.test.TestDataProvider;

/**
 * 
 * @author amit
 *
 */
public class FlatBufferBenchmarkSerializationTest {

	@Test
	public void startBenchmarkTest() throws Exception {
		// @formatter:off
		Options options = new OptionsBuilder().include(this.getClass().getName() + ".*")
				.mode(Mode.Throughput)
				.timeUnit(TimeUnit.SECONDS)
				.warmupTime(TimeValue.seconds(10))
				.warmupIterations(2)
				.measurementTime(TimeValue.seconds(10))
				.measurementIterations(2)
				.threads(4)
				.forks(1)
				.shouldFailOnError(true)
				.shouldDoGC(true)
				.build();
		// @formatter:on
		new Runner(options).run();
	}

	@State(Scope.Thread)
	public static class BenchmarkState {
		private Department department;
		private DepartmentTransformer flatBuffertransformer;

		@Setup
		public void init() {
			Random rand = new Random();
			this.flatBuffertransformer = new DepartmentTransformer();

			int departmentId = rand.nextInt(10000);
			String departmentName = "Department:" + departmentId;
			String tag = "Tag:" + departmentId;
			this.department = TestDataProvider.createTestData(departmentId, departmentName, tag, Genre.Educational,
					Subject.Maths);
		}
	}

	@Benchmark
	public void serialization(final BenchmarkState state, final Blackhole blackhole) {
		state.flatBuffertransformer.serialize(state.department);
	}
}
