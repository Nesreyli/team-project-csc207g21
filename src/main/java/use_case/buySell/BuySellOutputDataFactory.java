package use_case.buySell;

import entity.BuySellReceipt;

public class BuySellOutputDataFactory {
    /**
     * Creates a  BuySellOutputData object based on the provided receipt.
     *
     * @param receipt the domain receipt containing order execution details
     * @return a populated  BuySellOutputData representing the results
     *         of the buy or sell operation
     */
    public static BuySellOutputData create(BuySellReceipt receipt) {
        return new BuySellOutputData(receipt.getOrder(), receipt.getSymbol(),
                receipt.getAmount(), receipt.getPrice(), receipt.getTotalPrice());
    }
}
