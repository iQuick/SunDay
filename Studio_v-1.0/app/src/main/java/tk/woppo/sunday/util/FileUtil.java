package tk.woppo.sunday.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.URLEncoder;

import android.content.Context;

import tk.woppo.sunday.App;

public class FileUtil {

	/**
	 * 文件序列化对象列表
	 * 
	 * @param obj
	 * @param fileName
	 * @param mode
	 */
	public static void writeObjsToFile(Object obj, String fileName, int mode) {

		if (obj == null || fileName == null)
			return;

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = App.getContext().openFileOutput(URLEncoder.encode(fileName, "UTF-8"), mode);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从序列化文件中读取对象列表
	 * 
	 * @param fileName
	 * @return
	 */
	public static Object readObjsFromFile( String fileName) {

		if (fileName == null)
			return null;

		Object obj = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
			if (App.getContext().getFileStreamPath(fileName).exists()) {
				fis = App.getContext().openFileInput(fileName);
				ois = new ObjectInputStream(fis);
				obj = ois.readObject();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return obj;
	}

	/**
	 * 判断文件是否存在
	 * @return
	 */
	public static boolean fileIsExists(Context context,String fileName) {
		try {
			File f = new File(context.getFilesDir(), fileName);
			if (!f.exists()) {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	
//	public static long getCacheSize(Context context) {
//
//		long size = 0;
//		try {
//			size += FileSizeUtil.getFileSizes(context.getCacheDir());
//			size += FileSizeUtil.getFileSizes(context.getFilesDir());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return size;
//	}
}
