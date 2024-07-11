package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	//ユーザー情報の取得
		HttpSession session = req.getSession(true);// セッションを取得
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー
	// ログインユーザーの学校を取得
		School school = teacher.getSchool();

		// 入力された得点用マップ
		Map<String, String> inputPoints = new HashMap<>();
		// 成績リストを初期化
		List<Test> gradeList = new ArrayList<>();

	//リクエストパラメータ―の取得
		// 入力された科目コードを取得
		String subjectCd = req.getParameter("subject_cd");
		// 入力されたテスト回数を取得
		int num = Integer.parseInt(req.getParameter("num"));
		// 入力された学生番号の一覧を取得
		String[] studentNoSet = req.getParameterValues("student_no_set[]");
		// 科目コードから科目インスタンスを取得
		SubjectDao subjectDao = new SubjectDao();// 科目Daoを初期化
		Subject subject = subjectDao.get(subjectCd, school);

	// 成績データ保存※1人ずつ登録処理を実行
		// 学生情報を1人ずつ取得し全件走査(拡張for文)
		for (String studentNo : studentNoSet) {
			// 成績インスタンスを初期化
			Test test = new Test();
			// 入力された「point_学生番号」の得点文字列を取得
			String pointStr = req.getParameter("point_" + studentNo);
			// 得点用マップ(2次元配列)に学生番号と得点文字列を格納
			inputPoints.put(studentNo, pointStr);

			//pointStrが空になるとtureを返す
			if (pointStr.isEmpty()) {
				continue;
			}

			// 得点文字列を整数に変換
			int point = Integer.parseInt(pointStr);

			// 成績インスタンスに値をセット
			StudentDao studentDao = new StudentDao();// 学生Daoを初期化
			test.setNo(num);
			test.setPoint(point);
			test.setSchool(school);
			test.setStudent(studentDao.get(studentNo));
			test.setSubject(subject);
			gradeList.add(test);
		}

		// 成績Daoを初期化
		TestDao testDao = new TestDao();
		// 成績リストから成績を保存
		testDao.save(gradeList);

		// 完了ページにフォワード
		req.getRequestDispatcher("test_regist_done.jsp").forward(req,res);
	}
}
