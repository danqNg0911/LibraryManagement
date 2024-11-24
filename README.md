# Ulib Library Manager - Ứng dụng quản lý thư viện điện tử Ulib
# 🐋Tác giả
1. Nguyễn Hữu Hải Đăng - 23020524
2. Phạm Huy Hiếu - 23020535
3. Phạm Việt Hưng - 23020542
# 💡Mô tả
Ulib Library Manager là giải pháp tối ưu cho việc số hóa và quản lý thư viện một cách hiệu quả. Với giao diện thân thiện cùng các tính năng đa dạng, ứng dụng hỗ trợ từ quản lý kho sách, phân loại tác giả, thể loại đến theo dõi giao dịch mượn trả, giúp mọi hoạt động trở nên nhanh chóng và chính xác.

Điểm mạnh của Ulib Library Manager nằm ở khả năng tự động hóa và tinh gọn quy trình, từ tổ chức dữ liệu đến phân tích, báo cáo trực quan. Ứng dụng không chỉ giảm thiểu sai sót mà còn tiết kiệm thời gian đáng kể, mang lại sự tiện lợi tối đa cho người quản lý.

Không chỉ giới hạn trong các thư viện truyền thống, Ulib Library Manager còn phù hợp với nhiều mô hình khác như trường học, doanh nghiệp, và tổ chức phi lợi nhuận, đáp ứng nhu cầu lưu trữ tài liệu lớn một cách khoa học và dễ tiếp cận.

Với Ulib Library Manager, quản lý thư viện không chỉ là công việc, mà còn là trải nghiệm hiện đại, tiện nghi và đầy cảm hứng.

# 📊Chức năng
### 1. Với người dùng (User):
  _ Tìm kiếm sách theo tên, tác giả, và thể loại.
<br>_ Xem thông tin tóm tắt nội dung cuốn sách.
<br>_ Thêm, mượn và xóa sách. <br>&nbsp;&nbsp;&nbsp;Không chỉ giới hạn ở những cuốn sách trong API mà còn những cuốn sách cá nhân.
<br>_ Có bộ sưu tập sách cá nhân.
<br>_ Theo dõi số lượng sách được thêm mỗi ngày thông qua biểu đồ.
<br>_ Có thể chọn hình đại diện ảnh động.
<br>_ Được cập nhật tin tức và thông báo bản cập nhật mới của thư viện (nếu có).
<br>_ Được xem video giới thiệu thư viện đầy hấp dẫn.
<br>_ Trải nghiệm trò chơi Black Myth Wukong hoàn toàn miễn phí.
<br>_ Trải nghiệm danh sách phát nhạc thư giãn. 
<br>_ Trải nghiệm Chatbot AI Gemini qua email cá nhân.
<br>_  Thông tin cá nhân, tài khoản và mật khẩu được bảo mật an toàn. 


### 2. Với người quản lý (Manager)
  _ Xem danh sách người dùng, tìm kiếm theo tên người dùng, xem thông tin chi tiết của từng người dùng khi bấm vào.
<br>_ Xem toàn bộ các quyển sách đang được mượn, thông tin chi tiết của các quyển sách.
<br>_ Xem các quyển sách đã được người dùng trả về, ghi nhận phạt người dùng nếu quá hạn.
<br>_  Thông tin cá nhân, tài khoản và mật khẩu được bảo mật an toàn.
# ⚔Ngôn ngữ phát triển 
<table align="center">
  <tr>
    <!-- Java -->
    <td>
      <a href="https://www.java.com" target="_blank" rel="noreferrer">
        <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="60" height="60" />
      </a>
    </td>
    <td width="30"></td>
    <!-- CSS -->
    <td>
      <a href="https://www.w3schools.com/css/" target="_blank" rel="noreferrer">
        <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original-wordmark.svg" alt="css3" width="60" height="60" />
      </a>
    </td>
    <td width="30"></td>
    <!-- MySQL -->
    <td>
      <a href="https://www.mysql.com/" target="_blank" rel="noreferrer">
        <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="60" height="60" />
      </a>
    </td>
  </tr>
</table>


<p align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white" alt="CSS3" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
</p>





