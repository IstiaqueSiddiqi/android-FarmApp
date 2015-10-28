package smartfarms.utils;

import android.provider.BaseColumns;

public final class FarmDBContract {

	public FarmDBContract() {
	}

	public static abstract class Crop implements BaseColumns {
		public static final String TABLE_NAME = "crop";
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_IMAGE = "image";
		public static final String COLUMN_NAME_LANDNAME = "landname";
		public static final String COLUMN_NAME_CROPCATEGORY = "cropcategory";
	}

	public static abstract class Activity implements BaseColumns {
		public static final String TABLE_NAME = "activity";
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_TYPE = "type";
		public static final String COLUMN_NAME_CROP = "crop";
		public static final String COLUMN_NAME_LAND = "land";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_EXPENSE = "expense";
		public static final String COLUMN_NAME_INCOME = "income";
		public static final String COLUMN_NAME_QUANTITY = "quantity";
		public static final String COLUMN_NAME_LABOURERS = "labourers";
		public static final String COLUMN_NAME_CATEGORY = "category";

	}

	public static abstract class Land implements BaseColumns {
		public static final String TABLE_NAME = "land";
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_LOCATION = "location";
		public static final String COLUMN_NAME_AREA = "area";
		public static final String COLUMN_NAME_LAT = "lat";
		public static final String COLUMN_NAME_LNG = "lng";
		public static final String COLUMN_NAME_STATUS = "status";
	}

}
