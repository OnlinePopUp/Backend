# AuthController

## 1️⃣ 로그인 (Login)

| 항목         | 내용                                                                                                                                 |
|--------------|------------------------------------------------------------------------------------------------------------------------------------|
| **URL**      | `/auth/login`                                                                                                                      |
| **Method**   | `POST`                                                                                                                             |
| **Request 파라미터** | `email` (String), `password` (String), `nickname` (String), `birth` (String), `phone` (String), `address` (String)                 |
| **Response** | **성공**: `200 OK`, `"로그인 성공"`, 응답 헤더: `AccessToken: 새로운 액세스 토큰` <br> **실패**: `400 Bad Request`, `"사용자를 찾을 수 없습니다."` 또는 `"비밀번호 불일치"` |
| **설명**      | 사용자가 로그인 시, **email**과 **password**로 인증을 시도합니다. 로그인 성공 시 **access token**이 반환되며, **refresh token**은 쿠키에 저장됩니다.                     |

---

## 2️⃣ 회원가입 (Join)

| 항목         | 내용                              |
|--------------|-----------------------------------|
| **URL**      | `/auth/join`                      |
| **Method**   | `POST`                             |
| **Request 파라미터** | `email`, `password`, `birth`, `phone`, `address`, `nickname` (모두 필수) |
| **Response** | **성공**: `200 OK`, `"회원가입이 완료되었습니다."` <br> **실패**: `200 OK`, `"이미 존재하는 ID입니다"` |
| **설명**      | 새로운 사용자가 가입할 때 사용됩니다. 이미 등록된 이메일일 경우 `"이미 존재하는 ID입니다"`라는 응답이 반환됩니다. 성공적으로 가입되면 `"회원가입이 완료되었습니다."`라는 메시지가 반환됩니다. |

---

## 3️⃣ 로그아웃 (Logout)

| 항목         | 내용                              |
|--------------|-----------------------------------|
| **URL**      | `/auth/logout`                    |
| **Method**   | `POST`                             |
| **Request 파라미터** | 없음 (단, 쿠키에서 `refresh` 토큰 필요) |
| **설명**      | 사용자가 로그아웃할 때, **refresh token**을 삭제하고 쿠키에서 **refresh**를 만료시킵니다. `refresh` 토큰이 없을 경우 `"refresh 존재하지 않음"` 메시지가 반환됩니다. |

---

## 4️⃣ Access Token 재발급 (Reissue Access Token)

| 항목         | 내용                          |
|--------------|-------------------------------|
| **URL**      | `/auth/reissue`               |
| **Method**   | `GET`                          |
| **Request 파라미터** | `email` (String)           |
| **Response** | **성공**: `200 OK`, `"access token 재발급 성공"`, 응답 헤더: `AccessToken: 새로운 액세스 토큰` <br> **실패**: `400 Bad Request`, `"refresh 존재하지 않음"` 또는 `"refresh 토큰 만료. 재로그인 바람"` |
| **설명**      | 사용자의 `refresh token`을 기반으로 새로운 **access token**을 발급합니다. `refresh token`이 만료되었거나 존재하지 않는 경우, `"refresh 토큰 만료. 재로그인 바람"` 메시지가 반환됩니다. |

---

## 8️⃣ 포인트 충전 (Fill Point)

| 항목             | 내용                                                   |
|------------------|------------------------------------------------------|
| **URL**         | `/user/fill/point`                                    |
| **Method**      | `POST`                                               |
| **Request 헤더** | `Authorization: Bearer {AccessToken}`                 |
| **Request 파라미터** | `point` (long, 필수)                                |
| **Response**    | **성공**: `200 OK`, `"포인트가 충전되었습니다"` <br> **실패**: `400 Bad Request`, `"유효하지 않은 토큰"` 또는 `"존재하지 않는 유저"` |
| **설명**        | `Authorization` 헤더의 **access token**을 이용하여 사용자의 포인트를 충전합니다. |

<br><br>

# UserController

## 1️⃣ 팔로우 (Follow)

