package scoremanager.main;

import java.time.LocalDate;
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
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	//シ：ログインユーザー情報の取得
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー
	//エラー処理用
		Map<String, String> errors = new HashMap<>();// エラーリスト

	//リクエストパラメータ―の取得(絞り込みありの場合)
		String entYearStr = req.getParameter("f1");//入学年度の取得
		String classNum = req.getParameter("f2");//クラス番号の取得
		String subject = req.getParameter("f3");//科目の取得
		String test_num =req.getParameter("f4");//回数の取得
		int	entYear=0;//入学年度の数値代入用
		int num=0;//回数の数値代入用

		School school = teacher.getSchool();//ログインユーザーのschoolを取得
		List<Test> tests = null;// 検索後の表示リスト
		SubjectDao subDao = new SubjectDao();
		TestDao testDao = new TestDao();


	//シ：ユーザー情報からクラス番号取得
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		List<String> class_list = cNumDao.filter(teacher.getSchool());

//絞り込み表示処理関連

		// 入学年度が送信されていた場合
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		// 回数が送信されていた場合
		if (test_num != null) {
			// 数値に変換
			num = Integer.parseInt(test_num);
		}

		//科目データ(一覧)の取得
			SubjectDao sDao = new SubjectDao();// 科目Daoを初期化
			List<Subject> subjects = sDao.filter(teacher.getSchool());//科目の一覧を取得

		//入学年度(一覧)の取得
				LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
				int year = todaysDate.getYear();// 現在の年を取得
			// リストを初期化
				List<Integer> entYearSet = new ArrayList<>();
				// 10年前から1年後まで年をリストに追加
				for (int i = year - 10; i < year + 1; i++) {
					entYearSet.add(i);
				}

		// 全2回分のテスト回数をリストに追加
				List<Integer> numSet = new ArrayList<>();// テストの回数リストを初期化
				for (int i = 1; i <= 2; i++) {
					numSet.add(i);
				}

		// リクエストにデータをセット(絞り込み欄表示用)
				req.setAttribute("ent_year_set", entYearSet);//入学年度
				req.setAttribute("class_num_set", class_list);//クラス一覧
				req.setAttribute("subjects", subjects);//科目一覧
				req.setAttribute("num_set", numSet);//回数一覧

//絞り込み結果表示処理関連

		// 全てのパラメータがnullの場合は画面初期表示
		if (entYearStr != null && classNum != null && subject != null && test_num != null) {
			// 全てのパラメーターが選択されている場合
			if (entYear != 0 && !classNum.equals("0") && !subject.equals("0") && num != 0) {
				System.out.println("全パラメータ指定されてます！");

				// 科目コードから科目インスタンスを取得
				Subject subject_set = subDao.get(subject,school);//該当の科目データ取得
				// 成績を取得
				tests = testDao.filter(entYear, classNum, subject_set, num, school);
				// リクエストに回数をセット設定
				req.setAttribute("num", num);
				// リクエストに科目を設定
				req.setAttribute("subject", subject_set.getName());
				// リクエストに成績を設定
				req.setAttribute("tests", tests);

			} else {
				// 未選択がある場合はエラーメッセージを表示して画面再表示
				errors.put("filter", "入学年度とクラスと科目と回数を選択してください");
				req.setAttribute("errors", errors);
			}
		}

	//JSPへフォワード
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
		}
	}
