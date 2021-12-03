package com.mockitotutorial.happyhotel.booking;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

class Test10ArgumentCaptors {

	private BookingService bookingService;
	
	private PaymentService paymentServiceMock;
	
	private RoomService roomServiceMock;
	
	private BookingDAO bookingDAOMock;
	
	private MailSender mailSenderMock;
	
	private ArgumentCaptor<Double> doubleCaptor;
	
	@BeforeEach
	void setup() {
		
		this.paymentServiceMock = mock(PaymentService.class);
		
		this.roomServiceMock = mock(RoomService.class);
		
		this.bookingDAOMock = mock(BookingDAO.class);
		
		this.mailSenderMock = mock(MailSender.class);
		
		this.bookingService = new BookingService(this.paymentServiceMock, 
				                                 this.roomServiceMock,  
				                                 this.bookingDAOMock, 
				                                 this.mailSenderMock);
		
		this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
	}
	
	@Test
	void should_PayCorrectPrice_When_InputOK() {
		
		// Given
		BookingRequest bookingRequest = new BookingRequest("1",
				                                           LocalDate.of(2021, 12, 01),
				                                           LocalDate.of(2021, 12, 05),
				                                           2,
				                                           true);
		
		// When
		bookingService.makeBooking(bookingRequest);
		
		// Then
		verify(paymentServiceMock, times(1)).pay(eq(bookingRequest), doubleCaptor.capture());
		
		double actualValue = doubleCaptor.getValue();
		
		assertEquals(400.0, actualValue);
	}
	
	@Test
	void should_PayCorrectPrice_When_MultipleCalls() {
		
		// Given
		BookingRequest bookingRequestOne = new BookingRequest("1",
														   LocalDate.of(2021, 12, 01),
														   LocalDate.of(2021, 12, 05),
														   2,
														   true);
		
		BookingRequest bookingRequestTwo = new BookingRequest("2",
															  LocalDate.of(2021, 12, 06),
															  LocalDate.of(2021, 12, 10),
															  2,
															  true);
		
		List<Double> expectedValues = Arrays.asList(400.0, 400.0);
		
		// When
		bookingService.makeBooking(bookingRequestOne);
		
		bookingService.makeBooking(bookingRequestTwo);
		
		// Then
		verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
		
		List<Double> actualValues = doubleCaptor.getAllValues();
		
		assertEquals(expectedValues, actualValues);
	}
}
