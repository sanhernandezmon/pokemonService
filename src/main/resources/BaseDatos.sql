CREATE TABLE IF NOT EXISTS users
(
    user_id varchar(50) NOT NULL UNIQUE ,
    email varchar(30) NOT NULL,
    pass_word varchar(1) NOT NULL,
    PRIMARY KEY (user_id)
);
CREATE TABLE IF NOT EXISTS pokemons
(
    pokemon_id varchar(30) NOT NULL,
    user_id varchar(30) NOT NULL,
    pokemon_number numeric NOT NULL,
    pokemon_name varchar(30) NOT NULL,
    given_name varchar(30) NOT NULL,
    pokemon_level numeric NOT NULL,

    PRIMARY KEY (pokemon_id),
    CONSTRAINT fk_customer
        FOREIGN KEY(user_id)
        REFERENCES users(user_id)
)