package scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseConnection;

public class LeagueManager {

    public void displayStandings() {
        String sql = "SELECT Teams.team_name, Standings.matches_played, Standings.wins, Standings.losses, Standings.draws, Standings.points " +
                     "FROM Standings JOIN Teams ON Standings.team_id = Teams.team_id " +
                     "ORDER BY Standings.points DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("League Standings:");
            while (rs.next()) {
                System.out.println("Team: " + rs.getString("team_name") +
                                   " | Matches Played: " + rs.getInt("matches_played") +
                                   " | Wins: " + rs.getInt("wins") +
                                   " | Losses: " + rs.getInt("losses") +
                                   " | Draws: " + rs.getInt("draws") +
                                   " | Points: " + rs.getInt("points"));
            }
        } catch (SQLException e) {
            System.err.println("Error displaying standings: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displaySchedule() {
        String sql = "SELECT Matches.match_id, Teams1.team_name AS team1, Teams2.team_name AS team2, Matches.match_date, Matches.match_time " +
                     "FROM Matches " +
                     "JOIN Teams AS Teams1 ON Matches.team1_id = Teams1.team_id " +
                     "JOIN Teams AS Teams2 ON Matches.team2_id = Teams2.team_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Match Schedule:");
            while (rs.next()) {
                System.out.println("Match ID: " + rs.getInt("match_id") +
                                   " | " + rs.getString("team1") + " vs " + rs.getString("team2") +
                                   " | Date: " + rs.getDate("match_date") +
                                   " | Time: " + rs.getTime("match_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewLeaguesAndTeams() {
        String sql = "SELECT Leagues.league_name, Teams.team_name " +
                     "FROM Leagues " +
                     "JOIN Teams ON Leagues.league_id = Teams.league_id " +
                     "ORDER BY Leagues.league_name, Teams.team_name";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Leagues and Teams:");
            while (rs.next()) {
                System.out.println("League: " + rs.getString("league_name") +
                                   " | Team: " + rs.getString("team_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addLeague(String name, String format) {
        String sql = "INSERT INTO Leagues (league_name, format) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, format);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void displayLeagues() {
        String sql = "SELECT league_id, league_name, format FROM Leagues";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Leagues:");
            while (rs.next()) {
                System.out.println("League ID: " + rs.getInt("league_id") +
                                   " | Name: " + rs.getString("league_name") +
                                   " | Format: " + rs.getString("format"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void displayTeams() {
        String sql = "SELECT team_id, team_name, league_id FROM Teams";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Teams:");
            while (rs.next()) {
                System.out.println("Team ID: " + rs.getInt("team_id") +
                                   " | Name: " + rs.getString("team_name") +
                                   " | League ID: " + rs.getInt("league_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
