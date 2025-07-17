package com.generalfunction.demo.entity;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*MVCにおけるmodel,人によってはエンティティという場合もある考え方の違い？
 * この段階ではMySQLとつながってないはず…
 Lombokのアノテーションについて：
 * @Data
 *   - クラスの全フィールドに対して、getter/setter、equals、hashCode、toStringメソッドを自動生成する。
 * 
 * @AllArgsConstructor
 *   - 全フィールドを引数にとるコンストラクタを自動生成する。
 * 
 * @NoArgsConstructor
 *   - 引数なしのデフォルトコンストラクタを自動生成する。
 * 
 * これらにより、コードが簡潔になり、オブジェクトの生成やプロパティアクセスが容易になる。
 * */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {


	    private Long employeeId; // 社員番号（主キー）

	    @NotNull(message = "社員名は必須です")
	    @Size(min = 1, max = 30, message = "1文字から30文字で指定してください。")
	    private String employeeName; // 社員名

	    @NotNull(message = "パスワードは必須です")
	    @Size(min = 8, max = 255, message = "パスワードは8文字以上255文字以内で入力してください")
	    private String password; // パスワード（ハッシュ化想定）

	    @NotNull(message = "部署コードは必須です")
	    @Size(min = 1, max = 4, message = "部署コードは1～4文字で入力してください")
	    private String departmentId; // 部署コード

	    @NotNull(message = "パスワード初期設定フラグは必須です")
	    private Boolean isPassword; // パスワード初期設定フラグ

	    @NotNull(message = "有休数は必須です")
	    private Integer paidHoliday; // 有休数

	    @NotNull(message = "代休数は必須です")
	    private Integer compDay; // 代休数

	    private String departmentHistory; // 部署履歴（NULL許容）

	    private LocalDateTime createdAt; // 登録日時
	    private LocalDateTime updatedAt; // 更新日時
}
