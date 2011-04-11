package pl.karolt.funds.android.funds;

import pl.karolt.funds.android.funds.exceptions.NotImplementedException;
import android.database.Cursor;

public class UserFund {
	
	/**
	 * identyfiktor wpisu w lokalnej bazie
	 */
	private long id;
	
	
	/**
	 * fundusz, ktory zakupil user
	 * dobierany na podstawie fundId
	 */
	private Fund fund;
	
	/**
	 * id funduszu, ktorego dotyczy operacja
	 */
	private long fundId;
	
	/**
	 * suma wplaconej kasy
	 */
	private double moneyPaid;
	
	/**
	 * suma zakupionych jednostek
	 */
	private double units;
	
	/**
	 * obecna wartosc
	 */
	private double currentValue;

	/**
	 * prosty zwrot z inwestycji liczony jako (currentValue/moneyPaid - 1) * 100
	 */
	private double simpleReturn;
	
	
	
	
	public UserFund(long fundId, double moneyPaid, double units,
			double currentValue, double simpleReturn) {
		super();
		this.fundId = fundId;
		this.moneyPaid = moneyPaid;
		this.units = units;
		this.currentValue = currentValue;
		this.simpleReturn = simpleReturn;
	}
	
	public UserFund(Fund fund, double moneyPaid, double units,
			double currentValue, double simpleReturn) {
		super();
		this.fund = fund;
		this.fundId = fund.getId();
		this.moneyPaid = moneyPaid;
		this.units = units;
		this.currentValue = currentValue;
		this.simpleReturn = simpleReturn;
	}
	
	public UserFund(Cursor cursor, Fund f)
	{
		id		= cursor.getLong(cursor.getColumnIndex("_id"));
		fundId	= cursor.getLong(cursor.getColumnIndex("fundId"));
		fund	= f;
		moneyPaid		= cursor.getDouble(cursor.getColumnIndex("moneyPaid"));
		units			= cursor.getDouble(cursor.getColumnIndex("units"));
		simpleReturn	= cursor.getDouble(cursor.getColumnIndex("simpleReturn"));
		currentValue	= cursor.getDouble(cursor.getColumnIndex("currentValue"));
	}
	
	public String toString()
	{
		String str = "fundId="+fundId+", moneyPaid="+moneyPaid+", units="+units+", currentValue="+currentValue+" simpleReturn="+simpleReturn; 
		if (id != 0) {
			str = "id="+id+", " + str; 
		} else {
			str = "no id(not saved)" + str;
		}
			
		return "UserFund : "+str;
	}

	/**
	 * przeprowadza zadana operacje na fudnuszu usera
	 * 
	 * @param FundOperation operation
	 * @throws NotImplementedException obecnie uzywany dla niezaimplementowanej operacji odkupienia jednostek 
	 */
	public void performOperation(FundOperation operation) throws NotImplementedException
	{
		if (operation.getType() == FundOperation.TYPE_BUY)
		{
			moneyPaid	+= operation.getValue();
			units		+= operation.getUnits();
			currentValue+= operation.getValue();
			simpleReturn = currentValue/moneyPaid - 1;
		} else {
			throw new NotImplementedException("only Buy Operation can be performed");
		}
	}


	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public Fund getFund() {
		return fund;
	}




	public void setFund(Fund fund) {
		this.fund = fund;
	}




	public double getMoneyPaid() {
		return moneyPaid;
	}




	public void setMoneyPaid(double moneyPaid) {
		this.moneyPaid = moneyPaid;
	}




	public double getUnits() {
		return units;
	}




	public void setUnits(double units) {
		this.units = units;
	}




	public double getCurrentValue() {
		return currentValue;
	}




	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}




	public double getSimpleReturn() {
		return simpleReturn;
	}




	public void setSimpleReturn(double simpleReturn) {
		this.simpleReturn = simpleReturn;
	}




	public long getFundId() {
		return fundId;
	}
	
	
}

