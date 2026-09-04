# Quy Tắc An Toàn Git & Giao Tiếp

## 1. Git Restriction Rule (Bảo Vệ Code Local)
- Tuyệt đối **KHÔNG** dùng lệnh GIT làm thay đổi trạng thái code (như `git pull`, `git checkout`, `git merge`, `git rebase`, `git reset`) để lấy code hoặc thay đổi nhánh.
- Luôn giữ nguyên trạng thái code local hiện tại để làm việc.
- Nếu cần file hoặc nội dung mới, hãy yêu cầu người dùng cung cấp thay vì tự ý dùng git.
- **Được phép** dùng các lệnh read-only: `git status`, `git diff`, `git log` để kiểm tra trạng thái mà không gây ảnh hưởng đến code.

## 2. Ngôn Ngữ & Giao Tiếp
- Luôn giao tiếp, giải thích ý tưởng, giải pháp và báo cáo bằng **Tiếng Việt**.
- Diễn giải ngắn gọn, trực diện, dễ hiểu và có căn cứ trực tiếp từ log/code.
