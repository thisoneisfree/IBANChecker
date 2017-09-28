package IBANChecker;

import java.math.BigInteger;

@SuppressWarnings("unused")

public class IBAN {
	
	private BigInteger numIban;	//hat getter
	private String iban;		//hat getter
	private BigInteger bban;	//hat getter
	private BigInteger cid;
	private BigInteger check;
	
	private String countrySign; //hat getter und setter
	private BigInteger kn;	//hat g+s
	private BigInteger blz;	//hat g+s
	
	
	public IBAN(){					//FERTIG
		numIban = BigInteger.ZERO;
		iban = "";
		bban = BigInteger.ZERO;
		cid  = BigInteger.ZERO;
		check = BigInteger.ZERO;
		countrySign = "";
		kn = BigInteger.ZERO;
		blz = BigInteger.ZERO;
	}
	public IBAN(BigInteger iban){		//FERTIG
		numIban = iban;
		this.iban = "";
		bban = BigInteger.ZERO;
		cid = BigInteger.ZERO;
		check = BigInteger.ZERO;
		countrySign = "";
		kn = BigInteger.ZERO;
		blz = BigInteger.ZERO;
		//Parameter mit wahren werten fuellen, sind ja vorhanden
	}
	
	public IBAN(BigInteger kn, BigInteger blz){	//Annahme von DE als countrySign FERTIG
		numIban = BigInteger.ZERO;
		iban = "";
		bban = BigInteger.ZERO;
		cid  = BigInteger.ZERO;
		check = BigInteger.ZERO;
		countrySign = "DE";
		this.kn = kn;
		this.blz = blz;
		calcNumIBAN(kn,blz,countrySign);
	}
	
	public IBAN(BigInteger kn, BigInteger blz, String countrySign){	//FERTIG
		numIban = BigInteger.ZERO;
		iban = "";
		bban = BigInteger.ZERO;
		cid  = BigInteger.ZERO;
		check = BigInteger.ZERO;
		this.countrySign = countrySign;
		this.kn = kn;
		this.blz = blz;
		calcNumIBAN(kn,blz,countrySign);
	}
	
	public void calcNumIBAN(BigInteger kn, BigInteger blz, String countrySign){  //FERTIG
		countrySign.toUpperCase();
		cid = getCid(countrySign);
		for(int i = 0; i<20;i++) cid.multiply(BigInteger.TEN);
		numIban.add(cid);
		
		bban = getBBAN(kn,blz);
		numIban.add(bban);
		
		check = getCheckDigit(bban, cid);
		for(int i = 0; i<18;i++) check.multiply(BigInteger.TEN);
		numIban.add(check);
		
		iban = "";
		iban += countrySign;
		iban += check.toString();
		iban += blz.toString();
		iban += kn.toString();
		
	}
	
	public BigInteger getNumIBAN(){	//FERTIG
		return numIban;
	}
	
	public String getIBAN(){	//FERTIG
		return iban;
	}
	
	public BigInteger getBLZ(){
		return blz;
	}
	
	public BigInteger getKN(){
		return kn;
	}
	
	public void setKN(BigInteger kn){
		this.kn = kn;
	}
	
	public void setBLZ(BigInteger blz){
		this.blz = blz;
	}
	
	public BigInteger getBBAN(){
		return bban;
	}
	private BigInteger getCid(String countrySign){		//FERTIG
		countrySign.toUpperCase();
		Character second = countrySign.toCharArray()[1];
		BigInteger cid = BigInteger.valueOf((long)(Character.getNumericValue(countrySign.toCharArray()[0])-56));
		for(int i = 0; i<2;i++) cid.multiply(BigInteger.TEN);
		cid.add(BigInteger.valueOf((long)(Character.getNumericValue(countrySign.toCharArray()[1])-56)));
		return cid;
	}
	
	private String getCountySign(BigInteger cid){	//FERTIG
		String sign = "";
		cid.divide(BigInteger.valueOf(100));
		sign += Character.getName(cid.intValue() + 56);
		cid.subtract(BigInteger.valueOf((long)cid.intValue()));
		cid.multiply(BigInteger.valueOf(100));
		sign += Character.getName(cid.intValue() + 56);
		return sign;
	}
	
	public String getCountrySign(){
		return countrySign;
	}
	
	public void setCountrySign(String countrySign){
		this.countrySign = countrySign;
	}
	
	private BigInteger getBBAN(BigInteger kn, BigInteger blz){ //FERTIG
		BigInteger bban = blz; 
		for(int i = 0; i< 10;i++) bban.multiply(BigInteger.TEN);
		bban.add(kn);
		System.out.println(bban);
		return bban;
	}
	
	private BigInteger getCheckDigit(BigInteger bban, BigInteger cid){	//FERTIG
		BigInteger check= BigInteger.ZERO;
		BigInteger of = BigInteger.valueOf(97);
		for(int i = 0; i<6;i++) bban.multiply(BigInteger.TEN);
		for(int i = 0; i<2;i++) cid.multiply(BigInteger.TEN);
		bban.add(cid);
		check = of.subtract(bban.mod(BigInteger.valueOf(97))); 
		return check;
	}
	
		public void dissolve(){	//NICHT FERTIG
			if(iban.isEmpty()) return;
			//Iban wieder zerlegen und vorhande infos in ie variablen schreiben, fuer den fall, das das niht bei der Initialisierung passiert ist
		}
	
	public static boolean check(IBAN toCheck){		//FERTIG
		String iban = toCheck.getIBAN();
		toCheck.dissolve();
		toCheck.calcNumIBAN(toCheck.getKN(), toCheck.getBLZ(), toCheck.getCountrySign());
		if(iban.equals(toCheck.getIBAN())) return true;
		return false; 
	}
	
	public static boolean check(BigInteger numIban){	//FERTIG
		IBAN test = new IBAN(numIban);
		test.dissolve();
		test.calcNumIBAN(test.getKN(), test.getBLZ(), test.getCountrySign());
		if(numIban.compareTo(test.getNumIBAN())==0) return true;
		return false;
	}

	
}
