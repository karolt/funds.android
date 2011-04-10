package pl.karolt.funds.android.db;


import pl.karolt.funds.android.funds.FundOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FundOperationHistoryDbAdapter {
	
	private static final String	TAG	= "FundsUserFundDbAdapter";
	
	private static final String DB_TABLE_NAME	= "fund_operation_history";
	private static final String DB_TABLE_CREATE	= "create table fund_operation_history " +
								"(_id integer primary key autoincrement, "
								+ "fundId integer not_null, "
								+ "value double not null, "
								+ "units double not null, "
								+ "type integer not_null, "
								+ "performedAt text not null " 
								+ ");";
	
	
	private Context			mCtx;
	private DbHelper 		mDbHelper;
	private SQLiteDatabase	mDb;
	
	
	public FundOperationHistoryDbAdapter(Context context) {
		mCtx = context;
		mDbHelper = DbHelper.getInstance(mCtx);
		mDbHelper.addCreateQuery(DB_TABLE_NAME, DB_TABLE_CREATE);
	}
	
	
	public FundOperationHistoryDbAdapter open() throws SQLException {
        
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    
    }
    
    /**
     * wstawia informacje o zakupie jednostek
     * 
     * @param FundOperation 
     * @return identyfikator wpisu w historii
     */
    public long newOperation(FundOperation operation) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("fundId", operation.getFundId());
        initialValues.put("value", operation.getValue());
        initialValues.put("units", operation.getUnits());
        initialValues.put("type", operation.getType());
        initialValues.put("performedAt", operation.getPerformedAt());
        
        Log.v(TAG, "adding operation " + operation);
        return mDb.insert(DB_TABLE_NAME, null, initialValues);
    }
    
    public Cursor getAllAvailable()
    {
    	return mDb.query(DB_TABLE_NAME, new String[] {"_id", "fundId", "value", "units", "type", "performedAt"}, null, null, null, null, null);
    }
	
}
