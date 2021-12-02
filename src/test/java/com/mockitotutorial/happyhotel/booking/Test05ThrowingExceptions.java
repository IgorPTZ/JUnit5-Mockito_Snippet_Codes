package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

class Test05ThrowingExceptions {

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
	void should_ThrowException_When_NoRoomAvailable() {
		
		// Given
		BookingRequest bookingRequest = new BookingRequest("1",
				                                           LocalDate.of(2021, 12, 01),
				                                           LocalDate.of(2021, 12, 05),
				                                           2,
				                                           false);
		
		when(this.roomServiceMock.findAvailableRoomId(bookingRequest))
		.thenThrow(BusinessException.class);
		
		// When
		Executable executable = () -> bookingService.makeBooking(bookingRequest);
		
		// Then
		assertThrows(BusinessException.class, executable);
	}
}
