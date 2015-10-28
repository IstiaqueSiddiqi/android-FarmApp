package smartfarms.fragments;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import layout.layout.R;
import smartfarms.utils.Crop;
import smartfarms.utils.FarmDBManager;
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
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CameraDemo extends Fragment {

	ImageButton imgFavorite;
	Bitmap bmp;
	ImageView plus;
	String s;

	// Spinner
	Spinner categoryCrop;
	String categoryName;

	private Uri uriSavedImage = null;
	public String category = "SmartFarms";
	private static final int CAMERA_REQUEST = 1888;
	protected static final int REQUEST_CAMERA = 0;
	protected static final int SELECT_FILE = 1;
	protected static final int RESULT_OK = -1;
	final int THUMBSIZE = 350;
	String filePath = "";
	Button viewCrop;
	Button addCrop;
	String mypath;
	String str;
	int position;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.grocerylayout, container, false);
		getActivity().getActionBar().setTitle("Crops");
		// Spinner
		categoryCrop = (Spinner) view.findViewById(R.id.spinner_category_crop);
		List<String> list = new ArrayList<String>();
		list.add("Vegetables");
		list.add("Pulses");
		list.add("Fruits");
		list.add("Food Grains");
		list.add("Others");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				this.getActivity(), android.R.layout.simple_spinner_item, list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		categoryCrop.setAdapter(dataAdapter);
		OnItemSelectedListener selectedItemListener = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				categoryName = parent.getItemAtPosition(pos).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		};

		categoryCrop.setOnItemSelectedListener(selectedItemListener);

		// Spinner data end-------------

		// imgFavorite = (ImageButton) view.findViewById(R.id.imageButton1);
		plus = (ImageView) view.findViewById(R.id.ImageView1);
		plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				open();
			}
		});

		final EditText cropName = (EditText) view.findViewById(R.id.cropName);
		addCrop = (Button) view.findViewById(R.id.addCrop);
		addCrop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				str = new FarmDBManager().getdefland(getActivity());
				if (str.equals("false")) {

					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							getActivity());
					alertDialogBuilder.setMessage(R.string.decision2);
					alertDialogBuilder.setPositiveButton(
							R.string.positive_button1,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									Fragment frag = new Landhm();
									FragmentManager frgManager = getFragmentManager();
									frgManager.beginTransaction()
											.replace(R.id.content_frame, frag)
											.commit();
								}
							});
					alertDialogBuilder.setNegativeButton(
							R.string.negative_button1,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Fragment newFragment = new CameraDemo();
									FragmentManager frgManager = getFragmentManager();
									frgManager
											.beginTransaction()
											.replace(R.id.content_frame,
													newFragment).commit();
								}
							});

					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				} else {
					String name = cropName.getText().toString();
					Crop crop = new Crop();
					crop.setName(name);
					crop.setImage(filePath);
					crop.setLname(str);
					crop.setCropcategory(categoryName);// Setting default land
														// for crop
					new FarmDBManager().addCrop(getActivity(), crop);

					// redirecting
					Bundle bundle = new Bundle();
					bundle.putString("message", categoryName);
					Log.d("CATEGORY NAME IN CAMERADEMO", categoryName);
					Fragment fragment = new Crophm();
					fragment.setArguments(bundle);
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment).commit();
				}

			}
		});

		viewCrop = (Button) view.findViewById(R.id.viewCrop);
		viewCrop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Fragment fragment = new Crophm();
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
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
							Log.d("filepath", filePath);
							/*************************/

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
					// onActivityResult(2 ,1,intent );
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}

			private File createImageFile() throws IOException {
				// Create an image file name
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
				Log.d("picture directory ", storageDir.getAbsolutePath());
				/*************************/
				File image = new File(storageDir + File.separator
						+ imageFileName + ".jpg");
				Log.d("image absolute path ", image.getAbsolutePath());
				/*************************/
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
		Log.d("onactivityresult", "yes");//
		if (requestCode == 1 && resultCode == RESULT_OK) {

			Log.d("path ", data + "");
			/*************************/
			Bitmap photo = null;
			if (data == null) {
				Log.d("one", "1");
				/*************************/
				photo = new BitmapFactory().decodeFile(filePath);
			} else if (data.getExtras().get("data") instanceof Bitmap) {
				Log.d("one", "2");
				photo = (Bitmap) data.getExtras().get("data");
			} else {
				Uri selectedImage = data.getData();
				Log.d("Gallery Image Path", getRealPathFromURI(selectedImage));
				filePath = getRealPathFromURI(selectedImage);
				Uri uri = Uri.parse(data.getDataString());
				photo = new BitmapFactory().decodeFile(getRealPathFromURI(uri));
			}
			Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
					BitmapFactory.decodeFile(filePath), THUMBSIZE, THUMBSIZE);
			plus.setImageBitmap(ThumbImage);
			// plus.setImageBitmap(photo);

		}
	}

	public String getRealPathFromURI(Uri uri) {
		Log.d("uri in getrealpath", uri.toString());
		String[] projection = { MediaStore.Images.Media.DATA };
		Log.d("msg", projection.length + "");

		Cursor cursor = getActivity().getContentResolver().query(uri,
				projection, null, null, null);

		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
