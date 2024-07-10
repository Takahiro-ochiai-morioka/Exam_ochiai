package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class StudentCreateAction  extends Action{

	public void execute(HttpServletRequest req, HttpServletResponse res)throws Exception {

		// 処理内容(シーケンス図から)
		HttpSession session = req.getSession(); // セッションの開始
		Teacher teacher = (Teacher)session.getAttribute("user");
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得

		// クラス一覧を取得
		ClassNumDao cDao = new ClassNumDao();
		List<String> clist = cDao.filter(teacher.getSchool());

		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 11; i++) {
			entYearSet.add(i);
		}

		// レスポンス値をセット
		req.setAttribute("ent_year_set", entYearSet);

		req.setAttribute("clist", clist);

		req.getRequestDispatcher("student_create.jsp").forward(req, res);


	}
}