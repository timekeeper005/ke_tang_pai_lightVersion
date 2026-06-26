# ke_tang_pai_lightVersion
### 一、 核心数据库模型（Database Schema）

这些表结构是你们所有逻辑的物理映射。每个表都是一个独立的原子单元。

|**表名**|**字段设计**|**描述**|
|---|---|---|
|**User**|`uid(PK), name, role`|用户基础信息|
|**Credential**|`uid(FK), email, password_hash`|身份凭证（独立表，保证安全）|
|**Course**|`uid(PK), name, sub_title, description, school, semester, cover_image, status`|课程本体|
|**Membership**|`course_uid(FK), user_uid(FK), role`|纽带：课程与人的关系表|
|**Assignment**|`uid(PK), course_uid(FK), title, content_type, content_data`|作业定义|
|**Submission**|`id(PK), assignment_uid(FK), student_uid(FK), content, score, feedback, status`|作业提交与批改|



二、 契约规范（API Contract）
核心原则： 所有的请求和响应必须是 JSON 格式。

1. 登录与身份模块 (/api/auth)
   POST /api/auth/register
   发送： {name, email, password, role}
   返回： {status: "success", uid: 101}
   POST /api/auth/login
   发送： {email, password}
   返回： {status: "success", uid: 101, role: "TEACHER"}
2. 课程模块 (/api/course)
   POST /api/course/create
   发送： {name, sub_title, description, creator_uid}
   返回： {status: "success", course_uid: 501}
   POST /api/course/join
   发送： {course_uid, user_uid, invite_code}
   返回： {status: "success"}
3. 作业与批阅模块 (/api/assignment)
   POST /api/assignment/publish
   发送： {course_uid, title, content_data}
   返回： {status: "success"}
   POST /api/assignment/grade
   发送： {submission_id, score, feedback}
   返回： {status: "success"}
   三、 后端分层架构（逻辑流）
   每个人在开发自己的模块时，必须保证代码包含以下三层：

DAO (Data Access Object): 只负责写 SQL。例如 AssignmentDAO.save(assignment)。
Service (业务逻辑): 负责校验。例如：在批改作业前，先判断该 user_uid 是否拥有该课程的 TEACHER 角色。
Controller (接口入口): 只负责解析 JSON 和调用 Service。

### 1. 后端四层职责划分表 (架构蓝图)

|**层级**|**职责描述**|**必须遵循的逻辑**|
|---|---|---|
|**Model**|**纯粹的数据载体**|严禁包含业务逻辑，只能有字段、Getter/Setter|
|**DAO**|**数据的物理接口**|只管和数据库说话，执行 SQL，不关心为什么执行|
|**Service**|**业务的核心大脑**|负责协调 DAO，执行规则判断（如：如果分数 < 60 则不及格）|
|**Controller**|**外部逻辑转换器**|解析前端 JSON 请求，调用 Service，返回响应|

### 2. 为什么要分出这 4 个？ (逻辑闭环)

- **Controller 层：** 它是你们系统的“前台”。它不处理业务，只管“接单”**（接收 JSON）和**“回话”（返回响应）。

- **Service 层：** 它是你们系统的“后台管理”。它是最关键的，**所有的业务规则（比如：教师才能发布作业）全部写在这里。**

- **DAO 层：** 它是你们系统的“仓库管理员”。如果你想换数据库或者改 SQL，只需要动这一层，不会影响到业务逻辑。

- **Model 层：** 它是你们系统的“标准语言”。所有层级都用它作为传递数据的协议。


### 3. 开发规范

> **后端执行逻辑流 (The Execution Flow):**
>
> 1. 前端发送 JSON 到 `Controller`。
>
> 2. `Controller` 解析为 `Model` 对象，调用 `Service`。
>
> 3. `Service` 进行逻辑判断（比如 `if(user.role != 'TEACHER') throw Error`）。
>
> 4. `Service` 调用 `DAO` 去操作数据库。
>
> 5. `DAO` 返回结果，`Service` 处理后返回给 `Controller`。
>
> 6. `Controller` 返回给前端。
>

**绝对禁止：**

- **严禁 DAO 直接返回 JSON。**

- **严禁 Controller 里直接写 SQL。**

- **严禁 Service 里出现任何 `request.getParameter()` 这种底层的网络调用。**