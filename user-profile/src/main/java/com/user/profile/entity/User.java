package com.user.profile.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String enrollment;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = true)
	private Integer change_type;

	@Column(name = "father_name", nullable = true)
	private String fatherName;

	@Column(name = "student_id", nullable = true)
	private String studentId;
	@Column(name = "mother_name", nullable = true)
	private String motherName;
	@Column(name = "university_name", nullable = true)
	private String universityName;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "users_roles", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	private List<Role> roles = new ArrayList<>();

	public User(Long id, String name, String enrollment, String email, String password, Integer change_type,
			String fatherName, String studentId, String motherName, String universityName, List<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.enrollment = enrollment;
		this.email = email;
		this.password = password;
		this.change_type = change_type;
		this.fatherName = fatherName;
		this.studentId = studentId;
		this.motherName = motherName;
		this.universityName = universityName;
		this.roles = roles;
	}

	public User() {
		super();
	}

	public User(String studentId, String name, String email, String enrollment, String password) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.email = email;
		this.enrollment = enrollment;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getChange_type() {
		return change_type;
	}

	public void setChange_type(Integer change_type) {
		this.change_type = change_type;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(String enrollment) {
		this.enrollment = enrollment;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", enrollment=" + enrollment + ", email=" + email + ", password="
				+ password + ", change_type=" + change_type + ", fatherName=" + fatherName + ", studentId=" + studentId
				+ ", motherName=" + motherName + ", universityName=" + universityName + ", roles=" + roles + "]";
	}

}
