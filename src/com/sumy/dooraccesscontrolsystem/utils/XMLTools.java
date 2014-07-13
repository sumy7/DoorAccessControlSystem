package com.sumy.dooraccesscontrolsystem.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.sumy.dooraccesscontrolsystem.entity.Admin;
import com.sumy.dooraccesscontrolsystem.entity.Employee;
import com.sumy.dooraccesscontrolsystem.entity.Manager;
import com.sumy.dooraccesscontrolsystem.entity.User;

/**
 * 对XML进行操作，将用户列表写入或读出
 * 
 * @author sumy
 * 
 */
public class XMLTools {

    /**
     * 构造函数私有，防止实例化对象
     */
    private XMLTools() {
    }

    /**
     * 将用户列表写入到XML文件中
     * 
     * @param path
     *            要写入文件的存储目录
     * @param list
     *            要写入的用户列表
     * @return 写入是否成功
     */
    public static boolean writeXML(String path, ArrayList<User> list) {
        try {
            // 打开文件
            File file = new File(path);
            FileOutputStream os = new FileOutputStream(file);
            StringWriter writer = new StringWriter();

            // 新建xml写入器
            XmlSerializer xmlSerializer = Xml.newSerializer();
            // 重定向到字符串中
            xmlSerializer.setOutput(writer);
            // 设置xml文档头
            xmlSerializer.startDocument("utf-8", true);
            // 写开始标签
            xmlSerializer.startTag(null, "users");

            // 遍历列表，根据对象类型写出标签和属性
            for (User user : list) {
                if (user instanceof Admin) {
                    Admin admin = (Admin) user;
                    xmlSerializer.startTag(null, "admin");
                    xmlSerializer.attribute(null, "id", admin.getUserid());
                    xmlSerializer.attribute(null, "name", admin.getName());
                    xmlSerializer.attribute(null, "pwd", admin.getPassword());
                    xmlSerializer.endTag(null, "admin");
                } else if (user instanceof Employee) {
                    Employee employee = (Employee) user;
                    xmlSerializer.startTag(null, "employee");
                    xmlSerializer.attribute(null, "id", employee.getUserid());
                    xmlSerializer.attribute(null, "name", employee.getName());
                    xmlSerializer.attribute(null, "cardid",
                            employee.getCardid());
                    xmlSerializer.attribute(null, "photo", employee.getPhoto());
                    xmlSerializer.attribute(null, "hascard",
                            employee.isHasCard() ? "true" : "false");
                    xmlSerializer.endTag(null, "employee");
                } else if (user instanceof Manager) {
                    Manager manager = (Manager) user;
                    xmlSerializer.startTag(null, "manager");
                    xmlSerializer.attribute(null, "id", manager.getUserid());
                    xmlSerializer.attribute(null, "name", manager.getName());
                    xmlSerializer
                            .attribute(null, "finger", manager.getFigure());
                    xmlSerializer.endTag(null, "manager");
                }
            }
            // 写结束标签
            xmlSerializer.endTag(null, "users");
            // 写结束文档 BUG：没有将导致写出字符串为空
            xmlSerializer.endDocument();

            // Log.i("mytag", "xml:" + writer.toString());
            // 将字符串保存到文件中
            os.write(writer.toString().getBytes());
            os.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将XML文件解析成用户列表
     * 
     * @param path
     *            要解析的XML目录
     * @return 解析的文件列表，解析失败时返回<b>null</b>
     */
    public static ArrayList<User> readXML(String path) {
        ArrayList<User> list = null;
        try {
            File file = new File(path);
            FileInputStream input = new FileInputStream(file);
            //新建xml读取器
            XmlPullParser xmlPullParser = Xml.newPullParser();

            //设置输入编码
            xmlPullParser.setInput(input, "utf-8");

            User user = null;
            // 获取标签类型
            int type = xmlPullParser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {
                type = xmlPullParser.next();
                switch (type) {
                case XmlPullParser.START_TAG:
                    String tag = xmlPullParser.getName();
                    if ("users".equals(tag)) {
                        // 若为 users 标签，新建用户列表
                        list = new ArrayList<User>();
                    } else if ("admin".equals(tag)) {
                        // 依照顺序获取 admin 的属性
                        user = new Admin(xmlPullParser.getAttributeValue(0),
                                xmlPullParser.getAttributeValue(1),
                                xmlPullParser.getAttributeValue(2));
                    } else if ("manager".equals(tag)) {
                        // 依照顺序获取 manager 的属性
                        user = new Manager(xmlPullParser.getAttributeValue(0),
                                xmlPullParser.getAttributeValue(1),
                                xmlPullParser.getAttributeValue(2));
                    } else if ("employee".equals(tag)) {
                        // 依照顺序获取 employee 的属性
                        user = new Employee(xmlPullParser.getAttributeValue(0),
                                xmlPullParser.getAttributeValue(1),
                                xmlPullParser.getAttributeValue(2),
                                xmlPullParser.getAttributeValue(3),
                                xmlPullParser.getAttributeValue(4).equals(
                                        "true") ? true : false);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (!"users".equals(xmlPullParser.getName())) {
                        // 非 users 标签时保存用户信息
                        list.add(user);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
