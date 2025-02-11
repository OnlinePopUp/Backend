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