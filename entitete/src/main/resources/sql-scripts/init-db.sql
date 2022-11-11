INSERT INTO uporabnik (ime, priimek, username, primerjalnik, kosarice) VALUES ('Miha', 'Novak', 'mihanovak', 1, (1));
INSERT INTO uporabnik (ime, priimek, username, primerjalnik, kosarice) VALUES ('Nika', 'Molan', 'nikamolan', 2, (2,3));
INSERT INTO uporabnik (ime, priimek, username, primerjalnik, kosarice) VALUES ('Nina', 'Mislej', 'ninamislej', 3, (4,5));

INSERT INTO primerjalnik (stevilo, kosarice) VALUES (0, (1));
INSERT INTO primerjalnik (stevilo, kosarice) VALUES (2, (2,3));
INSERT INTO primerjalnik (stevilo, kosarice) VALUES (2, (4,5));

INSERT INTO trgovina (ime, kosarice, produkti) VALUES ("Asos", (1,4,5),(1,2,5));
INSERT INTO trgovina (ime, kosarice, produkti) VALUES ("Amazon", (2,3),(3,4));

INSERT INTO produkt (ime, opis, cena, trgovina, kosarice) VALUES ('Nalivno pero', 'Kakovostno nalivno pero zelene barve.', 10, 1, (2,3));
INSERT INTO produkt (ime, opis, cena, trgovina, kosarice) VALUES ('Črnilo', 'Nalivno črnilo modre barve.', 5, 1,(2));
INSERT INTO produkt (ime, opis, cena, trgovina, kosarice) VALUES ('Krilo', 'Zeleno krilo do kolen.', 30, 2,(4));
INSERT INTO produkt (ime, opis, cena, trgovina, kosarice) VALUES ('Kavbojke', 'Vintage Mom Jeans.', 45, 2, (5));
INSERT INTO produkt (ime, opis, cena, trgovina, kosarice) VALUES ('Namizna Lučka', 'Namizna svetilka rdeče barve.', 20, 1, (2));

INSERT INTO kosarica (kolicina, postnina, popust, uporabnik, primerjalnik, trgovina) VALUES (0, 0, 0, 1, 1, 1);
INSERT INTO kosarica (kolicina, postnina, popust, uporabnik, primerjalnik, trgovina, produkti) VALUES (1, 0, 10, 2, 2, 2, (1));
INSERT INTO kosarica (kolicina, postnina, popust, uporabnik, primerjalnik, trgovina, produkti) VALUES (3, 20, 0, 2, 2, 2, (1,2,5));
INSERT INTO kosarica (kolicina, postnina, popust, uporabnik, primerjalnik, trgovina, produkti) VALUES (1, 15, 10, 3, 3, 1, (3));
INSERT INTO kosarica (kolicina, postnina, popust, uporabnik, primerjalnik, trgovina, produkti) VALUES (1, 15, 30, 3, 3, 1, (4));




