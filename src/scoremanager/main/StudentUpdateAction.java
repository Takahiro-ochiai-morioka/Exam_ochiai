package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action{

	public void execute(HttpServletRequest req, HttpServletResponse res)throws Exception {

		String no = req.getParameter("no");

		StudentDao sDao = new StudentDao();
		Student stu = sDao.get(no);

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> clist = cNumDao.filter(school);

		req.setAttribute("stu_date", stu);
		req.setAttribute("clist", clist);

		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}
}