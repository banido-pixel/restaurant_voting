INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANTS (NAME)
VALUES ('Арарат'),
       ('Белый аист'),
       ('Прага');

INSERT INTO DISHES (NAME, PRICE, RESTAURANT_ID, MENU_DATE)
VALUES ('Уха', 250, 1, current_date),
       ('Весенний салат', 100, 1, current_date),
       ('Плов', 200, 1, current_date),
       ('Борщ', 250, 1, '2022-01-11'),
       ('Цезарь', 100, 1, '2022-01-11'),
       ('Куриное рагу', 150, 1, '2022-01-11');


INSERT INTO VOTES (RESTAURANT_ID, USER_ID, VOTE_DATE)
VALUES (1, 1, current_date),
       (3, 2, current_date),
       (1, 1, '2022-01-11'),
       (2, 1, '2022-01-10'),
       (1, 2, '2022-01-11');