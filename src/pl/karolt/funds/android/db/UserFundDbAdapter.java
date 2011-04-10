package pl.karolt.funds.android.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserFundDbAdapter {
	
	private static final String	TAG	= "FundsUserFundDbAdapter";
	
	private static final String DB_TABLE_NAME	= "user_fund";
	private static final String DB_TABLE_CREATE	= "create table user_fund " +
								"(_id integer primary key autoincrement, "
        						+ "fund_id integer not_null, "
								+ "money_paid double not null, "
								+ "current_value double not null"
								+ "simple_return double"
								+ "real_return double"
								+ ");";
	
	
	private Context			mCtx;
	private DbHelper 		mDbHelper;
	private SQLiteDatabase	mDb;
	
	
	public UserFundDbAdapter(Context context) {
		mCtx = context;
		mDbHelper = DbHelper.getInstance(mCtx);
		mDbHelper.addCreateQuery(DB_TABLE_NAME, DB_TABLE_CREATE);
	}
	
	
	public UserFundDbAdapter open() throws SQLException {
        
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    
    }
    
    /**
     * wstawia nowy fundusz do bazy wszystkich dostepnych funduszy
     * @param name
     * @param value obecna wartosc
     * @return long identyfikator w bazie
     */
    public long newFund(String name, Double value) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", name);
        initialValues.put("value", value);
        Log.v(TAG, "adding new Fund " + name);
        return mDb.insert(DB_TABLE_NAME, null, initialValues);
    }
    
    public Cursor getAllAvailable()
    {
    	return mDb.query(DB_TABLE_NAME, new String[] {"_id","name", "value"}, null, null, null, null, null);
    }
	
	
}
