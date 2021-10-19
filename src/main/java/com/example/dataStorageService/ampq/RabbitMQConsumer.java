package com.example.dataStorageService.ampq;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.example.dataStorageService.service.StorageService;

@Component
public class RabbitMQConsumer {

	@Autowired
	StorageService storageService;
	
	@RabbitListener(queuesToDeclare = @Queue(name = "${employee.service.rabbitmq.queue}"))
	public void recievedMessage(String employee, @Header("fileType") String fileType, @Header("RequestType") String requestType) {
		storageService.saveFile(employee, fileType, requestType);
	}
}
