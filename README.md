# Donate Backend System

Backend service cho hệ thống **donate streamer**, hỗ trợ realtime donation, bảng xếp hạng và xác thực người dùng.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- MySQL
- Redis (cache & leaderboard ranking)
- WebSocket (realtime donation)
- Docker (optional)

---

## 📂 Project Structure
<img width="827" height="224" alt="image" src="https://github.com/user-attachments/assets/f26cacae-847f-42bf-8957-9575d8378abe" />
##  Features

###  Authentication
- Đăng ký / đăng nhập
- Xác thực bằng JWT
- Phân quyền: `USER` / `STREAMER`

### Streamer
- Tạo streamer bằng token
- Upload avatar / thumbnail
- Lấy thông tin streamer

###  Donation
- Gửi donate theo streamer
- Lưu lịch sử donate
- Hỗ trợ anonymous donation

### Ranking (Redis)
- Top donor theo từng streamer
- Sử dụng Redis Sorted Set
- Tối ưu hiệu năng so với database

###  Realtime (WebSocket)
- Nhận donation ngay lập tức
- Broadcast tới client (UI streamer)

## ⚙️ Setup & Run

### 1. Clone project
```bash
git clone https://github.com/your-repo/donate-backend.git
cd donate-backend
```
2. Run Redis (Docker)
```bash
docker run -d -p 6379:6379 redis
```
3. Run Project
```bash
./mvnw spring-boot:run
```
