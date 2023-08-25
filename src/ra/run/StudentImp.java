package ra.run;

import ra.entity.IEntity;
import ra.entity.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StudentImp {
    public static Scanner scanner = new Scanner(System.in);
    public static List<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        readDataFromFile();
        do {
            System.out.println("*****************************MENU************************\n" +
                    "1. Nhập thông tin các sinh viên\n" +
                    "2. Tính tuổi các sinh viên\n" +
                    "3. Tính điểm trung bình và xếp loại sinh viên\n" +
                    "4. Sắp xếp sinh viên theo tuổi tăng dần\n" +
                    "5. Thống kê sinh viên theo xếp loại sinh viên\n" +
                    "6. Cập nhật thông tin sinh viên theo mã sinh viên\n" +
                    "7. Tìm kiếm sinh viên theo tên sinh viên\n" +
                    "8. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        calAgeStudent();
                        break;
                    case 3:
                        calAvgRankStudent();
                        break;
                    case 4:
                        sortStudent();
                        break;
                    case 5:
                        statisticRank();
                        break;
                    case 6:
                        updateStudent();
                        break;
                    case 7:
                        searchStudent();
                        break;
                    case 8:
                        writeDataToFile();
                        System.exit(0);
                    default:
                        System.err.println("vui lòng nhập trong khoảng 1 -8 ");
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Vui lòng chọn số");
            } catch (Exception ex) {
                System.err.println("Có lỗi không xác định, vui lòng liên hệ hệ thống");
            }
        } while (true);
    }

    public static void addStudent() {
        try {
            System.out.print("Nhập vào số lượng học sinh muốn thêm: ");
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < n; i++) {
                Student student = new Student();
                student.inputData(scanner, studentList);
                studentList.add(student);
            }
        }catch (NumberFormatException numberFormatException){
            System.err.println("vui long nhập số");
        }catch (Exception e){
            System.err.println("lỗi chưa xác định hãy liên hệ hệ thống");
        }

    }

    public static void calAgeStudent() {
        for (Student student : studentList) {
            student.calAge();
        }
        System.out.println("da tinh xong");
    }

    public static void calAvgRankStudent() {
        for (Student student : studentList) {
            student.calAvgMark_Rank();
        }
        System.out.println("da tinh xong");
    }

    public static void sortStudent() {
        studentList.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getAge() - o2.getAge();
            }
        });
        for (Student student : studentList) {
            student.displayData();
        }
    }

    public static void statisticRank() {
        int yeuNum = 0, gioiNum = 0, tbNum = 0, khaNum = 0, xsNum = 0;

        for (Student student : studentList) {
            if (student.getRank().equals("Yếu")) {
                yeuNum++;
            } else if (student.getRank().equals("Trung bình")) {
                tbNum++;
            } else if (student.getRank().equals("Khá")) {
                khaNum++;
            } else if (student.getRank().equals("Giỏi")) {
                gioiNum++;
            } else {
                xsNum++;
            }
        }
        System.out.printf("Yếu: %d - Trung bình: %d - Khá: %d - Giỏi: %d - Xuất sắc: %d\n",
                yeuNum, tbNum, khaNum, gioiNum, xsNum);
    }

    public static void updateStudent() {
        try {
            System.out.println("Nhập ma sinh viên cập nhật");
            String idStudent = scanner.nextLine();
            for (Student student : studentList) {
                if (student.getStudentId().equalsIgnoreCase(idStudent)) {
                    student.setStudentName(Student.validateStudentName(scanner));
                    student.setSex(Student.validateSex(scanner));
                    student.setBirthDay(Student.validateBirthDay(scanner));
                    student.setMark_html(Student.validateMarkHTML(scanner));
                    student.setMark_css(Student.validateMarkCss(scanner));
                    student.setMark_javascript(Student.validateMarkJavascript(scanner));
                    student.calAge();
                    student.calAvgMark_Rank();

                }
            }
        }catch(Exception exception){
            System.err.println("lỗi chưa xác định hãy liên hệ hệ thống");
        }
    }

    public static void searchStudent() {
        System.out.println("Nhập tên sinh viên tim kiem");
        String nameStudent = scanner.nextLine();
        for (Student student : studentList) {
            if (student.getStudentName().toLowerCase().contains(nameStudent.toLowerCase())) {
                student.displayData();
            }
        }
    }

    public static void writeDataToFile() {
        File file = new File("listStudent.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(studentList);
            oos.flush();
        } catch (FileNotFoundException ex1) {
            System.err.println("File không tồn tại");
        } catch (IOException ex2) {
            System.err.println("Lỗi khi ghi dữ liệu ra file");
        } catch (Exception ex) {
            System.err.println("Xảy ra lỗi trong quá trình ghi dữ liệu ra file");
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex1) {
                System.err.println("Xảy ra lỗi khi đóng các stream");
            } catch (Exception ex) {
                System.err.println("Xảy ra lỗi trong quá trình đóng các stream");
            }
        }
    }

    public static void readDataFromFile() {
        File file = new File("listStudent.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            if (ois.readObject() != null) {
                studentList = (List<Student>) ois.readObject();
            }
        } catch (FileNotFoundException ex1) {
            System.err.println("Không tồn tại file");
        } catch (IOException ex2) {
            System.err.println("Lỗi khi đọc file");
        } catch (Exception ex) {
            System.err.println("Có lỗi trong quá trình đọc dữ liệu từ file");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ex1) {
                System.err.println("Có lỗi khi đóng stream");
            } catch (Exception ex) {
                System.err.println("Có lỗi trong quá trình đóng các stream");
            }
        }
    }
}
