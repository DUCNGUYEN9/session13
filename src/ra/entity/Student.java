package ra.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Student implements IEntity<Student>, Serializable {
    private String studentId;
    private String studentName;
    private int age;
    private LocalDate birthDay;
    private boolean sex;
    private float mark_html;
    private float mark_css;
    private float mark_javascript;
    private float avgMark;
    private String rank;

    public Student() {
    }

    public Student(String studentId, String studentName,
                   int age, LocalDate birthDay, boolean sex, float mark_html,
                   float mark_css, float mark_javascript, String rank) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.age = age;
        this.birthDay = birthDay;
        this.sex = sex;
        this.mark_html = mark_html;
        this.mark_css = mark_css;
        this.mark_javascript = mark_javascript;
        this.rank = rank;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public float getMark_html() {
        return mark_html;
    }

    public void setMark_html(float mark_html) {
        this.mark_html = mark_html;
    }

    public float getMark_css() {
        return mark_css;
    }

    public void setMark_css(float mark_css) {
        this.mark_css = mark_css;
    }

    public float getMark_javascript() {
        return mark_javascript;
    }

    public void setMark_javascript(float mark_javascript) {
        this.mark_javascript = mark_javascript;
    }

    public float getAvgMark() {
        return avgMark;
    }

    public void setAvgMark(float avgMark) {
        this.avgMark = avgMark;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public void inputData(Scanner scanner, List<Student> list) {
        this.studentId = validateStudentId(scanner, list);
        this.studentName = validateStudentName(scanner);
        this.birthDay = validateBirthDay(scanner);
        this.sex = validateSex(scanner);
        this.mark_html = validateMarkHTML(scanner);
        this.mark_css = validateMarkCss(scanner);
        this.mark_javascript = validateMarkJavascript(scanner);
    }

    public String validateStudentId(Scanner scanner, List<Student> list) {
        System.out.println("Nhập vào mã sinh viên:");
        do {
            String studentId = scanner.nextLine();
            if (studentId.length() == 4) {
                if (studentId.startsWith("S")) {
                    boolean isExist = false;
                    for (Student student : list) {
                        if (student.getStudentId().equals(studentId)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        return studentId;
                    } else {
                        System.err.println("Mã sinh viên đã tồn tại, vui lòng nhập lại");
                    }
                } else {
                    System.err.println("Mã sinh viên phải bắt đầu là ký tự S, vui lòng nhập lại");
                }
            } else {
                System.err.println("Mã sinh viên phải gồm 4 ký tự, vui lòng nhập lại");
            }
        } while (true);
    }

    public static String validateStudentName(Scanner scanner) {
        System.out.println("Nhập vào tên sinh viên:");
        do {
            String studentName = scanner.nextLine();
            if (studentName.length() >= 10 && studentName.length() <= 50) {
                return studentName;
            } else {
                System.err.println("Tên sinh viên có độ dài từ 10-50 ký tự, vui lòng nhập lại");
            }
        } while (true);
    }


    public static LocalDate validateBirthDay(Scanner scanner) {
        System.out.println("Nhập vào ngày sinh của sinh viên:");
        String inputDate = scanner.nextLine();

        while (!isValidDate(inputDate)) {
            System.err.println("Ngày tháng năm sinh không hợp lệ. Vui lòng nhập lại:");
            inputDate = scanner.nextLine();
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(inputDate, dateFormatter);
    }

    public static boolean isValidDate(String dateStr) {
        // Chế độ STRICT để không cho phép ngày tháng không hợp lệ
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate date = LocalDate.parse(dateStr, dateFormatter);

            int day = date.getDayOfMonth();
            Month month = date.getMonth();
            int year = date.getYear();

            // Kiểm tra ngày hợp lệ cho từng tháng
            if ((month == Month.APRIL || month == Month.JUNE || month == Month.SEPTEMBER
                    || month == Month.NOVEMBER) && day > 30) {
                return false;
            } else if (month == Month.FEBRUARY) {
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    // Năm nhuận
                    return day <= 29;
                } else {
                    // Năm không nhuận
                    return day <= 28;
                }
            } else {
                return true;
            }

        } catch (DateTimeParseException e) {
            return false;
        }
    }
    public static boolean validateSex(Scanner scanner) {
        System.out.println("Nhập vào giới tính của sinh viên:");
        do {
            String sex = scanner.nextLine();
            if (sex.equalsIgnoreCase("true") || sex.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(sex);
            } else {
                System.err.println("Giới tính chỉ nhận giá trị true | false, vui lòng nhập lại");
            }
        } while (true);
    }

    public static float validateMarkHTML(Scanner scanner) {
        System.out.println("Nhập vào điểm html");
        do {
            try {
                float mark_html = Float.parseFloat(scanner.nextLine());
                if (mark_html >= 0 && mark_html <= 10) {
                    return mark_html;
                } else {
                    System.err.println("Điểm HTML trong khoảng 0-10, vui lòng nhập lại");
                }
            } catch (NumberFormatException ex1) {
                System.err.println("Điểm html không phải định dạng số thực, vui lòng nhập lại");
            } catch (Exception ex) {
                System.err.println("Lỗi không xác định, vui lòng liên hệ hệ thống");
            }
        } while (true);
    }

    public static float validateMarkCss(Scanner scanner) {
        System.out.println("Nhập vào điểm CSS");
        do {
            try {
                float mark_css = Float.parseFloat(scanner.nextLine());
                if (mark_css >= 0 && mark_css <= 10) {
                    return mark_css;
                } else {
                    System.err.println("Điểm CSS trong khoảng 0-10, vui lòng nhập lại");
                }
            } catch (NumberFormatException ex1) {
                System.err.println("Điểm CSS không phải định dạng số thực, vui lòng nhập lại");
            } catch (Exception ex) {
                System.err.println("Lỗi không xác định, vui lòng liên hệ hệ thống");
            }
        } while (true);
    }

    public static float validateMarkJavascript(Scanner scanner) {
        System.out.println("Nhập vào điểm Javascript");
        do {
            try {
                float mark_javascript = Float.parseFloat(scanner.nextLine());
                if (mark_javascript >= 0 && mark_javascript <= 10) {
                    return mark_javascript;
                } else {
                    System.err.println("Điểm Javascript trong khoảng 0-10, vui lòng nhập lại");
                }
            } catch (NumberFormatException ex1) {
                System.err.println("Điểm Javascript không phải định dạng số thực, vui lòng nhập lại");
            } catch (Exception ex) {
                System.err.println("Lỗi không xác định, vui lòng liên hệ hệ thống");
            }
        } while (true);
    }

    @Override
    public void displayData() {
        System.out.printf("Mã sv: %s - Tên sv:  %s- năm sinh: %td/%tb/%tY - tuôi: %d\n",
                this.studentId, this.studentName, this.birthDay, this.birthDay, this.birthDay, this.age);
        System.out.printf("điểm html: %f - css: %f - javascript: %f - avgMark: %f - rank: %s\n",
                this.mark_html, this.mark_css, this.mark_javascript, this.avgMark, this.rank);

    }

    @Override
    public void calAge() {
        LocalDate now = LocalDate.now();
        Period period = Period.between(this.birthDay, now);
        this.age = period.getYears();
    }


    @Override
    public void calAvgMark_Rank() {
        this.avgMark = (mark_html + mark_css + mark_javascript) / 3;
        if (this.avgMark < 5) {
            this.rank = "Yếu";
        } else if (this.avgMark < 7) {
            this.rank = "Trung bình";
        } else if (this.avgMark < 8) {
            this.rank = "Khá";
        } else if (this.avgMark < 9) {
            this.rank = "Giỏi";
        } else {
            this.rank = "Xuất sắc";
        }
    }
}
