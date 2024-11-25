# Ulib Library Manager - Ứng dụng quản lý thư viện điện tử Ulib
# 🐋Tác giả 
Nhóm `NONAME`
1. Nguyễn Hữu Hải Đăng - 23020524
2. Phạm Huy Hiếu - 23020535
3. Phạm Việt Hưng - 23020542
# 💡Mô tả
Thư viện điện tử Ulib (Ulib Library Manager) là giải pháp tối ưu cho việc số hóa và quản lý thư viện một cách hiệu quả. Ứng dụng được viết bằng ngôn ngữ Java, CSS, MySQL và tích hợp thư viện JavaFX. Ứng dụng dựa trên mô hình MVC (Model - View - Controller).

Với giao diện thân thiện cùng các tính năng đa dạng, ứng dụng hỗ trợ từ quản lý kho sách, phân loại tác giả, thể loại đến theo dõi giao dịch mượn trả, giúp mọi hoạt động trở nên nhanh chóng và chính xác. 

Điểm mạnh của thư viện điện tử Ulib nằm ở khả năng tự động hóa và tinh gọn quy trình, từ tổ chức dữ liệu đến phân tích, báo cáo trực quan. Ứng dụng không chỉ giảm thiểu sai sót mà còn tiết kiệm thời gian đáng kể, mang lại sự tiện lợi tối đa cho người quản lý.

Không chỉ giới hạn trong các thư viện truyền thống, thư viện điện tử Ulib còn phù hợp với nhiều mô hình khác như trường học, doanh nghiệp, và tổ chức phi lợi nhuận, đáp ứng nhu cầu lưu trữ tài liệu lớn một cách khoa học và dễ tiếp cận.

Với thư viện điện tử Ulib, quản lý thư viện không chỉ là công việc khô khan, mà còn là trải nghiệm hiện đại, tuyệt vời và đầy cảm hứng.

# 📊Chức năng
### 1. Với người dùng (User):
  - Tìm kiếm sách theo tên, tác giả, và thể loại.
  - Xem thông tin tóm tắt nội dung cuốn sách.
  - Thêm, mượn và xóa sách. <br> Không chỉ giới hạn ở những cuốn sách trong API mà còn những cuốn sách cá nhân.
  - Có bộ sưu tập sách cá nhân.
  - Theo dõi số lượng sách được thêm mỗi ngày thông qua biểu đồ.
  - Có thể chọn hình đại diện ảnh động.
  - Được cập nhật tin tức và thông báo bản cập nhật mới của thư viện (nếu có).
  - Được xem video giới thiệu thư viện đầy hấp dẫn.
  - Trải nghiệm trò chơi Black Myth Wukong hoàn toàn miễn phí.
  - Trải nghiệm danh sách phát nhạc thư giãn. 
  - Trải nghiệm Chatbot AI Gemini qua email cá nhân.
  - Thông tin cá nhân, tài khoản và mật khẩu được bảo mật an toàn. 


### 2. Với người quản lý (Manager)
  - Quản lý thông tin chi tiết của từng người dùng và từng cuốn sách.
  - Theo dõi lịch sử mượn, thêm trả và xóa sách của người dùng thông qua số liệu và biểu đồ.
  - Ghi nhận phạt người dùng nếu quá hạn trả sách.
  - Có thể chọn hình đại diện ảnh động.
  - Thông tin cá nhân, tài khoản và mật khẩu được bảo mật an toàn.
# <img src="https://user-images.githubusercontent.com/74038190/212284087-bbe7e430-757e-4901-90bf-4cd2ce3e1852.gif" height="40" width="40"> Ngôn ngữ phát triển 
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
     + Nhập đầy đủ thông tin : *Name*, *Username*, *Password* (từ 8 ký tự trở lên), *Phone number* (đủ 10 số), *Email*.
     + Chọn **Reader** rồi bấm **Next** để hiện ra trang đăng kí bảo mật.
     + Ở trang đăng kí bảo mật, bạn cần nhập những câu hỏi bảo mật liên quan đến cá nhân, bạn cần nhớ câu trả lời của những câu hỏi này, nó sẽ giúp bạn lấy lại tài khoản nếu bạn quên tài khoản
     + Ấn *Resigter* để hoàn tất đăng kí.
  - Nếu bạn quên mật khẩu, ấn *Forgot password ?* để lấy lại mật khẩu. Ở đây bạn cần nhập đầy đủ và chính xác tất cả thông tin của bạn (username, ngày sinh, câu hỏi bảo mật), sau đó nhấn *Reset password* để hoàn tất tạo mật khẩu mới.
