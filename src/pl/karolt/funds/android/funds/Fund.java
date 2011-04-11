package pl.karolt.funds.android.funds;

import android.database.Cursor;
import pl.karolt.funds.android.funds.exceptions.UnknownFundTypeException;

public class Fund {
	/**
	 * identyfikator w lokalnej bazie
	 */
	private long	id = 0;
	
	/**
	 * nazwa
	 */
	private String	name;
	
	/**
	 * obecna wartosc
	 */
	private double	currentValue;
	
	/**
	 * typ pieniezny/obligacji/hybrydowy/akcji
	 * definiowany stalymi TYPE_CASH, TYPE_BONDS, TYPE_HYBRID, TYPE_STOCK
	 */
	private int		type = 0;
	
	/**
	 * stala okreslajaca typ funduszu - pieniezne 
	 */
	public static final int TYPE_CASH	= 1;
	
	/**
	 * stala okreslajaca typ funduszu - obligacji 
	 */
	public static final int TYPE_BONDS	= 2;
	
	/**
	 * stala okreslajaca typ funduszu - hybrydowe 
	 */
	public static final int TYPE_HYBRID	= 3;
	
	/**
	 * stala okreslajaca typ funduszu - akcji 
	 */
	public static final int TYPE_STOCK	= 4;
	
	/**
	 * kostruktor 
	 * 
	 * @param id
	 * @param name
	 * @param currentValue
	 * @param type definiowany stalymi TYPE_CASH, TYPE_BONDS, TYPE_HYBRID, TYPE_STOCK
	 * @throws UnknownFundTypeException
	 */
	public Fund(long id, String name, double currentValue, int type) throws UnknownFundTypeException {
		super();
		this.id = id;
		this.name = name;
		this.currentValue = currentValue;
		
		if (type < TYPE_CASH || type > TYPE_STOCK) {
			throw new UnknownFundTypeException();
		}
		this.type = type;
			
	}
	/**
	 * konstruktor 
	 * 
	 * @param name
	 * @param currentValue
	 * @param type definiowany stalymi TYPE_CASH, TYPE_BONDS, TYPE_HYBRID, TYPE_STOCK
	 * @throws UnknownFundTypeException
	 */
	public Fund(String name, double currentValue, int type)  {
		super();
		this.name = name;
		this.currentValue = currentValue;
		
		if (type < TYPE_CASH || type > TYPE_STOCK) {
			//throw new UnknownFundTypeException();
			type = 0;
		}
		this.type = type;
	}
	
	public Fund (Cursor cursor)
	{
		id		= cursor.getLong(cursor.getColumnIndex("_id"));
		name	= cursor.getString(cursor.getColumnIndex("name"));
		type	= cursor.getInt(cursor.getColumnIndex("type"));
		currentValue	= cursor.getDouble(cursor.getColumnIndex("currentValue"));
		
	}

	public String toString()
	{
		String str = "name="+name+", currentValue="+currentValue+", type="+getTypeAsString()+"("+type+")"; 
		if (id != 0) {
			str = "id="+id+", " + str; 
		} else {
			str = "no id(not saved) + str";
		}
			
		return "Fund: "+str;
	}


	public double getCurrentValue() {
		return currentValue;
	}


	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}
	
	public double getValueForDate(String date)
	{
		//TODO
		return getCurrentValue();
	}


	public long getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public int getType() {
		return type;
	}
	
	/**
	 * identyfikator typu funduszu
	 * 
	 * @return String
	 */
	public String getTypeAsString(int type)
	{
		String s = "";
		
		switch (type) 
		{
			case TYPE_CASH: 
				s = "cash";
				break;
			case TYPE_BONDS: 
				s = "bonds";
				break;
			case TYPE_HYBRID: 
				s = "hybrid";
				break;
			case TYPE_STOCK: 
				s = "stock";
				break;
			
		}
		
		return s;
	}
	
	public String getTypeAsString()
	{
		return getTypeAsString(type);
	}
	
	
}
