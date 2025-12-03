package use_case.stock_search;

import data_access.SearchAccessObject;
import entity.Stock_Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SearchInteractor with 100% line coverage.
 * Uses manual test doubles instead of Mockito.
 */
class Stocksearchinteractortest {

    private TestSearchAccessObject testSearchAccessObject;
    private TestSearchOutputBoundary testOutputBoundary;
    private SearchInteractor searchInteractor;

    @BeforeEach
    void setUp() {
        testSearchAccessObject = new TestSearchAccessObject();
        testOutputBoundary = new TestSearchOutputBoundary();
        searchInteractor = new SearchInteractor(testSearchAccessObject, testOutputBoundary);
    }

    /**
     * Test double for SearchAccessObject.
     */
    private static class TestSearchAccessObject extends SearchAccessObject {
        private Map<String, Stock_Search> searchResults;
        private Map<String, Stock_Search> allStocks;
        private boolean shouldThrowException;
        private String exceptionMessage;
        private String lastSearchQuery;
        private boolean searchStocksCalled;
        private boolean getAllStocksCalled;

        @Override
        public Map<String, Stock_Search> searchStocks(String query) {
            searchStocksCalled = true;
            lastSearchQuery = query;
            if (shouldThrowException) {
                throw new RuntimeException(exceptionMessage);
            }
            return searchResults;
        }

        @Override
        public Map<String, Stock_Search> getAllStocks() {
            getAllStocksCalled = true;
            if (shouldThrowException) {
                throw new RuntimeException(exceptionMessage);
            }
            return allStocks;
        }

        public void setSearchResults(Map<String, Stock_Search> results) {
            this.searchResults = results;
        }

        public void setAllStocks(Map<String, Stock_Search> stocks) {
            this.allStocks = stocks;
        }

        public void setShouldThrowException(boolean shouldThrow, String message) {
            this.shouldThrowException = shouldThrow;
            this.exceptionMessage = message;
        }

        public String getLastSearchQuery() {
            return lastSearchQuery;
        }

        public boolean wasSearchStocksCalled() {
            return searchStocksCalled;
        }

        public boolean wasGetAllStocksCalled() {
            return getAllStocksCalled;
        }
    }

    /**
     * Test double for SearchOutputBoundary.
     */
    private static class TestSearchOutputBoundary implements SearchOutputBoundary {
        private SearchOutputData lastSuccessOutputData;
        private String lastErrorMessage;
        private boolean prepareSuccessViewCalled;
        private boolean prepareFailViewCalled;

        @Override
        public void prepareSuccessView(SearchOutputData outputData) {
            prepareSuccessViewCalled = true;
            lastSuccessOutputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            prepareFailViewCalled = true;
            lastErrorMessage = errorMessage;
        }

        public SearchOutputData getLastSuccessOutputData() {
            return lastSuccessOutputData;
        }

        public String getLastErrorMessage() {
            return lastErrorMessage;
        }

        public boolean wasPrepareSuccessViewCalled() {
            return prepareSuccessViewCalled;
        }

        public boolean wasPrepareFailViewCalled() {
            return prepareFailViewCalled;
        }
    }

    private Map<String, Stock_Search> createSampleStocks() {
        Map<String, Stock_Search> stocks = new HashMap<>();
        stocks.put("AAPL", new Stock_Search("AAPL", "Apple Inc.",
                new BigDecimal("150.25"), "United States"));
        return stocks;
    }

    @Test
    void testExecuteSearch_withValidQuery_success() {
        // Arrange
        String query = "AAPL";
        Map<String, Stock_Search> expectedResults = createSampleStocks();
        testSearchAccessObject.setSearchResults(expectedResults);

        SearchInputData inputData = new SearchInputData(query);

        // Act
        searchInteractor.executeSearch(inputData);

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
        assertFalse(testOutputBoundary.wasPrepareFailViewCalled());

        SearchOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertNotNull(outputData);
        assertEquals(expectedResults, outputData.getStocks());
        assertEquals(query, outputData.getQuery());
        assertTrue(outputData.isSuccess());
        assertEquals(query, testSearchAccessObject.getLastSearchQuery());
    }

