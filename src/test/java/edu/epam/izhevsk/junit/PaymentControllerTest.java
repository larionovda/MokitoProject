package edu.epam.izhevsk.junit;

import edu.epam.izhevsk.junit.dao.AccountService;
import edu.epam.izhevsk.junit.dao.DepositService;
import edu.epam.izhevsk.junit.services.PaymentController;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalMatchers.*;
import org.junit.Test;
import static org.mockito.BDDMockito.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

    @Mock
    AccountService accountServiceMock;
    @Mock
    DepositService depositServiceMock;

    @InjectMocks
    PaymentController paymentController;

    @Test
    public void whenUserId100ReturnTrue() {
        when(accountServiceMock.isUserAuthenticated(100L)).thenReturn(true);
        assertTrue(accountServiceMock.isUserAuthenticated(100L));
    }

    @Test
    public void whenDepositNormalReturnTrue() throws InsufficientFundsException {
        when(depositServiceMock.deposit(leq(100L), anyLong())).thenReturn("Success");
        assertEquals("Success", depositServiceMock.deposit(50L, 1L));
    }

    @Test(expected = InsufficientFundsException.class)
    public void whenDepositNotNormalReturnTrue() throws InsufficientFundsException {
        when(depositServiceMock.deposit(gt(100L), anyLong())).thenThrow(new InsufficientFundsException());
        depositServiceMock.deposit(200L, 100L);
    }

    @Test
    public void Test1() throws InsufficientFundsException {
        when(accountServiceMock.isUserAuthenticated(anyLong())).thenReturn(true);
        when(depositServiceMock.deposit(eq(50L), anyLong())).thenReturn("Success");
        paymentController.deposit(50L, 100L);
        verify(accountServiceMock).isUserAuthenticated(100L);
    }

    @Test(expected = SecurityException.class)
    public void Test2() throws InsufficientFundsException {
        when(accountServiceMock.isUserAuthenticated(100L)).thenReturn(true);
        when(depositServiceMock.deposit(50L, 100L)).thenReturn("Success");
        paymentController.deposit(50L, 99L);
    }

    @Test(expected = InsufficientFundsException.class)
    public void Test3() throws InsufficientFundsException {
        when(accountServiceMock.isUserAuthenticated(anyLong())).thenReturn(true);
        when(depositServiceMock.deposit(gt(100L), anyLong())).thenThrow(new InsufficientFundsException());
        paymentController.deposit(101L, 10L);
    }

}
