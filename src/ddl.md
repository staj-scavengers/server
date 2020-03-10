# SQL data definition language (DDL)

```sqlite
create table clue
(
    clue_id    CHAR(16) FOR BIT DATA not null,
    clue_name  varchar(255)          not null,
    hunt_order integer               not null,
    media      varchar(255)          not null,
    media_tag  varchar(255)          not null,
    hunt_id    CHAR(16) FOR BIT DATA,
    primary key (clue_id)
);
create table hunt
(
    hunt_id      CHAR(16) FOR BIT DATA not null,
    hunt_name    varchar(1024)         not null,
    organizer_id CHAR(16) FOR BIT DATA,
    primary key (hunt_id)
);
create table hunt_activity
(
    hunt_activity_id varchar(255) for bit data not null,
    clues_completed  integer,
    date_completed   timestamp,
    date_started     timestamp,
    total_time       bigint,
    hunt_id          CHAR(16) FOR BIT DATA,
    user_id          CHAR(16) FOR BIT DATA,
    primary key (hunt_activity_id)
);
create table organizer
(
    organizer_id CHAR(16) FOR BIT DATA not null,
    primary key (organizer_id)
);
create table user_profile
(
    user_id      CHAR(16) FOR BIT DATA not null,
    oauth_token  varchar(255),
    user_name    varchar(255)          not null,
    organizer_id CHAR(16) FOR BIT DATA,
    primary key (user_id)
);
create index IDXlt342gvnts8vebhc5crjwppdx on clue (clue_name);
create index IDXhjn9iy4vhusftx1f1angf3fig on clue (media_tag);
create unique index UKd4lwrlkamhdonieh738eq0i1v on clue (hunt_id, hunt_order);
create index IDXqji6d71vqp6tkx0feveaxd5hc on hunt (hunt_name);
alter table hunt
    add constraint UK_qji6d71vqp6tkx0feveaxd5hc unique (hunt_name);
create index IDXcp1bt0boosditkmdwin9rp8ya on hunt_activity (date_started);
create index IDXjqrt3fhu6nq9vudiwagm9tae8 on hunt_activity (date_completed);
create index IDX3lsbx2hr3n5tqownk16ocbhmi on hunt_activity (total_time);
create index IDXkprt8pwl8nkxr91ja1xwt039h on hunt_activity (clues_completed);
alter table clue
    add constraint FKix5b0u8u3iy3vlpt9ushkwyaw foreign key (hunt_id) references hunt;
alter table hunt
    add constraint FKesmdjn9m18prmexnxa2unredi foreign key (organizer_id) references organizer;
alter table hunt_activity
    add constraint FKki2thgd6w3ueym40u8a4xl0re foreign key (hunt_id) references hunt;
alter table hunt_activity
    add constraint FK10tlfcsk0xfkfdbk8i9l57lqo foreign key (user_id) references user_profile;
alter table user_profile
    add constraint FKe29sjqu03aqhpwk77wdma3fjj foreign key (organizer_id) references organizer;
```

[Download](ddl.sql)