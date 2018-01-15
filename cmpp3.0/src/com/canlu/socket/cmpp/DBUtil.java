package com.canlu.socket.cmpp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 用户数据库访问的类
 * 
 * @作者Administrator
 *@createTime 2011-12-5 上午11:55:18
 *@version 1.0
 */

public class DBUtil {
	private Connection conn;
	private Statement st;
	private PreparedStatement pps;
	private ResultSet rs;
	public String url = "jdbc:mysql://localhost:3306/sms?characterEncoding=utf-8";
	// public String url = "jdbc:oracle:thin:@172.17.100.8:1521:oradb9";
	private String user = "root";
	private String passWord = "cctv256canL#@751103";

	// 加载驱动、放在静态代码块中，保证驱动在整个项目中只加载一次，提高效率
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接的方法
	 * 
	 * @return Connection 一个有效的数据库连接
	 */
	public Connection getConnection() {
		try {
			// 注意链接时，要换成自己的数据库名，数据库用户名及密码
			Connection con = DriverManager.getConnection(url, user, passWord);
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用于执行更新的方法,包括（insert delete update）操作
	 * 
	 * @param sql
	 *            String 类型的SQL语句
	 * @return Integer 表示受影响的行数
	 */
	public int update(String sql) {
		// 定义变量用来判断更新操作是否成功，如果返回-1说明没有影响到更新操作的数据库记录条数，即更新操作失败
		int row = -1;
		try {
			// 如果数据库链接被关闭了，就要既得一个新的链接
			if (conn == null || conn.isClosed()) {
				conn = getConnection();
			}
			// 使用Connection对象conn的createStatement()创建Statement（数据库语句对象）st
			st = conn.createStatement();
			// 执行更新操作，返回影响的记录条数row
			row = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return row;
	}

	/**
	 * 基于PreparedStatement的修改方法 PreparedStatement:表示预编译的 SQL 语句的对象
	 * 
	 * @param sql
	 *            String 类型的SQL语句（insert delete update）
	 * @param obj
	 *            存放动态参数的数组
	 * @return Integer 表示受影响的行数
	 */
	public int update(String sql, Object... obj) {

		System.out.println("sql语句是" + sql);

		for (Object o : obj) {

			System.out.println("参数是:" + o);

		}
		try {
			// 获取链接
			if (conn == null || conn.isClosed()) {
				conn = getConnection();
			}
			// 创建预编译的 SQL 语句对象
			pps = conn.prepareStatement(sql);
			// 定义变量length代表数组长度，也就是预处理的sql语句中的参数个数
			int length = 0;
			// ParameterMetaData：用于获取关于 PreparedStatement 对象中每个参数的类型和属性信息的对象
			ParameterMetaData pmd = pps.getParameterMetaData();
			length = pmd.getParameterCount();
			// 循环将sql语句中的?设置为obj数组中对应的值，注意从1开始，所以i要加1
			for (int i = 0; i < length; i++) {
				pps.setObject(i + 1, obj[i]);
			}
			// 执行更新操作
			return pps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return -1;
	}

	/**
	 * 获取一条记录的方法，要依赖于下面的queryToList方法，注意泛型的使用
	 * 
	 * @param sql
	 * @return　Map<String,Object>
	 */
	public Map<String, Object> getOneRow(String sql) {
		// 执行下面的queryToList方法
		List<Map<String, Object>> list = queryToList(sql);
		// 三目运算，查询结果list不为空返回list中第一个对象,否则返回null
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 返回查询结果列表，形如：[{TEST_NAME=aaa, TEST_NO=2, TEST_PWD=aaa}, {TEST_NAME=bbb,
	 * TEST_NO=3, TEST_PWD=bbb}...]
	 * 
	 * @param sql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryToList(String sql) {
		// 创建集合列表用以保存所有查询到的记录
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		try {
			if (conn == null || conn.isClosed()) {
				conn = getConnection();
			}
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			// ResultSetMetaData 是结果集元数据，可获取关于 ResultSet 对象中列的类型和属性信息的对象
			// 例如：结果集中共包括多少列，每列的名称和类型等信息
			ResultSetMetaData rsmd = rs.getMetaData();
			// 获取结果集中的列数
			int columncount = rsmd.getColumnCount();
			// while条件成立表明结果集中存在数据
			while (rs.next()) {
				// 创建一个HashMap用于存储一条数据
				HashMap<String, Object> onerow = new HashMap<String, Object>();
				// 循环获取结果集中的列名及列名所对应的值，每次循环都得到一个对象，形如：{TEST_NAME=aaa, TEST_NO=2,
				// TEST_PWD=aaa}
				for (int i = 0; i < columncount; i++) {
					// 获取指定列的名称，注意orcle中列名的大小写
					String columnName = rsmd.getColumnName(i + 1);
					onerow.put(columnName, rs.getObject(i + 1));
				}
				// 将获取到的对象onewrow={TEST_NAME=aaa, TEST_NO=2,
				// TEST_PWD=aaa}放到集合列表中
				list.add(onerow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	/**
	 * 返回查询结果列表,使用的是预编绎SQL 语句对象PreparedStatement 形如：[{TEST_NAME=aaa, TEST_NO=2,
	 * TEST_PWD=aaa}, {TEST_NAME=bbb, TEST_NO=3, TEST_PWD=bbb}]
	 * 
	 * @param sql
	 * @param paramValues
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryWithParam(String sql,
			Object... paramValues) {

		for (Object aa : paramValues) {
			System.out.println("参数===>" + aa);
		}

		System.out.println("打印当前sql语句" + sql);

		// 创建集合列表用以保存所有查询到的记录
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		try {
			if (conn == null || conn.isClosed()) {
				conn = getConnection();
			}

			pps = conn.prepareStatement(sql);
			for (int i = 0; i < paramValues.length; i++) {

				pps.setObject(i + 1, paramValues[i]);
			}
			rs = pps.executeQuery();
			// ResultSetMetaData 是结果集元数据，可获取关于 ResultSet 对象中列的类型和属性信息的对象
			// 例如：结果集中共包括多少列，每列的名称和类型等信息
			ResultSetMetaData rsmd = rs.getMetaData();
			// 获取结果集中的列数
			int columncount = rsmd.getColumnCount();
			// while条件成立表明结果集中存在数据
			while (rs.next()) {
				// 创建一个HashMap用于存储一条数据
				HashMap<String, Object> onerow = new HashMap<String, Object>();
				// 循环获取结果集中的列名及列名所对应的值，每次循环都得到一个对象，形如：{TEST_NAME=aaa, TEST_NO=2,
				// TEST_PWD=aaa}
				for (int i = 0; i < columncount; i++) {
					// 获取指定列的名称，注意orcle中列名的大小写
					String columnName = rsmd.getColumnName(i + 1);
					onerow.put(columnName, rs.getObject(i + 1));
				}
				// 将获取到的对象onewrow={TEST_NAME=aaa, TEST_NO=2,
				// TEST_PWD=aaa}放到集合列表中
				list.add(onerow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	//	 
	/**
	 * 实现oracle分页功能
	 * 
	 * @param sql
	 * @param pagesize
	 * @param pagenow
	 * @return PageBean
	 */
	public List<Map<String, Object>> getPage(String sql, int pagesize,
			int pagenow, Object... paramValues) {
		int end = pagenow * pagesize;
		int start = end - pagesize + 1;
		String exesql = "select a.* from (select t.*,rownum as rowindex from ("
				+ sql + ") t where rownum<=" + end + " ) a where a.rowindex>="
				+ start;
		return queryWithParam(exesql, paramValues);// 最终显示在页面的数据;
	}

	/**
	 * 关闭数据库各种资源Connection Statement PreparedStatement ResultSet的方法
	 */
	private void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pps != null) {
			try {
				pps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			if (conn != null && !conn.isClosed()) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
