package com.chablis.lilosoft.utils;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtil {

	public void copyFile(String srcPath, String destPath) throws IOException {
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);
		if (!srcFile.exists()) {
			return;
		}
		if (!destFile.exists()) {
			destFile.mkdirs();
		}

		FileUtils.copyDirectory(srcFile, destFile);
//		FileUtils.copyFile(srcFile, destFile);

	}

	public void deleteFile(String srcPath) throws IOException {
		File file = new File(srcPath);
		if (file.exists()) {
			FileUtils.deleteDirectory(file);
		}
	}
}
