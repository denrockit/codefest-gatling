# Users schema

# --- !Ups

CREATE TABLE news (
    id BIGSERIAL NOT NULL,
    title varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO news VALUES (generate_series(1, 1000000), md5(random()::text), md5(random()::text) || md5(random()::text));
SELECT setval('news_id_seq', (SELECT MAX(id) FROM news));

# --- !Downs

DROP TABLE News;
