package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action{

	public void execute(HttpServletRequest req, HttpServletResponse res)throws Exception {

		int ent_year = Integer.parseInt(req.getParameter("ent_year"));
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String class_num = req.getParameter("class_num");
		String si_attend = req.getParameter("si_attend");
		boolean attend = true;

		if(si_attend == null) {
			attend = false;
		}

		Student stu = new Student();
		stu.setEntYear(ent_year);
		stu.setNo(no);
		stu.setName(name);
		stu.setClassNum(class_num);
		stu.setAttend(attend);

		StudentDao sDao = new StudentDao();
		sDao.save(stu);

		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	}
}