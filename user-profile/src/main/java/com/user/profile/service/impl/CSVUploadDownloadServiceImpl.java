package com.user.profile.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.user.profile.entity.User;
import com.user.profile.helper.CSVHelper;
import com.user.profile.repository.UserRepository;
import com.user.profile.service.CSVUploadDownloadService;

@Service
public class CSVUploadDownloadServiceImpl implements CSVUploadDownloadService {

	@Autowired
	private UserRepository userRepository;

	public void save(MultipartFile file) {
		try {
			List<User> userList = CSVHelper.csvToUsers(file.getInputStream());
			userRepository.saveAll(userList);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public ByteArrayInputStream load() {
		List<User> tutorials = userRepository.findAll();

		ByteArrayInputStream in = CSVHelper.usersToCSV(tutorials);
		return in;
	}

	public List<User> getAllStudents() {
		return userRepository.findAll();
	}
}
