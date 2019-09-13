# Documentation


#                                   ADMIN requests

<br></br>

##                  ADMIN Requests for Restaurants:

#### get All Restaurants with Dishes for today
curl --user admin@gmail.com:admin http://localhost:8080/GraduationTJ/rest/admin/restaurants

#### get All Restaurants with their score for the particular date
curl --user admin@gmail.com:admin http://localhost:8080/GraduationTJ/rest/admin/restaurants/history?date=2019-08-29

#### get All Restaurants with their full (all time) score 
curl --user admin@gmail.com:admin http://localhost:8080/GraduationTJ/rest/admin/restaurants/history

#### get Restaurant 100004 with dishes and count of votes for today
curl --user admin@gmail.com:admin http://localhost:8080/GraduationTJ/rest/admin/restaurants/100004

#### get Restaurant 100004 with dishes and votes for date
curl --user admin@gmail.com:admin http://localhost:8080/GraduationTJ/rest/admin/restaurants/100004?date=2019-08-29

#### create Restaurant
curl --user admin@gmail.com:admin --data '{"id":null,"name":"Created Restaurant","dishes":null,"votes":null}' -H "Content-Type: application/json" -X POST http://localhost:8080/GraduationTJ/rest/admin/restaurants

#### update Restaurant 100002
curl --user admin@gmail.com:admin --data '{"id":null,"name":"Updated Restaurant (Tokio City)","dishes":null,"votes":null}' -H "Content-Type: application/json" -X PUT http://localhost:8080/GraduationTJ/rest/admin/restaurants/100002

#### delete Restaurant 100002
curl --user admin@gmail.com:admin -X DELETE http://localhost:8080/GraduationTJ/rest/admin/restaurants/100002

<br/>

##                  ADMIN Requests for Dishes:

## get Dish 100007
curl --user admin@gmail.com:admin http://localhost:8080/GraduationTJ/rest/admin/restaurants/dishes/100007

## create Dish for Restaurant 100004
curl --user admin@gmail.com:admin --data '{"name":"Tasty Dish","date":null,"price":21}' -H "Content-Type: application/json" -X POST http://localhost:8080/GraduationTJ/rest/admin/restaurants/100004/dishes

## update Dish 100007
curl --user admin@gmail.com:admin --data '{"name":"Updated California","date":null,"price":21}' -H "Content-Type: application/json" -X PUT http://localhost:8080/GraduationTJ/rest/admin/restaurants/100004/dishes/100007

##delete Dish 100007 (From restaurant 100004)
curl --user admin@gmail.com:admin -X DELETE http://localhost:8080/GraduationTJ/rest/admin/restaurants/100004/dishes/100007

<br></br>
<br/></br>
<br/></br>
#                                   USER requests

#### get All Restaurants with Dishes for today
curl --user user@yandex.ru:password http://localhost:8080/GraduationTJ/rest/user/restaurants

#### get  All Restaurants with count of votes for today (status is forbidden if user hasn't voted today or it's before 11 A.M. )
curl --user user@yandex.ru:password http://localhost:8080/GraduationTJ/rest/user/restaurants/score

#### get Restaurant 100004 with dishes and count of votes for today 
curl --user user@yandex.ru:password http://localhost:8080/GraduationTJ/rest/user/restaurants/100004


### create vote for Restaurant 100003
curl --user user@yandex.ru:password -X POST http://localhost:8080/GraduationTJ/rest/user/restaurants/voting?resId=100003

### update vote for Restaurant 100004
curl --user user@yandex.ru:password -X PUT "http://localhost:8080/GraduationTJ/rest/user/restaurants/voting?resId=100004&voteId=100010"
