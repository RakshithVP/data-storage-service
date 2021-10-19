package com.example.dataStorageService.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dataStorageService.controller.Employee;
import com.example.dataStorageService.utils.ProtoUtils;
import com.example.dataStorageService.utils.CsvUtil;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Service
public class StorageService {

	@Autowired
	CsvUtil csvUtil;

	private static final String CSV_EXTENSION = ".csv";
	private static final String XML_EXTENSION = ".xml";

	public enum Filetype {
		CSV, XML
	};

	public void saveFile(String encryptedTextBase64, String fileType, String requestType) {
		String fileSeparator = System.getProperty("file.separator");

		try {
			Employee empParsed = ProtoUtils.deserializeProto(encryptedTextBase64);

			String currentWorkingDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
			StringBuilder filePath = new StringBuilder(currentWorkingDirectory);
			filePath.append(fileSeparator).append(empParsed.getName());

			File file = null;

			if (Filetype.XML.name().equals(fileType)) {
				XmlMapper xmlMapper = new XmlMapper();
				xmlMapper.findAndRegisterModules();
				file = new File(filePath.append(XML_EXTENSION).toString());
				
				if (requestType.equals("Update") && !file.exists()) {
					return;
				}
				xmlMapper.writeValue(file, empParsed);
			}
			if (Filetype.CSV.name().equals(fileType)) {
				file = new File(filePath.append(CSV_EXTENSION).toString());
				if (requestType.equals("Update") && !file.exists()) {
					return;
				}
				csvUtil.saveCsv(empParsed, filePath.toString());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String readEmployeeFromFile(String empName, String fileType) throws Exception {
		String encryptedProto = null;
		String fileSeparator = System.getProperty("file.separator");
		String currentWorkingDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
		StringBuilder filePath = new StringBuilder(currentWorkingDirectory);
		filePath.append(fileSeparator).append(empName);

		if (Filetype.XML.name().equals(fileType)) {
			String xml = inputStreamToString(new FileInputStream(filePath.append(XML_EXTENSION).toString()));

			encryptedProto = ProtoUtils.encryptProto(xml);
		}

		if (Filetype.CSV.name().equals(fileType)) {
			String csv = inputStreamToString(new FileInputStream(filePath.append(CSV_EXTENSION).toString()));
			encryptedProto = ProtoUtils.encryptProto(csv);
		}

		return encryptedProto;
	}

	public String inputStreamToString(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}
}
