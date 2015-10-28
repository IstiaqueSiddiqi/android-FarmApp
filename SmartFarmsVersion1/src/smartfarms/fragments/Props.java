package smartfarms.fragments;

import java.io.File;

import android.os.Environment;

public enum Props {

	PHOTO_PATH(Environment.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator + "SmartFarms"), GALLERY_PATH(Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ "SmartFarms" + File.separator + "Photos"),
	// GALLERY_PATH(File.separator),
	FILE_UPLOAD_URL("http://inignite.tcs.com/Mapping/UploadPhotos"), DATA_UPDATE_URL(
			"http://inignite.tcs.com/Mapping/UpdateData");

	String value;

	Props(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
