package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;

class Test04MultipleThenReturnCalls {

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
	void should_CountAvailablePlaces_When_CalledMultipleTimes() {
		
		// Given
		when(this.roomServiceMock.getAvailableRooms())
		
		      // First method call of roomService.getAvailableRooms() on bookingService.getAvailablePlaceCount()
		.thenReturn(Collections.singletonList(new Room("Room 1", 2)))
		
		     // Second method call o roomService.getAvailableRooms() on bookingService.getAvailablePlaceCount()
		.thenReturn(Collections.emptyList());
				
		int expectedFirstCall = 2;
		
		int expectedSecondCall = 0;
		
		// When
		int actualFirstCall = bookingService.getAvailablePlaceCount();
		
		int actualSecondCall = bookingService.getAvailablePlaceCount();
		
		// Then
		assertAll(
			() -> assertEquals(expectedFirstCall, actualFirstCall),
			() -> assertEquals(expectedSecondCall, actualSecondCall)
		);
	}
}
