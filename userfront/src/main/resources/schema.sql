
create table appointment (
                             id bigint not null auto_increment,
                             confirmed bit not null,
                             date datetime(6),
                             description varchar(255),
                             location varchar(255),
                             user_id bigint,
                             primary key (id)
) engine=InnoDB;

create table primary_account (
                                 id bigint not null auto_increment,
                                 account_balance decimal(19,2),
                                 account_number integer,
                                 primary key (id)
) engine=InnoDB;

create table primary_transaction (
                                     id bigint not null auto_increment,
                                     amount double precision,
                                     available_balance decimal(19,2),
                                     date datetime(6),
                                     description varchar(255),
                                     status varchar(255),
                                     type varchar(255),
                                     primary_account bigint,
                                     primary key (id)
) engine=InnoDB;

create table recipient (
                           id bigint not null auto_increment,
                           account_number varchar(255),
                           description varchar(255),
                           email varchar(255),
                           name varchar(255),
                           phone varchar(255),
                           user_id bigint,
                           primary key (id)
) engine=InnoDB;

create table role (
                      id bigint not null,
                      name varchar(255),
                      primary key (id)
) engine=InnoDB;

create table savings_account (
                                 id bigint not null auto_increment,
                                 account_balance decimal(19,2),
                                 account_number integer,
                                 primary key (id)
) engine=InnoDB;

create table savings_transaction (
                                     id bigint not null auto_increment,
                                     amount double precision,
                                     available_balance decimal(19,2),
                                     date datetime(6),
                                     description varchar(255),
                                     status varchar(255),
                                     type varchar(255),
                                     savings_account_id bigint,
                                     primary key (id)
) engine=InnoDB;

create table user (
                      id bigint not null auto_increment,
                      email varchar(255) not null,
                      enabled bit not null,
                      first_name varchar(255),
                      last_name varchar(255),
                      password varchar(255),
                      phone varchar(255),
                      username varchar(255),
                      primary_account_id bigint,
                      savings_account_id bigint,
                      primary key (id)
) engine=InnoDB;

create table user_role (
                           id bigint not null auto_increment,
                           role_id bigint,
                           user_id bigint,
                           primary key (id)
) engine=InnoDB;

alter table user
    add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

alter table appointment
    add constraint FKa8m1smlfsc8kkjn2t6wpdmysk
        foreign key (user_id)
            references user (id);

alter table primary_transaction
    add constraint FK5ng0pitvhli7rkjo6n8n98mb5
        foreign key (primary_account)
            references primary_account (id);

alter table recipient
    add constraint FK3041ks22uyyuuw441k5671ah9
        foreign key (user_id)
            references user (id);

alter table savings_transaction
    add constraint FK4bt1l2090882974glyn79q2s9
        foreign key (savings_account_id)
            references savings_account (id);

alter table user
    add constraint FKbj0uoj9i40dory8w4t5ojyb9n
        foreign key (primary_account_id)
            references primary_account (id);

alter table user
    add constraint FKihums7d3g5cv9ehminfs1539e
        foreign key (savings_account_id)
            references savings_account (id);

alter table user_role
    add constraint FKa68196081fvovjhkek5m97n3y
        foreign key (role_id)
            references role (id);

alter table user_role
    add constraint FK859n2jvi8ivhui0rl0esws6o
        foreign key (user_id)
            references user (id);
