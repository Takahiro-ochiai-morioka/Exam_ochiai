<%-- 成績管理登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
<!-- 検索フォーム処理 -->
			<form method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-2">
						<label class="form-label" for="student-f1-select">入学年度 </label>
						<select class="form-select " id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select " id="student-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="student-f2-select">科目</label>
						<select class="form-select " id="student-f2-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subjects}">
								<%-- 現在のsubject.cdと選択されていたf3が一致していた場合selectedを追記 --%>
								<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="student-f2-select">回数</label>
						<select class="form-select " id="student-f2-select" name="f4">
							<option value="0">--------</option>
							<c:forEach var="num" items="${num_set}">
								<%-- 現在のnumと選択されていたf4が一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==f4}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("filter")}</div>
				</div>
			</form>
<!-- 登録処理画面 -->
		<form action="TestRegistExecute.action" method="post">
			<c:choose>
				<c:when test="${tests.size()>0}">
					<div><h2>科目：${subject} (${num}回)</h2></div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>点数</th>
						</tr>
						<c:forEach var="test" items="${tests}">
							<tr>
								<td>${test.student.entYear}</td>
								<td>${test.classNum}</td>
								<td>${test.student.no}</td>
								<td>${test.student.name}</td>
								<td><input type="number" name="point_${test.student.no}" min=0 max=100 <c:if test="${test.point != -1}"> value="${test.point}"</c:if>></td>
							</tr>
							<!-- 登録する学生番号を一覧として送る -->
							<input type="hidden" name="student_no_set[]" value="${test.student.no}"/>
						</c:forEach>
					</table>
					<input type="hidden" id="test-subject_cd-hidden" name="subject_cd" value="${subject.cd}"/>
					<input type="hidden" id="test-num-hidden" name="num" value="${num}" />
					<input type="hidden" id="test-f1-hidden" name="f1" value="${f1}" />
					<input type="hidden" id="test-f2-hidden" name="f2" value="${f2}" />
					<input type="hidden" id="test-f3-hidden" name="f3" value="${f3}" />
					<input type="hidden" id="test-f4-hidden" name="f4" value="${f4}" />
					<input type="submit" value="登録して終了">
				</c:when>
				<c:otherwise>
					<div>成績が存在しませんでした</div>
				</c:otherwise>
			</c:choose>
			</form>
		</section>
	</c:param>
</c:import>

