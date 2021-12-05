package com.mockitotutorial.happyhotel.booking;


import java.time.LocalDate;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Test13StrictStubbing {

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
	void should_InvokePayment_When_Prepaid() {
		
		// Given
		BookingRequest bookingRequest = new BookingRequest("1",
				                                           LocalDate.of(2021, 12, 01),
				                                           LocalDate.of(2021, 12, 05),
				                                           2,
				                                           false);
		
		// When
		bookingService.makeBooking(bookingRequest);
		
		// Then
		
		/* No exception is thrown */
	}
}
