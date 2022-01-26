
create table if not exists cinema(
            id serial primary key ,
            name varchar(200),
            approved boolean,
            user_id integer,
            constraint fk_user_id foreign key (user_id) references users(id)
                );