package pl.karolt.funds.android.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	/**
	 * tag used for logCat logging 
	 * 
	 */
	private static final String	TAG	= "FundsDbHelper";
	
	private static DbHelper instance;
	
	private static final String	DB_NAME		= "funds";
	private static final int	DB_VERSION	= 2;
	
	/**
	 * hashmap tableName -> create query
	 */
	private HashMap<String, String> tableCreateQueries;
	
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		
		tableCreateQueries = new HashMap<String, String>(); 
		Log.v(TAG, "DbHelper constructor");
	}
	
	/**
	 * returns an instance of db helper
	 * 
	 * @param context
	 * @return DbHelper
	 */
	public static DbHelper getInstance(Context context)
	{
		Log.v(TAG, "getting DbHelper instance ");
		if (instance == null) {
			instance = new DbHelper(context);
		}
		
		return instance;
			
	}
	
	
	/**
	 * adds query that creates a table in database
	 * queries are run in onCreate method
	 * 
	 * @param table 
	 * @param query 
	 */
	public void addCreateQuery(String table, String query)
	{
		Log.v(TAG, "adding creation query for " + table);
		tableCreateQueries.put(table, query);
	}
	
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v(TAG, "onCreate");
		if (tableCreateQueries.size() == 0) {
			Log.w(TAG, "no queries to run");
			return;
		}
		
		Iterator<String> tableNames = tableCreateQueries.keySet().iterator();
		String tableName, query;
		
		while (tableNames.hasNext())
		{
			tableName	= tableNames.next();
			query		= tableCreateQueries.get(tableName);
			
			Log.d(TAG, "creating table " + tableName);
			db.execSQL(query);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//TODO: implement me
		Log.v(TAG, "onUpgrade DOING NOTHING, wanted to Upgrade from version:" + oldVersion + " to version:" + newVersion);

	}

}
