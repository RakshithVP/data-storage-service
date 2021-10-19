package com.example.dataStorageService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dataStorageService.service.StorageService;

@RestController
@RequestMapping("employee")
public class DataStorageCollector {

	@Autowired
	StorageService storageService;

	@GetMapping(path = "/read/{empName}")
	public String getEmployee(@PathVariable String empName,
			@RequestParam(name = "fileType", defaultValue = "CSV") String fileType) throws Exception {
		return storageService.readEmployeeFromFile(empName, fileType);
	}
}
