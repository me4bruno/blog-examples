# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table Users (
  id                        bigint not null,
  email                     varchar(255),
  name                      varchar(255),
  created_at                timestamp not null,
  updated_at                timestamp not null,
  version                   timestamp not null,
  constraint pk_Users primary key (id))
;

create sequence Users_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists Users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists Users_seq;

