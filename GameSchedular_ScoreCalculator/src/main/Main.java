package main;

import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;
import scheduler.LeagueManager;
import scheduler.TeamManager;
import scheduler.MatchScheduler;
import scoring.ScoreCalculator;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LeagueManager leagueManager = new LeagueManager();
        TeamManager teamManager = new TeamManager();
        MatchScheduler matchScheduler = new MatchScheduler();
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        boolean running = true;

        while (running) {
        	 System.out.println("1. Add League");
             System.out.println("2. Add Team");
             System.out.println("3. Schedule Match");
             System.out.println("4. Record Match Result");
             System.out.println("5. Display League Standings");
             System.out.println("6. Display Match Schedule");
             System.out.println("7. View Registered Leagues and Teams");
             System.out.println("8. Display All Leagues");
             System.out.println("9. Display All Teams");
             System.out.println("10. Exit");
             System.out.print("Choose an option: ");


            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter League Name: ");
                    String leagueName = scanner.nextLine();
                    System.out.print("Enter League Format (e.g., Round-robin, Knockout): ");
                    String format = scanner.nextLine();
                    leagueManager.addLeague(leagueName, format);
                    System.out.println("League added successfully.");
                    break;

                case 2:
                    System.out.print("Enter Team Name: ");
                    String teamName = scanner.nextLine();
                    System.out.print("Enter League ID: ");
                    int leagueId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    teamManager.addTeam(teamName, leagueId);
                    System.out.println("Team added successfully.");
                    break;

                case 3:
                    System.out.print("Enter Team 1 ID: ");
                    int team1Id = scanner.nextInt();
                    System.out.print("Enter Team 2 ID: ");
                    int team2Id = scanner.nextInt();
                    System.out.print("Enter Match Date (yyyy-mm-dd): ");
                    String dateInput = scanner.next();
                    Date matchDate = Date.valueOf(dateInput);
                    System.out.print("Enter Match Time (HH:mm:ss): ");
                    String timeInput = scanner.next();
                    Time matchTime = Time.valueOf(timeInput);
                    matchScheduler.scheduleMatch(team1Id, team2Id, matchDate, matchTime);
                    System.out.println("Match scheduled successfully.");
                    break;

                case 4:
                    System.out.print("Enter Match ID: ");
                    int matchId = scanner.nextInt();
                    System.out.print("Enter Team 1 ID: ");
                    int team1Id1 = scanner.nextInt();
                    System.out.print("Enter Team 2 ID: ");
                    int team2Id1 = scanner.nextInt();
                    System.out.print("Enter Team 1 Score: ");
                    int team1Score = scanner.nextInt();
                    System.out.print("Enter Team 2 Score: ");
                    int team2Score = scanner.nextInt();

                    Integer winningTeamId;
                    if (team1Score > team2Score) {
                        winningTeamId = team1Id1;
                    } else if (team2Score > team1Score) {
                        winningTeamId = team2Id1;
                    } else {
                        winningTeamId = null; // Indicates a draw
                    }

                    scoreCalculator.recordMatchResult(matchId, team1Score, team2Score, winningTeamId);
                    System.out.println("Match result recorded successfully.");
                    break;

                case 5:
                    leagueManager.displayStandings();
                    break;

                case 6:
                    leagueManager.displaySchedule();
                    break;

                case 7:
                    leagueManager.viewLeaguesAndTeams();
                    break;

                case 8:
                    leagueManager.displayLeagues();
                    break;

                case 9:
                    leagueManager.displayTeams();
                    break;

                case 10:
                    System.out.println("Exiting the program.");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}