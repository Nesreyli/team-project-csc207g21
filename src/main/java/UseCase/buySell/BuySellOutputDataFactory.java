package UseCase.buySell;

import Entity.BuySellReceipt;

public class BuySellOutputDataFactory {
    public static BuySellOutputData create(BuySellReceipt receipt) {
        return new BuySellOutputData(receipt.getOrder(), receipt.getSymbol(),
                receipt.getAmount(), receipt.getPrice(), receipt.getTotalPrice());
    }
}
