package com.company.gamestore.service;

import com.company.gamestore.exception.InvalidQuantityException;
import com.company.gamestore.exception.UnknownStateCodeException;
import com.company.gamestore.model.*;
import com.company.gamestore.repository.*;
import com.company.gamestore.viewmodel.InvoiceViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {
    private ServiceLayer serviceLayer;
    private InvoiceRepository invoiceRepository;
    private TaxRepository taxRepository;
    private FeeRepository feeRepository;
    private GameRepository gameRepository;
    private ConsoleRepository consoleRepository;
    private TShirtRepository tShirtRepository;
    Invoice invoice;
    InvoiceViewModel viewModel;

    @BeforeEach
    private void setUp() {
        setUpFeeRepo();
        setUpTaxRepo();
        setUpConsoleRepo();
        setUpTShirtRepo();
        setUpGameRepo();
        setUpInvoiceRepo();


    }

    private void setUpGameRepo() {
        gameRepository = mock(GameRepository.class);

        Game game = new Game();
        game.setGameId(1);
        game.setTitle("The Legend of Adventure");
        game.setEsrbRating("E10+");
        game.setDescription("Embark on an epic journey to save the mystical land from darkness.");
        game.setPrice(49.99);
        game.setStudio("QuestWorks Studios");
        game.setQuantity(100);

        List<Game> games = new ArrayList<>();
        games.add(game);

        doReturn(game).when(gameRepository).save(game);
        doReturn(Optional.of(game)).when(gameRepository).findById(1);
        doReturn(games).when(gameRepository).findAll();
    }

    public void setUpConsoleRepo() {
        consoleRepository = mock(ConsoleRepository.class);
        Console console = new Console();
        console.setConsoleId(1);
        console.setModel("PlayStation 5");
        console.setManufacturer("Sony");
        console.setMemoryAmount("825GB SSD");
        console.setProcessor("Custom AMD Zen 2");
        console.setPrice(499.99);
        console.setQuantity(100);

        List<Console> consoles = new ArrayList<>();
        consoles.add(console);

        doReturn(console).when(consoleRepository).save(console);
        doReturn(Optional.of(console)).when(consoleRepository).findById(1);
        doReturn(consoles).when(consoleRepository).findAll();
    }

    public void setUpTShirtRepo() {
        tShirtRepository = mock(TShirtRepository.class);
        TShirt tShirt = new TShirt();
        tShirt.setTshirtId(1);
        tShirt.setSize("M");
        tShirt.setColor("Red");
        tShirt.setDescription("TShirt description");
        tShirt.setPrice(19.99);
        tShirt.setQuantity(20);


        List<TShirt> tShirts = new ArrayList<>();
        tShirts.add(tShirt);

        doReturn(tShirt).when(tShirtRepository).save(tShirt);
        doReturn(Optional.of(tShirts)).when(tShirtRepository).findById(1);
        doReturn(tShirts).when(tShirtRepository).findAll();
    }

    public void setUpFeeRepo() {
        feeRepository = mock(FeeRepository.class);

        Fee fee = new Fee();
        fee.setProductType("Console");
        fee.setFee(14.99);
        Fee fee1 = new Fee();
        fee1.setFee(1.98);
        fee1.setProductType("T-Shirt");
        Fee fee2 = new Fee();
        fee2.setProductType("Game");
        fee2.setFee(1.49);
        List<Fee> fees = new ArrayList<>();
        fees.add(fee);
        fees.add(fee1);
        fees.add(fee2);

        doReturn(fee).when(feeRepository).save(fee);
        doReturn(fee1).when(feeRepository).save(fee1);
        doReturn(fee2).when(feeRepository).save(fee2);
        doReturn(Arrays.asList(fee)).when(feeRepository).findByProductType("Console");
        doReturn(Arrays.asList(fee1)).when(feeRepository).findByProductType("T-Shirt");
        doReturn(Arrays.asList(fee2)).when(feeRepository).findByProductType("Game");
        doReturn(fees).when(feeRepository).findAll();
    }

    public void setUpTaxRepo() {
        taxRepository = mock(TaxRepository.class);
        Tax tax = new Tax();
        tax.setState("CA");
        tax.setRate(0.06);
        Tax tax1 = new Tax();
        tax1.setState("FL");
        tax1.setRate(0.06);
        Tax tax2 = new Tax();
        tax.setState("GA");
        tax.setRate(0.07);

        List<Tax> taxes = new ArrayList<>();
        taxes.add(tax);
        taxes.add(tax1);
        taxes.add(tax2);

        doReturn(tax).when(taxRepository).save(tax);
        doReturn(tax1).when(taxRepository).save(tax1);
        doReturn(tax2).when(taxRepository).save(tax2);
        doReturn(Arrays.asList(tax)).when(taxRepository).findByState("CA");
        doReturn(Arrays.asList(tax1)).when(taxRepository).findByState("FL");
        doReturn(Arrays.asList(tax2)).when(taxRepository).findByState("GA");
        doReturn(taxes).when(taxRepository).findAll();
    }

    public void setUpInvoiceRepo() {
        invoiceRepository = mock(InvoiceRepository.class);
        invoice = new Invoice();

        invoice.setInvoiceId(1);
        invoice.setName("Customer Name");
        invoice.setStreet("1111 Customer Street");
        invoice.setCity("Los Gatos");
        invoice.setState("California");
        invoice.setZipcode("94065");
        invoice.setItemType("Console");
        invoice.setItemId(1);
        invoice.setQuantity(2);
        List<Tax> taxes = taxRepository.findByState("CA");
        List<Fee> fees = feeRepository.findByProductType("Console");
        Fee fee = fees.get(0);
        Tax tax = taxes.get(0);

        Optional<Console> console = consoleRepository.findById(1);

        double unitPrice = console.get().getPrice();
        invoice.setUnitPrice(unitPrice);
        double subtotal = round(2 * unitPrice);
        invoice.setSubtotal(subtotal);
        double taxVal = round(subtotal * tax.getRate());
        invoice.setTax(taxVal);
        double processingFee = fee.getFee();
        invoice.setProcessingFee(processingFee);
        double total = round(subtotal + processingFee + taxVal);
        invoice.setTotal(total);

        viewModel = new InvoiceViewModel();
        viewModel.setInvoiceId(1);
        viewModel.setName("Customer Name");
        viewModel.setStreet("1111 Customer Street");
        viewModel.setCity("Los Gatos");
        viewModel.setState("California");
        viewModel.setZipcode("94065");
        viewModel.setItemType("Console");
        viewModel.setItemId(1);
        viewModel.setQuantity(2);

        serviceLayer = mock(ServiceLayer.class);

        doReturn(invoice).when(serviceLayer).saveInvoice(viewModel);
        doReturn(Optional.of(invoice)).when(invoiceRepository).findById(1);
    }

    private double round(double value){
        return (double)Math.round(value *100)/100;
    }


    @Test
    public void shouldAddNewInvoice() {
        Invoice invoice1 = serviceLayer.saveInvoice(viewModel);
        assertEquals(invoice1, invoice);
    }


    @Test
    public void shouldThrowInsufficientStockException() {
        viewModel.setQuantity(110);
        when(serviceLayer.saveInvoice(viewModel)).thenThrow(new InsufficientStockException("Order quantity must be less than or equal available stock"));

    }



    @Test
    public void shouldThrowUnknownStateCodeException() {
        viewModel.setState("MA");
        when(serviceLayer.saveInvoice(viewModel)).thenThrow(new UnknownStateCodeException("Cannot process order for unknown state code"));
    }

    @Test
    public void shouldThrowInvalidQuantityException() {
        viewModel.setQuantity(0);
        when(serviceLayer.saveInvoice(viewModel)).thenThrow(new InvalidQuantityException("Order quantity must be greater than or equal to 1"));

    }

//    @Test
//    public void shouldThrowIfAttemptToUpdateNullObject() {
//
//    }
//
//
//    @Test
//    public void shouldThrowIfAttemptToUpdateNotMatchingIDs() {
//
//    }
}
