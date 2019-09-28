# API methods
Методы доступны по хосту: demo11.alpha.vkhackathon.com:8080/
## User
### GET: /api/user/getRating/
#### Params:
**auth**: token

**user_id**: user_vk_id

#### Return
`{ user_rating : "4.5" }`

### GET: /api/events/getActualEvents
#### Params:
**auth**: token

**user_id**: user_vk_id

#### Return
`[
    { "vk_id" : "546785",
      "is_open_to_apply" : "true",
      "is_user_applied" : "true",
      "organizer_id" : "127845",
      "manager_id" : "145785"
    }
]`
