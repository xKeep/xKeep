
create table label
(
	labelID bigint auto_increment
		primary key,
	userID bigint not null,
	noteID bigint not null,
	name varchar(100) not null
)
;

create index label_user_userID_fk
	on label (userID)
;

create index label_note_noteID_fk
	on label (noteID)
;

create table note
(
	noteID bigint auto_increment
		primary key,
	userID bigint not null,
	headerNote varchar(20) not null,
	textNote text not null,
	colorNote varchar(20) not null,
	dateCreateNote date not null,
	dateUpdateNote date not null,
	statusNote varchar(20) not null,
	positionNote int not null
)
	comment 'Database for accessing , modifying and updating  users notes.'
;

alter table label
	add constraint label_note_noteID_fk
foreign key (noteID) references note (noteID)
;

create table shared_note
(
	shared_noteID bigint auto_increment
		primary key,
	noteID bigint not null,
	userID bigint not null,
	constraint shared_note_note_noteID_fk
	foreign key (noteID) references note (noteID)
)
;

create index shared_note_note_noteID_fk
	on shared_note (noteID)
;

create index shared_note_user_userID_fk
	on shared_note (userID)
;

create table user
(
	userID bigint auto_increment
		primary key,
	userName varchar(40) not null,
	password varchar(40) not null,
	email varchar(40) not null
)
	comment 'Database for accessing information about user.'
;

alter table label
	add constraint label_user_userID_fk
foreign key (userID) references user (userID)
;

alter table shared_note
	add constraint shared_note_user_userID_fk
foreign key (userID) references user (userID)
;

