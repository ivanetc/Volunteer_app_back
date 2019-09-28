# API methods
Методы доступны по хосту: https://demo11.alpha.vkhackathon.com:443/
## User
### GET: /api/user/getRating/
#### Params:
**auth**: token

**user_id**: user_vk_id

#### Return
`{ user_rating : "4.5" }`

### GET: /api/user/Profile/
#### Params:
**auth**: token
**user_id**: user_vk_id

#### Return
`{
    "user_id":"1",
    "vk_id":"4521",
    "surname":"РРІР°РЅРµС†",
    "first_name":"РђР»РµРєСЃР°РЅРґСЂ",
    "second_name":"РЎРµСЂРіРµРµРІРёС‡",
    "birthday":"14.05.1998",
    "sex":"0",
    "email":"ivanetsas@yandex.ru",
    "phone":"89052668317",
    "occupation":"РџРѕР»РёРјРµС‚Р°Р»Р» РРЅР¶РёРЅРёСЂРёРЅРі.",
    "langs":"РђРЅРіР»РёР№СЃРєС‚РёС‚РµР»СЊРЅРѕ",
    "volunteer_experience":"РћРїС‹С‚РћРїС‹С‚РћРїС‹С‚",
    "children_work_experience":"РћРїС‹С‚CР”РµС”РµС‚СЊРјРё",
    "skills":"РЏРЈРјЏРЈРјРµРЅСЊСЏРЈСЊСЏРЈРјРµРЅСЊСЏ",
    "expectations":"РЏР¶РёРґР°РЅРёСЏ",
    "medical_contraindications":")ЊРµ)",
    "specialty":"РџСРїРѕ РєРѕРґСѓ",
    "food_preferences":"Р•ЂСѓС‡РµРЅРѕ",
    "clothes_size":"РЎРµСЂРіРµРµРІРёС‡",
    "information_source":"Р‚СЊ",
    "mailing_agreement":"true"
}`


# Events
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

### GET: /api/events/getEvent
#### Params:
**auth**: token

**id**: id мероприятия в нашей базе

#### Return
`[
    { "vk_id" : "546785",
      "is_open_to_apply" : "true",
      "is_user_applied" : "true",
      "organizer_id" : "127845",
      "manager_id" : "145785"
    }
]`