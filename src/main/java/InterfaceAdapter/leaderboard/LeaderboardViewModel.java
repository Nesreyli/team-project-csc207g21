package InterfaceAdapter.leaderboard;

import InterfaceAdapter.ViewModel;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Immutable, UI-facing state of portfolio.
 */
public class LeaderboardViewModel extends ViewModel<LeaderboardViewModel.LeaderboardUIState> {

    public LeaderboardViewModel() {
        super("leaderboard");
        setState(new LeaderboardUIState(
                Map.of(),
                Map.of()
        ));
    }

    public record LeaderboardUIState(
            Map<String, Object> rankedUsers,
            Map<String, Object> userValues
    ) {}

    public record LeaderboardHistoryEntry(String date, BigDecimal value) {}
}
