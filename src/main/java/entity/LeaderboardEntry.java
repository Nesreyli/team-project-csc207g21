package entity;

import java.math.BigDecimal;

/**
 * A simple Entity representing a single leaderboard entry.
 * Each entry contains a user's rank, username, and portfolio value.
 */
public class LeaderboardEntry {
    private Integer rank;
    private String username;
    private BigDecimal portfolioValue;

    /**
     * Creates a new leaderboard entry with the given rank, username, and portfolio value.
     * @param rank the user's rank in the leaderboard
     * @param username the username
     * @param portfolioValue the user's portfolio value
     */
    public LeaderboardEntry(Integer rank, String username, BigDecimal portfolioValue) {
        this.rank = rank;
        this.username = username;
        this.portfolioValue = portfolioValue;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(BigDecimal portfolioValue) {
        this.portfolioValue = portfolioValue;
    }
}

