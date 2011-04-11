package pl.karolt.funds.android;

import pl.karolt.funds.android.db.FundDbAdapter;
import pl.karolt.funds.android.db.FundOperationHistoryDbAdapter;
import pl.karolt.funds.android.db.UserFundDbAdapter;
import pl.karolt.funds.android.funds.Fund;
import pl.karolt.funds.android.funds.FundOperation;
import pl.karolt.funds.android.funds.UserFund;
import pl.karolt.funds.android.funds.exceptions.NotImplementedException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;


public class FundsAdd extends Activity {
	private static final String TAG = "FundsAdd";
	
	private FundDbAdapter mAllFundsDb;
	private FundOperationHistoryDbAdapter mFundOperationHistoryDb;
	private UserFundDbAdapter mUserFundDb;
	
	private Spinner mAllFundsSpinner;
	
	private String mSelectedFundName;
	private long mSelectedFundId;
	//private Double mGivenFundOperationValue;  
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fund);
        
        mAllFundsDb = new FundDbAdapter(this);
        mAllFundsDb.open();
        
        mFundOperationHistoryDb = new FundOperationHistoryDbAdapter(this);
        mFundOperationHistoryDb.open();
        
        mUserFundDb = new UserFundDbAdapter(this);
        mUserFundDb.open();
        
        /*
    	Fund f1 = new Fund("UniKorona Obligacje", 259.59, Fund.TYPE_BONDS);
    	mAllFundsDb.addNewFund(f1);
    	
    	f1 = new Fund("Amplico SFIO Subfundusz Obligacji Œwiatowych", 8.45, Fund.TYPE_BONDS);
    	mAllFundsDb.addNewFund(f1);
    	        	
    	f1 = new Fund("Idea Stabilnego Wzrostu", 214.69, Fund.TYPE_HYBRID);
    	mAllFundsDb.addNewFund(f1);
    	
    	f1 = new Fund("BPH Subfundusz Stabilnego Wzrostu", 16.30, Fund.TYPE_HYBRID);
    	mAllFundsDb.addNewFund(f1);
    	
    	f1 = new Fund("UniStabilny Wzrost", 146.48, Fund.TYPE_HYBRID);
    	mAllFundsDb.addNewFund(f1);
    	
    	f1 = new Fund("Idea Akcji", 231.67, Fund.TYPE_STOCK);
    	mAllFundsDb.addNewFund(f1);
    	
    	f1 = new Fund("BPH Subf Globalny ¯ywnoœci i Surowców", 170.37, Fund.TYPE_HYBRID);
    	mAllFundsDb.addNewFund(f1);
    */
        
        
        Button addButton = (Button)findViewById(R.id.add_button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText valueEditText = (EditText)findViewById(R.id.add_edittxt_value);
				
				DatePicker datePicker = (DatePicker)findViewById(R.id.add_datepicker);
		        String date = datePicker.getDayOfMonth()+"-"+datePicker.getMonth()+"-"+datePicker.getYear();
		        String valueStr = valueEditText.getText().toString();
				if (_saveData(mSelectedFundId, valueStr, date)) {
					setResult(RESULT_OK);
	                finish();
				} else {
					_showErrorDialog();
				}
		        
		        
				
			}
		});
        
        
        Spinner mAllFundsSpinner = (Spinner)findViewById(R.id.add_spinner_available_funds);
        mAllFundsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					android.view.View v, int position, long row) {
				
				Cursor c = (Cursor)parent.getItemAtPosition(position);
				String fundName = c.getString(c.getColumnIndex("name"));
				long fundId = c.getLong(c.getColumnIndex("_id"));
				Log.v(TAG, "Fund Selected at postion "+position+" " + "id:"+fundId+" name:"+fundName);
				mSelectedFundName = fundName;
				mSelectedFundId = fundId;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        _populateFields();
        	
        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        _populateFields();
        
    }
    
    
    
    
    private void _populateFields()
	{
		_populateAllFundsSpinner();
        
	}
    
    /**
	 * wypelnia dane w liscie z nazwami wszystkich dosptenych funduszy
	 */
	public void _populateAllFundsSpinner()
	{
		
        mAllFundsSpinner = (Spinner) findViewById(R.id.add_spinner_available_funds);
        Cursor allFundsCursor = mAllFundsDb.getAllAvailableCursor();
        
        SimpleCursorAdapter allFundsAdapter = new SimpleCursorAdapter(this,
            android.R.layout.simple_spinner_item, 
            allFundsCursor,
            new String[] {"name"}, 
            new int[] {android.R.id.text1});
                                                 
        allFundsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAllFundsSpinner.setAdapter(allFundsAdapter);
	}
	
	/**
	 * na podstawie danych z formularza dodaje operacje do historii + modyfikuje dane o funduszach usera
	 * 
	 * @param mSelectedFundId
	 * @param valueStr
	 * @param date
	 * @return
	 */
	private boolean _saveData(long mSelectedFundId, String valueStr, String date)
	{
		Fund fund = mAllFundsDb.getById(mSelectedFundId);
		double value = Double.parseDouble(valueStr);
		
		double units = value / fund.getValueForDate(date);
		FundOperation operation = new FundOperation(fund, value, units, FundOperation.TYPE_BUY, date);
		mFundOperationHistoryDb.newOperation(operation);
		
		UserFund fundAllreadyInUserFunds = mUserFundDb.getByFund(fund);
		if (fundAllreadyInUserFunds == null) 
		{
			UserFund userFund = new UserFund(fund, value, units, value, 0.0);
			mUserFundDb.newUserFund(userFund);
			
			return true;
		} else {
			try {
				fundAllreadyInUserFunds.performOperation(operation);
				Log.d(TAG, "Updating userFund " +fundAllreadyInUserFunds);
				int changed = mUserFundDb.update(fundAllreadyInUserFunds);
				if (changed <= 0) {
					Log.w(TAG, "zero rows affected while updating userFund");
				}
				
				return true;
			} catch (NotImplementedException e) {
				Log.e(TAG, e.getMessage());
				return false;
			}
			
		}
		
	}
	
	private void _showErrorDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Wyst¹pi³ nieoczekiwany b³¹d. Wys³ac zg³oszenie do twórców programu ?")
		       .setCancelable(false)
		       .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   //TODO: wysylanei zgloszenia
		        	   Log.w(TAG, "Sending failure report not yet implemented");
		        	   setResult(RESULT_CANCELED);
		               finish();
		           }
		       })
		       .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   setResult(RESULT_CANCELED);
		               finish();
		           }
		       });
		AlertDialog alert = builder.create();
	}
	
	/*
	Fund f1 = new Fund("UniKorona Obligacje", 259.59, Fund.TYPE_BONDS);
	mAllFundsDb.addNewFund(f1);
	
	f1 = new Fund("Amplico SFIO Subfundusz Obligacji Œwiatowych", 8.45, Fund.TYPE_BONDS);
	mAllFundsDb.addNewFund(f1);
	        	
	f1 = new Fund("Idea Stabilnego Wzrostu", 214.69, Fund.TYPE_HYBRID);
	mAllFundsDb.addNewFund(f1);
	
	f1 = new Fund("BPH Subfundusz Stabilnego Wzrostu", 16.30, Fund.TYPE_HYBRID);
	mAllFundsDb.addNewFund(f1);
	
	f1 = new Fund("UniStabilny Wzrost", 146.48, Fund.TYPE_HYBRID);
	mAllFundsDb.addNewFund(f1);
	
	f1 = new Fund("Idea Akcji", 231.67, Fund.TYPE_STOCK);
	mAllFundsDb.addNewFund(f1);
	
	f1 = new Fund("BPH Subf Globalny ¯ywnoœci i Surowców", 170.37, Fund.TYPE_HYBRID);
	mAllFundsDb.addNewFund(f1);
*/
}
