# Online-shop
### Используемые технологии:
- Java 8
- Spring framework (Sping boot, Spring MVC, Spring Data JPA/Hibernate, Spring Security)
- PostgreSQL
- Thymeleaf
- Bootstrap
- jQuery

Для запуска проекта необходимо настроить в конфигурации (файл application.properties) логин/пароль к postgres. Создавать базу данных не нужно, она будет создана автоматиески при запуске.
Стартовая страница - http://localhost:8080

### Данные существующих пользователей:
- пользователь с ролью "USER": inga/inga
- пользователь с ролью "ADMIN": admin/admin

### Возможности пользователя с ролью "USER":
- регистрация нового пользователя
- авторизация
- редактирование своего профиля
- редактирование своего адреса
- просмотр магазина
- добавление товаров в корзину
- удаление товаров из корзины
- совершение заказа (заказ сохраняется в базе данных)

### Возможности пользователя с ролью "ADMIN":
- авторизация
- редактирование своего профиля
- редактирование своего адреса
- просмотр магазина
- добавление товаров в корзину
- удаление товаров из корзины
- совершение заказа (заказ сохраняется в базе данных)
- управление существующими товарами (редактирование названия, цены, описания, фотографии, количества товаров в наличии)
- удаление существующего товара
- добавление нового товара

При регистрациии пользователя его пароль сохраняется в зашифрованном виде

### Главная страница
<img width="950" alt="index" src="https://user-images.githubusercontent.com/95912864/229703250-0e74bbe8-c37b-4e35-af29-a7e19fa5d03c.png">
<img width="959" alt="index_2" src="https://user-images.githubusercontent.com/95912864/229703266-da2bc278-ab15-40ca-96ad-d4f7639a7efc.png">

### Страница магазина
 <img width="575" alt="store" src="https://user-images.githubusercontent.com/95912864/229703606-77c00e34-96c5-45d4-9eb7-f7cb36f535fa.png">

### Страница авторизации / регистрации
<img width="949" alt="auth_register" src="https://user-images.githubusercontent.com/95912864/229703957-4aaa7696-1863-445d-b53f-112fbfd9b325.png">

### Профиль пользователя
<img width="631" alt="my-profile" src="https://user-images.githubusercontent.com/95912864/229703707-72c55ecf-daab-4b3d-87b2-410cfb6f0935.png">

### Корзина пользователя
<img width="557" alt="cart" src="https://user-images.githubusercontent.com/95912864/229703837-de4b78a8-7f7a-4b8a-af4a-a8ed6fb3ed39.png">

### Редактирование карточки товара администратором
<img width="578" alt="admin_edit-article" src="https://user-images.githubusercontent.com/95912864/229705419-ebd4b61d-4a30-4d53-af7a-c51addee46ff.png">


