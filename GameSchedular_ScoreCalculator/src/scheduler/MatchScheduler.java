package scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import database.DatabaseConnection;

public class MatchScheduler {

    public void scheduleMatch(int team1Id, int team2Id, Date date, Time time) {
        String sql = "INSERT INTO Matches (team1_id, team2_id, match_date, match_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, team1Id);
            pstmt.setInt(2, team2Id);
            pstmt.setDate(3, date);
            pstmt.setTime(4, time);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
