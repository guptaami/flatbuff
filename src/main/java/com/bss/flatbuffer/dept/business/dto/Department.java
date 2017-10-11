package com.bss.flatbuffer.dept.business.dto;

import java.util.List;

/**
 * 
 * @author amit
 *
 */
public class Department {
	private String departmentName;
	private String departmentTag;
	private Integer departmentId;
	private List<Book> books;
	private List<Student> students;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentTag() {
		return departmentTag;
	}

	public void setDepartmentTag(String departmentTag) {
		this.departmentTag = departmentTag;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((books == null) ? 0 : books.hashCode());
		result = prime * result + ((departmentId == null) ? 0 : departmentId.hashCode());
		result = prime * result + ((departmentName == null) ? 0 : departmentName.hashCode());
		result = prime * result + ((departmentTag == null) ? 0 : departmentTag.hashCode());
		result = prime * result + ((students == null) ? 0 : students.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
		if (departmentId == null) {
			if (other.departmentId != null)
				return false;
		} else if (!departmentId.equals(other.departmentId))
			return false;
		if (departmentName == null) {
			if (other.departmentName != null)
				return false;
		} else if (!departmentName.equals(other.departmentName))
			return false;
		if (departmentTag == null) {
			if (other.departmentTag != null)
				return false;
		} else if (!departmentTag.equals(other.departmentTag))
			return false;
		if (students == null) {
			if (other.students != null)
				return false;
		} else if (!students.equals(other.students))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Department [departmentName=");
		builder.append(departmentName);
		builder.append(", departmentTag=");
		builder.append(departmentTag);
		builder.append(", departmentId=");
		builder.append(departmentId);
		builder.append(", books=");
		builder.append(books);
		builder.append(", students=");
		builder.append(students);
		builder.append("]");
		return builder.toString();
	}
}