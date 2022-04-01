create table country_counter (
    country_code varchar(2) not null,
    counter bigint not null,

    constraint pk_country_counter primary key (country_code)
);