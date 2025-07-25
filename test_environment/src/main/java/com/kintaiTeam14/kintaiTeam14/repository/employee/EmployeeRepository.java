package com.kintaiTeam14.kintaiTeam14.repository.employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.kintaiTeam14.kintaiTeam14.entity.Employee;

import lombok.RequiredArgsConstructor;

/**
 * UserRepositoryクラスはユーザー情報をDBから取得・登録する役割を担う。
 * Springの@RepositoryでBean登録され、JdbcTemplateを使ってSQL実行を行う。
 */
@Repository
@RequiredArgsConstructor
public class EmployeeRepository {

	// Spring Bootが自動生成するJdbcTemplateをDIで受け取る
	private final JdbcTemplate jdbcTemplate;
	private final PasswordEncoder passwordEncoder;

	/**
	 * DBのResultSetからUserオブジェクトへマッピングするためのRowMapper実装
	 * RowMapper<T>は、Spring Frameworkのorg.springframework.jdbc.coreパッケージにあるインターフェースで、JDBCのResultSet
	 * （SQLの実行結果の行）をJavaオブジェクト（エンティティなど）に変換するための仕組み
	 */
	private static final RowMapper<Employee> USER_ROW_MAPPER = new RowMapper<Employee>() {
		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee employee = new Employee();
			employee.setEmployeeId(rs.getLong("employee_id")); // カラム名に合わせる
			employee.setEmployeeName(rs.getString("employee_name"));
			employee.setPassword(rs.getString("password"));
			employee.setDepartmentId(rs.getString("department_id"));
			employee.setIsPassword(rs.getBoolean("is_password"));
			employee.setPaidHoliday(rs.getInt("paid_holiday"));
			employee.setCompDay(rs.getInt("comp_day"));
			// department_historyはNULL許容
			employee.setDepartmentHistory(rs.getString("department_history"));
			// nullチェック付きでセット
			employee.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			employee.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			return employee;
		}
	};

	/**
	 * 指定したユーザー名でユーザーを検索する。
	 * 見つからなければnullを返す。
	 *
	 * @param username 検索するユーザー名
	 * @return Userオブジェクトまたはnull
	 */

	public Employee findByEmployeeName(String employeename) {
		String sql = "SELECT * FROM employee  WHERE employee_name = ?";
		List<Employee> employees = jdbcTemplate.query(sql, USER_ROW_MAPPER, employeename);
		return employees.isEmpty() ? null : employees.get(0);
	}

	/**
	 * 指定したユーザー名でユーザーを検索する。(部分一致で返す)
	 * 見つからなければnullを返す。
	 *
	 * @param username 検索するユーザー名
	 * @return Userオブジェクトまたはnull
	 */
	public List<Map<String, Object>> findByEmployeeNameAll(String employeename){
		String sql = "SELECT * FROM employee WHERE employee_name LIKE ?";
		String p="%"+employeename+"%";
		List<Map<String, Object>> employees = jdbcTemplate.queryForList(sql, p);
		System.out.println(employees);
		System.out.println(employeename);
		return employees.isEmpty() ? null : employees;
	}

	/**
	 * 新しいユーザーをDBに登録する。
	 * created_at、updated_atは現在時刻で自動設定される。
	 *
	 * @param employee 登録するUserオブジェクト
	 * @return 登録に成功したレコード数（通常1）
	 */

	public int insertEmployee(Employee employee) {
		String sql = "INSERT INTO employee (" +
				"employee_id, employee_name, password, department_id, is_password, paid_holiday, comp_day, department_history, created_at, updated_at" +
				") VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		return jdbcTemplate.update(sql,
				employee.getEmployeeId(),
				employee.getEmployeeName(),
				employee.getPassword(),
				employee.getDepartmentId(),
				employee.getIsPassword() != null ? (employee.getIsPassword() ? 1 : 0) : null,
						employee.getPaidHoliday(),
						employee.getCompDay(),
						employee.getDepartmentHistory()
				);
	}
	/**
	 * すべてのユーザーを取得する。
	 *
	 * @return ユーザーのリスト
	 */
	public List<Employee> findAll() {
		String sql = "SELECT * FROM users ORDER BY id";
		return jdbcTemplate.query(sql, USER_ROW_MAPPER);
	}

    /**
     * 指定したIDのユーザーを取得する。
     * 見つからなければnullを返す。
     *
     * @param id ユーザーID
     * @return Userオブジェクトまたはnull
     */
    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        List<Employee> employees = jdbcTemplate.query(sql, USER_ROW_MAPPER, id);
        return employees.isEmpty() ? Optional.empty() : Optional.of(employees.get(0));
    }

	/**
	 * 指定したIDのユーザーを削除する。
	 *
	 * @param id ユーザーID
	 * @return 削除したレコード数（通常1）
	 */
	public int deleteById(Long id) {
		String sql = "DELETE FROM employee WHERE employee_id = ?";
		return jdbcTemplate.update(sql, id);
	}


	/**
	 * 社員情報を保存するメソッド。
	 * employeeIdがnullなら新規登録、そうでなければ更新を行う。
	 * @param employee 保存するEmployeeオブジェクト
	 * @return 更新・挿入されたレコード数（通常1）
	 */


	public int save(Employee employee) {
		if (employee.getEmployeeId() == null) {
			// 新規
			return insertEmployee(employee);
		} else {
			// 更新
			String sql = "UPDATE employee SET " +
					"employee_name = ?, " +
					"password = ?, " +
					"department_id = ?, " +
					"is_password = ?, " +
					"paid_holiday = ?, " +
					"comp_day = ?, " +
					"department_history = ?, " +
					"updated_at = NOW() " +
					"WHERE employee_id = ?";
			return jdbcTemplate.update(sql,
					employee.getEmployeeName(),
					employee.getPassword(),
					employee.getDepartmentId(),
					employee.getIsPassword() != null ? (employee.getIsPassword() ? 1 : 0) : null,
							employee.getPaidHoliday(),
							employee.getCompDay(),
							employee.getDepartmentHistory(),
							employee.getEmployeeId()
					);
		}
	}

	/*
	 * パスワード変更メソッド
	 */
	public int updatePassword(Long employeeId, String newPassword) {
		String sql = "UPDATE employee SET password = ?, updated_at = NOW() WHERE employee_id = ?";
		return jdbcTemplate.update(sql, newPassword, employeeId);
	}

	public String findPassWordByEmployeeId(Long employeeId) {
		String sql = "SELECT  password FROM employee  WHERE employee_id = ?";
		return jdbcTemplate.queryForObject(sql, String.class, employeeId);
	}


	public boolean changePassword(Long employeeId,  String CurrentPass) {
		String pass=findPassWordByEmployeeId(employeeId);
		System.out.println("pass:"+pass);
		System.out.println("encodedCurrentPass:"+CurrentPass);

		return passwordEncoder.matches( CurrentPass, pass);


	}

	public void changeIsPassword(Long employeeId) {
		String sql = "UPDATE employee SET  is_password = false, updated_at = NOW() WHERE employee_id = ?";
		jdbcTemplate.update(sql, employeeId);
	}
}