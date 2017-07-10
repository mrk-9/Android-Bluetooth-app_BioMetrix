package com.biomx.android.client.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileCompressUtil {

	private static final int BUFFER = 2048;

	private List<String> _files;
	private String _zipFile;

	public FileCompressUtil(List<String> fileNames, String zipFile) {
		_files = fileNames;
		_zipFile = zipFile;
	}

	public void zip() {
		File root = android.os.Environment.getExternalStorageDirectory();
		File dataDirectory = new File(root.getAbsolutePath()
				+ DataHolder.homeFolderPath);
		try {
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(_zipFile);

			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					dest));

			byte data[] = new byte[BUFFER];

			for (String fileName : _files) {
				File file = new File(dataDirectory, fileName);
				FileInputStream fi = new FileInputStream(file.getAbsolutePath());
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(fileName.substring(fileName
						.lastIndexOf("/") + 1));
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}