| 항목             | 내용                                                   |
|------------------|------------------------------------------------------|
| **URL**         | `/user/follow`                                        |
| **Method**      | `POST`                                               |
| **Request 헤더** | `Authorization: Bearer {AccessToken}`                 |
| **Request 파라미터** | `flwEmail` (String)                                  |
| **Response**    | **성공**: `200 OK`, `"팔로우 되었습니다"` <br> **실패**: `400 Bad Request`, `"이미 팔로우가 되있는 상태"` 또는 `"유효하지 않은 토큰"` |
| **설명**        | `Authorization` 헤더의 **access token**을 이용하여, 특정 사용자를 팔로우합니다. |

---

## 2️⃣ 언팔로우 (Unfollow)

| 항목             | 내용                                                   |
|------------------|------------------------------------------------------|
| **URL**         | `/user/delete/follow`                                 |
| **Method**      | `POST`                                               |
| **Request 헤더** | `Authorization: Bearer {AccessToken}`                 |
| **Request 파라미터** | `flwEmail` (String)                                  |
| **Response**    | **성공**: `200 OK`, `"언팔로우 되었습니다"` <br> **실패**: `400 Bad Request`, `"팔로우 하지 않은 상태"` 또는 `"유효하지 않은 토큰"` |
| **설명**        | `Authorization` 헤더의 **access token**을 이용하여, 특정 사용자의 팔로우를 해제합니다. |

---

## 3️⃣ 팔로우 목록 조회 (Get Followed Users)

| 항목             | 내용                                                   |
|------------------|------------------------------------------------------|
| **URL**         | `/user/follow/all`                                    |
| **Method**      | `GET`                                                |
| **Request 파라미터** | `email` (String)                                    |
| **Response**    | **성공**: `200 OK`, `{"flwerList": [...], "flwerCnt": n, "flwerNick": [...], "flwingList": [...], "flwingCnt": n, "flwingNick": [...]}` |
| **설명**        | 특정 사용자의 팔로워 및 팔로잉 목록을 조회합니다. |

---

## 4️⃣ 회원 탈퇴 (Delete User)

| 항목             | 내용                                                   |
|------------------|------------------------------------------------------|
| **URL**         | `/user/delete`                                        |
| **Method**      | `POST`                                               |
| **Request 헤더** | `Authorization: Bearer {AccessToken}`                 |
| **Request 파라미터** | `email` (String)                                    |
| **Response**    | **성공**: `200 OK`, `"탈퇴되었습니다."` <br> **실패**: `400 Bad Request`, `"존재하지 않는 유저"` 또는 `"관리자 혹은 자신만 탈퇴 가능"` 또는 `"유효하지 않은 토큰"` |
| **설명**        | 본인 또는 관리자가 사용자를 삭제할 수 있습니다. |

---

## 5️⃣ 유저 목록 조회 (Get All Users)

| 항목             | 내용                                                   |
|------------------|------------------------------------------------------|
| **URL**         | `/user/all`                                           |
| **Method**      | `GET`                                                |
| **Request 파라미터** | `size` (int), `page` (int)                          |
| **Response**    | **성공**: `200 OK`, `[{ "email": "...", "nickname": "...", "address": "...", "phone": "...", "birth": "..." }, ...]` |
| **설명**        | 모든 유저 목록을 페이지네이션하여 조회합니다. |

---

## 6️⃣ 유저 정보 수정 (Update User Info)

| 항목             | 내용                                                   |
|------------------|------------------------------------------------------|
| **URL**         | `/user/update`                                        |
| **Method**      | `POST`                                               |
| **Request 헤더** | `Authorization: Bearer {AccessToken}`                 |
| **Request 파라미터** | `address` (String, 선택) <br> `birth` (String, 선택) <br> `phone` (String, 선택) <br> `nickname` (String, 선택) |
| **Response**    | **성공**: `200 OK`, `"유저 정보 수정 완료"` <br> **실패**: `400 Bad Request`, `"존재하지 않는 유저"` 또는 `"유효하지 않은 토큰"` |
| **설명**        | 사용자가 자신의 정보를 수정할 수 있습니다. |

