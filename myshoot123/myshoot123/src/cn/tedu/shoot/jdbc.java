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
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; //������ʹ��com.mysql.jdbc.Driver
    String url = "jdbc:sqlserver://localhost:1433;DatabaseName=ranking"; //�����ݿ�����
    String username = "sa"; //���ݿ��û���
    String password = "123321123"; //���ݿ��û�����
    Connection conn = null;
    try {
        Class.forName(driver); //classLoader,���ض�Ӧ����
        conn = (Connection) DriverManager.getConnection(url, username, password);
       System.out.println("���ݿ����ӳɹ�");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return conn;
}
public static void insert(String s1,String s2) {  	          
    conn = getConnection(); // ����Ҫ��ȡ���ӣ������ӵ����ݿ�  	  
    try {  
        String sql = "insert into rank1 values('"+
				s1+"','"+s2+"')"; // �������ݵ�sql���  	            
        st = (Statement) conn.createStatement();    // ��������ִ�о�̬sql����Statement����  	              
        int count = st.executeUpdate(sql);  // ִ�в��������sql��䣬�����ز������ݵĸ���  	              
        System.out.println("��rank1���в��� " + count + " ������"); //�����������Ĵ�����  	              
        conn.close();   //�ر����ݿ�����  	              
    } catch (SQLException e) {  
        System.out.println("��������ʧ��" + e.getMessage());  
    }  
}

public static void query() { 
	//rowData = new Vector();
    conn = getConnection(); //ͬ����Ҫ��ȡ���ӣ������ӵ����ݿ�  
    try {  
        String sql = "select username,grade from rank1";     // ��ѯ���ݵ�sql���  
        st = (Statement) conn.createStatement();    //��������ִ�о�̬sql����Statement����st���ֲ�����  
        ResultSet rs = st.executeQuery(sql);    //ִ��sql��ѯ��䣬���ز�ѯ���ݵĽ����  
        System.out.println("���Ĳ�ѯ���Ϊ��");  
        while (rs.next()) { // �ж��Ƿ�����һ������  
            // �����ֶ�����ȡ��Ӧ��ֵ  
            String username = rs.getString("username");
            String score =rs.getString("grade"); 
            System.out.println("����"+"  "+"����");
            //����鵽�ļ�¼�ĸ����ֶε�ֵ  
            System.out.println(username + " " + score + " " );  
          	            }  
        conn.close();   //�ر����ݿ�����                
    } catch (SQLException e) {  
        System.out.println("��ѯ����ʧ��");  
    }  
}

}

