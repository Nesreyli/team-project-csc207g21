package InterfaceAdapter.portfolio;

import InterfaceAdapter.ViewModel;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class PortfolioViewModel extends ViewModel<PortfolioViewModel.PortfolioUIState> {

    public PortfolioViewModel() {
        super("portfolio");
        setState(new PortfolioUIState(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                Map.of()
        ));
    }

    public record PortfolioUIState(
            BigDecimal cashBalance,
            BigDecimal totalPortfolioValue,
            BigDecimal performancePercentage,
            Map<String, Object> holdings
    ) {
    }
}