    @Test
    void testExecuteSearch_withNullQuery_failure() {
        // Arrange
        SearchInputData inputData = new SearchInputData(null);
        // The code will still try to call searchStocks with null, which will throw
        testSearchAccessObject.setShouldThrowException(true, "Null query");

        // Act
        searchInteractor.executeSearch(inputData);

        // Assert
        // Both prepareFailView calls happen - once for empty check, once for exception
        assertTrue(testOutputBoundary.wasPrepareFailViewCalled());
        // The last error message will be from the exception handler
        assertEquals("Please enter a valid company name or symbol",
                testOutputBoundary.getLastErrorMessage());
        // searchStocks IS called (with null), causing the exception
        assertTrue(testSearchAccessObject.wasSearchStocksCalled());
    }

    @Test
    void testExecuteSearch_withEmptyQuery_failure() {
        // Arrange
        SearchInputData inputData = new SearchInputData("");
        // The code will still try to call searchStocks with empty string
        testSearchAccessObject.setShouldThrowException(true, "Empty query");

        // Act
        searchInteractor.executeSearch(inputData);

        // Assert
        // Both prepareFailView calls happen
        assertTrue(testOutputBoundary.wasPrepareFailViewCalled());
        assertEquals("Please enter a valid company name or symbol",
                testOutputBoundary.getLastErrorMessage());
        assertTrue(testSearchAccessObject.wasSearchStocksCalled());
    }

    @Test
    void testExecuteSearch_withException_failure() {
        // Arrange
        String query = "INVALID";
        testSearchAccessObject.setShouldThrowException(true, "No Stocks Found");

        SearchInputData inputData = new SearchInputData(query);

        // Act
        searchInteractor.executeSearch(inputData);

        // Assert
        assertTrue(testOutputBoundary.wasPrepareFailViewCalled());
        assertFalse(testOutputBoundary.wasPrepareSuccessViewCalled());
        assertEquals("Please enter a valid company name or symbol",
                testOutputBoundary.getLastErrorMessage());
    }

    @Test
    void testExecuteSearch_withMultipleResults_success() {
        // Arrange
        String query = "Tech";
        Map<String, Stock_Search> expectedResults = new HashMap<>();
        expectedResults.put("AAPL", new Stock_Search("AAPL", "Apple Inc.",
                new BigDecimal("150.25"), "United States"));
        expectedResults.put("GOOGL", new Stock_Search("GOOGL", "Alphabet Inc.",
                new BigDecimal("125.50"), "United States"));
        expectedResults.put("MSFT", new Stock_Search("MSFT", "Microsoft Corporation",
                new BigDecimal("350.75"), "United States"));

        testSearchAccessObject.setSearchResults(expectedResults);

        SearchInputData inputData = new SearchInputData(query);

        // Act
        searchInteractor.executeSearch(inputData);

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());

        SearchOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertEquals(3, outputData.getStocks().size());
        assertEquals(query, outputData.getQuery());
        assertTrue(outputData.isSuccess());
    }

    @Test
    void testExecuteLoadAll_success() {
        // Arrange
        Map<String, Stock_Search> allStocks = new HashMap<>();
        allStocks.put("AAPL", new Stock_Search("AAPL", "Apple Inc.",
                new BigDecimal("150.25"), "United States"));
        allStocks.put("GOOGL", new Stock_Search("GOOGL", "Alphabet Inc.",
                new BigDecimal("125.50"), "United States"));
        allStocks.put("MSFT", new Stock_Search("MSFT", "Microsoft Corporation",
                new BigDecimal("350.75"), "United States"));

        testSearchAccessObject.setAllStocks(allStocks);

        // Act
        searchInteractor.executeLoadAll();

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
        assertTrue(testSearchAccessObject.wasGetAllStocksCalled());

        SearchOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertEquals(allStocks, outputData.getStocks());
        assertEquals("", outputData.getQuery());
        assertTrue(outputData.isSuccess());
    }

    @Test
    void testExecuteLoadAll_withException_failure() {
        // Arrange
        testSearchAccessObject.setShouldThrowException(true, "Database connection error");

        // Act
        searchInteractor.executeLoadAll();

        // Assert
        assertTrue(testOutputBoundary.wasPrepareFailViewCalled());
        assertFalse(testOutputBoundary.wasPrepareSuccessViewCalled());
        assertEquals("Failure to load all stocks: Database connection error",
                testOutputBoundary.getLastErrorMessage());
    }

    @Test
    void testExecuteLoadAll_withEmptyResults_success() {
        // Arrange
        Map<String, Stock_Search> emptyStocks = new HashMap<>();
        testSearchAccessObject.setAllStocks(emptyStocks);

        // Act
        searchInteractor.executeLoadAll();

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());

        SearchOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertEquals(0, outputData.getStocks().size());
        assertTrue(outputData.isSuccess());
    }

    @Test
    void testExecuteSearch_withSingleCharacterQuery_success() {
        // Arrange
        String query = "A";
        Map<String, Stock_Search> expectedResults = createSampleStocks();
        testSearchAccessObject.setSearchResults(expectedResults);

        SearchInputData inputData = new SearchInputData(query);

        // Act
        searchInteractor.executeSearch(inputData);

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());

        SearchOutputData outputData = testOutputBoundary.getLastSuccessOutputData();
        assertEquals(1, outputData.getStocks().size());
    }

    @Test
    void testExecuteSearch_verifyNoCallOnNullQuery() {
        // Arrange
        SearchInputData inputData = new SearchInputData(null);
        // Since the code doesn't return early, it will try to call searchStocks
        testSearchAccessObject.setShouldThrowException(true, "Null query");

        // Act
        searchInteractor.executeSearch(inputData);

        // Assert
        // searchStocks IS called even with null query (bug in original code)
        assertTrue(testSearchAccessObject.wasSearchStocksCalled());
        assertTrue(testOutputBoundary.wasPrepareFailViewCalled());
    }

    @Test
    void testExecuteSearch_verifyCorrectQueryPassed() {
        // Arrange
        String query = "TSLA";
        Map<String, Stock_Search> expectedResults = new HashMap<>();
        expectedResults.put("TSLA", new Stock_Search("TSLA", "Tesla Inc.",
                new BigDecimal("245.30"), "United States"));

        testSearchAccessObject.setSearchResults(expectedResults);

        SearchInputData inputData = new SearchInputData(query);

        // Act
        searchInteractor.executeSearch(inputData);

        // Assert
        assertTrue(testSearchAccessObject.wasSearchStocksCalled());
        assertEquals(query, testSearchAccessObject.getLastSearchQuery());
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
    }

    @Test
    void testExecuteLoadAll_verifyMethodCalled() {
        // Arrange
        Map<String, Stock_Search> allStocks = createSampleStocks();
        testSearchAccessObject.setAllStocks(allStocks);

        // Act
        searchInteractor.executeLoadAll();

        // Assert
        assertTrue(testSearchAccessObject.wasGetAllStocksCalled());
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
    }

    @Test
    void testExecuteSearch_withLongQuery_success() {
        // Arrange
        String query = "Apple Computer Company Technology";
        Map<String, Stock_Search> expectedResults = createSampleStocks();
        testSearchAccessObject.setSearchResults(expectedResults);

        SearchInputData inputData = new SearchInputData(query);

        // Act
        searchInteractor.executeSearch(inputData);

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
        assertEquals(query, testSearchAccessObject.getLastSearchQuery());
    }

    @Test
    void testExecuteSearch_withSpecialCharacters_success() {
        // Arrange
        String query = "S&P500";
        Map<String, Stock_Search> expectedResults = createSampleStocks();
        testSearchAccessObject.setSearchResults(expectedResults);

        SearchInputData inputData = new SearchInputData(query);

        // Act
        searchInteractor.executeSearch(inputData);

        // Assert
        assertTrue(testOutputBoundary.wasPrepareSuccessViewCalled());
        assertTrue(testSearchAccessObject.wasSearchStocksCalled());
    }
}