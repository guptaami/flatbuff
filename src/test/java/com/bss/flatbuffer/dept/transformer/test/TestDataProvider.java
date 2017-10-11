package com.bss.flatbuffer.dept.transformer.test;

import java.util.ArrayList;

import com.bss.flatbuffer.dept.business.dto.Book;
import com.bss.flatbuffer.dept.business.dto.Department;
import com.bss.flatbuffer.dept.business.dto.Genre;
import com.bss.flatbuffer.dept.business.dto.Student;
import com.bss.flatbuffer.dept.business.dto.Subject;

public class TestDataProvider {

	public static Department createTestData(
			final int departmentId, 
			final String departmentName, 
			final String tag,
			final Genre bookGenre,
			final Subject favSubject) {
		return new Department() {{
			setDepartmentId(departmentId);
			setDepartmentName(departmentName);
			setDepartmentTag(tag);
			setBooks(new ArrayList<Book>() {{
				add(new Book() {{
					setBookId("Battle of Panipat");
					setAuthorId("Unknown");
					setAuthorDesc("Author Not known");
					setGenre(bookGenre);
				}});
				add(new Book() {{
					setBookId("Shiva Triology");
					setAuthorId("Patel");
					setAuthorDesc("Popular Author");
					setGenre(bookGenre);
				}});
			}});
			setStudents(new ArrayList<Student>() {{
				add(new Student() {{
					setStudentId(111);
					setStudentName("Vihaan Shinde");
					setAddress("Pune");
					setFavSubject(favSubject);
				}});
				add(new Student() {{
					setStudentId(112);
					setStudentName("Tanush Shinde");
					setAddress("Chinchwad");
					setFavSubject(favSubject);
				}});
				add(new Student() {{
					setStudentId(113);
					setStudentName("BSS");
					setAddress("Pune");
					setFavSubject(favSubject);
				}});
			}});
		}};
	}
}
