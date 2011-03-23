package pl.karolt.funds.android;

//import com.android.demo.notepad2.NotesDbAdapter;

import pl.karolt.funds.android.db.AllFundsDbAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FundsMain extends Activity {
	
	private static final String TAG = "FundsMain";
	
	private static final int MENU_ADD_FUND = Menu.FIRST;
	
	private static final int ACTIVITY_ADD = 1;
	
	
	
	private AllFundsDbAdapter mAllFundsDb;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button addButton = (Button) findViewById(R.id.button_add_fund);
        
        addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

        mAllFundsDb = new AllFundsDbAdapter(this);
    }

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onMenuItemSelected");
		if (item.getItemId() == MENU_ADD_FUND) {
			Log.v(TAG, "add fund selected");
			Intent intent = new Intent(this, FundsAdd.class);
			startActivityForResult(intent, ACTIVITY_ADD);
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
    
    
    
}