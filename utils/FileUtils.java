package com.wink.utils;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class FileUtils {

	
	public static String getFileName(String filePath){
		if(StringUtils.isBlank(filePath))
			throw new RuntimeException("File path can not be null");
		File file = new File(filePath);
		return file.getName();
	}
	
	public static String getFilePathFromUri(Context context, Uri selectedImage){
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = context.getContentResolver().query(
				selectedImage, filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();
		return filePath;
	}
}
