use iot_the_operator;

select * from weather1 where deviceid = 6 order by time desc;

SELECT * FROM weather1 WHERE deviceid = 6 order by id desc limit 1;

select * from weather1 order by id desc;


use movie_ratings;

show tables;
desc movies;
desc ratings;
desc users;

select * from movies;

update movies set info = "IMDB: 8.2\nKinobesuch: 10.11.2017" where id = 1;


INSERT INTO movies (title, genres, releasedate, info, image, imdb, watched) VALUES ("Ready Player One", "Thriller, Fantasy, Cyberpunk", DATE("2018-03-28"), "", "http://de.web.img3.acsta.net/c_215_290/pictures/18/03/01/11/11/3183593.jpg", 7.9, DATE("2018-04-06"));

SELECT id, title, genres, DATE_FORMAT(releasedate, '%Y-%m-%d') as releasedate, info, image, imdb, DATE_FORMAT(watched, '%Y-%m-%d') as watchdate from movies order by watched desc;

select * from users;
desc users;

SELECT password, apikey FROM users WHERE username = "gas";


SELECT password, apikey FROM users WHERE username = 'peta';

select * from movies;
select * from ratings;
select * from users;

insert into ratings (movie_id, user_id, story, writing, music, actors, effects, camera, entertaining, overall, expectedoverall) values (2, 1, 1, 1, 1, 1, 9, 9, 1, 1, 9); 

-- movie list + average rating maybe not working completely with more movies
SELECT movies.id, title, genres, DATE_FORMAT(releasedate, '%Y-%m-%d') AS releasedate, info, image, imdb, DATE_FORMAT(watched, '%Y-%m-%d') AS watchdate, TRUNCATE((AVG(story) + AVG(writing) + AVG(music) + AVG(actors) + AVG(effects) + AVG(camera) + AVG(entertaining)) / 7, 1) AS averagerating FROM movies INNER JOIN ratings ON movies.id = ratings.movie_id ORDER BY watched DESC;

-- average ratings for a movie

SELECT movies.id, AVG(story) AS story, AVG(writing) AS writing, AVG(music) AS music, AVG(actors) AS acting, AVG(effects) AS effects, AVG(camera) AS camera, AVG(entertaining) AS entertaining, AVG(overall) AS overall, AVG(expectedoverall) AS expectedoverall FROM movies INNER JOIN ratings ON movies.id = ratings.movie_id;








