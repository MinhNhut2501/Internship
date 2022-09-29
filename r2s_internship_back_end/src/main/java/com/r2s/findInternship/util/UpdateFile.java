package com.r2s.findInternship.util;

import com.r2s.findInternship.common.FileUpload;

public interface UpdateFile {
	void update(FileUpload fileUp);
	void uploadCV(FileUpload fileUpload);
	void uploadCVApply(FileUpload fileUpload);
	void uploadExcel(FileUpload fileUp);
	void deleteFile(String url);
}
