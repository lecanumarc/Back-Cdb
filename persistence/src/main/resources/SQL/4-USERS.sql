use `computer-database-db`;
drop table if exists user;
create table user (
    id                        bigint not null auto_increment,
    username                  varchar(255),
    password                  varchar(255),
    role                      varchar(255),
    constraint pk_company primary key (id))
  ;

insert into user (id,username,password,role) values (  1,'admincdb','$2y$12$lIiLY2XSaZlPYpH2Tt4vNe.aw2BODXbf3pjl9UoxFGBKeu3AncuPm','ADMIN');
