# SimpleTrackExpenses

Программа для учета личных финансов.
Позволяет вводить, редактировать и удалять данные о поступлении и затратах, указанием даты и комментария к каждой транзакции
<p align="center">
  <img src="https://github.com/Timzmei/SimpleTrackExpenses/blob/master/images/20210803142103-856x893.png">
</p>
Программа позволяет добавлять, редактировать или удалять трнзакции.

Для редактирования или удаления выбирается в таблице нужная транзакция и потом совершается нужное действие.

Строится график учета средств и выводятся данные того, сколько поступило и убыло средств, а также екущий баланс.

Также можно сохранить данные в текстовом файле, нажав на кнопку "отчет" или сохранить график в .png формате (кнопка "График").

Данные сохраняются в БД MySQL.

### Программа написана с использованием:
* JavaFX
* Maven
* Hibernate
* c3p0

В Hibernate в свойствах конфигурации "HBM2DDL_AUTO" использовано значение "create-drop" для автоматического создания таблиц. База данных также создается автоматически, в случае ее отсутствия.

Для доступа к БД необходимо в файле "HibernateUtil.java" задать нужные значения значения "USER" и "PASS".

