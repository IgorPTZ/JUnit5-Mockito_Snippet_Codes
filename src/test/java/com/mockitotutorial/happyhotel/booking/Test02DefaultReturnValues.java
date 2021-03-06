package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;

class Test02DefaultReturnValues {

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
		
		System.out.println("List returned " + roomServiceMock.getAvailableRooms());
		
		System.out.println("Object returned " + roomServiceMock.findAvailableRoomId(null));
		
		System.out.println("Primitive returned " + roomServiceMock.getRoomCount());
	}
	
	@Test
	void shoud_CountAvailablePlaces() {
		
		// Given
		int expected = 0;
		
		// When
		int actual = bookingService.getAvailablePlaceCount();
		
		// Then
		assertEquals(expected, actual);
	}
}
