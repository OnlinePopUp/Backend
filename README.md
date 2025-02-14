# AuthController

| **Endpoint**      | **Method** | **Description**         | **Request Parameters**                                        | **Response** |
|------------------|-----------|-------------------------|--------------------------------------------------------------|-------------|
| `/auth/login`    | `POST`    | 사용자 로그인            | `email` (String), `password` (String), `nickname` (String), `birth` (String), `phone` (String), `address` (String) | `200 OK`: `"로그인 성공"`, 응답 헤더: `AccessToken: 새로운 액세스 토큰` <br> `400 Bad Request`: `"사용자를 찾을 수 없습니다."` 또는 `"비밀번호 불일치"` |
| `/auth/join`     | `POST`    | 사용자 회원가입          | `email`, `password`, `birth`, `phone`, `address`, `nickname` (모두 필수) | `200 OK`: `"회원가입이 완료되었습니다."` <br> `200 OK`: `"이미 존재하는 ID입니다"` |
| `/auth/logout`   | `POST`    | 로그아웃 처리            | 없음 (쿠키에서 `refresh` 토큰 필요)                           | `200 OK`: `"로그아웃 성공"` <br> `400 Bad Request`: `"refresh 존재하지 않음"` |
| `/auth/reissue`  | `GET`     | Access Token 재발급     | `email` (String)                                             | `200 OK`: `"access token 재발급 성공"`, 응답 헤더: `AccessToken: 새로운 액세스 토큰` <br> `400 Bad Request`: `"refresh 존재하지 않음"` 또는 `"refresh 토큰 만료. 재로그인 바람"` |

<br><br>

# UserController API

| **Endpoint**       | **Method** | **Description**         | **Request Parameters** | **Response** |
|-------------------|-----------|-------------------------|-----------------------|-------------|
| `/user/follow`   | `POST`    | 특정 사용자를 팔로우    | `Authorization: Bearer {AccessToken}`, `flwEmail` (String) | `200 OK`: `"팔로우 되었습니다"` <br> `400 Bad Request`: `"이미 팔로우가 되있는 상태"` 또는 `"유효하지 않은 토큰"` |
| `/user/delete/follow` | `POST` | 특정 사용자의 팔로우 해제 | `Authorization: Bearer {AccessToken}`, `flwEmail` (String) | `200 OK`: `"언팔로우 되었습니다"` <br> `400 Bad Request`: `"팔로우 하지 않은 상태"` 또는 `"유효하지 않은 토큰"` |
| `/user/follow/all` | `GET` | 특정 사용자의 팔로워 및 팔로잉 목록 조회 | `email` (String) | `200 OK`: `{ "flwerList": [...], "flwerCnt": n, "flwerNick": [...], "flwingList": [...], "flwingCnt": n, "flwingNick": [...] }` |
| `/user/delete`  | `POST`    | 회원 탈퇴 | `Authorization: Bearer {AccessToken}`, `email` (String) | `200 OK`: `"탈퇴되었습니다."` <br> `400 Bad Request`: `"존재하지 않는 유저"` 또는 `"관리자 혹은 자신만 탈퇴 가능"` 또는 `"유효하지 않은 토큰"` |
| `/user/all`  | `GET` | 모든 유저 목록 조회 (페이지네이션) | `size` (int), `page` (int) | `200 OK`: `[{ "email": "...", "nickname": "...", "address": "...", "phone": "...", "birth": "..." }, ...]` |
| `/user/update`  | `POST`    | 유저 정보 수정 | `Authorization: Bearer {AccessToken}`, `address` (String, 선택), `birth` (String, 선택), `phone` (String, 선택), `nickname` (String, 선택) | `200 OK`: `"유저 정보 수정 완료"` <br> `400 Bad Request`: `"존재하지 않는 유저"` 또는 `"유효하지 않은 토큰"` |
| `/user/report` | `POST` | 특정 사용자를 신고 | `Authorization: Bearer {AccessToken}`, `email` (String, 필수), `content` (String, 필수) | `200 OK`: `"신고가 완료되었습니다."` <br> `400 Bad Request`: `"유효하지 않은 토큰"` |
| `/user/fill/point` | `POST` | 포인트 충전 | `Authorization: Bearer {AccessToken}`, `point` (long, 필수) | `200 OK`: `"포인트가 충전되었습니다"` <br> `400 Bad Request`: `"유효하지 않은 토큰"` 또는 `"존재하지 않는 유저"` |

