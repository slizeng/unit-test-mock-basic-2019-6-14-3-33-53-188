package cashregister;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CashRegisterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void should_print_the_real_purchase_when_call_process() {
        CashRegister cashRegister = new CashRegister(new Printer());
        Purchase purchase = new Purchase(new Item[]{new Item("apple", 1)});

        cashRegister.process(purchase);

        assertEquals(purchase.asString().trim(), outContent.toString().trim());
    }

    @Test
    public void should_print_the_stub_purchase_when_call_process() {
        Purchase purchase = mock(Purchase.class);
        CashRegister cashRegister = new CashRegister(new Printer());

        when(purchase.asString()).thenReturn("stubbing string");
        cashRegister.process(purchase);

        assertEquals("stubbing string", outContent.toString().trim());
    }

    @Test
    public void should_verify_with_process_call_with_mockito() {
        Printer printer = mock(Printer.class);
        CashRegister cashRegister = new CashRegister(printer);
        Purchase purchase = new Purchase(new Item[]{});

        cashRegister.process(purchase);

        verify(printer).print(purchase.asString());
    }
}