# ❄️Sơ đồ UML
# 🚀Hướng dẫn cài đặt
1. Dự án yêu cầu IDE cho Java, CSS và MySQL (ví dụ: `IntelliJ IDEA bản Ultimate` và `MySQL WorkBench`).
2. Clone dự án về IDE: <a href="https://www.youtube.com/watch?v=ILSQeAOK0gs" target="_blank"> Hướng dẫn.</a>
3. Mở MySQL WorkBench, tạo MySQL Connections mới (nếu chưa có trên máy), rồi tạo tệp Query Tab mới. <br> Tại đây, sao chép, dán và chạy tệp [`sql`](https://github.com/danqNg0911/LibraryManagement/blob/main/src/main/SQL/sql) nhằm tạo database cho dự án.
4. Mở IDE IntelliJ, vào Project Structure, cài hết các thư viện mở rộng ở thư mục [lib](https://github.com/danqNg0911/LibraryManagement/tree/main/lib) trong dự án.
5. Tại 2 lớp enum LinkSetting của thư mục [`library`](https://github.com/danqNg0911/LibraryManagement/blob/main/src/main/java/com/example/library/LinkSetting.java) và [`game`](https://github.com/danqNg0911/LibraryManagement/blob/main/src/main/java/com/example/game/LinkSetting.java), chỉnh sửa đường dẫn phù hợp với máy bạn. <br> Đừng quên sửa cả đường dẫn database nhé !
6. Giờ bạn hãy vào tệp [`Main.java`](https://github.com/danqNg0911/LibraryManagement/blob/main/src/main/java/com/example/library/Main.java) và chạy dự án.
# 🔰Hướng dẫn sử dụng
  - Ấn **Start App** để bắt đầu chạy ứng dụng
### 1. Người dùng (User)
####  a. Đăng kí và đăng nhập
  - Nhập *username* và *password* chính xác rồi ấn *Sign in* để đăng nhập vào ứng dụng (nếu đã có tài khoản)
  - Nếu chưa có tài khoản, ấn *Create new account* để tạo tài khoản mới
     + Nhập đầy đủ thông tin : *Name*, *Username*, *Password* (từ 8 ký tự trở lên), *Phone number* (đủ 10 số), *Email*
     + Chọn **Reader** rồi bấm **Next** để hiện ra trang đăng kí bảo mật
     + Ở trang đăng kí bảo mật, bạn cần nhập những câu hỏi bảo mật liên quan đến cá nhân, bạn cần nhớ câu trả lời của những câu hỏi này, nó sẽ giúp bạn lấy lại tài khoản nếu bạn quên tài khoản
     + Ấn *Resigter* để hoàn tất đăng kí
  - Nếu bạn quên mật khẩu, ấn *Forgot password ?* để lấy lại mật khẩu. Ở đây bạn cần nhập đầy đủ và chính xác tất cả thông tin của bạn (username, ngày sinh, câu hỏi bảo mật), sau đó nhấn *Reset password* để hoàn tất tạo mật khẩu mới
####  b. Sử dụng các chức năng thư viện
####  c. *Trợ giúp* và *Nâng cấp*
### 2. Người quản lý (Manager)
####  a. Đăng kí và đăng nhập
####  b. Sử dụng các chức năng thư viện
####  c. *Trợ giúp* và *Nâng cấp*
  _Ấn "Create account" để tạo tài khoản mới
# 🐸Minh họa dự án 

# 🔭Cải tiến trong tương lai
# 🍒Đóng góp
Chúng tôi mong muốn có thêm những đóng góp của quý độc giả để hoàn thiện dự án hơn !
![](https://raw.githubusercontent.com/platane/snk/output/github-contribution-grid-snake-dark.svg)
# ⚛Trạng thái dự án 
Dự án đã được hoàn thiện 
# 📧Liên hệ

<div align="left">
  <a>
    <a href="https://www.facebook.com/profile.php?id=100021674799789" target="_blank">
    <img src="https://img.shields.io/badge/facebook-%232E87FB.svg?&style=for-the-badge&logo=facebook&logoColor=white" height="35" alt="facebook logo" />
  </a>
    
  <a>
    <a href="https://www.youtube.com/watch?v=rtOvBOTyX00" target="_blank">
    <img src="https://img.shields.io/static/v1?message=Youtube&logo=youtube&label=&color=FF0000&logoColor=white&labelColor=&style=for-the-badge" height="35" alt="youtube logo"  />
  </a>

  <a>
    <a href="https://www.youtube.com/watch?v=bTFGzdEmG-A&list=PLLeRClCOXxzLnfOsKduGsUwbgen0FG46t&index=5" target="_blank">
    <img src="https://img.shields.io/static/v1?message=Discord&logo=discord&label=&color=7289DA&logoColor=white&labelColor=&style=for-the-badge" height="35" alt="discord logo"  />
  </a>

  <a>
    <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ" target="_blank">
    <img src="https://img.shields.io/static/v1?message=Gmail&logo=gmail&label=&color=D14836&logoColor=white&labelColor=&style=for-the-badge" height="35" alt="gmail logo"  />
  </a>
</div>


# ✏️Ghi chú

