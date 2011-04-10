package pl.karolt.funds.android.db;


import pl.karolt.funds.android.funds.Fund;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FundDbAdapter {
	
	private static final String	TAG	= "FundsAllFundsDbAdapter";
	
	private static final String DB_TABLE_NAME	= "fund";
	private static final String DB_TABLE_CREATE	= "create table fund (_id integer primary key autoincrement, "
        + "name text not null, currentValue double not null, type integer not null);";
	
	
	private Context			mCtx;
	private DbHelper 		mDbHelper;
	private SQLiteDatabase	mDb;
	
	
	public FundDbAdapter(Context context) {
		mCtx = context;
		mDbHelper = DbHelper.getInstance(mCtx);
		mDbHelper.addCreateQuery(DB_TABLE_NAME, DB_TABLE_CREATE);
	}
	
	
	public FundDbAdapter open() throws SQLException {
        
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    
    }
    
    /**
     * dodaje nowy fundusz do bazy
     * @param Fund fund
     * @return long identyfikator nowo dodanego funduszu 
     */
    public long addNewFund(Fund fund) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", fund.getName());
        initialValues.put("currentValue", fund.getCurrentValue());
        initialValues.put("type", fund.getType());
        Log.v(TAG, "adding new fund: " + fund.getName());
        
        return mDb.insert(DB_TABLE_NAME, null, initialValues);
    }
    
    /*
    public Fund getById()
    {
    	return mDb.query(DB_TABLE_NAME, new String[] {"_id","name", "value"}, null, null, null, null, null);
    	return new Fund();
    }
    */
    
    public Cursor getAllAvailableCursor()
    {
    	return mDb.query(DB_TABLE_NAME, new String[] {"_id","name", "currentValue"}, null, null, null, null, null);
    }
    
}