####  b. Sử dụng các chức năng thư viện
  - **Dashboard** là nơi hiển thị mọi thông tin của bạn, bạn có thể xem thông tin tài khoản (Your information), lượng sách đã mượn qua biểu đồ. Ngoài ra, bạn có thể xem video giới thiệu về ULib và trải nghiệm AI khi bấm vào cửa sổ AI bên phải màn hình.
  - **Library** là thư viện của chúng tôi (sử dụng API của Google), bạn có thể tìm sách theo ISBN, tiêu đề, tác giả, thể loại. Thư viện có tích hợp gợi ý để bạn dễ dàng tìm kiếm hơn. Ấn *Search* để tìm sách. Bạn cũng có thể xem tổng quan về sách khi ấn *View*, ấn *Add* để thêm sách vào bộ sưu tập cá nhân, hoặc ấn *Delete* nếu thêm nhầm.
  - **Collection** là nơi bạn có thể xem bộ sưu tập của bạn, ngoài ra có thể xem những bộ sưu tập khác theo nhiều chủ đề khác nhau. Trước hết với *My collection*, bạn có thể xem toàn bộ số sách bạn đã mượn được sắp xếp theo Alphabet ở khung hình bên trái. Bạn có thể tìm kiếm sách tong bộ sưu tập theo tiêu đề, tác giả, thể loại, sau đó bấm biểu tượng *Tìm kiếm* để hiển thị. Ngoài ra bạn có thể lọc sách qua chức năng *Filters*. Bạn cũng có thể tự thêm sách vào bộ sưu tập của mình khi bấm vào nút *Add your own book* (lưu ý sách mà bạn thêm thì bạn có thể chỉnh sửa và update).
  - **Setting** là phần cài đặt. Ở đây có 2 phần là cài đặt thông tin tài khoản và cài đặt cá nhân. Bạn có thể chỉnh sửa thông tin cá nhân như *Name*, *Username*, *Security Questions* hay *Phone, Email*ở phần *Account settings* (Lưu ý cần nhập đúng mật khẩu hiện tại và tuân thủ các quy tắc như phần đăng ký). Ở phần cài đặt cá nhân, bạn có thể chọn avatar cho account của bạn khi ấn vào 1 trong 10 bức ảnh và *Accept*. Ngoài ra, chúng tôi còn cung cấp chức năng nghe nhạc khi bạn sử dụng thư viện, bạn có thể chọn phát bài nhạc yêu thích ở playlist và nghe nó trong khi sử dụng app.
####  c. Chức năng *Trợ giúp* và *Nâng cấp*
  - **Helps** gồm 2 phần là gửi ý kiến của bạn và hướng dẫn sử dụng. Bạn có thể viết ý kiến, đánh giá, góp ý cho chúng tôi để chúng tôi có thể hoàn thiện app hơn. Nếu bạn chưa biết cách sử dụng, có thể bấm vào nút *Open* để xem hướng dẫn của chúng tôi trên Youtube.
####  d. Game *"Black Myth Wukong"*
### 2. Người quản lý (Manager)
####  a. Đăng kí và đăng nhập
####  b. Sử dụng các chức năng thư viện
####  c. *Trợ giúp* và *Nâng cấp*
  _Ấn "Create account" để tạo tài khoản mới
# 🐸Minh họa dự án 

# 🔭Cải tiến trong tương lai
  - Tích hợp API Chatbot AI: Sử dụng trí tuệ nhân tạo để gợi ý sách dựa trên sở thích đọc của người dùng.
  - Tính năng đa nền tảng: Phát triển ứng dụng trên các nền tảng khác như Android, IOS hoặc Web để người dùng có thể truy cập mọi lúc, mọi nơi.
  - Bổ sung giao diện đa ngôn ngữ: Hỗ trợ nhiều ngôn ngữ như tiếng Anh, tiếng Pháp, tiếng Nhật,... để phục vụ đối tượng người dùng quốc tế.
  - Cải thiện hiệu suất: Tối ưu hóa tốc độ truy vấn và tải dữ liệu khi thư viện chứa số lượng lớn sách.
  - Tạo cộng đồng đọc sách trực tuyến: Mở ra diễn đàn để người dùng chia sẻ đánh giá, bình luận, hoặc đề xuất sách hay.
  - Tích hợp công nghệ quét mã QR: Hỗ trợ người dùng mượn/trả sách bằng cách quét mã QR trên sách.
# 🍒Đóng góp
Chúng tôi mong muốn có thêm những đóng góp của quý độc giả để hoàn thiện dự án hơn !
![](https://raw.githubusercontent.com/platane/snk/output/github-contribution-grid-snake-dark.svg)

# ⚛Trạng thái dự án 
Dự án đã được hoàn thiện.
<br> <img src="https://user-images.githubusercontent.com/74038190/212284158-e840e285-664b-44d7-b79b-e264b5e54825.gif" height= "30" width="400">

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

