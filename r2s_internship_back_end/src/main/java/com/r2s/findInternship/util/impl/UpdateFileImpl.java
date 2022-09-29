package com.r2s.findInternship.util.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.r2s.findInternship.common.FileUpload;
import com.r2s.findInternship.util.UpdateFile;
@Component
public class UpdateFileImpl implements UpdateFile{
	@Autowired
	ServletContext application;
	
	@Autowired
	Storage storage ;
	
	String bucketName = "r2s";

	@Override
	public void update(FileUpload fileUpload) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			fileUpload.setOutput(LocalDateTime.now().format(formatter) + 
					fileUpload.getFile().getOriginalFilename().substring(fileUpload.getFile().getOriginalFilename().lastIndexOf(".")));

			Credentials credentials = GoogleCredentials.fromStream(new ClassPathResource("keystorage.json").getInputStream());
			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
			
			String folderName =  "images/";
			BlobId blobId = BlobId.of("r2s",folderName + fileUpload.getOutput());
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(fileUpload.getFile().getContentType()).build();
			byte[] arr = fileUpload.getFile().getBytes();
			storage.create(blobInfo, arr);
			
			fileUpload.setOutput("https://storage.googleapis.com/" + bucketName + "/" + folderName +fileUpload.getOutput());
			fileUpload.setFile(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void uploadCV(FileUpload fileUpload) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			fileUpload.setOutput(LocalDateTime.now().format(formatter) + 
					fileUpload.getFile().getOriginalFilename().substring(fileUpload.getFile().getOriginalFilename().lastIndexOf(".")));

			Credentials credentials = GoogleCredentials.fromStream(new ClassPathResource("keystorage.json").getInputStream());
			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
			
			String folderName =  "CV/";
			BlobId blobId = BlobId.of("r2s",folderName + fileUpload.getOutput());
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(fileUpload.getFile().getContentType()).build();
			byte[] arr = fileUpload.getFile().getBytes();
			storage.create(blobInfo, arr);
			
			fileUpload.setOutput("https://storage.googleapis.com/" + bucketName + "/" + folderName +fileUpload.getOutput());
			fileUpload.setFile(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void uploadExcel(FileUpload fileUpload) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			fileUpload.setOutput(LocalDateTime.now().format(formatter) + 
					fileUpload.getFile().getOriginalFilename().substring(fileUpload.getFile().getOriginalFilename().lastIndexOf(".")));

			Credentials credentials = GoogleCredentials.fromStream(new ClassPathResource("keystorage.json").getInputStream());
			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
			
			String folderName =  "Students/";
			BlobId blobId = BlobId.of("r2s",folderName + fileUpload.getOutput());
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(fileUpload.getFile().getContentType()).build();
			byte[] arr = fileUpload.getFile().getBytes();
			storage.create(blobInfo, arr);
			
			fileUpload.setOutput("https://storage.googleapis.com/" + bucketName + "/" + folderName +fileUpload.getOutput());
			fileUpload.setFile(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteFile(String fullPath) {
		try {
			String name = fullPath.substring(fullPath.lastIndexOf("/") + 1);
			
			Credentials credentials = GoogleCredentials.fromStream(new ClassPathResource("keystorage.json").getInputStream());
			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

			storage.delete("r2s", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void uploadCVApply(FileUpload fileUpload) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			fileUpload.setOutput(LocalDateTime.now().format(formatter) + 
					fileUpload.getFile().getOriginalFilename().substring(fileUpload.getFile().getOriginalFilename().lastIndexOf(".")));

			Credentials credentials = GoogleCredentials.fromStream(new ClassPathResource("keystorage.json").getInputStream());
			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
			
			String folderName =  "images/";
			BlobId blobId = BlobId.of("r2s",folderName + fileUpload.getOutput());
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(fileUpload.getFile().getContentType()).build();
			byte[] arr = fileUpload.getFile().getBytes();
			storage.create(blobInfo, arr);
			
			fileUpload.setOutput("https://storage.googleapis.com/" + bucketName + "/" + folderName +fileUpload.getOutput());
			fileUpload.setFile(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
