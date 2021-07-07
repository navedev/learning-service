package com.wipro.learning;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wipro.learning.controller.ContentController;
import com.wipro.learning.model.ContentRequestDto;
import com.wipro.learning.service.ContentService;

@SpringBootTest
@ComponentScan(basePackages = { "com.wipro.learning.*", "com.wipro.reglogin.*", "com.wipro.payment.model.*" })
class ContentControllerTests {

	@MockBean
	private ContentService contentService;

	@Autowired
	private ContentController contentController;

	@Test
	void testCreateContent() {

		ContentRequestDto contentReq = new ContentRequestDto();
		contentReq.setData("Java is not pure Object Oriented Language");
		contentReq.setTitle("Java");
		contentReq.setUserid(1);

		ResponseEntity response = new ResponseEntity(HttpStatus.ACCEPTED);

		Mockito.when(contentService.createContent(contentReq)).thenReturn(response);

		assertNotNull(contentController.createContent(contentReq));
	}

}
