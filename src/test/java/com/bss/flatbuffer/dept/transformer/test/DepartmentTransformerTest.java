package com.bss.flatbuffer.dept.transformer.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bss.flatbuffer.dept.business.dto.Department;
import com.bss.flatbuffer.dept.business.dto.Genre;
import com.bss.flatbuffer.dept.business.dto.Subject;
import com.bss.flatbuffer.dept.transformer.DepartmentTransformer;

/**
 * 
 * @author amit
 *
 */
public class DepartmentTransformerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentTransformerTest.class);
	private DepartmentTransformer departmentTransformer;

	@Before
	public void setUp() {
		departmentTransformer = new DepartmentTransformer();
	}

	@Test
	public void testTransformation() {
		String departmentName = "Department Of Mathematics";
		int departmentId = 1;
		String tag = "No 1 Dept";
		Genre bookGenre = Genre.Educational;
		Subject favSubject = Subject.Maths;
		Department testData = TestDataProvider.createTestData(departmentId, departmentName, tag, bookGenre, favSubject);
		LOGGER.info("Original Object: {}", testData);
		byte[] serializedData = departmentTransformer.serialize(testData);
		
		Department transformedObj = departmentTransformer.deserialize(serializedData);
		LOGGER.info("Transformed Object: {}", transformedObj);

		Assert.assertSame("Should be same", departmentId, transformedObj.getDepartmentId());
		Assert.assertEquals("Should be same", departmentName, transformedObj.getDepartmentName());
		Assert.assertEquals("Should be same", tag, transformedObj.getDepartmentTag());
		Assert.assertEquals("Should be same", testData.getBooks().size(), transformedObj.getBooks().size());
		Assert.assertEquals("Should be same", testData.getStudents().size(), transformedObj.getStudents().size());
	}
}