package pl.karolt.funds.android.db;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AllFundsDbAdapter {
	
	private static final String DB_TABLE_NAME	= "funds_list";
	private static final String DB_TABLE_CREATE	= "create table funds_list (_id integer primary key autoincrement, "
        + "name text not null, value float not null);";
	
	
	private Context			mCtx;
	private DbHelper 		mDbHelper;
	private SQLiteDatabase	mDb;
	
	
	public AllFundsDbAdapter(Context context) {
		mCtx = context;
		mDbHelper = DbHelper.getInstance(mCtx);
		mDbHelper.addCreateQuery(DB_TABLE_NAME, DB_TABLE_CREATE);
	}
	
	
	/**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public AllFundsDbAdapter open() throws SQLException {
        
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }
	
	
}
