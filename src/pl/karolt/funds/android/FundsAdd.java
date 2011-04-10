package pl.karolt.funds.android;

import pl.karolt.funds.android.db.FundDbAdapter;
import pl.karolt.funds.android.funds.Fund;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;


public class FundsAdd extends Activity {
	private static final String TAG = "FundsAdd";
	
	private FundDbAdapter mAllFundsDb;
	private Spinner mAllFundsSpinner;
	
	private String mSelectedFundName;
	private Double mGivenFundOperationValue;  
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fund);
        
        mAllFundsDb = new FundDbAdapter(this);
        mAllFundsDb.open();
        
        
        Button addButton = (Button)findViewById(R.id.add_button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText valueEditText = (EditText)findViewById(R.id.add_edittxt_value);
				Log.v(TAG, "Adding Operation for name: " + mSelectedFundName + " value: " + valueEditText.getText().toString());
				
			}
		});
        
        
        
        Spinner mAllFundsSpinner = (Spinner)findViewById(R.id.add_spinner_available_funds);
        mAllFundsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					android.view.View v, int position, long row) {
				String fundName = parent.getItemAtPosition(position).toString();
				Log.v(TAG, "Fund Selected at postion "+position+" " + fundName);
				mSelectedFundName = fundName;
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
