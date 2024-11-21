//package scoring;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import database.DatabaseConnection;
//
//public class ScoreCalculator {
//
//    public void recordMatchResult(int matchId, int team1Score, int team2Score, int winningTeamId) {
//        String sql = "INSERT INTO Results (match_id, team1_score, team2_score, winning_team_id) VALUES (?, ?, ?, ?)";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, matchId);
//            pstmt.setInt(2, team1Score);
//            pstmt.setInt(3, team2Score);
//            pstmt.setInt(4, winningTeamId);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}


package scoring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseConnection;

public class ScoreCalculator {

    // Method to record match results
    public void recordMatchResult(int matchId, int team1Score, int team2Score, Integer winningTeamId) {
        String insertResultSql = "INSERT INTO Results (match_id, team1_score, team2_score, winning_team_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertResultSql)) {
            
            pstmt.setInt(1, matchId);
            pstmt.setInt(2, team1Score);
            pstmt.setInt(3, team2Score);
            if (winningTeamId != null) {
                pstmt.setInt(4, winningTeamId);
            } else {
                pstmt.setNull(4, java.sql.Types.INTEGER); // For draw case
            }
            pstmt.executeUpdate();
            
            // Update standings based on result
            updateStandings(matchId, team1Score, team2Score, winningTeamId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update standings
    private void updateStandings(int matchId, int team1Score, int team2Score, Integer winningTeamId) {
        String selectTeamsSql = "SELECT team1_id, team2_id FROM Matches WHERE match_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectTeamsStmt = conn.prepareStatement(selectTeamsSql)) {
            
            selectTeamsStmt.setInt(1, matchId);
            ResultSet rs = selectTeamsStmt.executeQuery();

            if (rs.next()) {
                int team1Id = rs.getInt("team1_id");
                int team2Id = rs.getInt("team2_id");
                
                // Update standings for both teams
                updateTeamStandings(conn, team1Id, team1Score, team2Score, winningTeamId);
                updateTeamStandings(conn, team2Id, team2Score, team1Score, winningTeamId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTeamStandings(Connection conn, int teamId, int teamScore, int opponentScore, Integer winningTeamId) throws SQLException {
        String updateStandingsSql = "UPDATE Standings SET matches_played = matches_played + 1, "
                + "wins = wins + ?, losses = losses + ?, draws = draws + ?, points = points + ? "
                + "WHERE team_id = ?";
        
        int win = (winningTeamId != null && winningTeamId == teamId) ? 1 : 0;
        int loss = (winningTeamId != null && winningTeamId != teamId) ? 1 : 0;
        int draw = (winningTeamId == null) ? 1 : 0;
        int points = win * 3 + draw;

        try (PreparedStatement pstmt = conn.prepareStatement(updateStandingsSql)) {
            pstmt.setInt(1, win);
            pstmt.setInt(2, loss);
            pstmt.setInt(3, draw);
            pstmt.setInt(4, points);
            pstmt.setInt(5, teamId);
            pstmt.executeUpdate();
        }
    }
}