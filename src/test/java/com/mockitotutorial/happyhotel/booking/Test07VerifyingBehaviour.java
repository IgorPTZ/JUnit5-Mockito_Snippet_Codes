package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

class Test07VerifyingBehaviour {

	private BookingService bookingService;
	
	private PaymentService paymentServiceMock;
	
	private RoomService roomServiceMock;
	
	private BookingDAO bookingDAOMock;
	
	private MailSender mailSenderMock;
	
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
	}
	
	@Test
	void should_InvokePayment_When_Prepaid() {
		
		// Given
		BookingRequest bookingRequest = new BookingRequest("1",
				                                           LocalDate.of(2021, 12, 01),
				                                           LocalDate.of(2021, 12, 05),
				                                           2,
				                                           true);
		
		// When
		bookingService.makeBooking(bookingRequest);
		
		// Then
		verify(paymentServiceMock, times(1)).pay(bookingRequest, 400.0);
		
		/* Check if any other method in this mock is invoked (In this case, paymentService on bookingService.makeBooking)*/
		verifyNoMoreInteractions(paymentServiceMock);
	}
	
	@Test
	void should_NotInvokePayment_When_NotPrepaid() {
		
		// Given
		BookingRequest bookingRequest = new BookingRequest("2",
				                                           LocalDate.of(2021, 12, 01),
				                                           LocalDate.of(2021, 12, 05),
				                                           2,
				                                           false);
		
		// When
		bookingService.makeBooking(bookingRequest);
		
		// Then
		verify(paymentServiceMock, never()).pay(any(), anyDouble());
	}
}
