package pl.karolt.funds.android;

//import com.android.demo.notepad2.NotesDbAdapter;

import pl.karolt.funds.android.db.FundDbAdapter;
import pl.karolt.funds.android.db.DbHelper;
import pl.karolt.funds.android.db.FundOperationHistoryDbAdapter;
import pl.karolt.funds.android.funds.Fund;
import pl.karolt.funds.android.funds.FundOperation;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class FundsMain extends Activity {
	
	private static final String TAG = "FundsMain";
	
	private static final int MENU_ADD_FUND = Menu.FIRST;
	
	private static final int ACTIVITY_ADD = 1;
	
	
	
	private FundDbAdapter mAllFundsDb;
	private FundOperationHistoryDbAdapter mUserFundHistoryDb;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button addButton = (Button) findViewById(R.id.main_button_add_fund);
        
        addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_startAddFundActivity();
				
			}
		});
       
        mAllFundsDb = new FundDbAdapter(this);
        mAllFundsDb.open();
        
        mUserFundHistoryDb = new FundOperationHistoryDbAdapter(this);
        mUserFundHistoryDb.open();
        
        //DbHelper helper = DbHelper.getInstance(this);
        //helper.onUpgrade(helper.getWritableDatabase(),2,3);
        
        _writeAllFundsToLog();
        _populateFields();
        _writeAllPurchasesToLog();
    }

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onMenuItemSelected");
		if (item.getItemId() == MENU_ADD_FUND) {
			Log.v(TAG, "add fund selected");
			_startAddFundActivity();
			
			
			//mUserFundHistoryDb.newPurchase(4, 354.2, 12.3, "2011-04-12 11:12:45");
		}
		
		
		return super.onMenuItemSelected(featureId, item);
	}
	
	

	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		boolean ret = super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ADD_FUND, 0, R.string.menu_add_fund);
		Log.v(TAG, "onMenuOpened");
		return ret;
    }
    
	@Override
    protected void onResume() {
        super.onResume();
        _populateFields();
        
    }
	
	private void _populateFields()
	{
        
	}
	
	
	private void _startAddFundActivity()
	{
		Intent intent = new Intent(this, FundsAdd.class);
		startActivityForResult(intent, ACTIVITY_ADD);
	}
	
	/**
	 * wypisuje do logCata informacje o wszystkich dostepnych funduszach
	 * 
	 * 
	 */
	public void _writeAllFundsToLog()
	{
		Cursor allFunds = mAllFundsDb.getAllAvailableCursor();
        startManagingCursor(allFunds);
        
		allFunds.moveToFirst();
        String name;
        Double value;
        Fund fund;
        
        try 
        {
        	while (!allFunds.isAfterLast())
            {
            	fund = new Fund(	allFunds.getLong(allFunds.getColumnIndex("_id")),
    		        				allFunds.getString(allFunds.getColumnIndex("name")),
    		        				allFunds.getDouble(allFunds.getColumnIndex("value")),
    		        				allFunds.getInt(allFunds.getColumnIndex("type"))
    		        				);
            	Log.d(TAG, "found " + fund);
            	allFunds.moveToNext();
            }
        } catch (Exception e) {
        	Log.e(TAG, e.getMessage());
        }
        
	}
	
	/**
	 * wypisuje do logCata informacje o wszystkich zakupach funduszy
	 * 
	 */
	public void _writeAllPurchasesToLog()
	{
		Cursor allPurchases = mUserFundHistoryDb.getAllAvailable();
        startManagingCursor(allPurchases);
        
        allPurchases.moveToFirst();
        FundOperation operation; 
        while (!allPurchases.isAfterLast())
        {
        	operation = new FundOperation(
        					allPurchases.getLong(allPurchases.getColumnIndex("fundId")),
        					allPurchases.getDouble(allPurchases.getColumnIndex("value")),
        					allPurchases.getDouble(allPurchases.getColumnIndex("units")),
        					allPurchases.getInt(allPurchases.getColumnIndex("type")),
        					allPurchases.getString(allPurchases.getColumnIndex("performedAt")));
        					

        	Log.d(TAG, "Found " + operation);
        	allPurchases.moveToNext();
        }
	}
	
	
    
}