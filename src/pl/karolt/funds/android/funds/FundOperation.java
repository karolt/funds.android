package pl.karolt.funds.android.funds;

import android.database.Cursor;

public class FundOperation {
	
	/**
	 * identyfiktor operacji w lokalnej bazie
	 */
	private long id;
	
	
	/**
	 * fundusz, ktorego dotyczy operacja
	 * dopierany na podstawie fundId
	 */
	private Fund fund;
	
	/**
	 * id funduszu, ktorego dotyczy operacja
	 */
	private long fundId;
	
	/**
	 * wartosc operacji, np kwota za ktora zakupiono jednostki
	 */
	private double value;
	
	/**
	 * liczba jednostek w operacji
	 */
	private double units;
	
	/**
	 * data wykonania operacji
	 */
	private String performedAt;
	
	/**
	 * typ operacji: nabycie, odkupienie
	 * wartosci okreslone przez stale TYPE_BUY, TYPE_SELL
	 */
	private int type;
	
	/**
	 * stala reprezentujaca operacje nabycia jednostek
	 */
	public static final int TYPE_BUY	= 1;
	
	/**
	 * stala reprezentujaca operacje odkupienia/sprzedazy jednostek
	 */
	public static final int TYPE_SELL	= 2;

	public FundOperation(Fund fund, double value, double units,
			 int type, String performedAt) {
		super();
		this.fund = fund;
		this.fundId = fund.getId();
		this.value = value;
		this.units = units;
		this.performedAt = performedAt;
		this.type = type;
	}

	public FundOperation(long fundId, double value, double units,
			int type, String performedAt) {
		super();
		this.fundId = fundId;
		//TODO: dopisac Fund wg fund
		this.value = value;
		this.units = units;
		this.performedAt = performedAt;
		this.type = type;
	}
	
	public FundOperation(Cursor c)
	{
		id		= c.getLong(c.getColumnIndex("_id"));
		fundId	= c.getLong(c.getColumnIndex("fundId"));
		value	= c.getDouble(c.getColumnIndex("value"));
		units	= c.getDouble(c.getColumnIndex("units"));
		type	= c.getInt(c.getColumnIndex("type"));
		performedAt = c.getString(c.getColumnIndex("performedAt"));
	}
	
	public String toString()
	{
		String str = "fundId="+fundId+", value="+value+", units="+units+", performedAt="+performedAt; 
		if (id != 0) {
			str = "id="+id+", " + str; 
		} else {
			str = "no id(not saved)" + str;
		}
			
		return "Operation ("+type+":"+getTypeAsString()+"): "+str;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getFundId() {
		return fundId;
	}

	public double getValue() {
		return value;
	}

	public double getUnits() {
		return units;
	}

	public String getPerformedAt() {
		return performedAt;
	}
	
	/**
	 * identyfikator typu operacji funduszu
	 * 
	 * @return String
	 */
	public String getTypeAsString(int type)
	{
		String s = "";
		
		switch (type) 
		{
			case TYPE_BUY: 
				s = "buy";
				break;
			case TYPE_SELL: 
				s = "sell";
				break;
		}
		
		return s;
	}
	
	public String getTypeAsString()
	{
		return getTypeAsString(type);
	}
}
