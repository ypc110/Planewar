package cn.tedu.shoot;

import java.sql.*;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
public class jdbc{
	 static Connection conn;  
	  
	    static Statement st;  
	    static JTable jt=null;
	    static JScrollPane jsp=null;
	    static Vector<String> columnNames;
	    static Vector<Vector<String>> rowData;
public static Connection getConnection() {
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; //若报错使用com.mysql.jdbc.Driver
    String url = "jdbc:sqlserver://localhost:1433;DatabaseName=ranking"; //是数据库名称
    String username = "sa"; //数据库用户名
    String password = "123321123"; //数据库用户密码
    Connection conn = null;
    try {
        Class.forName(driver); //classLoader,加载对应驱动
        conn = (Connection) DriverManager.getConnection(url, username, password);
       System.out.println("数据库连接成功");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return conn;
}
public static void insert(String s1,String s2) {  	          
    conn = getConnection(); // 首先要获取连接，即连接到数据库  	  
    try {  
        String sql = "insert into rank1 values('"+
				s1+"','"+s2+"')"; // 插入数据的sql语句  	            
        st = (Statement) conn.createStatement();    // 创建用于执行静态sql语句的Statement对象  	              
        int count = st.executeUpdate(sql);  // 执行插入操作的sql语句，并返回插入数据的个数  	              
        System.out.println("向rank1表中插入 " + count + " 条数据"); //输出插入操作的处理结果  	              
        conn.close();   //关闭数据库连接  	              
    } catch (SQLException e) {  
        System.out.println("插入数据失败" + e.getMessage());  
    }  
}

public static void query() { 
	//rowData = new Vector();
    conn = getConnection(); //同样先要获取连接，即连接到数据库  
    try {  
        String sql = "select username,grade from rank1";     // 查询数据的sql语句  
        st = (Statement) conn.createStatement();    //创建用于执行静态sql语句的Statement对象，st属局部变量  
        ResultSet rs = st.executeQuery(sql);    //执行sql查询语句，返回查询数据的结果集  
        System.out.println("最后的查询结果为：");  
        while (rs.next()) { // 判断是否还有下一个数据  
            // 根据字段名获取相应的值  
            String username = rs.getString("username");
            String score =rs.getString("grade"); 
            System.out.println("姓名"+"  "+"分数");
            //输出查到的记录的各个字段的值  
            System.out.println(username + " " + score + " " );  
          	            }  
        conn.close();   //关闭数据库连接                
    } catch (SQLException e) {  
        System.out.println("查询数据失败");  
    }  
}

}

