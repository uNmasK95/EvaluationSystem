# EvaluationSystem

...

## Backend

### Build and Run
___
```
cd EvaluationSystem/
mvn package
cp target/evalsys-backend-1.0.jar ../Docker/spring/
cd ../Docker/spring
docker build -t evalsys-backend .
docker run --net="bridge" -p 8080:8080 evalsys-backend
```



### Authentication
Após fazer login e receber o **token**, deve ser enviado no cabeçalho HTTP:

> Authorization = "Bearer TOKEN"

O servidor pode responder a qualquer pedido (excepto de autenticação) com um código HTTP **UNAUTHORIZED (401)**, caso o **token** seja inválido ou tenha expirado.
```json
{
  "message": "Invalid token"
}
```
```json
{
  "message": "Token expired"
}
```


### API

#### POST   /auth/login

##### Body
```json
{
 "email": "something@something",
 "password": "some_password"
}
```
##### Response
```json
{
 "token": "header.body.signature",
 "user":{
     "email": "something@something",
     "firstName": "Foo",
     "lastName": "Bar",
     "type": "student | teacher",
     "active": true,
     "id": 1
 }
}
```

##### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **UNAUTHORIZED (401)** - *Invalid authentication*, or *Unconfirmed email*


#### POST   /auth/signup

##### Body
```json
{
  "email": "something@something",
  "password": "some_password",
  "firstName": "Foo",
  "lastName": "Bar",
  "type": "student | teacher"
}
```

##### Response
```json
{
  "email": "something@something",
  "firstName": "Foo",
  "lastName": "Bar",
  "type": "student | teacher",
  "active": true,
  "id": 1
}
```

##### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_ACCEPTABLE (406)** - *Missing information*, *Email already in use*, *Invalid user type*
___

#### GET    /api/classes/{id}
### Response
```json
{
  "id": 1,
  "abbreviation": "AA",
  "name": "Arquiteturas Aplicacionais",
  "teacher": {
      "email": "something@something",
      "firstName": "Foo",
      "lastName": "Bar",
      "type": "student | teacher",
      "active": true,
      "id": 1
  }
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such class*

#### ~~PUT    /api/classes/{id}~~
#### ~~DELETE /api/classes/{id}~~

#### ~~GET    /api/classes/{class_id}/questions~~
#### ~~POST   /api/classes/{class_id}/questions~~
#### ~~GET    /api/classes/{class_id}/categories~~

#### GET      /api/classes/{class_id}/groups
### Response
```json
[
  {
    "name": "class1",
    "id": 1
  },
  {
    "name": "class2",
    "id": 2
  }
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such class*

#### POST     /api/classes/{class_id}/groups
### Body
```json
{
  "name": "Turma 16/17"
}
```
### Response
```json
{
    "name": "Turma 16/17",
    "_class": {
        "name": "Arquiteturas Aplicacionais",
        "abbreviation": "AA",
        "teacher": {
            "email": "teacher@teacher",
            "firstName": "John",
            "lastName": "Doe",
            "type": "teacher",
            "active": true,
            "id": 1
        },
        "id": 1
    },
    "id": 1
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such class*
- **NOT_ACCEPTABLE (406)** - *Group already exists*
- **UNAUTHORIZED (401)** - *No permission*
___

#### ~~GET    /api/questions/{question_id}~~
#### ~~PUT    /api/questions/{question_id}~~
#### ~~DELETE /api/questions/{question_id}~~
___

#### GET    /api/groups/{id}
### Response
```json
{
    "name": "Name2",
    "_class": {
        "name": "Name1",
        "abbreviation": "Abbreviation1",
        "teacher": {
            "email": "email2",
            "firstName": "firstName2",
            "lastName": "lastName2",
            "type": "teacher",
            "active": true,
            "id": 2
        },
        "id": 1
    },
    "id": 2
}
```

### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such group*

#### ~~PUT    /api/groups/{id}~~
#### ~~DELETE /api/groups/{id}~~

#### ~~GET    /api/groups/{group_id}/students~~
#### POST   /api/groups/{group_id}/students
### Body
```json
[
  {
    "email": "email1@email.com"
  },
  {
    "email": "email2@email.com"
  }
]
```
### Response
```json
[
  {
    "email": "email1@email.com",
    "user": {
      "email": "email1@email.com",
      "firstName": "John",
      "lastName": "Doe",
      "type": "student",
      "active": true,
      "id": 1
    }
  }
  {
    "email": "email2@email.com",
    "message": "User is a teacher | Student has already been added to the group"
  }
]
```
> Nota: Os utilizadores, mesmo que não existam, são adicionados. Nestes casos, a variável "active" toma o valor falso.

### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **UNAUTHORIZED (401)**
- **NOT FOUND (404)**

#### ~~DELETE /api/groups/{group_id}/students/{id}~~

#### ~~GET    /api/groups/{group_id}/exams?upcoming,history~~
#### ~~POST   /api/groups/{group_id}/exams~~
#### ~~POST   /api/groups/{group_id}/exams/generate~~

#### ~~GET    /api/groups/{group_id}/scores~~
___

#### ~~GET    /api/exams/{id}~~
#### ~~DELETE /api/exams/{id}~~
#### ~~PUT    /api/exams/{id}~~
#### ~~GET    /api/exams?user,class,upcoming,history~~
#### ~~GET    /api/exams/{exam_id}/scores~~
#### ~~POST   /api/exams/{exam_id}/submissions  (Array de respostas)~~
___

#### ~~GET    /api/submissions/{submission_id}~~
#### ~~PUT    /api/submissions/{submission_id}~~
#### ~~DELETE /api/submissions/{submission_id}~~

___
#### GET      /api/users/{user_id}
### Response
```json
{
  "email": "something@something",
  "firstName": "Foo",
  "lastName": "Bar",
  "type": "student | teacher",
  "active": true,
  "id": 1
}
```

### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such user*

#### ~~PUT    /api/users/{user_id}~~
#### ~~DELETE /api/users/{user_id}~~
#### ~~GET    /api/users/{user_id}/notifications~~
___

#### ~~GET    /api/invitations/{invitation_id}/accept~~
#### ~~GET    /api/invitations/{invitation_id}/decline~~
___

#### ~~GET    /api/students/{student_id}/scores~~
#### ~~GET    /api/students/{student_id}/exams?upcoming,history~~
___

#### GET      /api/teachers/{teacher_id}/classes
### Response
```json
[
  {
   "name": "class1",
   "abbreviation": "cl1",
   "id": 1
  }
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such teacher*

#### POST /teachers/{teacher_id}/classes
### Body
```json
{
  "name": "class1",
  "abbreviation": "cl1",
}
```
### Response
```json
[
  {
   "name": "class1",
   "abbreviation": "cl1",
   "id": 1
  }
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such teacher*
- **NOT_ACCEPTABLE (406)** - *Missing information*, *Class already exists*
___
