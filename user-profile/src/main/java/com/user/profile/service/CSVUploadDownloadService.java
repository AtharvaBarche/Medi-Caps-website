package com.user.profile.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.user.profile.entity.User;

public interface CSVUploadDownloadService {

	public void save(MultipartFile file);

	public ByteArrayInputStream load();

	public List<User> getAllStudents();
}
