package pl.karolt.funds.android.funds;

public class UserFund {
	
	/**
	 * identyfiktor wpisu w lokalnej bazie
	 */
	private long id;
	
	
	/**
	 * fundusz, ktory zakupil user
	 * dopierany na podstawie fundId
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

