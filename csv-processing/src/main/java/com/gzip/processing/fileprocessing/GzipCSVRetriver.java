package com.gzip.processing.fileprocessing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipCSVRetriver {

	private static BufferedInputStream fileInputBuffer;
	
	public static void closeFileConnection() {
		if(fileInputBuffer != null) {
			try {
				fileInputBuffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	public static GZIPInputStream GZipInputStreamFromLocal(String fileLocation){
		try {
			FileInputStream fileInput = new FileInputStream(new File(fileLocation));
			fileInputBuffer = new BufferedInputStream(fileInput);
			GZIPInputStream gz = new GZIPInputStream(fileInputBuffer);
			return gz;
		}catch(MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {}
		return null;
	}
	
	public static long gZipFileSize(String urlAddress) throws Exception{
		URL fileSourceLocation = new URL(urlAddress);
		HttpURLConnection conn = (HttpURLConnection) fileSourceLocation.openConnection();
	    conn.setRequestMethod("HEAD");
	    return conn.getContentLengthLong();
	}
	
	public static void gZipDownloadFromURL(String urlAddress,String filename) throws NullPointerException,IOException{
		URL fileSourceLocation = new URL(urlAddress);
		URLConnection fileConnection = fileSourceLocation.openConnection();
		fileConnection.setRequestProperty("Accept-Encoding", "gz");
		
		fileInputBuffer = new BufferedInputStream(fileConnection.getInputStream());
		GZIPInputStream gzIn = new GZIPInputStream(fileInputBuffer);
		
		File outputFile = new File(filename);
		FileOutputStream fos = new FileOutputStream(outputFile);
        GZIPOutputStream gzOut = new GZIPOutputStream(fos);
        byte data[] = new byte[65536];
        int gsize = 0;
        while((gsize = gzIn.read(data)) != -1){
        	gzOut.write(data, 0, gsize);
        }
        gzIn.close();
        gzOut.close();
	}
	
	
	public static GZIPInputStream gZipInputStreamFromURL(String urlAddress) throws NullPointerException,IOException{
		URL fileSourceLocation = new URL(urlAddress);
		URLConnection fileConnection = fileSourceLocation.openConnection();
		fileConnection.setRequestProperty("Accept-Encoding", "gz");
		
		fileInputBuffer = new BufferedInputStream(fileConnection.getInputStream());
		GZIPInputStream gz = new GZIPInputStream(fileInputBuffer);

		return gz;
	}

}
