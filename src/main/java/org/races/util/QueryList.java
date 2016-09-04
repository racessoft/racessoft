package org.races.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QueryList {
	
	List<String> columnName = new ArrayList<String>();
	List<String> serviceType = new ArrayList<String>();
	List<String> branches = new ArrayList<String>();
	List<String> districtName = new ArrayList<String>();
	List<String> talukName = new ArrayList<String>();
	List<String> deliveryBranches = new ArrayList<String>();
	List<String> registeredBranches = new ArrayList<String>();
	List<String> serviceNames = new ArrayList<String>();
	List<String> months = new ArrayList<String>();
	Map<String, String> exportType = new TreeMap<String, String>();
	
	public List<String> getColumnNames()
	{
		columnName.clear();
		System.out.println("column name : size "+columnName.size());
		columnName.add("firstServicedate");
		columnName.add("secondServiceDate");
		columnName.add("thirdServiceDate");
		columnName.add("fourthServiceDate");
		columnName.add("fifthServiceDate");
		columnName.add("sixthServiceDate");
		columnName.add("seventhServiceDate");
		columnName.add("eigthServiceDate"); 
		return columnName;
	}
	
	public List<String> getServiceType()
	{
		serviceType.clear();
		System.out.println("serviceType size :"+serviceType.size());
		serviceType.add("1st Service");
		serviceType.add("2nd Service");
		serviceType.add("3rd Service");
		serviceType.add("4th Service");
		serviceType.add("5th Service");
		serviceType.add("6th Service");
		serviceType.add("7th Service");
		serviceType.add("8th Service"); 
		return serviceType;
	}
	
	public List<String> getBranches()
	{
		
		branches.clear();
		System.out.println("branches size :"+branches.size());
		
		branches.add("Salem");
		branches.add("Krishnagiri");
		//branches.add("Dharmapuri");
		branches.add("Attur");
		branches.add("Hosur");
		//branches.add("Harur");
		return branches;
	}
	public List<String> getDistricts()
	{
		districtName.clear();
		System.out.println("districtName size :"+districtName.size());

		districtName.add("DHARMAPURI(DT)");
		districtName.add("SALEM(DT)");
		districtName.add("NAMAKKAL(DT)");
		districtName.add("KRISHNAGIRI(DT)");
		districtName.add("DINDUGUL(DT)");
		districtName.add("THIRUNEVELI(DT)");
		districtName.add("THANJAVUR(DT)");
		districtName.add("THIRUVANNAMALAI(DT)");
		districtName.add("CUDDALORE(DT)");
		districtName.add("VILLUPURAM(DT)");
		districtName.add("THIRUCHIRAPALLI(DT)");
		districtName.add("PERAMBALUR(DT)");
		districtName.add("CHENGALPATTU(DT)");
		districtName.add("MADURAI(DT)");
		districtName.add("VELLORE(DT)"); 
		Collections.sort(districtName); 
		return districtName;
	}
	public List<String> getTaluk()
	{
		talukName.clear();
		System.out.println("talukName size :"+talukName.size());
		talukName.add("HARUR(TK)");
		talukName.add("ATTUR(TK)");
		talukName.add("VAZHAPADY(TK)");
		talukName.add("GANGAVALLI(TK)");
		talukName.add("OMALUR(TK)");
		talukName.add("RASIPURAM(TK)");
		talukName.add("NAMAKKAL(TK)");
		talukName.add("PAPPIREDDYPATTY(TK)");
		talukName.add("UTHANGARAI(TK)");
		talukName.add("SALEM(TK)");
		talukName.add("EDAPADI(TK)");
		talukName.add("DHARMAPURI(TK)");
		talukName.add("DENKANIKOTTAI(TK)");
		talukName.add("PARAMATHIVELUR(TK)");
		talukName.add("SANGAGIRI(TK)");
		talukName.add("THOPPUR(TK)");
		talukName.add("PALACODE(TK)");
		talukName.add("TIRUCHENGODE(TK)");
		talukName.add("POCHAMPALLI(TK)");
		talukName.add("KRISHNAGIRI(TK)");
		talukName.add("HOSUR(TK)");
		talukName.add("KELAMANGALAM(TK)");
		talukName.add("VEERAPANDI(TK)");
		talukName.add("METTUR(TK)");
		talukName.add("PENNAGARAM(TK)"); 
		talukName.add("YERCAUD(TK)");
		talukName.add("PALANI(TK)");
		talukName.add("PALAYANKOTTAI(TK)");
		talukName.add("THANJAVUR(TK)");
		talukName.add("THIRUVANNAMALAI(TK)");
		talukName.add("CUDDALORE(TK)");
		talukName.add("VILLUPURAM(TK)");
		talukName.add("PAPPARAPATTI(TK)");
		talukName.add("SRIRANGAM(TK)");
		talukName.add("PERAMBALUR(TK)");
		talukName.add("MECHERI(TK)");
		talukName.add("ACHARAPAKKAM(TK)");
		talukName.add("PERAIYUR(TK)");
		talukName.add("VEPPANTHATTAI(TK)");
		talukName.add("MELUR(TK)");
		talukName.add("MADURAINORTH(TK)");
		talukName.add("NALAKOTTAI(TK)");
		talukName.add("TIRUPATHUR(TK)"); 
		Collections.sort(talukName);
		return talukName;
	}
	
	public List<String> getRegisteredBranch()
	{
		registeredBranches.clear();
		System.out.println("registeredBranches size :"+registeredBranches.size());
		registeredBranches.add("Salem");
		registeredBranches.add("Krishnagiri");
		//registeredBranches.add("Dharmapuri");
		registeredBranches.add("Attur");
		registeredBranches.add("Hosur");
		//registeredBranches.add("Harur");;
		return registeredBranches;
	}
	
	public List<String> getDeliverydBranch()
	{
		deliveryBranches.clear();
		System.out.println("deliveryBranches size :"+deliveryBranches.size());
		deliveryBranches.add("Salem");
		deliveryBranches.add("Krishnagiri");
		//deliveryBranches.add("Dharmapuri");
		deliveryBranches.add("Attur");
		deliveryBranches.add("Hosur");
		//deliveryBranches.add("Harur");;
		return deliveryBranches;
	}
	
	public List<String> getServiceNames()
	{
		serviceNames.clear();
		System.out.println("serviceNames size :"+serviceNames.size());
		serviceNames.add(0, "Not Applicable");
		serviceNames.add(1,"1st Service");
		serviceNames.add(2,"2nd Service");
		serviceNames.add(3,"3rd Service");
		serviceNames.add(4,"4th Service");
		serviceNames.add(5,"5th Service");
		serviceNames.add(6,"6th Service");
		serviceNames.add(7,"7th Service");
		serviceNames.add(8,"8th Service"); 
		return serviceNames;
	}
	public List<String> getMonth()
	{
		months.clear();
		System.out.println("months size :"+months.size());
		months.add(0, "---");
		months.add(1,"1");
		months.add(2,"2");
		months.add(3,"3");
		months.add(4,"4");
		months.add(5,"5");
		months.add(6,"6");
		months.add(7,"7");
		months.add(8,"8"); 
		months.add(9,"9");
		months.add(10,"10");
		months.add(11,"11");
		months.add(12,"12"); 
		return months;
	}	
	
	/**
	 * Types of export type. Based on this value default or taluk or district
	 * report will be exported.
	 * 
	 * @return export type
	 */
	public Map<String, String> getExportType() {
		exportType.clear();
		exportType.put("DEFAULT", "Default");
		exportType.put("TALUK", "Taluk");
		exportType.put("DISTRICT", "District");
		return exportType;
	}
}
