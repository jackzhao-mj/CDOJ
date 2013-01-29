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

package cn.edu.uestc.acmicpc.oj.db.dto;

import cn.edu.uestc.acmicpc.oj.db.entity.Department;
import cn.edu.uestc.acmicpc.oj.db.entity.User;
import cn.edu.uestc.acmicpc.util.StringUtil;

import java.sql.Timestamp;
import java.util.Date;

/**
 * collect information from register action and generate a User class.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 2
 */
public class UserDTO {

    /**
     * Input: user id, set null for new user
     */
    private Integer userId;

    /**
     * Input: user name
     */
    private String userName;

    /**
     * Input: password
     */
    private String password;

    /**
     * Input: repeat password
     */
    private String passwordRepeat;

    /**
     * Input: nick name
     */
    private String nickName;

    /**
     * Input: email
     */
    private String email;

    /**
     * Input: school
     */
    private String school;

    /**
     * Input: departmentId
     */
    private Integer departmentId;

    /**
     * Department entity
     */
    private Department department;

    /**
     * Input: student ID
     */
    private String studentId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Input: number of problems the user has solved
     */
    private Integer solved;

    public Integer getSolved() {
        return solved;
    }

    public void setSolved(Integer solved) {
        this.solved = solved;
    }

    public Integer getTried() {
        return tried;
    }

    public void setTried(Integer tried) {
        this.tried = tried;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Input: number the problems the user has tried
     */
    private Integer tried;

    /**
     * Input: User type
     */
    private Integer type;

    /**
     * Output: A user entity
     */
    private User user;

    /**
     * Build user entity according to this DTO
     *
     * @return expected user entity
     */
    public User getUser() {
        user = new User();
        user.setUserId(getUserId());
        user.setUserName(getUserName());
        user.setPassword(StringUtil.encodeSHA1(getPassword()));
        user.setNickName(getNickName());
        user.setEmail(getEmail());
        user.setSchool(getSchool());
        user.setDepartmentByDepartmentId(getDepartment());
        user.setStudentId(getStudentId());
        user.setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000));
        user.setSolved(getSolved() == null ? 0 : getSolved());
        user.setTried(getTried() == null ? 0 : getTried());
        user.setType(getType() == null ? 0 : getType());
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