<br><br>

# AdminController API

| **Endpoint**             | **Method** | **Description**                                                                                       | **Request Parameters**                                                                                                           | **Response**                                                                                         |
|-------------------------|------------|-------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|
| `/admin/join`            | `POST`     | 관리자가 새로운 사용자를 회원가입시킬 때 사용됩니다. 이미 등록된 이메일일 경우 실패               | `email` (String, 필수), `password` (String, 필수), `birth` (String, 필수), `phone` (String, 필수), `address` (String, 필수), `nickname` (String, 필수) | **성공**: `200 OK`, "관리자 회원가입이 완료되었습니다." <br> **실패**: `200 OK`, "이미 존재하는 ID입니다" |
| `/admin/user/all`        | `GET`      | 관리자가 모든 사용자의 정보를 조회할 때 사용됩니다. 페이지 번호와 페이지당 사용자 수를 받음       | `size` (int, 페이지당 사용자 수), `page` (int, 페이지 번호)                                                                    | **성공**: `200 OK`, 사용자 리스트와 함께 반환 <br> **실패**: `400 Bad Request`, 잘못된 요청 파라미터 |
| `/admin/report/all`      | `GET`      | 관리자가 모든 신고를 조회할 때 사용됩니다. 페이지 번호와 페이지당 신고 수를 받음                   | `size` (int, 페이지당 신고 수), `page` (int, 페이지 번호)                                                                      | **성공**: `200 OK`, 신고 리스트와 함께 반환 <br> **실패**: `400 Bad Request`, 잘못된 요청 파라미터 |
| `/admin/report/{reportId}`| `GET`     | 관리자가 특정 신고 내용을 조회할 때 사용됩니다. 신고가 존재하지 않으면 실패                        | `reportId` (Long, 필수)                                                                                                         | **성공**: `200 OK`, 해당 신고 정보 반환 <br> **실패**: `400 Bad Request`, "신고 내용이 없습니다." |

<br><br>

# ChatController API

| **Endpoint**             | **Method** | **Description**                                                                                       | **Request Parameters**                                                                                                           | **Response**                                                                                         |
|-------------------------|------------|-------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|
| `/chat/pub/send`         | `@MessageMapping` | 사용자가 채팅을 보내면, 해당 메시지가 지정된 `rEmail`(받는 사람 이메일)으로 실시간 전송됩니다. | `ChatDto` <br> - `sEmail` (보내는 사람 이메일) <br> - `rEmail` (받는 사람 이메일) <br> - `content` (메시지 내용) | **성공**: 수신자에게 실시간으로 메시지가 전송됩니다. <br> **실패**: 메시지 발송 실패 시 별도 응답 없음 (WebSocket을 통한 송신) |
| `/chat/history`          | `GET`      | 두 사용자가 주고받은 채팅 메시지 기록을 반환합니다.                                                    | `sEmail` (보내는 사람 이메일) <br> `rEmail` (받는 사람 이메일)                                                                  | **성공**: `200 OK` <br> `ChatDto` 목록 (보낸 사람과 받은 사람 간의 모든 채팅 메시지) <br> **실패**: `400 Bad Request`, `"존재하지 않는 유저"` |
| `/chat/ws`               | `GET`      | 클라이언트가 `ws://<서버 주소>/chat/ws`로 연결하여 WebSocket을 통한 실시간 채팅 기능을 사용합니다.    | 없음                                                                                                                             | WebSocket 연결 활성화 |
| `/chat/sub/{rEmail}`     | `GET`      | 수신자는 `/chat/sub/{자신의 Email}`을 구독하여 수신된 메시지를 실시간으로 수신합니다.               | 없음                                                                                                                             | 수신된 메시지를 실시간으로 받습니다. |

<br><br>

# BoardController API

