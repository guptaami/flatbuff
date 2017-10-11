package com.bss.flatbuffer.dept.transformer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.bss.flatbuffer.dept.FBBook;
import com.bss.flatbuffer.dept.FBDepartment;
import com.bss.flatbuffer.dept.FBGenre;
import com.bss.flatbuffer.dept.FBStudent;
import com.bss.flatbuffer.dept.FBSubject;
import com.bss.flatbuffer.dept.business.dto.Book;
import com.bss.flatbuffer.dept.business.dto.Department;
import com.bss.flatbuffer.dept.business.dto.Genre;
import com.bss.flatbuffer.dept.business.dto.Student;
import com.bss.flatbuffer.dept.business.dto.Subject;
import com.google.flatbuffers.FlatBufferBuilder;

/**
 * 
 * @author amit
 *
 */
public class DepartmentTransformer implements Transformer<Department> {

	@Override
	public byte[] serialize(final Department t) {
		byte[] response = null;
		if (t != null) {
			FlatBufferBuilder builder = new FlatBufferBuilder(0);

			List<Book> books = t.getBooks();
			int[] vectorForBooks = getVectorForBooks(books, builder);
			int booksVectorInt = FBDepartment.createBooksVector(builder, vectorForBooks);

			List<Student> students = t.getStudents();
			int[] vectorForStudent = getVectorForStudents(students, builder);
			int studentVectorInt = FBDepartment.createStudentsVector(builder, vectorForStudent);

			int departmentNameOffset = builder.createString(t.getDepartmentName());
			int departmentTagOffset = builder.createString(t.getDepartmentTag());

			FBDepartment.startFBDepartment(builder);
			FBDepartment.addDepartmentName(builder, departmentNameOffset);
			FBDepartment.addDepartmentTag(builder, departmentTagOffset);
			FBDepartment.addDepartmentId(builder, t.getDepartmentId());

			FBDepartment.addBooks(builder, booksVectorInt);
			FBDepartment.addStudents(builder, studentVectorInt);
			int deptEnd = FBDepartment.endFBDepartment(builder);
			builder.finish(deptEnd);

			response = builder.sizedByteArray();
		}

		return response;
	}

	@Override
	public Department deserialize(final byte[] stream) {
		Department department = null;
		if (stream != null) {
			department = new Department();
			ByteBuffer buffer = ByteBuffer.wrap(stream);
			FBDepartment rootAsDept = FBDepartment.getRootAsFBDepartment(buffer);

			department.setDepartmentId(rootAsDept.departmentId());
			department.setDepartmentName(rootAsDept.departmentName());
			department.setDepartmentTag(rootAsDept.departmentTag());

			List<Book> books = getBooksFromRoot(rootAsDept);
			department.setBooks(books);

			List<Student> students = getStudentsFromRoot(rootAsDept);
			department.setStudents(students);
		}

		return department;
	}

	private List<Book> getBooksFromRoot(final FBDepartment rootAsDept) {
		List<Book> books = null;
		if (rootAsDept != null) {
			int noOfBooks = rootAsDept.booksLength();
			if (noOfBooks > 0) {
				books = new ArrayList<>();
				for (int i = 0; i < noOfBooks; i++) {
					Book book = new Book();
					FBBook fbBook = rootAsDept.books(i);
					book.setAuthorDesc(fbBook.authorDesc());
					book.setAuthorId(fbBook.authorId());
					book.setBookId(fbBook.bookId());
					String genreName = FBGenre.name(fbBook.genre());
					book.setGenre(Genre.valueOf(genreName));

					books.add(book);
				}
			}
		}

		return books;
	}

	private List<Student> getStudentsFromRoot(final FBDepartment rootAsDept) {
		List<Student> students = null;
		if (rootAsDept != null) {
			int noOfStudents = rootAsDept.studentsLength();
			if (noOfStudents > 0) {
				students = new ArrayList<>();
				for (int i = 0; i < noOfStudents; i++) {
					Student student = new Student();
					FBStudent fbStudent = rootAsDept.students(i);
					student.setAddress(fbStudent.address());
					student.setStudentId(fbStudent.studentId());
					student.setStudentName(fbStudent.studentName());
					String favSubject = FBSubject.name(fbStudent.favSubject());
					student.setFavSubject(Subject.valueOf(favSubject));

					students.add(student);
				}
			}
		}

		return students;
	}

	private int[] getVectorForBooks(final List<Book> books, final FlatBufferBuilder builder) {
		int[] booksVector = null;

		if (books != null && books.size() > 0) {
			booksVector = new int[books.size()];
			int i = 0;
			for (Book book : books) {
				int offset = 0;
				int bookIdOffset = builder.createString(book.getBookId());
				int authorIdOffset = builder.createString(book.getAuthorId());
				int authorDescOffset = builder.createString(book.getAuthorDesc());

				FBBook.startFBBook(builder);
				FBBook.addBookId(builder, bookIdOffset);
				FBBook.addAuthorId(builder, authorIdOffset);
				FBBook.addAuthorDesc(builder, authorDescOffset);

				Genre genre = book.getGenre();
				if (genre != null) {
					byte gnr = 0;
					if (genre.equals(Genre.Educational)) {
						gnr = FBGenre.Educational;
					} else if (genre.equals(Genre.Romantic)) {
						gnr = FBGenre.Romantic;
					} else if (genre.equals(Genre.Thriller)) {
						gnr = FBGenre.Thriller;
					}
					FBBook.addGenre(builder, gnr);
				}
				offset = FBBook.endFBBook(builder);

				booksVector[i++] = offset;
			}
			;
		}

		return booksVector;
	}

	private int[] getVectorForStudents(final List<Student> students, final FlatBufferBuilder builder) {
		int[] studentVector = null;

		if (students != null && students.size() > 0) {
			studentVector = new int[students.size()];
			int i = 0;
			for (Student student : students) {
				int offset = 0;
				int studentNameOffset = builder.createString(student.getStudentName());
				int addressOffset = builder.createString(student.getAddress());

				FBStudent.startFBStudent(builder);
				FBStudent.addStudentId(builder, student.getStudentId());
				FBStudent.addStudentName(builder, studentNameOffset);
				FBStudent.addAddress(builder, addressOffset);

				Subject favSubject = student.getFavSubject();
				if (favSubject != null) {
					byte fsub = 0;
					if (favSubject.equals(Subject.Chemistry)) {
						fsub = FBSubject.Chemistry;
					} else if (favSubject.equals(Subject.English)) {
						fsub = FBSubject.English;
					} else if (favSubject.equals(Subject.Maths)) {
						fsub = FBSubject.Maths;
					}
					FBStudent.addFavSubject(builder, fsub);
				}

				offset = FBStudent.endFBStudent(builder);

				studentVector[i++] = offset;
			}
		}

		return studentVector;
	}
}
