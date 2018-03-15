CREATE TABLE YUser
(   
    UName VARCHAR2(255),
    UserId VARCHAR2(50),
    UType VARCHAR2(10),
    YelpingSince VARCHAR2(30),
    FunnyVotes NUMBER,
    UsefulVotes NUMBER,
    CoolVotes NUMBER,
    ReviewCount NUMBER,
    Fans NUMBER,
    AvgStars FLOAT,
    PRIMARY KEY (UserId)
);

CREATE TABLE Friends
(
    UserId VARCHAR2(30),
    FriendId VARCHAR2(30),
    PRIMARY KEY (UserId,FriendId),
    FOREIGN  KEY (UserId) REFERENCES YUser (UserId) ON DELETE CASCADE
);


CREATE TABLE TBusiness
(
    BId VARCHAR2(50),
    BType VARCHAR2(50),
    BName VARCHAR2(255),
    Address VARCHAR2(255),
    IsOpen VARCHAR2(20),
    City VARCHAR2(255),
    ReviewCount NUMBER,
    State VARCHAR2(255),
    Stars FLOAT,
    Longitude FLOAT,
    Latitude FLOAT,
    PRIMARY KEY (BId)
);

CREATE TABLE TBusinessCategory
(
    CatName VARCHAR2(255),
    BId VARCHAR2(50),
    PRIMARY KEY (BId, CatName),
    FOREIGN KEY (BId) REFERENCES TBusiness (BId) ON DELETE CASCADE
);

CREATE TABLE TBusinessSubCategory
(   
    SubCatName VARCHAR2(255),
    BId VARCHAR2(50),
    PRIMARY KEY (BId,SubCatName),
    FOREIGN KEY (BId) REFERENCES TBusiness (BId) ON DELETE CASCADE   
);



CREATE TABLE TCheckin(
BId VARCHAR2(50),
CheckDay VARCHAR2(50),
CheckHour NUMBER,
CheckCount NUMBER,
PRIMARY KEY(BId, CheckDay,CheckHour,CheckCount),
FOREIGN KEY(BId) REFERENCES TBusiness (BId) ON DELETE CASCADE
);

CREATE TABLE TReview
(   ReviewId varchar2(50),
    UserId varchar2(50),
    FunnyVote number,
    UsefulVote number,
    CoolVote number,
    Stars number,
    ReviewDate varchar2(20),
    ReviewText clob,
    ReviewType varchar2(255),
    BId varchar2(50),
    primary key (ReviewId),
    FOREIGN KEY (BId) REFERENCES TBusiness (BId) ON DELETE CASCADE,
    FOREIGN  KEY (UserId) REFERENCES YUser (UserId) ON DELETE CASCADE
);

create index reviewdate on TReview(ReviewDate);
create index reviewBid on TReview(BId);
create index reviewuser on TReview(UserId);
create index yelpsinceidx on YUser(YelpingSince);
create index fidx on Friends(UserId);
