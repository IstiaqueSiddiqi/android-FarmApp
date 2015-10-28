package smartfarms.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import smartfarms.utils.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class FarmDBManager {

	public long addCrop(Context context, Crop crop) {
		long newRowId;
		SQLiteDatabase db = null;
		try {
			db = new FarmDBHelper(context).getWritableDatabase();
			Timestamp ts = new Timestamp(Calendar.getInstance()
					.getTimeInMillis());
			ContentValues values = new ContentValues();
			values.put(FarmDBContract.Crop.COLUMN_NAME_NAME, crop.getName());
			values.put(FarmDBContract.Crop.COLUMN_NAME_IMAGE, crop.getImage());
			values.put(FarmDBContract.Crop.COLUMN_NAME_LANDNAME,
					crop.getLname());
			values.put(FarmDBContract.Crop.COLUMN_NAME_CROPCATEGORY,
					crop.getCropcategory());

			newRowId = db.insert(FarmDBContract.Crop.TABLE_NAME, "", values);
			Log.d("Crop Name", crop.getName());
			Log.d("Crop ID", crop.getId() + "");
			Toast.makeText(context, "Crop added Successfully.",
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
			newRowId = -1;
		} finally {
			db.close();
		}
		return newRowId;
	}

	public long editCrop(Context context, Crop crop) {
		long newRowId = -1;
		SQLiteDatabase db = null;
		try {
			db = new FarmDBHelper(context).getWritableDatabase();
			Timestamp ts = new Timestamp(Calendar.getInstance()
					.getTimeInMillis());

			ContentValues values = new ContentValues();

			values.put(FarmDBContract.Crop.COLUMN_NAME_NAME, crop.getName());
			values.put(FarmDBContract.Crop.COLUMN_NAME_IMAGE, crop.getImage());

			newRowId = db.update(FarmDBContract.Crop.TABLE_NAME, values,
					FarmDBContract.Crop.COLUMN_NAME_NAME + " = ?",
					new String[] { crop.getName() });
			Toast.makeText(context, "Updated Successfully.", Toast.LENGTH_SHORT)
					.show();
		} catch (Exception e) {
			e.printStackTrace();
			newRowId = -1;
		} finally {
			db.close();
		}
		return newRowId;
	}

	public List<Crop> getCropDetails(Context context) {
		List<Crop> details = new ArrayList<Crop>();
		SQLiteDatabase db = null;
		try {

			db = new FarmDBHelper(context).getReadableDatabase();

			String[] projection = { FarmDBContract.Crop.COLUMN_NAME_ID,
					FarmDBContract.Crop.COLUMN_NAME_NAME,
					FarmDBContract.Crop.COLUMN_NAME_IMAGE,
					FarmDBContract.Crop.COLUMN_NAME_LANDNAME,
					FarmDBContract.Crop.COLUMN_NAME_CROPCATEGORY };

			String sortOrder = FarmDBContract.Crop.COLUMN_NAME_ID + " DESC";

			Cursor c = db.query(FarmDBContract.Crop.TABLE_NAME,

			projection, null, null, null, null, sortOrder);

			if (c != null) {
				boolean flag = c.moveToFirst();
				System.out.println(flag);
				do {
					Crop crop = new Crop();
					crop.setId(c.getInt(0));
					crop.setName(c.getString(1));
					crop.setImage(c.getString(2));
					crop.setLname(c.getString(3));
					crop.setCropcategory(c.getString(4));
					Log.d("crop ", crop.getName() + " land " + crop.getLname());
					details.add(crop);
				} while (c.moveToNext());
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}
		return details;
	}

	public long addLand(Context context, Land land) {
		long newRowId = -1;
		SQLiteDatabase db = null;
		try {

			db = new FarmDBHelper(context).getWritableDatabase();
			Timestamp ts = new Timestamp(Calendar.getInstance()
					.getTimeInMillis());

			ContentValues values = new ContentValues();
			values.put(FarmDBContract.Land.COLUMN_NAME_NAME, land.getName());
			values.put(FarmDBContract.Land.COLUMN_NAME_AREA, land.getArea());
			values.put(FarmDBContract.Land.COLUMN_NAME_LAT, land.getLat());
			values.put(FarmDBContract.Land.COLUMN_NAME_LNG, land.getLng());
			values.put(FarmDBContract.Land.COLUMN_NAME_STATUS, land.getStatus());
			values.put(FarmDBContract.Land.COLUMN_NAME_LOCATION,
					land.getLocation());
			Toast.makeText(context, land.getStatus(), Toast.LENGTH_LONG).show();
			newRowId = db.insert(FarmDBContract.Land.TABLE_NAME, "", values);

		} catch (Exception e) {
			e.printStackTrace();
			newRowId = -1;
		} finally {
			db.close();
		}
		return newRowId;
	}

	public long editLand(Context context, Land land) {
		long newRowId = -1;
		SQLiteDatabase db = null;
		try {
			db = new FarmDBHelper(context).getWritableDatabase();
			Timestamp ts = new Timestamp(Calendar.getInstance()
					.getTimeInMillis());

			ContentValues values = new ContentValues();

			values.put(FarmDBContract.Land.COLUMN_NAME_NAME, land.getName());
			values.put(FarmDBContract.Land.COLUMN_NAME_AREA, land.getArea());
			values.put(FarmDBContract.Land.COLUMN_NAME_LAT, land.getLat());
			values.put(FarmDBContract.Land.COLUMN_NAME_LNG, land.getLng());
			values.put(FarmDBContract.Land.COLUMN_NAME_LOCATION,
					land.getLocation());
			values.put(FarmDBContract.Land.COLUMN_NAME_STATUS, land.getStatus());

			newRowId = db.update(FarmDBContract.Land.TABLE_NAME, values,
					FarmDBContract.Land.COLUMN_NAME_NAME + " = ?",
					new String[] { land.getName() });
			Toast.makeText(context, "Updated Successfully.", Toast.LENGTH_SHORT)
					.show();
		} catch (Exception e) {
			e.printStackTrace();
			newRowId = -1;
		} finally {
			db.close();
		}
		return newRowId;
	}

	public long deleteLand(Context context, Land land) {
		long newRowId = -1;
		SQLiteDatabase db = null;
		try {

			db = new FarmDBHelper(context).getWritableDatabase();
			Timestamp ts = new Timestamp(Calendar.getInstance()
					.getTimeInMillis());

			db.delete(FarmDBContract.Land.TABLE_NAME,
					FarmDBContract.Land.COLUMN_NAME_NAME + " = ?",
					new String[] { land.getName() });
			Toast.makeText(context, "Deleted Successfully.", Toast.LENGTH_SHORT)
					.show();

		} catch (Exception e) {
			e.printStackTrace();
			newRowId = -1;
		} finally {
			db.close();
		}
		return newRowId;
	}

	public long deleteCrop(Context context, Crop crop) {
		long newRowId = -1;
		SQLiteDatabase db = null;
		try {

			db = new FarmDBHelper(context).getWritableDatabase();
			Timestamp ts = new Timestamp(Calendar.getInstance()
					.getTimeInMillis());

			db.delete(FarmDBContract.Crop.TABLE_NAME,
					FarmDBContract.Crop.COLUMN_NAME_NAME + " = ?",
					new String[] { crop.getName() });
			Toast.makeText(context, "Deleted Successfully.", Toast.LENGTH_SHORT)
					.show();

		} catch (Exception e) {
			e.printStackTrace();
			newRowId = -1;
		} finally {
			db.close();
		}
		return newRowId;
	}

	public String getdefland(Context context) {

		SQLiteDatabase db = null;
		String s = "false";
		try {

			db = new FarmDBHelper(context).getReadableDatabase();

			Cursor c = db.rawQuery("SELECT "
					+ FarmDBContract.Land.COLUMN_NAME_NAME + " FROM "
					+ FarmDBContract.Land.TABLE_NAME + " WHERE "
					+ FarmDBContract.Land.COLUMN_NAME_STATUS + "= ?",
					new String[] { "true" });

			if (c.getCount() == 0)
				s = "false";
			else {
				c.moveToFirst();
				s = c.getString(c
						.getColumnIndex(FarmDBContract.Land.COLUMN_NAME_NAME));
			}

			Log.d("Default land added by you ", s);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}

		return s;
	}

	public List<Land> getLandDetails(Context context) {
		List<Land> details = new ArrayList<Land>();
		SQLiteDatabase db = null;
		try {

			db = new FarmDBHelper(context).getReadableDatabase();

			String[] projection = { FarmDBContract.Land.COLUMN_NAME_ID,
					FarmDBContract.Land.COLUMN_NAME_NAME,
					FarmDBContract.Land.COLUMN_NAME_AREA,
					FarmDBContract.Land.COLUMN_NAME_LAT,
					FarmDBContract.Land.COLUMN_NAME_LNG,
					FarmDBContract.Land.COLUMN_NAME_STATUS,
					FarmDBContract.Land.COLUMN_NAME_LOCATION };

			String sortOrder = FarmDBContract.Land.COLUMN_NAME_ID + " DESC";

			Cursor c = db.query(FarmDBContract.Land.TABLE_NAME,

			projection, null, null, null, null, sortOrder);

			if (c != null) {
				boolean flag = c.moveToFirst();
				System.out.println(flag);
				do {
					Land land = new Land();
					land.setId(c.getInt(0));
					land.setName(c.getString(1));
					land.setArea(c.getInt(2));
					land.setLat(c.getDouble(3));
					land.setLng(c.getDouble(4));
					land.setStatus(c.getString(5));
					land.setLocation(c.getString(6));
					details.add(land);

				} while (c.moveToNext());
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}
		return details;
	}

	public long addActivity(Context context, Activity activity) {
		long newRowId = -1;
		SQLiteDatabase db = null;
		try {

			db = new FarmDBHelper(context).getWritableDatabase();
			Timestamp ts = new Timestamp(Calendar.getInstance()
					.getTimeInMillis());

			ContentValues values = new ContentValues();
			values.put(FarmDBContract.Activity.COLUMN_NAME_CROP,
					activity.getCrop());
			values.put(FarmDBContract.Activity.COLUMN_NAME_LAND,
					activity.getLand());
			values.put(FarmDBContract.Activity.COLUMN_NAME_DATE,
					activity.getDate());
			values.put(FarmDBContract.Activity.COLUMN_NAME_EXPENSE,
					activity.getExpense());
			values.put(FarmDBContract.Activity.COLUMN_NAME_INCOME,
					activity.getIncome());
			values.put(FarmDBContract.Activity.COLUMN_NAME_LABOURERS,
					activity.getLabourers());
			values.put(FarmDBContract.Activity.COLUMN_NAME_QUANTITY,
					activity.getQuantity());
			values.put(FarmDBContract.Activity.COLUMN_NAME_TYPE,
					activity.getType());
			values.put(FarmDBContract.Activity.COLUMN_NAME_CATEGORY,
					activity.getCategory());
			newRowId = db
					.insert(FarmDBContract.Activity.TABLE_NAME, "", values);

			Toast.makeText(context, "Added Successfully", Toast.LENGTH_LONG)
					.show();

		} catch (Exception e) {
			e.printStackTrace();
			newRowId = -1;
		} finally {
			db.close();
		}
		return newRowId;
	}

	public long editActivity(Context context, Activity activity) {
		long newRowId = -1;
		SQLiteDatabase db = null;
		try {
			db = new FarmDBHelper(context).getWritableDatabase();
			Timestamp ts = new Timestamp(Calendar.getInstance()
					.getTimeInMillis());

			ContentValues values = new ContentValues();

			values.put(FarmDBContract.Activity.COLUMN_NAME_CROP,
					activity.getCrop());
			values.put(FarmDBContract.Activity.COLUMN_NAME_LAND,
					activity.getLand());
			values.put(FarmDBContract.Activity.COLUMN_NAME_DATE,
					activity.getDate());
			values.put(FarmDBContract.Activity.COLUMN_NAME_EXPENSE,
					activity.getExpense());
			values.put(FarmDBContract.Activity.COLUMN_NAME_INCOME,
					activity.getIncome());
			values.put(FarmDBContract.Activity.COLUMN_NAME_LABOURERS,
					activity.getLabourers());
			values.put(FarmDBContract.Activity.COLUMN_NAME_QUANTITY,
					activity.getQuantity());
			values.put(FarmDBContract.Activity.COLUMN_NAME_TYPE,
					activity.getType());
			values.put(FarmDBContract.Activity.COLUMN_NAME_CATEGORY,
					activity.getCategory());

			newRowId = db.update(FarmDBContract.Activity.TABLE_NAME, values,
					FarmDBContract.Activity.COLUMN_NAME_CROP + " = ?",
					new String[] { activity.getCrop() });
			Toast.makeText(context, "Updated Successfully.", Toast.LENGTH_SHORT)
					.show();
		} catch (Exception e) {
			e.printStackTrace();
			newRowId = -1;
		} finally {
			db.close();
		}
		return newRowId;
	}

	public List<Activity> getActivityDetails(Context context) {
		List<Activity> details = new ArrayList<Activity>();
		SQLiteDatabase db = null;
		try {

			db = new FarmDBHelper(context).getReadableDatabase();

			String[] projection = { FarmDBContract.Activity.COLUMN_NAME_ID,
					FarmDBContract.Activity.COLUMN_NAME_TYPE,
					FarmDBContract.Activity.COLUMN_NAME_CROP,
					FarmDBContract.Activity.COLUMN_NAME_LAND,
					FarmDBContract.Activity.COLUMN_NAME_EXPENSE,
					FarmDBContract.Activity.COLUMN_NAME_INCOME,
					FarmDBContract.Activity.COLUMN_NAME_QUANTITY,
					FarmDBContract.Activity.COLUMN_NAME_DATE,
					FarmDBContract.Activity.COLUMN_NAME_LABOURERS,
					FarmDBContract.Activity.COLUMN_NAME_CATEGORY };

			String sortOrder = FarmDBContract.Activity.COLUMN_NAME_ID + " DESC";

			Cursor c = db.query(FarmDBContract.Activity.TABLE_NAME,

			projection, null, null, null, null, sortOrder);

			if (c != null) {
				boolean flag = c.moveToFirst();
				System.out.println(flag);
				do {
					Activity activity = new Activity();
					activity.setCrop(c.getString(2));
					activity.setDate(c.getString(7));

					activity.setExpense(c.getFloat(4));
					activity.setId(c.getInt(0));
					activity.setIncome(c.getFloat(5));
					activity.setLabourers(c.getInt(8));
					activity.setLand(c.getString(3));
					activity.setQuantity(c.getInt(6));
					activity.setType(c.getString(1));
					activity.setCategory(c.getString(9));
					details.add(activity);

				} while (c.moveToNext());
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}
		return details;
	}

	public long deleteActivity(Context context, Activity activity) {
		long newRowId = -1;
		SQLiteDatabase db = null;
		String res = "false";
		try {

			db = new FarmDBHelper(context).getWritableDatabase();
			Timestamp ts = new Timestamp(Calendar.getInstance()
					.getTimeInMillis());

			db.delete(FarmDBContract.Activity.TABLE_NAME,
					FarmDBContract.Activity.COLUMN_NAME_ID + "= ?",
					new String[] { Integer.toString(activity.getId()) });

			Toast.makeText(context, "Deleted Successfully.", Toast.LENGTH_SHORT)
					.show();

		} catch (Exception e) {
			e.printStackTrace();
			newRowId = -1;
		} finally {
			db.close();
		}
		return newRowId;
	}

	public String checkActivity(Context context, Activity activity) {
		SQLiteDatabase db = null;
		String res = "false";
		try {

			db = new FarmDBHelper(context).getWritableDatabase();
			Cursor c = db.rawQuery("SELECT "
					+ FarmDBContract.Activity.COLUMN_NAME_CROP + " FROM "
					+ FarmDBContract.Activity.TABLE_NAME + " WHERE "
					+ FarmDBContract.Activity.COLUMN_NAME_CROP + "= ?",
					new String[] {
							"SELECT "
									+ FarmDBContract.Activity.COLUMN_NAME_CROP
									+ " FROM "
									+ FarmDBContract.Activity.TABLE_NAME
									+ " WHERE "
									+ FarmDBContract.Activity.COLUMN_NAME_DATE
									+ "= ?", activity.getDate() });
			Timestamp ts = new Timestamp(Calendar.getInstance()
					.getTimeInMillis());
			if (c.getCount() > 0)
				res = "true";
			Log.d("RESULT INSIDE", res);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return res;
	}

}
