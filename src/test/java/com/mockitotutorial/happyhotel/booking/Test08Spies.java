package com.mockitotutorial.happyhotel.booking;


import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

class Test08Spies {

	private BookingDAO bookingDAOSpy;
	
	private BookingService bookingService;
	
	private PaymentService paymentServiceMock;
	
	private RoomService roomServiceMock;
	
	private MailSender mailSenderMock;
	
	@BeforeEach
	void setup() {
		
		this.bookingDAOSpy = spy(BookingDAO.class);
		
		this.paymentServiceMock = mock(PaymentService.class);
		
		this.roomServiceMock = mock(RoomService.class);
		
		this.mailSenderMock = mock(MailSender.class);
		
		this.bookingService = new BookingService(this.paymentServiceMock, 
				                                 this.roomServiceMock, 
				                                 this.bookingDAOSpy, 
				                                 this.mailSenderMock);
	}
	
	@Test
	void should_MakeBooking_When_InputOK() {
		
		// Given
		BookingRequest bookingRequest = new BookingRequest("1",
				                                           LocalDate.of(2021, 12, 01),
				                                           LocalDate.of(2021, 12, 05),
				                                           2,
				                                           true);
		
		// When
		String bookingId = bookingService.makeBooking(bookingRequest);
		
		// Then
		
		/* Verify if method bookingDAO.save is invoked at least once on method bookingService.makeBooking */
		verify(bookingDAOSpy).save(bookingRequest);
		
		System.out.println("bookingId=" + bookingId);
	}
	
	@Test
	void should_CancelBooking_When_InputOK() {
		
		// Given
		BookingRequest bookingRequest = new BookingRequest("1",
														   LocalDate.of(2021, 12, 01),
														   LocalDate.of(2021, 12, 05),
														   2,
														   true);
		
		bookingRequest.setRoomId("1.5");
		
		String bookingId = "1";
		
		doReturn(bookingRequest).when(bookingDAOSpy).get(bookingId);
		
		// When
		bookingService.cancelBooking(bookingId);
	}
}
