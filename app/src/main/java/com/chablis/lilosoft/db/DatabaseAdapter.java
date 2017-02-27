package com.chablis.lilosoft.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chablis.lilosoft.base.Global;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class DatabaseAdapter {

	public Context context;
	public DatabaseHelper mDatabaseHelper;
	public SQLiteDatabase mSQLiteDatabase;
	public String DB_NAME;
	public int DB_VERSION;
	public String tableCreateSql;
	public String DB_TABLE;
	public String DB_TABLE_PK;

	private class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(tableCreateSql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			onCreate(db);
		}
	}

	public DatabaseAdapter(Context context, String _DB_NAME, int _DB_VERSION,
						   String _tableCreateSql, String _DB_TABLE, String _DB_TABLE_PK) {
		this.context = context;
		DB_NAME = Global.getSDCardPath(context, Global.AppFileRootPath)
				+ File.separator + _DB_NAME;
		DB_VERSION = _DB_VERSION;
		tableCreateSql = _tableCreateSql;
		DB_TABLE = _DB_TABLE;
		DB_TABLE_PK = _DB_TABLE_PK;
	}

	// 开启
	public void open() {
		mDatabaseHelper = new DatabaseHelper(context);
		mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
		if (!TableIsExist(mSQLiteDatabase, DB_TABLE)) {
			mDatabaseHelper.onCreate(mSQLiteDatabase);
		}
	}

	// 关闭
	public void close() {
		if (mSQLiteDatabase.isOpen()) {
			mSQLiteDatabase.close();
		}
		mDatabaseHelper.close();
	}

	public boolean TableIsExist(SQLiteDatabase db, String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		Cursor cursor = null;
		try {
			String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"
					+ tableName.trim() + "' ";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	// 增
	public long insertData(String cols, String[] valueArray) {
		String[] colArray = cols.split(",");
		ContentValues values = new ContentValues();
		for (int i = 0; i < colArray.length; i++) {
			values.put(colArray[i], valueArray[i].trim().length() == 0 ? ""
					: valueArray[i]);
		}
		long id = mSQLiteDatabase.insert(DB_TABLE, DB_TABLE_PK, values);
		return id;
	}

	public <T> void insertAll(ArrayList<T> list) {
		if (mSQLiteDatabase.isOpen()) {
			String clear_tb = "delete from " + DB_TABLE;

			mSQLiteDatabase.beginTransaction();

			try {
				mSQLiteDatabase.execSQL(clear_tb);

				for (T t : list) {
					ContentValues values = new ContentValues();

					Class<? extends Object> cls = t.getClass();
					Field[] fields = cls.getDeclaredFields();
					for (int i = 0; i < fields.length; i++) {
						Field f = fields[i];
						f.setAccessible(true);
						values.put(f.getName(), f.get(t).toString());
					}
					mSQLiteDatabase.insert(DB_TABLE, DB_TABLE_PK, values);
				}

				mSQLiteDatabase.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				mSQLiteDatabase.endTransaction();
			}
		}
	}

	public <T> void insert(T t) {
		if (mSQLiteDatabase.isOpen()) {
			String clear_tb = "delete from " + DB_TABLE;

			mSQLiteDatabase.beginTransaction();

			try {
				mSQLiteDatabase.execSQL(clear_tb);

				ContentValues values = new ContentValues();

				Class<? extends Object> cls = t.getClass();
				Field[] fields = cls.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					Field f = fields[i];
					f.setAccessible(true);
//					Log.d("DatabaseAdapter", f.getName());
//					if(f.get(t)==null) continue;
//					if(f.getName().equals("serialVersionUID")) continue;
					values.put(f.getName(), f.get(t).toString());
				}
				mSQLiteDatabase.insert(DB_TABLE, DB_TABLE_PK, values);

				mSQLiteDatabase.setTransactionSuccessful();
			} catch (Exception e) {
				Log.d("DatabaseAdapter", "e:" + e);
				// TODO: handle exception
			} finally {
				mSQLiteDatabase.endTransaction();
			}
		}
	}

	// 删
	public boolean deleteData(Context context, long id) {
		boolean delete = mSQLiteDatabase.delete(DB_TABLE, DB_TABLE_PK + "="
				+ id, null) > 0;
		return delete;
	}

	public boolean deleteData(Context context, String ids) {
		boolean delete = mSQLiteDatabase.delete(DB_TABLE, DB_TABLE_PK + "in("
				+ ids + ")", null) > 0;
		return delete;
	}

	public boolean deleteData(Context context, String where, String[] whereArgs) {
		boolean delete = mSQLiteDatabase.delete(DB_TABLE, where, null) > 0;
		return delete;
	}

	public long deleteAllData() {
		return mSQLiteDatabase.delete(DB_TABLE, null, null);
	}

	// 改
	public boolean updateData(long id, String cols, String[] valueArray) {
		String[] colArray = cols.split(",");
		ContentValues values = new ContentValues();
		for (int i = 0; i < colArray.length; i++) {
			values.put(colArray[i], valueArray[i]);
		}
		boolean update = mSQLiteDatabase.update(DB_TABLE, values, DB_TABLE_PK
				+ "=" + id, null) > 0;
		return update;
	}

	public boolean updateData(String id, String cols, String[] valueArray) {
		String[] colArray = cols.split(",");
		ContentValues values = new ContentValues();
		for (int i = 0; i < colArray.length; i++) {
			values.put(colArray[i], valueArray[i]);
		}
		boolean update = mSQLiteDatabase.update(DB_TABLE, values, DB_TABLE_PK
				+ "='" + id + "'", null) > 0;
		return update;
	}

	// 查
	public Cursor fetchData(String selection, String cols) {
		String[] colArray = cols.split(",");
		Cursor mCursor = mSQLiteDatabase.query(DB_TABLE, colArray, selection,
				null, null, null, null);
		if (mCursor != null)
			mCursor.moveToFirst();
		return mCursor;
	}

}