| **Endpoint**             | **Method** | **Description**                                                                                              | **Request Parameters**                                                                                                             | **Response**                                                                                          |
|-------------------------|------------|----------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|
| `/post/write`           | `POST`     | 게시글을 작성합니다. 첨부파일이 있을 경우 AWS S3에 업로드하여 파일 URL을 저장합니다.                                  | `name` (String, 게시글 제목) <br> `content` (String, 게시글 내용) <br> `files` (List<MultipartFile>, 첨부파일들)                    | **성공**: `200 OK`, `"게시글 작성 완료"` <br> **실패**: `400 Bad Request`, `"파일 형식이 잘못됨"`                           |
| `/post/all`             | `GET`      | 게시글 목록을 조회합니다. 페이지네이션과 작성자 닉네임도 반환됩니다.                                             | `size` (int, 한 페이지에 표시할 게시글 수) <br> `page` (int, 페이지 번호)                                                          | **성공**: `200 OK`, 게시글 목록과 작성자 닉네임 목록 반환                                             |
| `/post/{boardId}`       | `GET`      | 특정 게시글의 상세 정보를 조회합니다. 조회수와 댓글, 파일도 함께 반환됩니다.                                      | `boardId` (long, 게시글 ID)                                                                                                        | **성공**: `200 OK`, 게시글 상세 정보, 작성자 닉네임, 댓글 목록 반환 <br> **실패**: `400 Bad Request`, `"페이지가 존재하지 않습니다."` |
| `/post/search`          | `GET`      | 특정 항목으로 게시글을 검색하여 조회합니다.                                                                  | `category` (String, 검색 항목: name, email, content) <br> `keyword` (String, 검색 키워드) <br> `size` (int, 한 페이지에 표시할 게시글 수) <br> `page` (int, 페이지 번호) | **성공**: `200 OK`, 검색된 게시글 목록과 작성자 닉네임 목록 반환                                      |
| `/post/update/{boardId}` | `POST`     | 사용자가 작성한 게시글을 수정합니다. JWT 토큰을 이용해 작성자 여부를 검증합니다.                                        | `boardId` (long, 게시글 ID) <br> `content` (String, 수정할 게시글 내용)                                                              | **성공**: `200 OK`, `"게시글이 수정되었습니다."` <br> **실패**: `400 Bad Request`, `"게시글이 존재하지 않습니다."`, `"유효하지 않은 토큰"`, `"작성자만 수정할 수 있습니다."` |
| `/post/delete/{boardId}` | `POST`     | 사용자가 작성한 게시글을 삭제합니다. JWT 토큰을 이용해 작성자 또는 관리자 여부를 검증합니다.                             | `boardId` (long, 게시글 ID)                                                                                                        | **성공**: `200 OK`, `"게시글이 삭제되었습니다."` <br> **실패**: `400 Bad Request`, `"게시글이 존재하지 않습니다."`, `"유효하지 않은 토큰"`, `"작성자 or 관리자만 삭제할 수 있습니다."` |
| `/post/like/{boardId}`   | `POST`     | 특정 게시글에 좋아요를 추가합니다. 이미 좋아요를 누른 상태라면 실패 응답을 반환합니다.                                       | `boardId` (long, 게시글 ID)                                                                                                        | **성공**: `200 OK`, `"게시글 좋아요"` <br> **실패**: `400 Bad Request`, `"이미 좋아요 하신 상태입니다."`                      |
| `/post/delete/like/{boardId}` | `POST` | 특정 게시글의 좋아요를 취소합니다. 좋아요를 누르지 않은 게시글에 대해 취소를 시도하면 실패 응답을 반환합니다. | `boardId` (long, 게시글 ID)                                                                                                        | **성공**: `200 OK`, `"좋아요가 취소되었습니다."` <br> **실패**: `400 Bad Request`, `"좋아요 하지 않은 게시글입니다."`             |

<br><br>

# CommentController API

