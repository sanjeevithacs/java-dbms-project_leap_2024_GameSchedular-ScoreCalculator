CREATE DATABASE GameSchedulerDB;

USE GameSchedulerDB;

CREATE TABLE Leagues (
    league_id INT PRIMARY KEY AUTO_INCREMENT,
    league_name VARCHAR(50),
    format VARCHAR(20)
);

CREATE TABLE Teams (
    team_id INT PRIMARY KEY AUTO_INCREMENT,
    team_name VARCHAR(50),
    league_id INT,
    FOREIGN KEY (league_id) REFERENCES Leagues(league_id)
);

CREATE TABLE Matches (
    match_id INT PRIMARY KEY AUTO_INCREMENT,
    team1_id INT,
    team2_id INT,
    match_date DATE,
    match_time TIME,
    FOREIGN KEY (team1_id) REFERENCES Teams(team_id),
    FOREIGN KEY (team2_id) REFERENCES Teams(team_id)
);

CREATE TABLE Results (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    match_id INT,
    team1_score INT,
    team2_score INT,
    winning_team_id INT,
    FOREIGN KEY (match_id) REFERENCES Matches(match_id),
    FOREIGN KEY (winning_team_id) REFERENCES Teams(team_id)
);

CREATE TABLE Standings (
    standing_id INT PRIMARY KEY AUTO_INCREMENT,
    team_id INT,
    league_id INT,
    matches_played INT DEFAULT 0,
    wins INT DEFAULT 0,
    losses INT DEFAULT 0,
    draws INT DEFAULT 0,
    points INT DEFAULT 0,
    FOREIGN KEY (team_id) REFERENCES Teams(team_id),
    FOREIGN KEY (league_id) REFERENCES Leagues(league_id)
);


INSERT INTO Leagues (league_name, format) VALUES
('Premier League', 'Round-robin'),
('Champions League', 'Knockout'),
('La Liga', 'Round-robin');

INSERT INTO Teams (team_name, league_id) VALUES
('Team A', 1),
('Team B', 1),
('Team C', 1),
('Team D', 2),
('Team E', 2),
('Team F', 2),
('Team G', 3),
('Team H', 3),
('Team I', 3);

INSERT INTO Matches (team1_id, team2_id, match_date, match_time) VALUES
(1, 2, '2024-11-15', '14:00:00'),
(3, 1, '2024-11-16', '16:00:00'),
(4, 5, '2024-11-17', '15:30:00'),
(6, 4, '2024-11-18', '18:00:00'),
(7, 8, '2024-11-19', '13:00:00'),
(9, 7, '2024-11-20', '17:30:00');

INSERT INTO Results (match_id, team1_score, team2_score, winning_team_id) VALUES
(1, 2, 1, 1),  -- Team A vs Team B, Team A wins
(2, 0, 3, 3),  -- Team C vs Team A, Team C wins
(3, 1, 1, NULL), -- Team D vs Team E, Draw
(4, 2, 0, 6),  -- Team F vs Team D, Team F wins
(5, 1, 3, 8),  -- Team G vs Team H, Team H wins
(6, 4, 2, 7);  -- Team I vs Team G, Team G wins

INSERT INTO Standings (team_id, league_id, matches_played, wins, losses, draws, points) VALUES
(1, 1, 2, 1, 1, 0, 3),  -- Team A has 1 win, 1 loss
(2, 1, 1, 0, 1, 0, 0),  -- Team B has 1 loss
(3, 1, 1, 1, 0, 0, 3),  -- Team C has 1 win
(4, 2, 2, 0, 1, 1, 1),  -- Team D has 1 loss, 1 draw
(5, 2, 1, 0, 0, 1, 1),  -- Team E has 1 draw
(6, 2, 1, 1, 0, 0, 3),  -- Team F has 1 win
(7, 3, 2, 1, 1, 0, 3),  -- Team G has 1 win, 1 loss
(8, 3, 1, 1, 0, 0, 3),  -- Team H has 1 win
(9, 3, 1, 0, 1, 0, 0);  -- Team I has 1 loss

select * from Leagues;
select * from Teams;
select * from Matches;
select * from Results;
select * from Standings;