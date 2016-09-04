package org.races.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.races.model.CustomerDetails;

public class ExportUtil {
	public byte[] getCustomerExcelData(Map<String, Object> customerData) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		List<CustomerDetails> customerDetails = (List<CustomerDetails>) customerData.get("dataList");
		Map<String, Object[]> data = new HashMap<String, Object[]>();
		data.put("1", new Object[] {"Sno", "Customer Id", "Customer Details", "Contact No", "Chasis Number", "Engine Number"});
		for(int i=0 ;i<customerDetails.size();i++){ 
				data.put(""+i, new Object[] {i, customerDetails.get(i).getCustomerId(), customerDetails.get(i).getCustomerDetails(), customerDetails.get(i).getContactNumber(),customerDetails.get(i).getChasisNumber(), customerDetails.get(i).getEngineNumber()});	
		}
		
		
		HSSFSheet sheet = workbook.createSheet("Customer Data"); 
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
		    Row row = sheet.createRow(rownum++);
		    Object [] objArr = data.get(key);
		    int cellnum = 0;
		    for (Object obj : objArr) {
		        Cell cell = row.createCell(cellnum++);
		        if(obj instanceof Date) 
		            cell.setCellValue((Date)obj);
		        else if(obj instanceof Boolean)
		            cell.setCellValue((Boolean)obj);
		        else if(obj instanceof String)
		            cell.setCellValue((String)obj);
		        else if(obj instanceof Double)
		            cell.setCellValue((Double)obj);
		    }
		}  
		ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
		
		try {
			workbook.write(bOutput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte customerReportDataArray [] = bOutput.toByteArray();
		return customerReportDataArray;
	}
	
}