| **Endpoint**             | **Method** | **Description**                                                                                              | **Request Parameters**                                                                                                             | **Response**                                                                                          |
|-------------------------|------------|----------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|
| `/comment/write`        | `POST`     | 특정 게시글에 댓글을 작성합니다. 게시글이 존재하지 않으면 실패 응답을 반환합니다.                             | `content` (String, 댓글 내용) <br> `boardId` (long, 게시글 ID)                                                                      | **성공**: `200 OK`, `"댓글이 작성되었습니다."` <br> **실패**: `400 Bad Request`, `"게시글이 존재하지 않습니다."`                      |
| `/comment/update/{cmtId}` | `POST`    | 사용자가 작성한 댓글을 수정합니다. JWT 토큰을 이용해 작성자 여부를 검증합니다.                                  | `cmtId` (long, 댓글 ID) <br> `content` (String, 수정할 내용)                                                                        | **성공**: `200 OK`, `"댓글이 수정되었습니다."` <br> **실패**: `400 Bad Request`, `"댓글이 존재하지 않습니다."`, `"유효하지 않은 토큰"`, `"작성자만 수정할 수 있습니다."` |
| `/comment/delete/{cmtId}` | `POST`    | 사용자가 작성한 댓글을 삭제합니다. JWT 토큰을 이용해 작성자 또는 관리자 여부를 검증합니다.                      | `cmtId` (long, 댓글 ID)                                                                                                            | **성공**: `200 OK`, `"댓글이 삭제되었습니다."` <br> **실패**: `400 Bad Request`, `"댓글이 존재하지 않습니다."`, `"유효하지 않은 토큰"`, `"작성자 or 관리자만 삭제할 수 있습니다."` |
| `/comment/like/{cmtId}`  | `POST`    | 특정 댓글에 좋아요를 추가합니다. 이미 좋아요를 누른 상태라면 실패 응답을 반환합니다.                            | `cmtId` (long, 댓글 ID)                                                                                                            | **성공**: `200 OK`, `"댓글 좋아요"` <br> **실패**: `400 Bad Request`, `"존재하지 않는 댓글입니다."`, `"이미 좋아요 하신 상태입니다."` |
| `/comment/delete/like/{cmtId}` | `POST` | 특정 댓글의 좋아요를 취소합니다. 좋아요를 누르지 않은 댓글에 대해 취소를 시도하면 실패 응답을 반환합니다. | `cmtId` (long, 댓글 ID)                                                                                                            | **성공**: `200 OK`, `"댓글 좋아요 취소"` <br> **실패**: `400 Bad Request`, `"존재하지 않는 댓글입니다."`, `"좋아요 하신 상태가 아닙니다."`               |

# ItemController

| **Endpoint**      | **Method** | **Description**         | **Request Parameters**                                        | **Response** |
|------------------|-----------|-------------------------|--------------------------------------------------------------|-------------|
| `/item`  | `POST`    | 팝업 아이템 등록, 팝업을 만들지 않았으면 오류 반환 |Multipart/formData(`itemDto` (json) {"name": "아이템이름","amount": 수량,"price": 가격,"des": "설명"} `files` `(List<MultipartFile>`)) | `200 OK`: `itemDto반환`,  `400 Bad Request`: `"해당 이메일을 가진 팝업 스토어가 존재하지 않습니다."` |
| `/item/{itemId}`    | `PUT`    | 팝업 아이템 수정 |Multipart/formData(`itemDto` (json) {"name": "아이템이름","amount": 수량,"price": 가격,"des": "설명"} `files` `(List<MultipartFile>`))  | `200 OK`: `itemDto반환`,  `400 Bad Request`: `"해당 itemId에 해당하는 아이템을 찾을 수 없습니다."`  |
| `/item/{popId}`    | `GET`    | 선택한 팝업의 아이템 조회 |`popId` (pathVariable)  | `200 OK`: `List반환 [{json}, {json}, ...]`,  `400 Bad Request`: `"해당 팝업 ID를 가진 팝업 스토어가 존재하지 않습니다."`  |
| `/item`  | `GET`    | 전체 팝업 아이템 조회 |`popId` (pathVariable)  |  `200 OK`: `List반환 [{json}, {json}, ...]`  `400 Bad Request`: `"해당 팝업 ID를 가진 팝업 스토어가 존재하지 않습니다."`  |
| `/item/search?keyword=아이템이름 or 아이템설명` | `GET`    | 검색을 통한 팝업 아이템 조회 |`keyword` (Request Param)  |  `200 OK`: `List반환 [{json}, {json}, ...]` |
| `/item/{itemId}`  | `DELETE`    | 팝업 아이템 삭제 |`itemId` (pathVariable)  |  `204 No Content`,  `400 Bad Request`: `"해당 itemId에 해당하는 아이템을 찾을 수 없습니다."`  |

<br><br>
