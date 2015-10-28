package smartfarms.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import smartfarms.utils.FarmDBContract.Crop;
import smartfarms.utils.FarmDBContract.Land;
import smartfarms.utils.FarmDBContract.Activity;

public class FarmDBHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "SmartFarm.db";

	public FarmDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";

	private static final String SQL_CREATE_CROP = "CREATE TABLE "
			+ Crop.TABLE_NAME + " (" + Crop.COLUMN_NAME_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT " + COMMA_SEP
			+ Crop.COLUMN_NAME_NAME + TEXT_TYPE + " UNIQUE " + COMMA_SEP
			+ Crop.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP
			+ Crop.COLUMN_NAME_LANDNAME + TEXT_TYPE + COMMA_SEP
			+ Crop.COLUMN_NAME_CROPCATEGORY + TEXT_TYPE + " )";

	private static final String SQL_DELETE_CROP = "DROP TABLE IF EXISTS "
			+ Crop.TABLE_NAME;

	private static final String SQL_CREATE_LAND = "CREATE TABLE "
			+ Land.TABLE_NAME + " (" + Land.COLUMN_NAME_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT " + COMMA_SEP
			+ Land.COLUMN_NAME_NAME + TEXT_TYPE + " UNIQUE " + COMMA_SEP
			+ Land.COLUMN_NAME_AREA + " INTEGER " + COMMA_SEP
			+ Land.COLUMN_NAME_LAT + " DOUBLE " + COMMA_SEP
			+ Land.COLUMN_NAME_LNG + " DOUBLE " + COMMA_SEP
			+ Land.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP
			+ Land.COLUMN_NAME_LOCATION + TEXT_TYPE +

			" )";

	private static final String SQL_DELETE_LAND = "DROP TABLE IF EXISTS "
			+ Land.TABLE_NAME;

	private static final String SQL_CREATE_ACTIVITY = "CREATE TABLE "
			+ Activity.TABLE_NAME + " (" + Activity.COLUMN_NAME_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT  " + COMMA_SEP
			+ Activity.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP
			+ Activity.COLUMN_NAME_CROP + TEXT_TYPE + COMMA_SEP
			+ Activity.COLUMN_NAME_LAND + TEXT_TYPE + COMMA_SEP
			+ Activity.COLUMN_NAME_EXPENSE + " FLOAT " + COMMA_SEP
			+ Activity.COLUMN_NAME_INCOME + " FLOAT " + COMMA_SEP
			+ Activity.COLUMN_NAME_QUANTITY + " INTEGER " + COMMA_SEP
			+ Activity.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP
			+ Activity.COLUMN_NAME_LABOURERS + " INTEGER " + COMMA_SEP
			+ Activity.COLUMN_NAME_CATEGORY + TEXT_TYPE + " )";

	private static final String SQL_DELETE_ACTIVITY = "DROP TABLE IF EXISTS "
			+ Activity.TABLE_NAME;

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_CROP);
		db.execSQL(SQL_CREATE_ACTIVITY);
		db.execSQL(SQL_CREATE_LAND);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_CROP);
		db.execSQL(SQL_DELETE_ACTIVITY);
		db.execSQL(SQL_DELETE_LAND);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}
