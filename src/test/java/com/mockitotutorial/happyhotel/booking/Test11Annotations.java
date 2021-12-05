package com.mockitotutorial.happyhotel.booking;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Test11Annotations {

	@InjectMocks
	private BookingService bookingService;
	
	@Mock
	private PaymentService paymentServiceMock;
	
	@Mock
	private RoomService roomServiceMock;
	
	@Spy
	private BookingDAO bookingDAOMock;
	
	@Mock
	private MailSender mailSenderMock;
	
	@Captor
	private ArgumentCaptor<Double> doubleCaptor;
	
	
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
