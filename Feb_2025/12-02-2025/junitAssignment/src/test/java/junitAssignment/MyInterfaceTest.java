package junitAssignment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class MyInterfaceTest {

	@Test
	void testAbc() {
		MyInterface mi = mock(MyInterface.class);
		mi.xyz();
		mi.xyz();
		mi.xyz();
//		verify(mi,times(1)).xyz();
		verify(mi,times(3)).xyz();
		verify(mock(Thread.class),times(0)).suspend();
	}

}
