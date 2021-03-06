# EvaluationSystem

...
## FrontEnd - Angular 2


### Welcome Page (/)
- [] - Homepage
- [x] - Login
- [x] - Register

### Dashboard (/dashboard)
```
Falta fazer a parte de atualizar a navbar
```
- [x] Profile
- [x] Schedule
- [] Results
- [] Notifications
```
Falta testar em caso de erro
Depois do save apagar a password
```
- [] Classes
- [] GroupView
- [] Group Create
- [] Questions Create
- [] Questions View
- [] Class Results
- [] Exam Create
- [] Exam View Details
- [] Exam Resolve (make submission)

- [] Exam Submission Result  (Student)(Teacher)
- [] Exam Submissions Results (Teacher)



- DashBoard
```
Refresh - need someones changes
Remove and Edit Group
Many Groups problems with page_content_onresize();
```

- Schedule
```
Maybe change colors
```

- Create Group
```
Need Tests
```

- Create Question
```
Need Tests
```


## Backend - Spring and Hibernate

### Build and Run
```
./docker_build.sh
```
```
./docker_run.sh
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

- [POST /auth/login](#post-authlogin) [x]
- [POST /auth/signup](#post-authsignup) [x]
- [GET /api/classes/{class_id}](#get-apiclassesclass_id) [x]
- [PUT /api/classes/{class_id}](#put-apiclassesclass_id)
- [DELETE /api/classes/{class_id}](#delete-apiclassesclass_id)
- [GET /api/classes/{class_id}/questions](#get-apiclassesclass_idquestions) [x]
- [POST /api/classes/{class_id}/questions](#post-apiclassesclass_idquestions) [x]
- [GET /api/classes/{class_id}/categories](#get-apiclassesclass_idcategories) [x]
- [GET /api/classes/{class_id}/groups](#get-apiclassesclass_idgroups) [x]
- [POST /api/classes/{class_id}/groups](#post-apiclassesclass_idgroups) [x]
- [GET /api/questions/{question_id}](#get-apiquestionsquestion_id)
- [PUT /api/questions/{question_id}](#put-apiquestionsquestion_id)
- [DELETE /api/questions/{question_id}](#delete-apiquestionsquestion_id)
- [GET /api/groups/{group_id}](#get-apigroupsgroup_id) [x]
- [PUT /api/groups/{group_id}](#put-apigroupsgroup_id)
- [DELETE /api/groups/{group_id}](#delete-apigroupsgroup_id)
- [GET /api/groups/{group_id}/students](#get-apigroupsgroup_idstudents) [x]
- [POST /api/groups/{group_id}/students](#post-apigroupsgroup_idstudents) [x]
- [DELETE /api/groups/{group_id}/students/{student_id}](#delete-apigroupsgroup_idstudentsstudent_id) [x]
- [GET /api/groups/{group_id}/questions/available](#get-apigroupsgroup_idquestionsavailable)
- [GET /api/groups/{group_id}/exams](#get-apigroupsgroup_idexams)
- [POST /api/groups/{group_id}/exams](#post-apigroupsgroup_idexams)
- [POST /api/groups/{group_id}/exams/generate](#post-apigroupsgroup_idexamsgenerate)
- [POST /api/groups/{group_id}/exams/generate/question](#post-apigroupsgroup_idexamsgeneratequestion)
- [GET /api/groups/{group_id}/scores](#get-apigroupsgroup_idscores)
- [GET /api/exams/{exam_id}](#get-apiexamsexam_id)
- [DELETE /api/exams/{exam_id}](#delete-apiexamsexam_id)
- [PUT /api/exams/{exam_id}](#put-apiexamsexam_id)
- [GET /api/exams/{exam_id}/scores](#get-apiexamsexam_idscores)
- [POST /api/exams/{exam_id}/submission](#post-apiexamsexam_idsubmissions)
- [GET /api/submissions/{submission_id}](#get-apisubmissionssubmission_id)
- [PUT /api/submissions/{submission_id}](#put-apisubmissionssubmission_id)
- [DELETE /api/submissions/{submission_id}](#delete-apisubmissionssubmission_id)
- [GET /api/users/{user_id}](#get-apiusersuser_id) [x]
- [PUT /api/users/{user_id}](#put-apiusersuser_id)
- [DELETE /api/users/{user_id}](#delete-apiusersuser_id)
- [POST /api/users/{user_id}/classes](#post-apiusersuser_idclasses) [x]
- [GET /api/users/{user_id}/classes](#get-apiusersuser_idclasses)
- [GET /api/users/{user_id}/groups](#get-apiusersuser_idgroups)
- [DELETE /api/users/{user_id}/groups/{group_id}](#delete-apiusersuser_idgroupsgroup_id)
- [GET /api/users/{user_id}/notifications](#get-apiusersuser_idnotifications) [x]
- [GET /api/users/{user_id}/submissions](#get-apiusersuser_idsubmissions)
- [GET /api/users/{user_id}/exams](#get-apiusersuser_idexams)
- [GET /api/users/{user_id}/scores](#get-apiusersuser_idscores)
- [POST /api/invitations/{invitation_id}/accept](#post-apiinvitationsinvitation_idaccept) [x]
- [POST /api/invitations/{invitation_id}/decline](#post-apiinvitationsinvitation_iddecline) [x]

#### POST /auth/login
##### Body
```json
{
 "email": "email1@email.com",
 "password": "password"
}
```
##### Response
```json
{
 "token": "header.body.signature",
 "user":{
     "email": "email1@email.com",
     "firstName": "Foo",
     "lastName": "Bar",
     "type": "Student | Teacher",
     "active": true,
     "id": 1
 }
}
```

##### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **UNAUTHORIZED (401)** - *Invalid authentication*, or *Unconfirmed email*


#### POST /auth/signup
##### Body
```json
{
  "email": "email1@email.com",
  "password": "password",
  "firstName": "Foo",
  "lastName": "Bar",
  "type": "Student | Teacher"
}
```

##### Response
```json
{
  "email": "email1@email.com",
  "firstName": "Foo",
  "lastName": "Bar",
  "type": "Student | Teacher",
  "active": true,
  "id": 1
}
```

##### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_ACCEPTABLE (406)** - *Missing information*, *Email already in use*, *Invalid user type*
___

#### GET /api/classes/{class_id}
### Response
```json
{
  "id": 1,
  "abbreviation": "AA",
  "name": "Arquiteturas Aplicacionais",
  "teacher": {
      "email": "email1@email.com",
      "firstName": "Foo",
      "lastName": "Bar",
      "type": "Student | Teacher",
      "active": true,
      "id": 1
  }
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such class*

#### PUT /api/classes/{class_id}
### Body
```json
{
	"name": "Name1",
	"abbreviation": "Abbreviation1"
}
```
### Response
```json
[
  {
   "name": "Name1",
   "abbreviation": "Abbreviation11",
   "id": 1
  }
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such class*
- **NOT_ACCEPTABLE (406)** - *Class already exists*

#### DELETE /api/classes/{class_id}
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **UNAUTHORIZED (401)**
- **NOT_FOUND (404)** - *No such class*
- **NOT_ACCEPTABLE (406)** - *Class cannot be removed*

#### GET /api/classes/{class_id}/questions
### Response
```json
[
  {
    "id": 1,
    "text": "Enunciado pergunta 1",
    "category": "Category 1",
    "difficulty": 1,
    "answers": [
      {
        "id": 1,
        "text": "Alternativa 1",
        "correct": false,
        "order": 0
      },
      {
        "id": 2,
        "text": "Alternativa 2",
        "correct": true,
        "order": 1
      },
      {
        "id": 3,
        "text": "Alternativa 3",
        "correct": true,
        "order": 2
      }
    ]
  }
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such class*
- **UNAUTHORIZED (401)** - *No permission*

#### POST /api/classes/{class_id}/questions
### Body
```json
{
	"text": "Enunciado pergunta 1",
	"category": "Category 1",
	"difficulty": 1,
	"answers": [
		{
			"text": "Alternativa 1",
			"correct": false
		},
		{
			"text": "Alternativa 2",
			"correct": true
		},
		{
			"text": "Alternativa 3",
			"correct": false
		}
	]
}
```
### Response
```json
{
    "id": 1,
    "text": "Enunciado pergunta 1",
    "category": "Category 1",
    "difficulty": 1,
    "answers": [
        {
            "id": 1,
            "text": "Alternativa 1",
            "correct": false,
            "order": 0,
        },
        {
            "id": 2,
            "text": "Alternativa 2",
            "correct": false,
            "order": 1,
        },
        {
            "id": 3,
            "text": "Alternativa 3",
            "correct": true,
            "order": 2,
        }
    ]
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such class*
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *Invalid question*, *Question already exists*


#### GET /api/classes/{class_id}/categories
### Response
```json
[
  "Category1",
  "Category2"
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such class*
- **UNAUTHORIZED (401)** - *No permission*

#### GET /api/classes/{class_id}/groups
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

#### POST /api/classes/{class_id}/groups
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
            "type": "Teacher",
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

#### GET /api/questions/{question_id}
### Response
```json
"id": 6,
"text": "Solve for x: 5 + x = 9",
"category": "Category1",
"difficulty": 3,
"answers": [
  {
    "id": 24,
    "text": "6",
    "correct": false,
    "order": 0
  },
  {
    "..."
  }
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such question*
- **UNAUTHORIZED (401)** - *No permission*

#### PUT /api/questions/{question_id}
> Não é possível atualizar uma questão que esteja inserida num exame. Caso contrário, pode ser alterado qualquer um dos atributos *text*, *category*, *difficulty* ou *answers*.

### Body
```json
{
	"text": "Which is pi?",
	"category": "Category2",
	"difficulty": 1,
	"answers": [
		{
			"text": "pi",
			"correct": true
		},
		{
			"text": "2*pi",
			"correct": false
		}
	]
}
```

### Response
```json
{
  "id": 1,
  "text": "Which is pi?",
  "category": "Category2",
  "difficulty": 1,
  "answers": [
    {
      "id": 3603,
      "text": "pi",
      "correct": true,
      "order": 0
    },
    {
      "id": 3604,
      "text": "2*pi",
      "correct": false,
      "order": 1
    }
  ]
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such question*
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *Question in use*, *Question exists*, *Invalid question*

#### DELETE /api/questions/{question_id}
A questão só pode ser removida se não pertencer a nenhum exame.

### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such question*
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *Question cannot be removed*

___

#### GET /api/groups/{group_id}
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
            "type": "Teacher",
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

#### PUT /api/groups/{group_id}
### Body
```json
{
	"name": "Name1"
}
```
### Response
```
{
  "id": 1,
  "name": "Name1",
  "_class": {
    "name": "Name1",
    "abbreviation": "Abbreviation1",
    "teacher": {
      "id": 16,
      "email": "email16",
      "firstName": "firstName16",
      "lastName": "lastName16",
      "type": "Teacher",
      "active": true
    },
    "id": 1
  }
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)**
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *Group already exists*

#### DELETE /api/groups/{group_id}
Apaga um grupo apenas se este não tiver nenhuma submissão em algum dos seus exames.

### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)**
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *Group cannot be removed*

#### GET /api/groups/{group_id}/students
### Response
```json
[
  {
    "accepted": false,
    "user": {
      "email": "email999",
      "firstName": "ND",
      "lastName": "ND",
      "type": "Student",
      "active": false,
      "id": 21
    }
  },
  {
    "accepted": false,
    "user": {
      "email": "email1",
      "firstName": "firstName1",
      "lastName": "lastName1",
      "type": "Student",
      "active": true,
      "id": 1
    }
  }
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)**

#### POST /api/groups/{group_id}/students
### Body
```json
[
  "email1@email.com",
  "email2@email.com"
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
      "type": "Student",
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

#### DELETE /api/groups/{group_id}/students/{student_id}
Remove um estudante de um grupo apenas se este ainda não tiver feita nenhuma submissão de um exame.
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such group*, *No such student*

#### GET /api/groups/{group_id}/questions/available
Este método devolve as perguntas disponíveis para um dado grupo, organizadas por categoria e dificuldade.
Não são necessariamente as mesmas perguntas associadas à disciplina porque algumas dessas podem já ter sido utilizadas num exame anterior do grupo em questão.
### Response
```json
{
  "Category1": {
    "1": {
      "available": 1,
      "questionIDs": [
        1,
      ]
    },
    "3": {
      "available": 3,
      "questionIDs": [
        6,
        21,
        36
      ]
    }
  },
  "Category2": {
    "1": {
      "available": 2,
      "questionIDs": [
        22,
        37
      ]
    }
  }
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such group*
- **UNAUTHORIZED (401)** - *No permission*

#### GET /api/groups/{group_id}/exams
### Parameters
- *ongoing*: Faz com que seja retornada apenas uma lista dos exames a decorrer.

### Response
```json
{
  "exams": {
    "History": [
      {
        "id": 1,
        "name": "Exam 1",
        "beginDate": 1498908500000,
        "duration": 90,
      }
    ],
    "Ongoing": [
      {
        "..."
      }
    ],
    "Upcoming": [
      {
        "..."
      }
    ],
  }
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such group*
- **UNAUTHORIZED (401)** - *No permission*

#### POST /api/groups/{group_id}/exams


### Body
```json
{
	"beginDate": "1498908600000",
	"duration": 60,
	"name": "Exam 1",
	"questionIDs": [
		31, 7, 11, 6
	]
}
```
### Response
```json
{
  "id": 4,
  "name": "Exam 4",
  "beginDate": 1498908600000,
  "duration": 60,
  "questions": [
    {
      "id": 6,
      "text": "Solve for x: 5 + x = 9",
      "category": "Category1",
      "difficulty": 3,
      "answers": [
        {
          "id": 24,
          "text": "6",
          "correct": false,
          "order": 0
        },
        {
          "..."
        }
      ],
      "score": 5,
      "order": 0
    },
    {
      "..."
    }
  ]
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such group*
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *Invalid name*, *Invalid duration*, *Invalid date*, *Duplicate questions*, *Invalid exam*, *Invalid question* (id **ID**)

#### POST /api/groups/{group_id}/exams/generate
### Body
```json
[
	{
		"category": "Category1",
		"difficulty": 1
	},
	{
		"category": "Category2",
		"difficulty": 3
	}
]
```
### Response
```json
[
    {
        "id": 31,
        "text": "Solve for x: 30 + x = 34",
        "category": "Category1",
        "difficulty": 1,
        "answers": [
            {
                "id": 121,
                "text": "31",
                "correct": false,
                "order": 0,
            },
            {
              "id": 122,
              "text": "34",
              "correct": true,
              "order": 1,
            }
            {
                "id": 123,
                "text": "32",
                "correct": false,
                "order": 2,
            },
            {
                "id": 124,
                "text": "33",
                "correct": false,
                "order": 3,
            },
        ]
    },
    {
      "..."
    }
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such group*
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *Invalid questions*, *Insufficient questions*

#### POST /api/groups/{group_id}/exams/generate/question
Gera uma única questão para um exame.
### Body
> No campo *excluded* devem ir os IDs de perguntas a não considerar.

```json
{
	"category": "Category1",
	"difficulty": 1,
	"excluded": [1,16]
}
```
### Response
```json
{
    "id": 31,
    "text": "Solve for x: 30 + x = 34",
    "category": "Category1",
    "difficulty": 1,
    "answers": [
        {
            "id": 122,
            "text": "31",
            "correct": false,
            "order": 0
        },
        {
          "..."
        }
    ]
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such group*
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *No such question*, *Insufficient questions*

#### GET /api/groups/{group_id}/scores
### Response
> O campo *submissionID* pode não existir, caso o aluno não tenha feita uma submissão. Neste caso, o score é 0.

```json
{
  "students": [
    {
      "student": {
        "user info..."
      },
      "exams":[
        {
          "exam":{
            "exam info..."
          },
          "score": {
            "score": 20,
            "submissionID": 1
          }
        }
      ]
    },
  ]
}        
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such exam*
- **UNAUTHORIZED (401)** - *No permission*
___

#### GET /api/exams/{exam_id}
Se o utilizador for um aluno e o exame ainda não tiver começado, este método retorna apenas as informações do exame sem as questões; se o exame já tiver começado, então retorna também as perguntas mas sem a indicação de qual a resposta certa.
### Response
```json
{
  "id": 7,
  "name": "Exam 7",
  "beginDate": 1498908600000,
  "duration": 60,
  "questions": [
    {
      "id": 31,
      "text": "Solve for x: 30 + x = 34",
      "category": "Category1",
      "difficulty": 1,
      "answers": [
        {
          "id": 123,
          "text": "31",
          "correct": false,
          "order": 0
        },
        {
          "..."
        }
      ],
      "score": 5,
      "order": 0
    },
    {
      "..."
    }
  ]
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such exam*
- **UNAUTHORIZED (401)** - *No permission*

#### DELETE /api/exams/{exam_id}
Apaga um exame apenas se ainda não tiver sido feita nenhuma submissão.
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such exam*
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *Exam cannot be removed*

#### PUT /api/exams/{exam_id}
### Body
```json
{
	"name": "Exam 1",
	"beginDate": 1498908600000,
	"duration": 10
}
```
### Response
```json
{
  "id": 1,
  "name": "Exam 1",
  "beginDate": 1498908600000,
  "duration": 10
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *Exam already exists*


#### GET /api/exams/{exam_id}/scores
### Body
> O campo *submissionID* pode não existir, caso o aluno não tenha feita uma submissão. Neste caso, o score é 0.

```json
{
  "students": [
    {
      "student": {
        "user info..."
      },
      "score": {
        "score": 20,
        "submissionID": 1
      }
    },
  ]
}        
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such exam*
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *Invalid exam*

#### POST /api/exams/{exam_id}/submissions
### Body
Mapeamento pergunta: resposta (IDs)
```json
{
	"1": 4,
	"2": 5
}
```
### Response
São retornadas todas as questões do exame, com a respetiva resposta do estudante. Caso este não dê uma resposta a alguma questão, então o campo "answer" não aparece.

```json
{
  "id": 5,
  "questions": [
    {
      "question": {
        "id": 1,
        "text": "Solve for x: 0 + x = 4",
        "category": "Category1",
        "difficulty": 1,
        "answers": [
          {
            "id": 1,
            "text": "1",
            "order": 0
          },
          {
            "..."
          }
        ],
        "score": 3.3333333,
        "order": 0
      },
      "answer": {
        "id": 4,
        "text": "4",
        "order": 3
      }
    },
  ]
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such exam*
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_ACCEPTABLE (406)** - *Existent submission*, *Invalid answer*, *Invalid question*
___

#### GET /api/submissions/{submission_id}
### Response
> Se o aluno não tiver respondido a alguma questão, o campo *answer* não aparece.
> Se o exame já tiver terminado, então também é indicado se as respostas estão certas ou não.

```json
{
  "id": 1,
  "questions": [
    {
      "question": {
        "id": 1,
        "text": "Solve for x: 1 + x = 5",
        "category": "Category1",
        "difficulty": 2,
        "answers": [
          "answers..."
        ],
        "score": 3.3333333,
        "order": 1
      },
      "answer": {
        "id": 4,
        "text": "1",
        "correct": false,
        "order": 0
      }
    },
  ]
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such submissions*

#### PUT /api/submissions/{submission_id}
Este método altera ou acrescenta novas respostas a uma submissão já existente.
O funcionamento é igual a [POST /api/exams/{exam_id}/submission](#post-apiexamsexam_idsubmissions)

#### DELETE /api/submissions/{submission_id}
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such submissions*
- **UNAUTHORIZED (401)** - *No permission*

___
#### GET /api/users/{user_id}
### Response
```json
{
  "email": "email1@email.com",
  "firstName": "Foo",
  "lastName": "Bar",
  "type": "Student | Teacher",
  "active": true,
  "id": 1
}
```

### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such user*

#### PUT /api/users/{user_id}
### Body
```json
{
	"firstName": "John",
	"lastName": "Doe",
	"password": "password"
}
```
### Response
```json
{
  "id": 1,
  "email": "email1",
  "firstName": "John",
  "lastName": "Doe",
  "type": "Student",
  "active": true
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **UNAUTHORIZED (401)**
- **NOT_FOUND (404)** - *No such user*

#### DELETE /api/users/{user_id}
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such user*
- **UNAUTHORIZED (401)** - *No permission*

#### POST api/users/{user_id}/classes
### Body
```json
{
  "name": "class1",
  "abbreviation": "cl1",
}
```
### Response
```json
{
  "name": "class1",
  "abbreviation": "cl1",
  "id": 1
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such teacher*
- **NOT_ACCEPTABLE (406)** - *Missing information*, *Class already exists*

#### GET /api/users/{user_id}/classes
### Response
Caso o utilizador seja um professor, nao e enviado o professor.
```json
[
  {
    "name": "Name1",
    "abbreviation": "Abbreviation1",
    "id": 1,
    "teacher": {
      "id": 16,
      "email": "email16",
      "firstName": "firstName16",
      "lastName": "lastName16",
      "type": "Teacher",
      "active": true
    },
  }
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such user*

#### GET /api/users/{user_id}/groups
### Response
```json
[
  {
    "id": 1,
    "name": "Name1",
    "_class": {
      "name": "Name1",
      "abbreviation": "Abbreviation1",
      "teacher": {
        "id": 16,
        "email": "email16",
        "firstName": "firstName16",
        "lastName": "lastName16",
        "type": "Teacher",
        "active": true
      },
      "id": 1
    }
  },
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such user*

#### DELETE /api/users/{user_id}/groups/{group_id}
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **UNAUTHORIZED (401)** - *No permission*
- **NOT_FOUND (404)** - *No such user*, *No such group*

#### GET /api/users/{user_id}/notifications
### Response
```json
[
  {
    "type": "Group invitation",
    "group": {
      "name": "Name1",
      "_class": {
        "name": "Name1",
        "abbreviation": "Abbreviation1",
        "teacher": {
          "email": "email16",
          "firstName": "firstName16",
          "lastName": "lastName16",
          "type": "Teacher",
          "active": true,
          "id": 16
        },
        "id": 1
      },
      "id": 1
    },
    "id": 0
  }
]
```

### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)**
- **UNAUTHORIZED (401)**

#### GET /api/users/{user_id}/submissions
### Parameters
- exam=ID
- group=ID

### Response
```json
[
  {
    "id": 5,
    "exam": {
      "id": 1,
      "name": "Exam 8",
      "beginDate": 1498908600000,
      "duration": 60,
      "group": {
        "id": 1,
        "name": "Name1",
        "_class": {
          "name": "Name1",
          "abbreviation": "Abbreviation1",
          "teacher": {
            "id": 16,
            "email": "email16",
            "firstName": "firstName16",
            "lastName": "lastName16",
            "type": "Teacher",
            "active": true
          },
          "id": 1
        }
      }
    }
  }
]
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such student*
- **UNAUTHORIZED (401)**
- **NOT_ACCEPTABLE (406)** - *Invalid group*, *Invalid exam*


#### GET /api/users/{user_id}/exams
### Parameters
- *ongoing*: Faz com que seja retornada apenas uma lista dos exames a decorrer.

### Response
```json
{
  "exams": {
    "History": [
      {
        "id": 1,
        "name": "Exam 1",
        "beginDate": 1498908500000,
        "duration": 90,
        "group": {
          "id": 1,
          "name": "Name1",
          "_class": {
            "name": "Name1",
            "abbreviation": "Abbreviation1",
            "teacher": {
              "id": 16,
              "email": "email16",
              "firstName": "firstName16",
              "lastName": "lastName16",
              "type": "Teacher",
              "active": true
            },
            "id": 1
          }
        }
      }
    ],
    "Ongoing": [
      {
        "..."
      }
    ],
    "Upcoming": [
      {
        "..."
      }
    ],
  }
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)**
- **UNAUTHORIZED (401)**

#### GET /api/users/{user_id}/scores

### Parameters
- exam=ID
- group=ID

### Response
A resposta abaixo é enviada quando não é passado nenhum parâmetro. Caso seja passado um ID de um grupo, então é enviado apenas o correspondente ao array *exams*; caso seja passado um ID de um exame, é enviado apenas o correspondente ao objeto *score*.
```json
{
  "groups": [
    {
      "group": {
        "id": 1,
        "name": "Name1",
        "_class": {
          "name": "Name1",
          "abbreviation": "Abbreviation1",
          "teacher": {
            "..."
          },
          "id": 1
        }
      },
      "exams": [
        {
          "exam": {
            "id": 1,
            "name": "Exam 8",
            "beginDate": 1498908600000,
            "duration": 60
          },
          "score": {
            "submissionID": 1,
            "score": 0
          }
        }
      ]
    }
  ]
}
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)** - *No such student*
- **UNAUTHORIZED (401)**
- **NOT_ACCEPTABLE (406)** - *Invalid group*, *Invalid exam*
___

#### POST /api/invitations/{invitation_id}/accept
### Response
```json
"name": "Name1",
"abbreviation": "Abbreviation1",
"teacher": {
    "email": "email16",
    "firstName": "firstName16",
    "lastName": "lastName16",
    "type": "Teacher",
    "active": true,
    "id": 16
},
"id": 1
```
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)**
- **UNAUTHORIZED (401)**

#### POST /api/invitations/{invitation_id}/decline
### HttpStatus
- **OK (200)**
- **INTERNAL_SERVER_ERROR (500)**
- **NOT_FOUND (404)**
- **UNAUTHORIZED (401)**
___
