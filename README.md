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

<br><br>

# BoardController API

## 1️⃣ 게시글 작성 (Write Post)

| 항목            | 내용                                                                 |
|---------------|------------------------------------------------------------------|
| **URL**       | `/post/write`                                                     |
| **Method**    | `POST`                                                            |
| **Request 파라미터** | `name` (String, 작성자 이름) <br> `content` (String, 게시글 내용) <br> `email` (String, 작성자 이메일) <br> `files` (List<MultipartFile>, 첨부파일들) |
| **Response**  | **성공**: `200 OK`, `"게시글 작성 완료"` <br> **실패**: `400 Bad Request`, `"파일 형식이 잘못됨"` |
| **설명**      | 게시글을 작성합니다. 첨부파일이 있을 경우 AWS S3에 업로드하여 파일 URL을 저장합니다. |

---

## 2️⃣ 게시글 전체 조회 (Get All Posts)

| 항목            | 내용                                                                 |
|---------------|------------------------------------------------------------------|
| **URL**       | `/post/all`                                                       |
| **Method**    | `GET`                                                             |
| **Request 파라미터** | `size` (int, 한 페이지에 표시할 게시글 수) <br> `page` (int, 페이지 번호) |
| **Response**  | **성공**: `200 OK`, 게시글 목록과 닉네임 목록 반환                       |
| **설명**      | 게시글 목록을 조회합니다. 페이지네이션이 적용되며, 게시글의 작성자 닉네임도 함께 반환됩니다. |

---

## 3️⃣ 게시글 상세 조회 (Get Post)

| 항목            | 내용                                                                 |
|---------------|------------------------------------------------------------------|
| **URL**       | `/post/{boardId}`                                                  |
| **Method**    | `GET`                                                             |
| **Request 파라미터** | `boardId` (long, 게시글 ID)                                           |
| **Response**  | **성공**: `200 OK`, 게시글 상세 정보, 작성자 닉네임, 댓글 목록 반환 <br> **실패**: `400 Bad Request`, `"페이지가 존재하지 않습니다."` |
| **설명**      | 특정 게시글의 상세 정보를 조회합니다. 조회수와 댓글, 파일도 함께 반환됩니다. |

---

## 4️⃣ 게시글 검색 (Search Post)

| 항목            | 내용                                                                 |
|---------------|------------------------------------------------------------------|
| **URL**       | `/post/search`                                                     |
| **Method**    | `GET`                                                             |
| **Request 파라미터** | `category` (String, 검색 항목: name, email, content) <br> `keyword` (String, 검색 키워드) <br> `size` (int, 한 페이지에 표시할 게시글 수) <br> `page` (int, 페이지 번호) |
| **Response**  | **성공**: `200 OK`, 검색된 게시글 목록과 작성자 닉네임 목록 반환           |
| **설명**      | 게시글을 특정 항목으로 검색하여 조회합니다. |

---

## 5️⃣ 게시글 수정 (Update Post)

| 항목            | 내용                                                                 |
|---------------|------------------------------------------------------------------|
| **URL**       | `/post/update/{boardId}`                                           |
| **Method**    | `POST`                                                            |
| **Request 헤더** | `Authorization` (String, JWT 토큰)                              |
| **Request 파라미터** | `boardId` (long, 게시글 ID) <br> `content` (String, 수정할 게시글 내용) |
| **Response**  | **성공**: `200 OK`, `"게시글이 수정되었습니다."` <br> **실패**: `400 Bad Request`, `"게시글이 존재하지 않습니다."`, `"유효하지 않은 토큰"`, `"작성자만 수정할 수 있습니다."` |
| **설명**      | 사용자가 작성한 게시글을 수정합니다. JWT 토큰을 이용해 작성자 여부를 검증합니다. |

---

## 6️⃣ 게시글 삭제 (Delete Post)

| 항목            | 내용                                                                 |
|---------------|------------------------------------------------------------------|
| **URL**       | `/post/delete/{boardId}`                                           |
| **Method**    | `POST`                                                            |
| **Request 헤더** | `Authorization` (String, JWT 토큰)                              |
| **Request 파라미터** | `boardId` (long, 게시글 ID)                                       |
| **Response**  | **성공**: `200 OK`, `"게시글이 삭제되었습니다."` <br> **실패**: `400 Bad Request`, `"게시글이 존재하지 않습니다."`, `"유효하지 않은 토큰"`, `"작성자 or 관리자만 삭제할 수 있습니다."` |
| **설명**      | 사용자가 작성한 게시글을 삭제합니다. JWT 토큰을 이용해 작성자 또는 관리자 여부를 검증합니다. |

---

## 7️⃣ 게시글 좋아요 (Like Post)

| 항목            | 내용                                                                 |
|---------------|------------------------------------------------------------------|
| **URL**       | `/post/like/{boardId}`                                              |
| **Method**    | `POST`                                                            |
| **Request 파라미터** | `boardId` (long, 게시글 ID) <br> `email` (String, 좋아요 누른 사용자 이메일) |
| **Response**  | **성공**: `200 OK`, `"게시글 좋아요"` <br> **실패**: `400 Bad Request`, `"이미 좋아요 하신 상태입니다."` |
| **설명**      | 특정 게시글에 좋아요를 추가합니다. 이미 좋아요를 누른 상태라면 실패 응답을 반환합니다. |

