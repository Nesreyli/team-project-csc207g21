package use_case.buySell;

import entity.BuySellReceipt;
import entity.Response;
import entity.ResponseFactory;
import interface_adapter.stock_price.PriceState;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BuySellInteractorTest {
    /**
     * BuySellAccessObject test double
     */

    private static class TestBuySellAccessObject implements BuySellAccessInterface {

        // Things that the backend would return
        private Character order;
        private String outSymbol;
        private Integer outAmount;
        private BigDecimal price;
        private BigDecimal totalPrice;
        private int code;

        public void setValues(Character order, String outSymbol, Integer outAmount,
                                    BigDecimal price, BigDecimal totalPrice, Integer code) {
            this.order = order;
            this.outSymbol = outSymbol;
            this.outAmount = outAmount;
            this.price = price;
            this.totalPrice = totalPrice;
            this.code = code;
        }

        @Override
        public Response setStockData(PriceState priceState, Integer amount, Boolean isBuy) {
            if (code == 200) {
                BuySellReceipt buySellReceipt = new BuySellReceipt.Builder().order(order)
                        .amount(outAmount).symbol(outSymbol).price(price).totalPrice(totalPrice).build();

                return ResponseFactory.create(code, buySellReceipt);
            } else {
                return ResponseFactory.create(code, null);
            }
        }
    }

    /**
     * BuySellOutputBoundary test double
     */
    private static class TestBuySellOutputBoundary implements BuySellOutputBoundary {

        // Elements that would be found in View
        private String responseMessage;
        private Character order;
        private String outSymbol;
        private Integer outAmount;
        private BigDecimal price;
        private BigDecimal totalPrice;

        @Override
        public void prepareSuccessView(BuySellOutputData buySellOd) {
            responseMessage = "Success view called";
            order = buySellOd.getOrder();
            outSymbol = buySellOd.getSymbol();
            outAmount = buySellOd.getAmount();
            price = buySellOd.getPrice();
            totalPrice = buySellOd.getTotalPrice();
        }

        @Override
        public void prepareFailView(String message) {
            responseMessage = message;
        }

        public String getResponseMessage() {
            return responseMessage;
        }

        public Character getOrder() {
            return order;
        }

        public String getOutSymbol() {
            return outSymbol;
        }

        public Integer getOutAmount() {
            return outAmount;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }
    }

    @Test
    void testSuccessfulBuy() {

        // Sample price state
        PriceState priceState = new PriceState();

        // Set values for BuySellAccessObject double
        TestBuySellAccessObject testBuySellDB = new TestBuySellAccessObject();
        testBuySellDB.setValues('b', "AMZN", 2, new BigDecimal(200),
                new BigDecimal(400), 200);

        // Set input data
        BuySellInputData inputData = new BuySellInputData(priceState, 2, true);

        // Initialize output boundary
        TestBuySellOutputBoundary testBuySellOB = new TestBuySellOutputBoundary();

        // Interactor call
        BuySellInteractor interactor = new BuySellInteractor(testBuySellDB, testBuySellOB);
        interactor.execute(inputData);

        // Assert the values that were set equal to what appears in view
        assertEquals('b',  testBuySellOB.getOrder());
        assertEquals("AMZN", testBuySellOB.getOutSymbol());
        assertEquals(2,  testBuySellOB.getOutAmount());
        assertEquals(new BigDecimal(200),  testBuySellOB.getPrice());
        assertEquals(new BigDecimal(400),  testBuySellOB.getTotalPrice());

        // Assert success view is displayed
        assertEquals("Success view called", testBuySellOB.getResponseMessage());

    }

    @Test
    void testSuccessfulSell() {

        // Sample price state
        PriceState priceState = new PriceState();

        // Set values for BuySellAccessObject double
        TestBuySellAccessObject testBuySellDB = new TestBuySellAccessObject();
        testBuySellDB.setValues('s', "AAPL", 3, new BigDecimal(100),
                new BigDecimal(300), 200);

        // Set input data
        BuySellInputData inputData = new BuySellInputData(priceState, 3, false);

        // Initialize output boundary
        TestBuySellOutputBoundary testBuySellOB = new TestBuySellOutputBoundary();

        // Interactor call
        BuySellInteractor interactor = new BuySellInteractor(testBuySellDB, testBuySellOB);
        interactor.execute(inputData);

        // Assert the values that were set equal to what appears in view
        assertEquals('s',  testBuySellOB.getOrder());
        assertEquals("AAPL", testBuySellOB.getOutSymbol());
        assertEquals(3,  testBuySellOB.getOutAmount());
        assertEquals(new BigDecimal(100),  testBuySellOB.getPrice());
        assertEquals(new BigDecimal(300),  testBuySellOB.getTotalPrice());

        // Assert success view is displayed
        assertEquals("Success view called", testBuySellOB.getResponseMessage());

    }

    @Test
    void testFailedTransaction() {

        // Sample price state
        PriceState priceState = new PriceState();

        // Set values for BuySellAccessObject double
        TestBuySellAccessObject testBuySellDB = new TestBuySellAccessObject();
        testBuySellDB.setValues('b', "MSFT", 2, new BigDecimal(150),
                new BigDecimal(300), 400);

        // Set input data
        BuySellInputData inputData = new BuySellInputData(priceState, 2, true);

        // Initialize output boundary
        TestBuySellOutputBoundary testBuySellOB = new TestBuySellOutputBoundary();

        // Interactor call
        BuySellInteractor interactor = new BuySellInteractor(testBuySellDB, testBuySellOB);
        interactor.execute(inputData);

        // Assert correct error message displayed
        assertEquals("You don't have enough funds/shares!", testBuySellOB.getResponseMessage());
    }

    @Test
    void testServerError() {

        // Sample price state
        PriceState priceState = new PriceState();

        // Set values for BuySellAccessObject double
        TestBuySellAccessObject testBuySellDB = new TestBuySellAccessObject();
        testBuySellDB.setValues('b', "MSFT", 2, new BigDecimal(150),
                new BigDecimal(300), 500);

        // Set input data
        BuySellInputData inputData = new BuySellInputData(priceState, 2, true);

        // Initialize output boundary
        TestBuySellOutputBoundary testBuySellOB = new TestBuySellOutputBoundary();

        // Interactor call
        BuySellInteractor interactor = new BuySellInteractor(testBuySellDB, testBuySellOB);
        interactor.execute(inputData);

        // Assert correct error message displayed
        assertEquals("Server Error", testBuySellOB.getResponseMessage());
    }

    @Test
    void testDefaultStatusCode() {

        // Sample price state
        PriceState priceState = new PriceState();

        // Set values for BuySellAccessObject double
        TestBuySellAccessObject testBuySellDB = new TestBuySellAccessObject();
        testBuySellDB.setValues('b', "MSFT", 2, new BigDecimal(150),
                new BigDecimal(300), 999);

        // Set input data
        BuySellInputData inputData = new BuySellInputData(priceState, 2, true);

        // Initialize output boundary
        TestBuySellOutputBoundary testBuySellOB = new TestBuySellOutputBoundary();

        // Interactor call
        BuySellInteractor interactor = new BuySellInteractor(testBuySellDB, testBuySellOB);

        // Test exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> { interactor.execute(inputData);});
        assertEquals("Unhandled status code in BuySellInteractor", exception.getMessage());
    }
}
