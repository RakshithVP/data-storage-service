package com.example.dataStorageService.utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.dataStorageService.controller.Employee;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Component
public class CsvUtil {

	public void saveCsv(Employee employee, String file) throws Exception {
		FileWriter outputfile = new FileWriter(file);

		StatefulBeanToCsvBuilder<Employee> builder = new StatefulBeanToCsvBuilder<>(outputfile);
		StatefulBeanToCsv<Employee> beanWriter = builder.build();

		beanWriter.write(employee);
		outputfile.close();
	}

	public Employee getEmployee(String file) throws Exception {
		HeaderColumnNameTranslateMappingStrategy<Employee> strategy = new HeaderColumnNameTranslateMappingStrategy<Employee>();
		strategy.setType(Employee.class);
		setColumnMapping(strategy);

		CSVReader csvReader = null;
		List<Employee> empList = new ArrayList<Employee>();
		CsvToBean<Employee> csvToBean = new CsvToBean<>();
		csvReader = new CSVReader(new FileReader(file));
		csvToBean.setCsvReader(csvReader);
		csvToBean.setMappingStrategy(strategy);

		empList = csvToBean.parse();
		csvReader.close();
		return empList.get(0);
	}

	private void setColumnMapping(HeaderColumnNameTranslateMappingStrategy<Employee> strategy) {
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("name", "Name");
		mapping.put("dateOfBirth", "DateOfBirth");
		mapping.put("salary", "Salary");
		mapping.put("age", "Age");
		strategy.setColumnMapping(mapping);
	}
}