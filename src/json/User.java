package json;

import java.util.Date;

/**
 *@auth wws
 *@date 2018年3月21日---下午6:36:41
 *
 **/
public class User {
  
  private String name;
  private Integer age;
  private Date birthday;
  private String email;

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public Integer getAge() {
      return age;
  }

  public void setAge(Integer age) {
      this.age = age;
  }

  public Date getBirthday() {
      return birthday;
  }

  public void setBirthday(Date birthday) {
      this.birthday = birthday;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  @Override
  public String toString() {
      return "User{" +
              "name='" + name + '\'' +
              ", age=" + age +
              ", birthday=" + birthday +
              ", email='" + email + '\'' +
              '}';
  }

}
