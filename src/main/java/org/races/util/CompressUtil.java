/** 
 * 
 */
package org.races.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author preethav
 * 
 */



public class CompressUtil {
	
	/**
	 * Absolute path where the report pdf's are compressed and put in 
	 * disk.
	 */
	@Value("${compressFolder.location}")
	public String compressFolderPath;
	
	/**
	 * Absolute path where the report pdf's are compressed and put in 
	 * disk.
	 */
	@Value("${zipFolder.Name}")
	public String zipFolderName;
	
	/**
	 * Absolute path where the report pdf's are compressed and put in 
	 * disk.
	 */
	@Value("${compressedFile.location}")
	public String compressedFilePath;
	
	private static Logger log = Logger.getLogger(CompressUtil.class);

	public boolean CompressData()
	{
		if(compressFolderPath != null)
		{
		File directoryToZip = new File(compressFolderPath);
		List<File> fileList = new ArrayList<File>();
		log.info("Started getting the files list ");
		getAllFiles(directoryToZip, fileList);
		log.info("Completed getting the files list ");
		log.info("Folder Zipping Started !!!!");
		writeZipFile(directoryToZip, fileList);
		log.info("Folder Zipping Completed !!!!");
		return true;
		}
		else
		{
			log.fatal("The Compress path location is NULL !!! ");
			return false;
		}
	}
	
	public void getAllFiles(File dir, List<File> fileList) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				fileList.add(file);
				if (file.isDirectory()) {
					System.out.println("directory:" + file.getCanonicalPath());
					log.debug("directory:" + file.getCanonicalPath());
					getAllFiles(file, fileList);
				} else {
					System.out.println("     file:" + file.getCanonicalPath());
					log.debug("     file:" + file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeZipFile(File directoryToZip, List<File> fileList) {

		try {
			FileOutputStream fos = new FileOutputStream(compressedFilePath
					+ directoryToZip.getName() + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : fileList) {
				if (!file.isDirectory()) { // we only zip files, not directories
					addToZip(directoryToZip, file, zos);
				}
			}

			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			log.fatal("Exception at CompressUtil : "+e);
		} catch (IOException e) {
			//e.printStackTrace();
			log.fatal("Exception at CompressUtil : "+e);
		}
	}

	public static void addToZip(File directoryToZip, File file,
			ZipOutputStream zos) throws FileNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(file);

		// we want the zipEntry's path to be a relative path that is relative
		// to the directory being zipped, so chop off the rest of the path
		String zipFilePath = file.getCanonicalPath().substring(
				directoryToZip.getCanonicalPath().length() + 1,
				file.getCanonicalPath().length());
		System.out.println("Writing '" + zipFilePath + "' to zip file");
		log.debug("Writing '" + zipFilePath + "' to zip file");
		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

}
