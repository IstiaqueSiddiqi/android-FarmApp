package smartfarms.fragments;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import smartfarms.utils.Crop;
import smartfarms.utils.FarmDBManager;
import layout.layout.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ShowEditCrop extends Fragment {
	Button delbtn;
	Button editSaveBtn;
	EditText name, cropcategory1;
	String categoryName;
	int id;
	Crop crop = null;
	ImageView imgvw1;
	ImageButton upload;
	Uri uriSavedImage;
	String filePath;
	String category;
	int RESULT_OK = -1;
	final int THUMBSIZE = 350;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.show_edit_crop, container, false);

		final String cropname = getArguments().getString("message");
		filePath = getArguments().getString("imgPath");

		String cropcategory = getArguments().getString("cropcategory");
		crop = new Crop();
		crop.setName(cropname);

		EditText name = (EditText) view.findViewById(R.id.editCropName);
		imgvw1 = (ImageView) view.findViewById(R.id.cropImg);

		cropcategory1 = (EditText) view.findViewById(R.id.CropCategory);
		cropcategory1.setText(cropcategory);

		name.setText(cropname);

		Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeFile(filePath), THUMBSIZE, THUMBSIZE);
		imgvw1.setImageBitmap(ThumbImage);

		imgvw1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				open();
			}
		});

		editSaveBtn = (Button) view.findViewById(R.id.SaveCrop);
		editSaveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				crop.setImage(filePath);
				new FarmDBManager().editCrop(getActivity(), crop);

				Fragment fragment = new CropListView();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
		});

		delbtn = (Button) view.findViewById(R.id.DeleteCrop);
		delbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());
				alertDialogBuilder.setMessage(R.string.decision1);
				alertDialogBuilder.setPositiveButton(R.string.positive_button1,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								new FarmDBManager().deleteCrop(getActivity(),
										crop);
								Fragment newFragment = new CropListView();
								FragmentManager frgManager = getFragmentManager();
								frgManager
										.beginTransaction()
										.replace(R.id.content_frame,
												newFragment).commit();
							}
						});
				alertDialogBuilder.setNegativeButton(R.string.negative_button1,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Fragment newFragment = new CropListView();
								FragmentManager frgManager = getFragmentManager();
								frgManager
										.beginTransaction()
										.replace(R.id.content_frame,
												newFragment).commit();
							}
						});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
		return view;
	}

	public void open() {
		final CharSequence[] items = { "Take Photo", "Choose from Gallery",
				"Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {

					try {
						String timeStamp = new SimpleDateFormat(
								"yyyyMMdd_HHmmss").format(new Date());
						Intent camera = new Intent(
								android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

						File file = createImageFile();

						if (file != null) {

							uriSavedImage = Uri.fromFile(file);
							filePath = uriSavedImage.getPath();

							camera.putExtra(MediaStore.EXTRA_OUTPUT,
									uriSavedImage);
						} else {

							camera.putExtra(MediaStore.EXTRA_OUTPUT, "data");
						}
						startActivityForResult(camera, 1);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if (items[item].equals("Choose from Gallery")) {
					openGallery();

				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}

			private File createImageFile() throws IOException {

				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(new Date());
				String imageFileName = category + timeStamp;

				File storageDir = new File(Props.GALLERY_PATH.getValue());
				if (!storageDir.exists()) {
					storageDir.mkdirs();
				}
				if (!storageDir.exists()) {
					return null;
				}

				File image = new File(storageDir + File.separator
						+ imageFileName + ".jpg");

				return image;
			}
		});
		builder.show();

	}

	private void openGallery() {
		Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, 1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == RESULT_OK) {

			Bitmap photo = null;
			if (data == null) {

				photo = new BitmapFactory().decodeFile(filePath);
			} else if (data.getExtras().get("data") instanceof Bitmap) {

				photo = (Bitmap) data.getExtras().get("data");
			} else {
				Uri selectedImage = data.getData();

				filePath = getRealPathFromURI(selectedImage);
				Uri uri = Uri.parse(data.getDataString());
				photo = new BitmapFactory().decodeFile(getRealPathFromURI(uri));
			}
			Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
					BitmapFactory.decodeFile(filePath), THUMBSIZE, THUMBSIZE);
			imgvw1.setImageBitmap(ThumbImage);

		}
	}

	public String getRealPathFromURI(Uri uri) {

		String[] projection = { MediaStore.Images.Media.DATA };

		Cursor cursor = getActivity().getContentResolver().query(uri,
				projection, null, null, null);

		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