---

## 7️⃣ 신고하기 (Report User)

| 항목             | 내용                                                   |
|------------------|------------------------------------------------------|
| **URL**         | `/user/report`                                       |
| **Method**      | `POST`                                               |
| **Request 헤더** | `Authorization: Bearer {AccessToken}`                 |
| **Request 파라미터** | `email` (String, 필수) <br> `content` (String, 필수) |
| **Response**    | **성공**: `200 OK`, `"신고가 완료되었습니다."` <br> **실패**: `400 Bad Request`, `"유효하지 않은 토큰"` |
| **설명**        | `Authorization` 헤더의 **access token**을 이용하여 특정 사용자를 신고합니다. |

<br><br>

# AdminController API 문서

## 1️⃣ 관리자 회원가입 (Admin Join)

| 항목               | 내용                                                                                                                                    |
|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| **URL**            | `/admin/join`                                                                                                                           |
| **Method**         | `POST`                                                                                                                                  |
| **Request 파라미터** | `email` (String, 필수), `password` (String, 필수), `birth` (String, 필수), `phone` (String, 필수), `address` (String, 필수), `nickname` (String, 필수) |
| **Response**       | **성공**: `200 OK`, "관리자 회원가입이 완료되었습니다." <br> **실패**: `200 OK`, "이미 존재하는 ID입니다"                                |
| **설명**            | 관리자가 새로운 사용자를 회원가입시킬 때 사용됩니다. 이미 등록된 이메일일 경우 "이미 존재하는 ID입니다"라는 응답이 반환됩니다. 성공적으로 가입되면 "관리자 회원가입이 완료되었습니다."라는 메시지가 반환됩니다. |

---

## 2️⃣ 모든 사용자 조회 (Get All Users)

| 항목               | 내용                                                                                                  |
|--------------------|-----------------------------------------------------------------------------------------------------|
| **URL**            | `/admin/user/all`                                                                                     |
| **Method**         | `GET`                                                                                                |
| **Request 파라미터** | `size` (int, 페이지당 사용자 수), `page` (int, 페이지 번호)                                         |
| **Response**       | **성공**: `200 OK`, 사용자 리스트와 함께 반환 <br> **실패**: `400 Bad Request`, 잘못된 요청 파라미터  |
| **설명**            | 관리자가 모든 사용자의 정보를 조회할 때 사용됩니다. 페이지 번호와 페이지당 사용자 수를 파라미터로 받아 해당 페이지의 사용자 정보를 반환합니다. |

---

## 3️⃣ 모든 신고 조회 (Get All Reports)

| 항목               | 내용                                                                                                      |
|--------------------|---------------------------------------------------------------------------------------------------------|
| **URL**            | `/admin/report/all`                                                                                      |
| **Method**         | `GET`                                                                                                   |
| **Request 파라미터** | `size` (int, 페이지당 신고 수), `page` (int, 페이지 번호)                                             |
| **Response**       | **성공**: `200 OK`, 신고 리스트와 함께 반환 <br> **실패**: `400 Bad Request`, 잘못된 요청 파라미터        |
| **설명**            | 관리자가 모든 신고를 조회할 때 사용됩니다. 페이지 번호와 페이지당 신고 수를 파라미터로 받아 해당 페이지의 신고 정보를 반환합니다. |

---

## 4️⃣ 특정 신고 조회 (Get Report By ID)

| 항목               | 내용                                                                                              |
|--------------------|-------------------------------------------------------------------------------------------------|
| **URL**            | `/admin/report/{reportId}`                                                                      |
| **Method**         | `GET`                                                                                            |
| **Request 파라미터** | `reportId` (Long, 필수)                                                                       |
| **Response**       | **성공**: `200 OK`, 해당 신고 정보 반환 <br> **실패**: `400 Bad Request`, "신고 내용이 없습니다." |
| **설명**            | 관리자가 특정 신고 내용을 조회할 때 사용됩니다. 신고가 존재하지 않으면 "신고 내용이 없습니다."라는 메시지를 반환합니다. |