---

## 8️⃣ 게시글 좋아요 취소 (Delete Like Post)

| 항목            | 내용                                                                 |
|---------------|------------------------------------------------------------------|
| **URL**       | `/post/deletelike/{boardId}`                                       |
| **Method**    | `POST`                                                            |
| **Request 파라미터** | `boardId` (long, 게시글 ID) <br> `email` (String, 좋아요 취소한 사용자 이메일) |
| **Response**  | **성공**: `200 OK`, `"좋아요가 취소되었습니다."` <br> **실패**: `400 Bad Request`, `"좋아요 하지 않은 게시글입니다."` |
| **설명**      | 특정 게시글의 좋아요를 취소합니다. 좋아요를 누르지 않은 게시글에 대해 취소를 시도하면 실패 응답을 반환합니다. |

<br><br>

# CommentController

---

## 1️⃣ 댓글 작성 (Write Comment)

| 항목            | 내용                                                                 |
|---------------|------------------------------------------------------------------|
| **URL**       | `/comment/write`                                                 |
| **Method**    | `POST`                                                            |
| **Request 파라미터** | `content` (String, 댓글 내용), `email` (String, 작성자 이메일), `boardId` (long, 게시글 ID) |
| **Response**  | **성공**: `200 OK`, `"댓글이 작성되었습니다."` <br> **실패**: `400 Bad Request`, `"게시글이 존재하지 않습니다."` |
| **설명**      | 특정 게시글에 댓글을 작성합니다. 게시글이 존재하지 않으면 실패 응답을 반환합니다. |

---

## 2️⃣ 댓글 수정 (Update Comment)

| 항목            | 내용                                                       |
|---------------|--------------------------------------------------------|
| **URL**       | `/comment/update/{cmtId}`                                |
| **Method**    | `POST`                                                   |
| **Request 헤더** | `Authorization` (String, JWT 토큰)                    |
| **Request 파라미터** | `cmtId` (long, 댓글 ID), `content` (String, 수정할 내용) |
| **Response**  | **성공**: `200 OK`, `"댓글이 수정되었습니다."` <br> **실패**: `400 Bad Request`, `"댓글이 존재하지 않습니다."`, `"유효하지 않은 토큰"`, `"작성자만 수정할 수 있습니다."` |
| **설명**      | 사용자가 작성한 댓글을 수정합니다. JWT 토큰을 이용해 작성자 여부를 검증합니다. |

---

## 3️⃣ 댓글 삭제 (Delete Comment)

| 항목            | 내용                                                    |
|---------------|-----------------------------------------------------|
| **URL**       | `/comment/delete/{cmtId}`                           |
| **Method**    | `POST`                                              |
| **Request 헤더** | `Authorization` (String, JWT 토큰)               |
| **Request 파라미터** | `cmtId` (long, 댓글 ID)                          |
| **Response**  | **성공**: `200 OK`, `"댓글이 삭제되었습니다."` <br> **실패**: `400 Bad Request`, `"댓글이 존재하지 않습니다."`, `"유효하지 않은 토큰"`, `"작성자 or 관리자만 삭제할 수 있습니다."` |
| **설명**      | 사용자가 작성한 댓글을 삭제합니다. JWT 토큰을 이용해 작성자 또는 관리자 여부를 검증합니다. |

---

## 4️⃣ 댓글 좋아요 (Like Comment)

| 항목            | 내용                                                |
|---------------|-------------------------------------------------|
| **URL**       | `/comment/like/{cmtId}`                         |
| **Method**    | `POST`                                          |
| **Request 파라미터** | `cmtId` (long, 댓글 ID), `email` (String, 좋아요 누른 사용자 이메일) |
| **Response**  | **성공**: `200 OK`, `"댓글 좋아요"` <br> **실패**: `400 Bad Request`, `"존재하지 않는 댓글입니다."`, `"이미 좋아요 하신 상태입니다."` |
| **설명**      | 특정 댓글에 좋아요를 추가합니다. 이미 좋아요를 누른 상태라면 실패 응답을 반환합니다. |

---

## 5️⃣ 댓글 좋아요 취소 (Unlike Comment)

| 항목            | 내용                                                 |
|---------------|--------------------------------------------------|
| **URL**       | `/comment/deletelike/{cmtId}`                     |
| **Method**    | `POST`                                           |
| **Request 파라미터** | `cmtId` (long, 댓글 ID), `email` (String, 좋아요를 취소할 사용자 이메일) |
| **Response**  | **성공**: `200 OK`, `"댓글 좋아요 취소"` <br> **실패**: `400 Bad Request`, `"존재하지 않는 댓글입니다."`, `"좋아요 하신 상태가 아닙니다."` |
| **설명**      | 특정 댓글에 눌렀던 좋아요를 취소합니다. 좋아요를 하지 않은 상태라면 실패 응답을 반환합니다. |

