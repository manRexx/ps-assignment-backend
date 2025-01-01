## Instructions to Run Backend Project

1. Clone the repository from GitHub:
   ```bash
   git clone https://github.com/manRexx/ps-assignment-backend
   ```

2. Navigate to the `backend-api` folder.
   ```bash
   cd ps-assignment-backend/backend-api
   ```

3. Create a `.env` file inside the `backend-api` folder and add the following configuration:
   ```env
   DB_URL=jdbc:h2:mem:db
   DB_USERNAME=sa
   DB_PASSWORD=password
   API_URL=https://dummyjson.com/users
   ```

4. Build and run the application using Maven. You can use one of the following methods:

   - **Run via IDE**: Build and run the application directly from your IDE.
   - **Run via Terminal**: Execute the following command in the terminal (inside backend-api folder):
     ```bash
     mvn clean spring-boot:run
     ```

## Developed APIs

### Base URL
All APIs are accessible under the base URL:
```
/api/users
```

example API:
```
[/api/users/load] (http://127.0.0.1:8080/api/users/load)
```

### Endpoints

#### 1. **Load Users**
- **Endpoint:** `/load`
- **Method:** `POST`

#### 2. **Get All Users**
- **Endpoint:** `/`
- **Method:** `GET`

#### 3. **Get Users by Role**
- **Endpoint:** `/role`
- **Method:** `GET`
- **Request Parameters:**
  - `role`: Accepted values: `admin`, `user`, `moderator`.

#### 4. **Get Users Sorted by Age**
- **Endpoint:** `/sort/age`
- **Method:** `GET`
- **Request Parameters:**
  - `ascending`: default: `true`

#### 5. **Search Users by ID**
- **Endpoint:** `/search`
- **Method:** `GET`
- **Request Parameters:**
  - `id`: id must be between 1 and 30.

## Additional Tools (after running the application you can see APIs documentation and db in the below URLs)
- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **H2 Console:** http://localhost:8080/h2-console/
