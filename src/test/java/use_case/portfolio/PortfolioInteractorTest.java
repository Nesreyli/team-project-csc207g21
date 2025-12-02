package use_case.portfolio;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.stock_price.PriceAccessInterface;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioInteractorTest {
    private TestPortfolioDb testPortfolioDb;
    private TestPriceDb testPriceDb;
    private PortfolioInteractor portfolioInteractor;
    private TestPortfolioOutputBoundary testPortfolioOutputBoundary;

    @BeforeEach
    void setUp() {
        testPortfolioDb = new TestPortfolioDb();
        testPriceDb = new TestPriceDb();
        testPortfolioOutputBoundary = new TestPortfolioOutputBoundary();
        portfolioInteractor = new PortfolioInteractor(
                                    testPortfolioDb,
                                    testPriceDb,
                                    testPortfolioOutputBoundary);
    }

    private class TestPortfolioDb implements PortfolioAccessInterface {
        private Portfolio portMap;
        private boolean shouldThrowException;
        private boolean shouldThrowServerException;
        private boolean shouldThrowUnkownException;

        private boolean  getPortCalled;
        private String exceptionMessage;


        @Override
        public Response getPort(String username, String password) {
            getPortCalled = true;
            if (shouldThrowException){
                return new Response(400, portMap);
            }
            if (shouldThrowServerException){
                return new Response(500, portMap);
            }
            if (shouldThrowUnkownException){
                return new Response(404, portMap);
            }
            return new Response(200, portMap);
        }

        public Portfolio getPortMap() {
            return portMap;
        }

        public void setPortMap(Portfolio portMap) {
            this.portMap = portMap;
        }

        public boolean isShouldThrowException() {
            return shouldThrowException;
        }

        public void setShouldThrowException(boolean shouldThrowException) {
            this.shouldThrowException = shouldThrowException;
        }

        public boolean isGetPortCalled() {
            return getPortCalled;
        }

        public void setGetPortCalled(boolean getPortCalled) {
            this.getPortCalled = getPortCalled;
        }

        public boolean isShouldThrowServerException() {
            return shouldThrowServerException;
        }

        public void setShouldThrowServerException(boolean shouldThrowServerException) {
            this.shouldThrowServerException = shouldThrowServerException;
        }

        public boolean isShouldThrowUnkownException() {
            return shouldThrowUnkownException;
        }

        public void setShouldThrowUnkownException(boolean shouldThrowUnkownException) {
            this.shouldThrowUnkownException = shouldThrowUnkownException;
        }

        public String getExceptionMessage() {
            return exceptionMessage;
        }

        public void setExceptionMessage(String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
        }
    }

    private class TestPriceDb implements PriceAccessInterface {
        private Map<String, Price> price;
        private boolean shouldThrowException;
        private boolean  getPriceCalled;

        @Override
        public Response getPrice(String symbol) {
            getPriceCalled = true;
            if (shouldThrowException){
                return new Response(400, price.get(symbol));
            }
            return new Response(200, price.get(symbol));
        }

        public Map<String, Price> getPrice() {
            return price;
        }

        public void setPrice(Map<String, Price> price) {
            this.price = price;
        }

        public boolean isShouldThrowException() {
            return shouldThrowException;
        }

        public void setShouldThrowException(boolean shouldThrowException) {
            this.shouldThrowException = shouldThrowException;
        }

        public boolean isGetPriceCalled() {
            return getPriceCalled;
        }

        public void setGetPriceCalled(boolean getPriceCalled) {
            this.getPriceCalled = getPriceCalled;
        }
    }

    private class TestPortfolioOutputBoundary implements PortfolioOutputBoundary {
        private PortfolioOutputData portfolioOutputData;
        private String exceptionMessage;
        private boolean prepareSuccessViewCalled;
        private boolean prepareFailViewCalled;

        @Override
        public void preparePortSuccessView(PortfolioOutputData outputData) {
            prepareSuccessViewCalled = true;
            portfolioOutputData = outputData;
        }

        @Override
        public void preparePortFailView(String errorMessage) {
            prepareFailViewCalled = true;
            exceptionMessage = errorMessage;
        }
    }

    private Portfolio createSamplePort(){
        Map<String, Object> portfolio = new HashMap<>();
        portfolio.put("AAPL", 1);
        Portfolio portMap = new Portfolio.Builder()
                .value(new BigDecimal(100))
                .cash(new BigDecimal(100))
                .holdings(portfolio)
                .user(new User("Hello", "world"))
                .performance(new BigDecimal(10))
                .build();
        return portMap;
    }

    private Map<String, Price> createSamplePrices(){
        Map<String, Price> priceList = new HashMap<>();
        priceList.put("AAPL", new Price.Builder()
                .addPrice(new BigDecimal(100))
                .addYtdPrice(new BigDecimal(100))
                .addYtdPerformance(new BigDecimal(10))
                .addSymbol("AAPL").build());
        return priceList;
    }

    @Test
    void textExecutionTest() {
        String username = "Hello";
        String password = "world";
        Portfolio portfolio = createSamplePort();
        Map<String, Price> prices = createSamplePrices();
        testPortfolioDb.portMap = portfolio;
        testPriceDb.price = prices;
        PortfolioInputData portfolioInputData = new PortfolioInputData(username, password);

        portfolioInteractor.execute(portfolioInputData);

        assertTrue(testPortfolioOutputBoundary.prepareSuccessViewCalled);
        assertFalse(testPortfolioOutputBoundary.prepareFailViewCalled);

    }

    @Test
    void testIncorrectName() {
        String username = "H";
        String password = "w";
        Portfolio portfolio = createSamplePort();
        Map<String, Price> prices = createSamplePrices();
        testPortfolioDb.portMap = portfolio;
        testPriceDb.price = prices;
        testPortfolioDb.setShouldThrowException(true);
        PortfolioInputData portfolioInputData = new PortfolioInputData(username, password);

        portfolioInteractor.execute(portfolioInputData);

        assertFalse(testPortfolioOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testPortfolioOutputBoundary.prepareFailViewCalled);
        assertTrue(testPortfolioOutputBoundary.exceptionMessage.equals("Incorrect username or password."));
    }

    @Test
    void testServerError() {
        String username = "H";
        String password = "w";
        Portfolio portfolio = createSamplePort();
        Map<String, Price> prices = createSamplePrices();
        testPortfolioDb.portMap = portfolio;
        testPriceDb.price = prices;
        testPortfolioDb.setShouldThrowServerException(true);
        PortfolioInputData portfolioInputData = new PortfolioInputData(username, password);

        portfolioInteractor.execute(portfolioInputData);

        assertFalse(testPortfolioOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testPortfolioOutputBoundary.prepareFailViewCalled);
        assertTrue(testPortfolioOutputBoundary.exceptionMessage.equals("Server Error"));
    }

    @Test
    void testUnkownError() {
        String username = "H";
        String password = "w";
        Portfolio portfolio = createSamplePort();
        Map<String, Price> prices = createSamplePrices();
        testPortfolioDb.portMap = portfolio;
        testPriceDb.price = prices;
        testPortfolioDb.setShouldThrowUnkownException(true);
        PortfolioInputData portfolioInputData = new PortfolioInputData(username, password);

        portfolioInteractor.execute(portfolioInputData);

        assertFalse(testPortfolioOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testPortfolioOutputBoundary.prepareFailViewCalled);
        assertTrue(testPortfolioOutputBoundary.exceptionMessage.equals("Unkown Error"));
    }

    @Test
    void testSymbolError() {
        String username = "H";
        String password = "w";
        Portfolio portfolio = createSamplePort();
        Map<String, Price> prices = createSamplePrices();
        testPortfolioDb.portMap = portfolio;
        testPriceDb.price = prices;
        testPriceDb.setShouldThrowException(true);
        PortfolioInputData portfolioInputData = new PortfolioInputData(username, password);

        portfolioInteractor.execute(portfolioInputData);

        assertFalse(testPortfolioOutputBoundary.prepareSuccessViewCalled);
        assertTrue(testPortfolioOutputBoundary.prepareFailViewCalled);
        assertTrue(testPortfolioOutputBoundary.exceptionMessage.equals("Incorrect username or password."));
    }

    @Test
    void testOutputData() {
        String username = "Hello";
        String password = "world";
        Portfolio portfolio = createSamplePort();
        Map<String, Price> prices = createSamplePrices();
        testPortfolioDb.portMap = portfolio;
        testPriceDb.price = prices;
        PortfolioInputData portfolioInputData = new PortfolioInputData(username, password);

        portfolioInteractor.execute(portfolioInputData);
        Map<String, BigDecimal> performanceStock = new HashMap<>();
        performanceStock.put("AAPL", new BigDecimal(10));
        Map<String, BigDecimal> pricesStock = new HashMap<>();
        pricesStock.put("AAPL", new BigDecimal(100));
        assertTrue(testPortfolioOutputBoundary.prepareSuccessViewCalled);
        assertFalse(testPortfolioOutputBoundary.prepareFailViewCalled);
        assertTrue(testPortfolioOutputBoundary.portfolioOutputData.getCash().equals(new BigDecimal(100)));
        assertTrue(testPortfolioOutputBoundary.portfolioOutputData.getPassword().equals("world"));
        assertTrue(testPortfolioOutputBoundary.portfolioOutputData.getUsername().equals("Hello"));
        assertTrue(testPortfolioOutputBoundary.portfolioOutputData.getValue().equals(new BigDecimal(100)));
        assertTrue(testPortfolioOutputBoundary.portfolioOutputData.getHoldings().equals(portfolio.getHoldings()));
        assertTrue(testPortfolioOutputBoundary.portfolioOutputData.getPerformance().equals(new BigDecimal(10)));
        assertTrue(testPortfolioOutputBoundary.portfolioOutputData.getPrices().equals(pricesStock));
        assertTrue(testPortfolioOutputBoundary.portfolioOutputData.getStockPerformance().equals(performanceStock));
    }
}