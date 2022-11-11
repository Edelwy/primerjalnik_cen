INSERT INTO uporabnik (ime, priimek, username) VALUES ('Miha', 'Novak', 'mihanovak');

INSERT INTO primerjalnik (stevilo) VALUES (2);

INSERT INTO trgovina (ime) VALUES ('Amazon');

INSERT INTO produkt (ime, opis, cena, trgovina_id) VALUES ('Namizna Lucka', 'Namizna svetilka rdece barve.', 20, 1);
INSERT INTO produkt (ime, opis, cena, trgovina_id) VALUES ('Crnilo', 'Nalivno crnilo modre barve.', 5, 1);
INSERT INTO produkt (ime, opis, cena, trgovina_id) VALUES ('Krilo', 'Zeleno krilo do kolen.', 30, 1);
INSERT INTO produkt (ime, opis, cena, trgovina_id) VALUES ('Kavbojke', 'Vintage Mom Jeans.', 45, 1);

INSERT INTO kosarica (kolicina, postnina, popust, uporabnik_id, primerjalnik_id, trgovina_id) VALUES (1, 15, 30, 1, 1, 1);




