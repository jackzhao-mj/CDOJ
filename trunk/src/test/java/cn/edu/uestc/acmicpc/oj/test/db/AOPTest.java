package cn.edu.uestc.acmicpc.oj.test.db;
/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

import cn.edu.uestc.acmicpc.oj.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.oj.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.oj.db.entity.User;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Test cases for AOP framework
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext-test.xml"})
public class AOPTest {
    @Before
    public void init() {
        try {
            User user = new User();
            user.setUserName("admin");
            user.setPassword(StringUtil.encodeSHA1("admin"));
            user.setNickName("admin");
            user.setEmail("acm@uestc.edu.cn");
            user.setSchool("UESTC");
            user.setDepartmentByDepartmentId(departmentDAO.get(1));
            user.setStudentId("2010013100008");
            user.setLastLogin(new Timestamp(new Date().getTime()));
            User check = userDAO.getEntityByUniqueField("userName", user.getUserName());
            if (check == null)
                userDAO.add(user);
        } catch (Exception e) {
        }
    }

    @Autowired
    IUserDAO userDAO = null;

    @Autowired
    IDepartmentDAO departmentDAO = null;

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setDepartmentDAO(IDepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Test
    public void testDataBaseConnection() throws FieldNotUniqueException, AppException {
        User user = userDAO.getEntityByUniqueField("userName", "admin");
        System.out.println(user.getUserName());
    }
}
