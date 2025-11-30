package use_case.buySell;

import entity.BuySellReceipt;

public class BuySellOutputDataFactory {
    public static BuySellOutputData create(BuySellReceipt receipt) {
        return new BuySellOutputData(receipt.getOrder(), receipt.getSymbol(),
                receipt.getAmount(), receipt.getPrice(), receipt.getTotalPrice());
    }
}
