package com.gridu.microservice.taxes.rest.service;

import com.gridu.microservice.taxes.dao.StateDao;
import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.service.StateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {

	private static final String STATE_CODE = "AZ";
	private static final String STATE_CODE_2 = "AR";

	@InjectMocks
	private StateService stateService;

	@Mock
	private StateDao stateDaoMock;

	private State existingState1;
	private State existingState1_copy;
	private State existingState2;

	@Before
	public void setUp() {
		stateDaoMock = Mockito.mock(StateDao.class);

	//	stateService = new StateService();
		stateService.setStateDao(stateDaoMock);

		existingState1 = new State(1L, STATE_CODE, "Arizona");
		existingState1_copy = new State(1L, STATE_CODE, "Arizona");
		existingState2 = new State(2L, STATE_CODE_2, "Arkanzas");

		Mockito.when(stateDaoMock.findByCode(STATE_CODE_2))
			.thenReturn(existingState2);
	}

	@Test
	public void testFindByCode_FindExisting() {
		// ARRANGE
		Mockito.when(stateDaoMock.findByCode(STATE_CODE))
			.thenReturn(existingState1_copy);

		// ACT
		State foundState = stateService.findByCode(STATE_CODE);
		// ASSERT
		assertNotNull(foundState);
		assertEquals(existingState1, foundState);
	}

	@Test
	public void testFindByCode_FindNotExisting() {
		// ARRANGE
		Mockito.when(stateDaoMock.findByCode(Mockito.eq(STATE_CODE)))
			.thenReturn(existingState1_copy);
		// ACT
		State foundState = stateService.findByCode("XX");
		// ASSERT
		assertNull(foundState);
	}

	@Test
	public void testFindByCode_FindByNull() {
		// ARRANGE
		Mockito.when(stateDaoMock.findByCode(Mockito.eq(STATE_CODE)))
			.thenReturn(existingState1_copy);
		// ACT
		State foundState = stateService.findByCode(null);
		// ASSERT
		assertNull(foundState);
	}

	@Test(expected = RuntimeException.class)
	public void testFindByCode_ExceptionThrow() {
		// ARRANGE
		Mockito.when(stateDaoMock.findByCode(Mockito.anyString()))
			.thenThrow(new RuntimeException("TEST EXCEPTION"));
		// ACT
		State foundState = stateService.findByCode(null);
		// ASSERT
		assertNull(foundState);
	}
}
