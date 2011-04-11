package pl.karolt.funds.android.db;


import pl.karolt.funds.android.funds.Fund;
import pl.karolt.funds.android.funds.UserFund;
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
        						+ "fundId integer not_null, "
								+ "moneyPaid double not null, "
								+ "units double not null, "
								+ "currentValue double not null, "
								+ "simpleReturn double"
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
     * wstawia nowy rekord z funduszem usera
     * 
     * @param UserFund userFund
     * @return long
     */
    public long newUserFund(UserFund userFund) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("fundId", userFund.getFundId());
        initialValues.put("moneyPaid", userFund.getMoneyPaid());
        initialValues.put("currentValue", userFund.getCurrentValue());
        initialValues.put("units", userFund.getUnits());
        initialValues.put("simpleReturn", userFund.getSimpleReturn());
        
        Log.v(TAG, "adding new User Fund " + userFund);
        return mDb.insert(DB_TABLE_NAME, null, initialValues);
    }
    
    public UserFund getByFund(Fund fund)
    {
    	Cursor c = mDb.query(DB_TABLE_NAME, new String[] {"_id","fundId", "moneyPaid", "currentValue", "units", "simpleReturn"}, "fundId = " + fund.getId(), null, null, null, null);

    	if (c != null) {
            c.moveToFirst();
        }
    	
    	return new UserFund(c, fund);
    	
    }
    
    public Cursor getAll()
    {
    	return mDb.query(DB_TABLE_NAME, new String[] {"_id","fundId", "moneyPaid", "currentValue", "units", "simpleReturn"}, null, null, null, null, null);
    }
	
	
}
