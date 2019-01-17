INSERT INTO author(id, name) VALUES(1, 'Author #1');
INSERT INTO author(id, name) VALUES(2, 'Author #2');
INSERT INTO genre(id, name) VALUES(1, 'Genre #1');
INSERT INTO genre(id, name) VALUES(2, 'Genre #2');
INSERT INTO book(id, name, author_id, genre_id) VALUES(1, 'Book #1', 1, 1);
INSERT INTO book(id, name, author_id, genre_id) VALUES(2, 'Book #2', 2, 2);
INSERT INTO review(id, author, body, book_id) VALUES(1, 'Troll #1', 'Looser', 1);
INSERT INTO review(id, author, body, book_id) VALUES(2, 'Troll #2', 'Sic!', 2);