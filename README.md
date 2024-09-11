REST API using Spring-Boot/Spring Data JPA without frontend for the task:

Build a voting system for deciding where to have lunch.
- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote for a restaurant they want to have lunch at today
- Only one vote counted per user
- If user votes again the same day:
   - If it is before 11:00 we assume that he changed his mind.
   - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.


<br>
<a href='http://localhost:9999/swagger-ui/index.html#/' target="_blank">Link to Swagger</a><br>
<p><b>Credentionals for testing:</b><br>
                        - user@yandex.ru / password<br>
                        - admin@yandex.ru / admin<br>
