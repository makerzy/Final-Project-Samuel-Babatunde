package com.company.gamestore.service;

import com.company.gamestore.model.*;
import com.company.gamestore.repository.*;
import com.company.gamestore.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceLayer {

    private final double EXTRA_FEE= 15.49;
    private InvoiceRepository invoiceRepository;
    private TaxRepository taxRepository;
    private FeeRepository feeRepository;
    private ConsoleRepository consoleRepository;
    private GameRepository gameRepository;
    private TShirtRepository tShirtRepository;

    @Autowired
    public ServiceLayer(
            InvoiceRepository invoiceRepository,
            TaxRepository taxRepository,
            FeeRepository feeRepository,
            ConsoleRepository consoleRepository,
            GameRepository gameRepository,
            TShirtRepository tShirtRepository
    ) {
        this.invoiceRepository = invoiceRepository;
        this.taxRepository = taxRepository;
        this.feeRepository = feeRepository;
        this.consoleRepository = consoleRepository;
        this.gameRepository = gameRepository;
        this.tShirtRepository = tShirtRepository;
    }

    private double round(double value){
        return (double)Math.round(value *100)/100;
    }

    @Transactional
    public Invoice saveInvoice(InvoiceViewModel ivModel)  {
        Invoice invoice  = new Invoice();

            if (ivModel.getQuantity() < 1) {
                throw new IllegalArgumentException("Order quantity must be greater than or equal to 1");
            }

            invoice.setName(ivModel.getName());
            invoice.setStreet(ivModel.getStreet());
            invoice.setCity(ivModel.getCity());
            invoice.setState(ivModel.getState());
            invoice.setZipcode(ivModel.getZipcode());
            invoice.setItemType(ivModel.getItemType());
            invoice.setItemId(ivModel.getItemId());
            invoice.setQuantity(ivModel.getQuantity());
            double unitPrice = 0.0;
            switch (ivModel.getItemType().toLowerCase()) {
                case "game":
                    Optional<Game> game = gameRepository.findById(ivModel.getItemId());
                    if (game.isPresent()) {
                        if (game.get().getQuantity() < ivModel.getQuantity()) {
                            throw new IllegalArgumentException("Order quantity must be less than or equal available stock");
                        }
                        unitPrice = game.get().getPrice();
                    }
                    else{
                        throw new NotFoundException("Could not find a Game with ID of "+ ivModel.getItemId());
                    }

                    break;
                case "tshirt":
                    Optional<TShirt> tShirt = tShirtRepository.findById(ivModel.getItemId());
                    if (tShirt.isPresent()) {
                        if (tShirt.get().getQuantity() < ivModel.getQuantity()) {
                            throw new IllegalArgumentException("Order quantity must be less than or equal available stock");
                        }
                        unitPrice = tShirt.get().getPrice();
                    }else{
                        throw new NotFoundException("Could not find a T-Shirt with ID of "+ ivModel.getItemId());
                    }

                    break;
                case "console":
                    Optional<Console> console = consoleRepository.findById(ivModel.getItemId());

                    if (console.isPresent()) {
                        if (console.get().getQuantity() < ivModel.getQuantity()) {
                            throw new IllegalArgumentException("Order quantity must be less than or equal available stock");
                        }
                        unitPrice = console.get().getPrice();

                    }else{
                        throw new NotFoundException("Could not find a Console with ID of "+ ivModel.getItemId());
                    }
                    break;
            }
            invoice.setUnitPrice(round(unitPrice));
            double subtotal = round(unitPrice * ivModel.getQuantity());
            invoice.setSubtotal(subtotal);
            double rate = 0.0;
            List<Tax> taxes = taxRepository.findByState(ivModel.getState());
            if (taxes.size() > 0) {
                rate = taxes.get(0).getRate();
            }else{
                // size ==0 if the state code is invalid,
                // since we have all the supported states in the DB
                // Hence, we throw an exception
                throw new IllegalArgumentException("Cannot process order for unknown state code");
            }

            double taxValue = round(subtotal * rate);
            invoice.setTax(taxValue);
            String itemType = ivModel.getItemType().equalsIgnoreCase("tshirt") ? "T-Shirt" : ivModel.getItemType();
            List<Fee> fees = feeRepository.findByProductType(itemType);
            double processingFee = 0.0;

            if (ivModel.getQuantity() > 10) {
                processingFee = fees.get(0).getFee() + EXTRA_FEE;
            }else{
                processingFee = fees.get(0).getFee();
            }
            invoice.setProcessingFee(processingFee);

            double total = subtotal + taxValue + processingFee;
            invoice.setTotal(total);

            return invoiceRepository.save(invoice);
    }

    @Transactional
    public void handleUpdate(String category, int id, Object object) {
            switch (category.toLowerCase()) {
                case "game":
                    Game newGame = (Game) object;
                    if (newGame.getGameId() != id) {
                        throw new IllegalArgumentException("Game ID and ID must be the same");
                    }
                    Optional<Game> game = gameRepository.findById(id);
                    if (game.isEmpty())
                        throw new NotFoundException("Cannot update non existing Game Object");
                    gameRepository.save(newGame);
                    break;
                case "console":
                    Console newConsole = (Console) object;
                    if (newConsole.getConsoleId() != id) {
                        throw new IllegalArgumentException("Console ID and ID must be the same");
                    }
                    Optional<Console> console = consoleRepository.findById(id);
                    if (console.isEmpty())
                        throw new NotFoundException("Cannot update non existing Console Object");
                    consoleRepository.save(newConsole);
                    break;
                case "tshirt":
                    TShirt newTshirt = (TShirt) object;
                    if (newTshirt.getTshirtId() != id) {
                        throw new IllegalArgumentException("T-Shirt ID and ID must be the same");
                    }
                    Optional<TShirt> tShirt = tShirtRepository.findById(id);
                    if (tShirt.isEmpty())
                        throw new NotFoundException("Cannot update non existing T-Shirt Object");
                    tShirtRepository.save(newTshirt);
                    break;
                case "invoice":
                    Invoice newInvoice = (Invoice) object;
                    if (newInvoice.getInvoiceId() != id) {
                        throw new IllegalArgumentException("Invoice ID and ID must be the same");
                    }
                    Optional<Invoice> invoice = invoiceRepository.findById(id);
                    if (invoice.isEmpty())
                        throw new NotFoundException("Cannot update non existing Invoice Object");
                    invoiceRepository.save(newInvoice);
                    break;
            }
    }
}
